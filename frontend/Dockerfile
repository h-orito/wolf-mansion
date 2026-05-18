# syntax=docker/dockerfile:1.7
#
# wolf-mansion frontend image
# - bookworm-slim ベース (alpine の musl 起因問題を避ける)
# - linux/arm64 を明示 (OCL k8s ノードが arm64)
# - pnpm を corepack で固定バージョン、post-install スクリプト抑止
# - 非 root ユーザで実行
#
FROM --platform=linux/arm64 node:22-bookworm-slim AS build
WORKDIR /app
ENV CI=true PNPM_HOME=/pnpm PATH=/pnpm:$PATH
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
# react-router-serve を直接呼び出し (pnpm 経由を避けて軽量化)
CMD ["node_modules/.bin/react-router-serve", "./build/server/index.js"]
