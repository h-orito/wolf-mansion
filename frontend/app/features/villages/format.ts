import type { SimpleVillageView } from "./api";

/**
 * 村一覧の表示整形 (既存 :8091 の IndexContent 相当)。
 * backend はドメイン構造に近い生データ ([SimpleVillageView]) を返すので、表示文字列の組み立ては画面側のここで行う。
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
  return village.status.isPrologue
    ? `${village.participantCount}/${village.setting.personMax}${spectator}人`
    : `${village.participantCount}${spectator}人`;
}

/**
 * 村一覧画面の参加人数表示 (:8091 `VillageListContent.mapParticipateNum` 相当)。
 * トップ ([participateNumLabel]) と違い、募集中でも定員 (`/max`) は出さない。
 * - 見学あり: `参加 (見学)人`
 * - 見学なし: `参加人`
 */
export function villageListParticipateNum(village: SimpleVillageView): string {
  const spectator = village.spectatorCount > 0 ? ` (${village.spectatorCount})` : "";
  return `${village.participantCount}${spectator}人`;
}

/** 村タグ (village_tag_item) の code。backend はタグを生データで返すので、絞り込み・色付けは画面側で行う。 */
const TAG_R15 = "R15";
const TAG_R18 = "R18";
const TAG_ANYONE_WELCOME = "ANYONE_WELCOME";
const TAG_RELATIVES_ONLY = "RELATIVES_ONLY";

/** 一覧で出すタグ (年齢制限 → 歓迎区分の順)。`danger` で赤系 / それ以外でアクセント色にする。 */
export type VillageListTag = { name: string; danger: boolean };

/**
 * 村一覧の行に出すタグを抽出する (既存 :8091 と同じく 年齢制限 1 つ + 歓迎区分 1 つ)。
 * 年齢制限 (R15/R18) は赤、歓迎区分 (誰歓/身内) はアクセント色。
 */
export function villageListTags(village: SimpleVillageView): VillageListTag[] {
  const tags = village.setting.tags;
  const age = tags.find((t) => t.code === TAG_R15 || t.code === TAG_R18);
  const welcome = tags.find((t) => t.code === TAG_ANYONE_WELCOME || t.code === TAG_RELATIVES_ONLY);
  const result: VillageListTag[] = [];
  if (age) result.push({ name: age.name, danger: true });
  if (welcome) result.push({ name: welcome.name, danger: false });
  return result;
}
