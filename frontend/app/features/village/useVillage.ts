import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useCallback, useEffect, useRef, useState } from "react";

import { useMe } from "~/features/auth/useMe";
import {
  fetchMyVillageSituation,
  fetchVillage,
  fetchVillageDebugInfo,
  fetchVillageInfo,
  fetchVillageSituation,
  postVillageUpdate,
} from "./api";
import { VILLAGE_MESSAGES_QUERY_KEY } from "./useMessages";

export const VILLAGE_QUERY_KEY = "village";
export const VILLAGE_SITUATION_QUERY_KEY = "village-situation";
export const MY_VILLAGE_SITUATION_QUERY_KEY = "my-village-situation";

/** 村詳細を取得する。 */
export function useVillage(id: number) {
  return useQuery({
    queryKey: [VILLAGE_QUERY_KEY, id],
    queryFn: () => fetchVillage(id),
    retry: false,
  });
}

/** 村状況を取得する。ログイン状態で視点マスクが変わるため me と合わせてキャッシュを分ける。 */
export function useVillageSituation(
  id: number,
  day: number | undefined,
  viewerName: string | null,
) {
  return useQuery({
    queryKey: [VILLAGE_SITUATION_QUERY_KEY, id, day ?? "latest", viewerName],
    queryFn: () => fetchVillageSituation(id, day),
    retry: false,
  });
}

/**
 * 参加者本人の状態を取得する。未ログインの間は呼ばない。
 * ログイン中のはずなのに 401 (access 切れ + refresh も失敗) になった場合、このクエリの
 * error がセッション失効の検知点になる (apiFetch が refresh を試した後の 401 のため)。
 */
export function useMyVillageSituation(id: number, day: number | undefined) {
  const { me, isLoading } = useMe();
  return useQuery({
    queryKey: [MY_VILLAGE_SITUATION_QUERY_KEY, id, day ?? "latest", me?.name],
    queryFn: () => fetchMyVillageSituation(id, day),
    enabled: !isLoading && me != null,
    retry: false,
    refetchInterval: 5 * 60 * 1000,
  });
}

/** 村情報モーダル用の設定表示。モーダルを開いたときだけ取得する。 */
export function useVillageInfo(id: number, enabled: boolean) {
  return useQuery({
    queryKey: ["village-info", id],
    queryFn: () => fetchVillageInfo(id),
    enabled,
    retry: false,
  });
}

/** デバッグ情報を取得する。debug 無効時は isDebugMode=false が返る。 */
export function useVillageDebugInfo(id: number) {
  return useQuery({
    queryKey: ["village-debug", id],
    queryFn: () => fetchVillageDebugInfo(id),
    retry: false,
  });
}

/** 村系クエリをまとめて無効化する (日付更新・手動更新時)。発言一覧も含む。 */
export function useInvalidateVillage(id: number) {
  const queryClient = useQueryClient();
  return useCallback(
    () =>
      Promise.all([
        queryClient.invalidateQueries({ queryKey: [VILLAGE_QUERY_KEY, id] }),
        queryClient.invalidateQueries({ queryKey: [VILLAGE_SITUATION_QUERY_KEY, id] }),
        queryClient.invalidateQueries({ queryKey: [MY_VILLAGE_SITUATION_QUERY_KEY, id] }),
        queryClient.invalidateQueries({ queryKey: [VILLAGE_MESSAGES_QUERY_KEY, id] }),
      ]),
    [queryClient, id],
  );
}

const POLLING_INTERVAL_MS = 30_000;

/**
 * 村ポーリング。30 秒ごとに村状態の更新 (最終アクセス・日付更新の駆動) を行い、
 * 既知の最新日より新しい日付を検知したら村系クエリを無効化して true を返す。
 * 初回マウント直後にも 1 回実行する。失敗 (ネットワーク断など) は次回ポーリングに任せる。
 */
export function useVillagePolling(id: number, knownLatestDay: number | undefined): boolean {
  const invalidate = useInvalidateVillage(id);
  const [daychangeDetected, setDaychangeDetected] = useState(false);
  // ポーリングの比較基準。表示中データの最新日に追従する
  const latestDayRef = useRef<number | undefined>(knownLatestDay);
  latestDayRef.current = knownLatestDay;

  useEffect(() => {
    let active = true;
    const poll = async () => {
      try {
        const response = await postVillageUpdate(id);
        if (!active) return;
        const known = latestDayRef.current;
        if (known != null && response.latestDay > known) {
          setDaychangeDetected(true);
          await invalidate();
        }
      } catch {
        // 次回ポーリングに任せる
      }
    };
    poll();
    const timer = setInterval(poll, POLLING_INTERVAL_MS);
    return () => {
      active = false;
      clearInterval(timer);
    };
  }, [id, invalidate]);

  return daychangeDetected;
}
