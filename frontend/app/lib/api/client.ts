/**
 * API クライアントの薄いラッパ。
 * - browser 側: 相対パス (`/wolf-mansion-api/...`) を叩く (同一オリジン)
 * - SSR loader 側: cluster-internal URL に変換し、Cookie をリクエストヘッダから転送する
 */

const API_BASE_PATH = "/wolf-mansion-api";

export type ApiFetch = (path: string, init?: RequestInit) => Promise<Response>;

/** ヘッダのマージ + Body 付きリクエストにのみ Content-Type を付与する共通処理 */
function mergeHeaders(init: RequestInit | undefined): Record<string, string> {
  const headers: Record<string, string> = { ...(init?.headers as Record<string, string> | undefined ?? {}) };
  if (init?.body != null && headers["Content-Type"] == null && headers["content-type"] == null) {
    headers["Content-Type"] = "application/json";
  }
  return headers;
}

/**
 * browser から呼ぶときの fetch (TanStack Query の queryFn 等で使用)。
 *
 * 401 を受けたら **1回だけ** `/api/v1/auth/refresh` を試行し、成功したら同一リクエストを再試行する。
 * refresh も 401 を返した場合は元の 401 をそのまま返す。
 */
export const browserFetch: ApiFetch = async (path, init) => {
  const first = await rawBrowserFetch(path, init);
  if (first.status !== 401) return first;
  // 401 → refresh を 1 回だけ試行
  // /auth/refresh と /auth/login 自身は除外 (再帰防止)。
  // /auth/me は対象に含めてよい — refresh 後の me 再試行も結果が 401 のままなら
  // この関数は素直に 401 を返し、呼び出し元 (useMeQuery 等) が error として扱う。
  // rawBrowserFetch は無条件で 1 回しか追加 fetch しないので無限ループにはならない。
  if (path.startsWith("/api/v1/auth/refresh") || path.startsWith("/api/v1/auth/login")) {
    return first;
  }
  const refreshed = await rawBrowserFetch("/api/v1/auth/refresh", { method: "POST" });
  if (!refreshed.ok) return first;
  return rawBrowserFetch(path, init);
};

function rawBrowserFetch(path: string, init?: RequestInit): Promise<Response> {
  return fetch(`${API_BASE_PATH}${path}`, {
    credentials: "include",
    ...init,
    headers: mergeHeaders(init),
  });
}

/**
 * SSR の loader / action から呼ぶ fetch。
 *
 * `API_BASE_URL` 環境変数で API のルートを切り替える。例:
 *   - 開発: `http://localhost:8089/wolf-mansion-api`
 *   - k8s SSR Pod 内: `http://wolf-mansion-api-svc:8089/wolf-mansion-api`
 * **環境変数にはパスまで含める** (デフォルト値と同じ形式)。設定値の末尾と
 * 各 `path` 引数の先頭の `/` 重複に注意。
 *
 * 元 Request の Cookie をヘッダ転送するため、Cookie キーは呼び出し元 `init.headers`
 * からは上書きできない (cookie をスプレッド後に置く)。
 *
 * SSR では 401 → refresh の自動リトライは行わない (新トークンを set-cookie する経路が
 * 複雑になるため)。loader 側で 401 を捕捉して redirect("/login") に倒す方針。
 */
export function ssrFetch(request: Request): ApiFetch {
  const cookie = request.headers.get("cookie") ?? "";
  const base = process.env.API_BASE_URL ?? `http://localhost:8089${API_BASE_PATH}`;
  return (path, init) => {
    const headers = mergeHeaders(init);
    // cookie は呼び出し元から上書き不可 (スプレッドの後で固定)
    headers["cookie"] = cookie;
    return fetch(`${base}${path}`, { ...init, headers });
  };
}
