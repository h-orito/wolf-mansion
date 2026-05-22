/** アプリの basename。react-router.config.ts の basename と一致させる。 */
export const APP = "/wolf-mansion";

/** globalSetup が保存する player のログイン storageState ファイル。 */
export const PLAYER_STORAGE = ".auth/player.json";

/** 未認証コンテキスト (storageState を空にする) 用の指定値。 */
export const NO_AUTH = { cookies: [], origins: [] };
