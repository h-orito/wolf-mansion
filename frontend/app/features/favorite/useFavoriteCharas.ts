import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useMemo, useState } from "react";

import { useMe } from "~/features/auth/useMe";
import { ApiError } from "~/lib/api";
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

/** お気に入りの登録/解除。成功後にお気に入りキャッシュを取り直す。失敗時は error にメッセージが入る。 */
export function useToggleFavoriteChara(): {
  toggle: (charaId: number, isFavorite: boolean) => Promise<void>;
  error: string | null;
} {
  const queryClient = useQueryClient();
  const [error, setError] = useState<string | null>(null);
  const toggle = async (charaId: number, isFavorite: boolean) => {
    setError(null);
    try {
      if (isFavorite) {
        await removeFavoriteChara(charaId);
      } else {
        await addFavoriteChara(charaId);
      }
      // 初回フェッチが in-flight のまま invalidate すると、キャンセルされず更新前の
      // レスポンスがそのまま採用される (react-query は data 未確立の fetch を再利用する)。
      // 先に cancel して必ず更新後のデータを取り直す
      await queryClient.cancelQueries({ queryKey: FAVORITE_CHARAS_QUERY_KEY });
      await queryClient.invalidateQueries({ queryKey: FAVORITE_CHARAS_QUERY_KEY });
    } catch (e) {
      setError(e instanceof ApiError ? e.detail : "お気に入りの更新に失敗しました");
    }
  };
  return { toggle, error };
}
