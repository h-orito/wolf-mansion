import { useFormContext } from "react-hook-form";

import { fieldErrorClass, FormRow } from "~/components/ui/Form";
import { textareaClass } from "~/components/ui/Input";
import { RequiredAfterCreationMark, SettingSection } from "./fields";
import type { NewVillageFormInput } from "./schema";

/**
 * キャラチップ設定。キャラセット・ダミーキャラは村作成後に変更不可のため読み取り表示のみ。
 * ダミーキャラの1日目発言は編集可能。
 */
export function CharaChipSection({
  charachipNames,
  dummyCharaName,
}: {
  charachipNames: string[];
  dummyCharaName: string;
}) {
  const {
    register,
    formState: { errors },
  } = useFormContext<NewVillageFormInput>();

  return (
    <SettingSection title="キャラチップ設定">
      <FormRow
        label={
          <>
            キャラセット <RequiredAfterCreationMark />
          </>
        }
        labelWidth="wide"
      >
        <p className="pt-[5px]">{charachipNames.join("、")}</p>
      </FormRow>
      <FormRow
        label={
          <>
            ダミーキャラ <RequiredAfterCreationMark />
          </>
        }
        labelWidth="wide"
      >
        <p className="pt-[5px]">{dummyCharaName}</p>
      </FormRow>
      <FormRow label="ダミーキャラの1日目発言" htmlFor="dummyDay1Message" labelWidth="wide">
        <textarea
          id="dummyDay1Message"
          className={`${textareaClass} min-h-[77px]`}
          {...register("dummyDay1Message")}
        />
        {errors.dummyDay1Message && (
          <p className={fieldErrorClass}>{errors.dummyDay1Message.message}</p>
        )}
      </FormRow>
    </SettingSection>
  );
}
