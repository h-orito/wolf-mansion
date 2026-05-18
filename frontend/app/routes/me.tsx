import { useNavigate } from "react-router";
import type { Route } from "./+types/me";
import { useLogoutMutation, useMeQuery } from "~/features/auth/hooks";

export function meta(_: Route.MetaArgs) {
  return [{ title: "マイページ - wolf-mansion" }];
}

export default function MePage() {
  const navigate = useNavigate();
  const me = useMeQuery();
  const logoutMutation = useLogoutMutation();

  async function onLogout() {
    await logoutMutation.mutateAsync();
    navigate("/login", { replace: true });
  }

  const user = me.data?.user;

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100 px-6 py-12">
      <div className="max-w-2xl mx-auto space-y-6">
        <h1 className="text-3xl font-bold">マイページ</h1>
        <div className="rounded-lg bg-slate-800/60 p-6 border border-slate-700 space-y-2">
          <p className="text-sm text-slate-400">プレイヤー名</p>
          <p className="text-xl font-mono">{user?.userId ?? "..."}</p>
          <p className="text-sm text-slate-400 mt-4">権限</p>
          <p className="text-base">{user?.authority ?? "..."}</p>
        </div>
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
