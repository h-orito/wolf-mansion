import { Link } from "react-router";
import type { Route } from "./+types/villages.$id";
import {
  fetchMyself,
  fetchVillage,
  fetchVillageFootsteps,
  fetchVillageMessages,
  type MessagesView,
  type MyselfView,
  type VillageFootstepsView,
  type VillageParticipantView,
  type VillageView,
} from "~/features/village/detail/api";
import {
  useMyselfQuery,
  useVillageFootstepsQuery,
  useVillageMessagesQuery,
  useVillageQuery,
} from "~/features/village/detail/hooks";
import { ssrFetch } from "~/lib/api/client";

export function meta({ data }: Route.MetaArgs) {
  const name = data?.village?.name ?? "村詳細";
  return [{ title: `${name} - wolf-mansion` }];
}

export async function loader({ request, params }: Route.LoaderArgs) {
  const villageId = Number(params.id);
  if (!Number.isFinite(villageId)) {
    throw new Response("invalid village id", { status: 400 });
  }
  const api = ssrFetch(request);
  // 4 つは独立 fetch なので並列化。村本体が取れなかった場合は 404 を投げる。
  const [village, messages, footsteps, myself] = await Promise.all([
    fetchVillage(villageId, api).catch(() => null),
    fetchVillageMessages(villageId, undefined, api).catch(() => null),
    fetchVillageFootsteps(villageId, api).catch(() => null),
    fetchMyself(villageId, api).catch(() => null),
  ]);
  if (!village) throw new Response("village not found", { status: 404 });
  return { villageId, village, messages, footsteps, myself };
}

export default function VillageDetail({ loaderData }: Route.ComponentProps) {
  const { villageId, village: initialVillage, messages: initialMessages, footsteps: initialFootsteps, myself: initialMyself } = loaderData;

  const villageQuery = useVillageQuery(villageId, initialVillage);
  const messagesQuery = useVillageMessagesQuery(villageId, undefined, initialMessages ?? undefined);
  const footstepsQuery = useVillageFootstepsQuery(villageId, initialFootsteps ?? undefined);
  const myselfQuery = useMyselfQuery(villageId, initialMyself);

  const village = villageQuery.data ?? initialVillage;
  const messages = messagesQuery.data ?? initialMessages ?? null;
  const footsteps = footstepsQuery.data ?? initialFootsteps ?? null;
  const myself = myselfQuery.data ?? initialMyself ?? null;

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-4xl mx-auto px-6 py-10 space-y-6">
        <VillageHeader village={village} />

        {myself && <MyselfPanel myself={myself} />}

        <ParticipantsPanel participants={village.participants.list} />

        <MessagesPanel messages={messages} latestDay={village.time.latestDay} />

        <FootstepsPanel footsteps={footsteps} />
      </section>
    </main>
  );
}

// ---------- 部品 ----------

function VillageHeader({ village }: { village: VillageView }) {
  return (
    <header className="space-y-2">
      <div className="flex items-center justify-between">
        <div className="flex items-baseline gap-3">
          <span className="font-mono text-slate-400 text-sm">#{village.number}</span>
          <h1 className="text-2xl font-bold">{village.name}</h1>
        </div>
        <Link to="/villages" className="text-sm text-slate-400 hover:text-slate-200">
          ← 村一覧
        </Link>
      </div>
      <div className="flex flex-wrap items-center gap-3 text-sm text-slate-300">
        <StatusBadge name={village.statusName} />
        <span>現在 {village.time.latestDay}日目</span>
        {village.time.nextDayChangeDatetime && (
          <span className="text-slate-400">
            次回更新: {formatDateTime(village.time.nextDayChangeDatetime)}
          </span>
        )}
        {village.winCampName && (
          <span className="text-amber-300">勝利: {village.winCampName}</span>
        )}
      </div>
      <p className="text-xs text-slate-500">
        村建て: {village.createPlayerName} / {village.participants.count}人
        {village.participants.spectatorCount > 0 ? ` (見学${village.participants.spectatorCount})` : ""}
      </p>
    </header>
  );
}

function MyselfPanel({ myself }: { myself: MyselfView }) {
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-2">あなた</h2>
      <p className="text-base">
        {myself.name}
        {myself.skill && <span className="ml-2 text-indigo-300">[{myself.skill.name}]</span>}
        {myself.campCode && <span className="ml-2 text-amber-300">{myself.campCode}</span>}
        {myself.isDead && <span className="ml-2 text-rose-300">死亡</span>}
      </p>
    </section>
  );
}

function ParticipantsPanel({ participants }: { participants: VillageParticipantView[] }) {
  if (participants.length === 0) {
    return (
      <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
        <h2 className="text-sm text-slate-400">参加者</h2>
        <p className="text-slate-400 text-sm py-2">まだ参加者がいません</p>
      </section>
    );
  }
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-3">参加者 ({participants.length})</h2>
      <ul className="grid grid-cols-1 sm:grid-cols-2 gap-2">
        {participants.map((p) => (
          <li key={p.id} className="flex items-center gap-2 text-sm">
            <span className="font-mono text-slate-500 w-10 shrink-0">
              {p.roomNumber != null ? String(p.roomNumber).padStart(2, "0") : "--"}
            </span>
            <span className="flex-1 truncate">{p.name}</span>
            {p.skill && (
              <span className="text-xs text-indigo-300 shrink-0">[{p.skill.shortName}]</span>
            )}
            {p.isSpectator && (
              <span className="text-xs text-slate-400 shrink-0">見学</span>
            )}
            {p.isDead && (
              <span className="text-xs text-rose-300 shrink-0">{p.deadReasonCode ?? "死亡"}</span>
            )}
          </li>
        ))}
      </ul>
    </section>
  );
}

function MessagesPanel({ messages, latestDay }: { messages: MessagesView | null; latestDay: number }) {
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-3">発言 ({latestDay}日目)</h2>
      {!messages || messages.list.length === 0 ? (
        <p className="text-slate-400 text-sm py-2">この日の閲覧可能な発言はありません</p>
      ) : (
        <ul className="space-y-2 max-h-[480px] overflow-y-auto">
          {messages.list.map((m, i) => (
            <li key={`${m.typeCode}-${m.number ?? i}`} className="text-sm border-b border-slate-700/40 pb-2">
              <p className="text-xs text-slate-400">
                {m.fromCharaName ?? "system"} · {m.typeName} · {formatDateTime(m.datetime)}
              </p>
              <p className="whitespace-pre-wrap">{m.text}</p>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

function FootstepsPanel({ footsteps }: { footsteps: VillageFootstepsView | null }) {
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-3">足音</h2>
      {!footsteps || footsteps.list.length === 0 ? (
        <p className="text-slate-400 text-sm py-2">表示できる足音はありません</p>
      ) : (
        <ul className="space-y-1 text-sm">
          {footsteps.list.map((f, i) => (
            <li key={`${f.day}-${f.roomNumbers}-${i}`} className="flex items-center gap-2">
              <span className="text-slate-500 w-12 shrink-0">{f.day}日目</span>
              <span className="font-mono">{f.roomNumbers}</span>
              {f.registerChara && (
                <span className="text-xs text-slate-400">
                  by {f.registerChara.shortName}
                </span>
              )}
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

// ---------- utils ----------

const STATUS_BADGE: Record<string, string> = {
  募集中: "bg-emerald-600/30 text-emerald-100 border-emerald-500/40",
  進行中: "bg-amber-600/30 text-amber-100 border-amber-500/40",
  エピローグ: "bg-sky-600/30 text-sky-100 border-sky-500/40",
  終了: "bg-slate-600/30 text-slate-200 border-slate-500/40",
  廃村: "bg-rose-600/30 text-rose-100 border-rose-500/40",
};

function StatusBadge({ name }: { name: string }) {
  const cls = STATUS_BADGE[name] ?? "bg-slate-700/40 text-slate-200 border-slate-500/40";
  return (
    <span className={`text-xs px-2 py-0.5 rounded border ${cls}`}>{name}</span>
  );
}

function formatDateTime(iso: string): string {
  // backend が LocalDateTime を ISO 文字列で返す前提。Date 経由で MM/dd HH:mm 形式に整形。
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) return iso;
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  const hh = String(d.getHours()).padStart(2, "0");
  const mi = String(d.getMinutes()).padStart(2, "0");
  return `${mm}/${dd} ${hh}:${mi}`;
}
