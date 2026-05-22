import { chromium, type FullConfig } from "@playwright/test";
import { mkdirSync } from "node:fs";
import { USERS } from "./fixtures/users";
import { APP, PLAYER_STORAGE } from "./helpers/app";

/**
 * テスト用 player でログインし、認証 Cookie を storageState として保存する。
 * 認証が必要なテストは `test.use({ storageState: PLAYER_STORAGE })` で再利用する。
 */
async function globalSetup(config: FullConfig): Promise<void> {
  const origin = config.projects[0]?.use?.baseURL ?? "http://localhost:5173";
  mkdirSync(".auth", { recursive: true });

  const browser = await chromium.launch();
  try {
    const page = await browser.newPage();
    await page.goto(`${origin}${APP}/login`);
    await page.fill("#userId", USERS.player.userId);
    await page.fill("#password", USERS.player.password);
    await page.click('button[type="submit"]');
    // ログイン成功でトップ (basename 直下) へ遷移する
    await page.waitForURL(new RegExp(`${APP}/?$`));
    await page.context().storageState({ path: PLAYER_STORAGE });
  } finally {
    await browser.close();
  }
}

export default globalSetup;
