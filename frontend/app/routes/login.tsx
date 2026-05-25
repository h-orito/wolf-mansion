import { useState, type FormEvent } from "react";
import { redirect, useNavigate, useSearchParams } from "react-router";
import type { Route } from "./+types/login";
import { InvalidCredentialsError, type MeResponse } from "~/features/auth/api";
import { useLoginMutation } from "~/features/auth/hooks";
import { ssrFetch } from "~/lib/api/client";
import { sanitizeRedirect } from "~/lib/redirect";
import { PageHeader } from "~/components/layout/PageHeader";
import { Button } from "~/components/ui/Button";
import { Input, Label } from "~/components/ui/Input";

export function meta(_: Route.MetaArgs) {
  return [{ title: "ログイン | WOLF MANSION" }];
}

/** 既にログイン済みなら ?redirect=... or / にリダイレクト */
export async function loader({ request }: Route.LoaderArgs) {
  const api = ssrFetch(request);
  const res = await api("/api/v1/auth/me");
  if (res.ok) {
    const data = (await res.json()) as MeResponse;
    if (data.user) {
      const to = sanitizeRedirect(new URL(request.url).searchParams.get("redirect"));
      throw redirect(to);
    }
  }
  return null;
}

/**
 * 旧 templates/login.html を React で復元。
 *
 *   <h1 class="h4">ログイン</h1>
 *   <form class="form-horizontal">
 *     <div class="form-group">
 *       <label class="col-sm-2 col-xs-4 control-label">ユーザID</label>
 *       <div class="col-sm-10 col-xs-8"><input class="form-control" /></div>
 *     </div>
 *     ... 同様にパスワード
 *     <input type="submit" class="btn btn-sm btn-success pull-right" value="ログイン" />
 *   </form>
 */
export default function LoginPage() {
  const navigate = useNavigate();
  const [params] = useSearchParams();
  const loginMutation = useLoginMutation();
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");

  const redirectTo = sanitizeRedirect(params.get("redirect"));

  async function onSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    try {
      await loginMutation.mutateAsync({ userId, password });
      navigate(redirectTo, { replace: true });
    } catch {
      // mutation error は下で error として表示
    }
  }

  const errorMessage = loginMutation.error
    ? loginMutation.error instanceof InvalidCredentialsError
      ? "ID またはパスワードが違います"
      : "ログインに失敗しました。しばらく経ってから再試行してください"
    : null;

  return (
    <main className="max-w-screen-lg mx-auto">
      <PageHeader />
      <div className="px-3 max-w-[40em]">
        <h1 className="text-[1.5em] font-medium mb-3">ログイン</h1>
        {errorMessage && (
          <p role="alert" className="text-blood-500 mb-2">
            {errorMessage}
          </p>
        )}
        <form onSubmit={onSubmit}>
          <FormRow label="ユーザID" htmlFor="userId">
            <Input
              id="userId"
              name="userId"
              type="text"
              required
              autoComplete="username"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
            />
          </FormRow>
          <FormRow label="パスワード" htmlFor="password">
            <Input
              id="password"
              name="password"
              type="password"
              required
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </FormRow>
          <div className="flex justify-end mt-3">
            <Button
              type="submit"
              variant="success"
              disabled={loginMutation.isPending}
            >
              {loginMutation.isPending ? "ログイン中..." : "ログイン"}
            </Button>
          </div>
        </form>
      </div>
    </main>
  );
}

/**
 * 旧 .form-horizontal の form-group 1 行相当。
 *   label col-sm-2 col-xs-4 (右寄せ)
 *   input col-sm-10 col-xs-8
 */
function FormRow({
  label,
  htmlFor,
  children,
}: {
  label: string;
  htmlFor: string;
  children: React.ReactNode;
}) {
  return (
    <div className="flex flex-wrap items-center mb-2 gap-x-2">
      <Label htmlFor={htmlFor} className="w-[7em] text-right pr-2 shrink-0">
        {label}
      </Label>
      <div className="flex-1 min-w-0">{children}</div>
    </div>
  );
}
