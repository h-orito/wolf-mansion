# ユースケース: 認可マスク (誰に何を見せるか)

> 視点 (匿名/参加者/死者/人狼/村建て/管理者) × 村 status × フィールド で見え方が変わる。村画面移行の中核的な横断仕様。

## マスクの 3 軸

| 軸 | 値 | 効果の例 |
|---|---|---|
| 村 status | 進行中 | 囁き/秘話/独り言/PRIVATE_* 非表示、役職非開示 |
| | エピローグ/終了 (settled) | 全発言 + 役職 + 死因を開示 |
| 視点 | admin / creator (producer) | ほぼ全可視 |
| | 自参加者 | 役職権限に応じた能力結果が可視 |
| | 死者 (非突然死) | 墓下発言が可視 (オプション依存) |
| | 見学者 | `openSkillInGrave` で墓下公開 |
| フィールド | GRAVE_SAY | `visibleGraveSpectateMessage` でオーバーライド |
| | SECRET_SAY | 当事者のみ (+ admin) |
| | 死因 | **進行中は無惨死 (襲撃/呪殺/罠死/爆死/雑魚) を「無惨」にマスク**。突然/処刑/後追はそのまま表示。settled で実死因を全開示 (§3) |
| | 投票先メッセージ | **無記名 (`openVote=false`) or 黒箱あり → 非公開システムメッセージ (当事者のみ)**、記名かつ黒箱なし → 公開システムメッセージ (§4) |

## 1. スポイラー判定の共通基盤

- **`SpoilerDomainService.isViewableSpoilerContent(village, myself)`** =
  - `Village.isViewableSpoilerContent()` (= `status.isSettled()`, 終了後全公開) **OR**
  - `VillageParticipant.isViewableSpoilerContent(isOpenSkillInGrave)` (= `isOpenSkillInGrave && (isDead() || isSpectator)`, 死者/見学者 + 墓下公開)
- 村画面テンプレの `data-spoiled-content` / `content.dispSpoilerContent` はこれが正本 ([village-base.md](../screens/village/village-base.md))。足音 ([footstep.md](footstep.md)) もこれを使う

## 2. メッセージ可視性

- `MessageService.findMeesages/findMessage` → `MessageDomainService.setViewableQuery(village, myself, player, query)` が可視発言種別リストを query に設定
- `getViewableMessageTypeList()`:
  - admin / settled / canceled / producer → 全種別
  - 梟 (地獄耳) → 追加で `owlViewableSayTypeList`
  - 他者 → 役職・権限でフィルタ (検死官→検死結果, 霊能→霊視結果 等)
- 種別ごと `isViewable*` (`domain/service/message/say/*.kt`):
  - WEREWOLF_SAY (囁き): `settled || creator || myself.skill.isViewableWerewolfSay`
  - GRAVE_SAY (呻き): `settled || visibleGraveSpectateMessage || creator`
  - SECRET_SAY (秘話): `settled || creator || admin` (実質当事者のみ)
  - MONOLOGUE_SAY (独り言): `settled || creator`
  - PRIVATE_* (占い/霊媒/能力結果): 本人 + 権限のみ
- **2 層構造に注意**: 上記「種別リスト層」(`getViewableMessageTypeList`) に加え、`MessageQuery.setAvailable` の **query 層インクルージョン** (`includeMonologue` / `includeSecret` / `includePrivateAbility`, `MessageQuery.kt:48-50`) が併用される。当事者が自分の独り言/秘話/能力結果を見られるのはこの query 層の OR 付与による → 種別リスト層だけ移植すると**当事者が自分の秘話/独り言を見られないバグ**になる

## 3. 死亡理由・死亡日

- `VillageParticipant.dead: Dead` (reason 襲撃/処刑/突然死/後追 + deadDay)
- **死因は進行中マスクされる (全表示ではない)**: `DeadReason.getDisplayName(isSettled)` = `if (!isSettled && isMiserable()) "無惨" else name` (`DeadReason.kt:21-27`)
  - **無惨 (miserable) = `襲撃 / 呪殺 / 罠死 / 爆死 / 雑魚`** (`CDef.DeadReason.listOfMiserable()`)。進行中はこれらを実死因ではなく **「無惨死」(▲)** としてのみ開示
  - **`突然 / 処刑 / 後追` は非無惨**なので進行中も**そのまま表示**
  - `settled` (エピローグ/終了) で全死因を実名開示
- View (`VillageParticipantsContent` 等) では上記マスク後の死因を表示。進行中の room 表示でも死亡マーク (凸/▼/❤︎/▲) は出る (▲=無惨, situation.html, step-0.6)
- 墓下発言可否: `VillageParticipant.isViewableGraveSay()` = `isAdmin() || isSpectator || (isDead() && !dead.reason!!.isSuddenly())` (`VillageParticipant.kt:79`)。**admin と見学者は無条件で可視**、それ以外は死者かつ非突然死のみ (突然死は墓下発言不可)。進行中の見学者への公開可否は `Village.isViewableGraveSay(player)` 側の `isVisibleGraveSpectateMessage` ゲートが別途かかる

## 4. 投票先

- Vote (day/charaId/targetCharaId) は素の保持。だが **時間的フィルタ・隠蔽フィルタは既に存在する**:
  - `VoteDomainService.convertToVillageSituation` が `filterPastDay(day)` で当日票を除外 (`VoteDomainService.kt:38`) → 進行中は最新日の投票先がそもそも返らない
  - 黒箱 (隠蔽) ability の対象日は `getHideDays` で非表示化 (`VoteDomainService.kt:74-76`)
  - 公開 API `/api/village/{id}` も `filterDisplayVotes` (黒箱除去) + `filterByDay(day-1)` (1日ずれ) を適用 (`WholeVillageSituationsContent.kt`)
- **`openVote` (記名/無記名) は投票先の可視性そのものを左右する** (UI 表示差ではない): 日付更新時の投票結果メッセージ生成で `messageType = if (isOpenVote && !existsBlackbox) 公開システムメッセージ else 非公開システムメッセージ` (`ExecuteDomainService.kt:204-205`)
  - **無記名 (`openVote=false`) または黒箱あり → 非公開システムメッセージ (PRIVATE_SYSTEM)** = 当事者以外に投票先が見えない
  - **記名 (`openVote=true`) かつ黒箱なし → 公開システムメッセージ** = 全員に投票先一覧が見える
- → 投票先マスクは **filterPastDay (当日票除外) + 黒箱 getHideDays + openVote によるメッセージ種別分岐**で日付更新時に完結済み。移行では**この既存挙動をそのまま温存**すればよく、追加マスクの設計判断は不要

## 5. 役職可視性

- `VillageParticipant.skill` / `Skill`:
  - `Skill.isViewableWolfCharaName()`: 人狼キャラ名が仲間人狼に見える
  - 占い/霊媒等の結果は役職権限でフィルタ (`PsychicMessageDomainService` 等の isViewable)
  - 終了後の役職履歴全開示: `VillageParticipantsContent` 自体は status ガード無しで無条件全開示 (`VillageParticipantsContent.kt:45-62`)、settled 判定は呼び出し元 `VillageMessageController.getParticipants` の `if (!status.isSettled()) throw` で実施 (`VillageMessageController.kt:187`)。**マスク責務は controller 側**。表示形式は `skillName → 3d{skillName}` (1日目はプレフィックス無し)

## 関連クラス

| 責務 | パス |
|---|---|
| スポイラー基盤 | `domain/service/SpoilerDomainService.kt` |
| 可視判定 (モデル) | `domain/model/village/Village.kt`, `.../participant/VillageParticipant.kt`, `.../dead/Dead.kt` |
| メッセージ可視 | `domain/service/MessageDomainService.kt`, `domain/service/message/say/*.kt` |
| View 変換 | `api/view/VillageContent.kt`, `VillageParticipantsContent`, `WholeVillageSituationsContent` |

## 移行時の注意 (最重要)

- **マスクは全て backend で完結させる**。REST レスポンスは「そのビューアに見せてよいデータのみ」を返す。frontend にマスク前データを渡さない (リーク防止)
- `ParticipantSituation` / `isViewableSpoilerContent` / `getViewableMessageTypeList` を **村取得 API のマスク基盤**に据える ([village-base.md](../screens/village/village-base.md), firewolf の View 変換参考)
- **3 軸 (status × 視点 × フィールド)** を意識した API 設計。同じ村でもビューアごとにレスポンスが変わる → **ビューアによって内容が変わる部分はキャッシュ化しない** (サーバーサイドキャッシュの実装方針は現行から変えない。視点をキャッシュキーに含める等は不要)。認証必要データは CSR ([03-auth.md](../03-auth.md))
- **投票先の進行中マスク**は `filterPastDay` + 黒箱 `getHideDays` + `openVote` のメッセージ種別分岐 (§4) で日付更新時に**実装済み**。移行ではこの既存挙動を温存するのみ (追加マスクの設計判断は不要)
- **死因マスク**も既存挙動を温存: 進行中は無惨 (襲撃/呪殺/罠死/爆死/雑魚) を「無惨」表示、突然/処刑/後追はそのまま、settled で全開示 (§3)
- 公開 API (`/api/village/{id}`) の隠蔽パターンは step-0.17 でピン留め
