import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { inlineInputClass } from "~/components/ui/Input";
import { useToast } from "~/components/ui/Toast";
import {
  adminInsertSelfVotes,
  adminLeaveVillageParticipant,
  adminUpdateAllAccess,
  fetchAdminVillagePlayers,
  type AdminVillagePlayersResponse,
} from "~/features/village/api";
import { ApiError } from "~/lib/api";

function errorMessage(e: unknown, fallback: string): string {
  return e instanceof ApiError ? e.detail : fallback;
}

/** 管理者機能パネル。管理者プレイヤーのみ表示する。 */
export function AdminPanel({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  return (
    <Panel title="管理者メニュー">
      <div className="space-y-[20px]">
        <AccessSection villageId={villageId} onDone={onDone} />
        <SelfVoteSection villageId={villageId} onDone={onDone} />
        <LeaveSection villageId={villageId} onDone={onDone} />
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
      <p className="mb-[5px] font-bold">全員アクセス</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting}>
          更新
        </Button>
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
      <p className="mb-[5px] font-bold">全員自投票</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting}>
          全員投票
        </Button>
      </div>
    </div>
  );
}

function LeaveSection({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  const [villagePlayerId, setVillagePlayerId] = useState<string>("");
  const showToast = useToast((s) => s.show);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting || villagePlayerId === "") return;
    if (!window.confirm("本当に退村させてよろしいですか？")) return;
    setSubmitting(true);
    setError(null);
    try {
      await adminLeaveVillageParticipant(villageId, { villagePlayerId: Number(villagePlayerId) });
      showToast("退村させました");
      setVillagePlayerId("");
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "強制退村に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <p className="mb-[5px] font-bold">強制退村</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <input
        type="number"
        className={inlineInputClass}
        value={villagePlayerId}
        onChange={(e) => setVillagePlayerId(e.target.value)}
        aria-label="村参加者ID"
        placeholder="village_player_id"
      />
      <div className="mt-[5px] flex justify-end">
        <Button variant="danger" onClick={submit} disabled={submitting || villagePlayerId === ""}>
          退村させる
        </Button>
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
      <p className="mb-[5px] font-bold">参加プレイヤー確認</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <Button onClick={load} disabled={loading}>
        参加プレイヤーを表示
      </Button>
      {players != null && (
        <table className="mt-[10px] w-full border-collapse border border-[#464545]">
          <thead>
            <tr>
              <th className="border border-[#464545] px-[8px] py-[4px] text-left">キャラ名</th>
              <th className="border border-[#464545] px-[8px] py-[4px] text-left">プレイヤー名</th>
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
  );
}
