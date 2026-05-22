/**
 * テスト用ユーザ。ローカル DB に投入済みのプレイヤーで、パスワードは全員 `testuser`。
 */
export const USERS = {
  /** 一般プレイヤー権限 */
  player: { userId: "testuser01", password: "testuser" },
  /** 管理者権限 */
  admin: { userId: "master", password: "testuser" },
} as const;
