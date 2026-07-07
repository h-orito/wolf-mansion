import { Fragment, type ReactNode } from "react";

import { Button, LinkButton } from "~/components/ui/Button";
import { Modal } from "~/components/ui/Modal";
import { TextLink } from "~/components/ui/TextLink";
import type { VillageSettingsContent } from "~/features/village/api";
import { useVillageContext } from "~/features/village/VillageContext";
import { formatStartDatetime } from "~/lib/datetime";

const thClass = "w-[40%] border border-border bg-surface-alt p-[5px] text-left align-top";
const tdClass = "border border-border p-[5px]";
const innerThClass = "border border-border bg-surface-alt p-[3px] text-left";
const innerTdClass = "border border-border p-[3px]";

export function Row({ label, children }: { label: string; children: ReactNode }) {
  return (
    <tr>
      <th className={thClass}>{label}</th>
      <td className={tdClass}>{children}</td>
    </tr>
  );
}

/** 1回あたりの発言文字数 * 1日あたりの発言回数 の制限テーブル。 */
export function RestrictionTable({
  headerLabel,
  rows,
  emptyText,
  leadText,
}: {
  headerLabel: string;
  rows: {
    name: string;
    isRestrict: boolean;
    length: number | undefined;
    count: number | undefined;
  }[];
  emptyText: string;
  leadText: string;
}) {
  if (rows.length === 0) return <span>{emptyText}</span>;
  return (
    <div>
      {leadText}
      <table className="mt-[5px] border-collapse text-[10.32px]">
        <thead>
          <tr>
            <th className={innerThClass}>{headerLabel}</th>
            <th className={innerThClass}>制限</th>
            <th className={innerThClass}>1回あたりの発言文字数 * 1日あたりの発言回数</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((row) => (
            <tr key={row.name}>
              <td className={innerTdClass}>{row.name}</td>
              <td className={innerTdClass}>{row.isRestrict ? "あり" : "無制限"}</td>
              <td className={innerTdClass}>
                {row.isRestrict ? `${row.length} * ${row.count}` : ""}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

/** 闇鍋編成の人狼カウント + 陣営・役職ごとの配分テーブル。 */
function RandomOrganizationTable({ settings }: { settings: VillageSettingsContent }) {
  return (
    <table className="border-collapse text-[10.32px]">
      <tbody>
        <tr>
          <td className="border border-border bg-danger p-[3px] text-white" colSpan={9}>
            <strong>人狼カウント</strong>
          </td>
        </tr>
        <tr>
          <td className={innerTdClass}>人狼カウント</td>
          <td className={innerTdClass}>最少人数</td>
          <td className={innerTdClass}>{settings.wolfAllocation?.minNum}</td>
          <td className={innerTdClass}>最多人数</td>
          <td className={innerTdClass}>{settings.wolfAllocation?.maxNum}</td>
          <td className={innerTdClass} colSpan={4}></td>
        </tr>
        {(settings.campAllocationList ?? []).map((camp) => (
          <Fragment key={camp.campCode}>
            <tr>
              <td className="border border-border bg-info p-[3px] text-white" colSpan={9}>
                <strong>{camp.campName}</strong>
              </td>
            </tr>
            <tr>
              <td className={innerTdClass} colSpan={9}>
                陣営全体の配分
              </td>
            </tr>
            <tr>
              <td className={innerTdClass}>{camp.campName}</td>
              <td className={innerTdClass}>最少人数</td>
              <td className={innerTdClass}>{camp.minNum}</td>
              <td className={innerTdClass}>最多人数</td>
              <td className={innerTdClass}>{camp.maxNum}</td>
              <td className={innerTdClass}>配分</td>
              <td className={innerTdClass}>{camp.allocation}</td>
              <td className={innerTdClass}>転生配分</td>
              <td className={innerTdClass}>{camp.reincarnationAllocation}</td>
            </tr>
            <tr>
              <td className={innerTdClass} colSpan={9}>
                役職ごとの配分
              </td>
            </tr>
            {(camp.skillAllocation ?? []).map((skill) => (
              <tr key={`${camp.campCode}-${skill.skillCode}`}>
                <td className={innerTdClass}>{skill.skillName}</td>
                <td className={innerTdClass}>最少人数</td>
                <td className={innerTdClass}>{skill.minNum}</td>
                <td className={innerTdClass}>最多人数</td>
                <td className={innerTdClass}>{skill.maxNum}</td>
                <td className={innerTdClass}>配分</td>
                <td className={innerTdClass}>{skill.allocation}</td>
                <td className={innerTdClass}>転生配分</td>
                <td className={innerTdClass}>{skill.reincarnationAllocation}</td>
              </tr>
            ))}
          </Fragment>
        ))}
      </tbody>
    </table>
  );
}

/** 村の全設定の読み取り表示。村主のみ設定変更への導線が活性になる。 */
export function VillageInfoModal({
  open,
  onClose,
  canModifySetting,
}: {
  open: boolean;
  onClose: () => void;
  canModifySetting: boolean;
}) {
  const village = useVillageContext();
  const settings = village.info;

  return (
    <Modal open={open} onClose={onClose} title="村情報" size="wide">
      <div className="p-[15px] text-[12px]">
        <table className="w-full border-collapse">
          <tbody>
            {settings.welcomeRange != null && <Row label="募集範囲">{settings.welcomeRange}</Row>}
            <Row label="最少開始人数">{settings.startPersonMinNum}</Row>
            <Row label="定員">{settings.personMaxNum}</Row>
            <Row label="開始日時">{formatStartDatetime(settings.startDatetime)}</Row>
            <Row label="更新間隔">{settings.dayChangeInterval}</Row>
            <Row label="投票形式">{settings.voteType}</Row>
            <Row label="役職希望">{settings.skillRequestType}</Row>
            <Row label="見学入村">
              {settings.isAvailableSpectate ? "可能（[キャラチップ人数 - 定員]人まで）" : "不可"}
            </Row>
            <Row label="プロデューサー機能">{settings.creatorIsProducer ? "あり" : "なし"}</Row>
            <Row label="同一人狼による連続襲撃">
              {settings.isAvailableSameWolfAttack
                ? "可能"
                : "不可 (開始時点で狼2以下編成の場合は可能に変更されます)"}
            </Row>
            <Row label="狩人による連続護衛">
              {settings.isAvailableGuardSameTarget ? "可能" : "不可"}
            </Row>
            <Row label="転生時の役職候補">
              {settings.isReincarnationSkillAll ? "全役職" : "編成に含まれる役職のみ"}
            </Row>
            <Row label="墓下見学役職公開">{settings.isOpenSkillInGrave ? "公開" : "非公開"}</Row>
            <Row label="墓下見学と地上との会話">
              {settings.isVisibleGraveSpectateMessage ? "可能" : "不可"}
            </Row>
            <Row label="秘話">
              {settings.allowedSecretSayCode === "NOTHING"
                ? "なし"
                : settings.allowedSecretSayCode === "ONLY_CREATOR"
                  ? "村建てとのみ可能"
                  : "全員可能"}
            </Row>
            <Row label="突然死">{settings.isAvailableSuddenlyDeath ? "あり" : "なし"}</Row>
            <Row label="コミット">{settings.isAvailableCommit ? "あり" : "なし"}</Row>
            <Row label="キャラセット">
              {(settings.charachips ?? []).map((charachip, index) => (
                <span key={charachip.id}>
                  {index > 0 && "、"}
                  {settings.shouldOriginalImage ? (
                    charachip.name
                  ) : (
                    <TextLink to={`/chara-group/${charachip.id}`} target="_blank">
                      {charachip.name}
                    </TextLink>
                  )}
                </span>
              ))}
            </Row>
            <Row label="ダミーキャラ">{settings.dummyCharaName}</Row>
            <Row label="入村パスワード">{settings.isRequiredJoinPassword ? "あり" : "なし"}</Row>
            <Row label="館を建てたプレイヤー">{settings.createPlayerName}</Row>
            {settings.isRandomOrganization ? (
              <Row label="役職構成（闇鍋）">
                <RandomOrganizationTable settings={settings} />
              </Row>
            ) : (
              <Row label="役職構成">
                <span className="whitespace-pre-line">{settings.organization}</span>
              </Row>
            )}
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
        <p className="mt-[10px]">RP設定</p>
        <table className="mt-[5px] w-full border-collapse">
          <tbody>
            <Row label="年齢制限">{settings.ageLimit}</Row>
            <Row label="アクション">{settings.isAvailableAction ? "可能" : "不可"}</Row>
            {settings.isAvailableAction && (
              <Row label="発言制限（RP発言）">
                <RestrictionTable
                  headerLabel="発言種別"
                  rows={(settings.rpSayRestrictList ?? []).map((r) => ({
                    name: r.messageTypeName,
                    isRestrict: r.isRestrict,
                    length: r.length ?? undefined,
                    count: r.count ?? undefined,
                  }))}
                  emptyText="制限がかかっている発言種別はありません。"
                  leadText=""
                />
              </Row>
            )}
          </tbody>
        </table>
        <p className="mt-[10px] text-[10.32px]">館を建てたプレイヤーのみ設定を変更できます。</p>
        <div className="mt-[10px] flex justify-end gap-[10px]">
          {canModifySetting && (
            <LinkButton to={`/village/${village.id}/settings`}>設定変更</LinkButton>
          )}
          <Button variant="default" onClick={onClose}>
            閉じる
          </Button>
        </div>
      </div>
    </Modal>
  );
}
