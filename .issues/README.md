# Issues

ローカル Issue 管理ディレクトリ。`.issues/` は `.gitignore` 対象 (ローカル管理)。完了した Issue はファイルごと削除する運用。

## 命名規則

- `<type>-<slug>.md`（例: `fix-room-image-text-overflow.md`）
- ブランチ: `<type>/<slug>`、base は **`feature/monorepo`**

## ステータス凡例

- `open` 未着手
- `in-progress` 対応中
- `wontfix` 対応しない (要理由)

完了したものは一覧から削除 & ファイル削除する。

## 一覧

| # | タイトル | type | status |
| --- | --- | --- | --- |
| 13 | 推理補助機能（wolf-mansion-analyzer）の移植 | enhance | open |
| 14 | [[tp]]に[[#fff]]など色を重ねるとクリックしても内容が見られない | fix | open |


## frontend テスト基盤メモ

- **vitest を導入する場合は `4.1.6` 以上を使用する**。
  - 理由: `4.1.6` 未満には RCE 脆弱性 **CVE-2026-47429** がある。
  - `package.json` で `"vitest": "^4.1.6"` のように固定する。
