import { useCallback, useEffect, useRef, useState } from "react";

import { fetchAnalyzerMemo, saveAnalyzerMemo } from "./analyzerApi";
import type {
  DailyFootstepMemo,
  DailyMemo,
  DayFootstep,
  ParticipantMemo,
  PlayerMemo,
} from "./types";

const FOOTSTEP_COLORS = ["ff0000", "00ff00", "00aaff", "ffaa00", "ffff00", "ffffff", "ff00ff"];

function getColor(index: number): string {
  return FOOTSTEP_COLORS[index % FOOTSTEP_COLORS.length];
}

function initParticipantMemos(
  saved: PlayerMemo | null,
  participantIds: number[],
): ParticipantMemo[] {
  return participantIds.map((id) => {
    const existing = saved?.participantMemos?.find((pm) => pm.participantId === id);
    return existing ?? { participantId: id, memo: "", color: "ffffff" };
  });
}

function initDailyMemos(saved: PlayerMemo | null, days: number[]): DailyMemo[] {
  return days.map((day) => {
    const existing = saved?.dailyMemos?.find((dm) => dm.day === day);
    return existing ?? { day, memo: "" };
  });
}

function initDailyFootstepMemos(
  saved: PlayerMemo | null,
  rawFootstepsByDay: { day: number; footsteps: string[] }[],
): DailyFootstepMemo[] {
  return rawFootstepsByDay.map((dayData) => {
    const existing = saved?.dailyFootstepMemos?.find((dfm) => dfm.day === dayData.day);
    if (existing) return existing;
    return {
      day: dayData.day,
      footsteps: dayData.footsteps.map(
        (text, i): DayFootstep => ({
          footstep: text,
          color: getColor(i),
          show: true,
          memo: "",
        }),
      ),
    };
  });
}

export type AnalyzerMemosState = {
  loaded: boolean;
  participantMemos: ParticipantMemo[];
  dailyMemos: DailyMemo[];
  dailyFootstepMemos: DailyFootstepMemo[];
  wholeMemo: string;
  setParticipantMemo: (id: number, memo: ParticipantMemo) => void;
  setDailyMemo: (day: number, memo: string) => void;
  setDailyFootstepMemos: (day: number, footsteps: DayFootstep[]) => void;
  setWholeMemo: (memo: string) => void;
  flush: () => Promise<void>;
};

export function useAnalyzerMemos(
  playerId: number | null,
  villageId: number,
  participantIds: number[],
  rawFootstepsByDay: { day: number; footsteps: string[] }[],
): AnalyzerMemosState {
  const [loaded, setLoaded] = useState(false);
  const [participantMemos, setParticipantMemos] = useState<ParticipantMemo[]>([]);
  const [dailyMemos, setDailyMemos] = useState<DailyMemo[]>([]);
  const [dailyFootstepMemos, setDailyFootstepMemosState] = useState<DailyFootstepMemo[]>([]);
  const [wholeMemo, setWholeMemoState] = useState("");

  const participantIdsRef = useRef(participantIds);
  participantIdsRef.current = participantIds;
  const rawFootstepsRef = useRef(rawFootstepsByDay);
  rawFootstepsRef.current = rawFootstepsByDay;

  const stateRef = useRef({ participantMemos, dailyMemos, dailyFootstepMemos, wholeMemo });
  stateRef.current = { participantMemos, dailyMemos, dailyFootstepMemos, wholeMemo };

  const saveTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  // アンマウント時・村切替時に、保存待ちの変更があればフラッシュする
  useEffect(() => {
    return () => {
      if (!saveTimerRef.current) return;
      clearTimeout(saveTimerRef.current);
      saveTimerRef.current = null;
      if (playerId) void saveAnalyzerMemo(villageId, stateRef.current).catch(() => {});
    };
  }, [playerId, villageId]);

  const days = rawFootstepsByDay.map((d) => d.day);

  useEffect(() => {
    if (!playerId) return;
    let cancelled = false;
    fetchAnalyzerMemo(villageId)
      .then((saved) => {
        if (cancelled) return;
        setParticipantMemos(initParticipantMemos(saved, participantIdsRef.current));
        setDailyMemos(
          initDailyMemos(
            saved,
            rawFootstepsRef.current.map((d) => d.day),
          ),
        );
        setDailyFootstepMemosState(initDailyFootstepMemos(saved, rawFootstepsRef.current));
        setWholeMemoState(saved?.wholeMemo ?? "");
        setLoaded(true);
      })
      .catch(() => {
        if (cancelled) return;
        setParticipantMemos(initParticipantMemos(null, participantIdsRef.current));
        setDailyMemos(
          initDailyMemos(
            null,
            rawFootstepsRef.current.map((d) => d.day),
          ),
        );
        setDailyFootstepMemosState(initDailyFootstepMemos(null, rawFootstepsRef.current));
        setLoaded(true);
      });
    return () => {
      cancelled = true;
    };
  }, [playerId, villageId, days.length]);

  const scheduleSave = useCallback(() => {
    if (saveTimerRef.current) clearTimeout(saveTimerRef.current);
    saveTimerRef.current = setTimeout(async () => {
      saveTimerRef.current = null;
      if (!playerId) return;
      await saveAnalyzerMemo(villageId, stateRef.current).catch(() => {});
    }, 2000);
  }, [playerId, villageId]);

  // 保存待ちの変更があれば即座に保存する（なければ何もしない）
  const flush = useCallback(async () => {
    if (!saveTimerRef.current) return;
    clearTimeout(saveTimerRef.current);
    saveTimerRef.current = null;
    if (!playerId) return;
    await saveAnalyzerMemo(villageId, stateRef.current).catch(() => {});
  }, [playerId, villageId]);

  const setParticipantMemoFn = useCallback(
    (id: number, memo: ParticipantMemo) => {
      setParticipantMemos((prev) => prev.map((m) => (m.participantId === id ? memo : m)));
      scheduleSave();
    },
    [scheduleSave],
  );

  const setDailyMemoFn = useCallback(
    (day: number, memo: string) => {
      setDailyMemos((prev) => prev.map((m) => (m.day === day ? { day, memo } : m)));
      scheduleSave();
    },
    [scheduleSave],
  );

  const setDailyFootstepMemosFn = useCallback(
    (day: number, footsteps: DayFootstep[]) => {
      setDailyFootstepMemosState((prev) =>
        prev.map((m) => (m.day === day ? { day, footsteps } : m)),
      );
      scheduleSave();
    },
    [scheduleSave],
  );

  const setWholeMemoFn = useCallback(
    (memo: string) => {
      setWholeMemoState(memo);
      scheduleSave();
    },
    [scheduleSave],
  );

  return {
    loaded,
    participantMemos,
    dailyMemos,
    dailyFootstepMemos,
    wholeMemo,
    setParticipantMemo: setParticipantMemoFn,
    setDailyMemo: setDailyMemoFn,
    setDailyFootstepMemos: setDailyFootstepMemosFn,
    setWholeMemo: setWholeMemoFn,
    flush,
  };
}
