import { useFormContext } from "react-hook-form";

import { fieldErrorClass, FormRow } from "~/components/ui/Form";
import { inputClass } from "~/components/ui/Input";
import { NumberSelect, RadioRow, range, SettingSection } from "./fields";
import type { NewVillageFormInput } from "./schema";

const labelClass =
  "shrink-0 pb-1 min-[768px]:pt-[5px] min-[768px]:pr-[15px] min-[768px]:pb-0 min-[768px]:text-right";

/** 基本設定 (村名 / 募集範囲 / 人数 / 更新間隔 / 開始日時)。 */
export function BasicSection({ nowYear }: { nowYear: number }) {
  const {
    register,
    formState: { errors },
  } = useFormContext<NewVillageFormInput>();

  return (
    <SettingSection title="基本設定">
      <FormRow label="村名" htmlFor="villageName" labelWidth="wide">
        <input
          id="villageName"
          type="text"
          className={inputClass}
          placeholder="【身内】レタス村"
          {...register("villageName")}
        />
        {errors.villageName && <p className={fieldErrorClass}>{errors.villageName.message}</p>}
      </FormRow>
      <RadioRow
        name="welcomeRange"
        label="募集範囲"
        options={[
          { value: "ANYONE_WELCOME", label: "誰歓" },
          { value: "RELATIVES_ONLY", label: "身内" },
          { value: "", label: "その他" },
        ]}
      />
      <div className="mb-[15px] flex flex-col min-[768px]:grid min-[768px]:grid-cols-4">
        <label htmlFor="startPersonMinNum" className={labelClass}>
          最少開始人数
        </label>
        <div>
          <input
            id="startPersonMinNum"
            type="number"
            className={inputClass}
            placeholder="8"
            {...register("startPersonMinNum", { valueAsNumber: true })}
          />
          {errors.startPersonMinNum && (
            <p className={fieldErrorClass}>{errors.startPersonMinNum.message}</p>
          )}
        </div>
        <label htmlFor="personMaxNum" className={labelClass}>
          定員
        </label>
        <div>
          <input
            id="personMaxNum"
            type="number"
            className={inputClass}
            placeholder="16"
            {...register("personMaxNum", { valueAsNumber: true })}
          />
          {errors.personMaxNum && <p className={fieldErrorClass}>{errors.personMaxNum.message}</p>}
        </div>
      </div>
      <FormRow label="更新間隔" labelWidth="wide">
        <div className="grid grid-cols-3 gap-x-[30px]">
          <NumberSelect
            name="dayChangeIntervalHours"
            options={range(0, 72)}
            suffix="時間"
            ariaLabel="更新間隔 (時間)"
          />
          <NumberSelect
            name="dayChangeIntervalMinutes"
            options={range(0, 59)}
            suffix="分"
            ariaLabel="更新間隔 (分)"
          />
          <NumberSelect
            name="dayChangeIntervalSeconds"
            options={range(0, 50, 10)}
            suffix="秒"
            ariaLabel="更新間隔 (秒)"
          />
        </div>
        {errors.dayChangeIntervalHours && (
          <p className={fieldErrorClass}>{errors.dayChangeIntervalHours.message}</p>
        )}
      </FormRow>
      <FormRow label="開始日時" labelWidth="wide">
        <div className="grid grid-cols-3 gap-x-[30px] gap-y-[10px]">
          <NumberSelect
            name="startYear"
            options={[nowYear, nowYear + 1]}
            suffix="年"
            ariaLabel="開始日時 (年)"
          />
          <NumberSelect
            name="startMonth"
            options={range(1, 12)}
            suffix="月"
            ariaLabel="開始日時 (月)"
          />
          <NumberSelect
            name="startDay"
            options={range(1, 31)}
            suffix="日"
            ariaLabel="開始日時 (日)"
          />
          <NumberSelect
            name="startHour"
            options={range(0, 23)}
            suffix="時"
            ariaLabel="開始日時 (時)"
          />
          <NumberSelect
            name="startMinute"
            options={range(0, 55, 5)}
            suffix="分"
            ariaLabel="開始日時 (分)"
          />
        </div>
        {errors.startYear && <p className={fieldErrorClass}>{errors.startYear.message}</p>}
      </FormRow>
    </SettingSection>
  );
}
