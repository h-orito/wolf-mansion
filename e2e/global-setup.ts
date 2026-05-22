import { chromium, type FullConfig } from "@playwright/test";
import { mkdirSync } from "node:fs";
import { dirname } from "node:path";
import { USERS } from "./fixtures/users";
import { APP, PLAYER_STORAGE } from "./helpers/app";

/**
 * テスト用 player でログインし、認証 Cookie を storageState として保存する。
 * 認証が必要なテストは `test.use({ storageState: PLAYER_STORAGE })` で再利用する。
 *
 * globalSetup は `page` fixture / baseURL を使えないため、origin を config から
 * 取得して URL を自前で組み立てる (各 spec は baseURL 相対で書ける)。
 */
async function globalSetup(config: FullConfig): Promise<void> {
  const origin = config.projects[0]?.use?.baseURL ?? "http://localhost:5173";
  mkdirSync(dirname(PLAYER_STORAGE), { recursive: true });

  const browser = await chromium.launch();
  try {
    const page = await browser.newPage();
    await page.goto(`${origin}${APP}/login`);
    await page.locator("#userId").fill(USERS.player.userId);
    await page.locator("#password").fill(USERS.player.password);
    await page.locator('button[type="submit"]').click();
    // ログイン成功でトップ (basename 直下) へ遷移する
    await page.waitForURL(new RegExp(`${APP}/?$`));
    await page.context().storageState({ path: PLAYER_STORAGE });
  } finally {
    await browser.close();
  }
}

export default globalSetup;
