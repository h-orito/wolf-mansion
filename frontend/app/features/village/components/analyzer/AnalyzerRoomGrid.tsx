import type { AnalyzerDayRoom, AnalyzerVillageData } from "~/features/village/analyzer/analyzerApi";
import type { DayFootstep, ParticipantMemo } from "~/features/village/analyzer/types";
import { FootstepLines } from "./FootstepLines";

const DEAD_MARK: Record<string, string> = {
  SUDDON: "凸",
  EXECUTE: "▼",
  SUICIDE: "❤︎",
};

function deadMark(reason: { code: string } | null): string {
  if (!reason) return "▲";
  return DEAD_MARK[reason.code] ?? "▲";
}

function charaImageUrl(
  participantId: number,
  participantIdToChara: AnalyzerVillageData["participantIdToChara"],
): { url: string; width: number; height: number } | null {
  const chara = participantIdToChara[String(participantId)];
  if (!chara) return null;
  const img = chara.images.list.find((i) => i.faceType.code === "NORMAL") ?? chara.images.list[0];
  if (!img) return null;
  const url = img.url.startsWith("http") ? img.url : `https://wolfort.net${img.url}`;
  return { url, width: chara.size.width, height: chara.size.height };
}

export function AnalyzerRoomGrid({
  rooms,
  roomSize,
  footsteps,
  participantMemos,
  participantIdToChara,
  participants,
  dummyCharaId,
  onParticipantClick,
}: {
  rooms: AnalyzerDayRoom[];
  roomSize: { width: number; height: number };
  footsteps: DayFootstep[];
  participantMemos: ParticipantMemo[];
  participantIdToChara: AnalyzerVillageData["participantIdToChara"];
  participants: { id: number; charaName: { shortName: string } }[];
  dummyCharaId: number;
  onParticipantClick: (participantId: number) => void;
}) {
  const getMemo = (participantId: number | null) => {
    if (participantId == null) return null;
    return participantMemos.find((pm) => pm.participantId === participantId) ?? null;
  };

  const cellW = 100;
  const cellH = 100;

  const rows: AnalyzerDayRoom[][] = [];
  for (let y = 0; y < roomSize.height; y++) {
    rows.push(rooms.filter((r) => r.y === y).sort((a, b) => a.x - b.x));
  }

  return (
    <div className="overflow-x-auto">
      <table className="border border-[#464545] border-collapse" style={{ tableLayout: "fixed" }}>
        <tbody>
          {rows.map((row, y) => (
            <tr key={y}>
              {row.map((room) => {
                const pMemo = getMemo(room.participantId);
                const memoText = pMemo?.memo ?? "";
                const memoColor = pMemo?.color ?? "ffffff";
                const displayMemo = memoText.length > 24 ? `${memoText.slice(0, 23)}...` : memoText;
                const img = room.participantId
                  ? charaImageUrl(room.participantId, participantIdToChara)
                  : null;
                const isDummy =
                  room.participantId != null &&
                  participantIdToChara[String(room.participantId)]?.id === dummyCharaId;

                return (
                  <td
                    key={room.roomNumber}
                    className="border border-[#464545] p-0 text-center align-middle"
                  >
                    <div
                      className={`relative ${room.participantId != null ? "cursor-pointer" : ""}`}
                      style={{ width: cellW, height: cellH }}
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
                          allRooms={rooms}
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
                      {img && (
                        <div
                          className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2"
                          style={{
                            width: img.width,
                            height: img.height,
                            backgroundImage: `url(${img.url})`,
                            backgroundRepeat: "no-repeat",
                            backgroundSize: "contain",
                            opacity: room.isDead == null || room.isDead ? 0.2 : 0.7,
                          }}
                        />
                      )}
                      <div className="absolute bottom-0 left-1/2 -translate-x-1/2 whitespace-nowrap opacity-80">
                        <span className="bg-wm-base text-[11px]">
                          {String(room.roomNumber).padStart(2, "0")}{" "}
                          {room.participantId != null
                            ? (participants.find((p) => p.id === room.participantId)?.charaName
                                .shortName ?? "")
                            : ""}
                        </span>
                        {room.isDead && (
                          <span className="bg-wm-base text-[11px]">
                            <br />
                            {room.deadDay}d {deadMark(room.deadReason)}
                          </span>
                        )}
                        {isDummy && (
                          <span className="bg-wm-base text-[11px]">
                            <br />
                            ダミー
                          </span>
                        )}
                      </div>
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
