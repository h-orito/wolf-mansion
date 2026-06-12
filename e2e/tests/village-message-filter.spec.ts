import { expect, test, type Page } from "@playwright/test";

/**
 * 村画面の発言抽出 e2e。村はローカル DB から動的に探し、無ければスキップする。
 */

type SimpleVillage = { id: number; name: string };

async function findVillages(page: Page, statuses: string[]): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  const body = (await res.json()) as { villages: SimpleVillage[] };
  return body.villages;
}

test("URL の typ パラメータで種別が絞り込まれる (共有 URL の再現)", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PREPARATION", "IN_PROGRESS"]);
  test.skip(villages.length === 0, "表示できる村が無い DB のためスキップ");
  const village = villages[villages.length - 1];

  // 公開システムメッセージのみに絞る (どの村にも村オープン時の定型文がある)
  await page.goto(`village/${village.id}?typ=PUBLIC_SYSTEM`);

  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
  // 通常発言の吹き出しが出ない (種別フィルタが効いている)
  await expect(page.locator(".message-normal")).toHaveCount(0);
  // footer のボタンが「抽出中」表示になる
  await expect(page.getByRole("button", { name: "抽出中" })).toBeVisible();
});

test("抽出モーダルからキーワード抽出すると URL に保存される", async ({ page }) => {
  const villages = await findVillages(page, ["IN_PREPARATION", "IN_PROGRESS"]);
  test.skip(villages.length === 0, "表示できる村が無い DB のためスキップ");
  const village = villages[villages.length - 1];

  await page.goto(`village/${village.id}`);
  await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });

  await page.getByRole("button", { name: "抽出", exact: true }).click();
  await expect(page.getByRole("heading", { name: "発言抽出" })).toBeVisible();

  await page.getByPlaceholder("スペース区切り").fill("ようこそ");
  await page.getByRole("button", { name: "抽出", exact: true }).last().click();

  await expect(page).toHaveURL(/kwd=/);
});
