# 08. Step 分解ドラフト

各領域の方針が固まり次第、Step 0〜N の作業分解をここに書く。

## 全体の進め方 (確定)

- **Step 0 (調査) を完全に完了させてから Step 1 (環境整備) に着手**する (順番厳守)。並行はしない
- **Issue 化**: Step 0 の Issue だけ先に作成、Step 1 以降は各 step 着手直前に都度作成 ([07-workflow.md](07-workflow.md))
- **e2e**: 各画面 step では **実装後に e2e ケースを書く** (UI が固まってからセレクタを書く)
- **画面 step の順序**: 認証 → ホーム(村一覧) → 情報・静的 → ランダム → 新規村作成 → 村画面 → **プロフィール系** (依存が浅い順。プロフィール系は戦績検証に村のエピローグが要るため村画面の後へ後ろ倒し — ユーザー指示)
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
  - `doc/migration/usecases/<usecase>.md` … ユースケース横断 (縦/機構深掘り) の調査メモ (足音 reveal / Daychange / 死亡情報マスク / 投票 等。`doc/migration/usecases/` に配置済み)
  - `doc/migration/scenarios/<scenario>.md` … 進行シナリオ (横/時系列 happy-path) の調査メモ。**実体の authoring は e2e 検討時**で良い (`doc/migration/scenarios/README.md` に計画と器を用意済み)
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

### Step 4 以降: 画面ごとの REST 化 + frontend 実装

各画面 step は **REST API (`api/response/` に Response クラス) + React 実装 + e2e ケース**を 1 セットで仕上げる。

- **各 step 共通の進め方**: 実装 → 動作確認 (verify/run skill + `:8091` とスクショ比較) → e2e ケース追加
- **共通の依存**: Step 3 (認証基盤)、および当該画面の Step 0 ドキュメント (`screens/<screen>.md`)
- **並び順の原則**: 公開・読み取り中心の軽量画面を先、認証必須・複雑画面を後 (依存が浅い順)。順序は ホーム → 情報・静的 → ランダム → 新規村作成 → 村画面 → プロフィール系。**プロフィール系 (戦績) は settled 村が要るため村画面の後**へ置く (ユーザー指示)
- 各 step は規模に応じ複数 PR / サブ step に分割 (大きい画面は機能ブロックごとに細分化)

#### Step 4: ホーム・村一覧 (公開ランディング + 共通レイアウト基盤)

- **goal (完了時にできること)**: 未ログインでもトップ / イントロ / 村一覧が既存同等に表示でき、村一覧から各村画面へ遷移できる。**共通レイアウト (ヘッダ / フッタ) と `useMe` のログイン出し分け基盤がここで確立**し、以降の全画面がこれに乗る
- **対象**: [home.md](screens/home.md) / [intro.md](screens/intro.md) / [village-list.md](screens/village-list.md)
- **成果物**: `/` `/intro` `/village-list` の React route + 村一覧用 REST (`/api/v1/...`)。公開 API `/api/village-list` `/recruiting` は凍結のまま proxy 経由で利用。共通 layout / header / footer コンポーネント (フッタの広告・投げ銭・プライバシーポリシー含む) + `useMe` によるヘッダ出し分け
- **依存**: Step 3
- **動作確認**: トップ / 村一覧が `:8091` と一致表示 / 未ログイン・ログインでヘッダが切り替わる / 村一覧 → 村画面へ遷移

#### Step 5: 情報・静的ページ (公開・読み取り・サブ step に分割)

公開の読み取り専用ページ群。低リスクなので機能グループごとに細かく消化する。各サブ step の共通依存は Step 4 (layout)。

| サブ step | goal (完了時にできること) | 対象 md | 備考 |
|---|---|---|---|
| 5.1 役職一覧 | 役職一覧が陣営別に表示できる | skill.md | 公開 API `skill/list` は凍結のまま流用 |
| 5.2 ルール | ルールページ (`rule/*` サブ含む、`RuleContent` 駆動) が表示できる | rule.md | |
| 5.3 情報ページ群 | About / FAQ / 練習問題 / お知らせ が表示できる | about / faq / practice / announce | 単純静的。PR は個別でも一括でも可 |
| 5.4 キャラチップ一覧・詳細 | キャラチップ一覧と詳細が表示できる | charachip-list / charachip-detail | キャラ画像は外部 URL。**Step 7 の村作成キャラ選択で流用** |
| 5.5 エイプリル企画アーカイブ | `/archives/april-*` が表示できる | home.md のメモ | 2026 ランダム役職を `/archives/april-20260401` 化 |

#### Step 6: ランダム機能 (公開閲覧 + 認証書き込み)

- **goal**: ランダムキーワードの一覧閲覧 (公開) と作成・編集・削除 (要認証) ができる。**認証付き CRUD の最初のパターン**を確立する
- **対象**: [random-keyword.md](screens/random-keyword.md)
- **成果物**: 一覧 / 作成 / 編集 route + REST (閲覧 permitAll、書き込み要認証)
- **依存**: Step 3 (auth)
- **動作確認**: 未ログインで閲覧可・書き込み不可 (401) / ログインで作成・編集・削除が成功

#### Step 7: 新規村作成 (複雑フォーム・サブ step に分割)

設定項目が最多 (~40 フィールド) の画面。フォーム → 確認モーダル → 作成のフローを機能ブロックに分ける。対象はすべて [new-village.md](screens/new-village.md)。

| サブ step | goal | 依存 |
|---|---|---|
| 7.1 村作成フォーム本体 | 基本設定 / 詳細ルール / 見学・閲覧 / 身内村 / 特殊ルール / RP村 の入力フォームが表示・入力できる | Step 3 |
| 7.2 発言制限設定 | 役職別・発言種別・RP の発言制限 (count/length、先頭行コピー) が設定できる | 7.1 |
| 7.3 キャラチップ選択 | キャラセット連動 (`getCharacterList`) でダミーキャラ・画像を選択できる (オリジナル画像は確認画面で) | 7.1 + Step 5.4 |
| 7.4 確認モーダル → 作成 | 入力内容を**確認モーダル** (ユーザー指示) で再掲し、作成して募集中の村が立つ。闇鍋配分テーブル再掲・オリジナル画像アップロードを含む | 7.1〜7.3 |
| 7.5 既存村からの流用 | エピローグ/終了/廃村の村設定を流用 (`divert`) して初期化できる | 7.1 |

#### Step 8: 村画面 (最重量・サブ step に細分化)

wolf-mansion で最も機能密度が高い画面。**8.1 (ベース) を最初に確立**し、以降のサブ step がこれに乗る。対象 md は `screens/village/`。

| サブ step | goal (完了時にできること) | 対象 md | 依存 |
|---|---|---|---|
| 8.1 村画面ベース | 村を開くとレイアウト/日付ナビ/状況サマリ (部屋割り/参加者/投票/足音) が表示され、ポーリングで自動更新される。**`VillageSituation`+`ParticipantSituation` 二層 + `isViewableSpoilerContent` をマスク基盤として確立** | village-base / village-situation-summary | Step 7 |
| 8.2 メッセージ表示 | 発言ログが種別ごとに正しく描画され、アンカー / 参加者一覧公開が動く | village-messages | 8.1 |
| 8.3 メッセージフィルタ | 種別・対象でのメッセージフィルタ (modal-filter) が動く | village-messages | 8.2 |
| 8.4 発言投稿 | 通常/表情/装飾/秘話/返信の発言を確認フロー経由で投稿できる。文字数/行数制限 (種別別) で送信可否が出し分く | village-say | 8.2 |
| 8.5 アクション発言 | アクション発言 (別パネル `#actionform-panel`) を投稿できる | village-action | 8.4 |
| 8.6 入村 | キャラ選択 + 入村発言 + 希望役職 + 確認 (ルール/礼節の同意チェック) で入村できる。原画村のオリジナル画像アップロード含む | village-participate | 8.1 |
| 8.7 見学参加・参加見学切替 | 見学参加と 参加⇄見学 の切替ができる | village-participate | 8.1 |
| 8.8 希望役職変更・退村 | 希望役職 (第1/第2) の変更と退村ができる | village-participate | 8.1 |
| 8.9 能力使用 | 役職別パターン (A〜H) の能力をセットできる (襲撃者/対象/足音/徘徊先 等、`ParticipantAbilitySituation` フラグで UI 出し分け) | village-ability | 8.1 |
| 8.10 投票 | 投票 (2日目以降のみ・`day>1`) ができる | village-vote | 8.1 |
| 8.11 コミット | コミット (全員コミットで時刻前進行) ができる | village-commit | 8.1 |
| 8.12 RP | キャラ名/簡易メモ/表情差分の編集ができる | village-rp | 8.4 |
| 8.13 ユーザー設定 | 表示設定 (Cookie/localStorage、デフォルト変更済) と Discord 通知設定ができる | village-user-settings | 8.1 |
| 8.14 村情報モーダル | 村設定を閲覧モーダルで確認できる | village-info | 8.1 |
| 8.15 creator パネル | 村主操作 (kick/廃村/エピローグ延長短縮/村建て発言) ができる | village-creator | 8.1 + Step 7 |
| 8.16 admin パネル | 管理者操作 (access/leave/vote/player、参加プレイヤー確認は panel 内インライン) ができる | village-admin | 8.1 |
| 8.17 debug パネル | debug 操作 (allparticipate/dayChange/login/logout、ローカル開発向け) ができる | village-debug | 8.1 |
| 8.18 村設定変更 | creator が募集中の村設定を変更できる | village-settings | 8.1 + Step 7 |
| 8.19 村切り抜き (別画面) | 村の発言を切り抜くページ (別ルート) が動く | village-scrap | 8.2 |

- **8.1 の最重要事項**: situation 二層 + spoiler 判定を村取得 API のマスク基盤に据える ([usecases/mask.md](usecases/mask.md) / [footstep.md](usecases/footstep.md))。横断ユースケース (足音 reveal / Daychange / 認可マスク) は該当サブ step 内で domain ロジックを温存しつつ View 変換を実装
- **8.9 能力**は役職 133 / 能力サービス 67 と多い。`SkillTag` 述語でパターン A〜H に機械分類し、パターン別コンポーネントで実装。必要なら `8.9.K` でパターングループ単位にさらに分割可
- **e2e の進行シナリオ**: 村画面が動いたら `scenarios/` の happy-path (村作成→参加→開始→進行) を authoring して e2e の土台にする ([scenarios/README.md](scenarios/README.md))

#### Step 9: プロフィール系 (村画面の後・認証あり閲覧)

> **順序変更 (ユーザー指示)**: 戦績は settled (エピローグ以降) の村が必要なため、**村画面 (Step 8) が動いて村をエピローグまで進められる状態になってから**着手する。当初「ホーム → プロフィール系 → …」だった主要画面順を、プロフィール系だけ村画面の後ろへ後ろ倒し。

- **goal**: プロフィール (`/user/{name}`)・戦績・プレイヤー一覧が表示でき、パスワード変更ができる。**戦績は settled のみ集計**
- **対象**: [player-profile.md](screens/player-profile.md) / [player-list.md](screens/player-list.md) / [auth-change-password.md](screens/auth-change-password.md) (画面のみ。endpoint `/api/v1/auth/password` は Step 3)
- **成果物**: 各 route + REST (`/api/v1/players/{id}` 等)。パスワード変更フォーム (react-hook-form + zod)
- **依存**: Step 3 (auth)、**Step 8 (戦績検証に村のエピローグが必要)**
- **補足**: player-list / パスワード変更は村に依存しないため、検証上問題なければ前倒し可
- **動作確認**: プロフィール/戦績/一覧が `:8091` と一致 / パスワード変更が成功・失敗時に適切な表示 (緩和後ポリシー 3-60字)

### 横断タスク (各画面 step と並行)

特定の step に属さず、Step 3 以降を通して進める:

- **OpenAPI → TS 型生成パイプラインの構築**: Step 3 (認証 REST 化) の直後に入れる
  - `pnpm gen:api` で生成 + commit、CI で drift 検知 ([06-infra-deploy.md](06-infra-deploy.md))
- **外部公開 API の互換性ピン留めテスト**: Step 0 中盤で整備済みのものを継続活用 (REST 化で壊れないことを担保)
- e2e スイートの拡充 (各画面 step 内で実装後に追加)

### Step 10: 旧資産の撤去

- **goal**: 旧 Thymeleaf テンプレート / 旧 Controller / `api/view/` (旧 ViewModel) / backend 静的リソースが全て除去され、backend が REST API 専用になる
- **作業内容**: 全画面の React 移行完了を前提に旧 SSR 資産を削除。静的アセットの frontend 移管を完了 (キャラチップは外部 URL 参照のまま)。legacy 公開 API は frontend proxy として温存
- **依存**: Step 9 まで完了 (全画面移行済)
- **動作確認**: 旧テンプレート削除後も全画面が React で表示 / 公開 API がピン留めテストを通過

### Step 11: cutover (本番切替)

- **goal**: 本番が monorepo 構成 (backend / frontend 別 Deployment) で稼働し、`feature/monorepo` が `main` に取り込まれる
- **作業内容**: Ingress / k8s manifest / GitHub Actions 分割の本番反映、legacy proxy の path mapping 最終確定 ([06-infra-deploy.md](06-infra-deploy.md))、DB マイグレーション戦略の確定、ログ・メトリクスの整合。`feature/monorepo` → `main` を `--no-ff` merge
- **依存**: Step 10
- **動作確認**: 本番で全画面動作 / 公開 API 互換 / ロールバック手順の確認

### 忠実再現は各画面 step で行う (方針・最重要)

> **方針変更 (ユーザー指示)**: 当初は「移行中は機能優先で近似 → cutover 後の Step 12 で一括忠実復元」としていたが、**二度手間で非効率**なため廃止。**各画面を移行するその step で `:8091` 基準の見た目を忠実に再現する**方針に変更した。

- 各画面 step (Step 4 以降) の成果物は、**レイアウト・色・余白・細部・`<title>` / OGP (og:title・og:description・og:image 等) / `<head>` メタ / 共通ヘッダー (ナビ・ロゴ) を含めて `:8091` 基準で忠実に再現**したものとする (「近似で済ませて後で直す」はしない)
- 各 step の「動作確認」に **`:8091` とのスクショ/DOM 比較で見た目が一致すること**を含める
- 実装上やむを得ない置換 (Bootstrap collapse → React state 等) は挙動・見た目が同一なら現状踏襲とみなす ([04-frontend.md](04-frontend.md) の UI/UX 現状維持原則)

### Step 12: 視覚モダナイズ (本移行スコープ外・cutover 後)

本移行 (Step 0-11) は **UI/UX 現状維持** (= 既存の見た目を忠実再現し、モダナイズはしない) が原則。見た目の刷新は cutover 後の別フェーズで実施:

- **Step 12: 視覚モダナイズ** — デザイントークン + 共通コンポーネントの差し替えで見た目を刷新 ([04-frontend.md](04-frontend.md))。移行中は各画面 step で忠実再現済みのため、本 step は「現状再現の精度合わせ」ではなく純粋な刷新作業

## 未確定事項

- [x] Step 0 のドキュメント完成度 (合格基準) の定義 — `screens/README.md` 12-24 に 8 項目で定義済み
- [x] **村画面**サブ step の最終的な分割粒度・順序 — Step 8 の表で 19 サブ step (8.1〜8.19) に確定済み
- [x] **画面 step (Step 4 以降) の細分化** — Step 4〜9 (情報系・新規村作成・村画面はサブ step に細分化、プロフィール系は村画面の後へ後ろ倒し) + Step 10/11 (撤去/cutover) に goal 付きで定義済み
- [ ] cutover の具体的な切替手順 (ダウンタイム有無 / ロールバック手順) — Step 11
