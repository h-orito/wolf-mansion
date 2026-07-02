/** 死因コード → 部屋割りグリッドの死亡マーク。 */
export function deadMark(deadReason: string | null | undefined): string {
  switch (deadReason) {
    case "SUDDON":
      return "凸";
    case "EXECUTE":
      return "▼";
    case "SUICIDE":
      return "❤︎";
    default:
      return "▲";
  }
}

/**
 * 無惨系 (襲撃/呪殺/罠死/爆死/雑魚) の死因コード。部屋番号一覧で赤表示する。
 * MISERABLE は analyzer API (DeadReasonView) が無惨系を集約したコード。
 */
const MISERABLE_REASONS = ["ATTACK", "DIVINED", "TRAPPED", "BOMBED", "ZAKO", "MISERABLE"];

/** 死因コード → 部屋番号一覧の文字色 (生存・該当なしは null)。 */
export function deadColor(deadReason: string | null | undefined): string | null {
  if (deadReason == null) return null;
  if (MISERABLE_REASONS.includes(deadReason)) return "#ff0000";
  if (deadReason === "EXECUTE" || deadReason === "SUDDON") return "#3498db";
  if (deadReason === "SUICIDE") return "#db3498";
  return null;
}
