import type { VillageRoomAssignedRow } from "~/features/village/api";
import { deadColor } from "./dead";

/**
 * 部屋番号と参加者の対応一覧 (投票・足音タブの先頭に出す)。死亡者は死因の系統で色分けし、
 * 空き部屋は番号 + 全角アンダースコアで示す。
 */
export function RoomLegend({ rows }: { rows: VillageRoomAssignedRow[] }) {
  return (
    <>
      {rows.map((row, rowIndex) => (
        <span key={rowIndex}>
          {(row.roomAssignedList ?? []).map((room, roomIndex) => {
            const isVacant = room.isDead == null || room.charaShortName == null;
            const color = room.isDead ? deadColor(room.deadReason) : null;
            return (
              <span key={room.roomNumber}>
                <span style={color ? { color } : undefined}>
                  {isVacant ? `${room.roomNumber}＿` : `${room.roomNumber}${room.charaShortName}`}
                </span>
                {roomIndex < (row.roomAssignedList?.length ?? 0) - 1 && <span>　</span>}
              </span>
            );
          })}
          <br />
          {rowIndex < rows.length - 1 && <br />}
        </span>
      ))}
    </>
  );
}
