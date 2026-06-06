import { z } from "zod";

/**
 * 認証フォームの入力スキーマ (Step 3.3)。
 *
 * パスワードポリシー / userId 制約は backend と一致させる:
 * - PasswordPolicy (`backend/.../fw/security/PasswordPolicy.kt`): 3〜60 文字 / 印字可能 ASCII
 * - signup userId (`SignupRequest`): 3〜12 文字 / 1 文字目英字 + 英数 - _
 *
 * NOTE: これらの定数の **正式な共有は Step 3.4 (OpenAPI→TS)** で行う。ここでは暫定で手書きする。
 */

export const PASSWORD_MIN_LENGTH = 3;
export const PASSWORD_MAX_LENGTH = 60;
/** 印字可能 ASCII (0x21–0x7E) のみ。backend PasswordPolicy.PATTERN と等価。 */
const PASSWORD_PATTERN = /^[\x21-\x7E]+$/;
const PASSWORD_MESSAGE = `パスワードは ${PASSWORD_MIN_LENGTH}〜${PASSWORD_MAX_LENGTH} 文字の半角英数記号で入力してください`;

const password = z
  .string()
  .min(PASSWORD_MIN_LENGTH, PASSWORD_MESSAGE)
  .max(PASSWORD_MAX_LENGTH, PASSWORD_MESSAGE)
  .regex(PASSWORD_PATTERN, PASSWORD_MESSAGE);

/** login は backend が password 形式を検証しない (緩和後ポリシー) ので、空でないことだけ見る。 */
export const loginSchema = z.object({
  userId: z.string().min(1, "IDを入力してください"),
  password: z.string().min(1, "パスワードを入力してください"),
});

export const signupSchema = z.object({
  userId: z
    .string()
    .min(3, "IDは 3〜12 文字で入力してください")
    .max(12, "IDは 3〜12 文字で入力してください")
    .regex(
      /^[a-zA-Z][a-zA-Z0-9\-_]*$/,
      "IDは英字で始まり、英数字・ハイフン・アンダーバーのみ使えます",
    ),
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
