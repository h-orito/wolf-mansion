import { expect, test } from "@playwright/test";

test.describe("ユーザプロフィール", () => {
  test("存在するユーザのプロフィールが表示される", async ({ page }) => {
    await page.goto("user/master");
    await expect(page.getByRole("heading", { name: /ユーザID: master/ })).toBeVisible();
    await expect(page.getByText("総合戦績")).toBeVisible();
    await expect(page.getByText("陣営戦績")).toBeVisible();
    await expect(page.getByText("役職戦績")).toBeVisible();
  });

  test("存在しないユーザは「存在しません」と表示される", async ({ page }) => {
    await page.goto("user/nonexistent_user_12345");
    await expect(page.getByRole("heading", { name: /ユーザID: nonexistent_user_12345/ })).toBeVisible();
    await expect(page.getByText("ユーザが存在しません")).toBeVisible({ timeout: 15000 });
  });
});
