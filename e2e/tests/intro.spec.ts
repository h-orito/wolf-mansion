import { expect, test } from "@playwright/test";

/**
 * イントロ (`/intro`) e2e。
 *
 * 静的ページの smoke テスト。見出し・セクション・画像・ナビリンクの存在を確認する。
 */

test("intro ページが表示され、主要セクションとナビリンクがある", async ({ page }) => {
  await page.goto("intro");

  await expect(page).toHaveTitle("WOLF MANSION | 人狼館の事件簿村ルール");
  await expect(page.getByRole("heading", { name: "このページは" })).toBeVisible();

  // 各セクション見出し
  await expect(page.getByRole("heading", { name: "白発言での推理発言やCO禁止" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "足音の基本ルール" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "隣接した部屋だと足音は響かない" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "おわり" })).toBeVisible();

  // ナビリンク
  await expect(page.getByRole("link", { name: "サイトトップへ" })).toBeVisible();
  await expect(page.getByRole("link", { name: "練習問題へ" })).toBeVisible();
});

test("ホームから intro へ SPA 遷移できる", async ({ page }) => {
  await page.goto("");

  await page.getByRole("link", { name: "Introduction" }).click();
  await expect(page).toHaveURL(/\/intro$/);
  await expect(page.getByRole("heading", { name: "このページは" })).toBeVisible();
});
