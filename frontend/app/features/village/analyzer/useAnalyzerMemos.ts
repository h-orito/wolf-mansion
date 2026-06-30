import { useCallback, useEffect, useRef, useState } from "react";

import type { VillageFootstepContent } from "~/features/village/api";
import { fetchPlayerMemo, savePlayerMemo } from "./firebase";
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
  footstepList: VillageFootstepContent[],
): DailyFootstepMemo[] {
  return footstepList.map((fs) => {
    const existing = saved?.dailyFootstepMemos?.find((dfm) => dfm.day === fs.day);
    if (existing) return existing;
    const footstepTexts = fs.footstep.split("\n").filter((s) => s.trim() !== "");
    return {
      day: fs.day,
      footsteps: footstepTexts.map(
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
  save: () => Promise<void>;
};

export function useAnalyzerMemos(
  playerId: number | null,
  villageId: number,
  participantIds: number[],
  days: number[],
  footstepList: VillageFootstepContent[],
): AnalyzerMemosState {
  const [loaded, setLoaded] = useState(false);
  const [participantMemos, setParticipantMemos] = useState<ParticipantMemo[]>([]);
  const [dailyMemos, setDailyMemos] = useState<DailyMemo[]>([]);
  const [dailyFootstepMemos, setDailyFootstepMemosState] = useState<DailyFootstepMemo[]>([]);
  const [wholeMemo, setWholeMemoState] = useState("");

  const saveTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  useEffect(() => {
    if (!playerId) return;
    let cancelled = false;
    fetchPlayerMemo(playerId, villageId).then((saved) => {
      if (cancelled) return;
      setParticipantMemos(initParticipantMemos(saved, participantIds));
      setDailyMemos(initDailyMemos(saved, days));
      setDailyFootstepMemosState(initDailyFootstepMemos(saved, footstepList));
      setWholeMemoState(saved?.wholeMemo ?? "");
      setLoaded(true);
    });
    return () => {
      cancelled = true;
    };
  }, [playerId, villageId, participantIds, days, footstepList]);

  const debouncedSave = useCallback(() => {
    if (saveTimerRef.current) clearTimeout(saveTimerRef.current);
    saveTimerRef.current = setTimeout(() => {
      saveTimerRef.current = null;
    }, 2000);
  }, []);

  const doSave = useCallback(async () => {
    if (!playerId) return;
    await savePlayerMemo(playerId, villageId, {
      participantMemos,
      dailyMemos,
      dailyFootstepMemos,
      wholeMemo,
    });
  }, [playerId, villageId, participantMemos, dailyMemos, dailyFootstepMemos, wholeMemo]);

  const setParticipantMemoFn = useCallback(
    (id: number, memo: ParticipantMemo) => {
      setParticipantMemos((prev) => prev.map((m) => (m.participantId === id ? memo : m)));
      debouncedSave();
    },
    [debouncedSave],
  );

  const setDailyMemoFn = useCallback(
    (day: number, memo: string) => {
      setDailyMemos((prev) => prev.map((m) => (m.day === day ? { day, memo } : m)));
      debouncedSave();
    },
    [debouncedSave],
  );

  const setDailyFootstepMemosFn = useCallback(
    (day: number, footsteps: DayFootstep[]) => {
      setDailyFootstepMemosState((prev) =>
        prev.map((m) => (m.day === day ? { day, footsteps } : m)),
      );
      debouncedSave();
    },
    [debouncedSave],
  );

  const setWholeMemoFn = useCallback(
    (memo: string) => {
      setWholeMemoState(memo);
      debouncedSave();
    },
    [debouncedSave],
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
    save: doSave,
  };
}
