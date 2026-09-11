import { expect, test } from "@playwright/test";
import {
  ensureVillagesExist,
  loginApi,
  provisionRecruitingVillage,
} from "./helpers/provision";

/**
 * 村設定変更 (`/village/{id}/settings`、村主のみ) の e2e。
 * master 村建ての村を provision して使う (既存村頼みだと編集可能な村が無く落ち続ける)。
 */

test("村設定変更: 村名を変更して保存し、元に戻す", async ({ page }) => {
  // 村名を変更するため、共有村ではなくこのテスト専用の村を作る (master 村建て = 設定変更可)
  const village = await provisionRecruitingVillage(page);
  await loginApi(page, "master");

  await page.goto(`village/${village.id}/settings`);
  const nameInput = page.getByLabel("村名");
  await expect(nameInput).toBeVisible({ timeout: 15000 });
  await expect(nameInput).toHaveValue(village.name);

  // 変更不可の項目は読み取り表示 (役職希望にラジオが無い)
  await expect(page.getByRole("radiogroup", { name: "役職希望" })).toHaveCount(0);

  const newName = `${village.name}e2e`;
  await nameInput.fill(newName);
  await page.getByRole("button", { name: "変更する" }).click();

  // 保存後は村画面へ戻り、村名が反映されている
  await expect(page).toHaveURL(new RegExp(`/village/${village.id}\\/?$`), { timeout: 15000 });
  await expect(page.getByRole("heading", { name: new RegExp(newName) })).toBeVisible({
    timeout: 15000,
  });

  // 元に戻す
  await page.goto(`village/${village.id}/settings`);
  await expect(page.getByLabel("村名")).toHaveValue(newName, { timeout: 15000 });
  await page.getByLabel("村名").fill(village.name);
  await page.getByRole("button", { name: "変更する" }).click();
  await expect(page).toHaveURL(new RegExp(`/village/${village.id}\\/?$`), { timeout: 15000 });
  await expect(page.getByRole("heading", { name: new RegExp(village.name) })).toBeVisible({
    timeout: 15000,
  });
});

test("村建てでないユーザーには設定変更ページが開けない", async ({ page }) => {
  const villages = await ensureVillagesExist(page, ["IN_PREPARATION"]);
  const ok = await loginApi(page, "testuser01");
  expect(ok, "testuser01 が存在しない").toBe(true);

  await page.goto(`village/${villages[0].id}/settings`);
  await expect(page.getByText("村建てプレイヤーのみ実行できます")).toBeVisible({
    timeout: 15000,
  });
});
