/** react-router.config.ts の basename と必ず一致させる */
export const BASENAME = "/wolf-mansion";

/**
 * `request.url` から basename を取り除いた in-app パスを返す。
 * loader 内で redirect 先 query を組み立てるときに使う (二重 basename を避ける)。
 */
export function stripBasename(pathname: string): string {
  if (pathname === BASENAME) return "/";
  if (pathname.startsWith(BASENAME + "/")) return pathname.slice(BASENAME.length);
  return pathname;
}
