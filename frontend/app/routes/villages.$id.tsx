import { useCallback, useMemo, useState } from "react";
import { Link, useSearchParams } from "react-router";
import { useQueryClient } from "@tanstack/react-query";
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
import { PageHeader } from "~/components/layout/PageHeader";
import { PageFooter } from "~/components/layout/PageFooter";

const PAGE_SIZE = 50;

export function meta({ data }: Route.MetaArgs) {
  const name = data?.village?.name ?? "村詳細";
  return [{ title: `${name} | WOLF MANSION` }];
}

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
  const village = await fetchVillage(villageId, api).catch(() => null);
  if (!village) throw new Response("village not found", { status: 404 });
  const initialDay = dayParam ?? village.time.latestDay;
  const messagesQuery = buildMessagesQuery(initialDay, initialFilter, initialPage);
  const [messages, footsteps, myself, situation] = await Promise.all([
    fetchVillageMessages(villageId, messagesQuery, api).catch(() => null),
    fetchVillageFootsteps(villageId, api).catch(() => null),
    fetchMyself(villageId, api).catch(() => null),
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
  const selectedDay = parseDayParam(params.get("day")) ?? initialDay;
  const filter = useMemo(() => parseFilterFromParams(params), [params]);
  const page = parsePageParam(params.get("page"));
  const isFiltered = !isEmptyFilter(filter);
  const isPaging = typeof page === "number";

  const [showFilter, setShowFilter] = useState(false);
  const [showInfo, setShowInfo] = useState(false);

  const villageQuery = useVillageQuery(villageId, initialVillage);
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
  const meQuery = useMeQuery();
  const authority = meQuery.data?.user?.authority;
  const isAdmin = authority === "管理者";

  const village = villageQuery.data ?? initialVillage;
  const messages = messagesQuery.data ?? null;
  const footsteps = footstepsQuery.data ?? initialFootsteps ?? null;
  const myself = myselfQuery.data ?? initialMyself ?? null;
  const situation = situationQuery.data ?? initialSituation ?? null;
  const canSeeCreatorPanel = village.isCreator || isAdmin;
  const isViewingLatestDay = selectedDay === village.time.latestDay;

  const latestDay = village.time.latestDay;
  const selectDay = useCallback(
    (day: number) => {
      setParams(
        (prev) => {
          const next = new URLSearchParams(prev);
          if (day === latestDay) next.delete("day");
          else next.set("day", String(day));
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
    <main className="max-w-[1170px] mx-auto text-white pb-[40px]">
      <PageHeader />
      <section className="px-[15px] pb-[60px] space-y-[15px]">
        <SayFormProvider>
          <VillageHeader
            village={village}
            villageId={villageId}
            selectedDay={selectedDay}
          />

          <DayTabs
            days={village.days.list.map((d) => d.day)}
            selectedDay={selectedDay}
            onSelect={selectDay}
            statusName={village.statusName}
          />

          {/* 旧 village.html はメッセージを最上位に置く構成。
              user-context panel (あなた / 参加 / RP / 行動 / Creator / Admin) は
              メッセージの下、SayForm の前後で並べる。 */}
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

          {myself && <MyselfPanel myself={myself} />}

          <ParticipateActions village={village} myself={myself} />

          {myself && <RpActions village={village} myself={myself} />}

          {myself && <ActionPanel village={village} myself={myself} />}

          {canSeeCreatorPanel && <CreatorPanel village={village} />}

          {isAdmin && <AdminPanel village={village} />}

          <SituationOverviewPanel
            village={village}
            situation={situation}
            selectedDay={selectedDay}
          />

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
      <PageFooter />
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
          const el = document.querySelector("[data-action-panel]");
          el?.scrollIntoView({ behavior: "smooth", block: "start" });
        }}
        onRefresh={refreshAll}
      />
    </main>
  );
}

// ---------- 部品 ----------

/**
 * 旧 village.html の冒頭部分 (`0001. 村名` のヘッダ行 + ステータスタグ + 補助 link) 相当。
 * 本番計測 (wolfort.net /village/13) では:
 * - h1: 22.5px / weight 400 / line-height 1.428em
 * - 村番号は `0001.` (4桁ゼロ詰め + ピリオド) で名前と空白なし結合
 */
function VillageHeader({
  village,
  villageId,
  selectedDay,
}: {
  village: VillageView;
  villageId: number;
  selectedDay: number;
}) {
  // 旧 village.html 踏襲: h1 は `0001. 村名` のみで status badge は含めない。
  // 状態名は DayTabs 末尾に出している (= 旧画面のレイアウト)。
  return (
    <header className="flex items-baseline justify-between flex-wrap gap-2">
      <h1 className="text-[1.875em] font-normal m-0 leading-[1.1]">
        {village.number}. {village.name}
      </h1>
      <div className="flex items-center gap-2 text-[0.95em]">
        <Link to="/villages" className="message-link hover:underline">← 村一覧</Link>
        <Link
          to={`/villages/${villageId}/scrap?day=${selectedDay}`}
          target="_blank"
          rel="noopener noreferrer"
          className="message-link hover:underline"
        >
          切り抜き ↗
        </Link>
      </div>
    </header>
  );
}

/**
 * 旧 .old-thymeleaf/templates/village/village-day-list.html 相当の日付タブ。
 *
 * 本番は BS3 btn-group を ul として並べ、active=mint背景+白字 / inactive=黒背景+mint枠+mint字 の
 * .btn-success スタイル。村末尾には現ステータス (進行中 / 終了 等) を `<li>` で出している。
 */
function DayTabs({
  days,
  selectedDay,
  onSelect,
  statusName,
}: {
  days: number[];
  selectedDay: number;
  onSelect: (day: number) => void;
  statusName: string;
}) {
  if (days.length === 0) return null;
  return (
    <ul
      role="tablist"
      aria-label="日付ナビゲーション"
      className="flex flex-wrap items-center gap-0 list-none m-0 p-0"
    >
      {days.map((day) => {
        const active = day === selectedDay;
        return (
          <li key={day} className="-ml-px first:ml-0">
            <button
              type="button"
              role="tab"
              aria-selected={active}
              onClick={() => onSelect(day)}
              className={
                "px-[9px] py-[5px] border text-[13px] leading-[1.4] cursor-pointer transition-colors duration-100 " +
                (active
                  ? "bg-mint-600 border-mint-600 text-white"
                  : "bg-night-500 border-mint-600 text-mint-600 hover:bg-mint-600 hover:text-white")
              }
            >
              {day === 0 ? "プロローグ" : `${day}日目`}
            </button>
          </li>
        );
      })}
      <li className="px-3 text-[13px] opacity-80">{statusName}</li>
    </ul>
  );
}

function MyselfPanel({ myself }: { myself: MyselfView }) {
  return (
    <Panel>
      <PanelHeading>
        <h2 className="text-[1em] m-0 font-normal">あなた</h2>
      </PanelHeading>
      <PanelBody>
        <p className="m-0 text-[1em]">
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
 * 旧 situation.html の "状況 / 部屋割り当て / 参加者 / 投票 / 足音" タブ群相当。
 * Panel 1 枚の中にタブ UI を構築し、本番のコンパクトな表示に揃える。
 *
 * 本番は jQuery タブで切替えるが、React 版は state でタブ切替。
 * RoomGridPanel / ParticipantsPanel / SituationPanel をタブパネルとして組み合わせる。
 */
function SituationOverviewPanel({
  village,
  situation,
  selectedDay,
}: {
  village: VillageView;
  situation: ReturnType<typeof useVillageSituationQuery>["data"] | null;
  selectedDay: number;
}) {
  // 表示可能なタブを決める
  const hasRoom = village.roomWidth != null && selectedDay > 0;
  // 参加者は常にあり (1人でも)。
  const hasParticipants = village.participants.list.length > 0;
  const hasSituation = situation != null && selectedDay > 0;

  type Tab = "room" | "participants" | "situation";
  const tabs: Tab[] = [];
  if (hasRoom) tabs.push("room");
  if (hasParticipants) tabs.push("participants");
  if (hasSituation) tabs.push("situation");

  const [active, setActive] = useState<Tab>(tabs[0] ?? "participants");

  if (tabs.length === 0) return null;

  const tabLabel: Record<Tab, string> = {
    room: "部屋割り当て",
    participants: "参加者",
    situation: "状況",
  };

  return (
    <Panel>
      <PanelHeading>
        <ul className="flex list-none m-0 p-0 -my-[10px] -mx-[15px]">
          {tabs.map((t) => {
            const isActive = t === active;
            return (
              <li key={t}>
                <button
                  type="button"
                  onClick={() => setActive(t)}
                  className={
                    "px-[15px] py-[10px] text-[13px] cursor-pointer border-b-2 transition-colors duration-100 " +
                    (isActive
                      ? "border-mint-500 text-mint-500"
                      : "border-transparent text-white hover:text-mint-500")
                  }
                >
                  {tabLabel[t]}
                </button>
              </li>
            );
          })}
        </ul>
      </PanelHeading>
      <PanelBody>
        {active === "room" && hasRoom && <RoomGridPanel village={village} day={selectedDay} />}
        {active === "participants" && (
          <ParticipantsPanel participants={village.participants.list} />
        )}
        {active === "situation" && (
          <SituationPanel situation={situation ?? null} selectedDay={selectedDay} />
        )}
      </PanelBody>
    </Panel>
  );
}

function ParticipantsPanel({ participants }: { participants: VillageParticipantView[] }) {
  if (participants.length === 0) {
    return <p className="opacity-80 m-0 py-2">まだ参加者がいません</p>;
  }
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
    <div className="space-y-3">
      <ParticipantSubList title={`生存 (${alive.length})`} items={alive} kind="alive" />
      <ParticipantSubList title={`死亡 (${dead.length})`} items={dead} kind="dead" />
      {spectators.length > 0 && (
        <ParticipantSubList title={`見学 (${spectators.length})`} items={spectators} kind="spectator" />
      )}
    </div>
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
  const participantsById = useMemo(
    () => new Map(participants.map((p) => [p.id, p])),
    [participants],
  );
  const count = messages?.list.length ?? 0;
  const currentPage = messages?.currentPageNum ?? null;
  const totalPages = messages?.allPageCount ?? 0;
  const hasPrev = messages?.isExistPrePage ?? false;
  const hasNext = messages?.isExistNextPage ?? false;
  return (
    <Panel>
      <PanelHeading>
        <div className="flex flex-wrap items-center justify-between gap-2">
          <h2 className="text-[1em] m-0 font-normal">
            発言 ({day === 0 ? "プロローグ" : `${day}日目`} · {count}件)
            {isPaging && totalPages > 0 && currentPage != null && (
              <span className="ml-2 opacity-80">
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
          <ul className="list-none p-0 m-0">
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
  if (!isPaging) {
    return (
      <button
        type="button"
        className="px-[9px] py-[5px] border border-mint-600 bg-night-500 text-mint-600 text-[13px] hover:bg-mint-600 hover:text-white"
        onClick={() => onSetPage(1)}
      >
        分割表示
      </button>
    );
  }
  const cur = currentPage ?? 1;
  return (
    <div className="flex items-center gap-1 text-[13px]">
      <button
        type="button"
        className="px-[9px] py-[5px] border border-mint-600 bg-night-500 text-mint-600 disabled:opacity-40 hover:bg-mint-600 hover:text-white"
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
        className="px-[9px] py-[5px] border border-mint-600 bg-night-500 text-mint-600 disabled:opacity-40 hover:bg-mint-600 hover:text-white"
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
        <h2 className="text-[1em] m-0 font-normal">足音</h2>
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

function formatDateTime(iso: string): string {
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) return iso;
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  const hh = String(d.getHours()).padStart(2, "0");
  const mi = String(d.getMinutes()).padStart(2, "0");
  return `${mm}/${dd} ${hh}:${mi}`;
}
