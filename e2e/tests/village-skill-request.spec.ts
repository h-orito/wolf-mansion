import { expect, test } from "@playwright/test";
import { CANDIDATE_USERS, loginApi, provisionRecruitingVillage } from "./helpers/provision";

/**
 * 役職希望の e2e。役職希望ができない村を建てて確認する。
 */

const API = "/wolf-mansion-api/api/v1";

test("役職希望ができない村では、入村時に役職を希望してもおまかせになる", async ({ page }) => {
  test.setTimeout(120000);
  const village = await provisionRecruitingVillage(page, { possibleSkillRequest: false });
  const detail = (await (await page.request.get(`${API}/villages/${village.id}`)).json()) as {
    participants: { list: { chara: { id: number } }[] };
  };
  const used = new Set(detail.participants.list.map((p) => p.chara.id));
  const charaId = [1, 2, 3, 4, 5].find((id) => !used.has(id))!;

  const participate = () =>
    page.request.post(`${API}/villages/${village.id}/participate`, {
      multipart: {
        request: {
          name: "request.json",
          mimeType: "application/json",
          buffer: Buffer.from(
            JSON.stringify({
              charaId,
              charaName: "e2e",
              charaShortName: "e",
              joinMessage: "よろしく",
              requestedSkill: "WEREWOLF",
              secondRequestedSkill: "WEREWOLF",
            }),
          ),
        },
      },
    });

  // 入村できるテストユーザーで入村する
  let joined = false;
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    if ((await participate()).status() === 204) {
      joined = true;
      break;
    }
  }
  expect(joined, "入村できるテストユーザーが見つからない").toBe(true);

  const me = (await (await page.request.get(`${API}/villages/${village.id}/situation/me`)).json()) as {
    skillRequest: { skillRequest: { first: { code: string }; second: { code: string } } | null };
  };
  expect(me.skillRequest.skillRequest?.first.code).toBe("LEFTOVER");
  expect(me.skillRequest.skillRequest?.second.code).toBe("LEFTOVER");
});
