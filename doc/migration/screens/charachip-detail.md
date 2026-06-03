# 画面: キャラチップ詳細

> 一覧画面・横断 JSON API (`getCharacterList` / `getFaceImgUrl`) は [charachip-list.md](charachip-list.md) を参照。

## 概要

- **URL (既存)**: `GET /chara-group/{charaGroupId}`
- **テンプレート**: `chara.html`
- **担当 JS**: なし
- **Controller**: `CharaController.charaGroup` (`CharaController.kt:32-39`)
- **対象ユーザー**: 全員 (公開, permitAll)

## 1. 機能 / 出来ることリスト

- 1 キャラチップ内の全キャラクター詳細 (画像・名前・略称) の表示
- そのキャラチップでの**部屋割り例**の表示
- 作者情報 (作者名 / 名称変更可否 / 作者様 HP) の表示

## 2. 表示要素・UI 状態

`CharaGroupContent` (`CharaGroupContent.kt:8-23`):

- **見出し**: `キャラチップ: {charaGroupName}` (`chara.html:6,15`)
- **作者情報** (`chara.html:18-21`):
  - `作者: {designerName}様`
  - `肩書・名称変更: 可能 / 不可` (View フィールドは `isAvailableChangeName`、テンプレートは `content.availableChangeName` で参照 = Kotlin の `is`-prefix getter マッピング)
  - `作者様HP` リンク (`descriptionUrl`)。`th:if` で URL 存在時のみ表示 (`chara.html:20-21`)。※ ただし constructor は `descriptionUrl = charachip.descriptionUrl!!` で非 null 前提 (`CharaGroupContent.kt:30`)
- **キャラ一覧** (`charaList` = `CharaGroupChara` のリスト, `chara.html:24-33`): 各キャラを 1 ブロックで表示。キャラ画像 (`charaImgUrlList` を複数 img で並べ `charaImgWidth`/`charaImgHeight`) + `[charaShortName] charaName`
- **部屋割り例テーブル** (`roomAssignedRowList` = `RoomAssignedRow`, `chara.html:35-69` — **詳細画面の主要コンポーネント**): キャラ数から `RoomSize` を算出し部屋を格子配置。各セルに部屋番号 (`roomNumber`, 0 埋め 2 桁) + 略称 (`charaShortName`) + キャラ画像背景 + tooltip (`charaName`)。React 移植で漏らさないこと
- **エラー状態**: 該当 `charaGroupId` が存在しない場合は `WolfMansionBusinessException("charachip not found.")` を throw (`CharaController.kt:34-35`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/chara-group/{id}` | キャラチップ詳細 (SSR) | SSR |

## 4. 既存 JS の挙動

- 詳細画面に固有 JS なし (純 SSR)

## 5. 権限による分岐

| 権限 | 見え方 |
|---|---|
| 全員 | 詳細は公開 |

## 6. 認可マスク

- 詳細画面自体にマスクなし (公開情報)

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/chara-group/{id}`
- 確認ポイント: 作者様 HP リンクの有無 / 部屋割り例テーブルの格子レイアウト / キャラ画像の複数表情

## 8. 関連 e2e ケース候補

- [ ] 詳細表示 (キャラ画像一覧 + 略称/名前)
- [ ] 部屋割り例テーブルの描画
- [ ] 存在しない charaGroupId でエラー

## メモ / 移行時の注意

- キャラ画像は外部 URL (`wolfort.dev/wmansion` 配下)。React でもそのまま参照
- 部屋割り例 (`mapRoomAssign` → `RoomSize` 算出, `CharaGroupContent.kt:62-73`) はサーバ側で部屋格子を構築。React 化時は `RoomAssignedRow`/`RoomAssigned` 相当の DTO を REST で返すか、フロントで `RoomSize` ロジックを再実装するか要設計
- `isAvailableChangeName` は `is`-prefix のため JSON 化時の Jackson シリアライズ名 (`availableChangeName`) に注意 (player-list.md と同様の `is` 削り)
