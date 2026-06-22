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

const labelClass = "sm:w-[120px] sm:shrink-0 sm:text-right";
const rowClass = "sm:flex sm:items-center sm:gap-[10px]";

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
    <Panel title="デバッグメニュー" storageKey="debugform">
      <div className="space-y-[15px]">
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
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={rowClass}>
        <label className={labelClass}>入村させる</label>
        <div className="mt-[5px] flex flex-1 items-center gap-[10px] sm:mt-0">
          <input
            type="number"
            className={`${inlineInputClass} flex-1`}
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
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={rowClass}>
        <label className={labelClass}>日付を進める</label>
        <div className="mt-[5px] flex flex-1 justify-end sm:mt-0">
          <Button onClick={submit} disabled={submitting}>
            日付を進める
          </Button>
        </div>
      </div>
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
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={rowClass}>
        <label className={labelClass}>ダミーログイン</label>
        <div className="mt-[5px] flex flex-1 items-center gap-[10px] sm:mt-0">
          <select
            className={`${selectClass} flex-1`}
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
      <div className={rowClass}>
        <label className={labelClass} />
        <div className="mt-[5px] flex flex-1 justify-end sm:mt-0">
          <Button variant="danger" onClick={submit} disabled={submitting}>
            ログアウト
          </Button>
        </div>
      </div>
    </div>
  );
}
