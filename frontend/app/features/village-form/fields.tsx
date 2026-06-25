import type { ReactNode } from "react";
import { Controller, type FieldPath, useFormContext } from "react-hook-form";

import { ButtonRadioGroup, type ButtonRadioOption } from "~/components/ui/ButtonRadioGroup";
import { FormRow } from "~/components/ui/Form";
import { SubHeading } from "~/components/ui/Heading";
import { Divider } from "~/components/ui/Divider";
import { selectClass } from "~/components/ui/Input";
import type { NewVillageFormInput } from "./schema";

/** 村作成後に変更できない項目に付ける印。 */
export function RequiredAfterCreationMark() {
  return <span className="text-[#e74c3c]">*</span>;
}

/** 区切り線 + セクション見出し。 */
export function SettingSection({ title, children }: { title: ReactNode; children: ReactNode }) {
  return (
    <section>
      <Divider />
      <div className="my-[10.5px]">
        <SubHeading weight="normal">{title}</SubHeading>
      </div>
      {children}
    </section>
  );
}

type RadioValue = string | boolean;

/** ボタン型ラジオのフォーム行。note には項目の補足説明を渡す。 */
export function RadioRow({
  name,
  label,
  ariaLabel,
  options,
  note,
}: {
  name: FieldPath<NewVillageFormInput>;
  label: ReactNode;
  ariaLabel?: string;
  options: ButtonRadioOption<RadioValue>[];
  note?: ReactNode;
}) {
  const { control } = useFormContext<NewVillageFormInput>();
  return (
    <FormRow label={label} labelWidth="wide">
      <Controller
        control={control}
        name={name}
        render={({ field }) => (
          <ButtonRadioGroup
            variant="outline"
            options={options}
            value={field.value as RadioValue}
            onChange={field.onChange}
            ariaLabel={ariaLabel ?? (typeof label === "string" ? label : undefined)}
          />
        )}
      />
      {note && <div className="mt-[10px] [&>p]:mb-[10.5px]">{note}</div>}
    </FormRow>
  );
}

/** 数値のセレクトボックス (値は number で保持する)。 */
export function NumberSelect({
  name,
  options,
  suffix,
  ariaLabel,
}: {
  name: FieldPath<NewVillageFormInput>;
  options: number[];
  suffix: string;
  ariaLabel: string;
}) {
  const { register } = useFormContext<NewVillageFormInput>();
  return (
    <select
      className={selectClass}
      aria-label={ariaLabel}
      {...register(name, { valueAsNumber: true })}
    >
      {options.map((value) => (
        <option key={value} value={value}>
          {value}
          {suffix}
        </option>
      ))}
    </select>
  );
}

/** start〜end (両端含む) の連番。step 指定時はその刻み。 */
export function range(start: number, end: number, step = 1): number[] {
  const result: number[] = [];
  for (let i = start; i <= end; i += step) {
    result.push(i);
  }
  return result;
}
