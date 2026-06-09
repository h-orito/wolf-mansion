import { useQuery } from "@tanstack/react-query";
import { fetchJudges } from "./api";

const JUDGES_QUERY_KEY = "judges";

export function useJudges() {
  return useQuery({
    queryKey: [JUDGES_QUERY_KEY],
    queryFn: fetchJudges,
    staleTime: Infinity,
  });
}
