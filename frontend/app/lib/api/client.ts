/**
 * API クライアントの薄いラッパ。
 * - browser 側: 相対パス (`/wolf-mansion-api/...`) を叩く (同一オリジン)
 * - SSR loader 側: cluster-internal URL に変換し、Cookie をリクエストヘッダから転送する
 */

const API_BASE_PATH = "/wolf-mansion-api";

export type ApiFetch = (path: string, init?: RequestInit) => Promise<Response>;

/** browser から呼ぶときの fetch (TanStack Query の queryFn 等で使用) */
export const browserFetch: ApiFetch = (path, init) => {
  return fetch(`${API_BASE_PATH}${path}`, {
    credentials: "include",
    ...init,
    headers: {
      "Content-Type": "application/json",
      ...(init?.headers ?? {}),
    },
  });
};

/**
 * SSR の loader / action から呼ぶ fetch。
 * `API_BASE_URL` 環境変数で API のルートを切り替え (k8s 内では cluster-internal URL)、
 * 元 Request の Cookie を転送する。
 */
export function ssrFetch(request: Request): ApiFetch {
  const cookie = request.headers.get("cookie") ?? "";
  const base = process.env.API_BASE_URL ?? `http://localhost:8089${API_BASE_PATH}`;
  return (path, init) =>
    fetch(`${base}${path}`, {
      ...init,
      headers: {
        "Content-Type": "application/json",
        cookie,
        ...(init?.headers ?? {}),
      },
    });
}
