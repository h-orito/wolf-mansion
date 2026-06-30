import { expect, test } from "@playwright/test";
import {
  ensureMasterInProgressVillage,
  loginAsMasterUi,
  dismissInitialSkillModal,
} from "./helpers/provision";

/**
 * 村画面の発言投稿 e2e。master でログインし、
 * 発言できる村があれば 確認 → 投稿 → ログ反映 を通す。
 */

test("発言: 入力 → 確認 → 投稿 → ログ反映", async ({ page }) => {
  const village = await ensureMasterInProgressVillage(page);

  await loginAsMasterUi(page);
  await page.goto(`village/${village.id}`);

  const sayPanel = page.locator("#say-panel");
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);
  expect(await sayPanel.count()).toBeGreaterThan(0);

  // 独り言で投稿する (種別が無ければスキップ)
  const monologue = sayPanel.getByRole("button", { name: "独り言" });
  expect(await monologue.count()).toBeGreaterThan(0);
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
  const village = await ensureMasterInProgressVillage(page);

  await loginAsMasterUi(page);
  await page.goto(`village/${village.id}`);
  const sayPanel = page.locator("#say-panel");
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);
  expect(await sayPanel.count()).toBeGreaterThan(0);

  await expect(sayPanel.getByRole("button", { name: "確認画面へ" })).toBeDisabled();
});

test("アクション: 対象選択 + 本文 → 確認 → 投稿 → ログ反映", async ({ page }) => {
  const village = await ensureMasterInProgressVillage(page);

  await loginAsMasterUi(page);
  await page.goto(`village/${village.id}`);
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);

  const actionInput = page.getByLabel("アクション本文");
  expect(await actionInput.count()).toBeGreaterThan(0);
  const actionBodyId = await page
    .locator("button[aria-controls]")
    .filter({ hasText: /^アクション$/ })
    .getAttribute("aria-controls");
  const actionBody = page.locator(`[id="${actionBodyId}"]`);
  // 共有 DB で繰り返し実行すると 1 日のアクション回数を使い切るため、枯渇時はアサーション失敗
  expect(await actionBody.getByText(/残り0\/\d+回/).count()).toBe(0);

  const text = `に e2e アクション ${Date.now()}`;
  await page.getByLabel("アクションの対象").selectOption("全員");
  await actionInput.fill(text);
  await actionBody.getByRole("button", { name: "確認画面へ" }).click();

  const confirmArea = page.locator("#message-confirm-area");
  await expect(confirmArea).toBeVisible();
  await expect(confirmArea).toContainText(text);

  await confirmArea.getByRole("button", { name: "アクション" }).click();
  await expect(confirmArea).toHaveCount(0);
  await expect(page.locator(".message-action").filter({ hasText: text })).toBeVisible({
    timeout: 15000,
  });
});
