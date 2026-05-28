# ユースケース: 足音 (footstep) reveal

> wolf-mansion 固有概念。占い・襲撃・護衛・狂狐の徘徊で「誰がどの部屋を通ったか」を表す。見え方の制御が複雑。

## フロー全体

```
能力使用 (setAbility) → updateFootstep (DB insert)
  → Daychange (updateDaychangeDifference で前日差分を同期)
  → convertToSituation (Spoiler フィルタ)
  → VillageContent.villageFootstepList / 公開API
```

## 1. 登録 (いつ積まれるか)

- **能力使用時**: `VillageCoordinator.setAbility()` 直後に `FootstepApplicationService.updateFootstep()` → `FootstepDataSource.updateFootstep()`
  - 対象: `hasDisturbAbility()` (妨害=徘徊系) または `isTargetingAndFootstep()` 能力のみ
- **Daychange 時**: `DaychangeCoordinator.daychange()` が `footstepService.updateDaychangeDifference()` で前日能力設定の差分を DB 同期
- **テーブル `footstep`**: `day` (足音が鳴る=能力日の翌日) / `registerCharaId` (セット者) / `charaId` (対象) / `footstepRoomNumbers` (カンマ区切り部屋番号 or "なし")

## 2. 参照・選択 (能力時の足音候補)

- `VillageCoordinator.getSelectableFootstepList()` → `FootstepDomainService.getCandidateList(village, charaId, targetCharaId)`
- `FootstepCreator` が**時計回り/反時計回りの 2 経路**を生成 (グリッド上で開始→目標の直線経路、上→右→下→左 順で部屋列挙)
- 結果例: `"01,02,03"` / `"04,05"` (最大 2 パターン)
- 村画面 JS (village.js) は襲撃対象選択 → `getFootstepList` で候補取得 → 足音 select / 部屋テーブル

## 3. reveal / マスク (誰にどこまで)

- 表示形式の分岐: `FootstepDomainService.convertToSituation()` が `SpoilerDomainService.isViewableSpoilerContent(village, myself)` で切替
  - **可視 (詳細)**: 終了/エピローグ、または「死者 + 墓下役職公開(openSkillInGrave)」→ `getDisplayFootstepStringOpenSkill()` = `[キャラ名][役職名] セット→実際` 形式
  - **非可視 (簡略)**: 進行中の生存者 → `getDisplayFootstepStringWithoutHeader()` = 「部屋XX で足音…」のみ (誰のか分からない)
- spoiler 判定の根拠:
  - `Village.isViewableSpoilerContent()` = `status.isSettled()` (終了系)
  - `VillageParticipant.isViewableSpoilerContent(isOpenSkillInGrave)` = 死者 && 墓下役職公開
- 生存者フィルタ: `isAliveWhen(day)` かつ `!isNoSound()` (無音役職除外)
- 村画面テンプレ (situation.html 足音タブ) の `data-spoiled-content` / `data-spoiled-alternative-content` と連動 ([village-base.md](../screens/village-base.md))

## 4. View 変換

- `FootstepDomainService` の表示文字列化 (`getDisplayFootstepStringOpenSkill` / `WithoutHeader`)
- `VillageContent.villageFootstepList`: `VillageFootstepSituation` → `VillageFootstepContent`
- 能力候補: `AbilityDomainService.convertToParticipantSituation()` が足音候補を提供 (捜査系能力)

## 5. 公開 API での扱い

- `WholeVillageSituationsContent` (`/api/village/{id}`): `VillageDaySituation.footsteps` = 日ごと足音
  - `footsteps.filterByDay(day).list.map { it.roomNumbers }.sorted()` で **部屋番号のみ公開**、キャラ・役職は隠蔽
  - **1 日ずれ**: `day-1` の足音を `day` で表示する事前処理あり
  - `filterDisplayFootsteps()` で生存者フィルタ後に出力

## 関連クラス

| 層 | クラス | パス |
|---|---|---|
| Coordinator | VillageCoordinator | `application/coordinator/VillageCoordinator.kt` |
| Application | FootstepApplicationService | `application/service/FootstepApplicationService.kt` |
| Domain | FootstepDomainService | `domain/service/FootstepDomainService.kt` |
| Domain | SpoilerDomainService | `domain/service/SpoilerDomainService.kt` |
| Model | Footstep / Footsteps | `domain/model/footstep/Footstep.kt` |
| Model | Village.isViewableSpoilerContent | `domain/model/village/Village.kt` |
| Repository | FootstepDataSource | `infrastructure/datasource/FootstepDataSource.kt` |
| View | VillageContent / WholeVillageSituationsContent | `api/view/...` |

## 移行時の注意

- **reveal ロジックは backend に残す**。REST レスポンスは「そのビューアに見せてよい足音」に整形済みで返す (firewolf の View 変換参考、[02-backend.md](../02-backend.md))
- 経路生成 (FootstepCreator) と表示文字列化は domain に温存、React は整形済み文字列/構造を表示するだけにする
- 公開 API の 1 日ずれ・部屋番号のみ公開は **互換維持** (step-0.17 ピン留め対象)
- 足音の **可視化 UI** (analyzer 風) は本移行スコープ外 ([02-backend.md](../02-backend.md))
- spoiler 判定 (`isViewableSpoilerContent`) は死亡理由マスク等と共通基盤 → [mask.md](mask.md) (step-0.16) と統合的に設計
