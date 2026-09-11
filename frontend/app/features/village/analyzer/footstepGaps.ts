// 足音の鳴った部屋の集合から、鳴らなかった部屋（空き部屋・死亡者・防音者など）で
// 途切れた経路区間を推定する。
//
// 徘徊・襲撃の経路は縦横移動のみで曲がりは最大1回（直線または L字）。
// 足音は経路の内部の部屋（始点・終点を除く）でのみ鳴り、部屋番号昇順で記録される
// ため経路順は失われている。ここでは「すべての鳴った部屋が乗る直線または L字」を
// 幾何的に復元し、鳴った部屋同士の間で欠けているセル列を gap 区間として返す。
// L字の角が複数通りあり得る場合（対角2部屋など）は、あり得る候補すべてを返す。

export interface GridCell {
  x: number;
  y: number;
}

export function parseFootstepRoomNumbers(footstep: string): number[] {
  return footstep
    .split(",")
    .map((s) => Number.parseInt(s.trim(), 10))
    .filter((n) => !Number.isNaN(n));
}

function cellKey(cell: GridCell): string {
  return `${cell.x},${cell.y}`;
}

// 一直線上に並んだ鳴った部屋の間の gap 区間を返す
function straightGapSegments(cells: GridCell[], axis: "x" | "y"): GridCell[][] {
  const sorted = [...cells].sort((a, b) => a[axis] - b[axis]);
  const segments: GridCell[][] = [];
  for (let i = 0; i < sorted.length - 1; i++) {
    const from = sorted[i];
    const to = sorted[i + 1];
    if (to[axis] - from[axis] <= 1) continue;
    const segment = [from];
    for (let v = from[axis] + 1; v < to[axis]; v++) {
      segment.push(axis === "x" ? { x: v, y: from.y } : { x: from.x, y: v });
    }
    segment.push(to);
    segments.push(segment);
  }
  return segments;
}

// 角 (cx, cy) の L字経路としてすべての鳴った部屋を説明できる場合、
// 経路順に並べたセル列（両端は鳴った部屋）を返す。できなければ null
function orderedLPathCells(cells: GridCell[], cx: number, cy: number): GridCell[] | null {
  if (!cells.every((c) => c.x === cx || c.y === cy)) return null;

  const verticalYs = cells.filter((c) => c.x === cx).map((c) => c.y);
  const horizontalXs = cells.filter((c) => c.y === cy).map((c) => c.x);
  // 角は各辺の端点なので、辺上の部屋が角を跨いで両側にある並び（T字・十字）は L字ではない
  if (Math.min(...verticalYs) < cy && Math.max(...verticalYs) > cy) return null;
  if (Math.min(...horizontalXs) < cx && Math.max(...horizontalXs) > cx) return null;

  const farY = verticalYs.reduce((far, y) => (Math.abs(y - cy) > Math.abs(far - cy) ? y : far), cy);
  const farX = horizontalXs.reduce(
    (far, x) => (Math.abs(x - cx) > Math.abs(far - cx) ? x : far),
    cx,
  );
  // どちらかの辺に鳴った部屋がなければ L字ではなく直線として扱われるべき並び
  if (farY === cy || farX === cx) return null;

  const path: GridCell[] = [];
  const yStep = farY < cy ? 1 : -1;
  for (let y = farY; y !== cy; y += yStep) path.push({ x: cx, y });
  path.push({ x: cx, y: cy });
  const xStep = farX < cx ? -1 : 1;
  for (let x = cx + xStep; x !== farX + xStep; x += xStep) path.push({ x, y: cy });
  return path;
}

// 経路順セル列を歩き、鳴った部屋の間に鳴らなかったセルが挟まる区間を切り出す
function gapSegmentsAlongPath(path: GridCell[], audibleKeys: Set<string>): GridCell[][] {
  const segments: GridCell[][] = [];
  let current: GridCell[] | null = null;
  for (const cell of path) {
    if (audibleKeys.has(cellKey(cell))) {
      if (current && current.length > 1) {
        current.push(cell);
        segments.push(current);
      }
      current = [cell];
    } else if (current) {
      current.push(cell);
    }
  }
  return segments;
}

// 鳴った部屋のセル集合から補完すべき gap 区間を求める。
// 各区間は「鳴った部屋 → 鳴らなかったセル列 → 鳴った部屋」の経路順セル列
export function interpolateFootstepGapSegments(audibleCells: GridCell[]): GridCell[][] {
  if (audibleCells.length < 2) return [];

  const xs = [...new Set(audibleCells.map((c) => c.x))];
  const ys = [...new Set(audibleCells.map((c) => c.y))];
  if (ys.length === 1) return straightGapSegments(audibleCells, "x");
  if (xs.length === 1) return straightGapSegments(audibleCells, "y");

  const audibleKeys = new Set(audibleCells.map(cellKey));
  const segments: GridCell[][] = [];
  const seenSegmentKeys = new Set<string>();
  for (const cx of xs) {
    for (const cy of ys) {
      const path = orderedLPathCells(audibleCells, cx, cy);
      if (!path) continue;
      for (const segment of gapSegmentsAlongPath(path, audibleKeys)) {
        const keys = segment.map(cellKey);
        const segmentKey = keys.join(">");
        if (seenSegmentKeys.has(segmentKey)) continue;
        seenSegmentKeys.add(segmentKey);
        seenSegmentKeys.add(keys.reverse().join(">"));
        segments.push(segment);
      }
    }
  }
  return segments;
}
