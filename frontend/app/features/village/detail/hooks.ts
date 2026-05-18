import { useMutation, useQuery, useQueryClient, type UseMutationResult, type UseQueryResult } from "@tanstack/react-query";
import {
  deleteLeave,
  fetchMyself,
  fetchSelectableCharas,
  fetchVillage,
  fetchVillageFootsteps,
  fetchVillageMessages,
  postParticipate,
  postSay,
  postSwitchParticipate,
  putChangeRequestSkill,
  type CharaView,
  type MessagesView,
  type MyselfView,
  type SayInput,
  type VillageChangeRequestSkillBody,
  type VillageFootstepsView,
  type VillageParticipateBody,
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

/**
 * POST /api/v1/villages/{id}/messages を mutation で叩く。
 * 成功時は messages query を invalidate して最新一覧を再取得させる。
 */
export function useSayMutation(villageId: number): UseMutationResult<void, Error, SayInput> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, SayInput>({
    mutationFn: (input) => postSay(villageId, input),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["village", villageId, "messages"] });
    },
  });
}

/** 参加系操作後に村ヘッダ / myself / messages を refetch させる共通 invalidator */
function invalidateVillage(queryClient: ReturnType<typeof useQueryClient>, villageId: number) {
  queryClient.invalidateQueries({ queryKey: ["village", villageId] });
}

/**
 * GET /api/v1/villages/{id}/participate/selectable-charas
 *
 * 村のキャラチップ ID ごとに 1 クエリ。プロローグ + 未参加時のみ有効化する想定。
 */
export function useSelectableCharasQuery(
  villageId: number,
  charachipId: number | null,
): UseQueryResult<CharaView[]> {
  return useQuery<CharaView[]>({
    queryKey: ["village", villageId, "selectable-charas", charachipId],
    queryFn: () => fetchSelectableCharas(villageId, charachipId!),
    enabled: charachipId != null,
    staleTime: 60_000,
  });
}

export function useParticipateMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageParticipateBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageParticipateBody>({
    mutationFn: (body) => postParticipate(villageId, body),
    onSuccess: () => invalidateVillage(queryClient, villageId),
  });
}

export function useSwitchParticipateMutation(
  villageId: number,
): UseMutationResult<void, Error, void> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, void>({
    mutationFn: () => postSwitchParticipate(villageId),
    onSuccess: () => invalidateVillage(queryClient, villageId),
  });
}

export function useChangeRequestSkillMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageChangeRequestSkillBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageChangeRequestSkillBody>({
    mutationFn: (body) => putChangeRequestSkill(villageId, body),
    onSuccess: () => invalidateVillage(queryClient, villageId),
  });
}

export function useLeaveMutation(villageId: number): UseMutationResult<void, Error, void> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, void>({
    mutationFn: () => deleteLeave(villageId),
    onSuccess: () => invalidateVillage(queryClient, villageId),
  });
}
