import { useCallback, useMemo, useState } from "react";

import type { VillageSituationView } from "~/features/village/api";
import type { DayFootstep } from "~/features/village/analyzer/types";
import { Button } from "~/components/ui/Button";
import { useMe } from "~/features/auth/useMe";
import { useVillageContext } from "~/features/village/VillageContext";
import { useAnalyzerMemos } from "~/features/village/analyzer/useAnalyzerMemos";
import { AnalyzerFootsteps } from "./AnalyzerFootsteps";
import { AnalyzerMemos } from "./AnalyzerMemos";
import { AnalyzerRoomGrid } from "./AnalyzerRoomGrid";
import { ParticipantMemoModal } from "./ParticipantMemoModal";

type SubTab = "room" | "footstep" | "memo";

export function AnalyzerTab({ situation, day }: { situation: VillageSituationView; day: number }) {
  const { me } = useMe();
  const village = useVillageContext();

  const allParticipants = useMemo(
    () => [...village.participants.list, ...village.spectators.list],
    [village],
  );
  const participantIds = useMemo(() => allParticipants.map((p) => p.id), [allParticipants]);
  const days = useMemo(
    () =>
      village.days.list
        .map((d) => d.day)
        .filter((d) => d >= 1)
        .sort((a, b) => a - b),
    [village],
  );

  const footstepList = useMemo(() => situation.footstepList ?? [], [situation.footstepList]);

  const {
    loaded,
    participantMemos,
    dailyMemos,
    dailyFootstepMemos,
    wholeMemo,
    setParticipantMemo,
    setDailyMemo,
    setDailyFootstepMemos,
    setWholeMemo,
    save,
  } = useAnalyzerMemos(me?.playerId ?? null, village.id, participantIds, days, footstepList);

  const [activeSubTab, setActiveSubTab] = useState<SubTab>("room");
  const [memoParticipantId, setMemoParticipantId] = useState<number | null>(null);

  const hasRooms = situation.roomAssignedRowList != null && day > 0;
  const hasFootsteps = footstepList.length > 0;

  const subTabs: { key: SubTab; label: string }[] = [
    ...(hasRooms ? [{ key: "room" as const, label: "部屋" }] : []),
    ...(hasFootsteps ? [{ key: "footstep" as const, label: "足音" }] : []),
    { key: "memo", label: "メモ" },
  ];

  const currentDayFootsteps = useMemo(() => {
    const dfm = dailyFootstepMemos.find((m) => m.day === day);
    return dfm?.footsteps ?? [];
  }, [dailyFootstepMemos, day]);

  const onFootstepsChange = useCallback(
    (updated: DayFootstep[]) => {
      setDailyFootstepMemos(day, updated);
    },
    [setDailyFootstepMemos, day],
  );

  const openMemoParticipant = useMemo(
    () =>
      memoParticipantId != null
        ? (allParticipants.find((p) => p.id === memoParticipantId) ?? null)
        : null,
    [memoParticipantId, allParticipants],
  );
  const openMemoData = useMemo(
    () =>
      memoParticipantId != null
        ? (participantMemos.find((m) => m.participantId === memoParticipantId) ?? null)
        : null,
    [memoParticipantId, participantMemos],
  );

  if (!me) {
    return (
      <p className="py-[10px] text-village-sm text-gray-400">
        ログインすると推理補助が利用できます
      </p>
    );
  }

  if (!loaded) {
    return <p className="py-[10px] text-village-sm text-gray-400">読み込み中...</p>;
  }

  return (
    <div className="pt-[10px] pb-[10px]">
      <div className="mb-[8px] flex flex-wrap items-center gap-[4px]">
        {subTabs.map((tab) => (
          <button
            key={tab.key}
            type="button"
            onClick={() => setActiveSubTab(tab.key)}
            className={`rounded px-[10px] py-[4px] text-village-sm ${
              activeSubTab === tab.key
                ? "bg-[#00bc8c] text-white"
                : "cursor-pointer border border-[#464545] bg-[#303030] text-gray-300 hover:bg-[#404040]"
            }`}
          >
            {tab.label}
          </button>
        ))}
        <Button variant="default" size="xs" onClick={() => save()} className="ml-auto">
          保存
        </Button>
      </div>

      {activeSubTab === "room" && hasRooms && (
        <AnalyzerRoomGrid
          rows={situation.roomAssignedRowList!}
          footsteps={currentDayFootsteps}
          participantMemos={participantMemos}
          onParticipantClick={setMemoParticipantId}
        />
      )}

      {activeSubTab === "footstep" && (
        <AnalyzerFootsteps footsteps={currentDayFootsteps} onChange={onFootstepsChange} />
      )}

      {activeSubTab === "memo" && (
        <AnalyzerMemos
          day={day}
          dailyMemos={dailyMemos}
          wholeMemo={wholeMemo}
          participantMemos={participantMemos}
          participants={allParticipants}
          onDailyMemoChange={setDailyMemo}
          onWholeMemoChange={setWholeMemo}
          onParticipantClick={setMemoParticipantId}
        />
      )}

      <ParticipantMemoModal
        participant={openMemoParticipant}
        memo={openMemoData}
        onSave={setParticipantMemo}
        onClose={() => setMemoParticipantId(null)}
      />
    </div>
  );
}
