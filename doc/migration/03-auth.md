# 03. 認証

## 方針

- 既存の **ID / パスワードによるログイン UX は維持** (player テーブル + BCrypt パスワードをそのまま利用)
- セッションベースから **JWT** に切り替え、バックエンドを stateless にする
- **JWT filter は自前実装** (Spring Security のフィルタチェーンに差し込む filter は自分で書く)
  - JWT の生成 / 検証 / クレーム取り出しなど低レベル処理は、必要に応じて既存ライブラリ (例: `jjwt`, `nimbus-jose-jwt` 等) を利用してよい
  - ライブラリ選定は後で詰める ([下記未確定事項](#未確定事項--要調査) 参照)

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

- access は両アプリに飛ばす必要があるので `Path=/`
- refresh は API の auth エンドポイントだけに送ればよいので Path を絞る

## CSRF 対策

- `SameSite=Lax` + state-changing endpoint は `Content-Type: application/json` 必須
- クロスオリジンからの非単純リクエストは preflight でブロックされる
- Spring Security 側は `csrf().disable()` で運用

## エンドポイント (ドラフト)

- `POST /api/v1/auth/login` … ID / パスワードで認証、access + refresh Cookie をセット
- `POST /api/v1/auth/refresh` … refresh Cookie から新しい access を発行
- `POST /api/v1/auth/logout` … 両 Cookie を消去
- `GET  /api/v1/auth/me` (or `/api/v1/me`) … 現在のログインプレイヤー情報を返す
  - フロント (SSR loader / クライアント) が「誰がログインしているか」を取得するための基本エンドポイント
  - 未認証時は 401 (or 204?) を返す → 規約は要確定
  - レスポンスに含める情報: player_id / name / 権限ロール / その他必要なプロフィール最低限
  - 詳細プロフィール (戦績等) は player リソース側 (`/api/v1/players/{id}`) に分けるかは要検討
- (補足) **新規登録 (signup)** が現状あるなら同じく `POST /api/v1/auth/signup` 等を用意する想定 (要確認)
- (補足) **パスワード変更** や **パスワード忘れ** のフローも要確認

## 未確定事項 / 要調査

- [ ] JWT ライブラリ選定 (jjwt / nimbus-jose-jwt / その他 / 完全自前)
- [ ] JWT の署名アルゴリズム (HS256 / RS256 等)
- [ ] 署名鍵の管理方法 (環境変数 / k8s Secret)
- [ ] refresh token の rotation 戦略 (使い捨て?、無効化リスト?)
- [ ] パスワード忘れ等の付随フロー (現状アプリにあるならどう移行するか)
- [ ] `me` エンドポイントの URL 規約 (`/api/v1/auth/me` vs `/api/v1/me` vs `/api/v1/players/me`)
- [ ] `me` 未認証時のレスポンス (401 / 204 / null body) の規約
- [ ] `me` レスポンスに含める情報の粒度 (最小情報 vs 詳細プロフィール、詳細は別エンドポイントへ分離?)
- [ ] 新規登録 (signup) / パスワード変更 の REST エンドポイント設計
- [ ] 管理者ロール等の権限分離をどう claim に乗せるか
- [ ] CORS 方針 (同一オリジンで運用するので原則不要だが、開発環境用に必要かもしれない)
- [ ] SSR 時の認証 (フロント SSR 側 loader で API を呼ぶ際の Cookie 転送)
