import { useEffect, useState } from "react";

function pad(n: number): string {
  return String(n).padStart(2, "0");
}

function format(diffMs: number): string {
  const diff = Math.max(diffMs, 0);
  // 100 時間以上はカンスト表示 (募集中の村は開始まで数日あるため)
  if (diff >= 100 * 60 * 60 * 1000) return "99:59:59";
  const hours = Math.floor(diff / (60 * 60 * 1000));
  const minutes = Math.floor((diff % (60 * 60 * 1000)) / (60 * 1000));
  const seconds = Math.floor((diff % (60 * 1000)) / 1000);
  return `${pad(hours)}:${pad(minutes)}:${pad(seconds)}`;
}

/**
 * 次回更新までの残り時間 (HH:MM:SS)。500ms ごとに更新する。
 * 更新日時が無い (終了した村) 場合は null を返す。
 */
export function useCountdown(dayChangeDatetime: string | null | undefined): string | null {
  const [leftTime, setLeftTime] = useState<string | null>(null);

  useEffect(() => {
    if (dayChangeDatetime == null) {
      setLeftTime(null);
      return;
    }
    const target = new Date(dayChangeDatetime).getTime();
    const update = () => setLeftTime(format(target - Date.now()));
    update();
    const timer = setInterval(update, 500);
    return () => clearInterval(timer);
  }, [dayChangeDatetime]);

  return leftTime;
}
