import * as React from "react";
import { cn } from "./cn";

/**
 * 旧 .panel.panel-default 相当。
 * Bootstrap 3 のパネル (薄い border + heading 上段 + body 下段)。
 *
 * 旧画面 (dark テーマ) では bg は body と同じく深紺、border は #333 で
 * 全体を区切る。heading は薄い分割線で本文と区別する。
 */
export function Panel({
  className,
  children,
  ...rest
}: React.HTMLAttributes<HTMLDivElement>) {
  return (
    <div
      className={cn(
        "border border-night-700 bg-night-950 rounded-[0.25em]",
        className,
      )}
      {...rest}
    >
      {children}
    </div>
  );
}

export function PanelHeading({
  className,
  ...rest
}: React.HTMLAttributes<HTMLDivElement>) {
  return (
    <div
      className={cn(
        "px-3 py-2 border-b border-night-700 bg-night-900",
        className,
      )}
      {...rest}
    />
  );
}

export function PanelBody({
  className,
  ...rest
}: React.HTMLAttributes<HTMLDivElement>) {
  return <div className={cn("px-3 py-3", className)} {...rest} />;
}
