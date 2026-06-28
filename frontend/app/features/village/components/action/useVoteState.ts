import { useCallback, useRef, useState } from "react";

import type { ParticipantSituationView } from "~/features/village/api";

type Vote = ParticipantSituationView["vote"];

export function useVoteState(vote: Vote | undefined) {
  const [targetCharaId, setTargetCharaId] = useState<string>(
    vote?.targetCharaId != null ? String(vote.targetCharaId) : "",
  );

  const dataRef = useRef(vote);
  dataRef.current = vote;

  const initialize = useCallback(() => {
    const v = dataRef.current;
    setTargetCharaId(v?.targetCharaId != null ? String(v.targetCharaId) : "");
  }, []);

  return { targetCharaId, setTargetCharaId, initialize };
}
