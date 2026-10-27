import { expect, test } from "@playwright/test";
import { CANDIDATE_USERS, loginApi, provisionRecruitingVillage } from "./helpers/provision";

/**
 * 入村パスワードの試行回数制限の e2e。パスワード付きの募集中の村を建てて確認する。
 */

const API = "/wolf-mansion-api/api/v1";
const JOIN_PASSWORD = "e2epass";

test("入村パスワードを 5 回間違えると、正しいパスワードでも参加確認できなくなる", async ({ page }) => {
  test.setTimeout(120000);
  const village = await provisionRecruitingVillage(page, { joinPassword: JOIN_PASSWORD });
  const detail = (await (await page.request.get(`${API}/villages/${village.id}`)).json()) as {
    participants: { list: { chara: { id: number } }[] };
  };
  const used = new Set(detail.participants.list.map((p) => p.chara.id));
  const charaId = [1, 2, 3, 4, 5].find((id) => !used.has(id))!;

  const confirm = (joinPassword: string) =>
    page.request.post(`${API}/villages/${village.id}/participate-confirm`, {
      data: {
        charaId,
        charaName: "e2e",
        charaShortName: "e",
        joinMessage: "よろしく",
        joinPassword,
      },
    });

  // 正しいパスワードで確認が通る (= 参加できる) ユーザーを選ぶ。成功は回数に数えない
  let found = false;
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    if ((await confirm(JOIN_PASSWORD)).status() === 204) {
      found = true;
      break;
    }
  }
  expect(found, "参加できるテストユーザーが見つからない").toBe(true);

  for (let i = 0; i < 5; i++) {
    const res = await confirm("wrong");
    expect(res.status()).toBe(400);
    expect((await res.json()).detail).toBe("入村パスワードが誤っています。");
  }

  const locked = await confirm(JOIN_PASSWORD);
  expect(locked.status()).toBe(400);
  expect((await locked.json()).detail).toContain("しばらく参加できません");
});
