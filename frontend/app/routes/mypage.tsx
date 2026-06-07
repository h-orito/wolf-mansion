import { useState } from "react";
import { Link, useNavigate } from "react-router";

import { logout } from "~/features/auth/api";
import { RequireAuth } from "~/features/auth/RequireAuth";
import { AuthLayout, buttonClass, formErrorClass, linkClass } from "~/features/auth/ui";
import { useInvalidateMe, useMe } from "~/features/auth/useMe";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/mypage";

export function meta(_: Route.MetaArgs) {
  return siteMeta("マイページ");
}

function MyPageContent() {
  const { me } = useMe();
  const navigate = useNavigate();
  const invalidateMe = useInvalidateMe();
  const [loggingOut, setLoggingOut] = useState(false);
  const [logoutError, setLogoutError] = useState<string | null>(null);

  const onLogout = async () => {
    setLoggingOut(true);
    setLogoutError(null);
    try {
      await logout();
      // 先に home へ遷移してから me を無効化する。この画面は RequireAuth 配下なので、
      // 遷移前に me を null にすると RequireAuth が /login へリダイレクトしてしまい、
      // navigate("/") と競合してナビゲーションが揺れる。cache は null にせず invalidate し、
      // 遷移後の再取得 (401→null) で未ログインへ収束させる (refetch 中も旧データを保持するため
      // RequireAuth は誤発火しない)。
      navigate("/", { replace: true });
      await invalidateMe();
    } catch {
      // ログアウト失敗時はセッション (Cookie) がまだ生きている可能性がある。
      // 楽観的に未ログイン扱いにせず、me を取り直してサーバ実態に合わせ、エラーを表示する。
      await invalidateMe();
      setLogoutError("ログアウトに失敗しました。時間をおいて再試行してください。");
    } finally {
      setLoggingOut(false);
    }
  };

  return (
    // `:8091` に対応する画面はないため、認証画面と同じダークテーマ + 共通ヘッダー/フッターで構成する。
    <AuthLayout title="マイページ">
      <dl className="mb-6 space-y-2">
        <div className="flex gap-2">
          <dt className="w-28 text-gray-400">プレイヤーID</dt>
          <dd>{me?.playerId}</dd>
        </div>
        <div className="flex gap-2">
          <dt className="w-28 text-gray-400">名前</dt>
          <dd>{me?.name}</dd>
        </div>
        <div className="flex gap-2">
          <dt className="w-28 text-gray-400">権限</dt>
          <dd>{me?.authorities.join(", ") || "-"}</dd>
        </div>
      </dl>
      <div className="space-y-3">
        {logoutError && <span className={formErrorClass}>{logoutError}</span>}
        <p>
          <Link to="/change-password" className={linkClass}>
            パスワードを変更する
          </Link>
        </p>
        <button type="button" onClick={onLogout} disabled={loggingOut} className={buttonClass}>
          {loggingOut ? "ログアウト中..." : "ログアウト"}
        </button>
      </div>
    </AuthLayout>
  );
}

export default function MyPage() {
  return (
    <RequireAuth>
      <MyPageContent />
    </RequireAuth>
  );
}
