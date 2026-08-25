import { useEffect, useState } from "react";

import { Button } from "~/components/ui/Button";
import { skillDescriptions } from "~/features/skills/descriptions";
import type { ParticipantSituationView } from "~/features/village/api";
import { useVillageContext, useVillageId } from "~/features/village/VillageContext";
import { RestrictionTable, Row } from "../modal/VillageInfoModal";

const STORAGE_KEY = "already_skill_confirm";

function confirmedVillages(): string[] {
  try {
    const value = localStorage.getItem(STORAGE_KEY);
    return value == null || value === "" ? [] : value.split(",");
  } catch {
    return [];
  }
}

/**
 * 村が開始されて役職が割り当たった後、最初の 1 回だけ役職と主要ルールを表示する。
 * 確認済みの村 ID はブラウザに記憶し、次回以降は出さない。
 */
export function InitialSkillModal({
  mySituation,
  suppressed = false,
}: {
  mySituation: ParticipantSituationView | null | undefined;
  /** 年齢制限確認が済むまで出さない (確認モーダルの逐次表示) */
  suppressed?: boolean;
}) {
  const villageId = useVillageId();
  const village = useVillageContext();
  const settings = village.info;
  const skill = mySituation?.myself?.skill;
  const [open, setOpen] = useState(false);

  useEffect(() => {
    if (skill == null || suppressed || !village.status.isProgress) {
      setOpen(false);
      return;
    }
    setOpen(!confirmedVillages().includes(String(villageId)));
  }, [villageId, skill, suppressed, village.status.isProgress]);

  if (!open || skill == null) return null;

  const confirm = () => {
    try {
      const villages = confirmedVillages();
      if (!villages.includes(String(villageId))) villages.push(String(villageId));
      localStorage.setItem(STORAGE_KEY, villages.join(","));
    } catch {
      // 記憶できなくても表示は続行する
    }
    setOpen(false);
  };

  const descriptionItems = skillDescriptions[skill.code.toLowerCase()] ?? [];

  return (
    <div className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto bg-black/50 p-4">
      <div className="my-8 w-full max-w-lg rounded-[6px] border border-black/20 bg-surface p-[15px] text-white shadow-lg">
        <h5 className="mb-[10px] text-village-heading font-bold">
          村が開始されました。役職とルールは以下の通りです。
        </h5>
        <h5 className="mt-[10px] mb-[5px] text-village-heading">役職</h5>
        <p>
          あなたは <strong>{skill.name}</strong> です。
        </p>
        {descriptionItems.map((item, index) => (
          <p key={index} className="mt-[5px] whitespace-pre-line">
            {item.content}
          </p>
        ))}
        <h5 className="mt-[15px] mb-[5px] text-village-heading">村の設定</h5>
        <table className="w-full border-collapse">
          <tbody>
            <Row label="更新間隔">{settings.dayChangeInterval}</Row>
            <Row label="投票形式">{settings.voteType}</Row>
            <Row label="同一人狼による連続襲撃">
              {settings.isAvailableSameWolfAttack
                ? "可能"
                : "不可(狼2以下編成の場合は可能に変更されます)"}
            </Row>
            <Row label="狩人による連続護衛">
              {settings.isAvailableGuardSameTarget ? "可能" : "不可"}
            </Row>
            <Row label="突然死">{settings.isAvailableSuddenlyDeath ? "あり" : "なし"}</Row>
            <Row label="コミット">{settings.isAvailableCommit ? "あり" : "なし"}</Row>
            <Row label="ダミーキャラ">{settings.dummyCharaName}</Row>
            <Row label="役職構成">
              {settings.isRandomOrganization ? (
                "闇鍋編成のため非表示"
              ) : (
                <span className="whitespace-pre-line">{settings.organization}</span>
              )}
            </Row>
            <Row label="発言制限（通常発言）">
              <RestrictionTable
                headerLabel="役職"
                rows={(settings.sayRestrictList ?? []).map((r) => ({
                  name: r.skillName,
                  isRestrict: r.isRestrict,
                  length: r.length ?? undefined,
                  count: r.count ?? undefined,
                }))}
                emptyText="制限がかかっている役職はありません。"
                leadText="制限がかかっている役職のみ表示しています。"
              />
            </Row>
            <Row label="発言制限（役職発言）">
              <RestrictionTable
                headerLabel="発言種別"
                rows={(settings.skillSayRestrictList ?? []).map((r) => ({
                  name: r.messageTypeName,
                  isRestrict: r.isRestrict,
                  length: r.length ?? undefined,
                  count: r.count ?? undefined,
                }))}
                emptyText="制限がかかっている発言種別はありません。"
                leadText="制限がかかっている発言種別のみ表示しています。"
              />
            </Row>
          </tbody>
        </table>
        <div className="mt-[15px] flex justify-end">
          <Button onClick={confirm}>確認したので次回以降表示しない</Button>
        </div>
      </div>
    </div>
  );
}
