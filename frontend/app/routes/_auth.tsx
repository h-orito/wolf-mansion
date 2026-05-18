import { Outlet, redirect } from "react-router";
import type { Route } from "./+types/_auth";
import { ssrFetch } from "~/lib/api/client";
import { stripBasename } from "~/lib/basename";

/**
 * 認証必須レイアウト。
 * loader で /api/v1/auth/me を呼び、未認証なら /login にリダイレクト。
 */
export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  const res = await api("/api/v1/auth/me");
  if (!res.ok) throw redirectToLogin(request);

  const data = (await res.json()) as { user: { userId: string; authority: string } | null };
  if (!data.user) throw redirectToLogin(request);

  return { user: data.user };
}

function redirectToLogin(request: Request): Response {
  const url = new URL(request.url);
  const inAppPath = stripBasename(url.pathname) + url.search;
  return redirect(`/login?redirect=${encodeURIComponent(inAppPath)}`);
}

export default function AuthLayout() {
  return <Outlet />;
}
