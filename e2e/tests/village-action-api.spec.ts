import { expect, test } from "@playwright/test";
import { CANDIDATE_USERS, loginApi, provisionInProgressVillage } from "./helpers/provision";

/**
 * アクション発言の API の e2e。行為者名と対象をサーバーで確かめることを、専用に建てた村で確認する。
 */

const API = "/wolf-mansion-api/api/v1";

test("アクション発言の行為者名はサーバーで付け、村にいない対象や空の本文は受け付けない", async ({
  page,
}) => {
  test.setTimeout(180000);
  const village = await provisionInProgressVillage(page);

  let myName: string | null = null;
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    const res = await page.request.get(`${API}/villages/${village.id}/situation/me`);
    if (!res.ok()) continue;
    const me = (await res.json()) as { myself: { name: string } | null };
    if (me.myself != null) {
      myName = me.myself.name;
      break;
    }
  }
  expect(myName, "村に参加しているテストユーザーが見つからない").not.toBeNull();

  const action = (data: { myself: string; target: string | null; message: string }) =>
    page.request.post(`${API}/villages/${village.id}/action`, { data });

  // 行為者名を別人にしても、本人の名前で記録される
  expect((await action({ myself: "[偽] 別人は、", target: null, message: "e2e で手を振った。" })).status()).toBe(204);
  const list = (await (
    await page.request.get(`${API}/villages/${village.id}/messages?day=1&types=ACTION`)
  ).json()) as { messageList: { messageContent: string }[] };
  expect(list.messageList.map((m) => m.messageContent)).toContain(`${myName}は、e2e で手を振った。`);

  // 村にいない対象は指定できない
  const badTarget = await action({ myself: `${myName}は、`, target: "[偽] 別人に", message: "手を振った。" });
  expect(badTarget.status()).toBe(400);

  // 本文が空白だけのものは受け付けない
  const blank = await action({ myself: `${myName}は、`, target: "全員", message: " " });
  expect(blank.status()).toBe(400);
});
