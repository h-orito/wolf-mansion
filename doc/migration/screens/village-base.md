# 画面: 村画面ベース (レイアウト / 日付ナビ / 状況サマリ / 共通基盤)

> 村画面の **ハブ文書**。全体構成・共通基盤を記述し、機能別の詳細は step-0.7〜0.13 の各 md に分割する。

## 概要

- **URL (既存)**: `GET /village/{id}` (最新日) / `GET /village/{id}/day/{day}` (指定日)
- **テンプレート**: `village.html` (メイン) + `village/*.html` (24 断片) + Handlebars `village-template/*`
- **担当 JS**: `village.js` (2081行) / `village-message.js` (417行)
- **Controller**: `VillageController` + 中核ヘルパー **`VillageControllerHelper`**
- **対象ユーザー**: 全員 (匿名〜参加者〜村主〜管理者で見え方・操作が変化)

## アーキテクチャ: ParticipantSituation 駆動

村画面の「誰に何を見せ、何を操作させるか」は **`VillageControllerHelper.setIndexModel`** が組み立てる:

- `VillageContent` = village / day / myself / player / charachips / keywords / **villageSituation** / **participantSituation** / **isDispSpoilerContent**
- `villageSituation` / `participantSituation` (← `VillageCoordinator.findVillageSituation` / `findParticipantSituation`) が **能力モデル**。各フラグで以下フォームを条件付き出力:
  - participate / switchParticipate / changeRequestSkill / leave / commit / say / action / changeName / memo / faceType / ability / vote / creator(+kick, creatorSay) / notification
- `isDispSpoilerContent` (`SpoilerDomainService.isViewableSpoilerContent`) = スポイラー (足音・役職等) の可視判定 → step-0.16
- デバッグモード (`app.debug:true`): `dummyLoginPlayerList` で参加者になりすましログイン可 (e2e で活用、step-0.15)

> **移行の核心**: この situation オブジェクトが React 側の「ビューア別 capability + マスク」モデルの正本。REST 化時は `GET /api/v1/villages/{id}?day=` のレスポンスに situation 相当を含める設計になる。

## レイアウト構成 (village.html → form-area)

```
village.html
├ data-village-id / data-day (village.js が参照)
├ 村タイトル + Twitter共有
├ 日付リスト (village-day-list)          ← 日付ナビ
├ message-area (data-message-area)        ← village.js が Handlebars で描画 (step-0.7)
├ #bottom (発言後スクロール先)
├ form-area (village/form-area)           ← 全アクションフォームのコンテナ
│  ├ situation        … 状況サマリ (本 md)
│  ├ say-form / action-form               → step-0.8
│  ├ vote-form / skill-area / commit-form → step-0.10
│  ├ participate / change-skill / switch-participate / leave → step-0.9
│  ├ change-name / face-type              → step-0.11
│  └ creator / admin / debug              → step-0.12
├ footer-menu (フローティング操作メニュー)
├ village-day-list (下部にも)
├ modal-filter (抽出)                     → step-0.7
├ modal-village-info (村情報)             → step-0.13
├ display-settings (表示設定)             ← 本 md
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

## 2. 状況サマリ (situation.html) のタブ

| タブ | 表示条件 | 内容 |
|---|---|---|
| 部屋割り当て | `roomWidth != null && day > 0` | 部屋グリッド (キャラ画像/生死/死因マーク/役職スポイラー) + 日別状況 (突然死/処刑/犠牲/復活/後追/能力) → 詳細 step-0.13 |
| 参加者 | 常時 (初期 active は day0/部屋なし時) | 生存/死亡などステータス別メンバー一覧 (memo 付) → 詳細 step-0.13 |
| 投票 | `vote != null` | 投票表 (日付別) → 詳細 step-0.10 |
| 足音 | `villageFootstepList` あり | 日別足音 (スポイラーマスク対応) → 詳細 step-0.14 |

- 死因マーク: 凸(SUDDON) / ▼(EXECUTE) / ❤︎(SUICIDE) / ▲(その他)。色: 赤=襲撃系, 青=処刑/突然死, ピンク=後追
- スポイラー: `data-spoiled-content` / `data-spoiled-alternative-content` で「ネタバレ防止」時に内容を代替表示。`content.dispSpoilerContent` で列自体の有無

## 3. 呼び出す API エンドポイント (ベース)

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| GET | `/village/{id}` | 村画面 (最新日) | SSR |
| GET | `/village/{id}/day/{day}` | 指定日表示 | 日付リンク (`data-day-link`) |
| POST | `/village/{id}/update` | 村状態の定期更新 (login/latestDay 確認) | village.js (30秒) |
| GET | `/village/getLatestMessageDatetime?villageId=&day=` | 最新発言日時 (更新検知) | village.js (30秒) |

## 4. 既存 JS の挙動 (ベース部分)

- **ポーリング (30秒)**: `getLatestMessageDatetime` で新着検知 → リフレッシュアイコン点滅 / autorefresh 有効時は再ロード。`POST update` で日付更新・セッション失効を検知し alert
- **残り時間 (500ms)**: `daychange-datetime` と現在時刻の差分を `HH:MM:SS` 表示
- **日付ナビ**: ページネーション (前/次/指定/最新)。日付リンク遷移は1ページ目、それ以外は最新
- **表示設定 (display-settings)**: Cookie に各タブ開閉 / ページサイズ / 画像大小 / テキスト大小 / 年齢制限確認を保存・復元
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

- **`ParticipantSituation` / `VillageSituation` の構造把握が村画面移行の最重要事項**。REST 化時、村取得 API のレスポンスにこの situation を含め、frontend はそれを見て UI を出し分ける ([02-backend.md](../02-backend.md) firewolf 参考)
- ポーリングは React では TanStack Query の `refetchInterval` + (将来 WebSocket 検討) に置換
- Handlebars 3 テンプレート (message / message-partial / participants) は React コンポーネント化 (step-0.7)
- Cookie 表示設定は localStorage + Zustand へ
- 広告 (adsbygoogle) は移行スコープで扱いを判断 (R18村は noAd)
- 24 個の `village/*.html` 断片は form-area に集約。React では situation 駆動の条件付きコンポーネント群へ
