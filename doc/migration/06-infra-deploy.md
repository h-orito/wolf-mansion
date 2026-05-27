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

### Legacy 公開 API パスの取り扱い

現行の外部公開 API は `/wolf-mansion/...` 配下にあるが、新構成ではこのプレフィックスが **frontend service** に割り当てられるため、そのままでは backend に届かない。互換性は維持する必要がある ([02-backend.md](02-backend.md) 参照) ので、frontend 側で以下のいずれかの仕組みを用意する想定:

- **案 A: frontend が proxy**
  - frontend service が `/wolf-mansion/{api,village-record,skill,recruiting,...}` を backend にそのまま転送
  - レスポンスヘッダ含めて素通し
- **案 B: React Router の resource route (`application/json` 返却)**
  - RR の resource route として実装し、loader 相当のコード内で backend を呼んで JSON を返す
  - レスポンス整形 / キャッシュ制御を柔軟に書ける反面、互換性 (ヘッダ / 細部) は実装次第

採用案は別途決定 (要 [未確定事項](#未確定事項--要調査) 参照)。

## ディレクトリ構成 (monorepo)

```
(root)/
  backend/   # Spring Boot (Kotlin) - REST API
  frontend/  # React Router v7 (SSR)
  e2e/       # Playwright
```

- 既存ルート直下の Gradle プロジェクトは `backend/` に移動する想定 (詳細は step 分解で確定)

## k8s / コンテナ

- API + Node SSR を **k8s 上の別コンテナ**として動かす
- 既存の Jib (`./gradlew jibDockerBuild`) は backend 用に流用
- frontend は別 Dockerfile (Node 22) を用意

## 型共有 (OpenAPI → TypeScript)

- backend で SpringDoc OpenAPI を使い `/v3/api-docs` を出力
- frontend で `openapi-typescript` により TS 型を生成
- 生成された型を `frontend/app/api/types.ts` 等に取り込み、TanStack Query のクエリ関数で使う

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

- [ ] cluster-internal で frontend → API を呼ぶ URL (Service 名 / ポート)
- [ ] backend の context-path をどうするか (現在 `/wolf-mansion`、Ingress とどう揃えるか)
- [ ] frontend Docker のベースイメージ (`node:22-alpine`? slim?)
- [ ] k8s manifest の置き場所 (リポジトリ内? 別?) と backend / frontend 別 Deployment / Service の構成
- [ ] 既存 `deploy-ocl.yml` の撤去タイミング (新ワークフロー稼働確認後)
- [ ] Jib の image 名変更タイミング (`wolf-mansion` → `wolf-mansion-backend`) と旧イメージの扱い
- [ ] OpenAPI 型生成のフロー (CI で自動? 手元コマンド?)
- [ ] OpenAPI スキーマの drift 検知 (生成された型と実装の不整合をどう検知するか)
- [ ] 静的リソース (画像等) の配信パス (backend からの提供? frontend public/?)
- [ ] ログ / メトリクス / トレース (現状の構成と整合させる)
- [ ] DB マイグレーション戦略 (DBFlute スキーマ変更 + ReplaceSchema、本番投入手順)
- [ ] Legacy 公開 API (`/wolf-mansion/...`) を frontend 側でどう処理するか (proxy / resource route のどちらか) 確定
- [ ] proxy 採用時のリクエスト/レスポンスヘッダ・ステータスコード透過の確認
