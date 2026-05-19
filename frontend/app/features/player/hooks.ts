import { useMutation, useQuery, useQueryClient, type UseQueryResult } from "@tanstack/react-query";
import {
  fetchMyPlayer,
  fetchPlayerDetail,
  fetchPlayers,
  putMyPassword,
  putMyProfile,
  type PlayerDetailView,
  type PlayerPasswordBody,
  type PlayerProfileBody,
  type PlayersView,
  type PlayerView,
} from "./api";

export const PLAYERS_QUERY_KEY = (pageNum: number) => ["players", { pageNum }] as const;
export const PLAYER_DETAIL_QUERY_KEY = (userName: string) => ["players", "detail", userName] as const;
export const MY_PLAYER_QUERY_KEY = ["players", "me"] as const;

/** GET /api/v1/players */
export function usePlayersQuery(
  pageNum: number,
  initialData?: PlayersView,
): UseQueryResult<PlayersView> {
  return useQuery<PlayersView>({
    queryKey: PLAYERS_QUERY_KEY(pageNum),
    queryFn: () => fetchPlayers(pageNum),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    staleTime: 30 * 1000,
  });
}

/** GET /api/v1/players/{userName} */
export function usePlayerDetailQuery(
  userName: string,
  initialData?: PlayerDetailView,
): UseQueryResult<PlayerDetailView> {
  return useQuery<PlayerDetailView>({
    queryKey: PLAYER_DETAIL_QUERY_KEY(userName),
    queryFn: () => fetchPlayerDetail(userName),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    staleTime: 30 * 1000,
    enabled: userName.length > 0,
  });
}

/** GET /api/v1/players/me */
export function useMyPlayerQuery(initialData?: PlayerView): UseQueryResult<PlayerView> {
  return useQuery<PlayerView>({
    queryKey: MY_PLAYER_QUERY_KEY,
    queryFn: () => fetchMyPlayer(),
    initialData,
    initialDataUpdatedAt: initialData ? Date.now() : undefined,
    staleTime: 30 * 1000,
  });
}

/** PUT /api/v1/players/me/profile */
export function useUpdateProfileMutation(userName: string) {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (body: PlayerProfileBody) => putMyProfile(body),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: PLAYER_DETAIL_QUERY_KEY(userName) });
      qc.invalidateQueries({ queryKey: MY_PLAYER_QUERY_KEY });
    },
  });
}

/** PUT /api/v1/players/me/password */
export function useChangePasswordMutation() {
  return useMutation({
    mutationFn: (body: PlayerPasswordBody) => putMyPassword(body),
  });
}
