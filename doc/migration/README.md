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

## 画面別の詳細 (後続で作成)

各画面の機能調査・REST 化対応表・e2e ケース等は `screens/` 配下に画面単位で分けて配置する想定。

```
doc/migration/screens/
  <screen-name>.md   # 1 画面 1 ファイル、または画面単位のディレクトリ
```

具体的な命名規則・粒度は画面リスト確定後に決める。
