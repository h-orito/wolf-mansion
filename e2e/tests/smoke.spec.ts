import { expect, test } from "@playwright/test";

/**
 * smoke テスト。
 *
 * 目的は「Playwright + pnpm の器が動き、webServer 経由で frontend が起動して
 * トップ画面が表示される」ことの確認のみ。本格的な認証/村ライフサイクルの検証は
 * auth.spec.ts および Step 8 以降の scenarios で行う。
 *
 * トップの見出しは SSR で即描画されるため、認証 (CSR) の解決を待たずに確認できる。
 */
test("frontend トップ画面が表示される", async ({ page }) => {
  await page.goto("/");

  await expect(page).toHaveTitle("WOLF MANSION 〜人狼館の事件簿村〜");
  await expect(page.getByRole("heading", { name: "開催中の村" })).toBeVisible();
});
