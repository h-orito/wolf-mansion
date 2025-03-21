# 技術コンテキスト (Technical Context)

## 使用技術

WOLF MANSIONのフロントエンドは、以下の主要な技術を使用して構築されています：

### コア技術

- **Next.js**: Reactベースのフレームワークで、サーバーサイドレンダリング（SSR）とクライアントサイドレンダリング（CSR）の両方をサポート
- **React**: UIコンポーネントを構築するためのJavaScriptライブラリ
- **TypeScript**: 静的型付けを提供するJavaScriptのスーパーセット
- **Tailwind CSS**: ユーティリティファーストのCSSフレームワーク

### 主要ライブラリ

- **FontAwesome**: アイコンライブラリ
- **openapi-typescript**: OpenAPI仕様からTypeScript型定義を生成するツール

## 開発環境セットアップ

### 必要条件

- Node.js (推奨バージョン: 18.x以上)
- npm (Node.jsに付属)

### 開発サーバーの起動

```bash
# 依存関係のインストール
npm install

# 開発サーバーの起動
npm run dev
```

開発サーバーは、デフォルトで http://localhost:3000/wolf-mansion でアクセス可能です。

### バックエンド型定義ファイルの更新

バックエンドAPIの型定義を更新するには、バックエンドサーバーが起動している状態で以下のコマンドを実行します：

```bash
npx openapi-typescript http://localhost:8089/wolf-mansion/v3/api-docs -o src/lib/openapi-typescript/wolf-mansion/schema.d.ts
```

## 技術的制約

1. **バックエンド依存性**: フロントエンドはバックエンドAPIに依存しており、バックエンドが利用できない場合、多くの機能が動作しません。

2. **ブラウザ互換性**: モダンブラウザ（Chrome、Firefox、Safari、Edge）をサポートしていますが、古いブラウザでは一部の機能が正常に動作しない可能性があります。

3. **レスポンシブデザイン**: モバイルデバイスからデスクトップまで様々な画面サイズに対応していますが、最適な体験はデスクトップで提供されます。

## 依存関係

主要な依存関係は以下の通りです（package.jsonから抜粋）：

```json
{
  "dependencies": {
    "next": "^14.0.0",
    "react": "^18.2.0",
    "react-dom": "^18.2.0",
    "@fortawesome/fontawesome-svg-core": "^6.4.2",
    "@fortawesome/free-solid-svg-icons": "^6.4.2",
    "@fortawesome/react-fontawesome": "^0.2.0"
  },
  "devDependencies": {
    "typescript": "^5.2.2",
    "tailwindcss": "^3.3.3",
    "postcss": "^8.4.31",
    "autoprefixer": "^10.4.16",
    "eslint": "^8.52.0",
    "prettier": "^3.0.3",
    "openapi-typescript": "^6.7.0"
  }
}
```

## 環境変数

アプリケーションは以下の環境変数を使用します：

- `NEXT_PUBLIC_API_BASE`: バックエンドAPIのベースURL

開発環境では、`.env.development`ファイルに環境変数を設定できます。

## ビルドとデプロイ

### 本番ビルド

```bash
# 本番用ビルドの作成
npm run build

# ビルドの実行
npm start
```

### Dockerを使用したデプロイ

プロジェクトにはDockerfileが含まれており、コンテナ化してデプロイすることができます：

```bash
# Dockerイメージのビルド
docker build -t wolf-mansion-frontend .

# コンテナの実行
docker run -p 3000:3000 wolf-mansion-frontend
```

## ファイル構造

```
/
├── public/               # 静的ファイル
│   └── images/           # 画像ファイル
├── src/                  # ソースコード
│   ├── app/              # Next.js App Router
│   │   ├── layout.tsx    # レイアウトコンポーネント
│   │   ├── page.tsx      # メインページ
│   │   └── ...           # その他のページコンポーネント
│   ├── components/       # 再利用可能なコンポーネント
│   │   ├── api/          # APIクライアント
│   │   └── ui/           # UIコンポーネント
│   └── lib/              # ユーティリティと型定義
│       └── openapi-typescript/ # OpenAPIから生成された型定義
├── .env.development      # 開発環境の環境変数
├── next.config.ts        # Next.js設定
├── tailwind.config.js    # Tailwind CSS設定
└── tsconfig.json         # TypeScript設定
```

この技術スタックと開発環境により、型安全で保守性の高いフロントエンド開発が可能になっています。
