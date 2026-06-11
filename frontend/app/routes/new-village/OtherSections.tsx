import { useFormContext } from "react-hook-form";

import { fieldErrorClass, FormRow } from "~/components/ui/Form";
import { inputClass, selectClass } from "~/components/ui/Input";
import type { SimpleSkillView } from "~/features/skills/api";
import { RadioRow, RequiredAfterCreationMark, SettingSection } from "./fields";
import {
  CopySayRestrictButton,
  SayRestrictionTable,
  type SayRestrictRowLabel,
} from "./SayRestrictionTable";
import type { NewVillageFormInput } from "./schema";
import { RP_SAY_MESSAGE_TYPES, SKILL_SAY_MESSAGE_TYPES } from "./schema";

const sayRestrictNote = (
  <div className="[&>p]:mb-[10.5px]">
    <p>1回の文字数は0~400、1日の回数は0~100で設定してください。</p>
    <p>チェックなしにすると無制限になります。</p>
  </div>
);

function toMessageTypeRows(
  messageTypes: readonly { messageTypeCode: string; messageTypeName: string }[],
): SayRestrictRowLabel[] {
  return messageTypes.map((t) => ({ key: t.messageTypeCode, label: t.messageTypeName }));
}

/** 見学、閲覧設定。 */
export function SpectateSection() {
  return (
    <SettingSection title="見学、閲覧設定">
      <RadioRow
        name="availableSpectate"
        label="見学入村"
        options={[
          { value: true, label: "可能" },
          { value: false, label: "不可" },
        ]}
        note={
          <p>
            可能にした場合、最大で[キャラセットの人数 -
            定員]人まで見学としての入村が可能になります。
          </p>
        }
      />
      <RadioRow
        name="creatorIsProducer"
        label={
          <>
            プロデューサー機能 <RequiredAfterCreationMark />
          </>
        }
        ariaLabel="プロデューサー機能"
        options={[
          { value: true, label: "あり" },
          { value: false, label: "なし" },
        ]}
        note={
          <p>
            ありにした場合、村建ては見学でしか参加できなくなる代わりに、プロデューサー機能（ルール参照）を持ちます。
          </p>
        }
      />
      <RadioRow
        name="openSkillInGrave"
        label="墓下見学役職公開"
        options={[
          { value: true, label: "公開" },
          { value: false, label: "非公開" },
        ]}
        note={<p>公開にした場合、進行中に死亡者や見学者が全員の役職を参照できるようになります。</p>}
      />
      <RadioRow
        name="visibleGraveSpectateMessage"
        label="墓下見学会話公開"
        options={[
          { value: true, label: "公開" },
          { value: false, label: "非公開" },
        ]}
        note={<p>公開にした場合、進行中に生存者が墓下会話や見学会話を参照できるようになります。</p>}
      />
    </SettingSection>
  );
}

/** 身内村向け設定。 */
export function RelativesSection() {
  const {
    register,
    formState: { errors },
  } = useFormContext<NewVillageFormInput>();
  return (
    <SettingSection title="身内村向け設定">
      <FormRow label="入村パスワード" htmlFor="joinPassword" labelWidth="wide">
        <input
          id="joinPassword"
          type="text"
          className={inputClass}
          placeholder="3文字以上12文字以下（任意）"
          {...register("joinPassword")}
        />
        {errors.joinPassword && <p className={fieldErrorClass}>{errors.joinPassword.message}</p>}
        <div className="mt-[10px]">
          <p>設定した場合、入村時にパスワード入力が必要になります。</p>
        </div>
      </FormRow>
      <FormRow label="秘話（1:1の会話）" htmlFor="allowedSecretSayCode" labelWidth="wide">
        <select
          id="allowedSecretSayCode"
          className={selectClass}
          {...register("allowedSecretSayCode")}
        >
          <option value="NOTHING">なし</option>
          <option value="ONLY_CREATOR">村建てとのみ</option>
          <option value="EVERYTHING">誰とでも可能</option>
        </select>
      </FormRow>
    </SettingSection>
  );
}

/** 特殊ルール向け。 */
export function SpecialRuleSection({ skills }: { skills: SimpleSkillView[] }) {
  return (
    <SettingSection title="特殊ルール向け">
      <FormRow label="発言制限（通常発言）" labelWidth="wide">
        <CopySayRestrictButton />
        <SayRestrictionTable
          name="sayRestrictList"
          targetHeader="役職"
          rows={skills.map((s) => ({ key: s.code, label: s.name }))}
        />
        {sayRestrictNote}
      </FormRow>
      <FormRow label="発言制限（役職発言）" labelWidth="wide">
        <SayRestrictionTable
          name="skillSayRestrictList"
          targetHeader="発言種別"
          rows={toMessageTypeRows(SKILL_SAY_MESSAGE_TYPES)}
        />
        {sayRestrictNote}
      </FormRow>
      <RadioRow
        name="openVote"
        label="投票"
        options={[
          { value: true, label: "記名" },
          { value: false, label: "無記名" },
        ]}
        note={<p>基本的に投票と足音で推理するルールであるため、記名投票を推奨します。</p>}
      />
    </SettingSection>
  );
}

/** RP村向け。 */
export function RpSection() {
  return (
    <SettingSection title="RP村向け">
      <RadioRow
        name="ageLimit"
        label="年齢制限"
        options={[
          { value: "", label: "全年齢" },
          { value: "R15", label: "R15" },
          { value: "R18", label: "R18" },
        ]}
        note={
          <p>
            R15,R18表現が禁止されている場合があるため、キャラチップの利用規約を確認お願いします（例えば、人狼BBSチップはいらすとや様の利用規約に準ずるため、R18表現は禁止です）。
          </p>
        }
      />
      <RadioRow
        name="availableAction"
        label="アクション"
        options={[
          { value: true, label: "可能" },
          { value: false, label: "不可" },
        ]}
      />
      <FormRow label="発言制限（RP発言）" labelWidth="wide">
        <SayRestrictionTable
          name="rpSayRestrictList"
          targetHeader="発言種別"
          rows={toMessageTypeRows(RP_SAY_MESSAGE_TYPES)}
        />
        {sayRestrictNote}
      </FormRow>
    </SettingSection>
  );
}
