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
 * 村一覧の絞り込み条件。すべて省略可 (省略時はその軸で絞らない)。
 * - `statuses`: village_status の code 配列 (トップ = 未終了)。
 * - `charachips`: キャラセット (CharaGroup) の id 配列。
 * - `skills`: 役職 (CDef.Skill) の code 配列。**status とは排他**で、backend は skill 指定時に進行中を除外する。
 * - `random`: 編成。`true`=闇鍋 / `false`=固定 / 省略=両方。
 */
export type VillageFilter = {
  statuses?: string[];
  charachips?: number[];
  skills?: string[];
  random?: boolean | null;
};

/**
 * 村一覧を取得する (公開)。
 * @param filter 絞り込み条件。省略 (空オブジェクト) なら全件。
 */
export function fetchVillages(filter: VillageFilter = {}): Promise<VillageListResponse> {
  const params = new URLSearchParams();
  (filter.statuses ?? []).forEach((s) => params.append("status", s));
  (filter.charachips ?? []).forEach((c) => params.append("charachip", String(c)));
  (filter.skills ?? []).forEach((s) => params.append("skill", s));
  if (filter.random != null) params.append("random", String(filter.random));
  const query = params.toString();
  return apiFetch<VillageListResponse>(`/api/v1/villages${query ? `?${query}` : ""}`);
}

/**
 * 村一覧画面の検索候補 (キャラセット一覧 / 役職一覧)。
 * 既存の凍結公開 API `GET /api/village-list` を proxy 経由で流用する (新規エンドポイントは作らない)。
 * 同 API は村一覧 (`villageList`) も返すが、ここでは候補 (charachipList/skillList) のみ使う。
 * 旧 SSR 由来で OpenAPI (v1 面) には含まれないため型は手書きする。
 */
export type VillageSearchCandidates = {
  charachipList: { id: number; name: string }[];
  skillList: { code: string; name: string }[];
};

/** 検索候補を取得する (公開)。 */
export function fetchVillageSearchCandidates(): Promise<VillageSearchCandidates> {
  return apiFetch<VillageSearchCandidates>("/api/village-list");
}
