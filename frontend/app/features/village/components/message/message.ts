import { DEFAULT_MESSAGE_STYLE, MESSAGE_STYLES } from "~/components/ui/messageStyles";
import type { VillageMessageContent } from "~/features/village/api";
import { MessageType } from "./messageType";

/** 発言系メッセージの種別ごとの表示定義。アンカー接頭辞と装飾 (拡声/虹塗り) の対象か。 */
export const SAY_VARIANTS: Record<
  string,
  { anchorPrefix: string; styleKey: string; decoratable: boolean }
> = {
  [MessageType.NORMAL_SAY]: { anchorPrefix: ">>", styleKey: "message-normal", decoratable: true },
  [MessageType.WEREWOLF_SAY]: {
    anchorPrefix: ">>*",
    styleKey: "message-werewolf",
    decoratable: true,
  },
  [MessageType.MONOLOGUE_SAY]: {
    anchorPrefix: ">>-",
    styleKey: "message-monologue",
    decoratable: false,
  },
  [MessageType.SECRET_SAY]: { anchorPrefix: ">>s", styleKey: "message-secret", decoratable: false },
  [MessageType.MASON_SAY]: { anchorPrefix: ">>=", styleKey: "message-mason", decoratable: true },
  [MessageType.LOVERS_SAY]: { anchorPrefix: ">>?", styleKey: "message-lover", decoratable: true },
  [MessageType.TELEPATHY]: {
    anchorPrefix: ">>_",
    styleKey: "message-telepathy",
    decoratable: true,
  },
  [MessageType.GRAVE_SAY]: { anchorPrefix: ">>+", styleKey: "message-grave", decoratable: true },
  [MessageType.SPECTATE_SAY]: {
    anchorPrefix: ">>@",
    styleKey: "message-spectate",
    decoratable: false,
  },
};

/** システム系メッセージの種別 → 配色キー。 */
export const SYSTEM_VARIANTS: Record<string, string> = {
  [MessageType.PUBLIC_SYSTEM]: "message-public-system",
  [MessageType.PRIVATE_SYSTEM]: "message-private-system",
  [MessageType.PRIVATE_SEER]: "message-private-seer",
  [MessageType.PRIVATE_WISE]: "message-private-seer",
  [MessageType.PRIVATE_PSYCHIC]: "message-private-psychic",
  [MessageType.PRIVATE_GURU]: "message-private-psychic",
  [MessageType.PRIVATE_CORONER]: "message-private-psychic",
  [MessageType.PRIVATE_INVESTIGATE]: "message-private-investigate",
  [MessageType.PRIVATE_WEREWOLF]: "message-private-werewolf",
  [MessageType.PRIVATE_LOVER]: "message-private-lover",
  [MessageType.PRIVATE_FOX]: "message-private-fox",
  [MessageType.PRIVATE_ABILITY]: "message-private-ability",
};

const bubbleBaseClass = "message font-sans rounded-[5px] border p-[9px] break-words";

export function bubbleClass(styleKey: string): string {
  return `${bubbleBaseClass} ${styleKey} ${MESSAGE_STYLES[styleKey] ?? DEFAULT_MESSAGE_STYLE}`;
}

/** 返信・秘話返信で発言フォームへ引き継ぐ内容。 */
export type ReplyDraft = {
  anchorText: string | null;
  secretTargetCharaId: number | null;
  message: VillageMessageContent;
};

// ---------------------------------------------------------------------------
// 本文テキスト → 表示用 HTML 変換
// ---------------------------------------------------------------------------

const diceRegex = /(\[\[\d{1}d\d{1,5}\]\]?)/g;
const fortuneRegex = /(\[\[fortune\]\])/g;
const orRegex = /(?!\[\[fortune\]\])(\[\[[^\]]*or.*?\]\])/g;
const whoRegex = /(?!\[\[allwho\]\])(\[\[who\]\])/g;
const allWhoRegex = /(\[\[allwho\]\])/g;
const gwhoRegex = /(\[\[gwho\]\])/g;
const colorRegex = /\[\[(#[0-9a-fA-F]{6})\]\](.*?)\[\[\/#\]\]/g;
const boldRegex = /\[\[b\]\](.*?)\[\[\/b\]\]/g;
const strikeRegex = /\[\[s\]\](.*?)\[\[\/s\]\]/g;
const largeRegex = /\[\[large\]\](.*?)\[\[\/large\]\]/g;
const smallRegex = /\[\[small\]\](.*?)\[\[\/small\]\]/g;
const rubyRegex = /\[\[ruby\]\](.*?)\[\[rt\]\](.*?)\[\[\/rt\]\]\[\[\/ruby\]\]/g;
const netabareRegex = /\[\[netabare\]\](.*?)\[\[\/netabare\]\]/g;
const cwRegex = /\[\[cw\]\](.*?)\[\[\/cw\]\]/g;
const transparencyRegex = /\[\[tp\]\](.*?)\[\[\/tp\]\]/g;

const ANCHOR_RULES: { regex: RegExp; type: string; prefix: string }[] = [
  { regex: /&gt;&gt;(\d{1,5})/g, type: MessageType.NORMAL_SAY, prefix: "&gt;&gt;" },
  { regex: /&gt;&gt;\+(\d{1,5})/g, type: MessageType.GRAVE_SAY, prefix: "&gt;&gt;+" },
  { regex: /&gt;&gt;=(\d{1,5})/g, type: MessageType.MASON_SAY, prefix: "&gt;&gt;=" },
  { regex: /&gt;&gt;\?(\d{1,5})/g, type: MessageType.LOVERS_SAY, prefix: "&gt;&gt;?" },
  { regex: /&gt;&gt;_(\d{1,5})/g, type: MessageType.TELEPATHY, prefix: "&gt;&gt;_" },
  { regex: /&gt;&gt;@(\d{1,5})/g, type: MessageType.SPECTATE_SAY, prefix: "&gt;&gt;@" },
  { regex: /&gt;&gt;-(\d{1,5})/g, type: MessageType.MONOLOGUE_SAY, prefix: "&gt;&gt;-" },
  { regex: /&gt;&gt;\*(\d{1,5})/g, type: MessageType.WEREWOLF_SAY, prefix: "&gt;&gt;*" },
  { regex: /&gt;&gt;#(\d{1,5})/g, type: MessageType.CREATOR_SAY, prefix: "&gt;&gt;#" },
  { regex: /&gt;&gt;a(\d{1,5})/g, type: MessageType.ACTION, prefix: "&gt;&gt;a" },
  { regex: /&gt;&gt;s(\d{1,5})/g, type: MessageType.SECRET_SAY, prefix: "&gt;&gt;s" },
];

function escapeRegExp(text: string): string {
  return text.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
}

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
  item = item.replace(/'/g, "&#39;");
  for (const rule of ANCHOR_RULES) {
    item = item.replace(
      rule.regex,
      `<a href="javascript:void(0);" data-anchor-type="${rule.type}" data-anchor-number="$1">${rule.prefix}$1</a>`,
    );
  }
  return item;
}

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

export function replaceIdLink(messageType: string, html: string): string {
  if (messageType === MessageType.PUBLIC_SYSTEM && html.includes("(master)、死亡")) {
    const base = import.meta.env.BASE_URL.replace(/\/$/, "");
    return html.replace(
      / \(([^(]*)\)、/g,
      (_, name: string) =>
        `(<a href="${base}/user/${encodeURIComponent(name)}" target="_blank" rel="noreferrer">${name}</a>)、`,
    );
  }
  return html;
}

export function formatMessageTime(iso: string): string {
  return iso.split(".")[0].replace("T", " ").replace(/-/g, "/");
}
