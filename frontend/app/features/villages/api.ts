import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

/** 村一覧 (`GET /api/v1/villages`) の型。 */
export type VillageListResponse = components["schemas"]["VillageListResponse"];
/** 一覧用の軽量ビュー (ドメイン構造に近い生データ。表示整形は画面側)。 */
export type SimpleVillageView = components["schemas"]["SimpleVillageView"];
/** キャラセットの軽量ビュー (`GET /api/v1/charachips`)。 */
export type SimpleCharachipView = components["schemas"]["SimpleCharachipView"];
/** 役職の軽量ビュー (`GET /api/v1/skills`)。 */
export type SimpleSkillView = components["schemas"]["SimpleSkillView"];

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
 * - `skills`: 役職 (CDef.Skill) の code 配列。**status とは排他**で、backend は skill 指定時にエピローグ以降
 *   (募集中・進行中を除く) のみを対象にする。
 * - `random`: 編成。`true`=闇鍋 / `false`=固定 / 省略=両方。
 */
export type VillageFilter = {
  statuses?: string[];
  charachips?: number[];
  skills?: string[];
  random?: boolean | null;
  /** 村ID の並び順。未指定なら API 既定 (降順=新しい村が先)。 */
  order?: "asc" | "desc";
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
  if (filter.order != null) params.append("order", filter.order);
  const query = params.toString();
  return apiFetch<VillageListResponse>(`/api/v1/villages${query ? `?${query}` : ""}`);
}

/** 村作成リクエスト (`POST /api/v1/villages` の JSON part)。 */
export type VillageCreateRequest = components["schemas"]["VillageCreateRequest"];
/** 村作成のレスポンス (遷移用の村 ID のみ)。 */
export type VillageCreateResponse = components["schemas"]["VillageCreateResponse"];

/**
 * 村を作成する (要認証)。multipart/form-data で JSON part (`request`) +
 * オリジナルダミーキャラ画像 (`dummyCharaImage`、任意) を送る。
 */
export function createVillage(
  request: VillageCreateRequest,
  dummyCharaImage: File | null,
): Promise<VillageCreateResponse> {
  const formData = new FormData();
  formData.append("request", new Blob([JSON.stringify(request)], { type: "application/json" }));
  if (dummyCharaImage) formData.append("dummyCharaImage", dummyCharaImage);
  return apiFetch<VillageCreateResponse>("/api/v1/villages", { method: "POST", body: formData });
}

/** キャラセット一覧を取得する (公開・村一覧の絞り込み候補などで使う)。 */
export function fetchCharachips(): Promise<SimpleCharachipView[]> {
  return apiFetch<components["schemas"]["CharachipListResponse"]>("/api/v1/charachips").then(
    (r) => r.charachips,
  );
}

/** 役職一覧を取得する (公開・村一覧の絞り込み候補などで使う)。 */
export function fetchSkills(): Promise<SimpleSkillView[]> {
  return apiFetch<components["schemas"]["SkillListResponse"]>("/api/v1/skills").then(
    (r) => r.skills,
  );
}
