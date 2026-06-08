import type { ReactNode } from "react";

/** ページ見出し。各画面で共通の体裁にするための primitive。 */
export function Heading({ children }: { children: ReactNode }) {
  return <h1 className="my-[10px] text-[19px] font-normal">{children}</h1>;
}

/** セクション見出し (h2)。 */
export function SubHeading({ children }: { children: ReactNode }) {
  return <h2 className="text-[15px] font-bold">{children}</h2>;
}
