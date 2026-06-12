import { useQuery } from "@tanstack/react-query";
import { useEffect, useRef, useState } from "react";

import { useMe } from "~/features/auth/useMe";
import {
  fetchLatestMessageDatetime,
  fetchVillageMessages,
  fetchVillageParticipants,
  type VillageMessageSearch,
} from "./api";

export const VILLAGE_MESSAGES_QUERY_KEY = "village-messages";
export const VILLAGE_PARTICIPANTS_QUERY_KEY = "village-participants";

/** 発言一覧を取得する。可視範囲がログイン状態で変わるため me と合わせてキャッシュを分ける。 */
export function useVillageMessages(id: number, search: VillageMessageSearch) {
  const { me, isLoading } = useMe();
  return useQuery({
    queryKey: [VILLAGE_MESSAGES_QUERY_KEY, id, search, me?.name ?? null],
    queryFn: () => fetchVillageMessages(id, search),
    // ログイン判定前に匿名視点で取得すると判定後に二重取得になるため確定を待つ
    enabled: !isLoading,
    retry: false,
  });
}

/** 参加者の正体一覧 (エピローグ以降のみ)。 */
export function useVillageParticipants(id: number, enabled: boolean) {
  return useQuery({
    queryKey: [VILLAGE_PARTICIPANTS_QUERY_KEY, id],
    queryFn: () => fetchVillageParticipants(id),
    enabled,
    retry: false,
  });
}

const POLLING_INTERVAL_MS = 30_000;

/**
 * 新着発言の検知。最新ページを表示している間、30 秒ごとに最新発言日時を取得し、
 * 表示済みの日時より新しければ true を返す (更新ボタンの点滅用)。
 * 表示済み日時が更新されたら (= 再読み込みされたら) フラグを下ろす。
 */
export function useNewMessageDetector(
  id: number,
  day: number | undefined,
  displayedLatestDatetime: string | null | undefined,
  enabled: boolean,
): boolean {
  const [hasNewMessage, setHasNewMessage] = useState(false);
  const displayedRef = useRef(displayedLatestDatetime);

  useEffect(() => {
    displayedRef.current = displayedLatestDatetime;
    setHasNewMessage(false);
  }, [displayedLatestDatetime]);

  useEffect(() => {
    if (!enabled) {
      setHasNewMessage(false);
      return;
    }
    let active = true;
    const poll = async () => {
      try {
        const latest = await fetchLatestMessageDatetime(id, { day });
        if (!active) return;
        const displayed = displayedRef.current;
        if (displayed != null && latest !== "0" && Number(latest) > Number(displayed)) {
          setHasNewMessage(true);
        }
      } catch {
        // 次回ポーリングに任せる
      }
    };
    const timer = setInterval(poll, POLLING_INTERVAL_MS);
    return () => {
      active = false;
      clearInterval(timer);
    };
  }, [id, day, enabled]);

  return hasNewMessage;
}
