import { expect, test, type Page } from "@playwright/test";

/**
 * 設定モーダル (表示設定 / Discord 通知設定) の e2e。
 * 表示設定はブラウザ保存 (localStorage) のため DB を変更しない。
 * 通知設定はサーバ保存のため保存はせず、参加者にフォームが出ることまでを確認する。
 */

type SimpleVillage = { id: number; name: string };

const CANDIDATE_USERS = Array.from(
  { length: 16 },
  (_, i) => `testuser${String(i + 1).padStart(2, "0")}`,
);

async function findVillages(page: Page, statuses: string[]): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { villages: SimpleVillage[] };
  return body.villages;
}

async function login(page: Page, userId: string): Promise<boolean> {
  const res = await page.request.post(`/wolf-mansion-api/api/v1/auth/login`, {
    data: { userId, password: "testuser" },
  });
  return res.ok();
}

/** 村に参加している (myself が返る) ユーザーと村の組を探す。 */
async function findParticipant(
  page: Page,
): Promise<{ villageId: number; userId: string } | null> {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  if (villages.length === 0) return null;
  for (const userId of CANDIDATE_USERS) {
    if (!(await login(page, userId))) continue;
    for (const village of villages) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as { myself: unknown };
      if (body.myself != null) return { villageId: village.id, userId };
    }
  }
  return null;
}

async function dismissAgeLimitModal(page: Page) {
  const ageLimitConfirm = page.getByRole("button", { name: "表示する", exact: true });
  if ((await ageLimitConfirm.count()) > 0) {
    await ageLimitConfirm.click();
  }
}

test("設定モーダルで表示設定を変更でき、ブラウザに保存される", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS", "IN_PREPARATION"]);
  test.skip(villages.length === 0, "村が無い DB のためスキップ");
  const village = villages[0];

  await page.goto(`village/${village.id}`);
  await expect(page.getByRole("button", { name: "設定" })).toBeVisible({ timeout: 15000 });
  await dismissAgeLimitModal(page);
  await page.getByRole("button", { name: "設定" }).click();

  const dialog = page.getByRole("dialog", { name: "設定" });
  await expect(dialog.getByRole("heading", { name: "表示設定", exact: true })).toBeVisible();

  // ページサイズ変更が保存される
  await dialog.getByLabel("ページあたりの表示発言数").selectOption("10");
  const stored = await page.evaluate(() =>
    localStorage.getItem("wolf-mansion-display-settings"),
  );
  expect(stored).toContain('"pageSize":10');

  // リセットでデフォルト (50) に戻る
  await dialog.getByRole("button", { name: "リセット" }).click();
  const reset = await page.evaluate(() => localStorage.getItem("wolf-mansion-display-settings"));
  expect(reset).toContain('"pageSize":50');

  // 未ログイン or 未参加では Discord 通知設定が出ない (このテストは未ログイン)
  await expect(dialog.getByText("Discord通知設定")).toHaveCount(0);

  await dialog.getByRole("button", { name: "閉じる", exact: true }).last().click();
  await expect(dialog).toHaveCount(0);
});

test("参加者の設定モーダルには Discord 通知設定が表示される", async ({ page }) => {
  const candidate = await findParticipant(page);
  test.skip(candidate == null, "進行中の村の参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByRole("button", { name: "設定" })).toBeVisible({ timeout: 15000 });
  await dismissAgeLimitModal(page);
  await page.getByRole("button", { name: "設定" }).click();

  const dialog = page.getByRole("dialog", { name: "設定" });
  await expect(dialog.getByText("Discord通知設定")).toBeVisible();
  // webhookUrl が空のうちは保存できない (誤送信防止)
  await expect(dialog.getByRole("button", { name: "保存" })).toBeDisabled();
  await dialog.getByLabel("WebhookURL").fill("https://discord.com/api/webhooks/x/y");
  await expect(dialog.getByRole("button", { name: "保存" })).toBeEnabled();
});
