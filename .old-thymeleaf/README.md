# .old-thymeleaf — 旧 Thymeleaf 実装の退避アーカイブ

`feature/monorepo` 移行 (Step 9) で REST API 専用化したため、旧 Spring Boot + Thymeleaf
の画面実装をこのディレクトリに退避した。**コンパイル対象外** (`backend/` の外なので
Gradle はビルドしない)。

## 目的

Step 12 (既存画面のフィーチャー / 視覚的復元) で「旧画面に何が表示されていたか」
「どの条件で何が見えたか」を参照するためのリファレンス。git history を辿らずに
ブラウズできるようにしておく。

## 中身

| パス | 退避元 |
|---|---|
| `templates/` | `backend/src/main/resources/templates/` (Thymeleaf HTML 74 ファイル) |
| `static/` | `backend/src/main/resources/static/` (旧画面の JS / CSS / 画像) |
| `kotlin/` | 旧 `@Controller` / `api/view/` / `api/helper/` / Thymeleaf 専用 form・validator |

## 退避していないもの (= REST 側で生きている)

- `api/request/NewVillageForm.kt`, `VillageSettingForm.kt`, `request/setting/*` — REST が再利用
- `api/request/validator/NewVillageFormValidator.kt`, `SettingFormValidator.kt` — REST が再利用
- 外部連携の snake_case JSON エンドポイント (`/recruiting`, `/village-record/list`,
  `/village-record/latest-vid`, `/skill/list`, `/skill-list`) — `api/legacy/` に
  `LegacyExternalRestController` として再実装済 (レスポンス schema は現状維持)

## 削除条件

Step 12 の機能差分復元が完了し、ユーザー確認で「旧画面比で不足なし」と判断できたら、
このディレクトリごと削除する (別 PR)。それまでは残置。
