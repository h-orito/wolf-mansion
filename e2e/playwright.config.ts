import { defineConfig, devices } from "@playwright/test";

/**
 * Playwright 設定 (Step 2.4 雛形 / 正本: doc/migration/05-e2e.md)
 *
 * - ローカル動作確認専用。CI では走らせない (走らせる前提で作り込まない)。
 * - webServer で backend / frontend を自動起動する。通常起動 (backend 8089 /
 *   frontend 5173) と別ポートを使い、開発サーバを止めずに e2e を回せるようにする。
 * - DB はローカル開発環境とあいのり (リセットしない)。実テストは各自 setup/teardown で
 *   独立データを作る規約 (本雛形の smoke は読み取りのみで DB を変更しない)。
 */

// 通常起動 (backend 8089 / frontend 5173) と衝突しない e2e 専用ポート (05-e2e.md で確定)
const BACKEND_PORT = 18089;
const FRONTEND_PORT = 15173;
const BASE_URL = `http://localhost:${FRONTEND_PORT}`;

// CI では走らせない方針だが、誤起動時に環境変数で識別できるよう一応参照する
const isCI = !!process.env.CI;

export default defineConfig({
  testDir: "./tests",
  // テストは独立データ前提なので並列で良い (05-e2e.md)
  fullyParallel: true,
  forbidOnly: isCI,
  retries: 0,
  reporter: "html",
  use: {
    baseURL: BASE_URL,
    // 失敗時のみ artifacts を残す (05-e2e.md)。video は取らない。
    trace: "on-first-retry",
    screenshot: "only-on-failure",
    video: "off",
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] },
    },
  ],
  webServer: [
    {
      // backend: application.yml デフォルト (ローカル MySQL 4306 / あいのり DB) を
      // 別ポートで起動。context-path は /wolf-mansion 据置。
      command: `./gradlew bootRun --args='--server.port=${BACKEND_PORT}'`,
      cwd: "../backend",
      url: `http://localhost:${BACKEND_PORT}/wolf-mansion/`,
      // ローカルで既に起動済みの backend があれば使い回す
      reuseExistingServer: !isCI,
      // Spring Boot + DBFlute 初期化に時間がかかるため長めに
      timeout: 180_000,
    },
    {
      command: `pnpm dev --port ${FRONTEND_PORT}`,
      cwd: "../frontend",
      url: BASE_URL,
      reuseExistingServer: !isCI,
      timeout: 120_000,
    },
  ],
});
