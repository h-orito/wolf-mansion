import { expect, test } from "@playwright/test";
import {
  ensureVillagesExist,
  findVillages,
  loginApi,
  provisionCompletedVillage,
  type SimpleVillage,
} from "./helpers/provision";

/**
 * 参加報告メーカー (`/village/{id}/report`) e2e。
 * 参加した本人のみ利用できる画面のため、村に参加している master でログインして検証する。
 * 終了済みの村が必要なため、無ければ debug の日送りで新規に決着させる
 * (provision した村は master がダミーとして参加している)。
 */

async function completedVillage(page: Parameters<typeof findVillages>[0]): Promise<SimpleVillage> {
  const found = await findVillages(page, ["COMPLETED"]);
  if (found.length > 0) return found[0];
  return provisionCompletedVillage(page);
}

test("参加者本人は吹き出しプレビューと役職説明が表示される", async ({ page }) => {
  test.setTimeout(180000); // 終了村が無い場合は日送りで新規作成するため長め
  const village = await completedVillage(page);
  await loginApi(page, "master");

  await page.goto(`village/${village.id}/report`);
  await expect(page.getByRole("heading", { name: /参加報告メーカー/ })).toBeVisible({
    timeout: 15000,
  });

  // 発言を入力するとプレビューの吹き出しに反映される
  await page.getByLabel("発言").fill("お疲れ様でした！");
  await expect(page.locator(".message").getByText("お疲れ様でした！")).toBeVisible();

  // 本人の役職説明がプレビューに表示される
  await expect(page.getByText(/^あなたは/).first()).toBeVisible();

  // X ポストリンクが intent URL を指す
  await expect(page.getByRole("link", { name: "ポスト" })).toHaveAttribute(
    "href",
    /x\.com\/intent\/post/,
  );
});

test("発言種別を切り替えると吹き出しの配色クラスが変わる", async ({ page }) => {
  test.setTimeout(180000);
  const village = await completedVillage(page);
  await loginApi(page, "master");

  await page.goto(`village/${village.id}/report`);
  await expect(page.getByRole("heading", { name: /参加報告メーカー/ })).toBeVisible({
    timeout: 15000,
  });

  await expect(page.locator(".message.message-normal")).toBeVisible();
  await page.getByRole("button", { name: "囁き" }).click();
  await expect(page.locator(".message.message-werewolf")).toBeVisible();
});

test("未ログインでは利用できない", async ({ page }) => {
  test.setTimeout(180000);
  const village = await completedVillage(page);
  // 終了村が無く provision した場合は master でログイン済みになるため、未ログイン状態に戻す
  await page.context().clearCookies();

  await page.goto(`village/${village.id}/report`);
  await expect(
    page.getByText("参加報告メーカーは、この村に参加した本人のみ利用できます。"),
  ).toBeVisible({ timeout: 15000 });
  await expect(page.getByRole("link", { name: "ログイン" })).toBeVisible();
});

test("進行中の村では利用できない", async ({ page }) => {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS"]);

  await page.goto(`village/${villages[0].id}/report`);
  await expect(page.getByText("参加報告メーカーはエピローグ以降に利用できます。")).toBeVisible({
    timeout: 15000,
  });
});
