import { expect, test } from "@playwright/test";
import * as fs from "node:fs";
import * as path from "node:path";
import { fileURLToPath } from "node:url";

/**
 * オリジナルキャラチップ村の e2e。
 * master でオリジナルキャラチップ村を作成 → 新規ユーザーで入村 (画像アップロード)
 * → SSR 経由で表情差分追加 → 画像配信確認 → 廃村して後片付け。
 *
 * 前提: docker-compose の nginx が起動していること (画像配信)。
 */

const API = "/wolf-mansion-api/api/v1";
const SSR = "/wolf-mansion-api";
const __dirname = path.dirname(fileURLToPath(import.meta.url));
const TEST_IMAGE = path.resolve(__dirname, "fixtures/test-chara.png");

async function loginJwt(
  page: import("@playwright/test").Page,
  userId: string,
  password: string,
): Promise<boolean> {
  const res = await page.request.post(`${API}/auth/login`, {
    data: { userId, password },
  });
  return res.ok();
}

function futureStartDate(): {
  startYear: number;
  startMonth: number;
  startDay: number;
  startHour: number;
  startMinute: number;
} {
  const d = new Date(Date.now() + 7 * 24 * 60 * 60 * 1000);
  return {
    startYear: d.getFullYear(),
    startMonth: d.getMonth() + 1,
    startDay: d.getDate(),
    startHour: d.getHours(),
    startMinute: d.getMinutes(),
  };
}

function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-6);
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `o${stamp}${rand}`;
}

test("オリジナルキャラチップ村: 作成 → 入村 (画像) → 表情差分追加 → 画像配信確認 → 廃村", async ({
  page,
}) => {
  // --- master でログイン ---
  const ok = await loginJwt(page, "master", "testuser");
  test.skip(!ok, "master が存在しない DB のためスキップ");

  // --- 村作成 (REST API: multipart) ---
  const imageBytes = fs.readFileSync(TEST_IMAGE);
  const startDate = futureStartDate();

  const createRequest = {
    villageName: `e2eオリジナル村${Date.now()}`,
    startPersonMinNum: 8,
    personMaxNum: 20,
    dayChangeIntervalHours: 24,
    dayChangeIntervalMinutes: 0,
    dayChangeIntervalSeconds: 0,
    ...startDate,
    shouldOriginalImage: true,
    characterSetId: [],
    dummyCharaName: "ダミー太郎",
    dummyCharaShortName: "ダ",
    dummyJoinMessage: "e2eテスト用のダミーキャラです。",
    joinPassword: "testpass",
    openVote: false,
    possibleSkillRequest: true,
    availableSameWolfAttack: false,
    availableGuardSameTarget: false,
    reincarnationSkillAll: false,
    availableSuddonlyDeath: true,
    availableCommit: true,
    availableSpectate: false,
    creatorIsProducer: false,
    openSkillInGrave: false,
    visibleGraveSpectateMessage: false,
    availableAction: true,
    randomOrganization: false,
    organization: [
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
    ].join("\n"),
    allowedSecretSayCode: "NOTHING",
    sayRestrictList: [],
    skillSayRestrictList: [],
    rpSayRestrictList: [],
  };

  const createRes = await page.request.post(`${API}/villages`, {
    multipart: {
      request: {
        name: "request",
        mimeType: "application/json",
        buffer: Buffer.from(JSON.stringify(createRequest)),
      },
      dummyCharaImage: {
        name: "test-chara.png",
        mimeType: "image/png",
        buffer: imageBytes,
      },
    },
  });
  expect(createRes.ok(), `村作成失敗: ${createRes.status()} ${await createRes.text()}`).toBe(true);
  const { id: villageId } = (await createRes.json()) as { id: number };

  // 以降の処理が失敗しても廃村できるように try-finally で包む
  try {
    // --- 村が作成されていることを API で確認 ---
    const villageRes = await page.request.get(`${API}/villages/${villageId}`);
    expect(villageRes.ok()).toBe(true);
    const village = (await villageRes.json()) as {
      setting: { chara: { isOriginalCharachip: boolean } };
    };
    expect(village.setting.chara.isOriginalCharachip).toBe(true);

    // --- 新規ユーザーで入村 (画像アップロード付き) ---
    const joinUserId = uniqueUserId();
    const signupRes = await page.request.post(`${API}/auth/signup`, {
      data: { userId: joinUserId, password: "testpass1" },
    });
    expect(signupRes.ok()).toBe(true);

    const participateRequest = {
      charaName: "テスト花子",
      charaShortName: "花",
      joinMessage: "e2eオリジナル村テスト入村です。",
      joinPassword: "testpass",
    };

    const joinRes = await page.request.post(`${API}/villages/${villageId}/participate`, {
      multipart: {
        request: {
          name: "request",
          mimeType: "application/json",
          buffer: Buffer.from(JSON.stringify(participateRequest)),
        },
        charaImage: {
          name: "test-chara.png",
          mimeType: "image/png",
          buffer: imageBytes,
        },
      },
    });
    expect(joinRes.ok(), `入村失敗: ${joinRes.status()} ${await joinRes.text()}`).toBe(true);

    // --- 入村後の situation/me で画像 URL を確認 ---
    const meRes = await page.request.get(`${API}/villages/${villageId}/situation/me`);
    expect(meRes.ok()).toBe(true);
    const me = (await meRes.json()) as {
      myself: {
        chara: {
          images: { list: { url: string; faceType: { code: string; name: string } }[] };
        };
      };
    };
    const images = me.myself.chara.images.list;
    expect(images.length).toBeGreaterThanOrEqual(1);
    const normalImage = images[0];
    expect(normalImage.url).toContain("/wmansion/original/");

    // --- 画像が nginx 経由で配信されていることを確認 ---
    const imageRes = await page.request.get(normalImage.url);
    expect(imageRes.ok(), `画像配信失敗: ${normalImage.url} → ${imageRes.status()}`).toBe(true);
    expect(imageRes.headers()["content-type"]).toContain("image/");

    // --- SSR 経由で表情差分追加 ---
    // SSR はセッション認証 + CSRF が必要なので、まず SSR ログインする
    const loginPage = await page.request.get(`${SSR}/login`);
    // CSRF トークンを取得 (ログインページの hidden input から)
    const loginHtml = await loginPage.text();
    const csrfMatch = loginHtml.match(/name="_csrf"[^>]*value="([^"]+)"/);
    if (csrfMatch) {
      const csrf = csrfMatch[1];

      // SSR ログイン
      await page.request.post(`${SSR}/login`, {
        form: {
          userId: joinUserId,
          password: "testpass1",
          _csrf: csrf,
        },
      });

      // 村ページから新しい CSRF トークンを取得
      const villagePageRes = await page.request.get(`${SSR}/village/${villageId}`);
      const villageHtml = await villagePageRes.text();
      const villageCsrfMatch = villageHtml.match(/name="_csrf"[^>]*value="([^"]+)"/);

      if (villageCsrfMatch) {
        const villageCsrf = villageCsrfMatch[1];

        // 表情差分追加 (multipart form)
        const faceRes = await page.request.post(
          `${SSR}/village/${villageId}/add-face-type`,
          {
            multipart: {
              faceTypeName: "怒り",
              image: {
                name: "angry-face.png",
                mimeType: "image/png",
                buffer: imageBytes,
              },
              _csrf: villageCsrf,
            },
          },
        );
        // SSR は 302 リダイレクトで成功 (followRedirects: true がデフォルト)
        expect(
          faceRes.ok(),
          `表情差分追加失敗: ${faceRes.status()} ${await faceRes.text().catch(() => "")}`,
        ).toBe(true);

        // 表情差分が追加されたことを確認 (situation/me の画像リスト)
        // JWT で再ログインして確認
        await loginJwt(page, joinUserId, "testpass1");
        const meRes2 = await page.request.get(`${API}/villages/${villageId}/situation/me`);
        expect(meRes2.ok()).toBe(true);
        const me2 = (await meRes2.json()) as {
          myself: {
            chara: {
              images: { list: { url: string; faceType: { code: string; name: string } }[] };
            };
          };
        };
        const images2 = me2.myself.chara.images.list;
        expect(images2.length).toBeGreaterThanOrEqual(2);
        const angryImage = images2.find((i) => i.faceType.name === "怒り");
        expect(angryImage, "表情差分「怒り」が追加されていない").toBeTruthy();

        // 表情差分の画像も配信されていることを確認
        if (angryImage) {
          const angryImgRes = await page.request.get(angryImage.url);
          expect(
            angryImgRes.ok(),
            `表情差分画像配信失敗: ${angryImage.url} → ${angryImgRes.status()}`,
          ).toBe(true);
        }
      }
    }
  } finally {
    // --- 後片付け: 廃村 ---
    await loginJwt(page, "master", "testuser");
    const cancelRes = await page.request.post(
      `${API}/villages/${villageId}/creator/cancel`,
    );
    expect(cancelRes.ok(), `廃村失敗: ${cancelRes.status()}`).toBe(true);
  }
});
