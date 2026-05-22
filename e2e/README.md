# e2e — wolf-mansion E2E テスト (Playwright)

`backend/` `frontend/` と並列のトップディレクトリ。Playwright で
frontend (`http://localhost:5173/wolf-mansion`) をブラウザ操作して検証する。

## 前提

E2E 実行前に以下を起動しておくこと:

| サービス | 起動方法 |
|---|---|
| MySQL (:4306) | `werewolf_mansiondb` がローカルで稼働していること |
| backend (:8089) | `cd backend && JAVA_HOME=<zulu-21> ./gradlew bootRun` |

frontend dev server (:5173) は `playwright.config.ts` の `webServer` が自動起動する
(既に起動済みなら `reuseExistingServer` で再利用)。

テストユーザはローカル DB の `testuser01` 〜 / `master`。パスワードは全員 `testuser`。

## セットアップ

```bash
cd e2e
pnpm install --frozen-lockfile
pnpm exec playwright install chromium
```

## 実行

```bash
pnpm test          # ヘッドレス実行
pnpm test:ui       # UI モード
pnpm report        # 直近の HTML レポートを開く
```

`globalSetup` が `testuser01` でログインし `.auth/player.json` に storageState を
保存する。認証が必要なテストはこれを再利用する。

## スコープ (第一段階)

- auth: ログイン成功 / 失敗、ログアウト、未認証ガード
- village: トップ / 村一覧の表示

村作成・参加・発言・能力・日付更新・足音隠蔽・admin 操作などの game-flow
シナリオは後続フェーズ (`.issues/` 参照)。CI workflow も後続。
