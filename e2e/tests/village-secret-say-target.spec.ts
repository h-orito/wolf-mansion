import { expect, test } from "@playwright/test";
import { CANDIDATE_USERS, loginApi, provisionInProgressVillage } from "./helpers/provision";

/**
 * 秘話の宛先の e2e。秘話を「村建てとのみ」にした村を建てて確認する。
 * 村建て (master) はダミーキャラ (charaId=1) で参加している。
 */

const API = "/wolf-mansion-api/api/v1";
const CREATOR_CHARA_ID = 1;

test("秘話が村建てとのみの村では、村建て以外に秘話を送れない", async ({ page }) => {
  test.setTimeout(180000);
  const village = await provisionInProgressVillage(page, { allowedSecretSayCode: "ONLY_CREATOR" });
  const detail = (await (await page.request.get(`${API}/villages/${village.id}`)).json()) as {
    participants: { list: { chara: { id: number } }[] };
  };

  let myCharaId: number | null = null;
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    const res = await page.request.get(`${API}/villages/${village.id}/situation/me`);
    if (!res.ok()) continue;
    const me = (await res.json()) as { myself: { chara: { id: number } } | null };
    if (me.myself != null) {
      myCharaId = me.myself.chara.id;
      break;
    }
  }
  expect(myCharaId, "村に参加しているテストユーザーが見つからない").not.toBeNull();
  const otherCharaId = detail.participants.list
    .map((p) => p.chara.id)
    .find((id) => id !== myCharaId && id !== CREATOR_CHARA_ID)!;

  const say = (secretSayTargetCharaId: number) =>
    page.request.post(`${API}/villages/${village.id}/say`, {
      data: {
        message: "e2e の秘話",
        messageType: "SECRET_SAY",
        faceType: "NORMAL",
        secretSayTargetCharaId,
      },
    });

  const toOther = await say(otherCharaId);
  expect(toOther.status()).toBe(400);
  expect((await toOther.json()).detail).toBe("秘話を送れない相手です");

  expect((await say(CREATOR_CHARA_ID)).status()).toBe(204);
});
