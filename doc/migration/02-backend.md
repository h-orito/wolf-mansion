# 02. Backend (REST 化方針)

Spring Boot バックエンドを Thymeleaf SSR から REST API 専用に変換する。

## 方針

- Thymeleaf は **全廃**。`api/` 配下の Controller は REST レスポンス (JSON) を返す形に置き換える
- **Clean Architecture / DDD を維持する**
  - 依存方向は `api → application → domain` の片方向。`infrastructure` は `domain` のインターフェースを実装する形で外側に置く (依存性逆転)
  - ドメインモデル (data class) の不変性 (`copy()` ベースの状態遷移) を維持
  - ビジネスルールは `domain/service/` に集約、ユースケース調整 (トランザクション境界) は `application/coordinator/` に置く
  - REST 化に伴う変更は **api 層 (Controller / View) と application 層の入り口** に局所化し、domain / infrastructure は原則そのまま使う
- **ドメイン仕様の把握は「ユースケース単位」で行う**。1 ファイル / 1 レイヤーを読むだけでは不十分:
  - 状態組み立ては Controller / Coordinator / DomainService / Daychange / Repository / View 変換 にまたがって行われていることが多い
  - Model 詰め込み時の変換、`copy()` ベースの加工、表示判定 (誰に何を見せるか) などが各所に分散している
  - ユースケースごとに **入力 → ドメイン処理 → 表示までのフロー全体**を追って、現状仕様 (= 移行後も維持すべき挙動) を確定させる
- 既存のレイヤー構成 (`api / application / domain / infrastructure / fw`) は維持
  - DBFlute (`src/main/java/com/ort/dbflute/`) はそのまま使う
  - ドメインモデル (data class) はそのまま使う
- 参加者に見せてはいけない情報のマスク方法は **[h-orito/firewolf](https://github.com/h-orito/firewolf) の backend を参考**にする
  - 特に **DomainModel → XxxView 変換**で API Response 用に整える設計が参考になる

## 既存の現行公開 API も参考にする

- 本番 (wolfort.net) には既に **非参加者向けの公開 API** が存在する
  - 例: `https://wolfort.net/wolf-mansion/api/village/711`
  - 別アプリ [h-orito/wolf-mansion-analyzer](https://github.com/h-orito/wolf-mansion-analyzer) はこの API を消費している
- このレスポンスは、足音 / 投票情報など **「非参加者が見ても問題ない形」に既に隠蔽処理がなされている**
- 本移行で REST 化を進める際、非参加者視点での View 変換ロジックは現行公開 API の実装が **強い参考**になる
  - どのフィールドを落としているか / 集計済みに変換しているか
  - どこまで「進行中の村」と「終了した村」で見え方を変えているか
- 注意: 足音の **視覚化機能** (analyzer 側の UI) は本移行スコープ外。**後続ステップで別途検討**する

## firewolf を参考にするポイント (調査メモ)

- どのレイヤーで View 変換を行っているか (Controller / Coordinator / DomainService?)
- View クラスの粒度 (Aggregate ごと? Endpoint ごと?)
- 認可情報 (誰が見ているか) をどう View 変換に渡しているか
- ネストする View (例: VillageView 内に ParticipantView[]) の組み立て方
- 死亡理由マスクなど「見え方が状況によって変わる」フィールドの扱い

→ 計画ドラフト段階で firewolf のコードを直接確認し、本プロジェクトに適用できる形に落とし込む。

## wolf-mansion 固有: 足音情報

- firewolf には存在しない概念
- 「足音 (footstep)」は誰がどの部屋を通ったかを表す情報で、見え方の制御 (誰に見せるか) が複雑
- View 変換時の取り扱いは **別途調査・設計が必要**
- 仕様調査は **ユースケース単位**で行う:
  - 足音の登録 (能力使用時 / Daychange 時に発生?) → どのテーブルにどう積まれるか
  - 足音の参照 (村画面表示 / 日付ナビ / 各役職視点) → 誰にどこまで見せるか、reveal タイミング
  - Daychange による状態遷移 (Prologue / Progress / Epilogue / Finished / 廃村) 各段階での見え方
  - 関連レイヤーは Controller / Coordinator / DomainService / Repository / 各種 View 変換 にまたがるので、横断的に追う
- 上記をもとに reveal ロジック / View 変換仕様を整理する
- **非参加者向け表示**は、現行公開 API (`/wolf-mansion/api/village/{id}`) の隠蔽パターンを流用候補とする (上記「現行公開 API も参考にする」参照)
- 足音の **可視化 UI** (analyzer 風) は本移行スコープ外、後続ステップで扱う

## 外部公開済み API (互換性必須)

以下の API は **外部から既に利用されている**ため、エンドポイントパス・レスポンス内容を **変更してはいけない**。本移行で REST 化を進める際も、これらは**現状の挙動を完全維持**する。

- `/village-record/list`
- `/village-record/latest-vid`
- `/skill/list`
- `/recruiting`

加えて、村単位の公開 API も外部 (analyzer サイト等) から消費されている:

- `/wolf-mansion/api/village/{id}` (例: `https://wolfort.net/wolf-mansion/api/village/711`)

互換性維持にあたっての注意:

- **URL 全体 (context-path 含む)** を維持する必要がある。新 URL 構成では `/wolf-mansion` が **frontend service に割り当てられる**ため、`/wolf-mansion/...` 配下の既存パスは backend に直接届かない:
  - frontend 側でこれら **legacy パスを backend に proxy** して結果を返す
  - もしくは frontend (React Router の resource route 等) が backend を呼んで `application/json` を返す
  - どちらの形態を取るかは別途確定 ([06-infra-deploy.md](06-infra-deploy.md) 参照)
- 新規エンドポイントを `/api/v1/...` で切る場合、これら既存パスとの併存方針を決める必要がある
- 互換性確認のため **e2e or 契約テスト** で挙動をピン留めしておくことが望ましい

## lint / format

- **ktlint** を導入する
- 参考: [h-orito/lastwolf](https://github.com/h-orito/lastwolf) の backend 構成 (ktlint + Claude hooks による自動 fix)
- Claude Code の hooks (PostToolUse) で `.kt` ファイル編集後に **check & fix が自動で走る**仕組みを入れる
  - hook が呼び出すスクリプトは `.context/ktlint-hook/` 配下に配置する想定 (lastwolf 構成を参考)
- 設定の詳細は [07-workflow.md](07-workflow.md) で扱う

## API 設計指針

### バージョニング・エンドポイント構成 (B10 確定)

- **新規エンドポイントは `/api/v1/...` 配下に集約**する
- **既存パブリック API のパスは凍結 (= 完全維持)** とする:
  - `/village-record/list`
  - `/village-record/latest-vid`
  - `/skill/list`
  - `/recruiting`
  - `/wolf-mansion/api/village/{id}`
- 既存パスは `/api/v1/...` 配下への複製や rewrite は行わず、現在の Controller / View を **そのまま温存**する
- frontend service 側で `/wolf-mansion/...` 配下の legacy パスを backend に proxy する (詳細は [06-infra-deploy.md](06-infra-deploy.md))
- 既存パスを将来 `/api/v1/...` 側へ deprecation 移行する計画は **本移行スコープ外**

### レスポンス形式 (B2 確定)

- **JSON 一択**
- レスポンス用クラスは **`api/response/` 配下に新設**する
  - クラス名は `XxxResponse` 系統 (例: `VillageResponse`, `VillageDetailResponse`)
  - SSR 時代の `api/view/` 配下 (Thymeleaf 用 ViewModel) とは **別ディレクトリ**として明確に分ける
  - 移行期間中は `api/view/` (旧) と `api/response/` (新) が並存する。Step 終盤で旧 Controller / Thymeleaf テンプレートと一緒に `api/view/` も撤去
- リクエストフォームは既存の `api/request/` 配下を継続利用
- ページネーション、フィルタ、ソート: 必要箇所で統一規約を設ける (詳細は別途)

### エラーレスポンス (B4 確定)

- **Spring Boot 3 標準の `ProblemDetail` (RFC 7807 / `application/problem+json`)** を採用
- 共通フィールド: `type` / `title` / `status` / `detail` / `instance`
- アプリ固有のエラーコードや追加情報が必要になった場合は、`ProblemDetail` の `properties` を拡張して持たせる
- `@RestControllerAdvice` (`fw/` 配下) で例外を `ProblemDetail` に集約変換
- 既存パブリック API (上記「凍結」対象) は **対象外**。レスポンス互換性維持のため現状のエラー返却を変えない

## DBFlute との同居方針 (B5 確定)

- **現状維持**: `src/main/java/com/ort/dbflute/` 配下の自動生成コードは触らず、Repository 実装も Bhv 利用のまま継続
- REST 化に伴う変更は **api 層 / application 層の入り口** に局所化し、`domain/` / `infrastructure/` は原則ノータッチ
- DBFlute 自体を別 ORM (Exposed / jOOQ 等) に差し替える話は **本移行スコープ外**

## 静的リソース (B6 確定)

- `src/main/resources/static/` 配下のファイル (画像 / CSS / 既存 jQuery + Handlebars JS 13 ファイル等) は **全て frontend service へ移管**する
- backend は REST API 専用とし、静的ファイル配信を持たない
- Step 0 (調査ステップ) で静的リソースの **棚卸し一覧**を作成 (どのファイルがどの画面で使われているかを含めて)
- React 側で再利用するもの (画像 / 一部 CSS) は frontend リポジトリ配下の `public/` または `assets/` に移す
- jQuery + Handlebars JS は React コンポーネントに置き換えるため、Step 1 以降で **段階的に削除**

## 未確定事項 / 要調査

- [ ] エンドポイント一覧の洗い出し (画面別調査と連動) — Step 0
- [ ] 認可情報の View 渡し方 (現在の SecurityContext からの取得) — Step 2/3 着手前
- [ ] 日付更新 (Daychange) のトリガー (現在 Thymeleaf 側? バッチ?) の確認と REST 化対応 — Step 0
- [ ] 旧 Controller (`api/`) と Thymeleaf テンプレート (`src/main/resources/templates/`) の撤去計画 — Step 終盤
- [ ] 外部公開済み API の **現状フルパス**確認 (context-path 含む) — Step 0
- [ ] 外部公開済み API の **挙動ピン留めテスト** (契約テスト or e2e) の整備 — Step 0 中盤
- [ ] ktlint 導入の具体構成 (gradle plugin / standalone CLI / lastwolf 構成の流用範囲) — Step 2 着手前
- [ ] `.context/ktlint-hook/` 配下に置くスクリプトの設計 (lastwolf を参考) — Step 2 で実装
