/**
 * プレイヤー画面用の型と fetch 関数。
 * 型は SpringDoc → `pnpm gen:api` で取得した `~/lib/api/generated.ts` を参照する。
 */

import type { components } from "~/lib/api/generated";
import { browserFetch, type ApiFetch } from "~/lib/api/client";

export type PlayersView = components["schemas"]["PlayersView"];
export type PlayerSummaryView = components["schemas"]["PlayerSummaryView"];
export type PlayerView = components["schemas"]["PlayerView"];
export type PlayerDetailView = components["schemas"]["PlayerDetailView"];

// NOTE: backend の Kotlin `String?` は SpringDoc 経由で `string` (optional) として出てきて
// しまい、`null` をリテラル値として受け付ける型情報が落ちる。null 渡し (= 値クリア) を
// 明示できるよう、generated 型をベースに値側を `| null` に広げて使う。
// 既存値クリアは `null`、フィールド省略 (undefined) は backend 側で同じく `null` 扱い。
type Generated = components["schemas"];
export type PlayerProfileBody = {
  [K in keyof Generated["PlayerProfileBody"]]: Generated["PlayerProfileBody"][K] | null;
};
export type PlayerPasswordBody = Generated["PlayerPasswordBody"];

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
