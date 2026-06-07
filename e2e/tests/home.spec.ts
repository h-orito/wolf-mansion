import { expect, test } from "@playwright/test";

/**
 * ホーム (`/`) e2e (Step 4.1)。
 *
 * - 公開ランディング。匿名/ログインでタイルが出し分けされ、開催中の村一覧が表示される。
 * - DB はローカル開発環境とあいのり (リセットしない)。村データの有無に依存しないよう、村行の検証は
 *   行が存在する場合のみ行う (空でも構造は描画される)。
 * - CI 非実行 (local 専用)。
 */

function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-7);
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `e${stamp}${rand}`;
}

const PASSWORD = "test1234!";

test("匿名: ナビ/登録ログインタイルと開催中の村セクションが表示される", async ({ page }) => {
  // baseURL は `…/wolf-mansion/`。相対 goto でトップ (/wolf-mansion/) を開く。
  await page.goto("");

  // 共通ナビタイル (未移行 SSR への導線)
  await expect(page.getByRole("link", { name: "About" })).toBeVisible();
  await expect(page.getByRole("link", { name: "Introduction" })).toBeVisible();
  await expect(page.getByRole("link", { name: "Skill" })).toBeVisible();

  // 匿名は ID登録 / ログイン タイル
  await expect(page.getByRole("link", { name: "ID登録" })).toBeVisible();
  await expect(page.getByRole("link", { name: "ログイン" })).toBeVisible();
  // ログイン中専用タイルは出ない
  await expect(page.getByRole("button", { name: /ログアウト/ })).toHaveCount(0);

  // 開催中の村セクション
  await expect(page.getByRole("heading", { name: "開催中の村" })).toBeVisible();

  // 村行があれば、リンク先が /village/{id} になっていることを確認 (空 DB では skip)
  const villageLinks = page.locator('a[href*="/village/"]');
  if ((await villageLinks.count()) > 0) {
    await expect(villageLinks.first()).toHaveAttribute("href", /\/village\/\d+$/);
  }
});

test("ログイン後: マイページ/ログアウトタイルに切り替わる", async ({ page }) => {
  const userId = uniqueUserId();

  // signup で自動ログイン → トップへ
  await page.goto("signup");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", userId);
  await page.fill("#password", PASSWORD);
  // signup ボタンは `:8091` の new-player.html に合わせ「作成」(step-3.6 で忠実再現)。
  await page.getByRole("button", { name: "作成" }).click();

  await expect(page).toHaveURL(/\/wolf-mansion$/);
  // トップ画像にユーザID、ログイン専用タイル
  await expect(page.getByText(`ユーザID: ${userId}`)).toBeVisible();
  await expect(page.getByRole("link", { name: "マイページ" })).toBeVisible();
  await expect(page.getByRole("button", { name: /ログアウト/ })).toBeVisible();
  // 匿名専用タイルは消える
  await expect(page.getByRole("link", { name: "ID登録" })).toHaveCount(0);
});
