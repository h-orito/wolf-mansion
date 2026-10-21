# docker-compose

wolf-mansion 用のローカル開発環境を起動する docker-compose 定義。

- **MySQL 8**: ゲームデータベース
- **nginx**: オリジナルキャラチップ画像の配信 (本番と同じ構成)

## 起動 / 停止

```bash
cd docker-compose

# 起動（バックグラウンド）
docker compose up -d

# ログ確認
docker compose logs -f mysql

# 停止
docker compose down

# データも含めて完全削除
docker compose down -v
```

## プロジェクト名を固定する前の環境からの移行

compose のプロジェクト名を `wolf-mansion` に固定した（以前はフォルダ名の `docker-compose`）。以前のコンテナが残っているとコンテナ名が衝突するので、一度だけ旧プロジェクトのコンテナを削除してから起動する。DB のボリューム（`docker-compose_mysql-data`）はそのまま引き継ぐのでデータは消えない。

```bash
docker compose -p docker-compose down   # 旧プロジェクトのコンテナを削除 (-v は付けない)
docker compose up -d
```

## 接続情報

`backend/src/main/resources/config/application.yml` の datasource と一致させています。

| 項目         | 値                  |
| ------------ | ------------------- |
| host         | 127.0.0.1           |
| port         | 4306 (→ 3306)       |
| database     | werewolf_mansiondb  |
| user         | wmansion            |
| password     | wmans10n            |
| root password| root                |
| charset      | utf8mb4             |

```
jdbc:mysql://127.0.0.1:4306/werewolf_mansiondb?character_set_server=utf8mb4
```

## テーブル作成

スキーマ・テーブルは DBFlute の ReplaceSchema で作成します（`backend/dbflute_wolf_mansiondb/`）。
初回起動後、DB を初期化したい場合は `initdb/` に `*.sql` を置くとコンテナ初回起動時に自動実行されます。

## オリジナルキャラチップ画像

オリジナルキャラチップ村（参加者が自分でキャラ画像をアップロードする村）の画像は nginx が配信します。

- backend が `../data/original/` に画像を保存
- nginx が `/wmansion/original` パスで同ディレクトリを配信 (ポート 18080)
- Vite dev server が `/wmansion/original` を nginx にプロキシ

本番と同じ URL パス (`/wmansion/original`) で画像にアクセスできます。
