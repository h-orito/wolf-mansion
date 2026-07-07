import { useFormContext, useWatch } from "react-hook-form";

import { Button } from "~/components/ui/Button";
import { fieldErrorClass } from "~/components/ui/Form";
import type { NewVillageFormInput } from "./schema";

type SayRestrictListName = "sayRestrictList" | "skillSayRestrictList" | "rpSayRestrictList";

export type SayRestrictRowLabel = { key: string; label: string };

const tableClass =
  "border-collapse text-[10.32px] " +
  "[&_th]:border [&_th]:border-border [&_th]:p-[5px] [&_th]:text-left [&_th]:align-middle " +
  "[&_td]:border [&_td]:border-border [&_td]:p-[5px] [&_td]:align-middle";

const numberInputClass =
  "h-[30px] w-[135px] bg-white px-[10px] py-[5px] text-ink-strong disabled:bg-disabled";

function toNullableNumber(value: unknown): number | null {
  return value === "" || value == null ? null : Number(value);
}

/**
 * 発言制限テーブル (制限チェック + 1回あたりの文字数 * 1日あたりの回数)。
 * 行ラベルは静的 prop、値はフォーム state で持つ。チェックなしの行は無制限扱いのため
 * 入力を無効化する。
 */
export function SayRestrictionTable({
  name,
  targetHeader,
  rows,
}: {
  name: SayRestrictListName;
  targetHeader: string;
  rows: SayRestrictRowLabel[];
}) {
  return (
    <table className={tableClass}>
      <thead>
        <tr>
          <th>{targetHeader}</th>
          <th>制限</th>
          <th>1回あたりの発言文字数 * 1日あたりの発言回数</th>
        </tr>
      </thead>
      <tbody>
        {rows.map((row, index) => (
          <SayRestrictRow key={row.key} name={name} index={index} label={row.label} />
        ))}
      </tbody>
    </table>
  );
}

function SayRestrictRow({
  name,
  index,
  label,
}: {
  name: SayRestrictListName;
  index: number;
  label: string;
}) {
  const { register, getFieldState, formState } = useFormContext<NewVillageFormInput>();
  const restrict = useWatch<NewVillageFormInput>({ name: `${name}.${index}.restrict` }) as boolean;
  const lengthError = getFieldState(`${name}.${index}.length`, formState).error;
  const countError = getFieldState(`${name}.${index}.count`, formState).error;
  const error = lengthError ?? countError;
  return (
    <tr>
      <td>{label}</td>
      <td>
        <input
          type="checkbox"
          className="block h-[20px] w-[20px]"
          aria-label={`${label} 制限`}
          {...register(`${name}.${index}.restrict`)}
        />
      </td>
      <td>
        <div className="flex items-stretch">
          <input
            type="number"
            className={`${numberInputClass} rounded-l-[4px]`}
            placeholder="400"
            disabled={!restrict}
            aria-label={`${label} 発言文字数`}
            {...register(`${name}.${index}.length`, { setValueAs: toNullableNumber })}
          />
          <span className="flex items-center bg-surface-raised p-[5px] text-[12px]">{" * "}</span>
          <input
            type="number"
            className={`${numberInputClass} rounded-r-[4px]`}
            placeholder="20"
            disabled={!restrict}
            aria-label={`${label} 発言回数`}
            {...register(`${name}.${index}.count`, { setValueAs: toNullableNumber })}
          />
        </div>
        {error && <p className={fieldErrorClass}>{error.message}</p>}
      </td>
    </tr>
  );
}

/** 役職別の発言制限テーブルの先頭行 (村人) の設定を全行へ反映するボタン。 */
export function CopySayRestrictButton() {
  const { getValues, setValue, trigger } = useFormContext<NewVillageFormInput>();
  const copyFirstRowToAll = () => {
    const list = getValues("sayRestrictList");
    const first = list[0];
    if (!first) return;
    list.forEach((_, index) => {
      setValue(`sayRestrictList.${index}.restrict`, first.restrict, { shouldDirty: true });
      setValue(`sayRestrictList.${index}.length`, first.length, { shouldDirty: true });
      setValue(`sayRestrictList.${index}.count`, first.count, { shouldDirty: true });
    });
    // コピー元が範囲外の値でもコピー先にエラーが出るよう、まとめて再検証する
    void trigger("sayRestrictList");
  };
  return (
    <Button variant="info" size="xs" onClick={copyFirstRowToAll}>
      村人の設定を全てにコピー
    </Button>
  );
}
