/**
 * このファイルは `pnpm gen:api` による生成物です。手動編集しないでください。
 * 元: backend の OpenAPI spec (/v3/api-docs)。再生成すると上書きされます。
 */
export interface paths {
  "/api/v1/auth/signup": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["signup"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/auth/refresh": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["refresh"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/auth/password": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["changePassword"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/auth/logout": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["logout"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/auth/login": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["login"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["list"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/skills": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["list_1"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/skills/search": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["search"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/rule/judges": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["judges"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/charachips": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["list_2"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/auth/me": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["me"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
}
export type webhooks = Record<string, never>;
export interface components {
  schemas: {
    SignupRequest: {
      userId: string | null;
      password: string | null;
    };
    MeResponse: {
      /** Format: int32 */
      playerId: number;
      name: string;
      authorities: string[];
      canCreateVillage: boolean;
    };
    PasswordChangeRequest: {
      password: string | null;
      confirmPassword: string | null;
    };
    LoginRequest: {
      userId: string | null;
      password: string | null;
    };
    Setting: {
      /** Format: int32 */
      personMin: number;
      /** Format: int32 */
      personMax: number;
      tags: components["schemas"]["VillageTag"][];
    };
    SimpleVillageView: {
      /** Format: int32 */
      id: number;
      name: string;
      status: components["schemas"]["VillageStatus"];
      /** Format: int32 */
      participantCount: number;
      /** Format: int32 */
      spectatorCount: number;
      setting: components["schemas"]["Setting"];
    };
    VillageListResponse: {
      villages: components["schemas"]["SimpleVillageView"][];
    };
    VillageStatus: {
      code: string;
      name: string;
      isPrologue: boolean;
      isCanceled: boolean;
      isSettled: boolean;
      isProgress: boolean;
      isEpilogue: boolean;
      isNotFinished: boolean;
      isSettleOrCanceled: boolean;
      isFinished: boolean;
    };
    VillageTag: {
      code: string;
      name: string;
    };
    SimpleSkillView: {
      code: string;
      name: string;
      shortName: string;
      campCode: string;
      campName: string;
      tags: string[];
    };
    SkillListResponse: {
      skills: components["schemas"]["SimpleSkillView"][];
      tags: string[];
    };
    SkillSearchResponse: {
      skillCodes: string[];
    };
    JudgeListResponse: {
      judges: components["schemas"]["JudgeView"][];
    };
    JudgeSkillView: {
      code: string;
      name: string;
    };
    JudgeView: {
      skills: components["schemas"]["JudgeSkillView"][];
      divineResultWolf: boolean;
      psychicResultWolf: boolean;
      noDeadByAttack: boolean;
      count: string;
    };
    CharachipListResponse: {
      charachips: components["schemas"]["SimpleCharachipView"][];
    };
    SimpleCharachipView: {
      /** Format: int32 */
      id: number;
      name: string;
    };
  };
  responses: never;
  parameters: never;
  requestBodies: never;
  headers: never;
  pathItems: never;
}
export type $defs = Record<string, never>;
export interface operations {
  signup: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: {
        id_register?: boolean;
      };
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["SignupRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["MeResponse"];
        };
      };
    };
  };
  refresh: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: {
        refresh_token?: string;
      };
    };
    requestBody?: never;
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["MeResponse"];
        };
      };
    };
  };
  changePassword: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["PasswordChangeRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content?: never;
      };
    };
  };
  logout: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: {
        refresh_token?: string;
      };
    };
    requestBody?: never;
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content?: never;
      };
    };
  };
  login: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["LoginRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["MeResponse"];
        };
      };
    };
  };
  list: {
    parameters: {
      query?: {
        status?: string[];
        charachip?: number[];
        skill?: string[];
        random?: boolean;
        order?: string;
      };
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody?: never;
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["VillageListResponse"];
        };
      };
    };
  };
  list_1: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody?: never;
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["SkillListResponse"];
        };
      };
    };
  };
  search: {
    parameters: {
      query?: {
        tags?: string[];
        name?: string;
        villageId?: number;
      };
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody?: never;
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["SkillSearchResponse"];
        };
      };
    };
  };
  judges: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody?: never;
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["JudgeListResponse"];
        };
      };
    };
  };
  list_2: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody?: never;
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["CharachipListResponse"];
        };
      };
    };
  };
  me: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody?: never;
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["MeResponse"];
        };
      };
    };
  };
}
