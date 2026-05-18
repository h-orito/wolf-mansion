/**
 * API クライアントの薄いラッパ。
 * - browser 側: 相対パス (`/wolf-mansion-api/...`) を叩く (同一オリジン)
 * - SSR loader 側: cluster-internal URL に変換し、Cookie をリクエストヘッダから転送する
 */

const API_BASE_PATH = "/wolf-mansion-api";

export type ApiFetch = (path: string, init?: RequestInit) => Promise<Response>;

/** browser から呼ぶときの fetch (TanStack Query の queryFn 等で使用) */
export const browserFetch: ApiFetch = (path, init) => {
  const hasBody = init?.body != null;
  const headers: Record<string, string> = { ...(init?.headers as Record<string, string> | undefined ?? {}) };
  // Body 付きリクエストで Content-Type が未指定なら JSON をデフォルトに
  if (hasBody && headers["Content-Type"] == null && headers["content-type"] == null) {
    headers["Content-Type"] = "application/json";
  }
  return fetch(`${API_BASE_PATH}${path}`, {
    credentials: "include",
    ...init,
    headers,
  });
};

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
 */
export function ssrFetch(request: Request): ApiFetch {
  const cookie = request.headers.get("cookie") ?? "";
  const base = process.env.API_BASE_URL ?? `http://localhost:8089${API_BASE_PATH}`;
  return (path, init) => {
    const hasBody = init?.body != null;
    const headers: Record<string, string> = { ...(init?.headers as Record<string, string> | undefined ?? {}) };
    if (hasBody && headers["Content-Type"] == null && headers["content-type"] == null) {
      headers["Content-Type"] = "application/json";
    }
    // cookie は呼び出し元から上書き不可 (スプレッドの後で固定)
    headers["cookie"] = cookie;
    return fetch(`${base}${path}`, { ...init, headers });
  };
}
