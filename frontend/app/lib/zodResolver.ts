import type { FieldError, FieldErrors, FieldValues, Resolver } from "react-hook-form";
import type { ZodType } from "zod";

/**
 * react-hook-form 用の最小 zod リゾルバ。
 *
 * `@hookform/resolvers` を追加せず自前で持つ: 依存の追加 (供給網/版ピン留めポリシー) を避け、
 * 「safeParse → フィールド別エラー」だけを提供する。
 */
export function zodResolver<T extends FieldValues>(schema: ZodType<T>): Resolver<T> {
  return (values) => {
    const result = schema.safeParse(values);
    if (result.success) {
      return { values: result.data, errors: {} };
    }
    // RHF のリゾルバ契約: 失敗時は values を空オブジェクトにし、errors にフィールド別エラーを返す。
    // zod の issue path から動的にフィールドを引くため FieldErrors<T> を直接は組めず、
    // ここだけ型アサーションする (@hookform/resolvers も同様の構造)。
    const errors: Record<string, unknown> = {};
    for (const issue of result.error.issues) {
      const path = issue.path.length > 0 ? issue.path.map(String) : ["root"];
      setNestedError(errors, path, { type: "validation", message: issue.message });
    }
    return { values: {}, errors: errors as FieldErrors<T> };
  };
}

/**
 * issue path に沿って `{a: [{b: {type, message}}]}` 形式のネストエラーを組み立てる。
 * 同一フィールドに複数 issue がある場合は最初の 1 件のみ採用する (RHF の表示単位に合わせる)。
 */
function setNestedError(target: Record<string, unknown>, path: string[], error: FieldError) {
  let current = target;
  for (let i = 0; i < path.length - 1; i++) {
    const key = path[i];
    const child = current[key];
    if (isFieldError(child)) return; // 親パス自体に既にエラーがある場合はそちらを優先する
    if (child === undefined) current[key] = {};
    current = current[key] as Record<string, unknown>;
  }
  const leaf = path[path.length - 1];
  if (current[leaf] === undefined) {
    current[leaf] = error;
  }
}

function isFieldError(value: unknown): value is FieldError {
  return (
    typeof value === "object" &&
    value !== null &&
    "type" in value &&
    "message" in value &&
    typeof (value as { message?: unknown }).message === "string"
  );
}
