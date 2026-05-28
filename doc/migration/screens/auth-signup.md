# 画面: 新規登録 (ID登録)

## 概要

- **URL (既存)**: `GET /new-player` (フォーム) / `POST /new-player` (作成)
- **テンプレート**: `src/main/resources/templates/new-player.html`
- **担当 JS**: `new-player.js` (中身は空)
- **Controller**: `PlayerController.index(PlayerCreateForm)` / `createPlayer(...)`
- **対象ユーザー**: 匿名

## 1. 機能 / 出来ることリスト

- ユーザID + パスワードで新規プレイヤー登録
- 登録成功後ホームへリダイレクト
- **連続登録防止** (クールダウン)

## 2. 表示要素・UI 状態

- 説明文: 「3文字以上12文字以下、IDは英数とハイフン・アンダーバー、1文字目は英字」
- ユーザID 入力 (text) + フィールドエラー表示
- パスワード入力 (password) + フィールドエラー表示
- 作成ボタン
- エラー: `errorMessage` (赤字)。ビジネス例外メッセージ or クールダウンメッセージ

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/new-player` | 登録フォーム表示 | SSR |
| POST | `/new-player` | プレイヤー登録 | フォーム submit |

## 4. 既存 JS の挙動

- `new-player.js` は空。バリデーションは全てサーバ側

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 匿名 | 登録可能 |
| ログイン済 | (通常はヘッダーから遷移しない) |

## 6. 認可マスク

- なし

## 7. スクリーンショット

- (TODO) 登録フォーム、バリデーションエラー、クールダウンエラー

## 8. 関連 e2e ケース候補

- [ ] 正常登録: 有効な userId/password → 登録 → ホームへ
- [ ] バリデーションエラー: 短すぎる/記号入りパスワード等
- [ ] 重複 ID: 既存 ID → ビジネス例外メッセージ
- [ ] 連続登録防止: 登録直後の再登録 → クールダウンメッセージ

## 入力仕様 (PlayerCreateForm)

| フィールド | 制約 (現状) |
|---|---|
| `userId` | NotNull / 3〜12文字 / `[a-zA-Z][a-zA-Z0-9\-_]*` |
| `password` | NotNull / 3〜12文字 / `[a-zA-Z0-9]*` |

## 拡張仕様 (移行時に変更 — 既存からの意図的逸脱)

> **userId は現状維持**。パスワードのみ制約を緩和する。

| フィールド | 移行後の制約 |
|---|---|
| `userId` | 変更なし (3〜12文字 / `[a-zA-Z][a-zA-Z0-9\-_]*`) |
| `password` | NotNull / **3〜60文字** / **印字可能 ASCII (英数 + 記号, `0x21`–`0x7E`)** を許可 |

- **最大長を 12 → 60 に拡張**
  - 上限根拠: BCrypt は入力先頭 **72 バイト**のみ使用 (それ以降は黙って無視)。ASCII 60 文字 = 60 バイトで安全に収まる
  - DB は生パスワードを保存せず **BCrypt ハッシュ (常に 60 文字固定)** を保存するため、**DB カラム長は制約にならない**
  - マルチバイト文字は許可しない (英数記号のみなら 1 文字 = 1 バイトで 72 バイト制約と齟齬が出ない)
- **記号を許可** (`[a-zA-Z0-9]` → 印字可能 ASCII)。スペース・制御文字は不可 (前後トリム問題を避けるため。許可するなら要再検討)
- **最小長 3 は現状維持** (引き上げは別途の判断とする)
- バリデーションは **zod (クライアント) + サーバ**の 2 箇所で同一の長さ/文字種定数を共有する ([04-frontend.md](../04-frontend.md))
- 関連: ログイン側 (`LoginForm.password`) の形式バリデーションも撤廃が必要 ([auth-login.md](auth-login.md) 参照)

## メモ / 移行時の注意

- **連続登録防止**: Cookie `id_register` (値 `true`, maxAge 30分, path `/wolf-mansion/`) が存在すると登録拒否。メッセージ: 「連続して複数のIDを取得することはできません。時間をおいてから...」
  - 移行後も同等の **連続登録防止**を維持する必要あり。Cookie path が `/wolf-mansion/` 前提な点は新 URL 構成 ([06-infra-deploy.md](../06-infra-deploy.md)) で要調整
- 登録は `PlayerService.registerPlayer(userId, password)`。重複等は `WolfMansionBusinessException`
- 新 endpoint: `POST /api/v1/auth/signup` ([03-auth.md](../03-auth.md))。エラーは `ProblemDetail` で返す
- パスワードは BCrypt で保存
