import { useQuery, useQueryClient } from "@tanstack/react-query";

import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";
import { fetchPlayers } from "./api";

export type PlayerProfile = components["schemas"]["PlayerRecordsContent"];
export type PlayerDetailRequest = components["schemas"]["PlayerDetailRequest"];

export function usePlayers(pageNum: number) {
  return useQuery({
    queryKey: ["players", pageNum],
    queryFn: () => fetchPlayers(pageNum),
  });
}

export function usePlayerProfile(name: string) {
  return useQuery({
    queryKey: ["player", name],
    queryFn: () => apiFetch<PlayerProfile>(`/api/v1/players/${encodeURIComponent(name)}`),
  });
}

export function useUpdatePlayerDetail(name: string) {
  const queryClient = useQueryClient();
  return async (request: PlayerDetailRequest) => {
    await apiFetch<void>("/api/v1/players/me/detail", {
      method: "PUT",
      body: request,
    });
    await queryClient.invalidateQueries({ queryKey: ["player", name] });
  };
}
