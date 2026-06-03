# 画面: 村画面 — 投票 (vote)

> 村画面 form-area の投票操作。能力使用は [village-ability.md](village-ability.md)、コミットは [village-commit.md](village-commit.md)。投票先のマスクは [usecases/mask](../../usecases/mask.md) (step-0.16)。

## 概要

- **テンプレート**: `village/vote-form.html`
- **担当 JS**: `village.js` (投票フォームは最小)。※ 投票結果の表 (日付別) とそのソート/色付けは vote-form ではなく **状況サマリの投票タブ** ([village-situation-summary.md](village-situation-summary.md)) 側 (`village.js` L.1445-1508)
- **Controller**: `VillageAbilityController.setVote` (`/village/{id}/setVote`)
- **Form**: `VillageVoteForm` (`targetCharaId`)
- **situation**: `situation.vote.canVote`
- **対象ユーザー**: 投票可能な生存参加者

## 1. 機能 / 出来ることリスト

- 当日の投票先 (処刑対象) をセット

> 過去日の投票結果の閲覧 (日付別テーブル) は vote-form には無く、**状況サマリの投票タブ** ([village-situation-summary.md](village-situation-summary.md)) の機能。

## 2. 表示要素・UI 状態

- **投票パネル** (`vote-form.html`): `voteTargetList` が非空のとき表示。未セット時はヘッダが赤背景 + 「未セットのままだと突然死します」
- **現在の投票先** 表示 + **投票先 select** (`targetCharaId`) + セットボタン
- 投票可否は `content.form.vote` / `situation.vote.canVote` で出し分け

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| POST | `/village/{id}/setVote` | 投票セット (`targetCharaId`) | フォーム |

- 投稿は `VillageCoordinator.setVote`

## 4. 既存 JS の挙動

- 投票フォームは select + submit のみ (固有の動的処理はほぼ無し)
- 投票結果表のソート/色付け (`village.js` L.1445-1508) は状況サマリの投票タブ側 ([village-situation-summary.md](village-situation-summary.md))

## 5. 権限による分岐 / 6. 認可マスク

- 投票可否は `situation.vote.canVote`
- **投票先のマスク**: 当日票は除外 (`filterPastDay`)、黒箱能力 (`getHideDays`) で特定日票を非表示。詳細と「黒箱以外を進行中に追加で隠すか」の論点は [usecases/mask](../../usecases/mask.md) (step-0.16) を参照

## 7. 視覚比較

- 既存 `:8091`。投票 select、未投票時の警告 (赤背景 + 突然死注意)

## 8. 関連 e2e ケース候補

- [ ] 投票セット → 反映
- [ ] 未投票警告の表示 (赤背景 + 突然死注意)

## メモ / 移行時の注意

- REST 化: `POST /api/v1/villages/{id}/vote` 等へ整理
- 投票先の表示マスク (当日除外・黒箱日非表示) は backend 側ロジック。移行後も維持 ([usecases/mask](../../usecases/mask.md))
