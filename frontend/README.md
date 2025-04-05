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

## Cline

see https://github.com/nickbaumann98/cline_docs/blob/main/prompting/custom%20instructions%20library/cline-memory-bank.md

- memory-bank配下にCline向けの構造化されたドキュメントが配置されている（README.md参照）

- 自動的にClineによって更新されるが、「メモリバンクを更新して」など、指示すれば最新情報で書き換えてくれる

- 現在取り組んでいる機能も記録されるので、「作業を再開して」と依頼すれば再開してくれる

- 久々に動かすときも、「follow your custom instructions」と指示すれば、過去の文脈を読み込んだ状態で作業を再開できる
