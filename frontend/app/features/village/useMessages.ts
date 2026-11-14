import { useQuery } from "@tanstack/react-query";
import { useCallback, useEffect, useRef, useState } from "react";

import { useMe } from "~/features/auth/useMe";
import { fetchLatestMessageDatetime, fetchVillageMessages, type VillageMessageSearch } from "./api";
import { usePollingActive } from "./usePollingActive";

export const VILLAGE_MESSAGES_QUERY_KEY = "village-messages";

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

const POLLING_INTERVAL_MS = 30_000;

/**
 * 新着発言の検知。最新ページを表示している間、30 秒ごとに村全体 (抽出非適用・視点反映) の
 * 最新発言日時を取得し、前回把握した日時より新しければ true を返す (更新ボタンの点滅用)。
 *
 * 比較基準は「前回把握した村全体の最新日時」。抽出中は表示リストの最終日時が村全体より
 * 古いまま止まるため、表示リスト基準で比較すると新着がなくても検知し続けてしまう。
 * `baselineDatetime` は抽出していないとき (表示リスト = 村全体) だけ渡し、
 * ポーリングを待たず一覧ロード時点の日時を基準にできるようにする。
 * フラグは一覧のロード完了時に `resetNewMessage` で下ろす。
 *
 * 終了・廃村の村とタブが非表示の間は問い合わせない。非表示の間も基準日時は保持し、
 * 表示に戻った直後に 1 回問い合わせて、非表示の間の新着を検知する。
 */
export function useNewMessageDetector(
  id: number,
  day: number | undefined,
  baselineDatetime: string | null | undefined,
  enabled: boolean,
  isFinished: boolean | undefined,
): { hasNewMessage: boolean; resetNewMessage: () => void } {
  const [hasNewMessage, setHasNewMessage] = useState(false);
  const lastKnownRef = useRef<string | null>(null);
  const active = usePollingActive(isFinished);
  // 止めていた間に新着があり得るため、再開時は間隔を待たずに問い合わせる
  const pausedRef = useRef(false);

  const resetNewMessage = useCallback(() => setHasNewMessage(false), []);

  useEffect(() => {
    if (baselineDatetime != null) {
      lastKnownRef.current = baselineDatetime;
      setHasNewMessage(false);
    }
  }, [baselineDatetime]);

  useEffect(() => {
    if (!enabled) {
      setHasNewMessage(false);
      lastKnownRef.current = null;
      return;
    }
    if (!active) {
      // 村の読み込み待ちは止めていた扱いにしない (読み込み直後に余分な問い合わせをしない)
      if (isFinished === false) pausedRef.current = true;
      return;
    }
    let mounted = true;
    const poll = async () => {
      try {
        const latest = await fetchLatestMessageDatetime(id, { day });
        if (!mounted || latest === "0") return;
        const lastKnown = lastKnownRef.current;
        if (lastKnown == null) {
          // 基準が未確定の間 (抽出中の初回など) は保存のみ。次回以降の増分で検知する
          lastKnownRef.current = latest;
          return;
        }
        if (Number(latest) > Number(lastKnown)) {
          lastKnownRef.current = latest;
          setHasNewMessage(true);
        }
      } catch {
        // 次回ポーリングに任せる
      }
    };
    if (pausedRef.current) {
      pausedRef.current = false;
      void poll();
    }
    const timer = setInterval(poll, POLLING_INTERVAL_MS);
    return () => {
      mounted = false;
      clearInterval(timer);
    };
  }, [id, day, enabled, active, isFinished]);

  return { hasNewMessage, resetNewMessage };
}
