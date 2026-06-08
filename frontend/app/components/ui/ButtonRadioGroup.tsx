/** ボタン型ラジオの選択肢。 */
export type ButtonRadioOption<T> = {
  value: T;
  label: string;
};

/**
 * ボタンを横並びにした単一選択 (ラジオ)。選択中のボタンは押し込んだ表現にする。
 * 値は任意の型 (`===` で比較するため primitive 想定)。
 */
export function ButtonRadioGroup<T>({
  options,
  value,
  onChange,
  ariaLabel,
}: {
  options: ButtonRadioOption<T>[];
  value: T;
  onChange: (value: T) => void;
  ariaLabel?: string;
}) {
  return (
    <div role="radiogroup" aria-label={ariaLabel} className="inline-flex overflow-hidden rounded">
      {options.map((opt) => {
        const selected = opt.value === value;
        return (
          <button
            key={opt.label}
            type="button"
            role="radio"
            aria-checked={selected}
            onClick={() => onChange(opt.value)}
            className={`border-r border-white/20 bg-[#00bc8c] px-[9px] py-[5px] text-[13px] text-white last:border-r-0 hover:opacity-90 ${
              selected ? "shadow-[inset_0_3px_5px_rgba(0,0,0,0.225)] brightness-90" : ""
            }`}
          >
            {opt.label}
          </button>
        );
      })}
    </div>
  );
}
