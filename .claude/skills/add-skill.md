---
description: 人狼ゲームに新しい役職（スキル）を追加する手順を実行する。新役職のコード名・日本語名・略称・陣営・能力内容をユーザーに確認しながら、DB定義→コード自動生成→ドメインモデル→能力サービス→日付更新処理の順に実装を進める。
user_invocable: true
---

# 新役職追加スキル

人狼ゲーム「wolf-mansion」に新しい役職を追加する手順を段階的に実行します。

**重要**: `src/main/java/` 配下は DBFlute による自動生成ファイルのみで構成されているため、絶対に直接編集しないでください。

## 手順

### Step 1: 役職の基本情報を確認

ユーザーに以下を確認してください（未指定の場合）:

- **skill_code**: 英大文字のコード（例: `HUNTER`, `WEREWOLF`）
- **skill_name**: 日本語の役職名（例: 狩人）
- **skill_short_name**: 1文字の略称（例: 狩）。既存と重複しないこと
- **camp_code**: 陣営コード。`VILLAGER` / `WEREWOLF` / `LOVERS` / `FOX` / `CRIMINAL` のいずれか
- **disp_order**: 表示順。陣営ごとの既存の並び順を確認して適切な値を決める
  - 村人陣営: 1〜99
  - 人狼陣営: 100〜199（人狼系）、200〜299（狂人系）
  - 恋人陣営: 300〜399
  - 狐陣営: 400〜499
  - 愉快犯陣営: 500〜599
- **能力の内容**: どのような能力を持つか（占い系、護衛系、襲撃系、特殊能力など）
- **新しい AbilityType が必要か**: 既存の能力種別で代用できない場合は新設が必要

### Step 2: TSV にマスタデータを追加

`dbflute_wolf_mansiondb/playsql/data/common/tsv/UTF-8/04-SKILL.tsv` にタブ区切りで1行追加します。

```
{skill_code}\t{skill_name}\t{skill_short_name}\t{camp_code}\t{disp_order}
```

disp_order の昇順になるよう適切な位置に挿入してください。

新しい AbilityType が必要な場合は `07-ABILITY_TYPE.tsv` にも追加します。

### Step 3: classificationDefinitionMap.dfprop を更新

`dbflute_wolf_mansiondb/dfprop/classificationDefinitionMap.dfprop` の `Skill` セクション内 `groupingMap` で、新役職が属するグループに日本語名を追記します。

主なグループと追加が必要になるケース:

| グループ | 追加する場合 |
|---|---|
| `availableWerewolfSay` | 人狼の囁きに参加できる場合（人狼系、C国狂人） |
| `viewableWerewolfSay` | 人狼の囁きを閲覧できる場合（上記＋黙狼、聴狂人） |
| `hasDivineAbility` | 占い能力を持つ場合 |
| `hasSkillPsychicAbility` | 役職霊能能力を持つ場合 |
| `hasAttackAbility` | 人狼の襲撃能力を持つ場合 |
| `hasDisturbAbility` | 徘徊能力を持つ場合（占い師の結果に影響） |
| `noDeadByAttack` | 襲撃されても死なない場合 |
| `wolfCount` | 勝敗判定時に人狼としてカウントされる場合 |
| `noCount` | 勝敗判定時にどちらにもカウントされない場合（狐系など） |
| `viewableWolfCharaName` | 人狼が誰かを知ることができる場合 |
| `divineResultWolf` | 占い結果が「人狼」になる場合 |
| `psychicResultWolf` | 霊能結果が「人狼」になる場合 |

### Step 4: DBFlute コード再生成

```bash
cd dbflute_wolf_mansiondb && sh manage.sh 1
```

これにより `src/main/java/com/ort/dbflute/allcommon/CDef.java` が再生成され、新役職の enum エントリが追加されます。

**manage.sh 1 は「regenerate（再自動生成）」に対応します。** ユーザーに実行の許可を取ってください。

### Step 5: Skill.kt を更新

`src/main/kotlin/com/ort/app/domain/model/skill/Skill.kt` を更新します。

#### 5-1. skillToAbility マップ

能力を持つ場合、companion object 内の `skillToAbility` マップにエントリを追加:

```kotlin
CDef.Skill.{新役職名} to AbilityType(CDef.AbilityType.{能力種別})
```

#### 5-2. 各種フラグメソッド

役職の特性に応じて以下のメソッドを確認・修正:

| メソッド | 確認すべきケース |
|---|---|
| `isRevivable()` | 転生先候補に含めない場合は除外 |
| `isRequestable()` | 役職希望で選択不可にする場合 |
| `isFoxCount()` | 狐陣営でカウントされる場合 |
| `isDeadByDivine()` | 占われると死ぬ場合（狐系） |
| `isCounterDeadByDivine()` | 占った側が死ぬ場合（呪狼系） |
| `isCounterDeadByInvestigate()` | 捜査で逆に死ぬ場合 |
| `isNoSound()` | 足音が発生しない場合 |
| `isOpenSkill()` | 公開役職の場合 |
| `hasLoneAttackAbility()` | 単独襲撃能力を持つ場合 |
| `isShogiWolf()` | 将棋狼の場合 |

#### 5-3. タグ用リスト

該当するリストに追加:

- `hasCommandAbilitySkills`: 指揮系能力
- `hasGuardAbilitySkills`: 護衛系能力
- `hasChangeCampAbilitySkills`: 陣営変化能力
- `hasChangeSkillAbilitySkills`: 役職変化能力
- `hasRevivalOtherAbilitySkills`: 他者蘇生能力
- `hasRevivalMyselfAbilitySkills`: 自己蘇生能力
- `hasTrapAbilitySkills`: 踏むと死亡する能力
- `limitedCountSkills`: 回数制限あり
- `availableEverydaySkills`: 毎日使用可能
- `targetingAndFootstepSkills`: 対象指定＋足音
- `footstepSkills`: 足音が発生する役職
- `madmanPriorityList` / `wolfPriorityList` / `seerPriorityList`: おまかせ割り当て優先度

### Step 6: 能力サービスクラスを新規作成

`src/main/kotlin/com/ort/app/domain/service/ability/` に `{XxxDomainService}.kt` を新規作成します。

既存の類似能力サービスを参考にしてください。`AbilityTypeDomainService` インターフェースを実装します。

```kotlin
@Service
class XxxDomainService(
    // 必要な依存を注入
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.{能力種別})

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        // 能力対象の選択肢を返す
    }

    override fun getTargetPrefix(): String? = "対象ラベル"
    override fun getTargetSuffix(): String? = "を〜する"
    override fun isTargetingAndFootstep(): Boolean = true/false
    override fun canUseDay(day: Int): Boolean = day > 1

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        // デフォルト能力の設定
    }

    fun doAbility(daychange: Daychange): Daychange {
        // 能力の実行処理
    }
}
```

### Step 7: AbilityDomainService.kt に登録

`src/main/kotlin/com/ort/app/domain/service/ability/AbilityDomainService.kt` を更新:

1. **コンストラクタ**: 新サービスを DI パラメータに追加
2. **`detectAbilityTypeService()`**: when 式に分岐を追加
3. **`addDefaultAbilities()`**: デフォルト能力設定の呼び出しを追加（必要な場合）

### Step 8: ProgressDomainService.kt に登録

`src/main/kotlin/com/ort/app/domain/service/daychange/ProgressDomainService.kt` を更新:

1. **コンストラクタ**: 新サービスを DI パラメータに追加
2. **`changeDay()`**: 適切な処理順序の位置に能力処理を挿入

**処理順序の注意**: 既存の処理フローを確認し、能力の性質に応じた適切な位置に挿入すること。例えば護衛系は占いより前、襲撃系は護衛の後など。

### Step 9: ビルド確認

```bash
./gradlew build -x test
```

コンパイルエラーがないことを確認します。

## チェックリスト

```
【DB/マスタ層】
□ 04-SKILL.tsv にレコード追加
□ 07-ABILITY_TYPE.tsv に能力種別追加（必要な場合）
□ classificationDefinitionMap.dfprop の groupingMap に分類追加
□ sh manage.sh 1 で CDef.java を再生成

【ドメインモデル層】
□ Skill.kt の skillToAbility マップに能力追加
□ Skill.kt の各フラグメソッドを確認・修正
□ Skill.kt の各タグリストに追加

【能力サービス層】
□ XxxDomainService.kt を新規作成
□ AbilityDomainService.kt のコンストラクタに DI 追加
□ AbilityDomainService.kt の detectAbilityTypeService() に分岐追加
□ AbilityDomainService.kt の addDefaultAbilities() に追加（必要な場合）

【日付更新処理層】
□ ProgressDomainService.kt のコンストラクタに DI 追加
□ ProgressDomainService.kt の changeDay() に処理を追加

【最終確認】
□ ./gradlew build -x test でコンパイル成功
```
