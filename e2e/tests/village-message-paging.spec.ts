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
  const villages = await ensureVillagesExist(page, [
    "IN_PREPARATION",
    "IN_PROGRESS",
    "EPILOGUE",
    "COMPLETED",
  ]);
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

function pagingButton(page: Page, label: string) {
  return page.getByRole("button", { name: label, exact: true });
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
  await expect(pagingButton(page, "最新").first()).toBeVisible({ timeout: 15000 });

  const showLatest = async () => {
    await pagingButton(page, "最新").first().click();
    await expect(pagingButton(page, "最新").first()).toHaveClass(/bg-success-light/);
  };

  await showLatest();
  await pagingButton(page, "<<").first().click();
  await expect(pagingButton(page, "1").first()).toHaveClass(/bg-success-light/);

  for (const label of ["<", ">", ">>"]) {
    await showLatest();
    await pagingButton(page, label).first().click();
    await expect(pagingButton(page, lastPage).first()).toHaveClass(/bg-success-light/);
  }
});
