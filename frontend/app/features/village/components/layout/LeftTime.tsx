import { useVillageContext } from "~/features/village/VillageContext";
import { useCountdown } from "~/features/village/useCountdown";

export function LeftTime() {
  const village = useVillageContext();
  const dayChangeDatetime = !village.status.isFinished
    ? (village.days.list?.at(-1)?.dayChangeDatetime ?? null)
    : null;
  const leftTime = useCountdown(dayChangeDatetime);

  if (leftTime == null) return null;
  return (
    <div className="fixed top-[5px] right-[5px] z-[100] rounded bg-[#3498db] p-[5px] text-white">
      更新まで <span>{leftTime}</span>
    </div>
  );
}
