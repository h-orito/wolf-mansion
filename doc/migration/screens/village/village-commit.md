# 画面: 村画面 — コミット (commit)

> 村画面 form-area のコミット (時間前の進行確定) 操作。能力使用は [village-ability.md](village-ability.md)、投票は [village-vote.md](village-vote.md)。

## 概要

- **テンプレート**: `village/commit-form.html`
- **担当 JS**: `village.js`
- **Controller**: `VillageAbilityController.setCommit` (`/village/{id}/commit`)
- **Form**: `VillageCommitForm` (`commit` boolean)
- **situation**: `situation.commit.isAvailableCommit` / 表示可否 `content.form.commit.dispCommitForm`
- **対象ユーザー**: コミット可能な生存参加者 (村設定でコミット有効時)

## 1. 機能 / 出来ることリスト

- コミット (全員が揃ったら時間前でも日付更新を確定) の ON / OFF

## 2. 表示要素・UI 状態

- **コミット欄**: 表示可否は `content.form.commit.dispCommitForm`
- ボタン文言はフォーム値 `*{commit}` で「コミットする / コミットを取り消す」を切替 (`commit-form.html:33`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| POST | `/village/{id}/commit` | コミット ON/OFF (`commit` boolean) | フォーム |

- 投稿は `VillageCoordinator.setCommit`

## 4. 既存 JS の挙動

- ボタン submit で ON/OFF を切替 (現在値はフォーム hidden `commit` で保持)

## 5. 権限による分岐 / 6. 認可マスク

- コミット可否は `situation.commit.isAvailableCommit` (村設定 `availableCommit` 有効 + 生存参加者)
- マスクなし

## 7. 視覚比較

- 既存 `:8091`。コミット ON/OFF のボタン文言切替

## 8. 関連 e2e ケース候補

- [ ] コミット ON → OFF の切替
- [ ] コミット無効村ではフォーム非表示

## メモ / 移行時の注意

- REST 化: `POST /api/v1/villages/{id}/commit` 等へ整理
- コミット成立で日付更新が走る連動は Daychange ([usecases/daychange](../../usecases/daychange.md) step-0.15) と関係
