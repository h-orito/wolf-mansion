# 画面: ランダム機能 (ランダムキーワード)

> random-message (一覧) / new-random-keyword (作成) / random-keyword (編集) をまとめて扱う。

## 概要

- **URL (既存)**: `GET /random-message` (一覧) / `GET,POST /new-random-keyword` (作成) / `GET /random-keyword/{id}` (編集) / `POST /update-random-keyword` / `POST /delete-random-keyword`
- **テンプレート**: `random-message.html` (一覧) / `new-random-keyword.html` (作成) / `random-keyword.html` (編集)
- **担当 JS**: `random-message.js` (一覧) / `random-keyword.js` (削除確認)
- **Controller**: `RandomController`
- **対象ユーザー**: 明示的な認可制限なし (`permitAll`。create/delete も含め誰でも操作可 — **要確認**)

## 1. 機能 / 出来ることリスト

- ランダムキーワードの一覧・検索
- キーワードの新規作成 / 編集 / 削除
- キーワード参照記法 `[[キーワード]]` のクリップボードコピー

## 2. 表示要素・UI 状態

- 一覧 (`random-message.html`): キーワードテーブル (キーワード名 / コンテンツ複数行)。検索ボックス、「全て表示」展開、コピー ボタン
- 作成 (`new-random-keyword.html`): keyword + message (複数行 = 複数コンテンツ) フォーム
- 編集 (`random-keyword.html`): 既存キーワードの編集フォーム + 削除

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/random-message` | キーワード一覧 | SSR |
| GET/POST | `/new-random-keyword` | 作成フォーム / 登録 | SSR / submit |
| GET | `/random-keyword/{id}` | 編集フォーム | SSR |
| POST | `/update-random-keyword` | 更新 | submit |
| POST | `/delete-random-keyword` | 削除 | `random-keyword.js` (確認後 submit) |

## 4. 既存 JS の挙動

### `random-message.js`
- `[data-all-view]` (全て表示): セル内の hidden コンテンツを展開
- `[data-search]`: 検索語でキーワード/コンテンツを部分一致フィルタ (行 hidden 切替)
- `[data-copy]`: `[[キーワード]]` をクリップボードにコピー (execCommand)

### `random-keyword.js`
- `[data-delete]`: 確認ダイアログ → form action を `/delete-random-keyword` にして submit

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 全員 | 現状は閲覧・作成・編集・削除すべて可能に見える (Security で制限なし) |

> ⚠️ create/delete に認可がないのは意図的か **要確認** (移行時に管理者限定にすべきか検討)

## 6. 認可マスク

- なし

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/random-message` 他

## 8. 関連 e2e ケース候補

- [ ] 一覧表示・検索
- [ ] 作成 → 一覧に反映
- [ ] 編集 → 反映
- [ ] 削除 (確認ダイアログ)
- [ ] `[[キーワード]]` コピー

## データ構成

- `RandomKeyword`: `keyword` + `contents` (改行区切りの複数メッセージ `RandomContent`)
- `RandomKeywordService` で CRUD、`RandomKeywordFormValidator` で検証

## メモ / 移行時の注意

- `[[キーワード]]` は村の発言などで展開される再利用テキスト辞書と思われる (展開仕様は村画面の発言処理 step-0.8 で確認)
- **認可の見直し**: 現状 permitAll。移行時に「誰が編集できるか」を明確化すべき (要ユーザー確認)
- クリップボードコピーは `execCommand` (非推奨) → React では `navigator.clipboard` に置換
- REST 化: `GET/POST/PUT/DELETE /api/v1/random-keywords` で整理
