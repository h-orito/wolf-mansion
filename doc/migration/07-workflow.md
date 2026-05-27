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
  - 参考: [h-orito/lastwolf](https://github.com/h-orito/lastwolf) の backend hooks 構成 (ktlint check & fix を hook で自動実行)
  - スクリプト配置先: `.context/ktlint-hook/` (現状空のディレクトリだが、lastwolf 構成を参考に整備する)
- hook の trigger は `PostToolUse` on Edit/Write を想定
- 編集ファイルパスを見て backend / frontend を出し分け、対象範囲外のファイルは何もしない

(設定の具体内容は実装フェーズで詰める。skill `update-config` で settings.json を編集する想定)

## Issue / 計画ファイルの運用

- `.issues/step-<N>(.M)-<slug>.md` … step 駆動で 1 PR = 1 Issue として扱う
  - **階層番号方式**を採用 (例: `step-1-bootstrap.md`, `step-2-auth-api.md`)
  - **中間にタスクを足したい場合はマイナー番号で吸収**する (例: `step-1.5-tailwind-config.md`, `step-3.1-error-boundary.md`)
  - 既存ファイルの rename は基本不要 (どこに挿入しても周辺の番号を動かさなくて済む)
  - skill `ship-issue` / `add-issue` の自動採番ロジックがこの命名に合わない可能性があるため、実装時に skill の挙動を確認 / 必要に応じて手動採番に切り替える
- `.reviews/PR-<番号>.md` … pr-reviewer の結果出力
- `doc/migration/` … 設計ドキュメント (この計画ドラフト)
- `migration.md` … 全体 index と横断メモ

## 未確定事項

- [ ] step 毎の Issue 化のタイミング (計画ドラフト完成後に一気に作る? 都度?)
- [ ] step ごとの動作確認手順の標準化 (`./gradlew bootRun` + `pnpm dev` の同時起動など)
- [ ] skill `ship-issue` / `add-issue` の自動採番ロジックが階層番号方式に対応しているか確認 / 対応していない場合の運用方針
