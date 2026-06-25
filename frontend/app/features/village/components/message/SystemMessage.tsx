import type { MouseEvent } from "react";

import type { components } from "~/api/types";
import type { VillageMessageContent } from "~/features/village/api";
import { ParticipantsTable } from "../info/ParticipantsTable";
import { MessageType } from "./messageType";
import { SYSTEM_VARIANTS, bubbleClass } from "./message";
import { StableHtml } from "./StableHtml";

export function SystemMessage({
  message,
  html,
  onContentClick,
  allParticipants,
}: {
  message: VillageMessageContent;
  html: string;
  onContentClick: (e: MouseEvent<HTMLDivElement>) => void;
  allParticipants?: components["schemas"]["VillageParticipantView"][];
}) {
  if (message.messageType === MessageType.PARTICIPANTS && allParticipants != null) {
    return (
      <div className={`mb-[20px] ${bubbleClass("message-public-system")}`}>
        <ParticipantsTable participants={allParticipants} />
      </div>
    );
  }

  const systemStyleKey = SYSTEM_VARIANTS[message.messageType] ?? "message-public-system";
  return (
    <StableHtml
      className={`mb-[20px] ${bubbleClass(systemStyleKey)}`}
      onClick={onContentClick}
      html={html}
    />
  );
}
