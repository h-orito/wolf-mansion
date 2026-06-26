import { apiFetch } from "~/lib/api";

export type PlayerListResponse = {
  players: PlayerView[];
  allPageCount: number;
  isExistPrePage: boolean;
  isExistNextPage: boolean;
  currentPageNum: number;
};

export type PlayerView = {
  name: string;
};

export function fetchPlayers(pageNum: number): Promise<PlayerListResponse> {
  return apiFetch<PlayerListResponse>(`/api/v1/players?pageNum=${pageNum}`);
}
