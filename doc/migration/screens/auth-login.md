# 画面: ログイン

## 概要

- **URL (既存)**: ログインフォームは **ホーム (`/`) に埋め込み**表示される (`loginPage("/")`)。`login.html` は `GET /login` 直接アクセス時やログイン失敗 (`/login?error=true`) 時のフォールバックページ
- **テンプレート**: `src/main/resources/templates/login.html`
- **担当 JS**: なし (フォーム POST のみ)
- **Controller**: `PlayerController.index(LoginForm)` (`GET /login`)。認証処理自体は Spring Security の formLogin
- **Security 設定**: `WolfMansionWebSecurityConfig.kt`
- **対象ユーザー**: 匿名 (未ログイン)

## 1. 機能 / 出来ることリスト

- ユーザID + パスワードでログイン
- 「ログイン状態を保持」(remember-me、hidden で常に `on`)
- 失敗時にエラーメッセージ表示

## 2. 表示要素・UI 状態

- ユーザID 入力 (text)
- パスワード入力 (password)
- hidden `remember-me=on`
- ログインボタン
- エラー時: `ユーザIDまたはパスワードが違います` (赤字)。`GET /login?error=true` で表示

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| POST | `/login` | Spring Security formLogin (`loginProcessingUrl`)。param: `userId` / `password` / `remember-me` | フォーム submit |
| GET | `/login` | フォールバックのログインページ (error 表示) | SSR |
| POST | `/api/login` | **別系統の JSON ログイン** (`VillageApiController`、CSRF 除外、`PlayerView` を返す) | (要調査: analyzer/SPA 用?) |

- 成功時: `defaultSuccessUrl("/")` → ホームへ
- 失敗時: `failureUrl("/login?error=true")`

## 4. 既存 JS の挙動

- なし (純粋なフォーム POST)

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 匿名 | フォーム表示・ログイン可能 |
| ログイン済 | 通常はヘッダーがログアウト等に変わる (ホーム側で出し分け、step-0.2 で詳細) |

## 6. 認可マスク

- なし

## 7. スクリーンショット

- (TODO) `/login` ページ、ホーム埋め込みフォーム、error 表示

## 8. 関連 e2e ケース候補

- [ ] 正常ログイン: 正しい userId/password → ホームにリダイレクト、ヘッダーがログイン済表示
- [ ] 失敗ログイン: 誤ったパスワード → エラーメッセージ表示
- [ ] remember-me: ログイン保持

## 入力仕様 (LoginForm)

| フィールド | 制約 |
|---|---|
| `userId` | NotNull / 3〜12文字 / `[a-zA-Z][a-zA-Z0-9\-_]*` (英字始まり) |
| `password` | NotNull / 3〜12文字 / `[a-zA-Z0-9]*` (英数のみ) |

## メモ / 移行時の注意

- **現状はセッション + remember-me Cookie** (`WolfMansionWebSecurityConfig`、rememberMe key `X7kmptSvar`)。移行後は **JWT (access/refresh Cookie)** に置換 ([03-auth.md](../03-auth.md))
- パスワードは **BCrypt** (`BCryptPasswordEncoder`)。player テーブル + ハッシュはそのまま流用
- ログインフォームがホーム (`/`) に埋め込まれている点は、React では「ヘッダーのログインフォーム or ログインページ」のどちらにするか設計判断が必要
- 新 endpoint: `POST /api/v1/auth/login`。既存 `POST /api/login` (JSON) の利用元を step-0.17 で確認し、互換維持要否を判断
- CSRF: 現状 `/api/login` は CSRF 除外。JWT + SameSite=Lax 化で CSRF 戦略を再設計
