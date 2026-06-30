import { expect, test, type Page } from "@playwright/test";

/**
 * 管理者 (admin) メニューの e2e。master (ローカル DB の管理者 = playerId 1) でログインする。
 * 強制退村・全員自己投票は破壊的 (フィクスチャの参加者・投票状態を変える) ため実行せず、
 * 読み取り (参加プレイヤー一覧) と無害な全員アクセスのみ実行する。
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

/** master が参加している村を探す (admin パネルは参加者として表示されるため)。 */
async function findAdminVillage(page: Page): Promise<number | null> {
  if (!(await login(page, "master"))) return null;
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  for (const village of villages) {
    const res = await page.request.get(
      `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
    );
    if (!res.ok()) continue;
    const body = (await res.json()) as { admin: { isAdmin: boolean } };
    if (body.admin.isAdmin) return village.id;
  }
  return null;
}

async function dismissInitialSkillModal(page: Page) {
  const confirm = page.getByRole("button", { name: "確認したので次回以降表示しない" });
  if ((await confirm.count()) > 0) {
    await confirm.click();
  }
}

test("管理者メニュー: 参加プレイヤーのインライン表示と全員アクセス", async ({ page }) => {
  const villageId = await findAdminVillage(page);
  test.skip(villageId == null, "master が管理者として参加する進行中の村が無い DB のためスキップ");
  if (villageId == null) return;

  await page.goto(`village/${villageId}`);
  await expect(page.getByText("管理者メニュー")).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);

  // 参加プレイヤーがパネル内にインライン表示される (別画面に遷移しない)
  await page.getByRole("button", { name: "参加プレイヤーを表示" }).click();
  await expect(page.getByText("master").first()).toBeVisible({ timeout: 15000 });
  await expect(page).toHaveURL(new RegExp(`/village/${villageId}`));

  // 全員アクセス (lastAccess 更新のみで無害)
  const accessSection = page.getByText("全員アクセス").locator("..").locator("..");
  await accessSection.getByRole("button", { name: "更新" }).click();
  await expect(page.getByText(/に失敗しました/)).toHaveCount(0);
});

test("管理者でない参加者には管理者メニューが出ない", async ({ page }) => {
  const ok = await login(page, "testuser01");
  test.skip(!ok, "testuser01 が存在しない DB のためスキップ");
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  test.skip(villages.length === 0, "進行中の村が無い DB のためスキップ");

  await page.goto(`village/${villages[0].id}`);
  await expect(page.getByRole("button", { name: "更新" })).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);
  await expect(page.getByText("管理者メニュー")).toHaveCount(0);
});
