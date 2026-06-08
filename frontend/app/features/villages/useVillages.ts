import { useQuery } from "@tanstack/react-query";

import { fetchVillages, fetchVillageSearchCandidates, type VillageFilter } from "./api";

export const VILLAGES_QUERY_KEY = "villages";
export const VILLAGE_SEARCH_CANDIDATES_QUERY_KEY = "villageSearchCandidates";

/**
 * 村一覧を取得する。公開情報だが apiFetch は CSR 専用 (相対 URL + Cookie) なのでクライアントでのみ実行される
 * (react-query は SSR では prefetch しない限り queryFn を呼ばない)。絞り込み条件ごとにキャッシュを分ける
 * (queryKey に filter オブジェクトを含め、react-query の構造比較でキャッシュを区別する)。
 *
 * @param filter 絞り込み条件。省略・空オブジェクトなら全件。
 */
export function useVillages(filter: VillageFilter = {}) {
  return useQuery({
    queryKey: [VILLAGES_QUERY_KEY, filter],
    queryFn: () => fetchVillages(filter),
    // backend は村番号の昇順で返すが、legacy (:8091) はトップ・村一覧とも新しい村を上に表示する
    // (IndexContent / VillageListContent がいずれも `reversed()`)。表示整形は frontend 責務なので
    // ここで降順 (新しい村が上) にする。元配列は破壊しない。
    select: (data) => ({ ...data, villages: [...data.villages].reverse() }),
    retry: false,
  });
}

/**
 * 村一覧画面の検索候補 (キャラセット一覧 / 役職一覧) を取得する。
 * 候補は実質不変なので `staleTime: Infinity` でキャッシュを使い回す。
 */
export function useVillageSearchCandidates() {
  return useQuery({
    queryKey: [VILLAGE_SEARCH_CANDIDATES_QUERY_KEY],
    queryFn: fetchVillageSearchCandidates,
    staleTime: Infinity,
    retry: false,
  });
}
