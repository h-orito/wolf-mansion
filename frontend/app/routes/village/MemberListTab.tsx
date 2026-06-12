import type { VillageMemberContent } from "~/features/village/api";

const cellBorderClass = "border border-[#464545]";

function MemberTable({ group }: { group: VillageMemberContent }) {
  const members = group.statusMemberList ?? [];
  return (
    <table className={`${cellBorderClass} mb-[21px] w-full border-collapse text-[10.32px]`}>
      <tbody>
        <tr>
          <th className={`${cellBorderClass} p-[5px] text-left align-top`}>
            {group.status} ({members.length}人)
          </th>
        </tr>
        {members.map((member) => (
          <tr key={member.charaName}>
            <td className={`${cellBorderClass} p-[5px]`}>
              {member.deadDay != null ? `${member.deadDay} ` : ""}
              {member.charaName}
              {member.memo != null ? `　[${member.memo}]` : ""}
            </td>
          </tr>
        ))}
        {members.length === 0 && (
          <tr>
            <td className={`${cellBorderClass} p-[5px]`}>なし</td>
          </tr>
        )}
      </tbody>
    </table>
  );
}

/** ステータス別の参加者一覧。先頭グループ (生存) を左列、残りを右列に出す。 */
export function MemberListTab({ memberList }: { memberList: VillageMemberContent[] }) {
  const [first, ...rest] = memberList;
  return (
    <div className="flex pt-[10px] pb-[10px]">
      <div className="w-1/2 px-[15px]">{first && <MemberTable group={first} />}</div>
      <div className="w-1/2 px-[15px]">
        {rest.map((group) => (
          <MemberTable key={group.status} group={group} />
        ))}
      </div>
    </div>
  );
}
