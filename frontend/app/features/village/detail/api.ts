/**
 * 村詳細 (read-only) の型と fetch 関数。
 * 型は SpringDoc から `pnpm gen:api` で取得した `~/lib/api/generated.ts` を参照する。
 */

import type { components } from "~/lib/api/generated";
import { browserFetch, type ApiFetch } from "~/lib/api/client";

export type CharaView = components["schemas"]["CharaView"];
export type SkillView = components["schemas"]["SkillView"];
export type VillageParticipateBody = components["schemas"]["VillageParticipateBody"];
export type VillageChangeRequestSkillBody = components["schemas"]["VillageChangeRequestSkillBody"];
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
export type MyselfAbilityView = components["schemas"]["MyselfAbilityView"];
export type MyselfVoteView = components["schemas"]["MyselfVoteView"];
export type MyselfCommitView = components["schemas"]["MyselfCommitView"];
export type VillageAbilityBody = components["schemas"]["VillageAbilityBody"];
export type VillageVoteBody = components["schemas"]["VillageVoteBody"];
export type VillageCommitBody = components["schemas"]["VillageCommitBody"];
export type VillageChangeNameBody = components["schemas"]["VillageChangeNameBody"];
export type VillageMemoBody = components["schemas"]["VillageMemoBody"];
export type VillageFaceTypeModifyBody = components["schemas"]["VillageFaceTypeModifyBody"];
export type MyselfRpView = components["schemas"]["MyselfRpView"];
export type MyselfFaceTypeView = components["schemas"]["MyselfFaceTypeView"];
export type MyselfFaceTypesView = components["schemas"]["MyselfFaceTypesView"];
export type VillageCreatorSayBody = components["schemas"]["VillageCreatorSayBody"];
export type VillageKickBody = components["schemas"]["VillageKickBody"];
export type VillageAdminLeaveBody = components["schemas"]["VillageAdminLeaveBody"];
export type VillageAdminPlayerView = components["schemas"]["VillageAdminPlayerView"];
// SpringDoc + OpenAPI 3.1 で nullable フィールドが optional (`T | undefined`) に
// 落ちる件 (`PlayerView` でも同じ対応) に合わせ、null も書き込めるよう深く広げる。
// backend は undefined と null を同等に扱うので送信側でどちらでも OK。
type WidenOptionalsDeep<T> =
  T extends Array<infer U> ? Array<WidenOptionalsDeep<U>> :
  T extends object ? { [K in keyof T]: undefined extends T[K] ? WidenOptionalsDeep<T[K]> | null : WidenOptionalsDeep<T[K]> } :
  T;
export type VillageSettingsFormView = WidenOptionalsDeep<components["schemas"]["VillageSettingsFormView"]>;
export type VillageSettingsUpdateBody = WidenOptionalsDeep<components["schemas"]["VillageSettingsUpdateBody"]>;

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
  // backend contract: 参加者なし = 204、参加者あり = 200 + body。他の 2xx は返さない。
  if (res.status === 204) return null;
  return (await res.json()) as MyselfView;
}

/**
 * GET /api/v1/villages/{id}/participate/selectable-charas?charachipId=...
 *
 * 当該村で参加に選べるキャラを返す。複数キャラチップ村の場合は呼び出し側でキャラチップごとに
 * 並列呼出して結合する想定。失敗時は例外を投げる。
 */
export async function fetchSelectableCharas(
  villageId: number,
  charachipId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<CharaView[]> {
  const res = await fetcher(
    `/api/v1/villages/${villageId}/participate/selectable-charas?charachipId=${charachipId}`,
  );
  if (!res.ok) throw new Error(`selectable charas fetch failed: ${res.status}`);
  return res.json();
}

// ---------- participate mutations ----------

async function readErrorMessage(res: Response): Promise<string> {
  const errBody = await res.text();
  try {
    const parsed = JSON.parse(errBody) as { message?: string };
    if (parsed.message) return parsed.message;
  } catch {
    // not JSON
  }
  return errBody;
}

/** POST /api/v1/villages/{id}/participate */
export async function postParticipate(
  villageId: number,
  body: VillageParticipateBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/participate`, {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`participate failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/**
 * POST /api/v1/villages/{id}/participate/switch
 *
 * プロローグ中の参加 ↔ 見学切替。body 不要。
 */
export async function postSwitchParticipate(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/participate/switch`, {
    method: "POST",
  });
  if (!res.ok) {
    throw new Error(`switch participate failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** PUT /api/v1/villages/{id}/participate/skill */
export async function putChangeRequestSkill(
  villageId: number,
  body: VillageChangeRequestSkillBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/participate/skill`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`change request skill failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** DELETE /api/v1/villages/{id}/participate */
export async function deleteLeave(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/participate`, {
    method: "DELETE",
  });
  if (!res.ok) {
    throw new Error(`leave failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

// ---------- ability / vote / commit ----------

/** POST /api/v1/villages/{id}/abilities */
export async function postAbility(
  villageId: number,
  body: VillageAbilityBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/abilities`, {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`ability failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** POST /api/v1/villages/{id}/votes */
export async function postVote(
  villageId: number,
  body: VillageVoteBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/votes`, {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`vote failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** PUT /api/v1/villages/{id}/commit */
export async function putCommit(
  villageId: number,
  body: VillageCommitBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/commit`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`commit failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/**
 * GET /api/v1/villages/{id}/abilities/attack-targets?charaId=...
 *
 * 指定キャラが今日襲撃できる対象の charaId 一覧。
 */
export async function fetchAttackTargets(
  villageId: number,
  charaId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<number[]> {
  const res = await fetcher(
    `/api/v1/villages/${villageId}/abilities/attack-targets?charaId=${charaId}`,
  );
  if (!res.ok) {
    throw new Error(`attack-targets failed: ${res.status} ${await readErrorMessage(res)}`);
  }
  return res.json();
}

// ---------- RP (キャラ名 / メモ / 表情差分) ----------

/**
 * PUT /api/v1/villages/{id}/rp/name — キャラ名 + 略称変更。成功時 204。
 */
export async function putChangeName(
  villageId: number,
  body: VillageChangeNameBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/rp/name`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`change name failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/**
 * PUT /api/v1/villages/{id}/rp/memo — 簡易メモ変更。成功時 204。
 *
 * 空文字を送るとメモをクリア (backend は @Size(max=20) のみ、NotBlank ではない)。
 */
export async function putMemo(
  villageId: number,
  body: VillageMemoBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/rp/memo`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`memo failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/**
 * GET /api/v1/villages/{id}/rp/face-types — 自キャラに紐づく表情差分一覧。
 * オリジナルキャラチップ村以外は空配列。
 */
export async function fetchFaceTypes(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<MyselfFaceTypesView> {
  const res = await fetcher(`/api/v1/villages/${villageId}/rp/face-types`);
  if (!res.ok) {
    throw new Error(`face-types fetch failed: ${res.status} ${await readErrorMessage(res)}`);
  }
  return res.json();
}

/**
 * PUT /api/v1/villages/{id}/rp/face-types — 表情差分の name / display を一括更新。成功時 204。
 */
export async function putFaceTypes(
  villageId: number,
  body: VillageFaceTypeModifyBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/rp/face-types`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`face-types update failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

// ---------- creator / admin 操作 (Step 8e) ----------

/** POST /api/v1/villages/{id}/creator-say — 村建て発言。201 成功時 空 body。 */
export async function postCreatorSay(
  villageId: number,
  body: VillageCreatorSayBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/creator-say`, {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`creator-say failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** POST /api/v1/villages/{id}/kick — 村建てによる強制退村。 */
export async function postKick(
  villageId: number,
  body: VillageKickBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/kick`, {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`kick failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** POST /api/v1/villages/{id}/cancel — 廃村。 */
export async function postCancelVillage(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/cancel`, { method: "POST" });
  if (!res.ok) {
    throw new Error(`cancel failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** POST /api/v1/villages/{id}/extend-epilogue — エピローグ 1 日延長。 */
export async function postExtendEpilogue(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/extend-epilogue`, { method: "POST" });
  if (!res.ok) {
    throw new Error(`extend-epilogue failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** POST /api/v1/villages/{id}/shorten-epilogue — エピローグ 1 日短縮。 */
export async function postShortenEpilogue(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/shorten-epilogue`, { method: "POST" });
  if (!res.ok) {
    throw new Error(`shorten-epilogue failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** GET /api/v1/admin/villages/{id}/players — 管理者向けキャラ↔中の人一覧。 */
export async function fetchAdminPlayers(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<VillageAdminPlayerView[]> {
  const res = await fetcher(`/api/v1/admin/villages/${villageId}/players`);
  if (!res.ok) {
    throw new Error(`admin players failed: ${res.status} ${await readErrorMessage(res)}`);
  }
  return res.json();
}

/** POST /api/v1/admin/villages/{id}/access — 全員アクセス。 */
export async function postAdminForceAccess(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/admin/villages/${villageId}/access`, { method: "POST" });
  if (!res.ok) {
    throw new Error(`admin access failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** POST /api/v1/admin/villages/{id}/vote — 全員自分投票。 */
export async function postAdminForceVote(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/admin/villages/${villageId}/vote`, { method: "POST" });
  if (!res.ok) {
    throw new Error(`admin vote failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/** POST /api/v1/admin/villages/{id}/leave — 管理者強制退村。 */
export async function postAdminForceLeave(
  villageId: number,
  body: VillageAdminLeaveBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/admin/villages/${villageId}/leave`, {
    method: "POST",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`admin leave failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

// ---------- village settings (creator, Step 8f) ----------

/**
 * GET /api/v1/villages/{id}/settings/form — 設定編集 UI 用 (現在値 + 候補値) を返す。
 * 未認証 / 非 creator は 400。
 */
export async function fetchSettingsForm(
  villageId: number,
  fetcher: ApiFetch = browserFetch,
): Promise<VillageSettingsFormView> {
  const res = await fetcher(`/api/v1/villages/${villageId}/settings/form`);
  if (!res.ok) {
    throw new Error(`settings form fetch failed: ${res.status} ${await readErrorMessage(res)}`);
  }
  return res.json();
}

/**
 * PUT /api/v1/villages/{id}/settings — 設定を更新する。成功時 204。
 * cross-field バリデーション失敗時は 400 + ErrorResponse.message。
 */
export async function putSettings(
  villageId: number,
  body: VillageSettingsUpdateBody,
  fetcher: ApiFetch = browserFetch,
): Promise<void> {
  const res = await fetcher(`/api/v1/villages/${villageId}/settings`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    throw new Error(`settings update failed: ${res.status} ${await readErrorMessage(res)}`);
  }
}

/**
 * GET /api/v1/villages/{id}/abilities/footstep-candidates?charaId=...&targetCharaId=...
 *
 * 指定キャラが指定対象に対して残せる足音 (カンマ区切り部屋番号 / 'なし') の候補リスト。
 */
export async function fetchFootstepCandidates(
  villageId: number,
  params: { charaId?: number; targetCharaId?: number },
  fetcher: ApiFetch = browserFetch,
): Promise<string[]> {
  const qs = new URLSearchParams();
  if (params.charaId != null) qs.set("charaId", String(params.charaId));
  if (params.targetCharaId != null) qs.set("targetCharaId", String(params.targetCharaId));
  const suffix = qs.toString() ? `?${qs.toString()}` : "";
  const res = await fetcher(
    `/api/v1/villages/${villageId}/abilities/footstep-candidates${suffix}`,
  );
  if (!res.ok) {
    throw new Error(`footstep-candidates failed: ${res.status} ${await readErrorMessage(res)}`);
  }
  return res.json();
}
