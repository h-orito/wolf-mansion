import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

/** 村一覧 (`GET /api/v1/villages`) の型 (OpenAPI 生成型・Step 4.1)。 */
export type VillageListResponse = components["schemas"]["VillageListResponse"];
export type VillageSummary = components["schemas"]["VillageSummary"];

/** 村の状態フィルタ。backend `VillageRestController` の status と一致。 */
export type VillageStatusFilter = "all" | "notFinished" | "finished";

/** 村一覧を取得する (公開)。status で状態を絞り込む (既定 = 全件)。 */
export function fetchVillages(status?: VillageStatusFilter): Promise<VillageListResponse> {
  const query = status && status !== "all" ? `?status=${status}` : "";
  return apiFetch<VillageListResponse>(`/api/v1/villages${query}`);
}
