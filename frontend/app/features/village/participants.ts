import type { VillageDetailView, VillageParticipantView } from "~/features/village/api";

export function resolveParticipantName(village: VillageDetailView, charaId: number): string {
  const all = allParticipants(village);
  return all.find((p) => p.chara.id === charaId)?.name ?? `(${charaId})`;
}

export function allParticipants(village: VillageDetailView): VillageParticipantView[] {
  return [...(village.participants.list ?? []), ...(village.spectators.list ?? [])];
}
