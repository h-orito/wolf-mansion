import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

export type Charachips = components["schemas"]["Charachips"];

/** お気に入りキャラを、所属キャラチップの charas をお気に入りのみに絞った Charachips として取得する。 */
export function fetchFavoriteCharachips(): Promise<Charachips> {
  return apiFetch<Charachips>("/api/v1/players/me/favorite-charas");
}

export function addFavoriteChara(charaId: number): Promise<void> {
  return apiFetch<void>(`/api/v1/players/me/favorite-charas/${charaId}`, { method: "PUT" });
}

export function removeFavoriteChara(charaId: number): Promise<void> {
  return apiFetch<void>(`/api/v1/players/me/favorite-charas/${charaId}`, { method: "DELETE" });
}
