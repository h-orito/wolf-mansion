import { expect, test } from "@playwright/test";

/**
 * エイプリル企画アーカイブ (`/archives/april-*`) e2e。
 *
 * 2ch スレ風アーカイブ 2 ページ + ランダム役職説明アーカイブの smoke テスト。
 */

test("april-20250401 アーカイブが表示される", async ({ page }) => {
  await page.goto("archives/april-20250401");

  await expect(page).toHaveTitle("WOLF MANSION 〜人狼館の事件簿村〜");
  await expect(page.getByText("WOLF MANSIONのくにぬしだけど何か質問ある？")).toBeVisible();

  // スレ内のサイト案内リンク (SPA リンク)
  await expect(
    page.getByRole("link", { name: "ttps://wolfort.net/wolf-mansion/about" }),
  ).toBeVisible();
  // 村一覧 (API 由来の動的ブロック) のあとに出る固定リンク
  await expect(
    page.getByRole("link", { name: "ttps://wolfort.net/wolf-mansion/village-list" }),
  ).toBeVisible();
});

test("april-20250402 アーカイブが表示される", async ({ page }) => {
  await page.goto("archives/april-20250402");

  await expect(page).toHaveTitle("WOLF MANSION 〜人狼館の事件簿村〜");
  await expect(page.getByText("エイプリルフール二日目だけど何か質問ある？")).toBeVisible();
  await expect(
    page.getByRole("link", { name: "ttps://wolfort.net/wolf-mansion/faq" }),
  ).toBeVisible();
});

test("april-20260401 アーカイブが表示される", async ({ page }) => {
  await page.goto("archives/april-20260401");

  await expect(page).toHaveTitle("WOLF MANSION | エイプリルフール企画 (2026/04/01)");
  await expect(
    page.getByRole("heading", { name: "エイプリルフール企画 (2026/04/01)" }),
  ).toBeVisible();
  // 役職説明が全件 (29 種) 表示される
  await expect(page.getByText("です。水と昆布と一緒に火にかけられることができます。")).toBeVisible();
  await expect(page.getByText("（ファミチキください）")).toBeVisible();
});
