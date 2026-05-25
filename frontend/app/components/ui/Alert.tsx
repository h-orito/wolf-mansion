import * as React from "react";
import { cn } from "./cn";

/**
 * 旧 .alert-normal / .alert-warning-say / .alert-dead / .alert-status 相当。
 *
 *   .alert-normal       { border: 1px solid #fff; }
 *   .alert-warning-say  { border: 1px solid #f39c12; color: #f39c12; }
 *   .alert-dead         { border: 1px solid #3498db; color: #3498db; }
 *   .alert-status       { border: 1px solid #00bc8c; color: #00bc8c; }
 *
 * 共通: padding 5px / margin-bottom 10px / border-radius 4px
 */
type Tone = "normal" | "warning" | "dead" | "status";

const toneClass: Record<Tone, string> = {
  normal: "border-white text-white",
  warning: "border-warning-500 text-warning-500",
  dead: "border-info-500 text-info-500",
  status: "border-mint-600 text-mint-600",
};

export function Alert({
  tone = "normal",
  className,
  ...rest
}: React.HTMLAttributes<HTMLDivElement> & { tone?: Tone }) {
  return (
    <div
      role={tone === "warning" ? "alert" : undefined}
      className={cn(
        "border rounded-[0.25em] px-2 py-1 mb-2",
        toneClass[tone],
        className,
      )}
      {...rest}
    />
  );
}
