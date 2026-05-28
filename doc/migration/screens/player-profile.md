# 画面: プロフィール / 戦績

## 概要

- **URL (既存)**: `GET /user/{userName}`
- **テンプレート**: `src/main/resources/templates/user.html`
- **担当 JS**: `user.js` (空。自己紹介編集モーダルは Bootstrap の data-toggle)
- **Controller**: `PlayerController.user(...)` / `userDetail(...)` (`POST /user-detail`)
- **対象ユーザー**: 全員 (公開)。自分のページなら編集可

## 1. 機能 / 出来ることリスト

- 任意プレイヤーの戦績・参加履歴を閲覧
- 自分のページの場合: 自己紹介 (Twitter / 紹介文) の編集

## 2. 表示要素・UI 状態

- 見出し「ユーザID: {userName}」
- ユーザ未存在時: 「ユーザが存在しません。」
- Twitter リンク (`@xxx` → twitter.com)
- 自己紹介文 (改行保持)
- **総合戦績**: 参加数 / 勝利数 / 勝率
- **陣営戦績**: 陣営ごと 参加 / 勝利 / 勝率 (`campStatsList`)
- **役職戦績**: 役職ごと 参加 / 勝利 / 勝率 (`skillStatsList`)
- **参加した村**: 村番号(リンク) / 村名 / キャラ画像 / 役職 / 生死 (例: `6d 襲撃死`) / 陣営 / 勝敗
- **見学した村**: 村番号 / 村名 / キャラ画像
- 自分のページ: 「自己紹介編集」ボタン + モーダル (Twitter ユーザ名 / 自己紹介 textarea)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/user/{userName}` | プロフィール + 戦績表示 | SSR |
| POST | `/user-detail` | 自己紹介 (Twitter/紹介文) 更新 (本人のみ) | モーダル フォーム |

## 4. 既存 JS の挙動

- `user.js` は空。編集モーダルの開閉は Bootstrap (`data-toggle="modal"`)

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 匿名 / 他人 | 閲覧のみ。編集ボタンなし |
| 本人 (`myName == userName`) | 「自己紹介編集」ボタン + モーダル表示 (`userDetailForm` がモデルに入る) |

## 6. 認可マスク

- 参加した村の役職・陣営・勝敗は **本人/他人問わず公開** (終了済み戦績の集計値)。進行中の村の役職リークは無い想定だが、戦績集計の対象範囲は step-0.16 (マスク) と合わせて確認

## 7. スクリーンショット

- (TODO) 他人プロフィール、本人プロフィール (編集ボタンあり)、自己紹介編集モーダル、ユーザ未存在

## 8. 関連 e2e ケース候補

- [ ] 他人のプロフィール表示: 戦績テーブルと参加村一覧
- [ ] 本人プロフィール: 編集ボタン表示 → モーダルで保存 → 反映
- [ ] 存在しないユーザ: 「ユーザが存在しません」

## 入力仕様 (UserDetailForm)

| フィールド | 制約 |
|---|---|
| `twitterUserName` | 最大 50 文字 |
| `introduction` | 最大 2000 文字 |

## データ構成 (View)

- `PlayerRecordsContent`: `wholeStats` (participateNum/winNum/winRate), `campStatsList`, `skillStatsList`, `participateVillageList`, `spectateVillageList`, `twitterUserName`, `introduction`
- 戦績の組み立ては `PlayerCoordinator.findPlayerRecords(player)` + `CharaService` でキャラ画像解決

## メモ / 移行時の注意

- キャラ画像は `characterImgUrl` (外部 URL、`wolfort.dev/wmansion` 配下に移行済み)。React でもそのまま URL 参照 ([04-frontend.md](../04-frontend.md))
- 「本人判定」は現状 `WolfMansionUserInfoUtil.getUserInfo()?.username == userName`。移行後は `useMe` の player 名と照合 (CSR)
- REST 化: `GET /api/v1/players/{name}` (戦績含む詳細) + `PUT /api/v1/players/me/detail` 等に整理。`/api/v1/auth/me` は最小情報のみなので、戦績はこの players リソースに分離 ([03-auth.md](../03-auth.md))
- 自己紹介の改行は現状テンプレートで `\r\n|\r|\n` 分割表示 → React では `white-space: pre-wrap` 等で対応
