# 04. Frontend

## 技術スタック

- React Router v7 (framework mode, **SSR 有効**)
- TailwindCSS v4
- Vite
- `@tanstack/react-query` (server state)
- Zustand (UI state)
- `openapi-typescript` (型生成)
- heroicons (アイコン)
- **react-hook-form + zod** (フォーム / バリデーション)

## 状態管理の使い分け (確定)

- **server state**: `@tanstack/react-query` に一元化
  - API 由来データは全て TanStack Query のキャッシュに乗せる (`useQuery` / `useMutation`)
  - 認証情報 (me) も `useMe` hook で TanStack Query 経由で取得 (Zustand に mirror しない)
- **UI state**: `Zustand` のみ
  - モーダル開閉、サイドバー表示、トースト等の **client-only state**
  - サーバーデータの mirror は禁止 (二重管理の温床)
- フォーム内ローカル state は `react-hook-form` 側に閉じる

## 認証状態 (me) の取得経路 (確定)

- **ルートレイアウト (`app/root.tsx`)** で `useMe` を呼ぶ
  - `/api/v1/auth/me` を **CSR (hydration 後)** で叩く
  - 401 の場合は `user = null`
- 認証必要ページは個別に `<RequireAuth>` wrapper でガードし、`user === null` なら `useNavigate("/login")` でリダイレクト
- 認証不要ページではヘッダー表示等で `user` を参照しつつ、ログイン未済時の挙動を出し分け

## エラーハンドリング (確定)

- **Route 単位の重いエラー** (loader 失敗、想定外例外) は React Router の `ErrorBoundary` で専用画面を表示
- **CSR の useQuery / useMutation のエラー**は `QueryClient` の `defaultOptions.queries.onError` (および mutation) に集約し、共通 toast を出す
  - トーストライブラリは Step 1 で選定 (Sonner 等を有力候補に)
- **401 だけ特例**: 共通 onError で `/login` に navigate (refresh が必要なら refresh 試行 → 失敗で login)
- バリデーションエラー (zod 結果) はフォーム個別表示。グローバル toast には流さない

## フォーム / バリデーション (確定)

- **react-hook-form** + **zod** をデフォルト採用
- API レスポンスの型 (openapi-typescript 由来) とは別に、クライアント側で zod スキーマを定義する
  - 例: パスワード強度規約はクライアント側スキーマで先行チェック
- 送信は `react-hook-form` の `handleSubmit` → TanStack Query の `useMutation` を呼ぶ
- サーバー側のバリデーションエラー (`ProblemDetail` の properties 等) はフォーム側でフィールドエラーに振り分ける規約を Step 1 で決める

## lint / format

- **lint: oxlint をメイン**で採用
  - Rust 製で高速。React Hooks 等の主要ルールはカバーされる想定
  - 初期ルールセットは **recommend ベース** + `react-hooks` 系を有効化
  - **jsx-a11y / @tanstack/query 系**は oxlint で cover されない領域があるため、Step 2 で必要性を見て ESLint plugin の追加採用を検討 (oxlint で代替が cover されればそれを使う)
- **format: oxfmt (oxc format) を採用**
  - oxlint と同じ oxc エコシステムでツールチェイン統一
  - Prettier との切り分けはせず、原則 oxfmt に寄せる
- **oxlint / oxfmt で cover できない要件が出てきた場合のみ、ユーザー承認を得てから別ツール (ESLint plugin, Prettier 等) を追加採用**する
- 自動実行: **Claude Code の hooks** (PostToolUse 等) で Edit/Write 後に自動で lint/format を走らせる
  - 設定の詳細は [07-workflow.md](07-workflow.md) で扱う

## パッケージマネージャ (pnpm)

- **pnpm** を使用
- サプライチェーンアタック対策:
  - グローバルの `minimumReleaseAge` を活かしつつ慎重に作業する
  - `.npmrc` に `ignore-scripts=true` で post-install スクリプトのデフォルト実行を抑止
  - postinstall 等のスクリプトを持つパッケージは個別に精査してから有効化

## UI 方針

- **既存 UI を完全に踏襲**する。色・余白・細部に至るまで完全に同一にする
- 各画面の必要な機能を細かく調査し、最終的に e2e やチェックリストで確認できるよう進める
- **比較対象**: 既存実装が `http://localhost:8091/wolf-mansion/` でローカル稼働中 (DB はローカル DB を共有)
  - 新 frontend の各画面実装時、スクリーンショットや DOM 構造を既存画面と比較して整合を取る
  - Step 0 で取得したスクショ群 (`doc/migration/screens/<screen>.md` に添付) を一次基準にする

### 後続のモダナイズを見据えた実装方針

本移行後に **見た目のモダナイズ**を別フェーズで実施する予定。そのため移行作業中は **その場しのぎの class / inline style を避け、差し替えやすい構造で書く**ことを徹底する。

- **デザイントークン化**: 色 / 余白 / 角丸 / シャドウ / フォントサイズなどは Tailwind v4 の theme (CSS variables) に集約し、ページ毎に直値を埋め込まない
- **共通コンポーネント化**: ボタン / カード / モーダル / フォーム要素 / アバター / バッジ等、繰り返し現れる UI 単位は共通コンポーネントに切り出す
  - ページ固有の bare な div + class の積み上げは避ける
- **共通 CSS / utility は最小限に**、ただし共通化できるものは共通化する。Tailwind の `@apply` や CSS variables を使い、1 箇所変更で全体に波及する形を保つ
- **既存 UI 踏襲は「表面の見た目」のみ**であって、内部構造 (HTML 構造 / クラス名規約) は新規に整理してよい
- これらにより、モダナイズフェーズで **トークン値の変更 + 共通コンポーネントの差し替え**で全体を更新できるようにする

## 画面別の詳細

各画面ごとに、

- 必要な機能 / 出来ることリスト
- 表示要素・UI 状態
- 呼び出す API エンドポイント
- **対応する既存 JS (`src/main/resources/static/app/js/*.js`) の挙動** … 動的レンダリング / AJAX 通信 / クライアントバリデーション / フォーム制御 などは既存 JS に実装されているため、新 React 実装で再現するには JS 側の振る舞いも必ず読み解く
- 権限による分岐 (匿名 / ログイン済 / 村参加者 / 村主 / 管理者など)
- 認可によるマスク (足音情報、死亡理由等)
- 関連する e2e ケース

を整理する。粒度が大きくなったら `doc/migration/screens/<screen>.md` に分割する。

### 画面リストの棚卸し (Step 0 で実施)

- **Step 0 で一括棚卸し**する
  - `src/main/resources/templates/` 配下の Thymeleaf テンプレートと `api/` 配下の Controller を一覧化
  - 画面ごとに `doc/migration/screens/<screen>.md` を作成し、上記「画面別の詳細」のテンプレで埋める
  - 以降の step で 1 画面 (or 1 機能群) ずつ React に移植
- 確認前のたたき台 (Step 0 で正確に確定):
  - ログイン / 新規登録
  - ホーム (村一覧)
  - プロフィール / パスワード変更 / 戦績
  - 新規村作成
  - 村画面 (発言, 参加, 能力, 投票, RP, 設定変更, creator/admin 操作 ...)

## デザイントークン棚卸し (Step 0 で実施)

- **Step 0 で既存 CSS / Thymeleaf を読み込んで token を抽出**し `doc/migration/design-tokens.md` に記録
  - 抽出対象: 主要色 (役職別カラー / 陣営別カラー / 状態色) / 余白 / 角丸 / フォントサイズ / シャドウ など
- **Step 1 (frontend 初期設計)** で Tailwind v4 の theme (CSS variables) に落とし込む
  - Tailwind v4 の `@theme` ブロックで CSS variables として定義
  - ページ毎の直値埋め込みは禁止 (= モダナイズフェーズで token 値変更だけで全体更新できる構造)

## 静的アセット配置 (確定)

- 原則: **frontend repo の `public/` に同梱**して同じ frontend service から配信
- ただし以下は例外:
  - **キャラチップ画像**: 既に `wolfort.dev/wmansion` 配下に外部公開済みで全て URL 参照に置き換え済み。**frontend には同梱しない**。コード上は環境変数 (`CHARA_BASE_URL` 等) でベース URL を持つ
- Step 0 の静的リソース棚卸しで個別に決定する

## 国際化 (確定)

- **i18n は導入しない**
- テキストは日本語ハードコードでよい (現行と同じ)
- 将来余地として react-i18next 等を入れる計画は **本移行スコープ外**

## 未確定事項 / 要調査

- [ ] heroicons 以外の必要アイコン (絵文字・装飾) の素材 — Step 0 で棚卸し
- [ ] トースト UI ライブラリ選定 (Sonner / react-hot-toast / 自前 等) — Step 1
- [ ] oxlint で cover されない欲しいルール (`@tanstack/eslint-plugin-query`, jsx-a11y 等) の必要性洗い出し — Step 2
- [ ] oxfmt の対応範囲確認 (TS/TSX/CSS / Tailwind v4 directive / RR v7 構成) と未対応ファイルタイプの扱い — Step 2
- [ ] 共通コンポーネントの初期ラインナップ (Button / Card / Modal / Form / Avatar / Badge ...) と配置場所 (`app/components/ui/` 等) — Step 1
- [ ] サーバー側バリデーションエラー (`ProblemDetail` properties) → react-hook-form のフィールドエラー振り分け規約 — Step 1
