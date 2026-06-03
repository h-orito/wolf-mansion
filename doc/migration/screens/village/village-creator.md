# 画面: 村画面 — 村主 (creator) 操作

> 村画面 form-area の村主メニュー。**村主 (creator) = 村を建てた本人**で、自分の村に対する「村建て + その村の管理者」操作を行える。管理者 (admin、サイト全体管理) は [village-admin.md](village-admin.md)、ローカル開発向けは [village-debug.md](village-debug.md)、村設定変更は [village-settings.md](village-settings.md)。

## 概要

- **テンプレート**: `village/creator-form.html`
- **Controller**: `CreatorController` (`creatorCoordinator.isCreator(username, villageId)` で全 endpoint をガード、不一致は `redirect /village/{id}#bottom`)
- **対象ユーザー**: 村主 (creator) のみ。表示は `content.createPlayer` 真のとき (`creator-form.html:23`)

## 1. 機能 / 出来ることリスト

- 村設定変更 (→ [village-settings.md](village-settings.md)、`availableSettingsUpdate` 真のとき)
- **最終アクセス日時の確認** (プロローグ中、各メンバーの最終アクセス)
- 強制退村 (kick、プロローグ中)
- 村建て発言 (天からのお告げ、CREATOR_SAY)
- 廃村 (cancel、プロローグ中)
- エピローグ延長 / 短縮

## 2. 表示要素・UI 状態

- **設定変更リンク**: `content.availableSettingsUpdate` 真のとき表示 (`creator-form.html:33`) → [village-settings.md](village-settings.md)
- **最終アクセス日時テーブル** (`creator-form.html:44-57`): **プロローグ中 (`villageStatusCode == 'IN_PREPARATION'`) のみ表示**。各メンバーの `charaName` + `lastAccess` を一覧。プロローグ中の自動退村 (24h アクセスなし) 管理のための情報
- **kick フォーム** (`creator-form.html:58-`): プロローグ中のみ。参加者を選択して強制退村
- **村建て発言フォーム**: 「天からのお告げ」(CREATOR_SAY)。確認 → 投稿の 2 段
- **廃村フォーム**: プロローグ中のみ (`villageStatusCode == 'IN_PREPARATION'`)
- **エピローグ延長/短縮**: エピローグ中の操作

## 3. 呼び出す API エンドポイント (CreatorController, `isCreator` チェック)

| メソッド | パス | 用途 |
|---|---|---|
| GET/POST | `/village/{id}/settings` | 村設定表示/保存 (`VillageSettingForm`)。**詳細は [village-settings.md](village-settings.md)** |
| POST | `/village/{id}/kick` | 強制退村 (charaId) |
| POST | `/village/{id}/creator-say-confirm` `/creator-say` | 村建て発言 (CREATOR_SAY、確認→投稿) |
| POST | `/village/{id}/cancel` | 廃村 |
| POST | `/village/{id}/extend-epilogue` `/shorten-epilogue` | エピローグ延長/短縮 |

## 4. 既存 JS の挙動

- 各操作はフォーム submit 中心。kick は参加者選択
- 村建て発言は say-confirm 系の確認フロー ([village-say.md](village-say.md) の creator-say-confirm)

## 5. 権限による分岐 / 6. 認可マスク

- `creatorCoordinator.isCreator(username, villageId)` で全 endpoint をガード
- 最終アクセス日時・kick・廃村はプロローグ中のみ
- マスクなし (村主本人向け)

## 7. 視覚比較

- 既存 `:8091` (村主でログイン)。creator メニュー、プロローグ中の最終アクセス日時テーブル、kick、村建て発言

## 8. 関連 e2e ケース候補

- [ ] 村主: kick、村建て発言、廃村、エピローグ延長/短縮 (設定変更は [village-settings.md](village-settings.md))
- [ ] プロローグ中の最終アクセス日時テーブル表示

## メモ / 移行時の注意

- 「重要エンドポイント」(creator 操作) は **JWT claim だけでなく DB 再確認** ([03-auth.md](../../03-auth.md) の権限方針) の対象候補。村主判定 (`isCreator`) は DB で再確認する
- 村建て発言の確認フローは [village-say.md](village-say.md) と共通の say-confirm 系
- 設定変更は [village-settings.md](village-settings.md) (new-village とフォーム共通)
