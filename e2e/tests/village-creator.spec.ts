import { expect, test, type Page } from "@playwright/test";

/**
 * 村建て (creator) 機能の e2e。master (ローカル DB の村建てプレイヤー) でログインし、
 * 進行中の村で村建て発言フローを確認する。kick / 廃村 / エピローグ延長は
 * 破壊的でフィクスチャ村を壊すため e2e では実行しない (REST 検証は使い捨て村で実施済み)。
 */

type SimpleVillage = { id: number; name: string };

async function findVillages(page: Page, statuses: string[]): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { villages: SimpleVillage[] };
  return body.villages;
}

async function login(page: Page, userId: string): Promise<boolean> {
  const res = await page.request.post(`/wolf-mansion-api/api/v1/auth/login`, {
    data: { userId, password: "testuser" },
  });
  return res.ok();
}

/** master が村建てした村を探す。 */
async function findCreatorVillage(page: Page): Promise<number | null> {
  if (!(await login(page, "master"))) return null;
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  for (const village of villages) {
    const res = await page.request.get(
      `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
    );
    if (!res.ok()) continue;
    const body = (await res.json()) as { creator: { isCreator: boolean } };
    if (body.creator.isCreator) return village.id;
  }
  return null;
}

async function dismissInitialSkillModal(page: Page) {
  const confirm = page.getByRole("button", { name: "確認したので次回以降表示しない" });
  if ((await confirm.count()) > 0) {
    await confirm.click();
  }
}

test("村建て発言: 入力 → 確認 → 投稿 → ログ反映", async ({ page }) => {
  const villageId = await findCreatorVillage(page);
  test.skip(villageId == null, "master が村建てした進行中の村が無い DB のためスキップ");
  if (villageId == null) return;

  await page.goto(`village/${villageId}`);
  const sayInput = page.getByLabel("村建て発言");
  await expect(sayInput).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);

  const text = `e2e 天の声 ${Date.now()}`;
  await sayInput.fill(text);
  await expect(page.getByText(/文字数: \d+\/1000/)).toBeVisible();

  // 村建て機能パネルの確認画面へ (発言フォーム等にも同名ボタンがあるため、パネル内に限定)
  const creatorBodyId = await page
    .getByRole("button", { name: "村建て機能", exact: true })
    .getAttribute("aria-controls");
  await page
    .locator(`[id="${creatorBodyId}"]`)
    .getByRole("button", { name: "確認画面へ" })
    .click();

  const confirmArea = page.locator("#message-confirm-area");
  await expect(confirmArea).toBeVisible();
  await expect(confirmArea).toContainText(text);

  await confirmArea.getByRole("button", { name: "発言する（村建て）" }).click();
  await expect(confirmArea).toHaveCount(0);
  await expect(page.locator(".message").filter({ hasText: text })).toBeVisible({
    timeout: 15000,
  });
});

test("村建てでない参加者には村建て機能パネルが出ない", async ({ page }) => {
  const ok = await login(page, "testuser01");
  test.skip(!ok, "testuser01 が存在しない DB のためスキップ");
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  test.skip(villages.length === 0, "進行中の村が無い DB のためスキップ");

  await page.goto(`village/${villages[0].id}`);
  await expect(page.getByRole("button", { name: "更新" })).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);
  await expect(page.getByText("村建て機能")).toHaveCount(0);
});
