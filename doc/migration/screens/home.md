# 画面: ホーム / トップ

## 概要

- **URL (既存)**: `GET /`
- **テンプレート**: `src/main/resources/templates/index.html` (レイアウトは `layout/top-layout`)
- **担当 JS**: `index.js`
- **Controller**: `IndexController.index(LoginForm)`
- **対象ユーザー**: 全員 (匿名 / ログイン済で出し分け)

## 1. 機能 / 出来ることリスト

- サイト紹介・各ページへのナビゲーション (About / Intro / Announce / Rule / FAQ / Skill)
- 匿名: ID登録 / ログインへの導線
- ログイン済: マイページ / パスワード変更 / ログアウト
- **開催中の村**一覧 (未終了の村) の表示・村への遷移
- 村一覧 / 村作成 / ユーザー一覧 / キャラチップ一覧への導線
- (4/1 限定) ランダムなエイプリルフール役職説明の表示 → 移行時は `/archives/april-20260401` として切り出し (後述メモ)
- フッター: 広告 / 連絡先 / 投げ銭 / プライバシーポリシー (共通レイアウト `layout/footer`)

## 2. 表示要素・UI 状態

- トップ画像 (`/app/images/top.jpg`) + ロゴ。ログイン中は右下に「ユーザID: xxx」
- ナビタイル群 (About/Intro/Announce、Rule/FAQ/Skill)
- 登録/ログインタイル:
  - `user == null`: 「ID登録」「ログイン」
  - `user != null`: 「マイページ」「パスワード変更」「ログアウト」
- **開催中の村**テーブル: 村番号 / (タグ +) 村名 / 参加人数 / 状態。各セル → `/village/{id}`
- 村一覧/村作成タイル: 「村一覧」常時、「村を建てる」は `user != null && !content.participate` のとき
- ユーザー一覧タイル、キャラチップ一覧タイル
- 4/1: `aprilFoolDescription` を alert で表示 (`th:utext` で HTML)

### フッター (共通レイアウト `layout/footer::footer`、全画面共通)

home 固有ではなく全画面で表示される共通フラグメント (`index.html:273` が `layout:replace`)。要素:

- **広告 (Google AdSense)**: `th:unless="${noAd}"` で出し分け (`footer.html:29-40`)。`noAd=true` のページ (new-player / change-password) では非表示
- **連絡先テキスト**: 要望・不具合報告は Twitter `@ort_dev`、投げ銭リンク、プライバシーポリシーリンク、`© 2018- ort` (GitHub リンク) (`footer.html:43-49`)
- **投げ銭モーダル** (`#kampa-modal`, `footer.html:51-105`): Amazon ほしい物リスト / Amazon ギフト券 / Amazon アソシエイト / Pixiv Fanbox / 補足
- **プライバシーポリシーモーダル** (`#policy-modal`, `footer.html:106-236`): 全10条の規約本文
- モーダル開閉は Bootstrap (`data-toggle="modal"`)。footer の JS (jQuery/bootstrap/handlebars/common.js) は `footer.html:14-24` で読み込み

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/` | ホーム (未終了村一覧 + ログイン状態) | SSR |
| GET | `/archives/april-20250401` | エイプリルフール企画アーカイブ (2ch スレ風、`IndexContent` 流用) | SSR (`IndexController.april20250401`) |
| GET | `/archives/april-20250402` | エイプリルフール企画アーカイブ (同上) | SSR (`IndexController.april20250402`) |
| POST | `/logout` | ログアウト (hidden form `#logout-form`) | `index.js` `[data-submit-logout]` |
| (遷移) | `/village/{id}` | 開催中の村へ | アンカー / `index.js` `[data-goto-village]` |

> エイプリルフール企画 (`/archives/april-*`) は移行対象。詳細はメモ参照。

## 4. 既存 JS の挙動 (`index.js`)

- `[data-goto-village]` クリック → 子要素 `[data-village-url]` の URL へ `location.href`
- `[data-submit-logout]` クリック → `#logout-form` を submit (`POST /logout`)

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 匿名 (`user == null`) | ID登録 / ログイン導線。村を建てるは非表示 |
| ログイン済 (`user != null`) | マイページ / パスワード変更 / ログアウト。参加中でなければ「村を建てる」表示 |

- ログイン状態は `user` グローバル属性 (`UserInfoInterceptor` が全 ModelAndView に注入)
- `content.participate`: ユーザーがいずれかの村に参加中か (村作成可否に影響)

## 6. 認可マスク

- なし (開催中の村一覧は公開情報)

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/`。匿名時 / ログイン時のタイル差分、開催中の村テーブルを確認

## 8. 関連 e2e ケース候補

- [ ] 匿名表示: ID登録/ログインタイル表示、開催中の村一覧
- [ ] ログイン済表示: マイページ/ログアウトタイル、村を建てる表示条件
- [ ] 村クリック → 村画面遷移
- [ ] ログアウト → 匿名状態へ

## データ構成 (View: IndexContent)

- `villageList` (未終了 = 募集中/進行中/エピローグ): **villageId (遷移キー)**, villageNumber, villageName, tags(name/level), participateNum, status
  - ⚠️ 加えて april 企画用の不動産ネタ項目 chiku/toho/area/madori/yachin を `IndexVillage` constructor 内で生成・保持 (`IndexContent.kt:38-48`)。ただし **どのテンプレートからも参照されないデッドフィールド** (home / april20250401 / april20250402 いずれも未使用、grep 全件確認済)。april ページは villageNumber/villageName/tags/status/participateNum/villageId のみを 2ch スレ風に描画。移行時は不要 (再現不要、要削除候補)
- `isParticipate` (参加中フラグ)。※ `canCreateVillage` はコンストラクタ引数で、保持フィールドは `isParticipate = !canCreateVillage` (`IndexContent.kt:14,22`)。テンプレの `content.participate` は `isParticipate` の getter

## メモ / 移行時の注意

- **ログインフォームは共通ヘッダ (top-layout/header) 側**に存在し、`LoginForm` が model に渡される。React ではヘッダーのログインフォーム or ログインページとして再設計 (auth-login.md と連動)
- **`user` グローバル属性 (`UserInfoInterceptor`)** が「誰がログイン中か」の唯一の供給源。移行後は `useMe` hook (CSR、`/api/v1/auth/me`) に置換 ([03-auth.md](../03-auth.md))
- 開催中の村一覧は公開情報なので SSR 取得可。`/recruiting` 公開 API と内容が近い (step-0.17 で関係整理)
- **エイプリルフール企画は移行対象** (ユーザー判断確定、旧「対象外候補」から変更):
  - **過去アーカイブ**: `/archives/april-20250401`, `/archives/april-20250402` (2ch スレ風 AA ページ、`IndexContent` 流用 + ハードコード AA。特定村への直リンク `village/338`, `village/578` を含む) をそのまま移行
  - **2026 のランダム役職表示**: 現状ホーム `/` の 4/1 限定インライン表示 (`aprilFoolDescription` 28種ランダム、`IndexController.kt:33-63,80-83`) を、移行時は独立アーカイブ **`/archives/april-20260401`** として切り出す。ハードコードの役職説明リストはそのまま移植
  - React 化時はいずれも静的寄りページ + 未終了村一覧データ。年度ごとに `/archives/april-YYYYMMDD` を増やす運用を想定
- ナビ先の About/Intro/Announce/Rule/FAQ/Skill は step-0.4 で調査
