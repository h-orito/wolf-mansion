import { useMemo } from "react";
import type {
  VillageDayFootstepView,
  VillageSituationDayView,
  VillageSituationView,
  VillageSituationVoteMemberView,
} from "./api";
import { Panel, PanelBody, PanelHeading } from "~/components/ui/Panel";
import { Table, TableResponsive } from "~/components/ui/Table";

/**
 * 旧 .old-thymeleaf/templates/village/situation.html の "状況 / 投票 / 足音 (日別)"
 * タブ相当を React に復元。Step 13c で Panel + Table primitive に揃え、旧 BS3
 * .panel.panel-default + .table-bordered.table-condensed.small の見た目に寄せた。
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
  const visibleWhole = useMemo(
    () => (situation ? situation.whole.filter((d) => d.day <= selectedDay) : []),
    [situation, selectedDay],
  );
  const visibleDayFootsteps = useMemo(
    () => (situation ? situation.dayFootsteps.filter((d) => d.day <= selectedDay) : []),
    [situation, selectedDay],
  );

  if (!situation) return null;
  if (selectedDay <= 0) return null;

  const hasWhole = visibleWhole.length > 0;
  // 投票は前日に行われ翌日処刑なので、`selectedDay >= 3` ではじめて表示可能な投票が
  // 1 つでも存在する (= 2d 投票結果が公開できる)。
  const hasVote =
    situation.vote.list.length > 0 &&
    situation.vote.maxVoteCount > 0 &&
    selectedDay >= 3;
  const hasDayFootsteps = visibleDayFootsteps.some((d) => d.footstep.trim().length > 0);

  if (!hasWhole && !hasVote && !hasDayFootsteps) return null;

  return (
    <Panel>
      <PanelHeading>
        <h2 className="text-sm m-0">状況</h2>
      </PanelHeading>
      <PanelBody>
        <div className="space-y-3">
          {hasWhole && <WholeTable rows={visibleWhole} />}
          {hasVote && <VoteTable vote={situation.vote} selectedDay={selectedDay} />}
          {hasDayFootsteps && <DayFootstepsTable rows={visibleDayFootsteps} />}
        </div>
      </PanelBody>
    </Panel>
  );
}

function WholeTable({ rows }: { rows: VillageSituationDayView[] }) {
  // 旧画面では `能力` 列は spoiler 公開時のみ表示していた。backend が spoiler
  // 非公開時に `ability` を空配列で返すため、ここでは「全行が空なら列ごと非表示」
  // で判定する (= dispSpoilerContent 相当)。
  const hasAbility = rows.some((r) => r.ability.length > 0);
  return (
    <div>
      <h3 className="text-[0.95em] mb-1">日次状況</h3>
      <TableResponsive>
        <Table>
          <thead>
            <tr>
              <th className="text-center w-12">日付</th>
              <th>突然死</th>
              <th>処刑</th>
              <th>無惨</th>
              <th>復活</th>
              <th>後追</th>
              {hasAbility && <th>能力</th>}
            </tr>
          </thead>
          <tbody>
            {rows.map((r) => (
              <tr key={r.day}>
                <td className="text-center">{r.day}d</td>
                <td>{formatList(r.suddenlyDeath)}</td>
                <td>{formatList(r.executed)}</td>
                <td>{formatList(r.miserable)}</td>
                <td>{formatList(r.revival)}</td>
                <td>{formatList(r.suicide)}</td>
                {hasAbility && (
                  // 能力履歴は backend (`AbilityDomainService.mapAbilitySituation`) が
                  // `[type]from → to` の整形済み文字列を 1 件 1 行で List に詰めて返す。
                  <td className="whitespace-pre-line">{r.ability.join("\n")}</td>
                )}
              </tr>
            ))}
          </tbody>
        </Table>
      </TableResponsive>
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
  const maxDayInVotes = vote.list.reduce<number>((acc, m) => {
    const dmax = m.voteList.reduce<number>((a, v) => Math.max(a, v.day), 0);
    return Math.max(acc, dmax);
  }, 0);
  const upperDay = Math.min(maxDayInVotes, Math.max(0, selectedDay - 1));
  if (upperDay < 2) return null;
  const dayCols: number[] = [];
  for (let d = 2; d <= upperDay; d++) dayCols.push(d);

  return (
    <div>
      <h3 className="text-[0.95em] mb-1">投票</h3>
      <TableResponsive>
        <Table>
          <thead>
            <tr>
              <th>投票者</th>
              {dayCols.map((d) => (
                <th key={d} className="text-center">{d}d</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {vote.list.map((member) => (
              <VoteRow key={member.participantId} member={member} dayCols={dayCols} />
            ))}
          </tbody>
        </Table>
      </TableResponsive>
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
  const byDay = useMemo(() => {
    const m = new Map<number, string>();
    for (const cell of member.voteList) m.set(cell.day, cell.targetCharaShortName);
    return m;
  }, [member.voteList]);
  return (
    <tr>
      <td className="whitespace-nowrap">{member.charaShortName}</td>
      {dayCols.map((d) => (
        <td key={d} className="text-center">{byDay.get(d) ?? ""}</td>
      ))}
    </tr>
  );
}

function DayFootstepsTable({ rows }: { rows: VillageDayFootstepView[] }) {
  return (
    <div>
      <h3 className="text-[0.95em] mb-1">足音 (日別)</h3>
      <TableResponsive>
        <Table>
          <tbody>
            {rows.map((r) => (
              <tr key={r.day}>
                <td className="text-center w-12">{r.day}d</td>
                <td className="whitespace-pre-line">
                  {r.footstep.trim().length > 0 ? r.footstep : "なし"}
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </TableResponsive>
    </div>
  );
}

function formatList(items: string[]): string {
  if (items.length === 0) return "なし";
  return items.join("、");
}
