import { expect, test } from "@playwright/test";

test("キャラチップ一覧が表示され、テーブルにキャラセットが並ぶ", async ({ page }) => {
  await page.goto("chara-group");

  await expect(page).toHaveTitle("WOLF MANSION | キャラチップ一覧");
  await expect(page.getByRole("heading", { name: "キャラチップ一覧" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "キャラチップ製作者様へ" })).toBeVisible();

  // テーブルヘッダが表示される
  await expect(page.getByText("キャラチップ名")).toBeVisible();
  await expect(page.getByText("作者名")).toBeVisible();

  // 少なくとも1つのキャラセットがリンクとして表示される
  const firstLink = page.locator("table a").first();
  await expect(firstLink).toBeVisible();
});

test("一覧から詳細画面へ遷移できる", async ({ page }) => {
  await page.goto("chara-group");

  const firstLink = page.locator("table a").first();
  const chipName = await firstLink.textContent();
  await firstLink.click();

  await expect(page).toHaveURL(/\/chara-group\/\d+\/?$/);
  await expect(page.getByRole("heading", { name: `キャラチップ: ${chipName}` })).toBeVisible();
});

test("詳細画面にキャラ一覧と部屋割り例が表示される", async ({ page }) => {
  // id=1 (人狼BBS) の詳細を直接開く
  await page.goto("chara-group/1");

  await expect(page.getByRole("heading", { name: /キャラチップ:/ })).toBeVisible();
  await expect(page.getByText("作者:")).toBeVisible();
  await expect(page.getByText("肩書・名称変更:")).toBeVisible();
  await expect(page.getByRole("heading", { name: "部屋割り例" })).toBeVisible();

  // 部屋割りテーブルが存在する
  const roomTable = page.locator("table").last();
  await expect(roomTable).toBeVisible();
});

test("ホームからキャラチップ一覧へ SPA 遷移できる", async ({ page }) => {
  await page.goto("");

  await page.getByRole("link", { name: "Character list" }).click();
  await expect(page).toHaveURL(/\/chara-group\/?$/);
  await expect(page.getByRole("heading", { name: "キャラチップ一覧" })).toBeVisible();
});
