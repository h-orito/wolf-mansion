# 画面: 新規村作成

> new-village (フォーム) + new-village-confirm (確認) + 発言制限サブ + 流用 + キャラ選択 を1画面フローとして扱う。**最も設定項目が多い画面** (~40 フィールド)。

## 概要

- **URL (既存)**: `RequestMapping /new-village` (GET/POST 戻る両対応) → `POST /new-village/confirm` → `POST /new-village/create`
- **テンプレート**: `new-village.html` (746行) / `new-village-confirm.html` (517行) / `new-village-say-restriction.html` / `new-village-rp-say-restriction.html` / `new-village-skill-say-restriction.html` (発言制限サブフラグメント)
- **担当 JS**: `new-village.js` / `new-village-confirm.js`
- **Controller**: `NewVillageController`
- **対象ユーザー**: ログイン済 (`canCreateVillage` を満たす、=どこにも参加していない等)

## 1. 機能 / 出来ることリスト

- 村の各種設定を入力して村作成
- **既存村からの流用** (divert): **エピローグ/終了/廃村の村** (`VillageStatus.notProgressStatusLsit`) の設定をコピーして初期値化 (`NewVillageController.kt:167-171`)。募集中・進行中は流用候補に含まれない
- 確認画面 → 作成 → 作成した村へ遷移
- ダミーキャラのキャラチップ選択 (or オリジナル画像アップロード)
- 固定編成 / 闇鍋編成 の切替と詳細設定
- 役職別・発言種別・RP の発言制限設定

## 2. 表示要素・UI 状態 (フォーム項目グループ)

| グループ | 主なフィールド (default) |
|---|---|
| 基本 | `villageName` (5-40字), `startPersonMinNum` (≥8, 既定8), `personMaxNum` (≤999, 既定20) |
| 更新間隔 | `dayChangeIntervalHours` (0-72, 既定24) / `Minutes` / `Seconds` |
| 開始日時 | `startYear/Month/Day/Hour/Minute` (既定: 現在+7日 0:00) |
| 募集/年齢 | `welcomeRange` (誰歓/身内), `ageLimit` (R15/R18) → タグに変換 |
| ルール (bool) | `openVote`(記名投票,既定true), `possibleSkillRequest`(役職希望,true), `availableSameWolfAttack`(連続襲撃,true), `openSkillInGrave`(墓下役職公開,false), `visibleGraveSpectateMessage`(墓下見学地上可視,false), `availableSpectate`(観戦,false), `creatorIsProducer`(村建てプロデューサー,false), `availableSuddonlyDeath`(突然死,false), `availableCommit`(コミット,false), `availableGuardSameTarget`(連続ガード,true), `availableAction`(アクション,false), `reincarnationSkillAll`(転生全役職,false) |
| キャラ | `shouldOriginalImage`(自前画像か,false), `characterSetId` (複数, 既定[1]), `dummyCharaId`, `dummyCharaName`(1-40,既定"楽天家 ゲルト"), `dummyCharaShortName`(1字,既定"楽"), `dummyJoinMessage`(1-400), `dummyDay1Message`(max400)。※ `dummyCharaImageFile` (オリジナル時 multipart) は**確認画面でのみ入力** |
| 入村制限 | `joinPassword` (入村パスワード) |
| 構成 | `organization` (固定編成テキスト), `randomOrganization`(闇鍋か,false), `campAllocationList` (闇鍋: 村人/人狼/狐/恋人/愉快犯 陣営別配分 + 役職別配分), `wolfAllocation` (人狼数配分) |
| 秘話 | `allowedSecretSayCode` (既定: なし) |
| 発言制限 | `sayRestrictList` (役職別 通常発言: count既定20/length既定400), `skillSayRestrictList` (人狼の囁き/共鳴/恋人/念話), `rpSayRestrictList` (アクション) |

確認画面 (`new-village-confirm`, 517行): 全セクション (基本 / キャラチップ / 詳細ルール / 見学・閲覧 / 身内村 / 特殊ルール / RP村) を表形式で再掲 + 闇鍋編成は二次元配分テーブルも描画。**オリジナル画像のファイル入力 (`dummyCharaImageFile`) は最初のフォームには無く、この確認画面でのみ表示** (`new-village-confirm.html:77-79`、new-village.html:212 の案内文と整合)。`new-village-confirm.js` のプレビューは入村発言/1日目発言の 2 要素 (`#dummy-chara-img` / `#dummy-chara-img2`) に反映。

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET/POST | `/new-village` | フォーム初期表示 (POST は確認からの戻り) | SSR |
| POST | `/new-village/divert/{villageId}` | 既存村の設定を流用して初期化 | `new-village.js` `#divert-btn` |
| POST | `/new-village/confirm` | バリデーション + 確認画面 | フォーム submit |
| POST | `/new-village/create` | 村作成 → `redirect /village/{id}#bottom` | 確認画面 submit |
| GET | `/getCharacterList?charaGroupId=...` | キャラセットのキャラ一覧 (JSON) | `new-village.js` (キャラセット変更時) |

## 4. 既存 JS の挙動

### `new-village.js`
- **キャラセット連動**: `#characterSetId` 変更 → `GET /getCharacterList` → ダミーキャラ プルダウン再構築 + 画像差し替え + 入村/1日目発言の既定値オート入力 (上書き確認 dialog あり)
- **構成欄**: 表示時に各行へ「N人：」プレフィックス付与、submit 時に除去
- **発言制限**: チェックボックスで length/count 入力を有効/無効化。先頭行の値を全行へコピーするボタン
- **流用**: `#divert-btn` → form action を `/new-village/divert/{id}` にして submit
- **編成切替**: 闇鍋/固定で `#random-org` / `#fix-org` 表示切替
- **キャラチップ切替**: キャラチップ使用 / オリジナル画像 で `#use-charachip` / `#use-original-chara` 表示切替 (オリジナルは placeholder 画像)

### `new-village-confirm.js`
- 戻るボタン → `/new-village` へ再 submit (入力値保持)
- ファイル入力 (`:file`) 変更 → ファイル名表示 + 画像プレビュー (FileReader)

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 匿名 | アクセス不可 (ホームに導線なし) |
| ログイン済 (作成可) | フォーム表示・作成可 (`player.canCreateVillage()`) |
| ログイン済 (参加中等) | ホームで「村を建てる」が出ない (`content.participate`) |

## 6. 認可マスク

- なし (作成前)

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/new-village` (要ログイン)。フォーム全体、闇鍋/固定切替、発言制限テーブル、確認画面

## 8. 関連 e2e ケース候補

- [ ] 既定値での村作成 → 村画面へ遷移
- [ ] キャラセット変更 → ダミーキャラ候補・画像が変わる
- [ ] 固定編成 / 闇鍋編成 の切替と作成
- [ ] 発言制限の設定 → 作成後に反映
- [ ] 流用: 既存村から初期化
- [ ] バリデーション: villageName 文字数 / 定員 < 最少人数 NG / 更新間隔 1分〜72時間 / 開始日時は過去 NG かつ **2週間先まで** / 入村パスワード 3-12字 / オリジナル時パスワード必須 / オリジナル画像 ≤100KB / 構成 (固定: 各人数の行が過不足なく1つ・村人/人狼必須・狼過半数NG・恋人偶数 等、闇鍋: 陣営/役職/転生配分) (`NewVillageFormValidator.kt`)

## データ構成 / 変換

- `NewVillageForm` (api/request) ← 入力。`initialize()` で既定値、`override(village)` で流用、`toVillage(player)` で `Village` ドメインモデルへ変換
- ネスト Form: `RandomOrganizationCampForm` / `RandomOrganizationWolfForm` / `SkillSayRestrictForm` / `MessageTypeSayRestrictForm` (`api/request/setting/`)
- バリデータ: `NewVillageFormValidator` (相関チェック)
- 作成: `VillageCoordinator.assertCreateVillage()` + `registerVillage()`

## メモ / 移行時の注意

### 新実装の方針 (確定・**ユーザー指示による UI/UX 変更**)

> 以下は**ユーザーが明示的に指示した UI/UX 変更**。指示のない箇所は現状踏襲 ([04-frontend.md](../04-frontend.md) の UI/UX 現状維持原則)。

- **確認画面 → 確認モーダルに変更** (ユーザー指示): 現状は「フォーム画面 → 別ページの確認画面 (`new-village-confirm.html`) → 作成」の 2 段構成だが、新実装では確認を **別画面でなくモーダル表示**にする。「戻る」での再 submit (入力値保持) は不要になり、モーダルを閉じれば元フォームに戻る
- **オリジナルダミー画像アップロードは確認モーダルでなく元フォームで行う**: 現状は `dummyCharaImageFile` を確認画面でのみ入力する作りだが (本 md 2 章・`new-village-confirm.html:77-79`)、新実装では **元フォーム側**でアップロードする。確認モーダルはプレビュー/確認のみ
- ⚠️ **バリデーション維持が最重要の注意点**: この画面は `NewVillageFormValidator` を中心に**項目単位 + 相互チェック (相関) のバリデーションが大量**にある (本 md 8 章の e2e 候補参照)。React 化でエラー表示や個々のチェック内容が欠落しないよう、**項目単位 (相互チェック含む) で既存実装の検証内容を突合**すること
  - ただし **サーバーサイドの検証 (`NewVillageFormValidator` + `assertCreateVillage`) が維持されれば、最終的なデータ整合性は基本的に担保される**。クライアント側 zod はあくまで UX 向上であり、サーバー検証を正本とする
- **村設定変更画面 (`village-settings.html`, 669行) と酷似**: 設定項目の体系 (`VillageSettingForm`) はこのフォームとほぼ共通で、**コンポーネント/変換ロジックを大きく流用できる**はず。差分は「設定変更時は編集不可の項目 (キャラチップ/ダミーキャラ識別子) がある」点と「確認ステップが無い」点。設定変更画面は独立 md **[village-settings.md](village/village-settings.md)** を参照。REST/React 化時は両者でフォーム部品を共通化する前提で設計する

### その他

- **本移行で最も大きいフォーム**。React 化時は設定をセクション分割したコンポーネント群 + react-hook-form + zod で再構築 ([04-frontend.md](../04-frontend.md))
- 闇鍋編成 (campAllocation × skillAllocation の二次元配分) と発言制限リストの **動的行 UI** が難所
- ダミーキャラのオリジナル画像は **multipart アップロード** (`dummyCharaImageFile`)。REST 化時は multipart or 事前アップロード+URL のどちらにするか要検討
- キャラ情報取得 `GET /getCharacterList` は `GET /api/v1/charachips/{id}/charas` 等へ整理 (step-0.4 のキャラ調査と統一)
- 「N人：」プレフィックスの付与/除去は表示専用ロジック。React では表示と値を分離
- `toVillage()` の変換ロジック (Form → ドメイン) は backend に残るので、REST 化後も再利用可能
