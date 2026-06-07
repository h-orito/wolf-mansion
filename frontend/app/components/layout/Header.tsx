import { Link } from "react-router";

import { useMe } from "~/features/auth/useMe";
import { legacyUrl } from "~/lib/api";

/**
 * 共通ヘッダー (small バナー)。SSR `layout/header::header` のフル移植 (step-3.6)。
 *
 * トップ画像 (top-small.jpg) + WOLF MANSION ロゴ。ログイン中は右下にユーザID を出す (`useMe` 由来・CSR)。
 * 画像/ロゴフォントは移行中 backend (/wolf-mansion-api) 配信の静的アセットを参照する (root.tsx の favicon と同方針。
 * 静的アセット移管 (Step 10/11) で /wolf-mansion 側へ更新する)。
 *
 * ホーム (`/`) は大きいトップ画像 (top.jpg) を route 内に直接持つため、この共通ヘッダーは**ホーム以外**の
 * 画面 (認証画面など) で使う。
 */
export function Header() {
  const { me } = useMe();
  return (
    // 既存 `.top-image`: col の 15px padding で画像が container から横 15px インセット + 下 15px margin。
    <div className="mb-[15px] px-[15px]">
      <Link to="/" className="relative block">
        <img src={legacyUrl("/app/images/top-small.jpg")} alt="WOLF MANSION" className="w-full" />
        {/* ロゴは 1 行 (既存 header.html は home と違い改行なし・16px)。W/M を赤に。 */}
        <span className="font-anima absolute bottom-0 left-5 text-base text-white">
          <span className="text-wm-danger">W</span>OLF <span className="text-wm-danger">M</span>
          ANSION
        </span>
        {me && <span className="absolute right-5 bottom-0 text-white">ユーザID: {me.name}</span>}
      </Link>
    </div>
  );
}
