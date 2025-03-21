import { FC } from 'react'

const Announce2024: FC = () => {
  return (
    <>
      <li>
        2024/12/27 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「情緒」を追加</li>
        </ul>
      </li>
      <li>
        2024/10/22 以下を変更しました。
        <ul className='list-content-nested'>
          <li>暴走トラック仕様変更（襲撃耐性を追加）</li>
        </ul>
      </li>
      <li>
        2024/10/20 以下を変更しました。
        <ul className='list-content-nested'>
          <li>村設定でダミーキャラ名、略称、1日目を設定できるよう機能追加</li>
          <li>入村時にキャラ名、略称を設定できるよう機能追加</li>
          <li>
            バー狼仕様変更（元のメッセージを独り言で自動投下、10%の確率で変換なし）
          </li>
        </ul>
      </li>
      <li>
        2024/10/13 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「バー狼」「喰狼」を追加</li>
        </ul>
      </li>
      <li>
        2024/10/08 以下を変更しました。
        <ul className='list-content-nested'>
          <li>Discord通知に当サイトの画像が表示されないよう調整</li>
          <li>Discord通知から飛んだ発言でアンカー先が見られない不具合修正</li>
          <li>指揮官系のメッセージに「#CO発言」を追加</li>
          <li>
            村切り抜き画面を追加（村画面下部のトップページリンクの隣から遷移）
          </li>
        </ul>
      </li>
      <li>
        2024/10/06 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            発言装飾に&#091;&#091;netabare&#093;&#093;&#091;&#091;cw&#093;&#093;&#091;&#091;tp&#093;&#093;を追加(netabareとcwは同じ効果)
          </li>
        </ul>
      </li>
      <li>
        2024/09/29 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            ランダムキーワード検索で変換後の文字列も検索対象となるよう変更
          </li>
          <li>発言ランダム機能に&#091;&#091;gwho&#093;&#093;を追加</li>
          <li>最終ログイン日時の更新や日付更新の頻度を上昇</li>
        </ul>
      </li>
      <li>
        2024/09/27 以下を変更しました。
        <ul className='list-content-nested'>
          <li>エピローグの発言は発言変換/装飾系能力の影響を受けないよう修正</li>
        </ul>
      </li>
      <li>
        2024/09/21 以下を変更しました。
        <ul className='list-content-nested'>
          <li>Discord通知メッセージに通知元となった発言のリンクを追加</li>
        </ul>
      </li>
      <li>
        2024/09/19 以下を変更しました。
        <ul className='list-content-nested'>
          <li>参加見学切り替え機能を追加</li>
          <li>
            エピローグ前同等の表示機能で参加者のユーザーIDも非表示にするよう変更
          </li>
        </ul>
      </li>
      <li>
        2024/09/18 以下を変更しました。
        <ul className='list-content-nested'>
          <li>名前変更、簡易メモ変更に文字数カウントを追加</li>
          <li>発言抽出ショートカット機能を追加</li>
        </ul>
      </li>
      <li>
        2024/09/17 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            発言抽出をCookie保存でなくURLで保存する方式に変更（これによりタブごとに別々の抽出が可能になります）
          </li>
          <li>発言抽出に別タブ表示機能を追加</li>
        </ul>
      </li>
      <li>
        2024/05/12 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            前日まで別の役職で、転生で冤罪者になった場合、冤罪者になった当日は足音が発生しないよう仕様変更
          </li>
          <li>不具合修正</li>
        </ul>
      </li>
      <li>
        2024/04/10 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「ちくわ大明神」を追加</li>
        </ul>
      </li>
      <li>
        2024/04/05 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            闇鍋編成で初期編成の配分と転生の配分を別々に設定できるよう機能追加
          </li>
        </ul>
      </li>
      <li>
        2024/03/25 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            勇者や絶対人狼など登場時点で公開される役職を処刑した場合、追加で1名処刑されるよう変更
          </li>
          <li>ルールに処刑の詳細処理を追記</li>
        </ul>
      </li>
      <li>
        2024/03/19 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            村作成時のダミー発言および入村発言でユーザー定義ランダムが効かない不具合を修正
          </li>
        </ul>
      </li>
      <li>
        2024/02/23 以下を変更しました。
        <ul className='list-content-nested'>
          <li>発言抽出の宛先を複数選択可能に変更</li>
        </ul>
      </li>
      <li>
        2024/02/18 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「浮気者」を追加</li>
        </ul>
      </li>
      <li>
        2024/01/17 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「暴走トラック」を追加</li>
        </ul>
      </li>
    </>
  )
}

export default Announce2024
