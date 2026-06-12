import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { inputClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import {
  changeVillageCharaName,
  changeVillageMemo,
  type ParticipantSituationView,
} from "~/features/village/api";
import { ApiError } from "~/lib/api";

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
    <Panel title="名前変更・簡易メモ">
      <div className="space-y-[15px] text-[12px]">
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
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await changeVillageCharaName(villageId, { name, shortName });
      await onDone();
    } catch (e) {
      setError(e instanceof ApiError ? e.detail : "名前の変更に失敗しました");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="space-y-[10px]">
      <strong>名前変更</strong>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <label className="block">
        略称
        <input
          type="text"
          className={`${inputClass} mt-[5px]`}
          value={shortName}
          maxLength={1}
          onChange={(e) => setShortName(e.target.value)}
          aria-label="略称"
        />
      </label>
      <label className="block">
        名前
        <input
          type="text"
          className={`${inputClass} mt-[5px]`}
          value={name}
          maxLength={40}
          onChange={(e) => setName(e.target.value)}
          aria-label="名前"
        />
      </label>
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
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await changeVillageMemo(villageId, { memo });
      await onDone();
    } catch (e) {
      setError(e instanceof ApiError ? e.detail : "簡易メモの変更に失敗しました");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="space-y-[10px]">
      <strong>簡易メモ変更</strong>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <label className="block">
        簡易メモ
        <input
          type="text"
          className={`${inputClass} mt-[5px]`}
          value={memo}
          maxLength={20}
          onChange={(e) => setMemo(e.target.value)}
          aria-label="簡易メモ"
        />
      </label>
      <div className="flex justify-end">
        <Button onClick={submit} disabled={submitting}>
          簡易メモを変更する
        </Button>
      </div>
    </div>
  );
}
