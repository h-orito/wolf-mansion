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
  // 旧 .top-menu-selectable: padding 0、固定 100px 高さ、bg #0b162a + border 1px #333
  // 旧 .top-menu-selectable-inner: padding-top 15px のみ + height 100px。
  // <a> と <button> で vertical alignment が変わらないよう flex column で統一する。
  "flex flex-col items-center text-center h-[100px] pt-[15px] px-0 pb-0 " +
  "bg-night-950 border border-night-700 text-white " +
  "hover:bg-night-800 hover:border-mint-500 hover:text-mint-500 " +
  "transition-colors duration-100 " +
  "no-underline cursor-pointer " +
  // <button> 要素のユーザーエージェント font をリセット (Lato 継承)
  "font-sans";

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
 * MenuTileLink / MenuTileButton 共通の中身。
 *   - 旧 glyphicon (~12px) → svg を 1em 相当で出す
 *   - 旧 <span class="h6"> = font-size 13px / weight 400 / margin 10.5px 0 / line-height 14.3px
 *   - サブラベル = body 12px (regular)
 * tile 側を flex column items-center にしているため、children は順に縦に積み上がる。
 */
function TileContent({ icon, label, sublabel }: Pick<Common, "icon" | "label" | "sublabel">) {
  return (
    <>
      {icon && (
        <span aria-hidden className="block leading-none">
          {icon}
        </span>
      )}
      <span className="block text-[13px] font-normal leading-[14.3px] my-[10.5px]">
        {label}
      </span>
      {sublabel && (
        <span className="block text-[12px] font-normal leading-[1.4]">{sublabel}</span>
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
