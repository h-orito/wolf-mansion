import { useState } from "react";

import { Button, LinkButton } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { selectClass } from "~/components/ui/Input";
import { useToast } from "~/components/ui/Toast";
import {
  cancelVillage,
  extendVillageEpilogue,
  kickVillageParticipant,
  shortenVillageEpilogue,
  type ParticipantSituationView,
  type VillageCreatorSayRequest,
} from "~/features/village/api";
import { useAsyncAction } from "~/lib/useAsyncAction";

const labelClass = "sm:w-[120px] sm:shrink-0 sm:text-right";
const rowClass = "sm:flex sm:items-center sm:gap-[10px]";

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
    <Panel title="村建て機能" storageKey="creatorform" fixable>
      <div className="space-y-[15px]">
        {creator.isAvailableModifySetting && (
          <div className={rowClass}>
            <label className={labelClass}>設定変更</label>
            <div className="mt-[5px] flex flex-1 justify-end sm:mt-0">
              <LinkButton to={`/village/${villageId}/settings`} target="_blank" variant="success">
                村設定変更
              </LinkButton>
            </div>
          </div>
        )}
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
        {(creator.isAvailableExtendEpilogue || creator.isAvailableShortenEpilogue) && (
          <EpilogueSection
            villageId={villageId}
            canExtend={creator.isAvailableExtendEpilogue}
            canShorten={creator.isAvailableShortenEpilogue}
            onDone={onDone}
          />
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
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () => {
    if (selectedCharaId === "") return;
    if (!window.confirm("本当に退村させてよろしいですか？")) return;
    void execute(async () => {
      await kickVillageParticipant(villageId, { charaId: Number(selectedCharaId) });
      showToast("退村させました");
      await onDone();
    }, "強制退村に失敗しました");
  };

  return (
    <div className="space-y-[10px]">
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={`${rowClass} sm:items-start`}>
        <label className={labelClass}>最終アクセス日時</label>
        <div className="mt-[5px] flex-1 sm:mt-0">
          <table className="w-full border-collapse border border-[#464545]">
            <tbody>
              {members.map((m) => (
                <tr key={m.charaName}>
                  <td className="border border-[#464545] px-[8px] py-[4px]">{m.charaName}</td>
                  <td className="border border-[#464545] px-[8px] py-[4px]">
                    {m.lastAccess ?? ""}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <div className={rowClass}>
        <label className={labelClass}>強制退村</label>
        <div className="mt-[5px] flex flex-1 items-center gap-[10px] sm:mt-0">
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
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const submit = () => {
    if (!window.confirm("本当に廃村にしてよろしいですか？")) return;
    void execute(async () => {
      await cancelVillage(villageId);
      showToast("廃村にしました");
      await onDone();
    }, "廃村に失敗しました");
  };

  return (
    <div>
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={rowClass}>
        <label className={labelClass}>廃村</label>
        <div className="mt-[5px] flex flex-1 justify-end sm:mt-0">
          <Button variant="danger" onClick={submit} disabled={submitting}>
            廃村にする
          </Button>
        </div>
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
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      <div className={`${rowClass} sm:items-start`}>
        <label className={labelClass}>村建て発言</label>
        <div className="mt-[5px] flex-1 sm:mt-0">
          <textarea
            className="min-h-[77px] w-full rounded border border-[#464545] bg-white p-[9px] text-[#555]"
            rows={5}
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            aria-label="村建て発言"
          />
        </div>
      </div>
      <div className={rowClass}>
        <div className={labelClass} />
        <div className="mt-[5px] flex flex-1 items-center justify-between sm:mt-0">
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
      </div>
    </div>
  );
}

function EpilogueSection({
  villageId,
  canExtend,
  canShorten,
  onDone,
}: {
  villageId: number;
  canExtend: boolean;
  canShorten: boolean;
  onDone: () => Promise<unknown>;
}) {
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const extend = () =>
    execute(async () => {
      await extendVillageEpilogue(villageId);
      showToast("エピローグを延長しました");
      await onDone();
    }, "エピローグ延長に失敗しました");

  const shorten = () =>
    execute(async () => {
      await shortenVillageEpilogue(villageId);
      showToast("エピローグを短縮しました");
      await onDone();
    }, "エピローグ短縮に失敗しました");

  return (
    <div className="space-y-[10px]">
      {error != null && <p className="text-[#e74c3c]">{error}</p>}
      {canExtend && (
        <div className={rowClass}>
          <label className={labelClass}>エピローグ延長</label>
          <div className="mt-[5px] flex flex-1 justify-end sm:mt-0">
            <Button onClick={extend} disabled={submitting}>
              1日延長する
            </Button>
          </div>
        </div>
      )}
      {canShorten && (
        <div className={rowClass}>
          <label className={labelClass}>エピローグ短縮</label>
          <div className="mt-[5px] flex flex-1 justify-end sm:mt-0">
            <Button onClick={shorten} disabled={submitting}>
              1日短縮する
            </Button>
          </div>
        </div>
      )}
    </div>
  );
}
