import { Button } from "~/components/ui/Button";
import { useRandomKeywords } from "~/features/random-keywords/useRandomKeywords";
import { MessageCard } from "~/features/village/components/message/MessageCard";
import { MessageType } from "~/features/village/components/message/messageType";
import type {
  VillageActionRequest,
  VillageCreatorSayRequest,
  VillageMessageContent,
  VillageSayRequest,
} from "~/features/village/api";

export type SayPreview =
  | { kind: "say"; message: VillageMessageContent; request: VillageSayRequest }
  | { kind: "action"; message: VillageMessageContent; request: VillageActionRequest }
  | { kind: "creatorSay"; message: VillageMessageContent; request: VillageCreatorSayRequest };

function sayLabel(messageType: string | null | undefined): string {
  switch (messageType) {
    case MessageType.WEREWOLF_SAY:
      return "発言する（囁き）";
    case MessageType.MASON_SAY:
      return "発言する（共鳴）";
    case MessageType.LOVERS_SAY:
      return "発言する（恋人）";
    case MessageType.TELEPATHY:
      return "発言する（念話）";
    case MessageType.MONOLOGUE_SAY:
      return "発言する（独り言）";
    case MessageType.SECRET_SAY:
      return "発言する（秘話）";
    case MessageType.GRAVE_SAY:
      return "呻く";
    default:
      return "発言する";
  }
}

function determineLabel(preview: SayPreview): string {
  if (preview.kind === "action") return "アクション";
  if (preview.kind === "creatorSay") return "発言する（村建て）";
  return sayLabel(preview.request.messageType);
}

export function SayPreviewArea({
  preview,
  submitting,
  onDetermine,
  onCancel,
}: {
  preview: SayPreview | null;
  submitting: boolean;
  onDetermine: () => void;
  onCancel: () => void;
}) {
  const { data: randomKeywordsData } = useRandomKeywords();
  const randomKeywords = (randomKeywordsData ?? []).map((k) => k.keyword ?? "").filter(Boolean);
  if (preview == null) return null;
  return (
    <div
      id="message-confirm-area"
      className="mb-[20px] rounded border border-[#ffff00] bg-[#303030] p-[10px]"
    >
      <p className="mb-[10px]">以下の内容で発言してよろしいですか？（まだ発言されていません）</p>
      <MessageCard message={preview.message} randomKeywords={randomKeywords} />
      <div className="flex justify-end gap-[10px]">
        <Button variant="default" onClick={onCancel}>
          キャンセル
        </Button>
        <Button onClick={onDetermine} disabled={submitting}>
          {determineLabel(preview)}
        </Button>
      </div>
    </div>
  );
}
