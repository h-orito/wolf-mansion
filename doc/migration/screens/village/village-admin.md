# 画面: 村画面 — 管理者 (admin) 操作

> 村画面 form-area の管理者メニュー。**管理者 (admin) = サイト全体のシステム管理者** (`ROLE_ADMIN`)。村主 (creator、村単位) は [village-creator.md](village-creator.md)、ローカル開発向けは [village-debug.md](village-debug.md)。

## 概要

- **テンプレート**: `village/admin-form.html`
- **Controller**: `AdminController` (`/admin/**` → Spring Security `hasRole("ADMIN")`)
- **対象ユーザー**: `ROLE_ADMIN` のみ

## 1. 機能 / 出来ることリスト

- 強制退村 (villagePlayerId 指定)
- 全員アクセス (全参加者の lastAccess 更新)
- 全員自分投票 (未投票者に自己投票を挿入)
- 参加プレイヤー確認 (village_player_id 等の確認)

## 2. 表示要素・UI 状態

- **管理者メニュー** パネル (`admin-form.html:28`、collapse)
- **全員アクセス** フォーム (`/admin/village/{id}/access`)
- **全員自票** フォーム (`/admin/village/{id}/vote`)
- **強制退村** フォーム: `villagePlayerId` テキスト入力 (`admin-form.html:53-60`)
- **参加プレイヤー確認**: `GET /admin/village/{id}/player` への**リンク** (`admin-form.html:67-72`)。現状は別画面 (JSON) を開く

## 3. 呼び出す API エンドポイント (AdminController, `/admin/**` → ROLE_ADMIN)

| メソッド | パス | 用途 |
|---|---|---|
| POST | `/admin/village/{id}/leave` | 強制退村 (villagePlayerId) |
| POST | `/admin/village/{id}/access` | 全員 lastAccess 更新 |
| POST | `/admin/village/{id}/vote` | 未投票者に自己投票挿入 |
| GET | `/admin/village/{id}/player` | 参加プレイヤー一覧 (JSON、現状は別画面) |

## 4. 既存 JS の挙動

- 各操作はフォーム submit 中心
- 参加プレイヤー確認はリンク遷移 (別画面で JSON 表示)

## 5. 権限による分岐 / 6. 認可マスク

- Spring Security `/admin/**` → `hasRole("ADMIN")`
- **要注意**: `AdminController` は **DBFlute Bhv を直接操作** (domain 層を経由しない)。REST 化時に domain/coordinator 経由へ整理するか要検討

## 7. 視覚比較

- 既存 `:8091` (ROLE_ADMIN でログイン)。admin メニュー、各操作

## 8. 関連 e2e ケース候補

- [ ] 管理者: 強制退村、全員アクセス、全員自票
- [ ] 参加プレイヤー確認の表示

## メモ / 移行時の注意

- **参加プレイヤー確認を admin panel 内にインライン表示に変更 (確定・ユーザー指示による UI/UX 変更)**: 現状 `GET /admin/village/{id}/player` は別画面 (JSON) を開くが、移行後は **admin パネル内に参加プレイヤー一覧をインライン表示**する。別画面遷移をなくし、管理メニュー内で完結させる。※ これ以外の admin UI は現状踏襲 ([04-frontend.md](../../04-frontend.md) の UI/UX 現状維持原則)
- 「重要エンドポイント」(admin 操作) は **JWT claim だけでなく DB 再確認** ([03-auth.md](../../03-auth.md) の権限方針) の対象候補
- `AdminController` の Bhv 直接操作は要リファクタ検討 (DDD 維持方針と齟齬)。REST 化時に domain/coordinator 経由へ
