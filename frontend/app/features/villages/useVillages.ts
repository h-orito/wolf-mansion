import { useQuery } from "@tanstack/react-query";

import { fetchVillages } from "./api";

export const VILLAGES_QUERY_KEY = "villages";

/**
 * 村一覧を取得する。公開情報だが apiFetch は CSR 専用 (相対 URL + Cookie) なのでクライアントでのみ実行される
 * (react-query は SSR では prefetch しない限り queryFn を呼ばない)。status code の組み合わせごとにキャッシュを分ける。
 *
 * @param statuses village_status の code 配列。省略・空配列なら全件。
 */
export function useVillages(statuses: string[] = []) {
  return useQuery({
    queryKey: [VILLAGES_QUERY_KEY, statuses],
    queryFn: () => fetchVillages(statuses),
    retry: false,
  });
}
