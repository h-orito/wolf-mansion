import { Link } from "react-router";
import type { Route } from "./+types/village-records";
import { fetchVillageRecords, type VillageRecordSummary } from "~/features/meta/api";
import { useVillageRecordsQuery } from "~/features/meta/hooks";
import { ssrFetch } from "~/lib/api/client";

export function meta(_: Route.MetaArgs) {
  return [{ title: "終了村一覧 - wolf-mansion" }];
}

export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  const records = await fetchVillageRecords({}, api).catch(() => ({ list: [] }));
  return { records };
}

export default function VillageRecords({ loaderData }: Route.ComponentProps) {
  const query = useVillageRecordsQuery(loaderData.records);
  const view = query.data ?? loaderData.records;

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-4xl mx-auto px-6 py-10 space-y-6">
        <header className="flex items-center justify-between">
          <h1 className="text-2xl font-bold">終了村一覧</h1>
          <Link to="/" className="text-sm text-slate-400 hover:text-slate-200">
            ← トップへ
          </Link>
        </header>

        {view.list.length === 0 ? (
          <p className="text-sm text-slate-400">終了した村がまだありません。</p>
        ) : (
          <ul className="space-y-3">
            {view.list.map((v) => (
              <RecordRow key={v.id} village={v} />
            ))}
          </ul>
        )}
      </section>
    </main>
  );
}

function RecordRow({ village }: { village: VillageRecordSummary }) {
  return (
    <li className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-2">
      <div className="flex items-start justify-between gap-3">
        <div className="flex items-baseline gap-2 min-w-0">
          <span className="font-mono text-slate-500 text-sm shrink-0">
            #{String(village.id).padStart(4, "0")}
          </span>
          <Link to={`/villages/${village.id}`} className="text-base font-medium hover:text-indigo-300 truncate">
            {village.name}
          </Link>
        </div>
        <span className="text-xs text-slate-400 shrink-0">{village.status}</span>
      </div>
      <div className="flex flex-wrap items-center gap-3 text-xs text-slate-400">
        <span className="font-mono">{village.organization}</span>
        {village.winCampName && <span className="text-amber-300">勝利: {village.winCampName}</span>}
        {village.startDatetime && (
          <span>開始: {formatDateTime(village.startDatetime)}</span>
        )}
        {village.epilogueDatetime && (
          <span>EP: {formatDateTime(village.epilogueDatetime)} ({village.epilogueDay}日目)</span>
        )}
      </div>
      <details className="text-xs">
        <summary className="cursor-pointer text-slate-300">参加者 ({village.participants.length}名)</summary>
        <ul className="mt-2 grid grid-cols-1 sm:grid-cols-2 gap-1">
          {village.participants.map((p, i) => (
            <li key={`${village.id}-${i}-${p.userName}-${p.characterName}`} className="flex items-center gap-2">
              <span className="text-slate-500 w-24 truncate">{p.userName}</span>
              <span className="truncate">{p.characterName}</span>
              {p.skillName && <span className="text-indigo-300 text-[10px]">[{p.skillName}]</span>}
              {p.isSpectator && <span className="text-slate-400 text-[10px]">見学</span>}
              {p.isDead && (
                <span className="text-rose-300 text-[10px]">
                  {p.deadDay}d {p.deadReason ?? ""}
                </span>
              )}
              {p.isWin === true && <span className="text-amber-300 text-[10px]">勝</span>}
            </li>
          ))}
        </ul>
      </details>
    </li>
  );
}

function formatDateTime(iso: string): string {
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) return iso;
  const yy = String(d.getFullYear()).slice(-2);
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  const hh = String(d.getHours()).padStart(2, "0");
  const mi = String(d.getMinutes()).padStart(2, "0");
  return `${yy}/${mm}/${dd} ${hh}:${mi}`;
}
