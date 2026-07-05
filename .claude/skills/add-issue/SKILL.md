---
name: add-issue
description: ローカル `.issues/<type>-<slug>.md` を新規作成する。フロントマター + 標準セクション構成のテンプレを配置し、`.issues/README.md` の一覧表にも追記する。`/add-issue <タイトル>` で呼び出す。
---

# Add Issue

ローカル Issue を `.issues/<type>-<slug>.md` で管理する。

## 命名規則

ファイル名は `<type>-<slug>.md`。

- `type`: `fix` / `enhance` / `refactor` / `chore` など
- `slug`: 内容を表す短い kebab-case

```bash
ls .issues/ | grep -E '\.md$' | grep -v README | grep -v HANDOFF
```

## frontmatter

```yaml
---
title: <タイトル>
type: <type>
status: open
---
```

## 標準セクション

Issue ファイルには以下のセクションを含める:

- **目的**: なぜこの Issue が必要か
- **成果物**: マージ時点で何ができている状態か（**観測可能な文**で書く。`/define-goal` の DoD の書き方に従う）
- **作業内容**: 主要タスク
- **動作確認** (必須): backend は `./gradlew` 系、frontend は `pnpm dev`、確認 URL / 操作 / 期待表示を具体的に書く。UI 変更を含む場合は `/verify-ui` のフロー（複数 viewport スクショ + 横スクロール等の機械チェック）で確認する旨を書く
- **依存**: 先行 Issue（あれば）

## 採番

`.issues/README.md` の一覧表から `#` 列の最大値を取得し、+1 した番号を振る:

```bash
grep -oP '^\| \K[0-9]+' .issues/README.md | sort -n | tail -1
```

該当行がなければ `1` から開始。

## README 追記

`.issues/README.md` の一覧表に追記する:

```
| <番号> | <タイトル> | <type> | open |
```

## 注意

- 本プロジェクトの Issue 運用ルールは `.issues/README.md` を最優先
- スコープが 1 PR に収まらない場合は分割を促す
