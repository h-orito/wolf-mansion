import type { MouseEvent } from "react";

import type { VillageMessageContent } from "~/features/village/api";
import { MessageType } from "./messageType";
import { type ReplyDraft, SAY_VARIANTS, bubbleClass, formatMessageTime } from "./message";
import { StableHtml } from "./StableHtml";
import { UserPageLink } from "./UserPageLink";

export function SayMessage({
  message,
  html,
  onContentClick,
  copyAnchor,
  spoiled,
  imageScale,
  onReply,
  onSecret,
}: {
  message: VillageMessageContent;
  html: string;
  onContentClick: (e: MouseEvent<HTMLDivElement>) => void;
  copyAnchor: (text: string) => void;
  spoiled: boolean;
  imageScale: number;
  onReply?: (reply: ReplyDraft) => void;
  onSecret?: (reply: ReplyDraft) => void;
}) {
  const type = message.messageType;

  if (message.isBigEars) {
    return <BigEarsMessage message={message} html={html} onContentClick={onContentClick} />;
  }

  const sayVariant = SAY_VARIANTS[type];
  if (sayVariant != null) {
    return (
      <SayBubble
        message={message}
        html={html}
        sayVariant={sayVariant}
        onContentClick={onContentClick}
        copyAnchor={copyAnchor}
        spoiled={spoiled}
        imageScale={imageScale}
        onReply={onReply}
        onSecret={onSecret}
      />
    );
  }

  if (type === MessageType.CREATOR_SAY) {
    return (
      <CreatorSayMessage
        message={message}
        html={html}
        onContentClick={onContentClick}
        copyAnchor={copyAnchor}
      />
    );
  }

  return (
    <ActionMessage
      message={message}
      html={html}
      onContentClick={onContentClick}
      copyAnchor={copyAnchor}
      spoiled={spoiled}
    />
  );
}

function BigEarsMessage({
  message,
  html,
  onContentClick,
}: {
  message: VillageMessageContent;
  html: string;
  onContentClick: (e: MouseEvent<HTMLDivElement>) => void;
}) {
  return (
    <div className="mb-[15px]">
      <div className="text-village-sm">
        <span>地獄耳</span>
        <span className="ml-[5px]">{formatMessageTime(message.messageDatetime)}</span>
      </div>
      <div className="flex">
        <div className="h-[77px] w-[50px] rounded-[5px] border border-white" />
        <StableHtml
          className={`ml-[5px] flex-1 ${bubbleClass("message-owl")}`}
          onClick={onContentClick}
          html={html}
        />
      </div>
    </div>
  );
}

function CreatorSayMessage({
  message,
  html,
  onContentClick,
  copyAnchor,
}: {
  message: VillageMessageContent;
  html: string;
  onContentClick: (e: MouseEvent<HTMLDivElement>) => void;
  copyAnchor: (text: string) => void;
}) {
  const anchorText = message.messageNumber != null ? `>>#${message.messageNumber}` : ">>#";
  return (
    <div>
      <div className="text-village-sm">
        <span>
          <button
            type="button"
            className="text-wm-accent cursor-pointer hover:underline"
            onClick={() => copyAnchor(anchorText)}
          >
            {anchorText}
          </button>
          .&nbsp;天からのお告げ
        </span>
        <span className="ml-[5px]">{formatMessageTime(message.messageDatetime)}</span>
      </div>
      <StableHtml
        className={`mb-[20px] ${bubbleClass("message-creator")}`}
        onClick={onContentClick}
        html={html}
      />
    </div>
  );
}

function ActionMessage({
  message,
  html,
  onContentClick,
  copyAnchor,
  spoiled,
}: {
  message: VillageMessageContent;
  html: string;
  onContentClick: (e: MouseEvent<HTMLDivElement>) => void;
  copyAnchor: (text: string) => void;
  spoiled: boolean;
}) {
  const anchorText = message.messageNumber != null ? `>>a${message.messageNumber}` : ">>a";
  return (
    <div>
      <div className="text-village-sm">
        <span>
          <button
            type="button"
            className="text-wm-accent cursor-pointer hover:underline"
            onClick={() => copyAnchor(anchorText)}
          >
            {anchorText}
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
      <StableHtml
        className={`mb-[20px] ${bubbleClass("message-action")}`}
        onClick={onContentClick}
        html={html}
      />
    </div>
  );
}

function SayBubble({
  message,
  html,
  sayVariant,
  onContentClick,
  copyAnchor,
  spoiled,
  imageScale,
  onReply,
  onSecret,
}: {
  message: VillageMessageContent;
  html: string;
  sayVariant: { anchorPrefix: string; styleKey: string; decoratable: boolean };
  onContentClick: (e: MouseEvent<HTMLDivElement>) => void;
  copyAnchor: (text: string) => void;
  spoiled: boolean;
  imageScale: number;
  onReply?: (reply: ReplyDraft) => void;
  onSecret?: (reply: ReplyDraft) => void;
}) {
  const type = message.messageType;
  const alwaysShowAnchor = type !== MessageType.MONOLOGUE_SAY && type !== MessageType.SECRET_SAY;
  const anchorText =
    message.messageNumber != null
      ? `${sayVariant.anchorPrefix}${message.messageNumber}`
      : alwaysShowAnchor
        ? sayVariant.anchorPrefix
        : null;
  const loud = sayVariant.decoratable && message.isLoud;
  const rainbow = sayVariant.decoratable && message.isRainbow;
  const hasButtons =
    (message.canReply && onReply != null) ||
    (message.canSecret && onSecret != null && message.characterId != null);

  return (
    <div className={hasButtons ? "" : "mb-[20px]"}>
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
          {type === MessageType.SECRET_SAY && ` → ${message.targetCharacterName ?? ""}`}
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
        <div>
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
          onClick={onContentClick}
        >
          {rainbow ? <StableHtml className="rainbow" html={html} /> : <StableHtml html={html} />}
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
                  type === MessageType.SECRET_SAY
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
