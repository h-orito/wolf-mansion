import type { ReactNode } from "react";

/**
 * 認証画面の最小共通 UI (Step 3.3)。
 * デザインは詰めない方針 (Step 12 で復元 → Step 13 でモダナイズ)。機能優先の素朴なマークアップ。
 */

export const inputClass =
  "w-full rounded border border-gray-300 px-3 py-2 dark:border-gray-700 dark:bg-gray-900";
export const buttonClass =
  "w-full rounded bg-blue-600 px-4 py-2 font-medium text-white hover:bg-blue-700 disabled:opacity-50";
export const fieldErrorClass = "mt-1 text-sm text-red-600";
export const formErrorClass = "rounded bg-red-50 px-3 py-2 text-sm text-red-700 dark:bg-red-950";

export function AuthCard({ title, children }: { title: string; children: ReactNode }) {
  return (
    <main className="mx-auto max-w-sm p-6">
      <h1 className="mb-6 text-xl font-bold">{title}</h1>
      {children}
    </main>
  );
}
