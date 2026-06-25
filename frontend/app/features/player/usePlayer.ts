import { useQuery } from "@tanstack/react-query";

import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

export type PlayerProfile = components["schemas"]["PlayerRecordsContent"];

export function usePlayerProfile(name: string) {
  return useQuery({
    queryKey: ["player", name],
    queryFn: () => apiFetch<PlayerProfile>(`/api/v1/players/${encodeURIComponent(name)}`),
  });
}
