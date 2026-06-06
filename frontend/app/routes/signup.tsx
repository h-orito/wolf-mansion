import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate, useSearchParams } from "react-router";

import { signup } from "~/features/auth/api";
import { authErrorMessage } from "~/features/auth/errorMessage";
import { safeReturnTo } from "~/features/auth/returnTo";
import { type SignupInput, signupSchema } from "~/features/auth/schema";
import { useSetMe } from "~/features/auth/useMe";
import {
  AuthCard,
  buttonClass,
  fieldErrorClass,
  formErrorClass,
  inputClass,
} from "~/features/auth/ui";
import { zodResolver } from "~/lib/zodResolver";
import type { Route } from "./+types/signup";

export function meta(_: Route.MetaArgs) {
  return [{ title: "新規登録 | 人狼の館" }];
}

export default function Signup() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const setMe = useSetMe();
  const [formError, setFormError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<SignupInput>({ resolver: zodResolver(signupSchema) });

  const onSubmit = handleSubmit(async (values) => {
    setFormError(null);
    try {
      // signup は自動ログイン (Cookie 発行) 済で MeResponse を返す。
      const me = await signup(values.userId, values.password);
      setMe(me);
      navigate(safeReturnTo(searchParams.get("returnTo")), { replace: true });
    } catch (e) {
      setFormError(authErrorMessage(e));
    }
  });

  return (
    <AuthCard title="新規登録">
      <form onSubmit={onSubmit} className="space-y-4" noValidate>
        {formError && <p className={formErrorClass}>{formError}</p>}
        <div>
          <label htmlFor="userId" className="mb-1 block text-sm">
            ID
          </label>
          <input
            id="userId"
            autoComplete="username"
            className={inputClass}
            {...register("userId")}
          />
          <p className="mt-1 text-xs text-gray-500">3〜12 文字 / 英字始まり・英数 - _</p>
          {errors.userId && <p className={fieldErrorClass}>{errors.userId.message}</p>}
        </div>
        <div>
          <label htmlFor="password" className="mb-1 block text-sm">
            パスワード
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
        <button type="submit" disabled={isSubmitting} className={buttonClass}>
          {isSubmitting ? "登録中..." : "登録してはじめる"}
        </button>
      </form>
      <p className="mt-4 text-sm">
        既にアカウントがある場合は{" "}
        <Link to="/login" className="text-blue-600 hover:underline">
          ログイン
        </Link>
      </p>
    </AuthCard>
  );
}
