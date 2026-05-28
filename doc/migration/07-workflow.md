# 07. Workflow (開発フロー)

## ブランチ戦略

- **main には直接 push しない**。本移行作業中、main は触らない (緊急 hotfix のみ別途)
- 長寿命インテグレーションブランチとして **`feature/monorepo`** を使用
- 各 step PR は `feature/monorepo` から派生したブランチで作業し、base を `feature/monorepo` にして PR 作成
  - `git checkout feature/monorepo && git pull && git checkout -b step-N-xxx`
  - `gh pr create --base feature/monorepo`

## レビュー / マージ

- PR 作成後は **`pr-reviewer` サブエージェントで review** → 指摘反映 (fix) → 再 review を必要回数繰り返す
  - レビュー結果は `.reviews/PR-<番号>.md` に出力
  - must-fix / should-fix は反映、nits は要否判断
- review が落ち着いたら **ユーザーの承認を待ってから squash merge** する
  - 自動 merge はしない
- step PR の merge 方式: **squash merge** (1 step = 1 コミットで feature/monorepo に積む)
- step 間の依存関係: **シーケンシャル** (step N が merge されてから step N+1 着手)、stacked branches は使わない
- `feature/monorepo` には branch protection をかけない (AI 駆動で速く回すため。事故防止はレビュー手順 + ユーザー承認で担保)

## 最終リリース

- 全 step 完了後、`feature/monorepo` → `main` を **merge commit (`--no-ff`)** で取り込む
  - リリースポイントとして 1 つの merge commit + 各 step コミットを履歴に保持

## Claude hooks による自動 lint / format

ファイルを Edit / Write した後に、対象ファイルに応じて自動で lint / format が走るように Claude Code の hooks (`.claude/settings.json`) で設定する。

- **frontend** (TS / TSX / CSS): **oxlint + oxfmt** ([04-frontend.md](04-frontend.md) 参照)
- **backend** (Kotlin): **ktlint** ([02-backend.md](02-backend.md) 参照)

### ktlint 導入構成 (確定)

- **Gradle plugin (`org.jlleitschuh.gradle.ktlint`)** を `backend/build.gradle.kts` に導入
  - `./gradlew ktlintCheck` / `./gradlew ktlintFormat` で実行
- **Claude PostToolUse hook で自動 fix**:
  - `.kt` ファイルを Edit/Write した後に hook が走り、ktlintFormat (or 相当) で自動整形
  - スクリプト配置先: `.context/ktlint-hook/` ([h-orito/lastwolf](https://github.com/h-orito/lastwolf) の backend hooks 構成を参考に整備)
- hook の trigger は `PostToolUse` on Edit/Write
- 編集ファイルパスを見て backend (`.kt` → ktlint) / frontend (`.ts/.tsx/.css` → oxlint+oxfmt) を出し分け、対象範囲外のファイルは何もしない

(settings.json の具体内容は Step 2 実装フェーズで skill `update-config` で編集する想定)

## Issue / 計画ファイルの運用

- `.issues/step-<N>(.M)-<slug>.md` … step 駆動で 1 PR = 1 Issue として扱う
  - **階層番号方式**を採用 (例: `step-1-bootstrap.md`, `step-2-auth-api.md`)
  - **中間にタスクを足したい場合はマイナー番号で吸収**する (例: `step-1.5-tailwind-config.md`, `step-3.1-error-boundary.md`)
  - 既存ファイルの rename は基本不要 (どこに挿入しても周辺の番号を動かさなくて済む)
- `.reviews/PR-<番号>.md` … pr-reviewer の結果出力
- `doc/migration/` … 設計ドキュメント (この計画ドラフト)
- `migration.md` … 全体 index と横断メモ

### Issue 化のタイミング (確定)

- **Step 0 (調査 step) の Issue だけ先に作成**し、着手する
- **Step 1 以降は都度** (各 step 着手直前にその step の Issue を作成)
  - 理由: Step 0 の調査結果 (画面リスト / endpoint / 静的リソース / 村画面のサブ step 粒度) を踏まえないと、以降 step の正確な内容が確定しないため
- 08-step-plan.md には全 step の **骨子** (目的 / 成果物 / 依存) を書くが、Issue 化は上記タイミングで行う

### skill の採番対応 (確定)

- skill `ship-issue` / `add-issue` の **採番ロジックを `step-N(.M)-<slug>` 形式に対応させるカスタマイズ**を行う
  - カスタマイズ作業自体を **Step 0 (or その前) の準備タスク**として扱う
  - 対応するまでは手動採番で運用してもよい

## step 動作確認の標準化 (確定)

- 各 step の Issue に **「動作確認」セクション**を必須で設ける
  - `./gradlew bootRun` (backend) + `pnpm dev` (frontend) の同時起動手順
  - 確認する URL / 操作 / 期待される表示・挙動を step 固有に記述
- Claude の **`verify` / `run` skill** を活用して実際にアプリを起動して確認
- UI 変更を含む step では既存実装 (`http://localhost:8091/wolf-mansion/`) とのスクショ比較を行う

## 未確定事項

- [ ] skill `ship-issue` / `add-issue` のカスタマイズ実装範囲 (採番ロジックのどこを変えるか) — Step 0 準備時
