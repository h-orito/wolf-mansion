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
 * backend (context-path `/wolf-mansion-api`) 配下の URL を作る。移行中の **未移行 SSR ページ** へのリンクに使う。
 * dev は Vite proxy が同パスを backend へ転送する。React に移行済みの画面は react-router の `<Link>` を使う。
 */
export function legacyUrl(path: string): string {
  return `${API_BASE}${path}`;
}

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

/** backend が返す ProblemDetail を表すエラー。`code` で UI 分岐する。 */
export class ApiError extends Error {
  constructor(
    readonly status: number,
    readonly code: string,
    readonly detail: string,
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
  return new ApiError(response.status, code, detail);
}

type ApiFetchOptions = {
  method?: "GET" | "POST";
  /** JSON シリアライズして body にする。GET では指定しない。 */
  body?: unknown;
};

/**
 * API を叩いて JSON を返す。204 (No Content) は `undefined` を返す。
 * 非 2xx は {@link ApiError} を throw する。
 */
export async function apiFetch<T>(path: string, options: ApiFetchOptions = {}): Promise<T> {
  const { method = "GET", body } = options;
  const response = await fetch(`${API_BASE}${path}`, {
    method,
    credentials: "include",
    headers: body === undefined ? undefined : { "Content-Type": "application/json" },
    body: body === undefined ? undefined : JSON.stringify(body),
  });

  if (!response.ok) {
    throw await toApiError(response);
  }
  if (response.status === 204) {
    return undefined as T;
  }
  return (await response.json()) as T;
}
