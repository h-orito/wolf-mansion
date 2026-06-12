import type { VillageFootstepContent, VillageRoomAssignedRow } from "~/features/village/api";
import { RoomLegend } from "./RoomLegend";

const cellBorderClass = "border border-[#464545]";

/** 日別の足音一覧。表示形式 (詳細/簡略) はサーバがビューアごとに整形済み。 */
export function FootstepTab({
  footstepList,
  roomAssignedRows,
}: {
  footstepList: VillageFootstepContent[];
  roomAssignedRows: VillageRoomAssignedRow[] | null | undefined;
}) {
  return (
    <div className="pt-[10px] pb-[10px]">
      <div className="overflow-x-auto">
        <table className={`${cellBorderClass} border-collapse text-[10.32px]`}>
          <tbody>
            {roomAssignedRows && (
              <tr>
                <td className={`${cellBorderClass} p-[5px] whitespace-nowrap`} colSpan={2}>
                  <RoomLegend rows={roomAssignedRows} />
                </td>
              </tr>
            )}
            {footstepList.map((footstep) => (
              <tr key={footstep.day}>
                <td className={`${cellBorderClass} p-[5px] text-center`}>{footstep.day}d</td>
                <td className={`${cellBorderClass} p-[5px] whitespace-pre-line`}>
                  {footstep.footstep}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
