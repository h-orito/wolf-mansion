import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import { setVillageCommit, type ParticipantSituationView } from "~/features/village/api";
import { useAsyncAction } from "~/lib/useAsyncAction";

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
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await setVillageCommit(villageId, { commit: !isCommitting });
      showToast(isCommitting ? "コミットを取り消しました" : "コミットしました");
      await onDone();
    }, "コミットに失敗しました");

  return (
    <Panel title="コミット" storageKey="commitform">
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
