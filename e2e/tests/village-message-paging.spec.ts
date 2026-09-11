import { expect, test, type Page } from "@playwright/test";
import { ensureVillagesExist } from "./helpers/provision";

/**
 * 発言ページングの e2e。複数ページある村はローカル DB 依存のため、
 * 表示発言数を最小にした上で村一覧から動的に探し、無ければスキップする。
 */

const PAGE_SIZE = 10;

type PagedContent = { allPageCount: number };
type VillageDays = { days: { list: { day: number }[] } };
type Target = { id: number; day: number; allPageCount: number };

async function findMultiPageTarget(page: Page): Promise<Target | null> {
  // ensureVillagesExist は募集中・進行中を含むと終了村を探さないため、発言が蓄積している終了村を先に別途集める
  const villages = [
    ...(await ensureVillagesExist(page, ["EPILOGUE", "COMPLETED"])),
    ...(await ensureVillagesExist(page, ["IN_PROGRESS"])),
  ];
  for (const village of villages) {
    const villageRes = await page.request.get(`/wolf-mansion-api/api/v1/villages/${village.id}`);
    if (!villageRes.ok()) continue;
    const days = ((await villageRes.json()) as VillageDays).days.list ?? [];
    for (const { day } of days) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${village.id}/messages?day=${day}&isPaging=true&pageSize=${PAGE_SIZE}&isDispLatest=true`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as PagedContent;
      if (body.allPageCount >= 2) return { id: village.id, day, allPageCount: body.allPageCount };
    }
  }
  return null;
}

/** ページング操作は発言ログの上下 2 箇所に描画されるため、上側だけを対象にする */
function pagingButton(page: Page, label: string) {
  return page.getByRole("button", { name: label, exact: true }).first();
}

test("「最新」表示中でも << < > >> でページ移動できる", async ({ page }) => {
  await page.addInitScript((pageSize) => {
    localStorage.setItem(
      "wolf-mansion-display-settings",
      JSON.stringify({ state: { isPaging: true, pageSize }, version: 0 }),
    );
  }, PAGE_SIZE);

  const target = await findMultiPageTarget(page);
  test.skip(target == null, "複数ページになる村が無いためスキップ");
  if (target == null) return;
  const lastPage = String(target.allPageCount);

  await page.goto(`village/${target.id}/day/${target.day}`);
  await expect(pagingButton(page, "最新")).toBeVisible({ timeout: 15000 });

  const showLatest = async () => {
    await pagingButton(page, "最新").click();
    await expect(pagingButton(page, "最新")).toHaveClass(/bg-success-light/);
  };

  await showLatest();
  await pagingButton(page, "<<").click();
  await expect(pagingButton(page, "1")).toHaveClass(/bg-success-light/);

  for (const label of ["<", ">", ">>"]) {
    await showLatest();
    await pagingButton(page, label).click();
    await expect(pagingButton(page, lastPage)).toHaveClass(/bg-success-light/);
  }
});
