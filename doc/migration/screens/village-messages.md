# 画面: 村画面 — メッセージ表示 (一覧 / フィルタ / アンカー)

> 村画面のログ表示部分。村画面ベース ([village-base.md](village-base.md)) の `message-area` を Handlebars で描画する部分 + フィルタ + アンカー。

## 概要

- **テンプレート**: `village-template/message.html` (一覧ラッパ) / `message-partial.html` (各メッセージ, 490行) / `participants.html` (終了時正体公開) / `village/modal-filter.html` (抽出)
- **専用ページ**: `village-message.html` (`GET /village/{id}/message`、アンカー/切り抜き用の軽量メッセージページ)
- **担当 JS**: `village.js` (一覧取得/フィルタ/ポーリング) / `village-message.js` (アンカー専用)
- **Controller**: `VillageMessageController`
- **対象ユーザー**: 全員 (可視範囲はサーバ側でマスク)

## 1. 機能 / 出来ることリスト

- 日別メッセージ一覧のページング表示・自動更新
- 発言種別 / 発言者 / 宛先 / キーワード での抽出 (フィルタ)
- アンカー (`>>123` 等) クリックでの該当発言インライン展開
- 返信 / 秘話返信リンク
- 終了village での参加者正体公開

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
| CREATOR_SAY (天からのお告げ) | `>>#N` | message-creator | - | |
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
| PARTICIPANTS | - | (participants table) | - | 終了時の正体公開 |
| (isBigEars 地獄耳) | - | message-owl | ✓ | 顔なし特殊表示 |

共通要素 (say系): copy-anchor / characterName / playerName(spoiled) / timeFormat / 顔画像 / canReply (data-reply-to) / canSecret (data-secret-to)。
装飾: `isLoud`→loud クラス, `isRainbow`→rainbow ラッパ。
Handlebars カスタムヘルパー: `eq` `neq` `or` `timeFormat` `escapeHtmlWithoutBr` `minHeightCss`。

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/village/getMessageList` | 日別発言一覧 (filter 付) | village.js |
| GET | `/village/getLatestMessageDatetime` | 更新検知 | village.js (ポーリング) |
| GET | `/village/getAnchorMessage` | 単一アンカー発言 | village.js / village-message.js |
| GET | `/village/{id}/getAnchorMessages?anchors=` | 複数アンカー (`n123_w45` 形式) | village-message.js |
| GET | `/village/{id}/getParticipants` | 参加者正体一覧 (settled時のみ) | village.js |
| GET | `/village/{id}/message` | アンカー専用メッセージページ (SSR) | リンク |

`getMessageList` パラメータ: villageId, day, pageNum, pageSize, filterParticipantIds, filterTypes, filterKeywords, toParticipantIds, isPaging, isDispLatest。

## 4. 既存 JS の挙動

- **一覧描画**: `getMessageList` → `messageTemplate(response)` (Handlebars) で `messageList` を描画。`message-partial` を partial 登録
- **ページング**: 前/次/指定/最新 (`data-pagenum` 等)。日付リンクは1ページ目、それ以外は最新
- **アンカー展開**: `>>N` クリック → `getAnchorMessage` → `messagePartialTemplate` で collapse 展開。種別別アンカー (重狼/mason/恋人/念話等) 対応
- **アナウンス**: suddenlyDeathMessage / villageStatusMessage / commitStatusMessage を一覧末尾に表示
- **フィルタ (modal-filter)**: 種別 (全ON/OFF/反転)、発言者、宛先、キーワード(スペース区切り)、ショートカット(囁き/共鳴/恋人/念話/自分宛/通知キーワード)。選択は URL query に保存。抽出/別タブ/リセット
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
- [ ] 終了村の参加者正体公開
- [ ] ネタバレ防止トグル

## メモ / 移行時の注意

- **React 化**: 1 つの `MessageCard` コンポーネント + 種別バリアント (上表) で実装。Handlebars テンプレート群を置換
- **アンカー記法のパース** (`n123_w45`、種別文字 `nwmflgsMSca`) は backend (`Anchors.of`) + frontend 両方で必要。記法仕様を共有
- メッセージ可視性マスクは backend に残す (`messageService` + situation)。REST レスポンスは「見せてよい発言のみ」を返す設計
- 装飾 (loud/rainbow/ruby/色/取消線/ネタバレタグ) と `[[ランダムキーワード]]` 展開は発言投稿 (step-0.8) と対。messageContent は HTML (`{{{ }}}` raw 出力) なので **XSS 対策**を移行時に再確認 (現状 escapeHtmlWithoutBr ヘルパー)
- フィルタ状態の URL query 保存は React Router の searchParams で再現
- `village-message.html` の `<script src>` が village.js だが `th:src` は village-message.js という不整合あり (実体は village-message.js が効く)
