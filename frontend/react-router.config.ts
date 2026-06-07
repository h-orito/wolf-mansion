import type { Config } from "@react-router/dev/config";

export default {
  // SSR を有効化 (SPA モードにするなら false)
  ssr: true,
  // frontend は `/wolf-mansion` 配下で配信する (既存サイトと同一・target 構成)。backend は
  // `/wolf-mansion-api` に分離済みでパスが衝突しない。
  // RR の制約: basename は Vite の `base` で始まる必要がある。base/basename とも末尾スラッシュ無しで揃える
  // (末尾スラッシュ付き basename は client nav の navigate("/") でルート未マッチ=空ページになるため)。
  basename: "/wolf-mansion",
  // NOTE: create-react-router 7.16 テンプレ由来の v8_* future flags は、
  // minimumReleaseAge により RR を 7.15.1 に固定している間は型が合わない
  // (7.15.1 では unstable_* 名)。RR を 7.16+ に上げる際に再度有効化する。
} satisfies Config;
