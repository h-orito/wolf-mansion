import { useCallback, useMemo, useState } from "react";
import { Link, useSearchParams } from "react-router";
import type { Route } from "./+types/villages.$id";
import {
  fetchMyself,
  fetchVillage,
  fetchVillageFootsteps,
  fetchVillageMessages,
  fetchVillageSituation,
  type MessagesQuery,
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
  useVillageSituationQuery,
} from "~/features/village/detail/hooks";
import { ActionPanel } from "~/features/village/detail/ActionPanel";
import { AdminPanel } from "~/features/village/detail/AdminPanel";
import { CreatorPanel } from "~/features/village/detail/CreatorPanel";
import { FooterMenuDock } from "~/features/village/detail/FooterMenuDock";
import { MessageCard } from "~/features/village/detail/MessageCard";
import { ParticipateActions } from "~/features/village/detail/ParticipateActions";
import { RoomGridPanel } from "~/features/village/detail/RoomGridPanel";
import { RpActions } from "~/features/village/detail/RpActions";
import { SayForm } from "~/features/village/detail/SayForm";
import { SayFormProvider } from "~/features/village/detail/SayFormContext";
import { SituationPanel } from "~/features/village/detail/SituationPanel";
import {
  EMPTY_FILTER,
  isEmptyFilter,
  MessageFilterModal,
  type MessageFilterValue,
} from "~/features/village/detail/MessageFilter";
import { VillageInfoModal } from "~/features/village/detail/VillageInfoModal";
import { useMeQuery } from "~/features/auth/hooks";
import { ssrFetch } from "~/lib/api/client";
import { Panel, PanelBody, PanelHeading } from "~/components/ui/Panel";
import { Button, LinkButton } from "~/components/ui/Button";
import { VillageTag, villageTagLevel } from "~/components/ui/VillageTag";
import { useQueryClient } from "@tanstack/react-query";

const PAGE_SIZE = 50;

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

function parsePageParam(raw: string | null): number | undefined {
  if (raw == null || raw === "") return undefined;
  const n = Number(raw);
  if (!Number.isInteger(n) || n < 1) return undefined;
  return n;
}

/**
 * URL クエリ (`?type=A,B` `&from=1,2` `&to=3` `&kw=...`) を MessageFilterValue にパース。
 * 旧 Thymeleaf 互換ではなく URL を短く保つため、カンマ区切り 1 パラメータ + 短いキー名にした。
 */
function parseFilterFromParams(p: URLSearchParams): MessageFilterValue {
  const messageType = (p.get("type") ?? "")
    .split(",")
    .map((s) => s.trim())
    .filter(Boolean);
  const fromParticipantId = (p.get("from") ?? "")
    .split(",")
    .map((s) => Number(s.trim()))
    .filter((n) => Number.isInteger(n) && n > 0);
  const toParticipantId = (p.get("to") ?? "")
    .split(",")
    .map((s) => Number(s.trim()))
    .filter((n) => Number.isInteger(n) && n > 0);
  const keyword = p.get("kw") ?? "";
  return { messageType, fromParticipantId, toParticipantId, keyword };
}

function applyFilterToParams(p: URLSearchParams, v: MessageFilterValue) {
  if (v.messageType.length > 0) p.set("type", v.messageType.join(","));
  else p.delete("type");
  if (v.fromParticipantId.length > 0)
    p.set("from", v.fromParticipantId.join(","));
  else p.delete("from");
  if (v.toParticipantId.length > 0) p.set("to", v.toParticipantId.join(","));
  else p.delete("to");
  if (v.keyword.trim() !== "") p.set("kw", v.keyword.trim());
  else p.delete("kw");
}

function sortedNumbers(arr: number[]): number[] {
  return [...arr].sort((a, b) => a - b);
}
function sortedStrings(arr: string[]): string[] {
  return [...arr].sort();
}

/**
 * フィルタの等価判定。配列の順序差を吸収するため sort 後に比較する。
 * useVillageMessagesQuery 側の queryKey 生成と同じ正規化方針。
 */
function isFilterEqual(a: MessageFilterValue, b: MessageFilterValue): boolean {
  if (a.keyword.trim() !== b.keyword.trim()) return false;
  const aType = sortedStrings(a.messageType);
  const bType = sortedStrings(b.messageType);
  if (aType.length !== bType.length) return false;
  for (let i = 0; i < aType.length; i++) if (aType[i] !== bType[i]) return false;
  const aFrom = sortedNumbers(a.fromParticipantId);
  const bFrom = sortedNumbers(b.fromParticipantId);
  if (aFrom.length !== bFrom.length) return false;
  for (let i = 0; i < aFrom.length; i++) if (aFrom[i] !== bFrom[i]) return false;
  const aTo = sortedNumbers(a.toParticipantId);
  const bTo = sortedNumbers(b.toParticipantId);
  if (aTo.length !== bTo.length) return false;
  for (let i = 0; i < aTo.length; i++) if (aTo[i] !== bTo[i]) return false;
  return true;
}

function buildMessagesQuery(
  day: number,
  filter: MessageFilterValue,
  page: number | undefined,
): MessagesQuery {
  const q: MessagesQuery = { day };
  if (filter.messageType.length > 0) q.messageType = filter.messageType;
  if (filter.fromParticipantId.length > 0)
    q.fromParticipantId = filter.fromParticipantId;
  if (filter.toParticipantId.length > 0)
    q.toParticipantId = filter.toParticipantId;
  if (filter.keyword.trim() !== "") q.keyword = filter.keyword.trim();
  if (typeof page === "number") {
    q.pageSize = PAGE_SIZE;
    q.pageNum = page;
  }
  return q;
}

export async function loader({ request, params }: Route.LoaderArgs) {
  const villageId = Number(params.id);
  if (!Number.isFinite(villageId)) {
    throw new Response("invalid village id", { status: 400 });
  }
  const url = new URL(request.url);
  const dayParam = parseDayParam(url.searchParams.get("day"));
  const initialFilter = parseFilterFromParams(url.searchParams);
  const initialPage = parsePageParam(url.searchParams.get("page"));
  const api = ssrFetch(request);
  // 村の存在確認を先行させる。村が無い場合に messages / footsteps / myself へ
  // 無駄な API コール (backend で同じく 404 になる) が走るのを避けるため。
  const village = await fetchVillage(villageId, api).catch(() => null);
  if (!village) throw new Response("village not found", { status: 404 });
  // 初期表示日: ?day= があればそれ、無ければ最新日 (= backend で `day` 未指定時と同じ)。
  // 「latest」をクエリキーに乗せる都合で、ここでは明示的に number に正規化して渡す。
  const initialDay = dayParam ?? village.time.latestDay;
  // フィルタ / ページ指定があれば SSR でも反映 (URL 共有時の見た目を一致させる)。
  const messagesQuery = buildMessagesQuery(initialDay, initialFilter, initialPage);
  const [messages, footsteps, myself, situation] = await Promise.all([
    fetchVillageMessages(villageId, messagesQuery, api).catch(() => null),
    fetchVillageFootsteps(villageId, api).catch(() => null),
    fetchMyself(villageId, api).catch(() => null),
    // situation は day を渡さなくても backend 側で latestDay を採用するため未指定で OK。
    // CSR でも `useVillageSituationQuery` は day なしで叩く想定。
    fetchVillageSituation(villageId, undefined, api).catch(() => null),
  ]);
  return {
    villageId,
    village,
    initialDay,
    initialFilter,
    initialPage,
    messages,
    footsteps,
    myself,
    situation,
  };
}

export default function VillageDetail({ loaderData }: Route.ComponentProps) {
  const {
    villageId,
    village: initialVillage,
    initialDay,
    initialFilter,
    initialPage,
    messages: initialMessages,
    footsteps: initialFootsteps,
    myself: initialMyself,
    situation: initialSituation,
  } = loaderData;

  const [params, setParams] = useSearchParams();
  // ?day を最優先で使い、無ければ loader で求めた initialDay (= latest) にフォールバック。
  const selectedDay = parseDayParam(params.get("day")) ?? initialDay;
  const filter = useMemo(() => parseFilterFromParams(params), [params]);
  const page = parsePageParam(params.get("page"));
  const isFiltered = !isEmptyFilter(filter);
  const isPaging = typeof page === "number";

  const [showFilter, setShowFilter] = useState(false);
  const [showInfo, setShowInfo] = useState(false);

  const villageQuery = useVillageQuery(villageId, initialVillage);
  // SSR initialData: 日 / フィルタ / ページが loader と一致するときだけ使う。
  // どれかが変われば別データなので initial を渡すと不整合になる。
  // フィルタ比較は配列の順序差で initial を捨てないよう、useVillageMessagesQuery
  // 側の queryKey と同じく sort 後比較で揃える。
  const isInitialView =
    selectedDay === initialDay &&
    page === initialPage &&
    isFilterEqual(filter, initialFilter);
  const messagesInitialData = isInitialView ? initialMessages ?? undefined : undefined;
  const messagesQuery = useVillageMessagesQuery(
    villageId,
    buildMessagesQuery(selectedDay, filter, page),
    messagesInitialData,
  );
  const footstepsQuery = useVillageFootstepsQuery(villageId, initialFootsteps ?? undefined);
  const myselfQuery = useMyselfQuery(villageId, initialMyself);
  const situationQuery = useVillageSituationQuery(villageId, initialSituation ?? undefined);
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
  const situation = situationQuery.data ?? initialSituation ?? null;
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
          // 日付を切り替えたらページ番号はリセット (異なる日のページ位置は意味なし)。
          next.delete("page");
          return next;
        },
        { replace: true },
      );
    },
    [setParams, latestDay],
  );

  const applyFilter = useCallback(
    (v: MessageFilterValue) => {
      setParams(
        (prev) => {
          const next = new URLSearchParams(prev);
          applyFilterToParams(next, v);
          // フィルタ変更でページ番号はリセット (件数が変わるため意味が変わる)。
          next.delete("page");
          return next;
        },
        { replace: false },
      );
    },
    [setParams],
  );

  const setPage = useCallback(
    (p: number) => {
      setParams(
        (prev) => {
          const next = new URLSearchParams(prev);
          if (p <= 1) next.delete("page");
          else next.set("page", String(p));
          return next;
        },
        { replace: true },
      );
    },
    [setParams],
  );

  // SayForm 表示判定: 最新日 + backend が「発言可能」と返している場合のみ。
  // `availableMessageTypes` が空のときは backend が事前に「発言できる種別なし」と
  // 判断しているので、フロント側で死亡 / 見学のフラグを別途確認しない。
  // (見学者専用の `SPECTATE_SAY` 等も backend で availableMessageTypes に含まれるため)
  const canShowSayForm =
    myself != null &&
    isViewingLatestDay &&
    myself.say.isAvailableSay &&
    myself.say.availableMessageTypes.length > 0;

  const queryClient = useQueryClient();
  const refreshAll = useCallback(() => {
    queryClient.invalidateQueries({ queryKey: ["village", villageId] });
  }, [queryClient, villageId]);

  return (
    <main className="text-white pb-[60px]">
      <section className="max-w-[1170px] mx-auto px-2 py-4 space-y-3">
        <SayFormProvider>
          <VillageHeader
            village={village}
            onOpenInfo={() => setShowInfo(true)}
            onOpenFilter={() => setShowFilter(true)}
            isFiltered={isFiltered}
            villageId={villageId}
            selectedDay={selectedDay}
          />

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

          <SituationPanel situation={situation} selectedDay={selectedDay} />

          <MessagesPanel
            messages={messages}
            day={selectedDay}
            participants={village.participants.list}
            isPaging={isPaging}
            onSetPage={setPage}
          />

          {canShowSayForm && !isFiltered && !isPaging && (
            <SayForm villageId={villageId} myself={myself!} />
          )}

          <FootstepsPanel footsteps={footsteps} />
        </SayFormProvider>

        <MessageFilterModal
          open={showFilter}
          value={filter}
          participants={village.participants.list}
          onApply={applyFilter}
          onClose={() => setShowFilter(false)}
        />
        <VillageInfoModal
          open={showInfo}
          village={village}
          onClose={() => setShowInfo(false)}
        />
      </section>
      <FooterMenuDock
        onOpenInfo={() => setShowInfo(true)}
        onOpenFilter={() => setShowFilter(true)}
        isFiltered={isFiltered}
        showVoteShortcut={
          !!myself &&
          village.statusCode === "IN_PROGRESS" &&
          myself.vote.canVote &&
          myself.vote.targetCharaId == null
        }
        onGoToVote={() => {
          // 行動パネル (id 未設定だが ActionPanel の <h2>行動</h2> 近辺) へ簡易スクロール。
          // 厳密な id ターゲットは別 PR で。
          const el = document.querySelector("[data-action-panel]");
          el?.scrollIntoView({ behavior: "smooth", block: "start" });
        }}
        onRefresh={refreshAll}
      />
    </main>
  );
}

// ---------- 部品 ----------

function VillageHeader({
  village,
  villageId,
  selectedDay,
  onOpenInfo,
  onOpenFilter,
  isFiltered,
}: {
  village: VillageView;
  villageId: number;
  selectedDay: number;
  onOpenInfo: () => void;
  onOpenFilter: () => void;
  isFiltered: boolean;
}) {
  return (
    <header className="space-y-2">
      <div className="flex items-center justify-between">
        <div className="flex items-baseline gap-2">
          <span className="font-mono opacity-60 text-[0.95em]">#{village.number}</span>
          <h1 className="text-[1.5em] font-bold m-0">
            <VillageTag level={villageTagLevel(village.statusName)}>{village.statusName}</VillageTag>
            {village.name}
          </h1>
        </div>
        <Link to="/villages" className="message-link hover:underline">
          ← 村一覧
        </Link>
      </div>
      <div className="flex flex-wrap items-center gap-3 text-[0.95em] opacity-90">
        <span>現在 {village.time.latestDay}日目</span>
        {village.time.nextDayChangeDatetime && (
          <span className="opacity-80">
            次回更新: {formatDateTime(village.time.nextDayChangeDatetime)}
          </span>
        )}
        {village.winCampName && (
          <span className="text-mint-500">勝利: {village.winCampName}</span>
        )}
      </div>
      <p className="text-[0.85em] opacity-60 m-0">
        村建て: {village.createPlayerName} / {village.participants.count}人
        {village.participants.spectatorCount > 0 ? ` (見学${village.participants.spectatorCount})` : ""}
      </p>
      <div className="flex flex-wrap gap-2 pt-1">
        <Button variant="dark-success" onClick={onOpenInfo}>村情報</Button>
        <Button
          variant={isFiltered ? "success" : "dark-success"}
          onClick={onOpenFilter}
          aria-pressed={isFiltered}
        >
          発言抽出{isFiltered ? " (絞り込み中)" : ""}
        </Button>
        <LinkButton
          variant="dark-success"
          to={`/villages/${villageId}/scrap?day=${selectedDay}`}
          target="_blank"
          rel="noopener noreferrer"
        >
          切り抜き
        </LinkButton>
      </div>
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
      className="flex flex-wrap gap-[2px] border border-night-700 bg-night-950 p-2 rounded-[3px]"
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
              "px-[9px] py-[5px] rounded-[3px] border text-[0.95em] font-mono transition-colors duration-100 " +
              (active
                ? "bg-mint-600 text-white border-mint-600"
                : "border-mint-600 text-mint-600 bg-night-500 hover:bg-mint-600 hover:text-white")
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
    <Panel>
      <PanelHeading>
        <h2 className="text-sm m-0">あなた</h2>
      </PanelHeading>
      <PanelBody>
        <p className="m-0">
          {myself.name}
          {myself.skill && <span className="ml-2 text-mint-500">[{myself.skill.name}]</span>}
          {myself.campCode && <span className="ml-2 text-warning-500">{myself.campCode}</span>}
          {myself.isDead && <span className="ml-2 text-blood-500">死亡</span>}
        </p>
      </PanelBody>
    </Panel>
  );
}

/**
 * 旧 .old-thymeleaf/templates/village/situation.html 参加者タブ相当。
 * 生存 / 死亡 (見学者は別途末尾) に分割表示し、memo / 死亡日時を出す。
 */
function ParticipantsPanel({ participants }: { participants: VillageParticipantView[] }) {
  if (participants.length === 0) {
    return (
      <Panel>
        <PanelHeading>
          <h2 className="text-sm m-0">参加者</h2>
        </PanelHeading>
        <PanelBody>
          <p className="opacity-80 m-0">まだ参加者がいません</p>
        </PanelBody>
      </Panel>
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
    else if (p.dead) dead.push(p);
    else alive.push(p);
  }
  return (
    <Panel>
      <PanelHeading>
        <h2 className="text-sm m-0">
          参加者 (生存 {alive.length} / 死亡 {dead.length}
          {spectators.length > 0 ? ` / 見学 ${spectators.length}` : ""})
        </h2>
      </PanelHeading>
      <PanelBody>
        <div className="space-y-4">
          <ParticipantSubList title={`生存 (${alive.length})`} items={alive} kind="alive" />
          <ParticipantSubList title={`死亡 (${dead.length})`} items={dead} kind="dead" />
          {spectators.length > 0 && (
            <ParticipantSubList title={`見学 (${spectators.length})`} items={spectators} kind="spectator" />
          )}
        </div>
      </PanelBody>
    </Panel>
  );
}

type ParticipantKind = "alive" | "dead" | "spectator";

function ParticipantSubList({
  title,
  items,
  kind,
}: {
  title: string;
  items: VillageParticipantView[];
  kind: ParticipantKind;
}) {
  if (items.length === 0) {
    return (
      <div>
        <h3 className="text-[0.95em] opacity-80 mb-1">{title}</h3>
        <p className="text-[0.85em] opacity-60 px-2 m-0">なし</p>
      </div>
    );
  }
  const isDead = kind === "dead";
  const isSpectator = kind === "spectator";
  return (
    <div>
      <h3 className="text-[0.95em] opacity-80 mb-1">{title}</h3>
      <ul className="grid grid-cols-1 sm:grid-cols-2 gap-1.5 list-none p-0 m-0">
        {items.map((p) => (
          <li key={p.id} className="flex items-center gap-2 text-[0.95em]">
            <span className="font-mono opacity-60 w-10 shrink-0">
              {p.roomNumber != null ? String(p.roomNumber).padStart(2, "0") : "--"}
            </span>
            <span className={`flex-1 truncate ${isDead ? "opacity-80" : ""}`}>
              {p.name}
              {p.memo && (
                <span className="ml-2 opacity-60 text-[0.85em]">[{p.memo}]</span>
              )}
            </span>
            {p.skill && (
              <span className="text-[0.85em] text-mint-500 shrink-0">[{p.skill.shortName}]</span>
            )}
            {isSpectator && (
              <span className="text-[0.85em] opacity-80 shrink-0">見学</span>
            )}
            {isDead && p.dead && (
              <span className="text-[0.85em] text-blood-500 shrink-0">
                {p.dead.day}d {p.dead.name}
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
  isPaging,
  onSetPage,
}: {
  messages: MessagesView | null;
  day: number;
  participants: VillageParticipantView[];
  isPaging: boolean;
  onSetPage: (page: number) => void;
}) {
  // fromParticipantId → VillageParticipantView の逆引きマップを 1 回だけ作る。
  // useMemo で participants 配列の identity が変わったときのみ作り直す。
  const participantsById = useMemo(
    () => new Map(participants.map((p) => [p.id, p])),
    [participants],
  );
  const count = messages?.list.length ?? 0;
  const currentPage = messages?.currentPageNum ?? null;
  const totalPages = messages?.allPageCount ?? 0;
  // backend 側 `isExistPrePage` / `isExistNextPage` を信頼する (フィルタ込で正確に出ている)。
  const hasPrev = messages?.isExistPrePage ?? false;
  const hasNext = messages?.isExistNextPage ?? false;
  return (
    <Panel>
      <PanelHeading>
        <div className="flex flex-wrap items-center justify-between gap-2">
          <h2 className="text-sm m-0">
            発言 ({day === 0 ? "プロローグ" : `${day}日目`} · {count}件)
            {isPaging && totalPages > 0 && currentPage != null && (
              <span className="ml-2 opacity-60">
                ({currentPage} / {totalPages} ページ)
              </span>
            )}
          </h2>
          <Pagination
            isPaging={isPaging}
            currentPage={currentPage}
            totalPages={totalPages}
            hasPrev={hasPrev}
            hasNext={hasNext}
            onSetPage={onSetPage}
          />
        </div>
      </PanelHeading>
      <PanelBody>
        {!messages || count === 0 ? (
          <p className="opacity-80 m-0 py-2">この日の閲覧可能な発言はありません</p>
        ) : (
          <ul className="space-y-1 list-none p-0 m-0">
            {messages.list.map((m, i) => (
              <li key={`${m.typeCode}-${m.number ?? i}`}>
                <MessageCard message={m} participantsById={participantsById} />
              </li>
            ))}
          </ul>
        )}
        {(hasPrev || hasNext) && (
          <div className="flex justify-end mt-3">
            <Pagination
              isPaging={isPaging}
              currentPage={currentPage}
              totalPages={totalPages}
              hasPrev={hasPrev}
              hasNext={hasNext}
              onSetPage={onSetPage}
            />
          </div>
        )}
      </PanelBody>
    </Panel>
  );
}

function Pagination({
  isPaging,
  currentPage,
  totalPages,
  hasPrev,
  hasNext,
  onSetPage,
}: {
  isPaging: boolean;
  currentPage: number | null;
  totalPages: number;
  hasPrev: boolean;
  hasNext: boolean;
  onSetPage: (page: number) => void;
}) {
  // 全件 1 ページに収まる (ページング ON で次/前なし) または paging OFF で表示が長すぎる
  // ケースで "分割" ボタンを出す。`?page=1` を付けると backend が pageSize=50 で paging を ON にする。
  if (!isPaging) {
    return (
      <button
        type="button"
        className="rounded-[3px] border border-mint-600 bg-night-500 px-2 py-0.5 text-[0.95em] text-mint-600 hover:bg-mint-600 hover:text-white"
        onClick={() => onSetPage(1)}
      >
        分割表示
      </button>
    );
  }
  const cur = currentPage ?? 1;
  return (
    <div className="flex items-center gap-1 text-[0.95em]">
      <button
        type="button"
        className="rounded-[3px] border border-mint-600 bg-night-500 px-2 py-0.5 text-mint-600 disabled:opacity-40 hover:bg-mint-600 hover:text-white"
        onClick={() => onSetPage(cur - 1)}
        disabled={!hasPrev || cur <= 1}
      >
        ‹ 前
      </button>
      <span className="px-1 opacity-80">
        {cur}/{totalPages || cur}
      </span>
      <button
        type="button"
        className="rounded-[3px] border border-mint-600 bg-night-500 px-2 py-0.5 text-mint-600 disabled:opacity-40 hover:bg-mint-600 hover:text-white"
        onClick={() => onSetPage(cur + 1)}
        disabled={!hasNext}
      >
        次 ›
      </button>
    </div>
  );
}

function FootstepsPanel({ footsteps }: { footsteps: VillageFootstepsView | null }) {
  return (
    <Panel>
      <PanelHeading>
        <h2 className="text-sm m-0">足音</h2>
      </PanelHeading>
      <PanelBody>
        {!footsteps || footsteps.list.length === 0 ? (
          <p className="opacity-80 m-0 py-2">表示できる足音はありません</p>
        ) : (
          <ul className="space-y-1 text-[0.95em] list-none p-0 m-0">
            {footsteps.list.map((f, i) => (
              <li key={`${f.day}-${f.roomNumbers}-${i}`} className="flex items-center gap-2">
                <span className="opacity-60 w-12 shrink-0">{f.day}日目</span>
                <span className="font-mono">{f.roomNumbers}</span>
                {f.registerChara && (
                  <span className="text-[0.85em] opacity-80">
                    by {f.registerChara.shortName}
                  </span>
                )}
              </li>
            ))}
          </ul>
        )}
      </PanelBody>
    </Panel>
  );
}

// ---------- utils ----------

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
