import { expect, test } from "@playwright/test";
import { APP, PLAYER_STORAGE } from "../../helpers/app";

test.use({ storageState: PLAYER_STORAGE });

test("ログイン済みユーザはマイページからログアウトできる", async ({ page }) => {
  await page.goto(`${APP}/me`);
  await expect(page.getByRole("heading", { name: "マイページ" })).toBeVisible();

  await page.getByRole("button", { name: "ログアウト" }).click();

  await expect(page).toHaveURL(new RegExp(`${APP}/login`));
});
