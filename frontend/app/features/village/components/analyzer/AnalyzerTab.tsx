import { useCallback, useMemo, useState } from "react";
import { useQuery } from "@tanstack/react-query";

import type { VillageSituationView } from "~/features/village/api";
import {
  fetchAnalyzerVillage,
  type AnalyzerDaySituation,
} from "~/features/village/analyzer/analyzerApi";
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
}: {
  situation: VillageSituationView;
  day: number;
}) {
  const { me } = useMe();
  const village = useVillageContext();

  const { data: analyzerData } = useQuery({
    queryKey: ["analyzer-village", village.id],
    queryFn: () => fetchAnalyzerVillage(village.id),
  });

  const allParticipants = useMemo(
    () => [...village.participants.list, ...village.spectators.list],
    [village],
  );
  const participantIds = useMemo(() => allParticipants.map((p) => p.id), [allParticipants]);

  const analyzerDays = useMemo(() => {
    if (!analyzerData) return [];
    const epilogueDay = analyzerData.village.epilogueDay;
    return analyzerData.days
      .filter((d) => d.day >= 1)
      .filter((d) => (epilogueDay != null ? d.day <= epilogueDay : true))
      .sort((a, b) => a.day - b.day);
  }, [analyzerData]);

  const rawFootstepsByDay = useMemo(() => {
    if (!analyzerData) return [];
    return analyzerData.days
      .filter((d) => d.day >= 1)
      .map((d) => ({
        day: d.day,
        footsteps: d.footsteps,
      }));
  }, [analyzerData]);

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
  } = useAnalyzerMemos(me?.playerId ?? null, village.id, participantIds, rawFootstepsByDay);

  const [selectedDay, setSelectedDay] = useState<number | null>(null);
  const activeDay =
    selectedDay ?? (analyzerDays.length > 0 ? analyzerDays[analyzerDays.length - 1].day : 1);

  const currentDaySituation: AnalyzerDaySituation | undefined = useMemo(
    () => analyzerData?.days.find((d) => d.day === activeDay),
    [analyzerData, activeDay],
  );

  const [memoParticipantId, setMemoParticipantId] = useState<number | null>(null);
  const [bottomTab, setBottomTab] = useState<BottomTab>("vote");

  const currentDayFootsteps = useMemo(() => {
    const dfm = dailyFootstepMemos.find((m) => m.day === activeDay);
    return dfm?.footsteps ?? [];
  }, [dailyFootstepMemos, activeDay]);

  const onFootstepsChange = useCallback(
    (updated: DayFootstep[]) => {
      setDailyFootstepMemos(activeDay, updated);
    },
    [setDailyFootstepMemos, activeDay],
  );

  const currentDailyMemo = dailyMemos.find((m) => m.day === activeDay)?.memo ?? "";

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

  const dayLabel = (d: number) => (d === analyzerData?.village.epilogueDay ? "EP" : `${d}d`);

  if (!me) {
    return (
      <p className="py-[10px] text-village-sm text-gray-400">
        ログインすると推理補助が利用できます
      </p>
    );
  }

  if (!analyzerData || !loaded) {
    return <p className="py-[10px] text-village-sm text-gray-400">読み込み中...</p>;
  }

  return (
    <div className="pt-[8px] pb-[10px]">
      {/* Day tabs + save button */}
      <div className="mb-[8px] flex items-center gap-[4px]">
        <div className="flex flex-1 flex-wrap">
          {analyzerDays.map((d) => (
            <button
              key={d.day}
              type="button"
              onClick={() => setSelectedDay(d.day)}
              className={`min-w-[36px] cursor-pointer border-0 px-[8px] py-[6px] text-[13px] text-white first:rounded-l last:rounded-r ${
                activeDay === d.day
                  ? "border-b-2 border-b-[#00ff00] bg-[#304562] text-[#00ff00]"
                  : "bg-[#304562] hover:bg-[#3a5572]"
              }`}
            >
              {dayLabel(d.day)}
            </button>
          ))}
        </div>
        <Button variant="default" size="xs" onClick={() => save()}>
          保存
        </Button>
      </div>

      {/* Day content: Room grid + Footsteps/Daily memo */}
      {currentDaySituation && analyzerData.village.roomSize && (
        <div className="flex flex-col gap-[10px] lg:flex-row">
          {/* Room grid */}
          <div className="min-w-0 flex-shrink-0 lg:max-w-[60%]">
            <AnalyzerRoomGrid
              rooms={currentDaySituation.rooms}
              roomSize={analyzerData.village.roomSize}
              footsteps={currentDayFootsteps}
              participantMemos={participantMemos}
              participantIdToChara={analyzerData.participantIdToChara}
              participants={analyzerData.village.participants.list}
              dummyCharaId={analyzerData.village.setting.chara.dummyCharaId}
              onParticipantClick={setMemoParticipantId}
            />
          </div>

          {/* Footstep analysis + Daily memo */}
          <div className="min-w-0 flex-1 space-y-[10px]">
            <AnalyzerFootsteps footsteps={currentDayFootsteps} onChange={onFootstepsChange} />
            <div>
              <label className="mb-[4px] block text-village-sm font-bold text-gray-300">
                {dayLabel(activeDay)} メモ
              </label>
              <textarea
                value={currentDailyMemo}
                onChange={(e) => setDailyMemo(activeDay, e.target.value)}
                rows={3}
                className="w-full rounded border border-[#464545] bg-[#303030] p-[8px] text-village-sm text-white"
                placeholder="この日のメモ..."
              />
            </div>
          </div>
        </div>
      )}

      {/* Bottom section: Vote + Memos */}
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
            roomAssignedRows={initialSituation.roomAssignedRowList}
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
