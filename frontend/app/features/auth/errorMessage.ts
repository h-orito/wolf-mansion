import { ApiError } from "~/lib/api";

/**
 * 認証系 API エラーをフォーム表示用メッセージに変換する (Step 3.3)。
 * - 429 (too_many_requests): しばらくしてから
 * - 401 (authentication_failed): 列挙を助長しないため ID/PW を区別しない (03-auth.md)
 * - 400 (business_error / validation_error): backend の detail をそのまま見せる (重複 ID・確認不一致等)
 */
export function authErrorMessage(error: unknown): string {
  if (error instanceof ApiError) {
    switch (error.code) {
      case "too_many_requests":
        return "試行回数が多すぎます。しばらくしてから再試行してください。";
      case "authentication_failed":
        return "IDまたはパスワードが違います。";
      case "business_error":
      case "validation_error":
        return error.detail || "入力内容をご確認ください。";
      default:
        return "エラーが発生しました。時間をおいて再試行してください。";
    }
  }
  return "通信に失敗しました。ネットワーク状態をご確認ください。";
}
