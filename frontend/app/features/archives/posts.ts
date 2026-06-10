/**
 * エイプリルフール企画アーカイブ (2ch スレ風ページ) の投稿データ型。
 * 実データは `scripts/extract-april-threads.mjs` が SSR テンプレートから生成し、
 * 各 route 配下の `posts.ts` に置く (announce の releases.ts と同じ運用)。
 */

/** レスアンカー (>>N)。表示番号とリンク先 DOM id が食い違う投稿があるため別々に持つ。 */
export type ResAnchorSegment = { anchor: number; target: string };

/** SPA 内リンク。text は原文の表示文字列 (`ttps://...` 表記など) をそのまま使う。 */
export type LinkSegment = { to: string; text: string };

/**
 * 描画時にログイン状態や村一覧 API で内容が変わる動的ブロック。
 * - `authLinks`: ログイン状態で変わるリンク群 (マイページ/ログアウト or ID登録/ログイン)
 * - `villageList`: 未終了村の一覧行
 * - `createVillageLink`: 村作成リンク (村を建てられるユーザーのみ)
 */
export type DynamicSegment = { kind: "authLinks" | "villageList" | "createVillageLink" };

/** テキスト。`\n` は改行 (`<br>`) として描画する。 */
export type TextSegment = { text: string };

export type AprilSegment = TextSegment | ResAnchorSegment | LinkSegment | DynamicSegment;

export interface AprilPost {
  /** レス番号 (1001 もある)。 */
  no: number;
  /** 名無し表示名。 */
  name: string;
  /** 投稿日時。1001 (スレ終了レス) には無い。 */
  datetime?: string;
  /** 投稿者 ID。1001 には無い。 */
  posterId?: string;
  /** スレ主 (ID を赤で表示する)。 */
  isOwner?: boolean;
  /** レスアンカーの飛び先になる DOM id (id 属性を持つ投稿のみ)。 */
  anchorId?: string;
  /** 本文の要素種別。`p` は下余白あり / `div` は無し (原文の余白差を保持)。 */
  bodyTag: "p" | "div";
  body: AprilSegment[];
}
