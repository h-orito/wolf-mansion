import { useCallback, useEffect } from "react";
import { useSearchParams } from "react-router";

import type { VillageMessageListContent } from "~/features/village/api";
import { useRandomKeywordList } from "~/features/random-keywords/useRandomKeywords";
import { applyFilterToParams, EMPTY_FILTER, type MessageFilter } from "~/features/village/filter";
import { useVillageId } from "~/features/village/VillageContext";
import { useMyVillageSituation } from "~/features/village/useVillage";
import { useVillageMessages } from "~/features/village/useMessages";
import { MessageType } from "~/features/village/components/message/messageType";
import { bubbleClass } from "~/features/village/components/message/message";
import { MessageCard, type ReplyDraft } from "./MessageCard";
import { MessagePagination, type PageState } from "./MessagePagination";

function Announce({ text }: { text: string }) {
  return (
    <div className={`mb-[20px] ${bubbleClass("message-announce")}`}>
      {text.split("\n").map((line, i) => (
        <span key={i}>
          {i > 0 && <br />}
          {line}
        </span>
      ))}
    </div>
  );
}

/**
 * 発言ログ。日別の発言一覧 + ページング + 一覧末尾のアナウンス (突然死候補 / 村状態 / コミット状況)。
 * 取得した一覧の最新発言日時は新着検知の基準として親へ渡す。
 */
export function MessageArea({
  day,
  filter = EMPTY_FILTER,
  page,
  setPage,
  isPaging,
  pageSize,
  onReply,
  onLoaded,
  confirmArea,
}: {
  day: number | undefined;
  filter?: MessageFilter;
  page: PageState;
  setPage: (page: PageState) => void;
  isPaging: boolean;
  pageSize: number;
  onReply?: (reply: ReplyDraft) => void;
  onLoaded: (content: VillageMessageListContent) => void;
  confirmArea?: React.ReactNode;
}) {
  const villageId = useVillageId();
  const randomKeywords = useRandomKeywordList();
  const { data: mySituation } = useMyVillageSituation(villageId, day);
  const canReply = onReply != null && (mySituation?.say.isAvailableSay ?? false);
  const canSecretReply =
    onReply != null &&
    (mySituation?.say.selectableMessageTypeList?.some(
      (t) => t.messageType.code === MessageType.SECRET_SAY,
    ) ??
      false);
  const [, setSearchParams] = useSearchParams();
  const onHashtagClick = useCallback(
    (tag: string) => {
      setSearchParams((prev) => applyFilterToParams(prev, { ...EMPTY_FILTER, keywords: tag }));
    },
    [setSearchParams],
  );
  const { data, isLoading, isFetching } = useVillageMessages(villageId, {
    day,
    pageSize: isPaging ? pageSize : undefined,
    pageNum: isPaging && !page.isDispLatest ? page.pageNum : undefined,
    isPaging,
    isDispLatest: isPaging ? page.isDispLatest : undefined,
    participantIds: filter.participantIds.length > 0 ? filter.participantIds : undefined,
    toParticipantIds: filter.toParticipantIds.length > 0 ? filter.toParticipantIds : undefined,
    types: filter.types.length > 0 ? filter.types : undefined,
    keywords: filter.keywords !== "" ? filter.keywords : undefined,
  });

  useEffect(() => {
    if (data != null) onLoaded(data);
  }, [data, onLoaded]);

  if (isLoading || data == null) {
    return <div className="text-gray-400">読み込み中...</div>;
  }

  const showOverlay = isFetching && !isLoading;

  return (
    <div className="relative">
      {showOverlay && <div className="absolute inset-0 z-10 bg-wm-base/60" />}
      <MessagePagination content={data} onChange={setPage} />
      {(data.messageList ?? []).map((message, index) => (
        <MessageCard
          key={`${message.messageType}-${message.messageNumber ?? index}`}
          message={message}
          randomKeywords={randomKeywords}
          spoiled={filter.spoiled}
          onHashtagClick={onHashtagClick}
          onReply={canReply ? onReply : undefined}
          onSecret={canSecretReply ? onReply : undefined}
        />
      ))}
      {confirmArea}
      {/* 発言確認・確定時のスクロール下端。メッセージエリア外の要素 (DayList・広告) を画面に入れない */}
      <div id="message-bottom" />
      {data.suddenlyDeathMessage != null && <Announce text={data.suddenlyDeathMessage} />}
      {data.villageStatusMessage != null && <Announce text={data.villageStatusMessage} />}
      {data.commitStatusMessage != null && <Announce text={data.commitStatusMessage} />}
      <MessagePagination content={data} onChange={setPage} />
    </div>
  );
}
