# 画面: キャラチップ一覧

> 詳細画面は [charachip-detail.md](charachip-detail.md) を参照。

## 概要

- **URL (既存)**: `GET /chara-group`
- **テンプレート**: `chara-list.html`
- **担当 JS**: なし
- **Controller**: `CharaController.charaGroups` (`CharaController.kt:24-30`)
- **対象ユーザー**: 全員 (公開, permitAll)

## 1. 機能 / 出来ることリスト

- キャラチップ (キャラセット) の一覧表示
- 各行から詳細画面 (`/chara-group/{id}`) へ遷移
- キャラチップ製作者向けの案内 (設定ファイル作成補助ツールへのリンク)

## 2. 表示要素・UI 状態

- **一覧テーブル** (`CharaGroupListContent.charaGroupList` = `CharaGroupListCharaGroup` のリスト):
  - ヘッダ: `キャラチップ名` / `作者名` / `キャラチップ数` / `例` (`chara-list.html:19-23`)
  - 各行: キャラチップ名 (詳細へのリンク, `charaGroupName`) / 作者名 (`designerName`) / キャラ数 (`charaNum + '人'`) / ダミーキャラ画像 (`dummyImgUrl` + `dummyImgWidth`/`dummyImgHeight`)
- **空状態**: `charaGroupList` が null または 0 件のときテーブル自体を非表示 (`th:if`, `chara-list.html:16`)
- **静的案内セクション** 「キャラチップ製作者様へ」: Google スプレッドシート (設定ファイル作成補助) への外部リンク (`chara-list.html:41-49`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/chara-group` | キャラチップ一覧 (SSR) | SSR |

### 横断 JSON API (CharaController に同居、本画面では未使用)

以下は `CharaController` 内の `@ResponseBody` JSON API。一覧/詳細 SSR 画面では使わず、**他画面から AJAX で叩かれる**横断 API のため、Controller の主軸である本 md に集約して記録する。

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/getCharacterList?charaGroupId=` | キャラチップのキャラ一覧 (通常表情のみ, JSON)。`charaGroupId` は `List<Int>` (複数指定可, `CharacterListForm.kt:4`)。戻り値は専用 View でなく**ドメイン `Chara` 直シリアライズ** (`CharaController.kt:41-52`) → 公開 JSON 契約が暗黙的 | `new-village.js` (村作成, step-0.3) |
| GET | `/getFaceImgUrl/{villageId}/{faceTypeCode}` | 自分の参加キャラの指定表情画像 URL (JSON) | **村画面の発言フォーム** (表情選択, step-0.8) |

## 4. 既存 JS の挙動

- 一覧画面に固有 JS なし (純 SSR)
- `getCharacterList` / `getFaceImgUrl` は他画面 (村作成 / 村画面) から利用される JSON API

## 5. 権限による分岐

| 権限 | 見え方 |
|---|---|
| 全員 | 一覧は公開 |
| 村参加者 | `getFaceImgUrl` は自分の参加情報が前提 (未参加/匿名は null) |

## 6. 認可マスク

- 一覧画面自体にマスクなし
- `getFaceImgUrl` は本人の参加キャラに限定 (`WolfMansionUserInfoUtil` + 参加者照合, `CharaController.kt:57-58`)。未認証/未参加/別村は null

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/chara-group`

## 8. 関連 e2e ケース候補

- [ ] キャラチップ一覧表示 (テーブル + ダミー画像)
- [ ] 一覧行クリック → 詳細遷移

## メモ / 移行時の注意

- キャラ画像は外部 URL (`wolfort.dev/wmansion` 配下)。React でもそのまま参照
- `getCharacterList` / `getFaceImgUrl` は **村作成 (step-0.3) / 村画面 (step-0.8 発言フォーム)** から参照される横断 API。REST 化時は `GET /api/v1/charachips/{id}/charas`、`GET /api/v1/villages/{id}/me/face-images` 等へ整理
- `getFaceImgUrl` の認可 (本人参加キャラのみ) は維持。CSR で取得 (認証必要)
- 「キャラチップ製作者様へ」の案内テキスト + 外部リンクは静的コンテンツ。React 側でも掲載要否を確認
