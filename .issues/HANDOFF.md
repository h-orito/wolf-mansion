# HANDOFF — wolf-mansion REST + RR v7 移行計画

本ドキュメントは、移行計画作業の **次セッションで最初に何をやるか** を示すハンドオフメモ。
決定事項・設計詳細は `migration.md` (index) と `doc/migration/` 配下が正本。ここには docs に載らない運用状況だけを書く。

## 現在地

- **フェーズ**: **Step 3 (認証 REST 化) 進行中**。Step 2 (monorepo 化) は完了済 (2.1 #47 / 2.2 #48 / 2.3 #49 / 2.4 e2e #50)
  - **step-3.0 完了 ✅ (#51)**: DBFlute を tracking 中の engine 1.3.1 へ**全再生成** (旧 engine 製のコミット済み生成コードを 1.3.1 スタイル `DBDef.of`/簡素 Javadoc に統一、版差ドリフト解消) + **REFRESH_TOKEN テーブル追加** (PLAYER への FK=RESTRICT、`IX_REFRESH_TOKEN_EXPIRES` 索引、共通カラム準拠)。JWT の generate ノイズ (162 ファイル) を auth コードから分離するため独立 PR にした
  - **step-3.1 完了 ✅ (#52)**: backend JWT 認証基盤 + `/api/v1/auth/{login,refresh,logout,me}`。`WolfMansionWebSecurityConfig` を 2 チェーン化 (`/api/v1/**` stateless JWT @Order(1) + 既存 session @Order(2) 温存)。`JwtTokenProvider`(HS256 明示)/`JwtAuthenticationFilter`(access Cookie→SecurityContext)/`JwtAuthenticationEntryPoint`(401 ProblemDetail)/`AuthCookieFactory`(access Path=/ 15分・refresh Path=`<ctx>/api/v1/auth` 14日・HttpOnly/SameSite=Lax/Secure は `jwt.cookie-secure` 連動)。refresh は不透明乱数+DB は SHA-256 ハッシュのみ、使い捨て rotation + 漏洩検知(使用済み再提示で当該プレイヤー全失効、`noRollbackFor` で失効をコミット)。ProblemDetail を `@RestControllerAdvice(annotations=[RestController])` で統一(SSR 非干渉)。期限切れ refresh は login/refresh 時に掃除。単体 13 + curl 回帰 + security-review クリア
  - **step-3.2 完了 ✅ (#53)**: signup(`POST /api/v1/auth/signup` 匿名・自動ログイン) / change-password(`POST /api/v1/auth/password` 認証必須・確認一致) の REST 化 + ログインレート制限。パスワードポリシー緩和は `fw/security/PasswordPolicy`(3〜60字・`[\x21-\x7E]+`)で共有。`id_register` cooldown Cookie は `AuthCookieFactory.idRegisterCookie`(Path=`<ctx>/api/v1/auth`)。**レート制限は DB 採用**: `LOGIN_FAILURE` テーブル(FKなし)+ `LoginRateLimiter`(domain/service/auth)で 2軸(account 5/IP 30・window 15分、`login-rate-limit.*` で調整可)、超過 429(`WolfMansionTooManyRequestsException`→`RestApiExceptionHandler`)。掃除はオポチュニスティック(失敗記録時に窓外削除+成功時アカウント単位 reset)で有界。**閾値到達で管理者 Discord 通知**(`DiscordRepository.postToMaster`、村非依存・短タイムアウト・ベストエフォート、キー×窓ごと概ね1回)。**`getIpAddress` を CF-Connecting-IP 優先に統一修正**(XFF 詐称でレート制限/access-info を回避させない、`AccessContextInterceptor`)。signup の IP volumetric 制限はアプリ層では行わず Cloudflare edge + cookie cooldown に委譲(根拠コメント明記)。`login` は `recordFailure` をコミットさせるため `noRollbackFor=[WolfMansionAuthException, WolfMansionTooManyRequestsException]`。単体 40(LoginRateLimiterTest 7 + AuthCoordinatorTest 14)+ curl 回帰(CF-IP キー実証含む)+ pr-reviewer 2巡(6指摘全反映)クリア
  - **次: step-3.3** frontend 認証フロー (login/logout/signup/me + RequireAuth + useMe) + e2e。その後 3.4 OpenAPI→TS 型生成
  - **判断済**: context-path rename (`/wolf-mansion-api`) は **据置 → Step 3 先行** (cutover 前の別サブ step に後回し)。Step 3 は 4 分割 (3.1 JWT基盤 ✅ / 3.2 signup・password+レート制限 ✅ / 3.3 frontend+e2e / 3.4 OpenAPI→TS)
  - **未対応の follow-up (別 step 候補)**: `DiscordRepositoryImpl.post`/`postToWebhook` が素の `RestTemplate`(タイムアウト無制限)。`postToMaster` 同様に短タイムアウトを付けるとリスクが揃う(pr-reviewer nit、本 PR スコープ外)
  - **認証実装メモ (3.2 以降で踏襲)**: 権限名は現行 `CDef.Authority` = `ROLE_ADMIN`/`ROLE_PLAYER`(doc の `ROLE_USER` 表記は実体差異)。パスワードは login で形式検証しない(緩和後ポリシー)。`@RestController` 配下の例外は `RestApiExceptionHandler` が ProblemDetail 化。新 REST 認証必須エンドポイントは `/api/v1/**` チェーンに乗せれば JWT filter + 401 entrypoint が効く
- **DBFlute 運用メモ (重要)**:
  - **ReplaceSchema (`manage.sh 1`=renewal / `manage.sh 0`) は共有ライブ DB に厳禁** (全 drop で :8091 あいのりデータ消失)。テーブル追加は live DB へ `CREATE TABLE IF NOT EXISTS` (追加のみ) → `manage.sh regenerate` (非破壊・DB読み取り) の順
  - **gradle は JAVA_HOME=21 必須**。非対話シェルは JAVA_HOME=17 のことがあり `compileJava` が「21は無効なソース・リリース」で失敗。`JAVA_HOME=$(/usr/libexec/java_home -v 21) ./gradlew ...` で実行
  - DBFlute エンジン本体は Java 8 で動作 (`_project.sh` が `/usr/libexec/java_home -v 1.8` を設定)。エンジンは `mydbflute/dbflute-1.3.1` (git tracked)
- **Step 2 サブ step**: 2.1 移動+Jib (✅ #47) / 2.2 ktlint+hook+gitignore (✅ #48) / 2.3 frontend 雛形 (✅ #49) / 2.4 e2e 雛形 (✅ #50)。**context-path `/wolf-mansion-api` は別サブ step に切り出し**済 (PlayerController の id_register Cookie path と結合のため `/wolf-mansion` 据置、Step 3 前後で実施)
- **git 状態**:
  - ブランチ = `feature/monorepo`。HEAD = `c03ebd97` (= Step 3.2 #53)。作業ツリー clean、origin と同期
  - **構成**: `backend/` (Spring Boot/Kotlin、自己完結 Gradle) / `frontend/` (RR v7 SSR) / `e2e/` (Playwright、ローカル専用) / root は doc・設定のみ
  - **backend**: ktlint 導入済 (`backend/build.gradle.kts` plugin + `.editorconfig` で 5 ルール無効化)。context-path は `/wolf-mansion` 据置。`cd backend && ./gradlew ...`、bootRun は 8089。**JDK 21 (jenv): root + `backend/.java-version`=21 + jenv global=21**
  - **e2e**: Playwright (`@playwright/test` 1.60.0 pin、minimumReleaseAge 14日制約) + pnpm (独立プロジェクト)。`playwright.config.ts` の webServer が backend **18089** / frontend **15173** を別ポート自動起動 (通常 8089/5173 と並走可)、baseURL=frontend。smoke 1本のみ (本格 authoring は Step 8+/scenarios)。CI 非実行。`cd e2e && pnpm install && pnpm run install:browsers && pnpm test`
  - **frontend**: RR v7 framework(SSR) + Vite 8 + Tailwind v4 + TS + React 19。`pnpm install/dev(5173)/build/lint/format:check/typecheck` 全 green。**RR は `minimumReleaseAge`(14日) で 7.15.1 固定** (7.16+ が 14 日経過したら bump + v8 future flags 再有効化)。中核ライブラリ (react-query/zustand/react-hook-form/zod/heroicons + devDep openapi-typescript) 導入、`root.tsx` に QueryClientProvider 配線済。Dockerfile は Step 11 で作成
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

**step-3.3 (frontend 認証フロー + e2e)** に着手する。`/add-issue` で Issue 作成 → `/ship-issue step-3.3` で PR フロー (base = feature/monorepo)。

- **step-3.3 スコープ**: frontend(RR v7) の認証フロー — login / logout / signup / me を叩く API クライアント + `RequireAuth` ガード + `useMe` フック。Cookie は backend が HttpOnly で発行済みなので **CSR (hydration 後 fetch) で叩く**(03-auth.md「SSR/CSR 認証境界」)。401 でログイン画面リダイレクト、429 は「しばらくしてから」表示。e2e (Playwright) で正常ログイン/ログアウト/signup/未認証リダイレクトを 1〜数本。**backend の REST は 3.1/3.2 で完成済**(`/api/v1/auth/{login,refresh,logout,me,signup,password}`)
  - **参照**: signup は 200 で MeResponse + Cookie(access/refresh/id_register) を返し自動ログイン。password 変更は 204。レート制限 429 / 重複 400 / 確認不一致 400 のハンドリングを UI に。パスワード長さ・文字種は `PasswordPolicy`(3〜60・印字可ASCII) を zod 側にも反映(定数共有は 3.4 OpenAPI 経由が本筋だが 3.3 でも暫定可)
- **その後**: 3.4 OpenAPI→TS 型生成パイプライン (`pnpm gen:api` + CI drift 検知)。zod とポリシー定数の正式共有もここ
- **別サブ step (Step 3 の後でも可)**: context-path rename `/wolf-mansion` → `/wolf-mansion-api` (`PlayerController.PATH_ID_REGISTER` の Cookie path 修正込み。稼働中 SSR と結合するため move と分離)

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
- **既存 JS (jQuery + Handlebars)** が `src/main/resources/static/app/js/` 配下 (13 ファイル)。調査対象に必ず含める
- 調査系の子 Issue は個別 `.issues/` ファイルを作らず `.issues/README.md` 一覧 + git 履歴で記録

## 関連ファイル

- 全体 index: `migration.md`
- 領域別: `doc/migration/01-overview.md` 〜 `doc/migration/08-step-plan.md`
- 各 md の「確定」セクション = 決定事項、「未確定事項 / 要調査」セクション = Step 0/1 で詰める残課題
