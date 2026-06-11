import { expect, test } from "@playwright/test";

/**
 * 村作成フォーム e2e。
 *
 * フォーム本体 (表示・入力・クライアント検証) のみを対象とする。
 * 確認モーダル → 作成は未実装のため、村は作成しない (DB を汚さない)。
 * ページはログイン必須のため、テスト内で signup して自前のアカウントを使う
 * (ページ閲覧は認証のみで可。村作成可否の条件は作成時にサーバーで検証される)。
 */

// userId 制約: 3〜12 文字 / 英字始まり / 英数 - _ (backend SignupRequest)。
function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-7);
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `v${stamp}${rand}`;
}

const PASSWORD = "test1234!";

async function signupAndGotoNewVillage(page: import("@playwright/test").Page) {
  const userId = uniqueUserId();
  await page.goto("signup");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", userId);
  await page.fill("#password", PASSWORD);
  await page.getByRole("button", { name: "作成" }).click();
  await expect(page.getByText(`ユーザID: ${userId}`)).toBeVisible();
  await page.goto("new-village");
  await expect(page.getByRole("heading", { name: "村作成" })).toBeVisible();
}

test("未ログインで村作成ページに来ると /login にリダイレクトされる", async ({ page }) => {
  await page.goto("new-village");
  await expect(page).toHaveURL(/\/login\?returnTo=/);
});

test("フォームが既定値で表示される", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  // 基本設定の既定値
  await expect(page.locator("#startPersonMinNum")).toHaveValue("8");
  await expect(page.locator("#personMaxNum")).toHaveValue("20");
  await expect(page.getByLabel("更新間隔 (時間)")).toHaveValue("24");

  // 固定編成が既定で表示され、既定編成テキストに「N人：」プレフィックスが付く
  const organization = page.getByLabel("固定の役職構成");
  await expect(organization).toBeVisible();
  await expect(organization).toHaveValue(/^8人：村狼狼賢導村村村\n9人：/);

  // 全セクション見出し
  for (const title of [
    "基本設定",
    "詳細ルール設定",
    "見学、閲覧設定",
    "身内村向け設定",
    "特殊ルール向け",
    "RP村向け",
  ]) {
    await expect(page.getByRole("heading", { name: title })).toBeVisible();
  }
});

test("固定と闇鍋の編成切替で表示が切り替わる", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  const orgRadioGroup = page.getByRole("radiogroup", { name: "役職構成" });
  await expect(orgRadioGroup.getByRole("radio", { name: "固定" })).toHaveAttribute(
    "aria-checked",
    "true",
  );

  // 闇鍋に切替 → 配分テーブル表示。村人の最少人数は 1 が既定
  await orgRadioGroup.getByRole("radio", { name: "闇鍋" }).click();
  await expect(page.getByText("人狼カウント").first()).toBeVisible();
  await expect(page.getByLabel("村人 最少人数")).toHaveValue("1");

  // 固定に戻す → テキストエリア表示
  await orgRadioGroup.getByRole("radio", { name: "固定" }).click();
  await expect(page.getByLabel("固定の役職構成")).toBeVisible();
});

test("発言制限のチェックで入力の有効/無効が切り替わる", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  // 3 テーブル (役職別 / 発言種別 / RP) が表示される
  await expect(page.getByLabel("村人 発言文字数", { exact: true })).toBeVisible();
  await expect(page.getByLabel("人狼の囁き 発言文字数", { exact: true })).toBeVisible();
  await expect(page.getByLabel("アクション 発言文字数", { exact: true })).toBeVisible();

  // 既定はチェックなし + 入力無効 (400 * 20)
  const lengthInput = page.getByLabel("村人 発言文字数", { exact: true });
  const countInput = page.getByLabel("村人 発言回数", { exact: true });
  await expect(lengthInput).toBeDisabled();
  await expect(lengthInput).toHaveValue("400");
  await expect(countInput).toHaveValue("20");

  await page.getByLabel("村人 制限", { exact: true }).check();
  await expect(lengthInput).toBeEnabled();
  await expect(countInput).toBeEnabled();

  await page.getByLabel("村人 制限", { exact: true }).uncheck();
  await expect(lengthInput).toBeDisabled();
});

test("村人の設定を全てにコピーで役職別テーブルの全行に反映される", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  await page.getByLabel("村人 制限", { exact: true }).check();
  await page.getByLabel("村人 発言文字数", { exact: true }).fill("200");
  await page.getByRole("button", { name: "村人の設定を全てにコピー" }).click();

  await expect(page.getByLabel("占い師 制限", { exact: true })).toBeChecked();
  await expect(page.getByLabel("占い師 発言文字数", { exact: true })).toBeEnabled();
  await expect(page.getByLabel("占い師 発言文字数", { exact: true })).toHaveValue("200");

  // 発言種別 / RP のテーブルには波及しない
  await expect(page.getByLabel("人狼の囁き 制限", { exact: true })).not.toBeChecked();
  await expect(page.getByLabel("アクション 制限", { exact: true })).not.toBeChecked();
});

test("発言制限の範囲外入力でエラーが表示される", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  await page.getByLabel("村人 制限", { exact: true }).check();
  await page.getByLabel("村人 発言文字数", { exact: true }).fill("500");
  await page.getByLabel("村人 発言文字数", { exact: true }).blur();
  await expect(page.getByText("発言制限は0~400 * 0~100 で設定してください")).toBeVisible();

  await page.getByLabel("村人 発言文字数", { exact: true }).fill("400");
  await expect(page.getByText("発言制限は0~400 * 0~100 で設定してください")).not.toBeVisible();
});

test("クライアント検証エラーが表示される", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  // 村名: 5 文字未満
  await page.fill("#villageName", "あいう");
  await page.locator("#villageName").blur();
  await expect(page.getByText("村名は5文字以上40文字以下で入力してください")).toBeVisible();

  // 定員 < 最少開始人数 (相関)
  await page.fill("#personMaxNum", "5");
  await page.locator("#personMaxNum").blur();
  await expect(page.getByText("定員は最少開始人数以上で設定してください")).toBeVisible();

  // 入村パスワード: 2 文字
  await page.fill("#joinPassword", "ab");
  await page.locator("#joinPassword").blur();
  await expect(page.getByText("入村パスワードは3文字以上12文字以内にしてください")).toBeVisible();
});
