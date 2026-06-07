import type { SimpleVillageView } from "./api";

/**
 * 村一覧の表示整形 (既存 :8091 の IndexContent 相当)。
 * backend は生データ ([SimpleVillageView]) を返すので、表示文字列の組み立ては画面側のここで行う。
 */

/** 村表示番号 (ID を 4 桁 0 埋め)。 */
export function villageNumber(id: number): string {
  return String(id).padStart(4, "0");
}

/**
 * 参加人数の表示文字列。
 * - 募集中: `参加/定員 (見学)人`
 * - それ以外: `参加 (見学)人`
 * 見学が 0 のときは `(見学)` を省略する。
 */
export function participateNumLabel(village: SimpleVillageView): string {
  const spectator = village.spectatorCount > 0 ? ` (${village.spectatorCount})` : "";
  return village.isPrologue
    ? `${village.participantCount}/${village.maxPersonCount}${spectator}人`
    : `${village.participantCount}${spectator}人`;
}
