/**
 * サブページ共通の `<head>` メタ生成 (`layout/layout.html` の OGP 相当)。
 *
 * タイトルは title-pattern「WOLF MANSION | <ページ名>」。ページ名を省略するとサイト共通タイトル
 * 「WOLF MANSION 〜人狼館の事件簿村〜」になる (個別タイトルを持たないページ用)。OGP はサイト共通値。
 * OGP 画像は frontend (/wolf-mansion) 配信の public/app/images の静的アセット (本番の絶対 URL)。
 */

const SITE_DESCRIPTION =
  "WOLF MANSIONでは、占い・襲撃・護衛・狂狐の徘徊によって起こる【足音】と【投票】 の2つを使って推理・説得する 「人狼館の事件簿村」ルールの人狼ゲームを楽しむことができます。";

export function siteMeta(pageTitle?: string, description: string = SITE_DESCRIPTION) {
  return [
    { title: pageTitle ? `WOLF MANSION | ${pageTitle}` : "WOLF MANSION 〜人狼館の事件簿村〜" },
    { name: "description", content: description },
    { property: "og:site_name", content: "WOLF MANSION" },
    { property: "og:type", content: "website" },
    { property: "og:url", content: "https://wolfort.net/wolf-mansion/" },
    { property: "og:description", content: description },
    {
      property: "og:image",
      content: "https://wolfort.net/wolf-mansion/app/images/ogp-top.png",
    },
    { name: "twitter:card", content: "summary_large_image" },
    { name: "twitter:site", content: "@ort_dev" },
  ];
}
