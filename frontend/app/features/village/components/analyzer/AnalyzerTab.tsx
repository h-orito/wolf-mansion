import { useCallback, useMemo, useState } from "react";
import { useQuery } from "@tanstack/react-query";

import {
  fetchAnalyzerVillage,
  type AnalyzerDaySituation,
} from "~/features/village/analyzer/analyzerApi";
import type { DayFootstep } from "~/features/village/analyzer/types";
import { textareaClass } from "~/components/ui/Input";
import { useMe } from "~/features/auth/useMe";
import type { VillageSituationView } from "~/features/village/api";
import { useVillageContext } from "~/features/village/VillageContext";
import { useAnalyzerMemos } from "~/features/village/analyzer/useAnalyzerMemos";
import { dayLabel } from "~/features/village/components/info/dayLabel";
import { AnalyzerFootsteps } from "./AnalyzerFootsteps";
import { AnalyzerRoomGrid } from "./AnalyzerRoomGrid";
import { ParticipantMemoModal } from "./ParticipantMemoModal";

export function AnalyzerTab({
  footstepList,
  showsFootstepSpoiler,
}: {
  /** 日別の足音 (サーバ整形済み。エピローグ以降は誰が何の役職でどの足音を出したかの詳細になる) */
  footstepList: NonNullable<VillageSituationView["footstepList"]>;
  /** 足音の詳細 (ネタバレ) を表示してよいか */
  showsFootstepSpoiler: boolean;
}) {
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
    flush,
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

  // 足音がない日はサーバが空文字を返すため非表示に落とす
  const currentDayFootstepDetail = footstepList.find((f) => f.day === activeDay)?.footstep || null;

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
    return <p className="py-[10px] text-gray-400">ログインすると推理補助が利用できます</p>;
  }

  if (!analyzerData || !loaded) {
    return <p className="py-[10px] text-gray-400">読み込み中...</p>;
  }

  return (
    // メモ編集は2秒デバウンスで自動保存し、フォーカスが外れたタイミングで即時フラッシュする
    <div className="pt-[8px] pb-[10px]" onBlur={() => void flush()}>
      <ul className="mb-[8px] pl-0">
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

      {/* Day content: Room grid + Footsteps */}
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

          {/* Footstep analysis */}
          <div className="min-w-0 flex-1">
            <AnalyzerFootsteps footsteps={currentDayFootsteps} onChange={onFootstepsChange} />
            {/* エピローグ前の footstepList は鳴った部屋番号のみで上の分析表と重複するため、詳細が公開されてからのみ表示する */}
            {showsFootstepSpoiler && currentDayFootstepDetail != null && (
              <div className="mt-[10px]">
                <p className="mb-[4px] font-bold text-gray-300">足音の内訳</p>
                <p className=" whitespace-pre-line">{currentDayFootstepDetail}</p>
              </div>
            )}
          </div>
        </div>
      )}

      {/* Memos: Daily + Whole (lg では 2 カラム) */}
      <div className="mt-[10px] border-t border-border">
        <div className="flex flex-col gap-[10px] py-[10px] lg:flex-row">
          {currentDaySituation && analyzerData.village.roomSize && (
            <div className="flex min-w-0 flex-1 flex-col">
              <label className="mb-[4px] block font-bold text-gray-300">
                {dayLabel(activeDay, epilogueDay)} メモ
              </label>
              {/* 横並び時は全体メモと同じ高さまで伸ばす */}
              <textarea
                value={currentDailyMemo}
                onChange={(e) => setDailyMemo(activeDay, e.target.value)}
                rows={5}
                className={`${textareaClass} lg:flex-1`}
                placeholder="この日のメモ..."
              />
            </div>
          )}
          <div className="flex min-w-0 flex-1 flex-col">
            <label className="mb-[4px] block font-bold text-gray-300">全体メモ</label>
            <textarea
              value={wholeMemo}
              onChange={(e) => setWholeMemo(e.target.value)}
              rows={10}
              className={textareaClass}
              placeholder="村全体のメモ..."
            />
          </div>
        </div>
      </div>

      <ParticipantMemoModal
        participant={openMemoParticipant}
        memo={openMemoData}
        onChange={setParticipantMemo}
        onClose={() => {
          setMemoParticipantId(null);
          void flush();
        }}
      />
    </div>
  );
}
