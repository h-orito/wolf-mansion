FROM node:22-bookworm-slim AS build
RUN corepack enable
WORKDIR /app
COPY package.json pnpm-lock.yaml .npmrc ./
RUN pnpm install --frozen-lockfile
COPY . .
# VITE_* はビルド時にバンドルへインライン化されるため、build-arg で受け取る必要がある
ARG VITE_FIREBASE_API_KEY
ARG VITE_FIREBASE_DATABASE_URL
ARG VITE_FIREBASE_PROJECT_ID
RUN pnpm build

FROM node:22-bookworm-slim
RUN corepack enable
WORKDIR /app
COPY package.json pnpm-lock.yaml .npmrc ./
RUN pnpm install --frozen-lockfile --prod
COPY --from=build /app/build ./build
EXPOSE 3000
CMD ["pnpm", "start"]
