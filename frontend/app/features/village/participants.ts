import type { VillageDetailView, VillageParticipantView } from "~/features/village/api";

export function resolveParticipantName(village: VillageDetailView, charaId: number): string {
  const all = allParticipants(village);
  return all.find((p) => p.chara.id === charaId)?.name ?? `(${charaId})`;
}

export function allParticipants(village: VillageDetailView): VillageParticipantView[] {
  return [...(village.participants.list ?? []), ...(village.spectators.list ?? [])];
}

/** participantId から charaId を引く (部屋割データは participantId しか持たないため)。 */
export function resolveCharaId(village: VillageDetailView, participantId: number): number | null {
  return allParticipants(village).find((p) => p.id === participantId)?.chara.id ?? null;
}

/**
 * 部屋番号順の比較関数。backend の VillageParticipants.sortedByRoomNumber と同じセマンティクス:
 * 見学者を末尾に置き、部屋番号昇順 (部屋を持たない参加者は先頭)、同順位は chara.id 昇順。
 */
export function compareByRoomNumber(a: VillageParticipantView, b: VillageParticipantView): number {
  return (
    Number(a.isSpectator) - Number(b.isSpectator) ||
    (a.room?.number ?? 0) - (b.room?.number ?? 0) ||
    a.chara.id - b.chara.id
  );
}

/** 部屋番号順 (compareByRoomNumber) に並べ替えた新しい配列を返す。 */
export function sortByRoomNumber(participants: VillageParticipantView[]): VillageParticipantView[] {
  return [...participants].sort(compareByRoomNumber);
}

/**
 * 役職履歴＋状態の表示文字列 (エピローグ以降の参加者一覧向け)。
 * 1 日目は役職名のみ、以降は `{day}d{役職名}` を " → " で連結し、
 * 状態ラベルがあれば `（恋絆、狂気）` の形で付記する。
 * 例: `人狼 → 3dトラック → 6d村人（恋絆）`
 */
export function formatSkillHistory(p: VillageParticipantView): string {
  if (p.isSpectator) return "見学参加";
  if (p.skill == null) return "";
  const history =
    p.skill.histories.length > 0
      ? p.skill.histories.map((h) => (h.day === 1 ? h.name : `${h.day}d${h.name}`)).join(" → ")
      : p.skill.name;
  const statuses = p.statuses ?? [];
  return statuses.length === 0 ? history : `${history}（${statuses.join("、")}）`;
}
