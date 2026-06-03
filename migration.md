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

画面別の機能調査・REST 化対応表・e2e ケースは `doc/migration/screens/` 配下に画面単位で配置する。
横断的な仕様は 2 系統に分けて記録する: `doc/migration/usecases/` (縦 = 1 機構をレイヤー横断で深掘り。足音/Daychange/認可マスク) と `doc/migration/scenarios/` (横 = 時系列 happy-path。村作成→参加→開始→進行→終了。実体は e2e 検討時に authoring)。

## 計画フェーズの進捗

- **領域別の設計詳細 (01〜08) は一通り確定済み** (各 md の「確定」セクション参照)
  - backend: View 配置 (`api/response/`) / エラー (`ProblemDetail`) / DBFlute 現状維持 / 静的リソース frontend 移管 / API 併存ルール (既存凍結 + 新規 `/api/v1/`)
  - auth: jjwt / HS256 + 環境変数 / refresh 使い捨て rotation / `/api/v1/auth/me` (401・最小情報) / 権限 (claim + 重要 endpoint のみ DB 再確認) / CORS 不要 (同一オリジン) / 認証不要は SSR・認証必要は CSR
  - frontend: TanStack Query (server) + Zustand (UI のみ) / useMe + RequireAuth / RR ErrorBoundary + QueryClient onError + toast / react-hook-form + zod / 静的アセット public 同梱 (キャラチップは外部 URL) / i18n なし / oxlint recommend
  - e2e: Playwright webServer 自動起動 + 別ポート / DB あいのり + テストごと独立データ / Daychange は現行 endpoint / on-failure artifacts
  - infra: legacy API は frontend proxy / backend context-path `/wolf-mansion-api` / frontend Docker `node:22-bookworm` arm64 / OpenAPI 手元生成 + commit + CI drift 検知
  - workflow: Issue は Step 0 のみ先行・以降都度 / 動作確認は verify/run skill + step-plan 記述 / skill 採番を階層番号対応 / ktlint は gradle plugin + hook
  - step-plan: Step 0 完全完了後に Step 1 / 画面順 認証→ホーム→プロフィール→新規村→村画面 / e2e は実装後

- **残る主な未確定**は各領域の「未確定事項」に集約。多くは **Step 0 (調査) / Step 1 着手時に確定**する性質のもの

## 横断メモ

### `.java-version` の不整合
- `.java-version` は `17` のままだが build.gradle.kts は Java 21
- **Step 1 (環境整備)** で修正

### CLAUDE.md
- バージョン記述は **Java 21 / Kotlin 1.9.25 / Spring Boot 3.5.9** に修正済み
- 他の記述 (Thymeleaf 前提, Build & Run 等) は monorepo 化 (Step 2) 完了後に全面更新予定
