/** react-router.config.ts の basename と必ず一致させる */
export const BASENAME = "/wolf-mansion";

/**
 * `request.url` から basename を取り除いた in-app パスを返す。
 * loader 内で redirect 先 query を組み立てるときに使う (二重 basename を避ける)。
 *
 * basename で始まらない pathname (設定ミスや予期しないプロキシ動作) は
 * 安全側に倒して `/` を返す。サイレントに通すと open redirect の温床になる。
 */
export function stripBasename(pathname: string): string {
  if (pathname === BASENAME) return "/";
  if (pathname.startsWith(BASENAME + "/")) return pathname.slice(BASENAME.length);
  if (typeof console !== "undefined") {
    console.warn(`stripBasename: pathname "${pathname}" は basename "${BASENAME}" で始まらない。"/" にフォールバック。`);
  }
  return "/";
}
