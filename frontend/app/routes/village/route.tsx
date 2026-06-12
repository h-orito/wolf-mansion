import { useCallback, useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router";

import { PageLayout } from "~/components/layout/PageLayout";
import { Button, LinkButton } from "~/components/ui/Button";
import { useMe } from "~/features/auth/useMe";
import { useRandomKeywords } from "~/features/random-keywords/useRandomKeywords";
import {
  actionVillage,
  confirmVillageAction,
  confirmVillageSay,
  participateVillage,
  sayVillage,
  type VillageActionRequest,
  type VillageDetailView,
  type VillageMessageContent,
  type VillageMessageListContent,
  type VillageParticipateRequest,
  type VillageSayRequest,
} from "~/features/village/api";
import {
  applyFilterToParams,
  EMPTY_FILTER,
  isFiltering,
  parseFilter,
  type MessageFilter,
} from "~/features/village/filter";
import { useNewMessageDetector } from "~/features/village/useMessages";
import {
  useInvalidateVillage,
  useMyVillageSituation,
  useVillage,
  useVillagePolling,
  useVillageSituation,
} from "~/features/village/useVillage";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import { AbilityPanel } from "./AbilityPanel";
import { VotePanel } from "./VotePanel";
import { ActionPanel } from "./ActionPanel";
import { AgeLimitModal } from "./AgeLimitModal";
import { DayList } from "./DayList";
import { FilterModal } from "./FilterModal";
import { FooterMenu } from "./FooterMenu";
import { MessageArea } from "./MessageArea";
import { ChangeSkillPanel, LeavePanel, SwitchParticipatePanel } from "./ParticipantOpsPanels";
import { ParticipatePanel } from "./ParticipatePanel";
import { type ReplyDraft } from "./MessageCard";
import { MessageCard } from "./MessageCard";
import { SayPanel } from "./SayPanel";
import { SituationPanel } from "./SituationPanel";
import { useCountdown } from "./useCountdown";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  // 村名は CSR で取得するため、確定後に document.title を村名へ差し替える (タイトルは村名のみ)
  return siteMeta();
}

function latestDayOf(village: VillageDetailView): number {
  const days = village.days.list ?? [];
  return days.length > 0 ? days[days.length - 1].day : 0;
}

/** 村番号は 4 桁 0 埋めで表示する。 */
function villageNumber(id: number): string {
  return String(id).padStart(4, "0");
}

/** 確認画面の投稿ボタンのラベル (発言種別ごと)。 */
function sayLabel(messageType: string | null | undefined): string {
  switch (messageType) {
    case "WEREWOLF_SAY":
      return "発言する（囁き）";
    case "MASON_SAY":
      return "発言する（共鳴）";
    case "LOVERS_SAY":
      return "発言する（恋人）";
    case "TELEPATHY":
      return "発言する（念話）";
    case "MONOLOGUE_SAY":
      return "発言する（独り言）";
    case "SECRET_SAY":
      return "発言する（秘話）";
    case "GRAVE_SAY":
      return "呻く";
    default:
      return "発言する";
  }
}

function formatStartDatetime(iso: string): string {
  const d = new Date(iso);
  const pad = (n: number) => String(n).padStart(2, "0");
  return `${d.getFullYear()}/${pad(d.getMonth() + 1)}/${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}`;
}

/**
 * 村の共有ツイートボタン。村名 (募集中は開始予定日時も) + ハッシュタグを共有する。
 * 公式 widget script は読み込まず、同じ共有 URL を開くボタンで代替する。
 */
function TweetButton({ village }: { village: VillageDetailView }) {
  const lines = [village.name];
  if (latestDayOf(village) === 0) {
    lines.push(`開始予定: ${formatStartDatetime(village.setting.startDatetime)}`);
  }
  const url = `https://twitter.com/share?text=${encodeURIComponent(lines.join("\n") + "\n")}&hashtags=WOLF_MANSION`;
  return (
    <a
      href={url}
      target="_blank"
      rel="noreferrer"
      className="rounded-full bg-[#1d9bf0] px-[12px] py-[4px] text-[11px] font-bold text-white hover:bg-[#0c7abf]"
    >
      ツイート
    </a>
  );
}

export default function Village({ params }: Route.ComponentProps) {
  const villageId = Number(params.villageId);
  const dayParam = params.day != null ? Number(params.day) : undefined;

  const { me } = useMe();
  const { data: village, error: villageError } = useVillage(villageId);
  const { data: situation } = useVillageSituation(villageId, dayParam, me?.name ?? null);
  const { data: mySituation, error: mySituationError } = useMyVillageSituation(villageId, dayParam);
  const { data: randomKeywords } = useRandomKeywords();
  const invalidate = useInvalidateVillage(villageId);
  const keywordList = (randomKeywords ?? []).map((k) => k.keyword ?? "").filter(Boolean);
  const canAction =
    mySituation?.say.selectableMessageTypeList?.some((t) => t.messageTypeCode === "ACTION") ??
    false;
  const canSecretReply =
    mySituation?.say.selectableMessageTypeList?.some((t) => t.messageTypeCode === "SECRET_SAY") ??
    false;

  // 発言抽出。URL searchParams が正本 (共有 URL で再現できる)
  const [searchParams, setSearchParams] = useSearchParams();
  const filter = parseFilter(searchParams);
  const [filterOpen, setFilterOpen] = useState(false);
  const applyFilter = (next: MessageFilter) =>
    setSearchParams(applyFilterToParams(searchParams, next));
  const applyFilterNewTab = (next: MessageFilter) => {
    const params = applyFilterToParams(searchParams, next);
    const query = params.toString();
    window.open(`${window.location.pathname}${query !== "" ? `?${query}` : ""}`);
  };
  const onHashtagClick = useCallback(
    (tag: string) => {
      setSearchParams((prev) => applyFilterToParams(prev, { ...EMPTY_FILTER, keywords: tag }));
    },
    [setSearchParams],
  );

  // 発言: 返信引き継ぎと確認 (プレビュー) → 投稿の 2 段フロー
  const [reply, setReply] = useState<ReplyDraft | null>(null);
  const [sayPreview, setSayPreview] = useState<
    | { kind: "say"; message: VillageMessageContent; request: VillageSayRequest }
    | { kind: "action"; message: VillageMessageContent; request: VillageActionRequest }
    | null
  >(null);
  const [sayError, setSayError] = useState<string | null>(null);
  const [saySubmitting, setSaySubmitting] = useState(false);
  const onReply = useCallback((draft: ReplyDraft) => {
    setReply(draft);
    document.getElementById("say-panel")?.scrollIntoView();
  }, []);
  const onSayConfirm = async (request: VillageSayRequest) => {
    setSayError(null);
    try {
      const response = await confirmVillageSay(villageId, request);
      if (response.message == null) {
        setSayError("発言の確認に失敗しました");
        return;
      }
      setSayPreview({ kind: "say", message: response.message, request });
      requestAnimationFrame(() =>
        document.getElementById("message-confirm-area")?.scrollIntoView(),
      );
    } catch (e) {
      setSayError(e instanceof ApiError ? e.detail : "発言の確認に失敗しました");
    }
  };
  const onActionConfirm = async (request: VillageActionRequest) => {
    setSayError(null);
    try {
      const response = await confirmVillageAction(villageId, request);
      if (response.message == null) {
        setSayError("アクションの確認に失敗しました");
        return;
      }
      setSayPreview({ kind: "action", message: response.message, request });
      requestAnimationFrame(() =>
        document.getElementById("message-confirm-area")?.scrollIntoView(),
      );
    } catch (e) {
      setSayError(e instanceof ApiError ? e.detail : "アクションの確認に失敗しました");
    }
  };
  const onSayDetermine = async () => {
    // 非冪等な投稿のため連打をガードする
    if (sayPreview == null || saySubmitting) return;
    setSaySubmitting(true);
    setSayError(null);
    try {
      if (sayPreview.kind === "say") {
        await sayVillage(villageId, sayPreview.request);
      } else {
        await actionVillage(villageId, sayPreview.request);
      }
      setSayPreview(null);
      setReply(null);
      await invalidate();
      requestAnimationFrame(() => document.getElementById("bottom")?.scrollIntoView());
    } catch (e) {
      setSayError(e instanceof ApiError ? e.detail : "発言に失敗しました");
    } finally {
      setSaySubmitting(false);
    }
  };
  const onSayCancel = () => {
    setSayPreview(null);
    document.getElementById("say-panel")?.scrollIntoView();
  };
  const [participateError, setParticipateError] = useState<string | null>(null);
  const onParticipated = async (request: VillageParticipateRequest, charaImage: File | null) => {
    try {
      await participateVillage(villageId, request, charaImage);
      await invalidate();
      requestAnimationFrame(() => document.getElementById("bottom")?.scrollIntoView());
    } catch (e) {
      setParticipateError(e instanceof ApiError ? e.detail : "入村に失敗しました");
      throw e;
    }
  };

  const latestDay = village != null ? latestDayOf(village) : undefined;
  const currentDay = dayParam ?? latestDay ?? 0;

  const daychangeDetected = useVillagePolling(villageId, latestDay);

  // 新着発言の検知。最新日を表示している間だけ最新発言日時を見比べる
  const [loadedMessages, setLoadedMessages] = useState<VillageMessageListContent | null>(null);
  const onMessagesLoaded = useCallback(
    (content: VillageMessageListContent) => setLoadedMessages(content),
    [],
  );
  const hasNewMessage = useNewMessageDetector(
    villageId,
    dayParam,
    loadedMessages?.latestMessageDatetime,
    latestDay != null && currentDay === latestDay,
  );
  // ログイン中のはずなのに認証が立て直せない (refresh 失敗) 場合は再ログインを促す
  const sessionExpired =
    me != null && mySituationError instanceof ApiError && mySituationError.status === 401;

  const dayChangeDatetime =
    village != null && !village.status.isFinished
      ? (village.days.list?.[village.days.list.length - 1]?.dayChangeDatetime ?? null)
      : null;
  const leftTime = useCountdown(dayChangeDatetime);

  useEffect(() => {
    if (village != null) document.title = `WOLF MANSION | ${village.name}`;
  }, [village]);

  if (villageError instanceof ApiError && villageError.status === 404) {
    return (
      <PageLayout>
        <div className="px-[15px] py-[30px]">村が見つかりませんでした。</div>
      </PageLayout>
    );
  }
  if (village == null) {
    return (
      <PageLayout>
        <div className="px-[15px] py-[30px] text-gray-400">読み込み中...</div>
      </PageLayout>
    );
  }

  const noAd = (village.setting.tags.list ?? []).some((tag) => tag.code === "R18");
  const ageLimit = (village.setting.tags.list ?? []).find(
    (tag) => tag.code === "R15" || tag.code === "R18",
  )?.name;

  return (
    <PageLayout noAd={noAd}>
      <div className="px-[15px] pb-[45px]">
        {/* 村タイトル */}
        <div className="flex">
          <h1 className="my-[10.5px] flex-1 text-[15px] font-normal">
            {villageNumber(village.id)}. {village.name}
          </h1>
          <div className="my-[10.5px]">
            <TweetButton village={village} />
          </div>
        </div>
        <hr className="mt-[5px] mb-[10px] border-[#464545]" />

        <DayList
          villageId={villageId}
          dayList={(village.days.list ?? []).map((d) => d.day)}
          currentDay={currentDay}
          epilogueDay={village.epilogueDay}
        />

        <MessageArea
          villageId={villageId}
          day={dayParam}
          randomKeywords={keywordList}
          filter={filter}
          onHashtagClick={onHashtagClick}
          onReply={mySituation?.say.isAvailableSay ? onReply : undefined}
          onSecret={canSecretReply ? onReply : undefined}
          onLoaded={onMessagesLoaded}
        />
        {sayPreview != null && (
          <div
            id="message-confirm-area"
            className="mb-[20px] rounded border border-[#ffff00] bg-[#303030] p-[10px]"
          >
            <p className="mb-[10px]">
              以下の内容で発言してよろしいですか？（まだ発言されていません）
            </p>
            <MessageCard
              villageId={villageId}
              message={sayPreview.message}
              randomKeywords={keywordList}
            />
            <div className="flex justify-end gap-[10px]">
              <Button variant="default" onClick={onSayCancel}>
                キャンセル
              </Button>
              <Button onClick={onSayDetermine} disabled={saySubmitting}>
                {sayPreview.kind === "action"
                  ? "アクション"
                  : sayLabel(sayPreview.request.messageType)}
              </Button>
            </div>
          </div>
        )}
        {!noAd && (
          <div className="mt-[15px] min-h-[90px] border border-dashed border-gray-600 p-2 text-center text-gray-400">
            広告（移行中はプレースホルダー）
          </div>
        )}
        <hr className="mt-[5px] mb-[10px] border-[#464545]" />

        {/* 最下部への移動先 (発言後のスクロール先) */}
        <div id="bottom" />

        {situation != null && (
          <SituationPanel situation={situation} day={currentDay} spoiled={filter.spoiled} />
        )}

        {mySituation?.say.isAvailableSay && (
          <div id="say-panel">
            {sayError != null && <p className="mb-[5px] text-[#e74c3c]">{sayError}</p>}
            <SayPanel
              village={village}
              mySituation={mySituation}
              randomKeywords={keywordList}
              reply={reply}
              onClearReply={() => setReply(null)}
              onConfirm={onSayConfirm}
            />
          </div>
        )}

        {mySituation != null && canAction && (
          <ActionPanel
            mySituation={mySituation}
            participants={situation?.participantList ?? []}
            onConfirm={onActionConfirm}
          />
        )}

        {mySituation != null && mySituation.ability.canUseAbility && (
          <AbilityPanel
            villageId={villageId}
            mySituation={mySituation}
            roomAssignedRows={situation?.roomAssignedRowList}
            onDone={invalidate}
          />
        )}

        {mySituation != null && mySituation.vote.canVote && (
          <VotePanel villageId={villageId} mySituation={mySituation} onDone={invalidate} />
        )}

        {mySituation != null &&
          !mySituation.participate.isParticipating &&
          (mySituation.participate.isAvailableParticipate ||
            mySituation.participate.isAvailableSpectate) && (
            <div>
              {participateError != null && (
                <p className="mb-[5px] text-[#e74c3c]">{participateError}</p>
              )}
              <ParticipatePanel
                village={village}
                mySituation={mySituation}
                onParticipated={onParticipated}
                onError={setParticipateError}
              />
            </div>
          )}

        {mySituation != null &&
          mySituation.participate.isParticipating &&
          mySituation.skillRequest.isAvailableSkillRequest && (
            <ChangeSkillPanel villageId={villageId} mySituation={mySituation} onDone={invalidate} />
          )}
        {mySituation?.participate.isAvailableSwitchParticipate && (
          <SwitchParticipatePanel villageId={villageId} onDone={invalidate} />
        )}
        {mySituation?.participate.isAvailableLeave && (
          <LeavePanel villageId={villageId} onDone={invalidate} />
        )}

        <DayList
          villageId={villageId}
          dayList={(village.days.list ?? []).map((d) => d.day)}
          currentDay={currentDay}
          epilogueDay={village.epilogueDay}
        />

        <div className="mb-[10px]">
          <LinkButton to="/" variant="default">
            サイトトップへ
          </LinkButton>
          <LinkButton
            to={`/village/${villageId}/scrap`}
            target="_blank"
            variant="success"
            className="ml-[10px]"
          >
            切り抜き画面へ
          </LinkButton>
        </div>
      </div>

      <FooterMenu
        onRefresh={() => invalidate()}
        hasNewMessage={hasNewMessage}
        onFilter={() => setFilterOpen(true)}
        filtering={isFiltering(filter)}
      />
      <FilterModal
        open={filterOpen}
        onClose={() => setFilterOpen(false)}
        filter={filter}
        participants={situation?.participantList ?? []}
        myselfId={mySituation?.myself?.id ?? null}
        notificationKeyword={mySituation?.myself?.notificationKeyword ?? null}
        onApply={applyFilter}
        onApplyNewTab={applyFilterNewTab}
      />
      {ageLimit != null && <AgeLimitModal villageId={villageId} ageLimit={ageLimit} />}

      {/* 通知系のオーバーレイ */}
      {daychangeDetected && (
        <div className="fixed top-0 right-[5px] left-[5px] z-[105] rounded bg-[#00bc8c] p-[15px] text-white">
          日付が更新されました。ページを再読み込みしてください。
        </div>
      )}
      {leftTime != null && (
        <div className="fixed top-[5px] right-[5px] z-[100] rounded bg-[#3498db] p-[5px] text-white">
          更新まで <span>{leftTime}</span>
        </div>
      )}
      {me != null && !sessionExpired && (
        <Link to={`/user/${me.name}`} target="_blank">
          <div className="fixed top-[5px] left-[5px] z-[100] rounded bg-[#3498db] p-[5px] text-white">
            ユーザID: {me.name}
          </div>
        </Link>
      )}
      {sessionExpired && (
        <div className="fixed top-[5px] left-[5px] z-[100] rounded bg-[#e74c3c] p-[5px] text-white">
          要再ログイン
        </div>
      )}
    </PageLayout>
  );
}
