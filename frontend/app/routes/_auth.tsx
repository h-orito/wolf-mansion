import { Outlet, redirect } from "react-router";
import type { Route } from "./+types/_auth";
import type { MeResponse } from "~/features/auth/api";
import { ssrFetch } from "~/lib/api/client";
import { stripBasename } from "~/lib/basename";
import { PageHeader } from "~/components/layout/PageHeader";

/**
 * 認証必須レイアウト。
 * loader で /api/v1/auth/me を呼び、未認証なら /login にリダイレクト。
 * 全配下画面に PageHeader (旧 layout/header.html 相当) を被せる。
 */
export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  const res = await api("/api/v1/auth/me");
  if (!res.ok) throw redirectToLogin(request);

  const data = (await res.json()) as MeResponse;
  if (!data.user) throw redirectToLogin(request);

  return { user: data.user };
}

function redirectToLogin(request: Request): Response {
  const url = new URL(request.url);
  const inAppPath = stripBasename(url.pathname) + url.search;
  return redirect(`/login?redirect=${encodeURIComponent(inAppPath)}`);
}

export default function AuthLayout() {
  return (
    <div className="max-w-screen-lg mx-auto">
      <PageHeader />
      <div className="px-3">
        <Outlet />
      </div>
    </div>
  );
}
