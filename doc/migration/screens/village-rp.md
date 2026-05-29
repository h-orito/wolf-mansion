# 画面: 村画面 — RP (名前変更 / メモ / 表情差分 / 通知設定)

> 村画面 form-area の参加者個人設定系。

## 概要

- **テンプレート**: `village/change-name-form.html` / `village/face-type-form.html` / 通知設定は `village/display-settings.html` (表示設定モーダル `#modal-dsetting` 内, `:103-159`)
- **担当 JS**: `village.js` (RP/返信 L.2016-2065)
- **Controller**: `VillageRpController` + 通知は `VillageNotificationController`
- **対象ユーザー**: 参加者本人 (situation.rp 由来)

## 1. 機能 / 出来ることリスト

- キャラ名 / 略称の変更 (RP リネーム)
- 簡易メモ (自分用) の編集
- 表情差分の **追加** (画像アップロード) / **編集** (名前・表示有無) — 原画村
- 通知設定 (Discord webhook、村開始/日付更新/エピローグ、秘話/アンカー/役職窓 (abilitySay, UI ラベルは「役職窓通知」)/キーワード通知)

## 2. 表示要素・UI 状態

- **名前変更** (`change-name-form`): name + shortName
- **メモ**: 自分用メモ textarea (situation.rp.isAvailableMemo)
- **表情差分** (`face-type-form`):
  - 追加: 表情名 + 画像ファイル
  - 編集: 既存表情の一覧 (code/name/display トグル)
- **通知設定**: webhookUrl + 各種通知 ON/OFF + 通知キーワード

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| POST | `/village/{id}/change-name` | キャラ名/略称変更 |
| POST | `/village/{id}/memo` | 簡易メモ更新 |
| POST | `/village/{id}/add-face-type` | 表情差分追加 (画像 multipart) |
| POST | `/village/{id}/modify-face-type` | 表情差分編集 (名前/表示) |
| POST | `/village/{id}/notification-setting` | 通知設定更新 |

- フォーム: `VillageChangeNameForm` (name, shortName) / `VillageMemoForm` (memo) / `VillageFaceTypeForm` (faceTypeName, image) / `VillageFaceTypeModifyForm` (faceTypeList: code/name/display) / `VillageNotificationForm` (webhookUrl, villageStart/Daychange/Epilogue, secretSay, anchorSay, abilitySay, keyword)
- 表情: `CharaService.registerOriginalCharaImage` / `updateOriginalCharaImage` (原画キャラチップのみ)

## 4. 既存 JS の挙動

- 名前/略称変更: 文字数監視 (略称 1 / 名前 40 字, `village.js:888-900`)
- メモ: 文字数監視 (`village.js:909` 付近)
- 表情差分追加: ファイル選択時のファイル名表示 (`village.js:2067` 付近)
- 通知設定保存時、`notificationService.notifyTest` で webhook にテスト通知が飛ぶ (`VillageNotificationController.kt:68`)
- (参考) 返信 `data-reply-to` / 秘話返信 `data-secret-to` (`village.js:2016-2065`) は発言フォーム (step-0.8) 側の機能で RP 本体ではない

## 5. 権限による分岐 / 6. 認可マスク

- `situation.rp` (isAvailableChangeName / isAvailableMemo / canAddImage) で出し分け
- メモは本人のみ可視。表情差分追加は原画村のみ

## 7. 視覚比較

- 既存 `:8091`。名前変更、メモ、表情差分追加/編集、通知設定

## 8. 関連 e2e ケース候補

- [ ] 名前/略称変更 → 反映
- [ ] メモ編集 → 参加者一覧の memo 表示
- [ ] 表情差分追加 (画像) / 編集 (原画村)
- [ ] 通知設定の保存

## メモ / 移行時の注意

- 表情差分の画像は multipart アップロード。原画キャラチップ前提。REST 化時のアップロード方式は step-0.3/0.9 と統一
- 通知 (Discord webhook) は `infrastructure/discord/` 連携。移行後も維持
- メモは参加者一覧 (situation 参加者タブ、step-0.13) に `[memo]` で表示される
- 返信/秘話返信のアンカー挿入は発言フォーム (step-0.8) と連動
