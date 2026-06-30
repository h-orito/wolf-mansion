import { useMemo } from "react";

import type { VillageRoomAssigned } from "~/features/village/api";

type Room = { roomNumber: string; x: number; y: number };

function parseRoomNumbers(footstep: string): number[] {
  return footstep
    .split(",")
    .map((s) => Number.parseInt(s.trim(), 10))
    .filter((n) => !Number.isNaN(n));
}

function buildRoomMap(rows: { roomAssignedList: VillageRoomAssigned[] }[]): Map<number, Room> {
  const map = new Map<number, Room>();
  for (let y = 0; y < rows.length; y++) {
    const list = rows[y].roomAssignedList ?? [];
    for (let x = 0; x < list.length; x++) {
      const rn = Number.parseInt(list[x].roomNumber, 10);
      if (!Number.isNaN(rn)) map.set(rn, { roomNumber: list[x].roomNumber, x, y });
    }
  }
  return map;
}

function lineStyle(
  direction: "up" | "right" | "down" | "left" | "dot",
  index: number,
  color: string,
  isRound: boolean,
): React.CSSProperties {
  const offset = index % 2 === 0 ? 50 - 4 * Math.floor(index / 2) : 54 + 4 * Math.floor(index / 2);
  const offsetInv =
    index % 2 === 0 ? 51 + 4 * Math.floor(index / 2) : 47 - 4 * Math.floor(index / 2);
  const borderStyle = isRound ? "dashed" : "solid";
  const c = color.startsWith("#") ? color : `#${color}`;

  switch (direction) {
    case "up":
      return {
        position: "absolute",
        right: 0,
        top: 0,
        width: offset,
        height: offset + 1,
        border: 0,
        borderLeft: `2px ${borderStyle} ${c}`,
        zIndex: 5,
      };
    case "right":
      return {
        position: "absolute",
        right: 0,
        top: 0,
        width: offset,
        height: offset + 1,
        border: 0,
        borderBottom: `2px ${borderStyle} ${c}`,
        zIndex: 5,
      };
    case "down":
      return {
        position: "absolute",
        left: 0,
        bottom: 0,
        width: offsetInv,
        height: offsetInv - 1,
        border: 0,
        borderRight: `2px ${borderStyle} ${c}`,
        zIndex: 5,
      };
    case "left":
      return {
        position: "absolute",
        left: 0,
        bottom: 0,
        width: offsetInv,
        height: offsetInv - 1,
        border: 0,
        borderTop: `2px ${borderStyle} ${c}`,
        zIndex: 5,
      };
    case "dot": {
      const dotLeft =
        index % 2 === 0 ? 44 + 4 * Math.floor(index / 2) : 40 - 4 * Math.floor(index / 2);
      const dotTop =
        index % 2 === 0 ? 44 - 4 * Math.floor(index / 2) : 40 + 4 * Math.floor(index / 2);
      return {
        position: "absolute",
        left: dotLeft,
        top: dotTop,
        width: 12,
        height: 12,
        borderRadius: 10,
        backgroundColor: c,
        zIndex: 5,
      };
    }
  }
}

export function FootstepLines({
  footstep,
  color,
  show,
  room,
  rows,
  index,
}: {
  footstep: string;
  color: string;
  show: boolean;
  room: VillageRoomAssigned;
  rows: { roomAssignedList: VillageRoomAssigned[] }[];
  index: number;
}) {
  const roomMap = useMemo(() => buildRoomMap(rows), [rows]);

  if (!show) return null;

  const roomNumbers = parseRoomNumbers(footstep);
  const currentRn = Number.parseInt(room.roomNumber, 10);
  if (!roomNumbers.includes(currentRn)) return null;

  const currentRoom = roomMap.get(currentRn);
  if (!currentRoom) return null;

  const pathRooms = roomNumbers.map((rn) => roomMap.get(rn)).filter(Boolean) as Room[];

  const hasUp = pathRooms.some((r) => r.x === currentRoom.x && r.y < currentRoom.y);
  const hasRight = pathRooms.some((r) => r.y === currentRoom.y && r.x > currentRoom.x);
  const hasDown = pathRooms.some((r) => r.x === currentRoom.x && r.y > currentRoom.y);
  const hasLeft = pathRooms.some((r) => r.y === currentRoom.y && r.x < currentRoom.x);
  const showDot = roomNumbers.length === 1 || !(hasUp || hasRight || hasDown || hasLeft);

  const xs = new Set(pathRooms.map((r) => r.x));
  const ys = new Set(pathRooms.map((r) => r.y));
  const isRound = xs.size > 1 && ys.size > 1;

  return (
    <>
      {hasUp && <span style={lineStyle("up", index, color, isRound)} />}
      {hasRight && <span style={lineStyle("right", index, color, isRound)} />}
      {hasDown && <span style={lineStyle("down", index, color, isRound)} />}
      {hasLeft && <span style={lineStyle("left", index, color, isRound)} />}
      {showDot && <span style={lineStyle("dot", index, color, false)} />}
    </>
  );
}
