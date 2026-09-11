import { expect, test, type Page } from "@playwright/test";
import {
  ensureVillagesExist,
  loginApi,
  dismissInitialSkillModal,
  CANDIDATE_USERS,
  type SimpleVillage,
} from "./helpers/provision";

/**
 * 役職能力セットの e2e。この実行用に provision された進行中の村を対象に、
 * テストユーザー (testuser01〜16 / password=testuser) を走査して能力者を探す。
 * セットは現在値の再セットに留め、村の進行状態を変えない。
 */

type AbilityView = {
  canUseAbility: boolean;
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
