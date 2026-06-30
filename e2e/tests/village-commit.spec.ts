import { expect, test, type Page } from "@playwright/test";

/**
 * コミットの e2e。コミット可の村の参加者が必要なため、進行中の村と
 * ローカル開発 DB のテストユーザー (testuser01〜16 / password=testuser) を
 * 走査して動的に探し、見つからなければスキップする。
 * ON → OFF と元に戻して終わるため共有 DB の進行状態を変えない
 * (未コミットの参加者を選ぶので、自分のコミットで全員コミットが成立して
 * 日付更新が走ることもない)。
 */

type SimpleVillage = { id: number; name: string };

type CommitView = { isAvailableCommit: boolean; isCommitting: boolean };

type Candidate = { villageId: number; userId: string; commit: CommitView };

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

async function findCommitCandidate(page: Page): Promise<Candidate | null> {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  if (villages.length === 0) return null;
  for (const userId of CANDIDATE_USERS) {
    if (!(await login(page, userId))) continue;
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

test("コミット可の村でコミット ON → OFF を切り替えられる", async ({ page }) => {
  test.setTimeout(60000);
  const candidate = await findCommitCandidate(page);
  test.skip(candidate == null, "コミットできる参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  const commitButton = page.getByRole("button", { name: "コミットする" });
  await expect(commitButton).toBeVisible({ timeout: 15000 });

  // 年齢制限村は確認モーダルが被るため閉じる
  const ageLimitConfirm = page.getByRole("button", { name: "表示する" });
  if ((await ageLimitConfirm.count()) > 0) {
    await ageLimitConfirm.click();
  }
  // 初回役職確認モーダルも被るため閉じる
  const skillConfirm = page.getByRole("button", { name: "確認したので次回以降表示しない" });
  if ((await skillConfirm.count()) > 0) {
    await skillConfirm.click();
  }

  await commitButton.click();
  const cancelButton = page.getByRole("button", { name: "コミットを取り消す" });
  await expect(cancelButton).toBeVisible({ timeout: 15000 });

  // 元に戻して終わる
  await cancelButton.click();
  await expect(page.getByRole("button", { name: "コミットする" })).toBeVisible({
    timeout: 15000,
  });
});
