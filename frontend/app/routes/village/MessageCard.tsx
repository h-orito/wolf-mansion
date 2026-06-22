import { type MouseEvent, useMemo, useState } from "react";
import { Link } from "react-router";

import { DEFAULT_MESSAGE_STYLE, MESSAGE_STYLES } from "~/components/ui/messageStyles";
import { fetchAnchorMessage, type VillageMessageContent } from "~/features/village/api";
import { useDisplaySettings } from "~/features/village/displaySettings";
import { formatMessageTime, replaceIdLink, toMessageHtml } from "./messageHtml";
import { ParticipantsTable } from "./ParticipantsTable";

/** 発言系メッセージの種別ごとの表示定義。アンカー接頭辞と装飾 (拡声/虹塗り) の対象か。 */
const SAY_VARIANTS: Record<
  string,
  { anchorPrefix: string; styleKey: string; decoratable: boolean }
> = {
  NORMAL_SAY: { anchorPrefix: ">>", styleKey: "message-normal", decoratable: true },
  WEREWOLF_SAY: { anchorPrefix: ">>*", styleKey: "message-werewolf", decoratable: true },
  MONOLOGUE_SAY: { anchorPrefix: ">>-", styleKey: "message-monologue", decoratable: false },
  SECRET_SAY: { anchorPrefix: ">>s", styleKey: "message-secret", decoratable: false },
  MASON_SAY: { anchorPrefix: ">>=", styleKey: "message-mason", decoratable: true },
  LOVERS_SAY: { anchorPrefix: ">>?", styleKey: "message-lover", decoratable: true },
  TELEPATHY: { anchorPrefix: ">>_", styleKey: "message-telepathy", decoratable: true },
  GRAVE_SAY: { anchorPrefix: ">>+", styleKey: "message-grave", decoratable: true },
  SPECTATE_SAY: { anchorPrefix: ">>@", styleKey: "message-spectate", decoratable: false },
};

/** システム系メッセージの種別 → 配色キー。 */
const SYSTEM_VARIANTS: Record<string, string> = {
  PUBLIC_SYSTEM: "message-public-system",
  PRIVATE_SYSTEM: "message-private-system",
  PRIVATE_SEER: "message-private-seer",
  PRIVATE_WISE: "message-private-seer",
  PRIVATE_PSYCHIC: "message-private-psychic",
  PRIVATE_GURU: "message-private-psychic",
  PRIVATE_CORONER: "message-private-psychic",
  PRIVATE_INVESTIGATE: "message-private-investigate",
  PRIVATE_WEREWOLF: "message-private-werewolf",
  PRIVATE_LOVER: "message-private-lover",
  PRIVATE_FOX: "message-private-fox",
  PRIVATE_ABILITY: "message-private-ability",
};

/** ネタバレ防止の対象種別 (エピローグ前は見えなかったもの)。 */
const SPOILED_TYPES = new Set([
  "WEREWOLF_SAY",
  "MONOLOGUE_SAY",
  "SECRET_SAY",
  "MASON_SAY",
  "LOVERS_SAY",
  "TELEPATHY",
  "GRAVE_SAY",
  "SPECTATE_SAY",
  "PRIVATE_SYSTEM",
  "PRIVATE_SEER",
  "PRIVATE_WISE",
  "PRIVATE_PSYCHIC",
  "PRIVATE_GURU",
  "PRIVATE_CORONER",
  "PRIVATE_INVESTIGATE",
  "PRIVATE_WEREWOLF",
  "PRIVATE_LOVER",
  "PRIVATE_FOX",
  "PRIVATE_ABILITY",
]);

const bubbleBaseClass = "message rounded-[5px] border p-[9px] break-words";

// 種別クラス (message-normal 等) も付ける。本文内リンクの色分け CSS が参照する
function bubbleClass(styleKey: string): string {
  return `${bubbleBaseClass} ${styleKey} ${MESSAGE_STYLES[styleKey] ?? DEFAULT_MESSAGE_STYLE}`;
}

type ExpandedAnchor = {
  key: string;
  message: VillageMessageContent;
  visible: boolean;
};

/** 返信・秘話返信で発言フォームへ引き継ぐ内容。 */
export type ReplyDraft = {
  /** 本文へ挿入するアンカー (秘話返信は null) */
  anchorText: string | null;
  /** 秘話返信なら宛先のキャラ ID */
  secretTargetCharaId: number | null;
  /** 引用表示する元発言 */
  message: VillageMessageContent;
};

/** プレイヤーのプロフィールページへの別タブリンク。 */
export function UserPageLink({ name }: { name: string }) {
  return (
    <Link
      to={`/user/${encodeURIComponent(name)}`}
      target="_blank"
      className="text-wm-accent cursor-pointer hover:underline"
    >
      {name}
    </Link>
  );
}

/**
 * 発言ログの 1 メッセージ。本文中のアンカークリックで該当発言をこのカードの直下に
 * インライン展開する (展開済みは表示/非表示のトグル)。
 */
export function MessageCard({
  villageId,
  message,
  randomKeywords,
  spoiled = false,
  onHashtagClick,
  onReply,
  onSecret,
}: {
  villageId: number;
  message: VillageMessageContent;
  randomKeywords: string[];
  /** ネタバレ防止 (エピローグ前同等の表示)。対象種別の発言とプレイヤー名を隠す */
  spoiled?: boolean;
  onHashtagClick?: (tag: string) => void;
  /** 返信 (アンカー挿入 + 引用)。未指定なら返信リンクを出さない */
  onReply?: (reply: ReplyDraft) => void;
  /** 秘話返信 (宛先セット + 引用)。未指定なら秘話リンクを出さない */
  onSecret?: (reply: ReplyDraft) => void;
}) {
  const [expandedAnchors, setExpandedAnchors] = useState<ExpandedAnchor[]>([]);
  const largeImage = useDisplaySettings((s) => s.largeImage);
  const imageScale = largeImage ? 2 : 1;

  const html = useMemo(() => {
    const converted = toMessageHtml(
      message.messageContent,
      message.isConvertDisable ?? false,
      randomKeywords,
    );
    return replaceIdLink(message.messageType, converted);
  }, [message, randomKeywords]);

  const toggleAnchor = async (type: string, number: number) => {
    const key = `${type}_${number}`;
    const existing = expandedAnchors.find((a) => a.key === key);
    if (existing) {
      setExpandedAnchors((prev) =>
        prev.map((a) => (a.key === key ? { ...a, visible: !a.visible } : a)),
      );
      return;
    }
    try {
      const response = await fetchAnchorMessage(villageId, type, number);
      if (response.message == null) return;
      const anchorMessage = response.message;
      setExpandedAnchors((prev) => [...prev, { key, message: anchorMessage, visible: true }]);
    } catch {
      // 取得できないアンカー (閲覧不可など) は何もしない
    }
  };

  // 生成 HTML 内のリンク/装飾はイベント委譲で扱う (アンカー展開・ハッシュタグ抽出・伏せ字解除)
  const onContentClick = (e: MouseEvent<HTMLDivElement>) => {
    const target = e.target as HTMLElement;
    const anchor = target.closest("[data-anchor-type]") as HTMLElement | null;
    if (anchor != null) {
      const type = anchor.dataset.anchorType;
      const number = Number(anchor.dataset.anchorNumber);
      if (type != null && Number.isFinite(number)) toggleAnchor(type, number);
      return;
    }
    const hashtag = target.closest("[data-message-hashtag]") as HTMLElement | null;
    if (hashtag != null) {
      const tag = hashtag.dataset.messageHashtag ?? "";
      if (tag !== "" && onHashtagClick != null) onHashtagClick(tag);
      return;
    }
    if (target.classList.contains("netabare")) {
      target.classList.remove("netabare");
      return;
    }
    if (target.classList.contains("transparency")) {
      target.classList.remove("transparency");
    }
  };

  const copyAnchor = (text: string) => {
    navigator.clipboard.writeText(text).then(() => alert(`コピーしました： ${text}`));
  };

  // ネタバレ防止中はスポイラー対象の発言そのものを出さない
  if (spoiled && (message.isBigEars || SPOILED_TYPES.has(message.messageType))) {
    return null;
  }

  const body = renderBody(
    message,
    html,
    onContentClick,
    copyAnchor,
    villageId,
    spoiled,
    imageScale,
    onReply,
    onSecret,
  );

  return (
    <div className="mb-[20px]">
      {body}
      {expandedAnchors
        .filter((a) => a.visible)
        .map((a) => (
          <div
            key={a.key}
            className="relative mb-[10px] rounded border border-[#464545] bg-[#303030] p-[10px]"
          >
            <button
              type="button"
              className="absolute top-[5px] right-[5px] cursor-pointer rounded border border-[#464545] bg-[#464545] px-[8px] py-[2px] text-white"
              onClick={() =>
                setExpandedAnchors((prev) =>
                  prev.map((x) => (x.key === a.key ? { ...x, visible: false } : x)),
                )
              }
            >
              ×
            </button>
            <MessageCard
              villageId={villageId}
              message={a.message}
              randomKeywords={randomKeywords}
            />
          </div>
        ))}
    </div>
  );
}

function renderBody(
  message: VillageMessageContent,
  html: string,
  onContentClick: (e: MouseEvent<HTMLDivElement>) => void,
  copyAnchor: (text: string) => void,
  villageId: number,
  spoiled: boolean,
  imageScale: number,
  onReply?: (reply: ReplyDraft) => void,
  onSecret?: (reply: ReplyDraft) => void,
) {
  const type = message.messageType;

  // 梟の地獄耳: 発言者を伏せた特殊表示
  if (message.isBigEars) {
    return (
      <div>
        <div className="text-village-sm">
          <span>地獄耳</span>
          <span className="ml-[5px]">{formatMessageTime(message.messageDatetime)}</span>
        </div>
        <div className="flex">
          <div className="h-[77px] w-[50px] rounded-[5px] border border-white" />
          <div
            className={`ml-[5px] flex-1 ${bubbleClass("message-owl")}`}
            onClick={onContentClick}
            dangerouslySetInnerHTML={{ __html: html }}
          />
        </div>
      </div>
    );
  }

  const sayVariant = SAY_VARIANTS[type];
  if (sayVariant != null) {
    const alwaysShowAnchor = type !== "MONOLOGUE_SAY" && type !== "SECRET_SAY";
    const anchorText =
      message.messageNumber != null
        ? `${sayVariant.anchorPrefix}${message.messageNumber}`
        : alwaysShowAnchor
          ? sayVariant.anchorPrefix
          : null;
    const loud = sayVariant.decoratable && message.isLoud;
    const rainbow = sayVariant.decoratable && message.isRainbow;
    return (
      <div>
        <div className="text-village-sm">
          <span>
            {anchorText != null && (
              <>
                <button
                  type="button"
                  className="text-wm-accent cursor-pointer hover:underline"
                  onClick={() => copyAnchor(anchorText)}
                >
                  {anchorText}
                </button>
                .&nbsp;
              </>
            )}
            {message.characterName}
            {type === "SECRET_SAY" && ` → ${message.targetCharacterName ?? ""}`}
            {message.playerName != null && !spoiled && (
              <span>
                &nbsp;[
                <UserPageLink name={message.playerName} />]
              </span>
            )}
          </span>
          <span>&nbsp;{formatMessageTime(message.messageDatetime)}</span>
        </div>
        <div className="flex">
          <div className="mb-[20px]">
            {message.characterImageUrl != null && (
              <img
                src={message.characterImageUrl}
                width={message.width != null ? message.width * imageScale : undefined}
                height={message.height != null ? message.height * imageScale : undefined}
                alt={message.characterName ?? ""}
              />
            )}
          </div>
          <div
            className={`ml-[5px] flex-1 ${bubbleClass(sayVariant.styleKey)} ${loud ? "loud" : ""}`}
            style={message.height != null ? { minHeight: message.height } : undefined}
            onClick={onContentClick}
          >
            {rainbow ? (
              <div className="rainbow" dangerouslySetInnerHTML={{ __html: html }} />
            ) : (
              <div dangerouslySetInnerHTML={{ __html: html }} />
            )}
          </div>
        </div>
        {(message.canReply || message.canSecret) && (onReply != null || onSecret != null) && (
          <div className="flex justify-end gap-[10px]">
            {message.canReply && onReply != null && (
              <button
                type="button"
                className="text-wm-accent cursor-pointer hover:underline"
                onClick={() =>
                  onReply(
                    type === "SECRET_SAY"
                      ? {
                          anchorText: null,
                          secretTargetCharaId: message.characterId ?? null,
                          message,
                        }
                      : { anchorText: anchorText ?? "", secretTargetCharaId: null, message },
                  )
                }
              >
                &gt;&gt;返信
              </button>
            )}
            {message.canSecret && onSecret != null && message.characterId != null && (
              <button
                type="button"
                className="text-wm-accent cursor-pointer hover:underline"
                onClick={() =>
                  onSecret({
                    anchorText: null,
                    secretTargetCharaId: message.characterId ?? null,
                    message,
                  })
                }
              >
                &gt;&gt;秘話
              </button>
            )}
          </div>
        )}
      </div>
    );
  }

  if (type === "CREATOR_SAY") {
    const creatorAnchorText = message.messageNumber != null ? `>>#${message.messageNumber}` : ">>#";
    return (
      <div>
        <div className="text-village-sm">
          <span>
            <button
              type="button"
              className="text-wm-accent cursor-pointer hover:underline"
              onClick={() => copyAnchor(creatorAnchorText)}
            >
              {creatorAnchorText}
            </button>
            .&nbsp;天からのお告げ
          </span>
          <span className="ml-[5px]">{formatMessageTime(message.messageDatetime)}</span>
        </div>
        <div
          className={bubbleClass("message-creator")}
          onClick={onContentClick}
          dangerouslySetInnerHTML={{ __html: html }}
        />
      </div>
    );
  }

  if (type === "ACTION") {
    const actionAnchorText = message.messageNumber != null ? `>>a${message.messageNumber}` : ">>a";
    return (
      <div>
        <div className="text-village-sm">
          <span>
            <button
              type="button"
              className="text-wm-accent cursor-pointer hover:underline"
              onClick={() => copyAnchor(actionAnchorText)}
            >
              {actionAnchorText}
            </button>
            .
            {message.playerName != null && !spoiled && (
              <span>
                &nbsp;[
                <UserPageLink name={message.playerName} />]
              </span>
            )}
          </span>
          <span className="ml-[5px]">{formatMessageTime(message.messageDatetime)}</span>
        </div>
        <div
          className={bubbleClass("message-action")}
          onClick={onContentClick}
          dangerouslySetInnerHTML={{ __html: html }}
        />
      </div>
    );
  }

  if (type === "PARTICIPANTS") {
    return (
      <div className={bubbleClass("message-public-system")}>
        <ParticipantsTable villageId={villageId} />
      </div>
    );
  }

  const systemStyleKey = SYSTEM_VARIANTS[type] ?? "message-public-system";
  return (
    <div
      className={bubbleClass(systemStyleKey)}
      onClick={onContentClick}
      dangerouslySetInnerHTML={{ __html: html }}
    />
  );
}
