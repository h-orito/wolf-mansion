import { useState } from "react";

import { AlertList } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { selectClass } from "~/components/ui/Input";
import type {
  ParticipantSituationView,
  VillageActionRequest,
  VillageFilterParticipantContent,
} from "~/features/village/api";

/**
 * アクション発言フォーム (発言とは別パネル)。「{自分}は、{対象}{本文}」を結合して投稿する。
 * 制限超過でも入力はできるが確認ボタンを無効にする。
 */
export function ActionPanel({
  mySituation,
  participants,
  onConfirm,
  registerOnDone,
}: {
  mySituation: ParticipantSituationView;
  participants: VillageFilterParticipantContent[];
  onConfirm: (request: VillageActionRequest) => void;
  registerOnDone: (kind: "say" | "action" | "creatorSay", fn: () => void) => void;
}) {
  const myself = mySituation.myself;
  const restrict = mySituation.say.selectableMessageTypeList?.find(
    (t) => t.messageType.code === "ACTION",
  )?.restrict;

  const [target, setTarget] = useState("");
  const [message, setMessage] = useState("");
  const [convertDisable, setConvertDisable] = useState(false);
  registerOnDone("action", () => {
    setTarget("");
    setMessage("");
  });

  if (myself == null) return null;
  const prefix = `${myself.name}は、`;
  // 対象は自分以外の参加者を表示名で指定する
  const targets = participants.filter((p) => p.id !== myself.id).map((p) => p.name ?? "");

  const totalLength = (prefix + target + message).length;
  const maxLength = restrict?.maxLength ?? 400;
  const leftCount = restrict?.remainingCount ?? null;
  const maxCount = restrict?.maxCount ?? null;
  const overLimit = totalLength > maxLength || (leftCount != null && leftCount <= 0);
  const submitDisabled = overLimit || message.trim().length === 0;

  return (
    <Panel title="アクション" storageKey="actionform" fixable>
      <div>
        <AlertList className="mb-[10px]">
          <li>進行中は、推理、まとめ、および推理に繋がる内容は発言しないでください。</li>
        </AlertList>
        <p>{prefix}</p>
        <select
          className={selectClass}
          value={target}
          onChange={(e) => setTarget(e.target.value)}
          aria-label="アクションの対象"
        >
          <option value="">選択しない</option>
          <option value="全員">全員</option>
          {targets.map((name) => (
            <option key={name} value={name}>
              {name}
            </option>
          ))}
        </select>
        <input
          type="text"
          className="mt-[10px] w-full rounded border border-[#464545] bg-white p-[6px] text-[#555]"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          aria-label="アクション本文"
          placeholder="自由入力"
        />
        <div className={`mt-[5px] ${overLimit ? "text-[#e74c3c]" : ""}`}>
          {leftCount != null && maxCount != null && `残り${leftCount}/${maxCount}回, `}
          文字数: {totalLength}/{maxLength}
        </div>
        <div className="mt-[10px] flex items-center justify-between">
          <label className="flex cursor-pointer items-center gap-[5px]">
            <input
              type="checkbox"
              checked={convertDisable}
              onChange={() => setConvertDisable(!convertDisable)}
            />
            装飾・変換無効
          </label>
          <Button
            onClick={() =>
              onConfirm({
                myself: prefix,
                target: target === "" ? null : target,
                message,
                convertDisable,
              })
            }
            disabled={submitDisabled}
          >
            確認画面へ
          </Button>
        </div>
      </div>
    </Panel>
  );
}
