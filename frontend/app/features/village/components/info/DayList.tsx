import { Link, useLocation } from "react-router";

import { useVillageContext } from "~/features/village/VillageContext";
import { dayLabel } from "./dayLabel";

/**
 * 日付ナビゲーション。現在表示中の日はリンクにしない。
 * 先頭の「情報」は村情報モーダルを開く導線。
 */
export function DayList({
  currentDay,
  onInfo,
}: {
  currentDay: number;
  /** 村情報モーダルを開く。 */
  onInfo?: () => void;
}) {
  const village = useVillageContext();
  const dayList = (village.days.list ?? []).map((d) => d.day);
  // 抽出条件 (searchParams) は日付遷移後も引き継ぐ
  const { search } = useLocation();
  return (
    <ul className="my-[10px] pl-0">
      <li className="mr-[10px] inline list-none">
        {onInfo != null ? (
          <button
            type="button"
            className="text-wm-accent cursor-pointer hover:underline"
            onClick={onInfo}
          >
            情報
          </button>
        ) : (
          <span className="text-wm-accent opacity-50">情報</span>
        )}
      </li>
      {dayList.map((day) => (
        <li key={day} className="mr-[10px] inline list-none">
          {day === currentDay ? (
            <span>{dayLabel(day, village.epilogueDay)}</span>
          ) : (
            <Link
              to={`/village/${village.id}/day/${day}${search}`}
              className="text-wm-accent hover:underline"
            >
              {dayLabel(day, village.epilogueDay)}
            </Link>
          )}
        </li>
      ))}
    </ul>
  );
}
