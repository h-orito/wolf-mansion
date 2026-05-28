# 画面: パスワード変更

## 概要

- **URL (既存)**: `GET /change-password` (フォーム) / `POST /change-password` (変更)
- **テンプレート**: `src/main/resources/templates/change-password.html`
- **担当 JS**: なし
- **Controller**: `PlayerController.changePasswordIndex(...)` / `changePassword(...)`
- **バリデータ**: `PlayerChangePasswordFormValidator` (password == confirmPassword チェック)
- **対象ユーザー**: ログイン済 (`/change-password` は `fullyAuthenticated()`)

## 1. 機能 / 出来ることリスト

- ログイン中ユーザー自身のパスワード変更

## 2. 表示要素・UI 状態

- 変更後パスワード (password) + エラー表示
- パスワード（確認用） (password) + エラー表示
- hidden `remember-me=on`
- 送信ボタン (ラベルが `ログイン` になっている — コピペ由来の癖、要修正候補)
- エラー: `errorMessage` / フィールドエラー

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/change-password` | フォーム表示 (要認証) | SSR |
| POST | `/change-password` | パスワード更新 | フォーム submit |

## 4. 既存 JS の挙動

- なし

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 匿名 | アクセス不可 (`fullyAuthenticated`、未認証はログインへ) |
| ログイン済 | 自身のパスワード変更可 |

## 6. 認可マスク

- なし

## 7. スクリーンショット

- (TODO) フォーム、不一致エラー

## 8. 関連 e2e ケース候補

- [ ] 正常変更: 一致するパスワード → 変更 → ホームへ
- [ ] 不一致: password ≠ confirmPassword → エラー
- [ ] 未認証アクセス: ログイン画面へ誘導

## 入力仕様 (PlayerChangePasswordForm)

| フィールド | 制約 |
|---|---|
| `password` | NotNull / 3〜12文字 / `[a-zA-Z0-9]*` |
| `confirmPassword` | NotNull / 3〜12文字 / `[a-zA-Z0-9]*` |
| (相関) | `password == confirmPassword` (Validator、エラーキー `PlayerChangePasswordForm.validator.password`) |

## メモ / 移行時の注意

- 認可: 現状 `fullyAuthenticated()` (remember-me だけでは不可、明示ログインが必要)。移行後 JWT でも「明示認証必須」の挙動を踏襲するか要検討 ([03-auth.md](../03-auth.md))
- 更新は `PlayerService.updatePassword(username, password)`
- 新 endpoint: `POST /api/v1/auth/password`。確認用一致チェックはクライアント zod + サーバ両方で
- テンプレの送信ボタンラベル `ログイン` は誤り → React 化時に「変更」等へ修正
- hidden `remember-me` はこの画面では無意味 (POST 先が認証でない) → 移行時に除去
