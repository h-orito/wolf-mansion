import { Outlet, redirect } from "react-router";
import type { Route } from "./+types/_auth";
import type { MeResponse } from "~/features/auth/api";
import { ssrFetch } from "~/lib/api/client";
import { stripBasename } from "~/lib/basename";
import { PageHeader } from "~/components/layout/PageHeader";
import { PageFooter } from "~/components/layout/PageFooter";

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
  // 子 route (me / new-village) が自前で <main> を持っているため、ここは <div> で
  // 包んで PageHeader だけ被せる。子側を <main> でない構造に揃えるリファクタは
  // Step 13d / 13e (それぞれの route を design-restore する時) で実施
  return (
    <div className="max-w-screen-lg mx-auto">
      <PageHeader />
      <div className="px-3">
        <Outlet />
      </div>
      <PageFooter />
    </div>
  );
}
