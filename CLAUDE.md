# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

人狼ゲーム（Werewolf/Mafia）のWebアプリケーション「wolf-mansion」。Spring Boot + Kotlin（バックエンド）、Thymeleaf（テンプレートエンジン）、MySQL + DBFlute（ORM）で構成されたサーバーサイドレンダリングのWebアプリ。

## Monorepo 構成

monorepo 移行中。Gradle プロジェクト一式は **`backend/` 配下に移動済み**（root に Gradle ファイルは無い）。`frontend/`（RR v7）/ `e2e/`（Playwright）は後続サブ step で追加予定。以下の Gradle コマンドは **`backend/` で実行**する。

## Build & Run Commands

```bash
cd backend

# ビルド（テストスキップ）
./gradlew build -x test

# テスト実行
./gradlew test

# 単一テストクラス実行
./gradlew test --tests "com.ort.app.domain.model.village.room.RoomSizeTest"

# アプリケーション起動（ローカル）
./gradlew bootRun

# Dockerイメージビルド（Jib、image 名 ghcr.io/h-orito/wolf-mansion-backend）
./gradlew jibDockerBuild
```

- Java 21 / Kotlin 1.9.25 / Spring Boot 3.5.9
- ローカルDB: MySQL `werewolf_mansiondb` (port 4306, user: wmansion)
- アプリポート: 8089, コンテキストパス: `/wolf-mansion`
- プロファイル: `playground`（Jib）、`production`（本番）

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

mainへのpushで`deploy-ocl.yml`によりOCLサーバーへ自動デプロイ。

## Key Conventions

- 出力が長くなるコマンド（build/test/e2e 等）はログをファイルにリダイレクトし、失敗箇所だけ抽出して読む（ログ全文を会話に持ち込まない）
- ドメインモデルはKotlin data classで不変（状態変更は`copy()`で新インスタンスを返す）
- ゲーム内テキスト（ステータスメッセージ、役職名など）は日本語
- テンプレートは`backend/src/main/resources/templates/`にThymeleaf HTML
- 静的ファイルは`backend/src/main/resources/static/`

### リンクの規約（最重要・再発防止）

- **未移行画面へのリンクも SPA URL（react-router `<Link>` / `<LinkButton>`）を使う**。`legacyUrl` + `<a>` は「SPA ルート化の予定が無い」ページだけに限定する。`/rule` `/practice` `/skill` 等、今後 Step 5+ で移行予定のページは**現時点で未実装でも SPA URL を指す**（一時的に 404 になっても構わない）。

### コメント・実装の規約（重要・レビュー指摘の再発防止）

- **コメントに移行 step 番号を書かない**（`(Step 4)` `step-4.1 で…` 等）。step は Issue / git 履歴で追う。コードのコメントは恒久的に正しい説明だけにする。
- **「既存を再現する」旨のコメントを書かない**（``:8091` 基準で再現` `既存 `<h1 class="h4">` 相当` `legacy の◯◯相当` 等）。見た目の再現は **コンポーネント（Button / Heading / Panel / FormRow など）や CSS class の単位**に閉じ込め、その primitive を使い回す。レイアウト（`AuthLayout` 等）の単位で個々の見出し・ボタンを再現するのは誤り。コメントは「なぜそうするか（非自明な理由）」だけを書く。
- **その場しのぎ（inline・重複）の実装をしない**。ボタン・フォーム行・ラジオ・パネル・見出しなどの UI 部品は `frontend/app/components/ui/` に再利用可能なコンポーネントとして作り、画面側はそれを組み合わせる。
- **GET の検索系パラメータは個別 `@RequestParam` で受けず、`XxxRequest` クラスでまとめて受ける**。ドメインクエリへの変換（`toQuery()` 等）もその Request クラスに閉じ込め、Controller は薄く保つ。
- **一覧の並び順・絞り込み・ページングは API 側で指定可能にする**（frontend で `reverse()` 等の整形をしない）。