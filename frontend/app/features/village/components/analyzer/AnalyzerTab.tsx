import { useCallback, useMemo, useState } from "react";
import { useQuery } from "@tanstack/react-query";

import type { VillageSituationView } from "~/features/village/api";
import { fetchVillageSituation } from "~/features/village/api";
import type { DayFootstep } from "~/features/village/analyzer/types";
import { Button } from "~/components/ui/Button";
import { useMe } from "~/features/auth/useMe";
import { useVillageContext } from "~/features/village/VillageContext";
import { useAnalyzerMemos } from "~/features/village/analyzer/useAnalyzerMemos";
import { VoteTab } from "~/features/village/components/info/VoteTab";
import { AnalyzerFootsteps } from "./AnalyzerFootsteps";
import { AnalyzerMemos } from "./AnalyzerMemos";
import { AnalyzerRoomGrid } from "./AnalyzerRoomGrid";
import { ParticipantMemoModal } from "./ParticipantMemoModal";

type BottomTab = "vote" | "memo";

export function AnalyzerTab({
  situation: initialSituation,
  day: pageDay,
}: {
  situation: VillageSituationView;
  day: number;
}) {
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

  const footstepList = useMemo(
    () => initialSituation.footstepList ?? [],
    [initialSituation.footstepList],
  );

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

  const [selectedDay, setSelectedDay] = useState(() => {
    const epilogueDay = village.epilogueDay;
    const available = days.filter((d) => (epilogueDay != null ? d <= epilogueDay : true));
    return available.length > 0 ? available[available.length - 1] : pageDay;
  });

  const { data: daySituation } = useQuery({
    queryKey: ["analyzer-situation", village.id, selectedDay, me?.name],
    queryFn: () => fetchVillageSituation(village.id, selectedDay),
    placeholderData: selectedDay === pageDay ? initialSituation : undefined,
  });

  const [memoParticipantId, setMemoParticipantId] = useState<number | null>(null);
  const [bottomTab, setBottomTab] = useState<BottomTab>("vote");

  const hasRooms = daySituation?.roomAssignedRowList != null && selectedDay > 0;

  const currentDayFootsteps = useMemo(() => {
    const dfm = dailyFootstepMemos.find((m) => m.day === selectedDay);
    return dfm?.footsteps ?? [];
  }, [dailyFootstepMemos, selectedDay]);

  const onFootstepsChange = useCallback(
    (updated: DayFootstep[]) => {
      setDailyFootstepMemos(selectedDay, updated);
    },
    [setDailyFootstepMemos, selectedDay],
  );

  const currentDailyMemo = dailyMemos.find((m) => m.day === selectedDay)?.memo ?? "";

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
        ? (participantMemos.find((m) => m.participantId === memoParticipantId) ?? {
            participantId: memoParticipantId,
            memo: "",
            color: "ffffff",
          })
        : null,
    [memoParticipantId, participantMemos],
  );

  const dayLabel = (d: number) => (d === village.epilogueDay ? "EP" : `${d}d`);

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
    <div className="pt-[8px] pb-[10px]">
      {/* Day tabs + save button */}
      <div className="mb-[8px] flex items-center gap-[4px]">
        <div className="flex flex-1 flex-wrap">
          {days
            .filter((d) => (village.epilogueDay != null ? d <= village.epilogueDay : true))
            .map((d) => (
              <button
                key={d}
                type="button"
                onClick={() => setSelectedDay(d)}
                className={`min-w-[36px] cursor-pointer border-0 px-[8px] py-[6px] text-[13px] text-white first:rounded-l last:rounded-r ${
                  selectedDay === d
                    ? "border-b-2 border-b-[#00ff00] bg-[#304562] text-[#00ff00]"
                    : "bg-[#304562] hover:bg-[#3a5572]"
                }`}
              >
                {dayLabel(d)}
              </button>
            ))}
        </div>
        <Button variant="default" size="xs" onClick={() => save()}>
          保存
        </Button>
      </div>

      {/* Day content: Room grid + Footsteps/Daily memo — responsive */}
      {daySituation && (
        <div className="flex flex-col gap-[10px] lg:flex-row">
          {/* Room grid */}
          {hasRooms && (
            <div className="min-w-0 flex-shrink-0 lg:max-w-[60%]">
              <AnalyzerRoomGrid
                rows={daySituation.roomAssignedRowList!}
                footsteps={currentDayFootsteps}
                participantMemos={participantMemos}
                onParticipantClick={setMemoParticipantId}
              />
            </div>
          )}

          {/* Footstep analysis + Daily memo */}
          <div className="min-w-0 flex-1 space-y-[10px]">
            <AnalyzerFootsteps footsteps={currentDayFootsteps} onChange={onFootstepsChange} />
            <div>
              <label className="mb-[4px] block text-village-sm font-bold text-gray-300">
                {dayLabel(selectedDay)} メモ
              </label>
              <textarea
                value={currentDailyMemo}
                onChange={(e) => setDailyMemo(selectedDay, e.target.value)}
                rows={3}
                className="w-full rounded border border-[#464545] bg-[#303030] p-[8px] text-village-sm text-white"
                placeholder="この日のメモ..."
              />
            </div>
          </div>
        </div>
      )}

      {/* Bottom section: Vote + Whole memo + Participant memos */}
      <div className="mt-[10px] border-t border-[#464545] pt-[10px]">
        <div className="mb-[8px] flex gap-[4px]">
          {(["vote", "memo"] as const).map((tab) => (
            <button
              key={tab}
              type="button"
              onClick={() => setBottomTab(tab)}
              className={`rounded px-[10px] py-[4px] text-village-sm ${
                bottomTab === tab
                  ? "bg-[#00bc8c] text-white"
                  : "cursor-pointer border border-[#464545] bg-[#303030] text-gray-300 hover:bg-[#404040]"
              }`}
            >
              {tab === "vote" ? "投票" : "メモ"}
            </button>
          ))}
        </div>

        {bottomTab === "vote" && initialSituation.vote != null && (
          <VoteTab
            vote={initialSituation.vote}
            roomAssignedRows={daySituation?.roomAssignedRowList}
          />
        )}

        {bottomTab === "memo" && (
          <AnalyzerMemos
            wholeMemo={wholeMemo}
            participantMemos={participantMemos}
            participants={allParticipants}
            onWholeMemoChange={setWholeMemo}
            onParticipantClick={setMemoParticipantId}
          />
        )}
      </div>

      <ParticipantMemoModal
        participant={openMemoParticipant}
        memo={openMemoData}
        onSave={setParticipantMemo}
        onClose={() => setMemoParticipantId(null)}
      />
    </div>
  );
}
