import { type ReactNode, useRef } from "react";

import { Portal } from "~/components/ui/Portal";

type ModalSize = "default" | "wide";

const sizeClass: Record<ModalSize, string> = {
  default: "max-w-lg",
  wide: "max-w-3xl",
};

/**
 * モーダルの土台 (背景オーバーレイ + ダイアログ枠)。枠の中身は呼び出し側が組み立てる。
 * 見出しと × を持つ標準形は Modal を使う。
 */
export function ModalDialog({
  open,
  onClose,
  label,
  size = "default",
  children,
}: {
  open: boolean;
  onClose: () => void;
  /** ダイアログの aria-label */
  label: string;
  size?: ModalSize;
  children: ReactNode;
}) {
  const mouseDownOnBackdrop = useRef(false);

  if (!open) return null;
  return (
    <Portal>
      <div
        className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto bg-black/50 p-4"
        role="dialog"
        aria-modal="true"
        aria-label={label}
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
          className={`my-8 w-full ${sizeClass[size]} rounded-[6px] border border-black/20 bg-surface text-white shadow-lg`}
          onClick={(e) => e.stopPropagation()}
        >
          {children}
        </div>
      </div>
    </Portal>
  );
}

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
  return (
    <ModalDialog open={open} onClose={onClose} label={title} size={size}>
      <div className="flex items-center justify-between border-b border-border p-[15px]">
        <h4 className="text-ui-title font-bold">{title}</h4>
        <button
          type="button"
          onClick={onClose}
          aria-label="閉じる"
          className="text-ui-close leading-none text-white hover:opacity-70"
        >
          ×
        </button>
      </div>
      <div className="p-[15px]">{children}</div>
    </ModalDialog>
  );
}
