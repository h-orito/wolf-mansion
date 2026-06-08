import type { AnchorHTMLAttributes, ButtonHTMLAttributes } from "react";
import { Link, type LinkProps } from "react-router";

const successStyle =
  "rounded-[3px] border-2 border-[#00bc8c] bg-[#00bc8c] px-[9px] py-[6px] text-white hover:opacity-90";
const defaultStyle =
  "rounded-[3px] border-2 border-[#464545] bg-[#464545] px-[9px] py-[6px] text-white hover:opacity-90";

/** 主アクション用のボタン (緑系)。送信・実行ボタンに使う。 */
export function Button({
  className = "",
  type = "button",
  ...props
}: ButtonHTMLAttributes<HTMLButtonElement>) {
  return (
    <button type={type} className={`${successStyle} disabled:opacity-50 ${className}`} {...props} />
  );
}

/** react-router Link をボタン風にする。 */
export function LinkButton({
  variant = "success",
  className = "",
  ...props
}: LinkProps & { variant?: "success" | "default" }) {
  const base = variant === "success" ? successStyle : defaultStyle;
  return (
    <Link className={`inline-block text-[13px] no-underline ${base} ${className}`} {...props} />
  );
}

/** 外部 <a> をボタン風にする。 */
export function AnchorButton({
  variant = "success",
  className = "",
  ...props
}: AnchorHTMLAttributes<HTMLAnchorElement> & { variant?: "success" | "default" }) {
  const base = variant === "success" ? successStyle : defaultStyle;
  return <a className={`inline-block text-[13px] no-underline ${base} ${className}`} {...props} />;
}
