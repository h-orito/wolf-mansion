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

/** POST /api/v1/villages — 新規村作成 (非オリジナル、JSON)。201 で `{ id }` を返す。 */
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

/**
 * POST /api/v1/villages — 新規村作成 (オリジナルキャラチップ、multipart)。
 *
 * 2 パート構成: `body` (JSON、Content-Type 明示が必要) + `dummyCharaImage` (画像ファイル)。
 * `body.shouldOriginalImage=true` が必須 (backend で再検証)。
 */
export async function postNewVillageOriginal(
  body: NewVillageCreateBody,
  dummyCharaImage: File,
  fetcher: ApiFetch = browserFetch,
): Promise<CreatedVillageView> {
  const form = new FormData();
  // Spring の `@RequestPart @Valid body: NewVillageCreateBody` で Jackson が JSON として
  // パースするため、Blob に `application/json` の Content-Type を明示する必要がある。
  form.append(
    "body",
    new Blob([JSON.stringify(body)], { type: "application/json" }),
  );
  form.append("dummyCharaImage", dummyCharaImage);
  const res = await fetcher(`/api/v1/villages`, {
    method: "POST",
    body: form,
  });
  if (!res.ok) {
    throw new Error(`new-village create (original) failed: ${res.status} ${await readErrorMessage(res)}`);
  }
  return res.json();
}
