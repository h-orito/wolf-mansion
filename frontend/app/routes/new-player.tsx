import type { Route } from "./+types/new-player";
import { PlaceholderPage, placeholderMeta } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return placeholderMeta("ID 登録");
}

export default function NewPlayer() {
  return (
    <main className="max-w-[1170px] mx-auto">
      <PlaceholderPage
        title="ID 登録"
        englishTitle="Register"
        description="新規プレイヤー登録フォーム。プレイヤー名 / パスワード / メールアドレスの入力。"
      />
    </main>
  );
}
