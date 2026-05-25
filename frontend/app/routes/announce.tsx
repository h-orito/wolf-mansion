import type { Route } from "./+types/announce";
import { PlaceholderPage, placeholderMeta } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return placeholderMeta("お知らせ");
}

export default function Announce() {
  return (
    <main className="max-w-[1170px] mx-auto">
      <PlaceholderPage
        title="お知らせ"
        englishTitle="Announce"
        description="リリース告知 / メンテナンス予定 / 仕様変更などの運営からのお知らせ一覧。"
      />
    </main>
  );
}
