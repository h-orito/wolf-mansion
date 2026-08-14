import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useMemo } from "react";

import { useMe } from "~/features/auth/useMe";
import { addFavoriteChara, fetchFavoriteCharachips, removeFavoriteChara } from "./api";

const FAVORITE_CHARAS_QUERY_KEY = ["favorite-charas"] as const;

/**
 * ログインプレイヤーのお気に入りキャラ (キャラチップ単位)。未ログイン時は取得しない。
 * ユーザー切替時に他人のキャッシュを見せないよう、キーにユーザー名を含める。
 */
export function useFavoriteCharachips() {
  const { me } = useMe();
  return useQuery({
    queryKey: [...FAVORITE_CHARAS_QUERY_KEY, me?.name ?? null],
    queryFn: fetchFavoriteCharachips,
    enabled: me != null,
    staleTime: Infinity,
    retry: false,
  });
}

/** お気に入り charaId の Set (トグル表示や絞り込み判定用)。 */
export function useFavoriteCharaIds(): Set<number> {
  const { data } = useFavoriteCharachips();
  return useMemo(
    () => new Set((data?.list ?? []).flatMap((chip) => chip.charas.list.map((c) => c.id))),
    [data],
  );
}

/** お気に入りの登録/解除。成功後にお気に入りキャッシュを取り直す。 */
export function useToggleFavoriteChara() {
  const queryClient = useQueryClient();
  return async (charaId: number, isFavorite: boolean) => {
    if (isFavorite) {
      await removeFavoriteChara(charaId);
    } else {
      await addFavoriteChara(charaId);
    }
    await queryClient.invalidateQueries({ queryKey: FAVORITE_CHARAS_QUERY_KEY });
  };
}
