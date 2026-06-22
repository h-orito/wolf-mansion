import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import { setVillageCommit, type ParticipantSituationView } from "~/features/village/api";
import { ApiError } from "~/lib/api";

/** コミット (全員が揃ったら時間前でも日付更新を確定) の ON/OFF。 */
export function CommitPanel({
  villageId,
  mySituation,
  onDone,
}: {
  villageId: number;
  mySituation: ParticipantSituationView;
  onDone: () => Promise<unknown>;
}) {
  const isCommitting = mySituation.commit.isCommitting;
  const showToast = useToast((s) => s.show);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await setVillageCommit(villageId, { commit: !isCommitting });
      showToast(isCommitting ? "コミットを取り消しました" : "コミットしました");
      await onDone();
    } catch (e) {
      setError(e instanceof ApiError ? e.detail : "コミットに失敗しました");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Panel title="コミット">
      <div className="space-y-[10px]">
        {error != null && <p className="text-[#e74c3c]">{error}</p>}
        <div className="flex justify-end">
          <Button onClick={submit} disabled={submitting}>
            {isCommitting ? "コミットを取り消す" : "コミットする"}
          </Button>
        </div>
      </div>
    </Panel>
  );
}
