import { Link, useLocation } from "react-router";

import { dayLabel } from "./dayLabel";

/**
 * 日付ナビゲーション。現在表示中の日はリンクにしない。
 * 先頭の「情報」は村情報モーダルを開く導線。
 */
export function DayList({
  villageId,
  dayList,
  currentDay,
  epilogueDay,
  onInfo,
}: {
  villageId: number;
  dayList: number[];
  currentDay: number;
  epilogueDay: number | null | undefined;
  /** 村情報モーダルを開く。 */
  onInfo?: () => void;
}) {
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
            <span>{dayLabel(day, epilogueDay)}</span>
          ) : (
            <Link
              to={`/village/${villageId}/day/${day}${search}`}
              className="text-wm-accent hover:underline"
            >
              {dayLabel(day, epilogueDay)}
            </Link>
          )}
        </li>
      ))}
    </ul>
  );
}
