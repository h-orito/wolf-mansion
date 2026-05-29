# ユースケース: Daychange (日付更新 / 村ライフサイクル)

> 村の状態遷移処理。`Daychange` データクラスに村の全状態を集約し copy で不変進行。

## ⚠️ 最重要: 本番のトリガーは「ポーリング駆動」

- **スケジューラ / バッチ (`@Scheduled` 等) は存在しない**
- 本番で日付が進むのは **`POST /village/{id}/update` (村画面のクライアント JS が定期ポーリング)** がトリガー
  - その中で `daychangeCoordinator.changeDayIfNeeded(village)` が走り、`dayChangeDatetime` を現在時刻と比較して超過なら遷移
- つまり **「誰も村を見ていないと日付が進まない」** 可能性がある現仕様
- debug: `POST /village/{id}/dayChange` (`app.debug:true`) が最新日の `daychangeDatetime` を `now-1秒` にして `changeDayIfNeeded` を呼ぶ (e2e の日付進行手段、step-0.13/05-e2e)

### 移行時の設計判断 (要検討)

- 現状踏襲 (ポーリング駆動) か、移行を機に **cron/scheduler 導入**か
- React 化後はポーリングが TanStack Query の `refetchInterval` 経由になる。`POST /update` 相当を叩き続ける限り現挙動は維持できる
- ただし stateless backend + 複数 frontend インスタンスでは「ポーリング駆動の副作用で状態遷移」はやや危うい → **スケジューラ化を推奨検討事項**として残す ([06-infra-deploy.md](../06-infra-deploy.md) と連動)

## 委譲フロー

```
DaychangeCoordinator.changeDayIfNeeded(village)
  ├ Daychange 集約構築 (5引数 secondary constructor: village, abilities, votes, footsteps, players)
  ├ DaychangeDomainService.leaveParticipantIfNeeded()      … 長期未アクセス者の退村 (Prologue)
  ├ DaychangeDomainService.cancelOrExtendPrologueIfNeeded() … 最小人数未満で廃村 or 延長
  └ DaychangeDomainService.changeDayIfNeeded(daychange, commits, charas)
       ├ PROLOGUE: PrologueDomainService → startVillage (役職/部屋割当, 能力初期化) → PROGRESS
       ├ PROGRESS: ProgressDomainService → changeDay (襲撃/占い/投票/処刑 等 ~40能力処理)
       │            → EpilogueDomainService.transitionToEpilogueIfNeeded (勝敗判定) → EPILOGUE
       └ EPILOGUE: EpilogueDomainService → (24h後) → FINISHED
```

## Daychange データクラス (`domain/model/daychange/Daychange.kt`)

```kotlin
data class Daychange(
    val village: Village, val abilities: Abilities, val votes: Votes,
    val footsteps: Footsteps, val messages: Messages, val players: Players,
    val tweets: List<String>, val guarded: List<VillageParticipant>
)
```
- Coordinator は **5 引数 secondary constructor** で構築し、`messages` / `tweets` / `guarded` は空初期化される (`Messages(emptyList())` 等, `Daychange.kt:21-39`)。この 3 フィールドは処理中に蓄積されるもので、集約構築時の入力ではない
- 各処理は `daychange.copy(village=..., messages=...)` で不変進行 → 最後に diff 検出 → DB 更新 → 通知

## status 遷移条件

| status | 遷移条件 | 次 | 処理 |
|---|---|---|---|
| 募集中 (PROLOGUE, day0) | `now >= dayChangeDatetime` && 参加者 >= personMin | 進行中 | 役職/部屋割当・能力初期化 |
| 募集中 | `now >= dayChangeDatetime` && 参加者 < personMin && 参加者 > 1 | 募集中 (延長) | `extendPrologue` で dayChangeDatetime 延長 |
| 募集中 | `now >= dayChangeDatetime` && 参加者 < personMin && 参加者 <= 1 | 廃村 | キャンセル |
| 進行中 (PROGRESS) | (時刻超過 OR 全員コミット) && !settled | 進行中 | 能力処理・投票・処刑 |
| 進行中 | settled (狼全滅 OR 村人数 <= 狼) | エピローグ | 勝敗判定 |
| エピローグ (EPILOGUE) | `now >= dayChangeDatetime` | 終了 | ゲーム終了 |

- **コミット**: 全員コミットで時刻前でも進行 (ProgressDomainService.shouldChangeDay)
- エピローグ延長/短縮: creator 操作 (step-0.12) で `dayChangeDatetime` を変更

## 永続化・通知

- `DaychangeCoordinator.updateIfNeeded()` が各 Service (Village/Vote/Ability/Player/Footstep/Message) で diff update
- 通知: Prologue→Progress (村開始)、Progress→Epilogue、日付進行 を Discord/Mastodon へ (`notify...ToCustomerIfNeeded`)

## 関連クラス

| 責務 | パス |
|---|---|
| トリガー | `api/VillageController.kt` (`POST /village/{id}/update`) |
| Coordinator | `application/coordinator/DaychangeCoordinator.kt` |
| Model | `domain/model/daychange/Daychange.kt` |
| DomainService | `domain/service/daychange/{Daychange,Prologue,Progress,Epilogue}DomainService.kt` |
| DataSource | `infrastructure/datasource/village/VillageDayDataSource.kt` |
| Debug | `api/DebugController.kt` (`dayChange`) |

## 移行時の注意

- **Daychange の全 domain ロジックは温存** (REST 化は api/application 入口のみ、[02-backend.md](../02-backend.md))
- `POST /village/{id}/update` (ポーリング+日付更新トリガー) は REST 化必須。レスポンス `VillageUpdateResponse` (login, latestDay 等) を React のポーリングで使う
- **ポーリング駆動 vs スケジューラ化**は移行の設計論点として 08-step-plan / 06-infra に残す
- e2e は debug `dayChange` で進行制御 ([05-e2e.md](../05-e2e.md))
