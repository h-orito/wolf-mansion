# 08. Step 分解ドラフト

各領域の方針が固まり次第、Step 0〜N の作業分解をここに書く。

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
  - `doc/migration/usecases/<usecase>.md` … ユースケース横断の調査メモ (足音 reveal / Daychange / 死亡情報マスク / 投票 等。配置は要相談)
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
  - `feature/monorepo` ブランチを最新の main から切り直し
  - ローカル開発環境の確認手順 (DB / pnpm / Node) を README 等に整備
- **依存**: Step 0 (画面リストが固まっていなくても良いが、走り出す前提として feature/monorepo がクリーンであること)

### Step 2: monorepo 化

- **目的**: ディレクトリ構造を `backend/` `frontend/` `e2e/` の monorepo に再編
- **成果物**:
  - 既存 Gradle プロジェクトを `backend/` 配下に移動
  - `frontend/` 雛形 (RR v7 + Vite + Tailwind v4 + pnpm 初期化、oxlint + oxfmt 設定)
  - `e2e/` 雛形 (Playwright + pnpm)
  - Claude hooks 設定 (`.claude/settings.json` で backend ktlint / frontend oxlint+oxfmt 自動実行)
  - backend に **ktlint 導入** (lastwolf 構成参考)
- **依存**: Step 1

### Step 3: 認証 REST 化

- **目的**: JWT 認証基盤の最小セットを動く状態にする
- **成果物**:
  - backend: JWT filter 自前実装、`/api/v1/auth/{login,refresh,logout,me}` エンドポイント
  - frontend: ログイン / ログアウト / `me` 取得の最小フロー
  - Cookie 設計 (access / refresh) が wolfort.dev で動作確認できる状態
- **依存**: Step 2

### Step 4+: 主要画面ごとの REST 化 + frontend 実装

Step 0 の画面リストに沿って画面単位で進める。1 画面 = 1 step を基本とし、複雑な画面はサブ step (`step-N.M`) に分割。

- 各 step の成果物: 当該画面の REST API + frontend 実装 + e2e ケース
- **依存**: Step 3、および当該画面に対応する Step 0 のドキュメント

#### 村画面は特別: 大量の step に刻む

wolf-mansion で最も機能密度が高い画面。1 step に収めることは不可能なため、**機能ブロック単位で多数のサブ step に分割**する。Step 0 の村画面ドキュメントを元に分割粒度を最終確定するが、想定される分割軸の例:

- 村画面ベース (レイアウト / 日付ナビ / 状況サマリ)
- 発言表示 (種別ごとの出し分け、ページネーション、フィルタ)
- 発言投稿 (通常発言 / 表情差分 / 装飾タグ / アンカー / 秘話 / 返信)
- 参加・退村・見学切替・希望役職変更
- RP 関連 (キャラ名 / メモ / 表情差分)
- 投票
- 能力使用 (役職ごとの能力 UI)
- コミット
- 部屋割り (room grid) / 日別足音
- 参加者一覧 (生死分離 / memo 編集)
- 村情報モーダル / 切り抜き画面
- creator / admin 操作 (kick / 廃村 / 強制退村 / 全員アクセス / 全員自票 / 村建て発言 / epilogue / プレイヤー確認)

各サブ step の粒度・順序・依存関係は Step 0 完了時点で改めて整理する。

### Step 中盤の横断タスク

- OpenAPI → TS 型生成パイプラインの構築 (Step 3 後の早めに入れたい)
- 外部公開 API の互換性ピン留めテスト整備
- e2e スイートの拡充 (画面 step と並行)

### Step 終盤

- 旧 Thymeleaf テンプレート / Controller の撤去
- 外部公開 API の frontend proxy or resource route の最終確定
- cutover (Ingress / k8s manifest / GitHub Actions 分割の本番反映、`feature/monorepo` → `main` merge)

## 未確定事項

- [ ] Step 0 のドキュメント完成度 (合格基準) の定義
- [ ] Step 0 と Step 1 の並行可能範囲 (環境整備は調査と並行できる?)
- [ ] Step 4+ の画面単位 step の順序 (依存関係: ホーム → 村画面? 認証 → プレイヤー画面?)
- [ ] **村画面**サブ step の最終的な分割粒度・順序 (Step 0 完了時点で確定)
- [ ] OpenAPI 型生成パイプラインを Step 3 に含めるか、独立 step にするか
- [ ] e2e と frontend 実装の前後関係 (画面 step 内で先に e2e ケースを書く? 実装後に書く?)
- [ ] 外部公開 API ピン留めテストの実施タイミング (Step 0? Step 2? cutover 前?)
