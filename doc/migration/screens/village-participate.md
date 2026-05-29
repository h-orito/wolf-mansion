# 画面: 村画面 — 参加 / 退村 / 見学切替 / 希望役職変更

> 村画面 form-area の参加系。プロローグ中心の操作。

## 概要

- **テンプレート**: `village/participate-form.html`, `participate-confirm.html`, `village/switch-participate-form.html`, `village/leave-form.html`, `village/change-skill-form.html`
- **担当 JS**: `village.js` (参戦 L.1797-1872)
- **Controller**: `VillageParticipateController`
- **対象ユーザー**: ログイン済 (situation.participate / skillRequest 由来で各フォーム出し分け)

## 1. 機能 / 出来ることリスト

- 入村 (キャラ選択 + 入村発言 + 希望役職) — 確認画面経由
- 観戦 (見学) 参加
- 参加 ⇄ 見学 の切替
- 希望役職 (第1/第2) の変更
- 退村

## 2. 表示要素・UI 状態

- **参戦フォーム** (`participate-form`): キャラ選択 (キャラチップ → getSelectableCharaList で候補、テキスト/画像選択 UI)、キャラ名/略称、入村発言、入村パスワード、希望役職(第1/第2)、観戦チェック。**原画村のオリジナル画像アップロードは参戦フォームではなく確認画面で行う** (`participate-form.html:104` の案内文)
- **入村確認** (`participate-confirm`): `say-confirm.js` が描画。選択キャラ画像プレビュー + 内容確認 + **オリジナル画像アップロード (multipart, 原画村)** + **2 つの同意チェック (ルール `agree-rule` / 礼節 `agree-mind`) で「入村する」ボタンが活性化** (`participate-confirm.html:9,18-47,67-72`)
- **見学切替** (`switch-participate-form`): 参加 ⇄ 見学
- **希望役職変更** (`change-skill-form`): 第1/第2希望 select
- **退村** (`leave-form`): 退村ボタン

## 3. 呼び出す API エンドポイント

| メソッド | パス | 用途 | 呼び出し元 |
|---|---|---|---|
| POST | `/village/{id}/confirm-participate` | 入村確認 (assertParticipate) → confirm 画面 | フォーム |
| POST | `/village/{id}/participate` | 入村 (IP記録) → redirect | 確認画面 |
| POST | `/village/{id}/switch-participate` | 参加/見学切替 | フォーム |
| POST | `/village/{id}/change-skill` | 希望役職変更 | フォーム |
| POST | `/village/{id}/leave` | 退村 | フォーム |
| GET | `/getSelectableCharaList/{villageId}?charachipId=` | 選択可能キャラ一覧 (既使用を除外) | village.js |

- フォーム: `VillageParticipateForm` (charaId, charaName, charaShortName, charaImageFile, requestedSkill, secondRequestedSkill, joinMessage, joinPassword, spectator) / `VillageChangeRequestSkillForm` (requestedSkill, secondRequestedSkill)
- 希望役職の既定は **おまかせ** (`CDef.Skill.おまかせ`)
- 原画村 (isOriginalCharachip) は `charaImageFile` 必須
- 投稿は `VillageCoordinator.participate / switchParticipate / changeRequestSkill / leave`、`assertParticipate` で事前検証

## 4. 既存 JS の挙動

- 参戦時: `getSelectableCharaList` → キャラ選択 UI 生成 (テキスト一覧 / 画像選択)。選択キャラから charaName/charaShortName を自動補完 (`village.js:1890-1891`)、`canChangeName` が false のキャラは名前/略称を readonly 化 (`village.js:1864-1869`)
- 確認画面 (`participate-confirm`) は `say-confirm.js` 担当: メッセージ整形 + 画像プレビュー + 同意チェックで submit 活性化
- 確認 → 入村の 2 段。redirect は referer query 維持

## 5. 権限による分岐 / 6. 認可マスク

- 各フォームの表示可否は `situation.participate` (isAvailableParticipate / isAvailableSpectate / isAvailableSwitchParticipate / isAvailableLeave) と `situation.skillRequest` 由来
- 主にプロローグ (募集中) で有効

## 7. 視覚比較

- 既存 `:8091`。参戦フォーム (キャラ選択)、確認画面、見学切替、希望役職、退村

## 8. 関連 e2e ケース候補

- [ ] 入村: キャラ選択 → 確認 → 参加 → 参加者一覧反映
- [ ] 観戦参加 / 参加⇄見学切替
- [ ] 希望役職変更 (第1/第2)
- [ ] 退村
- [ ] 入村パスワード村への参加
- [ ] 原画村: 画像必須バリデーション

## メモ / 移行時の注意

- キャラ選択 + 画像アップロード (原画村) は multipart。REST 化時の扱いは新規村作成 (step-0.3) と統一
- 希望役職 select の候補は `situation` / 村構成由来。REST レスポンスに含める
- 確認 → 参加の 2 段フローは React のプレビュー UI に
- IP 記録は維持
