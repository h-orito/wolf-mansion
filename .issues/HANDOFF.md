# HANDOFF — wolf-mansion REST + React Router v7 移行

移行作業で得られたパターン・教訓・ローカル開発環境のメモ。

## 現在地

- fix: 日付更新通知が消えない問題を修正 (PR #122)。自動更新ON→10秒で消去、OFF→永続
- 残 Issue: step-13（推理補助機能の移植）のみ

## 実装パターン・教訓

### REST API

- **設計方針**: 画面専用の API やレスポンスを作らない。ドメインモデルをそのまま返す。Response DTO は「隠すべき情報がある」「大量取得で削ぎ落とす」場合のみ。複数ドメイン情報が要る画面は個別 API → frontend 組み立て。正本 `CLAUDE.md`
- **認証付き CRUD**: write 系は GET のみ permitAll に足し、書き込みは `/api/v1/**` チェーンの `authenticated()` に乗せる。frontend は作成系 = `RequireAuth`、公開ページ内の書き込みは 401 → メッセージ + ログイン誘導
- **コレクション要素の制約**: 型引数 `@Size` (`List<@Size(...) String>`) は実行時検証されない → コード検証 + `@ArraySchema` で spec に出す
- **複数テーブル書き込み**: service に `@Transactional` (coordinator でなくてよい場合)
- **SpringDoc 注意点**: (1) ネスト DTO とドメインの単純名衝突 → `@Schema(name = "...")` (2) 新エンドポイントは `@Operation(operationId=...)` 明示 (3) 2 文字目大文字 Kotlin プロパティは `@get:JsonProperty` (4) 自己参照サイクルは `OpenApiCustomizer` で除去
- **OpenAPI 運用**: 新 REST エンドポイント/DTO 追加後 `cd frontend && pnpm gen:api` (backend 8089 起動状態) → commit。Jakarta `@Size` を使う (`@Length` は spec に出ない)

### Frontend

- **構成**: colocation (`routes/<screen>/route.tsx`) / `features/<domain>` は共有のみ / `features/villages`(一覧)・`features/village`(詳細)
- **UI コンポーネント**: `components/ui/` を確認/拡張してから組む。inline・重複の「その場しのぎ」禁止
- **リンク規約**: 未移行でも移行予定のあるページは SPA URL (`<Link>`)。`legacyUrl` は SPA 化予定が無いページのみ
- **静的アセット**: `assetUrl("/app/...")` で参照、`frontend/public/app/` に置く。`legacyUrl` は未移行 SSR ページ専用

### コメント規約

- step 番号・「既存再現 (`:8091`/`相当`/`legacy`)」を書かない
- GET 検索は Request クラス + `toQuery()`、一覧の並び順/絞り込みは API 側

### e2e

- 初回役職確認モーダルが参加者系 e2e をブロックする → 各 spec に `dismissInitialSkillModal` を追加
- アクション e2e は 1 日の回数枯渇で動的 skip あり
- e2e は provision 済み DB が前提 (空 docker DB だと 500)

## ローカル開発環境

- **docker-compose**: MySQL (4306) + nginx (18080、オリジナルキャラチップ画像配信)
- **backend**: `cd backend && ./gradlew bootRun` (8089)。JDK 21 必須
- **frontend**: `cd frontend && pnpm dev` (5173)。Vite proxy で `/wolf-mansion-api` → backend、`/wmansion/original` → nginx
- **e2e**: `cd e2e && pnpm test` (backend 18089 / frontend 15173 を自動起動)
- **フィクスチャ村**: 村 4 (CANCEL、流用テスト用) / 村 5 (進行中、メッセージ・操作テスト用) / 村 6 (進行中、コミットテスト用)
- **ローカル認証**: 全ユーザー password=`testuser`。master は ROLE_ADMIN
- **DBFlute**: ReplaceSchema は共有 DB に厳禁。テーブル追加は `CREATE TABLE IF NOT EXISTS` → `manage.sh regenerate`

## 注意点 (引き継ぎ事項)

- **main には push しない**。`feature/monorepo` 上で進め、最終 cutover 時に `--no-ff` merge
- **既存稼働環境**: `http://localhost:8091/wolf-mansion/` でローカル稼働中、DB はあいのり。スクショ取得・視覚比較に活用
- **UI 忠実再現**: 各画面は移行 step 内で `:8091` 基準に忠実再現。Step 12 は視覚モダナイズ (刷新) のみ

## 関連ファイル

- REST API 設計方針・コーディング規約: `CLAUDE.md`
- Issue 運用: `.issues/README.md`
