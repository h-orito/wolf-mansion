import { useQuery } from "@tanstack/react-query";

import { fetchVillages, type VillageStatusFilter } from "./api";

export const VILLAGES_QUERY_KEY = "villages";

/**
 * 村一覧を取得する。公開情報だが apiFetch は CSR 専用 (相対 URL + Cookie) なのでクライアントでのみ実行される
 * (react-query は SSR では prefetch しない限り queryFn を呼ばない)。status ごとにキャッシュを分ける。
 */
export function useVillages(status: VillageStatusFilter = "all") {
  return useQuery({
    queryKey: [VILLAGES_QUERY_KEY, status],
    queryFn: () => fetchVillages(status),
    retry: false,
  });
}
