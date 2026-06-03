# 画面: 村画面 — ユーザー設定モーダル (表示設定 / 通知設定)

> 村画面の `#modal-dsetting` モーダル (`village/display-settings.html`)。footer-menu の「設定」から開く。**表示設定 (クライアント側・Cookie)** と **Discord 通知設定 (サーバ側)** の 2 セクションを持つ。村画面ベースは [village-base.md](village-base.md)。

## 概要

- **テンプレート**: `village/display-settings.html` (モーダル `#modal-dsetting`)
- **担当 JS**: `village.js` (表示設定の Cookie 保存・復元、自動更新トグル等)
- **Controller**: 通知設定のみ `VillageNotificationController` (`POST /village/{id}/notification-setting`)。表示設定は Cookie のみで API なし
- **対象ユーザー**: 全員 (表示設定) / 参加者 (通知設定。webhook は本人の参加情報前提)

## 1. 機能 / 出来ることリスト

- **表示設定** (クライアント側、Cookie 保存): ページ分割・自動更新・文字装飾ボタン表示・画像/文字サイズ・リセット
- **Discord 通知設定** (サーバ側保存): webhook URL + 各種イベント通知 ON/OFF + 通知キーワード

## 2. 表示要素・UI 状態

### 表示設定 (`<form>`、Cookie のみ。API なし)

- **ページ分割**: 「ページ分割する」トグル + ページあたり表示発言数 (10/20/30/50/100/200/300/500, `display-settings.html:30-52`)
- **更新通知**: 「更新検知時自動で読み込む」トグル (最新ページにいる時に自動更新, `:54-64`)
- **便利機能**: 「文字装飾ボタン」表示トグル (`:66-75`)
- **発言表示**: 画像を大きく / 文字を大きく トグル (`:77-93`)
- **表示設定のリセット** (`:95-`)

### 移行時のデフォルト値 (cookie/localStorage 未設定時、確定)

現状のデフォルトから変更し、未設定 (新規ユーザー) 時は以下を初期値とする:

| 項目 | 移行後デフォルト |
|---|---|
| ページ分割 | **する** (ページあたり **50**) |
| 更新検知時自動で読み込む | **true** (ON) |
| 文字装飾ボタン | **表示する** |
| 画像を大きく | **off** |
| 文字を大きく | **画面サイズが lg 以上なら on、それ未満は off** (レスポンシブ初期値) |

→ cookie/localStorage に保存値があればそれを優先、無い場合に上記を適用。

### Discord 通知設定 (`<form>` → `/village/{id}/notification-setting`, `display-settings.html:104-159`)

- **webhookUrl** (Discord webhook、バリデーションあり)
- イベント通知: **開始通知** (`villageStart`、進行中遷移) / **日付更新通知** (`daychange`) / **エピローグ通知** (`epilogue`)
- 発言通知: **秘話通知** (`secretSay`) / **アンカー通知** (`anchorSay`、梟視点は除く) / **役職窓通知** (`abilitySay`、UI ラベル「役職窓通知」) / **キーワード通知** (`keyword`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| POST | `/village/{id}/notification-setting` | Discord 通知設定の保存 (`VillageNotificationForm`) |
| (Cookie) | - | 表示設定はブラウザ Cookie に保存 (API なし) |

- フォーム: `VillageNotificationForm` (`webhookUrl`, `villageStart`, `daychange`, `epilogue`, `secretSay`, `anchorSay`, `abilitySay`, `keyword`)

## 4. 既存 JS の挙動

- **表示設定**: 各トグル/select を Cookie に保存・復元 (`village.js`)。画像サイズは発言再読み込みで反映、文字装飾ボタンは say-form の装飾領域トグルと連動
- **通知設定保存時**: `notificationService.notifyTest` で webhook にテスト通知が飛ぶ (`VillageNotificationController.kt:68`)

## 5. 権限による分岐 / 6. 認可マスク

- 表示設定は全員 (匿名含む、Cookie のみ)
- 通知設定は参加者本人の webhook 設定。マスクなし

## 7. 視覚比較

- 既存 `:8091`。設定モーダル (表示設定の各トグル、Discord 通知設定フォーム)

## 8. 関連 e2e ケース候補

- [ ] 表示設定: ページ分割/サイズ変更 → Cookie 保存・復元
- [ ] 自動更新トグル → 更新検知時の挙動
- [ ] Discord 通知設定の保存 → テスト通知

## メモ / 移行時の注意

- **表示設定 (Cookie) は localStorage + Zustand へ** ([village-base.md](village-base.md) と同方針)。API は不要
- **通知設定 (Discord webhook)** は `infrastructure/discord/` 連携。移行後も維持。REST 化: `PUT /api/v1/villages/{id}/me/notification-setting` 等
- 保存時テスト通知 (`notifyTest`) の挙動も維持
- 文字装飾ボタン表示トグルは発言フォーム ([village-say.md](village-say.md)) の装飾領域と連動
