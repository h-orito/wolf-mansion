import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

/** 村詳細 (`GET /api/v1/villages/{id}`)。公開情報のみ (入村パスワードはマスク済み)。 */
export type VillageDetailView = components["schemas"]["VillageDetailView"];
/** 村状況 (`GET /api/v1/villages/{id}/situation`)。スポイラーマスク適用済み。 */
export type VillageSituationView = components["schemas"]["VillageSituationView"];
/** 参加者本人の状態 (`GET /api/v1/villages/{id}/situation/me`)。要認証。 */
export type ParticipantSituationView = components["schemas"]["ParticipantSituationView"];
/** 村ポーリング (`POST /api/v1/villages/{id}/update`) の応答。 */
export type VillageUpdateResponse = components["schemas"]["VillageUpdateResponse"];

export type VillageRoomAssignedRow = components["schemas"]["VillageRoomAssignedRow"];
export type VillageRoomAssigned = components["schemas"]["VillageRoomAssigned"];
export type VillageMemberContent = components["schemas"]["VillageMemberContent"];
export type VillageVoteContent = components["schemas"]["VillageVoteContent"];
export type VillageFootstepContent = components["schemas"]["VillageFootstepContent"];
export type VillageSituationContent = components["schemas"]["VillageSituationContent"];

/** 村詳細を取得する (公開)。存在しない村は 404 (`ApiError.code === "not_found"`)。 */
export function fetchVillage(id: number): Promise<VillageDetailView> {
  return apiFetch<VillageDetailView>(`/api/v1/villages/${id}`);
}

/** 村状況を取得する (公開・ログイン時は視点がマスクに反映される)。 */
export function fetchVillageSituation(id: number, day?: number): Promise<VillageSituationView> {
  const query = day != null ? `?day=${day}` : "";
  return apiFetch<VillageSituationView>(`/api/v1/villages/${id}/situation${query}`);
}

/** 参加者本人の状態を取得する (要認証)。 */
export function fetchMyVillageSituation(
  id: number,
  day?: number,
): Promise<ParticipantSituationView> {
  const query = day != null ? `?day=${day}` : "";
  return apiFetch<ParticipantSituationView>(`/api/v1/villages/${id}/situation/me${query}`);
}

/**
 * 村ポーリング。最終アクセス更新と日付更新の駆動を行い、最新日を返す。
 * 日付更新が起きた直後の応答は更新前の最新日のままで、次回ポーリングで新しい日付になる。
 */
export function postVillageUpdate(id: number): Promise<VillageUpdateResponse> {
  return apiFetch<VillageUpdateResponse>(`/api/v1/villages/${id}/update`, { method: "POST" });
}

/** 発言一覧 (`GET /api/v1/villages/{id}/messages`)。可視範囲はサーバ側でマスク済み。 */
export type VillageMessageListContent = components["schemas"]["VillageMessageListContent"];
/** 1 件の発言。本文は生テキストで、HTML 化はクライアントの責務。 */
export type VillageMessageContent = components["schemas"]["VillageMessageContent"];
/** アンカー発言 (閲覧できない場合は message が null)。 */
export type VillageAnchorMessageContent = components["schemas"]["VillageAnchorMessageContent"];
export type VillageAnchorMessagesContent = components["schemas"]["VillageAnchorMessagesContent"];
/** 参加者の正体一覧 (エピローグ以降のみ)。 */
export type VillageParticipantsContent = components["schemas"]["VillageParticipantsContent"];

/** 発言一覧の取得条件。絞り込み系は発言抽出 UI が使う。 */
export type VillageMessageSearch = {
  day?: number;
  pageSize?: number;
  pageNum?: number;
  isPaging?: boolean;
  isDispLatest?: boolean;
  participantIds?: number[];
  toParticipantIds?: number[];
  types?: string[];
  keywords?: string;
};

function messageSearchParams(search: VillageMessageSearch): string {
  const params = new URLSearchParams();
  if (search.day != null) params.set("day", String(search.day));
  if (search.pageSize != null) params.set("pageSize", String(search.pageSize));
  if (search.pageNum != null) params.set("pageNum", String(search.pageNum));
  if (search.isPaging != null) params.set("isPaging", String(search.isPaging));
  if (search.isDispLatest != null) params.set("isDispLatest", String(search.isDispLatest));
  (search.participantIds ?? []).forEach((p) => params.append("participantIds", String(p)));
  (search.toParticipantIds ?? []).forEach((p) => params.append("toParticipantIds", String(p)));
  (search.types ?? []).forEach((t) => params.append("types", t));
  if (search.keywords != null && search.keywords !== "") params.set("keywords", search.keywords);
  const query = params.toString();
  return query ? `?${query}` : "";
}

/** 発言一覧を取得する (公開・視点反映)。 */
export function fetchVillageMessages(
  id: number,
  search: VillageMessageSearch,
): Promise<VillageMessageListContent> {
  return apiFetch<VillageMessageListContent>(
    `/api/v1/villages/${id}/messages${messageSearchParams(search)}`,
  );
}

/** 最新発言日時 (uuuuMMddHHmmss 文字列、新着検知用)。 */
export function fetchLatestMessageDatetime(
  id: number,
  search: VillageMessageSearch,
): Promise<string> {
  return apiFetch<components["schemas"]["VillageLatestMessageDatetimeContent"]>(
    `/api/v1/villages/${id}/messages/latest-datetime${messageSearchParams(search)}`,
  ).then((r) => r.latestMessageDatetime);
}

/** 単一アンカー発言を取得する。 */
export function fetchAnchorMessage(
  id: number,
  messageType: string,
  messageNumber: number,
): Promise<VillageAnchorMessageContent> {
  return apiFetch<VillageAnchorMessageContent>(
    `/api/v1/villages/${id}/messages/anchor?messageType=${encodeURIComponent(messageType)}&messageNumber=${messageNumber}`,
  );
}

/** 複数アンカー発言 (`n123_w45` 形式) を取得する。通知のパーマリンクページが使う。 */
export function fetchAnchorMessages(
  id: number,
  anchors: string,
): Promise<VillageAnchorMessagesContent> {
  return apiFetch<VillageAnchorMessagesContent>(
    `/api/v1/villages/${id}/messages/anchors?anchors=${encodeURIComponent(anchors)}`,
  );
}

/** 参加者の正体一覧を取得する (エピローグ以降のみ 200)。 */
export function fetchVillageParticipants(id: number): Promise<VillageParticipantsContent> {
  return apiFetch<VillageParticipantsContent>(`/api/v1/villages/${id}/participants`);
}

/** 発言抽出用の参加者ビュー (村状況 API の participantList)。 */
export type VillageFilterParticipantContent =
  components["schemas"]["VillageFilterParticipantContent"];

/** 発言の確認/投稿のリクエスト。 */
export type VillageSayRequest = components["schemas"]["VillageSayRequest"];
/** 発言確認 (プレビュー) の応答。 */
export type VillageSayConfirmContent = components["schemas"]["VillageSayConfirmContent"];
/** 発言種別ごとの選択肢 (制限・宛先候補)。 */
export type SayMessageTypeView = components["schemas"]["ParticipantSituationViewSayMessageType"];
export type SayCharaImageView = components["schemas"]["ParticipantSituationViewSayCharaImage"];

/** 発言を確認する (プレビュー。まだ保存されない)。要認証。 */
export function confirmVillageSay(
  id: number,
  request: VillageSayRequest,
): Promise<VillageSayConfirmContent> {
  return apiFetch<VillageSayConfirmContent>(`/api/v1/villages/${id}/say-confirm`, {
    method: "POST",
    body: request,
  });
}

/** 発言する。要認証 → 204。 */
export function sayVillage(id: number, request: VillageSayRequest): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/say`, { method: "POST", body: request });
}

/** アクション発言のリクエスト。 */
export type VillageActionRequest = components["schemas"]["VillageActionRequest"];

/** アクション発言を確認する (プレビュー)。要認証。 */
export function confirmVillageAction(
  id: number,
  request: VillageActionRequest,
): Promise<VillageSayConfirmContent> {
  return apiFetch<VillageSayConfirmContent>(`/api/v1/villages/${id}/action-confirm`, {
    method: "POST",
    body: request,
  });
}

/** アクション発言する。要認証 → 204。 */
export function actionVillage(id: number, request: VillageActionRequest): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/action`, { method: "POST", body: request });
}

/** 入村のリクエスト (JSON part)。 */
export type VillageParticipateRequest = components["schemas"]["VillageParticipateRequest"];

/** 入村確認 (サーバ検証のみ)。通れば 204。要認証。 */
export function confirmVillageParticipate(
  id: number,
  request: VillageParticipateRequest,
): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/participate-confirm`, {
    method: "POST",
    body: request,
  });
}

/**
 * 入村する。multipart/form-data で JSON part (`request`) + オリジナル画像 (`charaImage`、
 * 原画村のみ必須) を送る。要認証 → 204。
 */
export function participateVillage(
  id: number,
  request: VillageParticipateRequest,
  charaImage: File | null,
): Promise<void> {
  const formData = new FormData();
  formData.append("request", new Blob([JSON.stringify(request)], { type: "application/json" }));
  if (charaImage != null) formData.append("charaImage", charaImage);
  return apiFetch<void>(`/api/v1/villages/${id}/participate`, {
    method: "POST",
    body: formData,
  });
}

/** 希望役職変更のリクエスト。 */
export type VillageChangeSkillRequest = components["schemas"]["VillageChangeSkillRequest"];

/** 参加 ⇄ 見学を切り替える。要認証 → 204。 */
export function switchVillageParticipate(id: number): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/switch-participate`, { method: "POST" });
}

/** 希望役職 (第 1/第 2) を変更する。要認証 → 204。 */
export function changeVillageRequestSkill(
  id: number,
  request: VillageChangeSkillRequest,
): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/change-skill`, { method: "POST", body: request });
}

/** 退村する。要認証 → 204。 */
export function leaveVillage(id: number): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/leave`, { method: "POST" });
}

/** 能力セットのリクエスト。 */
export type VillageAbilityRequest = components["schemas"]["VillageAbilityRequest"];
/** 能力セットの候補 (襲撃対象 / 足音)。 */
export type AbilityCandidatesView = components["schemas"]["AbilityCandidatesView"];

/** 能力をセットする。要認証 → 204。 */
export function setVillageAbility(id: number, request: VillageAbilityRequest): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/ability`, { method: "POST", body: request });
}

/** 襲撃者を選んだときの襲撃対象候補。 */
export function fetchAttackTargets(id: number, charaId: number): Promise<AbilityCandidatesView> {
  return apiFetch<AbilityCandidatesView>(
    `/api/v1/villages/${id}/ability/attack-targets?charaId=${charaId}`,
  );
}

/** 対象を選んだときの足音 (通過する部屋) 候補。 */
export function fetchAbilityFootsteps(
  id: number,
  charaId: number | null,
  targetCharaId: number | null,
): Promise<AbilityCandidatesView> {
  const params = new URLSearchParams();
  if (charaId != null) params.set("charaId", String(charaId));
  if (targetCharaId != null) params.set("targetCharaId", String(targetCharaId));
  const query = params.toString();
  return apiFetch<AbilityCandidatesView>(
    `/api/v1/villages/${id}/ability/footsteps${query !== "" ? `?${query}` : ""}`,
  );
}

/** 投票セットのリクエスト。 */
export type VillageVoteRequest = components["schemas"]["VillageVoteRequest"];

/** 投票をセットする。要認証 → 204。 */
export function setVillageVote(id: number, request: VillageVoteRequest): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/vote`, { method: "POST", body: request });
}

/** コミット ON/OFF のリクエスト。 */
export type VillageCommitRequest = components["schemas"]["VillageCommitRequest"];

/** コミットを ON/OFF する。要認証 → 204。 */
export function setVillageCommit(id: number, request: VillageCommitRequest): Promise<void> {
  return apiFetch<void>(`/api/v1/villages/${id}/commit`, { method: "POST", body: request });
}
