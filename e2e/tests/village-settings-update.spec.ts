import { expect, test, type Page } from "@playwright/test";

/**
 * 村設定変更 (`/village/{id}/settings`、村主のみ) の e2e。
 * master が村建てした募集中の村を動的に探し、村名を変更して保存 → 元に戻す。
 */

type SimpleVillage = { id: number; name: string };

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

/** master が村建てした募集中の村を探す。 */
async function findEditableVillage(page: Page): Promise<SimpleVillage | null> {
  if (!(await login(page, "master"))) return null;
  const villages = await findVillages(page, ["IN_PREPARATION"]);
  for (const village of villages) {
    const res = await page.request.get(
      `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
    );
    if (!res.ok()) continue;
    const body = (await res.json()) as { creator: { isAvailableModifySetting: boolean } };
    if (body.creator.isAvailableModifySetting) return village;
  }
  return null;
}

test("村設定変更: 村名を変更して保存し、元に戻す", async ({ page }) => {
  const village = await findEditableVillage(page);
  test.skip(village == null, "master が設定変更できる募集中の村が無い DB のためスキップ");
  if (village == null) return;

  await page.goto(`village/${village.id}/settings`);
  const nameInput = page.getByLabel("村名");
  await expect(nameInput).toBeVisible({ timeout: 15000 });
  await expect(nameInput).toHaveValue(village.name);

  // 変更不可の項目は読み取り表示 (役職希望にラジオが無い)
  await expect(page.getByRole("radiogroup", { name: "役職希望" })).toHaveCount(0);

  const newName = `${village.name}e2e`;
  await nameInput.fill(newName);
  await page.getByRole("button", { name: "変更する" }).click();

  // 保存後は村画面へ戻り、村名が反映されている
  await expect(page).toHaveURL(new RegExp(`/village/${village.id}$`), { timeout: 15000 });
  await expect(page.getByRole("heading", { name: new RegExp(newName) })).toBeVisible({
    timeout: 15000,
  });

  // 元に戻す
  await page.goto(`village/${village.id}/settings`);
  await expect(page.getByLabel("村名")).toHaveValue(newName, { timeout: 15000 });
  await page.getByLabel("村名").fill(village.name);
  await page.getByRole("button", { name: "変更する" }).click();
  await expect(page).toHaveURL(new RegExp(`/village/${village.id}$`), { timeout: 15000 });
  await expect(page.getByRole("heading", { name: new RegExp(village.name) })).toBeVisible({
    timeout: 15000,
  });
});

test("村建てでないユーザーには設定変更ページが開けない", async ({ page }) => {
  const ok = await login(page, "testuser01");
  test.skip(!ok, "testuser01 が存在しない DB のためスキップ");
  const villages = await findVillages(page, ["IN_PREPARATION"]);
  test.skip(villages.length === 0, "募集中の村が無い DB のためスキップ");

  await page.goto(`village/${villages[0].id}/settings`);
  await expect(page.getByText("村建てプレイヤーのみ実行できます")).toBeVisible({
    timeout: 15000,
  });
});
