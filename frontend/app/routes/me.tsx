import { useState } from "react";
import { Link, useNavigate, useRouteLoaderData } from "react-router";
import type { Route } from "./+types/me";
import { useLogoutMutation } from "~/features/auth/hooks";

export function meta(_: Route.MetaArgs) {
  return [{ title: "マイページ - wolf-mansion" }];
}

// _auth.tsx の loader から user データを直接取得 (追加 fetch なし)
type AuthLoaderData = { user: { userId: string; authority: string } };

export default function MePage() {
  const navigate = useNavigate();
  const authData = useRouteLoaderData("routes/_auth") as AuthLoaderData;
  const logoutMutation = useLogoutMutation();
  const [logoutError, setLogoutError] = useState<string | null>(null);

  const user = authData.user;

  async function onLogout() {
    setLogoutError(null);
    try {
      await logoutMutation.mutateAsync();
      navigate("/login", { replace: true });
    } catch {
      setLogoutError(
        "ログアウトに失敗しました。通信状況を確認の上、再度お試しください。"
      );
    }
  }

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100 px-6 py-12">
      <div className="max-w-2xl mx-auto space-y-6">
        <h1 className="text-3xl font-bold">マイページ</h1>
        <div className="rounded-lg bg-slate-800/60 p-6 border border-slate-700 space-y-2">
          <p className="text-sm text-slate-400">プレイヤー名</p>
          <p className="text-xl font-mono">{user.userId}</p>
          <p className="text-sm text-slate-400 mt-4">権限</p>
          <p className="text-base">{user.authority}</p>
          <div className="pt-2">
            <Link
              to={`/players/${encodeURIComponent(user.userId)}`}
              className="text-sm text-indigo-300 hover:text-indigo-200 underline"
            >
              プロフィール / 戦績 / パスワード変更 →
            </Link>
          </div>
        </div>
        {logoutError && (
          <p role="alert" className="text-sm text-red-400">
            {logoutError}
          </p>
        )}
        <button
          type="button"
          onClick={onLogout}
          disabled={logoutMutation.isPending}
          className="rounded-md bg-rose-600 hover:bg-rose-500 disabled:bg-slate-600 px-5 py-2 font-semibold transition"
        >
          {logoutMutation.isPending ? "ログアウト中..." : "ログアウト"}
        </button>
      </div>
    </main>
  );
}
