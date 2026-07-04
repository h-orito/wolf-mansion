import { Modal } from "~/components/ui/Modal";
import type { VillageRoomAssigned, VillageRoomAssignedRow } from "~/features/village/api";
import { RoomGrid } from "~/features/village/components/RoomGrid";
import { resolveCharaId } from "~/features/village/participants";
import { useVillageContext } from "~/features/village/VillageContext";

/** 部屋割から対象者を選ぶモーダル。候補にいない参加者の部屋・空き部屋は選択不可。 */
export function RoomSelectModal({
  open,
  onClose,
  rows,
  selectableCharaIds,
  selectedCharaId,
  onSelect,
}: {
  open: boolean;
  onClose: () => void;
  rows: VillageRoomAssignedRow[];
  /** 選択候補の charaId (select の option と同じ集合) */
  selectableCharaIds: number[];
  selectedCharaId: number | null;
  onSelect: (charaId: number) => void;
}) {
  const village = useVillageContext();
  const charaIdOf = (room: VillageRoomAssigned): number | null =>
    room.participantId != null ? resolveCharaId(village, room.participantId) : null;

  return (
    <Modal open={open} onClose={onClose} title="部屋割から選択" size="wide">
      <div className="overflow-x-auto">
        <RoomGrid
          rows={rows}
          isSelectable={(room) => {
            const charaId = charaIdOf(room);
            return charaId != null && selectableCharaIds.includes(charaId);
          }}
          isSelected={(room) => selectedCharaId != null && charaIdOf(room) === selectedCharaId}
          onRoomClick={(room) => {
            const charaId = charaIdOf(room);
            if (charaId == null) return;
            onSelect(charaId);
            onClose();
          }}
        />
      </div>
    </Modal>
  );
}
