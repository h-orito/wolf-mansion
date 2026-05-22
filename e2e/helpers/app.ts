import { join } from "node:path";

/** アプリの basename。react-router.config.ts の basename と一致させる。 */
export const APP = "/wolf-mansion";

/** e2e/ ディレクトリの絶対パス (このファイルは e2e/helpers/ 配下)。 */
const E2E_ROOT = join(import.meta.dirname, "..");

/**
 * globalSetup が保存する player のログイン storageState (絶対パス)。
 *
 * 絶対パスにしているのは、`playwright test` を e2e/ 以外から `--config` 指定で
 * 実行しても globalSetup の保存先とテストの読込先がズレないようにするため。
 */
export const PLAYER_STORAGE = join(E2E_ROOT, ".auth", "player.json");

/** 未認証コンテキスト (storageState を空にする) 用の指定値。 */
export const NO_AUTH = { cookies: [], origins: [] };
