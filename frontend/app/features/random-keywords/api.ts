import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

export type RandomKeyword = components["schemas"]["RandomKeyword"];

export function fetchRandomKeywords(): Promise<RandomKeyword[]> {
  return apiFetch<components["schemas"]["RandomKeywords"]>("/api/v1/random-keywords").then(
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
