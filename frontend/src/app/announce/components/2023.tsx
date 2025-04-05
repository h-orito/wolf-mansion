import { FC } from 'react'

const Announce2023: FC = () => {
  return (
    <>
      <li>
        2023/11/29 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「暴狼」を追加</li>
        </ul>
      </li>
      <li>
        2023/11/25 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            役職「覚者」「興信者」「帝狼」「剖狼」「海王者」「念狐」を追加
          </li>
        </ul>
      </li>
      <li>
        2023/11/20 以下を変更しました。
        <ul className='list-content-nested'>
          <li>トップページに役職一覧へのリンクを追加</li>
          <li>役職一覧に「村に登場する役職」での検索を追加</li>
        </ul>
      </li>
      <li>
        2023/11/18 以下を変更しました。
        <ul className='list-content-nested'>
          <li>村建ては村設定に関わらず全員に秘話を送れるよう修正</li>
          <li>村建てオプションの秘話設定に「村建てとのみ」を追加</li>
        </ul>
      </li>
      <li>
        2023/09/29 以下を変更しました。
        <ul className='list-content-nested'>
          <li>突然死者は必ず敗北となるよう変更（実装漏れ）</li>
          <li>
            突然死者は蘇生されても後追い処理の最初に再度突然死するよう変更
          </li>
          <li>
            夜狐の襲撃者に恋絆が付与されていた場合、恋絆除去と狐憑き付与を行わないよう変更
          </li>
        </ul>
      </li>
      <li>
        2023/09/27 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            餡麺麭者の仕様を変更（パン屋と闇パン屋の生存状況に応じて信念や狂気を付与）
          </li>
        </ul>
      </li>
      <li>
        2023/08/29 以下を変更しました。
        <ul className='list-content-nested'>
          <li>共有者、共鳴者が役職欄で仲間を知ることができるよう仕様変更</li>
        </ul>
      </li>
      <li>
        2023/06/05 以下を変更しました。
        <ul className='list-content-nested'>
          <li>ババや当選者が勇者と役職交換できないよう変更</li>
          <li>
            勇者、絶対人狼、梟などの全体公開役職通知を復活処理の後にまとめて行うよう変更
          </li>
          <li>ババや当選者が絶対人狼と役職交換できる不具合を修正</li>
        </ul>
      </li>
      <li>
        2023/06/05 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「共有者」「勇者」「曇天者」「魅惑の人魚」を追加</li>
        </ul>
      </li>
      <li>
        2023/06/03 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「冷やし中華」を追加</li>
        </ul>
      </li>
      <li>
        2023/05/16 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            プロデューサーかつ参加している場合にキーワード通知、役職発言通知を受け取れるように変更
          </li>
          <li>プロデューサーは進行中も部屋割りで役職確認可能に変更</li>
          <li>プロデューサーは進行中も役職発言アンカーを開けるよう変更</li>
        </ul>
      </li>
      <li>
        2023/05/04 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「呪縛者」「反呪者」を追加</li>
        </ul>
      </li>
      <li>
        2023/04/23 以下を変更しました。
        <ul className='list-content-nested'>
          <li>Discord通知に日付更新通知を追加</li>
        </ul>
      </li>
      <li>
        2023/04/08 以下を変更しました。
        <ul className='list-content-nested'>
          <li>役職「闇パン屋」「リア充」を追加</li>
        </ul>
      </li>
      <li>
        2023/04/05 以下を変更しました。
        <ul className='list-content-nested'>
          <li>申し子と転生者の蘇生順をランダムに変更</li>
          <li>占い系（全知以外）の占い順をランダムに変更</li>
          <li>他者蘇生系の蘇生順をランダムに変更</li>
          <li>ルールページの日付更新時の処理順を詳細に記載</li>
        </ul>
      </li>
      <li>
        2023/02/23 以下を変更しました。
        <ul className='list-content-nested'>
          <li>Discord通知機能を追加</li>
        </ul>
      </li>
      <li>
        2023/02/21 以下を変更しました。
        <ul className='list-content-nested'>
          <li>闇鍋編成で狼カウントの最少最多を設定できるよう機能追加</li>
          <li>
            投票結果の開示時に投票人数と非投票数が異なる場合、投票人数も表示するよう変更
          </li>
        </ul>
      </li>
      <li>
        2023/02/18 以下を変更しました。
        <ul className='list-content-nested'>
          <li>
            プロローグでの自動退村条件を「村の開始まで72時間を切っている、かつ最終アクセスから24時間以上経過した」に変更
          </li>
        </ul>
      </li>
    </>
  )
}

export default Announce2023
