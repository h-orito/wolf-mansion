import type {
  VillageActionRequest,
  VillageCreatorSayRequest,
  VillageSayRequest,
} from "~/features/village/api";
import type { ReplyDraft } from "~/features/village/components/message/MessageCard";
import { MessageType } from "~/features/village/components/message/messageType";
import { useMe } from "~/features/auth/useMe";
import { useVillageContext } from "~/features/village/VillageContext";
import {
  useMyVillageSituation,
  useVillageDebugInfo,
  useVillageSituation,
} from "~/features/village/useVillage";
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
  dayParam,
  sayError,
  reply,
  clearReply,
  onSayConfirm,
  onActionConfirm,
  onCreatorSayConfirm,
  registerSayDone,
  refresh,
}: {
  dayParam: number | undefined;
  sayError: string | null;
  reply: ReplyDraft | null;
  clearReply: () => void;
  onSayConfirm: (request: VillageSayRequest) => void;
  onActionConfirm: (request: VillageActionRequest) => void;
  onCreatorSayConfirm: (request: VillageCreatorSayRequest) => Promise<void>;
  registerSayDone: (kind: "say" | "action" | "creatorSay", fn: () => void) => void;
  refresh: () => Promise<unknown>;
}) {
  const village = useVillageContext();
  const { me } = useMe();
  const { data: mySituation } = useMyVillageSituation(village.id, dayParam);
  const { data: situation } = useVillageSituation(village.id, dayParam, me?.name ?? null);
  const { data: debugInfo } = useVillageDebugInfo(village.id);

  const latestDay = village.days.list?.at(-1)?.day ?? 0;
  const currentDay = dayParam ?? latestDay;
  const isLatestDay = currentDay === latestDay;
  const canAction =
    mySituation?.say.selectableMessageTypeList?.some(
      (t) => t.messageType.code === MessageType.ACTION,
    ) ?? false;

  return (
    <>
      {mySituation?.say.isAvailableSay && (
        <div id="say-panel">
          {sayError != null && <p className="mb-[5px] text-[#e74c3c]">{sayError}</p>}
          <SayPanel
            mySituation={mySituation}
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
        <VotePanel mySituation={mySituation} />
      )}

      {isLatestDay && mySituation != null && mySituation.myself?.skill != null && (
        <AbilityPanel mySituation={mySituation} roomAssignedRows={situation?.roomAssignedRowList} />
      )}

      {mySituation != null &&
        !mySituation.participate.isParticipating &&
        (mySituation.participate.isAvailableParticipate ||
          mySituation.participate.isAvailableSpectate) && (
          <ParticipatePanel mySituation={mySituation} />
        )}

      {mySituation != null &&
        mySituation.participate.isParticipating &&
        mySituation.skillRequest.isAvailableSkillRequest && (
          <ChangeSkillPanel mySituation={mySituation} />
        )}
      {mySituation?.participate.isAvailableSwitchParticipate && <SwitchParticipatePanel />}
      {mySituation?.participate.isAvailableLeave && <LeavePanel />}

      {isLatestDay && mySituation != null && mySituation.commit.isAvailableCommit && (
        <CommitPanel mySituation={mySituation} />
      )}

      {mySituation != null &&
        (mySituation.rp.isAvailableChangeName || mySituation.rp.isAvailableMemo) && (
          <RpPanel mySituation={mySituation} />
        )}

      {mySituation != null && mySituation.rp.canAddImage && (
        <FaceTypePanel mySituation={mySituation} />
      )}

      {mySituation != null && mySituation.creator.isCreator && (
        <CreatorPanel
          mySituation={mySituation}
          onConfirm={onCreatorSayConfirm}
          registerOnDone={registerSayDone}
        />
      )}

      {mySituation != null && mySituation.admin.isAdmin && <AdminPanel />}

      {debugInfo?.isDebugMode && <DebugPanel debugInfo={debugInfo} onDone={refresh} />}
    </>
  );
}
