# 村画面の状況サマリ (situation) — village-base の一部

> **独立画面ではなく、[village-base.md](village-base.md) (村画面) の一部**。記載量が多いため doc を分けているだけ。村画面 form-area 上部の状況サマリ (部屋割り / 参加者 / 投票 / 足音 タブ) を扱う。本サマリは **`VillageSituation` (村全体・全員共通の状態)** の可視化に対応する (ベースの situation 二層構造を参照)。

## 概要

- **テンプレート**: `village/situation.html` (form-area 内)
- **担当 JS**: `village.js` (タブ開閉・投票表ソート・部屋割り描画)
- **データ**: `VillageContent.situationList` 等 (← `VillageSituation`: `roomAssigned` / `live` / `vote` / `footstep` / `whole`)
- **対象ユーザー**: 全員 (視点により一部フィールドはスポイラーマスク)

## 1. 機能 / 出来ることリスト

- 村の現況を 4 タブで俯瞰 (部屋割り / 参加者 / 投票 / 足音)
- 日別の状況 (突然死・処刑・犠牲・復活・後追い・能力) の確認

## 2. タブ構成

| タブ | 表示条件 | 内容 | 対応 VillageSituation |
|---|---|---|---|
| 部屋割り当て | `roomWidth != null && day > 0` | 部屋グリッド (キャラ画像/生死/死因マーク/役職スポイラー) + 日別状況 (突然死/処刑/犠牲/復活/後追/能力) | `roomAssigned` |
| 参加者 | 常時 (初期 active は day0/部屋なし時) | 生存/死亡などステータス別メンバー一覧 (memo 付) | `live` |
| 投票 | `vote != null` | 投票表 (日付別)。詳細は [village-vote.md](village-vote.md) | `vote` (VillageVoteSituation) |
| 足音 | `villageFootstepList` あり | 日別足音 (スポイラーマスク対応)。詳細は [usecases/footstep](../../usecases/footstep.md) (step-0.14) | `footstep` |

### 死因マーク / スポイラー

- **死因マーク**: 凸 (SUDDON 突然死) / ▼ (EXECUTE 処刑) / ❤︎ (SUICIDE 後追い) / ▲ (その他)。色: 赤=襲撃系、青=処刑/突然死、ピンク=後追い
- **スポイラー**: `data-spoiled-content` / `data-spoiled-alternative-content` で「ネタバレ防止」時に内容を代替表示。列自体の有無は `content.dispSpoilerContent` (`isViewableSpoilerContent`)。視点依存マスクの詳細は [usecases/mask](../../usecases/mask.md) (step-0.16)

## 3. 呼び出す API エンドポイント

- 専用 endpoint なし。村取得 (`GET /village/{id}` / `/day/{day}`) のレスポンスに含まれる ([village-base.md](village-base.md))

## 4. 既存 JS の挙動

- タブ開閉状態は表示設定 Cookie に保存・復元 (display-settings)
- 投票表は日付クリックでソート、セルクリックで色付け
- 部屋割りグリッドはサーバ算出の `roomAssignedRowList` を描画

## 5. 権限による分岐 / 6. 認可マスク

- タブ自体は全員に表示。**部屋割り・足音・死因・役職などは視点依存スポイラーマスク** (`isDispSpoilerContent` + `data-spoiled-*`、[usecases/mask](../../usecases/mask.md) step-0.16)

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/village/{id}`。各 status・各視点 (匿名/参加者/村主) でのタブ表示とスポイラー差分

## 8. 関連 e2e ケース候補

- [ ] 各タブ表示 (部屋割り/参加者/投票/足音)
- [ ] スポイラーマスク: 進行中は役職/足音が代替表示
- [ ] 死因マークの表示

## メモ / 移行時の注意

- 状況サマリは **`VillageSituation` (村全体・全員共通) の可視化**。REST 化時は村取得 API の公開 (誰が取得しても同じ) 部分として返し、スポイラーマスクのみ視点依存で適用する ([village-base.md](village-base.md) の situation 二層構造)
- 部屋割りグリッドの算出 (`roomAssignedRowList` / `RoomSize`) は backend に残す ([charachip-detail.md](../charachip-detail.md) と同じ部屋割りロジック)
- タブ開閉の Cookie は localStorage + Zustand へ
