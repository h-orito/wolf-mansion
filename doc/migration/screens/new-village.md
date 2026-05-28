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
- **既存村からの流用** (divert): 終了/募集中等の村の設定をコピーして初期値化
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
| キャラ | `shouldOriginalImage`(自前画像か,false), `characterSetId` (複数, 既定[1]), `dummyCharaId`, `dummyCharaImageFile` (オリジナル時 multipart), `dummyCharaName`(1-40,既定"楽天家 ゲルト"), `dummyCharaShortName`(1字,既定"楽"), `dummyJoinMessage`(1-400), `dummyDay1Message`(max400) |
| 入村制限 | `joinPassword` (入村パスワード) |
| 構成 | `organization` (固定編成テキスト), `randomOrganization`(闇鍋か,false), `campAllocationList` (闇鍋: 村人/人狼/狐/恋人/愉快犯 陣営別配分 + 役職別配分), `wolfAllocation` (人狼数配分) |
| 秘話 | `allowedSecretSayCode` (既定: なし) |
| 発言制限 | `sayRestrictList` (役職別 通常発言: count既定20/length既定400), `skillSayRestrictList` (人狼の囁き/共鳴/恋人/念話), `rpSayRestrictList` (アクション) |

確認画面 (`new-village-confirm`): 入力値サマリ + 開始日時/更新間隔の整形表示 + ダミーキャラ画像プレビュー。

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
- [ ] バリデーション: villageName 文字数、最低人数 等

## データ構成 / 変換

- `NewVillageForm` (api/request) ← 入力。`initialize()` で既定値、`override(village)` で流用、`toVillage(player)` で `Village` ドメインモデルへ変換
- ネスト Form: `RandomOrganizationCampForm` / `RandomOrganizationWolfForm` / `SkillSayRestrictForm` / `MessageTypeSayRestrictForm` (`api/request/setting/`)
- バリデータ: `NewVillageFormValidator` (相関チェック)
- 作成: `VillageCoordinator.assertCreateVillage()` + `registerVillage()`

## メモ / 移行時の注意

- **本移行で最も大きいフォーム**。React 化時は設定をセクション分割したコンポーネント群 + react-hook-form + zod で再構築 ([04-frontend.md](../04-frontend.md))
- 闇鍋編成 (campAllocation × skillAllocation の二次元配分) と発言制限リストの **動的行 UI** が難所
- ダミーキャラのオリジナル画像は **multipart アップロード** (`dummyCharaImageFile`)。REST 化時は multipart or 事前アップロード+URL のどちらにするか要検討
- キャラ情報取得 `GET /getCharacterList` は `GET /api/v1/charachips/{id}/charas` 等へ整理 (step-0.4 のキャラ調査と統一)
- 「N人：」プレフィックスの付与/除去は表示専用ロジック。React では表示と値を分離
- `toVillage()` の変換ロジック (Form → ドメイン) は backend に残るので、REST 化後も再利用可能
