import { useCallback, useEffect, useRef, useState } from "react";

import {
  fetchAbilityFootsteps,
  fetchAttackTargets,
  type ParticipantSituationView,
  type VillageDetailView,
} from "~/features/village/api";
import { resolveParticipantName } from "~/features/village/participants";

type Ability = ParticipantSituationView["ability"];
type Skill = NonNullable<ParticipantSituationView["myself"]>["skill"];

const NO_FOOTSTEP = "なし";

export function useAbilityState(
  villageId: number,
  village: VillageDetailView,
  ability: Ability | undefined,
  skill: Skill | undefined | null,
) {
  const isAttack = (ability?.attackerCharaIds.length ?? 0) > 0;
  const isInvestigate = skill?.hasInvestigateAbility ?? false;
  const isDisturb =
    (skill?.hasDisturbAbility ?? false) && (ability?.targetCharaIds.length ?? 0) === 0;

  const [attackerCharaId, setAttackerCharaId] = useState<string>(
    ability?.attackerCharaId != null ? String(ability.attackerCharaId) : "",
  );
  const [targetCharaId, setTargetCharaId] = useState<string>(
    ability?.targetCharaId != null ? String(ability.targetCharaId) : "",
  );
  const [footstep, setFootstep] = useState<string>(
    ability?.targetFootstep ?? ability?.footstep ?? ability?.targetFootstepList?.[0] ?? "",
  );
  const [disturbRooms, setDisturbRooms] = useState<string[]>(() => {
    const current = ability?.footstep;
    return current == null || current === NO_FOOTSTEP ? [] : current.split(",");
  });
  const [targets, setTargets] = useState<{ charaId: number; name: string }[]>(
    (ability?.targetCharaIds ?? []).map((id) => ({
      charaId: id,
      name: resolveParticipantName(village, id),
    })),
  );
  const [footstepOptions, setFootstepOptions] = useState<string[]>(
    ability?.targetFootstepList ?? [],
  );

  const dataRef = useRef({ villageId, village, ability, skill });
  dataRef.current = { villageId, village, ability, skill };

  useEffect(() => {
    if (ability == null || !ability.canUseAbility) return;
    if (isAttack && attackerCharaId !== "") {
      fetchAttackTargets(villageId, Number(attackerCharaId))
        .then((response) => setTargets(response.targets ?? []))
        .catch(() => {});
    }
    if ((isAttack || ability.isTargetingAndFootstep) && targetCharaId !== "") {
      fetchAbilityFootsteps(
        villageId,
        isAttack && attackerCharaId !== "" ? Number(attackerCharaId) : null,
        Number(targetCharaId),
      )
        .then((response) => {
          const opts = response.footsteps ?? [];
          setFootstepOptions(opts);
          if (!footstep && opts.length > 0) setFootstep(opts[0]);
        })
        .catch(() => {});
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const initialize = useCallback(() => {
    const { villageId: vid, village: v, ability: a, skill: s } = dataRef.current;
    if (a == null) return;

    const atkId = a.attackerCharaId != null ? String(a.attackerCharaId) : "";
    const tgtId = a.targetCharaId != null ? String(a.targetCharaId) : "";
    setAttackerCharaId(atkId);
    setTargetCharaId(tgtId);
    setFootstep(a.targetFootstep ?? a.footstep ?? a.targetFootstepList?.[0] ?? "");
    const current = a.footstep;
    setDisturbRooms(current == null || current === NO_FOOTSTEP ? [] : current.split(","));
    setTargets(
      a.targetCharaIds.map((id) => ({
        charaId: id,
        name: resolveParticipantName(v, id),
      })),
    );
    setFootstepOptions(a.targetFootstepList ?? []);

    if (!a.canUseAbility) return;

    const isAtk = a.attackerCharaIds.length > 0;
    const isDisturb2 = (s?.hasDisturbAbility ?? false) && a.targetCharaIds.length === 0;
    if (isAtk && atkId !== "") {
      fetchAttackTargets(vid, Number(atkId))
        .then((response) => setTargets(response.targets ?? []))
        .catch(() => {});
    }
    if ((isAtk || a.isTargetingAndFootstep) && tgtId !== "") {
      fetchAbilityFootsteps(vid, isAtk && atkId !== "" ? Number(atkId) : null, Number(tgtId))
        .then((response) => {
          const opts = response.footsteps ?? [];
          setFootstepOptions(opts);
          if (opts.length > 0 && !a.targetFootstep && !a.footstep) setFootstep(opts[0]);
        })
        .catch(() => {});
    }
    void isDisturb2;
  }, []);

  const onAttackerChange = async (value: string) => {
    setAttackerCharaId(value);
    setTargetCharaId("");
    setFootstep("");
    setFootstepOptions([]);
    if (value === "") return;
    try {
      const response = await fetchAttackTargets(villageId, Number(value));
      const newTargets = response.targets ?? [];
      setTargets(newTargets);
      if (newTargets.length > 0) {
        const firstTargetId = newTargets[0].charaId;
        setTargetCharaId(String(firstTargetId));
        const fsResponse = await fetchAbilityFootsteps(villageId, Number(value), firstTargetId);
        const opts = fsResponse.footsteps ?? [];
        setFootstepOptions(opts);
        setFootstep(opts.length > 0 ? opts[0] : "");
      }
    } catch {
      setTargets([]);
    }
  };

  const onTargetChange = async (value: string) => {
    setTargetCharaId(value);
    setFootstep("");
    if (!(isAttack || (ability?.isTargetingAndFootstep ?? false)) || value === "") return;
    try {
      const response = await fetchAbilityFootsteps(
        villageId,
        isAttack && attackerCharaId !== "" ? Number(attackerCharaId) : null,
        Number(value),
      );
      const opts = response.footsteps ?? [];
      setFootstepOptions(opts);
      setFootstep(opts.length > 0 ? opts[0] : "");
    } catch {
      setFootstepOptions([]);
    }
  };

  return {
    isAttack,
    isInvestigate,
    isDisturb,
    attackerCharaId,
    targetCharaId,
    footstep,
    setFootstep,
    disturbRooms,
    setDisturbRooms,
    targets,
    footstepOptions,
    onAttackerChange,
    onTargetChange,
    initialize,
  };
}
