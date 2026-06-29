import type {
  ParticipantSituationView,
  VillageActionRequest,
  VillageCreatorSayRequest,
  VillageDebugView,
  VillageParticipateRequest,
  VillageSayRequest,
  VillageSituationView,
} from "~/features/village/api";
import type { ReplyDraft } from "~/features/village/components/message/MessageCard";
import { AbilityPanel } from "~/features/village/components/action/AbilityPanel";
import { ActionPanel } from "~/features/village/components/action/ActionPanel";
import { CommitPanel } from "~/features/village/components/action/CommitPanel";
import { FaceTypePanel } from "~/features/village/components/action/FaceTypePanel";
import { RpPanel } from "~/features/village/components/action/RpPanel";
import { SayPanel } from "~/features/village/components/action/SayPanel";
import { VotePanel } from "~/features/village/components/action/VotePanel";
import { AdminPanel } from "~/features/village/components/admin/AdminPanel";
import { CreatorPanel } from "~/features/village/components/admin/CreatorPanel";
import { DebugPanel } from "~/features/village/components/admin/DebugPanel";
import {
  ChangeSkillPanel,
  LeavePanel,
  SwitchParticipatePanel,
} from "~/features/village/components/participate/ParticipantOpsPanels";
import { ParticipatePanel } from "~/features/village/components/participate/ParticipatePanel";

export function ActionPanels({
  mySituation,
  situation,
  debugInfo,
  isLatestDay,
  canAction,
  currentDay,
  sayError,
  keywordList,
  reply,
  clearReply,
  onSayConfirm,
  onActionConfirm,
  onCreatorSayConfirm,
  registerSayDone,
  invalidate,
  refresh,
  participateError,
  onParticipated,
  setParticipateError,
}: {
  mySituation: ParticipantSituationView | undefined;
  situation: VillageSituationView | undefined;
  debugInfo: VillageDebugView | undefined;
  isLatestDay: boolean;
  canAction: boolean;
  currentDay: number;
  sayError: string | null;
  keywordList: string[];
  reply: ReplyDraft | null;
  clearReply: () => void;
  onSayConfirm: (request: VillageSayRequest) => void;
  onActionConfirm: (request: VillageActionRequest) => void;
  onCreatorSayConfirm: (request: VillageCreatorSayRequest) => Promise<void>;
  registerSayDone: (kind: "say" | "action" | "creatorSay", fn: () => void) => void;
  invalidate: () => Promise<unknown>;
  refresh: () => Promise<unknown>;
  participateError: string | null;
  onParticipated: (request: VillageParticipateRequest, charaImage: File | null) => Promise<void>;
  setParticipateError: (error: string | null) => void;
}) {
  return (
    <>
      {mySituation?.say.isAvailableSay && (
        <div id="say-panel">
          {sayError != null && <p className="mb-[5px] text-[#e74c3c]">{sayError}</p>}
          <SayPanel
            mySituation={mySituation}
            randomKeywords={keywordList}
            reply={reply}
            onClearReply={clearReply}
            onConfirm={onSayConfirm}
            registerOnDone={registerSayDone}
          />
        </div>
      )}

      {mySituation != null && canAction && (
        <ActionPanel
          mySituation={mySituation}
          onConfirm={onActionConfirm}
          registerOnDone={registerSayDone}
        />
      )}

      {isLatestDay && mySituation != null && mySituation.vote.canVote && (
        <VotePanel mySituation={mySituation} onDone={invalidate} />
      )}

      {isLatestDay && mySituation != null && mySituation.myself?.skill != null && (
        <AbilityPanel
          mySituation={mySituation}
          roomAssignedRows={situation?.roomAssignedRowList}
          onDone={invalidate}
        />
      )}

      {mySituation != null &&
        !mySituation.participate.isParticipating &&
        (mySituation.participate.isAvailableParticipate ||
          mySituation.participate.isAvailableSpectate) && (
          <div>
            {participateError != null && (
              <p className="mb-[5px] text-[#e74c3c]">{participateError}</p>
            )}
            <ParticipatePanel
              mySituation={mySituation}
              onParticipated={onParticipated}
              onError={setParticipateError}
            />
          </div>
        )}

      {mySituation != null &&
        mySituation.participate.isParticipating &&
        mySituation.skillRequest.isAvailableSkillRequest && (
          <ChangeSkillPanel mySituation={mySituation} onDone={invalidate} />
        )}
      {mySituation?.participate.isAvailableSwitchParticipate && (
        <SwitchParticipatePanel onDone={invalidate} />
      )}
      {mySituation?.participate.isAvailableLeave && <LeavePanel onDone={invalidate} />}

      {isLatestDay && mySituation != null && mySituation.commit.isAvailableCommit && (
        <CommitPanel mySituation={mySituation} onDone={invalidate} />
      )}

      {mySituation != null &&
        (mySituation.rp.isAvailableChangeName || mySituation.rp.isAvailableMemo) && (
          <RpPanel mySituation={mySituation} onDone={invalidate} />
        )}

      {mySituation != null && mySituation.rp.canAddImage && (
        <FaceTypePanel mySituation={mySituation} onDone={invalidate} />
      )}

      {mySituation != null && mySituation.creator.isCreator && (
        <CreatorPanel
          mySituation={mySituation}
          situation={situation}
          onConfirm={onCreatorSayConfirm}
          onDone={invalidate}
          registerOnDone={registerSayDone}
        />
      )}

      {mySituation != null && mySituation.admin.isAdmin && <AdminPanel onDone={invalidate} />}

      {debugInfo?.isDebugMode && (
        <DebugPanel currentDay={currentDay} debugInfo={debugInfo} onDone={refresh} />
      )}
    </>
  );
}
