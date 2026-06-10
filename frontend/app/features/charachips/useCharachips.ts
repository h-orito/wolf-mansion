import { useQuery } from "@tanstack/react-query";

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

export function useRoomAssignment(personNum: number, enabled: boolean) {
  return useQuery({
    queryKey: ["room-assignment", personNum],
    queryFn: () => fetchRoomAssignment(personNum),
    staleTime: Infinity,
    enabled,
    retry: false,
  });
}
