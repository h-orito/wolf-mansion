import {
  ArrowDownIcon,
  ArrowPathIcon,
  ArrowUpIcon,
  Cog6ToothIcon,
  FunnelIcon,
  InformationCircleIcon,
} from "@heroicons/react/24/outline";
import { type ReactNode, useEffect, useRef } from "react";

import {
  FOOTER_MENU_HEIGHT_VAR,
  FOOTER_MENU_ID,
  useVillageScroll,
} from "~/features/village/useVillageScroll";

const buttonBaseClass =
  "flex flex-1 items-center justify-center gap-[2px] border px-[5px] py-[10px] min-[768px]:py-[4px] first:rounded-l-[3px] last:rounded-r-[3px]";

const variantClass = {
  default: {
    border: "border-success",
    active: "bg-success text-white",
    inactive: "bg-wm-base text-success",
    hover: "hover:bg-success hover:text-white",
  },
  danger: {
    border: "border-wm-danger",
    active: "bg-wm-danger text-white",
    inactive: "bg-wm-base text-wm-danger",
    hover: "hover:bg-wm-danger hover:text-white",
  },
} as const;

function MenuButton({
  icon,
  label,
  onClick,
  disabled = false,
  active = false,
  variant = "default",
  hideLabelOnMobile = true,
}: {
  icon?: ReactNode;
  label: string;
  onClick?: () => void;
  disabled?: boolean;
  /** 適用中の状態表示 (塗りつぶし)。 */
  active?: boolean;
  variant?: keyof typeof variantClass;
  /** スマホ幅ではアイコンだけにしてラベルを隠す。 */
  hideLabelOnMobile?: boolean;
}) {
  const c = variantClass[variant];
  return (
    <button
      type="button"
      className={`${buttonBaseClass} ${c.border} ${active ? c.active : c.inactive} ${
        disabled ? "opacity-50" : `cursor-pointer ${c.hover}`
      }`}
      onClick={onClick}
      disabled={disabled}
    >
      {icon}
      <span className={hideLabelOnMobile ? "max-[768px]:hidden" : ""}>{label}</span>
    </button>
  );
}

/**
 * 画面下部に固定表示する操作メニュー。新着発言を検知したら更新アイコンを点滅させる。
 * 投票先が未セットのときは、突然死を防ぐための警告ボタンを常時表示する。
 */
export function FooterMenu({
  onRefresh,
  hasNewMessage = false,
  onGotoVote,
  onFilter,
  filtering = false,
  onSettings,
  onInfo,
}: {
  onRefresh: () => void;
  hasNewMessage?: boolean;
  /** 投票先へスクロールする。渡されたときだけ未投票警告を表示する。 */
  onGotoVote?: () => void;
  /** 抽出モーダルを開く。 */
  onFilter?: () => void;
  /** 抽出条件が適用中か (ボタンを「抽出中」のアクティブ表示にする) */
  filtering?: boolean;
  /** 設定モーダルを開く。 */
  onSettings?: () => void;
  /** 村情報モーダルを開く。 */
  onInfo?: () => void;
}) {
  const iconClass = "h-[20px] w-[20px] min-[768px]:h-[14px] min-[768px]:w-[14px]";
  const { scrollToTop, scrollToBottom } = useVillageScroll();
  const rootRef = useRef<HTMLDivElement>(null);

  // safe area や画面幅で高さが変わるため、実高さを CSS 変数に書き出して
  // 下部余白 (PageLayout の spacer) がフッターに追従できるようにする。
  useEffect(() => {
    const el = rootRef.current;
    if (el == null) return;
    const update = () =>
      document.documentElement.style.setProperty(FOOTER_MENU_HEIGHT_VAR, `${el.offsetHeight}px`);
    update();
    const observer = new ResizeObserver(update);
    observer.observe(el);
    return () => {
      observer.disconnect();
      document.documentElement.style.removeProperty(FOOTER_MENU_HEIGHT_VAR);
    };
  }, []);

  return (
    <div id={FOOTER_MENU_ID} ref={rootRef} className="fixed bottom-0 left-0 z-20 w-screen">
      {onGotoVote != null && (
        <div className="flex rounded-[4px] bg-surface p-[3px] pl-[calc(3px+env(safe-area-inset-left))] pr-[calc(3px+env(safe-area-inset-right))]">
          <MenuButton
            label="投票欄へ (未セットのままだと突然死します)"
            onClick={onGotoVote}
            variant="danger"
            hideLabelOnMobile={false}
          />
        </div>
      )}
      <div className="flex rounded-[4px] bg-surface p-[3px] pb-[calc(3px+env(safe-area-inset-bottom))] pl-[calc(3px+env(safe-area-inset-left))] pr-[calc(3px+env(safe-area-inset-right))]">
        <MenuButton
          icon={<ArrowUpIcon className={iconClass} />}
          label="最上部へ"
          onClick={scrollToTop}
        />
        <MenuButton
          icon={<ArrowDownIcon className={iconClass} />}
          label="最下部へ"
          onClick={scrollToBottom}
        />
        <MenuButton
          icon={<ArrowPathIcon className={`${iconClass} ${hasNewMessage ? "flash" : ""}`} />}
          label="更新"
          onClick={onRefresh}
        />
        <MenuButton
          icon={<FunnelIcon className={iconClass} />}
          label={filtering ? "抽出中" : "抽出"}
          onClick={onFilter}
          disabled={onFilter == null}
          active={filtering}
        />
        <MenuButton
          icon={<InformationCircleIcon className={iconClass} />}
          label="情報"
          onClick={onInfo}
          disabled={onInfo == null}
        />
        <MenuButton
          icon={<Cog6ToothIcon className={iconClass} />}
          label="設定"
          onClick={onSettings}
          disabled={onSettings == null}
        />
      </div>
    </div>
  );
}
