import { useCallback } from "react";

const FOOTER_HEIGHT = 45;

/**
 * 指定 ID の要素が画面下端 (固定フッターの上) に来る位置までスクロールする。
 * 要素が positioned ancestor 内にあっても正しく計算できるよう viewport 座標基準で求める。
 */
function scrollToElementBottom(id: string, smooth: boolean) {
  const el = document.getElementById(id);
  if (el == null) return;
  const top = el.getBoundingClientRect().top + window.scrollY - window.innerHeight + FOOTER_HEIGHT;
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
