# Migration Docs Index

Spring Boot + Thymeleaf → Spring Boot REST API + React Router v7 (SSR) 移行に関する設計ドキュメント群。

ルートの [`migration.md`](../../migration.md) は全体の index / 横断メモ。各領域の詳細はこの配下に分割する。

## ファイル一覧

| # | ファイル | 内容 |
|---|---|---|
| 01 | [overview.md](01-overview.md) | 目的・コンテキスト・アーキテクチャ概要・技術スタック・ディレクトリ構成 |
| 02 | [backend.md](02-backend.md) | バックエンド REST 化方針 (firewolf 参考, View 変換, 足音情報) |
| 03 | [auth.md](03-auth.md) | 認証 (JWT 自前 filter, Cookie 設計, CSRF 方針) |
| 04 | [frontend.md](04-frontend.md) | React Router v7 / Tailwind v4 / TanStack Query / Zustand / pnpm |
| 05 | [e2e.md](05-e2e.md) | Playwright (e2e/ 配下) |
| 06 | [infra-deploy.md](06-infra-deploy.md) | URL/Ingress 設計, k8s, OpenAPI → TS 型生成 |
| 07 | [workflow.md](07-workflow.md) | 開発フロー (ブランチ戦略, PR, pr-reviewer, ユーザー承認 → merge) |
| 08 | [step-plan.md](08-step-plan.md) | Step 分解ドラフト (順次更新) |

## 調査ドキュメント (Step 0 で作成済み)

Step 0 (現状調査) の成果物。3 系統に分けて配置している:

```
doc/migration/
  screens/      # 画面別 (機能・UI・呼び出す API・既存 JS 挙動・権限分岐・認可マスク・e2e ケース)
    README.md   #   全画面 index。村プレイ画面は village/ サブディレクトリに集約
    village/    #   村画面の機能ブロック別 md 群
  usecases/     # 横断ユースケース (縦 = 1 機構をレイヤー横断で深掘り)
    README.md   #   足音 reveal / Daychange / 認可マスク
  scenarios/    # 進行シナリオ (横 = 村ライフサイクルを時系列で追う happy-path)
    README.md   #   実体の authoring は e2e 検討時 (計画と器のみ用意済み)
  public-api-pinning.md   # 外部公開 API のピン留め (現状挙動の記録)
```
