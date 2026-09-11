/** 複数選択リストボックスの選択肢。 */
export type MultiSelectOption = {
  value: string;
  label: string;
};

/** 複数選択できるリストボックス (native `<select multiple>`)。 */
export function MultiSelect({
  options,
  value,
  onChange,
  ariaLabel,
}: {
  options: MultiSelectOption[];
  value: string[];
  onChange: (values: string[]) => void;
  ariaLabel: string;
}) {
  return (
    <select
      multiple
      aria-label={ariaLabel}
      value={value}
      onChange={(e) => onChange(Array.from(e.target.selectedOptions, (o) => o.value))}
      className="h-[74px] w-full rounded border border-gray-400 bg-white px-[10px] py-[5px] text-ink"
    >
      {options.map((o) => (
        <option key={o.value} value={o.value}>
          {o.label}
        </option>
      ))}
    </select>
  );
}
