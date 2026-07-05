import { useCallback, useEffect, useRef, useState } from "react";

import type { VillageMessageListContent } from "~/features/village/api";
import { useDisplaySettings } from "~/features/village/displaySettings";
import { useNewMessageDetector } from "~/features/village/useMessages";

export function useMessageSync(
  villageId: number,
  dayParam: number | undefined,
  latestDay: number | undefined,
  currentDay: number,
  scrollToBottom: (smooth?: boolean) => void,
  invalidate: () => Promise<unknown>,
  showToast: (msg: string) => void,
  canAutoReload: boolean,
  filtering: boolean,
) {
  const [loadedMessages, setLoadedMessages] = useState<VillageMessageListContent | null>(null);
  const hashScrolled = useRef(false);

  const isInLatestPage =
    latestDay != null &&
    currentDay === latestDay &&
    (loadedMessages == null || loadedMessages.isDispLatest || !loadedMessages.isExistNextPage);

  // 抽出中は表示リストの最終日時が村全体の最新より古いため、検知基準に使わない
  const { hasNewMessage, resetNewMessage } = useNewMessageDetector(
    villageId,
    dayParam,
    filtering ? null : loadedMessages?.latestMessageDatetime,
    isInLatestPage,
  );

  const onMessagesLoaded = useCallback(
    (content: VillageMessageListContent) => {
      setLoadedMessages(content);
      resetNewMessage();
      if (!hashScrolled.current && window.location.hash === "#bottom") {
        hashScrolled.current = true;
        setTimeout(() => scrollToBottom(false), 0);
      }
    },
    [scrollToBottom, resetNewMessage],
  );

  const autoReload = useDisplaySettings((s) => s.autoReload);
  useEffect(() => {
    // 抽出中は自動リロードしない (点滅で新着を知らせ、手動更新に委ねる)
    if (hasNewMessage && autoReload && canAutoReload && !filtering) {
      void invalidate();
      resetNewMessage();
      showToast("最新発言を読み込みました");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [hasNewMessage, autoReload, canAutoReload, filtering]);

  return { onMessagesLoaded, hasNewMessage, resetNewMessage };
}
