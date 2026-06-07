import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

/** 村一覧 (`GET /api/v1/villages`) の型 (OpenAPI 生成型・Step 4.1)。 */
export type VillageListResponse = components["schemas"]["VillageListResponse"];
/** 一覧用の軽量ビュー (ドメイン構造に近い生データ。表示整形は画面側)。 */
export type SimpleVillageView = components["schemas"]["SimpleVillageView"];

/**
 * village_status の code (CDef.VillageStatus と一致)。
 * backend `GET /api/v1/villages?status=...` は code 配列で絞り込む (整形・キーワード変換はしない)。
 */
export const VillageStatusCode = {
  prologue: "IN_PREPARATION",
  progress: "IN_PROGRESS",
  epilogue: "EPILOGUE",
  completed: "COMPLETED",
  canceled: "CANCEL",
} as const;

/** 未終了 (募集中/進行中/エピローグ) の村の status code。トップ・村一覧で共有。 */
export const NOT_FINISHED_STATUSES = [
  VillageStatusCode.prologue,
  VillageStatusCode.progress,
  VillageStatusCode.epilogue,
];

/** 終了済み (終了/廃村) の村の status code。 */
export const FINISHED_STATUSES = [VillageStatusCode.completed, VillageStatusCode.canceled];

/**
 * 村一覧を取得する (公開)。
 * @param statuses village_status の code 配列。省略・空配列なら全件。
 */
export function fetchVillages(statuses: string[] = []): Promise<VillageListResponse> {
  const query = statuses.map((s) => `status=${encodeURIComponent(s)}`).join("&");
  return apiFetch<VillageListResponse>(`/api/v1/villages${query ? `?${query}` : ""}`);
}
