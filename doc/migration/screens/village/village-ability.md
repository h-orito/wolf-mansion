# 画面: 村画面 — 能力使用 (ability)

> 村画面 form-area の役職能力セット。**役職は 133 と非常に多い** (`CDef.Skill`)。能力ロジックは `domain/service/ability/` に 67 サービスクラスで実装され、複数役職が能力パターンを共有する。役職数が多いため入力パターン別に整理する。投票は [village-vote.md](village-vote.md)、コミットは [village-commit.md](village-commit.md)。足音の reveal は [usecases/footstep](../../usecases/footstep.md) (step-0.14)、能力結果メッセージの可視性は [usecases/mask](../../usecases/mask.md) (step-0.16)。

## 概要

- **テンプレート**: `village/skill-area.html` (381行) / `skill-selecting.html` (役職選択) / `skill-description.html` (役職説明)
- **担当 JS**: `village.js` (能力/足音 L.665-1003)
- **Controller**: `VillageAbilityController.setAbility` (`/village/{id}/setAbility`) + 候補取得 `getAttackTargetList` / `getFootstepList`
- **Form**: `VillageAbilityForm` (`attackerCharaId`, `targetCharaId`, `footstep`)
- **situation**: `ParticipantAbilitySituation` (`domain/model/situation/participant/ParticipantAbilitySituation.kt`) が UI capability を駆動
- **対象ユーザー**: 生存参加者 (`situation.ability.canUseAbility`)

## 1. 機能 / 出来ることリスト

- 自役職の能力を当日分セット (対象 / 襲撃者 / 足音 / 徘徊先 等、役職により入力が異なる)
- 当日の能力セット履歴 (`skillHistoryList`) の確認
- 自陣営の仲間名リスト確認 (人狼/狂信者/妖狐/恋人/共鳴・共有)
- 村開始時の初回役職確認モーダル

## 2. 入力パターン分類 (役職ごとの能力 UI)

UI は `ParticipantAbilitySituation` のフラグと役職 capability (`Skill.hasXxxAbilitySkills` / `SkillTag`) で出し分ける。`skill-area.html` の描画ブロックは以下:

| # | パターン | 入力 UI | 判定 (situation / skill capability) | 代表役職 |
|---|---|---|---|---|
| A | **対象選択型** | 対象 select (`targetCharaId`) + prefix/suffix 文言 | `abilityTargetList.size() > 0` かつ 襲撃でない (`skill-area.html:60-76`) | 占い師 / 狩人(護衛) / 多くの能動役職 |
| B | **対象なし許容型** | 対象 select (「対象なし」可) | A + `isAvailableNoTarget` | 対象任意の能力 |
| C | **襲撃型 (襲撃者 × 対象 × 足音)** | **襲撃者 select (`attackerCharaId`)** + 対象 select + 足音 | `attackerList.size() > 0` (`skill-area.html:99-131`) | 人狼 (`hasAttackAbility`) |
| D | **単独襲撃型** | 襲撃系 UI (単独で襲撃) | `SkillTag.単独襲撃` (`Skill.hasLoneAttackAbilitySkills`) | 一匹狼系 |
| E | **対象＋足音指定型** | 対象 select + **通過する部屋 select** (`footstep`) | `targetingAndFootstep` (`skill-area.html:84,129`、`SkillTag.対象指定_足音発生`) | 対象指定して足音を発生させる役職 |
| F | **調査型 (足音を調査)** | **足音 select** (`footstep`)「を調査する」 | `myself.skill.hasInvestigateAbility` (`skill-area.html:77-81`)。候補が無い日は「調査対象の足音がないため本日はセット不可」(`:143`) | 探偵 (調査) |
| G | **徘徊型 (部屋選択)** | **部屋選択テーブル** (`data-footstep-select-table`) で通過部屋を複数選択 → CSV を hidden `footstep` に保存。徘徊しない場合は未選択でセット | `hasDisturbAbility` かつ `abilityTargetList.size() == 0` (`skill-area.html:148-174`、`SkillTag.徘徊`) | 妖狐 / 狂人 (徘徊) |
| H | **入力なし / 自動発動型** | 入力 UI なし (フォーム非表示) | `canUseAbility` false もしくは入力不要の自動能力 | 自動発動・受動系 |

> ⚠️ **襲撃者選択 (`attackerCharaId`) は人狼が 1 人でも表示される**。`attackerList` が非空なら襲撃ブロックを描画する (`skill-area.html:99`) ため、単独人狼でも襲撃者欄が出る (誰が襲撃＝足音を担当するかを指定)。複数人狼時は候補が複数になるだけ。**「複数人狼時のみ表示」ではない**。

### 足音 (footstep) の絡む入力

- **襲撃 (C)**: 襲撃者 → `getAttackTargetList` で対象候補 → 対象選択後 `getFootstepList` で足音 (経路の部屋) 候補を取得し選択
- **対象＋足音指定 (E)** / **調査 (F)** / **徘徊 (G)** はそれぞれ別の足音入力 UI (上表)
- 足音の選択肢・reveal ロジックは wolf-mansion 固有 → [usecases/footstep](../../usecases/footstep.md) (step-0.14)

### その他の表示要素 (skill-area)

- **初回役職確認モーダル** (`#modal-initial-skill-description`, `skill-area.html:236-377`): 村開始時に自役職 + 村設定を初回表示
- **能力セット履歴** (`skillHistoryList`, `:226-230`)
- **陣営別メンバー名リスト** (`:186-225`): 人狼 (`werewolfCharaNameList`) / 狂信者 (`cMadmanCharaNameList`) / 妖狐 (`foxCharaNameList`) / 恋人 (`loversCharaNameList`) / 共鳴・共有 (`masonsCharaNameList` / `listenMasonsCharaNameList`)
- 対象説明はテンプレ上 `targetPrefixMessage` / `targetSuffixMessage` (`skill-area.html:66-74`。situation には `targetingMessage` もあるがテンプレは prefix/suffix を使用)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| POST | `/village/{id}/setAbility` | 能力セット (`attackerCharaId` / `targetCharaId` / `footstep`) | フォーム |
| GET | `/village/getAttackTargetList?villageId=&charaId=` | 襲撃者を選ぶと、その襲撃者の襲撃対象候補を取得 | `village.js` (襲撃者選択時) |
| GET | `/village/getFootstepList?villageId=&charaId=&targetCharaId=` | 対象選択後の足音 (経路の部屋) 候補を取得 | `village.js` (対象選択後) |

- 投稿は `VillageCoordinator.setAbility`、候補は `getAttackableTargets` / `getSelectableFootstepList`

## 4. 既存 JS の挙動 (`village.js` L.665-1003)

- 襲撃者選択 → `getAttackTargetList` → 対象 select 更新 → `getFootstepList` → 足音候補更新
- 徘徊 (G): 部屋選択テーブル (`data-footstep-select-table`) クリックで部屋番号トグル、CSV を hidden (`data-footstep-hd-input`) に保存
- 初期化時 `selectDefaultFootsteps` で既存選択を復元

## 5. 権限による分岐 / 6. 認可マスク

- 能力欄は `situation.ability.canUseAbility` + 役職の能力種別 (`type`) / 妨害能力で出し分け (`setAbilityFormIfNeeded`)
- 能力・足音の見え方 (誰の能力結果が見えるか) は spoiler マスク ([usecases/mask](../../usecases/mask.md) step-0.16) と足音 reveal ([usecases/footstep](../../usecases/footstep.md) step-0.14) に連動

## 7. 視覚比較

- 既存 `:8091`。各パターンの能力 UI: 占い (A) / 襲撃+足音 (C) / 探偵調査 (F) / 妖狐・狂人徘徊 (G)

## 8. 関連 e2e ケース候補

- [ ] 対象選択型 (占い): 対象選択 → セット
- [ ] 襲撃型: 襲撃者 (単独でも欄あり) + 対象 + 足音選択 → セット
- [ ] 徘徊型 (妖狐/狂人): 部屋選択テーブルで通過部屋セット / 未選択でセット (徘徊しない)
- [ ] 調査型 (探偵): 足音選択 → セット / 候補なし日はセット不可表示
- [ ] 初回役職確認モーダル表示

## メモ / 移行時の注意

- **役職ごとの能力 UI** は `domain/service/ability/` の 67 能力サービスに対応 (役職自体は 133)。React 化時は上表 A〜H の**入力パターン別コンポーネント**を用意し、`ParticipantAbilitySituation` のフラグ (`attackerList` / `abilityTargetList` / `targetingAndFootstep` / `hasInvestigateAbility` / `hasDisturbAbility` / `isAvailableNoTarget`) で出し分ける設計にする
- 役職→パターンの対応は `SkillTag` (`domain/model/skill/SkillTag.kt`) と `Skill.hasXxxAbilitySkills` の述語から導出可能 (例: `徘徊`=`hasDisturbAbility`、`単独襲撃`=`hasLoneAttackAbilitySkills`、`対象指定_足音発生`=`targetingAndFootstepSkills`)。実装時にこの述語で全 133 役職をパターンに機械分類すること
- **足音 (footstep)** の候補生成・reveal は固有の複雑機能 → [usecases/footstep](../../usecases/footstep.md) で詳細
- 能力結果メッセージ (PRIVATE_SEER 等) の可視性は [usecases/mask](../../usecases/mask.md)
- 候補取得 API は `GET /api/v1/villages/{id}/ability/targets`・`/ability/footsteps` 等へ整理
