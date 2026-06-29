import { expect, test } from "@playwright/test";

/**
 * 役職一覧 (`/skill`) e2e。
 *
 * 一覧表示・検索フォーム・タグ絞り込みの基本動作を確認する。
 */

test("役職一覧ページが表示され、陣営メニューと役職詳細がある", async ({ page }) => {
  await page.goto("skill");

  await expect(page).toHaveTitle("WOLF MANSION | 役職一覧");
  await expect(page.getByRole("heading", { name: "役職一覧" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "役職詳細" })).toBeVisible();

  // 陣営メニューに5陣営のリンクがある
  await expect(page.getByText("村人陣営").first()).toBeVisible();
  await expect(page.getByText("人狼陣営").first()).toBeVisible();

  // 占い師が表示されている (代表的な役職)
  await expect(page.getByText("【占】占い師").first()).toBeVisible();
});

test("タグで絞り込みができる", async ({ page }) => {
  await page.goto("skill");

  // 「護衛」タグをクリック
  await page.getByRole("button", { name: "護衛", exact: true }).click();
  // 検索パネル内の検索ボタンをクリック (パネルヘッダーの「検索」と区別するため last)
  await page.getByRole("button", { name: "検索", exact: true }).last().click();

  // 狩人が表示される
  await expect(page.getByText("【狩】狩人").first()).toBeVisible();
  // 村人は表示されない (護衛能力なし)
  await expect(page.getByText("【村】村人")).toBeHidden();
});

test("ホームから役職一覧へ SPA 遷移できる", async ({ page }) => {
  await page.goto("");

  await page.getByRole("link", { name: "Skill" }).click();
  await expect(page).toHaveURL(/\/skill\/?$/);
  await expect(page.getByRole("heading", { name: "役職一覧" })).toBeVisible();
});
