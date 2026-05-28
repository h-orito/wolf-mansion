# 画面: 村画面 — creator / admin / debug 操作

> 村画面 form-area の管理系。村主 (creator)、管理者 (admin)、デバッグ機能。

## 概要

- **テンプレート**: `village/creator-form.html`, `village/admin-form.html`, `village-settings.html` (669行), `village/agelimit-confirm.html`, `village/debug-form.html`
- **Controller**: `CreatorController` (村主) / `AdminController` (管理者) / `DebugController` (debug)
- **対象ユーザー**: 村主 / ROLE_ADMIN / デバッグモード

## 1. 機能 / 出来ることリスト

- **村主 (creator)**: 村設定変更、強制退村 (kick)、村建て発言 (天からのお告げ)、廃村、エピローグ延長/短縮
- **管理者 (admin)**: 強制退村、全員アクセス、全員自分投票、参加プレイヤー確認
- **debug**: テストアカウント一括参戦、時間を進める (Daychange)

## 2. 表示要素・UI 状態

- **creator-form**: 設定変更へのリンク、村建て発言フォーム、廃村、エピローグ延長/短縮、kick (参加者選択)
- **村設定 (village-settings)**: new-village とほぼ同じ設定項目 ([new-village.md](new-village.md))。`VillageSettingForm(village)` で現設定を初期化
- **admin-form**: 強制退村 / 全員アクセス / 全員自票 / 参加プレイヤー確認
- **debug-form**: 一括参戦 (人数) / 時間を進める。`isDebugMode` 時のみ。なりすましログイン用 `dummyLoginPlayerList`
- **agelimit-confirm**: R15/R18 村の年齢確認モーダル

## 3. 呼び出す API エンドポイント

### 村主 (CreatorController, `isCreator` チェック)
| メソッド | パス | 用途 |
|---|---|---|
| GET/POST | `/village/{id}/settings` | 村設定表示/保存 (`VillageSettingForm`) |
| POST | `/village/{id}/kick` | 強制退村 (charaId) |
| POST | `/village/{id}/creator-say-confirm` `/creator-say` | 村建て発言 (CREATOR_SAY) |
| POST | `/village/{id}/cancel` | 廃村 |
| POST | `/village/{id}/extend-epilogue` `/shorten-epilogue` | エピローグ延長/短縮 |

### 管理者 (AdminController, `/admin/**` → ROLE_ADMIN)
| メソッド | パス | 用途 |
|---|---|---|
| POST | `/admin/village/{id}/leave` | 強制退村 (villagePlayerId) |
| POST | `/admin/village/{id}/access` | 全員 lastAccess 更新 |
| POST | `/admin/village/{id}/vote` | 未投票者に自己投票挿入 |
| GET | `/admin/village/{id}/player` | 参加プレイヤー一覧 (JSON) |

### debug (DebugController, `app.debug:true` のみ)
| メソッド | パス | 用途 |
|---|---|---|
| POST | `/village/{id}/allparticipate` | テストアカウント一括参戦 (人数、ランダム希望役職) |
| POST | `/village/{id}/dayChange` | 時間を進める (最新日 daychangeDatetime を now-1秒 → `changeDayIfNeeded`) |

## 4. 既存 JS の挙動

- 設定/各操作はフォーム submit 中心。kick は参加者選択
- debug の dayChange は **e2e で日付進行に利用** (step-0.15)

## 5. 権限による分岐 / 6. 認可マスク

- creator: `creatorCoordinator.isCreator(username, villageId)` で全 endpoint をガード (不一致は redirect)
- admin: Spring Security `/admin/**` → `hasRole("ADMIN")`
- debug: `app.debug` プロパティ (本番 false)
- **要注意**: AdminController は **DBFlute Bhv を直接操作** (domain 層を経由しない)。REST 化時に domain/coordinator 経由へ整理するか要検討

## 7. 視覚比較

- 既存 `:8091` (村主/管理者/debug 各権限)。設定画面、creator メニュー、admin メニュー、debug メニュー

## 8. 関連 e2e ケース候補

- [ ] 村主: 設定変更 → 反映、kick、村建て発言、廃村、エピローグ延長/短縮
- [ ] 管理者: 強制退村、全員アクセス、全員自票
- [ ] debug: 一括参戦 → dayChange で進行 (e2e の基盤)

## メモ / 移行時の注意

- **設定変更は new-village と同一フォーム体系** (`VillageSettingForm`、闇鍋編成/発言制限含む)。REST 化時はフォーム/変換を共通化 ([new-village.md](new-village.md))
- 「重要エンドポイント」(creator/admin 操作) は **JWT claim だけでなく DB 再確認** ([03-auth.md](../03-auth.md) の権限方針) の対象候補
- AdminController の Bhv 直接操作は要リファクタ検討 (DDD 維持方針と齟齬)
- **debug dayChange は REST 化後も同等 endpoint を残す** (e2e 必須、[05-e2e.md](../05-e2e.md))。原画村パスワード必須など settings の制約も維持
- `agelimit-confirm` (R15/R18 確認) は表示設定 Cookie と連動 (確認済みフラグ)
