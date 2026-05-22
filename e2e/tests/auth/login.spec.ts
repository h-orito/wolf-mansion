import { expect, test } from "@playwright/test";
import { USERS } from "../../fixtures/users";
import { APP, NO_AUTH } from "../../helpers/app";

// ログインフロー自体を検証するので未認証で開始する
test.use({ storageState: NO_AUTH });

test("正しい資格情報でログインしトップに遷移する", async ({ page }) => {
  await page.goto(`${APP}/login`);
  await page.fill("#userId", USERS.player.userId);
  await page.fill("#password", USERS.player.password);
  await page.click('button[type="submit"]');

  await expect(page).toHaveURL(new RegExp(`${APP}/?$`));
  await expect(page.getByRole("heading", { name: "wolf-mansion", exact: true })).toBeVisible();
});

test("誤ったパスワードはエラーメッセージを表示する", async ({ page }) => {
  await page.goto(`${APP}/login`);
  await page.fill("#userId", USERS.player.userId);
  await page.fill("#password", "wrong-password");
  await page.click('button[type="submit"]');

  await expect(page.getByRole("alert")).toHaveText("ID またはパスワードが違います");
  await expect(page).toHaveURL(new RegExp(`${APP}/login`));
});
