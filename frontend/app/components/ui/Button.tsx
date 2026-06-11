import type { AnchorHTMLAttributes, ButtonHTMLAttributes } from "react";
import { Link, type LinkProps } from "react-router";

type ButtonVariant = "success" | "default" | "danger" | "info";
type ButtonSize = "sm" | "xs";

const variantStyle: Record<ButtonVariant, string> = {
  success: "border-[#00bc8c] bg-[#00bc8c]",
  default: "border-[#464545] bg-[#464545]",
  danger: "border-[#e74c3c] bg-[#e74c3c]",
  info: "border-[#3498db] bg-[#3498db]",
};

const sizeStyle: Record<ButtonSize, string> = {
  sm: "px-[9px] py-[6px]",
  xs: "px-[5px] py-[1px]",
};

function buttonClass(variant: ButtonVariant, size: ButtonSize): string {
  return `rounded-[3px] border-2 text-[13px] text-white hover:opacity-90 ${variantStyle[variant]} ${sizeStyle[size]}`;
}

/** ボタン。主アクションは success (緑)、破壊的操作は danger (赤)、補助操作は info (青)。 */
export function Button({
  variant = "success",
  size = "sm",
  className = "",
  type = "button",
  ...props
}: ButtonHTMLAttributes<HTMLButtonElement> & { variant?: ButtonVariant; size?: ButtonSize }) {
  return (
    <button
      type={type}
      className={`cursor-pointer ${buttonClass(variant, size)} disabled:opacity-50 ${className}`}
      {...props}
    />
  );
}

/** react-router Link をボタン風にする。 */
export function LinkButton({
  variant = "success",
  size = "sm",
  className = "",
  ...props
}: LinkProps & { variant?: ButtonVariant; size?: ButtonSize }) {
  return (
    <Link
      className={`inline-block no-underline ${buttonClass(variant, size)} ${className}`}
      {...props}
    />
  );
}

/** 外部 <a> をボタン風にする。 */
export function AnchorButton({
  variant = "success",
  size = "sm",
  className = "",
  ...props
}: AnchorHTMLAttributes<HTMLAnchorElement> & { variant?: ButtonVariant; size?: ButtonSize }) {
  return (
    <a
      className={`inline-block no-underline ${buttonClass(variant, size)} ${className}`}
      {...props}
    />
  );
}
