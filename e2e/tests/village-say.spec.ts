import { expect, test, type Page } from "@playwright/test";

/**
 * 村画面の発言投稿 e2e。ローカル DB の master (testuser) でログインし、
 * 発言できる村があれば 確認 → 投稿 → ログ反映 を通す。
 */

type SimpleVillage = { id: number; name: string };

async function findVillages(page: Page, statuses: string[]): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { villages: SimpleVillage[] };
  return body.villages;
}

async function loginAsMaster(page: Page) {
  await page.goto("login");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", "master");
  await page.fill("#password", "testuser");
  await page.getByRole("button", { name: "ログイン" }).click();
  await expect(page).toHaveURL(/\/wolf-mansion$/);
}

test("発言: 入力 → 確認 → 投稿 → ログ反映", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  test.skip(villages.length === 0, "進行中の村が無い DB のためスキップ");
  const village = villages[0];

  await loginAsMaster(page);
  await page.goto(`village/${village.id}`);

  const sayPanel = page.locator("#say-panel");
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
  test.skip((await sayPanel.count()) === 0, "発言できない状態のためスキップ");

  // 独り言で投稿する (種別が無ければスキップ)
  const monologue = sayPanel.getByRole("button", { name: "独り言" });
  test.skip((await monologue.count()) === 0, "独り言が選択できないためスキップ");
  await monologue.click();

  const text = `e2e 発言テスト ${Date.now()}`;
  await sayPanel.locator("textarea").fill(text);
  // 文字数カウントが入力に追従する
  await expect(sayPanel.getByText(/文字数: \d+\/\d+/)).toBeVisible();

  await sayPanel.getByRole("button", { name: "確認画面へ" }).click();

  // 確認プレビュー (まだ投稿されていない)
  const confirmArea = page.locator("#message-confirm-area");
  await expect(confirmArea).toBeVisible();
  await expect(confirmArea).toContainText(text);

  await confirmArea.getByRole("button", { name: "発言する（独り言）" }).click();

  // プレビューが消え、ログに反映される
  await expect(confirmArea).toHaveCount(0);
  await expect(page.locator(".message-monologue").filter({ hasText: text })).toBeVisible({
    timeout: 15000,
  });
});

test("空入力では確認ボタンが無効", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  test.skip(villages.length === 0, "進行中の村が無い DB のためスキップ");
  const village = villages[0];

  await loginAsMaster(page);
  await page.goto(`village/${village.id}`);
  const sayPanel = page.locator("#say-panel");
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
  test.skip((await sayPanel.count()) === 0, "発言できない状態のためスキップ");

  await expect(sayPanel.getByRole("button", { name: "確認画面へ" })).toBeDisabled();
});

test("アクション: 対象選択 + 本文 → 確認 → 投稿 → ログ反映", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  test.skip(villages.length === 0, "進行中の村が無い DB のためスキップ");
  const village = villages[0];

  await loginAsMaster(page);
  await page.goto(`village/${village.id}`);
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });

  const actionPanel = page.locator("div").filter({ hasText: /^アクション$/ }).first();
  const actionInput = page.getByLabel("アクション本文");
  test.skip((await actionInput.count()) === 0, "アクションできない状態のためスキップ");

  const text = `に e2e アクション ${Date.now()}`;
  await page.getByLabel("アクションの対象").selectOption("全員");
  await actionInput.fill(text);
  await actionPanel
    .locator("..")
    .getByRole("button", { name: "確認画面へ" })
    .last()
    .click();

  const confirmArea = page.locator("#message-confirm-area");
  await expect(confirmArea).toBeVisible();
  await expect(confirmArea).toContainText(text);

  await confirmArea.getByRole("button", { name: "アクション" }).click();
  await expect(confirmArea).toHaveCount(0);
  await expect(page.locator(".message-action").filter({ hasText: text })).toBeVisible({
    timeout: 15000,
  });
});
