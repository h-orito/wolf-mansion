import type { VillageDetailView } from "~/features/village/api";

export function resolveParticipantName(village: VillageDetailView, charaId: number): string {
  const all = [...(village.participants.list ?? []), ...(village.spectators.list ?? [])];
  return all.find((p) => p.chara.id === charaId)?.name ?? `(${charaId})`;
}
