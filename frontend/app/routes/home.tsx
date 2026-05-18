import { Link } from "react-router";
import type { Route } from "./+types/home";
import { fetchVillages, type VillagesView } from "~/features/village/api";
import { useVillagesQuery } from "~/features/village/hooks";
import { VillageList } from "~/features/village/VillageList";
import { ssrFetch } from "~/lib/api/client";

const TOP_STATUSES = ["募集中", "進行中", "エピローグ"] as const;

export function meta(_: Route.MetaArgs) {
  return [
    { title: "wolf-mansion" },
    { name: "description", content: "人狼ゲーム wolf-mansion" },
    { property: "og:title", content: "wolf-mansion" },
    { property: "og:image", content: "/wolf-mansion/img/ogp-top.png" },
  ];
}

export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  try {
    const villages = await fetchVillages({ statuses: [...TOP_STATUSES] }, api);
    return { villages };
  } catch {
    // backend 不可用時は空一覧で続行 (404 にせず UI を出す)
    return { villages: { list: [] } satisfies VillagesView };
  }
}

export default function Home({ loaderData }: Route.ComponentProps) {
  const villagesQuery = useVillagesQuery(
    { statuses: [...TOP_STATUSES] },
    loaderData.villages,
  );
  const villages = villagesQuery.data?.list ?? [];

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-3xl mx-auto px-6 py-12 space-y-8">
        <header className="text-center space-y-2">
          <img
            src="/wolf-mansion/img/top.jpg"
            alt="wolf-mansion"
            className="w-full max-w-xl mx-auto rounded-lg shadow-xl"
          />
          <h1 className="mt-6 text-4xl font-bold tracking-wide">wolf-mansion</h1>
          <p className="text-slate-300">人狼ゲーム</p>
        </header>

        <div className="flex gap-3 justify-center">
          <Link
            to="/villages"
            className="rounded-md bg-indigo-500 hover:bg-indigo-400 px-5 py-2 font-semibold transition"
          >
            全村一覧
          </Link>
          <Link
            to="/login"
            className="rounded-md border border-slate-600 hover:border-slate-400 px-5 py-2 font-semibold transition"
          >
            ログイン
          </Link>
        </div>

        <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
          <h2 className="text-lg font-semibold mb-2">開催中の村</h2>
          <p className="text-xs text-slate-400 mb-2">募集中 / 進行中 / エピローグ</p>
          <VillageList
            villages={villages}
            emptyMessage="現在、開催中の村はありません"
          />
        </section>
      </section>
    </main>
  );
}
