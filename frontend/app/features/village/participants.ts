import type { VillageDetailView, VillageParticipantView } from "~/features/village/api";

export function resolveParticipantName(village: VillageDetailView, charaId: number): string {
  const all = allParticipants(village);
  return all.find((p) => p.chara.id === charaId)?.name ?? `(${charaId})`;
}

export function allParticipants(village: VillageDetailView): VillageParticipantView[] {
  return [...(village.participants.list ?? []), ...(village.spectators.list ?? [])];
}

/**
 * 部屋番号昇順に並べ替えた新しい配列を返す。
 * 部屋を持たない参加者 (部屋割り前・部屋なし村・見学者) は元の順のまま末尾に置く。
 */
export function sortByRoomNumber(participants: VillageParticipantView[]): VillageParticipantView[] {
  return [...participants].sort(
    (a, b) =>
      (a.room?.number ?? Number.MAX_SAFE_INTEGER) - (b.room?.number ?? Number.MAX_SAFE_INTEGER),
  );
}
