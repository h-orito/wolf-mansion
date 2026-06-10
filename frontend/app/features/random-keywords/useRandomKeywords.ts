import { useQuery, useQueryClient } from "@tanstack/react-query";

import { fetchRandomKeyword, fetchRandomKeywords } from "./api";

const LIST_QUERY_KEY = ["random-keywords"] as const;

export function useRandomKeywords() {
  return useQuery({
    queryKey: LIST_QUERY_KEY,
    queryFn: fetchRandomKeywords,
    retry: false,
  });
}

export function useRandomKeyword(id: number, enabled: boolean = true) {
  return useQuery({
    queryKey: [...LIST_QUERY_KEY, id],
    queryFn: () => fetchRandomKeyword(id),
    enabled,
    retry: false,
  });
}

/** 作成・更新・削除後にキーワード系のキャッシュをまとめて捨てる。 */
export function useInvalidateRandomKeywords(): () => Promise<void> {
  const queryClient = useQueryClient();
  return () => queryClient.invalidateQueries({ queryKey: LIST_QUERY_KEY });
}
