import type { components } from "~/api/types";
import { apiFetch } from "~/lib/api";

/**
 * 認証 REST 呼び出し (Step 3.1/3.2 で実装済の backend に対応)。
 * すべて CSR 専用。Cookie は backend が HttpOnly で発行/破棄する。
 */

/** `/api/v1/auth/me` 等が返す最小プレイヤー情報 (OpenAPI 生成型・Step 3.4)。 */
export type MeResponse = components["schemas"]["MeResponse"];

const AUTH_BASE = "/api/v1/auth";

/** ID / パスワードでログイン。失敗 401 / レート制限 429。成功で Cookie がセットされる。 */
export function login(userId: string, password: string): Promise<MeResponse> {
  return apiFetch<MeResponse>(`${AUTH_BASE}/login`, {
    method: "POST",
    body: { userId, password },
  });
}

/** 新規登録 + 自動ログイン。重複 ID 400 / cooldown 429。成功で Cookie がセットされる。 */
export function signup(userId: string, password: string): Promise<MeResponse> {
  return apiFetch<MeResponse>(`${AUTH_BASE}/signup`, {
    method: "POST",
    body: { userId, password },
  });
}

/** ログイン中ユーザーのパスワード変更 (認証必須)。確認不一致 400 → 204。 */
export function changePassword(password: string, confirmPassword: string): Promise<void> {
  return apiFetch<void>(`${AUTH_BASE}/password`, {
    method: "POST",
    body: { password, confirmPassword },
  });
}

/** ログアウト。両 Cookie を破棄し DB の refresh も失効 → 204。 */
export function logout(): Promise<void> {
  return apiFetch<void>(`${AUTH_BASE}/logout`, { method: "POST" });
}

/** 現在のログインプレイヤー情報。未認証は 401 (ApiError) を throw する。 */
export function fetchMe(): Promise<MeResponse> {
  return apiFetch<MeResponse>(`${AUTH_BASE}/me`);
}
