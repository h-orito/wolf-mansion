import { type ReactNode, useRef } from "react";

type ModalSize = "default" | "wide";

const sizeClass: Record<ModalSize, string> = {
  default: "max-w-lg",
  wide: "max-w-3xl",
};

export function Modal({
  open,
  onClose,
  title,
  size = "default",
  children,
}: {
  open: boolean;
  onClose: () => void;
  title: string;
  size?: ModalSize;
  children: ReactNode;
}) {
  const mouseDownOnBackdrop = useRef(false);

  if (!open) return null;
  return (
    <div
      className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto bg-black/50 p-4"
      role="dialog"
      aria-modal="true"
      aria-label={title}
      onMouseDown={(e) => {
        mouseDownOnBackdrop.current = e.target === e.currentTarget;
      }}
      onClick={(e) => {
        if (e.target === e.currentTarget && mouseDownOnBackdrop.current) {
          onClose();
        }
      }}
    >
      <div
        // 既存 (:8091 の bootstrap modal・ダークテーマ) に合わせる:
        // ダイアログ #303030 / 白文字 / border 1px rgba(0,0,0,.2) / radius 6px、区切り線 #464545。
        className={`my-8 w-full ${sizeClass[size]} rounded-[6px] border border-black/20 bg-surface text-white shadow-lg`}
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex items-center justify-between border-b border-border p-[15px]">
          <h4 className="text-lg font-bold">{title}</h4>
          <button
            type="button"
            onClick={onClose}
            aria-label="閉じる"
            className="text-2xl leading-none text-white hover:opacity-70"
          >
            ×
          </button>
        </div>
        <div className="p-[15px] text-sm">{children}</div>
      </div>
    </div>
  );
}
