import type { VillageParticipantView, VillageView } from "./api";

/**
 * 部屋割りグリッド (旧 .old-thymeleaf/templates/village/situation.html の room
 * 部分を React に移植したもの)。
 *
 * 表示条件: 村の `roomWidth` が確定済 (= プロローグ終了後) かつ `day > 0`。
 * `village.participants.list` のうち `roomNumber` が割り当てられたものを対象に、
 * `roomWidth` 列のグリッドへ並べる。見学者と退村済は除外。
 *
 * 死亡者は半透明 + `<dead.day>d <記号>` を重ねる (記号: 襲撃系=▲ / 処刑=▼ /
 * 突然=凸 / 後追=❤ / MISERABLE=▲)。進行中の無惨死は backend が code/name を
 * `MISERABLE` / `無惨` に統一して返すので、フロントは MISERABLE → ▲ で扱う。
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
    <section className="rounded-xl bg-slate-800/40 border border-slate-700 p-4">
      <h2 className="text-sm text-slate-400 mb-3">部屋割り</h2>
      <div
        className="grid gap-2"
        style={{
          gridTemplateColumns: `repeat(${village.roomWidth}, minmax(0, 1fr))`,
        }}
      >
        {roomed.map((p) => (
          <RoomCell key={p.id} participant={p} />
        ))}
      </div>
    </section>
  );
}

function RoomCell({ participant }: { participant: VillageParticipantView }) {
  const dead = participant.dead;
  const room = participant.roomNumber != null
    ? String(participant.roomNumber).padStart(2, "0")
    : "--";
  return (
    <div
      className={
        "relative flex flex-col items-center justify-end rounded border border-slate-700 bg-slate-900/40 p-1 " +
        (dead ? "opacity-40" : "")
      }
      title={participant.name}
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
      <div className="text-[10px] font-mono text-center leading-tight text-slate-200 mt-0.5">
        <span>{room} {participant.chara.shortName}</span>
        {dead && (
          <span className="block text-rose-300">
            {dead.day}d {deadMarkOf(dead.code)}
          </span>
        )}
      </div>
    </div>
  );
}

/**
 * `CDef.DeadReason` の code (または backend の合成コード MISERABLE) から旧画面
 * 表記の記号を返す。
 * 旧 situation.html (line 80) の三項演算子は `EXECUTE → ▼`, `SUDDON → 凸`,
 * `SUICIDE → ❤︎`, それ以外 (襲撃系) → `▲`。襲撃系 (`ATTACK / DIVINED /
 * TRAPPED / BOMBED / ZAKO`) と `MISERABLE` (進行中マスク) を明示列挙して同じ ▲ にする。
 * 不明 code も安全側に倒して `▲` (= 何らかの無惨な死) として扱う。
 */
function deadMarkOf(code: string): string {
  switch (code) {
    case "EXECUTE":
      return "▼";
    case "SUDDON": // backend の DeadReason 突然 の code は「SUDDON」(spelling は backend 既存)
      return "凸";
    case "SUICIDE":
      return "❤";
    // 旧画面で同色 (#ff0000) 扱いだった襲撃系。記号は ATTACK 含め全て ▲ に揃える。
    // MISERABLE は進行中マスクの合成コード (backend が無惨死を統一して返す)。
    case "ATTACK":
    case "DIVINED":
    case "TRAPPED":
    case "BOMBED":
    case "ZAKO":
    case "MISERABLE":
      return "▲";
    default:
      return "▲";
  }
}
