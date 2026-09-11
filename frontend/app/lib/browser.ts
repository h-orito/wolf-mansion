/**
 * スマホ（iOS / Android）判定。
 * iPadOS 13 以降はデスクトップ UA（Macintosh）を名乗るため判定対象外。
 */
export function isMobile(): boolean {
  if (typeof navigator === "undefined") return false;
  return /android|iphone|ipad|ipod/i.test(navigator.userAgent);
}
