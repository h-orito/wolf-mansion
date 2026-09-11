import { useState } from "react";

import { ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  setVillageVote,
  type ParticipantSituationView,
  type VillageRoomAssignedRow,
} from "~/features/village/api";
import { RoomSelectModal } from "~/features/village/components/modal/RoomSelectModal";
import { resolveParticipantName } from "~/features/village/participants";
import { useVillageContext } from "~/features/village/VillageContext";
import { useVillageInvalidate } from "~/features/village/useVillage";
import { useAsyncAction } from "~/lib/useAsyncAction";
import { useVoteState } from "./useVoteState";

/** 処刑対象への投票。未セットのまま日付が更新されると突然死するため警告を出す。 */
export function VotePanel({
  mySituation,
  roomAssignedRows,
}: {
  mySituation: ParticipantSituationView;
  roomAssignedRows: VillageRoomAssignedRow[] | null | undefined;
}) {
  const village = useVillageContext();
  const invalidate = useVillageInvalidate();
  const vote = mySituation.vote;
  const { targetCharaId, setTargetCharaId } = useVoteState(vote);
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();
  const [roomSelectOpen, setRoomSelectOpen] = useState(false);
  const hasRoomAssigned = roomAssignedRows != null && roomAssignedRows.length > 0;

  const submit = () => {
    if (targetCharaId === "") return;
    void execute(async () => {
      await setVillageVote(village.id, { targetCharaId: Number(targetCharaId) });
      showToast("投票をセットしました");
      await invalidate();
    }, "投票セットに失敗しました");
  };

  const isUnset = vote.targetCharaId == null;
  return (
    <Panel
      title="投票"
      storageKey="voteform"
      fixable
      headerClassName={isUnset ? "bg-wm-danger" : "bg-surface-raised"}
      headerExtra={isUnset ? "(未セットのままだと突然死します)" : null}
    >
      <div className="space-y-[10px]">
        <ErrorMessage error={error} />
        <p>
          現在の投票先:{" "}
          {vote.targetCharaId != null
            ? resolveParticipantName(village, vote.targetCharaId)
            : "なし"}
        </p>
        <hr className="border-border" />
        <p>一番票を集めた人物が処刑されます。同数の場合はランダムで決定されます。</p>
        <div>
          <div className="flex items-center gap-[5px]">
            <select
              className={`${selectClass} flex-1`}
              value={targetCharaId}
              onChange={(e) => setTargetCharaId(e.target.value)}
              aria-label="投票先"
            >
              {targetCharaId === "" && <option value="">選択してください</option>}
              {(vote.targetCharaIds ?? []).map((charaId) => (
                <option key={charaId} value={charaId}>
                  {resolveParticipantName(village, charaId)}
                </option>
              ))}
            </select>
            {hasRoomAssigned && (
              <Button
                variant="info"
                className="shrink-0"
                aria-label="部屋割から投票先を選択"
                onClick={() => setRoomSelectOpen(true)}
              >
                部屋割から選択
              </Button>
            )}
          </div>
          に投票する
        </div>
        <div className="flex justify-end">
          <Button onClick={submit} disabled={submitting || targetCharaId === ""}>
            投票セット
          </Button>
        </div>
      </div>
      {hasRoomAssigned && (
        <RoomSelectModal
          open={roomSelectOpen}
          onClose={() => setRoomSelectOpen(false)}
          rows={roomAssignedRows}
          selectableCharaIds={vote.targetCharaIds ?? []}
          selectedCharaId={targetCharaId === "" ? null : Number(targetCharaId)}
          onSelect={(charaId) => setTargetCharaId(String(charaId))}
        />
      )}
    </Panel>
  );
}
