import { useCallback, useMemo, useState } from "react";
import { Link, useSearchParams } from "react-router";
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
  useSayMutation,
  useVillageFootstepsQuery,
  useVillageMessagesQuery,
  useVillageQuery,
} from "~/features/village/detail/hooks";
import { ActionPanel } from "~/features/village/detail/ActionPanel";
import { AdminPanel } from "~/features/village/detail/AdminPanel";
import { CreatorPanel } from "~/features/village/detail/CreatorPanel";
import { MessageCard } from "~/features/village/detail/MessageCard";
import { ParticipateActions } from "~/features/village/detail/ParticipateActions";
import { RoomGridPanel } from "~/features/village/detail/RoomGridPanel";
import { RpActions } from "~/features/village/detail/RpActions";
import { useMeQuery } from "~/features/auth/hooks";
import { ssrFetch } from "~/lib/api/client";

export function meta({ data }: Route.MetaArgs) {
  const name = data?.village?.name ?? "村詳細";
  return [{ title: `${name} - wolf-mansion` }];
}

/**
 * `?day=` を整数に変換。負数 / NaN / 範囲外チェックは下流の backend に任せる
 * (backend は範囲外の day でも空の MessagesView を返す)。0 はプロローグなので valid。
 *
 * loader (SSR) とコンポーネント (CSR) 両方から呼ぶため module top に置く。
 */
function parseDayParam(raw: string | null): number | undefined {
  if (raw == null || raw === "") return undefined;
  const n = Number(raw);
  if (!Number.isInteger(n) || n < 0) return undefined;
  return n;
}

export async function loader({ request, params }: Route.LoaderArgs) {
  const villageId = Number(params.id);
  if (!Number.isFinite(villageId)) {
    throw new Response("invalid village id", { status: 400 });
  }
  const url = new URL(request.url);
  const dayParam = parseDayParam(url.searchParams.get("day"));
  const api = ssrFetch(request);
  // 村の存在確認を先行させる。村が無い場合に messages / footsteps / myself へ
  // 無駄な API コール (backend で同じく 404 になる) が走るのを避けるため。
  const village = await fetchVillage(villageId, api).catch(() => null);
  if (!village) throw new Response("village not found", { status: 404 });
  // 初期表示日: ?day= があればそれ、無ければ最新日 (= backend で `day` 未指定時と同じ)。
  // 「latest」をクエリキーに乗せる都合で、ここでは明示的に number に正規化して渡す。
  const initialDay = dayParam ?? village.time.latestDay;
  const [messages, footsteps, myself] = await Promise.all([
    fetchVillageMessages(villageId, initialDay, api).catch(() => null),
    fetchVillageFootsteps(villageId, api).catch(() => null),
    fetchMyself(villageId, api).catch(() => null),
  ]);
  return { villageId, village, initialDay, messages, footsteps, myself };
}

export default function VillageDetail({ loaderData }: Route.ComponentProps) {
  const {
    villageId,
    village: initialVillage,
    initialDay,
    messages: initialMessages,
    footsteps: initialFootsteps,
    myself: initialMyself,
  } = loaderData;

  const [params, setParams] = useSearchParams();
  // ?day を最優先で使い、無ければ loader で求めた initialDay (= latest) にフォールバック。
  const selectedDay = parseDayParam(params.get("day")) ?? initialDay;

  const villageQuery = useVillageQuery(villageId, initialVillage);
  // 表示日が初期と一致するときだけ SSR の initialMessages を渡す。タブ切替後は
  // initial を使い回すと別日のデータを掴むため必ず再 fetch させる。
  const messagesInitialData =
    selectedDay === initialDay ? initialMessages ?? undefined : undefined;
  const messagesQuery = useVillageMessagesQuery(
    villageId,
    selectedDay,
    messagesInitialData,
  );
  const footstepsQuery = useVillageFootstepsQuery(villageId, initialFootsteps ?? undefined);
  const myselfQuery = useMyselfQuery(villageId, initialMyself);
  // 認証情報を取得して管理者 (CDef.Authority.管理者) 判定に使う。
  // SSR / 未ログイン時は失敗するが、`error` を握りつぶし `isAdmin=false` 扱いで進める。
  const meQuery = useMeQuery();
  const authority = meQuery.data?.user?.authority;
  const isAdmin = authority === "管理者";

  const village = villageQuery.data ?? initialVillage;
  // useVillageMessagesQuery に initialData を渡しているので messagesQuery.data は
  // 同値で同期される。`?? messagesInitialData` を再度書く必要はない。
  const messages = messagesQuery.data ?? null;
  const footsteps = footstepsQuery.data ?? initialFootsteps ?? null;
  const myself = myselfQuery.data ?? initialMyself ?? null;
  // creator パネルの表示判定: 村建て本人または管理者 (旧仕様: 管理者 = 全村 creator 扱い)。
  const canSeeCreatorPanel = village.isCreator || isAdmin;
  // 発言は常に最新日に積まれる。過去日タブを見ているときに発言してもその日には
  // 出ないので、混乱を避けるため SayForm は最新日表示時のみ出す。
  const isViewingLatestDay = selectedDay === village.time.latestDay;

  const latestDay = village.time.latestDay;
  // setParams の updater 形式で前回値を取り、`params` を依存配列から外す
  // (URLSearchParams は毎 render 新インスタンスなので依存に入れると memo が無効化される)。
  const selectDay = useCallback(
    (day: number) => {
      setParams(
        (prev) => {
          const next = new URLSearchParams(prev);
          // 最新日に戻すときは ?day= を消して URL を綺麗に保つ。
          if (day === latestDay) next.delete("day");
          else next.set("day", String(day));
          return next;
        },
        { replace: true },
      );
    },
    [setParams, latestDay],
  );

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100">
      <section className="max-w-4xl mx-auto px-6 py-10 space-y-6">
        <VillageHeader village={village} />

        <DayTabs
          days={village.days.list.map((d) => d.day)}
          selectedDay={selectedDay}
          onSelect={selectDay}
        />

        {myself && <MyselfPanel myself={myself} />}

        <ParticipateActions village={village} myself={myself} />

        {myself && <RpActions village={village} myself={myself} />}

        {myself && <ActionPanel village={village} myself={myself} />}

        {canSeeCreatorPanel && <CreatorPanel village={village} />}

        {isAdmin && <AdminPanel village={village} />}

        <RoomGridPanel village={village} day={selectedDay} />

        <ParticipantsPanel participants={village.participants.list} />

        <MessagesPanel
          messages={messages}
          day={selectedDay}
          participants={village.participants.list}
        />

        {myself && !myself.isSpectator && !myself.isDead && isViewingLatestDay && (
          <SayForm villageId={villageId} />
        )}

        <FootstepsPanel footsteps={footsteps} />
      </section>
    </main>
  );
}

function SayForm({ villageId }: { villageId: number }) {
  const [text, setText] = useState("");
  const sayMutation = useSayMutation(villageId);

  function submit(e: React.FormEvent) {
    e.preventDefault();
    const trimmed = text.trim();
    if (!trimmed) return;
    sayMutation.mutate(
      { message: trimmed },
      {
        onSuccess: () => setText(""),
      },
    );
  }

  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-2">発言</h2>
      <form onSubmit={submit} className="space-y-2">
        <textarea
          value={text}
          onChange={(e) => setText(e.target.value)}
          rows={3}
          maxLength={400}
          placeholder="発言を入力 (400 文字以内)"
          className="w-full bg-slate-900 border border-slate-700 rounded px-3 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-indigo-500"
          disabled={sayMutation.isPending}
        />
        <div className="flex items-center gap-3">
          <button
            type="submit"
            disabled={sayMutation.isPending || text.trim().length === 0}
            className="rounded bg-indigo-500 hover:bg-indigo-400 disabled:opacity-40 disabled:cursor-not-allowed px-4 py-1.5 text-sm font-medium"
          >
            {sayMutation.isPending ? "送信中..." : "発言する"}
          </button>
          <span className="text-xs text-slate-500">{text.length} / 400</span>
          {sayMutation.isError && (
            <span className="text-xs text-rose-300">{sayMutation.error.message}</span>
          )}
        </div>
      </form>
    </section>
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

/**
 * 旧 .old-thymeleaf/templates/village/village-day-list.html 相当の日付タブ。
 * `?day=N` を URL に同期させ、ページ遷移なしで messages を切り替える。
 */
function DayTabs({
  days,
  selectedDay,
  onSelect,
}: {
  days: number[];
  selectedDay: number;
  onSelect: (day: number) => void;
}) {
  if (days.length === 0) return null;
  // 単一の MessagesPanel に対する切替なので tablist パターンを採用 (role=tab + aria-selected)。
  // 矢印キーナビ (roving tabindex) は実装しないため tabIndex は全タブ 0 のまま
  // (= Tab キーで巡回できる)。12b 以降で role=tabpanel + 矢印キー対応をまとめて入れる。
  return (
    <div
      role="tablist"
      aria-label="日付ナビゲーション"
      className="flex flex-wrap gap-1 rounded-xl bg-slate-800/30 border border-slate-700 p-2"
    >
      {days.map((day) => {
        const active = day === selectedDay;
        return (
          <button
            key={day}
            type="button"
            role="tab"
            aria-selected={active}
            onClick={() => onSelect(day)}
            className={
              "rounded px-2.5 py-1 text-xs font-mono transition " +
              (active
                ? "bg-indigo-500/40 text-indigo-50 border border-indigo-400"
                : "border border-transparent text-slate-300 hover:bg-slate-700/40")
            }
          >
            {day === 0 ? "プロローグ" : `${day}日目`}
          </button>
        );
      })}
    </div>
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

/**
 * 旧 .old-thymeleaf/templates/village/situation.html 参加者タブ相当。
 * 生存 / 死亡 (見学者は別途末尾) に分割表示し、memo / 死亡日時を出す。
 */
function ParticipantsPanel({ participants }: { participants: VillageParticipantView[] }) {
  if (participants.length === 0) {
    return (
      <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
        <h2 className="text-sm text-slate-400">参加者</h2>
        <p className="text-slate-400 text-sm py-2">まだ参加者がいません</p>
      </section>
    );
  }
  // 退村済み (isGone) は旧画面でも参加者一覧から外れていたので除外する。
  // 残りを生存 (見学を含めない) / 死亡 / 見学 の 3 カテゴリへ振り分ける。
  const alive: VillageParticipantView[] = [];
  const dead: VillageParticipantView[] = [];
  const spectators: VillageParticipantView[] = [];
  for (const p of participants) {
    if (p.isGone) continue;
    if (p.isSpectator) spectators.push(p);
    else if (p.isDead) dead.push(p);
    else alive.push(p);
  }
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4 space-y-4">
      <h2 className="text-sm text-slate-400">
        参加者 (生存 {alive.length} / 死亡 {dead.length}
        {spectators.length > 0 ? ` / 見学 ${spectators.length}` : ""})
      </h2>
      <ParticipantSubList title={`生存 (${alive.length})`} items={alive} />
      <ParticipantSubList title={`死亡 (${dead.length})`} items={dead} isDead />
      {spectators.length > 0 && (
        <ParticipantSubList title={`見学 (${spectators.length})`} items={spectators} isSpectator />
      )}
    </section>
  );
}

function ParticipantSubList({
  title,
  items,
  isDead = false,
  isSpectator = false,
}: {
  title: string;
  items: VillageParticipantView[];
  isDead?: boolean;
  isSpectator?: boolean;
}) {
  if (items.length === 0) {
    return (
      <div>
        <h3 className="text-xs text-slate-400 mb-1">{title}</h3>
        <p className="text-xs text-slate-500 px-2">なし</p>
      </div>
    );
  }
  return (
    <div>
      <h3 className="text-xs text-slate-400 mb-1">{title}</h3>
      <ul className="grid grid-cols-1 sm:grid-cols-2 gap-1.5">
        {items.map((p) => (
          <li key={p.id} className="flex items-center gap-2 text-sm">
            <span className="font-mono text-slate-500 w-10 shrink-0">
              {p.roomNumber != null ? String(p.roomNumber).padStart(2, "0") : "--"}
            </span>
            <span className={`flex-1 truncate ${isDead ? "text-slate-400" : ""}`}>
              {p.name}
              {p.memo && (
                <span className="ml-2 text-slate-500 text-xs">[{p.memo}]</span>
              )}
            </span>
            {p.skill && (
              <span className="text-xs text-indigo-300 shrink-0">[{p.skill.shortName}]</span>
            )}
            {isSpectator && (
              <span className="text-xs text-slate-400 shrink-0">見学</span>
            )}
            {isDead && (
              <span className="text-xs text-rose-300 shrink-0">
                {p.deadDay != null ? `${p.deadDay}d ` : ""}
                {/* deadReasonName が null = 進行中の無惨死 (襲撃 / 呪殺 / 罠死 /
                    爆死 / 雑魚) でマスクされている状態。旧 DeadReason.getDisplayName
                    と同じ「無惨死」表記でカバーする */}
                {p.deadReasonName ?? "無惨死"}
              </span>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}

function MessagesPanel({
  messages,
  day,
  participants,
}: {
  messages: MessagesView | null;
  day: number;
  participants: VillageParticipantView[];
}) {
  // fromParticipantId → VillageParticipantView の逆引きマップを 1 回だけ作る。
  // useMemo で participants 配列の identity が変わったときのみ作り直す。
  const participantsById = useMemo(
    () => new Map(participants.map((p) => [p.id, p])),
    [participants],
  );
  const count = messages?.list.length ?? 0;
  return (
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-3">
        発言 ({day === 0 ? "プロローグ" : `${day}日目`} · {count}件)
      </h2>
      {!messages || count === 0 ? (
        <p className="text-slate-400 text-sm py-2">この日の閲覧可能な発言はありません</p>
      ) : (
        <ul className="space-y-2 max-h-[640px] overflow-y-auto pr-1">
          {messages.list.map((m, i) => (
            <li key={`${m.typeCode}-${m.number ?? i}`}>
              <MessageCard message={m} participantsById={participantsById} />
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
