# 画面: 村画面 — RP (名前変更 / 簡易メモ / 表情差分)

> 村画面 form-area の参加者個人設定系 (RP)。**通知設定 (Discord) は RP ではなく、設定モーダル ([village-user-settings.md](village-user-settings.md)) に含まれる**。

## 概要

- **テンプレート**: `village/change-name-form.html` (名前変更 + 簡易メモ) / `village/face-type-form.html` (表情差分)
- **担当 JS**: `village.js` (RP 名前/メモ/表情の文字数監視・ファイル名表示)
- **Controller**: `VillageRpController`
- **対象ユーザー**: 参加者本人 (`situation.rp` 由来)

## 1. 機能 / 出来ることリスト

- キャラ名 / 略称の変更 (RP リネーム)
- **簡易メモ**の編集 (参加者一覧に表示される。**他参加者も閲覧可能**)
- 表情差分の **追加** (画像アップロード) / **編集** (名前・表示有無) — **オリジナル画像アップロード村 (原画村) 限定**

## 2. 表示要素・UI 状態

- **名前変更** (`change-name-form`): name + shortName
- **簡易メモ** (`change-name-form` 内): textarea (`situation.rp.isAvailableMemo`)。自分用の非公開メモではなく、**状況サマリの参加者一覧に `[memo]` として表示され、他の参加者からも見える簡易メモ** (`situation.html:149,165`)
- **表情差分** (`face-type-form`、**原画村のみ**):
  - 追加: 表情名 + 画像ファイル
  - 編集: 既存表情の一覧 (code/name/display トグル)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| POST | `/village/{id}/change-name` | キャラ名/略称変更 |
| POST | `/village/{id}/memo` | 簡易メモ更新 |
| POST | `/village/{id}/add-face-type` | 表情差分追加 (画像 multipart、原画村) |
| POST | `/village/{id}/modify-face-type` | 表情差分編集 (名前/表示、原画村) |

- フォーム: `VillageChangeNameForm` (name, shortName) / `VillageMemoForm` (memo) / `VillageFaceTypeForm` (faceTypeName, image) / `VillageFaceTypeModifyForm` (faceTypeList: code/name/display)
- 表情: `CharaService.registerOriginalCharaImage` / `updateOriginalCharaImage` (原画キャラチップのみ)

## 4. 既存 JS の挙動

- 名前/略称変更: 文字数監視 (略称 1 / 名前 40 字, `village.js:888-900`)
- 簡易メモ: 文字数監視 (`village.js:909` 付近)
- 表情差分追加: ファイル選択時のファイル名表示 (`village.js:2067` 付近)
- (参考) 返信 `data-reply-to` / 秘話返信 `data-secret-to` (`village.js:2016-2065`) は発言フォーム (step-0.8 [village-say.md](village-say.md)) 側の機能で RP 本体ではない

## 5. 権限による分岐 / 6. 認可マスク

- `situation.rp` (isAvailableChangeName / isAvailableMemo / canAddImage) で出し分け
- **簡易メモは他参加者も閲覧可能** (参加者一覧に表示、マスクなし)
- 表情差分の追加/編集は **原画村のみ**

## 7. 視覚比較

- 既存 `:8091`。名前変更、簡易メモ、表情差分追加/編集 (原画村)

## 8. 関連 e2e ケース候補

- [ ] 名前/略称変更 → 反映
- [ ] 簡易メモ編集 → 参加者一覧の `[memo]` 表示 (他参加者からも見える)
- [ ] 表情差分追加 (画像) / 編集 (原画村のみ)

## メモ / 移行時の注意

- **簡易メモは公開情報** (参加者一覧表示)。REST レスポンスでも参加者一覧に含める。「自分専用メモ」と誤解しないこと
- 表情差分の画像は multipart アップロード、**原画キャラチップ前提**。REST 化時のアップロード方式は step-0.3/0.9 と統一
- 返信/秘話返信のアンカー挿入は発言フォーム ([village-say.md](village-say.md)) と連動
- 通知設定は [village-user-settings.md](village-user-settings.md) を参照 (RP ではない)
