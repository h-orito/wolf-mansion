import { defineConfig, devices } from "@playwright/test";

/** frontend dev server のオリジン。アプリ自体は basename `/wolf-mansion` 配下。 */
const FRONTEND_ORIGIN = "http://localhost:5173";

/**
 * wolf-mansion E2E。
 *
 * 前提: backend (:8089) と MySQL (:4306) は事前に起動しておくこと (README 参照)。
 * frontend dev server は webServer が起動 / 再利用する。
 */
export default defineConfig({
  testDir: "./tests",
  globalSetup: "./global-setup.ts",
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  reporter: "html",
  use: {
    baseURL: FRONTEND_ORIGIN,
    trace: "on-first-retry",
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] },
    },
  ],
  webServer: {
    command: "pnpm dev",
    cwd: "../frontend",
    url: `${FRONTEND_ORIGIN}/wolf-mansion/`,
    reuseExistingServer: true,
    timeout: 120_000,
  },
});
