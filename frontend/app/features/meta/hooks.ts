import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import {
  fetchCharachipDetail,
  fetchCharachips,
  fetchSkillCatalog,
  fetchVillageRecords,
  searchSkills,
  type CharachipDetailView,
  type CharachipView,
  type SkillCatalogView,
  type VillageRecordsView,
} from "./api";

// メタ情報は変動が少ないので staleTime を長めに (10 分)。明示的な refetch でのみ更新される。
const META_STALE_MS = 10 * 60_000;

export function useCharachipsQuery(
  initialData?: CharachipView[],
): UseQueryResult<CharachipView[]> {
  return useQuery<CharachipView[]>({
    queryKey: ["charachips"],
    queryFn: () => fetchCharachips(),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    staleTime: META_STALE_MS,
  });
}

export function useCharachipDetailQuery(
  charachipId: number,
  initialData?: CharachipDetailView,
): UseQueryResult<CharachipDetailView> {
  return useQuery<CharachipDetailView>({
    queryKey: ["charachips", charachipId],
    queryFn: () => fetchCharachipDetail(charachipId),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    staleTime: META_STALE_MS,
  });
}

export function useSkillCatalogQuery(
  initialData?: SkillCatalogView,
): UseQueryResult<SkillCatalogView> {
  return useQuery<SkillCatalogView>({
    queryKey: ["skills", "catalog"],
    queryFn: () => fetchSkillCatalog(),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    staleTime: META_STALE_MS,
  });
}

/**
 * 検索条件 (tags / name / villageId) が変わったら自動的に再 fetch する。
 * 検索フィルタ操作はインタラクティブなので staleTime は短め (30秒)。
 */
export function useSkillSearchQuery(
  params: { tags?: string; name?: string; villageId?: number },
  enabled: boolean,
): UseQueryResult<string[]> {
  return useQuery<string[]>({
    queryKey: ["skills", "search", params.tags ?? "", params.name ?? "", params.villageId ?? null],
    queryFn: () => searchSkills(params),
    enabled,
    staleTime: 30_000,
  });
}

export function useVillageRecordsQuery(
  initialData?: VillageRecordsView,
): UseQueryResult<VillageRecordsView> {
  return useQuery<VillageRecordsView>({
    queryKey: ["village-records"],
    queryFn: () => fetchVillageRecords(),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    staleTime: META_STALE_MS,
  });
}
