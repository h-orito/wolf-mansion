import type { Config } from "@react-router/dev/config";

export default {
  // SSR を有効化 (SPA モードにするなら false)
  ssr: true,
  // NOTE: create-react-router 7.16 テンプレ由来の v8_* future flags は、
  // minimumReleaseAge により RR を 7.15.1 に固定している間は型が合わない
  // (7.15.1 では unstable_* 名)。RR を 7.16+ に上げる際に再度有効化する。
} satisfies Config;
