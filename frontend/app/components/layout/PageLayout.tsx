import type { ReactNode } from "react";

import { Footer } from "~/components/layout/Footer";
import { Header } from "~/components/layout/Header";

/**
 * ホーム以外の画面で使う共通レイアウトシェル (Step 4)。
 * ブランドのダークテーマ (#222 地・白文字) + 共通ヘッダー (small バナー) + 共通フッターで構成する。
 * 見出しや本文は `children` 側で持つ (画面ごとに異なるため)。
 *
 * ホーム (`/`) は大きいトップ画像 (top.jpg) を route 内に直接持つため、このシェルは使わない。
 */
export function PageLayout({ children }: { children: ReactNode }) {
  return (
    // ページ地色は既存 `:8091` の body 背景 (#222) を全幅・全高で再現する。
    <div className="min-h-screen bg-wm-base text-xs text-white">
      {/* 既存 Bootstrap3 .container と同じレスポンシブ最大幅 (768→750 / 992→970 / 1200→1170)。 */}
      <div className="mx-auto w-full min-[768px]:max-w-[750px] min-[992px]:max-w-[970px] min-[1200px]:max-w-[1170px]">
        <Header />
        {children}
        <Footer />
      </div>
    </div>
  );
}
