---
id: step-9.1
title: プレイヤー詳細REST + プロフィール画面
type: feat
status: open
---

## 目的

`/user/{name}` でプレイヤーのプロフィール（戦績・参加村一覧・自己紹介）を表示できるようにする。Step 8 完了により settled 村の戦績データが利用可能になったため着手可能。

## 成果物

- **backend**: `GET /api/v1/players/{name}` (permitAll) — プレイヤー戦績・参加村一覧を返す REST エンドポイント
  - 既存 `PlayerCoordinator.findPlayerRecords()` + `PlayerRecordsContent` を活用
  - settled 村のみ集計（既存 domain ロジック維持）
  - 存在しないプレイヤーは 404
- **frontend**: `/user/:name` ルート
  - 総合戦績（参加数・勝利数・勝率）
  - 陣営別戦績テーブル
  - 役職別戦績テーブル（参加あり役職のみ）
  - 参加村一覧（村名・キャラ名・役職・生死・陣営・勝敗、キャラ画像付き）
  - 見学村一覧
  - 自己紹介・Twitter ユーザー名の表示
  - `:8091` の `/user/{name}` と忠実再現
- **e2e**: プロフィール表示の基本テスト

## 作業内容

1. `PlayerRestController` 新設 (`GET /api/v1/players/{name}`)
   - SSR `PlayerController.user()` のロジックを REST 化
   - レスポンスは `PlayerRecordsContent` を直接返すか、REST 用の Response DTO を検討
   - security: permitAll (公開情報)
2. `pnpm gen:api` で型生成
3. `/user/:name` ルート作成
   - 戦績テーブル・参加村テーブルのレイアウトは `:8091` 実測で再現
   - 自己紹介は本人かどうかの判定のみ（編集は 9.3）
4. e2e 追加

## 動作確認

- `cd backend && ./gradlew build -x test` でビルド通過
- `curl http://localhost:8089/wolf-mansion-api/api/v1/players/master` で戦績 JSON 取得
- `pnpm dev` → `/user/master` でプロフィール表示、`:8091` の `/wolf-mansion/user/master` と突合
- 存在しないユーザー `/user/nonexistent` で 404 表示

## 依存

- Step 8 完了（settled 村の戦績検証に必要）
- Step 3 完了（認証基盤）
