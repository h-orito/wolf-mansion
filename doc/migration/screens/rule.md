# 画面: ルール

> 関連する静的情報ページ: [about.md](about.md) / [faq.md](faq.md) / [practice.md](practice.md) / [announce.md](announce.md)。役職一覧画面は [skill.md](skill.md)。

## 概要

- **URL (既存)**: `GET /rule`
- **テンプレート**: `rule.html` + フラグメント `rule/{mansion,detail,skill,judge,status,camp,room,other}.html`
- **担当 JS**: `index.js` (共通スクリプト。ルール固有 JS ではない, `rule.html:8`)
- **Controller**: `AboutController.rule` (`AboutController.kt:17-21`) — `RuleContent()` を model 注入
- **対象ユーザー**: 全員 (公開)

## 1. 機能 / 出来ることリスト

- 人狼館の事件簿村ルールの閲覧 (基本/詳細/役職詳細/勝敗判定/ステータス/陣営/部屋サイズ/その他)
- 目次 (TOC) アンカーから各セクションへジャンプ
- 役職一覧画面 (`/skill`) への導線 (`rule.html:112`)

## 2. 表示要素・UI 状態

- **目次 (well)**: 全セクションへのアンカーリンク (`rule.html:16-86`)。役職詳細は `content.campList` を回して陣営→役職のネストリンクを動的生成 (`rule.html:45-53`)
- **本文セクション** (各 `rule/*.html` フラグメントを `layout:replace` で取り込み):
  - 人狼の基本ルール (`#basic`、本文は省略の注記のみ)
  - 人狼館の事件簿村ルール (`rule/mansion`)
  - 詳細ルール (`rule/detail` — 心構え/能力行使/突然死/投票/処刑/アンカー/役職配分/闇鍋/役職希望/見学/発言/アクション/コミット/転生/役職変化/流れ 等)
  - 役職詳細 (`rule/skill` — **2256 行**、全役職の詳細)
  - 占霊判定・勝敗カウント (`rule/judge`)
  - ステータス (`rule/status` — 恋絆/狐憑き/保険/不敬/呪縛符/反呪符/念力)
  - 陣営・勝敗 (`rule/camp`)
  - 人数と部屋サイズ (`rule/room`)
  - その他 (`rule/other` — 発言ランダム/文字装飾/発言種別/国主召喚)。**発言種別・文字装飾のセクションは村のメッセージと同じ `message message-{type}` クラスで発言例・システムメッセージ例を多数ハードコード**している (`rule/other.html:112-274` に通常/独り言/囁き/共鳴/恋人/念話/梟/墓下/見学/各種システムメッセージ/村建て発言の見本)

### ⚠️ 完全静的ではない (data-driven な部分)

`RuleContent` (`RuleContent.kt`) はマスタから算出した値を渡す:

- `campList`: 全陣営 (`Camps.all()`) と各陣営の役職リスト (`Skills.filterByCamp`) → 目次の役職詳細ネスト & 役職詳細セクションを駆動
- `judgeList`: 占い結果(人狼か)/霊結果/襲撃耐性/カウント種別の全組合せを役職マスタから算出し非空のみ抽出 → `rule/judge.html` の占霊判定表を駆動

→ ルールページは **役職・陣営マスタに依存**。マスタ変更で内容が変わる。React 化時は静的 MDX ではなく、役職/陣営マスタを参照する必要あり。

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 |
|---|---|---|
| GET | `/rule` | ルールページ (SSR、`RuleContent` 注入) |

## 4. 既存 JS の挙動

- ルール固有 JS なし。`index.js` (共通) を読み込むのみ
- TOC は素の HTML アンカー (`#basic` 等) によるページ内ジャンプ

## 5. 権限による分岐

- なし (全員同一・公開)

## 6. 認可マスク

- なし

## 7. 視覚比較

- 既存 `:8091/wolf-mansion/rule`。特に目次の役職ネスト・占霊判定表・各 `rule/*` セクションの構成

## 8. 関連 e2e ケース候補

- [ ] ルールページ smoke 表示 (各セクションが描画される)
- [ ] 目次アンカーのページ内ジャンプ
- [ ] 役職詳細ナビが陣営マスタ件数分生成される

## メモ / 移行時の注意

- `RuleContent` の `campList`/`judgeList` は役職/陣営マスタ駆動。React 化時はマスタを REST から取得して再構築するか、占霊判定の組合せロジックをフロント/バックのどちらで持つか要設計
- **発言例・システムメッセージ例 (`rule/other` の発言種別・文字装飾) は村のメッセージレンダリングコンポーネントを再利用する** (確定方針)。現状は `message message-{type}` クラスで例を独立にハードコードしているが、移行後は村画面のメッセージ表示コンポーネント (step-0.7 [village-messages.md](village/village-messages.md)) を流用し、ルール例と実際の村メッセージの見た目が乖離しないようにする。装飾記法の変換 (step-0.8 [village-say.md](village/village-say.md)) も同じ変換ロジックを通す
- `rule/skill.html` (役職詳細 2256 行) は [skill.md](skill.md) の役職データと内容が重複する可能性 → 役職マスタへの一元化は **モダナイズフェーズ**で検討 (本移行では現状踏襲)
- 画像は静的アセット (frontend public/ へ移管、[06-infra-deploy.md](../06-infra-deploy.md))
