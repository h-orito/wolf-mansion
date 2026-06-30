import { type ReactNode, useId, useState } from "react";

/** 見出しバーをクリックして本文を開閉できるパネル。 */
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
      {open && (
        <div id={bodyId} className="p-[15px]">
          {children}
        </div>
      )}
    </div>
  );
}
