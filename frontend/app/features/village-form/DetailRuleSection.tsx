import { useFormContext, useWatch } from "react-hook-form";

import { FormRow } from "~/components/ui/Form";
import { textareaClass } from "~/components/ui/Input";
import { TextLink } from "~/components/ui/TextLink";
import type { SimpleSkillView } from "~/features/skills/api";
import { RadioRow, RequiredAfterCreationMark, SettingSection } from "./fields";
import { RandomOrganizationTable } from "./RandomOrganizationTable";
import { type CampAllocationInput, type NewVillageFormInput } from "./schema";

/** 詳細ルール設定 (役職構成 / 役職希望 / 襲撃・護衛 / 転生 / 突然死 / コミット)。 */
export function DetailRuleSection({
  skills,
  defaultCamps,
  fixedSkillRequest,
}: {
  skills: SimpleSkillView[];
  defaultCamps: CampAllocationInput[];
  /** 役職希望の現在値 (村作成後は変更不可のため、指定時は読み取り表示にする) */
  fixedSkillRequest?: boolean;
}) {
  const randomOrganization = useWatch<NewVillageFormInput, "randomOrganization">({
    name: "randomOrganization",
  });

  return (
    <SettingSection title="詳細ルール設定">
      <RadioRow
        name="randomOrganization"
        label="役職構成"
        options={[
          { value: true, label: "闇鍋" },
          { value: false, label: "固定" },
        ]}
      />
      <FormRow label="" labelWidth="wide">
        {randomOrganization ? (
          <RandomOrganizationPanel defaultCamps={defaultCamps} />
        ) : (
          <FixedOrganizationPanel skills={skills} />
        )}
      </FormRow>
      {fixedSkillRequest != null ? (
        <FormRow
          label={
            <>
              役職希望 <RequiredAfterCreationMark />
            </>
          }
          labelWidth="wide"
        >
          <p className="pt-[5px]">{fixedSkillRequest ? "有効" : "無効"}</p>
        </FormRow>
      ) : (
        <RadioRow
          name="possibleSkillRequest"
          label={
            <>
              役職希望 <RequiredAfterCreationMark />
            </>
          }
          ariaLabel="役職希望"
          options={[
            { value: true, label: "有効" },
            { value: false, label: "無効" },
          ]}
        />
      )}
      <RadioRow
        name="availableSameWolfAttack"
        label="同一人狼による連続襲撃"
        options={[
          { value: true, label: "可能" },
          { value: false, label: "不可" },
        ]}
        note={
          <p>
            不可にした場合、LWである場合を除いて襲撃を担当した人狼はその翌日襲撃を担当できなくなります。
            <br />
            なお、「不可」設定で1日目に移行した際、人狼2以下編成の場合は自動で「可能」に変更されます。
          </p>
        }
      />
      <RadioRow
        name="availableGuardSameTarget"
        label="狩人による連続護衛"
        options={[
          { value: true, label: "可能" },
          { value: false, label: "不可" },
        ]}
        note={<p>不可にした場合、同一対象を2日続けて護衛することができなくなります。</p>}
      />
      <RadioRow
        name="reincarnationSkillAll"
        label="転生時の役職"
        options={[
          { value: true, label: "全役職" },
          { value: false, label: "編成に含まれる役職のみ" },
        ]}
        note={
          <p>
            「編成に含まれる役職のみ」にした場合、転生時の役職は編成に含まれる役職から選ばれます。
            <br />
            詳細は
            <TextLink to="/rule#tensei" target="_blank">
              ルール - 転生
            </TextLink>
            を参照ください。
          </p>
        }
      />
      <RadioRow
        name="availableSuddonlyDeath"
        label="突然死"
        options={[
          { value: true, label: "あり" },
          { value: false, label: "なし" },
        ]}
        note={<p>ありにした場合、日付更新までに投票しなかった参加者は突然死します。</p>}
      />
      <RadioRow
        name="availableCommit"
        label="コミット"
        options={[
          { value: true, label: "あり" },
          { value: false, label: "なし" },
        ]}
        note={
          <p>
            ありにした場合、生存者全員がコミットすると日付を更新することができます。
            <br />
            （コミット状況は人数のみ表示されます）
          </p>
        }
      />
    </SettingSection>
  );
}

function FixedOrganizationPanel({ skills }: { skills: SimpleSkillView[] }) {
  const { register } = useFormContext<NewVillageFormInput>();
  const skillListStr = skills
    .filter((s) => s.requestable)
    .map((s) => `${s.name}:${s.shortName}`)
    .join(" / ");
  return (
    <div className="[&>p]:mb-[10.5px]">
      <p>固定の役職構成にしたい場合はこちらで入力してください。</p>
      <textarea
        className={`${textareaClass} mb-[10.5px] min-h-[250px]`}
        aria-label="固定の役職構成"
        {...register("organization")}
      />
      <p>最少開始人数〜定員までの役職構成を全て入れてください。</p>
      <p>{skillListStr}</p>
      <p>ダミーは必ず村人となるため、村を1名以上入れてください。</p>
    </div>
  );
}

function RandomOrganizationPanel({ defaultCamps }: { defaultCamps: CampAllocationInput[] }) {
  return (
    <div>
      <p className="mb-[10.5px]">ランダム役職構成にしたい場合はこちらで入力してください。</p>
      <ul className="mb-[10.5px] list-disc pl-[20px]">
        <li>役職ごとに最少人数が1名以上で設定されている場合、先に確保されます。</li>
        <li>その後、陣営の最少人数が1名以上で設定されている場合、先に確保されます。</li>
        <li>その後、一人ずつ陣営を抽選 → 陣営内の役職を抽選します。</li>
        <li>配分の値が高いほど抽選率が上がります。</li>
        <li>
          転生でしか発生しない役職（例.
          暴走トラック）は、編成されません（最少最多に0名以外を指定できません）。
        </li>
        <li>陣営の抽選率＝[陣営の配分 / 全陣営の配分の合計]です。</li>
        <li>役職の抽選率＝[役職の配分 / 陣営内の役職の配分の合計]です。</li>
        <li>ダミーは必ず村人となるため、村人の最少人数は1以上にする必要があります。</li>
        <li>人狼系役職のいずれかの最多人数もしくは配分を1以上にする必要があります。</li>
        <li>
          割り振った結果以下の条件を1つでも満たすと50回まで再抽選しますが、50回で決まらなかった場合1日目開始時にエラーとなります。
          <ul className="list-[circle] pl-[20px]">
            <li>2日目にPP発生</li>
            <li>人狼カウントの最少最多設定を満たしていない</li>
            <li>恋人や同棲者が奇数</li>
            <li>妖狐系なしで背徳者がいるなど、勝利条件を満たせない役職がある</li>
          </ul>
        </li>
        <li>
          転生時の役職候補を「編成に含まれる役職」にした場合、転生時の役職は闇鍋設定の「転生配分」を参照しランダムに選ばれます（つまり、陣営や役職の転生配分を0にすれば転生時に選ばれません）。
        </li>
      </ul>
      <RandomOrganizationTable camps={defaultCamps} />
    </div>
  );
}
