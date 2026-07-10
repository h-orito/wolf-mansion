import { useQuery, useQueryClient } from "@tanstack/react-query";

import { fetchRandomKeyword, fetchRandomKeywords } from "./api";

const QUERY_KEY_PREFIX = ["random-keywords"] as const;

export function useRandomKeywords(q: string = "", order?: "keyword") {
  return useQuery({
    queryKey: [...QUERY_KEY_PREFIX, "list", q, order ?? ""],
    queryFn: () => fetchRandomKeywords(q || undefined, order),
    retry: false,
  });
}

/** キーワード名一覧。発言欄のランダムタグプルダウンの表示順に合わせてキーワード名昇順で取得する。 */
export function useRandomKeywordList(): string[] {
  const { data } = useRandomKeywords("", "keyword");
  return (data ?? []).map((k) => k.keyword ?? "").filter(Boolean);
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
