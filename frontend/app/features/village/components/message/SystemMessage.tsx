import { type MouseEvent, useMemo } from "react";

import type { VillageMessageContent } from "~/features/village/api";
import { useVillageContext } from "~/features/village/VillageContext";
import { ParticipantsTable } from "../info/ParticipantsTable";
import { MessageType } from "./messageType";
import { SYSTEM_VARIANTS, bubbleClass } from "./message";
import { StableHtml } from "./StableHtml";

export function SystemMessage({
  message,
  html,
  onContentClick,
}: {
  message: VillageMessageContent;
  html: string;
  onContentClick: (e: MouseEvent<HTMLDivElement>) => void;
}) {
  const village = useVillageContext();
  const sortedParticipants = useMemo(() => {
    return [...(village.participants.list ?? []), ...(village.spectators.list ?? [])].sort(
      (a, b) =>
        Number(a.isSpectator) - Number(b.isSpectator) ||
        (a.room?.number ?? 0) - (b.room?.number ?? 0) ||
        a.charaId - b.charaId,
    );
  }, [village.participants.list, village.spectators.list]);

  if (message.messageType === MessageType.PARTICIPANTS && sortedParticipants.length > 0) {
    return (
      <div className={`mb-[20px] ${bubbleClass("message-public-system")}`}>
        <ParticipantsTable participants={sortedParticipants} />
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
