import type { ReactNode } from "react";

/**
 * タイトルバー付きのパネル (村画面のフォーム群など)。開閉が要る場合は
 * `CollapsiblePanel` を使う。警告状態などでタイトルバーの見た目を変える場合は
 * `headerClassName`、タイトル横の注記は `headerExtra` で指定する。
 */
export function Panel({
  title,
  headerClassName = "bg-[#464545]",
  headerExtra,
  children,
}: {
  title: string;
  headerClassName?: string;
  headerExtra?: ReactNode;
  children: ReactNode;
}) {
  return (
    <div className="mb-[20px] rounded border border-[#464545] bg-[#303030]">
      <div className={`rounded-t px-[15px] py-[10px] ${headerClassName}`}>
        <span className="text-[15px] text-white">{title}</span>
        {headerExtra != null && (
          <span className="ml-[5px] text-[12px] text-white">{headerExtra}</span>
        )}
      </div>
      <div className="p-[15px]">{children}</div>
    </div>
  );
}
