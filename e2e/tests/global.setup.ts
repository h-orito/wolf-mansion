import { test as setup } from "@playwright/test";
import * as fs from "node:fs";
import * as path from "node:path";
import { fileURLToPath } from "node:url";
import {
  provisionInProgressVillage,
  provisionRecruitingVillage,
  provisionVotingVillage,
  type ProvisionCache,
} from "./helpers/provision";

const CACHE_PATH = path.join(
  path.dirname(fileURLToPath(import.meta.url)),
  ".provision-cache.json",
);

// 共有村はこの実行専用に必ず新規作成する。DB にたまたまある村を拾うと、
// 開始時刻超過・日数進行・master 不参加など前提の崩れた村に当たってフレークする
setup("provision shared villages", async ({ page }) => {
  setup.setTimeout(180000);

  // 過去の実行の古いキャッシュを specs に読ませないよう、provision 前に消しておく
  fs.rmSync(CACHE_PATH, { force: true });

  const inProgress = await provisionInProgressVillage(page);
  const voting = await provisionVotingVillage(page);
  const recruiting = await provisionRecruitingVillage(page);

  const cache: ProvisionCache = { inProgress, recruiting, voting };
  fs.writeFileSync(CACHE_PATH, JSON.stringify(cache));
});
