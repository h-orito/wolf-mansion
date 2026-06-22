import { useEffect, useState } from "react";

import { MESSAGE_STYLES } from "~/components/ui/messageStyles";
import type { VillageMessageListContent } from "~/features/village/api";
import { useDisplaySettings } from "~/features/village/displaySettings";
import { EMPTY_FILTER, type MessageFilter } from "~/features/village/filter";
import { useVillageMessages } from "~/features/village/useMessages";
import { MessageCard, type ReplyDraft } from "./MessageCard";
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
  onHashtagClick?: (tag: string) => void;
  onReply?: (reply: ReplyDraft) => void;
  onSecret?: (reply: ReplyDraft) => void;
  /** 一覧取得のたびに呼ぶ (新着検知の基準更新用)。 */
  onLoaded: (content: VillageMessageListContent) => void;
  confirmArea?: React.ReactNode;
}) {
  // 日付指定 (`/day/{day}`) で開いたら 1 ページ目、最新日 URL なら最新ページを初期表示する
  const initialPage = (d: number | undefined): PageState =>
    d == null ? { pageNum: 1, isDispLatest: true } : { pageNum: 1, isDispLatest: false };
  const [page, setPage] = useState<PageState>(() => initialPage(day));

  const isPaging = useDisplaySettings((s) => s.isPaging);
  const pageSize = useDisplaySettings((s) => s.pageSize);

  // 日付遷移・抽出条件・ページ設定の変更で先頭ページに戻す (存在しないページを引かないため)
  const filterKey = JSON.stringify(filter);
  useEffect(() => {
    setPage(initialPage(day));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [day, filterKey, isPaging, pageSize]);

  const { data, isLoading } = useVillageMessages(villageId, {
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
