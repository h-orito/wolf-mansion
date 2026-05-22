import { expect, test } from "@playwright/test";
import { APP, NO_AUTH } from "../../helpers/app";

test.use({ storageState: NO_AUTH });

test("未認証で認証必須ページに来るとログインへリダイレクトされる", async ({ page }) => {
  await page.goto(`${APP}/me`);

  await expect(page).toHaveURL(new RegExp(`${APP}/login\\?redirect=`));
  await expect(page.getByRole("heading", { name: "wolf-mansion ログイン" })).toBeVisible();
});
