import type { ReactNode } from "react";

import { Button } from "~/components/ui/Button";
import { Modal } from "~/components/ui/Modal";
import type { Chara } from "~/features/charachips/api";
import { useCharachipDetails, useCharachipList } from "~/features/charachips/useCharachips";
import { assetUrl } from "~/lib/api";
import type { NewVillageFormInput } from "~/features/village-form/schema";

const tableClass =
  "w-full border-collapse " +
  "[&_th]:border [&_th]:border-[#464545] [&_th]:p-[5px] [&_th]:text-left [&_th]:align-middle " +
  "[&_td]:border [&_td]:border-[#464545] [&_td]:p-[5px] [&_td]:align-middle";

const nestedTableClass =
  "border-collapse text-[10.32px] " +
  "[&_th]:border [&_th]:border-[#464545] [&_th]:p-[5px] [&_th]:text-left [&_th]:align-middle " +
  "[&_td]:border [&_td]:border-[#464545] [&_td]:p-[5px] [&_td]:align-middle";

function pad2(value: number): string {
  return String(value).padStart(2, "0");
}

/** 設定一覧のセクション見出し行。 */
function SectionRow({ title }: { title: string }) {
  return (
    <tr>
      <th colSpan={2}>
        <span className="text-[18px]">{title}</span>
      </th>
    </tr>
  );
}

function ValueRow({ label, children }: { label: string; children: ReactNode }) {
  return (
    <tr>
      <th className="w-1/4">{label}</th>
      <td>{children}</td>
    </tr>
  );
}

/** ダミーキャラ発言のプレビュー (キャラ画像 + 通常発言の吹き出し)。 */
function MessagePreview({
  message,
  chara,
  originalImageUrl,
  original,
}: {
  message: string;
  chara: Chara | undefined;
  originalImageUrl: string | null;
  original: boolean;
}) {
  const image =
    original || !chara
      ? { url: originalImageUrl ?? assetUrl("/app/images/placeholder.png"), width: 60, height: 60 }
      : {
          url: (chara.images.list.find((i) => i.faceType.code === "NORMAL") ?? chara.images.list[0])
            ?.url,
          width: chara.size.width,
          height: chara.size.height,
        };
  return (
    <div className="flex">
      <div>
        {image.url && <img src={image.url} width={image.width} height={image.height} alt="" />}
      </div>
      <div
        className="ml-[5px] flex-1 rounded-[5px] border border-[#e3e3e3] bg-white p-[9px] break-words whitespace-pre-line text-[#555555]"
        style={{ minHeight: image.height, fontFamily: "sans-serif" }}
      >
        {message}
      </div>
    </div>
  );
}

function ReadOnlySayRestrictTable({
  targetHeader,
  rows,
}: {
  targetHeader: string;
  rows: {
    key: string;
    label: string;
    restrict: boolean;
    length: number | null;
    count: number | null;
  }[];
}) {
  return (
    <table className={nestedTableClass}>
      <thead>
        <tr>
          <th>{targetHeader}</th>
          <th>制限</th>
          <th>1回あたりの発言文字数 * 1日あたりの発言回数</th>
        </tr>
      </thead>
      <tbody>
        {rows.map((row) => (
          <tr key={row.key}>
            <td>{row.label}</td>
            <td>{row.restrict ? "する" : "しない"}</td>
            <td>{row.restrict ? `${row.length} * ${row.count}` : ""}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

function ReadOnlyRandomOrganizationTable({ values }: { values: NewVillageFormInput }) {
  return (
    <table className={nestedTableClass}>
      <tbody>
        <tr>
          <td className="bg-[#e74c3c] text-white" colSpan={9}>
            <strong>人狼カウント</strong>
          </td>
        </tr>
        <tr>
          <td>人狼カウント</td>
          <td>最少人数</td>
          <td>{values.wolfAllocation.minNum}</td>
          <td>最多人数</td>
          <td>{values.wolfAllocation.maxNum}</td>
          <td colSpan={4}></td>
        </tr>
        {values.campAllocationList.map((camp) => (
          <ReadOnlyCampRows key={camp.campCode} camp={camp} />
        ))}
      </tbody>
    </table>
  );
}

function ReadOnlyCampRows({ camp }: { camp: NewVillageFormInput["campAllocationList"][number] }) {
  return (
    <>
      <tr>
        <td className="bg-[#3498db] text-white" colSpan={9}>
          <strong>{camp.campName}</strong>
        </td>
      </tr>
      <tr>
        <td colSpan={9}>陣営全体の配分</td>
      </tr>
      <tr>
        <td>{camp.campName}</td>
        <td>最少人数</td>
        <td>{camp.minNum}</td>
        <td>最多人数</td>
        <td>{camp.maxNum}</td>
        <td>配分（0-100）</td>
        <td>{camp.allocation}</td>
        <td>転生配分（0-100）</td>
        <td>{camp.reincarnationAllocation}</td>
      </tr>
      <tr>
        <td colSpan={9}>役職ごとの配分</td>
      </tr>
      {camp.skillAllocation.map((skill) => (
        <tr key={skill.skillCode}>
          <td>{skill.skillName}</td>
          <td>最少人数</td>
          <td>{skill.minNum}</td>
          <td>最多人数</td>
          <td>{skill.maxNum}</td>
          <td>配分（0-100）</td>
          <td>{skill.allocation}</td>
          <td>転生配分（0-100）</td>
          <td>{skill.reincarnationAllocation}</td>
        </tr>
      ))}
    </>
  );
}

/**
 * 村作成の確認モーダル。設定一覧と発言プレビューを表示し、「作成」で API へ送信する。
 * オリジナル画像の入力は元フォーム側で行うため、ここでは画像行は出さずプレビューにのみ反映する。
 */
export function ConfirmModal({
  open,
  values,
  originalImageUrl,
  creating,
  errorMessage,
  onBack,
  onCreate,
}: {
  open: boolean;
  values: NewVillageFormInput | null;
  originalImageUrl: string | null;
  creating: boolean;
  errorMessage: string | null;
  onBack: () => void;
  onCreate: () => void;
}) {
  const { data: charachips } = useCharachipList();
  const { charas } = useCharachipDetails(
    !open || !values || values.shouldOriginalImage ? [] : values.characterSetId,
  );
  if (!open || !values) return null;

  const original = values.shouldOriginalImage;
  const selectedChara = charas.find((c) => c.id === values.dummyCharaId);
  const characterSetName = (charachips ?? [])
    .filter((c) => values.characterSetId.includes(c.id))
    .map((c) => c.name)
    .join("、");
  const interval = `${pad2(values.dayChangeIntervalHours)}時間${pad2(values.dayChangeIntervalMinutes)}分${pad2(values.dayChangeIntervalSeconds)}秒`;
  const startDateTime = `${values.startYear}/${pad2(values.startMonth)}/${pad2(values.startDay)} ${pad2(values.startHour)}:${pad2(values.startMinute)}`;

  return (
    <Modal open={open} onClose={creating ? () => {} : onBack} title="村作成確認" size="wide">
      <table className={tableClass}>
        <tbody>
          <SectionRow title="基本設定" />
          <ValueRow label="村名">{values.villageName}</ValueRow>
          <ValueRow label="募集範囲">
            {values.welcomeRange === "ANYONE_WELCOME"
              ? "誰歓"
              : values.welcomeRange === "RELATIVES_ONLY"
                ? "身内"
                : "その他"}
          </ValueRow>
          <ValueRow label="最少開始人数">{values.startPersonMinNum}人</ValueRow>
          <ValueRow label="定員">{values.personMaxNum}人</ValueRow>
          <ValueRow label="更新間隔">{interval}</ValueRow>
          <ValueRow label="開始日時">{startDateTime}</ValueRow>
          <SectionRow title="キャラチップ設定" />
          {!original && <ValueRow label="キャラクターセット">{characterSetName}</ValueRow>}
          {!original && <ValueRow label="ダミーキャラ">{selectedChara?.name}</ValueRow>}
          <ValueRow label="ダミーキャラ名">{values.dummyCharaName}</ValueRow>
          <ValueRow label="ダミーキャラ名略称">{values.dummyCharaShortName}</ValueRow>
          <ValueRow label="ダミーキャラ入村発言">
            <MessagePreview
              message={values.dummyJoinMessage}
              chara={selectedChara}
              originalImageUrl={originalImageUrl}
              original={original}
            />
          </ValueRow>
          {values.dummyDay1Message !== "" && (
            <ValueRow label="ダミーキャラ1日目発言">
              <MessagePreview
                message={values.dummyDay1Message}
                chara={selectedChara}
                originalImageUrl={originalImageUrl}
                original={original}
              />
            </ValueRow>
          )}
          <SectionRow title="詳細ルール設定" />
          <ValueRow label="構成">
            {values.randomOrganization ? (
              <ReadOnlyRandomOrganizationTable values={values} />
            ) : (
              <span className="whitespace-pre-line">{values.organization}</span>
            )}
          </ValueRow>
          <ValueRow label="役職希望">{values.possibleSkillRequest ? "有効" : "無効"}</ValueRow>
          <ValueRow label="同一人狼による連続襲撃">
            {values.availableSameWolfAttack ? "可能" : "不可"}
          </ValueRow>
          <ValueRow label="狩人による連続護衛">
            {values.availableGuardSameTarget ? "可能" : "不可"}
          </ValueRow>
          <ValueRow label="転生時の役職候補">
            {values.reincarnationSkillAll ? "全役職" : "編成に含まれる役職のみ"}
          </ValueRow>
          <ValueRow label="突然死">{values.availableSuddonlyDeath ? "あり" : "なし"}</ValueRow>
          <ValueRow label="コミット">{values.availableCommit ? "あり" : "なし"}</ValueRow>
          <SectionRow title="見学、閲覧設定" />
          <ValueRow label="見学入村">{values.availableSpectate ? "可能" : "不可"}</ValueRow>
          <ValueRow label="プロデューサー機能">
            {values.creatorIsProducer ? "あり" : "なし"}
          </ValueRow>
          <ValueRow label="墓下見学役職公開">
            {values.openSkillInGrave ? "公開" : "非公開"}
          </ValueRow>
          <ValueRow label="墓下見学会話公開">
            {values.visibleGraveSpectateMessage ? "公開" : "非公開"}
          </ValueRow>
          <SectionRow title="身内村設定" />
          {values.joinPassword !== "" && (
            <ValueRow label="入村パスワード">{values.joinPassword}</ValueRow>
          )}
          <ValueRow label="秘話">
            {values.allowedSecretSayCode === "NOTHING"
              ? "なし"
              : values.allowedSecretSayCode === "ONLY_CREATOR"
                ? "村建てとのみ可能"
                : "全員可能"}
          </ValueRow>
          <SectionRow title="特殊ルール向け" />
          <ValueRow label="発言制限（通常発言）">
            <ReadOnlySayRestrictTable
              targetHeader="役職"
              rows={values.sayRestrictList.map((r) => ({
                key: r.skillCode,
                label: r.skillName,
                restrict: r.restrict,
                length: r.length,
                count: r.count,
              }))}
            />
          </ValueRow>
          <ValueRow label="発言制限（役職発言）">
            <ReadOnlySayRestrictTable
              targetHeader="発言種別"
              rows={values.skillSayRestrictList.map((r) => ({
                key: r.messageTypeCode,
                label: r.messageTypeName,
                restrict: r.restrict,
                length: r.length,
                count: r.count,
              }))}
            />
          </ValueRow>
          <ValueRow label="投票">{values.openVote ? "記名投票" : "無記名投票"}</ValueRow>
          <SectionRow title="RP村向け" />
          <ValueRow label="年齢制限">
            {values.ageLimit === "" ? "全年齢" : values.ageLimit}
          </ValueRow>
          <ValueRow label="アクション">{values.availableAction ? "可能" : "不可"}</ValueRow>
          <ValueRow label="発言制限（RP発言）">
            <ReadOnlySayRestrictTable
              targetHeader="発言種別"
              rows={values.rpSayRestrictList.map((r) => ({
                key: r.messageTypeCode,
                label: r.messageTypeName,
                restrict: r.restrict,
                length: r.length,
                count: r.count,
              }))}
            />
          </ValueRow>
        </tbody>
      </table>
      {errorMessage && <p className="mt-[10px] whitespace-pre-line text-red-400">{errorMessage}</p>}
      <div className="mt-[15px] flex items-center justify-between">
        <Button variant="default" onClick={onBack} disabled={creating}>
          戻る
        </Button>
        <Button onClick={onCreate} disabled={creating}>
          {creating ? "作成中..." : "作成"}
        </Button>
      </div>
    </Modal>
  );
}
