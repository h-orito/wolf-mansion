import type { VillageParticipantView } from "~/features/village/api";
import type { DailyMemo, ParticipantMemo } from "~/features/village/analyzer/types";

export function AnalyzerMemos({
  day,
  dailyMemos,
  wholeMemo,
  participantMemos,
  participants,
  onDailyMemoChange,
  onWholeMemoChange,
  onParticipantClick,
}: {
  day: number;
  dailyMemos: DailyMemo[];
  wholeMemo: string;
  participantMemos: ParticipantMemo[];
  participants: VillageParticipantView[];
  onDailyMemoChange: (day: number, memo: string) => void;
  onWholeMemoChange: (memo: string) => void;
  onParticipantClick: (participantId: number) => void;
}) {
  const currentDailyMemo = dailyMemos.find((m) => m.day === day)?.memo ?? "";

  return (
    <div className="space-y-[15px] pt-[10px] pb-[10px]">
      <div>
        <label className="mb-[4px] block text-village-sm font-bold text-gray-300">
          {day}d メモ
        </label>
        <textarea
          value={currentDailyMemo}
          onChange={(e) => onDailyMemoChange(day, e.target.value)}
          rows={3}
          className="w-full rounded border border-[#464545] bg-[#303030] p-[8px] text-village-sm text-white"
          placeholder="この日のメモ..."
        />
      </div>

      <div>
        <label className="mb-[4px] block text-village-sm font-bold text-gray-300">全体メモ</label>
        <textarea
          value={wholeMemo}
          onChange={(e) => onWholeMemoChange(e.target.value)}
          rows={4}
          className="w-full rounded border border-[#464545] bg-[#303030] p-[8px] text-village-sm text-white"
          placeholder="村全体のメモ..."
        />
      </div>

      <div>
        <label className="mb-[4px] block text-village-sm font-bold text-gray-300">参加者メモ</label>
        <div className="space-y-[4px]">
          {participants.map((p) => {
            const pm = participantMemos.find((m) => m.participantId === p.id);
            const memoText = pm?.memo ?? "";
            const memoColor = pm?.color ?? "ffffff";
            return (
              <button
                key={p.id}
                type="button"
                onClick={() => onParticipantClick(p.id)}
                className="flex w-full cursor-pointer items-center gap-[8px] rounded border border-[#464545] bg-[#303030] px-[8px] py-[4px] text-left hover:bg-[#404040]"
              >
                <span
                  className="inline-block h-[12px] w-[12px] rounded-full border border-[#464545]"
                  style={{ backgroundColor: `#${memoColor}` }}
                />
                <span className="text-village-sm text-white">{p.charaName.shortName}</span>
                {memoText && (
                  <span className="flex-1 truncate text-village-sm text-gray-400">{memoText}</span>
                )}
              </button>
            );
          })}
        </div>
      </div>
    </div>
  );
}
