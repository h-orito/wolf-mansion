import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { fetchMe, login, logout, type LoginBody, type MeResponse } from "./api";
// MeResponse は useMeQuery の型推論で使用

export const ME_QUERY_KEY = ["auth", "me"] as const;

/** GET /api/v1/auth/me */
export function useMeQuery() {
  return useQuery<MeResponse>({
    queryKey: ME_QUERY_KEY,
    queryFn: fetchMe,
    staleTime: 60 * 1000,
  });
}

/** POST /api/v1/auth/login → 成功時に me cache を更新 */
export function useLoginMutation() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (body: LoginBody) => login(body),
    onSuccess: (data) => {
      qc.setQueryData(ME_QUERY_KEY, data);
    },
  });
}

/** POST /api/v1/auth/logout → me cache を破棄 (次回参照時に refetch される) */
export function useLogoutMutation() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: () => logout(),
    onSuccess: () => {
      qc.removeQueries({ queryKey: ME_QUERY_KEY });
    },
  });
}
