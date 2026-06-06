import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link } from "react-router";

import { changePassword } from "~/features/auth/api";
import { authErrorMessage } from "~/features/auth/errorMessage";
import { RequireAuth } from "~/features/auth/RequireAuth";
import { type ChangePasswordInput, changePasswordSchema } from "~/features/auth/schema";
import {
  AuthCard,
  buttonClass,
  fieldErrorClass,
  formErrorClass,
  inputClass,
} from "~/features/auth/ui";
import { zodResolver } from "~/lib/zodResolver";
import type { Route } from "./+types/change-password";

export function meta(_: Route.MetaArgs) {
  return [{ title: "パスワード変更 | 人狼の館" }];
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
    <AuthCard title="パスワード変更">
      {done && (
        <p className="mb-4 rounded bg-green-50 px-3 py-2 text-sm text-green-700 dark:bg-green-950">
          パスワードを変更しました。
        </p>
      )}
      <form onSubmit={onSubmit} className="space-y-4" noValidate>
        {formError && <p className={formErrorClass}>{formError}</p>}
        <div>
          <label htmlFor="password" className="mb-1 block text-sm">
            新しいパスワード
          </label>
          <input
            id="password"
            type="password"
            autoComplete="new-password"
            className={inputClass}
            {...register("password")}
          />
          <p className="mt-1 text-xs text-gray-500">3〜60 文字 / 半角英数記号</p>
          {errors.password && <p className={fieldErrorClass}>{errors.password.message}</p>}
        </div>
        <div>
          <label htmlFor="confirmPassword" className="mb-1 block text-sm">
            新しいパスワード (確認)
          </label>
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
        </div>
        <button type="submit" disabled={isSubmitting} className={buttonClass}>
          {isSubmitting ? "変更中..." : "変更する"}
        </button>
      </form>
      <p className="mt-4 text-sm">
        <Link to="/mypage" className="text-blue-600 hover:underline">
          マイページへ戻る
        </Link>
      </p>
    </AuthCard>
  );
}

export default function ChangePassword() {
  return (
    <RequireAuth>
      <ChangePasswordForm />
    </RequireAuth>
  );
}
