import { expect, test, type Page } from "@playwright/test";
import {
  findVillages,
  loginApi,
  provisionInProgressVillage,
  CANDIDATE_USERS,
  type SimpleVillage,
} from "./helpers/provision";

/**
 * コミットの e2e。コミット可の村の参加者が必要なため、進行中の村と
 * ローカル開発 DB のテストユーザー (testuser01〜16 / password=testuser) を
 * 走査して動的に探す。見つからなければ村を作成して再走査する。
 * ON → OFF と元に戻して終わるため共有 DB の進行状態を変えない
 * (未コミットの参加者を選ぶので、自分のコミットで全員コミットが成立して
 * 日付更新が走ることもない)。
 */

type CommitView = { isAvailableCommit: boolean; isCommitting: boolean };

type Candidate = { villageId: number; userId: string; commit: CommitView };

async function scanCommitCandidate(
  page: Page,
  villages: SimpleVillage[],
): Promise<Candidate | null> {
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    for (const village of villages) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as { commit: CommitView };
      if (body.commit.isAvailableCommit && !body.commit.isCommitting) {
        return { villageId: village.id, userId, commit: body.commit };
      }
    }
  }
  return null;
}

async function findCommitCandidate(page: Page): Promise<Candidate | null> {
  const existing = await findVillages(page, ["IN_PROGRESS"]);
  const result = await scanCommitCandidate(page, existing);
  if (result) return result;
  const provisioned = await provisionInProgressVillage(page);
  return scanCommitCandidate(page, [provisioned]);
}

test("コミット可の村でコミット ON → OFF を切り替えられる", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findCommitCandidate(page);
  expect(candidate, "コミットできる参加者が見つからない").not.toBeNull();
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  const commitButton = page.getByRole("button", { name: "コミットする" });
  await expect(commitButton).toBeVisible({ timeout: 15000 });

  const ageLimitConfirm = page.getByRole("button", { name: "表示する" });
  if ((await ageLimitConfirm.count()) > 0) {
    await ageLimitConfirm.click();
  }
  const skillConfirm = page.getByRole("button", { name: "確認したので次回以降表示しない" });
  if ((await skillConfirm.count()) > 0) {
    await skillConfirm.click();
  }

  await commitButton.click();
  const cancelButton = page.getByRole("button", { name: "コミットを取り消す" });
  await expect(cancelButton).toBeVisible({ timeout: 15000 });

  await cancelButton.click();
  await expect(page.getByRole("button", { name: "コミットする" })).toBeVisible({
    timeout: 15000,
  });
});
