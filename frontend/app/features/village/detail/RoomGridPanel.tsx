import { HeartIcon } from "@heroicons/react/24/solid";
import type { VillageParticipantView, VillageView } from "./api";
import { Panel, PanelBody, PanelHeading } from "~/components/ui/Panel";

/**
 * 部屋割りグリッド (旧 .old-thymeleaf/templates/village/situation.html の room
 * 部分を React に移植したもの)。Step 13c で旧 BS3 .panel.panel-default + room
 * セルの見た目 (border / selected-room の mint border) に揃えた。
 *
 * 表示条件: 村の `roomWidth` が確定済 (= プロローグ終了後) かつ `day > 0`。
 * `village.participants.list` のうち `roomNumber` が割り当てられたものを対象に、
 * `roomWidth` 列のグリッドへ並べる。見学者と退村済は除外。
 *
 * 死亡者は半透明 + `<dead.day>d <記号>` を重ねる (記号: 襲撃系=▲ / 処刑=▼ /
 * 突然=凸 / 後追=ハート icon / MISERABLE=▲)。
 * 進行中の無惨死は backend が code/name を `MISERABLE` / `無惨` に統一して返すので、
 * フロントは MISERABLE → ▲ で扱う。
 *
 * 死亡判定は backend が現状 (最新日基準) で返す `dead` (= DeadView | null) を
 * 使う (任意の日の生死再現は範囲外、12c 以降)。
 */
export function RoomGridPanel({
  village,
  day,
}: {
  village: VillageView;
  day: number;
}) {
  if (village.roomWidth == null || day <= 0) return null;

  // 退村済 (isGone) は旧画面でも部屋割りから外れていたので除外する。
  // 部屋番号順は backend (`sortedByRoomNumber()`) で確定済なのでフロント側 sort は不要。
  const roomed = village.participants.list.filter(
    (p) => p.roomNumber != null && !p.isGone,
  );
  if (roomed.length === 0) return null;

  return (
    <Panel>
      <PanelHeading>
        <h2 className="text-sm m-0">部屋割り</h2>
      </PanelHeading>
      <PanelBody>
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
      </PanelBody>
    </Panel>
  );
}

function RoomCell({ participant }: { participant: VillageParticipantView }) {
  const dead = participant.dead;
  const room = participant.roomNumber != null
    ? String(participant.roomNumber).padStart(2, "0")
    : "--";
  return (
    <div
      className="relative flex flex-col items-center justify-end border border-night-700 bg-night-900 p-1"
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

/**
 * `CDef.DeadReason` の code (または backend の合成コード MISERABLE) から旧画面
 * 表記の記号を返す。
 * 旧 situation.html (line 80) の三項演算子: `EXECUTE → ▼`, `SUDDON → 凸`,
 * `SUICIDE → ❤︎` (heart-icon に置換), それ以外 (襲撃系) → `▲`。
 *
 * 旧画面では `❤︎` は絵文字だったが、13a で「絵文字使用禁止」が方針として決まっている
 * ため heroicons の HeartIcon に置換する。
 */
function DeadMark({ code }: { code: string }) {
  switch (code) {
    case "EXECUTE":
      return <span>▼</span>;
    case "SUDDON":
      // backend の DeadReason 突然 の code は「SUDDON」(spelling は backend 既存)
      return <span>凸</span>;
    case "SUICIDE":
      return <HeartIcon className="inline-block w-[1em] h-[1em] text-blood-500" aria-label="後追" />;
    // 旧画面で同色 (#ff0000) 扱いだった襲撃系。記号は ATTACK 含め全て ▲ に揃える。
    // MISERABLE は進行中マスクの合成コード (backend が無惨死を統一して返す)。
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
