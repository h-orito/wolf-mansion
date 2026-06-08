import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate, useSearchParams } from "react-router";

import {
  PASSWORD_MAX_LENGTH,
  PASSWORD_MIN_LENGTH,
  SIGNUP_USER_ID_MAX_LENGTH,
  SIGNUP_USER_ID_MIN_LENGTH,
} from "~/api/constants";
import { signup } from "~/features/auth/api";
import { authErrorMessage } from "~/features/auth/errorMessage";
import { safeReturnTo } from "~/features/auth/returnTo";
import { type SignupInput, signupSchema } from "~/features/auth/schema";
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
import type { Route } from "./+types/signup";

export function meta(_: Route.MetaArgs) {
  // 見出し (AuthLayout title) と揃える。`:8091` は title=「ID登録」/ 見出し=「ID作成」で割れているが、
  // 画面内で文言を統一する方を優先する。
  return siteMeta("ID作成");
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
    <AuthLayout title="ID作成">
      {/* 既存 new-player.html の説明文。文字数等は実ポリシー (生成定数) に合わせる (緩和後: パスワードは最大60字)。 */}
      <p className="mb-4">
        使用したいIDとパスワードを入力してください。
        <br />
        IDは{SIGNUP_USER_ID_MIN_LENGTH}〜{SIGNUP_USER_ID_MAX_LENGTH}
        文字で、1文字目は英字、以降は英数字・ハイフン・アンダーバーが使用可能です。
        <br />
        パスワードは{PASSWORD_MIN_LENGTH}〜{PASSWORD_MAX_LENGTH}文字の半角英数記号が使用可能です。
      </p>
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
            autoComplete="new-password"
            className={inputClass}
            {...register("password")}
          />
          {errors.password && <p className={fieldErrorClass}>{errors.password.message}</p>}
        </FormRow>
        <FormActions>
          <button type="submit" disabled={isSubmitting} className={buttonClass}>
            {isSubmitting ? "作成中..." : "作成"}
          </button>
        </FormActions>
      </form>
      <p className="mt-4">
        既にアカウントがある場合は{" "}
        <Link to="/login" className={linkClass}>
          ログイン
        </Link>
      </p>
    </AuthLayout>
  );
}
