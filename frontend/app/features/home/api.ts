import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

/** ホーム (`GET /api/v1/home`) のレスポンス型 (OpenAPI 生成型・Step 4.1)。 */
export type HomeResponse = components["schemas"]["HomeResponse"];
export type VillageSummary = components["schemas"]["VillageSummary"];

/** 開催中 (未終了) の村一覧 + 村作成可否を取得する。公開 (匿名でも可)。 */
export function fetchHome(): Promise<HomeResponse> {
  return apiFetch<HomeResponse>("/api/v1/home");
}
