import type { MessageView, VillageParticipantView } from "./api";

/**
 * 1 発言ぶんのカード。`MessageView.typeCode` に応じて色 / 番号プレフィックスを出し分け、
 * 発言者キャラ画像とプレイヤー名 (エピローグ以降のみ backend が返す) を表示する。
 *
 * 旧 Thymeleaf 版 `.old-thymeleaf/templates/village-template/message-partial.html` の
 * 表示種別 (NORMAL_SAY / WEREWOLF_SAY / MASON_SAY / LOVERS_SAY / MONOLOGUE_SAY /
 * SECRET_SAY / GRAVE_SAY / SPECTATE_SAY / CREATOR_SAY / TELEPATHY / ACTION / 各種
 * PRIVATE_* / PUBLIC_SYSTEM / PARTICIPANTS) を一通りカバーする。表情差分 / 大声 /
 * 虹色 / 返信 / 秘話 ボタンは Step 12d で扱う。
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

  // PUBLIC_SYSTEM / PRIVATE_* / PARTICIPANTS 等は番号も発言者も持たない単純な
  // システム枠で出す (旧画面 message-public-system / message-private-* に対応)。
  if (style.kind === "system") {
    return (
      <article
        className={`rounded border px-3 py-2 text-sm whitespace-pre-wrap ${style.body}`}
        data-message-type={message.typeCode}
      >
        {message.text}
      </article>
    );
  }

  const anchor = renderAnchor(style.anchorPrefix, message.number);

  return (
    <article
      className={`rounded border ${style.frame}`}
      data-message-type={message.typeCode}
    >
      <header className="px-3 pt-2 text-xs text-slate-400 flex flex-wrap items-center gap-x-2 gap-y-0.5">
        {anchor && <span className="font-mono">{anchor}</span>}
        <span className="text-slate-200">{message.fromCharaName ?? style.fallbackFrom}</span>
        {message.toCharaName && (
          <span className="text-slate-400">→ {message.toCharaName}</span>
        )}
        {fromParticipant?.playerName && (
          <span className="text-slate-500">[{fromParticipant.playerName}]</span>
        )}
        <span className="text-slate-500 ml-auto">{formatTime(message.datetime)}</span>
      </header>
      <div className="px-3 pb-2 pt-1 flex gap-2">
        {fromParticipant && (
          <img
            src={fromParticipant.chara.defaultImageUrl}
            width={fromParticipant.chara.imageWidth}
            height={fromParticipant.chara.imageHeight}
            alt=""
            loading="lazy"
            className="shrink-0 rounded border border-slate-700"
            style={{
              maxWidth: 60,
              maxHeight: 60,
              width: "auto",
              height: "auto",
            }}
          />
        )}
        {/* React JSX は子要素の文字列を textContent としてレンダリングするため、
            `message.text` を直接埋め込んでも XSS にはならない。旧 Thymeleaf の
            `{{{messageContent}}}` (HTML 非エスケープ) に相当する HTML 装飾
            (大声 / 虹色 / アンカー link) は Step 12d で安全に再実装する。 */}
        <p className={`flex-1 text-sm whitespace-pre-wrap ${style.text}`}>{message.text}</p>
      </div>
    </article>
  );
}

// ---------- styles ----------

type MessageKind = "say" | "system";

type MessageStyle = {
  kind: MessageKind;
  /** カード枠 (kind=say のときに使用) */
  frame: string;
  /** 本文文字色 (kind=say のときに使用) */
  text: string;
  /** システム枠の background+border (kind=system のときに使用) */
  body: string;
  /** >>N のような番号プレフィックス記号 (旧画面のアンカー記法に揃える) */
  anchorPrefix: string;
  /** fromCharaName が null のときに表示するフォールバック (主に creator) */
  fallbackFrom: string;
};

const DEFAULT_STYLE: MessageStyle = {
  kind: "say",
  frame: "bg-slate-800/40 border-slate-700",
  text: "text-slate-100",
  body: "",
  anchorPrefix: ">>",
  fallbackFrom: "?",
};

// 旧 .old-thymeleaf/templates/village-template/message-partial.html の anchor 記法と
// 配色を Tailwind に移植したもの。背景は slate ベースに寄せて統一感を出す。
const MESSAGE_STYLES: Record<string, MessageStyle> = {
  NORMAL_SAY: {
    kind: "say",
    frame: "bg-slate-800/50 border-slate-600",
    text: "text-slate-100",
    body: "",
    anchorPrefix: ">>",
    fallbackFrom: "?",
  },
  WEREWOLF_SAY: {
    kind: "say",
    frame: "bg-rose-950/40 border-rose-700",
    text: "text-rose-100",
    body: "",
    anchorPrefix: ">>*",
    fallbackFrom: "?",
  },
  MASON_SAY: {
    kind: "say",
    frame: "bg-violet-950/40 border-violet-700",
    text: "text-violet-100",
    body: "",
    anchorPrefix: ">>=",
    fallbackFrom: "?",
  },
  LOVERS_SAY: {
    kind: "say",
    frame: "bg-pink-950/40 border-pink-700",
    text: "text-pink-100",
    body: "",
    anchorPrefix: ">>?",
    fallbackFrom: "?",
  },
  MONOLOGUE_SAY: {
    kind: "say",
    frame: "bg-slate-900/60 border-slate-700",
    text: "text-slate-300 italic",
    body: "",
    anchorPrefix: ">>-",
    fallbackFrom: "?",
  },
  SECRET_SAY: {
    kind: "say",
    frame: "bg-amber-950/40 border-amber-700",
    text: "text-amber-100",
    body: "",
    anchorPrefix: ">>s",
    fallbackFrom: "?",
  },
  GRAVE_SAY: {
    kind: "say",
    frame: "bg-zinc-900/70 border-zinc-700",
    text: "text-zinc-300",
    body: "",
    anchorPrefix: ">>+",
    fallbackFrom: "?",
  },
  SPECTATE_SAY: {
    kind: "say",
    frame: "bg-sky-950/40 border-sky-700",
    text: "text-sky-100",
    body: "",
    anchorPrefix: ">>@",
    fallbackFrom: "?",
  },
  CREATOR_SAY: {
    kind: "say",
    frame: "bg-yellow-950/40 border-yellow-700",
    text: "text-yellow-100",
    body: "",
    anchorPrefix: ">>#",
    fallbackFrom: "天からのお告げ",
  },
  TELEPATHY: {
    kind: "say",
    frame: "bg-indigo-950/40 border-indigo-700",
    text: "text-indigo-100",
    body: "",
    anchorPrefix: ">>_",
    fallbackFrom: "?",
  },
  ACTION: {
    kind: "say",
    frame: "bg-slate-800/30 border-slate-700",
    text: "text-slate-300 italic",
    body: "",
    anchorPrefix: ">>a",
    fallbackFrom: "",
  },
  // システム / 個別役職向け private 系: 番号もキャラ画像も持たない一行枠
  PUBLIC_SYSTEM: makeSystem("bg-slate-700/30 border-slate-600 text-slate-100"),
  PRIVATE_SYSTEM: makeSystem("bg-slate-800/50 border-slate-600 text-slate-200"),
  PRIVATE_SEER: makeSystem("bg-emerald-950/40 border-emerald-700 text-emerald-100"),
  PRIVATE_WISE: makeSystem("bg-emerald-950/40 border-emerald-700 text-emerald-100"),
  PRIVATE_PSYCHIC: makeSystem("bg-teal-950/40 border-teal-700 text-teal-100"),
  PRIVATE_GURU: makeSystem("bg-teal-950/40 border-teal-700 text-teal-100"),
  PRIVATE_CORONER: makeSystem("bg-teal-950/40 border-teal-700 text-teal-100"),
  PRIVATE_INVESTIGATE: makeSystem("bg-cyan-950/40 border-cyan-700 text-cyan-100"),
  PRIVATE_WEREWOLF: makeSystem("bg-rose-950/40 border-rose-700 text-rose-100"),
  PRIVATE_LOVER: makeSystem("bg-pink-950/40 border-pink-700 text-pink-100"),
  PRIVATE_FOX: makeSystem("bg-orange-950/40 border-orange-700 text-orange-100"),
  PRIVATE_ABILITY: makeSystem("bg-slate-800/60 border-slate-600 text-slate-200"),
  PARTICIPANTS: makeSystem("bg-slate-700/30 border-slate-600 text-slate-100"),
};

function makeSystem(body: string): MessageStyle {
  return {
    kind: "system",
    frame: "",
    text: "",
    body,
    anchorPrefix: "",
    fallbackFrom: "",
  };
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
