import { useState } from "react";

import { ErrorMessage } from "~/components/ui/Alert";
import { Button, LinkButton } from "~/components/ui/Button";
import { VillageFormRow } from "~/components/ui/Form";
import { selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  cancelVillage,
  extendVillageEpilogue,
  kickVillageParticipant,
  shortenVillageEpilogue,
  type ParticipantSituationView,
  type VillageCreatorSayRequest,
} from "~/features/village/api";
import { useVillageContext, useVillageId } from "~/features/village/VillageContext";
import { useVillageInvalidate } from "~/features/village/useVillage";
import { allParticipants } from "~/features/village/participants";
import { formatLastAccess } from "~/lib/datetime";
import { useAsyncAction } from "~/lib/useAsyncAction";

/** 村建て機能パネル。村建てプレイヤーのみ表示する。 */
export function CreatorPanel({
  mySituation,
  onConfirm,
  registerOnDone,
}: {
  mySituation: ParticipantSituationView;
  onConfirm: (request: VillageCreatorSayRequest) => Promise<void>;
  registerOnDone: (kind: "say" | "action" | "creatorSay", fn: () => void) => void;
}) {
  const villageId = useVillageId();
  const creator = mySituation.creator;

  return (
    <Panel title="村建て機能" storageKey="creatorform" fixable>
      <div className="space-y-[15px]">
        {creator.isAvailableModifySetting && (
          <VillageFormRow label="設定変更">
            <div className="flex justify-end">
              <LinkButton to={`/village/${villageId}/settings`} target="_blank" variant="success">
                村設定変更
              </LinkButton>
            </div>
          </VillageFormRow>
        )}
        {creator.isAvailableKick && <KickSection />}
        {creator.isAvailableCancelVillage && <CancelSection />}
        {creator.isAvailableCreatorSay && (
          <CreatorSaySection onConfirm={onConfirm} registerOnDone={registerOnDone} />
        )}
        {(creator.isAvailableExtendEpilogue || creator.isAvailableShortenEpilogue) && (
          <EpilogueSection
            canExtend={creator.isAvailableExtendEpilogue}
            canShorten={creator.isAvailableShortenEpilogue}
          />
        )}
      </div>
    </Panel>
  );
}

function KickSection() {
  const village = useVillageContext();
  const invalidate = useVillageInvalidate();
  const all = allParticipants(village);
  const participants = all.map((p) => ({ charaId: p.chara.id, name: p.name }));
  const members = all.map((p) => ({
    charaName: p.name,
    lastAccess: p.lastAccessDatetime != null ? formatLastAccess(p.lastAccessDatetime) : null,
  }));
  const [selectedCharaId, setSelectedCharaId] = useState<string>("");
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () => {
    if (selectedCharaId === "") return;
    if (!window.confirm("本当に退村させてよろしいですか？")) return;
    void execute(async () => {
      await kickVillageParticipant(village.id, { charaId: Number(selectedCharaId) });
      showToast("退村させました");
      await invalidate();
    }, "強制退村に失敗しました");
  };

  return (
    <div className="space-y-[10px]">
      <ErrorMessage error={error} />
      <VillageFormRow label="最終アクセス日時" align="start">
        <table className="w-full border-collapse border border-[#464545]">
          <tbody>
            {members.map((m) => (
              <tr key={m.charaName}>
                <td className="border border-[#464545] px-[8px] py-[4px]">{m.charaName}</td>
                <td className="border border-[#464545] px-[8px] py-[4px]">{m.lastAccess ?? ""}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </VillageFormRow>
      <VillageFormRow label="強制退村">
        <div className="flex items-center gap-[10px]">
          <select
            className={`${selectClass} flex-1`}
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
      </VillageFormRow>
    </div>
  );
}

function CancelSection() {
  const villageId = useVillageId();
  const invalidate = useVillageInvalidate();
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () => {
    if (!window.confirm("本当に廃村にしてよろしいですか？")) return;
    void execute(async () => {
      await cancelVillage(villageId);
      showToast("廃村にしました");
      await invalidate();
    }, "廃村に失敗しました");
  };

  return (
    <div>
      <ErrorMessage error={error} />
      <VillageFormRow label="廃村">
        <div className="flex justify-end">
          <Button variant="danger" onClick={submit} disabled={submitting}>
            廃村にする
          </Button>
        </div>
      </VillageFormRow>
    </div>
  );
}

function CreatorSaySection({
  onConfirm,
  registerOnDone,
}: {
  onConfirm: (request: VillageCreatorSayRequest) => Promise<void>;
  registerOnDone: (kind: "say" | "action" | "creatorSay", fn: () => void) => void;
}) {
  const [message, setMessage] = useState("");
  registerOnDone("creatorSay", () => setMessage(""));
  const [convertDisable, setConvertDisable] = useState(false);
  const { error, submitting, execute } = useAsyncAction();

  const length = message.length;
  const lineCount = message.split("\n").length;
  const maxLength = 1000;
  const maxLine = 40;
  const overLimit = length > maxLength || lineCount > maxLine;
  const submitDisabled = overLimit || message.trim().length === 0 || submitting;

  const submit = () => {
    if (submitDisabled) return;
    void execute(async () => {
      await onConfirm({ message, convertDisable });
    }, "確認に失敗しました");
  };

  return (
    <div className="space-y-[10px]">
      <ErrorMessage error={error} />
      <VillageFormRow label="村建て発言" align="start">
        <textarea
          className="min-h-[77px] w-full rounded border border-[#464545] bg-white p-[9px] font-[sans-serif] text-[#555]"
          rows={5}
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          aria-label="村建て発言"
        />
      </VillageFormRow>
      <VillageFormRow>
        <div className="flex items-center justify-between">
          <div>
            <span className={overLimit ? "text-[#e74c3c]" : ""}>
              文字数: {length}/{maxLength}, 行数: {lineCount}/{maxLine}
            </span>
            <label className="ml-[10px] inline-flex cursor-pointer items-center gap-[5px]">
              <input
                type="checkbox"
                checked={convertDisable}
                onChange={() => setConvertDisable(!convertDisable)}
              />
              装飾・変換無効
            </label>
          </div>
          <Button onClick={submit} disabled={submitDisabled}>
            確認画面へ
          </Button>
        </div>
      </VillageFormRow>
    </div>
  );
}

function EpilogueSection({ canExtend, canShorten }: { canExtend: boolean; canShorten: boolean }) {
  const villageId = useVillageId();
  const invalidate = useVillageInvalidate();
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const extend = () =>
    execute(async () => {
      await extendVillageEpilogue(villageId);
      showToast("エピローグを延長しました");
      await invalidate();
    }, "エピローグ延長に失敗しました");

  const shorten = () =>
    execute(async () => {
      await shortenVillageEpilogue(villageId);
      showToast("エピローグを短縮しました");
      await invalidate();
    }, "エピローグ短縮に失敗しました");

  return (
    <div className="space-y-[10px]">
      <ErrorMessage error={error} />
      {canExtend && (
        <VillageFormRow label="エピローグ延長">
          <div className="flex justify-end">
            <Button onClick={extend} disabled={submitting}>
              1日延長する
            </Button>
          </div>
        </VillageFormRow>
      )}
      {canShorten && (
        <VillageFormRow label="エピローグ短縮">
          <div className="flex justify-end">
            <Button onClick={shorten} disabled={submitting}>
              1日短縮する
            </Button>
          </div>
        </VillageFormRow>
      )}
    </div>
  );
}
