import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate, useSearchParams } from "react-router";

import { login } from "~/features/auth/api";
import { authErrorMessage } from "~/features/auth/errorMessage";
import { safeReturnTo } from "~/features/auth/returnTo";
import { type LoginInput, loginSchema } from "~/features/auth/schema";
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
import { useSetMe } from "~/features/auth/useMe";
import { siteMeta } from "~/lib/meta";
import { zodResolver } from "~/lib/zodResolver";
import type { Route } from "./+types/login";

export function meta(_: Route.MetaArgs) {
  return siteMeta("ログイン");
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
    <AuthLayout title="ログイン">
      <form onSubmit={onSubmit} noValidate>
        {formError && <span className={formErrorClass}>{formError}</span>}
        <FormRow label="ユーザID" htmlFor="userId">
          <input
            id="userId"
            autoComplete="username"
            className={inputClass}
            {...register("userId")}
          />
          {errors.userId && <p className={fieldErrorClass}>{errors.userId.message}</p>}
        </FormRow>
        <FormRow label="パスワード" htmlFor="password">
          <input
            id="password"
            type="password"
            autoComplete="current-password"
            className={inputClass}
            {...register("password")}
          />
          {errors.password && <p className={fieldErrorClass}>{errors.password.message}</p>}
        </FormRow>
        <FormActions>
          <button type="submit" disabled={isSubmitting} className={buttonClass}>
            {isSubmitting ? "ログイン中..." : "ログイン"}
          </button>
        </FormActions>
      </form>
      <p className="mt-4">
        アカウントが無い場合は{" "}
        <Link to="/signup" className={linkClass}>
          新規登録
        </Link>
      </p>
    </AuthLayout>
  );
}
