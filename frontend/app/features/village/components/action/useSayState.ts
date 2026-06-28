import { useCallback, useRef, useState } from "react";

import type { ParticipantSituationView } from "~/features/village/api";
import { MessageType } from "~/features/village/components/message/messageType";
import { useRegisterRefresh } from "~/features/village/useRefresh";

type Say = ParticipantSituationView["say"];

const TYPE_TO_FACE: Record<string, string> = {
  [MessageType.NORMAL_SAY]: "NORMAL",
  [MessageType.WEREWOLF_SAY]: "WEREWOLF",
  [MessageType.MASON_SAY]: "MASON",
  [MessageType.LOVERS_SAY]: "LOVER",
  [MessageType.TELEPATHY]: "SECRET",
  [MessageType.MONOLOGUE_SAY]: "MONOLOGUE",
  [MessageType.SECRET_SAY]: "SECRET",
  [MessageType.GRAVE_SAY]: "GRAVE",
  [MessageType.SPECTATE_SAY]: "NORMAL",
};

function faceTypeFor(type: string, codes: string[]): string | null {
  const candidate = TYPE_TO_FACE[type];
  return candidate != null && codes.includes(candidate) ? candidate : null;
}

function defaultFaceType(say: Say, type: string): string {
  const images = say.selectableCharaImageList ?? [];
  const displayCodes = images.filter((i) => i.isDisplay).map((i) => i.faceType.code);
  return faceTypeFor(type, displayCodes) ?? displayCodes[0] ?? "NORMAL";
}

export function useSayState(say: Say | undefined) {
  const selectable = say?.selectableMessageTypeList ?? [];
  const defaultType =
    say?.defaultMessageType?.code ?? selectable[0]?.messageType.code ?? MessageType.NORMAL_SAY;

  const [messageType, setMessageType] = useState(defaultType);
  const [message, setMessage] = useState("");
  const [faceType, setFaceType] = useState<string>(() =>
    say != null ? defaultFaceType(say, defaultType) : "NORMAL",
  );
  const [convertDisable, setConvertDisable] = useState(false);
  const [secretTargetCharaId, setSecretTargetCharaId] = useState<string>("");

  const dataRef = useRef(say);
  dataRef.current = say;

  const initialize = useCallback(() => {
    const s = dataRef.current;
    if (s == null) return;
    const sel = s.selectableMessageTypeList ?? [];
    const dt = s.defaultMessageType?.code ?? sel[0]?.messageType.code ?? MessageType.NORMAL_SAY;
    setMessageType(dt);
    setFaceType(defaultFaceType(s, dt));
    setConvertDisable(false);
    setSecretTargetCharaId("");
  }, []);

  useRegisterRefresh(initialize);

  const changeType = useCallback(
    (type: string) => {
      setMessageType(type);
      if (say == null) return;
      const candidate = TYPE_TO_FACE[type];
      const images = say.selectableCharaImageList ?? [];
      const displayCodes = images.filter((i) => i.isDisplay).map((i) => i.faceType.code);
      if (candidate != null && displayCodes.includes(candidate)) {
        setFaceType(candidate);
      }
    },
    [say],
  );

  return {
    messageType,
    message,
    setMessage,
    faceType,
    setFaceType,
    convertDisable,
    setConvertDisable,
    secretTargetCharaId,
    setSecretTargetCharaId,
    changeType,
  };
}
