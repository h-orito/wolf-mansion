import { reactRouter } from "@react-router/dev/vite";
import tailwindcss from "@tailwindcss/vite";
import { defineConfig } from "vite";

export default defineConfig({
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
