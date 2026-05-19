/**
 * プレイヤー画面用の型と fetch 関数。
 * 型は SpringDoc → `pnpm gen:api` で取得した `~/lib/api/generated.ts` を参照する。
 */

import type { components } from "~/lib/api/generated";
import { browserFetch, type ApiFetch } from "~/lib/api/client";

// NOTE: backend の Kotlin `String?` には `@field:Schema(nullable = true)` も付けているが、
// SpringDoc + OpenAPI 3.1 では `nullable: true` ではなく `type: ["string", "null"]` 形式に
// 変換されるべきところを変換せず、`required: false` (= TypeScript の optional) のみが反映される。
// 結果として generated 型は `twitterUserName?: string` となり、`null` をリテラル値として
// 受け取る情報が落ちる。フロント側で null を許容するため optional フィールドを `| null` に
// 広げたエイリアス型を露出する。既存値クリアは `null`、フィールド省略 (undefined) は
// backend で同じく `null` 扱い。
type Schemas = components["schemas"];
type WidenOptionalsToNull<T> = {
  [K in keyof T]: undefined extends T[K] ? T[K] | null : T[K];
};

export type PlayersView = Schemas["PlayersView"];
export type PlayerSummaryView = Schemas["PlayerSummaryView"];
export type PlayerView = WidenOptionalsToNull<Schemas["MePlayerView"]>;
export type PlayerDetailView = WidenOptionalsToNull<Schemas["PlayerDetailView"]>;
export type PlayerProfileBody = WidenOptionalsToNull<Schemas["PlayerProfileBody"]>;
export type PlayerPasswordBody = Schemas["PlayerPasswordBody"];

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

/** GET /api/v1/players?pageNum=... — 認証不要 */
export async function fetchPlayers(
  pageNum: number,
  fetcher: ApiFetch = browserFetch,
): Promise<PlayersView> {
  const res = await fetcher(`/api/v1/players?pageNum=${pageNum}`);
  if (!res.ok) throw new Error(`players fetch failed: ${res.status}`);
  return res.json();
}

/** GET /api/v1/players/me — 認証必須 (失敗時は例外) */
export async function fetchMyPlayer(
  fetcher: ApiFetch = browserFetch,
): Promise<PlayerView> {
  const res = await fetcher(`/api/v1/players/me`);
  if (!res.ok) throw new Error(`my player fetch failed: ${res.status}`);
  return res.json();
}

/** GET /api/v1/players/{userName} — 認証不要 */
export async function fetchPlayerDetail(
  userName: string,
  fetcher: ApiFetch = browserFetch,
): Promise<PlayerDetailView> {
  const res = await fetcher(`/api/v1/players/${encodeURIComponent(userName)}`);
  if (!res.ok) throw new Error(`player detail fetch failed: ${res.status}`);
  return res.json();
}

/** PUT /api/v1/players/me/profile */
export async function putMyProfile(
  body: PlayerProfileBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/players/me/profile`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`update profile failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** PUT /api/v1/players/me/password */
export async function putMyPassword(
  body: PlayerPasswordBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/players/me/password`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`change password failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}
