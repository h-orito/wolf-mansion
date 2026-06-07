import type { ReactNode } from "react";

/**
 * 軽量モーダルダイアログ (Step 4.1)。Bootstrap JS は使わず state 駆動で開閉する
 * (挙動・見た目が同等なら現状踏襲とみなす。04-frontend.md UI/UX 現状維持原則)。
 */
export function Modal({
  open,
  onClose,
  title,
  children,
}: {
  open: boolean;
  onClose: () => void;
  title: string;
  children: ReactNode;
}) {
  if (!open) return null;
  return (
    <div
      className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto bg-black/50 p-4"
      role="dialog"
      aria-modal="true"
      aria-label={title}
      onClick={onClose}
    >
      <div
        // 白ダイアログ + 暗い文字を明示する (Footer の text-white を継承して白地に白文字に
        // なるのを防ぐ)。既存 (bootstrap modal) は白背景 + 暗色文字。
        className="my-8 w-full max-w-lg rounded bg-white p-4 text-gray-900 shadow-lg dark:bg-gray-900 dark:text-gray-100"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="mb-3 flex items-center justify-between border-b border-gray-200 pb-2 dark:border-gray-700">
          <h4 className="text-lg font-bold">{title}</h4>
          <button
            type="button"
            onClick={onClose}
            aria-label="閉じる"
            className="text-2xl leading-none text-gray-500 hover:text-gray-800 dark:hover:text-gray-200"
          >
            ×
          </button>
        </div>
        <div className="text-sm">{children}</div>
      </div>
    </div>
  );
}
