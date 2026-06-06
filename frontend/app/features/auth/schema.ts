import { z } from "zod";
import {
  PASSWORD_MAX_LENGTH,
  PASSWORD_MIN_LENGTH,
  PASSWORD_PATTERN,
  SIGNUP_USER_ID_MAX_LENGTH,
  SIGNUP_USER_ID_MIN_LENGTH,
  SIGNUP_USER_ID_PATTERN,
} from "~/api/constants";

/**
 * 認証フォームの入力スキーマ (Step 3.3 / 3.4)。
 *
 * パスワードポリシー / userId 制約は backend の OpenAPI spec 由来の生成定数 (`~/api/constants`) を使う。
 * 定数の単一ソースは backend (`PasswordPolicy` / `SignupRequest`) で、`pnpm gen:api` で同期される。
 * spec の pattern は非アンカーなので、Bean Validation (matches) と揃えるためここで `^…$` を付与する。
 */

const passwordRegex = new RegExp(`^${PASSWORD_PATTERN}$`);
const userIdRegex = new RegExp(`^${SIGNUP_USER_ID_PATTERN}$`);

const PASSWORD_MESSAGE = `パスワードは ${PASSWORD_MIN_LENGTH}〜${PASSWORD_MAX_LENGTH} 文字の半角英数記号で入力してください`;
const USER_ID_LENGTH_MESSAGE = `IDは ${SIGNUP_USER_ID_MIN_LENGTH}〜${SIGNUP_USER_ID_MAX_LENGTH} 文字で入力してください`;

const password = z
  .string()
  .min(PASSWORD_MIN_LENGTH, PASSWORD_MESSAGE)
  .max(PASSWORD_MAX_LENGTH, PASSWORD_MESSAGE)
  .regex(passwordRegex, PASSWORD_MESSAGE);

/** login は backend が password 形式を検証しない (緩和後ポリシー) ので、空でないことだけ見る。 */
export const loginSchema = z.object({
  userId: z.string().min(1, "IDを入力してください"),
  password: z.string().min(1, "パスワードを入力してください"),
});

export const signupSchema = z.object({
  userId: z
    .string()
    .min(SIGNUP_USER_ID_MIN_LENGTH, USER_ID_LENGTH_MESSAGE)
    .max(SIGNUP_USER_ID_MAX_LENGTH, USER_ID_LENGTH_MESSAGE)
    .regex(userIdRegex, "IDは英字で始まり、英数字・ハイフン・アンダーバーのみ使えます"),
  password,
});

export const changePasswordSchema = z
  .object({
    password,
    confirmPassword: z.string(),
  })
  .refine((v) => v.password === v.confirmPassword, {
    error: "確認用パスワードが一致しません",
    path: ["confirmPassword"],
  });

export type LoginInput = z.infer<typeof loginSchema>;
export type SignupInput = z.infer<typeof signupSchema>;
export type ChangePasswordInput = z.infer<typeof changePasswordSchema>;
