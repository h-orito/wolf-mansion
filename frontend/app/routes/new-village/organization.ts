/**
 * 固定編成テキストの扱い。
 *
 * 既定編成の正本は backend の `VillageOrganize.defaultFixedOrganization` (8〜20人)。
 * 村作成 API は確認モーダル実装時に接続するため、初期表示用の既定値としてここに持つ。
 */
export const DEFAULT_FIXED_ORGANIZATION = [
  "村狼狼賢導村村村",
  "村狼狼賢導村村村村",
  "村狼狼狂賢導村村村村",
  "村狼狼狂賢導村村村村村",
  "村狼狼狼狂賢導狩村村村村",
  "村狼狼狼狂賢導狩村村村村村",
  "村狼狼狼魔狐賢導狩霊霊霊霊霊",
  "村狼狼狼魔狐賢導狩霊霊霊霊霊霊",
  "村狼狼狼魔狐賢導狩霊霊霊霊霊共共",
  "村狼狼狼魔狐賢導狩霊霊霊霊霊霊共共",
  "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊共共",
  "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊共共",
  "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊霊共共",
].join("\n");

/**
 * 表示用に各行へ「N人：」プレフィックスを付ける。
 * テキストエリアの値はプレフィックス込みで保持し、backend は編成本体のテキストだけを
 * 期待するため送信時に取り除く。
 */
export function addPersonCountPrefix(organization: string): string {
  return organization
    .split("\n")
    .map((line) => `${line.length}人：${line}`)
    .join("\n");
}

/** 「N人：」プレフィックスを取り除き、編成本体のテキストへ戻す。 */
export function stripPersonCountPrefix(organization: string): string {
  return organization
    .split("\n")
    .map((line) => line.replace(/^\d+人：/, ""))
    .join("\n");
}
