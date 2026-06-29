import { useCallback, useEffect, useRef, useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router";

import { PageLayout } from "~/components/layout/PageLayout";
import { AdSense } from "~/components/ui/AdSense";
import { LinkButton } from "~/components/ui/Button";
import { useMe } from "~/features/auth/useMe";
import type { VillageDetailView } from "~/features/village/api";
import { useDisplaySettings } from "~/features/village/displaySettings";
import {
  applyFilterToParams,
  EMPTY_FILTER,
  isFiltering,
  parseFilter,
  type MessageFilter,
} from "~/features/village/filter";
import { useMessagePaging } from "~/features/village/useMessagePaging";
import { useMessageSync } from "~/features/village/useMessageSync";
import { RefreshContext, useRefresh } from "~/features/village/useRefresh";
import { useVillageContext, VillageProvider } from "~/features/village/VillageContext";
import { useSayFlow } from "~/features/village/useSayFlow";
import {
  useMyVillageSituation,
  useVillage,
  useVillagePolling,
  useVillageSituation,
} from "~/features/village/useVillage";
import { MessageType } from "~/features/village/components/message/messageType";
import { ApiError } from "~/lib/api";
import { formatStartDatetime } from "~/lib/datetime";
import { siteMeta } from "~/lib/meta";
import { SayPreviewArea } from "~/features/village/components/action/SayPreviewArea";
import { ActionPanels } from "~/features/village/components/ActionPanels";
import { DayList } from "~/features/village/components/info/DayList";
import { SituationPanel } from "~/features/village/components/info/SituationPanel";
import { FooterMenu } from "~/features/village/components/layout/FooterMenu";
import { MessageArea } from "~/features/village/components/message/MessageArea";
import { AgeLimitModal } from "~/features/village/components/modal/AgeLimitModal";
import { FilterModal } from "~/features/village/components/modal/FilterModal";
import { SettingsModal } from "~/features/village/components/modal/SettingsModal";
import { VillageInfoModal } from "~/features/village/components/modal/VillageInfoModal";
import { InitialSkillModal } from "~/features/village/components/participate/InitialSkillModal";
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

function XPostButton() {
  const village = useVillageContext();
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

  const navigate = useNavigate();
  const { scrollToBottom } = useVillageScroll();
  const { me } = useMe();
  const { data: village, error: villageError } = useVillage(villageId);
  const { data: situation } = useVillageSituation(villageId, dayParam, me?.name ?? null);
  const { data: mySituation, error: mySituationError } = useMyVillageSituation(villageId, dayParam);
  const { refresh, invalidate, register } = useRefresh(villageId);
  const canSecretReply =
    mySituation?.say.selectableMessageTypeList?.some(
      (t) => t.messageType.code === MessageType.SECRET_SAY,
    ) ?? false;

  const {
    reply,
    onReply,
    clearReply,
    sayPreview,
    sayError,
    saySubmitting,
    registerSayDone,
    onSayConfirm,
    onActionConfirm,
    onCreatorSayConfirm,
    onSayDetermine,
    onSayCancel,
  } = useSayFlow(villageId, invalidate, scrollToBottom);

  // 発言抽出。URL searchParams が正本 (共有 URL で再現できる)
  const [searchParams, setSearchParams] = useSearchParams();
  const filter = parseFilter(searchParams);
  const { page, setPage, isPaging, pageSize, resetToLatest } = useMessagePaging(dayParam, filter);
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

  const { onMessagesLoaded: onMessagesLoadedBase, hasNewMessage } = useMessageSync(
    villageId,
    dayParam,
    latestDay,
    currentDay,
    scrollToBottom,
    invalidate,
    showToast,
    sayPreview == null,
  );

  const pendingScroll = useRef(false);
  const onMessagesLoaded = useCallback(
    (content: Parameters<typeof onMessagesLoadedBase>[0]) => {
      onMessagesLoadedBase(content);
      if (pendingScroll.current) {
        pendingScroll.current = false;
        setTimeout(() => scrollToBottom(false), 0);
      }
    },
    [onMessagesLoadedBase, scrollToBottom],
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
      <VillageProvider value={village}>
        <RefreshContext.Provider value={register}>
          <div className={`px-[15px] ${largeText ? "text-[150%]" : ""}`}>
            {/* 村タイトル */}
            <div className="flex">
              <h1 className="my-[10.5px] flex-1 text-[1.125em]">
                {villageNumber(village.id)}. {village.name}
              </h1>
              <div className="my-[10.5px]">
                <XPostButton />
              </div>
            </div>
            <hr className="mt-[5px] mb-[10px] border-[#464545]" />

            <DayList currentDay={currentDay} onInfo={() => setInfoOpen(true)} />

            <MessageArea
              day={dayParam}
              filter={filter}
              page={page}
              setPage={setPage}
              isPaging={isPaging}
              pageSize={pageSize}
              onHashtagClick={onHashtagClick}
              onReply={mySituation?.say.isAvailableSay ? onReply : undefined}
              onSecret={canSecretReply ? onReply : undefined}
              onLoaded={onMessagesLoaded}
              confirmArea={
                <SayPreviewArea
                  preview={sayPreview}
                  submitting={saySubmitting}
                  onDetermine={onSayDetermine}
                  onCancel={onSayCancel}
                />
              }
            />
            <DayList currentDay={currentDay} onInfo={() => setInfoOpen(true)} />
            {!noAd && <AdSense slot="2768254717" className="mt-[15px]" />}
            <div id="bottom" />
            <hr className="mt-[5px] mb-[10px] border-[#464545]" />

            {situation != null && (
              <SituationPanel situation={situation} day={currentDay} spoiled={filter.spoiled} />
            )}

            <ActionPanels
              dayParam={dayParam}
              sayError={sayError}
              reply={reply}
              clearReply={clearReply}
              onSayConfirm={onSayConfirm}
              onActionConfirm={onActionConfirm}
              onCreatorSayConfirm={onCreatorSayConfirm}
              registerSayDone={registerSayDone}
              refresh={refresh}
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
            onRefresh={() => {
              resetToLatest();
              pendingScroll.current = true;
              if (dayParam != null) {
                navigate(`/village/${villageId}`);
              } else {
                void refresh();
              }
            }}
            hasNewMessage={hasNewMessage}
            onFilter={() => setFilterOpen(true)}
            filtering={isFiltering(filter)}
            onSettings={() => setSettingsOpen(true)}
            onInfo={() => setInfoOpen(true)}
          />
          <SettingsModal
            open={settingsOpen}
            onClose={() => setSettingsOpen(false)}
            mySituation={mySituation}
          />
          <VillageInfoModal
            open={infoOpen}
            onClose={() => setInfoOpen(false)}
            canModifySetting={mySituation?.creator.isAvailableModifySetting ?? false}
          />
          <InitialSkillModal
            mySituation={mySituation}
            suppressed={ageLimit != null && !ageLimitResolved}
          />
          <FilterModal
            open={filterOpen}
            onClose={() => setFilterOpen(false)}
            filter={filter}
            dayParam={dayParam}
            onApply={applyFilter}
            onApplyNewTab={applyFilterNewTab}
          />
          {ageLimit != null && (
            <AgeLimitModal ageLimit={ageLimit} onResolved={() => setAgeLimitResolved(true)} />
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
        </RefreshContext.Provider>
      </VillageProvider>
    </PageLayout>
  );
}
