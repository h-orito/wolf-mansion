import { type MouseEvent, useMemo, useState } from "react";
import { Link } from "react-router";

import { DEFAULT_MESSAGE_STYLE, MESSAGE_STYLES } from "~/components/ui/messageStyles";
import { fetchAnchorMessage, type VillageMessageContent } from "~/features/village/api";
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
}: {
  villageId: number;
  message: VillageMessageContent;
  randomKeywords: string[];
}) {
  const [expandedAnchors, setExpandedAnchors] = useState<ExpandedAnchor[]>([]);

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

  // 生成 HTML 内のリンク/装飾はイベント委譲で扱う (アンカー展開・伏せ字解除)
  const onContentClick = (e: MouseEvent<HTMLDivElement>) => {
    const target = e.target as HTMLElement;
    const anchor = target.closest("[data-anchor-type]") as HTMLElement | null;
    if (anchor != null) {
      const type = anchor.dataset.anchorType;
      const number = Number(anchor.dataset.anchorNumber);
      if (type != null && Number.isFinite(number)) toggleAnchor(type, number);
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

  const body = renderBody(message, html, onContentClick, copyAnchor, villageId);

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
) {
  const type = message.messageType;

  // 梟の地獄耳: 発言者を伏せた特殊表示
  if (message.isBigEars) {
    return (
      <div>
        <div className="text-[10.32px]">
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
    const anchorText =
      message.messageNumber != null ? `${sayVariant.anchorPrefix}${message.messageNumber}` : null;
    const loud = sayVariant.decoratable && message.isLoud;
    const rainbow = sayVariant.decoratable && message.isRainbow;
    return (
      <div>
        <div className="text-[10.32px]">
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
            {message.playerName != null && (
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
                width={message.width ?? undefined}
                height={message.height ?? undefined}
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
      </div>
    );
  }

  if (type === "CREATOR_SAY") {
    return (
      <div>
        <div className="text-[10.32px]">
          <span>
            {message.messageNumber != null && (
              <>
                <button
                  type="button"
                  className="text-wm-accent cursor-pointer hover:underline"
                  onClick={() => copyAnchor(`>>#${message.messageNumber}`)}
                >
                  {`>>#${message.messageNumber}`}
                </button>
                .&nbsp;
              </>
            )}
            天からのお告げ
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
    return (
      <div>
        <div className="text-[10.32px]">
          <span>
            {message.messageNumber != null && (
              <>
                <button
                  type="button"
                  className="text-wm-accent cursor-pointer hover:underline"
                  onClick={() => copyAnchor(`>>a${message.messageNumber}`)}
                >
                  {`>>a${message.messageNumber}`}
                </button>
                .
              </>
            )}
            {message.playerName != null && (
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
