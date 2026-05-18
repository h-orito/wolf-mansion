import { Link } from "react-router";
import type { SimpleVillageView } from "./api";

const STATUS_BADGE: Record<string, string> = {
  募集中: "bg-emerald-600/30 text-emerald-200 border-emerald-500/40",
  進行中: "bg-amber-600/30 text-amber-200 border-amber-500/40",
  エピローグ: "bg-sky-600/30 text-sky-200 border-sky-500/40",
  終了: "bg-slate-600/30 text-slate-300 border-slate-500/40",
  廃村: "bg-rose-600/30 text-rose-200 border-rose-500/40",
};

function StatusBadge({ code }: { code: string }) {
  const cls = STATUS_BADGE[code] ?? "bg-slate-700/40 text-slate-300 border-slate-500/40";
  return (
    <span className={`text-xs px-2 py-0.5 rounded border ${cls}`}>{code}</span>
  );
}

/** 村のリスト表示 (一覧 / トップ共通) */
export function VillageList({ villages, emptyMessage }: {
  villages: SimpleVillageView[];
  emptyMessage?: string;
}) {
  if (villages.length === 0) {
    return (
      <p className="text-slate-400 text-sm py-8 text-center">
        {emptyMessage ?? "村がありません"}
      </p>
    );
  }
  return (
    <ul className="divide-y divide-slate-700/60">
      {villages.map((v) => (
        <li key={v.id}>
          <Link
            to={`/villages/${v.id}`}
            className="flex items-center gap-3 py-3 px-2 hover:bg-slate-800/40 transition rounded"
          >
            <span className="font-mono text-slate-400 text-sm w-14 shrink-0">
              #{v.number}
            </span>
            <span className="flex-1 min-w-0 truncate">{v.name}</span>
            <span className="text-sm text-slate-400 shrink-0">
              {v.spectatorCount > 0
                ? `${v.participantCount} (${v.spectatorCount})人`
                : `${v.participantCount}人`}
            </span>
            <StatusBadge code={v.statusCode} />
          </Link>
        </li>
      ))}
    </ul>
  );
}
