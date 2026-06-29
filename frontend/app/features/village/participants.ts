import type { VillageDetailView, VillageParticipantView } from "~/features/village/api";

export function resolveParticipantName(village: VillageDetailView, charaId: number): string {
  const all = allParticipants(village);
  return all.find((p) => p.chara.id === charaId)?.name ?? `(${charaId})`;
}

export function allParticipants(village: VillageDetailView): VillageParticipantView[] {
  return [...(village.participants.list ?? []), ...(village.spectators.list ?? [])];
}

export type FilterParticipant = {
  id: number;
  charaId: number;
  name: string;
  imgWidth: number;
  imgHeight: number;
  imgUrl: string;
  deadStatus: string | null;
};

export function toFilterParticipants(village: VillageDetailView): FilterParticipant[] {
  return allParticipants(village).map((p) => ({
    id: p.id,
    charaId: p.chara.id,
    name: p.name,
    imgWidth: p.chara.size.width,
    imgHeight: p.chara.size.height,
    imgUrl: p.chara.images.list[0]?.url ?? "",
    deadStatus: toDeadStatus(p),
  }));
}

function toDeadStatus(p: VillageParticipantView): string | null {
  if (p.isSpectator) return "見学";
  if (!p.dead.isDead) return "生存";
  return `${p.dead.deadDay}d${p.dead.reason?.name ?? ""}`;
}
