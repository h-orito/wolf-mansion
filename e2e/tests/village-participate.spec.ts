import { expect, test, type Page } from "@playwright/test";

/**
 * 村画面の入村フォーム e2e。新規ユーザーで確認画面 (サーバ検証 204) まで進み、
 * 実際の入村はしない (共有 DB を汚さない)。
 */

type SimpleVillage = { id: number; name: string };

async function findVillages(page: Page, statuses: string[]): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { villages: SimpleVillage[] };
  return body.villages;
}

function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-6);
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `e${stamp}${rand}`;
}

test("入村: キャラ選択 → 確認画面 (同意チェックで活性化) まで", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PREPARATION"]);
  test.skip(villages.length === 0, "募集中の村が無い DB のためスキップ");
  const village = villages[villages.length - 1];

  // 新規ユーザー (終了村参加なし = 村作成不可だが入村は可能)
  await page.goto("signup");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", uniqueUserId());
  await page.fill("#password", "test1234!");
  await page.getByRole("button", { name: "作成" }).click();
  await expect(page).toHaveURL(/\/wolf-mansion\/?$/);

  await page.goto(`village/${village.id}`);
  await expect(page.getByText("入村", { exact: true })).toBeVisible({ timeout: 15000 });

  // キャラ選択で名前・略称が自動補完される
  const charaSelect = page.getByLabel("キャラクター", { exact: true });
  await charaSelect.selectOption({ index: 1 });
  await expect(page.getByLabel("キャラクター名")).not.toHaveValue("");
  await expect(page.getByLabel("略称")).not.toHaveValue("");

  await page.getByLabel("入村発言").fill("e2e テストです。");
  await page.getByRole("button", { name: "入村確認へ" }).click();

  // サーバ検証 (assertParticipate) を通って確認画面へ
  await expect(page.getByText("入村確認", { exact: true })).toBeVisible();
  const submit = page.getByRole("button", { name: "入村する" });
  await expect(submit).toBeDisabled();

  // 2 つの同意チェックで活性化する (実際の入村はしない)
  await page.getByText(/ルールを確認し/).click();
  await page.getByText(/他者への礼節/).click();
  await expect(submit).toBeEnabled();
});

test("入村 → 役職希望変更 → 退村の自己完結フロー", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PREPARATION"]);
  test.skip(villages.length === 0, "募集中の村が無い DB のためスキップ");
  const village = villages[villages.length - 1];

  await page.goto("signup");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", uniqueUserId());
  await page.fill("#password", "test1234!");
  await page.getByRole("button", { name: "作成" }).click();
  await expect(page).toHaveURL(/\/wolf-mansion\/?$/);

  await page.goto(`village/${village.id}`);
  await expect(page.getByText("入村", { exact: true })).toBeVisible({ timeout: 15000 });

  await page.getByLabel("キャラクター", { exact: true }).selectOption({ index: 1 });
  await page.getByLabel("入村発言").fill("e2e の入村テストです。後で退村します。");
  await page.getByRole("button", { name: "入村確認へ" }).click();
  await page.getByText(/ルールを確認し/).click();
  await page.getByText(/他者への礼節/).click();
  await page.getByRole("button", { name: "入村する" }).click();

  // 入村後は退村パネルが出る (= 参加状態になった)
  await expect(page.getByRole("button", { name: "村を出る" })).toBeVisible({ timeout: 15000 });

  // 役職希望を変更できる (希望可の村のみ)
  const firstSkill = page.getByLabel("第一役職希望");
  if ((await firstSkill.count()) > 0) {
    await firstSkill.selectOption({ index: 1 });
    await page.getByRole("button", { name: "役職希望を変更する" }).click();
    await expect(page.getByText(/現在の役職希望: /)).toBeVisible();
  }

  // 退村して後片付け (confirm ダイアログを受諾)
  page.on("dialog", (dialog) => dialog.accept());
  await page.getByRole("button", { name: "村を出る" }).click();
  await expect(page.getByRole("button", { name: "村を出る" })).toHaveCount(0, {
    timeout: 15000,
  });
  await expect(page.getByText("入村", { exact: true })).toBeVisible();
});
