# 画面: 村画面 — 能力使用 / 投票 / コミット

> 村画面 form-area のゲーム中核操作。役職能力・投票・コミット。

## 概要

- **テンプレート**: `village/skill-area.html` (381行, 役職別能力 UI) / `skill-selecting.html` / `skill-description.html` / `vote-form.html` / `commit-form.html`
- **担当 JS**: `village.js` (能力/足音 L.665-1003, 投票表示 L.1445-1508)
- **Controller**: `VillageAbilityController`
- **対象ユーザー**: 生存参加者 (situation.ability / vote / commit 由来)

## 1. 機能 / 出来ることリスト

- 役職能力のセット (占い / 襲撃 / 護衛 / 各種)。襲撃は **足音** (どの部屋を通るか) も指定
- 投票先のセット
- コミット (時間前の進行確定) の ON/OFF

## 2. 表示要素・UI 状態

- **能力欄 (skill-area)**: 役職に応じた能力 UI
  - 対象選択 (targetCharaId)。襲撃者選択 (attackerCharaId、複数人狼時)
  - **足音選択**: 襲撃対象を選ぶと `getFootstepList` で候補取得、足音 (経路の部屋) を選択。狐/狂人の徘徊は部屋選択テーブル
  - `targetPrefixMessage` / `targetSuffixMessage` で「○○に△△する」等の説明表示 (`skill-area.html:66-74`。situation には `targetingMessage` もあるがテンプレ上は prefix/suffix を使用)
  - 役職説明 (skill-description) / 役職選択 (skill-selecting)
  - **村開始時の初回役職確認モーダル** (`#modal-initial-skill-description`, `skill-area.html:236-377`): 自役職 + 村設定を初回表示
  - **能力セット履歴** (`skillHistoryList`, `skill-area.html:226-230`)
  - **陣営別メンバー名リスト**: 狂信者 / 人狼 / 背徳者 / 恋人 / 共鳴・共有 向けの仲間表示 (`skill-area.html:186-225`)
- **投票欄 (vote-form)**: 投票先 select。未投票警告 (footer-menu からも誘導)。突然死注意
- **コミット欄 (commit-form)**: 表示可否は `content.form.commit.dispCommitForm`。ボタン文言はフォーム値 `*{commit}` で「コミットする / コミットを取り消す」を切替 (`commit-form.html:33`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| POST | `/village/{id}/setAbility` | 能力セット (attacker/target/footstep) | フォーム |
| POST | `/village/{id}/setVote` | 投票セット (targetCharaId) | フォーム |
| POST | `/village/{id}/commit` | コミット (boolean) | フォーム |
| GET | `/village/getAttackTargetList?villageId=&charaId=` | 襲撃対象候補 | village.js (襲撃者選択時) |
| GET | `/village/getFootstepList?villageId=&charaId=&targetCharaId=` | 足音候補 | village.js (対象選択後) |

- フォーム: `VillageAbilityForm` (attackerCharaId, targetCharaId, footstep) / `VillageVoteForm` (targetCharaId) / `VillageCommitForm` (commit)
- 投稿は `VillageCoordinator.setAbility / setVote / setCommit`、候補は `getAttackableTargets / getSelectableFootstepList`

## 4. 既存 JS の挙動

- 襲撃者選択 → `getAttackTargetList` → 対象 select 更新 → `getFootstepList` → 足音候補更新
- 狐/狂人: 部屋選択テーブル (`data-footstep-select-table`) クリックで部屋番号トグル、CSV を hidden に保存 (`data-footstep-hd-input`)
- 初期化時 `selectDefaultFootsteps` で既存選択を復元
- 投票テーブルは日付クリックでソート、セルクリックで色付け

## 5. 権限による分岐 / 6. 認可マスク

- 能力欄は `situation.ability.canUseAbility` + 役職の能力種別 (`type`) / 妨害能力で出し分け (`setAbilityFormIfNeeded`)
- 投票は `situation.vote.canVote`、コミットは `situation.commit.isAvailableCommit`
- 能力・足音の見え方 (誰の能力結果が見えるか) は spoiler マスク (step-0.16) と足音 reveal (step-0.14) に連動

## 7. 視覚比較

- 既存 `:8091`。各役職の能力 UI (占い/襲撃+足音/護衛)、投票、コミット

## 8. 関連 e2e ケース候補

- [ ] 占い: 対象選択 → セット
- [ ] 襲撃: 襲撃者 + 対象 + 足音選択 → セット
- [ ] 狐/狂人: 部屋選択
- [ ] 投票セット
- [ ] コミット ON/OFF

## メモ / 移行時の注意

- **役職ごとの能力 UI** は `domain/service/ability/` の 60+ 能力サービスに対応。能力種別ごとの入力 (単一対象 / 攻撃者+対象+足音 / 部屋選択) を situation から駆動する設計に
- **足音 (footstep)** の選択・reveal は wolf-mansion 固有の複雑機能 → [usecases/footstep](../usecases/README.md) (step-0.14) で詳細調査
- 能力結果メッセージ (PRIVATE_SEER 等) の可視性は step-0.16
- 投票・能力候補 API は `GET /api/v1/villages/{id}/ability/targets` 等へ整理
