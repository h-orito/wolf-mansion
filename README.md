# wolf-mansion

人狼ゲーム（Werewolf/Mafia）の Web アプリケーション。Spring Boot + Kotlin（バックエンド）、Thymeleaf（テンプレート）、MySQL + DBFlute（ORM）によるサーバーサイドレンダリングの Web アプリ。

> **移行中**: monorepo + REST + React Router v7 への移行作業を進行中。移行計画は [`migration.md`](migration.md)（index）と [`doc/migration/`](doc/migration/) を参照。本 README は **現状（移行前）** のローカル開発手順を記す。

## 必要ランタイム

| ツール | バージョン | 用途 |
| --- | --- | --- |
| Java (JDK) | **21** | backend（Spring Boot / Gradle） |
| MySQL | 8 系 | ローカル DB |
| Node.js | 22 系 | frontend（Step 2 以降で導入） |
| pnpm | 10 系 | frontend のパッケージマネージャ（Step 2 以降） |

Node / pnpm は移行で導入する frontend 用。現状の backend 開発だけなら Java + MySQL があれば動く。

### Java バージョン管理（jenv）

リポジトリ直下の `.java-version` が `21` に固定されている。[jenv](https://github.com/jenv/jenv) を使う場合、JDK 21 を登録しておけば本ディレクトリで自動的に 21 が選択される。

```bash
# 例: 既存の JDK 21 を jenv に登録
jenv add "$(/usr/libexec/java_home -v 21)"

# 確認（このディレクトリで 21 になっていること）
jenv version          # => 21 (set by .../.java-version)
java -version         # => openjdk version "21.x"
```

`build.gradle.kts` は `JavaVersion.VERSION_21` を要求するため、JDK 17 等でビルドすると失敗する。

## ローカル DB

接続情報は `src/main/resources/config/application.yml`（既定プロファイル）にある:

| 項目 | 値 |
| --- | --- |
| host:port | `127.0.0.1:4306` |
| database | `werewolf_mansiondb` |
| user | `wmansion` |
| password | `wmans10n` |

スキーマ定義は `dbflute_wolf_mansiondb/` 配下。`src/main/java/com/ort/dbflute/` は DBFlute による自動生成コード（手動編集不可。`exbhv/` `exentity/` のみカスタマイズ可）。

## ビルド & 起動

```bash
# ビルド（テストスキップ）
./gradlew build -x test

# テスト実行
./gradlew test

# 単一テストクラス実行
./gradlew test --tests "com.ort.app.domain.model.village.room.RoomSizeTest"

# アプリ起動（ローカル）
./gradlew bootRun
```

起動後のアクセス先: **http://localhost:8089/wolf-mansion/**（port `8089` / context-path `/wolf-mansion`）。

Docker イメージ（Jib）をビルドする場合:

```bash
./gradlew jibDockerBuild
```

## ドキュメント

- [`CLAUDE.md`](CLAUDE.md) — アーキテクチャ・設計パターン・規約
- [`migration.md`](migration.md) — monorepo + REST + RR v7 移行計画の index
- [`doc/migration/`](doc/migration/) — 移行の領域別ドキュメント（画面別調査・ユースケース・step 分解）

## メモ

* 指揮官メッセージを目立たせる
* 管狐の場合呪殺なし
* ストーカーや求愛は使わないと死亡
