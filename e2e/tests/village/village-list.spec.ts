import { expect, test } from "@playwright/test";
import { APP, NO_AUTH } from "../../helpers/app";

// 村一覧・トップは認証不要なので未認証で検証する
test.use({ storageState: NO_AUTH });

test("トップから全村一覧へ遷移できる", async ({ page }) => {
  await page.goto(`${APP}/`);
  await expect(page.getByRole("heading", { name: "wolf-mansion", exact: true })).toBeVisible();

  await page.getByRole("link", { name: "全村一覧" }).click();

  await expect(page).toHaveURL(new RegExp(`${APP}/villages`));
});
