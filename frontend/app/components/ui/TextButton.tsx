import type { ButtonHTMLAttributes } from "react";

/**
 * 本文中のテキストリンク風アクションボタン。遷移を伴うものは `TextLink` を使う。
 */
export function TextButton({
  className = "",
  type = "button",
  ...props
}: ButtonHTMLAttributes<HTMLButtonElement>) {
  return (
    <button
      type={type}
      className={`text-wm-accent cursor-pointer hover:underline ${className}`}
      {...props}
    />
  );
}
