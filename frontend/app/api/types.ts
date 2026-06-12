/**
 * このファイルは `pnpm gen:api` による生成物です。手動編集しないでください。
 * 元: backend の OpenAPI spec (/v3/api-docs)。再生成すると上書きされます。
 */
export interface paths {
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
  "/api/v1/villages/{id}": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillage"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/ability": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["setVillageAbility"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/ability/attack-targets": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillageAttackTargets"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/ability/footsteps": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillageAbilityFootsteps"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/action": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["actionVillage"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/action-confirm": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["confirmVillageAction"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/change-skill": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["changeVillageRequestSkill"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/leave": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["leaveVillage"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/messages": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillageMessages"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/messages/anchor": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillageAnchorMessage"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/messages/anchors": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillageAnchorMessages"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/messages/latest-datetime": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillageLatestMessageDatetime"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/participants": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillageParticipants"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/participate": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["participateVillage"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/participate-confirm": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["confirmVillageParticipate"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/say": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["sayVillage"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/say-confirm": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["confirmVillageSay"];
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
  "/api/v1/villages/{id}/situation": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getVillageSituation"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/situation/me": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get: operations["getMyVillageSituation"];
    put?: never;
    post?: never;
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/switch-participate": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["switchVillageParticipate"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/update": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["updateVillage"];
    delete?: never;
    options?: never;
    head?: never;
    patch?: never;
    trace?: never;
  };
  "/api/v1/villages/{id}/vote": {
    parameters: {
      query?: never;
      header?: never;
      path?: never;
      cookie?: never;
    };
    get?: never;
    put?: never;
    post: operations["setVillageVote"];
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
    AbilityCandidatesTarget: {
      /** Format: int32 */
      charaId: number;
      name: string;
    };
    AbilityCandidatesView: {
      footsteps: string[];
      targets: components["schemas"]["AbilityCandidatesTarget"][];
    };
    AbilityType: {
      code: string;
      name: string;
      setMessageType: components["schemas"]["MessageType"];
    };
    Camp: {
      code: string;
      isCriminals: boolean;
      isFoxs: boolean;
      isLovers: boolean;
      isVillagers: boolean;
      isWolfs: boolean;
      name: string;
    };
    CampAllocation: {
      camp: components["schemas"]["Camp"];
      /** Format: int32 */
      initAllocation: number;
      /** Format: int32 */
      max?: number | null;
      /** Format: int32 */
      min: number;
      /** Format: int32 */
      reincarnationAllocation: number;
    };
    Chara: {
      defaultFirstdayMessage?: string | null;
      defaultJoinMessage?: string | null;
      /** Format: int32 */
      id: number;
      images: components["schemas"]["CharaImages"];
      name: string;
      shortName: string;
      size: components["schemas"]["CharaSize"];
    };
    CharaImage: {
      faceType: components["schemas"]["FaceType"];
      isDisplay: boolean;
      url: string;
    };
    CharaImages: {
      list: components["schemas"]["CharaImage"][];
    };
    CharaSize: {
      /** Format: int32 */
      height: number;
      /** Format: int32 */
      width: number;
    };
    Charachip: {
      charas: components["schemas"]["Charas"];
      descriptionUrl?: string | null;
      designer?: components["schemas"]["Designer"] | null;
      /** Format: int32 */
      id: number;
      isAvailableChangeName: boolean;
      name: string;
    };
    CharachipListResponse: {
      charachips: components["schemas"]["SimpleCharachipView"][];
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
    JudgeListResponse: {
      judges: components["schemas"]["JudgeView"][];
    };
    JudgeSkillView: {
      code: string;
      name: string;
    };
    JudgeView: {
      /** @enum {string} */
      countType: "HUMAN" | "WOLF" | "NO_COUNT";
      divineResultWolf: boolean;
      noDeadByAttack: boolean;
      psychicResultWolf: boolean;
      skills: components["schemas"]["JudgeSkillView"][];
    };
    LoginRequest: {
      password: string | null;
      userId: string | null;
    };
    MeResponse: {
      authorities: string[];
      canCreateVillage: boolean;
      name: string;
      /** Format: int32 */
      playerId: number;
    };
    MessageType: {
      code: string;
      isOwlViewableType: boolean;
      isSayType: boolean;
      name: string;
    };
    MessageTypeSayRestrict: {
      /** Format: int32 */
      count?: number | null;
      /** Format: int32 */
      length?: number | null;
      messageTypeCode: string | null;
      restrict: boolean | null;
    };
    NormalSayRestriction: {
      /** Format: int32 */
      count: number;
      /** Format: int32 */
      length: number;
      messageType: components["schemas"]["MessageType"];
      skill: components["schemas"]["Skill"];
    };
    ParticipantSituationView: {
      ability: components["schemas"]["ParticipantSituationViewAbility"];
      admin: components["schemas"]["ParticipantSituationViewAdmin"];
      commit: components["schemas"]["ParticipantSituationViewCommit"];
      creator: components["schemas"]["ParticipantSituationViewCreator"];
      myself?: components["schemas"]["ParticipantSituationViewMyself"] | null;
      participate: components["schemas"]["ParticipantSituationViewParticipate"];
      rp: components["schemas"]["ParticipantSituationViewRp"];
      say: components["schemas"]["ParticipantSituationViewSay"];
      skillRequest: components["schemas"]["ParticipantSituationViewSkillRequest"];
      vote: components["schemas"]["ParticipantSituationViewVote"];
    };
    ParticipantSituationViewAbility: {
      /** Format: int32 */
      attackerCharaId?: number | null;
      attackerList: components["schemas"]["ParticipantSituationViewAbilityTarget"][];
      cMadmanNames: string;
      canUseAbility: boolean;
      footstep?: string | null;
      foxNames: string;
      isAvailableNoTarget: boolean;
      isTargetingAndFootstep: boolean;
      listenMasonsNames: string;
      loversNames: string;
      masonsNames: string;
      skillHistoryList: string[];
      /** Format: int32 */
      targetCharaId?: number | null;
      targetFootstep?: string | null;
      targetFootstepList: string[];
      targetList: components["schemas"]["ParticipantSituationViewAbilityTarget"][];
      targetPrefix?: string | null;
      targetSuffix?: string | null;
      targetingMessage?: string | null;
      werewolfNames: string;
    };
    ParticipantSituationViewAbilityTarget: {
      /** Format: int32 */
      charaId: number;
      name: string;
    };
    ParticipantSituationViewAdmin: {
      isAdmin: boolean;
    };
    ParticipantSituationViewCommit: {
      isAvailableCommit: boolean;
      isCommitting: boolean;
    };
    ParticipantSituationViewCreator: {
      isAvailableCancelVillage: boolean;
      isAvailableCreatorSay: boolean;
      isAvailableExtendEpilogue: boolean;
      isAvailableKick: boolean;
      isAvailableModifySetting: boolean;
      isCreator: boolean;
    };
    ParticipantSituationViewMyself: {
      /** Format: int32 */
      charaId: number;
      /** Format: int32 */
      id: number;
      isDead: boolean;
      isSpectator: boolean;
      name: string;
      notificationKeyword?: string | null;
      shortName: string;
      skill?: components["schemas"]["ParticipantSituationViewMyselfSkill"] | null;
    };
    ParticipantSituationViewMyselfSkill: {
      code: string;
      hasDisturbAbility: boolean;
      hasInvestigateAbility: boolean;
      name: string;
    };
    ParticipantSituationViewParticipate: {
      isAvailableLeave: boolean;
      isAvailableParticipate: boolean;
      isAvailableSpectate: boolean;
      isAvailableSwitchParticipate: boolean;
      isParticipating: boolean;
      selectableCharachipList: components["schemas"]["ParticipantSituationViewParticipateCharachip"][];
    };
    ParticipantSituationViewParticipateChara: {
      /** Format: int32 */
      id: number;
      /** Format: int32 */
      imageHeight: number;
      imageUrl: string;
      /** Format: int32 */
      imageWidth: number;
      name: string;
      shortName: string;
    };
    ParticipantSituationViewParticipateCharachip: {
      charas: components["schemas"]["ParticipantSituationViewParticipateChara"][];
      /** Format: int32 */
      id: number;
      name: string;
    };
    ParticipantSituationViewRp: {
      canAddImage: boolean;
      isAvailableChangeName: boolean;
      isAvailableMemo: boolean;
    };
    ParticipantSituationViewSay: {
      defaultMessageTypeCode?: string | null;
      isAvailableSay: boolean;
      selectableCharaImageList: components["schemas"]["ParticipantSituationViewSayCharaImage"][];
      selectableMessageTypeList: components["schemas"]["ParticipantSituationViewSayMessageType"][];
    };
    ParticipantSituationViewSayCharaImage: {
      faceTypeCode: string;
      faceTypeName: string;
      url: string;
    };
    ParticipantSituationViewSayMessageType: {
      messageTypeCode: string;
      restrict: components["schemas"]["ParticipantSituationViewSayRestrict"];
      targetList: components["schemas"]["ParticipantSituationViewSayTarget"][];
    };
    ParticipantSituationViewSayRestrict: {
      isRestricted: boolean;
      /** Format: int32 */
      maxCount?: number | null;
      /** Format: int32 */
      maxLength: number;
      /** Format: int32 */
      maxLine: number;
      /** Format: int32 */
      remainingCount?: number | null;
    };
    ParticipantSituationViewSayTarget: {
      /** Format: int32 */
      charaId: number;
      name: string;
    };
    ParticipantSituationViewSkillRequest: {
      isAvailableSkillRequest: boolean;
      requestedSkillCode?: string | null;
      secondRequestedSkillCode?: string | null;
      selectableSkillList: components["schemas"]["ParticipantSituationViewSkillRequestSkill"][];
    };
    ParticipantSituationViewSkillRequestSkill: {
      code: string;
      name: string;
    };
    ParticipantSituationViewVote: {
      canVote: boolean;
      /** Format: int32 */
      targetCharaId?: number | null;
      targetList: components["schemas"]["ParticipantSituationViewAbilityTarget"][];
      targetName?: string | null;
    };
    PasswordChangeRequest: {
      confirmPassword: string | null;
      password: string | null;
    };
    RandomContent: {
      message: string;
    };
    RandomKeyword: {
      contents: components["schemas"]["RandomContent"][];
      /** Format: int32 */
      id: number;
      keyword: string;
    };
    RandomKeywordRegisterRequest: {
      keyword: string | null;
      messages: string[] | null;
    };
    RandomKeywordUpdateRequest: {
      messages: string[] | null;
    };
    RandomKeywords: {
      list: components["schemas"]["RandomKeyword"][];
    };
    RoomAssignmentResponse: {
      /** Format: int32 */
      height: number;
      roomNumbers: number[];
      /** Format: int32 */
      width: number;
    };
    RoomSize: {
      /** Format: int32 */
      height: number;
      /** Format: int32 */
      width: number;
    };
    SayRestriction: {
      normalSayRestriction: components["schemas"]["NormalSayRestriction"][];
      skillSayRestriction: components["schemas"]["SkillSayRestriction"][];
    };
    SecretSayRange: {
      code: string;
      name: string;
    };
    Setting: {
      /** Format: int32 */
      personMax: number;
      /** Format: int32 */
      personMin: number;
      tags: components["schemas"]["VillageTag"][];
    };
    SignupRequest: {
      password: string | null;
      userId: string | null;
    };
    SimpleCharachipView: {
      /** Format: int32 */
      charaNum: number;
      designerName: string;
      /** Format: int32 */
      dummyImgHeight: number;
      dummyImgUrl: string;
      /** Format: int32 */
      dummyImgWidth: number;
      /** Format: int32 */
      id: number;
      name: string;
    };
    SimpleSkillView: {
      campCode: string;
      campName: string;
      code: string;
      name: string;
      requestable: boolean;
      revivable: boolean;
      shortName: string;
      tags: string[];
    };
    SimpleVillageView: {
      /** Format: int32 */
      id: number;
      name: string;
      /** Format: int32 */
      participantCount: number;
      setting: components["schemas"]["Setting"];
      /** Format: int32 */
      spectatorCount: number;
      status: components["schemas"]["VillageStatus"];
    };
    Skill: {
      ability?: components["schemas"]["AbilityType"];
      code: string;
      isCounterDeadByDivine: boolean;
      isCounterDeadByInvestigate: boolean;
      isDeadByDivine: boolean;
      isDivineResultWolf: boolean;
      isFoxCount: boolean;
      isNoCount: boolean;
      isNoDeadByAttack: boolean;
      isNoSound: boolean;
      isOpenSkill: boolean;
      isPsychicResultWolf: boolean;
      isRequestable: boolean;
      isRevivable: boolean;
      isSayableSympathizeSay: boolean;
      isSayableTelepathy: boolean;
      isSayableWerewolfSay: boolean;
      isShogiWolf: boolean;
      isViewableAttackMessage: boolean;
      isViewableCoronerMessage: boolean;
      isViewableDivineMessage: boolean;
      isViewableFoxMessage: boolean;
      isViewableGuruMessage: boolean;
      isViewableInvestigateMessage: boolean;
      isViewableLoversMessage: boolean;
      isViewableLoversSay: boolean;
      isViewablePsychicMessage: boolean;
      isViewableSympathizeSay: boolean;
      isViewableTelepathy: boolean;
      isViewableWerewolfSay: boolean;
      isViewableWiseMessage: boolean;
      isViewableWolfCharaName: boolean;
      isWolfCount: boolean;
      name: string;
      shortName: string;
    };
    SkillAllocation: {
      /** Format: int32 */
      initAllocation: number;
      /** Format: int32 */
      max?: number | null;
      /** Format: int32 */
      min: number;
      /** Format: int32 */
      reincarnationAllocation: number;
      skill: components["schemas"]["Skill"];
    };
    SkillListResponse: {
      skills: components["schemas"]["SimpleSkillView"][];
      tags: string[];
    };
    SkillSayRestrict: {
      /** Format: int32 */
      count?: number | null;
      /** Format: int32 */
      length?: number | null;
      restrict: boolean | null;
      skillCode: string | null;
    };
    SkillSayRestriction: {
      /** Format: int32 */
      count: number;
      /** Format: int32 */
      length: number;
      messageType: components["schemas"]["MessageType"];
    };
    SkillSearchResponse: {
      skillCodes: string[];
    };
    VillageAbilityRequest: {
      /** Format: int32 */
      attackerCharaId?: number | null;
      footstep?: string | null;
      /** Format: int32 */
      targetCharaId?: number | null;
    };
    VillageActionRequest: {
      convertDisable?: boolean | null;
      message: string | null;
      myself: string | null;
      target?: string | null;
    };
    VillageAnchorMessageContent: {
      message?: components["schemas"]["VillageMessageContent"] | null;
    };
    VillageAnchorMessagesContent: {
      messageList: components["schemas"]["VillageMessageContent"][];
    };
    VillageChangeSkillRequest: {
      requestedSkill: string | null;
      secondRequestedSkill: string | null;
    };
    VillageCharaSetting: {
      charachipIds: number[];
      /** Format: int32 */
      dummyCharaId: number;
      dummyDay1Message?: string | null;
      isOriginalCharachip: boolean;
    };
    VillageCreateRequest: {
      ageLimit?: string | null;
      allowedSecretSayCode: string | null;
      availableAction: boolean | null;
      availableCommit: boolean | null;
      availableGuardSameTarget: boolean | null;
      availableSameWolfAttack: boolean | null;
      availableSpectate: boolean | null;
      availableSuddonlyDeath: boolean | null;
      campAllocationList?: components["schemas"]["VillageCreateRequestCampAllocation"][] | null;
      characterSetId: number[] | null;
      creatorIsProducer: boolean | null;
      /** Format: int32 */
      dayChangeIntervalHours: number | null;
      /** Format: int32 */
      dayChangeIntervalMinutes: number | null;
      /** Format: int32 */
      dayChangeIntervalSeconds: number | null;
      /** Format: int32 */
      dummyCharaId?: number | null;
      dummyCharaName: string | null;
      dummyCharaShortName: string | null;
      dummyDay1Message?: string | null;
      dummyJoinMessage: string | null;
      joinPassword?: string | null;
      openSkillInGrave: boolean | null;
      openVote: boolean | null;
      organization?: string | null;
      /** Format: int32 */
      personMaxNum: number | null;
      possibleSkillRequest: boolean | null;
      randomOrganization: boolean | null;
      reincarnationSkillAll: boolean | null;
      rpSayRestrictList: components["schemas"]["MessageTypeSayRestrict"][] | null;
      sayRestrictList: components["schemas"]["SkillSayRestrict"][] | null;
      shouldOriginalImage: boolean | null;
      skillSayRestrictList: components["schemas"]["MessageTypeSayRestrict"][] | null;
      /** Format: int32 */
      startDay: number | null;
      /** Format: int32 */
      startHour: number | null;
      /** Format: int32 */
      startMinute: number | null;
      /** Format: int32 */
      startMonth: number | null;
      /** Format: int32 */
      startPersonMinNum: number | null;
      /** Format: int32 */
      startYear: number | null;
      villageName: string | null;
      visibleGraveSpectateMessage: boolean | null;
      welcomeRange?: string | null;
      wolfAllocation?: components["schemas"]["VillageCreateRequestWolfAllocation"] | null;
    };
    VillageCreateRequestCampAllocation: {
      /** Format: int32 */
      allocation: number | null;
      campCode: string | null;
      /** Format: int32 */
      maxNum?: number | null;
      /** Format: int32 */
      minNum: number | null;
      /** Format: int32 */
      reincarnationAllocation: number | null;
      skillAllocation: components["schemas"]["VillageCreateRequestSkillAllocation"][] | null;
    };
    VillageCreateRequestSkillAllocation: {
      /** Format: int32 */
      allocation: number | null;
      /** Format: int32 */
      maxNum?: number | null;
      /** Format: int32 */
      minNum: number | null;
      /** Format: int32 */
      reincarnationAllocation: number | null;
      skillCode: string | null;
    };
    VillageCreateRequestWolfAllocation: {
      /** Format: int32 */
      maxNum?: number | null;
      /** Format: int32 */
      minNum: number | null;
    };
    VillageCreateResponse: {
      /** Format: int32 */
      id: number;
    };
    VillageDay: {
      /** Format: int32 */
      day: number;
      /** Format: date-time */
      dayChangeDatetime: string;
    };
    VillageDays: {
      list: components["schemas"]["VillageDay"][];
    };
    VillageDetailView: {
      days: components["schemas"]["VillageDays"];
      /** Format: int32 */
      epilogueDay?: number | null;
      /** Format: int32 */
      id: number;
      name: string;
      roomSize?: components["schemas"]["RoomSize"] | null;
      setting: components["schemas"]["VillageSettingView"];
      status: components["schemas"]["VillageStatus"];
    };
    VillageFilterParticipantContent: {
      deadStatus?: string | null;
      /** Format: int32 */
      id: number;
      /** Format: int32 */
      imgHeight: number;
      imgUrl: string;
      /** Format: int32 */
      imgWidth: number;
      name: string;
    };
    VillageFootstepContent: {
      /** Format: int32 */
      day: number;
      footstep: string;
    };
    VillageLatestMessageDatetimeContent: {
      latestMessageDatetime: string;
    };
    VillageListResponse: {
      villages: components["schemas"]["SimpleVillageView"][];
    };
    VillageMemberContent: {
      status: string;
      statusMemberList: components["schemas"]["VillageMemberDetailContent"][];
    };
    VillageMemberDetailContent: {
      charaName: string;
      deadDay?: string | null;
      lastAccess: string;
      /** Format: date-time */
      lastAccessDatetime: string;
      memo?: string | null;
    };
    VillageMemberVoteContent: {
      charaName: string;
      voteTargetList: string[];
    };
    VillageMessageContent: {
      canReply: boolean;
      canSecret: boolean;
      /** Format: int32 */
      characterId?: number | null;
      characterImageUrl?: string | null;
      characterName?: string | null;
      /** Format: int32 */
      height?: number | null;
      isBigEars: boolean;
      isConvertDisable: boolean;
      isLoud: boolean;
      isRainbow: boolean;
      messageContent: string;
      /** Format: date-time */
      messageDatetime: string;
      /** Format: int32 */
      messageNumber?: number | null;
      messageType: string;
      minHeightCss: string;
      playerName?: string | null;
      targetCharacterName?: string | null;
      /** Format: int32 */
      width?: number | null;
    };
    VillageMessageListContent: {
      /** Format: int32 */
      allPageCount: number;
      commitStatusMessage?: string | null;
      /** Format: int32 */
      currentPageNum?: number | null;
      isDispLatest: boolean;
      isExistNextPage: boolean;
      isExistPrePage: boolean;
      /** Format: int32 */
      latestDay: number;
      latestMessageDatetime?: string | null;
      messageList: components["schemas"]["VillageMessageContent"][];
      pageNumList: number[];
      suddenlyDeathMessage?: string | null;
      villageStatusMessage?: string | null;
    };
    VillageOrganize: {
      fixedOrganization: string;
      randomOrganization: components["schemas"]["VillageRandomOrganize"];
    };
    VillageParticipantContent: {
      deadStatus: string;
      isSpectator: boolean;
      name: string;
      playerName: string;
      skillName: string;
      winStatus: string;
    };
    VillageParticipantsContent: {
      list: components["schemas"]["VillageParticipantContent"][];
    };
    VillageParticipateRequest: {
      /** Format: int32 */
      charaId?: number | null;
      charaName: string | null;
      charaShortName: string | null;
      joinMessage: string | null;
      joinPassword?: string | null;
      requestedSkill?: string | null;
      secondRequestedSkill?: string | null;
      spectator?: boolean | null;
    };
    VillageRandomOrganize: {
      campAllocation: components["schemas"]["CampAllocation"][];
      skillAllocation: components["schemas"]["SkillAllocation"][];
      wolfAllocation?: components["schemas"]["WolfAllocation"] | null;
    };
    VillageRoomAssigned: {
      /** Format: int32 */
      charaImgHeight?: number | null;
      charaImgUrl?: string | null;
      /** Format: int32 */
      charaImgWidth?: number | null;
      charaName?: string | null;
      charaShortName?: string | null;
      /** Format: int32 */
      deadDay?: number | null;
      deadReason?: string | null;
      isDead?: boolean | null;
      isDummy?: boolean | null;
      /** Format: int32 */
      maxHeight?: number | null;
      /** Format: int32 */
      maxWidth?: number | null;
      /** Format: int32 */
      participantId?: number | null;
      roomNumber: string;
      skillName?: string | null;
    };
    VillageRoomAssignedRow: {
      roomAssignedList: components["schemas"]["VillageRoomAssigned"][];
    };
    VillageRule: {
      isAvailableAction: boolean;
      isAvailableCommit: boolean;
      isAvailableGuardSameTarget: boolean;
      isAvailableSameWolfAttack: boolean;
      isAvailableSpectate: boolean;
      isAvailableSuddenlyDeath: boolean;
      isCreatorIsProducer: boolean;
      isOpenSkillInGrave: boolean;
      isOpenVote: boolean;
      isPossibleSkillRequest: boolean;
      isRandomOrganization: boolean;
      isReincarnationSkillAll: boolean;
      isVisibleGraveSpectateMessage: boolean;
      secretSayRange: components["schemas"]["SecretSayRange"];
    };
    VillageSayConfirmContent: {
      message: components["schemas"]["VillageMessageContent"];
      randomKeywords: string;
    };
    VillageSayRequest: {
      convertDisable?: boolean | null;
      faceType: string | null;
      message: string | null;
      messageType: string | null;
      /** Format: int32 */
      secretSayTargetCharaId?: number | null;
    };
    VillageSettingView: {
      chara: components["schemas"]["VillageCharaSetting"];
      /** Format: int32 */
      dayChangeIntervalSeconds: number;
      organize: components["schemas"]["VillageOrganize"];
      /** Format: int32 */
      personMax: number;
      /** Format: int32 */
      personMin: number;
      rule: components["schemas"]["VillageRule"];
      sayRestriction: components["schemas"]["SayRestriction"];
      /** Format: date-time */
      startDatetime: string;
      tags: components["schemas"]["VillageTags"];
    };
    VillageSituationContent: {
      ability: string;
      attackedChara: string;
      /** Format: int32 */
      day: number;
      executedChara: string;
      revivalChara: string;
      suddonlyDeathChara: string;
      suicideChara: string;
    };
    VillageSituationView: {
      footstepList: components["schemas"]["VillageFootstepContent"][];
      isViewableSpoilerContent: boolean;
      memberList: components["schemas"]["VillageMemberContent"][];
      participantList: components["schemas"]["VillageFilterParticipantContent"][];
      roomAssignedRowList?: components["schemas"]["VillageRoomAssignedRow"][] | null;
      /** Format: int32 */
      roomWidth?: number | null;
      situationList: components["schemas"]["VillageSituationContent"][];
      vote?: components["schemas"]["VillageVoteContent"] | null;
    };
    VillageStatus: {
      code: string;
      isCanceled: boolean;
      isEpilogue: boolean;
      isFinished: boolean;
      isNotFinished: boolean;
      isProgress: boolean;
      isPrologue: boolean;
      isSettleOrCanceled: boolean;
      isSettled: boolean;
      name: string;
    };
    VillageTag: {
      code: string;
      name: string;
    };
    VillageTags: {
      list: components["schemas"]["VillageTag"][];
    };
    VillageUpdateResponse: {
      /** Format: int32 */
      latestDay: number;
    };
    VillageVoteContent: {
      /** Format: int32 */
      maxVoteCount: number;
      voteList: components["schemas"]["VillageMemberVoteContent"][];
    };
    VillageVoteRequest: {
      /** Format: int32 */
      targetCharaId: number | null;
    };
    WolfAllocation: {
      /** Format: int32 */
      max?: number | null;
      /** Format: int32 */
      min: number;
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
          /** Format: binary */
          dummyCharaImage?: string;
          request: components["schemas"]["VillageCreateRequest"];
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
  getVillage: {
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
          "*/*": components["schemas"]["VillageDetailView"];
        };
      };
    };
  };
  setVillageAbility: {
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
        "application/json": components["schemas"]["VillageAbilityRequest"];
      };
    };
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
  getVillageAttackTargets: {
    parameters: {
      query: {
        charaId: number;
      };
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
          "*/*": components["schemas"]["AbilityCandidatesView"];
        };
      };
    };
  };
  getVillageAbilityFootsteps: {
    parameters: {
      query?: {
        charaId?: number;
        targetCharaId?: number;
      };
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
          "*/*": components["schemas"]["AbilityCandidatesView"];
        };
      };
    };
  };
  actionVillage: {
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
        "application/json": components["schemas"]["VillageActionRequest"];
      };
    };
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
  confirmVillageAction: {
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
        "application/json": components["schemas"]["VillageActionRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["VillageSayConfirmContent"];
        };
      };
    };
  };
  changeVillageRequestSkill: {
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
        "application/json": components["schemas"]["VillageChangeSkillRequest"];
      };
    };
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
  leaveVillage: {
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
  getVillageMessages: {
    parameters: {
      query?: {
        day?: number;
        pageSize?: number;
        pageNum?: number;
        participantIds?: number[];
        toParticipantIds?: number[];
        types?: string[];
        keywords?: string;
      };
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
          "*/*": components["schemas"]["VillageMessageListContent"];
        };
      };
    };
  };
  getVillageAnchorMessage: {
    parameters: {
      query: {
        messageType: string;
        messageNumber: number;
      };
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
          "*/*": components["schemas"]["VillageAnchorMessageContent"];
        };
      };
    };
  };
  getVillageAnchorMessages: {
    parameters: {
      query: {
        anchors: string;
      };
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
          "*/*": components["schemas"]["VillageAnchorMessagesContent"];
        };
      };
    };
  };
  getVillageLatestMessageDatetime: {
    parameters: {
      query?: {
        day?: number;
        pageSize?: number;
        pageNum?: number;
        participantIds?: number[];
        toParticipantIds?: number[];
        types?: string[];
        keywords?: string;
      };
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
          "*/*": components["schemas"]["VillageLatestMessageDatetimeContent"];
        };
      };
    };
  };
  getVillageParticipants: {
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
          "*/*": components["schemas"]["VillageParticipantsContent"];
        };
      };
    };
  };
  participateVillage: {
    parameters: {
      query?: never;
      header?: never;
      path: {
        id: number;
      };
      cookie?: never;
    };
    requestBody?: {
      content: {
        "multipart/form-data": {
          /** Format: binary */
          charaImage?: string;
          request: components["schemas"]["VillageParticipateRequest"];
        };
      };
    };
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
  confirmVillageParticipate: {
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
        "application/json": components["schemas"]["VillageParticipateRequest"];
      };
    };
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
  sayVillage: {
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
        "application/json": components["schemas"]["VillageSayRequest"];
      };
    };
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
  confirmVillageSay: {
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
        "application/json": components["schemas"]["VillageSayRequest"];
      };
    };
    responses: {
      /** @description OK */
      200: {
        headers: {
          [name: string]: unknown;
        };
        content: {
          "*/*": components["schemas"]["VillageSayConfirmContent"];
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
  getVillageSituation: {
    parameters: {
      query?: {
        day?: number;
      };
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
          "*/*": components["schemas"]["VillageSituationView"];
        };
      };
    };
  };
  getMyVillageSituation: {
    parameters: {
      query?: {
        day?: number;
      };
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
          "*/*": components["schemas"]["ParticipantSituationView"];
        };
      };
    };
  };
  switchVillageParticipate: {
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
  updateVillage: {
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
          "*/*": components["schemas"]["VillageUpdateResponse"];
        };
      };
    };
  };
  setVillageVote: {
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
        "application/json": components["schemas"]["VillageVoteRequest"];
      };
    };
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
}
