import type { AnchorHTMLAttributes } from "react";
import { Link, type LinkProps } from "react-router";

const linkStyle = "text-wm-accent hover:underline";

/** 本文中の SPA 内リンク。 */
export function TextLink({ className = "", ...props }: LinkProps) {
  return <Link className={`${linkStyle} ${className}`} {...props} />;
}

/** 本文中の外部サイトリンク。別タブで開く。 */
export function ExternalLink({
  className = "",
  ...props
}: AnchorHTMLAttributes<HTMLAnchorElement>) {
  return <a className={`${linkStyle} ${className}`} target="_blank" rel="noreferrer" {...props} />;
}
