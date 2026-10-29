import { expect, test, type Page } from "@playwright/test";
import {
  CANDIDATE_USERS,
  DEFAULT_ORGANIZATION,
  loginApi,
  provisionInProgressVillage,
  provisionVotingVillage,
} from "./helpers/provision";

/**
 * アンカーまわりの閲覧範囲の e2e。
 * 発言以外の種別や、進行中は見られない発言をアンカーで読めないこと、見られない発言へのアンカーが
 * 宛先での絞り込みに使われないことを、専用に建てた村で確認する。
 */

const API = "/wolf-mansion-api/api/v1";

type Me = { myself: { id: number; skill: { code: string } | null } | null };
type Message = { messageType: string; messageNumber: number | null; messageContent: string };

/** 村の参加者 (テストユーザー) をログインして役職ごとに集める */
async function collectParticipants(
  page: Page,
  villageId: number,
): Promise<{ userId: string; participantId: number; skill: string }[]> {
  const result = [];
  for (const userId of CANDIDATE_USERS) {
    if (!(await loginApi(page, userId))) continue;
    const res = await page.request.get(`${API}/villages/${villageId}/situation/me`);
    if (!res.ok()) continue;
    const me = (await res.json()) as Me;
    if (me.myself?.skill == null) continue;
    result.push({ userId, participantId: me.myself.id, skill: me.myself.skill.code });
  }
  return result;
}

async function findMessages(
  page: Page,
  villageId: number,
  params: Record<string, string>,
): Promise<Message[]> {
  const query = new URLSearchParams({ day: "1", ...params });
  const res = await page.request.get(`${API}/villages/${villageId}/messages?${query}`);
  expect(res.ok()).toBe(true);
  return ((await res.json()) as { messageList: Message[] }).messageList;
}

test("役職占い結果はアンカーで読めない", async ({ page }) => {
  test.setTimeout(240000);
  // 賢者が 2 人いる村を 2 日目まで進める (1 日目の占い結果が 2 件できる)
  const village = await provisionVotingVillage(page, {
    organization: DEFAULT_ORGANIZATION.split("\n")
      .map((line) => line.replace("導", "賢"))
      .join("\n"),
  });
  const wises = (await collectParticipants(page, village.id)).filter((p) => p.skill === "WISE");
  expect(wises.length, "賢者が 2 人いない").toBe(2);

  await loginApi(page, wises[0].userId);
  const readable = [];
  for (const messageNumber of [1, 2]) {
    const res = await page.request.get(
      `${API}/villages/${village.id}/messages/anchor?messageType=PRIVATE_WISE&messageNumber=${messageNumber}`,
    );
    expect(res.ok()).toBe(true);
    const body = (await res.json()) as { message: Message | null };
    if (body.message != null) readable.push(body.message);
  }
  // 発言ではないので自分の結果も読めない
  expect(readable).toHaveLength(0);
});

test("進行中は自分の独り言もアンカーで読めない", async ({ page }) => {
  test.setTimeout(240000);
  const village = await provisionInProgressVillage(page);
  const participant = (await collectParticipants(page, village.id))[0];
  expect(participant, "参加者が見つからない").toBeDefined();
  if (participant == null) return;

  await loginApi(page, participant.userId);
  const say = await page.request.post(`${API}/villages/${village.id}/say`, {
    data: { message: "e2e の独り言", messageType: "MONOLOGUE_SAY", faceType: "NORMAL" },
  });
  expect(say.status()).toBe(204);
  const monologues = await findMessages(page, village.id, { types: "MONOLOGUE_SAY" });
  expect(monologues.map((m) => m.messageContent)).toContain("e2e の独り言");

  // 独り言は番号を表示しないため、番号を総当たりで指定しても読めないことを確認する
  for (const messageNumber of [1, 2, 3]) {
    const res = await page.request.get(
      `${API}/villages/${village.id}/messages/anchor?messageType=MONOLOGUE_SAY&messageNumber=${messageNumber}`,
    );
    expect(res.ok()).toBe(true);
    expect(((await res.json()) as { message: Message | null }).message).toBeNull();
  }
});

test("囁きへのアンカーで、宛先の絞り込みから囁いた人が分からない", async ({ page }) => {
  test.setTimeout(240000);
  const village = await provisionInProgressVillage(page);
  const participants = await collectParticipants(page, village.id);
  const wolf = participants.find((p) => p.skill === "WEREWOLF");
  const villager = participants.find((p) => p.skill === "VILLAGER");
  expect(wolf, "人狼が見つからない").toBeDefined();
  expect(villager, "村人が見つからない").toBeDefined();
  if (wolf == null || villager == null) return;

  await loginApi(page, wolf.userId);
  const whisper = await page.request.post(`${API}/villages/${village.id}/say`, {
    data: { message: "e2e の囁き", messageType: "WEREWOLF_SAY", faceType: "NORMAL" },
  });
  expect(whisper.status()).toBe(204);
  const whispers = await findMessages(page, village.id, { types: "WEREWOLF_SAY" });
  const whisperNumber = whispers.find((m) => m.messageContent === "e2e の囁き")?.messageNumber;
  expect(whisperNumber).not.toBeNull();

  await loginApi(page, villager.userId);
  const text = `e2e アンカー >>*${whisperNumber}`;
  const say = await page.request.post(`${API}/villages/${village.id}/say`, {
    data: { message: text, messageType: "NORMAL_SAY", faceType: "NORMAL" },
  });
  expect(say.status()).toBe(204);

  const toWolf = await findMessages(page, village.id, {
    toParticipantIds: String(wolf.participantId),
  });
  expect(toWolf.map((m) => m.messageContent)).not.toContain(text);
});
