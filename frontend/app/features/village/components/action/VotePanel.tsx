import { ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import { setVillageVote, type ParticipantSituationView } from "~/features/village/api";
import { resolveParticipantName } from "~/features/village/participants";
import { useVillageContext } from "~/features/village/VillageContext";
import { useVillageInvalidate } from "~/features/village/useVillage";
import { useAsyncAction } from "~/lib/useAsyncAction";
import { useVoteState } from "./useVoteState";

/** 処刑対象への投票。未セットのまま日付が更新されると突然死するため警告を出す。 */
export function VotePanel({ mySituation }: { mySituation: ParticipantSituationView }) {
  const village = useVillageContext();
  const invalidate = useVillageInvalidate();
  const vote = mySituation.vote;
  const { targetCharaId, setTargetCharaId } = useVoteState(vote);
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

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
      headerClassName={isUnset ? "bg-[#ff0000]" : "bg-[#464545]"}
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
        <hr className="border-[#464545]" />
        <p>一番票を集めた人物が処刑されます。同数の場合はランダムで決定されます。</p>
        <div>
          <select
            className={selectClass}
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
          </select>{" "}
          に投票する
        </div>
        <div className="flex justify-end">
          <Button onClick={submit} disabled={submitting || targetCharaId === ""}>
            投票セット
          </Button>
        </div>
      </div>
    </Panel>
  );
}
