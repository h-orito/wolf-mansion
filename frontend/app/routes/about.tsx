import type { Route } from "./+types/about";
import { PlaceholderPage } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return [{ title: "本サイトは | WOLF MANSION" }];
}

export default function About() {
  return (
    <main className="max-w-screen-lg mx-auto">
      <PlaceholderPage
        title="本サイトは"
        englishTitle="About"
        description="WOLF MANSION の運営方針 / 利用上の注意 / 開発者情報など、本サイト全般の説明。"
      />
    </main>
  );
}
