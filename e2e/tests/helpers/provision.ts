import { expect, type Page } from "@playwright/test";
import * as fs from "node:fs";
import * as path from "node:path";
import { fileURLToPath } from "node:url";

export type SimpleVillage = { id: number; name: string };

const API = "/wolf-mansion-api/api/v1";

// ─── Auth ────────────────────────────────────────────────

export async function loginApi(
  page: Page,
  userId: string,
  password = "testuser",
): Promise<boolean> {
  const res = await page.request.post(`${API}/auth/login`, {
    data: { userId, password },
  });
  return res.ok();
}

export async function loginAsMasterUi(page: Page): Promise<void> {
  await page.goto("login");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", "master");
  await page.fill("#password", "testuser");
  await page.getByRole("button", { name: "ログイン" }).click();
  await expect(page).toHaveURL(/\/wolf-mansion\/?$/);
}

// ─── Village queries ─────────────────────────────────────

export async function findVillages(
  page: Page,
  statuses: string[],
): Promise<SimpleVillage[]> {
  const query = statuses.map((s) => `status=${s}`).join("&");
  const res = await page.request.get(`${API}/villages?${query}`);
  expect(res.ok()).toBeTruthy();
  return ((await res.json()) as { villages: SimpleVillage[] }).villages;
}

export async function fetchVillage(page: Page, villageId: number): Promise<any> {
  const res = await page.request.get(`${API}/villages/${villageId}`);
  expect(res.ok()).toBeTruthy();
  return res.json();
}

// ─── UI helpers ──────────────────────────────────────────

export async function dismissInitialSkillModal(page: Page): Promise<void> {
  const btn = page.getByRole("button", {
    name: "確認したので次回以降表示しない",
  });
  if ((await btn.count()) > 0) await btn.click();
}

export async function dismissAgeLimitModal(page: Page): Promise<void> {
  const btn = page.getByRole("button", { name: "表示する" });
  if ((await btn.count()) > 0) await btn.click();
}

// ─── Constants ───────────────────────────────────────────

export const CANDIDATE_USERS = Array.from(
  { length: 16 },
  (_, i) => `testuser${String(i + 1).padStart(2, "0")}`,
);

export function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-7);
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `v${stamp}${rand}`;
}

// ─── Provisioning ────────────────────────────────────────

const DEFAULT_ORGANIZATION = [
  "村狼狼賢導村村村",
  "村狼狼賢導村村村村",
  "村狼狼狂賢導村村村村",
  "村狼狼狂賢導村村村村村",
  "村狼狼狼狂賢導狩村村村村",
  "村狼狼狼狂賢導狩村村村村村",
  "村狼狼狼魔狐賢導狩霊霊霊霊霊",
  "村狼狼狼魔狐賢導狩霊霊霊霊霊霊",
  "村狼狼狼魔狐賢導狩霊霊霊霊霊共共",
  "村狼狼狼魔狐賢導狩霊霊霊霊霊霊共共",
  "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊共共",
  "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊共共",
  "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊霊共共",
].join("\n");

async function createVillageApi(page: Page): Promise<SimpleVillage> {
  await loginApi(page, "master");

  const villageName = `e2e自動テスト${Date.now().toString(36).slice(-6)}`;
  const start = new Date(Date.now() + 3600_000);
  const request = {
    villageName,
    welcomeRange: "ANYONE_WELCOME",
    startPersonMinNum: 8,
    personMaxNum: 20,
    dayChangeIntervalHours: 24,
    dayChangeIntervalMinutes: 0,
    dayChangeIntervalSeconds: 0,
    startYear: start.getFullYear(),
    startMonth: start.getMonth() + 1,
    startDay: start.getDate(),
    startHour: start.getHours(),
    startMinute: start.getMinutes(),
    shouldOriginalImage: false,
    characterSetId: [1],
    dummyCharaId: 1,
    dummyCharaName: "楽天家 ゲルト",
    dummyCharaShortName: "楽",
    dummyJoinMessage: "e2eテスト用の村です。よろしくお願いします。",
    openVote: false,
    possibleSkillRequest: true,
    availableSameWolfAttack: false,
    availableGuardSameTarget: false,
    reincarnationSkillAll: false,
    availableSuddonlyDeath: true,
    availableCommit: true,
    availableSpectate: true,
    creatorIsProducer: false,
    openSkillInGrave: true,
    visibleGraveSpectateMessage: true,
    availableAction: true,
    randomOrganization: false,
    organization: DEFAULT_ORGANIZATION,
    allowedSecretSayCode: "NOTHING",
    sayRestrictList: [],
    skillSayRestrictList: [],
    rpSayRestrictList: [],
  };

  const res = await page.request.post(`${API}/villages`, {
    multipart: {
      request: {
        name: "request",
        mimeType: "application/json",
        buffer: Buffer.from(JSON.stringify(request)),
      },
    },
  });
  if (!res.ok()) {
    const body = await res.text();
    throw new Error(`Village creation failed: ${res.status()} ${body}`);
  }
  const id = ((await res.json()) as { id: number }).id;
  return { id, name: villageName };
}

async function debugFillParticipants(
  page: Page,
  villageId: number,
  count: number,
): Promise<void> {
  const res = await page.request.post(
    `${API}/villages/${villageId}/debug/all-participate`,
    { data: { personNumber: count } },
  );
  if (!res.ok()) {
    throw new Error(`allParticipate failed: ${res.status()} ${await res.text()}`);
  }
}

async function debugForceDayChange(
  page: Page,
  villageId: number,
): Promise<void> {
  const res = await page.request.post(
    `${API}/villages/${villageId}/debug/day-change`,
  );
  if (!res.ok()) {
    throw new Error(`dayChange failed: ${res.status()} ${await res.text()}`);
  }
}

export async function provisionRecruitingVillage(
  page: Page,
): Promise<SimpleVillage> {
  return createVillageApi(page);
}

export async function provisionInProgressVillage(
  page: Page,
): Promise<SimpleVillage> {
  const village = await createVillageApi(page);
  await debugFillParticipants(page, village.id, 7);
  await debugForceDayChange(page, village.id);
  return village;
}

export async function provisionCompletedVillage(
  page: Page,
): Promise<SimpleVillage> {
  const village = await provisionInProgressVillage(page);
  for (let i = 0; i < 50; i++) {
    await debugForceDayChange(page, village.id);
    const finished = await findVillages(page, [
      "EPILOGUE",
      "COMPLETED",
      "CANCEL",
    ]);
    if (finished.some((v) => v.id === village.id)) return village;
  }
  throw new Error(
    `Village ${village.id} did not reach completed state after 50 day changes`,
  );
}

// ─── Cache (globalSetup で作成した村を全テストで共有) ────

export type ProvisionCache = {
  inProgress: SimpleVillage;
  recruiting: SimpleVillage;
};

const CACHE_PATH = path.join(
  path.dirname(fileURLToPath(import.meta.url)),
  "../.provision-cache.json",
);

function readCache(): ProvisionCache | null {
  try {
    return JSON.parse(fs.readFileSync(CACHE_PATH, "utf-8"));
  } catch {
    return null;
  }
}

export async function ensureVillagesExist(
  page: Page,
  statuses: string[],
): Promise<SimpleVillage[]> {
  const cache = readCache();

  if (statuses.includes("IN_PROGRESS") && cache?.inProgress) {
    return [cache.inProgress];
  }
  if (statuses.includes("IN_PREPARATION") && cache?.recruiting) {
    return [cache.recruiting];
  }

  const existing = await findVillages(page, statuses);
  if (existing.length > 0) return existing;

  if (statuses.includes("IN_PREPARATION")) {
    return [await provisionRecruitingVillage(page)];
  }
  if (statuses.includes("IN_PROGRESS")) {
    return [await provisionInProgressVillage(page)];
  }
  if (
    statuses.some((s) => ["EPILOGUE", "COMPLETED", "CANCEL"].includes(s))
  ) {
    return [await provisionCompletedVillage(page)];
  }

  return [];
}

/** master が参加している IN_PROGRESS 村を返す。 */
export async function ensureMasterInProgressVillage(
  page: Page,
): Promise<SimpleVillage> {
  const cache = readCache();
  if (cache?.inProgress) return cache.inProgress;

  const villages = await findVillages(page, ["IN_PROGRESS"]);
  await loginApi(page, "master");
  for (const v of villages) {
    const res = await page.request.get(
      `${API}/villages/${v.id}/situation/me`,
    );
    if (!res.ok()) continue;
    const body = (await res.json()) as { myself: unknown };
    if (body.myself != null) return v;
  }
  return provisionInProgressVillage(page);
}

/** master が村建てした募集中の村を返す。 */
export async function ensureMasterRecruitingVillage(
  page: Page,
): Promise<SimpleVillage> {
  const cache = readCache();
  if (cache?.recruiting) return cache.recruiting;

  const villages = await findVillages(page, ["IN_PREPARATION"]);
  await loginApi(page, "master");
  for (const v of villages) {
    const res = await page.request.get(
      `${API}/villages/${v.id}/situation/me`,
    );
    if (!res.ok()) continue;
    const body = (await res.json()) as {
      creator: { isAvailableModifySetting: boolean };
    };
    if (body.creator?.isAvailableModifySetting) return v;
  }
  return provisionRecruitingVillage(page);
}
