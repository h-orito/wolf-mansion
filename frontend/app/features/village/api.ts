import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

/** 村詳細 (`GET /api/v1/villages/{id}`)。公開情報のみ (入村パスワードはマスク済み)。 */
export type VillageDetailView = components["schemas"]["VillageDetailView"];
/** 村状況 (`GET /api/v1/villages/{id}/situation`)。スポイラーマスク適用済み。 */
export type VillageSituationView = components["schemas"]["VillageSituationView"];
/** 参加者本人の状態 (`GET /api/v1/villages/{id}/situation/me`)。要認証。 */
export type ParticipantSituationView = components["schemas"]["ParticipantSituationView"];
/** 村ポーリング (`POST /api/v1/villages/{id}/update`) の応答。 */
export type VillageUpdateResponse = components["schemas"]["VillageUpdateResponse"];

export type VillageRoomAssignedRow = components["schemas"]["VillageRoomAssignedRow"];
export type VillageRoomAssigned = components["schemas"]["VillageRoomAssigned"];
export type VillageMemberContent = components["schemas"]["VillageMemberContent"];
export type VillageVoteContent = components["schemas"]["VillageVoteContent"];
export type VillageFootstepContent = components["schemas"]["VillageFootstepContent"];
export type VillageSituationContent = components["schemas"]["VillageSituationContent"];

/** 村詳細を取得する (公開)。存在しない村は 404 (`ApiError.code === "not_found"`)。 */
export function fetchVillage(id: number): Promise<VillageDetailView> {
  return apiFetch<VillageDetailView>(`/api/v1/villages/${id}`);
}

/** 村状況を取得する (公開・ログイン時は視点がマスクに反映される)。 */
export function fetchVillageSituation(id: number, day?: number): Promise<VillageSituationView> {
  const query = day != null ? `?day=${day}` : "";
  return apiFetch<VillageSituationView>(`/api/v1/villages/${id}/situation${query}`);
}

/** 参加者本人の状態を取得する (要認証)。 */
export function fetchMyVillageSituation(
  id: number,
  day?: number,
): Promise<ParticipantSituationView> {
  const query = day != null ? `?day=${day}` : "";
  return apiFetch<ParticipantSituationView>(`/api/v1/villages/${id}/situation/me${query}`);
}

/**
 * 村ポーリング。最終アクセス更新と日付更新の駆動を行い、最新日を返す。
 * 日付更新が起きた直後の応答は更新前の最新日のままで、次回ポーリングで新しい日付になる。
 */
export function postVillageUpdate(id: number): Promise<VillageUpdateResponse> {
  return apiFetch<VillageUpdateResponse>(`/api/v1/villages/${id}/update`, { method: "POST" });
}
