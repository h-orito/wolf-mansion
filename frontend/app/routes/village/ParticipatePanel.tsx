import { useMemo, useRef, useState } from "react";

import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { inlineInputClass, inputClass, selectClass, textareaClass } from "~/components/ui/Input";
import {
  confirmVillageParticipate,
  type ParticipantSituationView,
  type VillageDetailView,
  type VillageParticipateRequest,
} from "~/features/village/api";
import { ApiError } from "~/lib/api";
import { toMessageHtml } from "./messageHtml";

const OMAKASE = "LEFTOVER";

type Step = "input" | "confirm";

/**
 * 入村フォーム。キャラ選択 → 名前/略称 (キャラから自動補完) → 希望役職 → 入村発言 →
 * 確認 (ルール/礼節の 2 つの同意チェックで「入村する」が活性化) → 入村。
 */
export function ParticipatePanel({
  village,
  mySituation,
  onParticipated,
  onError,
}: {
  village: VillageDetailView;
  mySituation: ParticipantSituationView;
  onParticipated: (request: VillageParticipateRequest, charaImage: File | null) => Promise<void>;
  /** 確認 (サーバ検証) のエラーメッセージ表示用 */
  onError: (message: string | null) => void;
}) {
  const participate = mySituation.participate;
  const skillRequest = mySituation.skillRequest;
  const isOriginal = village.setting.chara.isOriginalCharachip;
  const charachips = participate.selectableCharachipList ?? [];

  const [step, setStep] = useState<Step>("input");
  const [charachipId, setCharachipId] = useState<number | null>(charachips[0]?.id ?? null);
  const [charaId, setCharaId] = useState<number | null>(null);
  const [charaName, setCharaName] = useState("");
  const [charaShortName, setCharaShortName] = useState("");
  const [requestedSkill, setRequestedSkill] = useState(OMAKASE);
  const [secondRequestedSkill, setSecondRequestedSkill] = useState(OMAKASE);
  const [joinMessage, setJoinMessage] = useState("");
  const [joinPassword, setJoinPassword] = useState("");
  const [spectator, setSpectator] = useState(false);
  const [agreeRule, setAgreeRule] = useState(false);
  const [agreeMind, setAgreeMind] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const fileRef = useRef<HTMLInputElement>(null);

  const currentChip = charachips.find((c) => c.id === charachipId) ?? charachips[0];
  const charas = currentChip?.charas ?? [];
  const chara = charas.find((c) => c.id === charaId) ?? null;

  const selectChara = (id: number | null) => {
    setCharaId(id);
    const selected = charas.find((c) => c.id === id);
    if (selected != null) {
      setCharaName(selected.name);
      setCharaShortName(selected.shortName);
    }
  };

  const length = joinMessage.length;
  const lineCount = joinMessage.split("\n").length;
  const overLimit = length > 400 || lineCount > 20;
  const confirmDisabled =
    overLimit ||
    joinMessage.trim().length === 0 ||
    charaName.trim().length === 0 ||
    charaShortName.trim().length !== 1 ||
    (!isOriginal && charaId == null);

  const request: VillageParticipateRequest = {
    charaId,
    charaName,
    charaShortName,
    requestedSkill,
    secondRequestedSkill,
    joinMessage,
    joinPassword: joinPassword === "" ? null : joinPassword,
    spectator,
  };

  const previewHtml = useMemo(() => toMessageHtml(joinMessage, false, []), [joinMessage]);

  const toConfirm = async () => {
    onError(null);
    try {
      await confirmVillageParticipate(village.id, request);
      setAgreeRule(false);
      setAgreeMind(false);
      setStep("confirm");
    } catch (e) {
      onError(e instanceof ApiError ? e.detail : "入村の確認に失敗しました");
    }
  };

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    onError(null);
    try {
      await onParticipated(request, fileRef.current?.files?.[0] ?? null);
    } finally {
      setSubmitting(false);
    }
  };

  if (step === "confirm") {
    return (
      <Panel title="入村確認">
        <div>
          <p className="mb-[10px]">以下の内容で入村してよろしいですか？</p>
          <div className="flex">
            <div>
              {chara != null && (
                <img
                  src={chara.imageUrl}
                  width={chara.imageWidth}
                  height={chara.imageHeight}
                  alt={charaName}
                />
              )}
            </div>
            <div
              className="message message-normal ml-[5px] flex-1 rounded-[5px] border bg-white p-[9px] break-words text-[#555]"
              dangerouslySetInnerHTML={{ __html: previewHtml }}
            />
          </div>
          <div className="mt-[10px] text-[12px]">
            <p>
              キャラクター: {charaName} ({charaShortName}){spectator && " / 見学者として入村"}
            </p>
          </div>
          {isOriginal && (
            <div className="mt-[10px]">
              <label className="block text-[12px]">
                キャラクター画像 (必須、100KB まで)
                <input ref={fileRef} type="file" accept="image/*" className="mt-[5px] block" />
              </label>
            </div>
          )}
          <div className="mt-[15px] space-y-[5px] text-[12px]">
            <label className="flex cursor-pointer items-center gap-[5px]">
              <input
                type="checkbox"
                checked={agreeRule}
                onChange={() => setAgreeRule(!agreeRule)}
              />
              ルールを確認し、同意します
            </label>
            <label className="flex cursor-pointer items-center gap-[5px]">
              <input
                type="checkbox"
                checked={agreeMind}
                onChange={() => setAgreeMind(!agreeMind)}
              />
              他の参加者への礼節を守り、迷惑をかけないことに同意します
            </label>
          </div>
          <div className="mt-[15px] flex justify-end gap-[10px]">
            <Button variant="default" onClick={() => setStep("input")}>
              戻る
            </Button>
            <Button onClick={submit} disabled={!agreeRule || !agreeMind || submitting}>
              入村する
            </Button>
          </div>
        </div>
      </Panel>
    );
  }

  return (
    <Panel title="入村">
      <div className="space-y-[10px] text-[12px]">
        {!isOriginal && (
          <>
            {charachips.length > 1 && (
              <div>
                <label className="mb-[5px] block">キャラセット</label>
                <select
                  className={selectClass}
                  value={charachipId ?? ""}
                  onChange={(e) => {
                    setCharachipId(Number(e.target.value));
                    setCharaId(null);
                  }}
                  aria-label="キャラセット"
                >
                  {charachips.map((chip) => (
                    <option key={chip.id} value={chip.id}>
                      {chip.name}
                    </option>
                  ))}
                </select>
              </div>
            )}
            <div>
              <label className="mb-[5px] block">キャラクター</label>
              <select
                className={selectClass}
                value={charaId ?? ""}
                onChange={(e) => selectChara(e.target.value === "" ? null : Number(e.target.value))}
                aria-label="キャラクター"
              >
                <option value="">選択してください</option>
                {charas.map((c) => (
                  <option key={c.id} value={c.id}>
                    {c.name}
                  </option>
                ))}
              </select>
            </div>
            {chara != null && (
              <img
                src={chara.imageUrl}
                width={chara.imageWidth}
                height={chara.imageHeight}
                alt={chara.name}
              />
            )}
          </>
        )}
        {isOriginal && (
          <p>この村はオリジナルキャラクター制です。キャラクター画像は確認画面で選択します。</p>
        )}
        <div className="flex gap-[10px]">
          <label className="flex-1">
            キャラクター名 (1〜40 字)
            <input
              type="text"
              className={`${inputClass} mt-[5px]`}
              value={charaName}
              onChange={(e) => setCharaName(e.target.value)}
              aria-label="キャラクター名"
            />
          </label>
          <label className="w-[120px]">
            略称 (1 字)
            <input
              type="text"
              className={`${inlineInputClass} mt-[5px] w-full`}
              value={charaShortName}
              onChange={(e) => setCharaShortName(e.target.value)}
              aria-label="略称"
            />
          </label>
        </div>
        {skillRequest.isAvailableSkillRequest && (
          <div className="flex gap-[10px]">
            <label className="flex-1">
              第 1 希望役職
              <select
                className={`${selectClass} mt-[5px]`}
                value={requestedSkill}
                onChange={(e) => setRequestedSkill(e.target.value)}
                aria-label="第1希望役職"
              >
                {(skillRequest.selectableSkillList ?? []).map((skill) => (
                  <option key={skill.code} value={skill.code}>
                    {skill.name}
                  </option>
                ))}
              </select>
            </label>
            <label className="flex-1">
              第 2 希望役職
              <select
                className={`${selectClass} mt-[5px]`}
                value={secondRequestedSkill}
                onChange={(e) => setSecondRequestedSkill(e.target.value)}
                aria-label="第2希望役職"
              >
                {(skillRequest.selectableSkillList ?? []).map((skill) => (
                  <option key={skill.code} value={skill.code}>
                    {skill.name}
                  </option>
                ))}
              </select>
            </label>
          </div>
        )}
        <label className="block">
          入村発言 (1〜400 字)
          <textarea
            className={`${textareaClass} mt-[5px] min-h-[100px]`}
            value={joinMessage}
            onChange={(e) => setJoinMessage(e.target.value)}
            aria-label="入村発言"
          />
        </label>
        <div className={overLimit ? "text-[#e74c3c]" : ""}>
          文字数: {length}/400, 行数: {lineCount}/20
        </div>
        <label className="block">
          入村パスワード (設定されている村のみ)
          <input
            type="text"
            className={`${inputClass} mt-[5px]`}
            value={joinPassword}
            onChange={(e) => setJoinPassword(e.target.value)}
            aria-label="入村パスワード"
          />
        </label>
        {participate.isAvailableSpectate && (
          <label className="flex cursor-pointer items-center gap-[5px]">
            <input type="checkbox" checked={spectator} onChange={() => setSpectator(!spectator)} />
            見学者として入村
          </label>
        )}
        <div className="flex justify-end">
          <Button onClick={toConfirm} disabled={confirmDisabled}>
            確認画面へ
          </Button>
        </div>
      </div>
    </Panel>
  );
}
