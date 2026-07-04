import { expect, test, type Page } from "@playwright/test";
import {
  ensureVillagesExist,
  loginApi,
  CANDIDATE_USERS,
  type SimpleVillage,
} from "./helpers/provision";

/**
 * コミットの e2e。この実行用に provision された進行中の村を対象に、
 * テストユーザー (testuser01〜16 / password=testuser) を走査して
 * コミット可能な参加者を探す。ON → OFF と元に戻して終わる
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
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS"]);
  return scanCommitCandidate(page, villages);
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
