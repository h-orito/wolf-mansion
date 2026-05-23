# .k8s — wolf-mansion Kubernetes マニフェスト

API + Frontend SSR の 2 コンテナ構成を OCL k8s クラスタ (namespace: `default`) に
デプロイするための manifest 群。CI (`deploy-api-ocl.yml` / `deploy-frontend-ocl.yml`) は
`kubectl rollout restart` でイメージを更新するだけで、本ディレクトリの manifest を
自動 apply はしない。**初回 apply と構成変更時は手動で apply する**。

## 構成

| ファイル | 内容 |
|---|---|
| `api-deployment.yaml` | Deployment `wolf-mansion-api` (replicas: 1 固定、port 8081) |
| `api-service.yaml` | Service `wolf-mansion-api-svc` (ClusterIP:8081) |
| `api-configmap.yaml` | ConfigMap `wolf-mansion-api-config` (CORS / DB 接続の非機密値) |
| `frontend-deployment.yaml` | Deployment `wolf-mansion-frontend` (port 3000) |
| `frontend-service.yaml` | Service `wolf-mansion-frontend-svc` (ClusterIP:3000) |
| `frontend-configmap.yaml` | ConfigMap `wolf-mansion-frontend-config` (`API_BASE_URL`) |
| `ingress.yaml` | Ingress `wolf-mansion` (nginx、host `wolfort.dev`) |
| `secret.example.yaml` | Secret `wolf-mansion-secrets` のテンプレート |

## クラスタ依存の調整ポイント

apply 前に、自クラスタの構成に合わせて以下を確認・修正すること:

- `ingress.yaml` の `ingressClassName` — ここでは `nginx` 想定。クラスタの ingress
  controller が異なる場合は変更する。
- **TLS**: `ingress.yaml` には TLS セクションを置いていない。`wolfort.dev` の HTTPS は
  cert-manager 等クラスタ側の仕組みで自動付与される前提。手動で証明書を当てる場合は
  `spec.tls` を追加すること。
- **GHCR パッケージの公開設定**: 新規 push される `wolf-mansion-backend` / `wolf-mansion-frontend`
  パッケージは GHCR のデフォルトで **private**。
  - 単純なのは GitHub UI で package を **public** に変更すること。
  - private のまま使う場合は、PAT (`read:packages` スコープ) で `docker-registry` 型 Secret
    (`ghcr-pull-secret` 等) を作成し、各 Deployment の `imagePullSecrets`
    (manifest 内にコメントアウトされた箇所あり) を有効化する。
- `api-configmap.yaml` の `MYSQL_HOST` — クラスタ内 MySQL の Service 名に合わせる。
- `api-configmap.yaml` の `ALLOWED_ORIGINS` — 公開ドメインに合わせる。
- 各 Deployment の `resources` — 初期値なので実トラフィックに合わせて調整する。

## apply 手順

```bash
# 1. Secret を実値で用意 (secret.yaml は .gitignore 済み、git にコミットしない)
cp secret.example.yaml secret.yaml
# secret.yaml を実値で編集
kubectl apply -f secret.yaml

# 2. ConfigMap
kubectl apply -f api-configmap.yaml -f frontend-configmap.yaml

# 3. Deployment / Service
kubectl apply -f api-deployment.yaml -f api-service.yaml
kubectl apply -f frontend-deployment.yaml -f frontend-service.yaml

# 4. Ingress
kubectl apply -f ingress.yaml

# 5. 確認
kubectl rollout status deployment/wolf-mansion-api
kubectl rollout status deployment/wolf-mansion-frontend
```

## cutover メモ

- 旧構成は単一 Deployment `wolf-mansion` (Thymeleaf 一体型)。本構成への切替時に、
  旧 `deployment/wolf-mansion` と関連 Service / Ingress を停止・削除する。
- backend イメージは `ghcr.io/h-orito/wolf-mansion` → `ghcr.io/h-orito/wolf-mansion-backend`
  にリネーム済み (`backend/build.gradle.kts` の jib 設定)。
- キャラ画像など大量の静的画像はクラスタ外ホスティングへ移管する (plan.md「画像配信」)。
  移管作業自体はクラスタ操作とは別途、利用者側で実施する。
- 外部連携 (Discord ボット等) が叩く legacy endpoint のパスは旧実装と同一なので、
  ingress で `/wolf-mansion-api` 配下に解決されれば疎通する。
