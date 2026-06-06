import type { FieldError, FieldErrors, FieldValues, Resolver } from "react-hook-form";
import type { ZodType } from "zod";

/**
 * react-hook-form 用の最小 zod リゾルバ (Step 3.3)。
 *
 * `@hookform/resolvers` を追加せず自前で持つ: 依存の追加 (供給網/版ピン留めポリシー) を避け、
 * フラットな認証フォームに必要な「safeParse → フィールド別エラー」だけを提供する。
 * ネストフォームが必要になったら `@hookform/resolvers` 導入を検討する。
 */
export function zodResolver<T extends FieldValues>(schema: ZodType<T>): Resolver<T> {
  return (values) => {
    const result = schema.safeParse(values);
    if (result.success) {
      return { values: result.data, errors: {} };
    }
    // RHF のリゾルバ契約: 失敗時は values を空オブジェクトにし、errors にフィールド別エラーを返す。
    // zod の issue path から動的にフィールド名を引くため FieldErrors<T> を直接は組めず、
    // ここだけ型アサーションする (@hookform/resolvers も同様の構造)。
    const errors: Partial<Record<string, FieldError>> = {};
    for (const issue of result.error.issues) {
      // フラットフォーム前提: 先頭パス要素をフィールド名とし、最初の 1 件のみ採用する。
      const key = String(issue.path[0] ?? "root");
      if (!(key in errors)) {
        errors[key] = { type: "validation", message: issue.message };
      }
    }
    return { values: {}, errors: errors as FieldErrors<T> };
  };
}
