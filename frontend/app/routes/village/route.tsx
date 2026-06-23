import { useCallback, useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router";

import { PageLayout } from "~/components/layout/PageLayout";
import { Button, LinkButton } from "~/components/ui/Button";
import { useMe } from "~/features/auth/useMe";
import { useRandomKeywords } from "~/features/random-keywords/useRandomKeywords";
import {
  participateVillage,
  type VillageDetailView,
  type VillageParticipateRequest,
} from "~/features/village/api";
import { useDisplaySettings } from "~/features/village/displaySettings";
import {
  applyFilterToParams,
  EMPTY_FILTER,
  isFiltering,
  parseFilter,
  type MessageFilter,
} from "~/features/village/filter";
import { useMessageSync } from "~/features/village/useMessageSync";
import { useSayFlow } from "~/features/village/useSayFlow";
import {
  useInvalidateVillage,
  useMyVillageSituation,
  useVillage,
  useVillageDebugInfo,
  useVillagePolling,
  useVillageSituation,
} from "~/features/village/useVillage";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import { AbilityPanel } from "~/features/village/components/action/AbilityPanel";
import { ActionPanel } from "~/features/village/components/action/ActionPanel";
import { CommitPanel } from "~/features/village/components/action/CommitPanel";
import { RpPanel } from "~/features/village/components/action/RpPanel";
import { SayPanel } from "~/features/village/components/action/SayPanel";
import { VotePanel } from "~/features/village/components/action/VotePanel";
import { AdminPanel } from "~/features/village/components/admin/AdminPanel";
import { CreatorPanel } from "~/features/village/components/admin/CreatorPanel";
import { DebugPanel } from "~/features/village/components/admin/DebugPanel";
import { DayList } from "~/features/village/components/info/DayList";
import { SituationPanel } from "~/features/village/components/info/SituationPanel";
import { FooterMenu } from "~/features/village/components/layout/FooterMenu";
import { MessageArea } from "~/features/village/components/message/MessageArea";
import { MessageCard } from "~/features/village/components/message/MessageCard";
import { AgeLimitModal } from "~/features/village/components/modal/AgeLimitModal";
import { FilterModal } from "~/features/village/components/modal/FilterModal";
import { SettingsModal } from "~/features/village/components/modal/SettingsModal";
import { VillageInfoModal } from "~/features/village/components/modal/VillageInfoModal";
import { InitialSkillModal } from "~/features/village/components/participate/InitialSkillModal";
import {
  ChangeSkillPanel,
  LeavePanel,
  SwitchParticipatePanel,
} from "~/features/village/components/participate/ParticipantOpsPanels";
import { ParticipatePanel } from "~/features/village/components/participate/ParticipatePanel";
import { useCountdown } from "~/features/village/useCountdown";
import { useVillageScroll } from "~/features/village/useVillageScroll";
import { Toast, useToast } from "~/components/ui/Toast";
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
function XPostButton({ village }: { village: VillageDetailView }) {
  const lines = [village.name];
  if (latestDayOf(village) === 0) {
    lines.push(`開始予定: ${formatStartDatetime(village.setting.startDatetime)}`);
  }
  const pageUrl = `${window.location.origin}/wolf-mansion/village/${village.id}`;
  const url = `https://x.com/intent/post?text=${encodeURIComponent(lines.join("\n") + "\n")}&hashtags=WOLF_MANSION&url=${encodeURIComponent(pageUrl)}`;
  return (
    <a
      href={url}
      target="_blank"
      rel="noreferrer"
      className="inline-flex items-center gap-[5px] rounded-full bg-black px-[12px] py-[4px] font-bold text-white hover:bg-[#333]"
    >
      <svg viewBox="0 0 24 24" className="h-[14px] w-[14px] fill-current" aria-hidden="true">
        <path d="M18.244 2.25h3.308l-7.227 8.26 8.502 11.24H16.17l-5.214-6.817L4.99 21.75H1.68l7.73-8.835L1.254 2.25H8.08l4.713 6.231zm-1.161 17.52h1.833L7.084 4.126H5.117z" />
      </svg>
      ポスト
    </a>
  );
}

export default function Village({ params }: Route.ComponentProps) {
  const villageId = Number(params.villageId);
  const dayParam = params.day != null ? Number(params.day) : undefined;

  const { scrollToBottom } = useVillageScroll();
  const { me } = useMe();
  const { data: village, error: villageError } = useVillage(villageId);
  const { data: situation } = useVillageSituation(villageId, dayParam, me?.name ?? null);
  const { data: mySituation, error: mySituationError } = useMyVillageSituation(villageId, dayParam);
  const { data: debugInfo } = useVillageDebugInfo(villageId);
  const { data: randomKeywords } = useRandomKeywords();
  const invalidate = useInvalidateVillage(villageId);
  const keywordList = (randomKeywords ?? []).map((k) => k.keyword ?? "").filter(Boolean);
  const canAction =
    mySituation?.say.selectableMessageTypeList?.some((t) => t.messageType.code === "ACTION") ??
    false;
  const canSecretReply =
    mySituation?.say.selectableMessageTypeList?.some((t) => t.messageType.code === "SECRET_SAY") ??
    false;

  const {
    reply,
    onReply,
    clearReply,
    sayPreview,
    sayError,
    saySubmitting,
    onSayConfirm,
    onActionConfirm,
    onCreatorSayConfirm,
    onSayDetermine,
    onSayCancel,
  } = useSayFlow(villageId, invalidate, scrollToBottom);

  // 発言抽出。URL searchParams が正本 (共有 URL で再現できる)
  const [searchParams, setSearchParams] = useSearchParams();
  const filter = parseFilter(searchParams);
  const [filterOpen, setFilterOpen] = useState(false);
  const [settingsOpen, setSettingsOpen] = useState(false);
  const [infoOpen, setInfoOpen] = useState(false);
  // 年齢制限確認 → 初回役職確認の順で出す (同時に重ねない)
  const [ageLimitResolved, setAgeLimitResolved] = useState(false);
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

  const [participateError, setParticipateError] = useState<string | null>(null);
  const onParticipated = async (request: VillageParticipateRequest, charaImage: File | null) => {
    try {
      await participateVillage(villageId, request, charaImage);
      await invalidate();
      requestAnimationFrame(() => scrollToBottom());
    } catch (e) {
      setParticipateError(e instanceof ApiError ? e.detail : "入村に失敗しました");
      throw e;
    }
  };

  const latestDay = village != null ? latestDayOf(village) : undefined;
  const currentDay = dayParam ?? latestDay ?? 0;

  const daychangeDetected = useVillagePolling(villageId, latestDay);
  const showToast = useToast((s) => s.show);
  useEffect(() => {
    if (daychangeDetected) {
      showToast("日付が更新されました。ページを再読み込みしてください。", {
        variant: "info",
        persistent: true,
      });
    }
  }, [daychangeDetected, showToast]);

  const { onMessagesLoaded, hasNewMessage } = useMessageSync(
    villageId,
    dayParam,
    latestDay,
    currentDay,
    scrollToBottom,
    invalidate,
    showToast,
    sayPreview == null,
  );

  const largeText = useDisplaySettings((s) => s.largeText);
  // ログイン中のはずなのに認証が立て直せない (refresh 失敗) 場合は再ログインを促す
  const sessionExpired =
    me != null && mySituationError instanceof ApiError && mySituationError.status === 401;
  useEffect(() => {
    if (sessionExpired) {
      showToast("要再ログイン", { variant: "error", persistent: true });
    }
  }, [sessionExpired, showToast]);

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
    <PageLayout noAd={noAd} footerPaddingBottom={50}>
      <div className={`px-[15px] ${largeText ? "text-[150%]" : ""}`}>
        {/* 村タイトル */}
        <div className="flex">
          <h1 className="my-[10.5px] flex-1 text-[1.125em]">
            {villageNumber(village.id)}. {village.name}
          </h1>
          <div className="my-[10.5px]">
            <XPostButton village={village} />
          </div>
        </div>
        <hr className="mt-[5px] mb-[10px] border-[#464545]" />

        <DayList
          villageId={villageId}
          dayList={(village.days.list ?? []).map((d) => d.day)}
          currentDay={currentDay}
          epilogueDay={village.epilogueDay}
          onInfo={() => setInfoOpen(true)}
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
          confirmArea={
            sayPreview != null ? (
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
                      : sayPreview.kind === "creatorSay"
                        ? "発言する（村建て）"
                        : sayLabel(sayPreview.request.messageType)}
                  </Button>
                </div>
              </div>
            ) : null
          }
        />
        <DayList
          villageId={villageId}
          dayList={(village.days.list ?? []).map((d) => d.day)}
          currentDay={currentDay}
          epilogueDay={village.epilogueDay}
          onInfo={() => setInfoOpen(true)}
        />
        {!noAd && (
          <div className="mt-[15px] min-h-[90px] border border-dashed border-gray-600 p-2 text-center text-gray-400">
            広告（移行中はプレースホルダー）
          </div>
        )}
        <div id="bottom" />
        <hr className="mt-[5px] mb-[10px] border-[#464545]" />

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
              onClearReply={clearReply}
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

        {mySituation != null && mySituation.vote.canVote && (
          <VotePanel
            villageId={villageId}
            village={village}
            mySituation={mySituation}
            onDone={invalidate}
          />
        )}

        {mySituation != null && mySituation.myself?.skill != null && (
          <AbilityPanel
            villageId={villageId}
            village={village}
            mySituation={mySituation}
            roomAssignedRows={situation?.roomAssignedRowList}
            onDone={invalidate}
          />
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

        {mySituation != null && mySituation.commit.isAvailableCommit && (
          <CommitPanel villageId={villageId} mySituation={mySituation} onDone={invalidate} />
        )}

        {mySituation != null &&
          (mySituation.rp.isAvailableChangeName || mySituation.rp.isAvailableMemo) && (
            <RpPanel
              villageId={villageId}
              mySituation={mySituation}
              onDone={async () => {
                await invalidate();
                requestAnimationFrame(() => scrollToBottom());
              }}
            />
          )}

        {mySituation != null && mySituation.creator.isCreator && (
          <CreatorPanel
            villageId={villageId}
            mySituation={mySituation}
            participants={(situation?.participantList ?? []).map((p) => ({
              charaId: p.charaId,
              name: p.name,
            }))}
            members={(situation?.memberList ?? []).flatMap((m) => m.statusMemberList)}
            onConfirm={onCreatorSayConfirm}
            onDone={invalidate}
          />
        )}

        {mySituation != null && mySituation.admin.isAdmin && (
          <AdminPanel villageId={villageId} onDone={invalidate} />
        )}

        {debugInfo?.isDebugMode && (
          <DebugPanel
            villageId={villageId}
            currentDay={currentDay}
            debugInfo={debugInfo}
            onDone={invalidate}
          />
        )}

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
        onSettings={() => setSettingsOpen(true)}
        onInfo={() => setInfoOpen(true)}
      />
      <SettingsModal
        open={settingsOpen}
        onClose={() => setSettingsOpen(false)}
        villageId={villageId}
        mySituation={mySituation}
      />
      <VillageInfoModal
        open={infoOpen}
        onClose={() => setInfoOpen(false)}
        villageId={villageId}
        canModifySetting={mySituation?.creator.isAvailableModifySetting ?? false}
      />
      <InitialSkillModal
        villageId={villageId}
        mySituation={mySituation}
        suppressed={ageLimit != null && !ageLimitResolved}
      />
      <FilterModal
        open={filterOpen}
        onClose={() => setFilterOpen(false)}
        filter={filter}
        participants={situation?.participantList ?? []}
        myselfId={mySituation?.myself?.id ?? null}
        notificationKeyword={mySituation?.myself?.notification?.keyword ?? null}
        onApply={applyFilter}
        onApplyNewTab={applyFilterNewTab}
      />
      {ageLimit != null && (
        <AgeLimitModal
          villageId={villageId}
          ageLimit={ageLimit}
          onResolved={() => setAgeLimitResolved(true)}
        />
      )}

      {/* ステータス表示 */}
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
      <Toast />
    </PageLayout>
  );
}
