import type { ReactNode } from "react";

import { Footer } from "~/components/layout/Footer";
import { Header } from "~/components/layout/Header";
import { RenewalBanner } from "~/components/layout/RenewalBanner";

/**
 * ホーム以外の画面で使う共通レイアウトシェル。ダーク地 + 共通ヘッダー (バナー) + 共通フッター +
 * レスポンシブなコンテナ幅で構成する。見出しや本文は `children` 側で持つ。
 * バナーを出さないページ (エイプリル企画アーカイブなど) は `header={false}` を指定する。
 *
 * ホーム (`/`) は大きいバナー画像を route 内に直接持つため、このシェルは使わない。
 */
export function PageLayout({
  children,
  header = true,
  noAd = false,
  footerPaddingBottom,
}: {
  children: ReactNode;
  header?: boolean;
  /** R18 村など広告を出さないページで true。 */
  noAd?: boolean;
  /** fixed フッターメニュー等で共通 Footer が隠れる場合の下部余白 (px)。 */
  footerPaddingBottom?: number;
}) {
  return (
    <div className="min-h-screen bg-wm-base text-xs text-white">
      <div className="mx-auto w-full min-[768px]:max-w-[750px] min-[992px]:max-w-[970px] min-[1200px]:max-w-[1170px]">
        {header && <Header />}
        <RenewalBanner />
        {children}
        <Footer noAd={noAd} />
        {footerPaddingBottom != null && <div style={{ height: footerPaddingBottom }} />}
      </div>
    </div>
  );
}
