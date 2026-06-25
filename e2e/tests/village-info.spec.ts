import { expect, test, type Page } from "@playwright/test";

/**
 * 村情報モーダルと初回役職確認モーダルの e2e。いずれも読み取り表示のため DB を変更しない。
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

/** 役職が割り当たっている参加者と村の組を探す。 */
async function findSkillParticipant(
  page: Page,
): Promise<{ villageId: number; userId: string; skillName: string } | null> {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  if (villages.length === 0) return null;
  for (const userId of CANDIDATE_USERS) {
    if (!(await login(page, userId))) continue;
    for (const village of villages) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as { myself: { skill: { name: string } | null } | null };
      if (body.myself?.skill != null) {
        return { villageId: village.id, userId, skillName: body.myself.skill.name };
      }
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

test("村情報モーダルに村設定が表示される (匿名は設定変更導線なし)", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS", "IN_PREPARATION"]);
  test.skip(villages.length === 0, "村が無い DB のためスキップ");
  const village = villages[0];

  await page.goto(`village/${village.id}`);
  await expect(page.getByRole("button", { name: "情報" }).first()).toBeVisible({
    timeout: 15000,
  });
  await dismissAgeLimitModal(page);
  await page.getByRole("button", { name: "情報" }).first().click();

  const dialog = page.getByRole("dialog", { name: "村情報" });
  await expect(dialog.getByText("最少開始人数")).toBeVisible({ timeout: 15000 });
  await expect(dialog.getByText("館を建てたプレイヤー", { exact: true })).toBeVisible();
  await expect(dialog.getByText("更新間隔")).toBeVisible();
  // 匿名には村主限定の設定変更導線が出ない
  await expect(dialog.getByText("設定変更")).toHaveCount(0);

  await dialog.getByRole("button", { name: "閉じる", exact: true }).last().click();
  await expect(dialog).toHaveCount(0);
});

test("役職持ち参加者には初回のみ役職確認モーダルが出る", async ({ page }) => {
  const candidate = await findSkillParticipant(page);
  test.skip(candidate == null, "役職持ちの参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByText("村が開始されました。役職とルールは以下の通りです。")).toBeVisible({
    timeout: 15000,
  });
  await expect(page.getByText(candidate.skillName, { exact: true }).first()).toBeVisible();

  await page.getByRole("button", { name: "確認したので次回以降表示しない" }).click();
  await expect(page.getByText("村が開始されました。役職とルールは以下の通りです。")).toHaveCount(0);

  // 再読み込みしても出ない (ブラウザに記憶済み)
  await page.reload();
  await expect(page.getByRole("button", { name: "更新" })).toBeVisible({ timeout: 15000 });
  await expect(page.getByText("村が開始されました。役職とルールは以下の通りです。")).toHaveCount(0);
});
