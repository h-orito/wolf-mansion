import { expect, test, type Page } from "@playwright/test";
import {
  ensureVillagesExist,
  loginApi,
  dismissInitialSkillModal,
  provisionInProgressVillage,
  CANDIDATE_USERS,
  DEFAULT_ORGANIZATION,
  type SimpleVillage,
} from "./helpers/provision";

/**
 * 役職能力セットの e2e。この実行用に provision された進行中の村を対象に、
 * テストユーザー (testuser01〜16 / password=testuser) を走査して能力者を探す。
 * セットは現在値の再セットに留め、村の進行状態を変えない。
 */

type AbilityView = {
  canUseAbility: boolean;
  isTargetingAndFootstep: boolean;
  isAvailableNoTarget: boolean;
  targetCharaIds: number[];
  attackerCharaIds: number[];
  attackerCharaId: number | null;
  targetCharaId: number | null;
  footstep: string | null;
  targetingMessage: string | null;
  wolfCharaIds: number[];
};

type Candidate = { villageId: number; userId: string; ability: AbilityView };

async function scanAbilityCandidate(
  page: Page,
  villages: SimpleVillage[],
  filter: (ability: AbilityView) => boolean,
): Promise<Candidate | null> {
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    for (const village of villages) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as { ability: AbilityView };
      if (body.ability.canUseAbility && filter(body.ability)) {
        return { villageId: village.id, userId, ability: body.ability };
      }
    }
  }
  return null;
}

async function findAbilityCandidate(
  page: Page,
  filter: (ability: AbilityView) => boolean,
): Promise<Candidate | null> {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS"]);
  return scanAbilityCandidate(page, villages, filter);
}

test("能力者で村画面を開くと役職パネルが表示される", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findAbilityCandidate(page, () => true);
  expect(candidate, "能力を使える参加者が見つからない").not.toBeNull();
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByText("役職", { exact: true }).first()).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);

  if (candidate.ability.targetingMessage != null) {
    await expect(
      page.getByText(candidate.ability.targetingMessage, { exact: true }).first(),
    ).toBeVisible();
  }
});

test("人狼が現在の襲撃セットを再セットできる (共有 DB の状態を変えない)", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findAbilityCandidate(
    page,
    (ability) =>
      ability.attackerCharaIds?.length > 0 &&
      ability.attackerCharaId != null &&
      ability.targetCharaId != null &&
      ability.footstep != null,
  );
  expect(candidate, "襲撃をセット済みの人狼が見つからない").not.toBeNull();
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByRole("button", { name: "能力セット" })).toBeVisible({ timeout: 15000 });
  await dismissInitialSkillModal(page);

  const targetSelect = page.getByRole("combobox", { name: "能力の対象" });
  await expect(targetSelect).toHaveValue(String(candidate.ability.targetCharaId), {
    timeout: 15000,
  });
  await expect(page.getByLabel("通過する部屋")).toHaveValue(candidate.ability.footstep ?? "");

  const [response] = await Promise.all([
    page.waitForResponse(
      (res) =>
        res.url().includes(`/api/v1/villages/${candidate.villageId}/ability`) &&
        res.request().method() === "POST",
    ),
    page.getByRole("button", { name: "能力セット" }).click(),
  ]);
  expect(response.status()).toBe(204);

  if (candidate.ability.targetingMessage != null) {
    await expect(page.getByText(candidate.ability.targetingMessage)).toBeVisible();
  }
});

/** 対象なしを選べる足音能力 (拡声者の拡声) を確実に含む村を建て、その能力者を探す */
async function findLoudSpeakerCandidate(page: Page): Promise<Candidate | null> {
  const village = await provisionInProgressVillage(page, {
    organization: DEFAULT_ORGANIZATION.split("\n")
      .map((line) => line.replace("導", "拡"))
      .join("\n"),
  });
  return scanAbilityCandidate(
    page,
    [village],
    (ability) =>
      (ability.attackerCharaIds?.length ?? 0) === 0 &&
      ability.isTargetingAndFootstep &&
      ability.isAvailableNoTarget,
  );
}

test("襲撃以外の能力では襲撃者を指定できない", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findLoudSpeakerCandidate(page);
  expect(candidate, "対象なしを選べる足音能力者が見つからない").not.toBeNull();
  if (candidate == null) return;

  const villageRes = await page.request.get(
    `/wolf-mansion-api/api/v1/villages/${candidate.villageId}`,
  );
  const me = await page.request.get(
    `/wolf-mansion-api/api/v1/villages/${candidate.villageId}/situation/me`,
  );
  const myCharaId = ((await me.json()) as { myself: { chara: { id: number } } }).myself.chara.id;
  const village = (await villageRes.json()) as {
    participants: { list: { chara: { id: number } }[] };
  };
  const other = village.participants.list.find((p) => p.chara.id !== myCharaId)!.chara.id;

  // 足音を他人のものとして記録させようとする
  const res = await page.request.post(
    `/wolf-mansion-api/api/v1/villages/${candidate.villageId}/ability`,
    { data: { attackerCharaId: other, targetCharaId: null, footstep: "なし" } },
  );
  expect(res.status()).toBe(400);
  expect((await res.json()).detail).toBe("襲撃者を指定できない能力です");
});

test("対象なしの足音能力に通過する部屋を指定するとエラーになる", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findLoudSpeakerCandidate(page);
  expect(candidate, "対象なしを選べる足音能力者が見つからない").not.toBeNull();
  if (candidate == null) return;

  const res = await page.request.post(
    `/wolf-mansion-api/api/v1/villages/${candidate.villageId}/ability`,
    { data: { targetCharaId: null, footstep: "1,2,3,4,5,6,7,8,9" } },
  );
  expect(res.status()).toBe(400);
  expect((await res.json()).detail).toBe("選択できない足音を選択しています");
});

test("画面で対象をなしに切り替えてセットすると、足音なしでセットされる", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findLoudSpeakerCandidate(page);
  expect(candidate, "対象なしを選べる足音能力者が見つからない").not.toBeNull();
  if (candidate == null) return;

  const url = `/wolf-mansion-api/api/v1/villages/${candidate.villageId}/ability`;
  try {
    await page.goto(`village/${candidate.villageId}`);
    await expect(page.getByRole("button", { name: "能力セット" })).toBeVisible({ timeout: 15000 });
    await dismissInitialSkillModal(page);

    // 対象を選んで通過する部屋が入った状態から、なしに切り替える
    const targetSelect = page.getByRole("combobox", { name: "能力の対象" });
    await targetSelect.selectOption(String(candidate.ability.targetCharaIds[0]));
    await expect(page.getByLabel("通過する部屋")).not.toHaveValue("", { timeout: 15000 });
    await targetSelect.selectOption("");

    const [response] = await Promise.all([
      page.waitForResponse(
        (res) =>
          res.url().includes(`/api/v1/villages/${candidate.villageId}/ability`) &&
          res.request().method() === "POST",
      ),
      page.getByRole("button", { name: "能力セット" }).click(),
    ]);
    expect(response.status()).toBe(204);
    expect(response.request().postDataJSON()).toMatchObject({
      targetCharaId: null,
      footstep: null,
    });

    const me = await page.request.get(
      `/wolf-mansion-api/api/v1/villages/${candidate.villageId}/situation/me`,
    );
    const ability = ((await me.json()) as { ability: AbilityView }).ability;
    expect(ability.targetCharaId).toBeNull();
    expect(ability.footstep).toBe("なし");
  } finally {
    // 元のセットに戻す
    await page.request.post(url, {
      data: {
        targetCharaId: candidate.ability.targetCharaId,
        footstep: candidate.ability.targetCharaId == null ? null : candidate.ability.footstep,
      },
    });
  }
});
