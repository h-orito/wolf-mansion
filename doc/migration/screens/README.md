# 画面別調査 index (Step 0)

Thymeleaf SSR から REST + React への移行にあたり、既存の全画面を棚卸しした index。
各画面の詳細調査は `doc/migration/screens/<screen>.md` に、横断ユースケースは `doc/migration/usecases/<usecase>.md` に分けて記録する。

- 既存稼働環境: `http://localhost:8091/wolf-mansion/` (ローカル DB あいのり、視覚比較に利用)
- context-path: `/wolf-mansion`、port 8089、`app.debug: true` (Daychange デバッグ機能有効)
- テンプレート: `src/main/resources/templates/` (60+ ファイル)
- 既存 JS: `src/main/resources/static/app/js/` (13 ファイル、jQuery + Handlebars)
- Controller: `src/main/kotlin/com/ort/app/api/` (18 クラス)

## ドキュメント合格基準

各画面 md / ユースケース md は以下を満たしたら「調査完了」とする (08-step-plan / 04-frontend に準拠):

1. **機能 / 出来ることリスト**: その画面で可能な操作を漏れなく列挙
2. **表示要素・UI 状態**: 主要コンポーネント、ローディング/空/エラー等の状態
3. **呼び出す API エンドポイント**: SSR レンダリング用 + 既存 JS が叩く内部 AJAX を実パスで列挙
4. **対応する既存 JS の挙動**: `static/app/js/*.js` の動的描画 / AJAX / バリデーション / フォーム制御を読み解いて記述
5. **権限による分岐**: 匿名 / ログイン済 / 村参加者 / 村主 (creator) / 管理者 (admin) での出し分け
6. **認可マスク**: 足音 / 死亡理由 / 投票先など状況依存で見え方が変わるフィールド (該当時)
7. **視覚比較**: 既存環境 (`:8091`) を一次基準にする。状態別 (匿名 / ログイン / 参加者 / 各村 status など) の確認ポイントを md に記述。スクショは `.playwright-mcp/` に随時取得 (git 管理外。PNG は commit せずライブ環境で都度比較)
8. **関連 e2e ケース候補**: 移行後の挙動確認に使うシナリオのドラフト

## 画面カテゴリ一覧

### A. 認証・プレイヤー (→ `step-0.1` ✅ 調査済)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint | 調査 md |
|---|---|---|---|---|
| ログイン | `login.html` | (common) | `GET/POST /login` (Security), `POST /api/login` | [auth-login.md](auth-login.md) |
| 新規登録 | `new-player.html` | `new-player.js` | `GET/POST /new-player` | [auth-signup.md](auth-signup.md) |
| パスワード変更 | `change-password.html` | (common) | `GET/POST /change-password` (要認証) | [auth-change-password.md](auth-change-password.md) |
| プロフィール / 戦績 | `user.html` | `user.js` | `GET /user/{userName}`, `POST /user-detail` | [player-profile.md](player-profile.md) |
| プレイヤー一覧 | `player-list.html` | `user-list.js` | `GET /user-list` | [player-list.md](player-list.md) |

### B. ホーム・村一覧 (→ `step-0.2` ✅ 調査済)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint | 調査 md |
|---|---|---|---|---|
| ホーム / トップ | `index.html` | `index.js` | `GET /`, `GET /recruiting` (公開API) | [home.md](home.md) |
| イントロ | `intro.html` | (index.js) | `GET /intro` | [intro.md](intro.md) |
| 村一覧 | `village-list.html` | - | `GET /village-list`, `GET /api/village-list` | [village-list.md](village-list.md) |

> 共通基盤: `user` グローバル属性は `UserInfoInterceptor` が全 ModelAndView に注入 (→ 移行後は `useMe` CSR)。ログインフォームは共通ヘッダ (top-layout/header)。

### C. 新規村作成 (→ `step-0.3` ✅ 調査済)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint | 調査 md |
|---|---|---|---|---|
| 村作成フォーム+確認+発言制限+流用 | `new-village.html` / `new-village-confirm.html` / `new-village-*-say-restriction.html` | `new-village.js` / `new-village-confirm.js` | `RequestMapping /new-village`, `POST /new-village/{confirm,create,divert/{id}}`, `GET /getCharacterList` | [new-village.md](new-village.md) |
| キャラ選択 (関連、別途) | `chara-list.html` / `chara.html` | - | `/chara-group`, `/getSelectableCharaList/{id}` | (step-0.4 で調査) |

### D. 役職・ルール・情報 (→ `step-0.4` ✅ 調査済)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint | 調査 md |
|---|---|---|---|---|
| 役職一覧 | `skill.html` | `skill.js` | `GET /skill`, `/skill-list`, `/skill/list` (公開API) | [skill.md](skill.md) |
| ルール | `rule.html` + `rule/*` | (index.js) | `GET /rule` (`RuleContent` 駆動) | [rule.md](rule.md) |
| About (本サイトは) | `about.html` | (index.js) | `GET /about` | [about.md](about.md) |
| FAQ | `faq.html` | (index.js) | `GET /faq` | [faq.md](faq.md) |
| 練習問題 | `practice.html` | (index.js) | `GET /practice` | [practice.md](practice.md) |
| お知らせ (公告) | `announce.html` | - | `GET /announce` | [announce.md](announce.md) |
| キャラチップ一覧 | `chara-list.html` | - | `GET /chara-group`, `/getCharacterList`, `/getFaceImgUrl/...` (横断 JSON API) | [charachip-list.md](charachip-list.md) |
| キャラチップ詳細 | `chara.html` | - | `GET /chara-group/{id}` | [charachip-detail.md](charachip-detail.md) |

### E. ランダム機能 (→ `step-0.5` ✅ 調査済)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint | 調査 md |
|---|---|---|---|---|
| ランダムキーワード 一覧/作成/編集/削除 | `random-message.html` / `new-random-keyword.html` / `random-keyword.html` | `random-message.js` / `random-keyword.js` | `GET /random-message`, `/new-random-keyword`, `/random-keyword/{id}`, `POST /{new,update,delete}-random-keyword` | [random-keyword.md](random-keyword.md) |

> random-* は現状 permitAll。**移行方針 (確定)**: 書き込み (作成・更新・削除) はログイン必須、閲覧は公開 ([random-keyword.md](random-keyword.md))。

### F〜M. 村画面 (最重量・多数のサブ step)

メイン JS は `village.js` / `village-message.js`。Handlebars テンプレート (`village-template/*.html`) でクライアント描画。
共通レイアウト断片は `village/*.html` (24 ファイル) に分散。

> 村画面は機能が多いため、調査 md は **`doc/migration/screens/village/` サブディレクトリ**に集約している (村一覧 `village-list.md` は別カテゴリ B のため screens 直下のまま)。

| # | サブ領域 | 主なテンプレート | 主な endpoint | 調査 md |
|---|---|---|---|---|
| `step-0.6` ✅ | 村画面ベース (レイアウト/日付ナビ/situation 二層/ポーリング) | `village.html`, `footer-menu.html`, `form-area.html`, `village-day-list.html`, `display-settings.html` | `GET /village/{id}`, `/village/{id}/day/{day}`, `/village/{id}/update`, `getLatestMessageDatetime` | [village-base.md](village/village-base.md) |
| `step-0.6` ✅ | 状況サマリ (部屋割り/参加者/投票/足音) | `village/situation.html` | (村取得に同梱) | [village-situation-summary.md](village/village-situation-summary.md) |
| `step-0.7` ✅ | メッセージ表示 (一覧/フィルタ/アンカー) | `village-message.html`, `village-template/{message,message-partial,participants}.html`, `village/modal-filter.html` | `getMessageList`, `getAnchorMessage`, `/village/{id}/getAnchorMessages`, `/village/{id}/message`, `getParticipants` | [village-messages.md](village/village-messages.md) |
| `step-0.8` ✅ | 発言投稿 (通常/表情/装飾/アンカー/秘話/返信) | `village/say-form.html`, `say-confirm.html`, `creator-say-confirm.html` | `POST /village/{id}/say`, `/confirm` (creator-say は step-0.12) | [village-say.md](village/village-say.md) |
| `step-0.8` ✅ | アクション発言 (別パネル) | `village/action-form.html` | `POST /village/{id}/action`, `/action-confirm` | [village-action.md](village/village-action.md) |
| `step-0.9` ✅ | 参加・退村・見学切替・希望役職 | `village/participate-form.html`, `participate-confirm.html`, `village/switch-participate-form.html`, `leave-form.html`, `change-skill-form.html` | `POST /village/{id}/participate`, `/confirm-participate`, `/switch-participate`, `/leave`, `/change-skill`, `GET /getSelectableCharaList/{id}` | [village-participate.md](village/village-participate.md) |
| `step-0.10` ✅ | 能力使用 (役職別パターン A〜H) | `village/skill-area.html` (381行), `skill-selecting.html`, `skill-description.html` | `POST /village/{id}/setAbility`, `getAttackTargetList`, `getFootstepList` | [village-ability.md](village/village-ability.md) |
| `step-0.10` ✅ | 投票 | `village/vote-form.html` | `POST /village/{id}/setVote` | [village-vote.md](village/village-vote.md) |
| `step-0.10` ✅ | コミット | `village/commit-form.html` | `POST /village/{id}/commit` | [village-commit.md](village/village-commit.md) |
| `step-0.11` ✅ | RP (キャラ名/簡易メモ/表情差分) | `village/change-name-form.html`, `village/face-type-form.html` | `POST /village/{id}/change-name`, `/memo`, `/add-face-type`, `/modify-face-type` | [village-rp.md](village/village-rp.md) |
| `step-0.11` ✅ | 設定モーダル (表示設定/Discord通知) | `village/display-settings.html` (`#modal-dsetting`) | `POST /village/{id}/notification-setting` (表示設定は Cookie) | [village-user-settings.md](village/village-user-settings.md) |
| `step-0.12` ✅ | 村主 (creator) 操作 | `village/creator-form.html` | `/kick`, `/creator-say(-confirm)`, `/cancel`, `/extend-epilogue`, `/shorten-epilogue` | [village-creator.md](village/village-creator.md) |
| `step-0.12` ✅ | 管理者 (admin) 操作 | `village/admin-form.html` | `/admin/village/{id}/{access,leave,vote,player}` | [village-admin.md](village/village-admin.md) |
| `step-0.12` ✅ | debug (ローカル開発向け) | `village/debug-form.html` | `/allparticipate`, `/village/{id}/dayChange`, `/login`, `/logout` | [village-debug.md](village/village-debug.md) |
| `step-0.12` ✅ | 村設定変更 (creator・募集中のみ) | `village-settings.html` (669行) | `GET/POST /village/{id}/settings` | [village-settings.md](village/village-settings.md) |
| `step-0.13` ✅ | 村情報モーダル | `village/modal-village-info.html` | (村取得に同梱) | [village-info.md](village/village-info.md) |
| `step-0.13` ✅ | 村切り抜き (別画面) | `scrap.html` | `GET /village/{id}/scrap` | [village-scrap.md](village/village-scrap.md) |

> 村画面 (0.6〜0.13) 調査完了。参加者一覧/部屋割り/投票/足音タブは [village-situation-summary.md](village/village-situation-summary.md) に記載。

### 保留・対象外 (要判断)

| 画面 | テンプレート | 扱い |
|---|---|---|
| エラーページ | `error.html` | Spring の error view。frontend のエラーページに置換 |
| レイアウト/フラグメント | `layout/{layout,top-layout,header,footer}.html`, `fragments/flagment-message.html` | React のレイアウト/共通コンポーネントに再設計 (画面ではない) |

> **エイプリルフール企画は移行対象に確定** (`/archives/april-2025040{1,2}` + 2026 ランダム役職表示を `/archives/april-20260401` 化)。詳細は [home.md](home.md) のメモ参照。共通フッター (`layout/footer`) の広告/投げ銭/プライバシーポリシーも home.md に記載。

## 横断ユースケース (→ `doc/migration/usecases/`)

詳細は [`../usecases/README.md`](../usecases/README.md) を参照。

| # | ユースケース | 概要 |
|---|---|---|
| `step-0.14` | 足音 (footstep) reveal | `getFootstepList`。誰がどの部屋を通ったかの reveal 制御 |
| `step-0.15` | Daychange (日付更新) | `POST /village/{id}/dayChange` (debug) → `DaychangeCoordinator`。村の状態遷移 |
| `step-0.16` | 認可マスク (死亡理由/投票/役職) | 状況・視点依存で見え方が変わるフィールドの整理 |

## 外部公開 API ピン留め (→ `step-0.17` ✅ → [public-api-pinning.md](public-api-pinning.md))

互換性維持必須 ([02-backend.md](../02-backend.md))。現状レスポンスを記録し契約テストでピン留め:

- `GET /wolf-mansion/recruiting`
- `GET /wolf-mansion/village-record/list`
- `GET /wolf-mansion/village-record/latest-vid`
- `GET /wolf-mansion/skill/list` (★ snake_case)
- `GET /wolf-mansion/api/village/{id}`
- `GET /wolf-mansion/api/village-list` (★ camelCase)

> 重要: エンドポイントで命名規則が混在 (snake_case / camelCase)、存在しない村は 500+stacktrace。各々を個別維持。詳細は [public-api-pinning.md](public-api-pinning.md)。

## 子 Issue ロードマップ

step-0 bootstrap (本 PR) の merge 後、以下を `add-issue` で順次作成する:

| 子 Issue | 対象 |
|---|---|
| `step-0.1` | 認証・プレイヤー画面 |
| `step-0.2` | ホーム・村一覧 |
| `step-0.3` | 新規村作成 |
| `step-0.4` | 役職・ルール・情報 |
| `step-0.5` | ランダム機能 |
| `step-0.6`〜`step-0.13` | 村画面 (8 サブ領域) |
| `step-0.14`〜`step-0.16` | 横断ユースケース (足音 / Daychange / マスク) |
| `step-0.17` | 外部公開 API ピン留め |

> 粒度・順序は調査の進捗で調整可。村画面のサブ step 分割は本 index を起点に Step 0 完了時点で最終確定する。
