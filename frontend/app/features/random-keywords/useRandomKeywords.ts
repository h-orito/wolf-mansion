import { useQuery, useQueryClient } from "@tanstack/react-query";

import { fetchRandomKeyword, fetchRandomKeywords } from "./api";

const QUERY_KEY_PREFIX = ["random-keywords"] as const;

export function useRandomKeywords(q: string = "") {
  return useQuery({
    queryKey: [...QUERY_KEY_PREFIX, "list", q],
    queryFn: () => fetchRandomKeywords(q || undefined),
    retry: false,
  });
}

export function useRandomKeyword(id: number, enabled: boolean = true) {
  return useQuery({
    queryKey: [...QUERY_KEY_PREFIX, "detail", id],
    queryFn: () => fetchRandomKeyword(id),
    enabled,
    retry: false,
  });
}

/** 作成・更新・削除後にキーワード系のキャッシュをまとめて捨てる。 */
export function useInvalidateRandomKeywords(): () => Promise<void> {
  const queryClient = useQueryClient();
  return () => queryClient.invalidateQueries({ queryKey: QUERY_KEY_PREFIX });
}
