/** ボタン型ラジオの選択肢。 */
export type ButtonRadioOption<T> = {
  value: T;
  label: string;
};

/** solid = 全ボタン緑地 (検索パネル系)。outline = 緑枠 + 選択時のみ緑地 (設定フォーム系)。 */
type ButtonRadioVariant = "solid" | "outline";

const solidButtonClass = (selected: boolean) =>
  `border-r border-white/20 bg-success px-[9px] py-[5px] text-[13px] text-white last:border-r-0 hover:opacity-90 ${
    selected ? "shadow-[inset_0_3px_5px_rgba(0,0,0,0.225)] brightness-90" : ""
  }`;

const outlineButtonClass = (selected: boolean) =>
  `not-first:-ml-px border border-success px-[9px] py-[5px] text-[13px] first:rounded-l-[3px] last:rounded-r-[3px] hover:opacity-90 ${
    selected
      ? "bg-success text-white shadow-[inset_0_3px_5px_rgba(0,0,0,0.125)]"
      : "bg-wm-base text-success"
  }`;

const buttonClass: Record<ButtonRadioVariant, (selected: boolean) => string> = {
  solid: solidButtonClass,
  outline: outlineButtonClass,
};

const groupClass: Record<ButtonRadioVariant, string> = {
  solid: "inline-flex overflow-hidden rounded",
  outline: "inline-flex",
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
  variant = "solid",
}: {
  options: ButtonRadioOption<T>[];
  value: T;
  onChange: (value: T) => void;
  ariaLabel?: string;
  variant?: ButtonRadioVariant;
}) {
  return (
    <div role="radiogroup" aria-label={ariaLabel} className={groupClass[variant]}>
      {options.map((opt) => {
        const selected = opt.value === value;
        return (
          <button
            key={opt.label}
            type="button"
            role="radio"
            aria-checked={selected}
            onClick={() => onChange(opt.value)}
            className={`cursor-pointer ${buttonClass[variant](selected)}`}
          >
            {opt.label}
          </button>
        );
      })}
    </div>
  );
}
