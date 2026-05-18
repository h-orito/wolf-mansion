import type { components } from "~/lib/api/generated";
import { browserFetch, type ApiFetch } from "~/lib/api/client";

export type VillagesView = components["schemas"]["VillagesView"];
export type SimpleVillageView = components["schemas"]["SimpleVillageView"];

export type VillageStatusCode =
  | "募集中"
  | "進行中"
  | "エピローグ"
  | "終了"
  | "廃村";

/**
 * GET /api/v1/villages
 *
 * SSR loader / browser どちらでも使えるよう、`fetcher` 引数で fetch 関数を差し替え可能。
 * 省略時は browserFetch (CSR 用)。
 */
export async function fetchVillages(
  params: { statuses?: VillageStatusCode[] } = {},
  fetcher: ApiFetch = browserFetch,
): Promise<VillagesView> {
  const qs = params.statuses && params.statuses.length > 0
    ? `?status=${encodeURIComponent(params.statuses.join(","))}`
    : "";
  const res = await fetcher(`/api/v1/villages${qs}`);
  if (!res.ok) throw new Error(`villages fetch failed: ${res.status}`);
  return res.json();
}
