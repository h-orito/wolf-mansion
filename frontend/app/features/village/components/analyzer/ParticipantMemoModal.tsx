import { useEffect, useRef, useState } from "react";

import type { VillageParticipantView } from "~/features/village/api";
import type { ParticipantMemo } from "~/features/village/analyzer/types";
import { Modal } from "~/components/ui/Modal";

export function ParticipantMemoModal({
  participant,
  memo,
  onSave,
  onClose,
}: {
  participant: VillageParticipantView | null;
  memo: ParticipantMemo | null;
  onSave: (id: number, memo: ParticipantMemo) => void;
  onClose: () => void;
}) {
  const [memoText, setMemoText] = useState("");
  const [color, setColor] = useState("ffffff");
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    if (memo) {
      setMemoText(memo.memo);
      setColor(memo.color);
    }
  }, [memo]);

  useEffect(() => {
    if (participant && textareaRef.current) {
      textareaRef.current.focus();
    }
  }, [participant]);

  const handleSave = () => {
    if (!participant) return;
    onSave(participant.id, {
      participantId: participant.id,
      memo: memoText,
      color,
    });
    onClose();
  };

  return (
    <Modal
      open={participant != null && memo != null}
      onClose={onClose}
      title={`${participant?.charaName.shortName ?? ""} メモ`}
    >
      <div className="mb-[10px] flex items-center gap-[8px]">
        <label className="text-village-sm text-gray-300">表示色</label>
        <input
          type="color"
          value={`#${color}`}
          onChange={(e) => setColor(e.target.value.replace("#", ""))}
          className="h-[30px] w-[30px] cursor-pointer border-none bg-transparent p-0"
        />
      </div>
      <textarea
        ref={textareaRef}
        value={memoText}
        onChange={(e) => setMemoText(e.target.value)}
        rows={5}
        className="mb-[10px] w-full rounded border border-[#464545] bg-[#303030] p-[8px] text-village-sm text-white"
        placeholder="メモを入力..."
      />
      <div className="flex justify-end gap-[8px]">
        <button
          type="button"
          onClick={onClose}
          className="cursor-pointer rounded border border-[#464545] bg-[#303030] px-[12px] py-[6px] text-village-sm text-white hover:bg-[#404040]"
        >
          キャンセル
        </button>
        <button
          type="button"
          onClick={handleSave}
          className="cursor-pointer rounded bg-[#00bc8c] px-[12px] py-[6px] text-village-sm text-white hover:bg-[#00a87d]"
        >
          保存
        </button>
      </div>
    </Modal>
  );
}
