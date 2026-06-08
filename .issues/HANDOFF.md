# HANDOFF — wolf-mansion REST + RR v7 移行計画

本ドキュメントは、移行計画作業の **次セッションで最初に何をやるか** を示すハンドオフメモ。
決定事項・設計詳細は `migration.md` (index) と `doc/migration/` 配下が正本。ここには docs に載らない運用状況だけを書く。

## 現在地

- **フェーズ**: **Step 4 (画面別 REST 化) 進行中** — **step-4.1 (ホーム) ✅ (#56)** / **step-3.6 (認証画面忠実再現) ✅ (#57)** / **step-4.2 (村一覧画面) ✅ (#58)**。残りは intro (`/intro`)。Step 3 / Step 2 完了済
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
  - **次の候補**: **step-4.3 (intro / `/intro`)** で Step 4 を完了させる → 以降 **Step 5 (情報・静的ページ: 5.1 役職一覧 → 5.2 ルール → …)**。役職一覧 (5.1) は **本 step で新設した `GET /api/v1/skills` を流用可** (陣営別表示が要るなら拡張)。`/add-issue` 起票 → `/ship-issue`。08-step-plan.md の順序に従う
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
  - ブランチ = `feature/monorepo`。HEAD = `fc72a573` (= step-4.2 #58)。作業ツリー clean、origin と同期
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

**Step 3 完了 🎉 + step-4.1 (ホーム) ✅ (#56) + step-3.6 (認証画面忠実再現) ✅ (#57) + step-4.2 (村一覧画面) ✅ (#58)**。次は **step-4.3 (intro `/intro`)** で Step 4 を完了 → **Step 5 (情報・静的)**。

- **(次) step-4.3 (intro `/intro`)**: home がまだ `legacyUrl('/intro')` を指す。intro 画面を React 化して Step 4 を完了させる。静的中心。`/add-issue` 起票 → `/ship-issue`
- **その後 Step 5 (情報・静的ページ)**: 5.1 役職一覧 (**step-4.2 新設の `GET /api/v1/skills` を流用可**) → 5.2 ルール → 5.3 情報ページ群 → 5.4 キャラチップ (**`GET /api/v1/charachips` 流用可**) → 5.5 エイプリル。08-step-plan.md 参照
- **UI コンポーネント (step-4.2 で確立・以降必須)**: 画面実装前に **`components/ui/`** (Button / Heading / Form(FormRow,FormActions) / CollapsiblePanel / MultiSelect / ButtonRadioGroup) を確認/拡張してから組む。inline・重複の「その場しのぎ」禁止。新規フォーム/ボタン/見出しはここに足す
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
