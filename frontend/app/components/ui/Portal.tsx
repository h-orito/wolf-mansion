import type { ReactNode } from "react";
import { createPortal } from "react-dom";

/**
 * 子要素を document.body 直下に描画する。
 * 画面全体に重ねる要素 (モーダルなど) を overflow スクロールする固定パネルの中に置くと、
 * iOS の WebKit ではそのパネルの枠を基準に配置・clip される (WebKit Bug 153852)。
 * DOM 上でパネルの外に出すことでこれを避ける。
 */
export function Portal({ children }: { children: ReactNode }) {
  // SSR にはポータル先の document が無い
  if (typeof document === "undefined") return null;
  return createPortal(children, document.body);
}
