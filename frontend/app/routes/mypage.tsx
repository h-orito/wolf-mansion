import { useState } from "react";
import { Link, useNavigate } from "react-router";

import { logout } from "~/features/auth/api";
import { RequireAuth } from "~/features/auth/RequireAuth";
import { useMe, useSetMe } from "~/features/auth/useMe";
import { buttonClass } from "~/features/auth/ui";
import type { Route } from "./+types/mypage";

export function meta(_: Route.MetaArgs) {
  return [{ title: "マイページ | 人狼の館" }];
}

function MyPageContent() {
  const { me } = useMe();
  const navigate = useNavigate();
  const setMe = useSetMe();
  const [loggingOut, setLoggingOut] = useState(false);

  const onLogout = async () => {
    setLoggingOut(true);
    try {
      await logout();
    } finally {
      // logout API が失敗しても UI 上はログアウト扱いにする (Cookie 失効はベストエフォート)。
      setMe(null);
      navigate("/", { replace: true });
    }
  };

  return (
    <main className="mx-auto max-w-sm p-6">
      <h1 className="mb-6 text-xl font-bold">マイページ</h1>
      <dl className="mb-6 space-y-2 text-sm">
        <div className="flex gap-2">
          <dt className="w-28 text-gray-500">プレイヤーID</dt>
          <dd>{me?.playerId}</dd>
        </div>
        <div className="flex gap-2">
          <dt className="w-28 text-gray-500">名前</dt>
          <dd>{me?.name}</dd>
        </div>
        <div className="flex gap-2">
          <dt className="w-28 text-gray-500">権限</dt>
          <dd>{me?.authorities.join(", ") || "-"}</dd>
        </div>
      </dl>
      <div className="space-y-3">
        <Link to="/change-password" className="block text-sm text-blue-600 hover:underline">
          パスワードを変更する
        </Link>
        <button type="button" onClick={onLogout} disabled={loggingOut} className={buttonClass}>
          {loggingOut ? "ログアウト中..." : "ログアウト"}
        </button>
      </div>
    </main>
  );
}

export default function MyPage() {
  return (
    <RequireAuth>
      <MyPageContent />
    </RequireAuth>
  );
}
