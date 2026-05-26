import * as React from "react";
import { cn } from "./cn";

/**
 * 旧 .panel.panel-default 相当 (Bootstrap 3 + Bootswatch Darkly)。
 *
 * 本番計測値 (wolfort.net /village/13):
 * - .panel.panel-default: bg #303030 (night-650) / border #464545 (night-550) / radius 4px
 * - .panel-heading: bg #464545 (night-550) / padding 10px 15px / radius 3px 3px 0 0 / color white
 */
export function Panel({
  className,
  children,
  ...rest
}: React.HTMLAttributes<HTMLDivElement>) {
  return (
    <div
      className={cn(
        "border border-night-550 bg-night-650 rounded-[4px]",
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
        "px-[15px] py-[10px] bg-night-550 rounded-t-[3px]",
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
  return <div className={cn("px-[15px] py-[15px]", className)} {...rest} />;
}
