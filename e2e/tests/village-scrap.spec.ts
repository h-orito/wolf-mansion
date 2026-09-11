import { expect, test } from "@playwright/test";
import { ensureVillagesExist } from "./helpers/provision";

/**
 * 村切り抜き画面 (`/village/{id}/scrap`) e2e。
 * 村の状態はローカル DB に依存するため、村一覧 API から対象を動的に探し、
 * 該当する村が無い場合はスキップする。読み取りのみで DB を変更しない。
 */


test("切り抜き画面が表示され、村タイトルが見える", async ({ page }) => {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS", "COMPLETED"]);
  const village = villages[0];

  await page.goto(`village/${village.id}/scrap`);

  await expect(page).toHaveTitle(`WOLF MANSION | ${village.name}`, { timeout: 15000 });
  const number = String(village.id).padStart(4, "0");
  await expect(page.getByRole("heading", { name: `${number}. ${village.name}` })).toBeVisible();
});

test("アンカー追加で URL に反映され、全消去で anchors が消える", async ({ page }) => {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS", "COMPLETED"]);
  const village = villages[0];

  await page.goto(`village/${village.id}/scrap`);
  await expect(page.getByRole("heading", { level: 1 })).toBeVisible({ timeout: 15000 });

  // アンカー入力 → 追加 (発言取得 API の完了を待って DOM 反映を検証する)
  await page.getByLabel("アンカー").fill(">>1");
  const [anchorsRes] = await Promise.all([
    page.waitForResponse((res) => res.url().includes("/messages/anchors")),
    page.getByRole("button", { name: "追加" }).click(),
  ]);

  // URL に anchors=n1 が付く
  await expect(page).toHaveURL(/anchors=n1/, { timeout: 5000 });

  // 発言 #1 が存在する村なら、取得された発言が描画される
  const body = (await anchorsRes.json()) as { messageList?: unknown[] };
  if ((body.messageList ?? []).length > 0) {
    await expect(page.locator(".message").first()).toBeVisible({ timeout: 15000 });
  }

  // 全消去 → anchors が URL から消える
  await page.getByRole("button", { name: "全消去" }).click();
  await expect(page).not.toHaveURL(/anchors/, { timeout: 5000 });
});
