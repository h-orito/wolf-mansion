import { expect, test, type Page } from "@playwright/test";
import {
  ensureVillagesExist,
  loginApi,
  dismissAgeLimitModal,
  dismissInitialSkillModal,
  CANDIDATE_USERS,
} from "./helpers/provision";

/**
 * 設定モーダル (表示設定 / Discord 通知設定) の e2e。
 * 表示設定はブラウザ保存 (localStorage) のため DB を変更しない。
 * 通知設定はサーバ保存のため保存はせず、参加者にフォームが出ることまでを確認する。
 */

/** 村に参加している (myself が返る) ユーザーと村の組を探す。 */
async function findParticipant(
  page: Page,
): Promise<{ villageId: number; userId: string } | null> {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS"]);
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    for (const village of villages) {
      const res = await page.request.get(
        `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
      );
      if (!res.ok()) continue;
      const body = (await res.json()) as { myself: unknown };
      if (body.myself != null) return { villageId: village.id, userId };
    }
  }
  return null;
}

test("設定モーダルで表示設定を変更でき、ブラウザに保存される", async ({ page }) => {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS", "IN_PREPARATION"]);
  const village = villages[0];

  await page.goto(`village/${village.id}`);
  await expect(page.getByRole("button", { name: "設定" })).toBeVisible({ timeout: 15000 });
  await dismissAgeLimitModal(page);
  await dismissInitialSkillModal(page);
  await page.getByRole("button", { name: "設定" }).click();

  const dialog = page.getByRole("dialog", { name: "設定" });
  await expect(dialog.getByRole("heading", { name: "表示設定", exact: true })).toBeVisible();

  // ページサイズ変更が保存される
  await dialog.getByLabel("ページあたりの表示発言数").selectOption("10");
  await page.waitForFunction(
    () => localStorage.getItem("wolf-mansion-display-settings")?.includes('"pageSize":10'),
  );

  // リセットでデフォルト (50) に戻る
  page.on("dialog", (d) => d.accept());
  await dialog.getByRole("button", { name: "リセット" }).click();
  await page.waitForFunction(
    () => localStorage.getItem("wolf-mansion-display-settings")?.includes('"pageSize":50'),
  );

  // 未ログイン or 未参加では Discord 通知設定が出ない (このテストは未ログイン)
  await expect(dialog.getByText("Discord通知設定")).toHaveCount(0);

  await dialog.getByRole("button", { name: "閉じる", exact: true }).last().click();
  await expect(dialog).toHaveCount(0);
});

test("参加者の設定モーダルには Discord 通知設定が表示される", async ({ page }) => {
  const candidate = await findParticipant(page);
  expect(candidate, "進行中の村の参加者が見つからない").not.toBeNull();
  if (candidate == null) return;

  await page.goto(`village/${candidate.villageId}`);
  await expect(page.getByRole("button", { name: "設定" })).toBeVisible({ timeout: 15000 });
  await dismissAgeLimitModal(page);
  // 初回役職確認モーダルは situation/me 取得後に遅れて出るため、出現を待って閉じる
  // (役職未割当の参加者は出ないので、その場合は待ちを諦めて先へ進む)
  await page
    .getByRole("button", { name: "確認したので次回以降表示しない" })
    .click({ timeout: 10000 })
    .catch(() => {});
  await page.getByRole("button", { name: "設定" }).click();

  const dialog = page.getByRole("dialog", { name: "設定" });
  await expect(dialog.getByText("Discord通知設定")).toBeVisible();
  // webhookUrl が空のうちは保存できない (誤送信防止)
  await expect(dialog.getByRole("button", { name: "保存" })).toBeDisabled();
  await dialog.getByLabel("WebhookURL").fill("https://discord.com/api/webhooks/x/y");
  await expect(dialog.getByRole("button", { name: "保存" })).toBeEnabled();
});
