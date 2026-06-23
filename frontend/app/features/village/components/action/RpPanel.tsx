import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { inputClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  changeVillageCharaName,
  changeVillageMemo,
  type ParticipantSituationView,
} from "~/features/village/api";
import { useAsyncAction } from "~/lib/useAsyncAction";

/**
 * RP 支援 (名前変更・簡易メモ)。簡易メモは参加者一覧に表示される公開情報で、
 * 自分専用メモではない。
 */
export function RpPanel({
  villageId,
  mySituation,
  onDone,
}: {
  villageId: number;
  mySituation: ParticipantSituationView;
  onDone: () => Promise<unknown>;
}) {
  const rp = mySituation.rp;
  return (
    <Panel title="名前変更・簡易メモ" storageKey="changenameform" fixable>
      <div className="space-y-[15px]">
        <ul className="list-disc space-y-[3px] rounded border border-[#f39c12] p-[10px] pl-[25px] text-[#f39c12]">
          <li>進行中は、推理、まとめ、および推理に繋がる内容は記載しないでください。</li>
          <li>簡易メモは状況欄の参加者一覧に表示されます。</li>
          {!rp.isAvailableChangeName && (
            <li>このキャラチップは制作者様の意向により名前変更ができません。</li>
          )}
        </ul>
        {rp.isAvailableChangeName && (
          <ChangeNameForm villageId={villageId} rp={rp} onDone={onDone} />
        )}
        {rp.isAvailableMemo && <MemoForm villageId={villageId} rp={rp} onDone={onDone} />}
      </div>
    </Panel>
  );
}

function ChangeNameForm({
  villageId,
  rp,
  onDone,
}: {
  villageId: number;
  rp: ParticipantSituationView["rp"];
  onDone: () => Promise<unknown>;
}) {
  const [name, setName] = useState(rp.name ?? "");
  const [shortName, setShortName] = useState(rp.shortName ?? "");
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
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className="sm:flex sm:items-center sm:gap-[10px]">
        <label className="sm:w-[120px] sm:shrink-0 sm:text-right">略称</label>
        <input
          type="text"
          className={`${inputClass} mt-[5px] sm:mt-0`}
          value={shortName}
          maxLength={1}
          onChange={(e) => setShortName(e.target.value)}
          aria-label="略称"
        />
      </div>
      <div className="sm:flex sm:items-center sm:gap-[10px]">
        <label className="sm:w-[120px] sm:shrink-0 sm:text-right">名前</label>
        <input
          type="text"
          className={`${inputClass} mt-[5px] sm:mt-0`}
          value={name}
          maxLength={40}
          onChange={(e) => setName(e.target.value)}
          aria-label="名前"
        />
      </div>
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting || name === "" || shortName === ""}>
          名前を変更する
        </Button>
      </div>
    </div>
  );
}

function MemoForm({
  villageId,
  rp,
  onDone,
}: {
  villageId: number;
  rp: ParticipantSituationView["rp"];
  onDone: () => Promise<unknown>;
}) {
  const [memo, setMemo] = useState(rp.memo ?? "");
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
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className="sm:flex sm:items-center sm:gap-[10px]">
        <label className="sm:w-[120px] sm:shrink-0 sm:text-right">簡易メモ</label>
        <input
          type="text"
          className={`${inputClass} mt-[5px] sm:mt-0`}
          value={memo}
          maxLength={20}
          onChange={(e) => setMemo(e.target.value)}
          aria-label="簡易メモ"
        />
      </div>
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting}>
          簡易メモを変更する
        </Button>
      </div>
    </div>
  );
}
