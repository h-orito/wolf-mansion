# 画面: 村画面 — アクション発言

> 村画面 form-area の**アクション発言**。発言 (say) とは**別パネル** (`#actionform-panel`) で、「〜は、〜」形式のアクションを投稿する。通常の発言投稿は [village-say.md](village-say.md)。

## 概要

- **テンプレート**: `village/action-form.html`
- **担当 JS**: `village.js` (アクション L.548-887)
- **Controller**: `VillageSayController` (`/village/{id}/action-confirm`, `/village/{id}/action`)
- **Form**: `VillageActionForm` (`myself`, `target`, `message`, `convertDisable`)。バリデータ `ActionFormValidator`
- **表示条件**: `content.form.action.dispActionForm` (`action-form.html:20`)
- **対象ユーザー**: アクション可能な参加者

## 1. 機能 / 出来ることリスト

- アクション発言 (「{自分}は、{対象}{本文}」形式) を確認 → 投稿
- 対象選択 (選択しない / 全員 / 参加者)
- 装飾・変換の無効化

## 2. 表示要素・UI 状態

- **アクションパネル** (`#actionform-panel`、collapse、`data-bottom-fix` で固定可)
- 進行中の注意文 (推理・まとめ・推理に繋がる内容は不可, `action-form.html:29-33`)
- **自分** prefix 表示 (`*{myself}` =「〜は、」, `:39`)
- **対象 select** (`target`, `:42-46`): `選択しない` / `全員` / `targetList` (参加者キャラ)
- **本文** text input (自由入力, `:52`)
- **文字数表示** (`data-action-count`, `:58-61`): `data-message-restrict-action-max-length` / `-max-count` / `-left-count` を `content.form.say.restrict` (`actionLength` / `actionCount` / `actionLeftCount`) から保持
- **装飾・変換無効** checkbox (`:62-63`)
- **確認画面へ** submit (初期 disabled、入力検証で活性化, `:64`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| POST | `/village/{id}/action-confirm` | アクション確認 (JSON) | village.js |
| POST | `/village/{id}/action` | アクション投稿 | 確認画面 |

- **CSRF**: `/village/*/action` `/village/*/action-confirm` は **CSRF 除外されず トークン必須** (`WolfMansionWebSecurityConfig.kt:53-57`、除外は confirm/say/update/api-login のみ)。→ 発言系 (say は除外) と**非対称**。移行時の CSRF 方針で要注意

## 4. 既存 JS の挙動

- `myself` (「〜は、」) + `target` + `message` を結合してアクション種別で投稿
- 文字数監視 (`actionLength` / `actionCount` / `actionLeftCount`)。制限超過は入力可だが送信不可 (確認ボタン disabled)
- 確認フロー中は `canAutoRefresh=false` (自動更新抑止)

## 5. 権限による分岐 / 6. 認可マスク

- 表示は `content.form.action.dispActionForm`
- 対象リストは `content.form.action.targetList`

## 7. 視覚比較

- 既存 `:8091`。アクションパネル、対象選択、確認 → 投稿

## 8. 関連 e2e ケース候補

- [ ] アクション発言: 対象選択 + 本文 → 確認 → 投稿 → ログ反映 (ACTION 種別)
- [ ] 対象「全員」/「選択しない」
- [ ] 文字数制限 (超過時は送信不可)

## メモ / 移行時の注意

- アクションは発言とは別パネル。React でも独立コンポーネント
- 確認 (`action-confirm`) → 投稿 (`action`) の 2 段フロー。発言確認の UIUX 方針は [village-say.md](village-say.md) と揃える (実際に表示される位置でプレビュー、キャンセルで入力欄に戻る)
- 文字数制限 (`actionCount` / `actionLength`) は村設定 ([village-settings.md](village-settings.md) の発言制限) 由来。REST レスポンスの restrict 情報に含める
- action の CSRF 非対称 (say は除外、action は必須) は移行時に統一方針で再設計
