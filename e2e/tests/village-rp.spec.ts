import { expect, test, type Page } from "@playwright/test";
import {
  findVillages,
  loginApi,
  dismissInitialSkillModal,
  dismissAgeLimitModal,
  provisionInProgressVillage,
  CANDIDATE_USERS,
  type SimpleVillage,
} from "./helpers/provision";

/**
 * RP 支援 (名前変更・簡易メモ) の e2e。進行中の村の参加者を
 * ローカル開発 DB のテストユーザー (testuser01〜16 / password=testuser) から
 * 動的に探す。見つからなければ村を作成して再走査する。
 * 変更後は元の値に戻して終わるため共有 DB の進行状態を変えない。
 */

type RpFlags = {
  isAvailableChangeName: boolean;
  isAvailableMemo: boolean;
};

type Myself = {
  charaName: { name: string; shortName: string };
  memo: string | null;
};

type Candidate = {
  villageId: number;
  userId: string;
  rpFlags: RpFlags;
  myself: Myself;
};

async function scanRpCandidate(
  page: Page,
  villages: SimpleVillage[],
  filter: (rpFlags: RpFlags, myself: Myself) => boolean,
): Promise<Candidate | null> {
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    for (const village of villages) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as { rp: RpFlags; myself: Myself | null };
      if (body.myself != null && filter(body.rp, body.myself)) {
        return { villageId: village.id, userId, rpFlags: body.rp, myself: body.myself };
      }
    }
  }
  return null;
}

async function findRpCandidate(
  page: Page,
  filter: (rpFlags: RpFlags, myself: Myself) => boolean,
): Promise<Candidate | null> {
  const existing = await findVillages(page, ["IN_PROGRESS"]);
  const result = await scanRpCandidate(page, existing, filter);
  if (result) return result;
  const provisioned = await provisionInProgressVillage(page);
  return scanRpCandidate(page, [provisioned], filter);
}

test("簡易メモを変更すると参加者一覧に表示される (元に戻す)", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findRpCandidate(page, (rp) => rp.isAvailableMemo);
  expect(candidate, "簡易メモを変更できる参加者が見つからない").not.toBeNull();
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  const memoInput = page.getByLabel("簡易メモ");
  await expect(memoInput).toBeVisible({ timeout: 15000 });
  await dismissAgeLimitModal(page);
  await dismissInitialSkillModal(page);

  const testMemo = "e2eメモ";
  await memoInput.fill(testMemo);
  await page.getByRole("button", { name: "簡易メモを変更する" }).click();

  await page.getByRole("button", { name: "参加者" }).click();
  await expect(page.getByText(`[${testMemo}]`)).toBeVisible({ timeout: 15000 });

  await memoInput.fill(candidate.myself.memo ?? "");
  await page.getByRole("button", { name: "簡易メモを変更する" }).click();
  await expect(page.getByText(`[${testMemo}]`)).toHaveCount(0, { timeout: 15000 });
});

test("キャラ名を変更できる (元に戻す)", async ({ page }) => {
  test.setTimeout(180000);
  const candidate = await findRpCandidate(
    page,
    (rp, myself) => rp.isAvailableChangeName && myself.charaName?.name != null && myself.charaName?.shortName != null,
  );
  expect(candidate, "名前を変更できる参加者が見つからない").not.toBeNull();
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  const nameInput = page.getByLabel("名前", { exact: true });
  await expect(nameInput).toBeVisible({ timeout: 15000 });
  await dismissAgeLimitModal(page);
  await dismissInitialSkillModal(page);

  const originalName = await nameInput.inputValue();
  expect(originalName).toBeTruthy();

  const testName = `${originalName}改`;
  await nameInput.fill(testName);
  await page.getByRole("button", { name: "名前を変更する" }).click();

  await page.getByRole("button", { name: "参加者" }).click();
  await expect(page.getByText(testName).first()).toBeVisible({ timeout: 15000 });

  await nameInput.fill(originalName);
  await page.getByRole("button", { name: "名前を変更する" }).click();
  await expect(async () => {
    const currentName = await nameInput.inputValue();
    expect(currentName).toBe(originalName);
  }).toPass({ timeout: 15000 });
});
