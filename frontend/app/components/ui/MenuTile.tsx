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
  // 旧 .top-menu-selectable + .top-menu-selectable-inner を 1 要素に統合。
  // 100px @ 12px base のタイル内で icon / label / sublabel が上下に等間隔で並ぶよう
  // justify-evenly を使う (固定 padding にしない)。
  "flex flex-col items-center justify-evenly text-center " +
  "px-1 py-[10px] min-h-[8.334em] " + // 100px @ 12px base (100/12)
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

/**
 * MenuTileLink / MenuTileButton 共通の中身。icon → label (太字) → sublabel
 * の縦並びを 1 箇所で定義する。
 *   - 旧 glyphicon ~14px。icon 側 span に scaler は付けない (svg 自身を 1em で)
 *   - 旧 <span class="h6"> は BS3 で font-weight: bold + 12px
 *   - サブラベルは body 12px (regular)
 */
function TileContent({ icon, label, sublabel }: Pick<Common, "icon" | "label" | "sublabel">) {
  return (
    <>
      {icon && (
        // mb は付けない (justify-evenly が icon / label / sublabel の間隔を均等に分配)
        <span aria-hidden className="leading-none">
          {icon}
        </span>
      )}
      <span className="block text-[1em] font-bold">{label}</span>
      {sublabel && (
        <span className="block text-[1em] opacity-90">{sublabel}</span>
      )}
    </>
  );
}

export function MenuTileLink({
  icon,
  label,
  sublabel,
  className,
  ...rest
}: Common & LinkProps) {
  return (
    <Link {...rest} className={cn(tileClass, className)}>
      <TileContent icon={icon} label={label} sublabel={sublabel} />
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
      <TileContent icon={icon} label={label} sublabel={sublabel} />
    </button>
  );
}
