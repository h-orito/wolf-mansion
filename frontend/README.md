# wolf-mansion frontend

## Getting Started

```bash
npm run dev
```

## Open

http://localhost:3000/wolf-mansion

## backend型定義ファイル更新

backendを起動した状態で行うこと

```bash
npx openapi-typescript http://localhost:8089/wolf-mansion/v3/api-docs -o src/lib/openapi-typescript/wolf-mansion/schema.d.ts
```
