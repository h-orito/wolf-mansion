# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 開発フロー (現在進行中の移行作業)

現在 `feature/monorepo` ブランチで Spring Boot + React Router v7 への大規模移行作業中 (詳細は `plan.md` 参照)。**移行完了まで以下を厳守**:

- **main には絶対に push / commit しない**。緊急 hotfix が必要になった場合のみ別途相談
- **作業ブランチは `feature/monorepo` から派生** させる
  - `git checkout feature/monorepo && git pull && git checkout -b step-N-xxx`
- **PR の base branch は常に `feature/monorepo`** (main ではない)
  - `gh pr create --base feature/monorepo` を必ず指定
  - `push-and-pr` / `ship-issue` 等のスキル実行時も同様、base を明示すること
- **PR 作成後は `pr-reviewer` サブエージェントで review → 指摘を反映 (fix) → 再 review、を必要に応じて繰り返してから squash merge**
  - レビュー結果は `.reviews/PR-<番号>.md` に出力される (pr-reviewer の挙動)
  - must-fix / should-fix は反映、nits は要否を判断
- step PR の merge は **squash merge** (1 step = 1 コミット)
- step は **シーケンシャル** に 1 つずつ進める (stacked branches 不使用、step N の merge 後に step N+1 着手)
- `feature/monorepo` には branch protection なし、ただしレビュー手順は遵守
- 全 step 完了時の `feature/monorepo` → `main` 取り込みは **merge commit (`--no-ff`)** で行う（PR経由）

### 引き継ぎ issue (`.issues/`) の扱い

step の review で残った should-fix / nits や、後続にまわした改善は `.issues/<n>-<slug>.md`
(ローカル管理、`.gitignore` 対象) に書き出して引き継ぐ。**次の step に着手する前に必ず `.issues/` を確認** し、
以下のルールで処理する:

- **次 step のスコープに含まれている issue** (例: step 2 で取り組む領域に対する step 1 の宿題)
  → そのまま step 2 PR の中で同時に解消する。issue ファイルは PR merge 後に削除。
- **次 step のスコープに含まれない issue**
  → **先に `ship-issue` (`/ship-issue <n>`) で別 PR を切って消化** してから step に進む。
    base は `feature/monorepo`、レビュー手順は通常の step PR と同じ。

`.issues/README.md` に一覧テーブルを置き、各 issue の本文には `recommended-when` (どの step と一緒に
潰すのが妥当か) を frontmatter で明示する。

## Project Overview

人狼ゲーム（Werewolf/Mafia）のWebアプリケーション「wolf-mansion」。Spring Boot + Kotlin（バックエンド）、MySQL + DBFlute（ORM）で構成。フロントエンドはモダン化中（旧 Thymeleaf テンプレートから SPA + REST API 構成へ移行予定）。

## Repository Layout

```
wolf-mansion/
  backend/        Spring Boot + Kotlin アプリ（Gradle ルート）
  frontend/       モダンフロントエンド（移行中、現状は空）
```

ビルドコマンドはすべて `backend/` 配下で実行する。

## Build & Run Commands

```bash
# ビルド（テストスキップ）
cd backend && ./gradlew build -x test

# テスト実行
cd backend && ./gradlew test

# 単一テストクラス実行
cd backend && ./gradlew test --tests "com.ort.app.domain.model.village.room.RoomSizeTest"

# アプリケーション起動（ローカル）
cd backend && ./gradlew bootRun

# Dockerイメージビルド（Jib）
cd backend && ./gradlew jibDockerBuild
```

- Java 21 / Kotlin 1.9 / Spring Boot 3.5
- ローカルDB: MySQL `werewolf_mansiondb` (port 3306, user: wmansion)
- アプリポート: 8089, コンテキストパス: `/wolf-mansion`
- プロファイル: `playground`（Jib）、`production`（本番）

## Architecture

レイヤードアーキテクチャ（DDD風）。パッケージ: `com.ort.app`（ソースは `backend/src/main/kotlin/com/ort/app/` 配下）

### レイヤー構成

```
api/            → Spring MVC Controller層（Thymeleafテンプレートを返す。API化移行中）
  helper/       → Controllerのヘルパー
  request/      → リクエストフォーム
  view/         → テンプレート用ViewModel
application/
  coordinator/  → 複数サービスをまたぐユースケース（トランザクション境界）
  service/      → アプリケーションサービス（DBアクセスの委譲）
domain/
  model/        → ドメインモデル（data class）+ Repositoryインターフェース
  service/      → ドメインロジック（ビジネスルール）
infrastructure/
  datasource/   → Repository実装（DBFlute Bhvを利用）
  discord/      → Discord通知
  mastodon/     → Mastodon連携
  twitter/      → Twitter連携
  slack/        → Slack通知
  microsoft/    → Microsoft翻訳API
fw/             → フレームワーク基盤（Security, Interceptor, Config, Exception）
```

### 重要な設計パターン

- **Repositoryパターン**: `domain/model/*/〜Repository.kt`にインターフェース定義、`infrastructure/datasource/`に実装
- **CDef（分類定義）**: DBFluteの自動生成コード`CDef`クラス（`src/main/java/com/ort/dbflute/allcommon/CDef.java`）が役職・陣営・村ステータスなどのenum的定数を管理。ドメインモデルとCDefの相互変換は`toCdef()`/`toModel()`で行う
- **Daychangeパターン**: 日付更新処理は`Daychange`データクラスに村の全状態を集約し、イミュータブルにcopyしながら処理を進行。`DaychangeCoordinator` → `DaychangeDomainService` → `PrologueDomainService`/`ProgressDomainService`/`EpilogueDomainService`の順に委譲
- **Abilityサービス**: 各役職の能力は`domain/service/ability/`配下に1能力1クラスで分離（60以上の能力サービスが存在）

### DBFlute（自動生成コード）

`backend/src/main/java/com/ort/dbflute/` 配下はDBFluteによる自動生成Javaコード。手動編集不可（`exbhv/`と`exentity/`のみカスタマイズ可能）。スキーマ定義は`backend/dbflute_wolf_mansiondb/`にある。

### 村のライフサイクル

`VillageStatus`: 募集中（Prologue） → 進行中（Progress） → エピローグ（Epilogue） → 終了（Finished）。廃村（Canceled）もある。

### デプロイ

mainへのpushで`deploy-ocl.yml`によりOCLサーバーへ自動デプロイ。

## Key Conventions

- ドメインモデルはKotlin data classで不変（状態変更は`copy()`で新インスタンスを返す）
- ゲーム内テキスト（ステータスメッセージ、役職名など）は日本語
- テンプレートは`backend/src/main/resources/templates/`にThymeleaf HTML（段階的に削除予定）
- 静的ファイルは`backend/src/main/resources/static/`
