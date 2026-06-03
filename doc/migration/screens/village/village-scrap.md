# 画面: 村切り抜き (scrap) — 別画面

> **村画面とは別の独立画面**。村ログから特定の発言を抽出・整形して共有/保存するための切り抜きページ。村画面は [village-base.md](village-base.md)、村情報モーダルは [village-info.md](village-info.md)。
>
> **設計参考: scrap 機能は firewolf (<https://github.com/h-orito/firewolf>) の実装が参考になる**。

## 概要

- **URL (既存)**: `GET /village/{villageId}/scrap` (別タブで開く)
- **テンプレート**: `scrap.html`
- **担当 JS**: `village-message.js` (`scrap.html:8` で `src` は village.js だが `th:src` は village-message.js → Thymeleaf 解決後は village-message.js が効く)。scrap 固有処理 (`#scrap-form` submit / `[data-scrap-remove-btn]`) は `village-message.js:405-410`。village.js に scrap 処理は無い
- **Controller**: `VillageController.scrap` (`VillageController.kt:103-120`)
- **対象ユーザー**: 全員 (発言可視性は通常のメッセージマスクに従う)

## 1. 機能 / 出来ることリスト

- 村ログから発言をフィルタ抽出して別ページに表示 (共有/保存用)
- 不要な発言を取り除く (`[data-scrap-remove-btn]`) など切り抜き編集

## 2. 表示要素・UI 状態

- 村画面と同じメッセージ表示基盤を使い、フィルタした発言を表示
- `#scrap-form` で抽出条件を指定、`[data-scrap-remove-btn]` で個別発言を除去
- Controller は `setIndexModel` でいったん画面を返し、**メッセージは後から API (`getMessageList`) で取得**する (`VillageController.kt:114` のコメント)
- R18 村は `noAd=true` (広告非表示, `VillageController.kt:116-118`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| GET | `/village/{id}/scrap` | 切り抜きページ (SSR、メッセージは後から `getMessageList` で取得) |
| GET | `/village/getMessageList` | 抽出した発言の取得 ([village-messages.md](village-messages.md)) |

## 4. 既存 JS の挙動

- `village-message.js` で村画面のメッセージ取得ロジックを再利用 (フィルタ付き)
- **`isSettled` チェックは現状コメントアウト** (`VillageController.kt:111-113`) → 進行中の村でも切り抜きを開ける
- `#scrap-form` submit / `[data-scrap-remove-btn]` (`village-message.js:405-410`)

## 5. 権限による分岐 / 6. 認可マスク

- 切り抜きの発言可視性は通常のメッセージマスク ([usecases/mask](../../usecases/mask.md) step-0.16) に従う

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/village/{id}/scrap`。フィルタ付き発言表示、切り抜き編集

## 8. 関連 e2e ケース候補

- [ ] 切り抜きページ (フィルタ付き発言表示)
- [ ] 発言の個別除去

## メモ / 移行時の注意

- **scrap は村画面と独立した別ルート**。React Router の別ルート (`/villages/{id}/scrap`) として実装、フィルタは searchParams
- メッセージ表示基盤は村メッセージ ([village-messages.md](village-messages.md), step-0.7) と共通化
- **firewolf の scrap 実装を参考にする** (切り抜き UI / 共有フローの設計)
- 進行中でも開ける現状仕様 (isSettled コメントアウト) を維持するか、移行時に要判断
