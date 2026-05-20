/**
 * 新規村作成 (creator) の型と fetch 関数。
 * 旧 Thymeleaf `/new-village` 系の置き換え。
 */

import type { components } from "~/lib/api/generated";
import { browserFetch, type ApiFetch } from "~/lib/api/client";

type Schemas = components["schemas"];

// nullable optional フィールドを `T | null` に深く広げる (既存パターン)
type WidenOptionalsDeep<T> =
  T extends Array<infer U> ? Array<WidenOptionalsDeep<U>> :
  T extends object ? { [K in keyof T]: undefined extends T[K] ? WidenOptionalsDeep<T[K]> | null : WidenOptionalsDeep<T[K]> } :
  T;

export type NewVillageFormView = WidenOptionalsDeep<Schemas["NewVillageFormView"]>;
export type NewVillageCreateBody = WidenOptionalsDeep<Schemas["NewVillageCreateBody"]>;
export type CreatedVillageView = Schemas["CreatedVillageView"];

async function readErrorMessage(res: Response): Promise<string> {
  const errBody = await res.text();
  try {
    const parsed = JSON.parse(errBody) as { message?: string };
    if (parsed.message) return parsed.message;
  } catch {
    // not JSON
  }
  return errBody;
}

/** GET /api/v1/new-village/form-defaults — 未認証は 400。 */
export async function fetchNewVillageDefaults(
  fetcher: ApiFetch = browserFetch,
): Promise<NewVillageFormView> {
  const res = await fetcher(`/api/v1/new-village/form-defaults`);
  if (!res.ok) {
    throw new Error(`new-village defaults failed: ${res.status} ${await readErrorMessage(res)}`);
  }
  return res.json();
}

/** POST /api/v1/villages — 新規村作成。201 で `{ id }` を返す。 */
export async function postNewVillage(
  body: NewVillageCreateBody,
  fetcher: ApiFetch = browserFetch,
): Promise<CreatedVillageView> {
  const res = await fetcher(`/api/v1/villages`, {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`new-village create failed: ${res.status} ${await readErrorMessage(res)}`);
  }
  return res.json();
}
