import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router";

import { Button } from "~/components/ui/Button";
import { FileUpload } from "~/components/ui/FileUpload";
import { VillageFormRow } from "~/components/ui/Form";
import { inputClass, selectClass, textareaClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import {
  confirmVillageParticipate,
  type ParticipantSituationView,
  type VillageDetailView,
  type VillageParticipateRequest,
} from "~/features/village/api";
import { ApiError } from "~/lib/api";
import { toMessageHtml } from "../message/message";

const OMAKASE = "LEFTOVER";

type CharaLike = {
  id: number;
  name: string;
  shortName: string;
  images: { list: { faceType: { code: string }; url: string }[] };
  size: { width: number; height: number };
};

function defaultImageUrl(c: CharaLike): string {
  return (
    c.images.list.find((i) => i.faceType.code === "NORMAL")?.url ?? c.images.list[0]?.url ?? ""
  );
}

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
  const [charaModalOpen, setCharaModalOpen] = useState(false);
  const [charaImageFile, setCharaImageFile] = useState<File | null>(null);

  const charaImageUrl = useMemo(
    () => (charaImageFile ? URL.createObjectURL(charaImageFile) : null),
    [charaImageFile],
  );
  useEffect(() => {
    return () => {
      if (charaImageUrl) URL.revokeObjectURL(charaImageUrl);
    };
  }, [charaImageUrl]);

  const currentChip = charachips.find((c) => c.id === charachipId) ?? charachips[0];
  const charas = currentChip?.charas?.list ?? [];
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
    (!isOriginal && charaId == null) ||
    (isOriginal && charaImageFile == null);

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
      await onParticipated(request, charaImageFile);
    } finally {
      setSubmitting(false);
    }
  };

  const charaNotSelected = !isOriginal && charaId == null;

  return (
    <Panel title="入村" storageKey="participateform">
      <div className="space-y-[10px]">
        {participate.isAvailableSpectate && (
          <VillageFormRow label="見学" labelWidth="wide">
            <label className="flex cursor-pointer items-center gap-[5px]">
              <input
                type="checkbox"
                checked={spectator}
                onChange={() => setSpectator(!spectator)}
              />
              見学者として入村
            </label>
          </VillageFormRow>
        )}

        {!isOriginal && (
          <>
            <VillageFormRow label="キャラクター" labelWidth="wide">
              <select
                className={selectClass}
                value={charachipId ?? ""}
                onChange={(e) => {
                  setCharachipId(Number(e.target.value));
                  setCharaId(null);
                  setCharaName("");
                  setCharaShortName("");
                }}
                aria-label="キャラセット"
              >
                {charachips.map((chip) => (
                  <option key={chip.id} value={chip.id}>
                    {chip.name}
                  </option>
                ))}
              </select>
            </VillageFormRow>
            <VillageFormRow labelWidth="wide">
              <div className="flex items-center gap-[10px]">
                <select
                  className={`${selectClass} flex-1`}
                  value={charaId ?? ""}
                  onChange={(e) =>
                    selectChara(e.target.value === "" ? null : Number(e.target.value))
                  }
                  aria-label="キャラクター"
                >
                  <option value="">選択してください</option>
                  {charas.map((c) => (
                    <option key={c.id} value={c.id}>
                      {c.name}
                    </option>
                  ))}
                </select>
                <Button onClick={() => setCharaModalOpen(true)}>画像から選択</Button>
              </div>
            </VillageFormRow>
          </>
        )}

        <VillageFormRow label="キャラクター名" labelWidth="wide">
          <input
            type="text"
            className={inputClass}
            value={charaName}
            onChange={(e) => setCharaName(e.target.value)}
            disabled={charaNotSelected}
            aria-label="キャラクター名"
          />
        </VillageFormRow>
        <VillageFormRow label="略称" labelWidth="wide">
          <input
            type="text"
            className={inputClass}
            value={charaShortName}
            onChange={(e) => setCharaShortName(e.target.value)}
            disabled={charaNotSelected}
            aria-label="略称"
          />
        </VillageFormRow>

        {isOriginal && (
          <VillageFormRow label="キャラクター画像" labelWidth="wide" align="start">
            <FileUpload
              accept="image/*"
              maxSizeBytes={100_000}
              imagePreviewSize={60}
              onSelect={setCharaImageFile}
            >
              <ul className="list-disc pl-[20px]">
                <li>
                  画像は60x60pxで表示されるため、解像度は60x60や120x120など60の倍数の大きさとすることを推奨します。
                </li>
                <li>100kByteを超える画像はアップロードできません。</li>
                <li>
                  登録した時点で、
                  <Link
                    to="/about#original"
                    target="_blank"
                    className="text-wm-accent hover:underline"
                  >
                    オリジナルキャラクターおよび画像の登録
                  </Link>
                  について了承したものとみなします。
                </li>
              </ul>
            </FileUpload>
          </VillageFormRow>
        )}

        {village.setting.rule.isPossibleSkillRequest && (
          <>
            <VillageFormRow label="役職第一希望" labelWidth="wide">
              <select
                className={selectClass}
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
            </VillageFormRow>
            <VillageFormRow label="役職第二希望" labelWidth="wide">
              <select
                className={selectClass}
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
            </VillageFormRow>
          </>
        )}

        <VillageFormRow label="発言" labelWidth="wide" align="start">
          <textarea
            className={`${textareaClass} min-h-[150px]`}
            value={joinMessage}
            onChange={(e) => setJoinMessage(e.target.value)}
            aria-label="入村発言"
            placeholder="人狼なんているわけないじゃん。みんな大げさだなあ"
          />
          <div className={`mt-[3px] ${overLimit ? "text-[#e74c3c]" : ""}`}>
            文字数: {length}/400, 行数: {lineCount}/20
          </div>
        </VillageFormRow>

        {village.setting.hasJoinPassword && (
          <VillageFormRow label="入村パスワード" labelWidth="wide">
            <input
              type="text"
              className={inputClass}
              value={joinPassword}
              onChange={(e) => setJoinPassword(e.target.value)}
              aria-label="入村パスワード"
            />
          </VillageFormRow>
        )}

        <div className="flex justify-end">
          <Button onClick={toConfirm} disabled={confirmDisabled}>
            入村確認へ
          </Button>
        </div>
        {village.setting.rule.isPossibleSkillRequest && (
          <div className="flex justify-end">
            <Link
              to="/rule#skill-request"
              target="_blank"
              className="text-wm-accent hover:underline"
            >
              役職希望について
            </Link>
          </div>
        )}
      </div>
      {charaModalOpen && (
        <div
          className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto bg-black/50 p-4"
          onClick={() => setCharaModalOpen(false)}
        >
          <div
            className="my-8 w-full max-w-2xl rounded-[6px] border border-black/20 bg-[#303030] p-[15px] text-white shadow-lg"
            onClick={(e) => e.stopPropagation()}
          >
            <h4 className="mb-[10px] font-bold">キャラクター選択</h4>
            <div className="grid grid-cols-2 gap-[5px] sm:grid-cols-3">
              {charas.map((c) => (
                <div key={c.id} className="border border-[#464545] p-[5px] text-center">
                  <div className="flex justify-center">
                    <img
                      src={defaultImageUrl(c)}
                      alt={c.name}
                      width={c.size.width}
                      height={c.size.height}
                    />
                  </div>
                  <div>{c.name}</div>
                  <Button
                    size="xs"
                    className="w-full"
                    onClick={() => {
                      selectChara(c.id);
                      setCharaModalOpen(false);
                    }}
                  >
                    選択
                  </Button>
                </div>
              ))}
            </div>
            <div className="mt-[10px] flex justify-end">
              <Button variant="default" onClick={() => setCharaModalOpen(false)}>
                閉じる
              </Button>
            </div>
          </div>
        </div>
      )}
      {step === "confirm" && (
        <div
          className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto bg-black/50 p-4"
          onClick={() => setStep("input")}
        >
          <div
            className="my-8 w-full max-w-2xl rounded-[6px] border border-black/20 bg-[#303030] p-[15px] text-white shadow-lg"
            onClick={(e) => e.stopPropagation()}
          >
            <h4 className="mb-[10px] font-bold">入村確認</h4>
            <p className="text-village-sm">
              [{charaShortName}] {charaName}
            </p>
            <div className="flex pt-[5px]">
              <div>
                {isOriginal && charaImageUrl ? (
                  <img src={charaImageUrl} width={60} height={60} alt={charaName} />
                ) : chara != null ? (
                  <img
                    src={defaultImageUrl(chara)}
                    width={chara.size.width}
                    height={chara.size.height}
                    alt={charaName}
                  />
                ) : null}
              </div>
              <div
                className="message message-normal ml-[5px] flex-1 rounded-[5px] border bg-white p-[9px] break-words text-[#555]"
                style={
                  chara != null
                    ? { minHeight: chara.size.height }
                    : isOriginal
                      ? { minHeight: 60 }
                      : undefined
                }
                dangerouslySetInnerHTML={{ __html: previewHtml }}
              />
            </div>
            <div className="mt-[10px] space-y-[5px]">
              <label className="flex cursor-pointer items-start gap-[5px]">
                <input
                  type="checkbox"
                  className="mt-[4px]"
                  checked={agreeRule}
                  onChange={() => setAgreeRule(!agreeRule)}
                />
                <span>
                  <Link to="/rule" target="_blank" className="text-wm-accent hover:underline">
                    ルール
                  </Link>
                  を確認し、人狼館の事件簿村ルール特有の禁止事項について理解しました。
                </span>
              </label>
              <label className="flex cursor-pointer items-start gap-[5px]">
                <input
                  type="checkbox"
                  className="mt-[4px]"
                  checked={agreeMind}
                  onChange={() => setAgreeMind(!agreeMind)}
                />
                <span>
                  他者への礼節を欠いたり、正常な運営を妨げるような行為を行なった場合、管理人の裁量により処罰される可能性があることについて理解しました。
                </span>
              </label>
            </div>
            <div className="mt-[15px] flex justify-between">
              <Button variant="default" onClick={() => setStep("input")}>
                戻る
              </Button>
              <Button onClick={submit} disabled={!agreeRule || !agreeMind || submitting}>
                入村する
              </Button>
            </div>
          </div>
        </div>
      )}
    </Panel>
  );
}
