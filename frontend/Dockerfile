# 本番は arm64。マルチアーキの node:* より arm64v8/node:* のほうが先にセキュリティ修正版へ更新されるのでこちらを使う。Alpine 系は k3s で DNS 問題が出るので使わない
FROM arm64v8/node:22-bookworm-slim AS build
RUN corepack enable
WORKDIR /app
COPY package.json pnpm-lock.yaml .npmrc ./
RUN pnpm install --frozen-lockfile
COPY . .
RUN pnpm build

FROM arm64v8/node:22-bookworm-slim
RUN corepack enable
WORKDIR /app
COPY package.json pnpm-lock.yaml .npmrc ./
RUN pnpm install --frozen-lockfile --prod
COPY --from=build /app/build ./build
EXPOSE 3000
CMD ["pnpm", "start"]
