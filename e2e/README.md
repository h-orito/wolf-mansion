# e2e

wolf-mansion の E2E テスト (Playwright)。**ローカル動作確認専用** — CI では走らせない。

正本: [`doc/migration/05-e2e.md`](../doc/migration/05-e2e.md)

> Step 2.4 時点では **雛形のみ** (config + smoke 1 本)。認証 / 村ライフサイクル / 認可マスク 等の
> 本格的なテストケースは村画面が動く Step 8 以降に [`doc/migration/scenarios/`](../doc/migration/scenarios/README.md)
> を起点として authoring する。

## セットアップ

```bash
cd e2e
pnpm install                       # .npmrc の ignore-scripts=true により browser は入らない
pnpm exec playwright install chromium  # browser バイナリを別途取得 (postinstall を使わない)
```

- パッケージマネージャは **pnpm** (frontend と同じ `pnpm@10.33.0` を `packageManager` で固定)
- `@playwright/test` は **1.60.0 に固定**。グローバル pnpm 設定 `minimumReleaseAge`(14日) により、
  リリース 14 日未満のバージョンは install 時に弾かれるため (frontend で RR を 7.15.1 に固定したのと同じ理由)。
  bump する際は 14 日以上前のバージョンを選ぶこと。

## 実行

```bash
cd e2e
pnpm test            # playwright test (webServer を自動起動)
pnpm test:ui         # UI モード
pnpm report          # 直近の HTML レポートを表示
```

### webServer / ポート

`playwright.config.ts` の `webServer` が backend / frontend を**自動起動**する。
通常起動と**別ポート**を使うため、開発サーバ (backend 8089 / frontend 5173) を止めずに e2e を回せる。

| | 通常起動 | e2e |
| --- | --- | --- |
| backend | 8089 | **18089** (`./gradlew bootRun --args='--server.port=18089'`) |
| frontend | 5173 | **15173** (`pnpm dev --port 15173`) |

- `baseURL` は frontend 側 (`http://localhost:15173`)
- 既に同ポートで起動済みのサーバがあれば `reuseExistingServer` で使い回す
- backend は `application.yml` デフォルト設定で起動 → **ローカル MySQL (port 4306) のあいのり DB** を共有する
  - 既存稼働環境 (`http://localhost:8091/wolf-mansion/`) と同じ DB を見るため、e2e の結果が既存画面にも反映される / 逆も成立

## DB / テストデータ規約 (本格 authoring 時)

- **DB はあいのり前提**。リセット (mysqldump 等) はしない
- 各テストは **setup/teardown で独立データ**を作る (自分用の村 / 参加者を作成し `afterEach` で削除 or 廃村)
- 他テストと被らない名前 (`{testName}-{timestamp}` 等) を使い、Playwright のデフォルト並列実行を活かす
- 順序依存が必要な一連の流れは `test.describe.serial` で明示
- テスト用ユーザーはローカル DB の既存プレイヤー (全員 password `testuser`)

## 失敗時の artifacts

- `trace: 'on-first-retry'` / `screenshot: 'only-on-failure'` / `video: 'off'`
- 保存先は Playwright デフォルト (`test-results/` `playwright-report/`)。いずれも `.gitignore` 済で git 管理外
