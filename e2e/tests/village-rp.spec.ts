import { expect, test, type Page } from "@playwright/test";

/**
 * RP 支援 (名前変更・簡易メモ) の e2e。進行中の村の参加者を
 * ローカル開発 DB のテストユーザー (testuser01〜16 / password=testuser) から
 * 動的に探し、見つからなければスキップする。変更後は元の値に戻して終わるため
 * 共有 DB の進行状態を変えない。
 */

type SimpleVillage = { id: number; name: string };

type RpView = {
  isAvailableChangeName: boolean;
  isAvailableMemo: boolean;
  name: string | null;
  shortName: string | null;
  memo: string | null;
};

type Candidate = { villageId: number; userId: string; rp: RpView };

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

async function findRpCandidate(
  page: Page,
  filter: (rp: RpView) => boolean,
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
      const body = (await res.json()) as { rp: RpView };
      if (filter(body.rp)) {
        return { villageId: village.id, userId, rp: body.rp };
      }
    }
  }
  return null;
}

async function dismissAgeLimitModal(page: Page) {
  const ageLimitConfirm = page.getByRole("button", { name: "表示する" });
  if ((await ageLimitConfirm.count()) > 0) {
    await ageLimitConfirm.click();
  }
}
/** 初回役職確認モーダルが被っていたら閉じる。 */
async function dismissInitialSkillModal(page: Page) {
  const confirm = page.getByRole("button", { name: "確認したので次回以降表示しない" });
  if ((await confirm.count()) > 0) {
    await confirm.click();
  }
}


test("簡易メモを変更すると参加者一覧に表示される (元に戻す)", async ({ page }) => {
  const candidate = await findRpCandidate(page, (rp) => rp.isAvailableMemo);
  test.skip(candidate == null, "簡易メモを変更できる参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  const memoInput = page.getByLabel("簡易メモ");
  await expect(memoInput).toBeVisible({ timeout: 15000 });
  await dismissAgeLimitModal(page);
  await dismissInitialSkillModal(page);

  const testMemo = "e2eメモ";
  await memoInput.fill(testMemo);
  await page.getByRole("button", { name: "簡易メモを変更する" }).click();

  // 状況の参加者タブに [memo] が出る (公開情報)
  await page.getByRole("button", { name: "参加者" }).click();
  await expect(page.getByText(`[${testMemo}]`)).toBeVisible({ timeout: 15000 });

  // 元に戻す
  await memoInput.fill(candidate.rp.memo ?? "");
  await page.getByRole("button", { name: "簡易メモを変更する" }).click();
  await expect(page.getByText(`[${testMemo}]`)).toHaveCount(0, { timeout: 15000 });
});

test("キャラ名を変更できる (元に戻す)", async ({ page }) => {
  const candidate = await findRpCandidate(
    page,
    (rp) => rp.isAvailableChangeName && rp.name != null && rp.shortName != null,
  );
  test.skip(candidate == null, "名前を変更できる参加者が見つからない DB のためスキップ");
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  const nameInput = page.getByLabel("名前", { exact: true });
  await expect(nameInput).toBeVisible({ timeout: 15000 });
  await dismissAgeLimitModal(page);
  await dismissInitialSkillModal(page);

  // 現在の名前が初期表示される
  await expect(nameInput).toHaveValue(candidate.rp.name ?? "");

  const testName = `${candidate.rp.name}改`;
  await nameInput.fill(testName);
  await page.getByRole("button", { name: "名前を変更する" }).click();

  // 状況の参加者タブにサーバ反映された新しい名前が出る (ローカル state でなく round-trip を検証)
  await page.getByRole("button", { name: "参加者" }).click();
  await expect(page.getByText(testName).first()).toBeVisible({ timeout: 15000 });

  // 元に戻す (変更履歴のシステムメッセージに旧名が残るため、復元はサーバ状態で検証する)
  await nameInput.fill(candidate.rp.name ?? "");
  await page.getByRole("button", { name: "名前を変更する" }).click();
  await expect(async () => {
    const res = await page.request.get(
      `/wolf-mansion-api/api/v1/villages/${candidate.villageId}/situation/me`,
    );
    expect(res.ok()).toBeTruthy();
    const body = (await res.json()) as { rp: RpView };
    expect(body.rp.name).toBe(candidate.rp.name);
  }).toPass({ timeout: 15000 });
});
