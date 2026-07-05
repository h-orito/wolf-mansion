import type { VillageRoomAssigned, VillageRoomAssignedRow } from "~/features/village/api";

/**
 * 部屋セルをクリックして選択する用途の部屋割グリッド。
 * 徘徊の対象部屋トグルや、部屋割からの対象者選択で共用する。
 */
export function RoomGrid({
  rows,
  isSelected = () => false,
  isSelectable = () => true,
  isDimmed = (room) => room.isDead == null || room.isDead,
  onRoomClick,
}: {
  rows: VillageRoomAssignedRow[];
  isSelected?: (room: VillageRoomAssigned) => boolean;
  /** false の部屋はグレーアウトしてクリック不可にする。 */
  isSelectable?: (room: VillageRoomAssigned) => boolean;
  /** 選択可否とは別にセルを薄く表示する条件。既定は死亡・空き部屋。 */
  isDimmed?: (room: VillageRoomAssigned) => boolean;
  onRoomClick: (room: VillageRoomAssigned) => void;
}) {
  return (
    <table className="border-collapse border border-[#464545] text-village-sm">
      <tbody>
        {rows.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {(row.roomAssignedList ?? []).map((room) => {
              const selectable = isSelectable(room);
              return (
                <td
                  key={room.roomNumber}
                  className={`text-center align-bottom ${selectable ? "cursor-pointer" : "cursor-not-allowed"}`}
                  role={selectable ? "button" : undefined}
                  tabIndex={selectable ? 0 : undefined}
                  aria-disabled={!selectable}
                  aria-label={
                    room.charaName != null
                      ? `${room.roomNumber} ${room.charaName}`
                      : room.roomNumber
                  }
                  style={{
                    border: isSelected(room) ? "2px solid #0ce3ac" : "1px solid #464545",
                    width: room.charaImgWidth ?? 50,
                    height: room.charaImgHeight ?? 60,
                    ...(room.charaImgUrl
                      ? {
                          backgroundImage: `url(${room.charaImgUrl})`,
                          backgroundRepeat: "no-repeat",
                          backgroundSize: "contain",
                        }
                      : {}),
                    ...(!selectable || isDimmed(room) ? { opacity: 0.3 } : {}),
                  }}
                  onClick={() => {
                    if (selectable) onRoomClick(room);
                  }}
                  onKeyDown={(e) => {
                    if (!selectable) return;
                    if (e.key === "Enter" || e.key === " ") {
                      e.preventDefault();
                      onRoomClick(room);
                    }
                  }}
                >
                  <span
                    className="whitespace-nowrap"
                    style={{ backgroundColor: "#222222", opacity: 0.8 }}
                  >
                    {room.roomNumber} {room.charaShortName ?? ""}
                  </span>
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  );
}
