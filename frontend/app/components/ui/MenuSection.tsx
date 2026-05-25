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
  return (
    <section
      aria-label={!title ? ariaLabel : undefined}
      className={cn(
        // 旧 .top-menu: bg #333 + padding 15px (上下のみ。横は tile が edge-to-edge)
        "bg-night-700 py-[15px] px-0",
        className,
      )}
    >
      {title && (
        // 旧 h2.h5 = 14px (= 1.17em @ 12px base)。heading は section bg (#333) 上に置く
        <h2 className="text-center text-[1.17em] mb-[15px] font-medium">
          {title}
        </h2>
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
