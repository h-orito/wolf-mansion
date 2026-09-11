import type { ReactNode } from "react";

/**
 * ページ見出し。各画面で共通の体裁にするための primitive。
 * セクション見出しを同じ体裁で出す場合は `as="h2"` を指定する。
 */
export function Heading({ children, as: Tag = "h1" }: { children: ReactNode; as?: "h1" | "h2" }) {
  return <Tag className="my-[10px] text-[19px] font-normal">{children}</Tag>;
}

/** セクション見出し (h2)。設定フォーム系の画面は weight="normal" を使う。 */
export function SubHeading({
  children,
  id,
  weight = "bold",
}: {
  children: ReactNode;
  id?: string;
  weight?: "bold" | "normal";
}) {
  return (
    <h2 id={id} className={`text-[15px] ${weight === "bold" ? "font-bold" : "font-normal"}`}>
      {children}
    </h2>
  );
}
