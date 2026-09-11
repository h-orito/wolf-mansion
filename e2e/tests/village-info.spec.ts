import { expect, test, type Page } from "@playwright/test";
import {
  ensureVillagesExist,
  loginApi,
  dismissAgeLimitModal,
  CANDIDATE_USERS,
} from "./helpers/provision";

/**
 * 村情報モーダルと初回役職確認モーダルの e2e。いずれも読み取り表示のため DB を変更しない。
 */

/** 役職が割り当たっている参加者と村の組を探す。 */
async function findSkillParticipant(
  page: Page,
): Promise<{ villageId: number; userId: string; skillName: string } | null> {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS"]);
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
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

test("村情報モーダルに村設定が表示される (匿名は設定変更導線なし)", async ({ page }) => {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS", "IN_PREPARATION"]);
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
  expect(candidate, "役職持ちの参加者が見つからない").not.toBeNull();
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
