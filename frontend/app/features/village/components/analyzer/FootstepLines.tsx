import { useMemo } from "react";

import type { AnalyzerDayRoom } from "~/features/village/analyzer/analyzerApi";

function parseRoomNumbers(footstep: string): number[] {
  return footstep
    .split(",")
    .map((s) => Number.parseInt(s.trim(), 10))
    .filter((n) => !Number.isNaN(n));
}

function lineStyle(
  direction: "up" | "right" | "down" | "left" | "dot",
  index: number,
  color: string,
  isRound: boolean,
): React.CSSProperties {
  const half = Math.floor(index / 2);
  const isEven = index % 2 === 0;
  const borderStyle = isRound ? "dashed" : "solid";
  const c = color.startsWith("#") ? color : `#${color}`;

  if (direction === "dot") {
    return {
      position: "absolute",
      left: isEven ? 44 + 4 * half : 40 - 4 * half,
      top: isEven ? 44 - 4 * half : 40 + 4 * half,
      width: 12,
      height: 12,
      borderRadius: 10,
      backgroundColor: c,
      zIndex: 5,
    };
  }

  const upRightSize = isEven ? 50 - 4 * half : 54 + 4 * half;
  const downLeftSize = isEven ? 51 + 4 * half : 47 - 4 * half;

  switch (direction) {
    case "up":
      return {
        position: "absolute",
        right: 0,
        top: 0,
        width: upRightSize,
        height: upRightSize + 1,
        border: 0,
        borderLeft: `2px ${borderStyle} ${c}`,
        zIndex: 5,
      };
    case "right":
      return {
        position: "absolute",
        right: 0,
        top: 0,
        width: upRightSize,
        height: upRightSize + 1,
        border: 0,
        borderBottom: `2px ${borderStyle} ${c}`,
        zIndex: 5,
      };
    case "down":
      return {
        position: "absolute",
        left: 0,
        bottom: 0,
        width: downLeftSize,
        height: downLeftSize - 1,
        border: 0,
        borderRight: `2px ${borderStyle} ${c}`,
        zIndex: 5,
      };
    case "left":
      return {
        position: "absolute",
        left: 0,
        bottom: 0,
        width: downLeftSize,
        height: downLeftSize - 1,
        border: 0,
        borderTop: `2px ${borderStyle} ${c}`,
        zIndex: 5,
      };
  }
}

export function FootstepLines({
  footstep,
  color,
  show,
  room,
  allRooms,
  index,
}: {
  footstep: string;
  color: string;
  show: boolean;
  room: AnalyzerDayRoom;
  allRooms: AnalyzerDayRoom[];
  index: number;
}) {
  const roomMap = useMemo(() => {
    const map = new Map<number, AnalyzerDayRoom>();
    for (const r of allRooms) map.set(r.roomNumber, r);
    return map;
  }, [allRooms]);

  if (!show) return null;

  const roomNumbers = parseRoomNumbers(footstep);
  if (!roomNumbers.includes(room.roomNumber)) return null;

  const pathRooms = roomNumbers.map((rn) => roomMap.get(rn)).filter(Boolean) as AnalyzerDayRoom[];

  const hasUp = pathRooms.some((r) => r.x === room.x && r.y < room.y);
  const hasRight = pathRooms.some((r) => r.y === room.y && r.x > room.x);
  const hasDown = pathRooms.some((r) => r.x === room.x && r.y > room.y);
  const hasLeft = pathRooms.some((r) => r.y === room.y && r.x < room.x);
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
