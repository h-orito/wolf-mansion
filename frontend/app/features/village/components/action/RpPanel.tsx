import { useState } from "react";

import { AlertList, ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { VillageFormRow } from "~/components/ui/Form";
import { inputClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  changeVillageCharaName,
  changeVillageMemo,
  type ParticipantSituationView,
} from "~/features/village/api";
import { useVillageId } from "~/features/village/VillageContext";
import { useAsyncAction } from "~/lib/useAsyncAction";

/**
 * RP 支援 (名前変更・簡易メモ)。簡易メモは参加者一覧に表示される公開情報で、
 * 自分専用メモではない。
 */
export function RpPanel({
  mySituation,
  onDone,
}: {
  mySituation: ParticipantSituationView;
  onDone: () => Promise<unknown>;
}) {
  const rp = mySituation.rp;
  return (
    <Panel title="名前変更・簡易メモ" storageKey="changenameform" fixable>
      <div className="space-y-[15px]">
        <AlertList>
          <li>進行中は、推理、まとめ、および推理に繋がる内容は記載しないでください。</li>
          <li>簡易メモは状況欄の参加者一覧に表示されます。</li>
          {!rp.isAvailableChangeName && (
            <li>このキャラチップは制作者様の意向により名前変更ができません。</li>
          )}
        </AlertList>
        {rp.isAvailableChangeName && <ChangeNameForm myself={mySituation.myself} onDone={onDone} />}
        {rp.isAvailableMemo && <MemoForm myself={mySituation.myself} onDone={onDone} />}
      </div>
    </Panel>
  );
}

function ChangeNameForm({
  myself,
  onDone,
}: {
  myself: ParticipantSituationView["myself"];
  onDone: () => Promise<unknown>;
}) {
  const villageId = useVillageId();
  const [name, setName] = useState(myself?.charaName.name ?? "");
  const [shortName, setShortName] = useState(myself?.charaName.shortName ?? "");
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await changeVillageCharaName(villageId, { name, shortName });
      showToast("名前を変更しました");
      await onDone();
    }, "名前の変更に失敗しました");

  return (
    <div className="space-y-[10px]">
      <strong>名前変更</strong>
      <ErrorMessage error={error} />
      <VillageFormRow label="略称">
        <input
          type="text"
          className={inputClass}
          value={shortName}
          maxLength={1}
          onChange={(e) => setShortName(e.target.value)}
          aria-label="略称"
        />
      </VillageFormRow>
      <VillageFormRow label="名前">
        <input
          type="text"
          className={inputClass}
          value={name}
          maxLength={40}
          onChange={(e) => setName(e.target.value)}
          aria-label="名前"
        />
      </VillageFormRow>
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting || name === "" || shortName === ""}>
          名前を変更する
        </Button>
      </div>
    </div>
  );
}

function MemoForm({
  myself,
  onDone,
}: {
  myself: ParticipantSituationView["myself"];
  onDone: () => Promise<unknown>;
}) {
  const villageId = useVillageId();
  const [memo, setMemo] = useState(myself?.memo ?? "");
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () =>
    execute(async () => {
      await changeVillageMemo(villageId, { memo });
      showToast("簡易メモを変更しました");
      await onDone();
    }, "簡易メモの変更に失敗しました");

  return (
    <div className="space-y-[10px]">
      <strong>簡易メモ変更</strong>
      <ErrorMessage error={error} />
      <VillageFormRow label="簡易メモ">
        <input
          type="text"
          className={inputClass}
          value={memo}
          maxLength={20}
          onChange={(e) => setMemo(e.target.value)}
          aria-label="簡易メモ"
        />
      </VillageFormRow>
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting}>
          簡易メモを変更する
        </Button>
      </div>
    </div>
  );
}
