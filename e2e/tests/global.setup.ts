import { test as setup } from "@playwright/test";
import * as fs from "node:fs";
import * as path from "node:path";
import { fileURLToPath } from "node:url";
import {
  findVillages,
  provisionInProgressVillage,
  provisionRecruitingVillage,
  type ProvisionCache,
  type SimpleVillage,
} from "./helpers/provision";

const CACHE_PATH = path.join(
  path.dirname(fileURLToPath(import.meta.url)),
  ".provision-cache.json",
);

setup("provision shared villages", async ({ page }) => {
  setup.setTimeout(180000);

  let inProgress: SimpleVillage;
  const existing = await findVillages(page, ["IN_PROGRESS"]);
  if (existing.length > 0) {
    inProgress = existing[0];
  } else {
    inProgress = await provisionInProgressVillage(page);
  }

  let recruiting: SimpleVillage;
  const existingPrep = await findVillages(page, ["IN_PREPARATION"]);
  if (existingPrep.length > 0) {
    recruiting = existingPrep[0];
  } else {
    recruiting = await provisionRecruitingVillage(page);
  }

  const cache: ProvisionCache = { inProgress, recruiting };
  fs.writeFileSync(CACHE_PATH, JSON.stringify(cache));
});
