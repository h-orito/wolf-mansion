import type { Route } from "./+types/faq";
import { PlaceholderPage } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return [{ title: "よくある質問 | WOLF MANSION" }];
}

export default function Faq() {
  return (
    <main className="max-w-screen-lg mx-auto">
      <PlaceholderPage
        title="よくある質問"
        englishTitle="FAQ"
        description="参加・進行・能力使用に関する FAQ。"
      />
    </main>
  );
}
