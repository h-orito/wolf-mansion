import { expect, test, type Page } from "@playwright/test";

/**
 * 村切り抜き画面 (`/village/{id}/scrap`) e2e。
 * 村の状態はローカル DB に依存するため、村一覧 API から対象を動的に探し、
 * 該当する村が無い場合はスキップする。読み取りのみで DB を変更しない。
 */

type SimpleVillage = { id: number; name: string };

async function findVillages(page: Page, statuses: string[]): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { villages: SimpleVillage[] };
  return body.villages;
}

test("切り抜き画面が表示され、村タイトルが見える", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS", "COMPLETED"]);
  test.skip(villages.length === 0, "進行中/終了の村が無い DB のためスキップ");
  const village = villages[0];

  await page.goto(`village/${village.id}/scrap`);

  await expect(page).toHaveTitle(`WOLF MANSION | ${village.name}`, { timeout: 15000 });
  const number = String(village.id).padStart(4, "0");
  await expect(page.getByRole("heading", { name: `${number}. ${village.name}` })).toBeVisible();
});

test("アンカー追加で URL に反映され、全消去で anchors が消える", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PROGRESS", "COMPLETED"]);
  test.skip(villages.length === 0, "進行中/終了の村が無い DB のためスキップ");
  const village = villages[0];

  await page.goto(`village/${village.id}/scrap`);
  await expect(page.getByRole("heading", { level: 1 })).toBeVisible({ timeout: 15000 });

  // アンカー入力 → 追加
  await page.getByLabel("アンカー").fill(">>1");
  await page.getByRole("button", { name: "追加" }).click();

  // URL に anchors=n1 が付く
  await expect(page).toHaveURL(/anchors=n1/, { timeout: 5000 });

  // 発言の読み込みを待つ (無ければ 0 件でも URL 反映だけ assert)
  const messageLocator = page.locator(".message");
  const count = await messageLocator.count();
  if (count > 0) {
    await expect(messageLocator.first()).toBeVisible();
  }

  // 全消去 → anchors が URL から消える
  await page.getByRole("button", { name: "全消去" }).click();
  await expect(page).not.toHaveURL(/anchors/, { timeout: 5000 });
});
