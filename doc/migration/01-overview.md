# 01. Overview

## 目的

人狼Webアプリ wolf-mansion を、Spring Boot + Thymeleaf の一体型構成から、Spring Boot REST API + React Router v7 (SSR) フロントエンドの分離構成に刷新する。

- フロントエンドの**開発体験・技術スタックを現代化**する (Tailwind, TanStack Query, 型安全な API クライアント)
  - ⚠️ **見た目の UI/UX は現状を忠実に踏襲する** (本移行ではモダナイズしない)。視覚的なモダナイズは cutover 後の別フェーズ (Step 13) で行う。詳細は [04-frontend.md](04-frontend.md) の「UI/UX 現状維持原則」
- API サーバとして独立させることで、将来のクライアント追加 (モバイル等) に備える
- 認証をセッションから JWT に移行し、バックエンドを stateless にする

## 前提・制約

- 既存データ (player テーブル, BCrypt パスワード) はそのまま使う
- ID / パスワードによるログイン UX は維持
- マージ時に一括切替なので、**移行途中の互換性は不要**

## 技術スタック

| 領域 | 選択 |
|---|---|
| Backend | Spring Boot 3.5 + Kotlin 1.9 + Java 21 + DBFlute (既存継続) |
| Backend 役割 | Thymeleaf 全廃、REST API 専用 |
| 認証 | JWT (httpOnly Cookie 格納、access 15分 + refresh 14日) |
| Frontend | React Router v7 framework mode (SSR 有効) + Vite + TailwindCSS v4 + heroicons |
| データ層 | TanStack Query (server state) + Zustand (UI state) |
| 型共有 | SpringDoc OpenAPI → `openapi-typescript` で TS 型生成 |
| デプロイ | k8s 上の別コンテナ (API + Node SSR) |
| URL 構成 | `https://wolfort.dev/wolf-mansion-api` (API) / `https://wolfort.dev/wolf-mansion` (Frontend)、同一ドメイン異パス |

## アーキテクチャ概要

```
                          Ingress (wolfort.dev)
                          ┌────────────────────────┐
                          │ /wolf-mansion-api/* → API Service
                          │ /wolf-mansion/*     → Frontend Service
                          └────────────────────────┘
                                     │
                ┌────────────────────┴────────────────────┐
                ▼                                         ▼
   Spring Boot (Kotlin)                         Node 22 (RR v7 SSR)
   - REST endpoints /api/v1/*                   - SSR loader が
   - JWT 発行/検証                               cluster-internal URL で
   - DBFlute → MySQL                              API を呼ぶ
                                                - browser は同一オリジン
                                                  /wolf-mansion-api を直叩き
```

### Cookie 設計 (要点)

- domain = `wolfort.dev`
- `access_token`: `Path=/` で両アプリに飛ぶ
- `refresh_token`: `Path=/wolf-mansion-api/api/v1/auth` に限定

詳細は [03-auth.md](03-auth.md) を参照。

## ディレクトリ構成 (monorepo)

```
(root)/
  backend/   # Spring Boot (Kotlin) - REST API
  frontend/  # React Router v7 (SSR)
  e2e/       # Playwright
```

詳細は各領域ドキュメント、およびインフラ周りは [06-infra-deploy.md](06-infra-deploy.md) を参照。
