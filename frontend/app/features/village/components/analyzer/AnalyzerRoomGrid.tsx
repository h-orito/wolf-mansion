import type { VillageRoomAssignedRow } from "~/features/village/api";
import type { DayFootstep, ParticipantMemo } from "~/features/village/analyzer/types";
import { deadMark } from "~/features/village/components/info/dead";
import { FootstepLines } from "./FootstepLines";

const cellBorderClass = "border border-[#464545]";

export function AnalyzerRoomGrid({
  rows,
  footsteps,
  participantMemos,
  onParticipantClick,
}: {
  rows: VillageRoomAssignedRow[];
  footsteps: DayFootstep[];
  participantMemos: ParticipantMemo[];
  onParticipantClick: (participantId: number) => void;
}) {
  const getMemo = (participantId: number | null | undefined) => {
    if (participantId == null) return null;
    return participantMemos.find((pm) => pm.participantId === participantId) ?? null;
  };

  return (
    <div className="overflow-x-auto pt-[10px] pb-[10px]">
      <table
        className={`${cellBorderClass} border-collapse text-[0.75rem]`}
        style={{ tableLayout: "fixed" }}
      >
        <tbody>
          {rows.map((row, rowIndex) => (
            <tr key={rowIndex}>
              {(row.roomAssignedList ?? []).map((room) => {
                const pMemo = getMemo(room.participantId);
                const memoText = pMemo?.memo ?? "";
                const memoColor = pMemo?.color ?? "ffffff";
                const displayMemo = memoText.length > 24 ? `${memoText.slice(0, 23)}...` : memoText;

                return (
                  <td
                    key={room.roomNumber}
                    className={`${cellBorderClass} relative p-0 text-center align-middle ${room.participantId != null ? "cursor-pointer" : ""}`}
                    style={{
                      width: room.maxWidth ?? undefined,
                      minWidth: room.maxWidth ?? undefined,
                      height: room.maxHeight ?? undefined,
                    }}
                    onClick={() =>
                      room.participantId != null && onParticipantClick(room.participantId)
                    }
                  >
                    {footsteps.map((fs, idx) => (
                      <FootstepLines
                        key={`${room.roomNumber}-fs-${idx}`}
                        footstep={fs.footstep}
                        color={fs.color}
                        show={fs.show}
                        room={room}
                        rows={rows}
                        index={idx}
                      />
                    ))}
                    {displayMemo && (
                      <div className="absolute top-0 left-0 right-0 z-[3] px-[2px]">
                        <p
                          className="my-0 whitespace-normal text-[10px] leading-tight"
                          style={{ color: `#${memoColor}` }}
                        >
                          {displayMemo}
                        </p>
                      </div>
                    )}
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
                        ...(room.isDead == null || room.isDead
                          ? { opacity: 0.2 }
                          : { opacity: 0.7 }),
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
                    </div>
                  </td>
                );
              })}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
