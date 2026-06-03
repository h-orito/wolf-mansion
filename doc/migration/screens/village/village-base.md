# 画面: 村画面ベース (レイアウト / 日付ナビ / 状況サマリ / 共通基盤)

> 村画面の **ハブ文書**。全体構成・共通基盤を記述し、機能別の詳細は step-0.7〜0.13 の各 md に分割する。

## 概要

- **URL (既存)**: `GET /village/{id}` (最新日) / `GET /village/{id}/day/{day}` (指定日)
- **テンプレート**: `village.html` (メイン) + `village/*.html` (24 断片) + Handlebars `village-template/*`
- **担当 JS**: `village.js` (2081行) / `village-message.js` (417行)
- **Controller**: `VillageController` + 中核ヘルパー **`VillageControllerHelper`**
- **対象ユーザー**: 全員 (匿名〜参加者〜村主〜管理者で見え方・操作が変化)

## アーキテクチャ: VillageSituation / ParticipantSituation の二層 situation 駆動

村画面の「誰に何を見せ、何を操作させるか」は **`VillageControllerHelper.setIndexModel`** が組み立てる。situation は **2 層**に分かれており、移行設計ではこの 2 つを明確に分けて扱う:

- **`VillageSituation` (村全体の状態 ＝ 誰が取得しても同じ情報)** (`domain/model/situation/VillageSituation.kt`)
  - 内訳: `roomAssigned` (部屋割り) / `live` (参加者の生死) / `footstep` (足音) / `vote` (投票状況, VillageVoteSituation) / `whole` (村全体)
  - 視点に依存しない村の現況。状況サマリ ([village-situation-summary.md](village-situation-summary.md)) の表示源
  - ※ ただし足音・役職などは表示時に **スポイラーマスク**が視点依存でかかる (`isDispSpoilerContent`、[usecases/mask](../../usecases/mask.md))。「同じ情報」なのは計算ベースで、最終表示はマスク後
- **`ParticipantSituation` (参加者自身の状態 ＝ 自分専用)** (`domain/model/situation/ParticipantSituation.kt`)
  - 内訳: `participate` / `skillRequest` / `commit` / `say` / `rp` / `ability` / `vote` (ParticipantVoteSituation) / `admin` / `creator`
  - **ビューア固有の capability** (どのフォーム/操作が可能か)。各フラグで participate / switchParticipate / changeRequestSkill / leave / commit / say / action / changeName / memo / faceType / ability / vote / creator(+kick, creatorSay) / notification を条件付き出力
  - `VillageCoordinator.findVillageSituation` / `findParticipantSituation` で構築

- `VillageContent` の **constructor 入力** = village / day / myself / player / charachips / keywords / **villageSituation** / **participantSituation** / **isDispSpoilerContent** (`VillageContent.kt:72-81`)。これらは内部で変換され、**実際にシリアライズされる data class フィールドは別物**:
  - `villageId, villageNumber, villageName, villageStatusCode, settings, day, dayList, epilogueDay, memberList, characterList, participantList, roomAssignedRowList, roomWidth, form, myself, isAvailableSettingsUpdate, vote, villageFootstepList, dayChangeDatetime, isDispUnspoiler, randomKeywords, situationList, isDispSpoilerContent, isCreatePlayer` (`VillageContent.kt:22-70`)
  - `villageSituation` / `participantSituation` は**フィールドとして保持されず**、`form` / `vote` / `situationList` 等に変換される → **REST DTO 設計はこの実フィールドを正本にする**
- `villageSituation` / `participantSituation` (← `VillageCoordinator.findVillageSituation` / `findParticipantSituation`) が **能力モデル**。各フラグで以下フォームを条件付き出力:
  - participate / switchParticipate / changeRequestSkill / leave / commit / say / action / changeName / memo / faceType / ability / vote / creator(+kick, creatorSay) / notification
- `isDispSpoilerContent` (`SpoilerDomainService.isViewableSpoilerContent`) = スポイラー (足音・役職等) の可視判定 → step-0.16
- デバッグモード (`app.debug:true`): `dummyLoginPlayerList` で参加者になりすましログイン可 (e2e で活用、step-0.15)

> **移行の核心**: この situation オブジェクトが React 側の「ビューア別 capability + マスク」モデルの正本。**2 層を分けて設計すること**:
> - **`VillageSituation` (村全体・公開)** は、誰が取得しても同じ村取得 API のレスポンスに含める (認証不要・SSR 取得可、スポイラーマスクのみ視点依存)
> - **`ParticipantSituation` (自分専用)** は、ログインユーザーの視点固有なので認証付き・CSR で取得する ([03-auth.md](../../03-auth.md) の SSR/CSR 境界)
>
> この 2 層分離は **firewolf (<https://github.com/h-orito/firewolf>) の実装が大いに参考になる** (同作者の後継実装で、村の公開状態と参加者個別状態を分離している)。REST/React 化の situation 設計はこれを参照する。

## レイアウト構成 (village.html → form-area)

```
village.html
├ data-village-id / data-day (village.js が参照)
├ 村タイトル + Twitter共有
├ 日付リスト (village-day-list)          ← 日付ナビ
├ message-area (data-message-area)        ← village.js が Handlebars で描画 (step-0.7)
├ #bottom (発言後スクロール先)
├ form-area (village/form-area)           ← 全アクションフォームのコンテナ
│  ├ situation        … 状況サマリ → village-situation-summary.md
│  ├ say-form / action-form               → step-0.8
│  ├ vote-form / skill-area / commit-form → step-0.10
│  ├ participate / change-skill / switch-participate / leave → step-0.9
│  ├ change-name / face-type              → step-0.11
│  └ creator / admin / debug              → step-0.12
├ footer-menu (フローティング操作メニュー)
├ village-day-list (下部にも)
├ modal-filter (抽出)                     → step-0.7
├ modal-village-info (村情報)             → step-0.13
├ display-settings (設定モーダル)         → village-user-settings.md
├ agelimit-confirm (R15/R18 確認モーダル)  ← village-message.js (全閲覧者向け)
├ skill-description (初回役職確認モーダル)  → step-0.10
├ Handlebars: message / message-partial / participants テンプレート
└ alerts: daychange / autorefresh / time(残り時間) / user
```

## 1. 機能 (ベース部分)

- 日付ナビゲーション (プロローグ / N日目 / エピローグ / 終了)
- 状況サマリ (部屋割り / 参加者 / 投票 / 足音 タブ)
- 自動更新ポーリング・残り時間カウントダウン・日付更新検知
- 表示設定 (Cookie 保存)
- フローティングフッターメニュー
- 切り抜き画面 / サイトトップへの導線

## 2. 状況サマリ (situation.html)

状況サマリ (部屋割り / 参加者 / 投票 / 足音 タブ) は **村画面の一部**だが、記載量が多いため別 md [village-situation-summary.md](village-situation-summary.md) に切り出している (独立画面ではなく doc 分割)。これは `VillageSituation` (村全体・全員共通) の可視化に対応する。

## 3. 呼び出す API エンドポイント (ベース)

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/village/{id}` | 村画面 (最新日) | SSR |
| GET | `/village/{id}/day/{day}` | 指定日表示 | 日付リンク (`data-day-link`) |
| POST | `/village/{id}/update` | 村状態の定期更新 (login/latestDay 確認) | village.js (30秒) |
| GET | `/village/getLatestMessageDatetime?villageId=&day=` | 最新発言日時 (更新検知) | village.js (30秒) |

> **CSRF**: `POST /village/{id}/update` は CSRF 除外 (`WolfMansionWebSecurityConfig.kt:53-57`、除外は confirm/say/update/api-login のみ)。移行時の CSRF 方針に影響。

## 4. 既存 JS の挙動 (ベース部分)

- **ポーリング (30秒)**: 同一 30 秒ループ内で `getLatestMessageDatetime` (新着検知 → リフレッシュアイコン点滅 / autorefresh 時は再ロード) と `POST update` (日付更新・セッション失効を検知し alert) を**両方**呼ぶ (`village.js:1929-1961`、`updateVillage()` は初回ロード直後にも 1 回実行 `:1961`)
- **残り時間 (500ms)**: `#daychange-datetime` 要素が存在する時のみ (= `dayChangeDatetime != null`、終了村は null で非表示) 現在時刻との差分を `HH:MM:SS` 表示 (`village.js:1897-1908`)
- **日付ナビ**: ページネーション (前/次/指定/最新)。日付リンク遷移は1ページ目、それ以外は最新
- **設定モーダル (display-settings, `#modal-dsetting`)**: 表示設定 (Cookie: ページ分割/サイズ/装飾ボタン 等) + Discord 通知設定。詳細は [village-user-settings.md](village-user-settings.md)。年齢制限確認フラグも Cookie 保存
- **footer-menu**: 最上部/最下部/更新/抽出(filter modal)/情報(village-info modal)/設定(dsetting modal)。未投票時「投票欄へ」警告
- `canAutoRefresh`: 発言/アクション確認中は false にして自動更新を抑止

## 5. 権限による分岐

- すべて `participantSituation` 由来。匿名は閲覧のみ、参加者は各種フォーム、村主/管理者は追加メニュー (詳細は各サブ step)

## 6. 認可マスク

- `isDispSpoilerContent` + `data-spoiled-*` (詳細 step-0.16)

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/village/{id}`。各 status (プロローグ/進行中/エピローグ/終了/廃村)、各視点 (匿名/参加者/村主) でのスクショ

## 8. 関連 e2e ケース候補

- [ ] 村表示 (各 status)
- [ ] 日付ナビ遷移
- [ ] 自動更新で新着反映
- [ ] 残り時間カウントダウン
- [ ] 表示設定の保存・復元

## メモ / 移行時の注意

- **`ParticipantSituation` / `VillageSituation` の構造把握が村画面移行の最重要事項**。REST 化時、村取得 API のレスポンスにこの situation を含め、frontend はそれを見て UI を出し分ける ([02-backend.md](../../02-backend.md) firewolf 参考)
- ポーリングは React では TanStack Query の `refetchInterval` + (将来 WebSocket 検討) に置換
- Handlebars 3 テンプレート (message / message-partial / participants) は React コンポーネント化 (step-0.7)
- Cookie 表示設定は localStorage + Zustand へ
- **広告 (adsbygoogle) は移行対象** (確定)。共通フッター ([home.md](../home.md) のフッター節) と同じく React へ移植。`noAd` フラグ (R18 村・new-player・change-password 等) での非表示制御も維持
- 24 個の `village/*.html` 断片は form-area に集約。React では situation 駆動の条件付きコンポーネント群へ
