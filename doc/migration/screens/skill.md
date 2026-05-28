# 画面: 役職一覧

## 概要

- **URL (既存)**: `GET /skill`
- **テンプレート**: `src/main/resources/templates/skill.html`
- **担当 JS**: `skill.js`
- **Controller**: `SkillController` (`GET /skill`, `GET /skill-list`)
- **公開 API**: `GET /skill/list` (`IndexController`、陣営別役職名 JSON)
- **対象ユーザー**: 全員 (公開)

## 1. 機能 / 出来ることリスト

- 全役職の説明一覧表示
- **タグ / 役職名 / 村** での絞り込み (該当役職のみ表示)

## 2. 表示要素・UI 状態

- 役職メニュー (`#menu` のアンカー) + 役職説明リスト (`#skill li ul li`、各 `id` = 役職コード)
- 検索 UI: タグラベル (クリックで選択 label-success/default 切替)、役職名入力 (`#skill-name`)、村選択 (`#villageId`)、検索ボタン
- 検索結果に該当しない役職を `hidden` で非表示化

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/skill` | 役職一覧ページ (SkillContent + 非闇鍋村リスト) | SSR |
| GET | `/skill-list?tags=&name=&villageId=` | 条件に合う役職コード配列 (JSON) | `skill.js` 検索 |
| GET | `/skill/list` | 陣営別役職名一覧 (公開 API) | 外部 |

## 4. 既存 JS の挙動 (`skill.js`)

- `[data-tag]` クリック → タグ選択トグル (label-success ⇔ label-default)
- `[data-search]` クリック → 選択タグ + 役職名 + villageId で `GET /skill-list` → 返ってきた役職コードに一致する `#menu` アンカーと役職説明だけ表示、他は `hidden`

## 5. 権限による分岐

| 権限 | 見え方 |
|---|---|
| 全員 | 同一 (公開) |

## 6. 認可マスク

- なし

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/skill`。タグ絞り込み・村絞り込みの挙動

## 8. 関連 e2e ケース候補

- [ ] 一覧表示
- [ ] タグ絞り込み → 該当役職のみ表示
- [ ] 村指定絞り込み (その村の構成役職のみ)

## ロジックメモ (SkillController)

- `/skill-list` は tags (`SkillTag`) / name 部分一致 / villageId で絞る
- villageId 指定時: 闇鍋村は全役職、プロローグ/廃村は希望可能役職、進行中は固定編成の人数行から役職を抽出して絞る (`filterByVillageSkill`)

## メモ / 移行時の注意

- 役職説明データ (`SkillContent`) は静的に近い (役職定義 `Skills`)。React では役職マスタ API + クライアントフィルタに置換
- 公開 `/skill/list` は互換維持 (step-0.17)
- 村連動フィルタ (その村で出る役職のみ) のロジックは backend に残す価値あり
