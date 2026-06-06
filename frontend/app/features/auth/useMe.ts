import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect, useState } from "react";

import { ApiError } from "~/lib/api";
import { fetchMe, type MeResponse } from "./api";

export const ME_QUERY_KEY = ["auth", "me"] as const;

/** 401 は「未ログイン」= `null`。それ以外のエラー (ネットワーク等) は throw して react-query に委ねる。 */
async function fetchMeOrNull(): Promise<MeResponse | null> {
  try {
    return await fetchMe();
  } catch (e) {
    if (e instanceof ApiError && e.status === 401) return null;
    throw e;
  }
}

type UseMeResult = {
  /** ログイン中なら MeResponse、未ログインなら null。判定確定前は null + isLoading=true。 */
  me: MeResponse | null;
  isLoading: boolean;
};

/**
 * 現在のログイン状態を返す。**CSR 専用** (03-auth.md の SSR/CSR 認証境界)。
 * SSR / hydration 直後は Cookie 付き fetch が無意味なので、クライアントマウント後にのみ走らせる。
 * その間は isLoading=true を返し、呼び出し側はプレースホルダを出す。
 */
export function useMe(): UseMeResult {
  const [isClient, setIsClient] = useState(false);
  useEffect(() => setIsClient(true), []);

  const query = useQuery({
    queryKey: ME_QUERY_KEY,
    queryFn: fetchMeOrNull,
    enabled: isClient,
    retry: false,
  });

  return {
    me: query.data ?? null,
    isLoading: !isClient || query.isLoading,
  };
}

/**
 * ログイン/サインアップ成功後に me キャッシュを更新し、以降の useMe を即時反映する。
 * ログアウト時は null を渡す。
 */
export function useSetMe(): (me: MeResponse | null) => void {
  const queryClient = useQueryClient();
  return (me) => queryClient.setQueryData(ME_QUERY_KEY, me);
}

/**
 * me キャッシュを無効化し、次の参照でサーバから取り直させる。
 * 楽観更新が当てにならない場面 (ログアウト失敗時など) でサーバ実態に合わせるのに使う。
 */
export function useInvalidateMe(): () => Promise<void> {
  const queryClient = useQueryClient();
  return () => queryClient.invalidateQueries({ queryKey: ME_QUERY_KEY });
}
