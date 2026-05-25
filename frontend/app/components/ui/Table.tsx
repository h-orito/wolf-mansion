import * as React from "react";
import { cn } from "./cn";

/**
 * 旧 .table.table-bordered.table-condensed.small 相当。
 *
 * Bootstrap 3 の table-condensed は padding-y を詰めるバリアント。
 * .small は font-size を 0.85em 相当に。border は全 cell に。
 *
 * div.table-responsive で横スクロール可能にする (旧画面と同じ)。
 */
export function TableResponsive({
  className,
  ...rest
}: React.HTMLAttributes<HTMLDivElement>) {
  return (
    <div
      className={cn("w-full overflow-x-auto", className)}
      {...rest}
    />
  );
}

export function Table({
  className,
  ...rest
}: React.TableHTMLAttributes<HTMLTableElement>) {
  return (
    <table
      className={cn(
        "w-full border-collapse text-[0.95em] " +
          // 各セル共通の border (table-bordered)
          "[&_th]:border [&_td]:border [&_th]:border-night-700 [&_td]:border-night-700 " +
          // table-condensed (padding 詰め)
          "[&_th]:px-2 [&_th]:py-1 [&_td]:px-2 [&_td]:py-1 " +
          "[&_th]:text-left [&_th]:font-medium [&_th]:bg-night-900",
        className,
      )}
      {...rest}
    />
  );
}
