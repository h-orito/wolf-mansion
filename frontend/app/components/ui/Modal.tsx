import * as React from "react";
import { createPortal } from "react-dom";
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

  // 重要: 親要素に opacity-* が付いていると CSS opacity が子孫に継承され
  // 背景が透けて見えてしまう (例: PageFooter の opacity-80)。Portal で
  // document.body 直下に描画して、呼び出し位置の opacity 文脈から逃がす。
  // SSR では document が存在しないので open && typeof window で guard。
  if (typeof document === "undefined") return null;

  return createPortal(
    <div
      // 旧 .modal-backdrop: 黒 opacity 0.7 (本番計測値)
      style={{ backgroundColor: "rgba(0, 0, 0, 0.7)" }}
      className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto"
      onClick={onClose}
      role="presentation"
    >
      <div
        ref={dialogRef}
        role="dialog"
        aria-modal="true"
        aria-labelledby={tid}
        // 旧 BS3 darkly .modal-content: bg #303030, 文字白
        // bg は app.css の .modal-dialog-bg (!important) + inline style の二重がけで
        // Tailwind arbitrary 値生成漏れ / 上書きを完全に排除する。
        style={{ backgroundColor: "#303030" }}
        className={cn(
          "modal-dialog-bg w-[80vw] my-[30px] text-white " +
            "border border-[rgba(0,0,0,0.2)] rounded-[6px] " +
            "shadow-[0_5px_15px_rgba(0,0,0,0.5)]",
          className,
        )}
        onClick={(e) => e.stopPropagation()}
      >
        {/* 旧 .modal-header: padding 15px + border-bottom 1px #464545 */}
        <div className="px-[15px] py-[15px] border-b border-night-550">
          {/* close × は float-right。テキスト × (font-size 22.5px / weight 700 / opacity 0.4) */}
          <button
            type="button"
            onClick={onClose}
            aria-label="閉じる"
            className="float-right text-white opacity-40 hover:opacity-80 transition-opacity text-[22.5px] leading-[1] font-bold cursor-pointer"
          >
            ×
          </button>
          {/* 旧 .modal-title (h4): font-size 19px / weight 400 / margin 0 / line-height 1.428 */}
          <h2
            id={tid}
            className="text-[19px] font-normal m-0 leading-[1.428]"
          >
            {title}
          </h2>
        </div>
        {/* 旧 .modal-body: padding 20px (本番計測) */}
        <div className="px-[20px] py-[20px]">{children}</div>
      </div>
    </div>,
    document.body,
  );
}
