import { useMemo, useState } from "react";

import type { VillageRoomAssignedRow, VillageVoteContent } from "~/features/village/api";
import { RoomLegend } from "./RoomLegend";

const cellBorderClass = "border border-[#464545]";

/**
 * 投票表。日付見出しをクリックするとその日の投票先でソートし、セルをクリックすると
 * 同じ投票先のセルを色付けする (議論の追跡用)。
 */
export function VoteTab({
  vote,
  roomAssignedRows,
}: {
  vote: VillageVoteContent;
  roomAssignedRows: VillageRoomAssignedRow[] | null | undefined;
}) {
  const [sortDayIndex, setSortDayIndex] = useState(0);
  const [highlightTarget, setHighlightTarget] = useState<string | null>(null);

  const maxVoteCount = vote.maxVoteCount ?? 0;

  const sortedList = useMemo(() => {
    const voteList = vote.voteList ?? [];
    if (sortDayIndex === 0) return voteList;
    // クリックした日の投票先 (なし = 末尾) で並べ替える
    const targetIndex = sortDayIndex - 1;
    return [...voteList].sort((a, b) => {
      const aTarget = a.voteTargetList?.[targetIndex] || "";
      const bTarget = b.voteTargetList?.[targetIndex] || "";
      if (aTarget === bTarget) return 0;
      if (aTarget === "") return 1;
      if (bTarget === "") return -1;
      return aTarget.localeCompare(bTarget);
    });
  }, [vote, sortDayIndex]);

  const onCellClick = (target: string) => {
    if (target === "") return;
    setHighlightTarget((prev) => (prev === target ? null : target));
  };

  return (
    <div className="pt-[10px] pb-[10px]">
      <div className="overflow-x-auto">
        <table className={`${cellBorderClass} border-collapse text-village-sm`}>
          <thead>
            {roomAssignedRows && (
              <tr>
                <td className={`${cellBorderClass} p-[5px]`} colSpan={maxVoteCount + 1}>
                  <RoomLegend rows={roomAssignedRows} />
                </td>
              </tr>
            )}
            <tr>
              <th className={`${cellBorderClass} p-[5px] text-left`}>
                <button
                  type="button"
                  className="text-wm-accent cursor-pointer hover:underline"
                  onClick={() => setSortDayIndex(0)}
                >
                  投票者
                </button>
              </th>
              {Array.from({ length: maxVoteCount }, (_, i) => i + 2).map((day) => (
                <th key={day} className={`${cellBorderClass} p-[5px] text-center`}>
                  <button
                    type="button"
                    className="text-wm-accent cursor-pointer hover:underline"
                    onClick={() => setSortDayIndex(day - 1)}
                  >
                    {day}d
                  </button>
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {sortedList.map((member) => (
              <tr key={member.charaName}>
                <td className={`${cellBorderClass} p-[5px]`}>{member.charaName}</td>
                {(member.voteTargetList ?? []).map((target, targetIndex) => (
                  <td
                    key={targetIndex}
                    className={`${cellBorderClass} cursor-pointer p-[5px] ${
                      highlightTarget != null && target === highlightTarget ? "bg-[#3498db]" : ""
                    }`}
                    onClick={() => onCellClick(target)}
                  >
                    {target}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
