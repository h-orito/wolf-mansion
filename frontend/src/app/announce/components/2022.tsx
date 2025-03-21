import Link from 'next/link'
import { FC } from 'react'

const Announce2022: FC = () => {
  return (
    <>
      <li>
        2022/12/15 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            役職「臭狼」「組長」「濡衣者」「陰陽師」「王族」「革命者」を追加（計100役職）
          </li>
        </ul>
      </li>
      <li>
        2022/11/21 以下を変更しました。
        <ul className='list-content-nested'>
          <li>村建てオプション：プロデューサー機能を追加</li>
          <li>発言抽出項目に宛先を追加</li>
        </ul>
      </li>
      <li>
        2022/11/19 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            <Link href='/wolf-mansion/skill' className='link-style'>
              役職一覧検索機能
            </Link>
            を追加（ルールページから遷移可能）
          </li>
        </ul>
      </li>
      <li>
        2022/11/16 以下を修正しました。
        <ul className='list-content-nested'>
          <li>
            梟が進行中に共鳴発言or囁きor念話or恋人発言を選択し、かつ一部参加者のみ選択した状態で発言抽出を行うと、その参加者の囁き/念話/恋人発言のみが抽出されてしまう（＝誰がどの発言種別で話したかわからない前提の発言なのに、発言者を特定することができる）不具合を修正
          </li>
        </ul>
      </li>
      <li>
        2022/10/28 以下を変更しました。
        <ul className='list-content-nested'>
          <li>ランダムメッセージ一覧にキーワード検索を追加</li>
          <li>管狐が妖狐系役職を占っても呪殺しないよう仕様変更</li>
        </ul>
      </li>
      <li>
        2022/10/27 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「餡麺麭者」を追加</li>
        </ul>
      </li>
      <li>
        2022/10/25 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「道化師」「伝説の殺し屋」を追加</li>
          <li>
            濁点者の叫び状態のとき、改行のみの行には濁点が付与されないよう変更
          </li>
        </ul>
      </li>
      <li>
        2022/10/23 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「冷凍者」を追加</li>
        </ul>
      </li>
      <li>
        2022/09/02 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「騙狐」「夜狐」を追加</li>
        </ul>
      </li>
      <li>
        2022/09/01 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「闇探偵」「耳年増」を追加</li>
        </ul>
      </li>
      <li>
        2022/07/30 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「全知者」を追加</li>
        </ul>
      </li>
      <li>
        2022/07/30 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            表示オプションに「文字を大きく表示する」「画像を大きく表示する」を追加
          </li>
        </ul>
      </li>
      <li>
        2022/07/04 以下を変更しました。
        <ul className='list-content-nested'>
          <li>ババ、当選者で役職交換された側にメッセージ表示を追加</li>
        </ul>
      </li>
      <li>
        2022/06/26 以下を変更しました。
        <ul className='list-content-nested'>
          <li>人狼の襲撃を以下のように仕様変更</li>
          <li>一人ずつ個別の襲撃セットを行える</li>
          <li>日付更新時は、投票と同様、最多票となる内容で襲撃が実行される</li>
          <li>他人の襲撃セットの内容は参照できない</li>
        </ul>
      </li>
      <li>
        2022/05/10 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「画鋲」「箪笥」を追加</li>
        </ul>
      </li>
      <li>
        2022/05/09 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「管狐」「稲荷」「当選者」を追加</li>
        </ul>
      </li>
      <li>
        2022/05/04 以下を変更しました。
        <ul className='list-content-nested'>
          <li>発言で絵文字を使用できるよう変更</li>
          <li>自己紹介機能を追加（自分の戦績ページで編集）</li>
        </ul>
      </li>
      <li>
        2022/05/01 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            静狼が護衛か襲撃耐性で襲撃失敗した場合、呪狼に変化するよう仕様変更
          </li>
          <li>役職「感覚者」を追加</li>
        </ul>
      </li>
      <li>
        2022/04/30 以下を変更しました。
        <ul className='list-content-nested'>
          <li>ルビ機能を追加</li>
        </ul>
      </li>
      <li>
        2022/04/29 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            抽出中に画面下部の抽出モーダルを開くボタンを押下すると抽出が解除されるよう動作を変更
          </li>
        </ul>
      </li>
      <li>
        2022/04/26 以下を変更しました。
        <ul className='list-content-nested'>
          <li>キャラチップ一覧画面に設定ファイル作成補助ツールについて追記</li>
          <li>ルールに闇鍋編成について記載</li>
        </ul>
      </li>
      <li>
        2022/04/21 以下を変更しました。
        <ul className='list-content-nested'>
          <li>キャラクターを自分で用意する村の規約変更</li>
          <li>
            キャラクターを自分で用意する村は入村パスワードを必須とするよう仕様変更
          </li>
          <li>表情差分編集機能を追加</li>
        </ul>
      </li>
      <li>
        2022/04/19 以下を実装しました。
        <ul className='list-content-nested'>
          <li>キャラクターを自分で用意する村建てオプションを追加</li>
        </ul>
      </li>
      <li>
        2022/04/10 以下を実装しました。
        <ul className='list-content-nested'>
          <li>キャラチップの詳細ページに部屋割り例を表示</li>
        </ul>
      </li>
      <li>
        2022/04/10 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            王狼、角狼、飛狼、金狼、銀狼、歩狼が護衛か襲撃耐性で襲撃失敗した場合、人狼に変化するよう仕様変更
          </li>
          <li>
            「●●！今日がお前の命日だ！」の発言者を「ランダム」→「襲撃者に固定」に仕様変更
          </li>
        </ul>
      </li>
      <li>
        2022/04/10 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「牧師」「教唆者」を追加</li>
          <li>ステータス「狂気」「信念」を追加</li>
          <li>破局者の陣営を愉快犯陣営から人狼陣営に変更</li>
          <li>誑狐が誑かす際、恋絆を除去するよう仕様変更</li>
          <li>
            破局者、恋泥棒が能力行使に失敗した場合、対象に通知されないよう仕様変更
          </li>
          <li>
            泥棒猫が恋泥棒に失敗した場合、自身への恋絆を付与しないよう仕様変更
          </li>
          <li>泥棒猫はエピローグまで能力行使しないと自害するよう仕様変更</li>
          <li>誑かしの処理順を恋絆系の処理の後に変更</li>
        </ul>
      </li>
      <li>
        2022/04/09 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「黙狼」「聴狂人」を追加</li>
          <li>
            死霊術師が蘇生させた際に変化する役職を「人狼」から「黙狼」に仕様変更
          </li>
        </ul>
      </li>
      <li>
        2022/03/30 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「静狼」「堅狼」を追加</li>
        </ul>
      </li>
      <li>
        2022/03/29 以下を変更しました。
        <ul className='list-content-nested'>
          <li>勝敗判定陣営を表示</li>
          <li>入村時に選択できるキャラ数が多い場合に100件ずつ画像表示</li>
        </ul>
      </li>
      <li>
        2022/03/28 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「防音者」「怨恨者」を追加</li>
        </ul>
      </li>
      <li>
        2022/03/12 以下を実装しました。
        <ul className='list-content-nested'>
          <li>各発言に返信、秘話リンクを追加（試験運用中）</li>
        </ul>
      </li>
      <li>
        2022/02/22 以下を実装しました。
        <ul className='list-content-nested'>
          <li>参加したことがない人は村を建てられないよう仕様変更</li>
        </ul>
      </li>
      <li>
        2022/02/06 以下を実装しました。
        <ul className='list-content-nested'>
          <li>ハッシュタグ機能を追加</li>
        </ul>
      </li>
    </>
  )
}

export default Announce2022
