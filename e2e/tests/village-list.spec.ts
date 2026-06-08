import { expect, test } from "@playwright/test";

/**
 * 村一覧 (`/village-list`) e2e (Step 4.2)。
 *
 * - 公開画面。全村 (終了/廃村含む) の一覧 + 検索 (キャラセット/役職/編成) を提供する。
 * - DB はローカル開発環境とあいのり (リセットしない)。村行の検証は行が存在する場合のみ行う。
 * - CI 非実行 (local 専用)。
 */

test("村一覧: 見出し・検索パネル (初期は閉) が表示される", async ({ page }) => {
  await page.goto("village-list");

  await expect(page).toHaveTitle("WOLF MANSION | 村一覧");
  await expect(page.getByRole("heading", { name: "村一覧" })).toBeVisible();

  // 検索トグルは見えるが、フォーム (キャラセット等) は初期では閉じている。
  const toggle = page.getByRole("button", { name: "検索", expanded: false });
  await expect(toggle).toBeVisible();
  await expect(page.getByLabel("キャラセット")).toHaveCount(0);
});

test("村一覧: 検索パネルを開くとキャラセット/役職/編成が表示される", async ({ page }) => {
  await page.goto("village-list");

  await page.getByRole("button", { name: "検索", expanded: false }).click();

  await expect(page.getByLabel("キャラセット")).toBeVisible();
  await expect(page.getByLabel("役職")).toBeVisible();
  await expect(page.getByRole("button", { name: "両方", exact: true })).toBeVisible();
  await expect(page.getByRole("button", { name: "闇鍋", exact: true })).toBeVisible();
  await expect(page.getByRole("button", { name: "固定", exact: true })).toBeVisible();
});

test("村一覧: 編成で絞り込むと URL の searchParams が更新される", async ({ page }) => {
  await page.goto("village-list");

  await page.getByRole("button", { name: "検索", expanded: false }).click();
  await page.getByRole("button", { name: "闇鍋", exact: true }).click();
  // フォーム送信ボタン (「検索」) を押す。トグル (同名・type=button) と区別するため type=submit を指定。
  await page.locator('form button[type="submit"]').click();

  await expect(page).toHaveURL(/random=true/);
});

test("村一覧: 村行があれば村名リンクが /village/{id} を指す", async ({ page }) => {
  await page.goto("village-list");

  // 全村対象なので通常は 1 件以上ある。空 DB では skip。
  const villageLinks = page.locator('a[href*="/village/"]');
  if ((await villageLinks.count()) > 0) {
    await expect(villageLinks.first()).toHaveAttribute("href", /\/village\/\d+$/);
    // テーブルヘッダーも確認。
    await expect(page.getByRole("columnheader", { name: "村番号" })).toBeVisible();
    await expect(page.getByRole("columnheader", { name: "村名" })).toBeVisible();
  }
});

test("ホーム: 村一覧タイルから村一覧画面へ遷移する", async ({ page }) => {
  await page.goto("");

  await page.getByRole("link", { name: "Village list" }).click();

  await expect(page).toHaveURL(/\/wolf-mansion\/village-list$/);
  await expect(page.getByRole("heading", { name: "村一覧" })).toBeVisible();
});
