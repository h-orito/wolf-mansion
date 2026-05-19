import { Link, useNavigate, useSearchParams } from "react-router";
import type { Route } from "./+types/players._index";
import { fetchPlayers, type PlayersView } from "~/features/player/api";
import { usePlayersQuery } from "~/features/player/hooks";
import { ssrFetch } from "~/lib/api/client";

export function meta(_: Route.MetaArgs) {
  return [{ title: "プレイヤー一覧 - wolf-mansion" }];
}

function parsePageNum(raw: string | null): number {
  if (!raw) return 1;
  const n = Number.parseInt(raw, 10);
  return Number.isFinite(n) && n > 0 ? n : 1;
}

export async function loader({ request }: Route.LoaderArgs) {
  const url = new URL(request.url);
  const pageNum = parsePageNum(url.searchParams.get("page"));
  const api = ssrFetch(request);
  try {
    const players = await fetchPlayers(pageNum, api);
    return { players, pageNum };
  } catch {
    // SSR fallback (DB ダウン等): 空のページを返してフロントから再取得
    const empty: PlayersView = {
      list: [],
      allPageCount: 0,
      isExistPrePage: false,
      isExistNextPage: false,
      currentPageNum: pageNum,
      pageNumList: [],
    };
    return { players: empty, pageNum };
  }
}

export default function PlayersIndex({ loaderData }: Route.ComponentProps) {
  const [params, setParams] = useSearchParams();
  const navigate = useNavigate();
  const pageNum = parsePageNum(params.get("page"));
  const playersQuery = usePlayersQuery(pageNum, loaderData.players);

  const view = playersQuery.data ?? loaderData.players;

  function goTo(p: number) {
    const next = new URLSearchParams(params);
    if (p <= 1) next.delete("page");
    else next.set("page", String(p));
    setParams(next, { replace: true });
  }

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-3xl mx-auto px-6 py-10 space-y-6">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold">プレイヤー一覧</h1>
          <Link to="/" className="text-sm text-slate-400 hover:text-slate-200">
            ← トップへ
          </Link>
        </div>

        {view.list.length === 0 ? (
          <p className="text-sm text-slate-400">プレイヤーが見つかりません。</p>
        ) : (
          <ul className="divide-y divide-slate-700 rounded-xl bg-slate-800/40 border border-slate-700">
            {view.list.map((p) => (
              <li key={p.name} className="px-4 py-3 flex items-center justify-between">
                <button
                  type="button"
                  onClick={() => navigate(`/players/${encodeURIComponent(p.name)}`)}
                  className="text-left text-base hover:text-indigo-300 transition"
                >
                  {p.name}
                </button>
              </li>
            ))}
          </ul>
        )}

        {view.allPageCount > 1 && (
          <nav className="flex items-center justify-center gap-1 text-sm">
            <button
              type="button"
              onClick={() => goTo(pageNum - 1)}
              disabled={!view.isExistPrePage}
              className="rounded-md border border-slate-600 px-2 py-1 hover:border-slate-400 disabled:opacity-40 disabled:cursor-not-allowed"
            >
              前へ
            </button>
            {view.pageNumList.map((p) => (
              <button
                key={p}
                type="button"
                onClick={() => goTo(p)}
                aria-current={p === view.currentPageNum ? "page" : undefined}
                className={
                  "rounded-md px-3 py-1 border transition " +
                  (p === view.currentPageNum
                    ? "bg-indigo-500/30 border-indigo-400 text-indigo-100"
                    : "border-slate-600 text-slate-300 hover:border-slate-400")
                }
              >
                {p}
              </button>
            ))}
            <button
              type="button"
              onClick={() => goTo(pageNum + 1)}
              disabled={!view.isExistNextPage}
              className="rounded-md border border-slate-600 px-2 py-1 hover:border-slate-400 disabled:opacity-40 disabled:cursor-not-allowed"
            >
              次へ
            </button>
          </nav>
        )}
      </section>
    </main>
  );
}
