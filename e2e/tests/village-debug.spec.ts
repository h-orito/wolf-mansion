import { expect, test, type Page } from "@playwright/test";

/**
 * デバッグメニュー (ローカル開発向け、app.debug 有効時のみ) の e2e。
 * 一括入村・日付進行はフィクスチャ村の進行状態を変えるため実行せず、
 * メニュー表示とダミーログイン (なりすまし視点切替) のみ確認する。
 */

type SimpleVillage = { id: number; name: string };

async function findVillages(page: Page, statuses: string[]): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { villages: SimpleVillage[] };
  return body.villages;
}

async function dismissInitialSkillModal(page: Page) {
  const confirm = page.getByRole("button", { name: "確認したので次回以降表示しない" });
  if ((await confirm.count()) > 0) {
    await confirm.click();
  }
}

test("デバッグメニュー: ダミーログインで任意プレイヤー視点に切り替えられる", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  test.skip(villages.length === 0, "進行中の村が無い DB のためスキップ");
  const village = villages[0];

  // デバッグモードでなければスキップ (本番相当設定の DB/環境)
  const debugRes = await page.request.get(`/wolf-mansion-api/api/v1/villages/${village.id}/debug`);
  expect(debugRes.ok()).toBeTruthy();
  const debug = (await debugRes.json()) as {
    isDebugMode: boolean;
    players: { userId: string; label: string }[];
  };
  test.skip(!debug.isDebugMode, "デバッグモードが無効のためスキップ");
  test.skip(debug.players.length === 0, "参加者がいないためスキップ");

  await page.goto(`village/${village.id}`);
  await expect(page.getByText("デバッグメニュー")).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);

  // ダミーログイン → リロード後にそのユーザーでログインしている
  const target = debug.players[0];
  await page.getByLabel("ダミーログインプレイヤー").selectOption(target.userId);
  await page.getByRole("button", { name: "ログイン", exact: true }).click();
  // ヘッダーとデバッグ表示の 2 箇所に出るため first で見る
  await expect(page.getByText(`ユーザID: ${target.userId}`).first()).toBeVisible({
    timeout: 15000,
  });

  // なりすまし先の初回役職確認モーダルがリロード後に遅れて出るため、出現を待って閉じる
  // (役職未割当なら出ないので、その場合は待ちを諦めて先へ進む)
  await page
    .getByRole("button", { name: "確認したので次回以降表示しない" })
    .click({ timeout: 10000 })
    .catch(() => {});

  // ログアウトで匿名に戻る (後続テストの独立性は context 分離で担保されるが、状態を戻しておく)
  await page.getByRole("button", { name: "ログアウト", exact: true }).click();
  await expect(page.getByText(`ユーザID: ${target.userId}`)).toHaveCount(0, { timeout: 15000 });
});
