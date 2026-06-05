# docker-compose

wolf-mansion 用のローカル開発用 MySQL 8 を起動する docker-compose 定義。

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
