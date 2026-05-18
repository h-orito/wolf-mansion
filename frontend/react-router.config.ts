import type { Config } from "@react-router/dev/config";

export default {
  // SSR を有効化 (loader で API を呼び SEO とパフォーマンスを両立)
  ssr: true,
  // 本番では ingress が /wolf-mansion でルーティングするため、ベースパスを揃える
  basename: "/wolf-mansion",
} satisfies Config;
