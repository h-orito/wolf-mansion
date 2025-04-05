import { FC } from 'react'

const Announce2019: FC = () => {
  return (
    <>
      <li>
        2019/11/30 以下を実装しました。
        <ul className='list-content-nested'>
          <li>罠師、爆弾魔の説明がわかりにくかったので修正</li>
          <li>役職「パン屋」「占星術師」「検死官」を追加</li>
          <li>朝の足音の表示順をランダムから番号が若い順に変更</li>
          <li>発言の名前欄に部屋番号を表示するように変更</li>
          <li>
            状況欄の部屋割りの名前をタップするとフルネームを表示するよう変更
          </li>
          <li>人狼は他の人がセットした襲撃履歴も見られるよう変更</li>
        </ul>
      </li>
      <li>
        2019/11/15 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「呪狼」「智狼」を追加</li>
          <li>役職向けシステムメッセージの表示色を若干変更</li>
        </ul>
      </li>
      <li>
        2019/11/14 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「罠師」「爆弾魔」を追加</li>
        </ul>
      </li>
      <li>
        2019/10/28 以下を実装しました。
        <ul className='list-content-nested'>
          <li>村の定員を最多20名から最多99名に変更</li>
          <li>
            村作成時、最少開始人数〜定員に含まれない構成は入力しなくても良いように変更
          </li>
          <li>
            占い師系、狂人系、探偵、妖狐など、構成に1名までしか含められなかった役職を複数含められるよう変更
          </li>
          <li>その他色々と表示改善</li>
        </ul>
      </li>
      <li>
        2019/07/16 以下を行いました。
        <ul className='list-content-nested'>
          <li>キャラチップ「城下町の酒場」のキャラを追加</li>
          <li>キャラチップ「closure」を追加（コラボ分は未実装）</li>
          <li>キャラチップ「Mad Party」を追加（コラボ分は未実装）</li>
        </ul>
      </li>
      <li>
        2019/07/10 以下を行いました。
        <ul className='list-content-nested'>
          <li>突然死ありの村で未投票の場合画面下部に警告表示するように変更</li>
        </ul>
      </li>
      <li>
        2019/07/03 以下を実装しました。
        <ul className='list-content-nested'>
          <li>ツイッターシェアボタン</li>
        </ul>
      </li>
      <li>
        2019/06/19 以下を行いました。
        <ul className='list-content-nested'>
          <li>キャラチップ「ぐれすけ・ぷらす」を追加</li>
        </ul>
      </li>
      <li>
        2019/05/27 以下の不具合を修正しました。
        <ul className='list-content-nested'>
          <li>発言制限ありの村で文字数がオーバーしていないのに発言できない</li>
        </ul>
      </li>
      <li>
        2019/03/03 以下を行いました。
        <ul className='list-content-nested'>
          <li>キャラチップ「歳時抄」の赤青差分を追加</li>
        </ul>
      </li>
      <li>
        2019/02/23 以下を行いました。
        <ul className='list-content-nested'>
          <li>キャラチップ「かくりよ」を追加</li>
          <li>キャラチップ「曲芸会hello！」を追加</li>
          <li>キャラチップ「少し大きな霧雨降る街」を追加</li>
          <li>キャラチップ「班帝家の一族」を追加</li>
        </ul>
      </li>
      <li>
        2019/02/13 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職: 探偵（WOLF MANSIONオリジナル）を追加</li>
        </ul>
      </li>
    </>
  )
}

export default Announce2019
