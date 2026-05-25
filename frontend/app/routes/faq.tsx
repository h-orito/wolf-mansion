import type { Route } from "./+types/faq";
import { PlaceholderPage, placeholderMeta } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return placeholderMeta("FAQ");
}

export default function Faq() {
  return (
    <main className="max-w-[1170px] mx-auto">
      <PlaceholderPage
        title="よくある質問"
        englishTitle="FAQ"
        description="参加・進行・能力使用に関する FAQ。"
      />
    </main>
  );
}
