import {
  useMutation,
  useQuery,
  type UseMutationResult,
  type UseQueryResult,
} from "@tanstack/react-query";
import {
  fetchNewVillageDefaults,
  postNewVillage,
  type CreatedVillageView,
  type NewVillageCreateBody,
  type NewVillageFormView,
} from "./api";

/**
 * 認証必須エンドポイント。未認証時は backend が 400 を返すため、`enabled` で抑止して
 * 認証済みのときだけ fetch する想定。
 */
export function useNewVillageDefaultsQuery(
  enabled: boolean,
  initialData?: NewVillageFormView,
): UseQueryResult<NewVillageFormView> {
  return useQuery<NewVillageFormView>({
    queryKey: ["new-village", "defaults"],
    queryFn: () => fetchNewVillageDefaults(),
    enabled,
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    // canCreate (= 進行中の自分の村が無いか) は他のフローで変わる可能性があるので
    // staleTime は短め (5分)。レイアウト崩れより最新性を優先。
    staleTime: 5 * 60_000,
  });
}

export function useCreateVillageMutation(): UseMutationResult<
  CreatedVillageView,
  Error,
  NewVillageCreateBody
> {
  return useMutation<CreatedVillageView, Error, NewVillageCreateBody>({
    mutationFn: (body) => postNewVillage(body),
  });
}
