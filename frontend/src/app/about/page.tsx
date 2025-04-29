import Breadcrumb from '@/components/ui/Breadcrumb'
import {
  faCircleExclamation,
  faCircleInfo,
  faImage,
  faUserPen
} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Link from 'next/link'

export default function AboutPage() {
  return (
    <div className='mt-8'>
      <Breadcrumb items={[{ label: '本サイトは' }]} />
      <div className='content-section'>
        <h1 className='section-title'>
          <FontAwesomeIcon
            icon={faCircleInfo}
            className='text-primary h-5 w-5'
          />
          本サイトは
        </h1>
        <ul className='list-content'>
          <li>
            人狼のルールに加え、特殊ルールである「人狼館の事件簿村」ルールを加えた人狼がプレイできるサイトです。
          </li>
          <li>
            通常の人狼をプレイしたことがない方は、
            <a
              href='https://ninjinix.x0.com/wolfg/'
              target='_blank'
              rel='noopener noreferrer'
              className='link-style'
            >
              人狼BBS
            </a>
            などで人狼をプレイしてみてから本サイトでプレイすることを推奨します。
          </li>
        </ul>
      </div>

      <div className='content-section'>
        <h2 className='section-title'>
          <FontAwesomeIcon
            icon={faCircleInfo}
            className='text-primary h-5 w-5'
          />
          人狼館の事件簿村とは
        </h2>
        <ul className='list-content'>
          <li>
            ふつつかものさんが考案されたルール（
            <a
              href='http://chabieru.blog108.fc2.com/blog-entry-124.html'
              target='_blank'
              rel='noopener noreferrer'
              className='link-style'
            >
              ブログ参照
            </a>
            ）で、通常の人狼に特殊ルールが加わったものです。
          </li>
          <li>
            これまでGMが処理していたこの特殊ルール部分をシステム化したのが本サイトです。
          </li>
          <li>
            <Link href='/intro' className='link-style'>
              人狼館の事件簿村
            </Link>
            ページでルールを解説しています。
          </li>
          <li>
            文章化したルールは
            <Link href='/rule' className='link-style'>
              ルール
            </Link>
            に記載しています。
          </li>
        </ul>
      </div>

      <div className='content-section'>
        <h2 className='section-title'>
          <FontAwesomeIcon
            icon={faCircleExclamation}
            className='text-warning h-5 w-5'
          />
          注意事項
        </h2>
        <ul className='list-content'>
          <li>
            同村しているプレイヤーの画面の向こうにはあなたと同様人間がいます。他者を思いやり、迷惑をかけないプレイを心がけるようお願いします。
          </li>
          <li>
            正常な運営を妨げる行為がなされた場合、管理人の裁量によりアクセス禁止措置等がとられる場合があります。
          </li>
        </ul>
      </div>

      <div className='content-section'>
        <h2 className='section-title'>
          <FontAwesomeIcon icon={faImage} className='h-5 w-5 text-blue-500' />
          キャラチップについて
        </h2>
        <ul className='list-content'>
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
            <a
              href='http://www.irasutoya.com/'
              target='_blank'
              rel='noopener noreferrer'
              className='link-style'
            >
              いらすとや
            </a>
            のものを使用させていただいています。
          </li>
        </ul>
      </div>

      <div className='content-section'>
        <h2 className='section-title'>
          <FontAwesomeIcon
            icon={faUserPen}
            className='h-5 w-5 text-purple-500'
          />
          自分でキャラクターおよびキャラクター画像を用意する村について
        </h2>
        <ul className='list-content'>
          <li>パスワード付きの村でしかこの設定を利用することはできません。</li>
          <li>オリジナルキャラクターを登録してください。</li>
          <li>
            あなた自身が作成した、もしくは、あなたが依頼し、あなたのために作成された、このサイトでの使用を許可されている画像を使用してください。
            <ul className='list-content-nested'>
              <li>
                一般公開されている著作権フリー画像や、それを加工したものはNGとします。
              </li>
              <li>
                論理的に著作権の問題がないことを説明できるもののみ許可します。
              </li>
            </ul>
          </li>
          <li>
            法令や公序良俗に違反したり、運営を妨げるような内容で登録しないでください。
          </li>
          <li>
            アップロードされた画像は、当サイトの管理者が、サイトの紹介や宣伝目的で使用することがあることを了承ください。
            <ul className='list-content-nested'>
              <li>
                例:{' '}
                <a
                  href='https://twitter.com/ort_dev_notice/status/1490495820356993024'
                  target='_blank'
                  rel='noopener noreferrer'
                  className='link-style'
                >
                  第2回マンションオールスター村宣伝ツイート
                </a>
              </li>
            </ul>
          </li>
          <li>
            村を建てた方は、村内でこれらの規約が守られているか確認してください。
          </li>
          <li>
            問題のある画像を発見した場合、管理者に連絡してください。
            <ul className='list-content-nested'>
              <li>
                著作権の問題がないことが確認できない場合、画像を削除します。
              </li>
            </ul>
          </li>
          <li>
            画像は60x60pxで表示されるため、解像度は60x60や120x120など60の倍数の大きさとすることを推奨します。
          </li>
          <li>100kByteを超える画像はアップロードできません。</li>
        </ul>
      </div>
    </div>
  )
}
