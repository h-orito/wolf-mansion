---
name: ship-issue
description: ローカル `.issues/step-<N>(.M)-<slug>.md` を 1 PR 単位で消化する (wolf-mansion monorepo 移行版)。base は feature/monorepo。ブランチ作成 → 実装 → 動作確認 → PR → pr-reviewer → レビュー反映 → squash merge → 後片付け。`/ship-issue [step番号]` で呼び出す。
---

# Ship Issue (wolf-mansion monorepo 移行版)

monorepo 移行作業中の Issue を 1 つ受け取り、PR まで持っていくフロー。

**まずグローバル版 (`~/.claude/skills/ship-issue/SKILL.md`) を読み、流れの骨格を把握すること。** その上で、以下の差分のみ上書きする。差分に書かれていない手順はグローバル版に従う。

## 差分

### Issue ファイルの命名・選択 (グローバル §0)

- Issue は `step-<N>(.M)-<slug>.md` 形式。一覧抽出:
  ```bash
  ls .issues/ | grep -E '^step-[0-9]'
  ```
- 着手優先順:
  1. `.issues/HANDOFF.md` の「次セッションでやること」で示された step
  2. `doc/migration/08-step-plan.md` の step 順 (依存: step N が merge 済みであること)
  3. `.issues/README.md` 一覧表の `status=open`
- **Step 0 は完全完了してから Step 1 着手** (08-step-plan.md の順番厳守)。step 間はシーケンシャル。

### base ブランチ (グローバル §1 を置き換え)

**デフォルトブランチ (main) は使わない。base は常に `feature/monorepo`。**

```bash
git checkout feature/monorepo && git pull
git checkout -b <type>/step-<N>(.M)-<slug>
```

- `type` は frontmatter の `type` から導出 (`bug`→`fix`, `refactor`→`refactor`, `enhancement`/`build`→`chore`, `performance`→`perf`, `design`→`docs` or `chore`)
- ブランチ名にドットを含めてよい (`chore/step-1.5-tailwind-config`)
- **main には絶対に push しない**

### 動作確認 (グローバル §3)

- backend lint: ktlint (`./gradlew ktlintCheck` / monorepo 化後は `-p backend`)
- frontend lint/format: oxlint + oxfmt
- e2e: `e2e/` の Playwright (ローカル専用、CI では走らせない)
- 調査系 step (step-0.x の多く) は **ドキュメント成果物**が中心。lint/build 対象コードが無い場合は、ドキュメントの合格基準 (screens/README.md 参照) を満たすかで判定

### PR (グローバル §6)

```bash
gh pr create --base feature/monorepo --title "<conventional commit>" --body "..."
```

- 本文冒頭: `closes .issues/step-<N>(.M)-<slug>.md`
- コミットメッセージ末尾: `(step-N(.M))`

### マージ後 (グローバル §9)

- **squash merge** で feature/monorepo に積む (`gh pr merge <PR> --squash --delete-branch`)
- merge 後は **feature/monorepo に戻る** (デフォルトブランチに戻らない):
  ```bash
  git checkout feature/monorepo && git pull
  ```
- `.issues/step-<N>(.M)-<slug>.md` 削除、README 一覧から行削除、HANDOFF 更新はグローバル §9 と同様

## 必ず守るルール (グローバル + 本プロジェクト追加)

- **main には push しない / merge しない**。すべて feature/monorepo 上
- **PR マージは必ずユーザー確認** (branch protection は無いが手順で担保)
- **PR 作成後は pr-review-toolkit:code-reviewer でreview and fix** (実装意図は渡さない)
  - pr-review-toolkit:code-reviewer → 修正 を3回繰り返す
    - should以上は必ず修正する
  - レビュー指摘 (must/should-fix) は省略せず反映、`.reviews/PR-<番号>.md` に出力
  - 指摘事項、修正内容、修正しなかった内容をユーザーに報告する
- プロジェクト固有ルールは `.issues/HANDOFF.md` / `CLAUDE.md` / `doc/migration/` を最優先
