# 画面: ランダム機能 (ランダムキーワード)

> random-message (一覧) / new-random-keyword (作成) / random-keyword (編集) をまとめて扱う。

## 概要

- **URL (既存)**: `GET /random-message` (一覧) / `GET,POST /new-random-keyword` (作成) / `GET /random-keyword/{id}` (編集) / `POST /update-random-keyword` / `POST /delete-random-keyword`
- **テンプレート**: `random-message.html` (一覧) / `new-random-keyword.html` (作成) / `random-keyword.html` (編集)
- **担当 JS**: `random-message.js` (一覧) / `random-keyword.js` (削除確認)
- **Controller**: `RandomController`
- **対象ユーザー**: 現状は明示的な認可制限なし (`permitAll`、誰でも操作可)。**移行時は閲覧=公開 / 書き込み(作成・更新・削除)=ログイン必須に変更 (確定)**

## 1. 機能 / 出来ることリスト

- ランダムキーワードの一覧・検索
- キーワードの新規作成 / 編集 / 削除
- キーワード参照記法 `[[キーワード]]` のクリップボードコピー

## 2. 表示要素・UI 状態

- 一覧 (`random-message.html`): テーブル (ヘッダ「キーワード」「変換後」, `:27-28`)。0 件時「登録されているキーワードがありません。」(`:34`)。各キーワードのコンテンツは**先頭 5 行のみ表示・6 行目以降 hidden** + 「全て表示」リンクで展開 (`:43-49`)。検索ボックス、コピーボタン
- 作成 (`new-random-keyword.html`): keyword + message (複数行 = 複数コンテンツ) フォーム
- 編集 (`random-keyword.html`): 既存キーワードの編集フォーム + 削除。**`randomKeywordForm == null` 時は「すでに削除されています」**を表示 (`:16-17`)。keyword は読み取り専用表示 (`*{keyword}` text + hidden, `:31`)

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

| 権限 | 見え方・できること (移行後) |
|---|---|
| 匿名 | 一覧・検索・編集フォーム閲覧・`[[キーワード]]` コピーのみ (読み取り公開) |
| ログイン済 | 上記 + 作成 / 更新 / 削除 (書き込み) |

> **認可方針 (確定)**: 現状は全操作 permitAll だが、**移行時に書き込み系 (作成・更新・削除) はログイン必須**にする。ユーザーが明示したのは create/delete だが、update も同じ書き込み操作のため同列でログイン必須とする。閲覧系は引き続き公開。
> → README の Q4 (random-* permitAll の見直し) はこれで解決。

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

- ドメイン `RandomKeyword`: `keyword` + `contents` (複数メッセージ `RandomContent`)
- View `RandomMessageListContent.RandomMessageContent`: `keywordId` / `keyword` / `contentExample` (`RandomMessageListContent.kt:17-19`)
- **改行コードの非対称**: 保存/更新は `form.message.split("\r\n")` (`RandomController.kt:58,102`)、編集表示は `contents.joinToString("\n")` (`:75`) → 移行時のデータ整形で要注意
- `RandomKeywordService` で CRUD、`RandomKeywordFormValidator` で検証

## メモ / 移行時の注意

- `[[キーワード]]` は村の発言などで展開される再利用テキスト辞書と思われる (展開仕様は村画面の発言処理 step-0.8 で確認)
- **認可の見直し (確定)**: 現状 permitAll → 移行時は **書き込み (作成・更新・削除) をログイン必須**、閲覧は公開。REST 化時は write 系 endpoint に認証ガードを付与 ([03-auth.md](../03-auth.md))
- クリップボードコピーは `execCommand` (非推奨) → React では `navigator.clipboard` に置換
- REST 化: `GET/POST/PUT/DELETE /api/v1/random-keywords` で整理
