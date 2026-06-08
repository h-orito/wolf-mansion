import type { ReactNode } from "react";

/** ラベル列の幅 (横並び時)。フォームの用途で使い分ける。 */
type LabelWidth = "narrow" | "wide";

const LABEL_SM_WIDTH: Record<LabelWidth, string> = {
  narrow: "min-[768px]:w-1/6",
  wide: "min-[768px]:w-1/4",
};

/**
 * ラベル + 入力を 1 行に並べるフォーム行。狭い画面ではラベルを上に積み、768px 以上で
 * ラベルを左 (右寄せ) ・入力を右に配置する。
 */
export function FormRow({
  label,
  htmlFor,
  labelWidth = "narrow",
  children,
}: {
  label: string;
  htmlFor?: string;
  labelWidth?: LabelWidth;
  children: ReactNode;
}) {
  return (
    <div className="mb-[15px] flex flex-col min-[768px]:flex-row min-[768px]:items-start">
      <label
        htmlFor={htmlFor}
        className={`shrink-0 pb-1 ${LABEL_SM_WIDTH[labelWidth]} min-[768px]:pt-[5px] min-[768px]:pr-[15px] min-[768px]:pb-0 min-[768px]:text-right`}
      >
        {label}
      </label>
      <div className="min-[768px]:flex-1">{children}</div>
    </div>
  );
}

/** 送信ボタンなどのアクション行 (右寄せ)。 */
export function FormActions({ children }: { children: ReactNode }) {
  return <div className="text-right">{children}</div>;
}
