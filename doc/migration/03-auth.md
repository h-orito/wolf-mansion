# 03. 認証

## 方針

- 既存の **ID / パスワードによるログイン UX は維持** (player テーブル + BCrypt パスワードをそのまま利用)
- セッションベースから **JWT** に切り替え、バックエンドを stateless にする
- **JWT filter は自前実装** (Spring Security のフィルタチェーンに差し込む filter は自分で書く)
  - JWT の生成 / 検証 / クレーム取り出しなど低レベル処理は **`jjwt` (io.jsonwebtoken)** を利用
- **JWT 署名**: **HS256** (HMAC-SHA256)
  - 単一サービスで発行・検証ともに backend が行うため対称鍵で十分
  - 署名鍵は **環境変数** で渡す (OCL は `.env` / 将来 k8s に移ったら Secret)
  - 鍵ローテーション戦略は将来課題として保留

## パスワードポリシー (移行時に緩和 — 既存からの意図的逸脱)

現状の制約 (3〜12文字 / 英数のみ) を **signup / change-password で緩和**し、**login 側の形式バリデーションは撤廃**する。Step 3 (認証 REST 化) で対応。

- 緩和後: パスワードは **3〜60文字 / 印字可能 ASCII (`0x21`–`0x7E`、英数+記号)**。スペース・制御文字・マルチバイトは不可
- 上限 60 の根拠: **BCrypt は先頭 72 バイトのみ使用**。ASCII 60 文字 = 60 バイトで安全。DB は BCrypt ハッシュ (常に 60 文字固定) を保存するためカラム長は無関係
- **login (`/api/v1/auth/login`) は password 形式を検証しない** (NotNull のみ)。形式チェックを残すと緩和後パスワードでログイン不能になるため必須対応
- 長さ・文字種の定数は **zod (クライアント) とサーバで共有**
- 詳細は画面別: [auth-signup.md](screens/auth-signup.md) / [auth-login.md](screens/auth-login.md) / [auth-change-password.md](screens/auth-change-password.md)

## トークン仕様

| 項目 | 内容 |
|---|---|
| access token 有効期間 | 15 分 |
| refresh token 有効期間 | 14 日 |
| 格納先 | httpOnly Cookie |

### Cookie 属性

| Cookie | Domain | Path | Secure | HttpOnly | SameSite |
|---|---|---|---|---|---|
| `access_token` | `wolfort.dev` | `/` | ✓ | ✓ | Lax |
| `refresh_token` | `wolfort.dev` | `/wolf-mansion-api/api/v1/auth` | ✓ | ✓ | Lax |

- access は両アプリ (`/wolf-mansion` と `/wolf-mansion-api`) に飛ばす必要があるので `Path=/`
- refresh は API の auth エンドポイントだけに送ればよいので Path を絞る

### refresh token rotation (確定)

- **使い捨て rotation** を採用
- `/api/v1/auth/refresh` を呼ぶたびに新しい refresh token を発行し、古いものを無効化する
- DB に refresh token (またはその `jti` / ハッシュ) を保存し、利用済みフラグや失効を管理する
- 既に使用済みの refresh token が再度送られてきた場合は、**漏洩疑い**として該当ユーザーの全 refresh を失効させる (検討事項)
- 詳細スキーマは Step 1 (auth 実装ステップ) で詰める

## CSRF 対策

- `SameSite=Lax` + state-changing endpoint は `Content-Type: application/json` 必須
- クロスオリジンからの非単純リクエストは preflight でブロックされる
- Spring Security 側は `csrf().disable()` で運用

## CORS (確定)

- 本番では frontend (`https://wolfort.dev/wolf-mansion`) と backend (`https://wolfort.dev/wolf-mansion-api`) が **同一オリジン** (同一ホスト・同一スキーム・同一ポート) で動作するため、**CORS 設定は不要**
- 開発環境で frontend dev server (Vite 等) と backend を別ポートで動かす場合は、開発時のみ `localhost` 系オリジンを許可する設定を入れる
  - 許可 Origin は `application-local.yml` 等で個別管理 (本番 yml には CORS 許可リストを入れない)
- Spring Security の `csrf().disable()` と合わせて `cors()` 設定を最小限化

## エンドポイント (確定)

- `POST /api/v1/auth/login` … ID / パスワードで認証、access + refresh Cookie をセット
- `POST /api/v1/auth/refresh` … refresh Cookie から新しい access + 新しい refresh を発行 (使い捨て rotation)
- `POST /api/v1/auth/logout` … 両 Cookie を消去、DB 側の refresh token も失効
- `POST /api/v1/auth/signup` … 新規登録 (現行 wolf-mansion にあるフローを REST 化)
- `POST /api/v1/auth/password` … パスワード変更 (現行フローを REST 化)
- `GET  /api/v1/auth/me` … 現在のログインプレイヤー情報を返す
  - URL は **`/api/v1/auth/me`** (認証関連は `/auth/` 配下に集約)
  - 未認証時は **`401 Unauthorized`** を返す
  - レスポンスは **最小情報のみ**: `player_id` / `name` / `authorities[]`
  - 詳細プロフィール (戦績等) は別エンドポイント (`/api/v1/players/{id}` 等) に分離

### スコープ外

- **パスワード忘れ (パスワードリセット)** フローは現行アプリに **存在しない**ため、本移行でも追加しない

## 権限・認可 (確定)

- JWT payload には以下を載せる:
  - `sub`: `player_id` (数値)
  - `authorities`: `["ROLE_USER"]`, `["ROLE_USER", "ROLE_ADMIN"]` 等の文字列配列 (現行スキーマの権限名を踏襲)
  - `exp` / `iat` 等の標準クレーム
- 通常エンドポイントは **claim の authorities を信用**して認可判定する (HS256 で署名されているため改竄不可)
- **重要エンドポイント** (管理者操作 / 権限剥奪が即座に効くべき箇所) では **DB から再取得して再確認**する
  - 具体的に「どのエンドポイントを重要扱いするか」は Step 1 着手時に endpoint 一覧と合わせて定義
  - 想定: 管理者専用の村操作 (強制廃村、強制 Daychange など)、プレイヤー権限変更、参加者BAN 系
- JWT filter は claim から `UsernamePasswordAuthenticationToken` を組み立てて `SecurityContext` に詰め、Spring Security の `@PreAuthorize` / `hasRole()` で通常チェック
- 重要エンドポイント側では `application/coordinator/` または `domain/service/` 内で改めて DB から最新の権限を取得し直して assertion を行う

## SSR / CSR 認証境界 (確定)

- **認証不要のデータのみ SSR で取得**、**認証必要なデータは CSR (hydration 後にクライアント側で fetch)**
- SSR loader (React Router v7) は public API (例: 公開村一覧、村の公開情報) のみを叩く
- 認証必要な情報 (`/api/v1/auth/me`、参加者視点の村情報など) は、React コンポーネント内で `useEffect` / `useFetcher` 等を使って **クライアント側 fetch**
  - ブラウザの Cookie が自動で送られるため、Cookie 転送の設計は不要
  - 未認証時は backend が 401 を返すので、それを受けてログイン画面へリダイレクト
- 利点: SSR loader での Cookie 転送ロジックを実装しなくて済む、認証必要画面が初期 HTML に含まれないため CDN キャッシュとも親和性が高い
- 欠点: 認証必要画面の初期表示にロード状態 (skeleton 等) が必要。これは UI 設計で吸収

## 未確定事項 / 要調査

- [ ] refresh token DB スキーマの詳細 (`jti` 保存方式 / ハッシュ化 / 期限 / 漏洩疑い時の全失効仕様) — Step 1 着手前
- [ ] 「重要エンドポイント」の具体的リスト (DB 再確認を必須とするもの) — Step 1 endpoint 設計時
- [ ] 鍵ローテーション戦略 (HS256 鍵の更新手順) — 運用課題として将来検討
- [ ] 開発環境で CORS 許可する Origin のリスト (Vite dev server のポート確定後)
