import Link from 'next/link'
import { FC } from 'react'

const Announce2021: FC = () => {
  return (
    <>
      <li>
        2021/12/15 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「バールのようなもの」を追加</li>
        </ul>
      </li>
      <li>
        2021/12/12 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「破局者」「泥棒猫」を追加</li>
        </ul>
      </li>
      <li>
        2021/12/02 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「黒箱者」を追加</li>
        </ul>
      </li>
      <li>
        2021/12/01 以下を実装しました。
        <ul className='list-content-nested'>
          <li>村設定「募集範囲」「年齢制限」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/30 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「マタギ」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/25 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「弁護士」「市長」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/17 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「翻訳者」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/16 以下を実装しました。
        <ul className='list-content-nested'>
          <li>プロローグ中に村名を変更できるように仕様変更</li>
          <li>
            恋絆、狐憑きの梟は恋人会話と念話を地獄耳でなく元の状態で参照できるよう仕様変更
          </li>
        </ul>
      </li>
      <li>
        2021/11/15 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「執行人」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/14 以下を実装しました。
        <ul className='list-content-nested'>
          <li>キャラチップを複数選択して村を作成できるよう変更</li>
          <li>村検索機能を追加</li>
        </ul>
      </li>
      <li>
        2021/11/12 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「保険屋」を追加</li>
          <li>
            一部の役職について、能力行使内容を表示できるよう仕様変更（例：狩人）
          </li>
          <li>※得られる情報量は変わりません</li>
        </ul>
      </li>
      <li>
        2021/11/09 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「濁点者」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/08 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「不止者」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/07 以下を実装しました。
        <ul className='list-content-nested'>
          <li>エピローグ後に秘話にアンカーを張れるよう変更</li>
        </ul>
      </li>
      <li>
        2021/11/06 以下を実装しました。
        <ul className='list-content-nested'>
          <li>エピローグ短縮機能を追加</li>
        </ul>
      </li>
      <li>
        2021/11/05 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「仙狐」「ババ」を追加</li>
          <li>発言種別「念話」を追加（仙狐の項目を参照ください）</li>
          <li>見学発言の色を変更（ルール - その他の項目を参照ください）</li>
        </ul>
      </li>
      <li>
        2021/11/04 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「花占い師」「監視者」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/03 以下を実装しました。
        <ul className='list-content-nested'>
          <li>秘話の対象を画像で選択</li>
        </ul>
      </li>
      <li>
        2021/11/03 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            <Link href='/wolf-mansion/rule#tensei' className='link-style'>
              転生
            </Link>
            の仕様やCOルールを明記
          </li>
          <li>転生で選ばれる役職の候補を仕様変更</li>
          <li>村建てオプションに転生先役職範囲を追加</li>
          <li>エピで役職履歴を表示、状況欄にその日の役職を表示</li>
        </ul>
      </li>
      <li>
        2021/11/02 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「蘇生者」「死霊術師」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/02 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「トラック」「ごん」を追加</li>
        </ul>
      </li>
      <li>
        2021/11/01 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「風来狩人」「拡声者」を追加</li>
          <li>
            「最新」表示時に自動更新や更新ボタンを押下しても「最新」が維持されるよう変更
          </li>
        </ul>
      </li>
      <li>
        2021/11/01 役職のバランス調整を行いました。
        <ul className='list-content-nested'>
          <li>罠師：設置の際対象の部屋までの足音が鳴るよう変更</li>
          <li>爆弾魔：設置の際対象の部屋までの足音が鳴るよう変更</li>
          <li>求愛者：求愛の際対象の部屋までの足音が鳴るよう変更</li>
          <li>求愛者：能力行使は1日目のみ → いつでも1回までに変更</li>
          <li>
            ストーカー：ストーキングの際対象の部屋までの足音が鳴るよう変更
          </li>
          <li>ストーカー：能力行使は1日目のみ → いつでも1回までに変更</li>
          <li>絡新婦：誘惑の際対象の部屋までの足音が鳴るよう変更</li>
          <li>美人局：誘惑の際対象の部屋までの足音が鳴るよう変更</li>
          <li>誑狐：誑かしの際対象の部屋までの足音が鳴るよう変更</li>
          <li>虹職人：虹塗りの際対象の部屋までの足音が鳴るよう変更</li>
          <li>壁殴り代行：一度殴った対象は再度殴る対象に選べないよう変更</li>
        </ul>
      </li>
      <li>
        2021/10/31 以下を実装しました。
        <ul className='list-content-nested'>
          <li>発言抽出を改良</li>
          <li>
            役職特有のメッセージや能力行使履歴を死亡時・エピローグ時にも参照できるよう変更
          </li>
          <li>役職「虹職人」を追加</li>
          <li>「最新」表示機能追加</li>
          <li>
            初期表示は最新ページを、日付移動時は1ページ目を表示するよう変更
          </li>
        </ul>
      </li>
      <li>
        2021/10/30 以下を実装しました。
        <ul className='list-content-nested'>
          <li>ユーザー一覧を実装</li>
          <li>
            「ページ分割しない」だとわかりにくいので「ページ分割する」に変更
          </li>
          <li>
            転生者が絶対人狼として生き返った場合、絶対人狼である旨のメッセージを表示するよう変更
          </li>
          <li>
            いずれかの転生者が梟として生き返り、それが村で一人目の梟の場合、梟が存在する旨のメッセージを表示するよう変更
          </li>
        </ul>
      </li>
      <li>
        2021/10/29 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「歩狼」「銀狼」「金狼」「王狼」「煽動者」を実装</li>
        </ul>
      </li>
      <li>
        2021/10/29 以下を変更しました。
        <ul className='list-content-nested'>
          <li>エピローグの参加者一覧で恋絆や狐憑きを確認できるよう変更</li>
        </ul>
      </li>
      <li>
        2021/10/28 以下を変更しました。
        <ul className='list-content-nested'>
          <li>罠と爆弾の作動・起爆結果メッセージを追加（エピまで非公開）</li>
          <li>背徳者、狐憑きが妖狐系役職が全滅した際に後追いするよう変更</li>
        </ul>
      </li>
      <li>
        2021/10/28 以下を修正しました。
        <ul className='list-content-nested'>
          <li>村設定変更画面を開けない不具合を修正</li>
          <li>1日目にフルバすると2日目の探偵調査でエラーになる不具合を修正</li>
          <li>
            闇鍋と固定編成で役職編成メッセージの公開範囲が逆になっていた不具合を修正
          </li>
        </ul>
      </li>
      <li>
        2021/10/27 以下を実装しました。
        <ul className='list-content-nested'>
          <li>
            大規模修繕（バグってる可能性が高いので変な挙動をしていたら教えてください）
          </li>
          <li>
            アクションと名前変更と村建て機能タブも畳んだ状態を保持できるように変更
          </li>
          <li>エピったら能力行使履歴を状況欄に全部表示</li>
        </ul>
      </li>
      <li>
        2021/08/19 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「飛狼」「角狼」を実装</li>
        </ul>
      </li>
      <li>
        2021/08/17 以下を修正・実装しました。
        <ul className='list-content-nested'>
          <li>村に参加していても新しい村を作成できるよう変更</li>
        </ul>
      </li>
      <li>
        2021/08/08 以下を修正・実装しました。
        <ul className='list-content-nested'>
          <li>
            絡新婦、美人局に誘惑された際に勝敗判定陣営が変わらない不具合を修正
          </li>
          <li>
            闇鍋編成で陣営の最多人数を設定しても最多人数を超えることがある不具合を修正
          </li>
          <li>
            秘話プルダウンに部屋番号を表示し、部屋番号順に表示されるよう修正
          </li>
          <li>
            秘話に切り替えた際に発言欄が独り言の色になっている不具合を修正
          </li>
        </ul>
      </li>
      <li>
        2021/08/03 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「壁殴り代行」を実装</li>
        </ul>
      </li>
      <li>
        2021/08/02 以下を実装しました。
        <ul className='list-content-nested'>
          <li>誑狐、絡新婦、美人局の能力を1日目以外にも使用できるよう変更</li>
        </ul>
      </li>
      <li>
        2021/08/02 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「転生者」「申し子」を追加</li>
        </ul>
      </li>
      <li>
        2021/08/01 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「絡新婦」「美人局」を追加</li>
        </ul>
      </li>
      <li>
        2021/08/01 以下を実装しました。
        <ul className='list-content-nested'>
          <li>闇鍋編成機能を追加</li>
          <li>梟がいると1日目に専用シスメが表示されるよう変更</li>
        </ul>
      </li>
      <li>
        2021/07/26 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「妄想癖」「夢遊病者」「冤罪者」を追加</li>
        </ul>
      </li>
      <li>
        2021/07/26 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「一匹狼」を追加</li>
          <li>秘話の背景色を変更</li>
        </ul>
      </li>
      <li>
        2021/07/26 以下を実装しました。
        <ul className='list-content-nested'>
          <li>役職「誑狐」を追加</li>
          <li>妖狐、誑狐は誰が妖狐系役職か認識できるよう変更</li>
          <li>
            希望役職に「おまかせ（妖狐陣営）」と「おまかせ（愉快犯陣営）」を追加
          </li>
        </ul>
      </li>
      <li>
        2021/07/25 以下を変更しました。
        <ul className='list-content-nested'>
          <li>進行中の村に参加していても新たに他の村に参加できるように変更</li>
        </ul>
      </li>
      <li>
        2021/06/14 以下を実装しました。
        <ul className='list-content-nested'>
          <li>名前変更</li>
          <li>簡易メモ</li>
          <li>自分宛発言抽出</li>
        </ul>
      </li>
      <li>
        2021/06/11 以下を実装しました。
        <ul className='list-content-nested'>
          <li>アクション発言を追加</li>
        </ul>
      </li>
    </>
  )
}

export default Announce2021
