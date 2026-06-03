# 画面: 村設定変更

> 村主 (creator) が **募集中 (プロローグ) の村**の設定を変更する画面。設定項目の体系は [new-village.md](../new-village.md) (新規村作成) とほぼ共通で、フォーム/変換ロジックを大きく流用できる。村主のその他操作は [village-creator.md](village-creator.md) を参照 (管理者は [village-admin.md](village-admin.md)、デバッグは [village-debug.md](village-debug.md))。

## 概要

- **URL (既存)**: `GET /village/{villageId}/settings` (表示) / `POST /village/{villageId}/settings` (保存)
- **テンプレート**: `village-settings.html` (669行)
- **担当 JS**: `new-village.js` (`village-settings.html:8` — 新規村作成と**同一 JS** を共有。闇鍋/固定切替・発言制限の行コピー等の動的 UI を再利用)
- **Controller**: `CreatorController.settingsIndex` / `storeSettings` (`CreatorController.kt:62-105`)
- **Form / Validator**: `VillageSettingForm` (`VillageSettingForm.kt`) + `SettingFormValidator` (`SettingFormValidator.kt`) + ドメインガード `Village.assertModifySetting()` (`Village.kt:178-184`)
- **View**: `VillageSettingsContent`
- **対象ユーザー**: **村主 (creator) のみ**、かつ **募集中 (プロローグ) の村のみ**

## 1. 機能 / 出来ることリスト

- 募集中の村の各種設定を変更して保存
- new-village と異なり **確認画面・確認モーダルは無し** (バリデーション通過後そのまま保存 → `redirect /village/{id}#bottom`)

## 2. 表示要素・UI 状態 (7 セクション)

`village-settings.html` の構成 (new-village とほぼ同型):

| セクション | 行 | 主な項目 |
|---|---|---|
| 基本設定 | `:23` | villageName(5-40字) / startPersonMinNum(≥8) / personMaxNum(≤999) / 更新間隔(時/分/秒) / 開始日時(年月日時分) / welcomeRange(誰歓/身内) / ageLimit(R15/R18) / joinPassword |
| キャラチップ設定 | `:136` | **キャラセット・ダミーキャラ名は read-only 表示** (`<p th:text>`、変更不可)。**`dummyDay1Message` (1日目発言) のみ編集可** |
| 詳細ルール設定 | `:167` | 構成 (固定/闇鍋切替)・固定編成テキスト・闇鍋編成 (陣営/役職/人狼の配分)・各ルール bool (記名投票/連続襲撃/突然死/コミット/連続ガード/アクション/転生全役職 等)・秘話範囲 |
| 見学、閲覧設定 | `:415` | availableSpectate / visibleGraveSpectateMessage / openSkillInGrave 等 |
| 身内村向け設定 | `:478` | (身内向けオプション) |
| 特殊ルール向け | `:499` | (特殊ルールオプション) |
| RP村向け | `:609` | (RP 村向けオプション) |

- バリデーションエラーは各項目に `th:errors` で描画。`errorMessage` (オリジナルキャラ村のパスワード必須など) は上部に表示

### ⚠️ new-village にあって settings で編集できない項目

`VillageSettingForm` は new-village のフォームから以下を**持たない** (構造的に変更不可。`merge()` でも更新しない、`CreatorController.kt:230-` / `VillageSettingForm.kt`):

- **キャラセット選択** (`characterSetId` / `shouldOriginalImage`)
- **ダミーキャラの識別子** (`dummyCharaId` / `dummyCharaName` / `dummyCharaShortName` / 入村発言 `dummyJoinMessage` / オリジナル画像 `dummyCharaImageFile`)
- → キャラチップ設定セクションでは上記を read-only 表示し、**1日目発言 (`dummyDay1Message`) のみ**編集可能

### 状態依存の編集制約 (`Village.assertModifySetting()`)

保存時にドメインで以下を assert (`Village.kt:178-184`)。これらは「現在の村状態に応じて編集できない値」:

- **プロローグ中のみ** (`latestDay() > 0` = プロローグ終了済みは変更不可)
- 開始日時を**現在より過去**にできない
- 開始日時は**最初に建てた日時の 14 日後以降**にできない
- 定員 (`personMax`) を**既に入村済みの人数未満**にできない
- 見学者が既にいる場合、**見学入村を不可にできない**

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/village/{villageId}/settings` | 設定フォーム表示 (現設定で初期化) | SSR (creator-form の「村設定変更」リンク, `creator-form.html:33-40`) |
| POST | `/village/{villageId}/settings` | 設定保存 (`VillageSettingForm`) | フォーム submit |
| GET | `/getCharacterList?charaGroupId=...` | (闇鍋等の動的 UI で new-village.js が利用) | `new-village.js` |

- 設定変更リンクは `content.availableSettingsUpdate` が真のときのみ表示 (`creator-form.html:33`)

## 4. 既存 JS の挙動

- `new-village.js` を共有。固定/闇鍋編成の表示切替、発言制限テーブルの行コピー、構成欄の「N人：」プレフィックス付与/除去など new-village と同じ動的処理 ([new-village.md](../new-village.md) 4 章参照)
- キャラチップ選択 UI は read-only 表示のため、new-village.js のキャラセット連動 (`getCharacterList` でプルダウン再構築) は実質使われない

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 村主 (creator) | 募集中の村のみ設定変更可。`creatorCoordinator.isCreator(username, villageId)` で GET/POST 双方をガード (不一致は `redirect /village/{id}#bottom`) |
| その他 (参加者/管理者/匿名) | 設定変更リンク非表示・直接アクセスも redirect |

- 進行中以降は `availableSettingsUpdate` が false + `assertModifySetting` で弾かれる

## 6. 認可マスク

- なし (creator 本人のみがアクセスする編集画面)

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/village/{id}/settings` (村主でログイン・募集中の村)。7 セクション、闇鍋/固定切替、発言制限テーブル、キャラチップ read-only 表示

## 8. 関連 e2e ケース候補

- [ ] 村主が設定変更 → 保存 → 村画面に反映
- [ ] バリデーション: 定員 < 最少人数 NG / 更新間隔 1分〜72時間 / 開始日時 過去NG・建て日+14日超NG / 入村PW 3-12字 / オリジナルキャラ村はPW必須
- [ ] 状態制約: 入村済み人数未満の定員に変更不可 / 見学者ありで見学不可に変更不可
- [ ] 非村主 / 進行中の村ではアクセス不可 (redirect)

## データ構成 / 変換

- `VillageSettingForm(village)` で現設定を初期化 (`VillageSettingForm.kt:156-263`)
- `SettingFormValidator`: new-village と同系の相関チェック。メッセージキーは `NewVillageForm.validator.*` を**流用** (定員<最少人数 / 更新間隔 / 開始日時 / 入村PW長 / dummyDay1Message 内容 など, `SettingFormValidator.kt`)
- 保存: `CreatorController.merge(village, form)` で `Village` を copy → `creatorCoordinator.saveSettings()` (内部で `assertModifySetting()`)
- オリジナルキャラ村は `joinPassword` 必須 (`CreatorController.kt:89-93`)

## メモ / 移行時の注意

- **new-village とフォーム体系を共有**。REST/React 化時は設定フォームのコンポーネント・zod スキーマ・`toVillage`/`merge` 変換を **new-village と共通化**し、settings 側では編集不可項目 (キャラチップ/ダミーキャラ識別子) を disabled/非表示にする差分のみ持たせる ([new-village.md](../new-village.md))
- new-village と違い **確認ステップが無い** (直接保存)。React 化でも単一フォーム + 保存でよい (確認モーダルは不要)
- バリデーションは **クライアント zod (new-village と共通) + サーバ `SettingFormValidator` + ドメイン `assertModifySetting`** の多層。特に `assertModifySetting` の状態依存チェック (入村済み人数・見学者・建て日+14日) は **サーバ/ドメインにのみ存在**するため、移行後も backend に残す (クライアントだけでは判定不能)
- REST 化: `GET /api/v1/villages/{id}/settings` (現設定取得) + `PUT /api/v1/villages/{id}/settings` (保存) 等に整理。creator ガードは [03-auth.md](../../03-auth.md) の「重要エンドポイント DB 再確認」対象候補
