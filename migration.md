# Spring Boot + Thymeleaf → Spring Boot API + React Router 移行計画

本ドキュメントは移行計画の **index** および **全領域横断のメモ置き場**。

各領域の詳細は `doc/migration/` 配下に分割している。

## 設計ドキュメント

[`doc/migration/README.md`](doc/migration/README.md) を参照。

| # | ファイル | 内容 |
|---|---|---|
| 01 | [overview](doc/migration/01-overview.md) | 目的・コンテキスト・アーキテクチャ概要・技術スタック・ディレクトリ構成 |
| 02 | [backend](doc/migration/02-backend.md) | バックエンド REST 化方針 (firewolf 参考, View 変換, 足音情報) |
| 03 | [auth](doc/migration/03-auth.md) | 認証 (JWT 自前 filter, Cookie 設計, CSRF 方針) |
| 04 | [frontend](doc/migration/04-frontend.md) | React Router v7 / Tailwind v4 / TanStack Query / Zustand / pnpm |
| 05 | [e2e](doc/migration/05-e2e.md) | Playwright (e2e/ 配下) |
| 06 | [infra-deploy](doc/migration/06-infra-deploy.md) | URL/Ingress 設計, k8s, OpenAPI → TS 型生成 |
| 07 | [workflow](doc/migration/07-workflow.md) | 開発フロー (ブランチ戦略, PR, pr-reviewer, ユーザー承認 → merge) |
| 08 | [step-plan](doc/migration/08-step-plan.md) | Step 分解ドラフト (順次更新) |

画面別の機能調査・REST 化対応表・e2e ケースは、今後 `doc/migration/screens/` 配下に画面単位で分けて配置する。

## 現状メモ (横断)

### ローカル作業ツリー
- 現在ローカル `main` ブランチ。`backend/` `frontend/` `e2e/` は **未作成**
- `.issues/` `.reviews/` ディレクトリは存在するが現時点では空

### CLAUDE.md
- バージョン記述を **Java 21 / Kotlin 1.9.25 / Spring Boot 3.5.9** に修正済み (本計画作業の冒頭で実施)
- 他の記述 (Thymeleaf 前提, Build & Run 等) は monorepo 化完了後に全面更新予定

### `.java-version` の不整合
- `.java-version` は `17` のままだが build.gradle.kts は Java 21
- Step 0 (環境整備) 候補に含める

## 未確認事項 / 質問候補

各領域ファイル末尾の「未確定事項 / 要調査」セクションに集約している。横断的な論点はここに追記する。

- (現時点なし — 各領域で詰める)
