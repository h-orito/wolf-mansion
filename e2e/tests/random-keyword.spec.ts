import { expect, test } from "@playwright/test";

/**
 * ランダムキーワード e2e。
 *
 * - 閲覧 (一覧・編集フォーム) は公開、書き込み (作成・更新・削除) はログイン必須。
 * - DB はローカル開発環境とあいのり (リセットしない)。キーワードは毎回 unique に生成し、
 *   テスト内で削除まで行って後始末する。
 * - 作成はログイン必須のため、テスト内で signup して自前のアカウントを使う。
 */

// userId 制約: 3〜12 文字 / 英字始まり / 英数 - _ (backend SignupRequest)。
function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-7);
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `k${stamp}${rand}`;
}

// keyword 制約: 3〜10 文字 / 半角英字のみ / or・who を含まない。
// "o" と "w" を除いた英字から生成して NG ワードを構造的に回避する。
function uniqueKeyword(): string {
  const letters = "abcdefghijklmnpqrstuvxyz";
  return Array.from(
    { length: 8 },
    () => letters[Math.floor(Math.random() * letters.length)],
  ).join("");
}

const PASSWORD = "test1234!";

test("一覧は未ログインで閲覧できる", async ({ page }) => {
  await page.goto("random-message");
  await expect(page.getByRole("heading", { name: "ランダムメッセージ一覧" })).toBeVisible();
  await expect(page.getByRole("link", { name: "新規追加" })).toBeVisible();
});

test("未ログインで作成ページに来ると /login にリダイレクトされる", async ({ page }) => {
  await page.goto("new-random-keyword");
  await expect(page).toHaveURL(/\/login\?returnTo=/);
});

test("未ログインの書き込み API は 401 になる", async ({ request }) => {
  const res = await request.post("/wolf-mansion-api/api/v1/random-keywords", {
    data: { keyword: "abcde", messages: ["x"] },
  });
  expect(res.status()).toBe(401);
});

test("作成 → 一覧反映 → 検索 → 編集 → 削除 の CRUD フロー", async ({ page }) => {
  const userId = uniqueUserId();
  const keyword = uniqueKeyword();

  // --- signup (自動ログイン) ---
  await page.goto("signup");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", userId);
  await page.fill("#password", PASSWORD);
  await page.getByRole("button", { name: "作成" }).click();
  await expect(page.getByText(`ユーザID: ${userId}`)).toBeVisible();

  // --- 作成 (6 行 = 折りたたみ境界の 5 行を超える) ---
  await page.goto("new-random-keyword");
  await page.fill("#keyword", keyword);
  await page.fill("#message", "い\nろ\nは\nに\nほ\nへ");
  await page.getByRole("button", { name: "登録" }).click();

  // 一覧に反映。6 行目は折りたたまれ「全て表示」で展開される
  await expect(page).toHaveURL(/\/random-message$/);
  const row = page.getByRole("row", { name: new RegExp(keyword) });
  await expect(row.getByText(`[[${keyword}]]`)).toBeVisible();
  await expect(row.getByText("へ")).toBeHidden();
  await row.getByRole("button", { name: "全て表示" }).click();
  await expect(row.getByText("へ")).toBeVisible();

  // --- 検索 (他キーワードがあっても自分の行だけ残る) ---
  await page.getByPlaceholder("キーワード絞り込み").fill(keyword);
  await page.getByRole("button", { name: "検索" }).click();
  await expect(page.getByText(`[[${keyword}]]`)).toBeVisible();

  // --- 編集 → 反映 ---
  await row.getByRole("link", { name: "編集" }).click();
  await expect(page.getByRole("heading", { name: "ランダムメッセージ編集" })).toBeVisible();
  await page.fill("#message", "甲\n乙\n丙");
  await page.getByRole("button", { name: "登録" }).click();
  await expect(page).toHaveURL(/\/random-message$/);
  await expect(page.getByText("甲")).toBeVisible();

  // --- 削除 (確認ダイアログ) → 一覧から消える ---
  await page
    .getByRole("row", { name: new RegExp(keyword) })
    .getByRole("link", { name: "編集" })
    .click();
  page.once("dialog", (dialog) => dialog.accept());
  await page.getByRole("button", { name: "削除" }).click();
  await expect(page).toHaveURL(/\/random-message$/);
  await expect(page.getByText(`[[${keyword}]]`)).toBeHidden();
});
