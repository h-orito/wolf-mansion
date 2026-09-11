import { type FieldPath, useFormContext } from "react-hook-form";

import { fieldErrorClass } from "~/components/ui/Form";
import { inlineInputClass } from "~/components/ui/Input";
import type { CampAllocationInput, NewVillageFormInput } from "./schema";

const headerTdClass = "bg-danger text-white";
const campTdClass = "bg-info text-white";

/** 配分セル (数値入力 + エラー表示)。 */
function AllocationCell({
  name,
  ariaLabel,
}: {
  name: FieldPath<NewVillageFormInput>;
  ariaLabel: string;
}) {
  const { register, getFieldState, formState } = useFormContext<NewVillageFormInput>();
  const { error } = getFieldState(name, formState);
  return (
    <td>
      <input
        type="number"
        className={`${inlineInputClass} w-full min-w-[50px]`}
        placeholder="0"
        aria-label={ariaLabel}
        {...register(name, {
          setValueAs: (v) => (v === "" || v === null ? null : Number(v)),
        })}
      />
      {error && <p className={fieldErrorClass}>{error.message}</p>}
    </td>
  );
}

/** 闇鍋編成の配分テーブル (人狼カウント + 陣営ごと + 役職ごと)。 */
export function RandomOrganizationTable({ camps }: { camps: CampAllocationInput[] }) {
  return (
    <table className="border-collapse text-[10.32px] [&_td]:border [&_td]:border-border [&_td]:p-[5px]">
      <tbody>
        <tr>
          <td className={headerTdClass} colSpan={9}>
            <strong>人狼カウント</strong>
          </td>
        </tr>
        <tr>
          <td>人狼カウント</td>
          <td>最少人数</td>
          <AllocationCell name="wolfAllocation.minNum" ariaLabel="人狼カウント 最少人数" />
          <td>最多人数</td>
          <AllocationCell name="wolfAllocation.maxNum" ariaLabel="人狼カウント 最多人数" />
          <td colSpan={4}></td>
        </tr>
        {camps.map((camp, campIndex) => (
          <CampRows key={camp.campCode} camp={camp} campIndex={campIndex} />
        ))}
      </tbody>
    </table>
  );
}

function CampRows({ camp, campIndex }: { camp: CampAllocationInput; campIndex: number }) {
  const base = `campAllocationList.${campIndex}` as const;
  return (
    <>
      <tr>
        <td className={campTdClass} colSpan={9}>
          <strong>{camp.campName}</strong>
        </td>
      </tr>
      <tr>
        <td colSpan={9}>陣営全体の配分</td>
      </tr>
      <tr>
        <td>{camp.campName}</td>
        <td>最少人数</td>
        <AllocationCell name={`${base}.minNum`} ariaLabel={`${camp.campName} 最少人数`} />
        <td>最多人数</td>
        <AllocationCell name={`${base}.maxNum`} ariaLabel={`${camp.campName} 最多人数`} />
        <td>配分（0-100）</td>
        <AllocationCell name={`${base}.allocation`} ariaLabel={`${camp.campName} 配分`} />
        <td>転生配分（0-100）</td>
        <AllocationCell
          name={`${base}.reincarnationAllocation`}
          ariaLabel={`${camp.campName} 転生配分`}
        />
      </tr>
      <tr>
        <td colSpan={9}>役職ごとの配分</td>
      </tr>
      {camp.skillAllocation.map((skill, skillIndex) => {
        const skillBase = `${base}.skillAllocation.${skillIndex}` as const;
        return (
          <tr key={skill.skillCode}>
            <td>{skill.skillName}</td>
            <td>最少人数</td>
            <AllocationCell
              name={`${skillBase}.minNum`}
              ariaLabel={`${skill.skillName} 最少人数`}
            />
            <td>最多人数</td>
            <AllocationCell
              name={`${skillBase}.maxNum`}
              ariaLabel={`${skill.skillName} 最多人数`}
            />
            <td>配分（0-100）</td>
            <AllocationCell
              name={`${skillBase}.allocation`}
              ariaLabel={`${skill.skillName} 配分`}
            />
            <td>転生配分（0-100）</td>
            <AllocationCell
              name={`${skillBase}.reincarnationAllocation`}
              ariaLabel={`${skill.skillName} 転生配分`}
            />
          </tr>
        );
      })}
    </>
  );
}
