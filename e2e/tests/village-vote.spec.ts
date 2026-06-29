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
  targetCharaIds: number[];
  targetCharaId: number | null;
};

type Participant = { chara: { id: number }; name: string };
type VillageDetail = {
  participants: { list: Participant[] };
  spectators: { list: Participant[] };
};

type Candidate = {
  villageId: number;
  userId: string;
  vote: VoteView;
  village: VillageDetail;
};

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
/** 初回役職確認モーダルが被っていたら閉じる。 */
async function dismissInitialSkillModal(page: Page) {
  const confirm = page.getByRole("button", { name: "確認したので次回以降表示しない" });
  if ((await confirm.count()) > 0) {
    await confirm.click();
  }
}

function resolveCharaName(village: VillageDetail, charaId: number): string {
  const all = [...(village.participants.list ?? []), ...(village.spectators.list ?? [])];
  return all.find((p) => p.chara.id === charaId)?.name ?? `(${charaId})`;
}

async function fetchVillage(page: Page, villageId: number): Promise<VillageDetail> {
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages/${villageId}`);
  expect(res.ok()).toBeTruthy();
  return (await res.json()) as VillageDetail;
}

async function findVoteCandidate(
  page: Page,
  filter: (vote: VoteView) => boolean,
): Promise<Candidate | null> {
  const villages = await findVillages(page, ["IN_PROGRESS"]);
  if (villages.length === 0) return null;
  for (const userId of CANDIDATE_USERS) {
    if (!(await login(page, userId))) continue;
    for (const v of villages) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${v.id}/situation/me`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as { vote: VoteView };
      if (body.vote.canVote && filter(body.vote)) {
        const village = await fetchVillage(page, v.id);
        return { villageId: v.id, userId, vote: body.vote, village };
      }
    }
  }
  return null;
}

test("投票可能な参加者に投票パネルが表示される", async ({ page }) => {
  const candidate = await findVoteCandidate(page, () => true);
  test.skip(candidate == null, "投票できる参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  const targetName =
    candidate.vote.targetCharaId != null
      ? resolveCharaName(candidate.village, candidate.vote.targetCharaId)
      : null;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByRole("button", { name: "投票セット" })).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);
  await expect(page.getByText(`現在の投票先: ${targetName ?? "なし"}`)).toBeVisible();
  if (candidate.vote.targetCharaId == null) {
    await expect(page.getByText("(未セットのままだと突然死します)")).toBeVisible();
  }
});

test("投票先を変更してセットできる (元に戻して共有 DB の状態を変えない)", async ({ page }) => {
  const candidate = await findVoteCandidate(
    page,
    (vote) => vote.targetCharaId != null && vote.targetCharaIds.length >= 2,
  );
  test.skip(candidate == null, "投票セット済みの参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  const original = candidate.vote.targetCharaId;
  const anotherCharaId = candidate.vote.targetCharaIds.find((id) => id !== original);
  expect(anotherCharaId).toBeTruthy();
  if (anotherCharaId == null) return;

  const anotherName = resolveCharaName(candidate.village, anotherCharaId);
  const originalName = resolveCharaName(candidate.village, original!);

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByRole("button", { name: "投票セット" })).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);

  await page.getByLabel("投票先").selectOption(String(anotherCharaId));
  await page.getByRole("button", { name: "投票セット" }).click();
  await expect(page.getByText(`現在の投票先: ${anotherName}`)).toBeVisible({ timeout: 15000 });

  // 元の投票先に戻す
  await page.getByLabel("投票先").selectOption(String(original));
  await page.getByRole("button", { name: "投票セット" }).click();
  await expect(page.getByText(`現在の投票先: ${originalName}`)).toBeVisible({
    timeout: 15000,
  });
});
