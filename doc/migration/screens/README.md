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

### B. ホーム・村一覧 (→ `step-0.2`)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint |
|---|---|---|---|
| ホーム / トップ | `index.html` | `index.js` | `GET /`, `GET /recruiting` (公開API) |
| イントロ | `intro.html` | - | `GET /intro` |
| 村一覧 | `village-list.html` | - | `GET /village-list`, `GET /api/village-list` |

### C. 新規村作成 (→ `step-0.3`)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint |
|---|---|---|---|
| 村作成フォーム | `new-village.html` | `new-village.js` | `RequestMapping /new-village`, `POST /new-village/confirm`, `/create`, `/divert/{id}` |
| 村作成確認 | `new-village-confirm.html` | `new-village-confirm.js` | (上記 confirm/create) |
| 発言制限設定 (サブ) | `new-village-say-restriction.html` / `new-village-rp-say-restriction.html` / `new-village-skill-say-restriction.html` | (new-village) | (フォーム内モーダル/部分) |
| キャラ選択 (関連) | `chara-list.html` / `chara.html` | (new-village) | `GET /getCharacterList`, `/getSelectableCharaList/{id}`, `/chara-group` |

### D. 役職・ルール・情報 (→ `step-0.4`)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint |
|---|---|---|---|
| 役職一覧 | `skill.html` | `skill.js` | `GET /skill`, `/skill-list`, `/skill/list` (公開API) |
| ルール | `rule.html` + `rule/{camp,detail,judge,mansion,other,room,skill,status}.html` | - | `GET /rule` |
| About / FAQ / 練習 | `about.html` / `faq.html` / `practice.html` | - | `GET /about`, `/faq`, `/practice` |
| 公告 | `announce.html` (965行) | - | `GET /announce` |

### E. ランダム機能 (→ `step-0.5`)

| 画面 | テンプレート | 担当 JS | 主な Controller / endpoint |
|---|---|---|---|
| ランダムキーワード | `random-keyword.html` / `new-random-keyword.html` | `random-keyword.js` | `GET /random-keyword/{id}`, `/new-random-keyword`, `POST /new-random-keyword`, `/update-random-keyword`, `/delete-random-keyword` |
| ランダムメッセージ | `random-message.html` | `random-message.js` | `GET /random-message` |

### F〜M. 村画面 (最重量・多数のサブ step)

メイン JS は `village.js` / `village-message.js`。Handlebars テンプレート (`village-template/*.html`) でクライアント描画。
共通レイアウト断片は `village/*.html` (24 ファイル) に分散。

| # | サブ領域 | 主なテンプレート | 主な endpoint |
|---|---|---|---|
| `step-0.6` | 村画面ベース (レイアウト/日付ナビ/状況サマリ) | `village.html`, `village/situation.html`, `footer-menu.html`, `form-area.html`, `village-day-list.html`, `display-settings.html` | `GET /village/{id}`, `/village/{id}/day/{day}`, `/village/{id}/update`, `getLatestMessageDatetime` |
| `step-0.7` | メッセージ表示 (一覧/フィルタ/アンカー) | `village-message.html`, `village-template/{message,message-partial,participants}.html`, `village/modal-filter.html` | `getMessageList`, `getAnchorMessage`, `/village/{id}/getAnchorMessages`, `/village/{id}/message`, `getParticipants` |
| `step-0.8` | 発言投稿 (通常/表情/装飾/アンカー/秘話/返信/アクション) | `village/say-form.html`, `say-confirm.html`, `creator-say-confirm.html`, `village/action-form.html`, `village/face-type-form.html` | `POST /village/{id}/say`, `/confirm`, `/action`, `/action-confirm`, `/creator-say`, `/creator-say-confirm` |
| `step-0.9` | 参加・退村・見学切替・希望役職 | `village/participate-form.html`, `participate-confirm.html`, `village/switch-participate-form.html`, `leave-form.html`, `change-skill-form.html` | `POST /village/{id}/participate`, `/confirm-participate`, `/switch-participate`, `/leave`, `/change-skill` |
| `step-0.10` | 能力使用・投票・コミット | `village/skill-area.html` (381行), `skill-selecting.html`, `skill-description.html`, `vote-form.html`, `commit-form.html` | `POST /village/{id}/setAbility`, `/setVote`, `/commit`, `getAttackTargetList` |
| `step-0.11` | RP (キャラ名/メモ/表情差分) | `village/change-name-form.html` | `POST /village/{id}/change-name`, `/memo`, `/add-face-type`, `/modify-face-type` |
| `step-0.12` | creator / admin 操作 | `village/creator-form.html`, `admin-form.html`, `village-settings.html` (669行), `village/agelimit-confirm.html`, `debug-form.html` | `GET/POST /village/{id}/settings`, `/cancel`, `/extend-epilogue`, `/shorten-epilogue`, `/allparticipate`, `/kick`, `/admin/village/{id}/{access,leave,vote}`, `/village/{id}/dayChange` |
| `step-0.13` | 村情報モーダル / 切り抜き | `village/modal-village-info.html`, `scrap.html` | `GET /village/{id}/scrap` |

### 保留・対象外 (要判断)

| 画面 | テンプレート | 扱い |
|---|---|---|
| エイプリルフール企画 | `april20250401.html` / `april20250402.html` | `GET /archives/april-2025040{1,2}`。**移行対象外候補** (要ユーザー判断) |
| エラーページ | `error.html` | Spring の error view。frontend のエラーページに置換 |
| レイアウト/フラグメント | `layout/{layout,top-layout,header,footer}.html`, `fragments/flagment-message.html` | React のレイアウト/共通コンポーネントに再設計 (画面ではない) |

## 横断ユースケース (→ `doc/migration/usecases/`)

詳細は [`../usecases/README.md`](../usecases/README.md) を参照。

| # | ユースケース | 概要 |
|---|---|---|
| `step-0.14` | 足音 (footstep) reveal | `getFootstepList`。誰がどの部屋を通ったかの reveal 制御 |
| `step-0.15` | Daychange (日付更新) | `POST /village/{id}/dayChange` (debug) → `DaychangeCoordinator`。村の状態遷移 |
| `step-0.16` | 認可マスク (死亡理由/投票/役職) | 状況・視点依存で見え方が変わるフィールドの整理 |

## 外部公開 API ピン留め (→ `step-0.17`)

互換性維持必須 ([02-backend.md](../02-backend.md))。現状レスポンスを記録し契約テストでピン留め:

- `GET /wolf-mansion/recruiting`
- `GET /wolf-mansion/village-record/list`
- `GET /wolf-mansion/village-record/latest-vid`
- `GET /wolf-mansion/skill/list`
- `GET /wolf-mansion/api/village/{id}`

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
