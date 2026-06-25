# Issues

ローカル Issue 管理ディレクトリ。`.issues/` は `.gitignore` 対象 (ローカル管理)。完了した Issue はファイルごと削除する運用。

monorepo 移行作業中につき **階層番号方式** (`step-<N>(.M)-<slug>.md`) を採用。
`/ship-issue [step番号]` skill (project-local 版) で 1 Issue = 1 PR を消化する。

## 命名規則

- `step-<N>(.M)-<slug>.md`
  - `N` = `doc/migration/08-step-plan.md` の Step 番号 (0=調査, 1=環境整備, 2=monorepo化, 3=認証, 4+=画面別)
  - 中間タスクは **マイナー番号** で吸収 (`step-1.5-...`, `step-3.1-...`)
- ブランチ: `<type>/step-<N>(.M)-<slug>`、base は常に **`feature/monorepo`**

## ステータス凡例

- `open` 未着手
- `in-progress` 対応中
- `wontfix` 対応しない (要理由)

完了したものは一覧から削除 & ファイル削除する。

## 一覧

| # | タイトル | type | status |
| --- | --- | --- | --- |
| (なし) | | | |

> **Step 8 完了 🎉 (2026-06-25)**: 統合 PR #89 merge 済。8.1〜8.19 全19サブstep ✅ (#71〜#88)。8.20 オリジナルキャラチップ村ローカルテスト環境 ✅ (#92)。8.12.1 表情差分 REST 化 ✅ (#93)。

> **Step 0・1・2 完了** 🎉 — Step 1 は `.java-version` 21 化 / README 整備 (PR #46)。
> **Step 2 (monorepo 化) は 4 サブ step すべて完了**: 2.1 backend/ 移動 (PR #47 ✅) / 2.2 ktlint+hook+per-dir .gitignore (PR #48 ✅) / 2.3 frontend 雛形 (PR #49 ✅) / 2.4 e2e 雛形 (PR #50 ✅)。次は **Step 3 (認証 REST 化)**。
> ※ step-0.3 以降の調査系子 Issue は個別ファイルを作らず、本一覧 + git 履歴で記録する運用。

## 関連の依存・順序メモ

- **step 間はシーケンシャル**。step N が squash merge されてから step N+1 着手
- **Step 0 (調査) は完全完了してから Step 1 (環境整備)** に進む (08-step-plan.md 順番厳守)
- Step 0 内の子 Issue (`step-0.x-*`) は step-0 bootstrap の merge 後に作成する

## 進行中の Phase 計画

08-step-plan.md を正本とする。要約:

| Step | 対象 | 状態 |
| --- | --- | --- |
| step-0 | 現状把握 (画面/JS/API/ユースケース調査 + ドキュメント化) | **完了** (ユーザーレビュー込み) |
| step-1 | 環境整備 (.java-version, README, skill 採番) | **完了** (PR #46) |
| step-2 | monorepo 化 (backend/ frontend/ e2e/、ktlint、hooks) | **完了** (2.1 移動 #47 ✅ / 2.2 ktlint+hook+gitignore #48 ✅ / 2.3 frontend #49 ✅ / 2.4 e2e #50 ✅) |
| step-3 | 認証 REST 化 (JWT, /api/v1/auth/*) | **完了** (3.0 DBFlute再生成+REFRESH_TOKEN ✅ #51 / 3.1 JWT基盤 ✅ #52 / 3.2 signup・password+レート制限 ✅ #53 / 3.3 frontend認証フロー+e2e ✅ #54 / 3.4 OpenAPI→TS型生成+CI drift ✅ #55) |
| step-4 | ホーム + 村一覧 + intro | **完了** (4.1 #56 / 3.6 #57 / 4.2 #58 / 4.3 #59) |
| step-5 | 情報・静的ページ (役職/ルール/About/FAQ/キャラチップ/エイプリル) | **完了** (5.1〜5.5 ✅ #60〜#64) |
| step-6 | ランダムキーワード CRUD | **完了** (#65) |
| step-7 | 新規村作成 | **完了** (7.1〜7.5 ✅ #66〜#70) |
| step-8 | 村画面 (最重量: 19 サブ step + 8.20) | **完了** (8.1〜8.19 ✅ #71〜#88、統合 PR #89 merge 済、8.20 ✅ #92) |
| step-9+ | プロフィール・戦績 → cutover | 未着手 |

## frontend テスト基盤メモ

- **vitest を導入する場合は `4.1.6` 以上を使用する**。
  - 理由: `4.1.6` 未満には RCE 脆弱性 **CVE-2026-47429** がある。
  - 参考: https://ai-heartland.com/security/vitest-rce-cve-2026-47429/
  - `package.json` で `"vitest": "^4.1.6"` のように固定し、古い系列に巻き戻らないよう注意する。

## 作業フロー

project-local の `/ship-issue` / `/add-issue` skill が標準化済み (`.claude/skills/`)。
グローバル版をベースに、本リポジトリ向けに **step-N(.M) 採番 / base=feature/monorepo** を上書きしている。詳細は各 SKILL.md 参照。
