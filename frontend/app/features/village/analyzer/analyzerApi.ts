import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

import type { PlayerMemo } from "./types";

export type AnalyzerVillageData = {
  village: {
    id: number;
    roomSize: { width: number; height: number } | null;
    epilogueDay: number | null;
    participants: { list: AnalyzerParticipant[] };
    days: { list: { day: number }[] };
    status: { code: string };
    setting: { chara: { dummyCharaId: number } };
  };
  participantIdToChara: Record<
    string,
    {
      id: number;
      size: { width: number; height: number };
      images: { list: { faceType: { code: string }; url: string }[] };
    }
  >;
  days: AnalyzerDaySituation[];
};

export type AnalyzerParticipant = {
  id: number;
  charaName: { name: string; shortName: string };
  room: { number: number } | null;
};

export type AnalyzerDaySituation = {
  day: number;
  rooms: AnalyzerDayRoom[];
  footsteps: string[];
  votes: AnalyzerVote[];
};

export type AnalyzerDayRoom = {
  roomNumber: number;
  x: number;
  y: number;
  participantId: number | null;
  isDead: boolean | null;
  deadDay: number | null;
  deadReason: { code: string; name: string } | null;
};

export type AnalyzerVote = {
  charaId: number;
  targetCharaId: number;
};

export function fetchAnalyzerVillage(villageId: number): Promise<AnalyzerVillageData> {
  return apiFetch<AnalyzerVillageData>(`/api/village/${villageId}`);
}

/** ログインプレイヤー本人の推理補助メモを取得する (未保存なら空のメモが返る)。 */
export function fetchAnalyzerMemo(villageId: number): Promise<PlayerMemo> {
  return apiFetch<PlayerMemo>(`/api/v1/villages/${villageId}/analyzer-memo`);
}

export function saveAnalyzerMemo(
  villageId: number,
  memo: Omit<PlayerMemo, "villageId">,
): Promise<void> {
  const body: components["schemas"]["AnalyzerMemoUpdateRequest"] = {
    wholeMemo: memo.wholeMemo,
    participantMemos: memo.participantMemos,
    dailyMemos: memo.dailyMemos,
    dailyFootstepMemos: memo.dailyFootstepMemos,
  };
  return apiFetch<void>(`/api/v1/villages/${villageId}/analyzer-memo`, {
    method: "PUT",
    body,
  });
}
