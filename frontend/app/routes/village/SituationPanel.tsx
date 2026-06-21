import { useId, useState } from "react";

import type { VillageSituationView } from "~/features/village/api";
import { FootstepTab } from "./FootstepTab";
import { MemberListTab } from "./MemberListTab";
import { RoomAssignedTab } from "./RoomAssignedTab";
import { VoteTab } from "./VoteTab";

type TabKey = "room" | "member" | "vote" | "footstep";

/**
 * 状況サマリ。部屋割り / 参加者 / 投票 / 足音 をタブで切り替える。
 * 部屋割りタブは部屋が割り当てられた 1 日目以降のみ表示する。
 */
export function SituationPanel({
  situation,
  day,
  spoiled = false,
}: {
  situation: VillageSituationView;
  day: number;
  /** ネタバレ防止 (役職名・能力欄・足音詳細を隠す) */
  spoiled?: boolean;
}) {
  const hasRoomTab = situation.roomWidth != null && day > 0;
  const hasVoteTab = situation.vote != null;
  const hasFootstepTab = (situation.footstepList ?? []).length > 0;

  const [open, setOpen] = useState(true);
  const [activeTab, setActiveTab] = useState<TabKey>(hasRoomTab ? "room" : "member");
  const bodyId = useId();

  const tabs: { key: TabKey; label: string }[] = [
    ...(hasRoomTab ? [{ key: "room" as const, label: "部屋割り当て" }] : []),
    { key: "member", label: "参加者" },
    ...(hasVoteTab ? [{ key: "vote" as const, label: "投票" }] : []),
    ...(hasFootstepTab ? [{ key: "footstep" as const, label: "足音" }] : []),
  ];

  return (
    <div className="mb-[20px] rounded border border-[#464545] bg-[#303030]">
      <div className="rounded-t bg-[#464545] px-[15px] py-[10px]">
        <button
          type="button"
          aria-expanded={open}
          aria-controls={bodyId}
          onClick={() => setOpen((v) => !v)}
          className="cursor-pointer text-white hover:underline"
        >
          状況
        </button>
      </div>
      {open && (
        <div id={bodyId} className="p-[15px]">
          <ul className="flex border-b border-[#464545]">
            {tabs.map((tab) => (
              <li key={tab.key} className="-mb-px">
                <button
                  type="button"
                  onClick={() => setActiveTab(tab.key)}
                  className={`mr-[2px] block rounded-t-[4px] border px-[15px] py-[10px] ${
                    activeTab === tab.key
                      ? "bg-wm-base border-[#464545] border-b-transparent text-[#00bc8c]"
                      : "text-wm-accent cursor-pointer border-transparent hover:border-[#464545] hover:bg-[#303030]"
                  }`}
                >
                  {tab.label}
                </button>
              </li>
            ))}
          </ul>
          {activeTab === "room" && hasRoomTab && (
            <RoomAssignedTab
              rows={situation.roomAssignedRowList ?? []}
              situationList={situation.situationList ?? []}
              isViewableSpoilerContent={(situation.isViewableSpoilerContent ?? false) && !spoiled}
              spoiled={spoiled}
            />
          )}
          {activeTab === "member" && <MemberListTab memberList={situation.memberList ?? []} />}
          {activeTab === "vote" && situation.vote != null && (
            <VoteTab vote={situation.vote} roomAssignedRows={situation.roomAssignedRowList} />
          )}
          {activeTab === "footstep" && (
            <FootstepTab
              footstepList={situation.footstepList ?? []}
              roomAssignedRows={situation.roomAssignedRowList}
              spoiled={spoiled}
            />
          )}
        </div>
      )}
    </div>
  );
}
