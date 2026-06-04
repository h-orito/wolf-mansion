import { expect, test } from "@playwright/test";

/**
 * Step 2.4 雛形の smoke テスト。
 *
 * 目的は「Playwright + pnpm の器が動き、webServer 経由で frontend が起動して
 * トップ画面が表示される」ことの確認のみ。本格的なテストケース (認証 / 村ライフ
 * サイクル / 認可マスク 等) は村画面が動く Step 8 以降に doc/migration/scenarios/
 * を起点として authoring する。
 *
 * frontend のトップ (RR v7 の welcome 画面) は backend 非依存だが、webServer 設定
 * 上 backend も起動される (実 e2e は backend REST を叩くため)。
 */
test("frontend トップ画面が表示される", async ({ page }) => {
  await page.goto("/");

  // RR v7 scaffold の既定 title (frontend/app/routes/home.tsx の meta)。
  // Step 8 以降で実アプリ画面に差し替わったら、この smoke も合わせて更新する。
  await expect(page).toHaveTitle("New React Router App");

  // welcome 画面のリンク (frontend/app/welcome/welcome.tsx)
  await expect(
    page.getByRole("link", { name: "React Router Docs" }),
  ).toBeVisible();
});
