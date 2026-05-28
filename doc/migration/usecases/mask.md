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
- 村画面テンプレの `data-spoiled-content` / `content.dispSpoilerContent` はこれが正本 ([village-base.md](../screens/village-base.md))。足音 ([footstep.md](footstep.md)) もこれを使う

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

## 3. 死亡理由・死亡日

- `VillageParticipant.dead: Dead` (reason 襲撃/処刑/突然死/後追 + deadDay)
- View (`VillageParticipantsContent` 等) では死因を表示。進行中の room 表示でも死亡マーク (凸/▼/❤︎/▲) は出る (situation.html, step-0.6)
- 墓下発言可否: `isViewableGraveSay()` = `isDead() && !dead.reason.isSuddenly()` (突然死は墓下発言不可、設定でオーバーライド可)

## 4. 投票先

- Vote (day/charaId/targetCharaId) は素の保持、**時間的フィルタはモデルにない**
- `VillageVoteSituation` は完全リストを返し、**可視制御は上位 (situation/View) で実施**
- `openVote` (記名/無記名) は主に UI 表示の差。データ取得制限ではない → REST 化時に「進行中に投票先を隠すか」を明示的に設計する必要

## 5. 役職可視性

- `VillageParticipant.skill` / `Skill`:
  - `Skill.isViewableWolfCharaName()`: 人狼キャラ名が仲間人狼に見える
  - 占い/霊媒等の結果は役職権限でフィルタ (`PsychicMessageDomainService` 等の isViewable)
  - 終了後は `VillageParticipantsContent` で役職履歴を全開示 (`1d△ → 3d◎` 形式)

## 関連クラス

| 責務 | パス |
|---|---|
| スポイラー基盤 | `domain/service/SpoilerDomainService.kt` |
| 可視判定 (モデル) | `domain/model/village/Village.kt`, `.../participant/VillageParticipant.kt`, `.../dead/Dead.kt` |
| メッセージ可視 | `domain/service/MessageDomainService.kt`, `domain/service/message/say/*.kt` |
| View 変換 | `api/view/VillageContent.kt`, `VillageParticipantsContent`, `WholeVillageSituationsContent` |

## 移行時の注意 (最重要)

- **マスクは全て backend で完結させる**。REST レスポンスは「そのビューアに見せてよいデータのみ」を返す。frontend にマスク前データを渡さない (リーク防止)
- `ParticipantSituation` / `isViewableSpoilerContent` / `getViewableMessageTypeList` を **村取得 API のマスク基盤**に据える ([village-base.md](../screens/village-base.md), firewolf の View 変換参考)
- **3 軸 (status × 視点 × フィールド)** を意識した API 設計。同じ村でもビューアごとにレスポンスが変わる → キャッシュキーに視点を含める / 認証必要データは CSR ([03-auth.md](../03-auth.md))
- **投票先の進行中マスク**は現状モデルに無いので、移行時に「隠すか否か」を明示決定 (要ユーザー確認の候補)
- 死因の可視範囲も「現状全表示」を維持するか確認
- 公開 API (`/api/village/{id}`) の隠蔽パターンは step-0.17 でピン留め
