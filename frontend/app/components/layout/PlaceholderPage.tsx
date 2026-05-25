import { LinkButton } from "~/components/ui/Button";
import { Panel, PanelBody } from "~/components/ui/Panel";
import { PageHeader } from "~/components/layout/PageHeader";
import { PageFooter } from "~/components/layout/PageFooter";

/**
 * 旧 Thymeleaf 版に存在し React 側に未移植のページのスタブ。
 * about / intro / announce / rule / faq / new-player など、home からリンク
 * されるが React Route 未実装のページで利用する。
 *
 * Step 13e の最終仕上げで実コンテンツに置き換える。
 */
export function PlaceholderPage({
  title,
  englishTitle,
  description,
}: {
  title: string;
  englishTitle?: string;
  description: React.ReactNode;
}) {
  return (
    <>
      <PageHeader />
      <div className="px-3">
        <h1 className="text-[1.5em] font-medium mb-1">{title}</h1>
        {englishTitle && (
          <p className="opacity-70 mb-3 text-[0.95em]">{englishTitle}</p>
        )}
        <Panel className="mb-3">
          <PanelBody>
            <p className="mb-2">{description}</p>
            <p className="text-warning-500 mb-3">
              このページは React 移植の最終仕上げ (Step 13e) でコンテンツが
              追加される予定です。準備中。
            </p>
            <LinkButton to="/" variant="dark-success">
              トップに戻る
            </LinkButton>
          </PanelBody>
        </Panel>
      </div>
      <PageFooter />
    </>
  );
}
