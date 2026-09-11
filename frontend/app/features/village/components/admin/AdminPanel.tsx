import { useState } from "react";

import { ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { VillageFormRow } from "~/components/ui/Form";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  adminInsertSelfVotes,
  adminUpdateAllAccess,
  fetchAdminVillagePlayers,
  type AdminVillagePlayersResponse,
} from "~/features/village/api";
import { useVillageId } from "~/features/village/VillageContext";
import { useVillageInvalidate } from "~/features/village/useVillage";
import { useAsyncAction } from "~/lib/useAsyncAction";

/** 管理者機能パネル。管理者プレイヤーのみ表示する。 */
export function AdminPanel() {
  return (
    <Panel title="管理者メニュー" storageKey="adminform">
      <div className="space-y-[15px]">
        <AccessSection />
        <SelfVoteSection />
        <PlayersSection />
      </div>
    </Panel>
  );
}

function AccessSection() {
  const villageId = useVillageId();
  const invalidate = useVillageInvalidate();
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await adminUpdateAllAccess(villageId);
      showToast("全員アクセスを更新しました");
      await invalidate();
    }, "全員アクセスに失敗しました");

  return (
    <div>
      <ErrorMessage error={error} />
      <VillageFormRow label="全員アクセス">
        <div className="flex justify-end">
          <Button onClick={submit} disabled={submitting}>
            更新
          </Button>
        </div>
      </VillageFormRow>
    </div>
  );
}

function SelfVoteSection() {
  const villageId = useVillageId();
  const invalidate = useVillageInvalidate();
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await adminInsertSelfVotes(villageId);
      showToast("全員自投票をセットしました");
      await invalidate();
    }, "全員自分投票に失敗しました");

  return (
    <div>
      <ErrorMessage error={error} />
      <VillageFormRow label="全員自投票">
        <div className="flex justify-end">
          <Button onClick={submit} disabled={submitting}>
            全員投票
          </Button>
        </div>
      </VillageFormRow>
    </div>
  );
}

function PlayersSection() {
  const villageId = useVillageId();
  const [players, setPlayers] = useState<AdminVillagePlayersResponse | null>(null);
  const { error, submitting: loading, execute } = useAsyncAction();

  const load = () =>
    execute(async () => {
      const data = await fetchAdminVillagePlayers(villageId);
      setPlayers(data);
    }, "参加プレイヤーの取得に失敗しました");

  return (
    <div>
      <ErrorMessage error={error} />
      <VillageFormRow label="ID確認" align="start">
        <div className="flex justify-end">
          <Button onClick={load} disabled={loading}>
            参加プレイヤーを表示
          </Button>
        </div>
        {players != null && (
          <table className="mt-[10px] w-full border-collapse border border-border">
            <thead>
              <tr>
                <th className="border border-border px-[8px] py-[4px] text-left">キャラ名</th>
                <th className="border border-border px-[8px] py-[4px] text-left">プレイヤー名</th>
              </tr>
            </thead>
            <tbody>
              {players.players.map((p) => (
                <tr key={p.charaName}>
                  <td className="border border-border px-[8px] py-[4px]">{p.charaName}</td>
                  <td className="border border-border px-[8px] py-[4px]">{p.playerName}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </VillageFormRow>
    </div>
  );
}
