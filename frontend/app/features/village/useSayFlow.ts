import { useCallback, useEffect, useRef, useState } from "react";

import {
  actionVillage,
  confirmVillageAction,
  confirmVillageCreatorSay,
  confirmVillageSay,
  sayVillage,
  sayVillageCreator,
  type VillageActionRequest,
  type VillageCreatorSayRequest,
  type VillageSayRequest,
} from "~/features/village/api";
import type { ReplyDraft } from "~/features/village/components/message/MessageCard";
import type { SayPreview } from "~/features/village/components/action/SayPreviewArea";
import { useVillageScroll } from "~/features/village/useVillageScroll";
import { ApiError } from "~/lib/api";

function isSayPanelFixed(): boolean {
  try {
    return localStorage.getItem("village_panel_bottom_fix") === "sayform";
  } catch {
    return false;
  }
}

function scrollToSayPanel() {
  if (!isSayPanelFixed()) {
    document.getElementById("say-panel")?.scrollIntoView({ behavior: "smooth", block: "end" });
  }
}

export function useSayFlow(villageId: number, invalidate: () => Promise<unknown>) {
  const { scrollToMessageBottom } = useVillageScroll();
  const [reply, setReply] = useState<ReplyDraft | null>(null);
  const [sayPreview, setSayPreview] = useState<SayPreview | null>(null);
  const [sayError, setSayError] = useState<string | null>(null);
  const [saySubmitting, setSaySubmitting] = useState(false);
  type SayKind = "say" | "action" | "creatorSay";
  const onSayDoneCallbacks = useRef<Map<SayKind, () => void>>(new Map());

  // state 更新直後はプレビューが DOM に反映される前でスクロール位置の計算がずれるため、
  // 反映後の effect からスクロールする
  useEffect(() => {
    if (sayPreview == null) return;
    requestAnimationFrame(() => scrollToMessageBottom());
  }, [sayPreview, scrollToMessageBottom]);

  const onReply = useCallback((draft: ReplyDraft) => {
    setReply(draft);
    scrollToSayPanel();
  }, []);

  const clearReply = useCallback(() => setReply(null), []);

  const registerSayDone = useCallback((kind: SayKind, fn: () => void) => {
    onSayDoneCallbacks.current.set(kind, fn);
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
      const kind = sayPreview.kind;
      setSayPreview(null);
      setReply(null);
      onSayDoneCallbacks.current.get(kind)?.();
      await invalidate();
      requestAnimationFrame(() => scrollToMessageBottom());
    } catch (e) {
      setSayError(e instanceof ApiError ? e.detail : "発言に失敗しました");
    } finally {
      setSaySubmitting(false);
    }
  };

  const onSayCancel = () => {
    setSayPreview(null);
    scrollToSayPanel();
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
