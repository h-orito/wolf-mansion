# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

人狼ゲーム（Werewolf/Mafia）のWebアプリケーション「wolf-mansion」。Spring Boot + Kotlin（バックエンド）、Thymeleaf（テンプレートエンジン）、MySQL + DBFlute（ORM）で構成されたサーバーサイドレンダリングのWebアプリ。

## Monorepo 構成

monorepo 移行中。3 層構成:

- **`backend/`** — Spring Boot + Kotlin + DBFlute（Gradle プロジェクト）
- **`frontend/`** — React Router v7 + Vite（pnpm）
- **`e2e/`** — Playwright（pnpm）

## Build & Run Commands

### Backend

```bash
cd backend
./gradlew build -x test          # ビルド（テストスキップ）
./gradlew test                   # テスト実行
./gradlew test --tests "com.ort.app.domain.model.village.room.RoomSizeTest"  # 単一テスト
./gradlew bootRun                # アプリケーション起動（localhost:8089）
./gradlew jibDockerBuild         # Docker イメージビルド（ghcr.io/h-orito/wolf-mansion-backend）
```

- Java 21 / Kotlin 1.9.25 / Spring Boot 3.5.9
- ローカルDB: MySQL `werewolf_mansiondb` (port 4306, user: wmansion)
- アプリポート: 8089, コンテキストパス: `/wolf-mansion`
- プロファイル: `playground`（Jib が指定。本番コンテナもこのプロファイルで動作）、`production`（現状どこからも指定されておらず未使用）

### Frontend

```bash
cd frontend
pnpm dev                         # 開発サーバー（localhost:5173）
pnpm build                       # プロダクションビルド
pnpm gen:api                     # OpenAPI クライアント生成（backend 8089 起動状態で実行）
pnpm lint                        # oxlint
pnpm format                      # oxfmt（整形実行）
pnpm format:check                # oxfmt（チェックのみ）
```

### E2E

```bash
cd e2e
pnpm test                        # Playwright e2e（backend 18089 / frontend 15173 を自動起動）
pnpm test tests/xxx.spec.ts      # 単一スペック実行
```

## Architecture

レイヤードアーキテクチャ（DDD風）。パッケージ: `com.ort.app`

### レイヤー構成

```
api/            → Spring MVC Controller層（Thymeleafテンプレートを返す）
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
- **CDef（分類定義）**: DBFluteの自動生成コード`CDef`クラス（`backend/src/main/java/com/ort/dbflute/allcommon/CDef.java`）が役職・陣営・村ステータスなどのenum的定数を管理。ドメインモデルとCDefの相互変換は`toCdef()`/`toModel()`で行う
- **Daychangeパターン**: 日付更新処理は`Daychange`データクラスに村の全状態を集約し、イミュータブルにcopyしながら処理を進行。`DaychangeCoordinator` → `DaychangeDomainService` → `PrologueDomainService`/`ProgressDomainService`/`EpilogueDomainService`の順に委譲
- **Abilityサービス**: 各役職の能力は`domain/service/ability/`配下に1能力1クラスで分離（60以上の能力サービスが存在）

### REST API 設計方針（最重要）

Clean Architecture + DDD に基づき、**画面専用の API やレスポンスを作らない**。

- **可能な限りドメインモデルをそのまま返す**。隠すべき情報がないドメインオブジェクトは専用の Response DTO を作らず直接シリアライズする
- **Response DTO を作ってよいケース**: (1) 認可マスクにより隠すべき情報がある場合、(2) 大量取得で不要な情報を削ぎ落とす場合（一覧 API 等）
- **複数のドメイン情報が必要な画面では、個別の API を叩いて frontend で組み立てる**

### DBFlute（自動生成コード）

`backend/src/main/java/com/ort/dbflute/` 配下はDBFluteによる自動生成Javaコード。手動編集不可（`exbhv/`と`exentity/`のみカスタマイズ可能）。スキーマ定義は`backend/dbflute_wolf_mansiondb/`にある。

### 村のライフサイクル

`VillageStatus`: 募集中（Prologue） → 進行中（Progress） → エピローグ（Epilogue） → 終了（Finished）。廃村（Canceled）もある。

### デプロイ

- feature/monorepoへのpushで `deploy-backend.yml` / `deploy-frontend.yml` により OCL サーバー（k8s の `wolf-mansion-backend` / `wolf-mansion-frontend`）へ自動デプロイ。**マージ = 即デプロイ**で、2026-08 時点でこれが唯一の稼働系（本番）。
- main の `deploy-ocl.yml`（旧 `wolf-mansion` Deployment 向け）は monorepo 移行前のもので、旧 Deployment は k8s 上に存在しない。

## Key Conventions

- セッション開始時に、Serena の `initial_instructions` を呼び出し、関連するプロジェクトメモリを読み込んでからコンテキストを把握すること
- コードベースの構造や規約に変更があった場合は、Serena の edit_memory / write_memory で該当メモリを更新すること
- タスク/Issue ごとに必ず新しいブランチを作成してから変更する。feature/monorepo やその他の共有ブランチに直接コミットしない
- API の型を編集する場合は types.ts を直接編集せず、gen:api で再生成する
- バグ調査時は、まず正確な根本原因を特定してユーザーに説明し、修正方針（特にUIの見た目・挙動に関わる場合）についてユーザーの合意を得てから編集する
- 出力が長くなるコマンド（build/test/e2e 等）はログをファイルにリダイレクトし、失敗箇所だけ抽出して読む（ログ全文を会話に持ち込まない）
- ドメインモデルはKotlin data classで不変（状態変更は`copy()`で新インスタンスを返す）
- ゲーム内テキスト（ステータスメッセージ、役職名など）は日本語
- テンプレートは`backend/src/main/resources/templates/`にThymeleaf HTML
- 静的ファイルは`backend/src/main/resources/static/`

### コメント・実装の規約（重要・レビュー指摘の再発防止）

- **コメントに移行 step/issue 番号を書かない**（`(Step 4)` `step-4.1 で…` 等）。step は Issue / git 履歴で追う。コードのコメントは恒久的に正しい説明だけにする。
- **「既存を再現する」旨のコメントを書かない**（``:8091` 基準で再現` `既存 `<h1 class="h4">` 相当` `legacy の◯◯相当` 等）。見た目の再現は **コンポーネント（Button / Heading / Panel / FormRow など）や CSS class の単位**に閉じ込め、その primitive を使い回す。レイアウト（`AuthLayout` 等）の単位で個々の見出し・ボタンを再現するのは誤り。コメントは「なぜそうするか（非自明な理由）」だけを書く。
- **その場しのぎ（inline・重複）の実装をしない**。ボタン・フォーム行・ラジオ・パネル・見出しなどの UI 部品は `frontend/app/components/ui/` に再利用可能なコンポーネントとして作り、画面側はそれを組み合わせる。
- **GET の検索系パラメータは個別 `@RequestParam` で受けず、`XxxRequest` クラスでまとめて受ける**。ドメインクエリへの変換（`toQuery()` 等）もその Request クラスに閉じ込め、Controller は薄く保つ。
- **一覧の並び順・絞り込み・ページングは API 側で指定可能にする**（frontend で `reverse()` 等の整形をしない）。