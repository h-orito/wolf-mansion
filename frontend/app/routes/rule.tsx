import type { Route } from "./+types/rule";
import { PlaceholderPage } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return [{ title: "ルール | WOLF MANSION" }];
}

export default function Rule() {
  return (
    <main className="max-w-screen-lg mx-auto">
      <PlaceholderPage
        title="ルール"
        englishTitle="Rule"
        description="勝利条件 / 投票 / 足音 / 各役職の能力詳細など、本ルールの全般。"
      />
    </main>
  );
}
