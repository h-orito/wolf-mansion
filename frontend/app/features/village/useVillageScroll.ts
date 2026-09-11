import { useCallback } from "react";

/** FooterMenu のルート要素 ID。スクロール位置計算で実高さを参照するために使う。 */
export const FOOTER_MENU_ID = "footer-menu";

/** FooterMenu が実高さを書き込む CSS 変数名。下部余白の確保に使う。 */
export const FOOTER_MENU_HEIGHT_VAR = "--footer-menu-height";

/** FooterMenu 未マウント時 (SSR 直後など) のフォールバック高さ。 */
const DEFAULT_FOOTER_HEIGHT = 45;

/** スクロール停止位置とフッター上端の間に確保する余白。 */
const SCROLL_MARGIN = 8;

/**
 * FooterMenu の実高さ。safe area や画面幅でフッター高さが変わるため要素から都度取得する。
 */
function getFooterHeight(): number {
  return document.getElementById(FOOTER_MENU_ID)?.offsetHeight ?? DEFAULT_FOOTER_HEIGHT;
}

/**
 * 指定 ID の要素が画面下端 (固定フッターの上) に来る位置までスクロールする。
 * 要素が positioned ancestor 内にあっても正しく計算できるよう viewport 座標基準で求める。
 */
function scrollToElementBottom(id: string, smooth: boolean) {
  const el = document.getElementById(id);
  if (el == null) return;
  const top =
    el.getBoundingClientRect().top +
    window.scrollY -
    window.innerHeight +
    getFooterHeight() +
    SCROLL_MARGIN;
  window.scrollTo({ top: Math.max(0, top), behavior: smooth ? "smooth" : "instant" });
}

export function useVillageScroll() {
  const scrollToTop = useCallback(() => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  }, []);

  /** ページ末尾 (`#bottom` = DayList・広告の下) を下端に合わせる。フッターの▼ボタン用。 */
  const scrollToBottom = useCallback((smooth = true) => {
    scrollToElementBottom("bottom", smooth);
  }, []);

  /**
   * メッセージ末尾 (`#message-bottom` = 発言プレビューの直下) を下端に合わせる。
   * 発言確認・確定時に確認したいメッセージが見やすい位置で止まるようにする。
   */
  const scrollToMessageBottom = useCallback((smooth = true) => {
    scrollToElementBottom("message-bottom", smooth);
  }, []);

  return { scrollToTop, scrollToBottom, scrollToMessageBottom };
}
