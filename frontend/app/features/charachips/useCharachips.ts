import { useQueries, useQuery } from "@tanstack/react-query";

import { fetchCharachipDetail, fetchCharachipList, fetchRoomAssignment } from "./api";

export function useCharachipList() {
  return useQuery({
    queryKey: ["charachip-list"],
    queryFn: fetchCharachipList,
    staleTime: Infinity,
    retry: false,
  });
}

export function useCharachipDetail(id: number) {
  return useQuery({
    queryKey: ["charachip-detail", id],
    queryFn: () => fetchCharachipDetail(id),
    staleTime: Infinity,
    retry: false,
  });
}

/** 複数キャラチップの詳細をまとめて取得し、ids の並び順に各キャラ一覧を連結する。 */
export function useCharachipDetails(ids: number[]) {
  return useQueries({
    queries: ids.map((id) => ({
      queryKey: ["charachip-detail", id],
      queryFn: () => fetchCharachipDetail(id),
      staleTime: Infinity,
      retry: false,
    })),
    combine: (results) => ({
      isLoading: results.some((r) => r.isLoading),
      charas: results.flatMap((r) => r.data?.charas.list ?? []),
    }),
  });
}

export function useRoomAssignment(personNum: number, enabled: boolean) {
  return useQuery({
    queryKey: ["room-assignment", personNum],
    queryFn: () => fetchRoomAssignment(personNum),
    staleTime: Infinity,
    enabled,
    retry: false,
  });
}
