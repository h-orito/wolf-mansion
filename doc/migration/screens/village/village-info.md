# 画面: 村画面 — 村情報モーダル

> 村画面の付随機能。村の全設定を読み取り表示するモーダル。村画面ベースは [village-base.md](village-base.md)。切り抜き (別画面) は [village-scrap.md](village-scrap.md)。

## 概要

- **テンプレート**: `village/modal-village-info.html`
- **担当 JS**: Bootstrap modal (固有 JS なし)
- **Controller**: 専用なし (村取得 `GET /village/{id}` のレスポンス `content.settings` に同梱)
- **対象ユーザー**: 全員 (公開設定情報)

## 1. 機能 / 出来ることリスト

- 村の全設定を読み取り表示
- (村主のみ) モーダル内から設定変更画面への導線

## 2. 表示要素・UI 状態

- **村情報モーダル** (`#village-info`): 設定テーブル — 募集範囲 / 最少開始人数 / 定員 / 開始日時 / 更新間隔 / 投票形式 / 役職希望 / 見学入村 / プロデューサー機能 / 連続襲撃 / 連続護衛 / 転生候補 / 墓下役職公開 / 墓下地上会話 / 秘話 / 突然死 / コミット / キャラセット(リンク) / ダミーキャラ / 入村パスワード有無 / 村建てプレイヤー 等
  - 日付リスト・footer-menu の「情報」から開く
  - モーダル内に **村主 (creator) 限定の「設定変更」導線** あり (`content.availableSettingsUpdate` で活性、`/village/{id}/settings` へ遷移, `modal-village-info.html:327-333`) → [village-settings.md](village-settings.md)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| (モーダル) | - | 村情報は村取得 (`GET /village/{id}`) の `content.settings` に同梱 (追加 API なし) |

## 4. 既存 JS の挙動

- Bootstrap modal (固有 JS なし)

## 5. 権限による分岐 / 6. 認可マスク

- 村情報は公開設定。村主のみ「設定変更」導線が活性

## 7. 視覚比較

- 既存 `:8091`。村情報モーダル (設定値)

## 8. 関連 e2e ケース候補

- [ ] 村情報モーダル表示 (設定値)
- [ ] 村主のみ設定変更導線が出る

## メモ / 移行時の注意

- 村情報は村取得 API (`GET /api/v1/villages/{id}`) のレスポンスに設定が含まれるので追加 API 不要。React ではモーダルコンポーネント
- 設定変更導線は [village-settings.md](village-settings.md) へ
