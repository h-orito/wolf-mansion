import { Link } from "react-router";
import type { Route } from "./+types/new-village";
import { fetchNewVillageDefaults } from "~/features/village/new/api";
import { useNewVillageDefaultsQuery } from "~/features/village/new/hooks";
import { NewVillageForm } from "~/features/village/new/NewVillageForm";
import { ssrFetch } from "~/lib/api/client";

export function meta(_: Route.MetaArgs) {
  return [{ title: "村を建てる - wolf-mansion" }];
}

/**
 * 新規村作成画面 (creator 専用、Step 8h)。
 *
 * SSR loader で `/api/v1/new-village/form-defaults` を呼ぶ。未認証 / 村建て不可なら
 * loader が null を返し、UI 側で「ログインしてください」「現在は村建てできません」を表示。
 *
 * オリジナルキャラチップ村は backend で 501 になるので、UI でも `shouldOriginalImage` は
 * 固定 false (= 切り替えセクションを出していない)。
 */
export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  const defaults = await fetchNewVillageDefaults(api).catch(() => null);
  return { defaults };
}

export default function NewVillagePage({ loaderData }: Route.ComponentProps) {
  const initialDefaults = loaderData.defaults;
  // SSR で取れなかった (= 未認証 or 一時エラー) ときも、クライアントで再 fetch を試みる。
  const query = useNewVillageDefaultsQuery(true, initialDefaults ?? undefined);
  const data = query.data ?? initialDefaults;

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-3xl mx-auto px-6 py-8 space-y-4">
        <header className="flex items-center justify-between">
          <h1 className="text-xl font-bold">村を建てる</h1>
          <Link to="/" className="text-sm text-slate-400 hover:text-slate-200">
            ← トップへ
          </Link>
        </header>

        {!data ? (
          <p className="text-sm text-rose-300">
            ログインが必要です。
            <Link to="/login" className="ml-2 underline">
              ログイン
            </Link>
          </p>
        ) : !data.canCreate ? (
          <p className="text-sm text-amber-300">
            現在は村を建てられません。直前に建てた村の決着がついていないか、参加実績が条件を
            満たしていない可能性があります。
          </p>
        ) : (
          <NewVillageForm initial={data} />
        )}
      </section>
    </main>
  );
}
