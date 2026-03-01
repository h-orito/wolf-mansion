# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

人狼ゲーム（Werewolf/Mafia）のWebアプリケーション「wolf-mansion」。Spring Boot + Kotlin（バックエンド）、Thymeleaf（テンプレートエンジン）、MySQL + DBFlute（ORM）で構成されたサーバーサイドレンダリングのWebアプリ。

## Build & Run Commands

```bash
# ビルド（テストスキップ）
./gradlew build -x test

# テスト実行
./gradlew test

# 単一テストクラス実行
./gradlew test --tests "com.ort.app.domain.model.village.room.RoomSizeTest"

# アプリケーション起動（ローカル）
./gradlew bootRun

# Dockerイメージビルド（Jib）
./gradlew jibDockerBuild
```

- Java 11 / Kotlin 1.5.32 / Spring Boot 2.3.0
- ローカルDB: MySQL `werewolf_mansiondb` (port 3306, user: wmansion)
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
- **CDef（分類定義）**: DBFluteの自動生成コード`CDef`クラス（`src/main/java/com/ort/dbflute/allcommon/CDef.java`）が役職・陣営・村ステータスなどのenum的定数を管理。ドメインモデルとCDefの相互変換は`toCdef()`/`toModel()`で行う
- **Daychangeパターン**: 日付更新処理は`Daychange`データクラスに村の全状態を集約し、イミュータブルにcopyしながら処理を進行。`DaychangeCoordinator` → `DaychangeDomainService` → `PrologueDomainService`/`ProgressDomainService`/`EpilogueDomainService`の順に委譲
- **Abilityサービス**: 各役職の能力は`domain/service/ability/`配下に1能力1クラスで分離（60以上の能力サービスが存在）

### DBFlute（自動生成コード）

`src/main/java/com/ort/dbflute/` 配下はDBFluteによる自動生成Javaコード。手動編集不可（`exbhv/`と`exentity/`のみカスタマイズ可能）。スキーマ定義は`dbflute_wolf_mansiondb/`にある。

### 村のライフサイクル

`VillageStatus`: 募集中（Prologue） → 進行中（Progress） → エピローグ（Epilogue） → 終了（Finished）。廃村（Canceled）もある。

### デプロイ

masterへのpushで`deploy-sakura.yml`によりさくらサーバーへ自動デプロイ。

## Key Conventions

- ドメインモデルはKotlin data classで不変（状態変更は`copy()`で新インスタンスを返す）
- ゲーム内テキスト（ステータスメッセージ、役職名など）は日本語
- テンプレートは`src/main/resources/templates/`にThymeleaf HTML
- 静的ファイルは`src/main/resources/static/`