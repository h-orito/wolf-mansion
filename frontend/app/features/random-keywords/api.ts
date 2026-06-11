import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

export type RandomKeyword = components["schemas"]["RandomKeyword"];

/** 一覧取得。`q` はキーワード/変換後文字列の部分一致 (API 側絞り込み)。 */
export function fetchRandomKeywords(q?: string): Promise<RandomKeyword[]> {
  const query = q ? `?${new URLSearchParams({ q })}` : "";
  return apiFetch<components["schemas"]["RandomKeywords"]>(`/api/v1/random-keywords${query}`).then(
    (r) => r.list,
  );
}

export function fetchRandomKeyword(id: number): Promise<RandomKeyword> {
  return apiFetch<RandomKeyword>(`/api/v1/random-keywords/${id}`);
}

export function registerRandomKeyword(input: {
  keyword: string;
  messages: string[];
}): Promise<RandomKeyword> {
  return apiFetch<RandomKeyword>("/api/v1/random-keywords", { method: "POST", body: input });
}

export function updateRandomKeyword(id: number, messages: string[]): Promise<RandomKeyword> {
  return apiFetch<RandomKeyword>(`/api/v1/random-keywords/${id}`, {
    method: "PUT",
    body: { messages },
  });
}

export function deleteRandomKeyword(id: number): Promise<void> {
  return apiFetch<void>(`/api/v1/random-keywords/${id}`, { method: "DELETE" });
}
