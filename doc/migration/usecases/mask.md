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
| | 死因 | 現状は全表示 (制限なし) |

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
- View (`VillageParticipantsContent` 等) では死因を表示。進行中の room 表示でも死亡マーク (凸/▼/❤︎/▲) は出る (situation.html, step-0.6)
- 墓下発言可否: `VillageParticipant.isViewableGraveSay()` = `isAdmin() || isSpectator || (isDead() && !dead.reason!!.isSuddenly())` (`VillageParticipant.kt:79`)。**admin と見学者は無条件で可視**、それ以外は死者かつ非突然死のみ (突然死は墓下発言不可)。進行中の見学者への公開可否は `Village.isViewableGraveSay(player)` 側の `isVisibleGraveSpectateMessage` ゲートが別途かかる

## 4. 投票先

- Vote (day/charaId/targetCharaId) は素の保持。だが **時間的フィルタ・隠蔽フィルタは既に存在する**:
  - `VoteDomainService.convertToVillageSituation` が `filterPastDay(day)` で当日票を除外 (`VoteDomainService.kt:38`) → 進行中は最新日の投票先がそもそも返らない
  - 黒箱 (隠蔽) ability の対象日は `getHideDays` で非表示化 (`VoteDomainService.kt:74-76`)
  - 公開 API `/api/village/{id}` も `filterDisplayVotes` (黒箱除去) + `filterByDay(day-1)` (1日ずれ) を適用 (`WholeVillageSituationsContent.kt`)
- `openVote` (記名/無記名) は主に UI 表示の差
- → **「投票先マスクはモデルに無い」は誤りで、既に部分実装済み**。残る論点は「黒箱以外の任意投票先を進行中に追加で隠すか」(下記「移行時の注意」参照)

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
- **3 軸 (status × 視点 × フィールド)** を意識した API 設計。同じ村でもビューアごとにレスポンスが変わる → キャッシュキーに視点を含める / 認証必要データは CSR ([03-auth.md](../03-auth.md))
- **投票先の進行中マスク**は `filterPastDay` + 黒箱 `getHideDays` で既に部分実装済み (§4)。残論点は「黒箱以外の任意投票先を進行中に追加で隠すか」を明示決定 (要ユーザー確認の候補)
- 死因の可視範囲も「現状全表示」を維持するか確認
- 公開 API (`/api/village/{id}`) の隠蔽パターンは step-0.17 でピン留め
