import { type MouseEvent, useMemo, useState } from "react";

import type { components } from "~/api/types";
import { fetchAnchorMessage, type VillageMessageContent } from "~/features/village/api";
import { useDisplaySettings } from "~/features/village/displaySettings";
import { SAY_TYPES, SPOILED_TYPES } from "./messageType";
import { type ReplyDraft, replaceIdLink, toMessageHtml } from "./message";
import { SayMessage } from "./SayMessage";
import { SystemMessage } from "./SystemMessage";

export type { ReplyDraft } from "./message";
export { UserPageLink } from "./UserPageLink";

type ExpandedAnchor = {
  key: string;
  message: VillageMessageContent;
  visible: boolean;
};

export function MessageCard({
  villageId,
  message,
  randomKeywords,
  spoiled = false,
  allParticipants,
  onHashtagClick,
  onReply,
  onSecret,
}: {
  villageId: number;
  message: VillageMessageContent;
  randomKeywords: string[];
  spoiled?: boolean;
  allParticipants?: components["schemas"]["VillageParticipantView"][];
  onHashtagClick?: (tag: string) => void;
  onReply?: (reply: ReplyDraft) => void;
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

  if (spoiled && (message.isBigEars || SPOILED_TYPES.has(message.messageType))) {
    return null;
  }

  const body =
    message.isBigEars || SAY_TYPES.has(message.messageType) ? (
      <SayMessage
        message={message}
        html={html}
        onContentClick={onContentClick}
        copyAnchor={copyAnchor}
        spoiled={spoiled}
        imageScale={imageScale}
        onReply={onReply}
        onSecret={onSecret}
      />
    ) : (
      <SystemMessage
        message={message}
        html={html}
        onContentClick={onContentClick}
        allParticipants={allParticipants}
      />
    );

  return (
    <div>
      {body}
      {expandedAnchors
        .filter((a) => a.visible)
        .map((a) => (
          <div
            key={a.key}
            className="mb-[10px] rounded border border-[#464545] bg-[#303030] p-[10px]"
          >
            <div className="flex justify-end">
              <button
                type="button"
                className="cursor-pointer rounded border border-[#464545] bg-[#464545] px-[8px] py-[2px] text-white"
                onClick={() =>
                  setExpandedAnchors((prev) =>
                    prev.map((x) => (x.key === a.key ? { ...x, visible: false } : x)),
                  )
                }
              >
                ×
              </button>
            </div>
            <div className="[&>div]:mb-0">
              <MessageCard
                villageId={villageId}
                message={a.message}
                randomKeywords={randomKeywords}
              />
            </div>
          </div>
        ))}
    </div>
  );
}
