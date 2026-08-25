import { useEffect, useRef } from "react";

import type { VillageParticipantView } from "~/features/village/api";
import type { ParticipantMemo } from "~/features/village/analyzer/types";
import { Button } from "~/components/ui/Button";
import { Modal } from "~/components/ui/Modal";
import { ColorPicker } from "./ColorPicker";

export function ParticipantMemoModal({
  participant,
  memo,
  onChange,
  onClose,
}: {
  participant: VillageParticipantView | null;
  memo: ParticipantMemo | null;
  onChange: (id: number, memo: ParticipantMemo) => void;
  onClose: () => void;
}) {
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    if (participant && textareaRef.current) {
      textareaRef.current.focus();
    }
  }, [participant]);

  const update = (partial: Partial<ParticipantMemo>) => {
    if (!participant || !memo) return;
    onChange(participant.id, { ...memo, ...partial });
  };

  return (
    <Modal
      open={participant != null && memo != null}
      onClose={onClose}
      title={participant?.name ?? ""}
    >
      <div className="mb-[10px] flex items-center gap-[8px]">
        <label className=" text-gray-300">表示色</label>
        <ColorPicker value={memo?.color ?? "ffffff"} onChange={(color) => update({ color })} />
      </div>
      <textarea
        ref={textareaRef}
        value={memo?.memo ?? ""}
        onChange={(e) => update({ memo: e.target.value })}
        rows={5}
        className="mb-[10px] w-full rounded border border-border bg-surface p-[8px] text-white"
        placeholder="メモを入力..."
      />
      <div className="flex justify-end">
        <Button variant="default" onClick={onClose}>
          閉じる
        </Button>
      </div>
    </Modal>
  );
}
