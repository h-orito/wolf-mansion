import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useCallback, useEffect, useRef, useState } from "react";

import { useRegisterRefresh } from "~/features/village/useRefresh";
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
const ATTACK_TARGETS_QUERY_KEY = "attack-targets";
const ABILITY_FOOTSTEPS_QUERY_KEY = "ability-footsteps";

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

  const queryClient = useQueryClient();

  const attackerIdNum = attackerCharaId !== "" ? Number(attackerCharaId) : null;
  const targetIdNum = targetCharaId !== "" ? Number(targetCharaId) : null;

  const attackTargetsQuery = useQuery({
    queryKey: [ATTACK_TARGETS_QUERY_KEY, villageId, attackerCharaId],
    queryFn: () => fetchAttackTargets(villageId, attackerIdNum!),
    enabled: !!ability?.canUseAbility && isAttack && attackerIdNum != null,
    retry: false,
  });

  const needsFootstep = isAttack || !!ability?.isTargetingAndFootstep;
  const footstepsQuery = useQuery({
    queryKey: [ABILITY_FOOTSTEPS_QUERY_KEY, villageId, attackerCharaId, targetCharaId],
    queryFn: () => fetchAbilityFootsteps(villageId, isAttack ? attackerIdNum : null, targetIdNum),
    enabled: !!ability?.canUseAbility && needsFootstep && targetIdNum != null,
    retry: false,
  });

  const targets =
    attackTargetsQuery.data?.targets ??
    (ability?.targetCharaIds ?? []).map((id) => ({
      charaId: id,
      name: resolveParticipantName(village, id),
    }));

  const footstepOptions = footstepsQuery.data?.footsteps ?? ability?.targetFootstepList ?? [];

  useEffect(() => {
    if (targetCharaId !== "") return;
    const t = attackTargetsQuery.data?.targets;
    if (t && t.length > 0) {
      setTargetCharaId(String(t[0].charaId));
    }
  }, [attackTargetsQuery.data, targetCharaId]);

  useEffect(() => {
    if (footstep !== "") return;
    const opts = footstepsQuery.data?.footsteps;
    if (opts && opts.length > 0) {
      setFootstep(opts[0]);
    }
  }, [footstepsQuery.data, footstep]);

  const dataRef = useRef({ villageId, village, ability, skill });
  dataRef.current = { villageId, village, ability, skill };

  const initialize = useCallback(() => {
    const { villageId: vid, ability: a } = dataRef.current;
    if (a == null) return;

    const atkId = a.attackerCharaId != null ? String(a.attackerCharaId) : "";
    const tgtId = a.targetCharaId != null ? String(a.targetCharaId) : "";
    setAttackerCharaId(atkId);
    setTargetCharaId(tgtId);
    setFootstep(a.targetFootstep ?? a.footstep ?? a.targetFootstepList?.[0] ?? "");
    const current = a.footstep;
    setDisturbRooms(current == null || current === NO_FOOTSTEP ? [] : current.split(","));

    void queryClient.invalidateQueries({ queryKey: [ATTACK_TARGETS_QUERY_KEY, vid] });
    void queryClient.invalidateQueries({ queryKey: [ABILITY_FOOTSTEPS_QUERY_KEY, vid] });
  }, [queryClient]);

  useRegisterRefresh(initialize);

  const onAttackerChange = (value: string) => {
    setAttackerCharaId(value);
    setTargetCharaId("");
    setFootstep("");
  };

  const onTargetChange = (value: string) => {
    setTargetCharaId(value);
    setFootstep("");
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
  };
}
