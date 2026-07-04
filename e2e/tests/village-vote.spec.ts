import { expect, test, type Page } from "@playwright/test";
import {
  ensureVotingVillage,
  loginApi,
  fetchVillage,
  dismissInitialSkillModal,
  CANDIDATE_USERS,
  type SimpleVillage,
} from "./helpers/provision";

/**
 * 投票セットの e2e。投票は2日目からのため、この実行用に provision された
 * 投票可能な (2日目の) 村を対象に、テストユーザー (testuser01〜16 /
 * password=testuser) を走査して投票可能者を探す。元の投票先に戻して終わる。
 */

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

const API = "/wolf-mansion-api/api/v1";

function resolveCharaName(village: VillageDetail, charaId: number): string {
  const all = [...(village.participants.list ?? []), ...(village.spectators.list ?? [])];
  return all.find((p) => p.chara.id === charaId)?.name ?? `(${charaId})`;
}

async function scanVoteCandidate(
  page: Page,
  villages: SimpleVillage[],
  filter: (vote: VoteView) => boolean,
): Promise<Candidate | null> {
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    for (const v of villages) {
      const res = await page.request.get(`${API}/villages/${v.id}/situation/me`);
      if (!res.ok()) continue;
      const body = (await res.json()) as { vote: VoteView };
      if (body.vote.canVote && filter(body.vote)) {
        const village = (await fetchVillage(page, v.id)) as VillageDetail;
        return { villageId: v.id, userId, vote: body.vote, village };
      }
    }
  }
  return null;
}

async function findVoteCandidate(
  page: Page,
  filter: (vote: VoteView) => boolean,
): Promise<Candidate | null> {
  const village = await ensureVotingVillage(page);
  return scanVoteCandidate(page, [village], filter);
}

/** 投票可能な参加者を探し、投票をセットして返す。 */
async function findVotedCandidate(page: Page): Promise<Candidate | null> {
  const simpleFilter = (vote: VoteView) =>
    vote.targetCharaId != null && vote.targetCharaIds.length >= 2;

  const village = await ensureVotingVillage(page);
  const result = await scanVoteCandidate(page, [village], simpleFilter);
  if (result) return result;

  // まだ誰も投票していなければ、投票可能な参加者に API で投票をセットする
  const unvoted = await scanVoteCandidate(page, [village], (vote) =>
    vote.canVote && vote.targetCharaIds.length >= 2,
  );
  if (!unvoted) return null;

  const targetCharaId = unvoted.vote.targetCharaIds[0];
  const voteRes = await page.request.post(`${API}/villages/${unvoted.villageId}/vote`, {
    data: { targetCharaId },
  });
  if (!voteRes.ok()) return null;

  const meRes = await page.request.get(`${API}/villages/${unvoted.villageId}/situation/me`);
  if (!meRes.ok()) return null;
  const updated = (await meRes.json()) as { vote: VoteView };
  return {
    villageId: unvoted.villageId,
    userId: unvoted.userId,
    vote: updated.vote,
    village: unvoted.village,
  };
}

test("投票可能な参加者に投票パネルが表示される", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findVoteCandidate(page, () => true);
  expect(candidate, "投票できる参加者が見つからない").not.toBeNull();
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
  test.setTimeout(180000);
  const candidate = await findVotedCandidate(page);
  expect(candidate, "投票セット済みの参加者が見つからない").not.toBeNull();
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

  await page.getByLabel("投票先").selectOption(String(original));
  await page.getByRole("button", { name: "投票セット" }).click();
  await expect(page.getByText(`現在の投票先: ${originalName}`)).toBeVisible({
    timeout: 15000,
  });
});
