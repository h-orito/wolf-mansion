import { expect, test, type Page } from "@playwright/test";

/**
 * 役職能力セットの e2e。能力を使える参加者が必要なため、進行中の村と
 * ローカル開発 DB のテストユーザー (testuser01〜16 / password=testuser) を
 * 走査して能力者を動的に探し、見つからなければスキップする。
 * セットは現在値の再セットに留め、共有 DB の進行状態を変えない。
 */

type SimpleVillage = { id: number; name: string };

type AbilityView = {
  canUseAbility: boolean;
  attackerList: { charaId: number; name: string }[];
  attackerCharaId: number | null;
  targetCharaId: number | null;
  footstep: string | null;
  targetingMessage: string | null;
  werewolfNames: string;
};

type Candidate = { villageId: number; userId: string; ability: AbilityView };

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

/** 能力を使える (user, village) の組を探す。filter で絞り込み条件を追加できる。 */
async function findAbilityCandidate(
  page: Page,
  filter: (ability: AbilityView) => boolean,
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
      const body = (await res.json()) as { ability: AbilityView };
      if (body.ability.canUseAbility && filter(body.ability)) {
        return { villageId: village.id, userId, ability: body.ability };
      }
    }
  }
  return null;
}

test("能力者で村画面を開くと役職パネルが表示される", async ({ page }) => {
  const candidate = await findAbilityCandidate(page, () => true);
  test.skip(candidate == null, "能力を使える参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByText("役職", { exact: true }).first()).toBeVisible({ timeout: 15000 });

  // 現在のセット内容の説明文が出る (セット済みの場合)。「なし」等の短文は
  // select の option などにも一致しうるため first で見る
  if (candidate.ability.targetingMessage != null) {
    await expect(
      page.getByText(candidate.ability.targetingMessage, { exact: true }).first(),
    ).toBeVisible();
  }
  // 人狼系なら仲間の名前が見える
  if (candidate.ability.werewolfNames !== "") {
    await expect(page.getByText(`この村の人狼は、 ${candidate.ability.werewolfNames} です。`)).toBeVisible();
  }
});

test("人狼が現在の襲撃セットを再セットできる (共有 DB の状態を変えない)", async ({ page }) => {
  // 襲撃型で現在セット済み (再セットしても状態が変わらない) の参加者に限定する
  const candidate = await findAbilityCandidate(
    page,
    (ability) =>
      ability.attackerList.length > 0 &&
      ability.attackerCharaId != null &&
      ability.targetCharaId != null &&
      ability.footstep != null,
  );
  test.skip(candidate == null, "襲撃をセット済みの人狼が見つからない DB のためスキップ");
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByRole("button", { name: "能力セット" })).toBeVisible({ timeout: 15000 });

  // 初期表示で現在のセット値が選択済みになるのを待つ (対象候補は遅延取得)
  const targetSelect = page.getByLabel("能力の対象");
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

  // 再セット後も同じセット内容の説明文が表示される
  if (candidate.ability.targetingMessage != null) {
    await expect(page.getByText(candidate.ability.targetingMessage)).toBeVisible();
  }
});
