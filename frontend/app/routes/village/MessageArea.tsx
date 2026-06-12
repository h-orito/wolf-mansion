import { useEffect, useState } from "react";

import { MESSAGE_STYLES } from "~/components/ui/messageStyles";
import type { VillageMessageListContent } from "~/features/village/api";
import { EMPTY_FILTER, type MessageFilter } from "~/features/village/filter";
import { useVillageMessages } from "~/features/village/useMessages";
import { MessageCard } from "./MessageCard";
import { MessagePagination, type PageState } from "./MessagePagination";

function Announce({ text }: { text: string }) {
  return (
    <div
      className={`message mb-[20px] rounded-[5px] border p-[9px] break-words ${MESSAGE_STYLES["message-announce"]}`}
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
  onHashtagClick,
  onLoaded,
}: {
  villageId: number;
  day: number | undefined;
  randomKeywords: string[];
  /** 発言抽出の条件 (URL searchParams 由来)。 */
  filter?: MessageFilter;
  onHashtagClick?: (tag: string) => void;
  /** 一覧取得のたびに呼ぶ (新着検知の基準更新用)。 */
  onLoaded: (content: VillageMessageListContent) => void;
}) {
  // 日付指定 (`/day/{day}`) で開いたら 1 ページ目、最新日 URL なら最新ページを初期表示する
  const initialPage = (d: number | undefined): PageState =>
    d == null ? { pageNum: 1, isDispLatest: true } : { pageNum: 1, isDispLatest: false };
  const [page, setPage] = useState<PageState>(() => initialPage(day));

  useEffect(() => {
    setPage(initialPage(day));
  }, [day]);

  const { data, isLoading } = useVillageMessages(villageId, {
    day,
    pageSize: 30,
    pageNum: page.isDispLatest ? undefined : page.pageNum,
    isPaging: true,
    isDispLatest: page.isDispLatest,
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

  return (
    <div>
      <MessagePagination content={data} onChange={setPage} />
      {(data.messageList ?? []).map((message, index) => (
        <MessageCard
          key={`${message.messageType}-${message.messageNumber ?? index}`}
          villageId={villageId}
          message={message}
          randomKeywords={randomKeywords}
          spoiled={filter.spoiled}
          onHashtagClick={onHashtagClick}
        />
      ))}
      {data.suddenlyDeathMessage != null && <Announce text={data.suddenlyDeathMessage} />}
      {data.villageStatusMessage != null && <Announce text={data.villageStatusMessage} />}
      {data.commitStatusMessage != null && <Announce text={data.commitStatusMessage} />}
      <MessagePagination content={data} onChange={setPage} />
    </div>
  );
}
