import { useCallback, useMemo, useState } from "react";
import { useQuery } from "@tanstack/react-query";

import type { VillageSituationView } from "~/features/village/api";
import {
  fetchAnalyzerVillage,
  type AnalyzerDaySituation,
} from "~/features/village/analyzer/analyzerApi";
import type { DayFootstep } from "~/features/village/analyzer/types";
import { Button } from "~/components/ui/Button";
import { textareaClass } from "~/components/ui/Input";
import { useMe } from "~/features/auth/useMe";
import { useVillageContext } from "~/features/village/VillageContext";
import { useAnalyzerMemos } from "~/features/village/analyzer/useAnalyzerMemos";
import { dayLabel } from "~/features/village/components/info/dayLabel";
import { VoteTab } from "~/features/village/components/info/VoteTab";
import { AnalyzerFootsteps } from "./AnalyzerFootsteps";
import { AnalyzerRoomGrid } from "./AnalyzerRoomGrid";
import { ParticipantMemoModal } from "./ParticipantMemoModal";

export function AnalyzerTab({ situation: initialSituation }: { situation: VillageSituationView }) {
  const { me } = useMe();
  const village = useVillageContext();

  const { data: analyzerData } = useQuery({
    queryKey: ["analyzer-village", village.id, village.days.list.length],
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

  const epilogueDay = analyzerData?.village.epilogueDay ?? null;

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
      <div className="mb-[8px] flex items-center">
        <ul className="flex-1 pl-0">
          {analyzerDays.map((d) => (
            <li key={d.day} className="mr-[10px] inline list-none">
              {activeDay === d.day ? (
                <span>{dayLabel(d.day, epilogueDay)}</span>
              ) : (
                <button
                  type="button"
                  onClick={() => setSelectedDay(d.day)}
                  className="text-wm-accent cursor-pointer hover:underline"
                >
                  {dayLabel(d.day, epilogueDay)}
                </button>
              )}
            </li>
          ))}
        </ul>
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
          <div className="flex min-w-0 flex-1 flex-col gap-[10px]">
            <AnalyzerFootsteps footsteps={currentDayFootsteps} onChange={onFootstepsChange} />
            {/* 横並び時は日次メモを部屋割の下端まで伸ばす */}
            <div className="lg:flex lg:min-h-0 lg:flex-1 lg:flex-col">
              <label className="mb-[4px] block text-village-sm font-bold text-gray-300">
                {dayLabel(activeDay, epilogueDay)} メモ
              </label>
              <textarea
                value={currentDailyMemo}
                onChange={(e) => setDailyMemo(activeDay, e.target.value)}
                rows={3}
                className={`${textareaClass} lg:flex-1`}
                placeholder="この日のメモ..."
              />
            </div>
          </div>
        </div>
      )}

      {/* Bottom section: Vote + Whole memo */}
      <div className="mt-[10px] border-t border-[#464545]">
        <div className="flex flex-col lg:flex-row lg:gap-[10px]">
          {initialSituation.vote != null && (
            <div className="min-w-0 flex-shrink-0 lg:max-w-[60%]">
              <VoteTab vote={initialSituation.vote} roomAssignedRows={null} />
            </div>
          )}

          {/* 横並び時は全体メモを投票欄の下端まで伸ばす。py は VoteTab 内の上下パディングと揃える */}
          <div className="flex min-w-0 flex-1 flex-col py-[10px]">
            <label className="mb-[4px] block text-village-sm font-bold text-gray-300">
              全体メモ
            </label>
            <textarea
              value={wholeMemo}
              onChange={(e) => setWholeMemo(e.target.value)}
              rows={4}
              className={`${textareaClass} lg:flex-1`}
              placeholder="村全体のメモ..."
            />
          </div>
        </div>
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
