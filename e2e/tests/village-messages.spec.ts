import { expect, test, type Page } from "@playwright/test";

/**
 * 村画面の発言ログ e2e。村・発言の有無はローカル DB 依存のため、
 * 村一覧 API から対象を動的に探し、無ければスキップする。
 */

type SimpleVillage = { id: number; name: string };

async function findVillages(page: Page, statuses: string[]): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { villages: SimpleVillage[] };
  return body.villages;
}

test("発言ログが表示される (公開システムメッセージ)", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PREPARATION", "IN_PROGRESS"]);
  test.skip(villages.length === 0, "表示できる村が無い DB のためスキップ");
  const village = villages[villages.length - 1];

  await page.goto(`village/${village.id}`);

  // 村オープン時の公開システムメッセージ (定型文) が描画される
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
});

test("匿名では非公開種別が API レスポンスに含まれない", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  test.skip(villages.length === 0, "進行中の村が無い DB のためスキップ");
  const village = villages[0];

  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages/${village.id}/messages`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { messageList: { messageType: string }[] };
  const secretTypes = body.messageList.filter((m) =>
    ["WEREWOLF_SAY", "MONOLOGUE_SAY", "SECRET_SAY", "PRIVATE_SYSTEM"].includes(m.messageType),
  );
  expect(secretTypes).toHaveLength(0);

  // 正体一覧はエピローグ以降のみ (進行中は 400)
  const participantsRes = await page.request.get(
    `/wolf-mansion-api/api/v1/villages/${village.id}/participants`,
  );
  expect(participantsRes.status()).toBe(404);
});

test("アンカー発言のパーマリンクページが表示される", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PREPARATION", "IN_PROGRESS"]);
  test.skip(villages.length === 0, "表示できる村が無い DB のためスキップ");
  const village = villages[villages.length - 1];

  // 1 件目の通常発言 (ダミーキャラの第一声) があるか確認してから開く
  const anchorRes = await page.request.get(
    `/wolf-mansion-api/api/v1/villages/${village.id}/messages/anchor?messageType=NORMAL_SAY&messageNumber=1`,
  );
  expect(anchorRes.ok()).toBeTruthy();
  const anchorBody = (await anchorRes.json()) as {
    message: { messageContent: string } | null;
  };
  test.skip(anchorBody.message == null, "通常発言が無い村のためスキップ");

  await page.goto(`village/${village.id}/message?anchors=n1`);
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
  await expect(page.locator(".message-normal").first()).toContainText(
    anchorBody.message!.messageContent.slice(0, 10),
  );
});
