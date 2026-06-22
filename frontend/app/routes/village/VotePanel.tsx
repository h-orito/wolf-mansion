import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import { setVillageVote, type ParticipantSituationView } from "~/features/village/api";
import { ApiError } from "~/lib/api";

/** 処刑対象への投票。未セットのまま日付が更新されると突然死するため警告を出す。 */
export function VotePanel({
  villageId,
  mySituation,
  onDone,
}: {
  villageId: number;
  mySituation: ParticipantSituationView;
  onDone: () => Promise<unknown>;
}) {
  const vote = mySituation.vote;
  const [targetCharaId, setTargetCharaId] = useState<string>(
    vote.targetCharaId != null ? String(vote.targetCharaId) : "",
  );
  const showToast = useToast((s) => s.show);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting || targetCharaId === "") return;
    setSubmitting(true);
    setError(null);
    try {
      await setVillageVote(villageId, { targetCharaId: Number(targetCharaId) });
      showToast("投票をセットしました");
      await onDone();
    } catch (e) {
      setError(e instanceof ApiError ? e.detail : "投票セットに失敗しました");
    } finally {
      setSubmitting(false);
    }
  };

  const isUnset = vote.targetCharaId == null;
  return (
    <Panel
      title="投票"
      headerClassName={isUnset ? "bg-[#ff0000]" : "bg-[#464545]"}
      headerExtra={isUnset ? "(未セットのままだと突然死します)" : null}
    >
      <div className="space-y-[10px]">
        {error != null && <p className="text-[#e74c3c]">{error}</p>}
        <p>現在の投票先: {vote.targetName ?? "なし"}</p>
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
            {(vote.targetList ?? []).map((target) => (
              <option key={target.charaId} value={target.charaId}>
                {target.name}
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
