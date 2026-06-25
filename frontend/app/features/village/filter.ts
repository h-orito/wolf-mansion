/**
 * 発言抽出のフィルタ状態。URL の searchParams を正本にする (共有 URL で再現可能)。
 * パラメータ名は既存 (`fpid`/`typ`/`kwd`/`tpid`/`spl`) を踏襲する。
 */

/** 抽出モーダルの発言種別 (チェックボックスの並び順)。 */
export const FILTER_TYPES: { value: string; label: string }[] = [
  { value: "NORMAL_SAY", label: "通常" },
  { value: "GRAVE_SPECTATE_SAY", label: "墓下見学" },
  { value: "MONOLOGUE_SAY", label: "独り言" },
  { value: "CREATOR_SAY", label: "村建て発言" },
  { value: "WEREWOLF_SAY", label: "囁き" },
  { value: "MASON_SAY", label: "共鳴" },
  { value: "LOVERS_SAY", label: "恋人" },
  { value: "TELEPATHY", label: "念話" },
  { value: "SECRET_SAY", label: "秘話" },
  { value: "ACTION", label: "アクション" },
  { value: "PUBLIC_SYSTEM", label: "公開シスメ" },
  { value: "PRIVATE_SYSTEM", label: "非公開シスメ" },
];

export type MessageFilter = {
  /** 発言者の参加者 ID。空 = 全員 */
  participantIds: number[];
  /** 宛先の参加者 ID。空 = 全員 */
  toParticipantIds: number[];
  /** 発言種別。空 = 全種別 */
  types: string[];
  /** キーワード (スペース区切りの原文)。空文字 = 指定なし */
  keywords: string;
  /** ネタバレ防止 (エピローグ前同等の表示にする) */
  spoiled: boolean;
};

export const EMPTY_FILTER: MessageFilter = {
  participantIds: [],
  toParticipantIds: [],
  types: [],
  keywords: "",
  spoiled: false,
};

export function parseFilter(params: URLSearchParams): MessageFilter {
  const ids = (value: string | null): number[] =>
    value == null || value === ""
      ? []
      : value
          .split(",")
          .map(Number)
          .filter((n) => Number.isFinite(n));
  return {
    participantIds: ids(params.get("fpid")),
    toParticipantIds: ids(params.get("tpid")),
    types: (params.get("typ") ?? "").split(",").filter((t) => t !== ""),
    keywords: params.get("kwd") ?? "",
    spoiled: params.get("spl") === "true",
  };
}

/** フィルタ状態を searchParams へ書き戻す (既存の他パラメータは保持)。 */
export function applyFilterToParams(
  params: URLSearchParams,
  filter: MessageFilter,
): URLSearchParams {
  const next = new URLSearchParams(params);
  const setOrDelete = (key: string, value: string) => {
    if (value === "") next.delete(key);
    else next.set(key, value);
  };
  setOrDelete("fpid", filter.participantIds.join(","));
  setOrDelete("tpid", filter.toParticipantIds.join(","));
  setOrDelete("typ", filter.types.join(","));
  setOrDelete("kwd", filter.keywords);
  setOrDelete("spl", filter.spoiled ? "true" : "");
  return next;
}

/** 何らかの抽出条件が指定されているか (footer の「抽出中」表示用)。 */
export function isFiltering(filter: MessageFilter): boolean {
  return (
    filter.participantIds.length > 0 ||
    filter.toParticipantIds.length > 0 ||
    filter.types.length > 0 ||
    filter.keywords !== "" ||
    filter.spoiled
  );
}
