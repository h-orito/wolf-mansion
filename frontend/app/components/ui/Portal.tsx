import { createContext, type ReactNode, useContext } from "react";
import { createPortal } from "react-dom";

const PortalContainerContext = createContext<HTMLElement | null>(null);

/**
 * 子孫の Portal の描画先を指定する。
 * 描画先の要素に当てたスタイル (村画面の「文字を大きく表示する」の font-size など) を
 * portal の中身にも継承させたいときに使う。
 */
export function PortalContainerProvider({
  container,
  children,
}: {
  container: HTMLElement | null;
  children: ReactNode;
}) {
  return (
    <PortalContainerContext.Provider value={container}>{children}</PortalContainerContext.Provider>
  );
}

/**
 * 子要素を PortalContainerProvider の描画先 (無ければ document.body) 直下に描画する。
 * 画面全体に重ねる要素 (モーダルなど) を overflow スクロールする固定パネルの中に置くと、
 * iOS の WebKit ではそのパネルの枠を基準に配置・clip される (WebKit Bug 153852)。
 * DOM 上でパネルの外に出すことでこれを避ける。
 */
export function Portal({ children }: { children: ReactNode }) {
  const container = useContext(PortalContainerContext);
  // SSR にはポータル先の document が無い
  if (typeof document === "undefined") return null;
  return createPortal(children, container ?? document.body);
}
