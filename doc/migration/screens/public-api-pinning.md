# 外部公開 API のピン留め (step-0.17)

> 外部 (analyzer サイト等) から消費される公開 API。**パス・レスポンス内容を変更してはいけない** ([02-backend.md](../02-backend.md))。現状を記録し契約テストでピン留めする。

## 対象エンドポイント (context-path `/wolf-mansion` 込み)

| パス | View | 命名規則 | 備考 |
|---|---|---|---|
| `GET /recruiting` | `RecruitingContent` | camelCase | 募集中〜未終了の村 (サンプル村除外) + charachips |
| `GET /village-record/list` | `VillageRecordListContent` | **snake_case** | エピローグ/終了/廃村の村 (reversed) + players。`?vid=` で絞込。ネスト (`VillageRecord`/`VillageParticipantRecord`) に `interval_seconds`/`start_datetime`/`win_camp_name`/`user_id`/`dead_reason` 等多数の `@JsonProperty` |
| `GET /village-record/latest-vid` | `VillageRecordLatestVidContent` | camelCase | エピローグ/終了/廃村の最新村 id |
| `GET /skill/list` | `SkillListContent` | **snake_case** | 陣営別役職名 |
| `GET /api/village/{id}` | `WholeVillageSituationsContent` | **camelCase** | 村の全状況 (足音は部屋番号のみ・1日ずれ、[footstep.md](../usecases/footstep.md))。ネストビュー含め `@JsonProperty` 無し |
| `GET /api/village-list` | `VillageListContent` | camelCase | 全村 + charachipList + skillList |

## 現状レスポンス記録 (ローカル :8091、村データ空の状態)

```jsonc
// GET /village-record/latest-vid
{"vid":0}

// GET /skill/list  (★ snake_case: camp_name / skill_name_list)
{"list":[{"camp_name":"愉快犯陣営","skill_name_list":["爆弾魔","梟",...]}, ...]}

// GET /recruiting   (村なし時)
{"villageList":[]}

// GET /village-record/list   (村なし時)
{"list":[]}

// GET /api/village-list   (★ camelCase)
{"villageList":[],
 "charachipList":[{"id":1,"name":"人狼BBS"},{"id":2,"name":"大神学園"},...],
 "skillList":[{"code":"VILLAGER","name":"村人"},{"code":"SEER","name":"占い師"},...]}

// GET /api/village/1   (存在しない村)  ★ ローカル :8091 (devtools 有効) では 500 + trace
//   ⚠ この trace は spring-boot-devtools が有効な開発環境の挙動。本番 (packaged jar = devtools 自動無効、
//      Boot 3.5.9 既定 include-stacktrace=never / include-message=never) では trace も詳細 message も出ない見込み
{"timestamp":"...","status":500,"error":"Internal Server Error",
 "trace":"com.ort.app.fw.exception.WolfMansionBusinessException: village not found. id: 1 ..."}
```

## 重要な契約上の注意

1. **命名規則がエンドポイントで混在**: `skill/list` は **snake_case** (`camp_name`, `skill_name_list`)、`api/village-list` は **camelCase**。
   - REST 化で Jackson のグローバル命名戦略を変えると **既存 API が壊れる**。各エンドポイントの現状 casing を**個別に維持**すること (View ごとに `@JsonProperty` 等で固定)
2. **エラー時の 500 レスポンス形状は環境依存** (`village not found` で `WolfMansionBusinessException`)。
   - アプリ層に有効な例外ハンドラは無い (`ExceptionControllerAdvice` は `@ControllerAdvice` 等の annotation が無く**未登録のデッドコード**) → Spring Boot 既定の `BasicErrorController` が処理
   - **ローカル :8091 で trace が出るのは `spring-boot-devtools` が有効なため** (`build.gradle.kts:33` `developmentOnly`)。本番 (packaged jar で devtools 自動無効、Boot 3.5.9 既定 `include-stacktrace=never`) では trace は出ない見込み
   - → **ピン留めサンプルは本番 (or devtools 無効ビルド) の実レスポンスから採取**すること。dev の trace 付きサンプルを凍結すると本番と恒常的に不一致になる
   - 新規 API は `ProblemDetail` 化。公開 API のエラー形状変更は消費側を壊す可能性があるため、本番の現状形状を確定してから方針判断 (スタックトレース露出はセキュリティ上望ましくない)
3. **足音**: `/api/village/{id}` は部屋番号のみ公開・1 日ずれ ([footstep.md](../usecases/footstep.md))。この隠蔽/集計を維持
4. **URL 全体 (context-path `/wolf-mansion` 込み)** を維持。新構成では frontend が proxy ([06-infra-deploy.md](../06-infra-deploy.md))

## ピン留めテスト方針

- **契約テスト**を整備し、移行前後でレスポンスが一致することを保証
- 実装場所: `e2e/` または backend のテスト。リクエスト → レスポンス JSON のスナップショット比較
- **フィクスチャ用に実際の村データが必要**: ローカル DB は現状空 → debug の `allparticipate` + `dayChange` で村を作って各 status のサンプルを生成、または本番 `wolfort.net/wolf-mansion/api/village/711` 等の実レスポンスを参照サンプルに
- 比較対象: ステータスコード / Content-Type / レスポンスボディ (キー名・構造・隠蔽の有無)
- **整備タイミング**: Step 0 中盤〜 Step 2 まで (REST 化が公開 API を壊さない安全網、[05-e2e.md](../05-e2e.md))

## 移行時の注意

- これらは `/api/v1/...` に **移さない** (既存パス凍結、[02-backend.md](../02-backend.md) B10)
- backend では legacy 用 Controller として温存し、frontend proxy で `/wolf-mansion/...` を転送
- ピン留めテストを **先に整備**してから REST 化本体に着手する (回帰検知)
