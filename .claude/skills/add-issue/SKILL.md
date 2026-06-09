---
name: add-issue
description: ローカル `.issues/step-<N>(.M)-<slug>.md` を新規作成する (wolf-mansion monorepo 移行版)。階層番号方式で採番し、フロントマター + 標準セクション構成のテンプレを配置、`.issues/README.md` の一覧表にも追記する。`/add-issue <タイトル>` で呼び出す。
---

# Add Issue (wolf-mansion monorepo 移行版)

このプロジェクトは monorepo 移行作業中で、Issue を **階層番号方式** (`step-<N>(.M)-<slug>.md`) で管理する。

## 採番

ファイル名は `step-<N>(.M)-<slug>.md`。番号は **自動採番しない**。どの step に属するかを文脈から決める:

- step 番号は `doc/migration/08-step-plan.md` の Step 0〜N に対応
  - `step-0` = 調査 (Discovery)、`step-1` = 環境整備、`step-2` = monorepo 化、`step-3` = 認証 REST 化、`step-4` 以降 = 画面別
- 中間に差し込むタスクは **マイナー番号** で吸収 (`step-1.5-...`, `step-3.1-...`)
- どの step / マイナー番号にするか不明な場合は **ユーザーに確認**する
- 同一 step 内の連番が必要なら既存ファイルを見て最大マイナー番号 + 1:

```bash
ls .issues/ | grep -E '^step-[0-9]'
```

## frontmatter

```yaml
---
id: step-<N>(.M)
title: <タイトル>
type: <type>
status: open
---
```

## 標準セクション

Issue ファイルには以下のセクションを含める:

- **目的**: なぜこの step が必要か
- **成果物**: マージ時点で何ができている状態か
- **作業内容**: 主要タスク
- **動作確認** (必須): backend は `./gradlew` 系、frontend は `pnpm dev`、確認 URL / 操作 / 期待表示を step 固有に書く
- **依存**: 先行 step

## README 追記

`.issues/README.md` の一覧表の `#` 列には `step-N(.M)` を入れる:

```
| step-N(.M) | <タイトル> | <type> | open |
```

## 注意

- 本プロジェクトの Issue 運用ルールは `.issues/README.md` と `.issues/HANDOFF.md` を最優先
- スコープが 1 PR に収まらない場合はマイナー番号で分割を促す
