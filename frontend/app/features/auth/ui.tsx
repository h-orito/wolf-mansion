import type { ReactNode } from "react";

import { Heading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";

export { inputClass } from "~/components/ui/Input";
export { fieldErrorClass, formErrorClass } from "~/components/ui/Form";

/** ダーク地のリンク (アクセント色)。 */
export const linkClass = "text-wm-accent hover:underline";

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
