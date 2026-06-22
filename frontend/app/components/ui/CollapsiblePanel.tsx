import { type ReactNode, useId, useState } from "react";

/**
 * 見出しバーをクリックして本文を開閉できるパネル。閉じている間は本文を描画しない。
 */
export function CollapsiblePanel({
  title,
  defaultOpen = false,
  children,
}: {
  title: string;
  defaultOpen?: boolean;
  children: ReactNode;
}) {
  const [open, setOpen] = useState(defaultOpen);
  const bodyId = useId();
  return (
    <div className="mb-[15px] rounded border border-[#464545] bg-[#303030]">
      <div className="rounded-t bg-[#464545] px-[15px] py-[10px]">
        <button
          type="button"
          aria-expanded={open}
          aria-controls={bodyId}
          onClick={() => setOpen((v) => !v)}
          className="cursor-pointer text-[15px] text-white hover:underline"
        >
          {title}
        </button>
      </div>
      <div
        id={bodyId}
        className={`grid transition-[grid-template-rows] duration-300 ease-in-out ${open ? "grid-rows-[1fr]" : "grid-rows-[0fr]"}`}
      >
        <div className="overflow-hidden">
          <div className="p-[15px]">{children}</div>
        </div>
      </div>
    </div>
  );
}
