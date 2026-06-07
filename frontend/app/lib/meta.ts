/**
 * サブページ共通の `<head>` メタ生成 (SSR `layout/layout.html` の OGP 相当)。
 *
 * タイトルは `:8091` の title-pattern `$LAYOUT_TITLE | $CONTENT_TITLE` に揃え
 * 「WOLF MANSION | <ページ名>」とする。OGP はサイト共通値 (layout.html を踏襲)。
 * OGP 画像は移行中 backend (/wolf-mansion-api) 配信の静的アセット (home の meta と同方針。
 * 静的アセット移管 (Step 10/11) で /wolf-mansion 側へ更新する)。
 */

const SITE_DESCRIPTION =
  "WOLF MANSIONでは、占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と【投票】 の2つを使って推理・説得する 「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。";

export function siteMeta(pageTitle: string, description: string = SITE_DESCRIPTION) {
  return [
    { title: `WOLF MANSION | ${pageTitle}` },
    { name: "description", content: description },
    { property: "og:site_name", content: "WOLF MANSION" },
    { property: "og:type", content: "website" },
    { property: "og:url", content: "https://wolfort.net/wolf-mansion/" },
    { property: "og:description", content: description },
    {
      property: "og:image",
      content: "https://wolfort.net/wolf-mansion-api/app/images/ogp-top.png",
    },
    { name: "twitter:card", content: "summary_large_image" },
    { name: "twitter:site", content: "@ort_dev" },
  ];
}
