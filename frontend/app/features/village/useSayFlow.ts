import { useCallback, useRef, useState } from "react";

import {
  actionVillage,
  confirmVillageAction,
  confirmVillageCreatorSay,
  confirmVillageSay,
  sayVillage,
  sayVillageCreator,
  type VillageActionRequest,
  type VillageCreatorSayRequest,
  type VillageMessageContent,
  type VillageSayRequest,
} from "~/features/village/api";
import type { ReplyDraft } from "~/features/village/components/message/MessageCard";
import { ApiError } from "~/lib/api";

type SayPreview =
  | { kind: "say"; message: VillageMessageContent; request: VillageSayRequest }
  | { kind: "action"; message: VillageMessageContent; request: VillageActionRequest }
  | { kind: "creatorSay"; message: VillageMessageContent; request: VillageCreatorSayRequest }
  | null;

export function useSayFlow(
  villageId: number,
  invalidate: () => Promise<unknown>,
  scrollToBottom: () => void,
) {
  const [reply, setReply] = useState<ReplyDraft | null>(null);
  const [sayPreview, setSayPreview] = useState<SayPreview>(null);
  const [sayError, setSayError] = useState<string | null>(null);
  const [saySubmitting, setSaySubmitting] = useState(false);
  const onSayDoneRef = useRef<(() => void) | null>(null);

  const onReply = useCallback((draft: ReplyDraft) => {
    setReply(draft);
    document.getElementById("say-panel")?.scrollIntoView({ behavior: "smooth", block: "end" });
  }, []);

  const clearReply = useCallback(() => setReply(null), []);

  const registerSayDone = useCallback((fn: () => void) => {
    onSayDoneRef.current = fn;
  }, []);

  const onSayConfirm = async (request: VillageSayRequest) => {
    setSayError(null);
    try {
      const response = await confirmVillageSay(villageId, request);
      if (response.message == null) {
        setSayError("発言の確認に失敗しました");
        return;
      }
      setSayPreview({ kind: "say", message: response.message, request });
      requestAnimationFrame(() => scrollToBottom());
    } catch (e) {
      setSayError(e instanceof ApiError ? e.detail : "発言の確認に失敗しました");
    }
  };

  const onActionConfirm = async (request: VillageActionRequest) => {
    setSayError(null);
    try {
      const response = await confirmVillageAction(villageId, request);
      if (response.message == null) {
        setSayError("アクションの確認に失敗しました");
        return;
      }
      setSayPreview({ kind: "action", message: response.message, request });
      requestAnimationFrame(() => scrollToBottom());
    } catch (e) {
      setSayError(e instanceof ApiError ? e.detail : "アクションの確認に失敗しました");
    }
  };

  const onCreatorSayConfirm = async (request: VillageCreatorSayRequest) => {
    setSayError(null);
    try {
      const response = await confirmVillageCreatorSay(villageId, request);
      if (response.message == null) {
        setSayError("村建て発言の確認に失敗しました");
        return;
      }
      setSayPreview({ kind: "creatorSay", message: response.message, request });
      requestAnimationFrame(() => scrollToBottom());
    } catch (e) {
      setSayError(e instanceof ApiError ? e.detail : "村建て発言の確認に失敗しました");
    }
  };

  const onSayDetermine = async () => {
    if (sayPreview == null || saySubmitting) return;
    setSaySubmitting(true);
    setSayError(null);
    try {
      if (sayPreview.kind === "say") {
        await sayVillage(villageId, sayPreview.request);
      } else if (sayPreview.kind === "action") {
        await actionVillage(villageId, sayPreview.request);
      } else {
        await sayVillageCreator(villageId, sayPreview.request);
      }
      setSayPreview(null);
      setReply(null);
      onSayDoneRef.current?.();
      await invalidate();
      requestAnimationFrame(() => scrollToBottom());
    } catch (e) {
      setSayError(e instanceof ApiError ? e.detail : "発言に失敗しました");
    } finally {
      setSaySubmitting(false);
    }
  };

  const onSayCancel = () => {
    setSayPreview(null);
    document.getElementById("say-panel")?.scrollIntoView({ behavior: "smooth", block: "end" });
  };

  return {
    reply,
    onReply,
    clearReply,
    sayPreview,
    sayError,
    saySubmitting,
    registerSayDone,
    onSayConfirm,
    onActionConfirm,
    onCreatorSayConfirm,
    onSayDetermine,
    onSayCancel,
  };
}
