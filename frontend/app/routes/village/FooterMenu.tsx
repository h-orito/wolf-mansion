import {
  ArrowDownIcon,
  ArrowPathIcon,
  ArrowUpIcon,
  Cog6ToothIcon,
  FunnelIcon,
  InformationCircleIcon,
} from "@heroicons/react/24/outline";
import type { ReactNode } from "react";

const buttonBaseClass =
  "flex flex-1 items-center justify-center gap-[2px] border border-[#00bc8c] bg-wm-base px-[5px] pt-[4px] pb-[4px] text-[13px] text-[#00bc8c] first:rounded-l-[3px] last:rounded-r-[3px]";

function MenuButton({
  icon,
  label,
  onClick,
  disabled = false,
}: {
  icon: ReactNode;
  label: string;
  onClick?: () => void;
  disabled?: boolean;
}) {
  return (
    <button
      type="button"
      className={`${buttonBaseClass} ${
        disabled ? "opacity-50" : "cursor-pointer hover:bg-[#00bc8c] hover:text-white"
      }`}
      onClick={onClick}
      disabled={disabled}
    >
      {icon}
      <span className="max-[768px]:hidden">{label}</span>
    </button>
  );
}

/**
 * 画面下部に固定表示する操作メニュー。抽出 / 情報 / 設定はそれぞれのモーダル実装までは
 * 無効状態で出す (ボタン構成は最終形を保つ)。新着発言を検知したら更新アイコンを点滅させる。
 */
export function FooterMenu({
  onRefresh,
  hasNewMessage = false,
}: {
  onRefresh: () => void;
  hasNewMessage?: boolean;
}) {
  const iconClass = "h-[14px] w-[14px]";
  const gotoTop = () => window.scrollTo({ top: 0 });
  const gotoBottom = () => {
    const bottom = document.getElementById("bottom");
    if (bottom) bottom.scrollIntoView();
  };

  return (
    <div className="fixed bottom-0 left-0 z-20 w-screen">
      <div className="flex rounded-[4px] bg-[#303030] p-[3px]">
        <MenuButton
          icon={<ArrowUpIcon className={iconClass} />}
          label="最上部へ"
          onClick={gotoTop}
        />
        <MenuButton
          icon={<ArrowDownIcon className={iconClass} />}
          label="最下部へ"
          onClick={gotoBottom}
        />
        <MenuButton
          icon={<ArrowPathIcon className={`${iconClass} ${hasNewMessage ? "flash" : ""}`} />}
          label="更新"
          onClick={onRefresh}
        />
        <MenuButton icon={<FunnelIcon className={iconClass} />} label="抽出" disabled />
        <MenuButton icon={<InformationCircleIcon className={iconClass} />} label="情報" disabled />
        <MenuButton icon={<Cog6ToothIcon className={iconClass} />} label="設定" disabled />
      </div>
    </div>
  );
}
