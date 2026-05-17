# PR #6 レビュー

JWT 認証の骨格は正しく実装されており、refresh token rotation・SHA-256 ハッシュ保管・httpOnly Cookie など主要な設計判断は妥当。一方で、ログイン失敗時の HTTP ステータスが 500 になる可能性・Cookie の `Secure=true` によるローカル開発の破綻・rotation のアトミック性欠如など、機能上の問題が複数あるため must-fix が必要。DBFlute 自動生成差分は実害のある変更なし（`codeOf()` → `of().orElse(null)` はスタイル変更でセマンティクス同等）。

## must-fix

- [ ] **`BadCredentialsException` を throw しても 401 にならない** (`backend/src/main/kotlin/com/ort/app/api/auth/AuthController.kt:62,66,69,72`)
  - `AuthController` は `@RestController` だが、`BadCredentialsException` を処理する `@ExceptionHandler` / `@RestControllerAdvice` が存在しない。Spring MVC はこれをデフォルトで 500 にマッピングする（`BearerTokenAuthenticationEntryPoint` はフィルター層の例外のみを担う）。`@ResponseStatus(HttpStatus.UNAUTHORIZED)` を持つカスタム例外か、`@RestControllerAdvice` に `@ExceptionHandler(BadCredentialsException::class)` を追加する必要がある。

- [ ] **refresh rotation がアトミックでない** (`backend/src/main/kotlin/com/ort/app/api/auth/AuthController.kt:82-87`)
  - `refreshTokenRepository.revoke(stored.id)` → `issueCookies()` の間に例外が発生した場合、旧トークンは失効済みで新トークンは未発行となりユーザーが強制ログアウトされる。また `AuthController` 自体に `@Transactional` がなく、revoke と register が別トランザクションで実行される。Controller 内を `@Transactional` で囲むか、Coordinator/Service 層に処理を移してトランザクション境界を設けるべき。

- [ ] **Cookie `Secure=true` でローカル HTTP 環境でログインできない** (`backend/src/main/kotlin/com/ort/app/api/auth/AuthController.kt:115,125`)
  - `buildAccessCookie` / `buildRefreshCookie` は無条件に `.secure(true)` を設定するため、`http://localhost` では Set-Cookie が無視されブラウザに届かない。プロファイルまたは環境変数で切り替えるか、テスト計画通り E2E で検証するならその前に対処が必要。

## should-fix

- [ ] **`REFRESH_COOKIE_PATH` がコンテキストパスをハードコードしている** (`backend/src/main/kotlin/com/ort/app/api/auth/AuthController.kt:41`)
  - `"/wolf-mansion-api/api/v1/auth"` と直書きされている。コンテキストパスが変わると壊れる。`request.contextPath + "/api/v1/auth"` で動的に取得するか、`@Value("\${server.servlet.context-path}")` を使うべき。

- [ ] **`jwtDecoder()` が呼び出しごとに新インスタンスを生成するシグネチャになっている** (`backend/src/main/kotlin/com/ort/app/fw/security/JwtTokenService.kt:75`)
  - `@Bean` なしの通常メソッドなので、`filterChain` 内での1回呼び出しであれば実害はない。ただし `SecurityFilterChain` が複数定義された場合や将来のリファクタ時に二重生成のリスクがある。`@Bean` で `JwtDecoder` を直接提供するか、`lazy` プロパティに変更する方が安全。

- [ ] **`deleteExpired` / `revokeAllByPlayerId` が呼ばれる箇所がない** (`backend/src/main/kotlin/com/ort/app/domain/model/auth/RefreshTokenRepository.kt:14,12`)
  - `deleteExpired` は `@Scheduled` などで定期的に呼ばれないと `refresh_token` テーブルが無限に肥大化する。このステップで Scheduler まで実装する必要はないが、少なくとも呼び出し経路の不在をコメントか TODO で明示すること。`revokeAllByPlayerId` も利用箇所がなく、存在理由が不明瞭。

- [ ] **`issueCookies` で DB 書き込み後にレスポンスヘッダーを設定しているが例外時にヘッダーが中途半端に送信される可能性** (`backend/src/main/kotlin/com/ort/app/api/auth/AuthController.kt:101-107`)
  - `refreshTokenRepository.register()` が成功してからレスポンスヘッダーを付与しているため順序は問題ないが、`register()` が例外を投げた場合に `issueCookies` を呼び出した `login()` / `refresh()` が 500 を返しつつ `addHeader` が一部実行される競合状態を排除するためにも、@Transactional による一貫性確保が必要（上の must-fix と関連）。

## nits

- [ ] **`JwtTokenService.verifier` フィールドが宣言されているが使われていない** (`backend/src/main/kotlin/com/ort/app/fw/security/JwtTokenService.kt:46`)
  - `NimbusJwtDecoder` が検証を担うため `MACVerifier` は不要。コンパイル警告を避けるためにも削除を推奨。

- [ ] **`jwtAuthenticationConverter()` 内で `empty` 変数が `converter` に設定されていない** (`backend/src/main/kotlin/com/ort/app/fw/security/WolfMansionWebSecurityConfig.kt:98-100`)
  - `val empty = JwtGrantedAuthoritiesConverter(); empty.setAuthoritiesClaimName("__none__")` と作成されているが `converter` に適用されていない（デッドコード）。`setJwtGrantedAuthoritiesConverter` でカスタムラムダを直接設定しているためデフォルト scope 権限は実際には付与されないが、コードの意図が読み取れないため削除するか、コメントを修正すること。

- [ ] **`ExceptionControllerAdvice` に `@ControllerAdvice` がない** (`backend/src/main/kotlin/com/ort/app/fw/exception/ExceptionControllerAdvice.kt:9`)
  - これは PR 追加分ではなく既存のバグだが、このステップで例外処理を追加する際に混乱の元になる。must-fix 対応時に合わせて確認することを推奨。
