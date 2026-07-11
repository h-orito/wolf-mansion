import { useCallback, useId } from "react";

import { bottomFixedPanelClass } from "~/components/ui/Panel";
import type { VillageSituationView } from "~/features/village/api";
import { useLocalStorage } from "~/lib/useLocalStorage";
import { useVillageContext } from "~/features/village/VillageContext";
import { AnalyzerTab } from "~/features/village/components/analyzer/AnalyzerTab";
import { MemberListTab } from "./MemberListTab";
import { RoomAssignedTab } from "./RoomAssignedTab";
import { VoteTab } from "./VoteTab";

type TabKey = "room" | "member" | "vote" | "analyzer";

const STORAGE_KEY = "village_panel_situation";
const BOTTOM_FIX_KEY = "village_panel_bottom_fix";
const TAB_STORAGE_KEY = "village_panel_situation_tab";

/**
 * 状況サマリ。部屋割り当て / 参加者 / 投票 / 推理補助 をタブで切り替える。
 * 部屋割り当てタブは部屋が割り当てられた 1 日目以降のみ表示する。
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
  const village = useVillageContext();
  const hasRoomTab = situation.roomWidth != null && day > 0;
  const hasVoteTab = situation.vote != null;
  const hasAnalyzerTab = !village.status.isPrologue && !village.status.isCanceled;

  const bodyId = useId();

  const [openRaw, setOpenRaw] = useLocalStorage(STORAGE_KEY, "true");
  const open = openRaw === "true";
  const toggle = useCallback(() => setOpenRaw(String(!open)), [open, setOpenRaw]);

  const [fixRaw, setFixRaw] = useLocalStorage(BOTTOM_FIX_KEY, "");
  const isFixed = fixRaw === "situation";
  const toggleFix = useCallback(() => setFixRaw(isFixed ? "" : "situation"), [isFixed, setFixRaw]);

  const tabs: { key: TabKey; label: string }[] = [
    ...(hasRoomTab ? [{ key: "room" as const, label: "部屋割り当て" }] : []),
    { key: "member", label: "参加者" },
    ...(hasVoteTab ? [{ key: "vote" as const, label: "投票" }] : []),
    ...(hasAnalyzerTab ? [{ key: "analyzer" as const, label: "推理補助" }] : []),
  ];

  // 開いていたタブを記憶する。保存値がこの村で表示できないタブならデフォルトに戻す
  const [savedTab, setSavedTab] = useLocalStorage(TAB_STORAGE_KEY, "");
  const activeTab: TabKey = tabs.some((t) => t.key === savedTab)
    ? (savedTab as TabKey)
    : hasRoomTab
      ? "room"
      : "member";
  const setActiveTab = setSavedTab;

  return (
    <div
      className={`mb-[20px] rounded border border-border bg-surface ${isFixed ? bottomFixedPanelClass : ""}`}
    >
      <div className="flex items-center rounded-t bg-surface-raised px-[15px] py-[10px]">
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
            <ul className="flex flex-wrap border-b border-border">
              {tabs.map((tab) => (
                <li key={tab.key} className="-mb-px">
                  <button
                    type="button"
                    onClick={() => setActiveTab(tab.key)}
                    className={`mr-[2px] block rounded-t-[4px] border px-[10px] py-[8px] text-[13px] sm:px-[15px] sm:py-[10px] sm:text-[14px] ${
                      activeTab === tab.key
                        ? "bg-wm-base border-border border-b-transparent text-success"
                        : "text-wm-accent cursor-pointer border-transparent hover:border-border hover:bg-surface"
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
            {activeTab === "member" && <MemberListTab />}
            {activeTab === "vote" && situation.vote != null && (
              <VoteTab vote={situation.vote} roomAssignedRows={situation.roomAssignedRowList} />
            )}
            {activeTab === "analyzer" && (
              <AnalyzerTab
                footstepList={situation.footstepList ?? []}
                showsFootstepSpoiler={(situation.isViewableSpoilerContent ?? false) && !spoiled}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
