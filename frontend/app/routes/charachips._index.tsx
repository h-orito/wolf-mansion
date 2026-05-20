import { Link } from "react-router";
import type { Route } from "./+types/charachips._index";
import { fetchCharachips, type CharachipView } from "~/features/meta/api";
import { useCharachipsQuery } from "~/features/meta/hooks";
import { ssrFetch } from "~/lib/api/client";

export function meta(_: Route.MetaArgs) {
  return [{ title: "キャラチップ一覧 - wolf-mansion" }];
}

export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  // SSR で取れなければ空配列でフォールバック (DB ダウン等の防御)
  const charachips = await fetchCharachips(api).catch(() => [] as CharachipView[]);
  return { charachips };
}

export default function CharachipsIndex({ loaderData }: Route.ComponentProps) {
  const query = useCharachipsQuery(loaderData.charachips);
  const list = query.data ?? loaderData.charachips;

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-4xl mx-auto px-6 py-10 space-y-6">
        <header className="flex items-center justify-between">
          <h1 className="text-2xl font-bold">キャラチップ一覧</h1>
          <Link to="/" className="text-sm text-slate-400 hover:text-slate-200">
            ← トップへ
          </Link>
        </header>

        {list.length === 0 ? (
          <p className="text-sm text-slate-400">キャラチップが見つかりません。</p>
        ) : (
          <ul className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            {list.map((c) => (
              <li key={c.id}>
                <Link
                  to={`/charachips/${c.id}`}
                  className="flex items-center gap-3 rounded-xl bg-slate-800/40 border border-slate-700 p-3 hover:border-indigo-400 transition"
                >
                  <img
                    src={c.dummyImageUrl}
                    width={c.dummyImageWidth}
                    height={c.dummyImageHeight}
                    alt={c.name}
                    className="bg-slate-900/60 rounded"
                  />
                  <div className="flex-1 min-w-0">
                    <p className="text-base font-medium truncate">{c.name}</p>
                    <p className="text-xs text-slate-400 truncate">{c.designerName}</p>
                    <p className="text-xs text-slate-500">{c.charaCount} キャラ</p>
                  </div>
                </Link>
              </li>
            ))}
          </ul>
        )}
      </section>
    </main>
  );
}
