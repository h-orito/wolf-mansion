import { test as teardown } from "@playwright/test";
import { clearCreatedVillages, readCreatedVillages, settleVillage } from "./helpers/provision";

// この実行で作成した村を終了状態に片付ける。進行中の村を残すと、開発サーバー側の
// 日付更新スケジューラに日送りされてテストアカウントが突然死ペナルティを受けたり、
// 村一覧を汚したりするため
teardown("settle provisioned villages", async ({ page }) => {
  // 全村を直列に settle するため、村数が多い実行 (前回残骸の回収時など) に備えて長めにとる
  teardown.setTimeout(300000);

  for (const village of readCreatedVillages()) {
    try {
      await settleVillage(page, village.id);
    } catch {
      // 片付けは best effort でよい。失敗しても次回実行は新しい村を作るため影響しない
    }
  }
  clearCreatedVillages();
});
