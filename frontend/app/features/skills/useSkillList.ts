import { useQuery } from "@tanstack/react-query";

import { fetchSkillList, fetchSkillSearch, type SkillSearchFilter } from "./api";

export const SKILL_LIST_QUERY_KEY = "skill-list";
export const SKILL_SEARCH_QUERY_KEY = "skill-search";

export function useSkillList() {
  return useQuery({
    queryKey: [SKILL_LIST_QUERY_KEY],
    queryFn: fetchSkillList,
    staleTime: Infinity,
    retry: false,
  });
}

export function useSkillSearch(filter: SkillSearchFilter, enabled: boolean) {
  return useQuery({
    queryKey: [SKILL_SEARCH_QUERY_KEY, filter],
    queryFn: () => fetchSkillSearch(filter),
    enabled,
    retry: false,
  });
}
