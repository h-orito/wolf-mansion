# 08. Step 分解ドラフト

各領域の方針が固まり次第、Step 0〜N の作業分解をここに書く。

## 全体の進め方 (確定)

- **Step 0 (調査) を完全に完了させてから Step 1 (環境整備) に着手**する (順番厳守)。並行はしない
- **Issue 化**: Step 0 の Issue だけ先に作成、Step 1 以降は各 step 着手直前に都度作成 ([07-workflow.md](07-workflow.md))
- **e2e**: 各画面 step では **実装後に e2e ケースを書く** (UI が固まってからセレクタを書く)
- **画面 step の順序**: 認証 → ホーム(村一覧) → プロフィール系 → 新規村作成 → 村画面 (依存が浅い順 / 村画面が最重量で最後)
- **外部公開 API ピン留めテスト**: Step 0 (中盤) で整備し、以降の REST 化が既存挙動を壊さない安全網にする

## 各 step の記載項目

- **目的**: なぜこの step が必要か
- **成果物**: マージ時点で何ができている状態か
- **作業内容**: 主要タスク
- **依存**: 先行 step
- **動作確認**: 完了判定の手段 (e2e / 手動 / 単体テスト)
- **対応 Issue / PR**: 後で埋める (`.issues/step-N(.M)-<slug>.md`)

大きな step は複数 PR / 複数 Issue に分割される (例: `step-N-x` `step-N.y-...`)。

## 想定する大枠 (ドラフト)

順序・粒度は要調整。

### Step 0: 現状把握 (Discovery)

**独立した step として時間をかけて進める**。実装には踏み込まず、調査と文書化に専念する。後続の全 step (特に画面別 REST 化と e2e スイート) の基礎になる。

- **目的**: 既存の機能・UI・ドメイン仕様を完全に把握し、移行後も維持すべき挙動を確定させる。e2e テストケース設計の基礎を作る
- **成果物**:
  - `doc/migration/screens/<screen>.md` … 画面別ファイル群 (機能・UI 要素・呼び出す API・**対応する既存 JS の挙動**・権限分岐・認可マスク・関連 e2e ケース)
  - `doc/migration/screens/README.md` … 画面一覧 index
  - `doc/migration/usecases/<usecase>.md` … ユースケース横断の調査メモ (足音 reveal / Daychange / 死亡情報マスク / 投票 等。`doc/migration/usecases/` に配置済み)
  - 外部公開 API (`/village-record/list` 等) の現状挙動メモ
  - 既存 JS が叩いている内部 AJAX エンドポイントの一覧 (Handlebars レンダリング含む)
- **作業内容**:
  - 既存 Thymeleaf テンプレート (`src/main/resources/templates/`) から画面の棚卸し
  - **既存 JS (jQuery + Handlebars) の機能調査** … `src/main/resources/static/app/js/` 配下に画面ロジック (合計 13 ファイル) があり、AJAX / 動的レンダリング / クライアントバリデーション / 動的フォーム制御 等を担当しているため、必ず画面ごとの調査対象に含める
    - 例: `village.js` / `village-message.js` / `new-village.js` / `new-village-confirm.js` / `new-player.js` / `user.js` / `user-list.js` / `say-confirm.js` / `skill.js` / `index.js` / `random-keyword.js` / `random-message.js` / `common.js`
    - Handlebars テンプレート (`src/main/resources/static/lib/handlebars/`) を使っている箇所は、サーバから受け取った JSON をクライアントで描画しているはず → 既に存在する内部 API も洗い出す
  - **既存稼働環境を使った視覚比較** … 既存実装はローカルで稼働中 (`http://localhost:8091/wolf-mansion/`、ローカル DB を共有)
    - 各画面にアクセスしてスクリーンショットを取得し `doc/migration/screens/<screen>.md` に添付
    - 状態別 (匿名 / ログイン済 / 参加者 / 村主 / admin / 各村 status) のスクショを揃え、UI 踏襲時の参照基準にする
    - DevTools の Network パネルで実際に飛んでいる AJAX エンドポイントとペイロードを記録 (既存 JS の挙動把握と組み合わせる)
  - 各画面の機能 / UI 要素 / 表示状態の整理 (Thymeleaf 出力 + JS の動的挙動を統合した最終的な振る舞い)
  - 既存 Controller / Coordinator / DomainService / View 変換を横断的に追って **ユースケース単位の振る舞い**を整理
  - 外部公開 API のレスポンス取得 (実環境 or ローカルで叩いて記録) と隠蔽パターンの把握
  - 各画面に対応する e2e テストケース候補のドラフト記載
- **依存**: なし
- **動作確認**: ドキュメント完成度 (画面リスト網羅、機能・API・認可の記述レベル) を別途定める合格基準で判定
- **対応 Issue**: **複数 Issue に分割**して実施
  - `step-0-bootstrap.md` (画面リスト棚卸し + 雛形作成)
  - `step-0.1-screen-<screen-a>.md`, `step-0.2-screen-<screen-b>.md` … 画面ごと
  - `step-0.x-usecase-<usecase>.md` … ユースケース横断
  - `step-0.x-public-api-pinning.md` … 外部公開 API の現状記録

### Step 1: 環境整備 (Bootstrap)

- **目的**: 移行作業のスタート地点を整える
- **成果物**:
  - `.java-version` 修正 (17 → 21)
  - ローカル開発環境の確認手順 (DB / pnpm / Node) を README 等に整備
  - `ship-issue` / `add-issue` skill の採番ロジックを `step-N(.M)-<slug>` 形式に対応させるカスタマイズ
- **依存**: **Step 0 (完全完了が前提)**
- **動作確認**: `./gradlew build -x test` が通る / skill の採番が階層番号で動く

### Step 2: monorepo 化

- **目的**: ディレクトリ構造を `backend/` `frontend/` `e2e/` の monorepo に再編
- **成果物**:
  - 既存 Gradle プロジェクトを `backend/` 配下に移動 (Jib image 名を `wolf-mansion-backend` にリネーム)
  - backend context-path を `/wolf-mansion-api` に設定
  - `frontend/` 雛形 (RR v7 + Vite + Tailwind v4 + pnpm 初期化、oxlint + oxfmt 設定)
  - `e2e/` 雛形 (Playwright + pnpm、webServer 設定の枠)
  - Claude hooks 設定 (`.claude/settings.json` で backend ktlint / frontend oxlint+oxfmt 自動実行)
  - backend に **ktlint 導入** (`org.jlleitschuh.gradle.ktlint` plugin + `.context/ktlint-hook/`、lastwolf 構成参考)
- **依存**: Step 1
- **動作確認**: `backend/` で bootRun 起動 / `frontend/` で `pnpm dev` 起動 / hook で .kt / .tsx 編集時に自動 lint が走る

### Step 3: 認証 REST 化

- **目的**: JWT 認証基盤の最小セットを動く状態にする
- **成果物**:
  - backend: JWT filter 自前実装 (jjwt / HS256 / 鍵は環境変数)、`/api/v1/auth/{login,signup,password,refresh,logout,me}` エンドポイント
  - refresh token rotation (使い捨て) + DB 管理
  - エラーは `ProblemDetail` (RFC 7807) で統一、`@RestControllerAdvice` を整備
  - frontend: ログイン / ログアウト / signup / `me` 取得の最小フロー (react-hook-form + zod、useMe hook、RequireAuth wrapper)
  - Cookie 設計 (access `Path=/` / refresh `Path=/wolf-mansion-api/api/v1/auth`) が動作確認できる状態
- **依存**: Step 2
- **動作確認**: ログイン → me が取得できる / 未認証で認証必要ページが /login に飛ぶ / refresh で access 更新 / logout で Cookie 消去 (e2e: 認証フロー)

### Step 4+: 主要画面ごとの REST 化 + frontend 実装

Step 0 の画面リストに沿って画面単位で進める。1 画面 = 1 step を基本とし、複雑な画面はサブ step (`step-N.M`) に分割。

**進める順序 (確定)**: 認証 (Step 3 で実施済) → **ホーム (村一覧)** → **プロフィール系 (プロフィール / パスワード変更 / 戦績)** → **新規村作成** → **村画面** (最重量、多数のサブ step)

- 各 step の成果物: 当該画面の REST API (`api/response/` に Response クラス) + frontend 実装 + e2e ケース
- 各 step 内の進め方: **実装 → 動作確認 (verify/run skill + 既存実装とスクショ比較) → e2e ケース追加**
- **依存**: Step 3、および当該画面に対応する Step 0 のドキュメント

#### 村画面は特別: 大量の step に刻む (Step 0 完了により粒度確定)

wolf-mansion で最も機能密度が高い画面。Step 0 の調査 (`doc/migration/screens/village/village-*.md`) を元に、**実装サブ step を調査の機能ブロックと一致させる**ことで確定:

| 実装サブ step | 対応調査 md | 主な内容 | 依存 |
|---|---|---|---|
| 村画面ベース | village/village-base.md / village/village-situation-summary.md | レイアウト/日付ナビ/状況サマリ/ポーリング/`VillageSituation`+`ParticipantSituation` 二層基盤 | 先頭 (他サブ step の土台) |
| メッセージ表示 | village/village-messages.md | 一覧/種別描画/フィルタ/アンカー/参加者公開 | ベース |
| 発言投稿 / アクション | village/village-say.md / village/village-action.md | 発言/装飾/表情/秘話/確認フロー、アクション(別パネル) | メッセージ表示 |
| 参加・退村 | village/village-participate.md | 入村/観戦/切替/希望役職/退村 | ベース |
| 能力・投票・コミット | village/village-ability.md / village/village-vote.md / village/village-commit.md | 能力(役職別パターン A〜H +足音)/投票/コミット | ベース |
| RP / 設定モーダル | village/village-rp.md / village/village-user-settings.md | 名前/簡易メモ/表情差分、表示設定/Discord通知 | 発言投稿 |
| creator/admin/debug | village/village-creator.md / village/village-admin.md / village/village-debug.md | 村主(kick/廃村/エピローグ/村建て発言)/管理者/デバッグ。設定変更は village-settings.md | ベース + 新規村作成のフォーム共通化 |
| 村情報モーダル | village/village-info.md | 村設定の閲覧モーダル | ベース |
| 村切り抜き (別画面) | village/village-scrap.md | 切り抜きページ (別ルート) | メッセージ表示 |

- **基盤の最重要事項**: `ParticipantSituation` / `VillageSituation` / `isViewableSpoilerContent` を村取得 API のマスク基盤に据える (village/village-base.md / usecases/mask.md)。これを村画面ベース step で確立し、以降のサブ step が乗る
- 横断ユースケース (足音 reveal / Daychange / 認可マスク) は該当サブ step 内で domain ロジックを温存しつつ View 変換を実装
- 各サブ step は必要に応じ `step-N.M.K` 相当でさらに分割可 (例: 能力は役職グループ単位)

### Step 中盤の横断タスク

- **OpenAPI → TS 型生成パイプラインの構築**: Step 3 (認証 REST 化) の直後に入れる
  - `pnpm gen:api` で生成 + commit、CI で drift 検知 ([06-infra-deploy.md](06-infra-deploy.md))
- **外部公開 API の互換性ピン留めテスト**: Step 0 中盤で整備済みのものを継続活用 (REST 化で壊れないことを担保)
- e2e スイートの拡充 (各画面 step 内で実装後に追加)

### Step 終盤

- 旧 Thymeleaf テンプレート / Controller / `api/view/` (旧 ViewModel) / 静的リソースの撤去
- legacy 公開 API の frontend proxy の path mapping 最終確定 ([06-infra-deploy.md](06-infra-deploy.md))
- 静的アセットの frontend 移管完了 (キャラチップは外部 URL 参照のまま)
- DB マイグレーション戦略の確定 / ログ・メトリクスの整合
- cutover (Ingress / k8s manifest / GitHub Actions 分割の本番反映、`feature/monorepo` → `main` を `--no-ff` merge)

## 未確定事項

- [x] Step 0 のドキュメント完成度 (合格基準) の定義 — `screens/README.md` 12-24 に 8 項目で定義済み
- [x] **村画面**サブ step の最終的な分割粒度・順序 — 上記「村画面は特別」表で 8 サブ step に確定済み
- [ ] cutover の具体的な切替手順 (ダウンタイム有無 / ロールバック手順) — 終盤
