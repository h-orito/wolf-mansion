# wolf-mansion frontend

人狼ゲーム wolf-mansion のフロントエンド (React Router v7 SSR + Vite + TailwindCSS v4)。

`feature/monorepo` で進行中の Spring Boot + RR v7 への移行に伴って新規作成。
将来は `https://wolfort.dev/wolf-mansion` で配信され、`/wolf-mansion-api` の REST API を叩く。

## スタック

- **React 19** + **React Router v7 framework mode** (SSR 有効)
- **Vite 8** + **@tailwindcss/vite v4**
- **TanStack Query 5** (server state)
- **Zustand 5** (UI state)
- **openapi-typescript** (API 型生成、`pnpm gen:api`)
- TypeScript / Node 22

## セットアップ

```bash
# pnpm の固定バージョンを corepack で有効化 (推奨)
corepack enable
corepack prepare pnpm@10.33.0 --activate

# 依存インストール (.npmrc の ignore-scripts=true を明示的に追加して安全側に倒す)
pnpm install --frozen-lockfile --ignore-scripts
```

## 開発

```bash
# dev server (http://localhost:5173/wolf-mansion)
pnpm dev

# 型チェック
pnpm typecheck

# production build
pnpm build && pnpm start

# OpenAPI 型生成 (backend を 8089 で起動してから)
pnpm gen:api
```

## サプライチェーン対策

`.npmrc` で以下を強制している:

- `ignore-scripts=true` — post-install スクリプトをデフォルト実行しない
- `audit=false` — pnpm 既定 audit は CI で `pnpm audit --prod --audit-level high` として明示実行
- 固定 registry (`registry.npmjs.org`)

`package.json` の `packageManager` で pnpm を pin。CI / Docker でも同バージョンを使う。
native binding を持つパッケージのみ `pnpm.onlyBuiltDependencies` で個別許可する。

### 依存追加時のルール

1. 週次ダウンロード数、GitHub スター、最終更新を確認
2. メジャー v0.x の小規模パッケージは原則避ける
3. `pnpm install <pkg>` 後、`pnpm-lock.yaml` の diff を必ずレビュー
4. CI で `pnpm audit --prod --audit-level high` が緑になることを確認

## 画像配置

- `public/favicon.ico` — favicon
- `public/img/ogp-top.png` — OGP 画像
- `public/img/april-top.png`, `april-top2.png` — エイプリルフール用
- `public/img/top.jpg`, `top-small.jpg` — ランディングのヒーロー画像
- `public/img/mansion01.jpg` 〜 `mansion04.jpg` — トップカルーセル用

キャラ画像など大量の画像は backend ではなく外部ホスティング (`https://wolfort.dev/wmansion/...`) で配信予定。

## ディレクトリ構成

```
frontend/
  app/
    root.tsx                ルートレイアウト + QueryClientProvider
    routes.ts               ルート定義
    routes/                 ページ
    features/               機能単位 (auth, village, ...)
    lib/
      api/
        client.ts           browserFetch / ssrFetch
        generated.ts        openapi-typescript で生成 (gitignore 対象)
      query.ts              QueryClient ファクトリ
      stores/               Zustand stores
    components/ui/          Tailwind 共通部品
  public/                   静的ファイル
  package.json
  vite.config.ts
  react-router.config.ts    basename: "/wolf-mansion", ssr: true
  Dockerfile                Step 11 で arm64 / bookworm-slim に書き換え予定
```
