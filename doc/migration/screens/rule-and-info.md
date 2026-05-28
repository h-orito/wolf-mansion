# 画面: ルール・情報ページ群 (静的系)

> rule / about / faq / practice / announce をまとめて扱う。いずれも **ほぼ静的コンテンツ** (Controller はテンプレを返すのみ、`AboutController`)。

## 概要

| 画面 | URL | テンプレート | 備考 |
|---|---|---|---|
| ルール | `GET /rule` | `rule.html` + `rule/{camp,detail,judge,mansion,other,room,skill,status}.html` | `RuleContent()` を渡すが内容は静的リファレンス。`rule/skill.html` は 2256 行 (全役職詳細) |
| About | `GET /about` | `about.html` | 完全静的 |
| FAQ | `GET /faq` | `faq.html` | 完全静的 |
| 練習 | `GET /practice` | `practice.html` | 完全静的 |
| お知らせ | `GET /announce` | `announce.html` (965行) | 完全静的 (更新履歴の長い羅列) |

- **担当 JS**: なし
- **対象ユーザー**: 全員 (公開)

## 1. 機能 / 出来ることリスト

- 各種ドキュメント (ルール / サイト説明 / FAQ / 練習案内 / お知らせ) の閲覧のみ

## 2. 表示要素・UI 状態

- 静的テキスト + 画像 + 内部リンク中心
- ルールはタブ/セクション分割 (camp 陣営 / detail 詳細 / judge 勝敗判定 / mansion 館 / room 部屋 / skill 役職 / status 状態 / other その他)

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| GET | `/rule` `/about` `/faq` `/practice` `/announce` | 各静的ページ (SSR) |

## 4. 既存 JS の挙動

- なし (静的)

## 5. 権限による分岐

- なし (全員同一・公開)

## 6. 認可マスク

- なし

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/{rule,about,faq,practice,announce}`。特に rule のタブ/セクション構成

## 8. 関連 e2e ケース候補

- [ ] 各ページの smoke 表示確認 (リンク切れ・大崩れがないか)

## メモ / 移行時の注意

- **完全静的なので React 側の静的ページ / MDX 的コンテンツ**として移植。SSR で取得 (認証不要)
- `announce.html` (お知らせ) は更新履歴の羅列。今後の更新運用 (ハードコード継続 or データ化) を要検討だが、移行時は現状踏襲でよい
- `rule/skill.html` (役職詳細 2256行) は `skill.md` の役職データと内容が重複する可能性 → 役職マスタへの一元化は **モダナイズフェーズ**で検討 (本移行では現状踏襲)
- 画像は静的アセット (frontend public/ へ移管、[06-infra-deploy.md](../06-infra-deploy.md))
