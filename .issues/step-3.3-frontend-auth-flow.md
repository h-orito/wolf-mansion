---
id: step-3.3
title: frontend 認証フロー (login/logout/signup/me + RequireAuth + useMe) + e2e
type: enhancement
status: open
---

## 背景・現状

backend の認証 REST は Step 3.1 (#52) / 3.2 (#53) で完成済み:
`/api/v1/auth/{login,signup,refresh,logout,me,password}`。Cookie (access/refresh/id_register)
は backend が HttpOnly で発行する。一方 frontend (`frontend/`) は RR v7 雛形 (welcome 画面) のみで
認証 UI が無い。本 Issue で frontend 側の認証フローを実装し、e2e で疎通を担保する。

正本: `doc/migration/03-auth.md` (とりわけ「SSR/CSR 認証境界」「エンドポイント」「パスワードポリシー」)。

### backend 契約 (実装済・確認済)

- リクエスト/レスポンスは **camelCase JSON** (Jackson デフォルト、snake 変換なし)
  - login: `POST /api/v1/auth/login` body `{userId, password}` → 200 `MeResponse`
  - signup: `POST /api/v1/auth/signup` body `{userId, password}` → 200 `MeResponse` (自動ログイン + id_register cooldown Cookie)
  - password: `POST /api/v1/auth/password` body `{password, confirmPassword}` → 204 (認証必須)
  - logout: `POST /api/v1/auth/logout` → 204
  - me: `GET /api/v1/auth/me` → 200 `MeResponse` / 未認証 401
  - `MeResponse` = `{playerId: number, name: string, authorities: string[]}`
- エラーは **ProblemDetail (RFC7807)** `{type, title, status, detail, error}`。`error` はコード文字列
  (`authentication_failed` / `too_many_requests` / `business_error` / `validation_error` / `internal_error`)
- パスワードポリシー (`PasswordPolicy`): **3〜60 文字 / 印字可能 ASCII (`\x21`–`\x7E`)**
- signup userId 制約: 3〜12 文字 / `[a-zA-Z][a-zA-Z0-9\-_]*`
- dev は `cookie-secure: false` (http で動く)

## 対応方針

### API クライアント / 認証ロジック (CSR 専用)

- `app/lib/api.ts`: fetch ラッパ。base path は env で切替 (既定 `/wolf-mansion` = 現行 context-path)。
  JSON 固定 + `credentials: "include"`。ProblemDetail を解析し `ApiError(status, code, detail)` を throw。
- `app/features/auth/api.ts`: `login` / `signup` / `logout` / `changePassword` / `fetchMe`。
- `app/features/auth/schema.ts`: zod スキーマ。`PasswordPolicy` 相当の定数 (3〜60・印字可 ASCII) を
  暫定でフロントにも定義 (コメントで「正式共有は 3.4 OpenAPI」)。
- `app/features/auth/useMe.ts`: react-query で `GET /me`。401 は `null` を返す (エラーにしない)。
  **SSR 中は走らせない** (client-only guard)。03-auth.md の CSR 境界に従う。
- `app/features/auth/RequireAuth.tsx`: 認証ガード。ロード中はプレースホルダ、`me == null` なら
  `/login?returnTo=...` へリダイレクト、認証済なら children を描画。

### ルート / 画面 (最小 UI・デザインは詰めない)

- `/login` … login フォーム (react-hook-form + zod)。成功で returnTo か `/` へ。
- `/signup` … signup フォーム。成功で自動ログイン済 → returnTo か `/` へ。
- `/mypage` … **保護ルート** (RequireAuth)。me 情報表示 + logout ボタン + change-password リンク。
- `/change-password` … **保護ルート**。パスワード変更フォーム (確認一致は zod)。
- `/` (home) … 公開。useMe でログイン状態を表示し、未/済でリンクを出し分け。

### エラー UI ハンドリング

- 401 → ログイン画面リダイレクト (RequireAuth) / フォーム上は資格情報エラー表示
- 429 (`too_many_requests`) → 「しばらくしてから再試行してください」
- 400 重複 (`business_error`) / 確認不一致 → メッセージ表示

### dev クロスオリジン: Vite proxy (CORS は採らない)

- `frontend/vite.config.ts` に proxy: `/wolf-mansion` → `env BACKEND_ORIGIN` (既定 `http://localhost:8089`)、
  **rewrite しない** (backend の cookie Path=`/wolf-mansion/...` とブラウザ可視パスを一致させる)。
- 採用理由: backend 無変更で済む / 本番の同一オリジン構成に最も近い / cookie が確実に飛ぶ。
- e2e は frontend webServer に `BACKEND_ORIGIN=http://localhost:18089` を渡す。

### e2e (Playwright, local 専用)

- `e2e/tests/auth.spec.ts`:
  - **自己完結フロー**: signup (unique userId) → me 表示 → logout → 未ログイン → 同 ID で login → 再び me 表示
  - **未認証リダイレクト**: 未ログインで `/mypage` → `/login` に飛ぶ
- `e2e/playwright.config.ts`: frontend webServer の `env` に `BACKEND_ORIGIN` を追加。

## スコープ・注意

- **backend は変更しない** (REST は 3.1/3.2 で完成)。dev クロスオリジンは Vite proxy で吸収し CORS は入れない。
- **context-path rename (`/wolf-mansion-api`) は別サブ step**。API base は現行 `/wolf-mansion` 据置。
- **デザインは詰めない** (移行中方針。Step 12 復元 → Step 13 モダナイズ)。機能優先・最小マークアップ。
- **OpenAPI→TS 型生成 / zod 定数の正式共有は 3.4**。本 Issue は手書き型 + 暫定 zod 定数で進める。
- **SSR で認証 API を叩かない** (CSR 境界)。auth クエリは client-only。
- e2e は DB あいのり (リセットしない)。signup は毎回 unique な userId を生成。CI 非実行 (local 専用)。

## 影響範囲

- `frontend/app/lib/api.ts` (新規)
- `frontend/app/features/auth/{api,schema,useMe,RequireAuth}.tsx` (新規)
- `frontend/app/routes/{login,signup,mypage,change-password}.tsx` (新規) / `home.tsx` (改修)
- `frontend/app/routes.ts` (ルート追加) / `frontend/vite.config.ts` (proxy)
- `e2e/tests/auth.spec.ts` (新規) / `e2e/playwright.config.ts` (env 追加)

## 動作確認

- `cd frontend && pnpm typecheck && pnpm lint && pnpm build` が green
- backend bootRun (8089) + `cd frontend && pnpm dev` (5173) で手動:
  - signup / login / logout / me 表示 / change-password / 未認証 `/mypage` リダイレクト / 429 (連続失敗) 表示
- `cd e2e && pnpm install && pnpm run install:browsers && pnpm test` (webServer 自動起動) が green

## release-note

- (移行中・cutover 前のため利用者影響は無し。frontend 内部実装)

## 関連

- `doc/migration/03-auth.md` (認証方針 / CSR 境界 / エンドポイント / ポリシー)
- backend: `backend/src/main/kotlin/com/ort/app/api/auth/AuthController.kt`,
  `.../api/auth/{request,response}/*.kt`, `.../fw/exception/RestApiExceptionHandler.kt`,
  `.../fw/security/PasswordPolicy.kt`
- 過去 PR: #52 (3.1 JWT 基盤) / #53 (3.2 signup・password + レート制限)
