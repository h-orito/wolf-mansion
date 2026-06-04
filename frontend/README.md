# wolf-mansion frontend

人狼ゲーム「wolf-mansion」のフロントエンド。monorepo の `frontend/`。
React Router v7 (framework mode, SSR) + Vite + TailwindCSS v4 + TypeScript。
backend (REST API) は `../backend`。移行計画は [`../doc/migration/04-frontend.md`](../doc/migration/04-frontend.md)。

## 必要ランタイム

- Node.js 22 系 / pnpm 10 系（`packageManager` 固定）

## セットアップ & 開発

いずれも `frontend/` で実行する:

```bash
pnpm install        # 依存インストール (.npmrc: ignore-scripts=true / サプライチェーン対策)

pnpm dev            # 開発サーバ (Vite + HMR)、http://localhost:5173
pnpm build          # 本番ビルド (SSR: build/client + build/server)
pnpm start          # ビルド成果物を react-router-serve で起動

pnpm typecheck      # react-router typegen + tsc
pnpm lint           # oxlint
pnpm format         # oxfmt --write（整形）
pnpm format:check   # oxfmt --check
```

## 技術スタック

- **ルーティング / SSR**: React Router v7（framework mode、`react-router.config.ts` で `ssr: true`）
- **ビルド**: Vite + `@tailwindcss/vite`（Tailwind CSS v4）
- **server state**: `@tanstack/react-query`（`app/root.tsx` で `QueryClientProvider` を配線）
- **UI state**: `zustand` / **フォーム**: `react-hook-form` + `zod` / **アイコン**: `@heroicons/react`
- **lint/format**: oxlint + oxfmt（`.oxlintrc.json` / `.oxfmtrc.json`）

## メモ

- バージョンは pnpm の `minimumReleaseAge`（14 日）制約に従う。react-router 系は現状 `7.15.1` 固定（7.16 が制約を満たし次第 v8 future flags と共に更新予定）。
- 本番コンテナ（`Dockerfile`）は cutover（Step 11）で `node:22-bookworm` + pnpm multistage として用意する（[`../doc/migration/06-infra-deploy.md`](../doc/migration/06-infra-deploy.md)）。
- OpenAPI → TS 型生成（`gen:api`）は backend の SpringDoc 整備後に追加する。
