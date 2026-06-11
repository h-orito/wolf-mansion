/**
 * このファイルは `pnpm gen:api` による生成物です。手動編集しないでください。
 * 元: backend の OpenAPI spec (/v3/api-docs)。再生成すると上書きされます。
 */
export interface paths {
  "/api/v1/random-keywords/{id}": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["detail"];
    put: operations["update"];
    post?: never;
    delete: operations["delete"];
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/random-keywords": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["list"];
    put?: never;
    post: operations["register"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
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
    get: operations["list_1"];
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
    get: operations["list_2"];
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
  "/api/v1/rooms": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["roomAssignment"];
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
    get: operations["list_3"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/charachips/{id}": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["detail_1"];
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
    RandomKeywordUpdateRequest: {
      messages: string[] | null;
    };
    RandomContent: {
      message: string;
    };
    RandomKeyword: {
      /** Format: int32 */
      id: number;
      keyword: string;
      contents: components["schemas"]["RandomContent"][];
    };
    RandomKeywordRegisterRequest: {
      keyword: string | null;
      messages: string[] | null;
    };
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
      requestable: boolean;
      revivable: boolean;
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
      /** @enum {string} */
      countType: "HUMAN" | "WOLF" | "NO_COUNT";
    };
    RoomAssignmentResponse: {
      /** Format: int32 */
      width: number;
      /** Format: int32 */
      height: number;
      roomNumbers: number[];
    };
    RandomKeywords: {
      list: components["schemas"]["RandomKeyword"][];
    };
    CharachipListResponse: {
      charachips: components["schemas"]["SimpleCharachipView"][];
    };
    SimpleCharachipView: {
      /** Format: int32 */
      id: number;
      name: string;
      designerName: string;
      /** Format: int32 */
      charaNum: number;
      dummyImgUrl: string;
      /** Format: int32 */
      dummyImgWidth: number;
      /** Format: int32 */
      dummyImgHeight: number;
    };
    Chara: {
      /** Format: int32 */
      id: number;
      name: string;
      shortName: string;
      defaultJoinMessage?: string | null;
      defaultFirstdayMessage?: string | null;
      size: components["schemas"]["CharaSize"];
      images: components["schemas"]["CharaImages"];
    };
    CharaImage: {
      faceType: components["schemas"]["FaceType"];
      url: string;
      isDisplay: boolean;
    };
    CharaImages: {
      list: components["schemas"]["CharaImage"][];
    };
    CharaSize: {
      /** Format: int32 */
      width: number;
      /** Format: int32 */
      height: number;
    };
    Charachip: {
      /** Format: int32 */
      id: number;
      name: string;
      designer?: components["schemas"]["Designer"] | null;
      descriptionUrl?: string | null;
      isAvailableChangeName: boolean;
      charas: components["schemas"]["Charas"];
    };
    Charas: {
      list: components["schemas"]["Chara"][];
    };
    Designer: {
      /** Format: int32 */
      id: number;
      name: string;
    };
    FaceType: {
      code: string;
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
  detail: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        id: number;
      };
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
          "*/*": components["schemas"]["RandomKeyword"];
        };
      };
    };
  };
  update: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        id: number;
      };
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["RandomKeywordUpdateRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["RandomKeyword"];
        };
      };
    };
  };
  delete: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        id: number;
      };
      cookie?: never;
    };
    requestBody?: never;
    responses: {
      /** @description No Content */
      204: {
        headers: {
          [name: string]: unknown;
        };
        content?: never;
      };
    };
  };
  list: {
    parameters: {
      query?: {
        q?: string;
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
          "*/*": components["schemas"]["RandomKeywords"];
        };
      };
    };
  };
  register: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody: {
      content: {
        "application/json": components["schemas"]["RandomKeywordRegisterRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["RandomKeyword"];
        };
      };
    };
  };
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
  list_1: {
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
  roomAssignment: {
    parameters: {
      query: {
        personNum: number;
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
          "*/*": components["schemas"]["RoomAssignmentResponse"];
        };
      };
    };
  };
  list_3: {
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
  detail_1: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        id: number;
      };
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
          "*/*": components["schemas"]["Charachip"];
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
