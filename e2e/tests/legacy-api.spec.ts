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
  // ローカル DB に依存しないよう存在しない村 id を使う。現状の backend は
  // 存在しない村に 500 を返す仕様 (公開 API の契約として維持中) のため、
  // 404 (frontend ルート未定義) ではなく backend まで到達していることを確認する
  const res = await request.get("api/village/999999999");
  expect(res.status()).toBe(500);
});
