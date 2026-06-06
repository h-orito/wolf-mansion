/**
 * `returnTo` クエリの正規化 (Step 3.3)。
 * オープンリダイレクト防止のため **自サイト内の絶対パスのみ** 許可し、`/` にフォールバックする。
 *
 * NOTE: 入力は `useSearchParams().get()` 由来で **既に 1 回デコード済み**。ここで再 decode すると
 * 二重デコードになり (`%20`→space 等で returnTo が壊れる / 単独 `%` で URIError)、そのまま使う。
 * `//host` や `/\host` のような (ブラウザがプロトコル相対 URL に解釈しうる) 値は拒否する。
 */
export function safeReturnTo(raw: string | null): string {
  if (!raw) return "/";
  return raw.startsWith("/") && !/^\/[\\/]/.test(raw) ? raw : "/";
}
