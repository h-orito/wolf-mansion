# 画面: About (本サイトは)

> 関連する情報ページ: [rule.md](rule.md) / [faq.md](faq.md) / [practice.md](practice.md) / [announce.md](announce.md)。イントロは [intro.md](intro.md)。

## 概要

- **URL (既存)**: `GET /about`
- **テンプレート**: `about.html`
- **担当 JS**: `index.js` (共通スクリプト, `about.html:8`)
- **Controller**: `AboutController.about` (`AboutController.kt:11-12`) — model 注入なし、`"about"` を返すのみ
- **対象ユーザー**: 全員 (公開)
- **ページタイトル**: `本サイトは`

## 1. 機能 / 出来ることリスト

- サイト概要・特殊ルール (人狼館の事件簿村) の説明・注意事項・キャラチップ規約等の閲覧のみ (**完全静的**)

## 2. 表示要素・UI 状態

静的セクション (`about.html`):

- 本サイトは (サイト概要、人狼BBS への外部リンク)
- 人狼館の事件簿村とは (考案者ブログ・`/intro`・`/rule` への内部リンク)
- 注意事項 (マナー / アクセス禁止措置)
- キャラチップについて (著作権・SNS アップロード・いらすとや)
- 自分でキャラクター画像を用意する村について (`#original` — オリジナルキャラ村の規約、画像サイズ 60px 倍数・100kByte 制限等)
- タイトルロゴについて (フォント作者クレジット)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| GET | `/about` | About ページ (SSR、静的) |

## 4. 既存 JS の挙動

- なし (静的)。`index.js` (共通) のみ読み込み

## 5. 権限による分岐

- なし (全員同一・公開)

## 6. 認可マスク

- なし

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/about`

## 8. 関連 e2e ケース候補

- [ ] About ページ smoke 表示 (リンク切れ・大崩れがないか)
- [ ] `/intro` `/rule` への内部リンク遷移

## メモ / 移行時の注意

- **完全静的なので React 側の静的ページ / MDX 的コンテンツ**として移植。SSR で取得 (認証不要)
- 内部リンク (`/intro` `/rule`) は React Router のパスに張り替え
- 「自分でキャラクター画像を用意する村」規約 (`#original`) は new-village の画像アップロード仕様と整合させる ([new-village.md](new-village.md))
- 画像は静的アセット (frontend public/ へ移管、[06-infra-deploy.md](../06-infra-deploy.md))
