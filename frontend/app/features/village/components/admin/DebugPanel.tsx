import { useState } from "react";

import { ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { VillageFormRow } from "~/components/ui/Form";
import { inlineInputClass, selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { login, logout } from "~/features/auth/api";
import { debugAllParticipate, debugDayChange, type VillageDebugView } from "~/features/village/api";
import { useVillageContext } from "~/features/village/VillageContext";
import { useAsyncAction } from "~/lib/useAsyncAction";

/** ローカル開発向けデバッグメニュー。app.debug 有効時のみ表示する。 */
export function DebugPanel({
  currentDay,
  debugInfo,
  onDone,
}: {
  currentDay: number;
  debugInfo: VillageDebugView;
  onDone: () => Promise<unknown>;
}) {
  const village = useVillageContext();
  return (
    <Panel title="デバッグメニュー" storageKey="debugform">
      <div className="space-y-[15px]">
        {currentDay === 0 && <ParticipateSection villageId={village.id} onDone={onDone} />}
        <DayChangeSection villageId={village.id} onDone={onDone} />
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
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await debugAllParticipate(villageId, personNumber);
      await onDone();
    }, "入村に失敗しました");

  return (
    <div>
      <ErrorMessage error={error} />
      <VillageFormRow label="入村させる">
        <div className="flex items-center gap-[10px]">
          <input
            type="number"
            className={`${inlineInputClass} flex-1`}
            value={personNumber}
            min={1}
            max={99}
            onChange={(e) => setPersonNumber(Number(e.target.value))}
            aria-label="入村人数"
          />
          <Button onClick={submit} disabled={submitting}>
            人数分入村させる
          </Button>
        </div>
      </VillageFormRow>
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
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await debugDayChange(villageId);
      await onDone();
    }, "日付更新に失敗しました");

  return (
    <div>
      <ErrorMessage error={error} />
      <VillageFormRow label="日付を進める">
        <div className="flex justify-end">
          <Button onClick={submit} disabled={submitting}>
            日付を進める
          </Button>
        </div>
      </VillageFormRow>
    </div>
  );
}

function DummyLoginSection({ players }: { players: VillageDebugView["players"] }) {
  const [userId, setUserId] = useState(players[0]?.userId ?? "");
  const { error, submitting, execute } = useAsyncAction();

  const submit = () => {
    if (userId === "") return;
    void execute(async () => {
      await login(userId, "testuser");
      window.location.reload();
    }, "ログインに失敗しました");
  };

  return (
    <div>
      <ErrorMessage error={error} />
      <VillageFormRow label="ダミーログイン">
        <div className="flex items-center gap-[10px]">
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
      </VillageFormRow>
    </div>
  );
}

function LogoutSection() {
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await logout();
      window.location.reload();
    }, "ログアウトに失敗しました");

  return (
    <div>
      <ErrorMessage error={error} />
      <VillageFormRow>
        <div className="flex justify-end">
          <Button variant="danger" onClick={submit} disabled={submitting}>
            ログアウト
          </Button>
        </div>
      </VillageFormRow>
    </div>
  );
}
