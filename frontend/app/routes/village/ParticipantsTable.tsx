import { useVillageParticipants } from "~/features/village/useMessages";
import { UserPageLink } from "./MessageCard";

const cellBorderClass = "border border-[#464545]";

/**
 * エピローグ以降の参加者正体一覧。発言ログ中の参加者一覧メッセージの位置に表示する。
 */
export function ParticipantsTable({ villageId }: { villageId: number }) {
  const { data } = useVillageParticipants(villageId, true);
  if (data == null) return null;
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
            {(data.list ?? []).map((participant, index) => (
              <tr key={index}>
                <td className={`${cellBorderClass} p-[5px]`}>{participant.name}</td>
                <td className={`${cellBorderClass} p-[5px]`}>
                  {participant.playerName != null && <UserPageLink name={participant.playerName} />}
                </td>
                <td className={`${cellBorderClass} p-[5px]`}>{participant.skillName}</td>
                <td className={`${cellBorderClass} p-[5px]`}>{participant.deadStatus}</td>
                <td className={`${cellBorderClass} p-[5px]`}>{participant.winStatus}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
