# 画面: 村画面 — debug 操作 (ローカル開発向け)

> 村画面 form-area のデバッグメニュー。**ローカル開発向け機能** (`app.debug:true` のときのみ表示、本番は無効)。村主 (creator) は [village-creator.md](village-creator.md)、管理者 (admin) は [village-admin.md](village-admin.md)。

## 概要

- **テンプレート**: `village/debug-form.html` (128行)
- **Controller**: `DebugController` (一括参戦・日付進め) + Spring Security の `/login` `/logout` (ダミーログイン)
- **対象ユーザー**: `isDebugMode` (`app.debug:true`) 時のみ。**本番では非表示・無効**
- **用途**: e2e / ローカル動作確認の基盤 (任意プレイヤーへのなりすまし + 日付進行で各視点・各 status を再現)

## 1. 機能 / 出来ることリスト

1. **テストアカウント一括参戦** (プロローグ中のみ)
2. **日付を進める** (Daychange を即時実行)
3. **ダミーログイン** (任意プレイヤーになりすましてログイン)
4. **ログアウト**

## 2. 表示要素・UI 状態 (`debug-form.html`、`isDebugMode` 時のみ)

- **入村させる** (`debug-form.html:32-44`): `content.day == 0` (プロローグ) のときのみ。人数入力 (`personNumber`、既定 16) → 「人数分入村させる」で一括参戦
- **日付を進める** (`:45-55`): 「日付を進める」ボタンで Daychange を即時実行
- **ダミーログイン** (`:56-72`): `dummyLoginPlayerList` のプレイヤーを select で選び「ログイン」。`POST /login` を **password 固定 `testuser`** で送信し、ajax 成功後 `location.reload()` (`:105-120`)。= 任意プレイヤーへのなりすまし
- **ログアウト** (`:73-82`): 「ログアウト」ボタン → ajax `POST /logout` → reload (`:94-104`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 備考 |
|---|---|---|---|
| POST | `/village/{id}/allparticipate` | テストアカウント一括参戦 (人数、ランダム希望役職) | `DebugController`、プロローグのみ |
| POST | `/village/{id}/dayChange` | 時間を進める (最新日 daychangeDatetime を now-1秒 → `changeDayIfNeeded`) | `DebugController` |
| POST | `/login` | ダミーログイン (`userId` = 選択プレイヤー、`password=testuser` 固定) | Spring Security formLogin |
| POST | `/logout` | ログアウト | Spring Security |

## 4. 既存 JS の挙動 (`debug-form.html` 内 inline script)

- `[data-dummy-login]`: select の値を `userId` に、`password=testuser` で `POST /login` (ajax) → reload。あわせて hidden `#login-form` も submit
- `[data-logout]`: `POST /logout` (ajax、`_csrf` 付与) → reload
- 一括参戦・日付進めは通常のフォーム submit

## 5. 権限による分岐 / 6. 認可マスク

- `app.debug` プロパティが true のときのみメニュー表示 (`isDebugMode`)。**本番 (`app.debug:false`) では描画されない**
- ダミーログインは固定パスワード `testuser` 前提 (ローカル DB のテストアカウント。[ローカルテスト資格情報] と同じ)

## 7. 視覚比較

- 既存 `:8091` (ローカル `app.debug:true`)。デバッグメニュー (入村/日付進め/ダミーログイン/ログアウト)

## 8. 関連 e2e ケース候補

- [ ] debug: 一括参戦 → dayChange で進行 (e2e の基盤、step-0.15 [usecases/daychange](../../usecases/daychange.md))
- [ ] ダミーログインで任意プレイヤー視点に切替 → 各視点の表示確認

## メモ / 移行時の注意

- **debug dayChange は REST 化後も同等 endpoint を残す** (e2e 必須、[05-e2e.md](../../05-e2e.md))。Daychange の現行 endpoint 駆動は [usecases/daychange](../../usecases/daychange.md) (step-0.15)
- ダミーログイン (`password=testuser` 固定) は **ローカル開発・e2e 専用**。本番では `app.debug:false` で無効。移行後も同等のデバッグ手段 (なりすまし + 日付進め) を開発環境限定で用意する
- 一括参戦のテストアカウントはローカル DB のダミープレイヤー前提
