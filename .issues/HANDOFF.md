# HANDOFF — wolf-mansion REST + RR v7 移行計画

本ドキュメントは、移行計画作業の **次セッションで最初に何をやるか** を示すハンドオフメモ。
決定事項・設計詳細は `migration.md` (index) と `doc/migration/` 配下が正本。ここには docs に載らない運用状況だけを書く。

## 現在地

- **フェーズ**: **Step 8 (村画面) 進行中 — 8.1〜8.6 ✅ #71〜#76 / 8.7+8.8 (切替・希望変更・退村) ✅ #77**。Step 7 (新規村作成) 全完了 🎉 (7.1〜7.5 ✅ #66〜#70)。Step 6 (ランダム機能) 完了 (#65)。Step 5 (情報・静的ページ) 完了 (5.1〜5.5 ✅ #60〜#64)。Step 4 完了済 (4.1 ホーム / 3.6 認証忠実再現 / 4.2 村一覧 / 4.3 intro)。Step 3 / Step 2 完了済
- **Step 8 は統合ブランチ方式 (ユーザー指示 2026-06-12・最重要)**: `feature/monorepo-step8` を base にサブ step PR を積む。**同ブランチへの squash merge は Claude 単独で可** (夜間連続作業のため)。`feature/monorepo` への merge は Step 8 完了時の統合 PR でユーザーレビュー。api-drift CI の branches フィルタに `feature/monorepo-step8` 追加済み (8.1 PR)
  - **step-8.7/8.8 完了 ✅ (#77)**: 参加見学切替・希望役職変更・退村 (同 controller の小操作 3 つを 1 PR に統合)。`POST switch-participate`/`change-skill`/`leave` (domain assert 委譲) + situation/me フラグで出し分ける 3 パネル。退村は confirm ダイアログ (legacy parity)
    - **components/ui に `Panel` (タイトル付きパネル) を新設**し、発言/アクション/入村/参加系の重複 markup を排除 (collapsible は従来どおり別物)
    - e2e 1 件追加: **入村 → 役職希望変更 → 退村の自己完結フロー** (実入村するが退村で後片付け)。全 **61 件**。8.6 で残置した村 2 の `e2ejoin01` も退村実測で片付け済み。pr-reviewer 2 巡 (`.reviews/PR-77.md`)
  - **step-8.6 完了 ✅ (#76)**: 入村。`ParticipatePanel` (input → confirm の 2 段: キャラセット/キャラ select で名前・略称自動補完 → 希望役職 第1/第2 → 入村発言 → パスワード → 見学チェック → **ルール/礼節の 2 つの同意チェックで「入村する」活性化**)。原画村のオリジナル画像は確認画面で選択 (multipart)
    - **backend**: situation/me の participate (selectableCharachipList = キャラセットごとの**空きキャラのみ**) / skillRequest (selectableSkillList + 現在の希望) を additive 拡張。`POST participate-confirm` (検証のみ 204) / `POST participate` (multipart、validator + `VillageCoordinator` 流用、IP 記録維持)
    - **意図的差異**: 入村パスワード入力は常時表示 (`joinPassword` はマスク済みで有無を露出しないため)
    - **共有 DB**: REST 実測でユーザー `e2ejoin01` を村 2 に参加させたまま残置 (退村実装後に外せる)。e2e は確認画面まで (実入村しない = DB 非汚染)
    - e2e 1 件追加 (全 **60 件**)。pr-reviewer 2 巡 (should 1 = 共有 input クラス反映、`.reviews/PR-76.md`)
  - **step-8.5 完了 ✅ (#75)**: アクション発言。`ActionPanel` (「{表示名}は、」+ 対象 選択しない/全員/参加者 + 本文 + 制限出し分け) + `POST action-confirm`/`action` (ActionFormValidator + MessageCoordinator 流用)。確認 → 投稿フローは say と共用 (プレビュー state に kind 導入)。SSR の action 系 CSRF 非対称は v1 チェーンで解消。e2e 1 件追加 (全 **59 件**)。pr-reviewer 1 巡 should 0 (`.reviews/PR-75.md`)
  - **step-8.4 完了 ✅ (#74)**: 発言投稿。種別ラジオ (selectable のみ・背景色/既定表情の自動切替) / 秘話相手 / 装飾ツールバー (選択範囲を [[tag]] で囲む + ランダム機能挿入) / 表情選択 / **残数・文字数・行数のリアルタイム表示 (超過は入力可・確認のみ無効)** / **確認 → 投稿の 2 段フロー** (発言位置に黄枠プレビュー、種別別ボタンラベル、連打ガード) / 返信・秘話返信の接続
    - **backend**: situation/me の say を拡張 (selectableMessageTypeList + restrict + 秘話宛先 / selectableCharaImageList / defaultMessageTypeCode)。`POST say-confirm` / `POST say` (要認証、`SayFormValidator` + `MessageCoordinator` 流用、**IP 記録維持**)。なりすまし不可 (myself は principal からのみ解決)、可否の権威検証は domain `assertSay`
    - **意図的な暫定差異**: 装飾ツールバーは legacy では表示設定で既定非表示 → 表示設定実装 (8.13) までは常時表示。表情/秘話相手の画像選択モーダルは select で代替。`canAutoRefresh` 相当は自動リロード実装時 (8.13) に対応
    - e2e 2 件追加 (全 **58 件** green)。pr-reviewer 2 巡 (should 1 = 連打ガード反映、`.reviews/PR-74.md`)
  - **step-8.3 完了 ✅ (#73)**: 村画面の発言抽出。抽出モーダル (種別 12 種/発言者/宛先/キーワード/ショートカット 囁き・共鳴・恋人・念話・自分宛・通知キーワード) + **フィルタ状態は URL searchParams が正本** (`fpid`/`typ`/`kwd`/`tpid`/`spl`、日付ナビ遷移でも引き継ぐ) + ハッシュタグクリック連動 + ネタバレ防止トグル (spoiled 種別/playerName/役職名/能力欄/足音詳細を隠す)。footer「抽出」有効化 (適用中は「抽出中」塗り)
    - backend は `VillageSituationView.participantList` (発言者・宛先の素材、`VillageFilterParticipantContent` 流用) と `MyselfView.notificationKeyword` の additive 追加のみ。絞り込みは 8.2 の `VillageMessageSearchRequest` をそのまま使用
    - **components/ui 拡充**: `TextButton` (リンク風アクション) / `ButtonCheckboxGroup` (ButtonRadioGroup outline の複数選択版)
    - フィルタ変更時は先頭ページにリセット (legacy parity、空ページ防止。pr-reviewer should 反映)
    - e2e 2 件追加 (全 **56 件** green)。pr-reviewer 2 巡 (`.reviews/PR-73.md`)
  - **step-8.2 完了 ✅ (#72)**: 村画面メッセージ表示。発言ログ全種別描画 / ページング / アンカーのインライン展開 / 一覧末尾アナウンス / settled の参加者正体公開 / 新着発言検知 (footer 更新ボタン点滅) / 年齢制限確認モーダル (localStorage) / 通知用パーマリンク `/village/{id}/message?anchors=`
    - **backend**: `VillageMessageRestController` (messages / latest-datetime / anchor / anchors / participants、全 permitAll + JWT 視点反映)。検索条件は `VillageMessageSearchRequest` → **SSR の `VillageGetMessageListForm.toMessageQuery` に委譲** (種別展開・全員選択空化を二重実装しない)。応答は既存 `VillageMessageListContent` 等を直接返す。**可視性マスク 2 層 (mask.md §2) は既存 `MessageService` をそのまま通る** (実測: 匿名 5 件/admin 11 件)。participants の settled ガードは controller 責務
    - **frontend**: `messageHtml.ts` = legacy `escapeAndSetAnchor` の忠実移植 (**エスケープ → 固定マークアップ置換の順序が安全性の正本**。dangerouslySetInnerHTML はこの変換出力のみ)。`MessageCard` (種別バリアント + アンカー展開トグル + netabare/tp クリック解除)。配色は `components/ui/messageStyles.ts` に集約し **SkillMessage と共有 (5.1 の暫定枠線置換が完了)**。吹き出しには `message-normal` 等のセマンティッククラスを併記 (本文内リンク色 CSS と e2e が参照)
    - 動作確認用に村 5 へ装飾フルセットの村建て発言を投稿済み (SSR `creator-say` を curl で。say 系は CSRF 除外だが creator-say は要 CSRF トークン)
    - e2e 3 件追加 (全 **54 件** green)。pr-reviewer 2 巡 (should 1 = `/user/` リンク規約違反を反映、2 巡目 0 件、`.reviews/PR-72.md`)
  - **step-8.1 完了 ✅ (#71)**: 村画面ベース。`/village/:id` (+ `/day/:day`) で レイアウト / 日付ナビ / 状況サマリ 4 タブ (部屋割り・参加者・投票・足音) / 30 秒ポーリング / 残り時間カウントダウン。**二層 situation + `isViewableSpoilerContent` をマスク基盤として確立**
    - **backend**: `GET /api/v1/villages/{id}` (村詳細、joinPassword 除外) / `GET .../situation?day=` (状況、permitAll だが JWT があれば視点をマスクに反映) / `GET .../situation/me?day=` (capability フラグのみ、認証必須、**各操作の入力候補はその操作のサブ step で追加**) / `POST .../update` (ポーリング = 最終アクセス更新 + daychange 駆動、permitAll、応答 `{latestDay}` のみ)。マスクは既存 view (`VillageRoomAssignedRow.isViewableMemberSkill`) と domain service (足音/能力欄/投票隠蔽) を**そのまま流用**し重複実装しない。`VillageSituationView` は `VillageContent` の実フィールドが正本 (village-base.md 方針)
    - **operationId 衝突の教訓 (今後の REST 化で要注意)**: メソッド名が他 Controller と単純名衝突すると SpringDoc の自動連番 (`detail_1`/`detail_2`) が探索順で揺れて spec の不要差分になる → **新エンドポイントは `@Operation(operationId=...)` を明示** (7.5 のスキーマ名衝突の operationId 版、`.reviews/PR-71.md`)
    - **frontend**: `features/village/` (hooks: useVillage / useVillageSituation / useMyVillageSituation / useVillagePolling) + `routes/village/` (colocation)。**`apiFetch` に 401→refresh→リトライ (single-flight) を追加** (村画面は長時間滞在のため access 15 分の自動更新が必須。refresh rotation の二重提示 = 漏洩検知全失効を防ぐため並行でも 1 回。`/auth/me` も対象)。セッション失効検知は situation/me の 401 (refresh 失敗後) → 「要再ログイン」表示
    - **実バグの教訓: fetch の response body は必ず消費する**。`refreshTokens()` が body を読まず Playwright networkidle が永遠に来ず既存 e2e 14 件がタイムアウトした (`res.text()` で解消)
    - **`:8091` (bk) は旧コードベースで部屋割りの役職名 span を出力しない**。移行正本は本リポジトリの SSR (8089) で、admin / 墓下公開視点では役職名を出す。視覚比較で差が出たらまず 8089 SSR と突合する
    - **共有 DB に村 5「step8 動作確認用の村」(進行中・3日目) を残置**: master で作成 → debug 機能 (人数分入村 / 日付を進める) で進行。**e2e の進行中村テストが利用** (村の状態は API から動的に探し、無ければ skip)。検証が終わったら廃村可。村作成 → debug 進行のフローが Step 8 の動作確認の基本手段
    - 申し送り: 状況パネルの「固定」(bottom-fix) と表示設定 Cookie → 8.13 / ネタバレ防止トグル → 8.3 / 村情報モーダル (日付ナビの「情報」リンク有効化) → 8.14 / 年齢制限確認モーダル → 8.2 / メッセージエリアはプレースホルダー (8.2)。Twitter 共有は widget script を読まず共有 URL ボタンで代替 (意図的差異)
    - e2e 5 件追加 (全 **51 件** green)。pr-reviewer 2 巡 (2 巡目 should 0 で打ち切り、`.reviews/PR-71.md`)
  - **step-7.5 完了 ✅ (#70)**: 設定流用 (divert) REST + React 化。`/new-village` 最上部に設定流用セクション (終了村セレクト + 流用ボタン)
    - **backend**: `GET /api/v1/villages/{id}/setting` 新設 (permitAll、404=not_found)。`VillageSettingView` はドメイン `VillageSetting` から **joinPassword のみ除外**して直返し。流用候補一覧は既存 `GET /api/v1/villages?status=...&order=asc` 流用
    - **spec スキーマ名衝突の実バグを修正 (今後の REST 化で要注意)**: ネスト DTO とドメインのネスト型が**単純名衝突すると SpringDoc が片方の定義で上書き**する (response スキーマが request の形になっていた)。`VillageCreateRequest` のネスト DTO に `@Schema(name = "VillageCreateRequestXxx")` を付与して解消。**新 DTO 追加時は既存スキーマ名との単純名衝突に注意**
    - **spec の決定性を確保 (api-drift 誤検知の根治・重要)**: (1) Kotlin の `isXxx()` 由来プロパティはリフレクション列挙順 (JVM ごとに不定) で spec に並ぶ → `springdoc.writer-with-order-by-keys: true` でキー順固定。(2) `Skill ⇄ SkillHistories ⇄ SkillHistory` の自己参照サイクルは SpringDoc が $ref を張れず解決順依存の退化ノードを出す → `WolfMansionOpenApiConfig` の OpenApiCustomizer で `Skill.histories` を spec から除去しサイクルを断つ (シリアライズ不変)。**ドメイン直返しで自己参照サイクルを持つ型を spec に載せる場合は同様の対処が要る**
    - **frontend**: `divert.ts` = `NewVillageForm.override` の忠実な移植 (流用しない項目=村名/開始日時/入村パスワード/役職希望/ダミーキャラ名・略称・発言は既定値に戻る。欠落行は発言制限=無制限/配分=min0・max なし・出現割合0・転生50)。`CharachipSection` に **`charaSyncKey`** を追加し、流用 reset 後のダミーキャラ由来項目 (画像・名前・略称・既定発言) を confirm なしで再同期 + 部分読み込み中の誤補正を isLoading ガードで防止
    - **意図的な legacy 差異**: legacy はロード時に先頭キャラ (ゲルト) の既定発言が残留する quirk (選択ダミーキャラが別キャラでもゲルトの発言が入る) → React 版は選択キャラの既定発言のみ自動入力
    - **共有 DB に村 ID 4「流用動作確認用の村」(CANCEL) を残置**: 動作確認用に master で作成→廃村化したもの。**e2e の流用テストが利用** (流用候補ゼロの DB では skip する作り)。ローカル `master`/`testuser` は ROLE_ADMIN で常に村建て可 (ただし creatorIsProducer=true はダミー参加が player 1=master のため作成不可)
    - e2e 1 件追加 (全 **46 件** green)。`:8091` 突合で全項目一致確認。pr-reviewer 3巡 (1巡目 should 0 / CI fail 対応 / 2巡目 should 1 反映 / 3巡目 should 0 で打ち切り、`.reviews/PR-70.md`)
  - **step-7.4 完了 ✅ (#69)**: 確認モーダル→村作成 REST 化。`/new-village` で実際に村が作れる ("確認画面へ" 有効化)
    - **backend**: `POST /api/v1/villages` (**multipart/form-data**、要認証) 新設。JSON part (`VillageCreateRequest`) + オリジナルダミー画像 part (`dummyCharaImage`、任意)。検証は **SSR と共通の `NewVillageFormValidator` を `toForm()` 変換後に流用** (重複実装しない)。フィールドエラーは `WolfMansionValidationException` → ProblemDetail の **`fieldErrors`** (`MethodArgumentNotValidException` 側も統一)。作成は既存 `VillageCoordinator.registerVillage` 流用
    - **作成 API は `VillageCreateResponse` (id のみ) を返す** (raw `Village` は `VillageSetting.joinPassword` 等のマスク対象を平文露出するため。REST API 設計方針: マスク要なら Response DTO。pr-reviewer 3巡目 should)。これにより `Village` ドメイン型は OpenAPI 応答スキーマから外れた
    - **闇鍋構造チェック (重要)**: REST 化で `campAllocationList` をクライアント構築にしたため、村人陣営/村人役職を欠くと validator の `first { }` が `NoSuchElementException` → 500 になる。`VillageCreateRequest.validateRandomOrganizationStructure()` で先回りして 400 に倒す (pr-reviewer 1巡目 should)
    - **frontend**: 「確認画面へ」= zod 検証 → **`ConfirmModal`** (設定一覧 + 発言プレビュー + 闇鍋編成/発言制限テーブルの読み取り表示、legacy `new-village-confirm.html` 相当) → 「作成」で multipart POST → `/village/{id}` 遷移 (村画面は Step 8 まで 404)。`lib/api` を **FormData (multipart) + `fieldErrors`** 対応に拡張。送信変換 `toCreateRequest` (空文字→null、固定編成「N人：」除去)
    - **オリジナル画像**: ファイル入力を**元フォーム側**に追加 (確定方針: 確認モーダルはプレビューのみ)。`URL.createObjectURL`+revoke、0バイト/100KB をクライアントでも検証 (backend `validateOriginalImage` とパリティ)。**画像未選択での作成は backend が許容するためフロントも許容** (パリティ維持の意図的判断)。`components/ui/Modal` に `size="wide"` 追加
    - zod に **入村発言の trim 後検証** (全空白拒否、PR-68 申し送り対応) + **オリジナル時パスワード必須** 相関を追加
    - **e2e の実村作成は共有 DB 都合で見送り** (新規 signup ユーザーは終了村参加が無く村建て不可 = `assertCreateVillage` の最初のチェックで業務エラー → DB 非汚染で作成 API 接続を検証)。**実村作成→廃村 teardown は Step 8 (村画面) 完成後のシナリオ e2e で検討**。単体 8 + e2e 1 追加 (全 **45 件** green)。pr-reviewer 3巡 (各巡 should 反映、`.reviews/PR-69.md`)
  - **step-7.3 完了 ✅ (#68)**: キャラチップ選択 React 化。`/new-village` にキャラチップ設定セクション (基本設定と詳細ルール設定の間) — キャラチップ利用切替 / キャラセット複数選択 (`MultiSelect`、ラベル「名前（作者様作）」) / ダミーキャラ選択 / キャラ名・略称 / 入村・1日目発言 (キャラ画像プレビュー)。**backend 変更なし**
    - **キャラ情報は既存 `GET /api/v1/charachips/{id}` を選択 id ごとに取得し frontend で結合** (`useCharachipDetails` = react-query `useQueries` + combine)。**legacy `GET /getCharacterList` の REST 化は不要になった** (new-village.md の要検討事項が解消)
    - キャラ連動 (legacy `replaceCharaSet`/`replaceDummyChara` 相当): ダミーキャラ変更で画像・名前・略称反映 / 既定発言は空なら自動入力・入力済みはユーザー操作時のみ confirm / **複数キャラセット選択時は自動入力しない**。初期表示 vs 手動変更は `manualChangeRef` で区別
    - オリジナル画像選択時は案内文 + placeholder 60×60 (`placeholder.png` を frontend `public/app/images/` に移管)。**ダミー画像のファイル入力は 7.4 で元フォームに追加** (確認モーダルはプレビューのみ、new-village.md 確定方針)
    - zod: キャラ名 1〜40 / 略称 1 字 / 入村発言 1〜400 必須 / 1日目 max 400 / **発言 20 行上限** / キャラチップ利用時キャラセット ≥1。**`.max(400)` は backend `validateMessage` と完全一致** (改行 = 1 文字カウント。「改行除外にすべき」というレビュー指摘は検証で誤指摘と確定、`.reviews/PR-68.md` 2巡目)
    - **7.4 への申し送り**: 入村発言の trim 後検証 (全空白 `" "` を backend は拒否、client は通す) を submit 実装時に揃える
    - e2e 3件追加 (全 **44 件** green)。pr-reviewer 3巡 (3巡目 should 0 で打ち切り、`.reviews/PR-68.md`)
  - **step-7.2 完了 ✅ (#67)**: 発言制限設定 React 化。`/new-village` に 3 テーブル追加 — 役職別 (通常発言・全役職123行、行順は `GET /api/v1/skills` のまま) / 発言種別 (囁き・共鳴・恋人・念話) / RP (アクション)。**backend 変更なし**
    - チェックで length/count を有効化 (未チェック = 無制限扱いで disabled + 背景 #aaaaaa)。「村人の設定を全てにコピー」は役職別テーブル限定 (legacy `#say-restriction` と同挙動)。コピーは `setValue(shouldDirty)` + `trigger("sayRestrictList")` でまとめて再検証 (コピー先にもエラーを出す、pr-reviewer should 反映)
    - zod は制限ありの行のみ 0〜400 / 0〜100 を検証 (`refineSayRestrict`、文言はサーバー `NewVillageForm.validator.sayRestrictList` と同一)。既定値 (restrict=false / 400 * 20) と発言種別コード (`WEREWOLF_SAY`/`MASON_SAY`/`LOVERS_SAY`/`TELEPATHY`/`ACTION`) は `NewVillageForm.initialize()` と一致
    - **`components/ui/Button` を variant (色) × size (padding) に分離**: info variant (#3498db) + xs サイズ (px-[5px] py-[1px]) 追加。LinkButton / AnchorButton も同構成
    - 行ラベルは静的 prop / 値はフォーム state (闇鍋テーブルと同じ分離構造 → 7.5 divert 時は同様に reset で値のみ流し込み)
    - e2e 3件追加 (全 **41 件** green)。発言制限ラベルの `getByLabel` は **`{ exact: true }` 必須** (「占い師」が「花占い師」に部分一致する実害あり)。pr-reviewer 2巡 (2巡目 should 0 で打ち切り、`.reviews/PR-67.md`)
  - **step-7.1 完了 ✅ (#66)**: 村作成フォーム本体 React 化 (`/new-village`、RequireAuth)。基本設定 / 詳細ルール (固定⇄闇鍋編成切替 + 闇鍋配分テーブル) / 見学・閲覧 / 身内村 / 特殊ルール / RP村 の入力 + zod クライアント検証 (**正本はサーバー検証**、相関は定員≧最少・間隔1分〜72h・開始日時14日以内のみ client 再現)。7.2 発言制限・7.3 キャラチップのセクションは未表示、**「確認画面へ」ボタンは 7.4 まで disabled** (作成 REST API も 7.4 で新設)
    - **backend 変更は `SimpleSkillView` に `requestable`/`revivable` 追加のみ** (闇鍋配分の既定値組み立てに必要な役職属性。`pnpm gen:api` 済)
    - **既定値は `NewVillageForm.initialize()` と同一を frontend で再現** (`routes/new-village/schema.ts` の `createDefaultValues`)。`:8091` と実測突合で完全一致 (闇鍋145行・村人 min1・暴走トラック max0・恋人転生0・編成テンプレ13行)。既定編成テンプレは frontend 定数 (正本 `VillageOrganize.defaultFixedOrganization`、`organization.ts`)。固定編成は「N人：」プレフィックス込みで編集し送信時に除去
    - **`lib/zodResolver` をネストエラー対応に拡張** (`campAllocationList[i].skillAllocation[j].*` の配列パス)。`@hookform/resolvers` は引き続き不採用
    - **components/ui 拡充**: `ButtonRadioGroup` に **outline variant** (new-village の `btn-dark-success`=緑枠+選択時緑地。village-list の塗り `btn-success` とは別物と実測判明)、`Input.selectClass`、`FormRow` label の ReactNode 化 (変更不可 `*` 印)、`SubHeading` に `weight` prop
    - **FAQ に「村を建てられないのですが」追加** (2つ目・ユーザー指示)。村作成3条件 = 終了村に1戦以上参加 / 入村制限なし / 自分が建てた村が進行中でない (`Player.isAvailableCreateVillage`)。legacy faq.html は据置 (ユーザー判断)
    - **7.5 (流用 divert) 実装時の注意**: 闇鍋テーブルは「ラベル=静的 prop / 値=フォーム state」の分離構造。`reset()` で値だけ流し込んでもラベルは変わらない (`.reviews/PR-66.md` 2巡目 nit)。配分セルのエラー表示 e2e は 7.4 (submit 接続) で追加検討
    - e2e 4件追加 (全 **38 件** green)。pr-reviewer 2巡 (2巡目 should 0 で打ち切り、`.reviews/PR-66.md`)
  - **step-6 完了 ✅ (#65)**: ランダムキーワード React 化 (`/random-message` 一覧 / `/new-random-keyword` 作成 / `/random-keyword/:id` 編集・削除)。**認証付き CRUD の最初のパターンを確立**
    - **backend**: `GET /api/v1/random-keywords(?q=)` / `GET /{id}` = permitAll、`POST`/`PUT /{id}`/`DELETE /{id}` = **要認証** (legacy の permitAll 書き込みを random-keyword.md の確定方針どおり厳格化)。ドメイン `RandomKeywords`/`RandomKeyword` を直接返す。絞り込みは API 側 (`RandomKeywordSearchRequest` + `RandomKeywords.filterBy`、pr-reviewer 指摘で client filter から移行)
    - **実バグ 2 件を実測で発見・対処 (今後の REST 化で要注意)**: (1) **型引数の `@Size` (`List<@Size(...) String>`) は実行時検証されない** (Kotlin は型アノテーションを既定でバイトコードに出さない) → コレクション要素の制約はコード検証 (`toModel`/`toContents`) で行い、spec へは `@ArraySchema` で出す。(2) **複数テーブル書き込みにトランザクションが無く部分挿入が残った** → `RandomKeywordService` の書き込み 3 メソッドに `@Transactional` 付与 (単一サービス内のため coordinator は作らない判断、レビュー合意)
    - **制約の単一ソース**: backend `RandomKeywordPolicy` → Jakarta `@Size`/`@Pattern` + `@ArraySchema` → spec → `gen-api.mjs` 抽出 (`RANDOM_KEYWORD_*` 定数) → zod。NG ワード (or/who)・行重複・要素長 1〜20 は backend/frontend 双方で検証
    - **components/ui 拡充**: `Input.tsx` 新設 (`inputClass`/`inlineInputClass`/`textareaClass`、auth/ui は再エクスポートに一本化)、`Button` に **danger variant** (#e74c3c) + `cursor-pointer text-[13px]`、`Form` に `fieldErrorClass`/`formErrorClass` 移設。`lib/api` の `apiFetch` を PUT/DELETE 対応
    - 編集ページは閲覧公開のまま (未ログインにはログイン誘導表示、書き込みは 401→メッセージ)。作成ページは `RequireAuth`。クリップボードは `navigator.clipboard`
    - e2e 4 件追加 (全 **34 件** green)。`:8091` 実測比較済 (テーブル 10.32px/mb 21px/th 左寄せ下揃え、ボタン 13px・padding 6px 9px)。pr-reviewer 2 巡 (2 巡目 should 0 で打ち切り、`.reviews/PR-65.md`)
  - **step-3.0 完了 ✅ (#51)**: DBFlute を tracking 中の engine 1.3.1 へ**全再生成** (旧 engine 製のコミット済み生成コードを 1.3.1 スタイル `DBDef.of`/簡素 Javadoc に統一、版差ドリフト解消) + **REFRESH_TOKEN テーブル追加** (PLAYER への FK=RESTRICT、`IX_REFRESH_TOKEN_EXPIRES` 索引、共通カラム準拠)。JWT の generate ノイズ (162 ファイル) を auth コードから分離するため独立 PR にした
  - **step-3.1 完了 ✅ (#52)**: backend JWT 認証基盤 + `/api/v1/auth/{login,refresh,logout,me}`。`WolfMansionWebSecurityConfig` を 2 チェーン化 (`/api/v1/**` stateless JWT @Order(1) + 既存 session @Order(2) 温存)。`JwtTokenProvider`(HS256 明示)/`JwtAuthenticationFilter`(access Cookie→SecurityContext)/`JwtAuthenticationEntryPoint`(401 ProblemDetail)/`AuthCookieFactory`(access Path=/ 15分・refresh Path=`<ctx>/api/v1/auth` 14日・HttpOnly/SameSite=Lax/Secure は `jwt.cookie-secure` 連動)。refresh は不透明乱数+DB は SHA-256 ハッシュのみ、使い捨て rotation + 漏洩検知(使用済み再提示で当該プレイヤー全失効、`noRollbackFor` で失効をコミット)。ProblemDetail を `@RestControllerAdvice(annotations=[RestController])` で統一(SSR 非干渉)。期限切れ refresh は login/refresh 時に掃除。単体 13 + curl 回帰 + security-review クリア
  - **step-3.2 完了 ✅ (#53)**: signup(`POST /api/v1/auth/signup` 匿名・自動ログイン) / change-password(`POST /api/v1/auth/password` 認証必須・確認一致) の REST 化 + ログインレート制限。パスワードポリシー緩和は `fw/security/PasswordPolicy`(3〜60字・`[\x21-\x7E]+`)で共有。`id_register` cooldown Cookie は `AuthCookieFactory.idRegisterCookie`(Path=`<ctx>/api/v1/auth`)。**レート制限は DB 採用**: `LOGIN_FAILURE` テーブル(FKなし)+ `LoginRateLimiter`(domain/service/auth)で 2軸(account 5/IP 30・window 15分、`login-rate-limit.*` で調整可)、超過 429(`WolfMansionTooManyRequestsException`→`RestApiExceptionHandler`)。掃除はオポチュニスティック(失敗記録時に窓外削除+成功時アカウント単位 reset)で有界。**閾値到達で管理者 Discord 通知**(`DiscordRepository.postToMaster`、村非依存・短タイムアウト・ベストエフォート、キー×窓ごと概ね1回)。**`getIpAddress` を CF-Connecting-IP 優先に統一修正**(XFF 詐称でレート制限/access-info を回避させない、`AccessContextInterceptor`)。signup の IP volumetric 制限はアプリ層では行わず Cloudflare edge + cookie cooldown に委譲(根拠コメント明記)。`login` は `recordFailure` をコミットさせるため `noRollbackFor=[WolfMansionAuthException, WolfMansionTooManyRequestsException]`。単体 40(LoginRateLimiterTest 7 + AuthCoordinatorTest 14)+ curl 回帰(CF-IP キー実証含む)+ pr-reviewer 2巡(6指摘全反映)クリア
  - **step-3.3 完了 ✅ (#54)**: frontend(RR v7) 認証フローを CSR で実装 (03-auth.md の SSR/CSR 境界)。`app/lib/api.ts`(fetch ラッパ・`credentials:include`・ProblemDetail→`ApiError`)/ `app/lib/zodResolver.ts`(自前最小リゾルバ、`@hookform/resolvers` 不追加)/ `features/auth`(schema(zod+暫定PasswordPolicy定数) / api / `useMe`(react-query・401→null・**client-only**) / `RequireAuth`(未認証は `/login?returnTo`)/ errorMessage / returnTo(オープンリダイレクト防止))。ルート `/login` `/signup` `/mypage`(保護) `/change-password`(保護) + home 改修。dev は **Vite proxy**(`/wolf-mansion`→backend, rewrite無し)で吸収・CORS不採用、`optimizeDeps.include`(react-hook-form/zod)で初回フォーム遷移の再最適化リロード排除。e2e: signup→me→logout→login + 未認証/mypageリダイレクト。**pr-review-toolkit:code-reviewer 3巡**(`.reviews/PR-54.md`)で実バグ2件修正: **logout のナビ競合**(RequireAuth 配下で `setMe(null)`→`/login` リダイレクトが `navigate("/")` と競合 → 先に遷移→`invalidateMe`)/ **returnTo 二重デコード**(`useSearchParams().get()` が既decode済なのに再decode)。backend 無変更
  - **step-3.4 完了 ✅ (#55)**: OpenAPI→TS 型生成パイプライン + CI drift 検知。backend に **SpringDoc** (`springdoc-openapi-starter-webmvc-api:2.8.17`、swagger-ui 無し) 導入、`springdoc.paths-to-match=/api/v1/**` で**新 REST 面のみ** spec 化 (旧 SSR/公開 API 除外)、production は `api-docs.enabled=false` で**本番非公開**。`SignupRequest`/`PasswordChangeRequest` を Hibernate `@Length` → Jakarta `@Size` に変更 (SpringDoc が minLength/maxLength を出力するようにし spec を制約の単一ソース化)。frontend は `scripts/gen-api.mjs` (`/v3/api-docs`→`app/api/{openapi.json,types.ts,constants.ts}`、**`servers` 除去でポート非依存**、必須キー欠落で throw guard) + `pnpm gen:api`/`check:api`。3.3 の暫定**手書き `MeResponse` 型→生成型** (`components["schemas"]["MeResponse"]`)、**zod の password/userId 定数→`~/api/constants` (生成定数)** に置換 (spec の非アンカー pattern は zod 用に `^…$` 付与)。CI `.github/workflows/api-drift.yml` (PR で MySQL+backend 起動→再生成→`git diff --exit-code`、**空 DB で起動到達可**を実証、`git add -N` で未追跡も検知)。**実 CI run が pass 済** (3m20s)。pr-review-toolkit:code-reviewer 3巡 (`.reviews/PR-55.md`): Critical 1 (servers 誤ポートで drift 恒常 fail) + should 1 (undefined 定数 黙生成) + nit 2 全反映。**Step 3 はこれで完了**
  - **step-4.1 完了 ✅ (#56)**: frontend ホーム (`/`) + 共通フッター + **村一覧 REST** + **context-path rename** (rename を本 step に同梱)。
    - **API 設計 (ユーザー指摘で確定・重要)**: 画面固有 API は作らない。**村一覧 API** `GET /api/v1/villages?status=<code>&status=<code>...` (permitAll、status は **village_status の code 配列**=`IN_PREPARATION`/`IN_PROGRESS`/`EPILOGUE`/`COMPLETED`/`CANCEL`、省略=全件、不正 code は 400)。`VillageRestController` (既存 SSR `VillageController` と名前衝突回避)。レスポンス `SimpleVillageView` は **整形済みでなく、できる限り domain `Village` に近い生データ** を返す (`status: VillageStatus`、`setting:{personMin,personMax,tags: VillageTag[]}`、参加/見学 count)。**派生・整形 (statusName/isPrologue/タグ絞り込み色付け/村番号0埋め/参加人数文字列) は一切作らず frontend の責務**。VillageStatus の `isXxx()` 述語が JSON に出るのは domain 構造の一部なので許容 (ユーザー判断)。村作成可否は **`MeResponse.canCreateVillage`** (player 由来、`AuthController` が PlayerService で埋める)
    - **context-path rename 確定**: backend `/wolf-mansion` → **`/wolf-mansion-api`** (`application{,-production,-playground}.yml` / `PlayerController.PATH_ID_REGISTER`)。frontend は **`/wolf-mansion`** で配信 (RR `basename` + Vite `base`、**末尾スラッシュ無し**: 付けると `navigate("/")` が空ページ)。proxy `/wolf-mansion-api`→backend、`lib/api` API_BASE / `app.css` font / `gen-api.mjs` / CI api-drift / e2e config も `/wolf-mansion-api` に
    - **frontend 構成 確定 (ユーザー指摘)**: **colocation** 採用 — `routes/` = 画面 (画面専用 component/logic は `routes/<screen>/` にコロケート、本体は `route.tsx`、`+types` は `./+types/route`)。`features/<domain>/` = 複数画面で共有する機能。**village は用途で分割**: `features/villages/` (複数形) = 村一覧ドメイン / `features/village/` (単数形) = 今後の村詳細ドメイン。正本は [`doc/migration/04-frontend.md`](../doc/migration/04-frontend.md) 「ディレクトリ構成 / 設計方針」+ `frontend/README.md`
    - **忠実再現 (`:8091` 実測比較)**: 地色 #222 / 帯 #333 bg-clip-content 15px インセット / コンテナ幅 750·970·1170 / トップ画像 15px インセット / タイルは上揃え 15px + 10px 等間隔 / 村テーブル列幅 8.33%·残·16.67%·16.67% / favicon (step-3.6 で frontend 配信へ移管済) / **Modal はダークテーマ #303030・白文字・リンク #0ce3ac・ボタン #00bc8c**。`<title>`/OGP も一致
    - 検証: backend test/ktlint、frontend typecheck/lint/format/build、e2e 5/5、CI drift pass (3m21s) すべて green
  - **step-3.6 完了 ✅ (#57)**: 認証4画面 (login/signup/mypage/change-password) を `:8091` 基準で忠実再現 (3.3 は旧方針で素朴に作成していた) + **共通ヘッダー** + **静的アセットの frontend 移管**。
    - **忠実再現 (`:8091` 実測スクショ比較で確定)**: 認証画面は**ダークテーマ** (body bg #222・白文字)。Bootstrap `.form-horizontal` (ラベル左 / 入力右、緑 `pull-right` 送信ボタン #00bc8c) を再現。`<h1 class="h4">` 見出し・footer・title-pattern「WOLF MANSION | <page>」+ サイト共通 OGP も一致。`features/auth/ui` を明色カード → `AuthLayout`(ダーク+共通ヘッダー/フッター) + `FormRow`/`FormActions` に刷新。挙動 (zod/returnTo/RequireAuth/認証フロー) は不変
    - **共通ヘッダー `components/layout/Header`** 新規 (`layout/header.html` 相当の small バナー top-small.jpg + ロゴ + ログイン中 `ユーザID:`)。**ホーム以外**の画面で使う (home は大 top.jpg を route 内に直書き)
    - **静的アセット移管 (ユーザー指示で前倒し・重要)**: トップ画像/ロゴフォント/favicon/OGP 画像を backend (/wolf-mansion-api) proxy 依存 → **frontend `public/app/` 配下** へ移し `/wolf-mansion` で自己完結配信。`lib/api` に **`assetUrl`** (`import.meta.env.BASE_URL` ベース) を追加し静的アセット参照を `legacyUrl`→`assetUrl` に (legacyUrl は**未移行 SSR ページ専用**に限定)。OGP 絶対 URL も `/wolf-mansion-api/`→`/wolf-mansion/` に。Step 10/11 への「静的アセット移管」繰り越しは**解消済**
    - **意図的な :8091 差異**: change-password ボタンは :8091 の legacy 誤り「ログイン」でなく「変更する」/ signup ボタンは「作成」(:8091 準拠)・説明文の文字数は実ポリシー (生成定数 password 3〜60) / signup の title=見出しを「ID作成」に統一 (:8091 は title「ID登録」と割れている)
    - **`/mypage` follow-up (ユーザー指示)**: 暫定 mypage を最終的に `/user/{自分の名前}` (プロフィール/戦績) と**同じ動作**に統合する。**Step 9 (プロフィール系) で対応**。doc: [`screens/player-profile.md`](../doc/migration/screens/player-profile.md) follow-up + 08-step-plan.md Step 9 + mypage.tsx の `TODO(Step 9)`
    - **e2e**: signup ボタン rename (登録→作成) と共通ヘッダー追加に伴う mypage の `ユーザID:` 確認に追従。**5/5 green** (typecheck/lint/format/build も green)。pr-review-toolkit:code-reviewer 4巡 (`.reviews/PR-57.md` は削除済): should 1 反映 / nit 2 理由付き不採用 / asset 移管分も追加指摘なし
  - **step-4.2 完了 ✅ (#58)**: 村一覧画面 (`/village-list`) の検索/絞り込み + 全村一覧。
    - **backend (ユーザーレビューで設計を作り直し・重要)**: 検索 GET は個別 `@RequestParam` でなく **`VillageSearchRequest` (`api/village/request`)** に集約し `toQuery()` で `VillageQuery` 変換を内包 (`@ParameterObject` で spec は param 展開、Controller は1行)。並び順を **API 側**で指定可能化 (`VillageQuery.isDescending` + `order` param、既定 desc)。**legacy は home=昇順 (IndexContent) / 村一覧=降順 (VillageListContent.reversed) と判明**したため home は `order:"asc"`・村一覧は既定 desc。検索候補は `/api/village-list` 流用をやめ **`GET /api/v1/charachips` / `GET /api/v1/skills`** を新設 (`SimpleCharachipView{id,name}` / `SimpleSkillView{code,name,shortName}`、security に 2 GET permitAll 追加)。`status` と `skill` は既存ドメイン挙動どおり排他 (skill 指定時はエピローグ以降)
    - **frontend (ui コンポーネント化・重要)**: `components/ui/` に **Button / Heading / Form(FormRow,FormActions) / CollapsiblePanel / MultiSelect / ButtonRadioGroup** を新設し、検索パネルを inline でなくコンポーネント組み立てに。**AuthLayout / auth 4画面も共通 ui に移行** (重複していた form-horizontal/button のクラス文字列を排除、Heading で見出し共通化)。候補は `useCharachips`/`useSkills` で別 API から取得。**村名リンクは未実装でも SPA URL** (RR `<Link to="/village/{id}">`、Step 8 まで 404 だが**ユーザー指示で確定**)。home の村一覧タイルも React ルート化
    - **コメント・実装規約 (ユーザー指摘・再発防止)**: **`CLAUDE.md`「コメント・実装の規約」を新設** — コメントに **step 番号や「既存再現 (`:8091`/`相当`/`legacy`)」を書かない**、UI は `components/ui` 化、GET は Request クラス、一覧の並び順/絞り込みは **API 側**、未移行リンクも可能なら SPA URL。memory にも保存済
    - 検証: backend ktlintCheck test (`VillageSearchRequestTest`) / frontend typecheck・lint・format・build・gen:api / e2e 全10件 green。pr-review-toolkit:code-reviewer は初版3巡 + rework3巡
  - **step-4.3 完了 ✅ (#59)**: intro 画面 (`/intro`) React 化。静的ページ (API/認証不要)。
    - **MessageBubble / SystemMessage コンポーネント**: Thymeleaf `flagment-message` フラグメントの React 化。キャラ吹き出し (外部画像 `wolfort.dev`) + 左右反転 + メッセージ種別色分け (normal/werewolf/grave/public-system/private-system/creator)。`font-family: sans-serif` (Inter でなくシステムフォント)、`margin-bottom: 20px` で `:8091` 実測値と一致
    - **UI コンポーネント追加**: `SubHeading` (h2 セクション見出し)、`LinkButton` / `AnchorButton` (ボタン風リンク。default variant = `#464545`、success = `#00bc8c`、`:8091` 実測の border 2px / padding 6px 9px に合わせ)
    - **リンク規約 (ユーザー指摘・再発防止)**: CLAUDE.md に「リンクの規約（最重要）」セクションを新設・昇格。**未移行でも移行予定のあるページは SPA URL** (`<Link>` / `<LinkButton>`)。`legacyUrl` は SPA 化予定が無いページのみ
    - **既存改善**: Footer のモーダル開閉ボタンに `cursor-pointer` 追加 / CollapsiblePanel ヘッダーに `cursor-pointer` 追加
    - intro 画像 (intro01〜04.png) を `frontend/public/app/images/intro/` に移管。ホームの intro リンクを `legacyUrl` → RR `<Link>` に変更
    - 検証: frontend typecheck・lint・format・build green / e2e 全12件 green (intro 2件追加)
  - **step-5.1 完了 ✅ (#60)**: 役職一覧画面 (`/skill`) React 化。
    - **backend**: `SimpleSkillView` に `campCode`/`campName`/`tags` を追加。`GET /api/v1/skills/search` (タグ/名前/村で絞り込み) 新設。`SkillSearchRequest` + `SkillSearchResponse`
    - **frontend**: `/skill` ルート。API の `SimpleSkillView` で陣営別グルーピング・名前表示。説明文は `descriptions.ts` (rule/skill.html から自動生成した `Record<code, DescriptionItem[]>`)。検索パネル (タグトグル/名前/村選択)、12種メッセージボックス (暫定色分け枠線。完全な見た目は Step 8.2 で村メッセージコンポーネント実装後に差し替え → 08-step-plan.md 繰り越し事項)
    - ホームの `/skill` リンクを legacyUrl → SPA URL に変更
    - e2e 3件追加 (全15件 green)
  - **step-5.2 完了 ✅ (#61)**: ルールページ (`/rule`) React 化。
    - **backend**: `GET /api/v1/rule/judges` 新設。`CountType` enum (`HUMAN`/`WOLF`/`NO_COUNT`)。`JudgeListResponse` は全役職を `groupBy` で1回走査して分類
    - **frontend**: `/rule` ルート。目次 (TOC) + 8 セクション (基本/事件簿村/詳細/役職詳細/占霊判定/ステータス/陣営・勝敗/部屋サイズ/その他)。役職詳細は `descriptions.ts` (step-5.1 パターン) を再利用。未実装役職も extraction スクリプトで自動抽出
    - **SkillMessage を `components/ui/` に昇格** (skill/rule 2ルートで共有)。通常/囁き/共鳴/恋人/念話/梟/墓下/見学/村建ての色定義を追加
    - **フォント**: Inter を除去し system sans-serif に (既存 `:8091` と一致)
    - ホームの `/rule` リンクを legacyUrl → SPA URL に変更。rule01.png を frontend/public に移管
    - e2e 3件追加 (全18件 green)
  - **step-5.3 完了 ✅ (#62)**: 情報ページ群 (`/about` `/faq` `/practice` `/announce`) React 化。完全静的・backend 変更なし。
    - **announce はデータ駆動**: `frontend/scripts/extract-announce-releases.mjs` で announce.html から `routes/announce/releases.ts` (156 エントリ、`ReleaseSegment[]` でテキスト/内部リンクを保持) を生成。スクリプトは生成後に oxfmt を実行し再生成 diff ゼロ (冪等)。新しいお知らせは releases.ts 先頭に手で追記する運用
    - **components/ui 拡充**: `MessageBubble` / `SystemMessage` を intro 配下→`components/ui/` に昇格 (practice と共有、`message-monologue` 追加・余白を原本どおり `ml-[5px]` 固定に修正)。`TextLink` / `ExternalLink` (本文中リンク)、`Divider` (セクション区切り hr、4ルートの重複を統一・原本実測 `my-[21px]` `#464545`。intro/practice の旧 `my-[10px] #333` はユーザー指摘で発覚) を新設。`Heading` に `as="h2"` 追加
    - **`:8091` computed style 実測合わせ**: ul は `pl-[20px]`・ネストは `list-[circle]`・FAQ 段落 `mb-[10.5px]`・`.faq .icon` (青 #3498db / 赤 #e74c3c の 20px 円)
    - ホームの About / Announce / FAQ / **キャラチップ一覧** タイルを legacyUrl → SPA URL に変更 (キャラチップは Step 5.4 移行予定のためリンク規約どおり。5.4 まで一時 404)
    - practice の解答トグルは `aria-expanded` 付き (レビュー指摘)。e2e 5件追加 (全23件 green)
  - **step-5.4 完了 ✅ (#63)**: キャラチップ一覧 (`/chara-group`) + 詳細 (`/chara-group/{id}`) React 化。
    - **backend**: `SimpleCharachipView` に designerName/charaNum/dummyImg を追加 (一覧用)。`GET /api/v1/charachips/{id}` はドメインモデル `Charachip` を直返し (画面専用レスポンス不使用)。`GET /api/v1/rooms?personNum=N` を新設 (部屋サイズ+番号リスト)。`RestApiExceptionHandler` に `ResponseStatusException` ハンドラ追加 (404→`not_found` コード)
    - **frontend**: `/chara-group` (一覧テーブル+製作者案内) / `/chara-group/:id` (作者情報+キャラ画像+部屋割り例テーブル)。部屋割りグリッドは charachip API + rooms API を別々に取得し frontend で組み立て
    - **API 設計方針 (ユーザー指摘で確定・重要)**: 画面専用の API やレスポンスを作らない。可能な限りドメインモデルをそのまま返す。隠すべき情報がある場合と大量取得で不要情報を削ぎ落とす場合のみ Response DTO を許可。複数のドメイン情報が必要な画面は個別 API を叩いて frontend で組み立てる。正本は [`doc/migration/01-overview.md`](../doc/migration/01-overview.md)「REST API 設計方針」
    - e2e 4件追加 (全27件 green)
  - **step-5.5 完了 ✅ (#64)**: エイプリル企画アーカイブ (`/archives/april-2025040{1,2}` + 新設 `/archives/april-20260401`) React 化。backend 変更なし。
    - **スレデータはスクリプト生成 (announce 方式)**: `frontend/scripts/extract-april-threads.mjs` が SSR テンプレートから `routes/archives/april-2025040{1,2}/posts.ts` を生成 (冪等・oxfmt 済)。レスアンカー (表示番号と DOM id の食い違いも保持)・SPA リンク・ログイン状態依存リンク (`authLinks`)・村一覧 (`villageList`)・村作成 (`createVillageLink`) を型付きセグメント/マーカーで抽出。AA テキストはブラウザの空白処理 (ASCII collapse・全角保持) を再現した形で保存
    - **`features/archives/`**: `AprilPost` 型 + `AprilThread` レンダラ。村一覧マーカーは `GET /api/v1/villages` (未終了・昇順、home と同条件) から組み立て、ログイン依存リンクは `useMe` で出し分け
    - **AA フォント**: `aahub_light` を legacy CSS の埋め込み base64 から `frontend/public/app/font/aahub_light.woff2` (44KB) に抽出、app.css に `@font-face` + `.font-aahub`
    - **ホームの 4/1 限定ランダム役職説明はアーカイブ化**: 全 **29 種** (docs の「28種」は誤記) を `routes/archives/april-20260401/descriptions.ts` に移植。SSR 側 (`IndexController` の `/archives/*`・`aprilFoolDescriptions`) は cutover まで温存
    - **PageLayout に `header` オプション** (アーカイブはバナー無しページ)、**`siteMeta()` のページ名省略対応** (省略時はサイト共通タイトル「WOLF MANSION 〜人狼館の事件簿村〜」)
    - **announce にお知らせ追記** (releases.ts 先頭に手追記の運用どおり、3 ページへのリンク付き)
    - **検証**: `:8091` とスレ本文 `innerText` を空白正規化比較で**完全一致** (2 ページとも)。e2e 3件追加 (全30件 green)。pr-reviewer 2巡 must/should 0 件 (**指摘 0 件の巡があれば打ち切ってよい運用に ship-issue skill を更新**)
  - **次の候補**: **Step 8 (村画面・最重量)**。19 サブ step (8.1〜8.19) に分割済み (08-step-plan.md の表参照)。Step 7 は全サブ step 完了 (7.1〜7.5 ✅)
  - **UI 忠実再現メモ (方針変更・重要)**: 「移行中は近似 → Step 12 で一括復元」は**廃止**。**各画面を移行する step 内で `:8091` 基準に忠実再現する** (レイアウト・色・余白・`<title>` / OGP / `<head>` メタ・共通ヘッダー含む)。Step 12 は純粋な視覚モダナイズ (刷新) のみ。08-step-plan.md「忠実再現は各画面 step で行う」が正本。~~3.3 の認証画面は旧方針で素朴に作ったため後日忠実再現が要る~~ → **step-3.6 (#57) で対応済**
  - **判断済**: context-path rename (`/wolf-mansion-api`) は **据置 → Step 3 先行** (cutover 前の別サブ step に後回し)。Step 3 は 4 分割 (3.1 JWT基盤 ✅ / 3.2 signup・password+レート制限 ✅ / 3.3 frontend+e2e / 3.4 OpenAPI→TS)
  - **未対応の follow-up (別 step 候補)**: `DiscordRepositoryImpl.post`/`postToWebhook` が素の `RestTemplate`(タイムアウト無制限)。`postToMaster` 同様に短タイムアウトを付けるとリスクが揃う(pr-reviewer nit、本 PR スコープ外)
  - **認証実装メモ (3.2 以降で踏襲)**: 権限名は現行 `CDef.Authority` = `ROLE_ADMIN`/`ROLE_PLAYER`(doc の `ROLE_USER` 表記は実体差異)。パスワードは login で形式検証しない(緩和後ポリシー)。`@RestController` 配下の例外は `RestApiExceptionHandler` が ProblemDetail 化。新 REST 認証必須エンドポイントは `/api/v1/**` チェーンに乗せれば JWT filter + 401 entrypoint が効く
- **DBFlute 運用メモ (重要)**:
  - **ReplaceSchema (`manage.sh 1`=renewal / `manage.sh 0`) は共有ライブ DB に厳禁** (全 drop で :8091 あいのりデータ消失)。テーブル追加は live DB へ `CREATE TABLE IF NOT EXISTS` (追加のみ) → `manage.sh regenerate` (非破壊・DB読み取り) の順
  - **gradle は JAVA_HOME=21 必須**。非対話シェルは JAVA_HOME=17 のことがあり `compileJava` が「21は無効なソース・リリース」で失敗。`JAVA_HOME=$(/usr/libexec/java_home -v 21) ./gradlew ...` で実行
  - DBFlute エンジン本体は Java 8 で動作 (`_project.sh` が `/usr/libexec/java_home -v 1.8` を設定)。エンジンは `mydbflute/dbflute-1.3.1` (git tracked)
- **Step 2 サブ step**: 2.1 移動+Jib (✅ #47) / 2.2 ktlint+hook+gitignore (✅ #48) / 2.3 frontend 雛形 (✅ #49) / 2.4 e2e 雛形 (✅ #50)。**context-path `/wolf-mansion-api` は別サブ step に切り出し**済 (PlayerController の id_register Cookie path と結合のため `/wolf-mansion` 据置、Step 3 前後で実施)
- **git 状態**:
  - ブランチ = `feature/monorepo`。HEAD = `ff109a6d` (= step-7.5 #70) + 後片付け commit。作業ツリー clean、origin と同期
  - **構成**: `backend/` (Spring Boot/Kotlin、自己完結 Gradle) / `frontend/` (RR v7 SSR) / `e2e/` (Playwright、ローカル専用) / root は doc・設定のみ
  - **backend**: ktlint 導入済 (`backend/build.gradle.kts` plugin + `.editorconfig` で 5 ルール無効化)。context-path は `/wolf-mansion` 据置。`cd backend && ./gradlew ...`、bootRun は 8089。**JDK 21 (jenv): root + `backend/.java-version`=21 + jenv global=21**
  - **e2e**: Playwright (`@playwright/test` 1.60.0 pin、minimumReleaseAge 14日制約) + pnpm (独立プロジェクト)。`playwright.config.ts` の webServer が backend **18089** / frontend **15173** を別ポート自動起動 (通常 8089/5173 と並走可)、baseURL=frontend。smoke + **auth.spec (3.3 で追加: signup→me→logout→login + 未認証リダイレクト)**。frontend webServer に `BACKEND_ORIGIN=…:18089` を注入し Vite proxy の転送先を e2e backend に向ける。本格 authoring は Step 8+/scenarios。CI 非実行。`cd e2e && pnpm install && pnpm run install:browsers && pnpm test`。**注意: e2e は provision 済み DB が前提** (空 docker DB だと `VILLAGE doesn't exist` 等で 500)
  - **frontend**: RR v7 framework(SSR) + Vite 8 + Tailwind v4 + TS + React 19。`pnpm install/dev(5173)/build/lint/format:check/typecheck` 全 green。**RR は `minimumReleaseAge`(14日) で 7.15.1 固定** (7.16+ が 14 日経過したら bump + v8 future flags 再有効化)。中核ライブラリ (react-query/zustand/react-hook-form/zod/heroicons + devDep openapi-typescript) 導入、`root.tsx` に QueryClientProvider 配線済 (リクエスト毎生成)。**3.3 で認証フロー実装済** (`app/lib/{api,zodResolver}.ts` / `app/features/auth/*` / `/login,/signup,/mypage,/change-password` ルート)。dev は `vite.config.ts` の **proxy `/wolf-mansion`→`BACKEND_ORIGIN`(既定 8089, rewrite無し)** + `optimizeDeps.include`(react-hook-form/zod)。API base は env `VITE_API_BASE`(既定 `/wolf-mansion`)。Dockerfile は Step 11 で作成
  - **Claude hooks** (`.claude/settings.json` PostToolUse Edit|Write): `ktlint-check.sh` (.kt→ktlintCheck) + `oxlint-check.sh` (frontend .ts/.tsx/.js/.jsx/.css→oxlint+oxfmt、検査のみ)。state は `.context/{ktlint,oxlint}-hook` (gitignore)。**`.claude/settings.json` の編集は self-modification ガードで都度ユーザー許可が必要**
  - **`.gitignore` は per-dir**: root(リポジトリ全体) / `backend/.gitignore`(Gradle/STS/dbflute-log) / `frontend/.gitignore`(node_modules/build/.react-router/.vite/env)
  - `origin/main` = `8959ea5c`。feature/monorepo は新 main へ rebase 済で同期。**以降は通常 `git push` で OK** (force 不要)
  - `step-0` ローカルブランチ (`668b5335`) 残置 (不要なら `git branch -D step-0`)。`.reviews/step-0.md` 残置 (Step 0 分)

## Step 0 成果物

- `doc/migration/screens/*.md` + `screens/village/*.md` (画面別) / `screens/public-api-pinning.md` (公開 API ピン留め)
- `doc/migration/usecases/*.md` (縦/機構深掘り: footstep / daychange / mask)
- `doc/migration/scenarios/README.md` (横/時系列シナリオ)。**実体の authoring は e2e 検討時**で良い。今は器+計画のみ (4 シナリオ: create-join / prologue-manage / start-roles / progress-day。epilogue-finish は要否未確定)
- `doc/migration/01-overview.md` 〜 `08-step-plan.md` (領域別) / 索引 `doc/migration/screens/README.md`

## Step 0 の重要発見 (移行設計に効く)

- `VillageControllerHelper` の **`ParticipantSituation`/`VillageSituation` 二層駆動**が村画面移行の核心。これを村取得 API のマスク基盤に据える (village-base.md / usecases/mask.md)
- **認可マスクは backend 完結**、3 軸 (status × 視点 × フィールド)。死因は進行中に無惨マスク、投票先は openVote/黒箱で非公開化 (usecases/mask.md)
- **Daychange は本番ポーリング駆動** + `VILLAGE_DAY` PK で二重進行を排他済み → scheduler 化は不要 (usecases/daychange.md)
- **公開 API は命名規則混在** (snake/camel) → 個別維持必須。`/api/login` は analyzer が使用のため互換維持必須 (public-api-pinning.md)
- パスワードポリシー緩和 (3-60字・記号、login 形式検証撤廃) (03-auth.md)

## 次にやること

**Step 8 (村画面) 進行中 — 8.1〜8.15 ✅ (#71〜#84)、次は 8.16 (admin 機能)**。

- **(次) step-8.16 admin 機能**: 管理者 (playerId=1) 専用機能 (village-admin.md が正本)。`AdminView.isAdmin` は出ている。SSR の該当 controller を踏襲
- **8.15 のメモ**: creator REST は `/api/v1/villages/{id}/creator/*` (say-confirm/say/kick/cancel/extend-epilogue/shorten-epilogue)。kick/cancel の正常系は**使い捨て村を作って検証し、最後に cancel して後片付け**する方式が有効 (村 7 = 廃村済みがその痕跡)。`isAvailableShortenEpilogue` を domain situation に追加済み (短縮は残り 1 日超のみ)。エピローグ延長/短縮の正常系実測は未実施 (エピローグ村が無いため。domain assert + 異常系 400 は確認済み) — エピローグ村ができたら確認
- **8.14 のメモ**: 村情報は `GET /api/v1/villages/{id}/info` (公開) = SSR と共通の `VillageSettingsContent` を直返し。**ネスト DTO の単純名衝突 (`SayRestriction` 等 vs domain) で SpringDoc が生成型を壊す問題を実際に踏んだ** → `@Schema(name = "VillageSettingsXxx")` で解消。初回役職確認モーダルは localStorage `already_skill_confirm` (AgeLimitModal と同パターン)、年齢制限確認 → 役職確認の逐次表示。**初回モーダルは参加者系 e2e をブロックするため各 spec に dismiss を追加済み** — 新しい参加者系 e2e を書くときは `dismissInitialSkillModal` を忘れない。アクション e2e は 1 日の回数枯渇 (残り0/8回) で動的 skip
- **8.13 のメモ**: 表示設定は `features/village/displaySettings.ts` (zustand persist / localStorage キー `wolf-mansion-display-settings`)。**react-query を SSR dehydrate する場合は persist の hydration 不一致に注意** (`skipHydration` 等。現状は設定依存 UI が loading 分岐の背後なので安全)。通知 keyword の空要素除去は legacy からの意図的改善
- **8.12 の申し送り**: **表情差分 (原画村限定の画像アップロード add-face-type / modify-face-type) は 8.12.1 として分割・未実装**。原画村フィクスチャが必要 (村作成で「オリジナル画像をアップロードする」を選ぶ)。SSR `VillageRpController` の該当 2 エンドポイントと `face-type-form.html` が正本。なお SSR の modify-face-type は **face type の所有者検証をしていない** (本人キャラ以外の code を送ると他人の表情を更新できる可能性) — REST 化時に要検証・要修正
- **検証用フィクスチャ村 6「step8 コミット動作確認用の村」を追加 (2026-06-12)**: master 作成、コミットあり、進行中 2 日目、参加者 9 (dummy + testuser01〜08、players 2〜9)。SPA 村作成 (村 4 から設定流用) + SSR debug メニューで進行。**流用で付いた R18 タグは年齢制限モーダルが e2e を妨げるため DELETE 済み** (RELATIVES_ONLY タグは残存)。コミット系の動作確認・e2e 動的探索が村 6 を拾う
- **SSR デバッグ操作の curl 手順 (8.11 で確立)**: SSR ログイン = GET `/wolf-mansion-api/login` で `_csrf` 取得 → 同 cookie jar で POST (`userId`/`password`/`_csrf`) → 302。debug 進行 = 村ページから `_csrf` 取り直して POST `/wolf-mansion-api/village/{id}/allparticipate` (**`personNumber` 必須** — 無いと NPE 500) / POST `/village/{id}/dayChange`。**SSR の context-path は `/wolf-mansion-api`** (8089 の `/wolf-mansion/...` は 404)
- **8.9 で確立したパターン (能力セット)**: 能力 UI は situation/me の `ability` 素材で出し分け (襲撃=attackerList 非空 / 調査=skill.hasInvestigateAbility / 徘徊=hasDisturbAbility && targetList 空 / 対象+足音=isTargetingAndFootstep / 対象のみ)。**役職別の分岐を frontend に持ち込まず、判定済みフラグ・候補リストを View で返す**。襲撃対象と現在対象の足音候補は situation に含まれないため AbilityPanel が初期表示時に candidates API (`ability/attack-targets`, `ability/footsteps`) で取得。**2 文字目大文字の Kotlin プロパティ (`cMadmanNames` 等) は Jackson と SpringDoc で名前が割れて spec に重複フィールドが出る → `@get:JsonProperty` で明示**。村 5 の配役は DB 直クエリ (`docker exec wolf-mansion-mysql mysql -u wmansion -p... -e "SELECT ... FROM VILLAGE_PLAYER vp JOIN PLAYER p ..."`) で確認でき、testuser03/05/15=人狼、testuser06=狩人 (全員 password=testuser) — 新規村を建てなくても役職者の実測ができた
- **Step 8 の進め方**: 統合ブランチ `feature/monorepo-step8` 上でサブ step を `/add-issue` → `/ship-issue` (base 読み替え)。8.2 → 8.3 (フィルタ) → 8.4 (発言投稿) → … と依存順 (08-step-plan.md の表)。村 5 (進行中) を動作確認に使い、足りない状態は debug 機能 (人数分入村 / 日付を進める) で作る
- **Step 8 で再利用できる資産**: `GET /api/v1/villages/{id}/setting` (7.5 新設、joinPassword マスク済み) は村画面の設定表示・村設定変更 (village-settings) でも使える。村設定変更画面はフォーム部品を new-village と共通化する前提 (new-village.md「村設定変更画面と酷似」)
- **申し送り (Step 7 から)**: 実村作成→廃村 teardown を伴うシナリオ e2e は **Step 8 (村画面) 完成後**に検討 (現状の e2e は業務エラーパス or 既存 CANCEL 村 (ID 4) 利用で DB 非汚染)
- **認証付き CRUD パターン (step-6 で確立・以降踏襲)**: write 系 REST は GET のみ permitAll に足し、書き込みは `/api/v1/**` チェーンの `authenticated()` に乗せる。frontend は作成系ページ = `RequireAuth`、公開ページ内の書き込みは 401 → メッセージ + ログイン誘導。mutation 後は `useInvalidateXxx` → `navigate`。**コレクション要素の制約は型引数 `@Size` でなくコード検証** (実行時に効かない。spec へは `@ArraySchema` で出す)。**複数テーブル書き込みは service に `@Transactional`**
- **step-5.1/5.2 で確立したパターン**: 役職説明データ・未実装役職は `descriptions.ts` (rule/skill.html からスクリプト自動生成)。名前・略称・陣営名は API (`SimpleSkillView`) から取得。メッセージ表示は暫定の色分け枠線 (`components/ui/SkillMessage`)、完全な見た目は Step 8.2 後に差し替え (08-step-plan.md 繰り越し事項)。フォントは system sans-serif (Inter 除去済)
- **REST API 設計方針 (step-5.4 で確定・以降必須)**: 画面専用の API やレスポンスを作らない。ドメインモデルをそのまま返す。Response DTO は「隠すべき情報がある」「大量取得で削ぎ落とす」場合のみ。複数ドメイン情報が要る画面は個別 API → frontend 組み立て。正本 [`doc/migration/01-overview.md`](../doc/migration/01-overview.md)「REST API 設計方針」
- **UI コンポーネント (step-4.2 で確立・以降必須)**: 画面実装前に **`components/ui/`** (Button / Heading / Form(FormRow,FormActions) / CollapsiblePanel / MultiSelect / ButtonRadioGroup) を確認/拡張してから組む。inline・重複の「その場しのぎ」禁止。新規フォーム/ボタン/見出しはここに足す
- **リンク規約 (step-4.3 で CLAUDE.md 昇格・最重要)**: **未移行でも移行予定のあるページは SPA URL** (`<Link>` / `<LinkButton>`)。`legacyUrl` + `<a>` は SPA 化予定が無いページのみ。レビューで誤って legacyUrl に戻されたため独立セクションに昇格した
- **コメント規約 (step-4.2 で CLAUDE.md 明文化・厳守)**: コメントに **step 番号・「既存再現 (`:8091`/`相当`/`legacy`)」を書かない**。GET 検索は **Request クラス + `toQuery()`**。一覧の並び順/絞り込みは **API 側**で指定 (frontend で `reverse()` しない)
- **静的アセット参照 (step-3.6 で確立)**: frontend 配信の静的アセット (画像/フォント等) は **`assetUrl("/app/...")`** (`lib/api`) で参照し `frontend/public/app/` に置く。`legacyUrl` は**未移行 SSR ページ専用**。新画面でトップ系画像が要るなら public に追加して assetUrl で参照する
- **frontend 構成 (4.1 で確定)**: colocation (`routes/<screen>/route.tsx` + 画面専用をコロケート) / `features/<domain>` は共有のみ / `features/villages`(一覧)・`features/village`(今後の詳細) の使い分け。正本 [`doc/migration/04-frontend.md`](../doc/migration/04-frontend.md)
- **OpenAPI 運用メモ (3.4 以降)**: 新 REST エンドポイント/DTO を追加したら **`cd frontend && pnpm gen:api`** (backend 8089 起動状態で) を実行して `app/api/{openapi.json,types.ts,constants.ts}` を再生成・commit する。怠ると PR の `api-drift` workflow が fail する。DTO の長さ制約は **Jakarta `@Size`** を使う (`@Length` は spec に出ない)。フロントの制約定数は手書きせず `~/api/constants` (生成物) を使う
- **UI 忠実再現 (方針変更)**: 各画面は**移行 step 内で `:8091` 基準に忠実再現**する (`<title>` / OGP / `<head>` メタ / 共通ヘッダー含む)。Step 12 への先送りは廃止。Step 12 は視覚モダナイズ (刷新) のみ。08-step-plan.md「忠実再現は各画面 step で行う」が正本

### Step 2.4 e2e の確定事項 (次セッション参照用)

- **ポート**: e2e は backend **18089** / frontend **15173** (通常 8089/5173 と別、並走可)、baseURL=frontend
- **JDK (重要)**: `./gradlew` は PATH の jenv shim より **`JAVA_HOME` を優先**。jenv の `JAVA_HOME` 再設定は**対話シェルの cd フックでのみ**起きるため、**非対話 spawn (Playwright webServer / CI 的呼び出し) は JDK 21 を取り損ねうる**。対策として root + `backend/.java-version`=21 + jenv global=21 を配置済。ユーザー対話シェルからの `cd e2e && pnpm test` は JAVA_HOME=21 が継承され backend 正常起動 (検証済 smoke green)
- **browser**: `ignore-scripts=true` のため `pnpm run install:browsers` (= `playwright install chromium`) で別途取得
- **ログ**: `e2e/.logs/` (gitignore) に出力。`pnpm run clean:logs` で掃除 (`settings.local.json` で許可済、一般 `rm` の ask は維持)
- **smoke**: frontend トップ表示 1本のみ。logback の `/var/log/werewolf-mansion/tomcat.log` エラーは本番 appender 不在の非致命的 status (無視可)

#### Step 2 全体の動作確認 (完了判定 ✅)

- `backend/` で bootRun 起動 / `frontend/` で `pnpm dev` 起動 / `e2e/` で `pnpm test` (webServer 自動起動) / hook で .kt/.tsx 編集時に自動 lint — すべて green

### ワークフロー (Step 1 以降共通)

- **PR ベース**: ブランチ `<type>/step-N(.M)-<slug>`、base `feature/monorepo`、`/ship-issue` で 実装 → 動作確認 → PR → pr-reviewer → ユーザー承認 → squash merge
- **main には push / merge しない**。最終 cutover (Step 11) で `--no-ff` merge
- Step 1 実績: `.java-version` 17→21 / README ローカル開発手順整備 (skill 採番は Step 0 bootstrap で対応済みのため変更なし)

## 注意点 (引き継ぎ事項)

- **過去の feature/monorepo の話には一切触れない** (リセット済み)
- **main には push しない**。`feature/monorepo` 上で進め、最終 cutover 時に `--no-ff` merge する
- **既存稼働環境**: `http://localhost:8091/wolf-mansion/` でローカル稼働中、DB はあいのり。スクショ取得・視覚比較に活用。スクショは `.playwright-mcp/` (gitignore 済)、視覚比較は `:8091` 基準、PNG は commit しない
- **ローカル認証情報**: ローカル DB の登録済み全ユーザーは password = `testuser`。e2e / 手動の動作確認で既存ユーザーログインにそのまま使える (**ローカル専用テストデータ・本番非該当・公開可**)。詳細は `e2e/README.md`「ローカル認証情報」
- **既存 JS (jQuery + Handlebars)** が `src/main/resources/static/app/js/` 配下 (13 ファイル)。調査対象に必ず含める
- 調査系の子 Issue は個別 `.issues/` ファイルを作らず `.issues/README.md` 一覧 + git 履歴で記録

## 関連ファイル

- 全体 index: `migration.md`
- 領域別: `doc/migration/01-overview.md` 〜 `doc/migration/08-step-plan.md`
- 各 md の「確定」セクション = 決定事項、「未確定事項 / 要調査」セクション = Step 0/1 で詰める残課題
