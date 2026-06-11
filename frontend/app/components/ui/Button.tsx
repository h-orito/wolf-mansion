import type { AnchorHTMLAttributes, ButtonHTMLAttributes } from "react";
import { Link, type LinkProps } from "react-router";

type ButtonVariant = "success" | "default" | "danger";

const variantStyle: Record<ButtonVariant, string> = {
  success:
    "rounded-[3px] border-2 border-[#00bc8c] bg-[#00bc8c] px-[9px] py-[6px] text-white hover:opacity-90",
  default:
    "rounded-[3px] border-2 border-[#464545] bg-[#464545] px-[9px] py-[6px] text-white hover:opacity-90",
  danger:
    "rounded-[3px] border-2 border-[#e74c3c] bg-[#e74c3c] px-[9px] py-[6px] text-white hover:opacity-90",
};

/** ボタン。主アクションは success (緑)、破壊的操作は danger (赤)。 */
export function Button({
  variant = "success",
  className = "",
  type = "button",
  ...props
}: ButtonHTMLAttributes<HTMLButtonElement> & { variant?: ButtonVariant }) {
  return (
    <button
      type={type}
      className={`cursor-pointer text-[13px] ${variantStyle[variant]} disabled:opacity-50 ${className}`}
      {...props}
    />
  );
}

/** react-router Link をボタン風にする。 */
export function LinkButton({
  variant = "success",
  className = "",
  ...props
}: LinkProps & { variant?: ButtonVariant }) {
  return (
    <Link
      className={`inline-block text-[13px] no-underline ${variantStyle[variant]} ${className}`}
      {...props}
    />
  );
}

/** 外部 <a> をボタン風にする。 */
export function AnchorButton({
  variant = "success",
  className = "",
  ...props
}: AnchorHTMLAttributes<HTMLAnchorElement> & { variant?: ButtonVariant }) {
  return (
    <a
      className={`inline-block text-[13px] no-underline ${variantStyle[variant]} ${className}`}
      {...props}
    />
  );
}
