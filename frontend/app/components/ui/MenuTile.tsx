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
  // 旧 .top-menu-selectable は padding: 0、.top-menu-selectable-inner が padding-top 15px
  // + height 100px。これを 1 要素にまとめて pt + min-h で再現。
  "flex flex-col items-center text-center " +
  "px-1 pt-[15px] pb-[10px] min-h-[8.3em] " + // 100px @ 12px base
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
      {/* 旧 glyphicon ~14px。span に scaler は付けない (svg 自身を 1em 相当で出す) */}
      {icon && <span aria-hidden className="leading-none mb-[6px]">{icon}</span>}
      {/* 旧 <span class="h6"> は BS3 で font-weight: bold + 12px。サブは body 12px (regular) */}
      <span className="block text-[1em] font-bold">{label}</span>
      {sublabel && (
        <span className="block text-[1em] opacity-90">{sublabel}</span>
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
      {icon && <span aria-hidden className="leading-none mb-[6px]">{icon}</span>}
      {/* 旧 <span class="h6"> は BS3 で font-weight: bold + 12px。サブは body 12px (regular) */}
      <span className="block text-[1em] font-bold">{label}</span>
      {sublabel && (
        <span className="block text-[1em] opacity-90">{sublabel}</span>
      )}
    </button>
  );
}
