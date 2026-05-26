import { HeartIcon } from "@heroicons/react/24/solid";
import type { VillageParticipantView, VillageView } from "./api";

/**
 * 部屋割りグリッド (旧 .old-thymeleaf/templates/village/situation.html の room タブ相当)。
 *
 * SituationOverviewPanel のタブパネルとして使うため、Panel ラッパーは持たず内容のみ返す。
 *
 * 死亡記号: 処刑 ▼ / 突然 凸 / 後追 ハート icon (絵文字禁止) / 襲撃系 ▲ / MISERABLE ▲。
 */
export function RoomGridPanel({
  village,
  day,
}: {
  village: VillageView;
  day: number;
}) {
  if (village.roomWidth == null || day <= 0) return null;

  const roomed = village.participants.list.filter(
    (p) => p.roomNumber != null && !p.isGone,
  );
  if (roomed.length === 0) return null;

  return (
    <div
      className="grid gap-[2px]"
      style={{
        gridTemplateColumns: `repeat(${village.roomWidth}, minmax(0, 1fr))`,
      }}
    >
      {roomed.map((p) => (
        <RoomCell key={p.id} participant={p} />
      ))}
    </div>
  );
}

function RoomCell({ participant }: { participant: VillageParticipantView }) {
  const dead = participant.dead;
  const room = participant.roomNumber != null
    ? String(participant.roomNumber).padStart(2, "0")
    : "--";
  return (
    <div
      className="flex flex-col items-center justify-end border border-night-700 bg-night-900 p-1"
      title={participant.name}
      style={dead ? { opacity: 0.5 } : undefined}
    >
      <img
        src={participant.chara.defaultImageUrl}
        width={participant.chara.imageWidth}
        height={participant.chara.imageHeight}
        alt={participant.name}
        loading="lazy"
        className="shrink-0"
        style={{ maxWidth: 60, maxHeight: 60, width: "auto", height: "auto" }}
      />
      <div className="text-[0.85em] font-mono text-center leading-tight mt-0.5">
        <span>{room} {participant.chara.shortName}</span>
        {dead && (
          <span className="flex items-center justify-center gap-0.5 mt-0.5">
            <span>{dead.day}d</span>
            <DeadMark code={dead.code} />
          </span>
        )}
      </div>
    </div>
  );
}

function DeadMark({ code }: { code: string }) {
  switch (code) {
    case "EXECUTE":
      return <span>▼</span>;
    case "SUDDON":
      return <span>凸</span>;
    case "SUICIDE":
      return <HeartIcon className="inline-block w-[1em] h-[1em] text-blood-500" aria-label="後追" />;
    case "ATTACK":
    case "DIVINED":
    case "TRAPPED":
    case "BOMBED":
    case "ZAKO":
    case "MISERABLE":
      return <span>▲</span>;
    default:
      return <span>▲</span>;
  }
}
