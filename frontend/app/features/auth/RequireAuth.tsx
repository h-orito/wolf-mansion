import type { ReactNode } from "react";
import { Navigate, useLocation } from "react-router";

import { useMe } from "./useMe";

/**
 * 認証ガード (Step 3.3)。保護ルートを children に包んで使う。
 * - 判定確定前 (SSR / me 取得中): プレースホルダを表示
 * - 未ログイン: `/login?returnTo=<元のパス>` へリダイレクト (replace)
 * - ログイン済: children を描画
 */
export function RequireAuth({ children }: { children: ReactNode }) {
  const { me, isLoading } = useMe();
  const location = useLocation();

  if (isLoading) {
    return <p className="p-4 text-gray-500">読み込み中...</p>;
  }

  if (me == null) {
    const returnTo = encodeURIComponent(`${location.pathname}${location.search}`);
    return <Navigate to={`/login?returnTo=${returnTo}`} replace />;
  }

  return <>{children}</>;
}
