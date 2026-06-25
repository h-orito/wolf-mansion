# HANDOFF — wolf-mansion REST + RR v7 移行計画

本ドキュメントは、移行計画作業の **次セッションで最初に何をやるか** を示すハンドオフメモ。
決定事項・設計詳細は `migration.md` (index) と `doc/migration/` 配下が正本。ここには docs に載らない運用状況だけを書く。

## 現在地

- **フェーズ**: **Step 8 (村画面) 完了 🎉**。Step 0〜7 も全完了。次は **Step 9 (プロフィール・戦績)**
- 完了 PR 一覧: Step 0 (#46) / Step 2 (#47〜#50) / Step 3 (#51〜#55) / Step 4+3.6 (#56〜#59) / Step 5 (#60〜#64) / Step 6 (#65) / Step 7 (#66〜#70) / Step 8 (#71〜#89, #91〜#93)

## 次にやること

**Step 9 (プロフィール・戦績) へ**。

- **残課題 (Step 8 から繰り越し)**: なし (8.12.1 表情差分 REST 化 ✅ #93、エピローグ延長/短縮 ✅ 手動確認済み)
- **未対応の follow-up**: なし (Discord タイムアウト統一 ✅)

## 今後の実装で踏襲するパターン・教訓

### REST API

- **設計方針**: 画面専用の API やレスポンスを作らない。ドメインモデルをそのまま返す。Response DTO は「隠すべき情報がある」「大量取得で削ぎ落とす」場合のみ。複数ドメイン情報が要る画面は個別 API → frontend 組み立て。正本 `doc/migration/01-overview.md`
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

- 全体 index: `migration.md`
- 領域別: `doc/migration/01-overview.md` 〜 `doc/migration/08-step-plan.md`
- 各 md の「確定」セクション = 決定事項、「未確定事項 / 要調査」セクション = Step 0/1 で詰める残課題
