import { useQuery } from "@tanstack/react-query";

import { fetchCharachips, fetchSkills, fetchVillages, type VillageFilter } from "./api";

export const VILLAGES_QUERY_KEY = "villages";
export const CHARACHIPS_QUERY_KEY = "charachips";
export const SKILLS_QUERY_KEY = "skills";

/**
 * 村一覧を取得する。公開情報だが apiFetch は CSR 専用 (相対 URL + Cookie) なのでクライアントでのみ実行される
 * (react-query は SSR では prefetch しない限り queryFn を呼ばない)。絞り込み条件ごとにキャッシュを分ける
 * (queryKey に filter オブジェクトを含め、react-query の構造比較でキャッシュを区別する)。並び順は API 側で
 * 既定が降順 (新しい村が先)。
 *
 * @param filter 絞り込み条件。省略・空オブジェクトなら全件。
 */
export function useVillages(filter: VillageFilter = {}) {
  return useQuery({
    queryKey: [VILLAGES_QUERY_KEY, filter],
    queryFn: () => fetchVillages(filter),
    retry: false,
  });
}

/** キャラセット一覧を取得する。実質不変なので長期キャッシュする。 */
export function useCharachips() {
  return useQuery({
    queryKey: [CHARACHIPS_QUERY_KEY],
    queryFn: fetchCharachips,
    staleTime: Infinity,
    retry: false,
  });
}

/** 役職一覧を取得する。実質不変なので長期キャッシュする。 */
export function useSkills() {
  return useQuery({
    queryKey: [SKILLS_QUERY_KEY],
    queryFn: fetchSkills,
    staleTime: Infinity,
    retry: false,
  });
}
