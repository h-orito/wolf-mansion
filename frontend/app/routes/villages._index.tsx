import { Link, useSearchParams } from "react-router";
import type { Route } from "./+types/villages._index";
import { fetchVillages, type VillagesView, type VillageStatusCode } from "~/features/village/api";
import { useVillagesQuery } from "~/features/village/hooks";
import { VillageList } from "~/features/village/VillageList";
import { ssrFetch } from "~/lib/api/client";

const ALL_STATUSES: VillageStatusCode[] = ["募集中", "進行中", "エピローグ", "終了", "廃村"];

export function meta(_: Route.MetaArgs) {
  return [{ title: "村一覧 - wolf-mansion" }];
}

function parseStatuses(raw: string | null): VillageStatusCode[] {
  if (!raw) return [];
  return raw
    .split(",")
    .map((s) => s.trim())
    .filter((s): s is VillageStatusCode => ALL_STATUSES.includes(s as VillageStatusCode));
}

export async function loader({ request }: Route.LoaderArgs) {
  const url = new URL(request.url);
  const statuses = parseStatuses(url.searchParams.get("status"));
  const api = ssrFetch(request);
  try {
    const villages = await fetchVillages({ statuses }, api);
    return { villages, statuses };
  } catch {
    return { villages: { list: [] } satisfies VillagesView, statuses };
  }
}

export default function VillagesIndex({ loaderData }: Route.ComponentProps) {
  const [params, setParams] = useSearchParams();
  const selected = parseStatuses(params.get("status"));

  const villagesQuery = useVillagesQuery(
    { statuses: selected.length > 0 ? selected : undefined },
    loaderData.villages,
  );

  function toggleStatus(s: VillageStatusCode) {
    const next = selected.includes(s)
      ? selected.filter((x) => x !== s)
      : [...selected, s];
    const newParams = new URLSearchParams(params);
    if (next.length === 0) newParams.delete("status");
    else newParams.set("status", next.join(","));
    setParams(newParams, { replace: true });
  }

  const list = villagesQuery.data?.list ?? [];

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-3xl mx-auto px-6 py-10 space-y-6">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold">村一覧</h1>
          <Link to="/" className="text-sm text-slate-400 hover:text-slate-200">
            ← トップへ
          </Link>
        </div>

        <div className="flex flex-wrap gap-2">
          {ALL_STATUSES.map((s) => {
            const active = selected.includes(s);
            return (
              <button
                key={s}
                type="button"
                onClick={() => toggleStatus(s)}
                aria-pressed={active}
                className={
                  "rounded-full px-3 py-1 text-xs border transition " +
                  (active
                    ? "bg-indigo-500/30 border-indigo-400 text-indigo-100"
                    : "border-slate-600 text-slate-300 hover:border-slate-400")
                }
              >
                {s}
              </button>
            );
          })}
          {selected.length > 0 && (
            <button
              type="button"
              onClick={() => setParams(new URLSearchParams(), { replace: true })}
              className="text-xs text-slate-400 hover:text-slate-200 ml-2 underline"
            >
              クリア
            </button>
          )}
        </div>

        <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
          <p className="text-xs text-slate-400 mb-2">
            {selected.length === 0 ? "全村" : `${selected.join(" / ")}`} ({list.length}件)
          </p>
          <VillageList villages={list} emptyMessage="該当する村がありません" />
        </section>
      </section>
    </main>
  );
}
