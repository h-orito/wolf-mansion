import { expect, test, type Page } from "@playwright/test";
import * as path from "node:path";
import { fileURLToPath } from "node:url";

/**
 * オリジナルキャラチップ村の e2e。
 * master で村作成画面から村を建て → 新規ユーザーで入村 (画像アップロード)
 * → 表情差分追加 (SSR、REST 未実装のため) → 画像配信確認 → 廃村して後片付け。
 *
 * 前提: docker-compose の nginx が起動していること (画像配信)。
 */

const API = "/wolf-mansion-api/api/v1";
const SSR = "/wolf-mansion-api";
const __dirname = path.dirname(fileURLToPath(import.meta.url));
const TEST_IMAGE = path.resolve(__dirname, "fixtures/test-chara.png");

function uniqueUserId(): string {
  const stamp = Date.now().toString(36).slice(-6);
  const rand = Math.floor(Math.random() * 36 ** 2)
    .toString(36)
    .padStart(2, "0");
  return `o${stamp}${rand}`;
}

async function loginViaUi(page: Page, userId: string, password: string) {
  await page.goto("login");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", userId);
  await page.fill("#password", password);
  await page.getByRole("button", { name: "ログイン" }).click();
  await expect(page.getByText(`ユーザID: ${userId}`)).toBeVisible({ timeout: 15000 });
}

async function signupViaUi(page: Page, userId: string, password: string) {
  await page.goto("signup");
  await page.waitForLoadState("networkidle");
  await page.fill("#userId", userId);
  await page.fill("#password", password);
  await page.getByRole("button", { name: "作成" }).click();
  await expect(page.getByText(`ユーザID: ${userId}`)).toBeVisible({ timeout: 15000 });
}

async function loginJwt(page: Page, userId: string, password: string): Promise<boolean> {
  const res = await page.request.post(`${API}/auth/login`, {
    data: { userId, password },
  });
  return res.ok();
}

test("オリジナルキャラチップ村: 作成 → 入村 (画像) → 表情差分追加 → 画像配信確認 → 廃村", async ({
  page,
}) => {
  // --- master でログインして村作成ページへ ---
  const ok = await loginJwt(page, "master", "testuser");
  test.skip(!ok, "master が存在しない DB のためスキップ");

  await loginViaUi(page, "master", "testuser");
  await page.goto("new-village");
  await expect(page.getByRole("heading", { name: "村作成" })).toBeVisible();

  // --- 村作成フォーム入力 ---
  await page.fill("#villageName", `e2eオリジナル村${Date.now()}`);

  // キャラチップ利用 → 「自分で用意する」に切替
  const radioGroup = page.getByRole("radiogroup", { name: "キャラチップ利用" });
  await radioGroup.getByRole("radio", { name: "自分で用意する" }).click();

  // キャラクター名・略称・入村発言を入力
  await page.fill("#dummyCharaName", "ダミー太郎");
  await page.fill("#dummyCharaShortName", "ダ");
  await page.fill("#dummyJoinMessage", "e2eテスト用のダミーキャラです。");

  // 入村パスワードを設定
  await page.fill("#joinPassword", "testpass");

  // オリジナル画像をアップロード (FileUpload コンポーネント)
  const fileInput = page.locator('input[type="file"][accept="image/*"]').first();
  await fileInput.setInputFiles(TEST_IMAGE);
  await expect(page.getByText("test-chara.png")).toBeVisible();

  // 確認モーダルを開く
  await page.getByRole("button", { name: "確認画面へ" }).click();
  const modal = page.getByRole("dialog", { name: "村作成確認" });
  await expect(modal).toBeVisible({ timeout: 15000 });

  // 作成
  await modal.getByRole("button", { name: "作成" }).click();

  // 村画面に遷移する (作成成功)
  await expect(page).toHaveURL(/\/village\/\d+/, { timeout: 15000 });
  const villageUrl = page.url();
  const villageId = Number(villageUrl.match(/\/village\/(\d+)/)?.[1]);
  expect(villageId).toBeGreaterThan(0);

  // 以降の処理が失敗しても廃村できるように try-finally で包む
  try {
    // --- 新規ユーザーを作成して入村 ---
    const joinUserId = uniqueUserId();
    await signupViaUi(page, joinUserId, "testpass1");

    await page.goto(`village/${villageId}`);
    await expect(page.getByText("入村", { exact: true })).toBeVisible({ timeout: 15000 });

    // キャラクター名・略称を入力
    await page.getByLabel("キャラクター名").fill("テスト花子");
    await page.getByLabel("略称").fill("花");

    // オリジナル画像をアップロード
    const participateFileInput = page.locator('input[type="file"][accept="image/*"]');
    await participateFileInput.setInputFiles(TEST_IMAGE);
    await expect(page.getByText("test-chara.png")).toBeVisible();

    // 入村発言を入力
    await page.getByLabel("入村発言").fill("e2eオリジナル村テスト入村です。");

    // 入村パスワードを入力
    const passwordInput = page.getByLabel("入村パスワード");
    if ((await passwordInput.count()) > 0) {
      await passwordInput.fill("testpass");
    }

    // 確認画面へ
    await page.getByRole("button", { name: "入村確認へ" }).click();
    await expect(page.getByText("入村確認")).toBeVisible({ timeout: 15000 });

    // 確認モーダルに画像プレビューが表示されている
    await expect(page.locator("img[alt='テスト花子']")).toBeVisible();

    // 同意して入村
    await page.getByText("ルールを確認し、").click();
    await page.getByText("他者への礼節を欠いたり、").click();
    await page.getByRole("button", { name: "入村する" }).click();

    // 入村後は退村ボタンが出る
    await expect(page.getByRole("button", { name: "村を出る" })).toBeVisible({ timeout: 15000 });

    // --- 画像が nginx 経由で配信されていることを API で確認 ---
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

    const imageRes = await page.request.get(normalImage.url);
    expect(imageRes.ok(), `画像配信失敗: ${normalImage.url} → ${imageRes.status()}`).toBe(true);

    // --- SSR 経由で表情差分追加 (REST 未実装のため API 呼び出し) ---
    const loginPage = await page.request.get(`${SSR}/login`);
    const loginHtml = await loginPage.text();
    const csrfMatch = loginHtml.match(/name="_csrf"[^>]*value="([^"]+)"/);
    if (csrfMatch) {
      await page.request.post(`${SSR}/login`, {
        form: { userId: joinUserId, password: "testpass1", _csrf: csrfMatch[1] },
      });

      const villagePageRes = await page.request.get(`${SSR}/village/${villageId}`);
      const villageCsrfMatch = (await villagePageRes.text()).match(
        /name="_csrf"[^>]*value="([^"]+)"/,
      );

      if (villageCsrfMatch) {
        const faceRes = await page.request.post(
          `${SSR}/village/${villageId}/add-face-type`,
          {
            multipart: {
              faceTypeName: "怒り",
              image: {
                name: "angry-face.png",
                mimeType: "image/png",
                buffer: (await import("node:fs")).readFileSync(TEST_IMAGE),
              },
              _csrf: villageCsrfMatch[1],
            },
          },
        );
        expect(faceRes.ok(), `表情差分追加失敗: ${faceRes.status()}`).toBe(true);

        // 表情差分追加後の画像リストを確認
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

        if (angryImage) {
          const angryImgRes = await page.request.get(angryImage.url);
          expect(angryImgRes.ok(), `表情差分画像配信失敗: ${angryImage.url}`).toBe(true);
        }
      }
    }
  } finally {
    // --- 後片付け: 廃村 ---
    await loginJwt(page, "master", "testuser");
    const cancelRes = await page.request.post(`${API}/villages/${villageId}/creator/cancel`);
    expect(cancelRes.ok(), `廃村失敗: ${cancelRes.status()}`).toBe(true);
  }
});
