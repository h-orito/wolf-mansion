import type { components } from "~/api/types";
import { UserPageLink } from "../message/MessageCard";

type VillageParticipantView = components["schemas"]["VillageParticipantView"];

const cellBorderClass = "border border-border";

function deadStatus(p: VillageParticipantView): string {
  if (p.isSpectator) return "";
  if (!p.dead.isDead) return "生存";
  const name = p.dead.reason?.name ?? "";
  const reason = name.endsWith("死") ? name : `${name}死`;
  return `${p.dead.deadDay}d${reason}`;
}

function winStatus(p: VillageParticipantView): string {
  if (p.isWin == null) return "";
  return p.isWin ? "勝利" : "敗北";
}

function skillName(p: VillageParticipantView): string {
  if (p.isSpectator) return "見学参加";
  return p.skill?.name ?? "";
}

/**
 * エピローグ以降の参加者正体一覧。発言ログ中の参加者一覧メッセージの位置に表示する。
 */
export function ParticipantsTable({ participants }: { participants: VillageParticipantView[] }) {
  if (participants.length === 0) return null;
  return (
    <div>
      <p>館に集まった村人達の正体は、以下の通りだった。</p>
      <div className="overflow-x-auto">
        <table className={`${cellBorderClass} w-full border-collapse text-village-sm`}>
          <thead>
            <tr>
              <th className={`${cellBorderClass} p-[5px] text-left`}>参加者</th>
              <th className={`${cellBorderClass} p-[5px] text-left`}>ユーザー名</th>
              <th className={`${cellBorderClass} p-[5px] text-left`}>役職</th>
              <th className={`${cellBorderClass} p-[5px] text-left`}>生死</th>
              <th className={`${cellBorderClass} p-[5px] text-left`}>勝敗</th>
            </tr>
          </thead>
          <tbody>
            {participants.map((p) => (
              <tr key={p.id}>
                <td className={`${cellBorderClass} p-[5px]`}>{p.name}</td>
                <td className={`${cellBorderClass} p-[5px]`}>
                  {p.player?.name != null && <UserPageLink name={p.player.name} />}
                </td>
                <td className={`${cellBorderClass} p-[5px]`}>{skillName(p)}</td>
                <td className={`${cellBorderClass} p-[5px]`}>{deadStatus(p)}</td>
                <td className={`${cellBorderClass} p-[5px]`}>{winStatus(p)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
