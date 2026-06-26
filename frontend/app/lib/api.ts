/**
 * REST API クライアント (CSR 専用 / Step 3.3)。
 *
 * - 常に JSON。`credentials: "include"` で HttpOnly Cookie (access/refresh) を送る。
 * - base path は env で切替 (既定 `/wolf-mansion-api` = backend context-path)。dev は Vite proxy
 *   が同パスを backend へ転送する (vite.config.ts)。frontend 自体は `/wolf-mansion` 配下。
 * - backend のエラーは ProblemDetail (RFC7807) `{type,title,status,detail,error}`。
 *   これを解析し {@link ApiError} を throw する。`error` はコード文字列。
 * - SSR では呼ばない (認証は CSR 境界。03-auth.md)。Cookie が無い SSR fetch は無意味。
 */

// 末尾スラッシュは付けない (path 側を `/api/...` で始める)。
const API_BASE = import.meta.env.VITE_API_BASE ?? "/wolf-mansion-api";

/**
 * frontend (`/wolf-mansion`) が配信する静的アセット (`public/` 配下) の URL を作る。
 * `path` は先頭スラッシュ始まり (例: `/app/images/top.jpg`)。`import.meta.env.BASE_URL` は
 * Vite の `base` (末尾スラッシュ付き) なので重複を除いて連結する。
 */
export function assetUrl(path: string): string {
  const base = import.meta.env.BASE_URL.replace(/\/$/, "");
  return `${base}${path}`;
}

/**
 * ProblemDetail の `error` コード (UI 分岐に使う)。backend の RestApiExceptionHandler /
 * JwtAuthenticationEntryPoint が返しうる全コード。`ApiError.code` は network 由来のため型は
 * `string` のままにし、この union はドキュメント兼参照用に公開する。
 */
export type ApiErrorCode =
  | "authentication_failed"
  | "unauthorized"
  | "too_many_requests"
  | "not_found"
  | "business_error"
  | "validation_error"
  | "internal_error";

/** ProblemDetail の `fieldErrors` 要素 (検証エラーのフィールド単位の内訳)。 */
export type ApiFieldError = {
  field: string;
  message: string;
};

/** backend が返す ProblemDetail を表すエラー。`code` で UI 分岐する。 */
export class ApiError extends Error {
  constructor(
    readonly status: number,
    readonly code: string,
    readonly detail: string,
    readonly fieldErrors: ApiFieldError[] = [],
  ) {
    super(detail || `HTTP ${status}`);
    this.name = "ApiError";
  }
}

type ProblemDetail = {
  status?: number;
  detail?: string;
  title?: string;
  error?: string;
  fieldErrors?: ApiFieldError[];
};

async function toApiError(response: Response): Promise<ApiError> {
  let body: ProblemDetail = {};
  try {
    body = (await response.json()) as ProblemDetail;
  } catch {
    // ProblemDetail でない (HTML エラーページ等)。status だけで組み立てる。
  }
  const code = body.error ?? "internal_error";
  const detail = body.detail ?? body.title ?? `HTTP ${response.status}`;
  return new ApiError(response.status, code, detail, body.fieldErrors ?? []);
}

type ApiFetchOptions = {
  method?: "GET" | "POST" | "PUT" | "DELETE";
  /**
   * リクエスト body。FormData はそのまま送り (multipart、Content-Type はブラウザが boundary
   * 込みで付ける)、それ以外は JSON シリアライズする。GET では指定しない。
   */
  body?: unknown;
};

/**
 * access token (15分) 切れの 401 を refresh token で立て直す。並行リクエストが同時に 401 に
 * なっても refresh は 1 回だけ走らせる (rotation 方式のため同じ refresh token の二重提示は
 * 漏洩検知で全失効してしまう)。refresh 自体が失敗したら未ログインとして扱う。
 */
let refreshPromise: Promise<boolean> | null = null;

function refreshTokens(): Promise<boolean> {
  refreshPromise ??= fetch(`${API_BASE}/api/v1/auth/refresh`, {
    method: "POST",
    credentials: "include",
  })
    .then(async (res) => {
      // body を消費しないとリクエストが完了扱いにならず残り続ける (中身は使わない)
      await res.text().catch(() => {});
      return res.ok;
    })
    .catch(() => false)
    .finally(() => {
      refreshPromise = null;
    });
  return refreshPromise;
}

/**
 * refresh によるリトライをしないパス。login/signup の 401 は資格情報の誤り、logout/refresh は
 * それ自体がトークン操作なのでリトライしない。`/auth/me` は対象に含める (access 切れでも
 * refresh が生きていればログイン中と判定できる)。
 */
const NO_REFRESH_PATHS = [
  "/api/v1/auth/login",
  "/api/v1/auth/signup",
  "/api/v1/auth/logout",
  "/api/v1/auth/refresh",
];

function isNoRefreshPath(path: string): boolean {
  return NO_REFRESH_PATHS.includes(path);
}

/**
 * API を叩いて JSON を返す。204 (No Content) は `undefined` を返す。
 * 非 2xx は {@link ApiError} を throw する。401 は一度だけ refresh → リトライする
 * (auth 系パスを除く。FormData はストリーム消費済みの可能性がないのでそのまま再送できる)。
 */
export async function apiFetch<T>(path: string, options: ApiFetchOptions = {}): Promise<T> {
  const { method = "GET", body } = options;
  const isFormData = body instanceof FormData;
  const doFetch = () =>
    fetch(`${API_BASE}${path}`, {
      method,
      credentials: "include",
      headers:
        body === undefined || isFormData ? undefined : { "Content-Type": "application/json" },
      body: body === undefined ? undefined : isFormData ? body : JSON.stringify(body),
    });

  let response = await doFetch();
  if (response.status === 401 && !isNoRefreshPath(path) && (await refreshTokens())) {
    response = await doFetch();
  }

  if (!response.ok) {
    throw await toApiError(response);
  }
  if (response.status === 204) {
    return undefined as T;
  }
  return (await response.json()) as T;
}
