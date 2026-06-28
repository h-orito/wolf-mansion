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
| 1 | Thymeleaf旧画面およびController methodの削除 | chore | open |
| 4 | アンカー発言のネスト展開が入れ子表示になり領域が狭まる | fix | open |
| 5 | #bottomスクロール位置が広告に隠れて最新発言や発言確認が見えない | fix | open |
| 6 | 入村パネルのキャラ選択ですでに入村済みのキャラが表示される | fix | open |


## frontend テスト基盤メモ

- **vitest を導入する場合は `4.1.6` 以上を使用する**。
  - 理由: `4.1.6` 未満には RCE 脆弱性 **CVE-2026-47429** がある。
  - `package.json` で `"vitest": "^4.1.6"` のように固定する。
