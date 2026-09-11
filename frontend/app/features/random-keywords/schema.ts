import { z } from "zod";
import {
  RANDOM_KEYWORD_MAX_LENGTH,
  RANDOM_KEYWORD_MESSAGE_MAX_LENGTH,
  RANDOM_KEYWORD_MESSAGE_MIN_LENGTH,
  RANDOM_KEYWORD_MIN_LENGTH,
  RANDOM_KEYWORD_PATTERN,
} from "~/api/constants";

/**
 * ランダムキーワードフォームの入力スキーマ。
 *
 * 制約は backend の OpenAPI spec 由来の生成定数 (`~/api/constants`、単一ソースは backend の
 * RandomKeywordPolicy) を使う。spec の pattern は非アンカーなのでここで `^…$` を付与する。
 * NG ワード・行重複は backend (toModel) と同じ規則をクライアントでも検証する。
 */

const keywordRegex = new RegExp(`^${RANDOM_KEYWORD_PATTERN}$`);

const KEYWORD_LENGTH_MESSAGE = `キーワードは${RANDOM_KEYWORD_MIN_LENGTH}文字以上${RANDOM_KEYWORD_MAX_LENGTH}文字以内で入力してください`;
const MESSAGE_LENGTH_MESSAGE = `変換後文字列はそれぞれ${RANDOM_KEYWORD_MESSAGE_MIN_LENGTH}文字以上${RANDOM_KEYWORD_MESSAGE_MAX_LENGTH}文字以内で入力してください`;

/** textarea の入力を 1 行 = 1 変換後文字列に分解する (送信ペイロードと検証で共用)。 */
export function splitMessages(text: string): string[] {
  return text.trim().split(/\r\n|\r|\n/);
}

const keyword = z
  .string()
  .min(RANDOM_KEYWORD_MIN_LENGTH, KEYWORD_LENGTH_MESSAGE)
  .max(RANDOM_KEYWORD_MAX_LENGTH, KEYWORD_LENGTH_MESSAGE)
  .regex(keywordRegex, "キーワードは半角英語のみで入力してください")
  .refine(
    (v) => !v.includes("or") && !v.includes("who"),
    "キーワードにorとwhoを含むことはできません",
  );

const message = z
  .string()
  .min(1, "変換後文字列を入力してください")
  .refine(
    (v) =>
      splitMessages(v).every(
        (line) =>
          line.length >= RANDOM_KEYWORD_MESSAGE_MIN_LENGTH &&
          line.length <= RANDOM_KEYWORD_MESSAGE_MAX_LENGTH,
      ),
    MESSAGE_LENGTH_MESSAGE,
  )
  .refine((v) => {
    const lines = splitMessages(v);
    return new Set(lines).size === lines.length;
  }, "変換後文字列は全て違う文字列にしてください");

export const randomKeywordCreateSchema = z.object({ keyword, message });

export const randomKeywordEditSchema = z.object({ message });

export type RandomKeywordCreateInput = z.infer<typeof randomKeywordCreateSchema>;
export type RandomKeywordEditInput = z.infer<typeof randomKeywordEditSchema>;
