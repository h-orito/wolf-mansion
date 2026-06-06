import { expect, test } from "@playwright/test";

/**
 * 認証フロー e2e (Step 3.3)。
 *
 * - DB はローカル開発環境とあいのり (リセットしない)。signup は毎回 unique な userId を生成し、
 *   既存データと衝突しないようにする。
 * - frontend は Vite proxy (/wolf-mansion → backend) 経由で REST を叩き、backend が HttpOnly
 *   Cookie を発行する (CSR 境界 / 03-auth.md)。
 * - CI 非実行 (local 専用)。
 */

// userId 制約: 3〜12 文字 / 英字始まり / 英数 - _ (backend SignupRequest)。
function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-7);
  // fullyParallel で同一 ms に開始したテスト同士の衝突 (= 紛らわしい 400) を避けるため
  // 乱数サフィックスを 2 文字に広げる。"e" + 7 + 2 = 10 文字 (上限 12 内)。
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `e${stamp}${rand}`; // 例: "e3k9f2a1xz" (10 文字)
}

const PASSWORD = "test1234!";

test("signup → me 表示 → logout → login の自己完結フロー", async ({ page }) => {
  const userId = uniqueUserId();

  // --- signup (自動ログイン) ---
  await page.goto("/signup");
  // フルページロード直後はまだ hydration 前で、submit がネイティブ GET 送信になりうる。
  // JS のロード完了 (= hydration 完了) を待ってからフォーム操作する。
  // 以降の遷移は SPA (client) なので待ち不要。
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", userId);
  await page.fill("#password", PASSWORD);
  await page.getByRole("button", { name: /登録/ }).click();

  // 成功でトップへ。ようこそ表示にログイン名 (= userId) が出る。
  await expect(page).toHaveURL(/\/$/);
  await expect(page.getByText("ようこそ")).toBeVisible();
  await expect(page.getByText(userId)).toBeVisible();

  // --- マイページで me 情報を確認 ---
  await page.getByRole("link", { name: "マイページ" }).click();
  await expect(page).toHaveURL(/\/mypage$/);
  await expect(page.getByText(userId)).toBeVisible();

  // --- logout ---
  await page.getByRole("button", { name: /ログアウト/ }).click();
  await expect(page).toHaveURL(/\/$/);
  await expect(page.getByRole("link", { name: "ログイン" })).toBeVisible();

  // --- 同じ ID で login ---
  await page.getByRole("link", { name: "ログイン" }).click();
  await expect(page).toHaveURL(/\/login/);
  await page.fill("#userId", userId);
  await page.fill("#password", PASSWORD);
  await page.getByRole("button", { name: "ログイン" }).click();

  await expect(page).toHaveURL(/\/$/);
  await expect(page.getByText("ようこそ")).toBeVisible();
  await expect(page.getByText(userId)).toBeVisible();
});

test("未ログインで保護ルートに来ると /login にリダイレクトされる", async ({ page }) => {
  // 新規コンテキスト (Cookie なし) で保護ルートへ直接アクセス。
  await page.goto("/mypage");

  // RequireAuth が returnTo 付きで /login へ飛ばす。
  await expect(page).toHaveURL(/\/login\?returnTo=/);
  await expect(page.getByRole("button", { name: "ログイン" })).toBeVisible();
});
