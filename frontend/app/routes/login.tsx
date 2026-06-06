import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate, useSearchParams } from "react-router";

import { login } from "~/features/auth/api";
import { authErrorMessage } from "~/features/auth/errorMessage";
import { safeReturnTo } from "~/features/auth/returnTo";
import { type LoginInput, loginSchema } from "~/features/auth/schema";
import { useSetMe } from "~/features/auth/useMe";
import {
  AuthCard,
  buttonClass,
  fieldErrorClass,
  formErrorClass,
  inputClass,
} from "~/features/auth/ui";
import { zodResolver } from "~/lib/zodResolver";
import type { Route } from "./+types/login";

export function meta(_: Route.MetaArgs) {
  return [{ title: "ログイン | 人狼の館" }];
}

export default function Login() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const setMe = useSetMe();
  const [formError, setFormError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<LoginInput>({ resolver: zodResolver(loginSchema) });

  const onSubmit = handleSubmit(async (values) => {
    setFormError(null);
    try {
      const me = await login(values.userId, values.password);
      setMe(me);
      navigate(safeReturnTo(searchParams.get("returnTo")), { replace: true });
    } catch (e) {
      setFormError(authErrorMessage(e));
    }
  });

  return (
    <AuthCard title="ログイン">
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
          {errors.userId && <p className={fieldErrorClass}>{errors.userId.message}</p>}
        </div>
        <div>
          <label htmlFor="password" className="mb-1 block text-sm">
            パスワード
          </label>
          <input
            id="password"
            type="password"
            autoComplete="current-password"
            className={inputClass}
            {...register("password")}
          />
          {errors.password && <p className={fieldErrorClass}>{errors.password.message}</p>}
        </div>
        <button type="submit" disabled={isSubmitting} className={buttonClass}>
          {isSubmitting ? "ログイン中..." : "ログイン"}
        </button>
      </form>
      <p className="mt-4 text-sm">
        アカウントが無い場合は{" "}
        <Link to="/signup" className="text-blue-600 hover:underline">
          新規登録
        </Link>
      </p>
    </AuthCard>
  );
}
