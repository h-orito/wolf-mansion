import * as React from "react";
import type { MessageView, VillageParticipantView } from "./api";
import { useSayFormRequester } from "./SayFormContext";

/**
 * 1 発言ぶんのカード。`MessageView.typeCode` に応じて色 / アンカー記号 / 構造を
 * 旧 Thymeleaf `.message-*` variant に揃えて描画する。
 *
 * Step 13c で旧 .old-thymeleaf/templates/village-template/message-partial.html
 * と bootstrap-override.css の色値を 1:1 で持ち込んだ。
 *
 * - 通常発言系 (kind=say): header (anchor + name + time) + face image + bubble + footer (返信/秘話)
 * - システム系 (kind=system): bubble だけのシンプル枠
 *
 * 装飾タグ (`[[b]]` / `[[s]]` / `[[large]]` 等) は本文 text を簡易パースして
 * React 要素を組み立てる (XSS 安全)。`loud` / `rainbow` / `extra-small` の自動
 * 付与は backend 側にフラグが必要なため当面非対応。
 */
export function MessageCard({
  message,
  participantsById,
}: {
  message: MessageView;
  participantsById: Map<number, VillageParticipantView>;
}) {
  const style = MESSAGE_STYLES[message.typeCode] ?? DEFAULT_STYLE;
  const fromParticipant = message.fromParticipantId != null
    ? participantsById.get(message.fromParticipantId)
    : undefined;
  const requestSay = useSayFormRequester();

  const content = renderMessageContent(message.text, message.isConvertDisable);

  if (style.kind === "system") {
    return (
      <article className="mb-[1em]" data-message-type={message.typeCode}>
        <div
          className="message-bubble"
          style={{
            backgroundColor: style.bg,
            color: style.color,
            border: `1px solid ${style.border}`,
            padding: "0.75em",
            borderRadius: "0.42em",
            fontSize: "1em",
            wordBreak: "break-word",
          }}
        >
          {content}
        </div>
      </article>
    );
  }

  const anchor = renderAnchor(style.anchorPrefix, message.number);
  const isSecret = message.typeCode === "SECRET_SAY";

  function handleAnchorClick() {
    if (message.number == null) return;
    requestSay({
      anchorPrefix: style.anchorPrefix,
      messageNumber: message.number,
      isSecret: false,
    });
  }

  function handleReplyClick() {
    if (message.number == null) return;
    requestSay({
      anchorPrefix: style.anchorPrefix,
      messageNumber: message.number,
      isSecret,
      secretTargetCharaId: isSecret ? fromParticipant?.chara.id : undefined,
    });
  }

  return (
    <article className="mb-[1em]" data-message-type={message.typeCode}>
      <div className="text-[0.85em] mb-[2px] flex flex-wrap items-baseline gap-x-2">
        {anchor && (
          <button
            type="button"
            onClick={handleAnchorClick}
            className="font-mono message-link hover:underline"
            title="アンカーを発言フォームに挿入"
          >
            {anchor}
          </button>
        )}
        <span>{message.fromCharaName ?? style.fallbackFrom}</span>
        {message.toCharaName && <span>→ {message.toCharaName}</span>}
        {fromParticipant?.playerName && (
          <span className="opacity-80">[{fromParticipant.playerName}]</span>
        )}
        <span className="ml-auto opacity-80">{formatTime(message.datetime)}</span>
      </div>
      <div className="flex items-start gap-[5px]">
        {fromParticipant && (
          <div className="shrink-0">
            <img
              src={fromParticipant.chara.defaultImageUrl}
              width={fromParticipant.chara.imageWidth}
              height={fromParticipant.chara.imageHeight}
              alt=""
              loading="lazy"
              style={{
                maxWidth: 60,
                maxHeight: 60,
                width: "auto",
                height: "auto",
              }}
            />
          </div>
        )}
        <div
          className="message-bubble flex-1"
          style={{
            backgroundColor: style.bg,
            color: style.color,
            border: `1px solid ${style.border}`,
            padding: "0.75em",
            borderRadius: "0.42em",
            fontSize: "1em",
            wordBreak: "break-word",
            fontFamily: "sans-serif",
          }}
        >
          {content}
        </div>
      </div>
      {message.number != null && (
        <div className="text-[0.85em] mt-1 flex justify-end gap-3">
          <button
            type="button"
            onClick={handleReplyClick}
            className="message-link hover:underline"
            title={isSecret ? "秘話で返信" : "返信"}
          >
            &gt;&gt;{isSecret ? "秘話" : "返信"}
          </button>
        </div>
      )}
    </article>
  );
}

type MessageKind = "say" | "system";

type MessageStyle = {
  kind: MessageKind;
  bg: string;
  color: string;
  border: string;
  anchorPrefix: string;
  fallbackFrom: string;
};

const DEFAULT_STYLE: MessageStyle = {
  kind: "say",
  bg: "#ffffff",
  color: "#555555",
  border: "#e3e3e3",
  anchorPrefix: ">>",
  fallbackFrom: "?",
};

const MESSAGE_STYLES: Record<string, MessageStyle> = {
  NORMAL_SAY: { kind: "say", bg: "#ffffff", color: "#555555", border: "#e3e3e3", anchorPrefix: ">>", fallbackFrom: "?" },
  WEREWOLF_SAY: { kind: "say", bg: "#f2aeae", color: "#333333", border: "#f2aeae", anchorPrefix: ">>*", fallbackFrom: "?" },
  MASON_SAY: { kind: "say", bg: "#aef2ae", color: "#333333", border: "#aef2ae", anchorPrefix: ">>=", fallbackFrom: "?" },
  LOVERS_SAY: { kind: "say", bg: "#f2cece", color: "#cc2222", border: "#f2cece", anchorPrefix: ">>?", fallbackFrom: "?" },
  TELEPATHY: { kind: "say", bg: "#f2f2ae", color: "#cc2200", border: "#f2f2ae", anchorPrefix: ">>_", fallbackFrom: "?" },
  MONOLOGUE_SAY: { kind: "say", bg: "#aaaaaa", color: "#333333", border: "#b5b5b5", anchorPrefix: ">>-", fallbackFrom: "?" },
  SECRET_SAY: { kind: "say", bg: "#aa99aa", color: "#333333", border: "#b5b5b5", anchorPrefix: ">>s", fallbackFrom: "?" },
  GRAVE_SAY: { kind: "say", bg: "#a9edf7", color: "#333333", border: "#a9edf7", anchorPrefix: ">>+", fallbackFrom: "?" },
  SPECTATE_SAY: { kind: "say", bg: "#ffdea9", color: "#333333", border: "#ffdea9", anchorPrefix: ">>@", fallbackFrom: "?" },
  CREATOR_SAY: { kind: "say", bg: "transparent", color: "#00bc8c", border: "#00bc8c", anchorPrefix: ">>#", fallbackFrom: "天からのお告げ" },
  ACTION: { kind: "say", bg: "#232355", color: "#ffffff", border: "#232355", anchorPrefix: ">>a", fallbackFrom: "" },
  PUBLIC_SYSTEM: makeSystem("transparent", "#ffffff", "transparent"),
  PRIVATE_SYSTEM: makeSystem("#333333", "#eeeeee", "#cccccc"),
  PRIVATE_SEER: makeSystem("#334033", "#eeeeee", "#34a865"),
  PRIVATE_WISE: makeSystem("#334033", "#eeeeee", "#34a865"),
  PRIVATE_PSYCHIC: makeSystem("#333340", "#eeeeee", "#3465a8"),
  PRIVATE_GURU: makeSystem("#333340", "#eeeeee", "#3465a8"),
  PRIVATE_CORONER: makeSystem("#333340", "#eeeeee", "#3465a8"),
  PRIVATE_INVESTIGATE: makeSystem("#403333", "#eeeeee", "#a86534"),
  PRIVATE_WEREWOLF: makeSystem("#403333", "#eeeeee", "#a83434"),
  PRIVATE_LOVER: makeSystem("#403333", "#eeeeee", "#f9318f"),
  PRIVATE_FOX: makeSystem("#403333", "#eeeeee", "#c9c934"),
  PRIVATE_ABILITY: makeSystem("#333333", "#eeeeee", "#cccccc"),
  PARTICIPANTS: makeSystem("transparent", "#ffffff", "transparent"),
};

function makeSystem(bg: string, color: string, border: string): MessageStyle {
  return { kind: "system", bg, color, border, anchorPrefix: "", fallbackFrom: "" };
}

function renderAnchor(prefix: string, num: number | null | undefined): string {
  if (!prefix || num == null) return "";
  return `${prefix}${num}.`;
}

function formatTime(iso: string): string {
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) return iso;
  const hh = String(d.getHours()).padStart(2, "0");
  const mi = String(d.getMinutes()).padStart(2, "0");
  return `${hh}:${mi}`;
}

/**
 * 本文 text を React 要素ツリーに変換する。改行 → <br>, アンカー (`>>N` 系) →
 * .message-link span, 装飾タグ (`[[b]]` `[[s]]` `[[large]]` `[[small]]` `[[#hex]]`
 * `[[netabare]]` `[[cw]]` `[[tp]]` `[[ruby]]`) → 対応要素。
 *
 * 装飾タグの入れ子は旧 JS が逐次 regex 置換していたため非対応 (= 最外側 1 段階のみ)。
 * `isConvertDisable=true` なら装飾タグ変換をスキップし改行 + アンカーのみ適用する。
 */
function renderMessageContent(text: string, isConvertDisable: boolean): React.ReactNode {
  const lines = text.split(/\r\n|\r|\n/);
  const out: React.ReactNode[] = [];
  for (let i = 0; i < lines.length; i++) {
    out.push(
      <React.Fragment key={`l-${i}`}>{parseLine(lines[i], isConvertDisable, `l-${i}`)}</React.Fragment>,
    );
    if (i < lines.length - 1) out.push(<br key={`br-${i}`} />);
  }
  return <>{out}</>;
}

function parseLine(line: string, isConvertDisable: boolean, keyBase: string): React.ReactNode[] {
  const out: React.ReactNode[] = [];
  let remaining = line;
  let idx = 0;
  // text 断片にも key を付ける (React の key 警告予防 + reconciler の最適化が
  // 効くようにするため)。Fragment でラップして key を載せる。
  while (remaining.length > 0) {
    const best = findEarliestMatch(remaining, isConvertDisable);
    if (!best) {
      out.push(<React.Fragment key={`${keyBase}-t${idx}`}>{remaining}</React.Fragment>);
      break;
    }
    if (best.index > 0) {
      const textChunk = remaining.slice(0, best.index);
      out.push(<React.Fragment key={`${keyBase}-t${idx}`}>{textChunk}</React.Fragment>);
      idx++;
    }
    out.push(best.render(`${keyBase}-${idx}`, isConvertDisable));
    idx++;
    remaining = remaining.slice(best.index + best.length);
  }
  return out;
}

type Match = {
  index: number;
  length: number;
  render: (key: string, isConvertDisable: boolean) => React.ReactNode;
};

function findEarliestMatch(s: string, isConvertDisable: boolean): Match | null {
  const candidates: Match[] = [];

  const anchorRegexes = [
    /(>>\*\d{1,5})/,
    /(>>=\d{1,5})/,
    /(>>\?\d{1,5})/,
    /(>>_\d{1,5})/,
    /(>>@\d{1,5})/,
    /(>>-\d{1,5})/,
    /(>>\+\d{1,5})/,
    /(>>#\d{1,5})/,
    /(>>a\d{1,5})/,
    /(>>s\d{1,5})/,
    /(>>\d{1,5})/,
  ];
  // 同じ index に複数パターンがマッチした場合は **マッチ長が長い方** を優先する
  // (例: `>>*12` を `>>\d{1,5}` ではなく `>>\*\d{1,5}` で拾う)。現状の入力では
  // 各プレフィックス文字が digit でないため同一 index で競合しないが、防御的に
  // この不変条件を明示する。
  let earliestAnchor: { idx: number; text: string } | null = null;
  for (const re of anchorRegexes) {
    const m = re.exec(s);
    if (!m) continue;
    if (earliestAnchor == null) {
      earliestAnchor = { idx: m.index, text: m[1] };
      continue;
    }
    if (m.index < earliestAnchor.idx) {
      earliestAnchor = { idx: m.index, text: m[1] };
    } else if (m.index === earliestAnchor.idx && m[1].length > earliestAnchor.text.length) {
      earliestAnchor = { idx: m.index, text: m[1] };
    }
  }
  if (earliestAnchor) {
    const { idx, text } = earliestAnchor;
    candidates.push({
      index: idx,
      length: text.length,
      render: (k) => <span key={k} className="message-link">{text}</span>,
    });
  }

  if (!isConvertDisable) {
    pushTag(/\[\[(#[0-9a-fA-F]{6})\]\]([\s\S]*?)\[\[\/#\]\]/, s, candidates, (m, k, cd) => (
      <span key={k} style={{ color: m[1] }}>{parseLine(m[2], cd, `${k}-i`)}</span>
    ));
    pushTag(/\[\[b\]\]([\s\S]*?)\[\[\/b\]\]/, s, candidates, (m, k, cd) => (
      <strong key={k}>{parseLine(m[1], cd, `${k}-i`)}</strong>
    ));
    pushTag(/\[\[s\]\]([\s\S]*?)\[\[\/s\]\]/, s, candidates, (m, k, cd) => (
      <span key={k} style={{ textDecoration: "line-through" }}>{parseLine(m[1], cd, `${k}-i`)}</span>
    ));
    pushTag(/\[\[large\]\]([\s\S]*?)\[\[\/large\]\]/, s, candidates, (m, k, cd) => (
      <span key={k} style={{ fontSize: "150%" }}>{parseLine(m[1], cd, `${k}-i`)}</span>
    ));
    pushTag(/\[\[small\]\]([\s\S]*?)\[\[\/small\]\]/, s, candidates, (m, k, cd) => (
      <span key={k} style={{ fontSize: "80%" }}>{parseLine(m[1], cd, `${k}-i`)}</span>
    ));
    pushTag(/\[\[ruby\]\]([\s\S]*?)\[\[rt\]\]([\s\S]*?)\[\[\/rt\]\]\[\[\/ruby\]\]/, s, candidates, (m, k) => (
      <ruby key={k}>{m[1]}<rt>{m[2]}</rt></ruby>
    ));
    pushTag(/\[\[netabare\]\]([\s\S]*?)\[\[\/netabare\]\]/, s, candidates, (m, k, cd) => (
      <span key={k} className="netabare">{parseLine(m[1], cd, `${k}-i`)}</span>
    ));
    pushTag(/\[\[cw\]\]([\s\S]*?)\[\[\/cw\]\]/, s, candidates, (m, k, cd) => (
      <span key={k} className="netabare">{parseLine(m[1], cd, `${k}-i`)}</span>
    ));
    pushTag(/\[\[tp\]\]([\s\S]*?)\[\[\/tp\]\]/, s, candidates, (m, k, cd) => (
      <span key={k} className="transparency">{parseLine(m[1], cd, `${k}-i`)}</span>
    ));
  }

  if (candidates.length === 0) return null;
  candidates.sort((a, b) => a.index - b.index);
  return candidates[0];
}

function pushTag(
  re: RegExp,
  s: string,
  candidates: Match[],
  render: (m: RegExpExecArray, key: string, isConvertDisable: boolean) => React.ReactNode,
) {
  const m = re.exec(s);
  if (!m) return;
  candidates.push({
    index: m.index,
    length: m[0].length,
    render: (k, cd) => render(m, k, cd),
  });
}
