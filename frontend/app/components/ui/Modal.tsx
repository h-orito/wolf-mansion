import * as React from "react";
import { XMarkIcon } from "@heroicons/react/24/outline";
import { cn } from "./cn";

/**
 * 最小限の Modal primitive。旧 Bootstrap 3 の .modal-dialog / .modal-content /
 * .modal-header (× ボタン + h4 title) / .modal-body の構造を再現する。
 *
 * - Esc で閉じる
 * - 背景クリックで閉じる
 * - body スクロールをロック
 * - open 時に最初の focusable へ focus、close 時に trigger に focus 戻し
 *
 * **既知の制約**: Tab / Shift+Tab のフォーカストラップは未実装。`aria-modal=true`
 * はあくまでヒントで、ブラウザ・AT 実装によっては modal 外要素にフォーカスが
 * 移ることがある。現状の用途 (kampa / policy のような閉じる・外部リンク中心の
 * informational modal) では実害は限定的。フォーム入力を持つ Modal に使う場合は
 * focus trap を別途実装すること。
 */
type ModalProps = {
  open: boolean;
  onClose: () => void;
  title: React.ReactNode;
  children: React.ReactNode;
  /** title 要素の id (aria-labelledby 用)。省略時は自動採番 */
  titleId?: string;
  className?: string;
};

export function Modal({
  open,
  onClose,
  title,
  children,
  titleId,
  className,
}: ModalProps) {
  const reactId = React.useId();
  const tid = titleId ?? `modal-title-${reactId}`;
  const dialogRef = React.useRef<HTMLDivElement>(null);
  const previousFocus = React.useRef<HTMLElement | null>(null);

  // open 切替で focus 管理 + body scroll lock
  React.useEffect(() => {
    if (!open) return;
    previousFocus.current = document.activeElement as HTMLElement | null;
    const body = document.body;
    const prevOverflow = body.style.overflow;
    body.style.overflow = "hidden";

    // open 時、dialog 内の最初の focusable に focus
    const first = dialogRef.current?.querySelector<HTMLElement>(
      "button, [href], input, textarea, select, [tabindex]:not([tabindex='-1'])",
    );
    first?.focus();

    return () => {
      body.style.overflow = prevOverflow;
      previousFocus.current?.focus?.();
    };
  }, [open]);

  // Esc 閉じる
  React.useEffect(() => {
    if (!open) return;
    function onKey(e: KeyboardEvent) {
      if (e.key === "Escape") {
        e.preventDefault();
        onClose();
      }
    }
    window.addEventListener("keydown", onKey);
    return () => window.removeEventListener("keydown", onKey);
  }, [open, onClose]);

  if (!open) return null;

  return (
    <div
      className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto p-4 bg-black/60"
      onClick={onClose}
      role="presentation"
    >
      <div
        ref={dialogRef}
        role="dialog"
        aria-modal="true"
        aria-labelledby={tid}
        className={cn(
          "w-full max-w-[40em] my-8 bg-night-950 border border-night-700 rounded-[0.25em] shadow-lg",
          className,
        )}
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex items-center justify-between px-3 py-2 border-b border-night-700">
          <h2 id={tid} className="text-[1.17em] font-medium">
            {title}
          </h2>
          <button
            type="button"
            onClick={onClose}
            aria-label="閉じる"
            className="text-white hover:text-mint-500 transition-colors"
          >
            <XMarkIcon className="w-[1.5em] h-[1.5em]" aria-hidden />
          </button>
        </div>
        <div className="px-3 py-3">{children}</div>
      </div>
    </div>
  );
}
