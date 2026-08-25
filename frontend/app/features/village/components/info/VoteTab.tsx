import { useMemo, useState } from "react";

import type { VillageRoomAssignedRow, VillageVoteContent } from "~/features/village/api";
import { RoomLegend } from "./RoomLegend";

const cellBorderClass = "border border-border";

/**
 * 投票表。日付見出しをクリックするとその日の投票先の得票数が多い順にソートし、セルをクリックすると
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
    const byCharaName = (a: { charaName?: string | null }, b: { charaName?: string | null }) =>
      (a.charaName ?? "").localeCompare(b.charaName ?? "");
    if (sortDayIndex === 0) {
      return [...voteList].sort(byCharaName);
    }
    // クリックした日の投票先ごとに得票数の多い順に並べる (票が集まっている先を追いやすくする)。
    // 同数なら投票先名順、同じ投票先内は投票者名順。未投票 (投票先なし) は末尾
    const targetIndex = sortDayIndex - 1;
    const targetOf = (member: { voteTargetList?: string[] | null }) =>
      member.voteTargetList?.[targetIndex] || "";
    const voteCountByTarget = new Map<string, number>();
    for (const member of voteList) {
      const target = targetOf(member);
      if (target === "") continue;
      voteCountByTarget.set(target, (voteCountByTarget.get(target) ?? 0) + 1);
    }
    return [...voteList].sort((a, b) => {
      const aTarget = targetOf(a);
      const bTarget = targetOf(b);
      if (aTarget === bTarget) return byCharaName(a, b);
      if (aTarget === "") return 1;
      if (bTarget === "") return -1;
      const countDiff =
        (voteCountByTarget.get(bTarget) ?? 0) - (voteCountByTarget.get(aTarget) ?? 0);
      return countDiff !== 0 ? countDiff : aTarget.localeCompare(bTarget);
    });
  }, [vote, sortDayIndex]);

  const onCellClick = (target: string) => {
    if (target === "") return;
    setHighlightTarget((prev) => (prev === target ? null : target));
  };

  return (
    <div className="pt-[10px] pb-[10px]">
      <div className="overflow-x-auto">
        <table className={`${cellBorderClass} border-collapse`}>
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
                <td
                  className={`${cellBorderClass} cursor-pointer p-[5px] ${
                    highlightTarget != null && member.charaName === highlightTarget ? "bg-info" : ""
                  }`}
                  onClick={() => onCellClick(member.charaName ?? "")}
                >
                  {member.charaName}
                </td>
                {(member.voteTargetList ?? []).map((target, targetIndex) => (
                  <td
                    key={targetIndex}
                    className={`${cellBorderClass} cursor-pointer p-[5px] ${
                      highlightTarget != null && target === highlightTarget ? "bg-info" : ""
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
