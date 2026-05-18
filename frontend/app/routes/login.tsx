import { useState, type FormEvent } from "react";
import { redirect, useNavigate, useSearchParams } from "react-router";
import type { Route } from "./+types/login";
import { InvalidCredentialsError } from "~/features/auth/api";
import { useLoginMutation, useMeQuery } from "~/features/auth/hooks";
import { ssrFetch } from "~/lib/api/client";

export function meta(_: Route.MetaArgs) {
  return [{ title: "ログイン - wolf-mansion" }];
}

/** 既にログイン済みなら ?redirect=... or / にリダイレクト */
export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  const res = await api("/api/v1/auth/me");
  if (res.ok) {
    const data = (await res.json()) as { user: { userId: string } | null };
    if (data.user) {
      const to = sanitizeRedirect(new URL(request.url).searchParams.get("redirect"));
      throw redirect(to);
    }
  }
  return null;
}

/**
 * `?redirect=...` を相対 in-app パスに正規化する (open redirect 防止)。
 * `/` で始まらない or `//` で始まる (protocol-relative) は弾く。
 */
function sanitizeRedirect(raw: string | null): string {
  if (!raw) return "/";
  if (!raw.startsWith("/")) return "/";
  if (raw.startsWith("//")) return "/";
  return raw;
}

export default function LoginPage() {
  const navigate = useNavigate();
  const [params] = useSearchParams();
  const me = useMeQuery();
  const loginMutation = useLoginMutation();
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");

  // login 成功後の遷移先 (?redirect=... 指定があればそこへ、ただし in-app パスに正規化)
  const redirectTo = sanitizeRedirect(params.get("redirect"));

  async function onSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    try {
      await loginMutation.mutateAsync({ userId, password });
      await me.refetch();
      navigate(redirectTo, { replace: true });
    } catch {
      // mutation error は下で error として表示する
    }
  }

  const errorMessage = loginMutation.error
    ? loginMutation.error instanceof InvalidCredentialsError
      ? "ID またはパスワードが違います"
      : "ログインに失敗しました。しばらく経ってから再試行してください"
    : null;

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100 flex items-center justify-center px-4">
      <form
        onSubmit={onSubmit}
        className="w-full max-w-sm bg-slate-800/70 rounded-xl shadow-xl p-8 space-y-5 border border-slate-700"
      >
        <h1 className="text-2xl font-bold text-center">wolf-mansion ログイン</h1>

        <div className="space-y-1">
          <label htmlFor="userId" className="block text-sm font-medium text-slate-300">
            プレイヤー名
          </label>
          <input
            id="userId"
            name="userId"
            type="text"
            required
            autoComplete="username"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            className="w-full rounded-md bg-slate-900 border border-slate-600 px-3 py-2 text-slate-100 focus:border-indigo-400 focus:outline-none"
          />
        </div>

        <div className="space-y-1">
          <label htmlFor="password" className="block text-sm font-medium text-slate-300">
            パスワード
          </label>
          <input
            id="password"
            name="password"
            type="password"
            required
            autoComplete="current-password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full rounded-md bg-slate-900 border border-slate-600 px-3 py-2 text-slate-100 focus:border-indigo-400 focus:outline-none"
          />
        </div>

        {errorMessage && (
          <p role="alert" className="text-sm text-red-400">
            {errorMessage}
          </p>
        )}

        <button
          type="submit"
          disabled={loginMutation.isPending}
          className="w-full rounded-md bg-indigo-500 hover:bg-indigo-400 disabled:bg-slate-600 disabled:cursor-not-allowed py-2 font-semibold transition"
        >
          {loginMutation.isPending ? "ログイン中..." : "ログイン"}
        </button>
      </form>
    </main>
  );
}
