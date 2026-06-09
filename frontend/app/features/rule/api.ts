import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

export type JudgeListResponse = components["schemas"]["JudgeListResponse"];
export type JudgeView = components["schemas"]["JudgeView"];

export function fetchJudges(): Promise<JudgeListResponse> {
  return apiFetch<JudgeListResponse>("/api/v1/rule/judges");
}
