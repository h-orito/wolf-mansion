import { useQuery } from "@tanstack/react-query";

import { apiFetch } from "~/lib/api";

type StatsRecord = {
  participateNum: number;
  winNum: number;
  winRate: number;
};

type VillageEntry = {
  villageId: number;
  villageName: string;
  characterName: string;
  characterImgUrl: string;
  characterImgWidth: number;
  characterImgHeight: number;
  skillName: string;
  liveStatus: string;
  campName: string;
  winStatus: string;
};

export type PlayerProfile = {
  twitterUserName: string | null;
  introduction: string | null;
  wholeStats: StatsRecord;
  campStatsList: { campName: string; stats: StatsRecord }[];
  skillStatsList: { skillName: string; stats: StatsRecord }[];
  participateVillageList: VillageEntry[];
  spectateVillageList: VillageEntry[];
};

export function usePlayerProfile(name: string) {
  return useQuery({
    queryKey: ["player", name],
    queryFn: () => apiFetch<PlayerProfile>(`/api/v1/players/${encodeURIComponent(name)}`),
  });
}
