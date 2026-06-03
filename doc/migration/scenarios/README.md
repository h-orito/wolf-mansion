# 進行シナリオ調査 index (Step 0 / e2e 検討時に拡充)

村のライフサイクルを **時系列 (happy-path)** で追うシナリオ記録置き場。
1 画面 / 1 機構を読むだけでは捉えられない「**作成 → 参加 → 開始 → 進行 → 終了** の流れ」を、
各段階で **どの画面 / endpoint / situation / ドメインメソッド / フェーズ依存ルールが絡むか**を時系列に繋いで記録する。

## `usecases/` との役割分担

| | 軸 | 内容 | 例 |
|---|---|---|---|
| [`usecases/`](../usecases/README.md) | **縦** (1 機構をレイヤー横断で深掘り) | ある仕組みが Controller→…→View でどう動くか | footstep reveal / daychange 状態遷移 / 認可マスク |
| `scenarios/` (本ディレクトリ) | **横** (時系列で複数画面/機構をまたぐ) | 村ライフサイクルの happy-path フロー | 村作成→参加 / 進行中の 1 日サイクル |

両者は相互補完。シナリオから機構の詳細は `usecases/` を、画面の詳細は [`../screens/`](../screens/README.md) を参照する。

## 作成タイミング

- **実体の authoring は e2e 検討時** ([05-e2e.md](../05-e2e.md) / [08-step-plan.md](../08-step-plan.md) の e2e 着手時) で良い。
- 目的の第一は **e2e happy-path シナリオの土台**。各シナリオ md が、対応する e2e の `test.describe` 1 つに概ね対応する想定。
- 本 README は **計画 (どのシナリオを作るか) を失わないための器**。各 md はまだ空 (= これから書く)。

## 構成方針 (doc drift 回避)

移行は **現状踏襲 + domain ロジック温存**が原則。シナリオ md は:

- **ルールを再導出しない**。`day > 1` 等のゲームルールの正本は **コード** (例: `Village.canVote()`)。シナリオ側はそれを**参照・リンク**するに留める。
- 各 phase で「**何を叩くと / どの状態が前提で / 何が起きて / 誰に何が見えるか**」を時系列で繋ぐ**糊**に徹する。
- 画面詳細は `../screens/`、機構詳細は `../usecases/` へリンクし、重複記述を避ける。

## 計画シナリオ一覧 (確定: ユーザー選択)

| md | カバー範囲 | 主な参照先 |
|---|---|---|
| `scenario-create-join.md` | 村建てが村作成 → 他ユーザー参加 (+ 見学参加) → プロローグ滞在 | `../screens/new-village.md`, `../screens/village/village-participate.md` |
| `scenario-prologue-manage.md` | プロローグ中の管理: 最終アクセス確認 → 手動 kick / 自動退村 / 廃村 / 延長 / 設定変更 | `../usecases/daychange.md` (自動退村/廃村/延長), `../screens/village/village-admin.md` |
| `scenario-start-roles.md` | daychange で PROGRESS 遷移 → 役職 / 部屋割当 → 初回役職確認モーダル | `../usecases/daychange.md` (startVillage), `../screens/village/village-ability.md` |
| `scenario-progress-day.md` | 進行中の 1 日サイクル。**1 日目 (投票なし・能力のみ)** と **2 日目以降 (daychange → 処刑/結果開示 → 投票 + 能力 + commit)** を 1 本の時系列で | `../usecases/daychange.md`, `../screens/village/village-vote.md`, `../screens/village/village-ability.md`, `../screens/village/village-commit.md` |

- `scenario-epilogue-finish.md` (settled → エピローグ全開示/感想戦/creator 延長短縮 → 終了) は **要否未確定**。ライフサイクル完結のため候補。

## 既知ルールの種 (シナリオ authoring 時に正本リンクを張る)

- **1 日目は投票不可**: `Village.canVote(): Boolean = status.isProgress() && days.latestDay().day > 1` (`domain/model/village/Village.kt:165`)。`scenario-progress-day.md` の 1 日目で参照。
- (随時追記)

## 合格基準

各シナリオ md は以下を満たす:

1. 村ライフサイクルの該当区間を **時系列**で追えている (前提状態 → アクション → 結果 → 可視性)
2. 各段階で絡む **画面 / endpoint / situation / ドメインメソッド**が明記され、`../screens/` `../usecases/` と相互リンクされている
3. **フェーズ依存ルール**はコードを正本として参照 (再導出しない)
4. 対応する **e2e happy-path** (`test.describe` 単位) の骨子が引ける
