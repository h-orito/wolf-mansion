// @ts-check
/**
 * OpenAPI → TypeScript 型 / 定数 生成スクリプト (Step 3.4)。
 *
 * 稼働中 backend の `/v3/api-docs` を取得し、以下を `app/api/` に生成する:
 *   - openapi.json … 取得した OpenAPI spec (drift 検知の基準)
 *   - types.ts     … openapi-typescript による型定義
 *   - constants.ts … request schema の制約 (minLength/maxLength/pattern) 由来の共有定数
 *
 * すべて生成物。手動編集しないこと (再生成で上書きされる)。
 * 実行前に backend を起動しておく (既定 http://localhost:8089/wolf-mansion-api)。
 *
 * env:
 *   BACKEND_ORIGIN … backend オリジン (既定 http://localhost:8089)。CI は別ポートを渡す
 *   VITE_API_BASE  … backend context-path (既定 /wolf-mansion-api)
 *   OPENAPI_URL    … spec URL を直接指定 (上記 2 つを上書き)
 */
import { mkdir, writeFile } from "node:fs/promises";
import { dirname, resolve } from "node:path";
import { fileURLToPath } from "node:url";
import openapiTS, { astToString } from "openapi-typescript";

const BACKEND_ORIGIN = process.env.BACKEND_ORIGIN ?? "http://localhost:8089";
const API_BASE = process.env.VITE_API_BASE ?? "/wolf-mansion-api";
const SPEC_URL = process.env.OPENAPI_URL ?? `${BACKEND_ORIGIN}${API_BASE}/v3/api-docs`;

const apiDir = resolve(dirname(fileURLToPath(import.meta.url)), "../app/api");

const GENERATED_HEADER = `/**
 * このファイルは \`pnpm gen:api\` による生成物です。手動編集しないでください。
 * 元: backend の OpenAPI spec (/v3/api-docs)。再生成すると上書きされます。
 */
`;

/** spec を取得する。 */
async function fetchSpec() {
  const res = await fetch(SPEC_URL);
  if (!res.ok) {
    throw new Error(
      `OpenAPI spec の取得に失敗: ${SPEC_URL} → HTTP ${res.status}. backend が起動しているか確認してください。`,
    );
  }
  return res.json();
}

/** request schema のプロパティから制約定数を抽出する。 */
function extractConstants(spec) {
  const schemas = spec.components?.schemas ?? {};
  const signup = schemas.SignupRequest?.properties ?? {};
  const password = signup.password ?? {};
  const userId = signup.userId ?? {};
  const randomKeywordRegister = schemas.RandomKeywordRegisterRequest?.properties ?? {};
  const randomKeyword = randomKeywordRegister.keyword ?? {};
  const randomKeywordMessage = randomKeywordRegister.messages?.items ?? {};

  // backend を制約の単一ソースにするのが目的なので、ソース欠落は黙って `undefined` 定数を吐かず loud に落とす
  // (SpringDoc の回帰や @Size/@Pattern 取り違えで frontend バリデーションが無効化されるのを防ぐ)。
  const required = {
    "SignupRequest.password.minLength": password.minLength,
    "SignupRequest.password.maxLength": password.maxLength,
    "SignupRequest.password.pattern": password.pattern,
    "SignupRequest.userId.minLength": userId.minLength,
    "SignupRequest.userId.maxLength": userId.maxLength,
    "SignupRequest.userId.pattern": userId.pattern,
    "RandomKeywordRegisterRequest.keyword.minLength": randomKeyword.minLength,
    "RandomKeywordRegisterRequest.keyword.maxLength": randomKeyword.maxLength,
    "RandomKeywordRegisterRequest.keyword.pattern": randomKeyword.pattern,
    "RandomKeywordRegisterRequest.messages.items.minLength": randomKeywordMessage.minLength,
    "RandomKeywordRegisterRequest.messages.items.maxLength": randomKeywordMessage.maxLength,
  };
  const missing = Object.entries(required)
    .filter(([, v]) => v === undefined)
    .map(([k]) => k);
  if (missing.length > 0) {
    throw new Error(
      `OpenAPI spec に必須の制約が見つかりません: ${missing.join(", ")}. ` +
        "backend の @Size/@Pattern (PasswordPolicy / SignupRequest) を確認してください。",
    );
  }

  /** @param {string} name @param {unknown} value */
  const line = (name, value) => `export const ${name} = ${JSON.stringify(value)};`;

  return [
    GENERATED_HEADER,
    "/** パスワードポリシー (backend PasswordPolicy 由来)。 */",
    line("PASSWORD_MIN_LENGTH", password.minLength),
    line("PASSWORD_MAX_LENGTH", password.maxLength),
    line("PASSWORD_PATTERN", password.pattern),
    "",
    "/** signup userId 制約 (backend SignupRequest 由来)。 */",
    line("SIGNUP_USER_ID_MIN_LENGTH", userId.minLength),
    line("SIGNUP_USER_ID_MAX_LENGTH", userId.maxLength),
    line("SIGNUP_USER_ID_PATTERN", userId.pattern),
    "",
    "/** ランダムキーワード制約 (backend RandomKeywordPolicy 由来)。 */",
    line("RANDOM_KEYWORD_MIN_LENGTH", randomKeyword.minLength),
    line("RANDOM_KEYWORD_MAX_LENGTH", randomKeyword.maxLength),
    line("RANDOM_KEYWORD_PATTERN", randomKeyword.pattern),
    line("RANDOM_KEYWORD_MESSAGE_MIN_LENGTH", randomKeywordMessage.minLength),
    line("RANDOM_KEYWORD_MESSAGE_MAX_LENGTH", randomKeywordMessage.maxLength),
    "",
  ].join("\n");
}

async function main() {
  const spec = await fetchSpec();
  await mkdir(apiDir, { recursive: true });

  // SpringDoc は server URL をリクエスト由来 (host:port + context-path) で動的に埋め込むため、
  // 生成元のポート (8089 / e2e 18089 / CI) によって `servers` が揺れて drift の温床になる。
  // frontend は相対 base (VITE_API_BASE) で叩くので server URL は不要 → 除去してポート非依存にする。
  delete spec.servers;

  // 1. spec を保存 (drift 検知の基準)
  await writeFile(resolve(apiDir, "openapi.json"), `${JSON.stringify(spec, null, 2)}\n`);

  // 2. 型生成
  const ast = await openapiTS(spec);
  await writeFile(resolve(apiDir, "types.ts"), GENERATED_HEADER + astToString(ast));

  // 3. 共有定数生成
  await writeFile(resolve(apiDir, "constants.ts"), extractConstants(spec));

  console.log(`generated app/api/{openapi.json,types.ts,constants.ts} from ${SPEC_URL}`);
}

await main();
