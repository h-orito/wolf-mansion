# 画面: キャラチップ一覧 / 詳細

## 概要

- **URL (既存)**: `GET /chara-group` (一覧) / `GET /chara-group/{charaGroupId}` (詳細)
- **テンプレート**: `chara-list.html` (一覧) / `chara.html` (詳細)
- **担当 JS**: なし
- **Controller**: `CharaController`
- **対象ユーザー**: 全員 (公開)

## 1. 機能 / 出来ることリスト

- キャラチップ (キャラセット) の一覧表示
- 各キャラチップ内のキャラクター詳細 (画像・名前等) の表示

## 2. 表示要素・UI 状態

- 一覧: キャラチップ名 + デザイナー名、各詳細へのリンク (`CharaGroupListContent`)
- 詳細 (`CharaGroupContent`): 作者名 (`designerName`) / **肩書・名称変更可否** (`availableChangeName`, `chara.html:19`) / **作者様 HP リンク** (`descriptionUrl`, `chara.html:20-21`) / キャラチップ内の全キャラの画像・名前 (`charaList`) / **部屋割り例テーブル** (`roomAssignedRowList`, `chara.html:36-48` — 詳細画面の主要コンポーネント。React 移植で漏らさないこと)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/chara-group` | キャラチップ一覧 | SSR |
| GET | `/chara-group/{id}` | キャラチップ詳細 | SSR |
| GET | `/getCharacterList?charaGroupId=` | キャラチップのキャラ一覧 (通常表情のみ, JSON)。`charaGroupId` は `List<Int>` (複数指定可, `CharacterListForm.kt:4`)。戻り値は専用 View でなく**ドメイン `Chara` 直シリアライズ** (`CharaController.kt:41-52`) → 契約が暗黙的 | `new-village.js` (村作成) |
| GET | `/getFaceImgUrl/{villageId}/{faceTypeCode}` | 自分の参加キャラの指定表情画像 URL (JSON) | **村画面の発言フォーム** (表情選択) |

## 4. 既存 JS の挙動

- 一覧/詳細画面に固有 JS なし
- `getCharacterList` / `getFaceImgUrl` は他画面 (村作成 / 村画面) から利用される JSON API

## 5. 権限による分岐

| 権限 | 見え方 |
|---|---|
| 全員 | 一覧/詳細は公開 |
| 村参加者 | `getFaceImgUrl` は自分の参加情報が前提 (未参加/匿名は null) |

## 6. 認可マスク

- `getFaceImgUrl` は本人の参加キャラに限定 (`WolfMansionUserInfoUtil` + 参加者照合)

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/chara-group`、`/chara-group/{id}`

## 8. 関連 e2e ケース候補

- [ ] キャラチップ一覧表示
- [ ] 詳細表示 (キャラ画像一覧)

## メモ / 移行時の注意

- キャラ画像は外部 URL (`wolfort.dev/wmansion` 配下)。React でもそのまま参照
- `getCharacterList` / `getFaceImgUrl` は **村作成 (step-0.3) / 村画面 (step-0.8 発言フォーム)** から参照される横断 API。REST 化時は `GET /api/v1/charachips/{id}/charas`、`GET /api/v1/villages/{id}/me/face-images` 等へ整理
- `getFaceImgUrl` の認可 (本人参加キャラのみ) は維持。CSR で取得 (認証必要)
