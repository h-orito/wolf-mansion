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
  children,
  className,
}: {
  title?: React.ReactNode;
  children: React.ReactNode;
  className?: string;
}) {
  return (
    <section
      className={cn(
        "bg-night-700 py-4 px-3", // 旧 #333333 + padding 15px
        className,
      )}
    >
      {title && (
        <h2 className="text-center text-[1.17em] mb-3 font-medium">{title}</h2>
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
