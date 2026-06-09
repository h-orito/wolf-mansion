import { expect, test } from "@playwright/test";

/**
 * ルール (`/rule`) e2e。
 *
 * ページ表示・目次アンカー・各セクションの基本表示を確認する。
 */

test("ルールページが表示され、主要セクションがある", async ({ page }) => {
  await page.goto("rule");

  await expect(page).toHaveTitle("WOLF MANSION | ルール");
  await expect(page.getByRole("heading", { name: "ルール", exact: true })).toBeVisible();

  // 主要セクション見出し
  await expect(page.getByRole("heading", { name: "人狼の基本ルール" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "詳細ルール", exact: true })).toBeVisible();
  await expect(page.getByRole("heading", { name: "役職詳細", exact: true })).toBeVisible();
  await expect(page.getByRole("heading", { name: "占霊判定、勝敗時のカウント" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "ステータス", exact: true })).toBeVisible();
  await expect(page.getByRole("heading", { name: "その他", exact: true })).toBeVisible();
});

test("目次に陣営・役職のネストリンクが動的生成される", async ({ page }) => {
  await page.goto("rule");

  // API データの読み込みを待つ
  await expect(page.getByRole("link", { name: "村人陣営" }).first()).toBeVisible({ timeout: 10000 });
  await expect(page.getByRole("link", { name: "人狼陣営" }).first()).toBeVisible();

  // 目次内に代表的な役職リンクがある
  await expect(page.getByRole("link", { name: "占い師" }).first()).toBeVisible();
});

test("ホームからルールへ SPA 遷移できる", async ({ page }) => {
  await page.goto("");

  await page.getByRole("link", { name: "ルール Rule" }).click();
  await expect(page).toHaveURL(/\/rule$/);
  await expect(page.getByRole("heading", { name: "ルール", exact: true })).toBeVisible();
});
