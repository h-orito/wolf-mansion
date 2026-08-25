import { expect, test } from "@playwright/test";
import { ensureVillagesExist, findVillages, provisionThirdDayVillage } from "./helpers/provision";

/**
 * 村画面 (`/village/{id}`) e2e。
 *
 * 村の状態はローカル DB に依存するため、村一覧 API から対象を動的に探し、
 * 該当する村が無い場合はスキップする。
 */


test("募集中の村画面が表示される (タイトル/日付ナビ/状況パネル/フッターメニュー)", async ({
  page,
}) => {
  const villages = await ensureVillagesExist(page, ["IN_PREPARATION"]);
  const village = villages[villages.length - 1];

  await page.goto(`village/${village.id}`);

  await expect(page).toHaveTitle(`WOLF MANSION | ${village.name}`);
  // 村番号 (4桁0埋め) + 村名の見出し
  const number = String(village.id).padStart(4, "0");
  await expect(page.getByRole("heading", { name: `${number}. ${village.name}` })).toBeVisible();
  // 日付ナビ (現在日はリンクにならない)
  await expect(page.getByText("プロローグ").first()).toBeVisible();
  // 状況パネルと参加者タブ
  await expect(page.getByRole("button", { name: "状況" })).toBeVisible();
  await expect(page.getByRole("button", { name: "参加者" })).toBeVisible();
  await expect(page.getByText("生存 (", { exact: false }).first()).toBeVisible();
  // フッターメニュー
  await expect(page.getByRole("button", { name: "最上部へ" })).toBeVisible();
  await expect(page.getByRole("button", { name: "更新" })).toBeVisible();
  // 募集中の村は次回更新までのカウントダウンが出る
  await expect(page.getByText("更新まで")).toBeVisible();
});

test("存在しない村はその旨を表示する", async ({ page }) => {
  await page.goto("village/99999");

  await expect(page.getByText("村が見つかりませんでした。")).toBeVisible();
});

test("進行中以降の村で日付ナビ遷移と状況タブが動く", async ({ page }) => {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS", "EPILOGUE", "COMPLETED"]);
  const village = villages[0];

  await page.goto(`village/${village.id}`);
  await expect(page.getByRole("button", { name: "状況" })).toBeVisible();

  // 開始済みの村は部屋割りタブが初期表示される
  await expect(page.getByRole("button", { name: "部屋割り当て" })).toBeVisible();

  // 日付ナビでプロローグへ遷移できる
  await page.getByRole("link", { name: "プロローグ" }).first().click();
  await expect(page).toHaveURL(new RegExp(`/village/${village.id}/day/0\\/?$`));
  // プロローグ表示では部屋割りタブが無く参加者タブが初期表示
  await expect(page.getByRole("button", { name: "部屋割り当て" })).toHaveCount(0);
  await expect(page.getByText("生存 (", { exact: false }).first()).toBeVisible();
});

test("3日目以降の村で投票・足音タブを切り替えられる", async ({ page }) => {
  // 投票タブは前日の投票結果がある 3 日目以降にしか出ないため専用村を作る
  const village = await provisionThirdDayVillage(page);

  await page.goto(`village/${village.id}`);
  // 新しく作った村なので初回役職確認モーダルが必ず出る。閉じないと状況パネルを操作できない
  await page.getByRole("button", { name: "確認したので次回以降表示しない" }).click();
  await expect(page.getByRole("button", { name: "状況" })).toBeVisible();

  await page.getByRole("button", { name: "投票", exact: true }).click();
  await expect(page.getByRole("button", { name: "投票者" })).toBeVisible();

  // 足音タブは 2 日目以降の足音を日別に一覧する。master はネタバレ閲覧可のため
  // 「[短縮名][役職] セット → 実際」の詳細形式、それ以外は「…足音…」の簡略形式になる
  await page.getByRole("button", { name: "足音", exact: true }).click();
  for (const day of ["2d", "3d"]) {
    const row = page.getByRole("row").filter({ has: page.getByRole("cell", { name: day, exact: true }) });
    await expect(row).toBeVisible();
    await expect(row).toContainText(/→|足音/);
  }
});

test("未ログインでは situation/me が 401 になる (公開 API はマスク済み)", async ({ page }) => {
  const villages = await ensureVillagesExist(page, ["IN_PROGRESS", "EPILOGUE", "COMPLETED"]);
  const village = villages[0];

  const meRes = await page.request.get(
    `/wolf-mansion-api/api/v1/villages/${village.id}/situation/me`,
  );
  expect(meRes.status()).toBe(401);

  // 公開の村状況は取得でき、進行中の村では役職名がマスクされている
  const res = await page.request.get(`/wolf-mansion-api/api/v1/villages/${village.id}/situation`);
  expect(res.ok()).toBeTruthy();
  const situation = (await res.json()) as {
    isViewableSpoilerContent: boolean;
    roomAssignedRowList: { roomAssignedList: { skillName: string | null }[] }[] | null;
  };
  const progressVillages = await findVillages(page, ["IN_PROGRESS"]);
  if (progressVillages.some((v) => v.id === village.id)) {
    expect(situation.isViewableSpoilerContent).toBe(false);
    const skillNames = (situation.roomAssignedRowList ?? [])
      .flatMap((row) => row.roomAssignedList)
      .map((room) => room.skillName)
      .filter((name) => name != null);
    expect(skillNames).toHaveLength(0);
  }
});
