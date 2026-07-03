import type { VillageDetailView, VillageParticipantView } from "~/features/village/api";

export function resolveParticipantName(village: VillageDetailView, charaId: number): string {
  const all = allParticipants(village);
  return all.find((p) => p.chara.id === charaId)?.name ?? `(${charaId})`;
}

export function allParticipants(village: VillageDetailView): VillageParticipantView[] {
  return [...(village.participants.list ?? []), ...(village.spectators.list ?? [])];
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
