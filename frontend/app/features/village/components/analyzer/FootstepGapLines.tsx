import { useMemo } from "react";

import type { AnalyzerDayRoom } from "~/features/village/analyzer/analyzerApi";
import type { DayFootstep } from "~/features/village/analyzer/types";
import {
  interpolateFootstepGapSegments,
  parseFootstepRoomNumbers,
} from "~/features/village/analyzer/footstepGaps";

// FootstepLines の実線・破線・ドットと同じ index ごとのずらし量に合わせる。
// FootstepLines は縦線を x 方向、横線を y 方向に逆符号でずらすため、y は x の逆符号
function overlayOffset(index: number): { x: number; y: number } {
  const half = Math.floor(index / 2);
  const x = index % 2 === 0 ? 4 * half : -(4 * half + 4);
  return { x, y: -x };
}

// 鳴らなかった部屋（空き部屋・死亡者・防音者など）で途切れた足音経路の推定区間を、
// 部屋割テーブルに重ねて描く。防音者の可能性があり推測にすぎないため、
// 鳴った部屋同士を繋ぐ実線・破線とは区別できる半透明の細かい点線にする
export function FootstepGapLines({
  footsteps,
  rooms,
  roomSize,
  cellW,
  cellH,
}: {
  footsteps: DayFootstep[];
  rooms: AnalyzerDayRoom[];
  roomSize: { width: number; height: number };
  cellW: number;
  cellH: number;
}) {
  const roomMap = useMemo(() => {
    const map = new Map<number, AnalyzerDayRoom>();
    for (const r of rooms) map.set(r.roomNumber, r);
    return map;
  }, [rooms]);

  const polylines = footsteps.flatMap((fs, index) => {
    if (!fs.show) return [];
    const audibleCells = parseFootstepRoomNumbers(fs.footstep)
      .map((rn) => roomMap.get(rn))
      .filter((r) => r != null)
      .map((r) => ({ x: r.x, y: r.y }));
    const offset = overlayOffset(index);
    const color = fs.color.startsWith("#") ? fs.color : `#${fs.color}`;
    return interpolateFootstepGapSegments(audibleCells).map((segment, segmentIndex) => ({
      key: `${index}-${segmentIndex}`,
      color,
      points: segment
        .map(
          (cell) =>
            `${cell.x * cellW + cellW / 2 + offset.x},${cell.y * cellH + cellH / 2 + offset.y}`,
        )
        .join(" "),
    }));
  });

  if (polylines.length === 0) return null;

  return (
    <svg
      className="pointer-events-none absolute inset-0 z-[6]"
      width={roomSize.width * cellW}
      height={roomSize.height * cellH}
    >
      {polylines.map((p) => (
        <polyline
          key={p.key}
          points={p.points}
          fill="none"
          stroke={p.color}
          strokeWidth={1.5}
          strokeDasharray="3 4"
          opacity={0.6}
        />
      ))}
    </svg>
  );
}
