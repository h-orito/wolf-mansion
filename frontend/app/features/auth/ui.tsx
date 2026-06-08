import type { ReactNode } from "react";

import { Heading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";

/** 入力欄 (白地・薄枠)。ダーク地で見えるよう入力欄のみ明色にする。 */
export const inputClass =
  "h-[30px] w-full rounded border border-gray-400 bg-white px-[10px] py-[5px] text-[#555555]";
/** ダーク地のリンク (アクセント色)。 */
export const linkClass = "text-wm-accent hover:underline";
/** フィールド単位のエラー文言。 */
export const fieldErrorClass = "mt-1 text-red-400";
/** フォーム全体のエラー文言。 */
export const formErrorClass = "mb-2 block text-red-400";

/** 認証画面の外枠: 共通レイアウトシェル + 見出し。 */
export function AuthLayout({ title, children }: { title: string; children: ReactNode }) {
  return (
    <PageLayout>
      <div className="px-[15px]">
        <Heading>{title}</Heading>
        {children}
      </div>
    </PageLayout>
  );
}
