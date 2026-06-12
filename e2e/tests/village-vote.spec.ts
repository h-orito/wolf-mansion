import { expect, test, type Page } from "@playwright/test";

/**
 * 投票セットの e2e。投票できる参加者が必要なため、進行中の村と
 * ローカル開発 DB のテストユーザー (testuser01〜16 / password=testuser) を
 * 走査して投票可能者を動的に探し、見つからなければスキップする。
 * 元の投票先に戻して終わるため共有 DB の進行状態を変えない。
 */

type SimpleVillage = { id: number; name: string };

type VoteView = {
  canVote: boolean;
  targetList: { charaId: number; name: string }[];
  targetCharaId: number | null;
  targetName: string | null;
};

type Candidate = { villageId: number; userId: string; vote: VoteView };

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

async function findVoteCandidate(
  page: Page,
  filter: (vote: VoteView) => boolean,
): Promise<Candidate | null> {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  if (villages.length === 0) return null;
  for (const userId of CANDIDATE_USERS) {
    if (!(await login(page, userId))) continue;
    for (const village of villages) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as { vote: VoteView };
      if (body.vote.canVote && filter(body.vote)) {
        return { villageId: village.id, userId, vote: body.vote };
      }
    }
  }
  return null;
}

test("投票可能な参加者に投票パネルが表示される", async ({ page }) => {
  const candidate = await findVoteCandidate(page, () => true);
  test.skip(candidate == null, "投票できる参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByRole("button", { name: "投票セット" })).toBeVisible({ timeout: 15000 });
  await expect(
    page.getByText(`現在の投票先: ${candidate.vote.targetName ?? "なし"}`),
  ).toBeVisible();
  // 未セットの場合は突然死の警告が出る
  if (candidate.vote.targetCharaId == null) {
    await expect(page.getByText("(未セットのままだと突然死します)")).toBeVisible();
  }
});

test("投票先を変更してセットできる (元に戻して共有 DB の状態を変えない)", async ({ page }) => {
  // 復元のためセット済みかつ候補 2 名以上の参加者に限定する
  const candidate = await findVoteCandidate(
    page,
    (vote) => vote.targetCharaId != null && vote.targetList.length >= 2,
  );
  test.skip(candidate == null, "投票セット済みの参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  const original = candidate.vote.targetCharaId;
  const another = candidate.vote.targetList.find((t) => t.charaId !== original);
  expect(another).toBeTruthy();
  if (another == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByRole("button", { name: "投票セット" })).toBeVisible({ timeout: 15000 });

  await page.getByLabel("投票先").selectOption(String(another.charaId));
  await page.getByRole("button", { name: "投票セット" }).click();
  await expect(page.getByText(`現在の投票先: ${another.name}`)).toBeVisible({ timeout: 15000 });

  // 元の投票先に戻す
  await page.getByLabel("投票先").selectOption(String(original));
  await page.getByRole("button", { name: "投票セット" }).click();
  await expect(page.getByText(`現在の投票先: ${candidate.vote.targetName}`)).toBeVisible({
    timeout: 15000,
  });
});
