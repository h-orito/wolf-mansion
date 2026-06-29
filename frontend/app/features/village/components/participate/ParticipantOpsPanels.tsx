import { useState } from "react";

import { ErrorMessage } from "~/components/ui/Alert";
import { Button } from "~/components/ui/Button";
import { VillageFormRow } from "~/components/ui/Form";
import { selectClass } from "~/components/ui/Input";
import { Panel } from "~/components/ui/Panel";
import { useToast } from "~/components/ui/Toast";
import {
  changeVillageRequestSkill,
  leaveVillage,
  switchVillageParticipate,
  type ParticipantSituationView,
} from "~/features/village/api";
import { useVillageContext } from "~/features/village/VillageContext";
import { useAsyncAction } from "~/lib/useAsyncAction";

/** 参加 ⇄ 見学の切替。 */
export function SwitchParticipatePanel({ onDone }: { onDone: () => Promise<unknown> }) {
  const village = useVillageContext();
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();
  const submit = () =>
    execute(async () => {
      await switchVillageParticipate(village.id);
      showToast("参加見学を切り替えました");
      await onDone();
    }, "切り替えに失敗しました");
  return (
    <Panel title="参加見学切り替え" storageKey="switchparticipateform">
      <div className="space-y-[10px]">
        <ErrorMessage error={error} />
        <div className="flex justify-end">
          <Button onClick={submit} disabled={submitting}>
            参加見学を切り替える
          </Button>
        </div>
      </div>
    </Panel>
  );
}

/** 希望役職 (第 1/第 2) の変更。 */
export function ChangeSkillPanel({
  mySituation,
  onDone,
}: {
  mySituation: ParticipantSituationView;
  onDone: () => Promise<unknown>;
}) {
  const village = useVillageContext();
  const skillRequest = mySituation.skillRequest;
  const skills = skillRequest.selectableSkillList ?? [];
  const [first, setFirst] = useState(skillRequest.skillRequest?.first?.code ?? "LEFTOVER");
  const [second, setSecond] = useState(skillRequest.skillRequest?.second?.code ?? "LEFTOVER");
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();

  const currentFirst =
    skills.find((s) => s.code === skillRequest.skillRequest?.first?.code)?.name ?? "";
  const currentSecond =
    skills.find((s) => s.code === skillRequest.skillRequest?.second?.code)?.name ?? "";

  const submit = () =>
    execute(async () => {
      await changeVillageRequestSkill(village.id, {
        requestedSkill: first,
        secondRequestedSkill: second,
      });
      showToast("役職希望を変更しました");
      await onDone();
    }, "役職希望の変更に失敗しました");

  return (
    <Panel title="役職希望" storageKey="changeskillform">
      <div className="space-y-[10px]">
        <ErrorMessage error={error} />
        <p>
          現在の役職希望: {currentFirst}/{currentSecond}
        </p>
        <VillageFormRow label="第一役職希望">
          <select
            className={selectClass}
            value={first}
            onChange={(e) => setFirst(e.target.value)}
            aria-label="第一役職希望"
          >
            {skills.map((skill) => (
              <option key={skill.code} value={skill.code}>
                {skill.name}
              </option>
            ))}
          </select>
        </VillageFormRow>
        <VillageFormRow label="第二役職希望">
          <select
            className={selectClass}
            value={second}
            onChange={(e) => setSecond(e.target.value)}
            aria-label="第二役職希望"
          >
            {skills.map((skill) => (
              <option key={skill.code} value={skill.code}>
                {skill.name}
              </option>
            ))}
          </select>
        </VillageFormRow>
        <div className="flex justify-end">
          <Button onClick={submit} disabled={submitting}>
            役職希望を変更する
          </Button>
        </div>
      </div>
    </Panel>
  );
}

/** 退村。 */
export function LeavePanel({ onDone }: { onDone: () => Promise<unknown> }) {
  const village = useVillageContext();
  const showToast = useToast((s) => s.show);
  const { error, submitting, execute } = useAsyncAction();
  const submit = () => {
    if (!window.confirm("本当に退村してよろしいですか？")) return;
    void execute(async () => {
      await leaveVillage(village.id);
      showToast("退村しました");
      await onDone();
    }, "退村に失敗しました");
  };
  return (
    <Panel title="退村" storageKey="leaveform">
      <div className="space-y-[10px]">
        <ErrorMessage error={error} />
        <div className="flex justify-end">
          <Button variant="danger" onClick={submit} disabled={submitting}>
            村を出る
          </Button>
        </div>
      </div>
    </Panel>
  );
}
