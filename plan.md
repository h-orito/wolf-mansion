# wolf-mansion バックエンドAPI化 + モダンフロントエンド移行プラン

## Context

人狼Webアプリ wolf-mansion を、Spring Boot + Thymeleaf の一体型構成から、Spring Boot REST API + React Router v7 (SSR) フロントエンドの分離構成に刷新する。目的:

- フロントエンドの開発体験・UI 品質を現代化する (Tailwind, TanStack Query, 型安全な API クライアント)
- API サーバとして独立させることで、将来のクライアント追加 (モバイル等) に備える
- 認証をセッションから JWT に移行し、バックエンドを stateless にする

既存データ (player テーブル, BCrypt パスワード) はそのまま使い、ID/パスワードログインの UX は維持。マージ時に一括切替なので、移行途中の互換性は不要。

## 開発フロー (重要)

- **main には直接 push しない**。本移行作業中、main は触らない (必要があれば緊急 hotfix のみ別途)
- 長寿命インテグレーションブランチとして **`feature/monorepo`** を使用
- 各 step PR は `feature/monorepo` から派生したブランチで作業し、**base を `feature/monorepo`** にして PR を作成
  - `git checkout feature/monorepo && git pull && git checkout -b step-N-xxx`
  - `gh pr create --base feature/monorepo`
- **PR 作成後は `pr-reviewer` サブエージェントで review → 指摘反映 (fix) → 再 review** を必要回数繰り返してから squash merge
  - レビュー結果は `.reviews/PR-<番号>.md` に出力される
  - must-fix / should-fix は反映、nits は要否判断
- step PR の merge 方式: **squash merge** (1 step = 1 コミットで feature/monorepo に積む)
- step 間の依存関係: **シーケンシャル** (step N が merge されてから step N+1 着手)、stacked branches は使わない
- `feature/monorepo` には branch protection をかけない (AI 駆動で速く回すため。事故防止はレビュー手順で担保)
- 全 step 完了後、`feature/monorepo` → `main` を **merge commit (`--no-ff`)** で取り込む (リリースポイントとして 1 つの merge commit + 各 step コミットを履歴に保持)

## 確定済みの方針

| 領域 | 選択 |
|---|---|
| Backend | Spring Boot 3.5 + Kotlin 1.9 + Java 21 + DBFlute (既存継続) |
| Backend 役割 | Thymeleaf 全廃、REST API 専用 |
| 認証 | JWT (httpOnly Cookie 格納、access 15分 + refresh 14日) |
| Frontend | React Router v7 framework mode (SSR 有効) + Vite + TailwindCSS v4 |
| データ層 | TanStack Query (server state) + Zustand (UI state) |
| 型共有 | SpringDoc OpenAPI → `openapi-typescript` で TS 型生成 |
| デプロイ | k8s 上の別コンテナ (API + Node SSR) |
| URL 構成 | `https://wolfort.dev/wolf-mansion-api` (API) / `https://wolfort.dev/wolf-mansion` (Frontend)、同一ドメイン異パス |

## アーキテクチャ概要

```
                          Ingress (wolfort.dev)
                          ┌────────────────────────┐
                          │ /wolf-mansion-api/* → API Service
                          │ /wolf-mansion/*     → Frontend Service
                          └────────────────────────┘
                                     │
                ┌────────────────────┴────────────────────┐
                ▼                                         ▼
   Spring Boot (Kotlin)                         Node 22 (RR v7 SSR)
   - REST endpoints /api/v1/*                   - SSR loader が
   - JWT 発行/検証                                 cluster-internal URL で
   - DBFlute → MySQL                               API を呼ぶ
                                                 - browser は同一オリジン
                                                   /wolf-mansion-api を直叩き
```

Cookie は domain=`wolfort.dev`、`access_token` は `Path=/` で両アプリに飛ぶ。`refresh_token` は `Path=/wolf-mansion-api/api/v1/auth` に限定。

## 1. バックエンド REST 化

### Thymeleaf 全廃
- 削除: `backend/src/main/resources/templates/` 全 74 ファイル
- 削除: `backend/src/main/resources/static/` 全件 (画像の扱いは次々節「画像配信」参照)
- `backend/build.gradle.kts`: `spring-boot-starter-thymeleaf`, `thymeleaf-extras-springsecurity6`, `thymeleaf-layout-dialect` を除去
- 削除: `backend/src/main/kotlin/com/ort/app/api/view/` 全件 (Thymeleaf 前提で表示用に変換済みフィールドを大量に持っているため流用不可)
- `backend/src/main/kotlin/com/ort/app/api/` Controller 約 107 件:
  - `@Controller` → `@RestController` 化、テンプレート名返却 → 新規 DTO 返却 (詳細は次節)
  - 既存 `api/request/` を全面整理 (詳細は「リクエスト DTO 設計」節)
  - `GlobalControllerAdvice` の Thymeleaf 用属性注入を削除
- `api/helper/` のうち template 専用ヘルパーは削除、業務ロジックを含むものは残す
- 削除: Twitter / Slack 連携 (`backend/src/main/kotlin/com/ort/app/infrastructure/twitter/`, `backend/src/main/kotlin/com/ort/app/infrastructure/slack/` および関連設定/呼び出し箇所)
- Discord / Mastodon / Microsoft 翻訳の連携は影響なし、そのまま継続

### API レスポンス DTO 設計 (新規 `api/response/`)

**方針**: [firewolf の api/view 構成](https://github.com/h-orito/firewolf/tree/main/backend/src/main/kotlin/com/ort/firewolf/api/view) を参考に、`backend/src/main/kotlin/com/ort/app/api/response/` 配下にドメイン単位のサブディレクトリで整理する。クラス名は `~View` で統一 (firewolf 流)。

ディレクトリ案:
```
api/response/
  village/
    SimpleVillageView.kt          村一覧用の軽量ビュー
    VillageView.kt                 村詳細のルート
    VillageSettingsView.kt
    VillageParticipantView.kt
    VillageParticipantsView.kt
    VillageDayView.kt
    VillageDaysView.kt
    VillageTimeView.kt
    VillageRegisterView.kt
    VillagesView.kt                村一覧 (Simple のコレクション)
    VillageFootstepView.kt         ★wolf-mansion 固有
    VillageFootstepsView.kt        ★wolf-mansion 固有
  message/
    MessageView.kt
    MessagesView.kt
    MessageAnchorView.kt
  skill/
    SkillView.kt
    SkillRequestView.kt
  player/
    PlayerView.kt
  chara/
    CharaView.kt
    CharachipView.kt
  ability/
    AbilityView.kt
  myself/
    MyselfParticipantView.kt       自分視点の参加者情報 (隠蔽なし)
  dead/
    DeadView.kt
  reserved/
    ReservedView.kt
```

**ViewModel 内に「ドメインモデルから組み立てる secondary constructor」を持たせ、隠蔽ロジックを内包する** (firewolf 流):

```kotlin
data class VillageParticipantView(
    val id: Int,
    val name: String,
    val chara: CharaView,
    val player: PlayerView?,
    val skill: SkillView?,
    val skillRequest: SkillRequestView?,
    val camp: String?,
    val isDead: Boolean,
    // ...
) {
    constructor(
        participant: VillageParticipant,
        village: Village,
        // ...
        shouldHidePlayer: Boolean,
        shouldHideSkill: Boolean,
        shouldHideFootstep: Boolean,
    ) : this(
        id = participant.id,
        // ...
        player = if (shouldHidePlayer) null else PlayerView(/* ... */),
        skill = if (shouldHideSkill) null else SkillView(participant.skill),
        // ...
    )
}
```

呼び出し元 (Controller / Coordinator) で `village.status` と `currentUser` の参加状況に応じて `shouldHide*` フラグを判断して渡す。

### 足音 (Footstep) の DTO 設計と隠蔽

wolf-mansion 固有の足音ドメイン (`backend/src/main/kotlin/com/ort/app/domain/model/footstep/`) は **役職と同じくエピローグまで生情報を隠す** 必要がある。

**生 Footstep のフィールド** (`domain/model/footstep/Footstep.kt`):
- `day`: 日付
- `registerCharaId`: 登録者 (誰が登録したか)
- `charaId`: 足音の主 (能力で他人として偽装可)
- `roomNumbers`: 経路 (例: `"101,102,103"`)

**隠蔽ルール案**:

| ステータス | 自分の足音 | 同陣営の足音 (人狼/共有者など) | それ以外 |
|---|---|---|---|
| 進行中 (Progress) | 全フィールド表示 | チーム内共有 (既存 `HideDomainService` のルールに準拠) | `registerChara`/`chara` を隠す。経路 (`roomNumbers`) のみ匿名で公開 |
| エピローグ / 終了 | 全公開 | 全公開 | 全公開 |
| 募集中 (Prologue) | 未登録 | - | - |

**DTO 案**:

```kotlin
data class VillageFootstepView(
    val day: Int,
    val registerChara: CharaView?,   // 隠蔽時 null
    val chara: CharaView?,           // 隠蔽時 null
    val roomNumbers: String,         // 経路は推理材料として常に公開
)

data class VillageFootstepsView(
    val list: List<VillageFootstepView>,
)
```

**隠蔽判断の置き場所**: `VillageFootstepView` の secondary constructor に `shouldRevealOwner: Boolean` を渡し、内部で出し分け。`shouldRevealOwner` の判断は既存 `domain/service/ability/HideDomainService.kt` (`Footsteps` 引数を持つメソッドあり) のロジックと整合させる。必要なら DTO 構築用に同サービスにメソッドを切り出すか、新規 `FootstepRevealDomainService` を追加。

**注意**: 現状の `api/view/VillageGetFootstepListContent.kt` と `domain/model/situation/village/VillageFootstepSituation.kt` は既に表示用文字列に変換済みなので流用せず、生 `Footstep` ドメインモデルから DTO を組み立てる。

### `myself/` ビュー
自分視点のデータ (自分の能力履歴、自分宛て囁き等) は隠蔽不要なので、村全体のビュー (`VillageView`) とは別 endpoint で取得。例: `GET /api/v1/villages/{id}/myself`。

### リクエスト DTO 設計 (`api/request/` 整理)

**現状**: `backend/src/main/kotlin/com/ort/app/api/request/` 配下にフラットに `~Form` クラスが並んでいる (例: `VillageAbilityForm`, `VillageGetFootstepListForm` 等)。

**新方針**:
- response と同じドメイン単位サブディレクトリに整理
- 命名規則を `~Form` → `~Body` に統一 (JSON `@RequestBody` を受ける DTO であることを明示)
- ファイル整理は機能ごとに段階的に (実装ステップ 5〜8 で都度移動)

```
api/request/
  village/
    CreateVillageBody.kt
    VillageSayBody.kt
    VillageAbilityBody.kt
    VillageParticipateBody.kt
    VillageRpBody.kt
    VillageGetFootstepListBody.kt
    ...
  auth/
    LoginBody.kt
  player/
    UpdatePlayerBody.kt
    ChangePasswordBody.kt
  chara/
    CreateCharaBody.kt
    ...
  admin/
    ...
```

`@Valid` + Jakarta Bean Validation アノテーション (`@NotBlank`, `@Size` 等) で入力検証を統一。エラーは `ExceptionControllerAdvice` で 400 + 構造化 JSON に変換。

### 画像配信

**現状**: `backend/src/main/resources/static/` 配下 5.1M、`app.original-image.basedir=/var/www/html/wmansion/original` 設定もあり (外部ディレクトリ + nginx 配信を併用していた)。

**新方針**:
- **削除**: `backend/src/main/resources/static/` 全件 (backend からの画像配信は完全停止)
- **frontend `frontend/public/img/`**: favicon、OGP 画像、サイトロゴなど **サイト UI に必須の少数の画像のみ**
- **キャラ画像など大量画像**: 外部ホスティング (k8s 外、nginx 等で `wolfort.dev` 配下のパスで配信)
  - DB には **相対パス** で保存 (例: `/wmansion/1/hoge.png`)
  - 配信 URL は同一ドメインで `https://wolfort.dev/wmansion/1/hoge.png`
  - そのため DTO 上は path のままで返し、フロントでは相対パスとして `<img src="/wmansion/1/hoge.png">` で参照 (相対パス → 同一オリジン解決)
  - ベース URL を環境で切り替えたい場合は env (`IMAGE_BASE_URL`) で prefix 付与する余地を残すが、現時点では path 直書きで OK
- 移管作業自体 (画像ファイルを外部ホスティングへコピー) はユーザ側で実施。plan には **backend の static 削除と Spring 側の静的配信無効化** だけ含める
- `app.original-image.basedir` / `app.original-image.baseurl` 設定は不要になれば削除、画像アップロード機能 (もしあれば) のアップロード先は別途調整

### context-path 変更
- `backend/src/main/resources/config/application.yml`: `server.servlet.contextPath` を `/wolf-mansion` → `/wolf-mansion-api`
- session timeout 等の設定は JWT 化に伴い不要 (後述)
- `spring.web.resources.add-mappings: false` で静的配信無効化

### endpoint 命名規則
新規/書き換えは `/api/v1/...` プレフィックス (context-path 込みで `/wolf-mansion-api/api/v1/...`)。機能単位 (auth, villages, players, charas, skills, admin) で Controller を再構成。

例:
- `GET /api/v1/villages?status=...`
- `GET /api/v1/villages/{id}`
- `GET /api/v1/villages/{id}/messages?since=...`
- `POST /api/v1/villages/{id}/messages`
- `POST /api/v1/villages/{id}/abilities`
- `POST /api/v1/auth/login`

### SpringDoc OpenAPI
- 依存: `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0`
- `application.yml`: `springdoc.api-docs.path: /v3/api-docs`
- DTO に最小限の `@Schema` 付与
- spec URL: `http://localhost:8089/wolf-mansion-api/v3/api-docs`

### CORS
- 本番も含めて常時有効化し、許可オリジンを env (`ALLOWED_ORIGINS`、カンマ区切り) で **ホワイトリスト管理**
  - 本番例: `https://wolfort.dev`
  - dev 例: `http://localhost:5173`
  - 将来ドメインが変わった/追加された場合に ConfigMap 変更だけで対応可能
- `WolfMansionWebSecurityConfig` に `CorsConfigurationSource` Bean を追加、`allowCredentials=true` (Cookie 送受信のため)
- `allowedMethods`: `GET, POST, PUT, PATCH, DELETE, OPTIONS`
- `allowedHeaders`: `Content-Type, X-XSRF-TOKEN` (将来 CSRF token 追加に備えて)

## 2. JWT 認証

### ライブラリ
`spring-boot-starter-oauth2-resource-server` + `nimbus-jose-jwt`。Spring Security と公式統合されており、JWT filter 自前実装が不要。

### トークン設計
- 署名: HS256 + 32 byte 以上の secret (env `JWT_SECRET`)
- access_token: TTL 15 分、claims `{sub: playerName, authority: "プレイヤー" | "管理者", exp, iat}`
  - `authority` は 2 値のみ (既存 `hasRole("ADMIN")` と整合)
- refresh_token: TTL 14 日、**MySQL `refresh_token` テーブルで管理** (rotation 方式)
  - スキーマ: `id, token_hash, player_id, expires_at, revoked, created_at`
  - `token_hash` は SHA-256 (生トークンは DB に置かない、漏洩時の影響を抑える)
  - リポジトリ: `domain/model/auth/RefreshTokenRepository.kt` (interface) + `infrastructure/datasource/auth/RefreshTokenDataSource.kt` (DBFlute 実装)
  - **DDL 追加手順** (DBFlute): `backend/dbflute_wolf_mansiondb/playsql/replace-schema-10-basic.sql` に `CREATE TABLE refresh_token (...)` を追記し、`cd backend/dbflute_wolf_mansiondb && ./manage.sh 1` (ReplaceSchema) を実行して DB に反映 + DBFlute コード再生成
- 格納先: 両方とも httpOnly Cookie (SSR loader からも認証必要なため)
  - access: `HttpOnly; Secure; SameSite=Lax; Path=/`
  - refresh: `HttpOnly; Secure; SameSite=Lax; Path=/wolf-mansion-api/api/v1/auth`

### endpoint 仕様
| Method | Path | 内容 |
|---|---|---|
| POST | `/api/v1/auth/login` | `{userId, password}` → 既存 `UserInfoService` で BCrypt 検証 → Set-Cookie 2 種 |
| POST | `/api/v1/auth/refresh` | refresh_token Cookie 読取 → rotation して新 access 発行 |
| POST | `/api/v1/auth/logout` | refresh_token revoke、Cookie 削除 |
| GET | `/api/v1/auth/me` | 現在ユーザ。未認証は `200 + {user: null}` で SSR で扱いやすく |

### WolfMansionUserInfoUtil 置き換え
現状 `SecurityContextHolder.authentication.principal as UserInfo` を返している (`backend/src/main/kotlin/com/ort/app/fw/util/WolfMansionUserInfoUtil.kt:8-13`)。resource server 構成では principal が `Jwt` になるため、内部実装を「`Jwt` の `sub` claim から `UserInfo` を組み立てる」に差し替える。**シグネチャは維持**するので、参照する 41 箇所は無修正。

### CSRF
`SameSite=Lax` + state-changing endpoint は `Content-Type: application/json` 必須 → クロスオリジンからの非単純リクエストは preflight でブロックされる。`csrf().disable()` で運用。

防御強化が必要になった段階で、double-submit cookie (`XSRF-TOKEN` header) を追加可能 (任意)。

### remember-me 代替
refresh_token (14 日) で実質永続ログインを実現。チェックボックスは廃止。

## 3. フロントエンド

### 初期化
```bash
cd /Users/h-orito/ort/wolf/workspace/wolf-mansion
pnpm create react-router@latest frontend
```
`react-router.config.ts` で `ssr: true, basename: "/wolf-mansion"`。

### ディレクトリ
```
frontend/
  app/
    root.tsx                  ルートレイアウト
    routes.ts                 ルート定義
    routes/
      _index.tsx              トップ
      login.tsx
      _auth.tsx               認証必須レイアウト (loader で /auth/me)
      villages._index.tsx     村一覧
      villages.$id.tsx        村詳細
      villages.$id.participate.tsx
      village-records._index.tsx
      admin.*.tsx
    features/                 機能単位 (auth, village, chara, ...)
    lib/
      api/
        client.ts             fetch wrapper (Cookie forward, 401 → refresh リトライ)
        generated.ts          openapi-typescript 生成
        endpoints.ts          query/mutation hooks
      query.ts                QueryClient
      stores/                 Zustand stores
    components/ui/            Tailwind 共通部品
  public/img/                 既存画像を移管
  package.json
  vite.config.ts
  react-router.config.ts
  Dockerfile
```

### 主要ライブラリ
- `react-router` 7.x + `@react-router/node` + `@react-router/serve`
- `tailwindcss` 4.x + `@tailwindcss/vite` (CSS 内 `@import "tailwindcss"` + `@theme`)
- `@tanstack/react-query` 5.x
- `zustand`
- `openapi-typescript` (devDep, 型生成スクリプト)

### パッケージ管理 / サプライチェーン対策

**pnpm を使用** (npm/yarn は使わない)。

- `package.json` の `packageManager` フィールドで pnpm のバージョンを pin (例: `"packageManager": "pnpm@9.12.0"`)
- `corepack enable` 経由で固定バージョンの pnpm を使用 (Dockerfile / CI 共通)
- `pnpm-lock.yaml` を git でコミット、`pnpm install --frozen-lockfile` のみ使用
- `frontend/.npmrc` を作成し以下を設定:
  ```
  registry=https://registry.npmjs.org/
  ignore-scripts=true         # ★ デフォルトで postinstall を実行しない
  fund=false
  audit=false
  ```
  - `ignore-scripts=true` で post-install スクリプトのデフォルト実行を抑止 (サプライチェーン対策)
  - 必要なパッケージ (例: native binding を持つもの) は `pnpm.onlyBuiltDependencies` で個別許可
- 依存追加時のルール (README に記載):
  - 週次ダウンロード数・GitHub スター・最終更新を確認
  - メジャーバージョン v0.x の小規模パッケージは原則避ける
  - lockfile diff を必ずレビュー
- CI に以下を追加:
  - `pnpm audit --prod --audit-level high` (high 以上で失敗、CI ブロック)
  - `pnpm dedupe --check`
- 本番 Dockerfile で `--ignore-scripts` を明示、`PNPM_HOME` を非実行可能ディレクトリに

### SSR loader での Cookie 転送
```ts
// app/lib/api/client.ts
export function apiFetch(request: Request, path: string, init?: RequestInit) {
  const cookie = request.headers.get("cookie") ?? "";
  const url = `${process.env.API_BASE_URL}${path}`;
  return fetch(url, { ...init, headers: { ...init?.headers, cookie } });
}
```
- 開発: `API_BASE_URL=http://localhost:8089/wolf-mansion-api`
- 本番 (SSR Pod 内): `API_BASE_URL=http://wolf-mansion-api-svc:8089/wolf-mansion-api` (cluster-internal)
- browser 側 fetch (TanStack Query) はベース `/wolf-mansion-api` で相対パス (同一オリジン)
- loader 内で Set-Cookie が返ってきたら `headers` で transparent forward

### リアルタイム更新
第一段階: TanStack Query の `refetchInterval: 30000` で既存と同等。WebSocket/SSE は後続課題 (Spring 側で `SseEmitter` か STOMP)。

### ルーティング & ガード
- `routes/_auth.tsx` の loader で `/auth/me` を呼び、`user==null` なら `throw redirect("/wolf-mansion/login")`
- `client.ts` で 401 を受けたら 1 回だけ `/auth/refresh` 試行して再リトライ
- `ErrorBoundary` を root と主要レイアウトに

## 4. k8s / デプロイ

### Manifest (新規 `.k8s/` に配置)
- `Deployment wolf-mansion-api` (既存 `wolf-mansion` を rename)
- `Deployment wolf-mansion-frontend` (新規、Node 22)
- `Service wolf-mansion-api-svc` ClusterIP:8089
- `Service wolf-mansion-frontend-svc` ClusterIP:3000
- `Ingress wolf-mansion` (host: `wolfort.dev`)
  - `/wolf-mansion-api` → api-svc
  - `/wolf-mansion` → frontend-svc

### Frontend Dockerfile

**alpine は使わず Debian (bookworm-slim) ベース、arm64 を明示**。alpine は musl-libc 起因のネイティブビルド問題が起きやすいため。

```dockerfile
# syntax=docker/dockerfile:1.7
FROM --platform=linux/arm64 node:22-bookworm-slim AS build
WORKDIR /app
ENV CI=true
COPY package.json pnpm-lock.yaml .npmrc ./
RUN corepack enable && \
    pnpm install --frozen-lockfile --ignore-scripts
COPY . .
RUN pnpm build

FROM --platform=linux/arm64 node:22-bookworm-slim
WORKDIR /app
ENV NODE_ENV=production PORT=3000
# 非 root ユーザで実行
RUN groupadd -r app && useradd -r -g app -d /app -s /sbin/nologin app
COPY --from=build --chown=app:app /app/build ./build
COPY --from=build --chown=app:app /app/node_modules ./node_modules
COPY --from=build --chown=app:app /app/package.json ./
USER app
EXPOSE 3000
CMD ["node_modules/.bin/react-router-serve", "./build/server/index.js"]
```

ポイント:
- `--platform=linux/arm64` 明示 (OCL k8s ノードが arm64)
- `--ignore-scripts` で post-install スクリプト実行を抑止 (`.npmrc` に加えコマンドラインでも明示)
- 非 root ユーザで実行
- `react-router-serve` を直接呼び出し (`pnpm start` 経由を避けて軽量化)

### Backend Dockerfile
既存 Jib をそのまま使用 (既に arm64/linux 明示済み、コミット 33d4e22f)。`backend/build.gradle.kts` の `jib.to.image` を `ghcr.io/h-orito/wolf-mansion-api` に rename。

### GitHub Actions
`.github/workflows/deploy-ocl.yml` を 2 job 並列に拡張、**すべて arm64 GH runner (`ubuntu-24.04-arm`)** を使用:

- `build-api` (`runs-on: ubuntu-24.04-arm`): 既存の `cd backend && ./gradlew jibDockerBuild` をそのまま、push 先 image 名のみ変更
- `build-frontend` (`runs-on: ubuntu-24.04-arm`): `docker buildx build --platform linux/arm64 -t ghcr.io/h-orito/wolf-mansion-frontend frontend/` で push
  - 事前に `pnpm audit --prod --audit-level high` を実行 (high 以上があれば失敗)
- `deploy` (`runs-on: [self-hosted, k8s]`): `kubectl rollout restart deployment/wolf-mansion-api deployment/wolf-mansion-frontend`

emulation (qemu) を使わず arm64 ネイティブビルドにすることで時間短縮 + バイナリ整合性確保。

### 環境変数
- k8s Secret `wolf-mansion-secrets`: `JWT_SECRET`, DB password 等
- k8s ConfigMap `wolf-mansion-frontend-config`: `API_BASE_URL`
- k8s ConfigMap `wolf-mansion-api-config`: `ALLOWED_ORIGINS` (CORS ホワイトリスト)

## 5. E2E テスト (新規 `e2e/`)

**Playwright** を使用、`backend/` `frontend/` と並列のトップディレクトリ。

### ディレクトリ構成
```
wolf-mansion/
  backend/
  frontend/
  e2e/
    package.json
    .npmrc                       (frontend と同じ ignore-scripts=true 等)
    playwright.config.ts
    tests/
      auth/
        login.spec.ts
        logout.spec.ts
      village/
        create-village.spec.ts
        participate.spec.ts
        say.spec.ts
        ability.spec.ts
        daychange.spec.ts        日付更新後の表示確認
        footstep.spec.ts         足音の隠蔽確認 (進行中 vs エピローグ)
      admin/
        ...
    fixtures/
      users.ts                   テスト用 player の認証情報
      villages.ts
    helpers/
      api.ts                     テスト前準備で API を直叩きしてセットアップ
      db.ts                      DB を初期状態にリセット
```

### 構成方針

- **テスト対象環境**: docker compose で MySQL + backend + frontend を立ち上げ、Playwright が `http://localhost/wolf-mansion` を叩く形が基本
- **依存管理**: `e2e/package.json` で `@playwright/test`、`pnpm install --frozen-lockfile --ignore-scripts`、`.npmrc` も frontend と同等
- **ブラウザ**: chromium / firefox / webkit から chromium のみ (本番想定が chromium 系で十分)
- **DB セットアップ**: `e2e/helpers/db.ts` で `dbflute_wolf_mansiondb/playsql/data/` の test data を投入、もしくは API 経由で fixture を投入
- **認証**: `playwright.config.ts` の `globalSetup` で各テスト用ユーザのログイン状態を `storageState` として保存、各テストで再利用
- **CI**: 別 workflow `.github/workflows/e2e.yml` を作成、PR 時に走らせる (将来課題、初期実装ではローカル実行のみで可)

### スコープ

第一段階のシナリオ:
1. ログイン / ログアウト
2. 村作成 → 参加 → 発言 → 退村
3. 進行中に他者の足音が隠蔽されること、エピローグで開示されること
4. 能力使用 → 翌日の結果反映
5. 管理者画面で村ステータス操作

WebSocket/SSE 未導入なので、リアルタイム性の e2e は polling 待ち (`page.waitForResponse` 等) で代替。

## 6. 実装ステップ (各 1 PR 目安)

1. **JWT 化 + context-path 変更 + Twitter/Slack 削除**
   - 編集: `backend/src/main/kotlin/com/ort/app/fw/security/WolfMansionWebSecurityConfig.kt`, `backend/build.gradle.kts`, `backend/src/main/resources/config/application.yml`, `backend/src/main/kotlin/com/ort/app/fw/util/WolfMansionUserInfoUtil.kt`
   - 新規: `backend/src/main/kotlin/com/ort/app/fw/security/JwtTokenService.kt`, `backend/src/main/kotlin/com/ort/app/api/auth/AuthController.kt`, `backend/src/main/kotlin/com/ort/app/domain/model/auth/RefreshToken*.kt`, `backend/src/main/kotlin/com/ort/app/infrastructure/datasource/auth/RefreshTokenDataSource.kt`, refresh_token テーブル DDL
   - 削除: `backend/src/main/kotlin/com/ort/app/infrastructure/twitter/`, `backend/src/main/kotlin/com/ort/app/infrastructure/slack/` および関連設定/呼出
   - Thymeleaf Controller は一旦コンパイル可能な状態に stub 化 (空 `@RestController` でも可)
2. **SpringDoc OpenAPI 導入 + auth endpoint 整備 + CORS**
   - 新規: `backend/src/main/kotlin/com/ort/app/fw/config/CorsConfig.kt`
   - 環境変数 `ALLOWED_ORIGINS` 読込
3. **Frontend 初期化 + サプライチェーン対策**
   - `pnpm create react-router`, Tailwind v4, TanStack Query, Zustand, openapi-typescript パイプライン
   - `frontend/.npmrc` (`ignore-scripts=true` 等)、`packageManager` フィールド設定
   - 既存画像のうち favicon/OGP/ロゴだけ `frontend/public/img/` へ配置
4. **ログイン画面 + 認証ガード** (`routes/login.tsx`, `routes/_auth.tsx`, `lib/api/client.ts`)
5. **村一覧 + トップ** (`/api/v1/villages` REST 化 + 新 `api/response/village/*.kt` + `api/request/village/*.kt` → `routes/_index.tsx`, `routes/villages._index.tsx`)
6. **村画面 read-only + polling + 足音隠蔽実装** (`/api/v1/villages/{id}`, `/messages`, `/footsteps`、`VillageFootstepView` の `shouldRevealOwner` 判定)
7. **村画面 操作系** (`VillageSayController`, `VillageAbilityController`, `VillageParticipateController`, `VillageRpController` を REST 化、対応 `~Body` 作成)
8. **残り画面** (新規村作成、プレイヤー、admin、creator、chara、skill、village-record)
9. **Thymeleaf / 旧コード完全削除**
   - `backend/src/main/resources/templates/` 全削除
   - `backend/src/main/resources/static/` 全削除
   - `backend/src/main/kotlin/com/ort/app/api/view/` 全削除
   - 未参照になった `api/helper/` の Thymeleaf 専用ヘルパー削除
   - `app.original-image.basedir` / `baseurl` 設定削除
   - `backend/build.gradle.kts` から thymeleaf 系依存除去
10. **E2E 初期化** (`e2e/` 作成、Playwright セットアップ、主要シナリオ実装)
11. **Dockerfile / k8s manifest / GitHub Actions 更新 + cutover**
    - frontend Dockerfile (bookworm-slim, arm64)
    - `.github/workflows/deploy-ocl.yml` を 2 job 並列 (api / frontend) に
    - `.k8s/` 配下に manifest 配置
    - 画像の外部移管 (ユーザ側で実施) 完了確認

## Critical Files

- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/src/main/kotlin/com/ort/app/fw/security/WolfMansionWebSecurityConfig.kt` — security config 全面書き換え (JWT resource server, CORS)
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/src/main/kotlin/com/ort/app/fw/security/UserInfoService.kt` — そのまま継続利用 (BCrypt 検証)
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/src/main/kotlin/com/ort/app/fw/util/WolfMansionUserInfoUtil.kt` — 内部実装のみ差し替え、シグネチャ維持
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/src/main/kotlin/com/ort/app/domain/service/ability/HideDomainService.kt` — 足音隠蔽ロジックの整合先 (DTO 構築でも参照)
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/build.gradle.kts` — 依存変更 (thymeleaf 除去, JWT/SpringDoc 追加)
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/src/main/resources/config/application.yml` — context-path 変更, 静的配信無効化
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/.github/workflows/deploy-ocl.yml` — 2 イメージビルド対応 (arm64)
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/dbflute_wolf_mansiondb/playsql/replace-schema-10-basic.sql` — refresh_token テーブル追加 (追記後 `cd backend/dbflute_wolf_mansiondb && ./manage.sh 1` で反映)
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/src/main/kotlin/com/ort/app/infrastructure/twitter/` — 削除対象
- `/Users/h-orito/ort/wolf/workspace/wolf-mansion/backend/src/main/kotlin/com/ort/app/infrastructure/slack/` — 削除対象

## Verification

各ステップで以下を実行して動作を確認する。

### Backend
```bash
cd backend
./gradlew build -x test            # コンパイル通過
./gradlew test                     # 既存テスト通過
./gradlew bootRun                  # ローカル起動
curl -i -X POST http://localhost:8089/wolf-mansion-api/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"userId":"...","password":"..."}'
# → 200 + Set-Cookie: access_token, refresh_token
curl -i http://localhost:8089/wolf-mansion-api/api/v1/auth/me \
  --cookie "access_token=..."
# → 200 + {user: {...}}
curl http://localhost:8089/wolf-mansion-api/v3/api-docs   # OpenAPI spec
```

### Frontend
```bash
cd frontend
pnpm dev                           # http://localhost:5173/wolf-mansion で起動
pnpm gen:api                       # OpenAPI 型生成
pnpm build && pnpm start           # SSR 本番ビルド確認
```
- ブラウザで `/wolf-mansion/login` → ログイン → `/wolf-mansion/villages` 表示
- DevTools Network で `/wolf-mansion-api/api/v1/...` への fetch を確認
- DevTools Application で `access_token` (httpOnly) Cookie を確認
- View Source で SSR された HTML に村一覧が含まれることを確認 (SEO 確認)
- 30 秒待って村画面が再 fetch されることを確認 (TanStack Query polling)

### k8s
```bash
kubectl apply -f .k8s/
kubectl rollout status deployment/wolf-mansion-api
kubectl rollout status deployment/wolf-mansion-frontend
curl https://wolfort.dev/wolf-mansion-api/api/v1/auth/me   # 200
curl https://wolfort.dev/wolf-mansion/                     # SSR HTML
```

### 既存データでの動作確認
- 本番 DB のコピーで既存ユーザ ID/パスワードでログイン成功すること (BCrypt そのまま使えるため成功するはず)
- 既存村が一覧/詳細で表示されること
- 既存村で発言/能力使用ができること
- 進行中の村で他者の足音が隠蔽され、エピローグで開示されること

### E2E
```bash
cd e2e
pnpm install --frozen-lockfile --ignore-scripts
pnpm exec playwright install --with-deps chromium
pnpm test
```

### サプライチェーン
```bash
cd frontend && pnpm audit --prod --audit-level high   # CI で blocking
cd e2e      && pnpm audit --prod --audit-level high
```
