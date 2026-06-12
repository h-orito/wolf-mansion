import type { ReactNode } from "react";

/**
 * タイトルバー付きのパネル (村画面のフォーム群など)。開閉が要る場合は
 * `CollapsiblePanel` を使う。
 */
export function Panel({ title, children }: { title: string; children: ReactNode }) {
  return (
    <div className="mb-[20px] rounded border border-[#464545] bg-[#303030]">
      <div className="rounded-t bg-[#464545] px-[15px] py-[10px]">
        <span className="text-[15px] text-white">{title}</span>
      </div>
      <div className="p-[15px]">{children}</div>
    </div>
  );
}
