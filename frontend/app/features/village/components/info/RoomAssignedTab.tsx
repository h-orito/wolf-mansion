import { useEffect, useRef, useState } from "react";
import type { VillageRoomAssignedRow, VillageSituationContent } from "~/features/village/api";
import { deadMark } from "./dead";

const cellBorderClass = "border border-[#464545]";

/** 役職名は 5 文字を超えると先頭 4 文字 + 省略記号にする (部屋セル幅に収めるため)。 */
function shortenSkillName(skillName: string): string {
  return skillName.length > 5 ? `${skillName.slice(0, 4)}...` : skillName;
}

const TOOLTIP_MARGIN = 8;

/** 部屋割りグリッド + 日別状況テーブル。 */
export function RoomAssignedTab({
  rows,
  situationList,
  isViewableSpoilerContent,
  spoiled = false,
}: {
  rows: VillageRoomAssignedRow[];
  situationList: VillageSituationContent[];
  isViewableSpoilerContent: boolean;
  /** ネタバレ防止 (役職名を隠す) */
  spoiled?: boolean;
}) {
  const [tooltip, setTooltip] = useState<{ name: string; top: number; left: number } | null>(null);
  const tooltipRef = useRef<HTMLDivElement>(null);

  const handleCellClick = (
    e: React.MouseEvent<HTMLTableCellElement>,
    charaName: string | null | undefined,
  ) => {
    if (!charaName) {
      setTooltip(null);
      return;
    }
    const cellRect = e.currentTarget.getBoundingClientRect();
    const rawLeft = cellRect.left + cellRect.width / 2;
    setTooltip({
      name: charaName,
      top: cellRect.top - 4,
      left: rawLeft,
    });
  };

  useEffect(() => {
    if (!tooltip || !tooltipRef.current) return;
    const el = tooltipRef.current;
    const elRect = el.getBoundingClientRect();
    let clamped = false;
    if (elRect.left < TOOLTIP_MARGIN) {
      el.style.left = `${TOOLTIP_MARGIN + elRect.width / 2}px`;
      clamped = true;
    } else if (elRect.right > window.innerWidth - TOOLTIP_MARGIN) {
      el.style.left = `${window.innerWidth - TOOLTIP_MARGIN - elRect.width / 2}px`;
      clamped = true;
    }
    if (!clamped) {
      el.style.left = `${tooltip.left}px`;
    }
  }, [tooltip]);

  useEffect(() => {
    if (!tooltip) return;
    const dismiss = (e: PointerEvent) => {
      if (tooltipRef.current?.contains(e.target as Node)) return;
      setTooltip(null);
    };
    document.addEventListener("pointerdown", dismiss);
    return () => document.removeEventListener("pointerdown", dismiss);
  }, [tooltip]);

  return (
    <div className="pt-[10px] pb-[10px]">
      <div className="overflow-x-auto">
        <table
          className={`${cellBorderClass} border-collapse text-[0.75rem]`}
          style={{ tableLayout: "fixed" }}
        >
          <tbody>
            {rows.map((row, rowIndex) => (
              <tr key={rowIndex}>
                {(row.roomAssignedList ?? []).map((room) => (
                  <td
                    key={room.roomNumber}
                    className={`${cellBorderClass} relative p-0 text-center align-middle cursor-pointer`}
                    style={{
                      width: room.maxWidth ?? undefined,
                      minWidth: room.maxWidth ?? undefined,
                      height: room.maxHeight ?? undefined,
                    }}
                    onClick={(e) => handleCellClick(e, room.charaName)}
                  >
                    <div
                      title={room.charaName ?? undefined}
                      className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2"
                      style={{
                        width: room.charaImgWidth ?? room.maxWidth ?? undefined,
                        height: room.charaImgHeight ?? room.maxHeight ?? undefined,
                        ...(room.charaImgUrl
                          ? {
                              backgroundImage: `url(${room.charaImgUrl})`,
                              backgroundRepeat: "no-repeat",
                              backgroundSize: "contain",
                            }
                          : {}),
                        ...(room.isDead == null || room.isDead ? { opacity: 0.3 } : {}),
                      }}
                    />
                    <div className="absolute bottom-0 left-1/2 -translate-x-1/2 opacity-80">
                      <span className="bg-wm-base whitespace-nowrap">
                        {room.roomNumber} {room.charaShortName ?? ""}
                      </span>
                      {room.isDead && room.charaShortName != null && (
                        <span className="bg-wm-base whitespace-nowrap">
                          <br />
                          {room.deadDay}d {deadMark(room.deadReason)}
                        </span>
                      )}
                      {room.isDummy && (
                        <span className="bg-wm-base whitespace-nowrap">
                          <br />
                          ダミー
                        </span>
                      )}
                      {room.skillName != null && !spoiled && (
                        <span className="bg-wm-base whitespace-nowrap">
                          <br />
                          {shortenSkillName(room.skillName)}
                        </span>
                      )}
                    </div>
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {tooltip && (
        <div
          ref={tooltipRef}
          className="fixed z-50 rounded bg-gray-800 px-2 py-1 text-village-sm text-white whitespace-nowrap"
          style={{
            top: tooltip.top,
            left: tooltip.left,
            transform: "translate(-50%, -100%)",
          }}
        >
          {tooltip.name}
        </div>
      )}
      {situationList.length > 0 && (
        <div className="mt-[10px]">
          <table className={`${cellBorderClass} border-collapse text-village-sm`}>
            <thead>
              <tr>
                <th className={`${cellBorderClass} p-[5px] text-center`}>日付</th>
                <th className={`${cellBorderClass} p-[5px] text-left`}>突然死</th>
                <th className={`${cellBorderClass} p-[5px] text-left`}>処刑</th>
                <th className={`${cellBorderClass} p-[5px] text-left`}>犠牲</th>
                <th className={`${cellBorderClass} p-[5px] text-left`}>復活</th>
                <th className={`${cellBorderClass} p-[5px] text-left`}>後追</th>
                {isViewableSpoilerContent && (
                  <th className={`${cellBorderClass} p-[5px] text-left`}>能力</th>
                )}
              </tr>
            </thead>
            <tbody>
              {situationList.map((situation) => (
                <tr key={situation.day}>
                  <td className={`${cellBorderClass} p-[5px] text-center`}>{situation.day}d</td>
                  <td className={`${cellBorderClass} p-[5px]`}>{situation.suddonlyDeathChara}</td>
                  <td className={`${cellBorderClass} p-[5px]`}>{situation.executedChara}</td>
                  <td className={`${cellBorderClass} p-[5px]`}>{situation.attackedChara}</td>
                  <td className={`${cellBorderClass} p-[5px]`}>{situation.revivalChara}</td>
                  <td className={`${cellBorderClass} p-[5px]`}>{situation.suicideChara}</td>
                  {isViewableSpoilerContent && (
                    <td className={`${cellBorderClass} p-[5px] whitespace-pre-line`}>
                      {situation.ability}
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
