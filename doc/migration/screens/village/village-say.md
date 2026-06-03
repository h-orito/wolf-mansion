# 画面: 村画面 — 発言投稿

> 村画面 form-area の発言系。発言種別選択・装飾・表情・秘話を扱う。**アクション発言は別パネルのため [village-action.md](village-action.md) に分離**。

## 概要

- **テンプレート**: `village/say-form.html` (309行) / `say-confirm.html` / `creator-say-confirm.html`
- **担当 JS**: 村画面内の発言フォーム・確認プレビューは `village.js` (発言フォーム L.400-662)。**SSR 確認ページ `say-confirm.html` / `creator-say-confirm.html` は別 JS `say-confirm.js` (109行) が描画** (メッセージ文字列変換 + キャラ画像プレビュー)。村画面内のプレビュー確認は `village.js:510-661`
- **Controller**: `VillageSayController` (creator-say は `CreatorController`、step-0.12)
- **対象ユーザー**: 発言可能な参加者 (situation.say.isAvailableSay 由来)

## 1. 機能 / 出来ることリスト

- 発言種別を選んで発言 (確認画面 → 投稿)
- 装飾タグ / ランダム機能の挿入、表情選択、秘話相手指定
- 文字数 / 行数 / 残り発言回数のリアルタイム表示

## 2. 表示要素・UI 状態 (say-form)

- 死亡時アラート / 進行ルール注意 (墓下・見学発言の可視性に応じ分岐)
- **発言種別ラジオ** (availability フラグで出し分け): 囁き(WEREWOLF_SAY) / 共鳴(MASON_SAY) / 恋人(LOVERS_SAY) / 念話(TELEPATHY) / 通常(NORMAL_SAY) / 呻き(GRAVE_SAY) / 見学(SPECTATE_SAY) / 独り言(MONOLOGUE_SAY) / 秘話(SECRET_SAY)
- **秘話相手**選択 (select + 画像選択モーダル)
- **装飾タグ**ボタン: B(太字) / S(取消線) / 大 / 小 / ruby / 隠(cw=ネタバレ) / 透(tp) / 色**7種** (`say-form.html:134-152`) / `[[]]`。色タグ領域 (`data-random-tag-area`) は表示設定 `is_disp_random_tag_area` で表示トグル (初期 hidden, `village.js:1569-1691`)
- **ランダム機能** select: fortune / 1d6 / or / who / allwho / gwho + ランダムキーワード → タグ追加
- 表情画像 + 表情 select (+ 画像選択モーダル)
- 本文 textarea
- **文字数表示** (`data-message-count` 系): **発言種別ごと**に `max length` / `max count` / `left count` (残り回数) を data 属性で保持。これらの制限値は **村設定で発言種別ごとに細かく設定**される (`sayRestriction`、[village-settings.md](village-settings.md) / [new-village.md](../new-village.md) の発言制限)
- 変換無効 (convertDisable) チェック
- 確認画面へ submit、返信プレビュー領域

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| POST | `/village/{id}/confirm` | 発言確認プレビュー (JSON, 失敗時 null) | village.js |
| POST | `/village/{id}/say` | 発言投稿 → redirect (referer query 維持 + `#bottom`) | 確認画面 |
| GET | `/getFaceImgUrl/{villageId}/{faceTypeCode}` | 表情画像 URL (所属は `CharaController`) | village.js (表情切替) |

- フォーム: `VillageSayForm` (message, messageType, secretSayTargetCharaId, convertDisable, faceType)。バリデータ `SayFormValidator`
- 投稿は `MessageCoordinator.confirmToSay` / `say` (IP アドレス記録)
- CSRF: `/village/*/confirm` `/village/*/say` は CSRF 除外 (`WolfMansionWebSecurityConfig.kt:53-57`)。一方アクション (`/action` `/action-confirm`) は除外されず非対称 → [village-action.md](village-action.md)
- アクション発言の endpoint・フォームは [village-action.md](village-action.md)

## 4. 既存 JS の挙動

- 発言種別変更 → textarea 背景色切替、表情自動切替 (種別デフォルト表情)
- 文字数・行数・残り回数をリアルタイム監視 (種別別制限)
- 秘話選択時は相手選択を強制
- 確認フロー中は `canAutoRefresh=false` (自動更新抑止)
- 装飾タグ: 選択テキストを `[[tag:...]]` 等で囲む。`[[]]` / ランダム関数挿入
- 文字数・行数が制限を超えても**入力自体はできる**が、超過時は**確認ボタンが disabled で送信不可**

## 5. 権限による分岐 / 6. 認可マスク

- 発言可否・選択可能種別は `situation.say` 由来 (役職・生死・村状態で変化)
- 秘話相手リストは `content.form.say.secretSayTargetList`

## 7. 視覚比較

- 既存 `:8091`。各種別の発言、装飾プレビュー、確認画面

## 8. 関連 e2e ケース候補

- [ ] 通常発言: 入力 → 確認 → 投稿 → ログ反映
- [ ] 種別切替 (囁き/独り言/秘話)、秘話相手指定
- [ ] 装飾タグ・ランダム機能・変換無効
- [ ] 表情切替
- [ ] 残り回数・文字数制限 (超過時は送信不可)
- [ ] 発言確認 → プレビュー位置に遷移 → 投稿 / キャンセルで入力欄に戻る

## メモ / 移行時の注意

- **装飾・変換システム** ([[b]]/色/ruby/cw/tp、fortune/dice/who 等のランダム、`[[キーワード]]` 展開) は backend の messageContent 変換で実現。記法・変換仕様を移行時に明文化 (step-0.5 ランダムと対)
  - **発言装飾の実装は firewolf (<https://github.com/h-orito/firewolf>) の `message-decorator` が参考になる**。装飾記法のパース/レンダリングはそれを参照して設計する
- **文字数制限**: 発言種別ごとに `max length` / `max count` / `left count` を持ち、値は村設定 (`sayRestriction`) で細かく設定される。**制限超過しても入力は可能だが送信 (確認) はできない** (確認ボタン disabled)。この挙動を移行後も維持。data 属性群は situation の restrict 情報なので REST レスポンスに含める
- **確認 (`confirm`) → 投稿 (`say`) の 2 段フローの UIUX は崩さない** (確定方針): React 化でも、確認時は**実際に発言が表示される位置に遷移してプレビュー**し、キャンセル時は**発言入力欄に戻る**、という現行の体験を維持する。確認は JSON プレビューなので CSR で実装しやすい
- 表情選択は `getFaceImgUrl` (本人参加キャラのみ、step-0.4 [charachip-list.md](../charachip-list.md) の横断 JSON API)
- IP アドレス記録は移行後も維持 (不正対策)
- `face-type-form.html` (表情差分の**追加/管理**) は RP 機能として step-0.11 で扱う (本 step は表情**選択**のみ)
