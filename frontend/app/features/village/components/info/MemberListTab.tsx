import type { VillageParticipantView } from "~/features/village/api";
import { useVillageContext } from "~/features/village/VillageContext";
import { allParticipants, compareByRoomNumber } from "~/features/village/participants";

const cellBorderClass = "border border-border";

type MemberGroup = {
  status: string;
  members: { name: string; deadDay: string | null; memo: string | null }[];
};

function groupParticipants(participants: VillageParticipantView[]): MemberGroup[] {
  const alive: VillageParticipantView[] = [];
  const deadByReason = new Map<string, VillageParticipantView[]>();
  const spectators: VillageParticipantView[] = [];

  for (const p of participants) {
    if (p.isSpectator) {
      spectators.push(p);
    } else if (!p.dead.isDead) {
      alive.push(p);
    } else {
      const reasonLabel = p.dead.reason?.name ?? "不明";
      const group = deadByReason.get(reasonLabel) ?? [];
      group.push(p);
      deadByReason.set(reasonLabel, group);
    }
  }

  const toMembers = (list: VillageParticipantView[]) =>
    list.map((p) => ({
      name: p.name,
      deadDay: p.dead.isDead ? `${p.dead.deadDay}d` : null,
      memo: p.memo ?? null,
    }));

  const groups: MemberGroup[] = [
    { status: "生存", members: toMembers([...alive].sort(compareByRoomNumber)) },
  ];
  for (const [reason, list] of deadByReason) {
    const sorted = [...list].sort(
      (a, b) => (a.dead.deadDay ?? 0) - (b.dead.deadDay ?? 0) || compareByRoomNumber(a, b),
    );
    groups.push({ status: reason, members: toMembers(sorted) });
  }
  if (spectators.length > 0) {
    groups.push({ status: "見学", members: toMembers([...spectators].sort(compareByRoomNumber)) });
  }
  return groups;
}

function MemberTable({ group }: { group: MemberGroup }) {
  return (
    <table className={`${cellBorderClass} mb-[21px] w-full border-collapse`}>
      <tbody>
        <tr>
          <th className={`${cellBorderClass} p-[5px] text-left align-top`}>
            {group.status} ({group.members.length}人)
          </th>
        </tr>
        {group.members.map((member) => (
          <tr key={member.name}>
            <td className={`${cellBorderClass} p-[5px]`}>
              {member.deadDay != null ? `${member.deadDay} ` : ""}
              {member.name}
              {member.memo != null ? `　[${member.memo}]` : ""}
            </td>
          </tr>
        ))}
        {group.members.length === 0 && (
          <tr>
            <td className={`${cellBorderClass} p-[5px]`}>なし</td>
          </tr>
        )}
      </tbody>
    </table>
  );
}

/** ステータス別の参加者一覧。先頭グループ (生存) を左列、残りを右列に出す。 */
export function MemberListTab() {
  const village = useVillageContext();
  const groups = groupParticipants(allParticipants(village));
  const [first, ...rest] = groups;
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
