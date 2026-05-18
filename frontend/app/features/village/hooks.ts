import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import { fetchVillages, type VillagesView, type VillageStatusCode } from "./api";

/** TanStack Query 用のキー。statuses 配列はソート済み文字列にしてキー安定化 */
function villagesQueryKey(statuses: VillageStatusCode[] | undefined) {
  return ["villages", { statuses: (statuses ?? []).slice().sort() }] as const;
}

/**
 * GET /api/v1/villages を TanStack Query で取得。
 * - SSR で取得したデータを `initialData` として渡すと、初回マウント時の追加 fetch を避けられる。
 *   `initialDataUpdatedAt` を指定しないと v5 では epoch (1970) 起点で stale 判定されて
 *   必ず refetch が走り、hydration mismatch のリスクがあるので Date.now() を渡す。
 */
export function useVillagesQuery(
  params: { statuses?: VillageStatusCode[] } = {},
  initialData?: VillagesView,
): UseQueryResult<VillagesView> {
  return useQuery<VillagesView>({
    queryKey: villagesQueryKey(params.statuses),
    queryFn: () => fetchVillages(params),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    staleTime: 30 * 1000,
  });
}
