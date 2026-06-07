import { Link } from "react-router";

import { useMe } from "~/features/auth/useMe";
import { assetUrl } from "~/lib/api";

/**
 * 共通ヘッダー (small バナー)。トップ画像 (top-small.jpg) + WOLF MANSION ロゴ。
 * ログイン中は右下にユーザID を出す (`useMe` 由来・CSR)。
 *
 * ホーム (`/`) は大きいトップ画像 (top.jpg) を route 内に直接持つため、この共通ヘッダーは
 * ホーム以外の画面 (認証画面など) で使う。
 */
export function Header() {
  const { me } = useMe();
  return (
    // `.top-image`: col の 15px padding で画像が container から横 15px インセット + 下 15px margin。
    <div className="mb-[15px] px-[15px]">
      <Link to="/" className="relative block">
        <img src={assetUrl("/app/images/top-small.jpg")} alt="WOLF MANSION" className="w-full" />
        {/* ロゴは 1 行 (16px)。W/M を赤に。 */}
        <span className="font-anima absolute bottom-0 left-5 text-base text-white">
          <span className="text-wm-danger">W</span>OLF <span className="text-wm-danger">M</span>
          ANSION
        </span>
        {me && <span className="absolute right-5 bottom-0 text-white">ユーザID: {me.name}</span>}
      </Link>
    </div>
  );
}
