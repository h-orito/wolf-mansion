import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import {
  fetchMyself,
  fetchVillage,
  fetchVillageFootsteps,
  fetchVillageMessages,
  type MessagesView,
  type MyselfView,
  type VillageFootstepsView,
  type VillageView,
} from "./api";

/** 30s polling — plan.md「TanStack Query `refetchInterval: 30000`」に合わせる */
const POLL_INTERVAL_MS = 30_000;

export function useVillageQuery(
  villageId: number,
  initialData?: VillageView,
): UseQueryResult<VillageView> {
  return useQuery<VillageView>({
    queryKey: ["village", villageId],
    queryFn: () => fetchVillage(villageId),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    refetchInterval: POLL_INTERVAL_MS,
    staleTime: POLL_INTERVAL_MS,
  });
}

export function useVillageMessagesQuery(
  villageId: number,
  day: number | undefined,
  initialData?: MessagesView,
): UseQueryResult<MessagesView> {
  return useQuery<MessagesView>({
    queryKey: ["village", villageId, "messages", day ?? "latest"],
    queryFn: () => fetchVillageMessages(villageId, day),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    refetchInterval: POLL_INTERVAL_MS,
    staleTime: POLL_INTERVAL_MS,
  });
}

export function useVillageFootstepsQuery(
  villageId: number,
  initialData?: VillageFootstepsView,
): UseQueryResult<VillageFootstepsView> {
  return useQuery<VillageFootstepsView>({
    queryKey: ["village", villageId, "footsteps"],
    queryFn: () => fetchVillageFootsteps(villageId),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    refetchInterval: POLL_INTERVAL_MS,
    staleTime: POLL_INTERVAL_MS,
  });
}

export function useMyselfQuery(
  villageId: number,
  initialData?: MyselfView | null,
): UseQueryResult<MyselfView | null> {
  return useQuery<MyselfView | null>({
    queryKey: ["village", villageId, "myself"],
    queryFn: () => fetchMyself(villageId),
    // null も valid な initial value として扱う
    initialData,
    initialDataUpdatedAt: initialData !== undefined ? Date.now() : undefined,
    refetchInterval: POLL_INTERVAL_MS,
    staleTime: POLL_INTERVAL_MS,
  });
}
