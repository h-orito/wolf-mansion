import type { VillageParticipantView, VillageView } from "./api";

/**
 * 部屋割りグリッド (旧 .old-thymeleaf/templates/village/situation.html の room
 * 部分を React に移植したもの)。
 *
 * 表示条件: 村の `roomWidth` が確定済 (= プロローグ終了後) かつ `day > 0`。
 * `village.participants.list` のうち `roomNumber` が割り当てられたものを対象に、
 * `roomWidth` 列のグリッドへ並べる。見学者は除外。
 *
 * 死亡者は半透明 + `<deadDay>d <記号>` を重ねる (記号: 襲撃=▲ / 処刑=▼ / 突然=凸 /
 * 後追=❤ / その他=空)。死亡判定は backend が現状 (最新日基準) で返す `isDead /
 * deadDay / deadReasonCode` を使う (任意の日の生死再現は範囲外、12c 以降)。
 */
export function RoomGridPanel({
  village,
  day,
}: {
  village: VillageView;
  day: number;
}) {
  if (village.roomWidth == null || day <= 0) return null;

  // 退村済み (isGone) は旧画面でも部屋割りから外れていたので除外する。
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
  const isDead = participant.isDead;
  const room = participant.roomNumber != null
    ? String(participant.roomNumber).padStart(2, "0")
    : "--";
  const deadMark = deadMarkOf(participant.deadReasonCode);
  return (
    <div
      className={
        "relative flex flex-col items-center justify-end rounded border border-slate-700 bg-slate-900/40 p-1 " +
        (isDead ? "opacity-40" : "")
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
        {isDead && (
          <span className="block text-rose-300">
            {participant.deadDay != null ? `${participant.deadDay}d` : ""}
            {deadMark ? ` ${deadMark}` : ""}
          </span>
        )}
      </div>
    </div>
  );
}

/**
 * `CDef.DeadReason` の code から旧画面表記の記号を返す。
 * 旧 situation.html (line 80) の三項演算子は `EXECUTE → ▼`, `SUDDON → 凸`,
 * `SUICIDE → ❤︎`, それ以外 (襲撃系) → `▲` だったので、ATTACK 系
 * (`ATTACK / DIVINED / TRAPPED / BOMBED / ZAKO`) を明示列挙して同じ ▲ にする。
 * 不明 code は安全側に倒して `▲` (= 何らかの非自然死) として扱う。
 */
function deadMarkOf(code: string | null | undefined): string {
  if (!code) return "";
  switch (code) {
    case "EXECUTE":
      return "▼";
    case "SUDDON": // backend の DeadReason 突然 の code は「SUDDON」(spelling は backend 既存)
      return "凸";
    case "SUICIDE":
      return "❤";
    // 旧画面で同色 (#ff0000) 扱いだった襲撃系。記号は ATTACK 含め全て ▲ に揃える
    case "ATTACK":
    case "DIVINED":
    case "TRAPPED":
    case "BOMBED":
    case "ZAKO":
      return "▲";
    default:
      return "▲";
  }
}
