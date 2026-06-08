import type { ButtonHTMLAttributes } from "react";

/** 主アクション用のボタン (緑系)。送信・実行ボタンに使う。 */
export function Button({
  className = "",
  type = "button",
  ...props
}: ButtonHTMLAttributes<HTMLButtonElement>) {
  return (
    <button
      type={type}
      className={`rounded bg-[#00bc8c] px-3 py-[5px] text-white hover:opacity-90 disabled:opacity-50 ${className}`}
      {...props}
    />
  );
}
