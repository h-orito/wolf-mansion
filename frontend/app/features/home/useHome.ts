import { useQuery } from "@tanstack/react-query";

import { fetchHome } from "./api";

export const HOME_QUERY_KEY = ["home"] as const;

/**
 * 開催中の村一覧 + 村作成可否を取得する。公開情報だが apiFetch は CSR 専用 (相対 URL + Cookie) なので
 * クライアントでのみ実行される (react-query は SSR では prefetch しない限り queryFn を呼ばない)。
 * ログイン状態で `canCreateVillage` が変わるため、ログイン/ログアウト後は本クエリも無効化する。
 */
export function useHome() {
  return useQuery({
    queryKey: HOME_QUERY_KEY,
    queryFn: fetchHome,
    retry: false,
  });
}
