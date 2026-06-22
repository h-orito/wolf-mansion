import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  adminInsertSelfVotes,
  adminUpdateAllAccess,
  fetchAdminVillagePlayers,
  type AdminVillagePlayersResponse,
} from "~/features/village/api";
import { ApiError } from "~/lib/api";

function errorMessage(e: unknown, fallback: string): string {
  return e instanceof ApiError ? e.detail : fallback;
}

const labelClass = "sm:w-[120px] sm:shrink-0 sm:text-right";
const rowClass = "sm:flex sm:items-center sm:gap-[10px]";

/** 管理者機能パネル。管理者プレイヤーのみ表示する。 */
export function AdminPanel({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  return (
    <Panel title="管理者メニュー" storageKey="adminform">
      <div className="space-y-[15px]">
        <AccessSection villageId={villageId} onDone={onDone} />
        <SelfVoteSection villageId={villageId} onDone={onDone} />
        <PlayersSection villageId={villageId} />
      </div>
    </Panel>
  );
}

function AccessSection({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  const showToast = useToast((s) => s.show);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await adminUpdateAllAccess(villageId);
      showToast("全員アクセスを更新しました");
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "全員アクセスに失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={rowClass}>
        <label className={labelClass}>全員アクセス</label>
        <div className="mt-[5px] flex flex-1 justify-end sm:mt-0">
          <Button onClick={submit} disabled={submitting}>
            更新
          </Button>
        </div>
      </div>
    </div>
  );
}

function SelfVoteSection({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  const showToast = useToast((s) => s.show);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await adminInsertSelfVotes(villageId);
      showToast("全員自投票をセットしました");
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "全員自分投票に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={rowClass}>
        <label className={labelClass}>全員自投票</label>
        <div className="mt-[5px] flex flex-1 justify-end sm:mt-0">
          <Button onClick={submit} disabled={submitting}>
            全員投票
          </Button>
        </div>
      </div>
    </div>
  );
}

function PlayersSection({ villageId }: { villageId: number }) {
  const [players, setPlayers] = useState<AdminVillagePlayersResponse | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const load = async () => {
    if (loading) return;
    setLoading(true);
    setError(null);
    try {
      const data = await fetchAdminVillagePlayers(villageId);
      setPlayers(data);
    } catch (e) {
      setError(errorMessage(e, "参加プレイヤーの取得に失敗しました"));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={`${rowClass} sm:items-start`}>
        <label className={labelClass}>ID確認</label>
        <div className="mt-[5px] flex-1 sm:mt-0">
          <div className="flex justify-end">
            <Button onClick={load} disabled={loading}>
              参加プレイヤーを表示
            </Button>
          </div>
          {players != null && (
            <table className="mt-[10px] w-full border-collapse border border-[#464545]">
              <thead>
                <tr>
                  <th className="border border-[#464545] px-[8px] py-[4px] text-left">キャラ名</th>
                  <th className="border border-[#464545] px-[8px] py-[4px] text-left">
                    プレイヤー名
                  </th>
                </tr>
              </thead>
              <tbody>
                {players.players.map((p) => (
                  <tr key={p.charaName}>
                    <td className="border border-[#464545] px-[8px] py-[4px]">{p.charaName}</td>
                    <td className="border border-[#464545] px-[8px] py-[4px]">{p.playerName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
}
