import { Link } from "react-router";

import { dayLabel } from "./dayLabel";

/**
 * 日付ナビゲーション。現在表示中の日はリンクにしない。
 * 先頭の「情報」は村情報モーダルを開く導線 (モーダル実装までは無効表示)。
 */
export function DayList({
  villageId,
  dayList,
  currentDay,
  epilogueDay,
}: {
  villageId: number;
  dayList: number[];
  currentDay: number;
  epilogueDay: number | null | undefined;
}) {
  return (
    <ul className="pl-0 text-[10.32px]">
      <li className="mr-[10px] inline list-none">
        <span className="text-wm-accent opacity-50">情報</span>
      </li>
      {dayList.map((day) => (
        <li key={day} className="mr-[10px] inline list-none">
          {day === currentDay ? (
            <span>{dayLabel(day, epilogueDay)}</span>
          ) : (
            <Link
              to={`/village/${villageId}/day/${day}`}
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
