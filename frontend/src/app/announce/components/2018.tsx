import Link from 'next/link'
import { FC } from 'react'

const Announce2018: FC = () => {
  return (
    <>
      <li>
        2018/11/11 以下を実装しました。
        <ul className='list-content-nested'>
          <li>H)SOCIUS(Aとジランドールの差分画像を追加</li>
          <li>村建て画面のUI変更</li>
          <li>村建て時のダミーキャラ任意選択</li>
          <li>発言時の表情（差分画像）選択</li>
          <li>FAQ、ルール追記</li>
          <li>発言確認を同一画面で行うよう変更</li>
        </ul>
      </li>
      <li>
        2018/10/23 以下を実装しました。
        <ul className='list-content-nested'>
          <li>村建てオプション：発言制限</li>
          <li>村建て発言へのアンカー</li>
          <li>1日目に役職アナウンスを表示</li>
        </ul>
      </li>
      <li>
        2018/9/24 以下を実装しました。
        <ul className='list-content-nested'>
          <li>第二役職希望</li>
          <li>
            構成が人数に応じて変更される場合、村人、霊能、狂人系、占い師系の役職希望を自動変更
          </li>
          <li>役職希望におまかせ系の希望を追加</li>
        </ul>
      </li>
      <li>
        2018/9/10 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職：狂信者を追加</li>
          <li>
            プロローグ中、参加者なしで更新日時を迎えた場合、廃村となるように変更
          </li>
          <li>
            エピローグを迎えていない村に参加中のプレイヤーは新しい村を建てられないように変更
          </li>
        </ul>
      </li>
      <li>
        2018/9/9 以下を行いました。
        <ul className='list-content-nested'>
          <li>キャラチップ「大神学園」の画像を更新、キャラ追加</li>
        </ul>
      </li>
      <li>
        2018/8/3 以下を実装しました。
        <ul className='list-content-nested'>
          <li>村建てオプション：秘話機能</li>
          <li>ユーザ定義ランダム発言（ランダム変換を自分で作れる機能）</li>
          <li>
            エピローグは固定で24hに変更（変更前：更新間隔＋48h。24h更新の場合72h）
          </li>
        </ul>
      </li>
      <li>
        2018/7/28 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職の能力セット履歴（能力欄）を表示</li>
          <li>30秒ごとに更新チェック、更新があったら更新ボタンが点滅</li>
          <li>自動更新（更新があったら自動で読み込み）</li>
          <li>個人戦績ページ</li>
          <li>50x77以外のキャラチップ画像を表示できるように変更</li>
          <li>キャラチップ「歳時抄」の画像を更新、キャラ追加</li>
          <li>インターフェース確認用の村</li>
          <li>ネタバレ防止機能（発言抽出欄）</li>
        </ul>
      </li>
      <li>
        2018/6/26 以下を行いました。
        <ul className='list-content-nested'>
          <li>人狼館の事件簿村ルール紹介ページを追加</li>
          <li>サイト名をWOLF MANSIONに決定</li>
        </ul>
      </li>
      <li>
        2018/6/12 以下を実装しました。
        <ul className='list-content-nested'>
          <li>村名を40字まで設定できるように変更（旧：20字）</li>
          <li>村建て専用機能：廃村（プロローグ時のみ）</li>
          <li>村建て専用機能：エピローグ延長（エピローグ時のみ）</li>
        </ul>
      </li>
      <li>
        2018/6/10 以下を実装しました。
        <ul className='list-content-nested'>
          <li>村建てオプション：コミット機能</li>
          <li>村建てオプション：狩人による同一対象の連続護衛不可</li>
          <li>更新までの時間を表示</li>
          <li>文字装飾：打ち消し線（ルール - その他を参照）</li>
        </ul>
      </li>
      <li>
        2018/6/6 以下を実装しました。
        <ul className='list-content-nested'>
          <li>人狼、共鳴者は死亡後も役職窓を見られるように変更</li>
          <li>文字装飾機能（ルール - その他を参照）</li>
          <li>
            発言可能な文字数を変更（200→400文字、10行→20行）（村建て発言は1000文字40行）
          </li>
          <li>
            ページ分割について、1ページあたりに表示する発言数を選択できるように
          </li>
        </ul>
      </li>
      <li>
        2018/05/23 以下を実装しました。
        <ul className='list-content-nested'>
          <li>国主呼び出し機能（ルール - その他を参照）</li>
          <li>
            同一人狼による連続襲撃について、1日目遷移時に編成が2狼以下になる場合、自動で「可能」に変更
          </li>
        </ul>
      </li>
      <li>
        2018/05/10 以下を実装しました。
        <ul className='list-content-nested'>
          <li>ページ分割機能</li>
          <li>プロローグで連続24時間アクセスしていないと自動で村を去る機能</li>
          <li>村建てオプション：突然死機能</li>
          <li>村建て専用機能（キック、村建て発言）</li>
        </ul>
      </li>
      <li>
        2018/04/24 以下を実装しました。
        <ul className='list-content-nested'>
          <li>
            発言ランダム機能（&#091;&#091;XdY&#093;&#093;、&#091;&#091;fortune&#093;&#093;、&#091;&#091;AorB&#093;&#093;、&#091;&#091;who&#093;&#093;、&#091;&#091;allwho&#093;&#093;）
          </li>
        </ul>
      </li>
      <li>
        2018/04/21 以下の不具合を修正しました。
        <ul className='list-content-nested'>
          <li>エピったときに見学者が「●●、△△だった」の一覧に出てこない</li>
        </ul>
      </li>
      <li>
        2018/04/21 以下を実装しました。
        <ul className='list-content-nested'>
          <li>村建てオプション：同一人狼が連続襲撃不可</li>
          <li>村建てオプション：役職構成変更</li>
          <li>村建てオプション：墓下役職公開</li>
          <li>村建てオプション：墓下見学と生存者の会話可否</li>
          <li>独り言へのアンカーを打てるように（エピローグ後限定）</li>
          <li>発言に発言者のIDを表示（エピローグ後限定）</li>
        </ul>
      </li>
      <li>
        2018/04/02 テストプレイ中です。以下は現在未実装もしくは未実施です。
        <ul className='list-content-nested'>
          <li>村建てオプション：同一人狼が連続襲撃不可、発言数制限</li>
          <li>個人戦績</li>
          <li>
            キャラチップアップロード（提供いただける方はTwitterにてご連絡ください）
          </li>
        </ul>
      </li>
      <li>
        2018/03/25 じっぷ様にキャラチップ「
        <Link href='/wolf-mansion/chara-group/2' className='link-style'>
          大神学園
        </Link>
        」を提供していただきました。
      </li>
    </>
  )
}

export default Announce2018
