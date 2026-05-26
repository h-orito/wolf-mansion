import {
  ArrowDownIcon,
  ArrowPathIcon,
  ArrowUpIcon,
  FunnelIcon,
  InformationCircleIcon,
} from "@heroicons/react/24/outline";

/**
 * 旧 .old-thymeleaf/templates/village/footer-menu.html (`#footer-menu.footer-menu-bottom`)
 * 相当の bottom-fixed dock。Step 13c で復元。
 *
 * 旧画面と同じく ["最上部へ", "最下部へ", "更新", "抽出", "情報"] を並べ、
 * `data-floating-group` 相当 (display: flex; padding: 3px) で横並び。
 * `投票欄へ` (未投票時のみ) は本実装でも `showVoteShortcut` で出し分ける。
 *
 * 旧画面の「設定」(modal-dsetting) は本 PR ではスコープ外。
 *
 * 絵文字使用禁止 (13a 方針) のため、glyphicon は heroicons の SVG に置換。
 */
export function FooterMenuDock({
  onOpenInfo,
  onOpenFilter,
  isFiltered,
  showVoteShortcut,
  onGoToVote,
  onRefresh,
}: {
  onOpenInfo: () => void;
  onOpenFilter: () => void;
  isFiltered: boolean;
  showVoteShortcut: boolean;
  onGoToVote?: () => void;
  onRefresh?: () => void;
}) {
  return (
    <div
      id="footer-menu"
      className="fixed bottom-0 left-0 right-0 z-20 w-screen"
    >
      {showVoteShortcut && onGoToVote && (
        <div
          className="flex w-full px-[3px] py-[3px] bg-night-500"
        >
          <DockButton variant="vote" onClick={onGoToVote}>
            投票欄へ (未セットのままだと突然死します)
          </DockButton>
        </div>
      )}
      <div className="flex w-full px-[3px] py-[3px] bg-night-500">
        <DockButton onClick={() => window.scrollTo({ top: 0, behavior: "smooth" })}>
          <ArrowUpIcon className="w-4 h-4 inline-block" aria-hidden="true" />
          <span className="hidden sm:inline ml-1">最上部へ</span>
        </DockButton>
        <DockButton
          onClick={() =>
            window.scrollTo({ top: document.body.scrollHeight, behavior: "smooth" })
          }
        >
          <ArrowDownIcon className="w-4 h-4 inline-block" aria-hidden="true" />
          <span className="hidden sm:inline ml-1">最下部へ</span>
        </DockButton>
        {onRefresh && (
          <DockButton onClick={onRefresh}>
            <ArrowPathIcon className="w-4 h-4 inline-block" aria-hidden="true" />
            <span className="hidden sm:inline ml-1">更新</span>
          </DockButton>
        )}
        <DockButton onClick={onOpenFilter} active={isFiltered}>
          <FunnelIcon className="w-4 h-4 inline-block" aria-hidden="true" />
          <span className="hidden sm:inline ml-1">{isFiltered ? "抽出中" : "抽出"}</span>
        </DockButton>
        <DockButton onClick={onOpenInfo}>
          <InformationCircleIcon className="w-4 h-4 inline-block" aria-hidden="true" />
          <span className="hidden sm:inline ml-1">情報</span>
        </DockButton>
      </div>
    </div>
  );
}

function DockButton({
  children,
  onClick,
  active,
  variant,
}: {
  children: React.ReactNode;
  onClick?: () => void;
  active?: boolean;
  variant?: "vote";
}) {
  // 旧 #footer-menu .btn-footermenu: bg #222222 / mint border + mint text
  // hover / active: mint bg + white
  // .footermenu-vote: red border + red text、hover で赤 bg + white
  const isVote = variant === "vote";
  const base =
    "flex-1 px-[6px] py-[5px] mx-[1px] border text-[0.95em] cursor-pointer text-center leading-tight rounded-[3px] transition-colors duration-100";
  let cls: string;
  if (isVote) {
    cls = `${base} border-blood-500 text-blood-500 bg-night-500 hover:bg-blood-500 hover:text-white`;
  } else if (active) {
    cls = `${base} border-mint-600 bg-mint-600 text-white`;
  } else {
    cls = `${base} border-mint-600 text-mint-600 bg-night-500 hover:bg-mint-600 hover:text-white`;
  }
  return (
    <button type="button" className={cls} onClick={onClick}>
      {children}
    </button>
  );
}
