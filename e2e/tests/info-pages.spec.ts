import { expect, test } from "@playwright/test";

/**
 * 情報ページ群 (`/about` `/faq` `/practice` `/announce`) e2e。
 *
 * いずれも静的ページのため smoke 表示確認 + practice の解答開閉のみ。
 */

test("about ページが表示される", async ({ page }) => {
  await page.goto("about");

  await expect(page).toHaveTitle("WOLF MANSION | 本サイトは");
  await expect(page.getByRole("heading", { name: "本サイトは" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "人狼館の事件簿村とは" })).toBeVisible();
  await expect(page.getByRole("heading", { name: "タイトルロゴについて" })).toBeVisible();

  // intro / rule への内部リンク
  await expect(page.getByRole("link", { name: "人狼館の事件簿村", exact: true })).toBeVisible();
  await expect(page.getByRole("link", { name: "ルール", exact: true })).toBeVisible();
});

test("faq ページが表示される", async ({ page }) => {
  await page.goto("faq");

  await expect(page).toHaveTitle("WOLF MANSION | FAQ");
  await expect(page.getByText("入村できないのですが")).toBeVisible();
  await expect(
    page.getByText("ルールにもここにも書かれてないことでわからないことがあるのですが"),
  ).toBeVisible();
});

test("announce ページが表示される", async ({ page }) => {
  await page.goto("announce");

  await expect(page).toHaveTitle("WOLF MANSION | お知らせ");
  await expect(page.getByRole("heading", { name: "リリースノート" })).toBeVisible();
  // 最古のエントリまで描画されている
  await expect(page.getByText("2018/03/25 じっぷ様にキャラチップ「")).toBeVisible();
});

test("practice ページが表示され、答えを開閉できる", async ({ page }) => {
  await page.goto("practice");

  await expect(page).toHaveTitle("WOLF MANSION | 人狼館の事件簿村ルール 練習問題");
  await expect(page.getByText("第1問")).toBeVisible();
  await expect(page.getByText("第3問")).toBeVisible();
  await expect(page.getByRole("link", { name: "ルール紹介へ戻る" })).toBeVisible();

  // 解答は開くまで非表示
  const answer = page.getByText("・導師でも結果発表以外の推理発言はしてはいけません。");
  await expect(answer).toBeHidden();

  const toggle = page.getByRole("button", { name: "答えを開く" }).first();
  await toggle.click();
  await expect(answer).toBeVisible();
  await toggle.click();
  await expect(answer).toBeHidden();
});

test("ホームから about へ SPA 遷移できる", async ({ page }) => {
  await page.goto("");

  await page.getByRole("link", { name: "About" }).click();
  await expect(page).toHaveURL(/\/about$/);
  await expect(page.getByRole("heading", { name: "本サイトは" })).toBeVisible();
});
