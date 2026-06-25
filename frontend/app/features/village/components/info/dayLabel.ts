/**
 * 日付の表示ラベル。0 日目はプロローグ、エピローグ日はエピローグ、その翌日は終了。
 */
export function dayLabel(day: number, epilogueDay: number | null | undefined): string {
  if (day === 0) return "プロローグ";
  if (epilogueDay != null) {
    if (day === epilogueDay) return "エピローグ";
    if (day - 1 === epilogueDay) return "終了";
  }
  return `${day}日目`;
}
