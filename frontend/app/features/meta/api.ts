/**
 * メタ情報 (キャラチップ / 役職カタログ / 終了村戦績) の型 + fetch 関数。
 * 型は SpringDoc → `pnpm gen:api` で生成した `~/lib/api/generated.ts` を参照する。
 *
 * 認可不要 (read-only)。`browserFetch` / `ssrFetch` どちらでも使える。
 */

import type { components } from "~/lib/api/generated";
import { browserFetch, type ApiFetch } from "~/lib/api/client";

type Schemas = components["schemas"];

// nullable な optional フィールドを `T | null` に広げて、SSR JSON で null が来ても受けられるよう
// 既存パターン (`features/player/api.ts`) と揃える。
type WidenOptionalsDeep<T> =
  T extends Array<infer U> ? Array<WidenOptionalsDeep<U>> :
  T extends object ? { [K in keyof T]: undefined extends T[K] ? WidenOptionalsDeep<T[K]> | null : WidenOptionalsDeep<T[K]> } :
  T;

export type CharachipView = Schemas["CharachipView"];
export type CharachipDetailView = WidenOptionalsDeep<Schemas["CharachipDetailView"]>;
export type SkillCatalogView = Schemas["SkillCatalogView"];
export type VillageRecordsView = WidenOptionalsDeep<Schemas["VillageRecordsView"]>;
export type VillageRecordSummary = VillageRecordsView["list"][number];

// ---------- charachips ----------

export async function fetchCharachips(
  fetcher: ApiFetch = browserFetch,
): Promise<CharachipView[]> {
  const res = await fetcher(`/api/v1/charachips`);
  if (!res.ok) throw new Error(`charachips fetch failed: ${res.status}`);
  return res.json();
}

export async function fetchCharachipDetail(
  charachipId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<CharachipDetailView> {
  const res = await fetcher(`/api/v1/charachips/${charachipId}`);
  if (!res.ok) throw new Error(`charachip detail fetch failed: ${res.status}`);
  return res.json();
}

// ---------- skills ----------

export async function fetchSkillCatalog(
  fetcher: ApiFetch = browserFetch,
): Promise<SkillCatalogView> {
  const res = await fetcher(`/api/v1/skills`);
  if (!res.ok) throw new Error(`skill catalog fetch failed: ${res.status}`);
  return res.json();
}

/**
 * `GET /api/v1/skills/search` — tags / name / villageId による絞り込み結果の役職コード (lowercase) を返す。
 * 旧 `/skill-list` 互換。tags はカンマ区切り。
 */
export async function searchSkills(
  params: { tags?: string; name?: string; villageId?: number },
  fetcher: ApiFetch = browserFetch,
): Promise<string[]> {
  const qs = new URLSearchParams();
  if (params.tags) qs.set("tags", params.tags);
  if (params.name) qs.set("name", params.name);
  if (params.villageId != null) qs.set("villageId", String(params.villageId));
  const suffix = qs.toString() ? `?${qs.toString()}` : "";
  const res = await fetcher(`/api/v1/skills/search${suffix}`);
  if (!res.ok) throw new Error(`skill search failed: ${res.status}`);
  return res.json();
}

// ---------- village records ----------

export async function fetchVillageRecords(
  params: { vid?: number[] } = {},
  fetcher: ApiFetch = browserFetch,
): Promise<VillageRecordsView> {
  const qs = new URLSearchParams();
  (params.vid ?? []).forEach((id) => qs.append("vid", String(id)));
  const suffix = qs.toString() ? `?${qs.toString()}` : "";
  const res = await fetcher(`/api/v1/village-records${suffix}`);
  if (!res.ok) throw new Error(`village-records fetch failed: ${res.status}`);
  return res.json();
}
