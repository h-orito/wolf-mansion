import { useCallback, useEffect, useState } from "react";

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
) {
  const [loadedMessages, setLoadedMessages] = useState<VillageMessageListContent | null>(null);

  const onMessagesLoaded = useCallback(
    (content: VillageMessageListContent) => {
      setLoadedMessages(content);
      if (window.location.hash === "#bottom") {
        window.history.replaceState(null, "", window.location.pathname + window.location.search);
        setTimeout(() => scrollToBottom(false), 0);
      }
    },
    [scrollToBottom],
  );

  const isInLatestPage =
    latestDay != null &&
    currentDay === latestDay &&
    (loadedMessages == null || loadedMessages.isDispLatest || !loadedMessages.isExistNextPage);

  const hasNewMessage = useNewMessageDetector(
    villageId,
    dayParam,
    loadedMessages?.latestMessageDatetime,
    isInLatestPage,
  );

  const autoReload = useDisplaySettings((s) => s.autoReload);
  useEffect(() => {
    if (hasNewMessage && autoReload && canAutoReload) {
      void invalidate();
      showToast("最新発言を読み込みました");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [hasNewMessage, autoReload, canAutoReload]);

  return { onMessagesLoaded, hasNewMessage };
}
