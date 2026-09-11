import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

export type SimpleCharachipView = components["schemas"]["SimpleCharachipView"];
export type Charachip = components["schemas"]["Charachip"];
export type Chara = components["schemas"]["Chara"];
export type RoomAssignmentResponse = components["schemas"]["RoomAssignmentResponse"];

export function fetchCharachipList(): Promise<SimpleCharachipView[]> {
  return apiFetch<components["schemas"]["CharachipListResponse"]>("/api/v1/charachips").then(
    (r) => r.charachips,
  );
}

export function fetchCharachipDetail(id: number): Promise<Charachip> {
  return apiFetch<Charachip>(`/api/v1/charachips/${id}`);
}

export function fetchRoomAssignment(personNum: number): Promise<RoomAssignmentResponse> {
  return apiFetch<RoomAssignmentResponse>(`/api/v1/rooms?personNum=${personNum}`);
}
