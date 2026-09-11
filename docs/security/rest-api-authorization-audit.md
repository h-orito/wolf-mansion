# REST API 情報秘匿・認可監査レポート

- 対象: `/api/v1/**`（backend `com.ort.app.api` 配下の全 RestController）
- ベースコミット: `feature/monorepo` (3108b994)
- 監査日: 2026-07（Issue: REST API の情報秘匿・認可監査）
- 手法: (1) 全エンドポイントのレスポンス構築を静的追跡し、(2) 稼働 backend で視点別トークン（匿名 / 生存参加者 / 死亡 / 見学 / 村建て / 管理者）× 村ステータスの実レスポンスを curl で照合

## 背景

人狼ゲームは「誰に何を見せないか」がゲーム性そのもの。REST 移行では「ドメインモデルをそのまま返す」方針を採るため、認可マスクの漏れがそのままゲーム崩壊バグになる。SSR には無かったリスク面（ViewModel の生シリアライズ）が移行で新設された API 群に集中するため、網羅監査してテストで固定化する。

## 認可モデルの全体像

- `/api/v1/**` は `WolfMansionWebSecurityConfig` の Order(1) チェーン（JWT / stateless）。列挙した公開 GET と一部 POST（村ポーリング `/{id}/update`、debug）以外は `anyRequest().authenticated()`。
- SecurityConfig は「ログイン済みか」しか判定しない**粗いゲート**。**リソース所有者・ロール・視点マスクは各 Controller / Coordinator / DomainService 層で適用**される。したがって監査の主眼はこのドメイン層マスクの網羅性に置いた。

## 結論サマリ

| 重大度 | 件数 | 対応 |
| --- | --- | --- |
| critical | 1 | 本 PR で修正 + 回帰テスト |
| low | 3 | L-1 / L-2 は許容（対応不要・ユーザー判断）、L-3 は推奨 |
| info | 3 | 記録のみ（過剰秘匿／設計意図） |

視点別トークンでの実測により、**進行中の村における匿名・生存参加者視点での役職・陣営・囁き・独り言・墓下・秘話・投票先・能力履歴・分析メモの漏洩は、下記 critical 1 件を除き検出されなかった**。

## critical: 梟（地獄耳）視点で囁き等の発言者 identity が漏洩【本 PR で修正】

- 箇所: `api/view/VillageMessageContent.kt`、経路 `GET /api/v1/villages/{id}/messages`
- 内容: 梟は進行中の村で人狼の囁き・共鳴発言・恋人発言・念話を「地獄耳」として閲覧できる。仕様上「名前も『地獄耳』固定となるため、誰がどの種別で発言したかはわからない」（`frontend/.../rule/sections/OtherSection.tsx`）。ところが `VillageMessageContent` は big-ears 対象でも `messageType`（発言種別）/ `characterName` / `characterId` / `characterImageUrl` / `width` / `height` / `playerName` / `messageNumber` を素通しでシリアライズしていた。フロント（`BigEarsMessage`）は表示上マスクするが、**生 JSON（DevTools / curl）から囁き主＝人狼・共鳴/共有・恋人・念話メンバーを特定可能**。役職バランスを崩す、REST 化による生シリアライズ由来の新規リグレッション。
- 修正: `VillageMessageContent.of(...)` で big-ears 判定時に上記の発言者特定情報を null 化し、`messageType` は通常発言に倒し、`isRainbow` / `isLoud` を false、`canReply` を false にする。本文（`messageContent`）は梟が読める情報なので残す。表示は `isBigEars` で「地獄耳」に倒れ、これらを使わないため無影響。
- 回帰テスト: `api/view/VillageMessageContentTest`（地獄耳マスク時に identity が全て null / 非地獄耳時は保持）。

## low

### L-1: `situation/me` の恋人リストが自ペア以外の恋絆まで開示【許容・対応不要】
`AbilityDomainService.getLoversList` は「村内の全恋人ペア」を返す。単一キューピッド編成では無害だが、求愛・略奪等で複数ペアが同時成立する編成では、あるペアが他ペアの恋絆相手を推測できる。**ゲーム仕様上許容（ユーザー判断で対応不要）**。

### L-2: `random-keywords` の update/delete が所有者/ロール検証なし【許容・対応不要】
`RandomKeywordRestController` の update/delete は存在確認のみで、共有グローバルマスタを全ログインユーザーが改変・削除可能（実測: 別ユーザーで PUT/DELETE 成功）。「認証付き CRUD」の設計意図であり、**共有マスタ運用として許容（ユーザー判断で対応不要）**。

### L-3: 通知設定 `webhookUrl` のドメイン非検証（ブラインド SSRF）【推奨】
`VillageNotificationRequest.webhookUrl` は `@NotBlank` のみ。保存直後にサーバから任意 URL へ POST（`notifyTest`）するため、認証済みユーザーが内部エンドポイントを標的にできる。レスポンスは呼び出し元に返さないブラインド SSRF。`discord.com`/`discordapp.com` 系ホストのホワイトリスト検証を推奨。

## info（記録のみ・実害なし）

- **過剰秘匿の視点不整合**: `situation` のスポイラー可視判定は `settled || (グレー公開 && 死亡/見学)` で admin/producer を含まないため、進行中に admin/producer は部屋割の役職は見えるが足音の役職ヘッダ・能力履歴は見えない。`detail` の `participants[].skill` は principal 非受領のため常に `isSettled()` 一択で、墓下公開ルールが反映されない。いずれも漏洩ではなく過剰秘匿／不整合。フロントが役職表示に `situation` の部屋割を使う前提なら実害なし。
- **`memo`（RP メモ）の常時公開**: `/{id}/memo` で本人が編集する公開プロフィール欄。秘匿対象ではない。
- **公開マスタ**: `charachips` / `skills` / `rooms` / `rule` は秘匿情報を含まない純粋な公開データ。

## 実測で確認済み（問題なし）

- 発言: 匿名 / 他参加者 / 賢者 / 導師 の各視点で `PUBLIC_SYSTEM` + `NORMAL_SAY` のみ取得。囁き・他人の独り言は取得不可。投稿者本人のみ自分の囁き / 独り言を取得。
- anchor 参照: 匿名 / 非人狼が囁きを `messageType`+番号 指定で取得しようとしても `null`（IDOR 遮断）。
- 村状況・村詳細: 進行中の村で `skill` / `camp` / `player` / `isWin` は全視点 null（役職語はいずれも公開 `setting` の編成配分由来で個人割当ではない）。
- 村設定: 入村パスワードは `hasJoinPassword: false`（ブール）のみで実体を返さない。
- `situation/me`: 匿名は 401。
- 分析メモ: `analyzer-memo` は JWT の `playerId` でスコープされ、他人視点では空（実測: 別ユーザーで他人メモを取得できない）。匿名は 401。
- 認可: `admin/*` は DB 再取得で ADMIN 限定（一般ユーザー 400 /「管理者のみ」）、`creator/*` は村建て本人限定（一般ユーザー 400）、`ability`/`vote`/`commit`/`participate` は呼び出し元本人の participant のみで代理不可。`attack-targets`/`footsteps` は襲撃/捜査能力を持たない視点には候補を返さない。
