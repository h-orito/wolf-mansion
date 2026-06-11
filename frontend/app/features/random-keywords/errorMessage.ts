import { ApiError } from "~/lib/api";

/**
 * ランダムキーワードの書き込み API エラーをフォーム表示用メッセージに変換する。
 * 書き込みはログイン必須のため、未ログイン (401) はログイン誘導の文言にする。
 */
export function randomKeywordErrorMessage(error: unknown): string {
  if (error instanceof ApiError) {
    if (error.status === 401) {
      return "この操作にはログインが必要です。";
    }
    switch (error.code) {
      case "not_found":
        return "すでに削除されています。";
      case "business_error":
      case "validation_error":
        return error.detail || "入力内容をご確認ください。";
      default:
        return "エラーが発生しました。時間をおいて再試行してください。";
    }
  }
  return "通信に失敗しました。ネットワーク状態をご確認ください。";
}
