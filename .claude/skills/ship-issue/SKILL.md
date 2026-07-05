---
name: ship-issue
description: ローカル `.issues/step-<N>(.M)-<slug>.md` を 1 PR 単位で消化する (wolf-mansion monorepo 移行版)。base は feature/monorepo。ブランチ作成 → 実装 → 動作確認 → PR → pr-reviewer → レビュー反映 → squash merge → 後片付け。`/ship-issue [step番号]` で呼び出す。
---

# Ship Issue

monorepo 移行作業中の Issue を 1 つ受け取り、PR まで持っていくフロー。

## §0 Issue の選択

- Issue は `step-<N>(.M)-<slug>.md` 形式。一覧抽出:
  ```bash
  ls .issues/ | grep -E '^step-[0-9]'
  ```
- 着手優先順:
  1. `.issues/HANDOFF.md` の「次セッションでやること」で示された step
  2. `.issues/README.md` 一覧表の `status=open`（番号の小さい step 優先）
- step 間はシーケンシャル（step N が merge 済みでないと step N+1 に着手しない）。

## §1 ブランチ作成

**デフォルトブランチ (main) は使わない。base は常に `feature/monorepo`。**

```bash
git checkout feature/monorepo && git pull
git checkout -b <type>/step-<N>(.M)-<slug>
```

- `type` は frontmatter の `type` から導出 (`bug`→`fix`, `refactor`→`refactor`, `enhancement`/`build`→`chore`, `performance`→`perf`, `design`→`docs` or `chore`)
- ブランチ名にドットを含めてよい (`chore/step-1.5-tailwind-config`)
- **main には絶対に push しない**

## §2 実装

Issue ファイルの「作業内容」に従って実装する。

### 分担（コンテキスト節約）

メイン agent のコンテキストを温存するため、かさばる作業は agent に委譲する:

- **既存実装の調査・パターン特定** → `Explore` agent に委譲し、結論（該当ファイルパス・パターンの要点）だけ受け取る。メインで grep / ファイル全文 Read を繰り返さない
- **パターンが確立した実装**（RestController/Request 追加、frontend の route/Panel/API client 追加、e2e spec 追加など）→ `step-implementer` agent に委譲。委譲時は「仕様」と「参照すべき既存実装のファイルパス」を明示して渡す
- **lint / test / e2e の実行** → `test-runner` agent に委譲し、失敗の要約だけ受け取る。メインでログ全文を読まない
- **設計判断・参照パターンの選定・レビュー指摘の反映・PR 文面**はメイン agent が行う

## §3 動作確認

- **UI に関わる変更は `/verify-ui` のフロー**（複数 viewport スクショ + console / 横スクロールの機械チェック + ユーザー提示）で確認し、合意を得てからコミットする
- Issue の「動作確認」セクションの各項目を実測し、結果を報告する（未検証項目は未検証と明示）
- backend lint: `cd backend && ./gradlew ktlintCheck`
- frontend lint/format: `cd frontend && pnpm lint && pnpm format:check`
- backend build: `cd backend && ./gradlew build -x test`
- frontend build: `cd frontend && pnpm build`
- e2e: `cd e2e && pnpm test`（ローカル専用、CI では走らせない）
- **全チェック green が §4 以降への進行条件**。失敗を「既存の問題」と主張する場合は base ブランチ（feature/monorepo）で同コマンドを実行した失敗ログを添え、直すか issue 化するかユーザーの判断を仰ぐ。黙って先に進まない

## §4 コミット

- conventional commit 形式: `<type>: <summary> (step-N(.M))`
- 1 PR = 1 squash commit が基本だが、論理的に分けたほうがよいなら複数 commit 可

## §5 PR 作成

```bash
git push -u origin <branch>
gh pr create --base feature/monorepo --title "<conventional commit>" --body "..."
```

- 本文冒頭: `closes .issues/step-<N>(.M)-<slug>.md`

## §6 レビュー

- **PR 作成後は pr-review-toolkit:code-reviewer で review and fix**（実装意図は渡さない）
  - pr-review-toolkit:code-reviewer → 修正 を最大 3 回繰り返す
    - should 以上は必ず修正する
    - **指摘 (should 以上) が 0 件の巡があったら、そこで打ち切ってよい**（残りの巡は不要）
  - レビュー指摘 (must/should-fix) は省略せず反映、`.reviews/PR-<番号>.md` に出力
  - 指摘事項、修正内容、修正しなかった内容をユーザーに報告する

## §7 マージ

- **PR マージは必ずユーザー確認**（branch protection は無いが手順で担保）
- **squash merge** で feature/monorepo に積む: `gh pr merge <PR> --squash --delete-branch`
- merge 後は **feature/monorepo に戻る**（デフォルトブランチに戻らない）:
  ```bash
  git checkout feature/monorepo && git pull
  ```

## §8 後片付け

- `.issues/step-<N>(.M)-<slug>.md` を削除
- `.issues/README.md` 一覧表から該当行を削除
- `.issues/HANDOFF.md` の「現在地」と「次にやること」を更新

## 必ず守るルール

- **main には push しない / merge しない**。すべて feature/monorepo 上
- プロジェクト固有ルールは `.issues/HANDOFF.md` / `CLAUDE.md` を最優先
