import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { inlineInputClass, selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { login, logout } from "~/features/auth/api";
import { debugAllParticipate, debugDayChange, type VillageDebugView } from "~/features/village/api";
import { ApiError } from "~/lib/api";

function errorMessage(e: unknown, fallback: string): string {
  return e instanceof ApiError ? e.detail : fallback;
}

/** ローカル開発向けデバッグメニュー。app.debug 有効時のみ表示する。 */
export function DebugPanel({
  villageId,
  currentDay,
  debugInfo,
  onDone,
}: {
  villageId: number;
  currentDay: number;
  debugInfo: VillageDebugView;
  onDone: () => Promise<unknown>;
}) {
  return (
    <Panel title="デバッグメニュー">
      <div className="space-y-[20px]">
        {currentDay === 0 && <ParticipateSection villageId={villageId} onDone={onDone} />}
        <DayChangeSection villageId={villageId} onDone={onDone} />
        <DummyLoginSection players={debugInfo.players} />
        <LogoutSection />
      </div>
    </Panel>
  );
}

function ParticipateSection({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  const [personNumber, setPersonNumber] = useState(16);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await debugAllParticipate(villageId, personNumber);
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "入村に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <p className="mb-[5px] font-bold">入村させる</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className="flex flex-wrap items-center gap-[5px]">
        <input
          type="number"
          className={inlineInputClass}
          style={{ width: "80px" }}
          value={personNumber}
          min={1}
          max={50}
          onChange={(e) => setPersonNumber(Number(e.target.value))}
          aria-label="入村人数"
        />
        <Button onClick={submit} disabled={submitting}>
          人数分入村させる
        </Button>
      </div>
    </div>
  );
}

function DayChangeSection({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await debugDayChange(villageId);
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "日付更新に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <p className="mb-[5px] font-bold">日付を進める</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <Button onClick={submit} disabled={submitting}>
        日付を進める
      </Button>
    </div>
  );
}

function DummyLoginSection({ players }: { players: VillageDebugView["players"] }) {
  const [userId, setUserId] = useState(players[0]?.userId ?? "");
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting || userId === "") return;
    setSubmitting(true);
    setError(null);
    try {
      await login(userId, "testuser");
      window.location.reload();
    } catch (e) {
      setError(errorMessage(e, "ログインに失敗しました"));
      setSubmitting(false);
    }
  };

  return (
    <div>
      <p className="mb-[5px] font-bold">ダミーログイン</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className="flex flex-wrap items-center gap-[5px]">
        <select
          className={`${selectClass} max-w-[240px]`}
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          aria-label="ダミーログインプレイヤー"
        >
          {players.map((p) => (
            <option key={p.userId} value={p.userId}>
              {p.label}
            </option>
          ))}
        </select>
        <Button onClick={submit} disabled={submitting || userId === ""}>
          ログイン
        </Button>
      </div>
    </div>
  );
}

function LogoutSection() {
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await logout();
      window.location.reload();
    } catch (e) {
      setError(errorMessage(e, "ログアウトに失敗しました"));
      setSubmitting(false);
    }
  };

  return (
    <div>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <Button variant="danger" onClick={submit} disabled={submitting}>
        ログアウト
      </Button>
    </div>
  );
}
