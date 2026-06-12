/**
 * 発言本文 (生テキスト) を表示用 HTML へ変換する。サーバは本文を生のまま返し、
 * エスケープ・装飾・アンカーのリンク化はクライアントの責務 (発言保存時も生のまま)。
 *
 * 変換順序が安全性に直結する: 先に HTML エスケープし、その後で許可した装飾だけを
 * 信頼できる固定マークアップに置換する。
 */

// ランダム表記 (展開済みの注釈を小さく出す)
const diceRegex = /(\[\[\d{1}d\d{1,5}\]\]?)/g;
const fortuneRegex = /(\[\[fortune\]\])/g;
const orRegex = /(?!\[\[fortune\]\])(\[\[[^\]]*or.*?\]\])/g;
const whoRegex = /(?!\[\[allwho\]\])(\[\[who\]\])/g;
const allWhoRegex = /(\[\[allwho\]\])/g;
const gwhoRegex = /(\[\[gwho\]\])/g;
// 文字装飾
const colorRegex = /\[\[(#[0-9a-fA-F]{6})\]\](.*?)\[\[\/#\]\]/g;
const boldRegex = /\[\[b\]\](.*?)\[\[\/b\]\]/g;
const strikeRegex = /\[\[s\]\](.*?)\[\[\/s\]\]/g;
const largeRegex = /\[\[large\]\](.*?)\[\[\/large\]\]/g;
const smallRegex = /\[\[small\]\](.*?)\[\[\/small\]\]/g;
const rubyRegex = /\[\[ruby\]\](.*?)\[\[rt\]\](.*?)\[\[\/rt\]\]\[\[\/ruby\]\]/g;
const netabareRegex = /\[\[netabare\]\](.*?)\[\[\/netabare\]\]/g;
const cwRegex = /\[\[cw\]\](.*?)\[\[\/cw\]\]/g;
const transparencyRegex = /\[\[tp\]\](.*?)\[\[\/tp\]\]/g;

/** アンカー記法 → クリック用 data 属性 (messageType を data-anchor-type に持つ)。 */
const ANCHOR_RULES: { regex: RegExp; type: string; prefix: string }[] = [
  { regex: /&gt;&gt;(\d{1,5})/g, type: "NORMAL_SAY", prefix: "&gt;&gt;" },
  { regex: /&gt;&gt;\+(\d{1,5})/g, type: "GRAVE_SAY", prefix: "&gt;&gt;+" },
  { regex: /&gt;&gt;=(\d{1,5})/g, type: "MASON_SAY", prefix: "&gt;&gt;=" },
  { regex: /&gt;&gt;\?(\d{1,5})/g, type: "LOVERS_SAY", prefix: "&gt;&gt;?" },
  { regex: /&gt;&gt;_(\d{1,5})/g, type: "TELEPATHY", prefix: "&gt;&gt;_" },
  { regex: /&gt;&gt;@(\d{1,5})/g, type: "SPECTATE_SAY", prefix: "&gt;&gt;@" },
  { regex: /&gt;&gt;-(\d{1,5})/g, type: "MONOLOGUE_SAY", prefix: "&gt;&gt;-" },
  { regex: /&gt;&gt;\*(\d{1,5})/g, type: "WEREWOLF_SAY", prefix: "&gt;&gt;*" },
  { regex: /&gt;&gt;#(\d{1,5})/g, type: "CREATOR_SAY", prefix: "&gt;&gt;#" },
  { regex: /&gt;&gt;a(\d{1,5})/g, type: "ACTION", prefix: "&gt;&gt;a" },
  { regex: /&gt;&gt;s(\d{1,5})/g, type: "SECRET_SAY", prefix: "&gt;&gt;s" },
];

function escapeRegExp(text: string): string {
  return text.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
}

/**
 * 1 行ぶんの変換。エスケープ → ランダム表記 → ハッシュタグ → アンカーの順。
 */
function convertLine(line: string, isConvertDisable: boolean, randomKeywords: string[]): string {
  let item = line
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;");
  if (!isConvertDisable) {
    item = item.replace(diceRegex, '<span class="msg-extra-small">$1</span>');
    item = item.replace(fortuneRegex, '<span class="msg-extra-small">$1</span>');
    item = item.replace(orRegex, '<span class="msg-extra-small">$1</span>');
    item = item.replace(whoRegex, '<span class="msg-extra-small">$1</span>');
    item = item.replace(allWhoRegex, '<span class="msg-extra-small">$1</span>');
    item = item.replace(gwhoRegex, '<span class="msg-extra-small">$1</span>');
    for (const keyword of randomKeywords) {
      const regex = new RegExp(`(\\[\\[${escapeRegExp(keyword)}\\]\\])`, "g");
      item = item.replace(regex, '<span class="msg-extra-small">$1</span>');
    }
  }
  // ハッシュタグ (アンカー記法・装飾タグの直後の # は対象外。否定後読みの非対応ブラウザ向け実装を踏襲)
  const hashArray = item.split("#");
  item = hashArray
    .map((str, index) => {
      if (index === 0) return str;
      const prev = hashArray[index - 1];
      if (prev.endsWith("&gt;&gt;") || prev.endsWith("[[") || prev.endsWith("[[/")) {
        return `#${str}`;
      }
      return str.replace(
        /^([Ａ-Ｚａ-ｚA-Za-z一-鿆0-9０-９ぁ-ヶｦ-ﾟー._-]+)(.*)/g,
        '<a href="javascript:void(0);" data-message-hashtag="#$1">#$1</a>$2',
      );
    })
    .join("");
  // シングルクォートはハッシュタグより後で変換する
  item = item.replace(/'/g, "&#39;");
  for (const rule of ANCHOR_RULES) {
    item = item.replace(
      rule.regex,
      `<a href="javascript:void(0);" data-anchor-type="${rule.type}" data-anchor-number="$1">${rule.prefix}$1</a>`,
    );
  }
  return item;
}

/** 本文全体を表示用 HTML にする。 */
export function toMessageHtml(
  content: string,
  isConvertDisable: boolean,
  randomKeywords: string[],
): string {
  let mes = content
    .replace(/(\r\n|\n|\r)/gm, "<br>")
    .split("<br>")
    .map((line) => convertLine(line, isConvertDisable, randomKeywords))
    .join("<br>");
  if (!isConvertDisable) {
    mes = mes.replace(colorRegex, '<span style="color: $1">$2</span>');
    mes = mes.replace(boldRegex, "<strong>$1</strong>");
    mes = mes.replace(strikeRegex, '<span style="text-decoration: line-through;">$1</span>');
    mes = mes.replace(largeRegex, '<span style="font-size: 150%;">$1</span>');
    mes = mes.replace(smallRegex, '<span style="font-size: 80%;">$1</span>');
    mes = mes.replace(rubyRegex, "<ruby>$1<rt>$2</rt></ruby>");
    mes = mes.replace(netabareRegex, '<span class="netabare">$1</span>');
    mes = mes.replace(cwRegex, '<span class="netabare">$1</span>');
    mes = mes.replace(transparencyRegex, '<span class="transparency">$1</span>');
  }
  return mes;
}

/**
 * 死亡時の公開システムメッセージ中のユーザ ID をプロフィールリンクにする。
 * リンク先は SPA の `/user/{name}` (リンク規約: 未移行ページも SPA URL を指す)。
 */
export function replaceIdLink(messageType: string, html: string): string {
  if (messageType === "PUBLIC_SYSTEM" && html.includes("(master)、死亡")) {
    const base = import.meta.env.BASE_URL.replace(/\/$/, "");
    return html.replace(
      / \(([^(]*)\)、/g,
      (_, name: string) =>
        `(<a href="${base}/user/${encodeURIComponent(name)}" target="_blank" rel="noreferrer">${name}</a>)、`,
    );
  }
  return html;
}

/** ISO 日時 → `yyyy/MM/dd HH:mm:ss` 表示 (小数秒は落とす)。 */
export function formatMessageTime(iso: string): string {
  return iso.split(".")[0].replace("T", " ").replace(/-/g, "/");
}
