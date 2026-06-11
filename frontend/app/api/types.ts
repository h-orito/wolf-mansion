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
  "/api/v1/villages": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["list"];
    put?: never;
    post: operations["create"];
    delete?: never;
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
    get: operations["list_1"];
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
  "/api/v1/villages/{id}/setting": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["setting"];
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
    MessageTypeSayRestrict: {
      messageTypeCode: string | null;
      restrict: boolean | null;
      /** Format: int32 */
      length?: number | null;
      /** Format: int32 */
      count?: number | null;
    };
    SkillSayRestrict: {
      skillCode: string | null;
      restrict: boolean | null;
      /** Format: int32 */
      length?: number | null;
      /** Format: int32 */
      count?: number | null;
    };
    VillageCreateRequest: {
      villageName: string | null;
      welcomeRange?: string | null;
      /** Format: int32 */
      startPersonMinNum: number | null;
      /** Format: int32 */
      personMaxNum: number | null;
      /** Format: int32 */
      dayChangeIntervalHours: number | null;
      /** Format: int32 */
      dayChangeIntervalMinutes: number | null;
      /** Format: int32 */
      dayChangeIntervalSeconds: number | null;
      /** Format: int32 */
      startYear: number | null;
      /** Format: int32 */
      startMonth: number | null;
      /** Format: int32 */
      startDay: number | null;
      /** Format: int32 */
      startHour: number | null;
      /** Format: int32 */
      startMinute: number | null;
      shouldOriginalImage: boolean | null;
      characterSetId: number[] | null;
      /** Format: int32 */
      dummyCharaId?: number | null;
      dummyCharaName: string | null;
      dummyCharaShortName: string | null;
      dummyJoinMessage: string | null;
      dummyDay1Message?: string | null;
      joinPassword?: string | null;
      openVote: boolean | null;
      possibleSkillRequest: boolean | null;
      availableSameWolfAttack: boolean | null;
      availableGuardSameTarget: boolean | null;
      reincarnationSkillAll: boolean | null;
      availableSuddonlyDeath: boolean | null;
      availableCommit: boolean | null;
      availableSpectate: boolean | null;
      creatorIsProducer: boolean | null;
      openSkillInGrave: boolean | null;
      visibleGraveSpectateMessage: boolean | null;
      availableAction: boolean | null;
      randomOrganization: boolean | null;
      organization?: string | null;
      campAllocationList?: components["schemas"]["VillageCreateRequestCampAllocation"][] | null;
      wolfAllocation?: components["schemas"]["VillageCreateRequestWolfAllocation"] | null;
      allowedSecretSayCode: string | null;
      sayRestrictList: components["schemas"]["SkillSayRestrict"][] | null;
      skillSayRestrictList: components["schemas"]["MessageTypeSayRestrict"][] | null;
      rpSayRestrictList: components["schemas"]["MessageTypeSayRestrict"][] | null;
      ageLimit?: string | null;
    };
    VillageCreateRequestCampAllocation: {
      campCode: string | null;
      /** Format: int32 */
      minNum: number | null;
      /** Format: int32 */
      maxNum?: number | null;
      /** Format: int32 */
      allocation: number | null;
      /** Format: int32 */
      reincarnationAllocation: number | null;
      skillAllocation: components["schemas"]["VillageCreateRequestSkillAllocation"][] | null;
    };
    VillageCreateRequestSkillAllocation: {
      skillCode: string | null;
      /** Format: int32 */
      minNum: number | null;
      /** Format: int32 */
      maxNum?: number | null;
      /** Format: int32 */
      allocation: number | null;
      /** Format: int32 */
      reincarnationAllocation: number | null;
    };
    VillageCreateRequestWolfAllocation: {
      /** Format: int32 */
      minNum: number | null;
      /** Format: int32 */
      maxNum?: number | null;
    };
    VillageCreateResponse: {
      /** Format: int32 */
      id: number;
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
    AbilityType: {
      code: string;
      name: string;
      setMessageType: components["schemas"]["MessageType"];
    };
    Camp: {
      code: string;
      name: string;
      isFoxs: boolean;
      isLovers: boolean;
      isVillagers: boolean;
      isWolfs: boolean;
      isCriminals: boolean;
    };
    CampAllocation: {
      camp: components["schemas"]["Camp"];
      /** Format: int32 */
      min: number;
      /** Format: int32 */
      max?: number | null;
      /** Format: int32 */
      initAllocation: number;
      /** Format: int32 */
      reincarnationAllocation: number;
    };
    MessageType: {
      code: string;
      name: string;
      isOwlViewableType: boolean;
      isSayType: boolean;
    };
    NormalSayRestriction: {
      skill: components["schemas"]["Skill"];
      messageType: components["schemas"]["MessageType"];
      /** Format: int32 */
      count: number;
      /** Format: int32 */
      length: number;
    };
    SayRestriction: {
      normalSayRestriction: components["schemas"]["NormalSayRestriction"][];
      skillSayRestriction: components["schemas"]["SkillSayRestriction"][];
    };
    SecretSayRange: {
      code: string;
      name: string;
    };
    Skill: {
      code: string;
      name: string;
      shortName: string;
      histories: components["schemas"]["SkillHistories"];
      isRequestable: boolean;
      isWolfCount: boolean;
      ability?: components["schemas"]["AbilityType"];
      isNoSound: boolean;
      isNoDeadByAttack: boolean;
      isShogiWolf: boolean;
      isViewableWolfCharaName: boolean;
      isSayableWerewolfSay: boolean;
      isFoxCount: boolean;
      isViewableSympathizeSay: boolean;
      isDivineResultWolf: boolean;
      isDeadByDivine: boolean;
      isCounterDeadByInvestigate: boolean;
      isOpenSkill: boolean;
      isViewableTelepathy: boolean;
      isSayableTelepathy: boolean;
      isViewableWerewolfSay: boolean;
      isViewableAttackMessage: boolean;
      isViewableCoronerMessage: boolean;
      isViewableLoversSay: boolean;
      isSayableSympathizeSay: boolean;
      isViewableFoxMessage: boolean;
      isViewableGuruMessage: boolean;
      isViewableInvestigateMessage: boolean;
      isViewableLoversMessage: boolean;
      isViewableDivineMessage: boolean;
      isViewablePsychicMessage: boolean;
      isViewableWiseMessage: boolean;
      isNoCount: boolean;
      isPsychicResultWolf: boolean;
      isRevivable: boolean;
      isCounterDeadByDivine: boolean;
    };
    SkillAllocation: {
      skill: components["schemas"]["Skill"];
      /** Format: int32 */
      min: number;
      /** Format: int32 */
      max?: number | null;
      /** Format: int32 */
      initAllocation: number;
      /** Format: int32 */
      reincarnationAllocation: number;
    };
    SkillHistories: {
      list: components["schemas"]["SkillHistory"][];
    };
    SkillHistory: {
      skill: unknown;
      /** Format: int32 */
      day: number;
    };
    SkillSayRestriction: {
      messageType: components["schemas"]["MessageType"];
      /** Format: int32 */
      count: number;
      /** Format: int32 */
      length: number;
    };
    VillageCharaSetting: {
      isOriginalCharachip: boolean;
      /** Format: int32 */
      dummyCharaId: number;
      dummyDay1Message?: string | null;
      charachipIds: number[];
    };
    VillageOrganize: {
      fixedOrganization: string;
      randomOrganization: components["schemas"]["VillageRandomOrganize"];
    };
    VillageRandomOrganize: {
      skillAllocation: components["schemas"]["SkillAllocation"][];
      campAllocation: components["schemas"]["CampAllocation"][];
      wolfAllocation?: components["schemas"]["WolfAllocation"] | null;
    };
    VillageRule: {
      isOpenVote: boolean;
      isPossibleSkillRequest: boolean;
      isAvailableSpectate: boolean;
      isCreatorIsProducer: boolean;
      isAvailableSameWolfAttack: boolean;
      isOpenSkillInGrave: boolean;
      isVisibleGraveSpectateMessage: boolean;
      isAvailableSuddenlyDeath: boolean;
      isAvailableCommit: boolean;
      isAvailableGuardSameTarget: boolean;
      isAvailableAction: boolean;
      isRandomOrganization: boolean;
      isReincarnationSkillAll: boolean;
      secretSayRange: components["schemas"]["SecretSayRange"];
    };
    VillageSettingView: {
      chara: components["schemas"]["VillageCharaSetting"];
      /** Format: int32 */
      personMin: number;
      /** Format: int32 */
      personMax: number;
      /** Format: date-time */
      startDatetime: string;
      /** Format: int32 */
      dayChangeIntervalSeconds: number;
      rule: components["schemas"]["VillageRule"];
      organize: components["schemas"]["VillageOrganize"];
      sayRestriction: components["schemas"]["SayRestriction"];
      tags: components["schemas"]["VillageTags"];
    };
    VillageTags: {
      list: components["schemas"]["VillageTag"][];
    };
    WolfAllocation: {
      /** Format: int32 */
      min: number;
      /** Format: int32 */
      max?: number | null;
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
  create: {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    requestBody?: {
      content: {
        "multipart/form-data": {
          request: components["schemas"]["VillageCreateRequest"];
          /** Format: binary */
          dummyCharaImage?: string;
        };
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["VillageCreateResponse"];
        };
      };
    };
  };
  list_1: {
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
  setting: {
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
          "*/*": components["schemas"]["VillageSettingView"];
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
