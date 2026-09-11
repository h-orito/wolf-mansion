/**
 * このファイルは `pnpm gen:api` による生成物です。手動編集しないでください。
 * 元: backend の OpenAPI spec (/v3/api-docs)。再生成すると上書きされます。
 */

/** パスワードポリシー (backend PasswordPolicy 由来)。 */
export const PASSWORD_MIN_LENGTH = 3;
export const PASSWORD_MAX_LENGTH = 60;
export const PASSWORD_PATTERN = "[\\x21-\\x7E]+";

/** signup userId 制約 (backend SignupRequest 由来)。 */
export const SIGNUP_USER_ID_MIN_LENGTH = 3;
export const SIGNUP_USER_ID_MAX_LENGTH = 12;
export const SIGNUP_USER_ID_PATTERN = "[a-zA-Z][a-zA-Z0-9\\-_]*";

/** ランダムキーワード制約 (backend RandomKeywordPolicy 由来)。 */
export const RANDOM_KEYWORD_MIN_LENGTH = 3;
export const RANDOM_KEYWORD_MAX_LENGTH = 10;
export const RANDOM_KEYWORD_PATTERN = "[a-zA-Z]*";
export const RANDOM_KEYWORD_MESSAGE_MIN_LENGTH = 1;
export const RANDOM_KEYWORD_MESSAGE_MAX_LENGTH = 20;
