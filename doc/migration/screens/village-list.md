# 画面: 村一覧

## 概要

- **URL (既存)**: `GET /village-list` (検索フォーム付き)
- **テンプレート**: `src/main/resources/templates/village-list.html`
- **担当 JS**: なし (SSR フォーム submit + Bootstrap collapse)
- **Controller**: `VillageController.villageListIndex(VillageListForm)`
- **公開 API**: `GET /api/village-list` (`VillageApiController`、全村を JSON 返却、フィルタなし)
- **対象ユーザー**: 全員 (公開)

## 1. 機能 / 出来ることリスト

- 全村の一覧表示
- 絞り込み検索: キャラセット / 役職 / 編成 (両方・闇鍋・固定)
- 村名クリックで村画面へ

## 2. 表示要素・UI 状態

- 検索パネル (Bootstrap collapse 「検索」、**初期は閉**: `village-list.html:20` は `collapse` のみ。skill.html は `collapse in` で初期展開と逆):
  - キャラセット (複数選択 `charachipIds`)
  - 役職 (複数選択 `skillCodes`)
  - 編成 (ラジオ: 両方=null / 闇鍋=true / 固定=false、`random`)
  - 検索ボタン (GET submit)
- 村テーブル: 村番号 / 村名(リンク) / 人数 / 状態 (0件時は非表示)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/village-list?charachipIds=&skillCodes=&random=` | 村一覧 (フィルタ付き) | SSR フォーム submit |
| GET | `/api/village-list` | 全村 JSON (公開、フィルタなし) | 外部 (analyzer 等?) |
| (遷移) | `/village/{id}` | 村画面へ | アンカー |

## 4. 既存 JS の挙動

- 専用 JS なし。検索は GET フォーム submit、検索パネルは Bootstrap collapse

## 5. 権限による分岐

| 権限 | 見え方・できること |
|---|---|
| 全員 | 同一 (公開) |

## 6. 認可マスク

- なし

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/village-list`。検索パネル展開、フィルタ適用結果

## 8. 関連 e2e ケース候補

- [ ] 一覧表示: 全村テーブル
- [ ] キャラセット / 役職 / 編成での絞り込み
- [ ] 村名クリック → 村画面

## データ構成 (View: VillageListContent)

- `villageList`: villageNumber, villageName, participateNum, status
- `charachipList` (検索候補), `skillList` (検索候補)
- 取得: `VillageService.findVillages(VillageQuery(charachipIds, skills, isRandomOrg))`

## メモ / 移行時の注意

- ホーム (`home.md`) の「開催中の村」が未終了のみなのに対し、村一覧は **全村 (終了/廃村含む)**
- 検索フィルタ (charachipIds / skillCodes / random) は REST 化時に query param で踏襲 → `GET /api/v1/villages?charachip=&skill=&random=` 等 ([02-backend.md](../02-backend.md) のフィルタ規約)
- 公開 `/api/village-list` の互換維持は step-0.17 で確認
- 編成ラジオの値マッピング (両方=未指定 / 闇鍋=true / 固定=false) を frontend で再現
