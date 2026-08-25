import { useMemo } from "react";

import { Alert } from "~/components/ui/Alert";
import type { VillageDetailView, VillageParticipantView } from "~/features/village/api";
import { formatSkillHistory } from "~/features/village/participants";
import { SAY_VARIANTS, bubbleClass, toMessageHtml } from "../message/message";
import { MessageType } from "../message/messageType";
import { StableHtml } from "../message/StableHtml";

export type ReportOptions = {
  /** 名前の横に役職履歴・状態を表示する */
  showSkillHistory: boolean;
  /** 役職説明欄を表示する */
  showSkillDescription: boolean;
  /** 能力行使履歴を表示する (本人ログイン時のみデータあり) */
  showAbilityHistory: boolean;
};

/**
 * 参加報告の画像化対象プレビュー。村ページの発言欄と同じ部品 (toMessageHtml / bubbleClass) で
 * 未投稿の発言を吹き出し描画し、役職履歴・役職説明・能力行使履歴を添える。
 */
export function ReportPreview({
  village,
  participant,
  imageUrl,
  messageType,
  messageText,
  convertDisable,
  randomKeywords,
  abilityHistories,
  options,
}: {
  village: VillageDetailView;
  participant: VillageParticipantView;
  imageUrl: string | null;
  messageType: string;
  messageText: string;
  convertDisable: boolean;
  randomKeywords: string[];
  abilityHistories: string[];
  options: ReportOptions;
}) {
  const html = useMemo(
    () => toMessageHtml(messageText, convertDisable, randomKeywords),
    [messageText, convertDisable, randomKeywords],
  );
  const variant = SAY_VARIANTS[messageType] ?? SAY_VARIANTS[MessageType.NORMAL_SAY];
  const skillHistory = formatSkillHistory(participant);

  return (
    <div className="bg-wm-base p-[15px] text-white">
      <div>
        {participant.name}
        {options.showSkillHistory && skillHistory !== "" && `（${skillHistory}）`}
      </div>
      <div className="flex">
        <div>
          {imageUrl != null && (
            <img
              src={imageUrl}
              width={participant.chara.size.width}
              height={participant.chara.size.height}
              alt={participant.charaName.name}
            />
          )}
        </div>
        <div className={`ml-[5px] min-w-0 flex-1 ${bubbleClass(variant.styleKey)}`}>
          <StableHtml html={html} />
        </div>
      </div>
      {options.showSkillDescription && participant.skill != null && (
        <Alert className="mt-[10px]">
          <p dangerouslySetInnerHTML={{ __html: participant.skill.description }} />
          {participant.camp != null && (
            <p>
              あなたは <strong>{participant.camp.name}</strong> です。
            </p>
          )}
        </Alert>
      )}
      {options.showAbilityHistory && abilityHistories.length > 0 && (
        <div className="mt-[10px]">
          <p>能力セット履歴</p>
          {abilityHistories.map((history, index) => (
            <p key={index}>{history}</p>
          ))}
        </div>
      )}
      <div className="mt-[10px] text-right text-gray-300">
        {String(village.id).padStart(4, "0")}. {village.name} - WOLF MANSION
      </div>
    </div>
  );
}
