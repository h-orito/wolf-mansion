import * as React from "react";
import { cn } from "./cn";

/**
 * 旧 .village-tag / .village-tag.success / .village-tag.danger 相当。
 *
 *   .village-tag { padding: 2px; margin-right: 5px; border-radius: 4px; }
 *   .village-tag.success { color: #0ce3ac; border: 1px solid #0ce3ac; }
 *   .village-tag.danger  { color: #ff0000; border: 1px solid #ff0000; }
 *
 * 村一覧の村名前に付くインラインタグ。
 */
type Level = "success" | "danger" | "default";

const levelClass: Record<Level, string> = {
  success: "text-mint-500 border-mint-500",
  danger: "text-blood-500 border-blood-500",
  default: "text-white border-night-700",
};

export function VillageTag({
  level = "default",
  className,
  children,
  ...rest
}: React.HTMLAttributes<HTMLSpanElement> & { level?: Level }) {
  return (
    <span
      className={cn(
        "inline-block border rounded-[0.25em] px-1 mr-1 leading-[1.4em] text-[0.9em]",
        levelClass[level],
        className,
      )}
      {...rest}
    >
      {children}
    </span>
  );
}
