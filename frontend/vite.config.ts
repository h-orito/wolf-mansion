import { reactRouter } from "@react-router/dev/vite";
import tailwindcss from "@tailwindcss/vite";
import { defineConfig } from "vite";

export default defineConfig({
  // public/ 配下を basename と同じ /wolf-mansion の下で配信させる。
  // dev / prod で URL を揃え、`/wolf-mansion/img/*` `/wolf-mansion/fonts/*` の
  // 直書きが両方の環境で解決できるようにする。
  // RR の制約: react-router の basename は vite の base から始まる必要があるため、
  // 末尾スラッシュは付けない (basename "/wolf-mansion" と前方一致するように)。
  base: "/wolf-mansion",
  plugins: [tailwindcss(), reactRouter()],
  resolve: {
    tsconfigPaths: true,
  },
  server: {
    // dev 中の browser fetch を 同一オリジン扱いにするためのプロキシ。
    // 本番では ingress が同一ドメインの /wolf-mansion-api を API service にルーティングする。
    proxy: {
      "/wolf-mansion-api": {
        target: "http://localhost:8089",
        changeOrigin: false,
      },
    },
  },
});
