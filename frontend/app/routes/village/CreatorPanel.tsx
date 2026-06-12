import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { selectClass } from "~/components/ui/Input";
import {
  cancelVillage,
  extendVillageEpilogue,
  kickVillageParticipant,
  shortenVillageEpilogue,
  type ParticipantSituationView,
  type VillageCreatorSayRequest,
} from "~/features/village/api";
import { ApiError } from "~/lib/api";

function errorMessage(e: unknown, fallback: string): string {
  return e instanceof ApiError ? e.detail : fallback;
}

/** 村建て機能パネル。村建てプレイヤーのみ表示する。 */
export function CreatorPanel({
  villageId,
  mySituation,
  participants,
  members,
  onConfirm,
  onDone,
}: {
  villageId: number;
  mySituation: ParticipantSituationView;
  /** 強制退村の選択肢 (charaId と name を持つリスト)。 */
  participants: { charaId: number; name: string }[];
  /** 最終アクセス日時テーブル用メンバー一覧。 */
  members: { charaName: string; lastAccess: string | null }[];
  /** 村建て発言の確認画面へ進む (プレビュー取得は親が行う)。 */
  onConfirm: (request: VillageCreatorSayRequest) => Promise<void>;
  onDone: () => Promise<unknown>;
}) {
  const creator = mySituation.creator;

  return (
    <Panel title="村建て機能">
      <div className="space-y-[20px] text-[12px]">
        {creator.isAvailableKick && (
          <KickSection
            villageId={villageId}
            participants={participants}
            members={members}
            onDone={onDone}
          />
        )}
        {creator.isAvailableCancelVillage && (
          <CancelSection villageId={villageId} onDone={onDone} />
        )}
        {creator.isAvailableCreatorSay && <CreatorSaySection onConfirm={onConfirm} />}
        {creator.isAvailableExtendEpilogue && (
          <EpilogueSection villageId={villageId} onDone={onDone} />
        )}
      </div>
    </Panel>
  );
}

function KickSection({
  villageId,
  participants,
  members,
  onDone,
}: {
  villageId: number;
  participants: { charaId: number; name: string }[];
  members: { charaName: string; lastAccess: string | null }[];
  onDone: () => Promise<unknown>;
}) {
  const [selectedCharaId, setSelectedCharaId] = useState<string>("");
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const submit = async () => {
    if (submitting || selectedCharaId === "") return;
    if (!window.confirm("本当に退村させてよろしいですか？")) return;
    setSubmitting(true);
    setError(null);
    try {
      await kickVillageParticipant(villageId, { charaId: Number(selectedCharaId) });
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "強制退村に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <p className="mb-[5px] font-bold">最終アクセス日時</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <table className="mb-[10px] w-full border-collapse border border-[#464545]">
        <thead>
          <tr>
            <th className="border border-[#464545] px-[8px] py-[4px] text-left">キャラ名</th>
            <th className="border border-[#464545] px-[8px] py-[4px] text-left">最終アクセス</th>
          </tr>
        </thead>
        <tbody>
          {members.map((m) => (
            <tr key={m.charaName}>
              <td className="border border-[#464545] px-[8px] py-[4px]">{m.charaName}</td>
              <td className="border border-[#464545] px-[8px] py-[4px]">{m.lastAccess ?? ""}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <p className="mb-[5px] font-bold">強制退村</p>
      <div className="flex flex-wrap items-center gap-[5px]">
        <select
          className={selectClass}
          value={selectedCharaId}
          onChange={(e) => setSelectedCharaId(e.target.value)}
          aria-label="退村させる参加者"
        >
          <option value="">選択してください</option>
          {participants.map((p) => (
            <option key={p.charaId} value={p.charaId}>
              {p.name}
            </option>
          ))}
        </select>
        <Button variant="danger" onClick={submit} disabled={submitting || selectedCharaId === ""}>
          退村させる
        </Button>
      </div>
    </div>
  );
}

function CancelSection({
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
    if (!window.confirm("本当に廃村にしてよろしいですか？")) return;
    setSubmitting(true);
    setError(null);
    try {
      await cancelVillage(villageId);
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "廃村に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className="flex justify-end">
        <Button variant="danger" onClick={submit} disabled={submitting}>
          廃村にする
        </Button>
      </div>
    </div>
  );
}

function CreatorSaySection({
  onConfirm,
}: {
  onConfirm: (request: VillageCreatorSayRequest) => Promise<void>;
}) {
  const [message, setMessage] = useState("");
  const [convertDisable, setConvertDisable] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const length = message.length;
  const lineCount = message.split("\n").length;
  const maxLength = 1000;
  const maxLine = 40;
  const overLimit = length > maxLength || lineCount > maxLine;
  const submitDisabled = overLimit || message.trim().length === 0 || submitting;

  const submit = async () => {
    if (submitDisabled) return;
    setSubmitting(true);
    setError(null);
    try {
      await onConfirm({ message, convertDisable });
    } catch (e) {
      setError(errorMessage(e, "確認に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <p className="mb-[5px] font-bold">村建て発言</p>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <textarea
        className="w-full rounded border border-[#464545] bg-white p-[9px] text-[12px] text-[#555]"
        rows={5}
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        aria-label="村建て発言"
      />
      <div className={`mt-[5px] text-[12px] ${overLimit ? "text-[#e74c3c]" : ""}`}>
        文字数: {length}/{maxLength}, 行数: {lineCount}/{maxLine}
      </div>
      <div className="mt-[10px] flex items-center justify-between">
        <label className="flex cursor-pointer items-center gap-[5px] text-[12px]">
          <input
            type="checkbox"
            checked={convertDisable}
            onChange={() => setConvertDisable(!convertDisable)}
          />
          装飾・変換無効
        </label>
        <Button onClick={submit} disabled={submitDisabled}>
          確認画面へ
        </Button>
      </div>
    </div>
  );
}

function EpilogueSection({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const extend = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await extendVillageEpilogue(villageId);
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "エピローグ延長に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  const shorten = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await shortenVillageEpilogue(villageId);
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "エピローグ短縮に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className="flex gap-[5px]">
        <Button onClick={extend} disabled={submitting}>
          1日延長する
        </Button>
        <Button onClick={shorten} disabled={submitting}>
          1日短縮する
        </Button>
      </div>
    </div>
  );
}
