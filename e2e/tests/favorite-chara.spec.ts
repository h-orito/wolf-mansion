import { expect, test } from "@playwright/test";

/**
 * お気に入りキャラ e2e。
 *
 * - DB はローカル開発環境とあいのり (リセットしない)。signup で毎回 unique なユーザーを作り、
 *   お気に入りが空の状態から自己完結でテストする (auth.spec.ts と同じ方針)。
 */

// userId 制約: 3〜12 文字 / 英字始まり / 英数 - _ (backend SignupRequest)。
function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-7);
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `f${stamp}${rand}`; // 例: "f3k9f2a1xz" (10 文字)
}

const PASSWORD = "test1234!";

test("お気に入り登録 → 一覧表示 → 解除の自己完結フロー", async ({ page }) => {
  const userId = uniqueUserId();

  // --- signup (自動ログイン) ---
  await page.goto("signup");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", userId);
  await page.fill("#password", PASSWORD);
  await page.getByRole("button", { name: "作成" }).click();
  await expect(page.getByText(`ユーザID: ${userId}`)).toBeVisible();

  // --- キャラチップ詳細で最初のキャラをお気に入り登録 ---
  await page.goto("chara-group/1");
  const addButton = page.getByRole("button", { name: "お気に入り登録" }).first();
  await expect(addButton).toBeVisible({ timeout: 15000 });
  await addButton.click();
  // 登録が反映されると ★ (解除) に切り替わる (サーバ起動直後は反映が遅いことがあるため長めに待つ)
  await expect(page.getByRole("button", { name: "お気に入り解除" })).toHaveCount(1, {
    timeout: 15000,
  });

  // --- トップの「お気に入りキャラ」タイルから一覧へ遷移 ---
  await page.goto("");
  await page.getByRole("link", { name: "Favorite" }).click();
  await expect(page).toHaveURL(/\/favorite-charas\/?$/);
  await expect(page.getByRole("heading", { name: "お気に入りキャラ" })).toBeVisible();

  // キャラチップごとの見出しとカードが表示される
  await expect(page.getByRole("heading", { name: "人狼BBS" })).toBeVisible();
  const removeButton = page.getByRole("button", { name: "お気に入り解除" });
  await expect(removeButton).toHaveCount(1);

  // --- 解除すると空状態になる ---
  await removeButton.click();
  await expect(page.getByText("お気に入りキャラはいません。")).toBeVisible();
});

test("未ログインで /favorite-charas に来ると /login にリダイレクトされる", async ({ page }) => {
  await page.goto("favorite-charas");

  await expect(page).toHaveURL(/\/login\?returnTo=/);
  await expect(page.getByRole("button", { name: "ログイン" })).toBeVisible();
});

test("未ログインのキャラチップ詳細にはお気に入りトグルが表示されない", async ({ page }) => {
  await page.goto("chara-group/1");

  // キャラカードの描画完了を待ってからトグル不在を確認する
  await expect(page.getByRole("heading", { name: /キャラチップ:/ })).toBeVisible();
  await expect(page.getByText("部屋割り例")).toBeVisible();
  await expect(page.getByRole("button", { name: "お気に入り登録" })).toHaveCount(0);
});
