/**
 * `returnTo` クエリの正規化 (Step 3.3)。
 * オープンリダイレクト防止のため **自サイト内の絶対パスのみ** 許可し、
 * `//evil.com` のようなプロトコル相対 URL は拒否して `/` にフォールバックする。
 */
export function safeReturnTo(raw: string | null): string {
  if (!raw) return "/";
  const decoded = decodeURIComponent(raw);
  return decoded.startsWith("/") && !decoded.startsWith("//") ? decoded : "/";
}
