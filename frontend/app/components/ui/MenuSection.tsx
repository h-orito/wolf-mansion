import * as React from "react";
import { cn } from "./cn";

/**
 * 旧 .top-menu の section 枠。
 * <h2 class="h5">タイトル</h2> + tile grid をまとめる。
 *
 * 旧 CSS:
 *   .top-menu {
 *     display: flex;
 *     justify-content: center;
 *     background-color: #333333;
 *     padding-top: 15px;
 *     padding-bottom: 15px;
 *   }
 */
export function MenuSection({
  title,
  ariaLabel,
  children,
  className,
}: {
  title?: React.ReactNode;
  /**
   * title 省略時に accessible name として使う。スクリーンリーダの region 一覧で
   * unnamed section にならないように、title or ariaLabel のいずれかを指定する。
   */
  ariaLabel?: string;
  children: React.ReactNode;
  className?: string;
}) {
  // title 指定時は内側の <h2> を accessible name として利用するため id を採番し、
  // <section aria-labelledby> で紐付ける (unnamed landmark を回避)。
  // title 省略時も Hooks の呼び出し順を保つため useId は常に呼ぶ (id は DOM に出ない)。
  const headingId = React.useId();
  return (
    <section
      aria-label={!title ? ariaLabel : undefined}
      aria-labelledby={title ? headingId : undefined}
      className={cn(
        // 旧 .top-menu: bg #333 + padding 15px (4 辺すべて、本番計測値)
        "bg-night-700 p-[15px]",
        className,
      )}
    >
      {title && (
        // 旧 h2.h5 を含む h100px 行: flex 中央配置で 100px 高さ。
        // h2 自体は font-size 15px / weight 400 / margin 10.5px 0 (BS3 デフォルト)
        <div className="h-[100px] flex items-center justify-center px-[15px]">
          <h2
            id={headingId}
            className="m-0 text-center text-[15px] font-normal leading-[1.1]"
          >
            {title}
          </h2>
        </div>
      )}
      {children}
    </section>
  );
}

/**
 * tile を横並びに敷くグリッド。旧画面は Bootstrap col-sm-4 / col-sm-6 で
 * 3up / 2up を切替えていた。`cols` で行ごとに切り替え可能。
 */
export function MenuTileRow({
  children,
  cols = 3,
  className,
}: {
  children: React.ReactNode;
  /** 旧 col-sm-X 相当 */
  cols?: 1 | 2 | 3 | 4;
  className?: string;
}) {
  const gridClass =
    cols === 1
      ? "grid-cols-1"
      : cols === 2
        ? "grid-cols-2"
        : cols === 3
          ? "grid-cols-3"
          : "grid-cols-4";
  return (
    <div className={cn("grid gap-0", gridClass, className)}>{children}</div>
  );
}
