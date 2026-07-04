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

// このプロセスが作成した村の記録 (global.teardown が廃村して後片付けする)。
// 並列 worker からの追記が混ざっても壊れないよう 1 行 1 村の NDJSON で追記する
const CREATED_PATH = path.join(
  path.dirname(fileURLToPath(import.meta.url)),
  "../.provision-created.ndjson",
);

function recordCreatedVillage(village: SimpleVillage): void {
  fs.appendFileSync(CREATED_PATH, `${JSON.stringify(village)}\n`);
}

export function readCreatedVillages(): SimpleVillage[] {
  try {
    return fs
      .readFileSync(CREATED_PATH, "utf-8")
      .split("\n")
      .filter(Boolean)
      .map((line) => JSON.parse(line));
  } catch {
    return [];
  }
}

export function clearCreatedVillages(): void {
  fs.rmSync(CREATED_PATH, { force: true });
}

/**
 * 村を終了状態に片付ける。廃村はプロローグ中しかできないため、
 * 進行中・エピローグの村は debug の日送りで決着させる。
 */
export async function settleVillage(page: Page, villageId: number): Promise<void> {
  await loginApi(page, "master");
  for (let i = 0; i < 10; i++) {
    const village = await fetchVillage(page, villageId);
    const code: string = village.status?.code ?? "";
    if (code === "IN_PREPARATION" || code === "WAITING") {
      await page.request.post(`${API}/villages/${villageId}/creator/cancel`);
      return;
    }
    if (code === "IN_PROGRESS" || code === "EPILOGUE") {
      await page.request.post(`${API}/villages/${villageId}/debug/day-change`);
      continue;
    }
    return;
  }
}

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
    // 突然死ありにすると、片付け損ねた村の日送りでテストユーザーが突然死して
    // 参加制限ペナルティを受け、以降の村プロビジョニングを壊す。
    // 突然死なしの村は日付更新時に全生存者へ自投票が自動セットされるため、
    // 投票系スペックの前提 (投票済み参加者の存在) はむしろ決定的になる
    availableSuddonlyDeath: false,
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
  const village = { id, name: villageName };
  recordCreatedVillage(village);
  return village;
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

/**
 * 進行中の村 (1日目) を作る。ダミーキャラは master (player_id=1) 名義で参加する
 * 仕様のため、作成した村では master が最初からダミーとして参加者になっており、
 * 村建て発言・独り言・村管理などの master 前提スペックがそのまま動く。
 */
export async function provisionInProgressVillage(
  page: Page,
): Promise<SimpleVillage> {
  const village = await createVillageApi(page);
  await debugFillParticipants(page, village.id, 7);
  await debugForceDayChange(page, village.id);
  return village;
}

/**
 * 投票可能な村 (2日目) を作る。投票は2日目からのため、1日目の村では投票系の
 * テストができない。突然死なし村は日付更新時に全生存者へ自投票が自動セット
 * されるため、この村には「投票済みの参加者」が必ず存在する。
 */
export async function provisionVotingVillage(page: Page): Promise<SimpleVillage> {
  const village = await provisionInProgressVillage(page);
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
  voting: SimpleVillage;
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

/**
 * 状態に合う村を返す。募集中・進行中は「DB にたまたまある村」を拾わず、
 * global.setup がこの実行用に作った村 (キャッシュ) を使う。
 * 終了系 (EPILOGUE/COMPLETED/CANCEL) は状態が変わらないため既存村を再利用してよい。
 */
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
  if (statuses.includes("IN_PREPARATION")) {
    return [await provisionRecruitingVillage(page)];
  }
  if (statuses.includes("IN_PROGRESS")) {
    return [await provisionInProgressVillage(page)];
  }
  if (statuses.some((s) => ["EPILOGUE", "COMPLETED", "CANCEL"].includes(s))) {
    const existing = await findVillages(page, statuses);
    if (existing.length > 0) return existing;
    return [await provisionCompletedVillage(page)];
  }

  return [];
}

/** master が参加している IN_PROGRESS 村を返す (provision した村は master がダミーとして参加している)。 */
export async function ensureMasterInProgressVillage(
  page: Page,
): Promise<SimpleVillage> {
  const cache = readCache();
  if (cache?.inProgress) return cache.inProgress;
  return provisionInProgressVillage(page);
}

/** master が村建てした募集中の村を返す (provision した村は master が村建てしている)。 */
export async function ensureMasterRecruitingVillage(
  page: Page,
): Promise<SimpleVillage> {
  const cache = readCache();
  if (cache?.recruiting) return cache.recruiting;
  return provisionRecruitingVillage(page);
}

/** 投票可能な (2日目の) IN_PROGRESS 村を返す。 */
export async function ensureVotingVillage(page: Page): Promise<SimpleVillage> {
  const cache = readCache();
  if (cache?.voting) return cache.voting;
  return provisionVotingVillage(page);
}
