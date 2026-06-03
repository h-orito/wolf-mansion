# 画面: 村画面 — メッセージ表示 (一覧 / フィルタ / アンカー)

> 村画面のログ表示部分。村画面ベース ([village-base.md](village-base.md)) の `message-area` を Handlebars で描画する部分 + フィルタ + アンカー。

## 概要

- **テンプレート**: `village-template/message.html` (一覧ラッパ) / `message-partial.html` (各メッセージ, 490行) / `participants.html` (エピローグ以降の正体公開) / `village/modal-filter.html` (抽出)
- **専用ページ**: `village-message.html` (`GET /village/{id}/message?anchors=`、**通知に貼られるアンカーのパーマリンクページ**。アプリ内 UI からの遷移は無く、通知 (`NotificationService`) 経由の外部ディープリンク専用)
- **担当 JS**: `village.js` (一覧取得/フィルタ/ポーリング) / `village-message.js` (アンカー専用)
- **Controller**: `VillageMessageController`
- **対象ユーザー**: 全員 (可視範囲はサーバ側でマスク)

## 1. 機能 / 出来ることリスト

- 日別メッセージ一覧のページング表示・自動更新
- 発言種別 / 発言者 / 宛先 / キーワード での抽出 (フィルタ)
- アンカー (`>>123` 等) クリックでの該当発言インライン展開
- 返信 / 秘話返信リンク
- **エピローグ以降 (settled = エピローグ + 終了)** での参加者正体公開 (`getParticipants` は `village.status.isSettled()` ガード, `VillageMessageController.kt:187`)

## 2. メッセージ種別カタログ (message-partial.html)

各メッセージは `message-card` + 種別ごとの CSS クラス + アンカー記法。`data-spoiled-content` 付きはネタバレ対象。

| messageType | アンカー記法 | CSS クラス | spoiled | 備考 |
|---|---|---|---|---|
| NORMAL_SAY (通常) | `>>N` | message-normal | - | 返信/秘話可 |
| WEREWOLF_SAY (囁き) | `>>*N` | message-werewolf | ✓ | |
| MONOLOGUE_SAY (独り言) | `>>-N` | message-monologue | ✓ | |
| SECRET_SAY (秘話) | `>>sN` | message-secret | ✓ | → 宛先 charaName |
| MASON_SAY (共鳴) | `>>=N` | message-mason | ✓ | |
| LOVERS_SAY (恋人) | `>>?N` | message-lover | ✓ | |
| TELEPATHY (念話) | `>>_N` | message-telepathy | ✓ | |
| GRAVE_SAY (墓下) | `>>+N` | message-grave | ✓ | |
| SPECTATE_SAY (見学) | `>>@N` | message-spectate | ✓ | |
| CREATOR_SAY (村建て発言) | `>>#N` | message-creator | - | `CDef.MessageType.村建て発言` = `"CREATOR_SAY"`。村主が「天からのお告げ」として投稿 |
| ACTION (アクション) | `>>aN` | message-action | - | |
| PUBLIC_SYSTEM | - | message-public-system | - | |
| PRIVATE_SYSTEM | - | message-private-system | ✓ | |
| PRIVATE_SEER/WISE | - | message-private-seer | ✓ | 占い/賢者結果 |
| PRIVATE_PSYCHIC/GURU/CORONER | - | message-private-psychic | ✓ | 霊媒/導師/検死 |
| PRIVATE_INVESTIGATE | - | message-private-investigate | ✓ | |
| PRIVATE_WEREWOLF | - | message-private-werewolf | ✓ | |
| PRIVATE_LOVER | - | message-private-lover | ✓ | |
| PRIVATE_FOX | - | message-private-fox | ✓ | |
| PRIVATE_ABILITY | - | message-private-ability | ✓ | |
| PARTICIPANTS | - | (participants table) | - | エピローグ以降 (settled) の正体公開 |
| (isBigEars 地獄耳) | - | message-owl | ✓ | 顔なし特殊表示 |

共通要素 (say系): copy-anchor / characterName / playerName(spoiled) / timeFormat / 顔画像 / canReply (data-reply-to) / canSecret (data-secret-to)。
装飾: `isLoud`→loud クラス, `isRainbow`→rainbow ラッパ。
Handlebars カスタムヘルパー: `eq` `neq` `or` `timeFormat` `escapeHtmlWithoutBr` `minHeightCss`。

> **フィルタ種別の補足**: `村建て発言` は **`CDef.MessageType.村建て発言` = `"CREATOR_SAY"`** であり、上表の **CREATOR_SAY (message-creator) 行と同一**。filter 対象種別として typeMap にも存在 (`VillageGetMessageListForm.kt:77`)。`GRAVE_SPECTATE_SAY` キーは「死者の呻き + 見学発言」をまとめた合成フィルタ種別。

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/village/getMessageList` | 日別発言一覧 (filter 付) | village.js |
| GET | `/village/getLatestMessageDatetime` | 更新検知 | village.js (ポーリング) |
| GET | `/village/getAnchorMessage` | 単一アンカー発言 | village.js / village-message.js |
| GET | `/village/{id}/getAnchorMessages?anchors=` | 複数アンカー (`n123_w45` 形式) | village-message.js |
| GET | `/village/{id}/getParticipants` | 参加者正体一覧 (settled=エピローグ以降のみ, `isSettled()` ガード) | village.js |
| GET | `/village/{id}/message?anchors=` | **通知に貼られるアンカーのパーマリンクページ** (`village-message.html`、`VillageController.kt:86`)。`village-message.js` が `getAnchorMessages?anchors=<種別><番号>` で該当発言のみ取得・表示する軽量ページ | **通知 (`NotificationService.createMessageNotificationUrl`, NotificationService.kt:247-253)**。アプリ内 UI からのリンクは無し |

`getMessageList` パラメータ (実 API 名, `VillageGetMessageListForm`): `villageId, day, pageNum, pageSize, participantIds (発言者), types (種別), keywords (スペース区切り), toParticipantIds (宛先), isPaging, isDispLatest`。

> ⚠️ `filterParticipantIds` / `filterTypes` / `filterKeywords` は **village.js 内部の JS 変数名であって API パラメータ名ではない**。REST 化時は上記の実パラメータ名 (`participantIds` / `types` / `keywords`) を使うこと。

## 4. 既存 JS の挙動

- **一覧描画**: `getMessageList` → `messageTemplate(response)` (Handlebars) で `messageList` を描画。`message-partial` を partial 登録
- **ページング**: 前/次/指定/最新 (`data-pagenum` 等)。日付リンクは1ページ目、それ以外は最新
- **アンカー展開**: `>>N` クリック → `getAnchorMessage` → `messagePartialTemplate` で collapse 展開。種別別アンカー (重狼/mason/恋人/念話等) 対応
- **アナウンス**: suddenlyDeathMessage / villageStatusMessage / commitStatusMessage を一覧末尾に表示
- **フィルタ (modal-filter)**: 種別 (全ON/OFF/反転)、発言者、宛先、キーワード(スペース区切り)、ショートカット(囁き/共鳴/恋人/念話/自分宛/通知キーワード)。選択は URL query に保存。抽出/別タブ/リセット
- **ハッシュタグ**: 本文中の `#タグ` は `data-message-hashtag` 付きリンクとして描画され、クリックで全種別/全宛先 ON + キーワードにタグを設定してフィルタ実行 (`village.js:1406-1413`)
- **ネタバレ防止 (`data-dsetting-unspoiled` / filterSpoiled)**: `data-spoiled-content` を隠し `data-spoiled-alternative-content` を表示。「エピローグ前同等の表示にする」トグル

## 5. 権限による分岐 / 6. 認可マスク

- **可視範囲はサーバ側で決定**: `messageService.findMeesages(village, myself, myselfPlayer, query)` が myself/player に応じて見せる発言を絞る (囁きは人狼のみ、墓下は死者のみ等)
- playerName 表示も `data-spoiled-content` (進行中は隠れる)
- 詳細なマスク仕様は step-0.16 (認可マスク) で整理

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/village/{id}`。各メッセージ種別の見た目、フィルタ動作、アンカー展開、ネタバレ防止表示

## 8. 関連 e2e ケース候補

- [ ] 各メッセージ種別の表示 (通常/囁き/秘話/墓下 等)
- [ ] ページング・最新移動
- [ ] フィルタ (種別/発言者/宛先/キーワード/ショートカット)
- [ ] アンカー展開
- [ ] 返信 / 秘話返信リンクから返信・秘話を送信
- [ ] ハッシュタグ表示 → クリックで該当タグ抽出 (フィルタ連動)
- [ ] ランダム機能 (fortune/dice/who 等) が展開された発言の表示
- [ ] 文字装飾 (色/ruby/cw/tp/太字/取消線 等) が適用された発言の表示
- [ ] エピローグ以降 (settled) の参加者正体公開
- [ ] ネタバレ防止トグル

## メモ / 移行時の注意

- **React 化**: 1 つの `MessageCard` コンポーネント + 種別バリアント (上表) で実装。Handlebars テンプレート群を置換
- **アンカー記法のパース** (`n123_w45`、種別文字 `nwmflgsMSca`) は backend (`Anchors.of`) + frontend 両方で必要。記法仕様を共有
- メッセージ可視性マスクは backend に残す (`messageService` + situation)。REST レスポンスは「見せてよい発言のみ」を返す設計
- **参加者正体一覧 (`getParticipants`) はクライアント処理に寄せられる可能性**: エピローグ以降 (settled) は村取得 API のレスポンスに役職等が既に含まれていれば、専用 API を叩かずクライアント側で正体一覧を組み立てられる (要検討)。現状は専用 endpoint
- **アナウンス (suddenlyDeathMessage / villageStatusMessage / commitStatusMessage) もクライアント生成に寄せられる**: 一覧末尾のこれらの定型メッセージは、クライアント側で文言を組み立てて表示可能。ただし突然死候補者・コミット済み人数などの素材は `VillageSituation` 等から取得する必要がある
- 装飾 (loud/rainbow/ruby/色/取消線/ネタバレタグ) と `[[ランダムキーワード]]` 展開は発言投稿 (step-0.8) と対。messageContent は HTML (`{{{ }}}` raw 出力) なので **XSS 対策**を移行時に再確認 (現状 escapeHtmlWithoutBr ヘルパー)
- フィルタ状態の URL query 保存は React Router の searchParams で再現
- `village-message.html` の `<script src>` が village.js だが `th:src` は village-message.js という不整合あり (実体は village-message.js が効く)
