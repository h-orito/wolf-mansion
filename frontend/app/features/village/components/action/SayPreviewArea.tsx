import { Button } from "~/components/ui/Button";
import { MessageCard } from "~/features/village/components/message/MessageCard";
import { sayLabel } from "./SayPanel";
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

function determineLabel(preview: SayPreview): string {
  if (preview.kind === "action") return "アクション";
  if (preview.kind === "creatorSay") return "発言する（村建て）";
  return sayLabel(preview.request.messageType);
}

export function SayPreviewArea({
  preview,
  randomKeywords,
  submitting,
  onDetermine,
  onCancel,
}: {
  preview: SayPreview | null;
  randomKeywords: string[];
  submitting: boolean;
  onDetermine: () => void;
  onCancel: () => void;
}) {
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
