import { expect, test } from "@playwright/test";

/**
 * 外部公開 API の proxy 疎通テスト。
 *
 * `/wolf-mansion/{recruiting,village-record/*,skill/list,api/village*}` は外部サイトが
 * 消費している URL 変更不可の API。frontend のリソースルートが backend
 * (`/wolf-mansion-api`) へ素通しすることを、frontend 経由の実 HTTP で確認する。
 * レスポンス形状は消費側との契約なので、キー名 (casing 含む) まで確認する。
 */

test("recruiting が JSON を返す", async ({ request }) => {
  const res = await request.get("recruiting");
  expect(res.status()).toBe(200);
  expect(res.headers()["content-type"]).toContain("application/json");
  const body = await res.json();
  // charachip は各村の charaset 文字列に埋め込まれる形が契約 (top-level キーは villageList のみ)
  expect(body).toHaveProperty("villageList");
});

test("village-record/list が JSON を返す", async ({ request }) => {
  const res = await request.get("village-record/list");
  expect(res.status()).toBe(200);
  expect(res.headers()["content-type"]).toContain("application/json");
  const body = await res.json();
  expect(body).toHaveProperty("list");
});

test("village-record/list の vid 絞り込みクエリが透過される", async ({ request }) => {
  // 存在しない村 id で絞り込むと空リストになる = クエリ文字列が backend まで届いている
  const res = await request.get("village-record/list?vid=999999999");
  expect(res.status()).toBe(200);
  const body = await res.json();
  expect(body.list).toEqual([]);
});

test("village-record/latest-vid が JSON を返す", async ({ request }) => {
  const res = await request.get("village-record/latest-vid");
  expect(res.status()).toBe(200);
  const body = await res.json();
  expect(body).toHaveProperty("vid");
});

test("skill/list が snake_case の JSON を返す", async ({ request }) => {
  const res = await request.get("skill/list");
  expect(res.status()).toBe(200);
  const body = await res.json();
  expect(Array.isArray(body.list)).toBe(true);
  // この API のみ snake_case が契約 (camp_name / skill_name_list)
  expect(body.list[0]).toHaveProperty("camp_name");
  expect(body.list[0]).toHaveProperty("skill_name_list");
});

test("api/village-list が JSON を返す", async ({ request }) => {
  const res = await request.get("api/village-list");
  expect(res.status()).toBe(200);
  const body = await res.json();
  expect(body).toHaveProperty("villageList");
  expect(body).toHaveProperty("charachipList");
  expect(body).toHaveProperty("skillList");
});

test("api/village/:id はパスパラメータが透過される", async ({ request }) => {
  // ローカル DB に依存しないよう存在しない村 id を使う。backend のエラー時ステータス
  // (現状 500) は proxy と無関係に変わりうるため固定せず、frontend のルート未定義時は
  // HTML (404 ページ) が返ることを利用して「JSON = backend まで到達」を確認する
  const res = await request.get("api/village/999999999");
  expect(res.headers()["content-type"]).toContain("application/json");
});
