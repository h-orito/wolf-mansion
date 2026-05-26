import { useMemo } from "react";
import type {
  VillageDayFootstepView,
  VillageSituationDayView,
  VillageSituationView,
  VillageSituationVoteMemberView,
} from "./api";

/**
 * 旧 .old-thymeleaf/templates/village/situation.html の "状況 / 投票 / 足音 (日別)"
 * タブ相当を React に復元。
 *
 * 表示順: 状況テーブル → 投票テーブル → 日別足音 (旧画面と同じ順)。
 *
 * `selectedDay` は「今表示している日」を渡す。旧画面では `situation.day` が範囲
 * 上限として使われていたため、本パネルでも各表を `day <= selectedDay` で切る。
 * (= 過去日タブを開いている時にネタバレを出さない)
 */
export function SituationPanel({
  situation,
  selectedDay,
}: {
  situation: VillageSituationView | null;
  selectedDay: number;
}) {
  // React hooks の順序固定のため、useMemo は早期 return より前で呼ぶ。
  // situation == null のときも空配列扱いで evaluate して問題ない (重い処理ではない)。
  const visibleWhole = useMemo(
    () => (situation ? situation.whole.filter((d) => d.day <= selectedDay) : []),
    [situation, selectedDay],
  );
  const visibleDayFootsteps = useMemo(
    () => (situation ? situation.dayFootsteps.filter((d) => d.day <= selectedDay) : []),
    [situation, selectedDay],
  );

  if (!situation) return null;
  // 旧画面と同じく、`day = 0` (プロローグ) では何も意味ある情報がない (= テーブル全部空) ので
  // パネルごと非表示にする。
  if (selectedDay <= 0) return null;

  const hasWhole = visibleWhole.length > 0;
  // 投票は前日に行われ翌日処刑なので、`selectedDay >= 3` ではじめて表示可能な投票が
  // 1 つでも存在する (= 2d 投票結果が公開できる)。`VoteTable` の内部判定と
  // 整合させ、`selectedDay <= 2` のときは「状況」セクションに投票表が無いだけで
  // whole / dayFootsteps があれば表示する。
  const hasVote =
    situation.vote.list.length > 0 &&
    situation.vote.maxVoteCount > 0 &&
    selectedDay >= 3;
  const hasDayFootsteps = visibleDayFootsteps.some((d) => d.footstep.trim().length > 0);

  // 3 サブブロックいずれも内容なしならパネル自体出さない。
  if (!hasWhole && !hasVote && !hasDayFootsteps) return null;

  return (
    <div className="space-y-5">
      {hasWhole && <WholeTable rows={visibleWhole} />}
      {hasVote && <VoteTable vote={situation.vote} selectedDay={selectedDay} />}
      {hasDayFootsteps && <DayFootstepsTable rows={visibleDayFootsteps} />}
    </div>
  );
}

function WholeTable({ rows }: { rows: VillageSituationDayView[] }) {
  // 旧画面では `能力` 列は spoiler 公開時のみ表示していた。backend が spoiler
  // 非公開時に `ability` を空配列で返すため、ここでは「全行が空なら列ごと非表示」
  // で判定する (= dispSpoilerContent 相当)。
  const hasAbility = rows.some((r) => r.ability.length > 0);
  return (
    <div className="overflow-x-auto">
      <h3 className="text-xs opacity-80 mb-1">日次状況</h3>
      <table className="min-w-full text-xs border-collapse">
        <thead>
          <tr className="opacity-80">
            <th className="border border-night-700 px-2 py-1 text-center w-12">日付</th>
            <th className="border border-night-700 px-2 py-1 text-left">突然死</th>
            <th className="border border-night-700 px-2 py-1 text-left">処刑</th>
            <th className="border border-night-700 px-2 py-1 text-left">無惨</th>
            <th className="border border-night-700 px-2 py-1 text-left">復活</th>
            <th className="border border-night-700 px-2 py-1 text-left">後追</th>
            {hasAbility && (
              <th className="border border-night-700 px-2 py-1 text-left">能力</th>
            )}
          </tr>
        </thead>
        <tbody>
          {rows.map((r) => (
            <tr key={r.day}>
              <td className="border border-night-700 px-2 py-1 text-center text-white">
                {r.day}d
              </td>
              <td className="border border-night-700 px-2 py-1">{formatList(r.suddenlyDeath)}</td>
              <td className="border border-night-700 px-2 py-1">{formatList(r.executed)}</td>
              <td className="border border-night-700 px-2 py-1">{formatList(r.miserable)}</td>
              <td className="border border-night-700 px-2 py-1">{formatList(r.revival)}</td>
              <td className="border border-night-700 px-2 py-1">{formatList(r.suicide)}</td>
              {hasAbility && (
                // 能力履歴は backend (`AbilityDomainService.mapAbilitySituation`) が
                // `[type]from → to` の整形済み文字列を 1 件 1 行で List に詰めて返す。
                // 他の列 (突然死 / 処刑等) は単純な name list なので `formatList` で
                // 「、」区切りにするが、ability は 1 件ずつが長い文字列のため改行で並べる。
                <td className="border border-night-700 px-2 py-1 whitespace-pre-line">
                  {r.ability.join("\n")}
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

/**
 * 旧画面の投票テーブルは「列 = 投票日 (2d, 3d, ..., maxVoteCount+1)」「行 = 参加者」。
 * domain は黒箱日を除外して `voteList` を返すため、行ごとに `voteList` の `day` を
 * 直接突き合わせる (= 旧 VillageMemberVoteContent.voteTargetList と同じ復元方法)。
 */
function VoteTable({
  vote,
  selectedDay,
}: {
  vote: VillageSituationView["vote"];
  selectedDay: number;
}) {
  // 表示すべき日列を計算: 2d 〜 (selectedDay - 1) まで (= 投票は前日に行われ翌日処刑)
  // ただし「実際に存在する vote の最大日」も超えないように cap する (旧 `maxVoteCount` 互換)。
  const maxDayInVotes = vote.list.reduce<number>((acc, m) => {
    const dmax = m.voteList.reduce<number>((a, v) => Math.max(a, v.day), 0);
    return Math.max(acc, dmax);
  }, 0);
  // selectedDay - 1 まで (= 当日 selectedDay で開示できる投票は前日まで)
  // 例: selectedDay=3 なら 2d 投票結果は確定済みで表示してよい。
  const upperDay = Math.min(maxDayInVotes, Math.max(0, selectedDay - 1));
  if (upperDay < 2) return null;
  const dayCols: number[] = [];
  for (let d = 2; d <= upperDay; d++) dayCols.push(d);

  return (
    <div className="overflow-x-auto">
      <h3 className="text-xs opacity-80 mb-1">投票</h3>
      <table className="min-w-full text-xs border-collapse">
        <thead>
          <tr className="opacity-80">
            <th className="border border-night-700 px-2 py-1 text-left">投票者</th>
            {dayCols.map((d) => (
              <th key={d} className="border border-night-700 px-2 py-1 text-center">
                {d}d
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {vote.list.map((member) => (
            <VoteRow key={member.participantId} member={member} dayCols={dayCols} />
          ))}
        </tbody>
      </table>
    </div>
  );
}

function VoteRow({
  member,
  dayCols,
}: {
  member: VillageSituationVoteMemberView;
  dayCols: number[];
}) {
  // O(N×M) を避けるため day→target の Map を作る。投票は 1 日 1 回。
  const byDay = useMemo(() => {
    const m = new Map<number, string>();
    for (const cell of member.voteList) m.set(cell.day, cell.targetCharaShortName);
    return m;
  }, [member.voteList]);
  return (
    <tr>
      <td className="border border-night-700 px-2 py-1 text-white whitespace-nowrap">
        {member.charaShortName}
      </td>
      {dayCols.map((d) => (
        <td key={d} className="border border-night-700 px-2 py-1 text-center text-white">
          {byDay.get(d) ?? ""}
        </td>
      ))}
    </tr>
  );
}

function DayFootstepsTable({
  rows,
}: {
  rows: VillageDayFootstepView[];
}) {
  return (
    <div className="overflow-x-auto">
      <h3 className="text-xs opacity-80 mb-1">足音 (日別)</h3>
      <table className="min-w-full text-xs border-collapse">
        <tbody>
          {rows.map((r) => (
            <tr key={r.day}>
              <td className="border border-night-700 px-2 py-1 text-center text-white w-12">
                {r.day}d
              </td>
              <td className="border border-night-700 px-2 py-1 whitespace-pre-line">
                {r.footstep.trim().length > 0 ? r.footstep : "なし"}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function formatList(items: string[]): string {
  if (items.length === 0) return "なし";
  return items.join("、");
}
