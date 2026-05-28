# 06. Infra / Deploy / 型共有

## URL / Ingress 設計

```
Ingress (wolfort.dev)
  /wolf-mansion-api/*  →  API Service        (Spring Boot)
  /wolf-mansion/*      →  Frontend Service   (Node 22 / RR v7 SSR)
```

- 同一ドメイン異パス構成
- ブラウザは同一オリジンで `/wolf-mansion-api` を直接叩く (CORS 不要)
- フロント (SSR loader) は **cluster-internal URL** で API を呼ぶ

### Legacy 公開 API パスの取り扱い (確定)

現行の外部公開 API (`/wolf-mansion/{village-record/list, village-record/latest-vid, skill/list, recruiting, api/village/{id}}`) は **frontend service が proxy** して backend に転送する。

- frontend service (Node) が **スルーパスの reverse proxy** として動作
  - リクエスト / レスポンスのヘッダ・ステータスコード・ボディを **そのまま透過**
  - 認証 Cookie も透過 (ただし legacy 公開 API は基本未認証想定)
- backend 側は legacy endpoint を **新しい context-path 配下に再配置**する
  - 例: `/wolf-mansion-api/legacy/village-record/list`
  - 既存 Controller の `@RequestMapping` は変えず、context-path の変更 (`/wolf-mansion` → `/wolf-mansion-api`) に追従するための **prefix 調整は frontend proxy 側で吸収**する
- 具体的な path mapping (どの外部パスをどの backend パスに変換するか) は Step 0 で legacy endpoint の完全な棚卸し後に Step 1 で決定

## ディレクトリ構成 (monorepo)

```
(root)/
  backend/   # Spring Boot (Kotlin) - REST API
  frontend/  # React Router v7 (SSR)
  e2e/       # Playwright
```

- 既存ルート直下の Gradle プロジェクトは `backend/` に移動する想定 (詳細は step 分解で確定)

## Backend context-path (確定)

- backend の **context-path を `/wolf-mansion-api` に設定**する
- Ingress では **rewrite せず** `/wolf-mansion-api/*` をそのまま backend に届ける
- 新規 endpoint: `/wolf-mansion-api/api/v1/...`
- legacy endpoint (移植後): `/wolf-mansion-api/legacy/...` (上記参照)

## k8s / コンテナ (確定)

- API + Node SSR を **k8s 上の別 Deployment / Service** として動かす (`wolf-mansion-backend` / `wolf-mansion-frontend`)
- rollout は独立
- **アーキテクチャ**: arm 系 (arm64)
- **backend**:
  - 既存の Jib (`./gradlew jibDockerBuild`) を流用
  - ベースイメージは現状の Jib 設定 (arm64 明示) を踏襲
  - イメージ名は `ghcr.io/h-orito/wolf-mansion-backend` にリネーム
- **frontend**:
  - Dockerfile を用意 (`frontend/Dockerfile`)
  - ベースイメージは **`node:22-bookworm`** (alpine 系は使わない / slim でもなく bookworm)
  - マルチステージビルドで build artifacts を最終イメージに含める
  - イメージ名は `ghcr.io/h-orito/wolf-mansion-frontend`

## 型共有 (OpenAPI → TypeScript) (確定)

- backend で SpringDoc OpenAPI を使い `/v3/api-docs` を出力
- frontend で `openapi-typescript` により TS 型を生成
- 生成された型を `frontend/app/api/types.ts` 等に取り込み、TanStack Query のクエリ関数で使う

### 生成・drift 検知フロー

- **手元コマンドで生成し、生成物を git commit**
  - 開発者は `pnpm gen:api` (or 相当の script) を叩く
  - script の中身: backend を起動 → `/v3/api-docs` を fetch → `openapi-typescript` で TS 型を生成 → `frontend/app/api/types.ts` に書き出し
  - 生成された型ファイルは git にチェックインする (frontend が単独でビルド可能になる)
- **CI で drift 検知**
  - CI で同じ script を走らせ、`git diff --exit-code` で生成物と commit 済みファイルの差分を検知
  - 差分があれば fail → 開発者に「生成し直して commit」を促す
- これにより API スキーマ変更時の追随漏れを防ぐ

## CI / デプロイ (要再設計)

- 現状: main push → `deploy-ocl.yml` で全てを OCL サーバへ自動デプロイ
- 移行後: backend / frontend を **2 つの GitHub Actions ワークフローに分割**
  - 例: `.github/workflows/deploy-backend-ocl.yml` / `.github/workflows/deploy-frontend-ocl.yml`
  - それぞれ `paths:` filter で **対象ディレクトリ (`backend/` or `frontend/`) の変更時のみ走る**ように切る (両方更新時は両方走る)
- 各ワークフローが ghcr へ別イメージで push
  - backend: `ghcr.io/h-orito/wolf-mansion-backend`
  - frontend: `ghcr.io/h-orito/wolf-mansion-frontend`
  - 現状の Jib 設定 (`ghcr.io/h-orito/wolf-mansion`) はイメージ名を **backend 向けにリネーム**する必要あり
- k8s 上は **別 Deployment / Service** として動かす
  - Ingress (wolfort.dev) で `/wolf-mansion-api` → backend Service、`/wolf-mansion` → frontend Service にルーティング
  - rollout は独立 (例: `kubectl rollout restart deployment/wolf-mansion-backend`)

## 未確定事項 / 要調査

- [ ] cluster-internal で frontend → API を呼ぶ URL (Service 名 / ポート) — Step 1 / インフラ準備時
- [ ] k8s manifest の置き場所 (リポジトリ内? 別?) と Deployment / Service の具体的 yaml — インフラ準備時
- [ ] 既存 `deploy-ocl.yml` の撤去タイミング (新ワークフロー稼働確認後) — 移行終盤
- [ ] Jib の image 名変更タイミング (`wolf-mansion` → `wolf-mansion-backend`) と旧イメージの扱い — Step 1 / インフラ準備時
- [ ] legacy 公開 API の path mapping 詳細 (どの外部 path → どの backend path) — Step 0 棚卸し後 Step 1 で決定
- [ ] frontend proxy の実装 (Node middleware / Express proxy / undici 直接 等) — Step 1
- [ ] ログ / メトリクス / トレース (現状の構成と整合させる) — 移行終盤
- [ ] DB マイグレーション戦略 (DBFlute スキーマ変更 + ReplaceSchema、本番投入手順) — 移行終盤
- [ ] frontend Dockerfile の具体構成 (multi-stage / non-root user / port) — Step 1
