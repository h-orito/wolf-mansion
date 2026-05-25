import * as React from "react";
import { Link } from "react-router";
import type { LinkProps } from "react-router";
import { cn } from "./cn";

/**
 * 旧 .btn / .btn-sm + variant class の再現。
 *
 * 旧画面で頻出だった variant:
 * - success: .btn-success (Bootstrap 緑 #5cb85c → 旧画面では footer-menu や form submit)
 * - dark-success: .btn-dark-success (dark bg + mint text + mint border、active で反転)
 * - danger: .btn-danger (Bootstrap 赤)
 * - dark-warning: .btn-dark-warning (dark bg + orange、active で反転)
 * - gray: .btn-gray (gray bg + white)
 *
 * 旧画面の挙動踏襲: hover/active で前景・背景が反転する系が多い。
 */
export type ButtonVariant =
  | "success"
  | "dark-success"
  | "danger"
  | "dark-warning"
  | "gray"
  | "default";

const baseClass =
  "inline-flex items-center justify-center gap-1 " +
  // 旧 BS3 .btn-sm 相当 (本番計測値 padding 6px 9px / radius 3px / border 2px)
  "px-[9px] py-[6px] rounded-[3px] border-2 " +
  // 文字サイズはユーザー設定で拡大されるよう em ベース。本番 .btn-sm font-size 13px
  "text-[13px] leading-[1.4] font-medium " +
  "transition-colors duration-100 " +
  "disabled:opacity-60 disabled:cursor-not-allowed " +
  "focus-visible:outline focus-visible:outline-2 focus-visible:outline-mint-500 focus-visible:outline-offset-2";

const variantClass: Record<ButtonVariant, string> = {
  // 旧画面は Bootstrap Darkly テーマで .btn-success を mint (#00bc8c) に上書き
  // していた (本番計測値)。Bootswatch 既定の緑 #5cb85c ではない。
  success:
    "bg-mint-600 border-mint-600 text-white hover:bg-mint-700 hover:border-mint-700",
  // 旧 .btn-dark-success: dark bg + mint 文字 + mint border、hover で反転
  "dark-success":
    "bg-night-500 border-mint-600 text-mint-600 hover:bg-mint-600 hover:text-white",
  danger:
    "bg-blood-500 border-blood-500 text-white hover:bg-blood-600 hover:border-blood-600",
  "dark-warning":
    "bg-night-500 border-warning-500 text-warning-500 hover:bg-warning-500 hover:text-white",
  gray: "bg-gray-500 border-gray-500 text-white hover:bg-gray-600 hover:border-gray-600",
  default:
    "bg-night-800 border-night-700 text-white hover:bg-night-700",
};

type Common = {
  variant?: ButtonVariant;
  fullWidth?: boolean;
  className?: string;
};

export type ButtonProps = Common &
  React.ButtonHTMLAttributes<HTMLButtonElement>;

export function Button({
  variant = "default",
  fullWidth,
  className,
  type = "button",
  ...rest
}: ButtonProps) {
  return (
    <button
      type={type}
      className={cn(baseClass, variantClass[variant], fullWidth && "w-full", className)}
      {...rest}
    />
  );
}

export type LinkButtonProps = Common & LinkProps;

/** react-router Link を Button と同じ見た目で出すラッパ */
export function LinkButton({
  variant = "default",
  fullWidth,
  className,
  ...rest
}: LinkButtonProps) {
  return (
    <Link
      className={cn(
        baseClass,
        variantClass[variant],
        fullWidth && "w-full",
        "no-underline",
        className,
      )}
      {...rest}
    />
  );
}
