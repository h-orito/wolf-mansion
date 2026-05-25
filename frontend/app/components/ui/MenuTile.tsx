import * as React from "react";
import { Link } from "react-router";
import type { LinkProps } from "react-router";
import { cn } from "./cn";

/**
 * 旧 .top-menu-selectable / .top-menu-selectable-inner 相当。
 *
 * - 高さは固定 100px ではなく min-h で持ち、文字拡大時に潰れないようにする
 * - 通常: 深紺 bg + #333 border + 白文字
 * - hover: bg = #22224a, border + 文字を mint-500 に
 */
const tileClass =
  // layout
  "flex flex-col items-center justify-center text-center gap-1 " +
  "px-3 py-4 min-h-[8.3em] " + // 旧 100px @ 12px base 相当を em で
  // 色 (旧 .top-menu-selectable)
  "bg-night-950 border border-night-700 text-white " +
  // hover (旧 :hover で mint に)
  "hover:bg-night-800 hover:border-mint-500 hover:text-mint-500 " +
  "transition-colors duration-100 " +
  // a 由来の打ち消し
  "no-underline cursor-pointer";

type Common = {
  /** 上段に出す小さなアイコン / glyph (絵文字 or SVG) */
  icon?: React.ReactNode;
  /** 太字メインラベル (日本語想定) */
  label: React.ReactNode;
  /** サブラベル (英語想定) */
  sublabel?: React.ReactNode;
  className?: string;
};

export function MenuTileLink({
  icon,
  label,
  sublabel,
  className,
  ...rest
}: Common & LinkProps) {
  return (
    <Link {...rest} className={cn(tileClass, className)}>
      {icon && <span aria-hidden className="text-[1.5em] leading-none">{icon}</span>}
      <span className="block font-bold">{label}</span>
      {sublabel && (
        <span className="block text-[0.85em] opacity-80">{sublabel}</span>
      )}
    </Link>
  );
}

export function MenuTileButton({
  icon,
  label,
  sublabel,
  className,
  type = "button",
  ...rest
}: Common & React.ButtonHTMLAttributes<HTMLButtonElement>) {
  return (
    <button {...rest} type={type} className={cn(tileClass, className)}>
      {icon && <span aria-hidden className="text-[1.5em] leading-none">{icon}</span>}
      <span className="block font-bold">{label}</span>
      {sublabel && (
        <span className="block text-[0.85em] opacity-80">{sublabel}</span>
      )}
    </button>
  );
}
