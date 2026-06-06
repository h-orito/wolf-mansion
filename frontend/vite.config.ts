import { reactRouter } from "@react-router/dev/vite";
import tailwindcss from "@tailwindcss/vite";
import { defineConfig } from "vite";

// dev クロスオリジン吸収: CORS を入れず Vite proxy で backend に転送する (Step 3.3 / 03-auth.md)。
// rewrite しない: backend が発行する cookie の Path=/wolf-mansion(/...) とブラウザ可視パスを
// 一致させ、access/refresh/id_register cookie を確実にブラウザへ届かせるため。
// 本番は frontend(/wolf-mansion) と backend(/wolf-mansion-api) が同一オリジンなので proxy は dev のみ。
const BACKEND_ORIGIN = process.env.BACKEND_ORIGIN ?? "http://localhost:8089";

export default defineConfig({
  plugins: [tailwindcss(), reactRouter()],
  resolve: {
    tsconfigPaths: true,
  },
  // フォーム系ルート (/login,/signup,/change-password) でしか使わない依存を起動時に
  // 先行 pre-bundle する。これらをトップ画面ロードでは発見できないため、初回フォーム遷移時に
  // vite の dep 再最適化 → 全ページ強制リロードが発生し、dev 操作中断や e2e 並列実行の
  // フレークを招く。明示 include で実行時の再最適化を無くす。
  optimizeDeps: {
    include: ["react-hook-form", "zod", "@tanstack/react-query"],
  },
  server: {
    proxy: {
      "/wolf-mansion": {
        target: BACKEND_ORIGIN,
        changeOrigin: true,
      },
    },
  },
});
