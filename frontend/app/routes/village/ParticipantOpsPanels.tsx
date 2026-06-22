import { useState } from "react";

import { Button } from "~/components/ui/Button";
import { Panel } from "~/components/ui/Panel";
import { selectClass } from "~/components/ui/Input";
import {
  changeVillageRequestSkill,
  leaveVillage,
  switchVillageParticipate,
  type ParticipantSituationView,
} from "~/features/village/api";
import { ApiError } from "~/lib/api";

function errorMessage(e: unknown, fallback: string): string {
  return e instanceof ApiError ? e.detail : fallback;
}

/** 参加 ⇄ 見学の切替。 */
export function SwitchParticipatePanel({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);
  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await switchVillageParticipate(villageId);
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "切り替えに失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };
  return (
    <Panel title="参加見学切り替え">
      <div className="space-y-[10px]">
        {error != null && <p className="text-[#e74c3c]">{error}</p>}
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
  villageId,
  mySituation,
  onDone,
}: {
  villageId: number;
  mySituation: ParticipantSituationView;
  onDone: () => Promise<unknown>;
}) {
  const skillRequest = mySituation.skillRequest;
  const skills = skillRequest.selectableSkillList ?? [];
  const [first, setFirst] = useState(skillRequest.requestedSkillCode ?? "LEFTOVER");
  const [second, setSecond] = useState(skillRequest.secondRequestedSkillCode ?? "LEFTOVER");
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const currentFirst = skills.find((s) => s.code === skillRequest.requestedSkillCode)?.name ?? "";
  const currentSecond =
    skills.find((s) => s.code === skillRequest.secondRequestedSkillCode)?.name ?? "";

  const submit = async () => {
    if (submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      await changeVillageRequestSkill(villageId, {
        requestedSkill: first,
        secondRequestedSkill: second,
      });
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "役職希望の変更に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Panel title="役職希望">
      <div className="space-y-[10px]">
        {error != null && <p className="text-[#e74c3c]">{error}</p>}
        <p>
          現在の役職希望: {currentFirst}/{currentSecond}
        </p>
        <div className="sm:flex sm:items-center sm:gap-[10px]">
          <label className="sm:w-[120px]">第一役職希望</label>
          <select
            className={`${selectClass} mt-[5px] sm:mt-0`}
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
        </div>
        <div className="sm:flex sm:items-center sm:gap-[10px]">
          <label className="sm:w-[120px]">第二役職希望</label>
          <select
            className={`${selectClass} mt-[5px] sm:mt-0`}
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
        </div>
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
export function LeavePanel({
  villageId,
  onDone,
}: {
  villageId: number;
  onDone: () => Promise<unknown>;
}) {
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);
  const submit = async () => {
    if (submitting) return;
    if (!window.confirm("本当に退村してよろしいですか？")) return;
    setSubmitting(true);
    setError(null);
    try {
      await leaveVillage(villageId);
      await onDone();
    } catch (e) {
      setError(errorMessage(e, "退村に失敗しました"));
    } finally {
      setSubmitting(false);
    }
  };
  return (
    <Panel title="退村">
      <div className="space-y-[10px]">
        {error != null && <p className="text-[#e74c3c]">{error}</p>}
        <div className="flex justify-end">
          <Button variant="danger" onClick={submit} disabled={submitting}>
            村を出る
          </Button>
        </div>
      </div>
    </Panel>
  );
}
