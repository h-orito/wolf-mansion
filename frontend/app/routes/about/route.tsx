import { Heading } from "~/components/ui/Heading";
import { ExternalLink, TextLink } from "~/components/ui/TextLink";
import { PageLayout } from "~/components/layout/PageLayout";
import { siteMeta } from "~/lib/meta";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("本サイトは");
}

const listStyle = "mb-[10.5px] list-disc pl-[20px]";

export default function About() {
  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <section>
          <Heading>本サイトは</Heading>
          <ul className={listStyle}>
            <li>
              人狼のルールに加え、特殊ルールである「人狼館の事件簿村」ルールを加えた人狼がプレイできるサイトです。
            </li>
            <li>
              通常の人狼をプレイしたことがない方は、
              <ExternalLink href="https://ninjinix.x0.com/wolfg/">人狼BBS</ExternalLink>
              などで人狼をプレイしてみてから本サイトでプレイすることを推奨します。
            </li>
          </ul>
        </section>

        <section>
          <Heading as="h2">人狼館の事件簿村とは</Heading>
          <ul className={listStyle}>
            <li>
              ふつつかものさんが考案されたルール（
              <ExternalLink href="http://chabieru.blog108.fc2.com/blog-entry-124.html">
                ブログ参照
              </ExternalLink>
              ）で、通常の人狼に特殊ルールが加わったものです。
            </li>
            <li>これまでGMが処理していたこの特殊ルール部分をシステム化したのが本サイトです。</li>
            <li>
              <TextLink to="/intro">人狼館の事件簿村</TextLink>ページでルールを解説しています。
            </li>
            <li>
              文章化したルールは<TextLink to="/rule">ルール</TextLink>に記載しています。
            </li>
          </ul>
        </section>

        <section>
          <Heading as="h2">注意事項</Heading>
          <ul className={listStyle}>
            <li>
              同村しているプレイヤーの画面の向こうにはあなたと同様人間がいます。他者を思いやり、迷惑をかけないプレイを心がけるようお願いします。
            </li>
            <li>
              正常な運営を妨げる行為がなされた場合、管理人の裁量によりアクセス禁止措置等がとられる場合があります。
            </li>
          </ul>
        </section>

        <section>
          <Heading as="h2">キャラチップについて</Heading>
          <ul className={listStyle}>
            <li>キャラチップの著作権は作者様にあります。</li>
            <li>
              スクリーンショットのSNSアップロードやキャラ画像のアイコン利用などについては著作権者である作者様の意向に従ってください。
              <br />
              （スクリーンショットもNGのチップもあります）
            </li>
            <li>
              「実装された国の規約に準ずる」とある場合は、村ログのスクリーンショットのSNSアップロードはOK、キャラ画像のみを利用するのはNGとします。
            </li>
            <li>
              デフォルトセットのキャラチップは
              <ExternalLink href="http://www.irasutoya.com/">いらすとや</ExternalLink>
              のものを使用させていただいています。
            </li>
          </ul>
        </section>

        <section id="original">
          <Heading as="h2">自分でキャラクターおよびキャラクター画像を用意する村について</Heading>
          <ul className={listStyle}>
            <li>パスワード付きの村でしかこの設定を利用することはできません。</li>
            <li>オリジナルキャラクターを登録してください。</li>
            <li>
              あなた自身が作成した、もしくは、あなたが依頼し、あなたのために作成された、このサイトでの使用を許可されている画像を使用してください。
              <ul className="list-[circle] pl-[20px]">
                <li>一般公開されている著作権フリー画像や、それを加工したものはNGとします。</li>
                <li>論理的に著作権の問題がないことを説明できるもののみ許可します。</li>
              </ul>
            </li>
            <li>法令や公序良俗に違反したり、運営を妨げるような内容で登録しないでください。</li>
            <li>
              アップロードされた画像は、当サイトの管理者が、サイトの紹介や宣伝目的で使用することがあることを了承ください。
              <ul className="list-[circle] pl-[20px]">
                <li>
                  例:{" "}
                  <ExternalLink href="https://twitter.com/ort_dev_notice/status/1490495820356993024">
                    第2回マンションオールスター村宣伝ツイート
                  </ExternalLink>
                </li>
              </ul>
            </li>
            <li>村を建てた方は、村内でこれらの規約が守られているか確認してください。</li>
            <li>
              問題のある画像を発見した場合、管理者に連絡してください。
              <ul className="list-[circle] pl-[20px]">
                <li>著作権の問題がないことが確認できない場合、画像を削除します。</li>
              </ul>
            </li>
            <li>
              画像は60x60pxで表示されるため、解像度は60x60や120x120など60の倍数の大きさとすることを推奨します。
            </li>
            <li>100kByteを超える画像はアップロードできません。</li>
          </ul>
        </section>

        <section>
          <Heading as="h2">タイトルロゴについて</Heading>
          <ul className={listStyle}>
            <li>
              <ExternalLink href="https://twitter.com/Lemon_orenoyome">
                @Lemon_orenoyome
              </ExternalLink>
              様の「Anima」フォントを使用させていただいています。
            </li>
          </ul>
        </section>
      </div>
    </PageLayout>
  );
}
