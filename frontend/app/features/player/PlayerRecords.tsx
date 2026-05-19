import { Link } from "react-router";
import type { PlayerDetailView } from "./api";

/**
 * 戦績 + 参加村 / 見学村 履歴の表示。閲覧者によらず常に同じ内容。
 */
export function PlayerRecords(props: { detail: PlayerDetailView }) {
  const { detail } = props;
  return (
    <div className="space-y-6">
      <WholeStats detail={detail} />
      <CampStats detail={detail} />
      <SkillStats detail={detail} />
      <ParticipateVillages
        title="参加した村"
        list={detail.participateVillageList}
        emptyMessage="参加した村はまだありません。"
      />
      <ParticipateVillages
        title="見学した村"
        list={detail.spectateVillageList}
        emptyMessage="見学した村はまだありません。"
      />
    </div>
  );
}

function formatRate(rate: number | undefined): string {
  if (rate == null) return "0%";
  return `${Math.round(rate * 1000) / 10}%`;
}

function WholeStats({ detail }: { detail: PlayerDetailView }) {
  const s = detail.wholeStats;
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-lg font-bold mb-3">総合戦績</h2>
      <dl className="grid grid-cols-3 gap-3 text-center">
        <div>
          <dt className="text-xs text-slate-400">参加</dt>
          <dd className="text-2xl font-mono">{s?.participateNum ?? 0}</dd>
        </div>
        <div>
          <dt className="text-xs text-slate-400">勝利</dt>
          <dd className="text-2xl font-mono">{s?.winNum ?? 0}</dd>
        </div>
        <div>
          <dt className="text-xs text-slate-400">勝率</dt>
          <dd className="text-2xl font-mono">{formatRate(s?.winRate)}</dd>
        </div>
      </dl>
    </section>
  );
}

function CampStats({ detail }: { detail: PlayerDetailView }) {
  const list = detail.campStatsList ?? [];
  const haveData = list.some((c) => (c.stats?.participateNum ?? 0) > 0);
  if (!haveData) return null;
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-lg font-bold mb-3">陣営別戦績</h2>
      <table className="w-full text-sm">
        <thead className="text-xs text-slate-400">
          <tr>
            <th className="text-left py-1">陣営</th>
            <th className="text-right">参加</th>
            <th className="text-right">勝利</th>
            <th className="text-right">勝率</th>
          </tr>
        </thead>
        <tbody>
          {list.map((c) => {
            const n = c.stats?.participateNum ?? 0;
            if (n === 0) return null;
            return (
              <tr key={c.campName} className="border-t border-slate-700">
                <td className="py-1">{c.campName}</td>
                <td className="text-right font-mono">{n}</td>
                <td className="text-right font-mono">{c.stats?.winNum ?? 0}</td>
                <td className="text-right font-mono">{formatRate(c.stats?.winRate)}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </section>
  );
}

function SkillStats({ detail }: { detail: PlayerDetailView }) {
  const list = detail.skillStatsList ?? [];
  if (list.length === 0) return null;
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-lg font-bold mb-3">役職別戦績</h2>
      <table className="w-full text-sm">
        <thead className="text-xs text-slate-400">
          <tr>
            <th className="text-left py-1">役職</th>
            <th className="text-right">参加</th>
            <th className="text-right">勝利</th>
            <th className="text-right">勝率</th>
          </tr>
        </thead>
        <tbody>
          {list.map((s) => (
            <tr key={s.skillName} className="border-t border-slate-700">
              <td className="py-1">{s.skillName}</td>
              <td className="text-right font-mono">{s.stats?.participateNum ?? 0}</td>
              <td className="text-right font-mono">{s.stats?.winNum ?? 0}</td>
              <td className="text-right font-mono">{formatRate(s.stats?.winRate)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}

function ParticipateVillages(props: {
  title: string;
  list: PlayerDetailView["participateVillageList"] | undefined;
  emptyMessage: string;
}) {
  const list = props.list ?? [];
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-lg font-bold mb-3">
        {props.title} ({list.length})
      </h2>
      {list.length === 0 ? (
        <p className="text-sm text-slate-400">{props.emptyMessage}</p>
      ) : (
        <ul className="space-y-2">
          {list.map((pv) => (
            <li key={pv.villageId} className="flex items-center gap-3 text-sm">
              {pv.characterImgUrl && (
                <img
                  src={pv.characterImgUrl}
                  alt={pv.characterName}
                  width={Math.min(pv.characterImgWidth ?? 60, 60)}
                  height={Math.min(pv.characterImgHeight ?? 60, 60)}
                  loading="lazy"
                  className="rounded bg-slate-900"
                />
              )}
              <div className="flex-1 min-w-0">
                <Link
                  to={`/villages/${pv.villageId}`}
                  className="font-medium hover:text-indigo-300 transition truncate block"
                >
                  {pv.villageName}
                </Link>
                <p className="text-xs text-slate-400">
                  {pv.characterName}
                  {pv.skillName && ` / ${pv.skillName}`}
                  {pv.campName && ` / ${pv.campName}`}
                  {pv.liveStatus && ` / ${pv.liveStatus}`}
                  {pv.winStatus && ` / ${pv.winStatus}`}
                </p>
              </div>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}
