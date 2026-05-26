# Spring Boot + Thymeleaf → Spring Boot API + React Router 移行計画

本ドキュメントはユーザーからの input を書き溜めていく作業用メモ。
実装はまだ行わない。input が一段落してから、整理・質問・計画化を進める。

## 現状（既知の情報）

- バックエンド: Spring Boot + Kotlin / Thymeleaf SSR
- DB: MySQL + DBFlute (ORM, 自動生成コード)
- パッケージ: `com.ort.app`（api / application / domain / infrastructure / fw のレイヤード構成）
- ドメイン: 人狼ゲーム（wolf-mansion）
- デプロイ: main push → `deploy-ocl.yml` で OCL サーバーへ自動デプロイ
- 既存ディレクトリに `backend/` `frontend/` `e2e/` が未追跡で存在（monorepo 化が進行中の可能性あり）

## ユーザーからの input

### 1. Context / 目的

人狼Webアプリ wolf-mansion を、Spring Boot + Thymeleaf の一体型構成から、Spring Boot REST API + React Router v7 (SSR) フロントエンドの分離構成に刷新する。

**目的:**
- フロントエンドの開発体験・UI 品質を現代化する (Tailwind, TanStack Query, 型安全な API クライアント)
- API サーバとして独立させることで、将来のクライアント追加 (モバイル等) に備える
- 認証をセッションから JWT に移行し、バックエンドを stateless にする

**前提:**
- 既存データ (player テーブル, BCrypt パスワード) はそのまま使う
- ID/パスワードログインの UX は維持
- マージ時に一括切替なので、移行途中の互換性は不要

### 2. 開発フロー

**ブランチ戦略:**
- **main には直接 push しない**。本移行作業中、main は触らない (緊急 hotfix のみ別途)
- 長寿命インテグレーションブランチとして **`feature/monorepo`** を使用
- 各 step PR は `feature/monorepo` から派生したブランチで作業し、base を `feature/monorepo` にして PR 作成
  - `git checkout feature/monorepo && git pull && git checkout -b step-N-xxx`
  - `gh pr create --base feature/monorepo`

**レビュー / マージ:**
- PR 作成後は `pr-reviewer` サブエージェントで review → 指摘反映 (fix) → 再 review を必要回数繰り返してから squash merge
  - レビュー結果は `.reviews/PR-<番号>.md` に出力
  - must-fix / should-fix は反映、nits は要否判断
- step PR の merge 方式: **squash merge** (1 step = 1 コミットで feature/monorepo に積む)
- step 間の依存関係: **シーケンシャル** (step N が merge されてから step N+1 着手)、stacked branches は使わない
- `feature/monorepo` には branch protection をかけない (AI 駆動で速く回すため。事故防止はレビュー手順で担保)

**最終リリース:**
- 全 step 完了後、`feature/monorepo` → `main` を **merge commit (`--no-ff`)** で取り込む
  - リリースポイントとして 1 つの merge commit + 各 step コミットを履歴に保持

### 3. 技術スタック

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

### 4. アーキテクチャ概要

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

**Cookie 設計:**
- domain = `wolfort.dev`
- `access_token`: `Path=/` で両アプリに飛ぶ
- `refresh_token`: `Path=/wolf-mansion-api/api/v1/auth` に限定

### 5. ディレクトリ構成 (monorepo)

```
(root)/
  backend/   # Spring Boot (Kotlin) - REST API
  frontend/  # React Router v7 (SSR)
  e2e/       # Playwright
```

### 6. バックエンド REST 化方針

- 似たプロジェクトである **[h-orito/firewolf](https://github.com/h-orito/firewolf)** の backend を参考にする
  - 特に「参加者に見せてはいけない情報」について、DomainModel から `XxxView` という形に変換して API Response にしている点が参考になる
- wolf-mansion 固有の差分: **足音情報**が存在する点が firewolf と異なる（View 変換時の取り扱いを別途検討）
- この領域は調査を進めた上で、細かく方針決め・タスク分解を行う必要がある（後続で詳細化）

### 7. 認証

- 既存の **ID / パスワードによるログイン UX は維持**（player テーブル + BCrypt パスワードをそのまま利用）
- セッションベースから **JWT** に切り替え、バックエンドを stateless にする
- **JWT filter は自前実装**（既存ライブラリの薄いラッパは使わず、必要な検証ロジックを自分で書く）
- トークン仕様（再掲）: access 15分 / refresh 14日、httpOnly Cookie 格納
  - `access_token`: `Path=/`, domain=`wolfort.dev`
  - `refresh_token`: `Path=/wolf-mansion-api/api/v1/auth`
- **CSRF 対策方針:** `SameSite=Lax` + state-changing endpoint は `Content-Type: application/json` 必須
  - クロスオリジンからの非単純リクエストは preflight でブロックされる
  - Spring Security 側は `csrf().disable()` で運用

### 8. Frontend

**技術スタック:**
- React Router v7 (framework mode, SSR 有効)
- TailwindCSS v4
- Vite
- `@tanstack/react-query` (server state)
- Zustand (UI state)
- `openapi-typescript` (型生成)
- heroicons (アイコン)

**パッケージマネージャ:**
- **pnpm** を使用
- サプライチェーンアタック対策:
  - グローバルの `minimumReleaseAge` を活かしつつ慎重に作業する
  - `ignore-scripts=true` で post-install スクリプトのデフォルト実行を抑止
  - postscript (postinstall 等) を持つパッケージは個別に精査

**UI 方針:**
- **既存 UI を完全に踏襲**する。色・余白・細部に至るまで完全に同一にする
- 各画面の必要な機能を細かく調査し、最終的に e2e やチェックリストで確認できるよう進める

### 9. E2E テスト

- **`e2e/` 配下**に実装する
- **Playwright** を使用
- パッケージマネージャは **pnpm**
- 機能の細かい調査により、e2e テストケースも設計し、細かくレビューしていく
  - frontend の画面ごとの調査結果と連動して、必要なテストケースを洗い出す

## 未確認事項 / 質問候補

<!-- input が落ち着いてからまとめる -->

## 計画ドラフト

<!-- input が落ち着いてからまとめる -->
