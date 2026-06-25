import { useCallback, useId, useState } from "react";

import type { VillageSituationView } from "~/features/village/api";
import { useLocalStorage } from "~/lib/useLocalStorage";
import { FootstepTab } from "./FootstepTab";
import { MemberListTab } from "./MemberListTab";
import { RoomAssignedTab } from "./RoomAssignedTab";
import { VoteTab } from "./VoteTab";

type TabKey = "room" | "member" | "vote" | "footstep";

const STORAGE_KEY = "village_panel_situation";
const BOTTOM_FIX_KEY = "village_panel_bottom_fix";

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

  const bodyId = useId();

  const [openRaw, setOpenRaw] = useLocalStorage(STORAGE_KEY, "true");
  const open = openRaw === "true";
  const toggle = useCallback(() => setOpenRaw(String(!open)), [open, setOpenRaw]);

  const [fixRaw, setFixRaw] = useLocalStorage(BOTTOM_FIX_KEY, "");
  const isFixed = fixRaw === "situation";
  const toggleFix = useCallback(() => setFixRaw(isFixed ? "" : "situation"), [isFixed, setFixRaw]);

  const [activeTab, setActiveTab] = useState<TabKey>(hasRoomTab ? "room" : "member");

  const tabs: { key: TabKey; label: string }[] = [
    ...(hasRoomTab ? [{ key: "room" as const, label: "部屋割り当て" }] : []),
    { key: "member", label: "参加者" },
    ...(hasVoteTab ? [{ key: "vote" as const, label: "投票" }] : []),
    ...(hasFootstepTab ? [{ key: "footstep" as const, label: "足音" }] : []),
  ];

  return (
    <div
      className={`mb-[20px] rounded border border-[#464545] bg-[#303030] ${isFixed ? "fixed bottom-0 left-0 z-20 mb-0 w-screen max-h-[30vh] overflow-y-auto" : ""}`}
    >
      <div className="flex items-center rounded-t bg-[#464545] px-[15px] py-[10px]">
        <div className="flex-1">
          <button
            type="button"
            aria-expanded={open}
            aria-controls={bodyId}
            onClick={toggle}
            className="cursor-pointer text-[15px] text-white hover:underline"
          >
            状況
          </button>
        </div>
        <button
          type="button"
          onClick={toggleFix}
          className="cursor-pointer text-[12px] text-white hover:underline"
        >
          {isFixed ? "固定解除" : "固定"}
        </button>
      </div>
      <div
        id={bodyId}
        className={`grid transition-[grid-template-rows] duration-300 ease-in-out ${open ? "grid-rows-[1fr]" : "grid-rows-[0fr]"}`}
      >
        <div className="overflow-hidden">
          <div className="p-[15px]">
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
        </div>
      </div>
    </div>
  );
}
