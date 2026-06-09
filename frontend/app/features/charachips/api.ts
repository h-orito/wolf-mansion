import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

export type SimpleCharachipView = components["schemas"]["SimpleCharachipView"];
export type CharachipDetailResponse = components["schemas"]["CharachipDetailResponse"];
export type CharaView = components["schemas"]["CharaView"];
export type RoomAssignmentView = components["schemas"]["RoomAssignmentView"];
export type RoomAssignmentRowView = components["schemas"]["RoomAssignmentRowView"];
export type RoomAssignmentCellView = components["schemas"]["RoomAssignmentCellView"];

export function fetchCharachipList(): Promise<SimpleCharachipView[]> {
  return apiFetch<components["schemas"]["CharachipListResponse"]>("/api/v1/charachips").then(
    (r) => r.charachips,
  );
}

export function fetchCharachipDetail(id: number): Promise<CharachipDetailResponse> {
  return apiFetch<CharachipDetailResponse>(`/api/v1/charachips/${id}`);
}
