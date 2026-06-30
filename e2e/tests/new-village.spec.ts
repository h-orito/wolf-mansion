import { expect, test } from "@playwright/test";
import { ensureVillagesExist, uniqueUserId } from "./helpers/provision";

/**
 * 村作成フォーム e2e。
 *
 * フォーム本体 (表示・入力・クライアント検証) のみを対象とする。
 * 確認モーダル → 作成は未実装のため、村は作成しない (DB を汚さない)。
 * ページはログイン必須のため、テスト内で signup して自前のアカウントを使う
 * (ページ閲覧は認証のみで可。村作成可否の条件は作成時にサーバーで検証される)。
 */

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

test("キャラチップ設定が既定値で表示されダミーキャラ情報が自動入力される", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  // 既定: 人狼BBS が選択され、ダミーキャラ候補の読み込み後に楽天家 ゲルトが選ばれる
  const charaSet = page.getByLabel("キャラセット");
  await expect(charaSet).toBeVisible();
  await expect(charaSet.locator("option", { hasText: "人狼BBS" })).toHaveCount(1);

  const dummySelect = page.locator("#dummyCharaId");
  await expect(dummySelect).toHaveValue("1");
  await expect(page.locator("#dummyCharaName")).toHaveValue("楽天家 ゲルト");
  await expect(page.locator("#dummyCharaShortName")).toHaveValue("楽");
  await expect(page.locator("#dummyJoinMessage")).not.toHaveValue("");
});

test("ダミーキャラ変更でキャラ名・略称が置き換わる", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  // 自動入力済みの入村発言に対する上書き confirm はキャンセルし、名前・略称の置換だけを検証する
  page.on("dialog", (dialog) => dialog.dismiss());

  const dummySelect = page.locator("#dummyCharaId");
  await expect(dummySelect).toHaveValue("1");
  await dummySelect.selectOption({ label: "村長 ヴァルター" });
  await expect(page.locator("#dummyCharaName")).toHaveValue("村長 ヴァルター");
  await expect(page.locator("#dummyCharaShortName")).toHaveValue("長");
});

test("キャラチップ利用の切替でキャラセット選択と案内が切り替わる", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  const radioGroup = page.getByRole("radiogroup", { name: "キャラチップ利用" });
  await radioGroup.getByRole("radio", { name: "自分で用意する" }).click();
  await expect(page.getByLabel("キャラセット")).not.toBeVisible();
  await expect(page.locator("#dummyCharaId")).not.toBeVisible();
  await expect(
    page.getByText("オリジナル画像を各参加者がアップロードして使用します。"),
  ).toBeVisible();

  await radioGroup.getByRole("radio", { name: "利用する", exact: true }).click();
  await expect(page.getByLabel("キャラセット")).toBeVisible();
  await expect(page.locator("#dummyCharaId")).toBeVisible();
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

test("設定流用セクションが表示され、流用で選択した村の設定がフォームへ流し込まれる", async ({ page }) => {
  test.setTimeout(180000);
  await signupAndGotoNewVillage(page);

  await expect(page.getByRole("heading", { name: "設定流用" })).toBeVisible();
  const select = page.getByLabel("他の村から流用する");
  await expect(select).toBeVisible();
  await expect(page.getByRole("button", { name: "流用する" })).toBeVisible();

  // 流用候補 (エピローグ/終了/廃村の村) が存在することを保証する
  await ensureVillagesExist(page, ["EPILOGUE", "COMPLETED", "CANCEL"]);

  // 流用候補が select に入るまで待つ
  const listRes = await page.request.get(
    "/wolf-mansion-api/api/v1/villages?status=EPILOGUE&status=COMPLETED&status=CANCEL&order=asc",
  );
  expect(listRes.ok()).toBe(true);
  const candidates = (await listRes.json()).villages as { id: number }[];

  // 先頭候補が select に入るまで待つ (既定で選択される)
  await expect(select).toHaveValue(String(candidates[0].id));
  const villageId = candidates[0].id;
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages/${villageId}/setting`);
  expect(res.ok()).toBe(true);
  const setting = await res.json();

  // 編集状態は保持されない (村名は流用対象外のため既定値 = 空に戻る)
  await page.fill("#villageName", "流用前の編集");
  await page.getByRole("button", { name: "流用する" }).click();

  await expect(page.locator("#villageName")).toHaveValue("");
  await expect(page.locator("#startPersonMinNum")).toHaveValue(String(setting.personMin));
  await expect(page.locator("#personMaxNum")).toHaveValue(String(setting.personMax));
  await expect(page.getByLabel("更新間隔 (時間)")).toHaveValue(
    String(Math.floor(setting.dayChangeIntervalSeconds / 3600)),
  );

  // 編成 (固定/闇鍋) も流用元に合わせて切り替わる
  const orgRadioGroup = page.getByRole("radiogroup", { name: "役職構成" });
  await expect(
    orgRadioGroup.getByRole("radio", { name: setting.rule.isRandomOrganization ? "闇鍋" : "固定" }),
  ).toHaveAttribute("aria-checked", "true");
});

test("確認画面へ→確認モーダル→作成で村作成 API を叩く", async ({ page }) => {
  await signupAndGotoNewVillage(page);

  await page.fill("#villageName", "e2e確認用テスト村");
  await page.getByRole("button", { name: "確認画面へ" }).click();

  // 確認モーダルが開き、設定一覧と作成ボタンが表示される
  const modal = page.getByRole("dialog", { name: "村作成確認" });
  await expect(modal).toBeVisible();
  await expect(modal.getByText("e2e確認用テスト村")).toBeVisible();
  await expect(modal.getByText("基本設定")).toBeVisible();
  await expect(modal.getByText("キャラチップ設定")).toBeVisible();

  // 作成を実行する。新規 signup ユーザーは終了村への参加が無く村建て不可のため、
  // backend の業務エラー (400) がモーダルに表示される (DB を汚さず作成 API 接続を検証できる)。
  await modal.getByRole("button", { name: "作成" }).click();
  await expect(
    page.getByText("村建てした村の決着がつくまでは村を建てられません。"),
  ).toBeVisible();

  // 「戻る」でモーダルを閉じてフォームに戻れる
  await modal.getByRole("button", { name: "戻る" }).click();
  await expect(modal).not.toBeVisible();
});
