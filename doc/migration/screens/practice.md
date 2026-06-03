# 画面: 練習問題

> 関連する情報ページ: [rule.md](rule.md) / [about.md](about.md) / [faq.md](faq.md) / [announce.md](announce.md)。

## 概要

- **URL (既存)**: `GET /practice`
- **テンプレート**: `practice.html` (`fragments/flagment-message` フラグメントを利用)
- **担当 JS**: `index.js` (共通スクリプト, `practice.html:7`)
- **Controller**: `AboutController.practice` (`AboutController.kt:26-27`) — model 注入なし、`"practice"` を返すのみ
- **対象ユーザー**: 全員 (公開)
- **ページタイトル**: `人狼館の事件簿村ルール 練習問題`
- **主な導線**: イントロ ([intro.md](intro.md)) 末尾の「練習問題へ」ボタンから遷移 (`intro.html:293`)。ホームのナビタイルには無く、`/intro` が実質的な入口

## 1. 機能 / 出来ることリスト

- 部屋配置・足音から人狼/占い師候補を推理する練習問題 (3 問) の閲覧
- 各問の「答えを開く」で解答を開閉

## 2. 表示要素・UI 状態

- キャラのメッセージ風吹き出し (`fragments/flagment-message` フラグメント、`jimuzon` キャラで出題, `practice.html:24`)
- 練習問題 3 問 (`#q1`/`#q2`/`#q3`)。各問に部屋配置・足音の図と設問
- **答えの開閉**: `data-toggle="collapse"` + `data-target="#q{n}"` (Bootstrap collapse)。初期は `collapse` クラスで折りたたみ (`practice.html:42-43,78-79,117-118`)。**カスタム JS なし** (Bootstrap の標準挙動)
- 末尾に「おわり」(`practice.html:133`)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| GET | `/practice` | 練習問題ページ (SSR、静的) |

## 4. 既存 JS の挙動

- 練習問題固有 JS なし。解答開閉は Bootstrap collapse (`data-toggle`) で実現
- `index.js` (共通) のみ読み込み

## 5. 権限による分岐

- なし (全員同一・公開)

## 6. 認可マスク

- なし

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/practice`。特にメッセージ吹き出しと collapse の開閉

## 8. 関連 e2e ケース候補

- [ ] 練習問題ページ smoke 表示
- [ ] 「答えを開く」クリックで解答が展開する

## メモ / 移行時の注意

- **ほぼ静的なので React 側の静的ページ**として移植。解答開閉の挙動 (クリックで展開) は現状踏襲し、実装のみ Bootstrap collapse → React state トグルに置換 (見た目・操作は変えない)
- メッセージ吹き出し (`flagment-message`) は村画面のメッセージ表示コンポーネントと共通化の余地あり ([village-messages.md](village/village-messages.md))
- 部屋配置・足音の図は静的アセット。frontend public/ へ移管
