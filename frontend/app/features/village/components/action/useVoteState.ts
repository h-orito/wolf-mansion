import { useCallback, useRef, useState } from "react";

import type { ParticipantSituationView } from "~/features/village/api";
import { useRegisterRefresh } from "~/features/village/useRefresh";

type Vote = ParticipantSituationView["vote"];

export function useVoteState(vote: Vote) {
  const [targetCharaId, setTargetCharaId] = useState<string>(
    vote.targetCharaId != null ? String(vote.targetCharaId) : "",
  );

  const dataRef = useRef(vote);
  dataRef.current = vote;

  const initialize = useCallback(() => {
    const v = dataRef.current;
    setTargetCharaId(v.targetCharaId != null ? String(v.targetCharaId) : "");
  }, []);

  useRegisterRefresh(initialize);

  return { targetCharaId, setTargetCharaId };
}
