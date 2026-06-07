import type { ComponentType, ReactNode } from "react";
import { Link } from "react-router";

/**
 * ホームの「タイル」UI (既存 `top-menu-selectable` の忠実再現)。
 * 暗色タイル (bg-wm-tile) + hover でアクセント (teal) 枠/文字。アイコン + 日本語ラベル(小) + 英語ラベル。
 */

type IconType = ComponentType<{ className?: string }>;

const tileClass =
  "flex min-h-[100px] flex-col items-center justify-center gap-1 border border-wm-band bg-wm-tile p-2 text-center text-white no-underline transition-colors hover:border-wm-accent hover:bg-wm-tile-hover hover:text-wm-accent disabled:opacity-60 disabled:hover:border-wm-band disabled:hover:bg-wm-tile disabled:hover:text-white";

function TileInner({ icon: Icon, jp, en }: { icon: IconType; jp: string; en: string }) {
  // 既存 (:8091) に合わせる: アイコン 12px、日本語ラベル 13px、英語ラベル 12px。
  return (
    <>
      <Icon className="h-3 w-3" />
      <span className="text-[13px]">{jp}</span>
      <span className="text-xs">{en}</span>
    </>
  );
}

/** 内部ルート (react-router) へのタイル。 */
export function TileRoute({
  to,
  icon,
  jp,
  en,
}: {
  to: string;
  icon: IconType;
  jp: string;
  en: string;
}) {
  return (
    <Link to={to} className={tileClass}>
      <TileInner icon={icon} jp={jp} en={en} />
    </Link>
  );
}

/** 未移行の SSR ページ / 外部リンクへのタイル (フルナビゲーション)。 */
export function TileAnchor({
  href,
  icon,
  jp,
  en,
}: {
  href: string;
  icon: IconType;
  jp: string;
  en: string;
}) {
  return (
    <a href={href} className={tileClass}>
      <TileInner icon={icon} jp={jp} en={en} />
    </a>
  );
}

/** アクション (ログアウト等) のタイル。 */
export function TileButton({
  onClick,
  disabled,
  icon,
  jp,
  en,
}: {
  onClick: () => void;
  disabled?: boolean;
  icon: IconType;
  jp: string;
  en: string;
}) {
  return (
    <button type="button" onClick={onClick} disabled={disabled} className={tileClass}>
      <TileInner icon={icon} jp={jp} en={en} />
    </button>
  );
}

/** 暗色帯のメニューセクション (既存 `top-menu` 帯)。白見出し + 中身。 */
export function MenuSection({ title, children }: { title: string; children: ReactNode }) {
  return (
    <section className="mb-4 bg-wm-band p-4">
      <h2 className="mb-3 text-center text-[15px] font-normal text-white">{title}</h2>
      {children}
    </section>
  );
}
