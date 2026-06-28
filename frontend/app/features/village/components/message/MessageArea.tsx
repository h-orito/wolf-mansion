import { useEffect } from "react";

import type { components } from "~/api/types";
import { MESSAGE_STYLES } from "~/components/ui/messageStyles";
import type { VillageMessageListContent } from "~/features/village/api";
import { EMPTY_FILTER, type MessageFilter } from "~/features/village/filter";
import { useVillageMessages } from "~/features/village/useMessages";
import { MessageCard, type ReplyDraft } from "./MessageCard";
import { MessagePagination, type PageState } from "./MessagePagination";

function Announce({ text }: { text: string }) {
  return (
    <div
      className={`message mb-[20px] rounded-[5px] border p-[9px] break-words break-all ${MESSAGE_STYLES["message-announce"]}`}
    >
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
  villageId,
  day,
  randomKeywords,
  filter = EMPTY_FILTER,
  allParticipants,
  page,
  setPage,
  isPaging,
  pageSize,
  onHashtagClick,
  onReply,
  onSecret,
  onLoaded,
  confirmArea,
}: {
  villageId: number;
  day: number | undefined;
  randomKeywords: string[];
  /** 発言抽出の条件 (URL searchParams 由来)。 */
  filter?: MessageFilter;
  allParticipants?: components["schemas"]["VillageParticipantView"][];
  page: PageState;
  setPage: (page: PageState) => void;
  isPaging: boolean;
  pageSize: number;
  onHashtagClick?: (tag: string) => void;
  onReply?: (reply: ReplyDraft) => void;
  onSecret?: (reply: ReplyDraft) => void;
  /** 一覧取得のたびに呼ぶ (新着検知の基準更新用)。 */
  onLoaded: (content: VillageMessageListContent) => void;
  confirmArea?: React.ReactNode;
}) {
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
      {showOverlay && <div className="absolute inset-0 z-10 bg-[#222222]/60" />}
      <MessagePagination content={data} onChange={setPage} />
      {(data.messageList ?? []).map((message, index) => (
        <MessageCard
          key={`${message.messageType}-${message.messageNumber ?? index}`}
          villageId={villageId}
          message={message}
          randomKeywords={randomKeywords}
          spoiled={filter.spoiled}
          allParticipants={allParticipants}
          onHashtagClick={onHashtagClick}
          onReply={onReply}
          onSecret={onSecret}
        />
      ))}
      {confirmArea}
      {data.suddenlyDeathMessage != null && <Announce text={data.suddenlyDeathMessage} />}
      {data.villageStatusMessage != null && <Announce text={data.villageStatusMessage} />}
      {data.commitStatusMessage != null && <Announce text={data.commitStatusMessage} />}
      <MessagePagination content={data} onChange={setPage} />
    </div>
  );
}
