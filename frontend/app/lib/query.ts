import { QueryClient } from "@tanstack/react-query";

/**
 * TanStack Query の QueryClient。
 * SSR 安全のため、リクエスト毎に新規 instance を作る。
 */
export function makeQueryClient(): QueryClient {
  return new QueryClient({
    defaultOptions: {
      queries: {
        // SSR 中に重複 fetch を抑止
        staleTime: 30 * 1000,
        // ネットワーク不調時に 1 回だけリトライ
        retry: 1,
        refetchOnWindowFocus: false,
      },
    },
  });
}
