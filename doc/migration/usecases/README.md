# 横断ユースケース調査 index (Step 0)

1 画面 / 1 レイヤーを読むだけでは把握できない、**ユースケース単位**で追うべき仕様の調査記録置き場 ([02-backend.md](../02-backend.md) 参照)。
各ユースケースは Controller / Coordinator / DomainService / Daychange / Repository / View 変換 にまたがるため、入力 → ドメイン処理 → 表示までのフロー全体を追う。

詳細は `doc/migration/usecases/<usecase>.md` に分けて記録する。

## 調査対象

### `step-0.14` 足音 (footstep) reveal ✅ → [footstep.md](footstep.md)

- 「誰がどの部屋を通ったか」を表す情報。見え方の制御が複雑 (firewolf には無い wolf-mansion 固有概念)
- 調査軸:
  - **登録**: 足音はいつ積まれるか (能力使用時 / Daychange 時?)、どのテーブルにどう入るか
  - **参照**: `GET /village/getFootstepList`、村画面表示・各役職視点で誰にどこまで見せるか、reveal タイミング
  - **状態遷移**: Prologue / Progress / Epilogue / Finished / 廃村 各段階での見え方
  - **非参加者向け**: 公開 API (`/api/village/{id}`) の隠蔽パターン
- 関連レイヤー: VillageMessageController / 村画面 View 変換 / DomainService
- 可視化 UI (analyzer 風) は **本移行スコープ外**

### `step-0.15` Daychange (日付更新) ✅ → [daychange.md](daychange.md)

- 村の状態遷移処理。`Daychange` データクラスに村の全状態を集約し copy で進行
- 調査軸:
  - **トリガー**: `POST /village/{id}/dayChange` (DebugController, `app.debug:true` 時)。本番のトリガー経路 (バッチ? スケジューラ?) を確認
  - **委譲フロー**: `DaychangeCoordinator` → `DaychangeDomainService` → `Prologue/Progress/Epilogue DomainService`
  - **各 status での挙動**: 募集中→進行中→エピローグ→終了、廃村
  - e2e からの利用: デバッグ endpoint を叩いて日付を進める (時刻操作不要)
- REST 化後も同等のデバッグ endpoint を残す方針 ([05-e2e.md](../05-e2e.md))

### `step-0.16` 認可マスク (死亡理由 / 投票 / 役職) ✅ → [mask.md](mask.md)

- 視点 (誰が見ているか) と村状況で見え方が変わるフィールドの整理
- 調査軸:
  - 死亡理由・死亡日のマスク (誰にいつ見せるか)
  - 投票先の公開タイミング (進行中は隠す? エピローグで開示?)
  - 役職の可視性 (自分 / 仲間 / 占い結果 / 霊媒結果 / 終了後全開示)
  - 秘話 (限定公開メッセージ) の可視範囲
- firewolf の DomainModel → XxxView 変換を参考に、API Response でどうマスクするか整理
- 関連: 村画面の全 View 変換、公開 API の隠蔽処理

## 合格基準

各ユースケース md は以下を満たす:

1. 入力 → ドメイン処理 → 表示までの **フロー全体**が追えている (関連レイヤー / クラスを明記)
2. **視点・状況ごとの見え方**が表で整理されている
3. REST 化後の **View 変換方針** (どのフィールドを落とす / 集計済みに変換するか) の方向性が出ている
4. 関連する画面 md (`../screens/`) と相互リンクされている
