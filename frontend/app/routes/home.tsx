import { Link } from "react-router";

import { useMe } from "~/features/auth/useMe";
import type { Route } from "./+types/home";

export function meta(_: Route.MetaArgs) {
  return [{ title: "人狼の館" }, { name: "description", content: "人狼ゲーム「人狼の館」" }];
}

export default function Home() {
  const { me, isLoading } = useMe();

  return (
    <main className="mx-auto max-w-md p-6">
      <h1 className="mb-6 text-2xl font-bold">人狼の館</h1>
      {isLoading ? (
        <p className="text-gray-500">読み込み中...</p>
      ) : me ? (
        <div className="space-y-2">
          <p>
            ようこそ、<span className="font-medium">{me.name}</span> さん
          </p>
          <Link to="/mypage" className="text-blue-600 hover:underline">
            マイページ
          </Link>
        </div>
      ) : (
        <div className="space-x-4">
          <Link to="/login" className="text-blue-600 hover:underline">
            ログイン
          </Link>
          <Link to="/signup" className="text-blue-600 hover:underline">
            新規登録
          </Link>
        </div>
      )}
    </main>
  );
}
