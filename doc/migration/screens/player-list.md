# 画面: プレイヤー一覧

## 概要

- **URL (既存)**: `GET /user-list?pageNum={n}`
- **テンプレート**: `src/main/resources/templates/player-list.html`
- **担当 JS**: `user-list.js` (ページネーション操作)
- **Controller**: `PlayerController.index(UserListForm)`
- **対象ユーザー**: 全員 (公開)

## 1. 機能 / 出来ることリスト

- 全プレイヤーの一覧 (名前) を 30 件ずつページング表示
- 名前クリックでプロフィールを別タブで開く

## 2. 表示要素・UI 状態

- 見出し「ユーザー一覧」
- ページネーション (上下 2 箇所): `<<` `<` 数字 `>` `>>`、現在ページ active、前後なしは disabled
- プレイヤー名テーブル (各行リンク)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/user-list?pageNum={n}` | プレイヤー一覧 (30件/ページ) | SSR (JS が location.href で再読込) |
| GET | `/user/{name}` | 名前クリックでプロフィールへ (別タブ) | `user-list.js` `window.open` |

## 4. 既存 JS の挙動 (`user-list.js`)

- `[data-pagenum]` / `[data-prev-page]` / `[data-next-page]` クリック → `location.href = contextPath + 'user-list?pageNum=' + page` でページ再読込 (SSR ページング)
- `[data-user-page]` クリック → `window.open(contextPath + 'user/' + userName)` (別タブ)

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 全員 | 同一 (公開、権限差なし) |

## 6. 認可マスク

- なし

## 7. スクリーンショット

- (TODO) 一覧 (複数ページ)、ページネーション

## 8. 関連 e2e ケース候補

- [ ] 一覧表示: 30件 + ページネーション
- [ ] ページ遷移: 次ページ → 別の30件
- [ ] 名前クリック → プロフィールへ

## データ構成 (View: PlayerListContent)

- `players`, `allPageCount`, `currentPageNum`, `pageNumList`, `isExistPrePage`, `isExistNextPage` (`PlayerListContent.kt:8-9`。テンプレが `content.existPrePage` で引けるのは Kotlin `is`-prefix boolean の getter マッピングによる。**REST DTO 化時は Jackson が `is` を落とすシリアライズ名に注意**)
- `PlayerService.findAllPlayers(pageSize=30, pageNum)` で取得

## メモ / 移行時の注意

- 現状は SSR ページング (リンク再読込)。React では `GET /api/v1/players?page={n}&size=30` 等で取得し、クライアントページネーション ([02-backend.md](../02-backend.md) のページネーション規約に従う)
- 名前クリックの別タブ (`window.open`) は React Router の通常遷移 or `target=_blank` で再現
- 公開情報なので SSR 取得可 ([03-auth.md](../03-auth.md) の「認証不要は SSR」)
