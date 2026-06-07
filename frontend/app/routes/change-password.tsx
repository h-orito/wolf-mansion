import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link } from "react-router";

import { changePassword } from "~/features/auth/api";
import { authErrorMessage } from "~/features/auth/errorMessage";
import { RequireAuth } from "~/features/auth/RequireAuth";
import { type ChangePasswordInput, changePasswordSchema } from "~/features/auth/schema";
import {
  AuthLayout,
  buttonClass,
  fieldErrorClass,
  FormActions,
  FormRow,
  formErrorClass,
  inputClass,
  linkClass,
} from "~/features/auth/ui";
import { siteMeta } from "~/lib/meta";
import { zodResolver } from "~/lib/zodResolver";
import type { Route } from "./+types/change-password";

export function meta(_: Route.MetaArgs) {
  return siteMeta("パスワード変更");
}

function ChangePasswordForm() {
  const [formError, setFormError] = useState<string | null>(null);
  const [done, setDone] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<ChangePasswordInput>({ resolver: zodResolver(changePasswordSchema) });

  const onSubmit = handleSubmit(async (values) => {
    setFormError(null);
    try {
      await changePassword(values.password, values.confirmPassword);
      reset();
      setDone(true);
    } catch (e) {
      setFormError(authErrorMessage(e));
    }
  });

  return (
    <AuthLayout title="パスワード変更">
      {done && (
        <p className="mb-4 rounded bg-[#334033] px-3 py-2 text-wm-accent">
          パスワードを変更しました。
        </p>
      )}
      <form onSubmit={onSubmit} noValidate>
        {formError && <span className={formErrorClass}>{formError}</span>}
        {/* ラベルは既存 change-password.html の文言に合わせる。 */}
        <FormRow label="変更後パスワード" htmlFor="password">
          <input
            id="password"
            type="password"
            autoComplete="new-password"
            className={inputClass}
            {...register("password")}
          />
          {errors.password && <p className={fieldErrorClass}>{errors.password.message}</p>}
        </FormRow>
        <FormRow label="パスワード（確認用）" htmlFor="confirmPassword">
          <input
            id="confirmPassword"
            type="password"
            autoComplete="new-password"
            className={inputClass}
            {...register("confirmPassword")}
          />
          {errors.confirmPassword && (
            <p className={fieldErrorClass}>{errors.confirmPassword.message}</p>
          )}
        </FormRow>
        <FormActions>
          {/* 既存 :8091 はボタン文言が "ログイン" (明らかな legacy のコピペ誤り) のため、意味の通る "変更する" にする。 */}
          <button type="submit" disabled={isSubmitting} className={buttonClass}>
            {isSubmitting ? "変更中..." : "変更する"}
          </button>
        </FormActions>
      </form>
      <p className="mt-4">
        <Link to="/mypage" className={linkClass}>
          マイページへ戻る
        </Link>
      </p>
    </AuthLayout>
  );
}

export default function ChangePassword() {
  return (
    <RequireAuth>
      <ChangePasswordForm />
    </RequireAuth>
  );
}
