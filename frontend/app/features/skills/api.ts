import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

export type SkillListResponse = components["schemas"]["SkillListResponse"];
export type SimpleSkillView = components["schemas"]["SimpleSkillView"];
export type SkillSearchResponse = components["schemas"]["SkillSearchResponse"];

export type SkillSearchFilter = {
  tags?: string[];
  name?: string;
  villageId?: number | null;
};

export function fetchSkillList(): Promise<SkillListResponse> {
  return apiFetch<SkillListResponse>("/api/v1/skills");
}

export function fetchSkillSearch(filter: SkillSearchFilter): Promise<SkillSearchResponse> {
  const params = new URLSearchParams();
  (filter.tags ?? []).forEach((t) => params.append("tags", t));
  if (filter.name) params.append("name", filter.name);
  if (filter.villageId != null) params.append("villageId", String(filter.villageId));
  const query = params.toString();
  return apiFetch<SkillSearchResponse>(`/api/v1/skills/search${query ? `?${query}` : ""}`);
}
