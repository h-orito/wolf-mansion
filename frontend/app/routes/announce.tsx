import type { Route } from "./+types/announce";
import { PlaceholderPage } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return [{ title: "お知らせ | WOLF MANSION" }];
}

export default function Announce() {
  return (
    <main className="max-w-screen-lg mx-auto">
      <PlaceholderPage
        title="お知らせ"
        englishTitle="Announce"
        description="リリース告知 / メンテナンス予定 / 仕様変更などの運営からのお知らせ一覧。"
      />
    </main>
  );
}
