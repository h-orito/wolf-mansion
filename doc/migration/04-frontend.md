# 04. Frontend

## 技術スタック

- React Router v7 (framework mode, **SSR 有効**)
- TailwindCSS v4
- Vite
- `@tanstack/react-query` (server state)
- Zustand (UI state)
- `openapi-typescript` (型生成)
- heroicons (アイコン)

## lint / format

- **lint: oxlint をメイン**で採用
  - Rust 製で高速。React Hooks 等の主要ルールはカバーされる想定
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

### 画面リスト (要確定)

要洗い出し。既存 `src/main/resources/templates/` の Thymeleaf テンプレート構成をベースに棚卸しする予定。例 (確認前):

- ログイン / 新規登録
- ホーム (村一覧)
- プロフィール / パスワード変更 / 戦績
- 新規村作成
- 村画面 (発言, 参加, 能力, 投票, RP, 設定変更, creator/admin 操作 ...)

## 未確定事項 / 要調査

- [ ] React Router v7 framework mode の SSR と TanStack Query / Zustand の組み合わせ方
- [ ] SSR loader 経由で API を呼ぶ際の Cookie 引き渡し
- [ ] エラーハンドリングのグローバル戦略 (TanStack Query の error boundary, RR の `ErrorBoundary`)
- [ ] 認証状態 (誰がログインしているか) の取得経路 (loader + Zustand?)
- [ ] フォーム / バリデーションのライブラリ選定 (react-hook-form 等)
- [ ] 国際化 (現状は日本語のみだが将来余地を残すか)
- [ ] heroicons 以外の必要アイコン (絵文字・装飾) の素材
- [ ] 静的アセット (キャラチップ画像等) の配置 (frontend? CDN?)
- [ ] oxlint で使うルールセット (recommend / 個別調整)
- [ ] oxlint で cover されない欲しいルール (`@tanstack/eslint-plugin-query`, jsx-a11y 等) の必要性洗い出し
- [ ] oxfmt の対応範囲確認 (TS/TSX/CSS / Tailwind v4 directive / RR v7 構成) と未対応ファイルタイプの扱い
- [ ] デザイントークンの初期セット (現行カラー / 余白 / 角丸 等の棚卸し) と Tailwind v4 theme への落とし込み
- [ ] 共通コンポーネントの初期ラインナップ (Button / Card / Modal / Form / Avatar / Badge ...) と配置場所 (`app/components/ui/` 等)
