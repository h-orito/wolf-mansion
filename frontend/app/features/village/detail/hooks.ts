import {
  useMutation,
  useQueries,
  useQuery,
  useQueryClient,
  type UseMutationResult,
  type UseQueryResult,
} from "@tanstack/react-query";
import {
  deleteLeave,
  fetchAdminPlayers,
  fetchAttackTargets,
  fetchFaceTypes,
  fetchFootstepCandidates,
  fetchMyself,
  fetchSelectableCharas,
  fetchVillage,
  fetchVillageFootsteps,
  fetchVillageMessages,
  postAbility,
  postAdminForceAccess,
  postAdminForceLeave,
  postAdminForceVote,
  postCancelVillage,
  postCreatorSay,
  postExtendEpilogue,
  postKick,
  postParticipate,
  postSay,
  postShortenEpilogue,
  postSwitchParticipate,
  postVote,
  putChangeName,
  putChangeRequestSkill,
  putCommit,
  putFaceTypes,
  putMemo,
  type CharaView,
  type MessagesView,
  type MyselfFaceTypesView,
  type MyselfView,
  type SayInput,
  type VillageAbilityBody,
  type VillageAdminLeaveBody,
  type VillageAdminPlayerView,
  type VillageChangeNameBody,
  type VillageChangeRequestSkillBody,
  type VillageCommitBody,
  type VillageCreatorSayBody,
  type VillageFaceTypeModifyBody,
  type VillageFootstepsView,
  type VillageKickBody,
  type VillageMemoBody,
  type VillageParticipateBody,
  type VillageView,
  type VillageVoteBody,
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
 * GET /api/v1/villages/{id}/participate/selectable-charas を charachipId ごとに並列発行し、
 * 全チップのキャラを結合した一覧を返す。複数キャラチップ村に対応するため。
 *
 * - 空配列 → クエリ自体を発行せず `{ data: [], isLoading: false, isError: false }`
 * - すべてのクエリが完了したら `isLoading=false`、いずれか失敗していれば `isError=true`
 *
 * Hooks のルールに従い、useQueries は常に同じ引数構造で呼び出す (charachipIds が
 * 空のときは queries=[] のまま useQueries を呼ぶ)。
 */
export function useSelectableCharasQuery(
  villageId: number,
  charachipIds: number[],
): { data: CharaView[]; isLoading: boolean; isError: boolean; error?: Error } {
  const results = useQueries({
    queries: charachipIds.map((charachipId) => ({
      queryKey: ["village", villageId, "selectable-charas", charachipId] as const,
      queryFn: () => fetchSelectableCharas(villageId, charachipId),
      staleTime: 60_000,
    })),
  });
  const isLoading = results.some((r) => r.isLoading);
  const errorResult = results.find((r) => r.isError);
  const isError = errorResult != null;
  const data: CharaView[] = isLoading || isError
    ? []
    : results.flatMap((r) => r.data ?? []);
  return { data, isLoading, isError, error: errorResult?.error ?? undefined };
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

// ---------- ability / vote / commit ----------

/**
 * 能力 / 投票 / commit を更新したら、myself (当日の選択状態) と messages
 * (能力セットの非公開メッセージ反映) をどちらも refetch する。
 */
function invalidateActionState(
  queryClient: ReturnType<typeof useQueryClient>,
  villageId: number,
) {
  queryClient.invalidateQueries({ queryKey: ["village", villageId, "myself"] });
  queryClient.invalidateQueries({ queryKey: ["village", villageId, "messages"] });
}

export function useAbilityMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageAbilityBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageAbilityBody>({
    mutationFn: (body) => postAbility(villageId, body),
    onSuccess: () => invalidateActionState(queryClient, villageId),
  });
}

export function useVoteMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageVoteBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageVoteBody>({
    mutationFn: (body) => postVote(villageId, body),
    onSuccess: () => invalidateActionState(queryClient, villageId),
  });
}

export function useCommitMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageCommitBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageCommitBody>({
    mutationFn: (body) => putCommit(villageId, body),
    onSuccess: () => invalidateActionState(queryClient, villageId),
  });
}

// ---------- RP (キャラ名 / メモ / 表情差分) ----------

/**
 * RP 系操作後は myself (キャラ名 / メモ / RP 可否) と、表示名が映る箇所 (村ヘッダ /
 * 参加者一覧 / 発言) を更新する。
 *
 * `invalidateQueries({ queryKey: ["village", villageId] })` は prefix match なので
 * `["village", villageId]` 本体に加えて `["village", villageId, "myself"]` /
 * `["village", villageId, "messages"]` / `["village", villageId, "footsteps"]` 等
 * 全ての配下クエリを invalidate する (= キャラ名変更時に myself も refetch される)。
 */
function invalidateRp(queryClient: ReturnType<typeof useQueryClient>, villageId: number) {
  queryClient.invalidateQueries({ queryKey: ["village", villageId] });
}

export function useChangeNameMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageChangeNameBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageChangeNameBody>({
    mutationFn: (body) => putChangeName(villageId, body),
    onSuccess: () => invalidateRp(queryClient, villageId),
  });
}

export function useMemoMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageMemoBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageMemoBody>({
    mutationFn: (body) => putMemo(villageId, body),
    onSuccess: () => {
      // memo は myself にしか乗っていない (村全体には出していない) ので myself のみ refetch
      queryClient.invalidateQueries({ queryKey: ["village", villageId, "myself"] });
    },
  });
}

/**
 * GET /api/v1/villages/{id}/rp/face-types: 表情差分の編集元データ。
 * `enabled=false` でクエリを抑止 (オリジナルキャラチップ村でないときは fetch しない)。
 */
export function useFaceTypesQuery(
  villageId: number,
  enabled: boolean,
): UseQueryResult<MyselfFaceTypesView> {
  return useQuery<MyselfFaceTypesView>({
    queryKey: ["village", villageId, "face-types"],
    queryFn: () => fetchFaceTypes(villageId),
    enabled,
    staleTime: 60_000,
  });
}

export function useFaceTypesMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageFaceTypeModifyBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageFaceTypeModifyBody>({
    mutationFn: (body) => putFaceTypes(villageId, body),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["village", villageId, "face-types"] });
    },
  });
}

/**
 * 襲撃者を選択するたびに、その襲撃者で襲撃可能な対象の charaId を fetch する。
 * `charaId` が null のときはクエリを発行せず空配列扱い。
 */
export function useAttackTargetsQuery(
  villageId: number,
  charaId: number | null,
): UseQueryResult<number[]> {
  return useQuery<number[]>({
    queryKey: ["village", villageId, "attack-targets", charaId],
    queryFn: () => fetchAttackTargets(villageId, charaId as number),
    enabled: charaId != null,
    staleTime: 30_000,
  });
}

/**
 * 能力主体 / 対象が変わるたびに足音候補を fetch する。
 * 「対象なし」しか選べない (例えば徘徊で target null のとき) ケースでも
 * backend が ['なし'] を返してくれる前提で常時 fetch。
 */
export function useFootstepCandidatesQuery(
  villageId: number,
  params: { charaId?: number | null; targetCharaId?: number | null },
  enabled: boolean,
): UseQueryResult<string[]> {
  return useQuery<string[]>({
    queryKey: [
      "village",
      villageId,
      "footstep-candidates",
      params.charaId ?? null,
      params.targetCharaId ?? null,
    ],
    queryFn: () =>
      fetchFootstepCandidates(villageId, {
        charaId: params.charaId ?? undefined,
        targetCharaId: params.targetCharaId ?? undefined,
      }),
    enabled,
    staleTime: 30_000,
  });
}

// ---------- Creator / Admin operations (Step 8e) ----------

/**
 * Creator/Admin 系の mutation 成功時は村の状態が大きく変わる可能性があるので
 * 村ヘッダ / 参加者 / 発言 / myself を一括で refetch する。
 */
function invalidateAll(
  queryClient: ReturnType<typeof useQueryClient>,
  villageId: number,
) {
  queryClient.invalidateQueries({ queryKey: ["village", villageId] });
}

export function useCreatorSayMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageCreatorSayBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageCreatorSayBody>({
    mutationFn: (body) => postCreatorSay(villageId, body),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["village", villageId, "messages"] });
    },
  });
}

export function useKickMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageKickBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageKickBody>({
    mutationFn: (body) => postKick(villageId, body),
    onSuccess: () => invalidateAll(queryClient, villageId),
  });
}

export function useCancelVillageMutation(
  villageId: number,
): UseMutationResult<void, Error, void> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, void>({
    mutationFn: () => postCancelVillage(villageId),
    onSuccess: () => invalidateAll(queryClient, villageId),
  });
}

export function useExtendEpilogueMutation(
  villageId: number,
): UseMutationResult<void, Error, void> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, void>({
    mutationFn: () => postExtendEpilogue(villageId),
    onSuccess: () => invalidateAll(queryClient, villageId),
  });
}

export function useShortenEpilogueMutation(
  villageId: number,
): UseMutationResult<void, Error, void> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, void>({
    mutationFn: () => postShortenEpilogue(villageId),
    onSuccess: () => invalidateAll(queryClient, villageId),
  });
}

/**
 * GET /api/v1/admin/villages/{id}/players — 管理者向けキャラ↔中の人一覧。
 * `enabled=false` のときはクエリを発行しない (= 管理者でないユーザでも safe)。
 */
export function useAdminPlayersQuery(
  villageId: number,
  enabled: boolean,
): UseQueryResult<VillageAdminPlayerView[]> {
  return useQuery<VillageAdminPlayerView[]>({
    queryKey: ["village", villageId, "admin", "players"],
    queryFn: () => fetchAdminPlayers(villageId),
    enabled,
    staleTime: 30_000,
  });
}

export function useAdminForceAccessMutation(
  villageId: number,
): UseMutationResult<void, Error, void> {
  return useMutation<void, Error, void>({
    mutationFn: () => postAdminForceAccess(villageId),
  });
}

export function useAdminForceVoteMutation(
  villageId: number,
): UseMutationResult<void, Error, void> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, void>({
    mutationFn: () => postAdminForceVote(villageId),
    onSuccess: () => invalidateActionState(queryClient, villageId),
  });
}

export function useAdminForceLeaveMutation(
  villageId: number,
): UseMutationResult<void, Error, VillageAdminLeaveBody> {
  const queryClient = useQueryClient();
  return useMutation<void, Error, VillageAdminLeaveBody>({
    mutationFn: (body) => postAdminForceLeave(villageId, body),
    onSuccess: () => invalidateAll(queryClient, villageId),
  });
}
