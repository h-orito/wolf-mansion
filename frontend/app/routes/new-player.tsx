import type { Route } from "./+types/new-player";
import { PlaceholderPage } from "~/components/layout/PlaceholderPage";

export function meta(_: Route.MetaArgs) {
  return [{ title: "ID 登録 | WOLF MANSION" }];
}

export default function NewPlayer() {
  return (
    <main className="max-w-screen-lg mx-auto">
      <PlaceholderPage
        title="ID 登録"
        englishTitle="Register"
        description="新規プレイヤー登録フォーム。プレイヤー名 / パスワード / メールアドレスの入力。"
      />
    </main>
  );
}
