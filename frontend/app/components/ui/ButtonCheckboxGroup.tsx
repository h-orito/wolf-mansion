/** ボタン型チェックボックスの選択肢。 */
export type ButtonCheckboxOption<T> = {
  value: T;
  label: string;
};

const buttonClass = (selected: boolean) =>
  `not-first:-ml-px flex-1 cursor-pointer border border-[#00bc8c] px-[9px] py-[5px] text-[13px] first:rounded-l-[3px] last:rounded-r-[3px] hover:opacity-90 ${
    selected
      ? "bg-[#00bc8c] text-white shadow-[inset_0_3px_5px_rgba(0,0,0,0.125)]"
      : "bg-[#222222] text-[#00bc8c]"
  }`;

/**
 * ボタンを横並びにした複数選択 (チェックボックス)。緑枠 + 選択時のみ緑地で、
 * 単一選択の `ButtonRadioGroup` (outline) と同じ見た目にする。
 */
export function ButtonCheckboxGroup<T>({
  options,
  values,
  onToggle,
  ariaLabel,
}: {
  options: ButtonCheckboxOption<T>[];
  values: T[];
  onToggle: (value: T) => void;
  ariaLabel?: string;
}) {
  return (
    <div role="group" aria-label={ariaLabel} className="flex">
      {options.map((opt) => {
        const selected = values.includes(opt.value);
        return (
          <button
            key={opt.label}
            type="button"
            role="checkbox"
            aria-checked={selected}
            onClick={() => onToggle(opt.value)}
            className={buttonClass(selected)}
          >
            {opt.label}
          </button>
        );
      })}
    </div>
  );
}
