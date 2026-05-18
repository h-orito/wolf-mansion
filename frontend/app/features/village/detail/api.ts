/**
 * 村詳細 (read-only) の型と fetch 関数。
 * 型は SpringDoc から `pnpm gen:api` で取得した `~/lib/api/generated.ts` を参照する。
 */

import type { components } from "~/lib/api/generated";
import { browserFetch, type ApiFetch } from "~/lib/api/client";

export type CharaView = components["schemas"]["CharaView"];
export type SkillView = components["schemas"]["SkillView"];
export type VillageView = components["schemas"]["VillageView"];
export type VillageSettingsView = components["schemas"]["VillageSettingsView"];
export type VillageDayView = components["schemas"]["VillageDayView"];
export type VillageDaysView = components["schemas"]["VillageDaysView"];
export type VillageTimeView = components["schemas"]["VillageTimeView"];
export type VillageParticipantView = components["schemas"]["VillageParticipantView"];
export type VillageParticipantsView = components["schemas"]["VillageParticipantsView"];
export type VillageFootstepView = components["schemas"]["VillageFootstepView"];
export type VillageFootstepsView = components["schemas"]["VillageFootstepsView"];
export type MessageView = components["schemas"]["MessageView"];
export type MessagesView = components["schemas"]["MessagesView"];
export type MyselfView = components["schemas"]["MyselfView"];

// ---------- fetchers ----------

export async function fetchVillage(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<VillageView> {
  const res = await fetcher(`/api/v1/villages/${villageId}`);
  if (!res.ok) throw new Error(`village fetch failed: ${res.status}`);
  return res.json();
}

export async function fetchVillageMessages(
  villageId: number,
  day: number | undefined,
  fetcher: ApiFetch = browserFetch,
): Promise<MessagesView> {
  const qs = typeof day === "number" ? `?day=${day}` : "";
  const res = await fetcher(`/api/v1/villages/${villageId}/messages${qs}`);
  if (!res.ok) throw new Error(`messages fetch failed: ${res.status}`);
  return res.json();
}

export async function fetchVillageFootsteps(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<VillageFootstepsView> {
  const res = await fetcher(`/api/v1/villages/${villageId}/footsteps`);
  if (!res.ok) throw new Error(`footsteps fetch failed: ${res.status}`);
  return res.json();
}

// ---------- mutations ----------

export type SayInput = {
  message: string;
  /** CDef.MessageType の code。省略すると "NORMAL_SAY" (通常発言) 扱い。 */
  messageType?: string;
  secretSayTargetCharaId?: number;
  faceType?: string;
  convertDisable?: boolean;
};

/**
 * POST /api/v1/villages/{id}/messages
 *
 * 成功時は 201 + 空 body。失敗時は 400 + `{ message: string }` のエラー。
 */
export async function postSay(
  villageId: number,
  input: SayInput,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const body = {
    messageType: "NORMAL_SAY",
    ...input,
  };
  const res = await fetcher(`/api/v1/villages/${villageId}/messages`, {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    // backend は WolfMansionBusinessException 等を ErrorResponse(message) で返す
    const errBody = await res.text();
    let detail = errBody;
    try {
      const parsed = JSON.parse(errBody) as { message?: string };
      if (parsed.message) detail = parsed.message;
    } catch {
      // テキストのまま
    }
    throw new Error(`say failed: ${res.status} ${detail}`);
  }
}

export async function fetchMyself(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<MyselfView | null> {
  const res = await fetcher(`/api/v1/villages/${villageId}/myself`);
  if (!res.ok) throw new Error(`myself fetch failed: ${res.status}`);
  // backend は未参加時に 200 + body=null を返す
  const text = await res.text();
  if (!text || text === "null") return null;
  return JSON.parse(text) as MyselfView;
}
