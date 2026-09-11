import type { VillageSettingForm } from "~/features/village/api";
import type { SimpleSkillView } from "~/features/skills/api";
import { addPersonCountPrefix, stripPersonCountPrefix } from "./organization";
import {
  createDefaultCampAllocations,
  createDefaultSayRestricts,
  createDefaultMessageTypeSayRestricts,
  type NewVillageFormInput,
  SKILL_SAY_MESSAGE_TYPES,
  RP_SAY_MESSAGE_TYPES,
} from "./schema";

/**
 * GET レスポンスのフォーム値をフォームの初期値へ変換する。
 * キャラチップ系フィールドはこの画面では変更不可のため、ダミー値 (schema の必須を通す) を設定する
 * (villageSettingsSchema は characterSetId を検証しない)。
 * isOriginalCharachip はオリジナル村のパスワード必須検証に使うため実値を渡す。
 */
export function toSettingsFormValues(
  setting: VillageSettingForm,
  skills: SimpleSkillView[],
  isOriginalCharachip: boolean,
): NewVillageFormInput {
  const defaults = createDefaultCampAllocations(skills);
  const campAllocationList = defaults.map((camp) => {
    const src = (setting.campAllocationList ?? []).find((c) => c.campCode === camp.campCode);
    return {
      ...camp,
      minNum: src?.minNum ?? 0,
      maxNum: src?.maxNum ?? null,
      allocation: src?.allocation ?? 0,
      reincarnationAllocation: src?.reincarnationAllocation ?? 50,
      skillAllocation: camp.skillAllocation.map((skill) => {
        const srcSkill = (src?.skillAllocation ?? []).find((s) => s.skillCode === skill.skillCode);
        return {
          ...skill,
          minNum: srcSkill?.minNum ?? 0,
          maxNum: srcSkill?.maxNum ?? null,
          allocation: srcSkill?.allocation ?? 0,
          reincarnationAllocation: srcSkill?.reincarnationAllocation ?? 50,
        };
      }),
    };
  });

  const sayRestrictList = createDefaultSayRestricts(skills).map((row) => {
    const src = (setting.sayRestrictList ?? []).find((r) => r.skillCode === row.skillCode);
    return {
      ...row,
      restrict: src?.restrict ?? false,
      length: src?.length ?? 400,
      count: src?.count ?? 20,
    };
  });

  const skillSayRestrictList = createDefaultMessageTypeSayRestricts(SKILL_SAY_MESSAGE_TYPES).map(
    (row) => {
      const src = (setting.skillSayRestrictList ?? []).find(
        (r) => r.messageTypeCode === row.messageTypeCode,
      );
      return {
        ...row,
        restrict: src?.restrict ?? false,
        length: src?.length ?? 400,
        count: src?.count ?? 20,
      };
    },
  );

  const rpSayRestrictList = createDefaultMessageTypeSayRestricts(RP_SAY_MESSAGE_TYPES).map(
    (row) => {
      const src = (setting.rpSayRestrictList ?? []).find(
        (r) => r.messageTypeCode === row.messageTypeCode,
      );
      return {
        ...row,
        restrict: src?.restrict ?? false,
        length: src?.length ?? 400,
        count: src?.count ?? 20,
      };
    },
  );

  return {
    villageName: setting.villageName ?? "",
    welcomeRange: (setting.welcomeRange ?? "") as NewVillageFormInput["welcomeRange"],
    startPersonMinNum: setting.startPersonMinNum ?? 8,
    personMaxNum: setting.personMaxNum ?? 20,
    dayChangeIntervalHours: setting.dayChangeIntervalHours ?? 24,
    dayChangeIntervalMinutes: setting.dayChangeIntervalMinutes ?? 0,
    dayChangeIntervalSeconds: setting.dayChangeIntervalSeconds ?? 0,
    startYear: setting.startYear ?? new Date().getFullYear(),
    startMonth: setting.startMonth ?? 1,
    startDay: setting.startDay ?? 1,
    startHour: setting.startHour ?? 0,
    startMinute: setting.startMinute ?? 0,
    // キャラチップ系は変更不可だが、オリジナル村のパスワード必須検証のため実値を保持する
    shouldOriginalImage: isOriginalCharachip,
    characterSetId: [],
    dummyCharaId: null,
    dummyCharaName: "dummy",
    dummyCharaShortName: "d",
    dummyJoinMessage: "dummy",
    dummyDay1Message: setting.dummyDay1Message ?? "",
    openVote: setting.openVote ?? true,
    possibleSkillRequest: true,
    availableSameWolfAttack: setting.availableSameWolfAttack ?? true,
    availableGuardSameTarget: setting.availableGuardSameTarget ?? true,
    reincarnationSkillAll: setting.reincarnationSkillAll ?? false,
    availableSuddonlyDeath: setting.availableSuddonlyDeath ?? false,
    availableCommit: setting.availableCommit ?? false,
    availableSpectate: setting.availableSpectate ?? false,
    creatorIsProducer: false,
    openSkillInGrave: setting.openSkillInGrave ?? false,
    visibleGraveSpectateMessage: setting.visibleGraveSpectateMessage ?? false,
    availableAction: setting.availableAction ?? false,
    randomOrganization: setting.randomOrganization ?? false,
    organization: addPersonCountPrefix(setting.organization ?? ""),
    campAllocationList,
    wolfAllocation: {
      minNum: setting.wolfAllocation?.minNum ?? 1,
      maxNum: setting.wolfAllocation?.maxNum ?? null,
    },
    sayRestrictList,
    skillSayRestrictList,
    rpSayRestrictList,
    allowedSecretSayCode: (setting.allowedSecretSayCode ??
      "NOTHING") as NewVillageFormInput["allowedSecretSayCode"],
    joinPassword: setting.joinPassword ?? "",
    ageLimit: (setting.ageLimit ?? "") as NewVillageFormInput["ageLimit"],
  };
}

/**
 * フォーム値を PUT body へ変換する。
 * キャラチップ系フィールドは settings API に存在しないため除外する。
 */
export function toUpdateRequest(values: NewVillageFormInput): VillageSettingForm {
  return {
    villageName: values.villageName,
    welcomeRange: values.welcomeRange === "" ? null : values.welcomeRange,
    startPersonMinNum: values.startPersonMinNum,
    personMaxNum: values.personMaxNum,
    dayChangeIntervalHours: values.dayChangeIntervalHours,
    dayChangeIntervalMinutes: values.dayChangeIntervalMinutes,
    dayChangeIntervalSeconds: values.dayChangeIntervalSeconds,
    startYear: values.startYear,
    startMonth: values.startMonth,
    startDay: values.startDay,
    startHour: values.startHour,
    startMinute: values.startMinute,
    dummyDay1Message: values.dummyDay1Message === "" ? null : values.dummyDay1Message,
    openVote: values.openVote,
    availableSameWolfAttack: values.availableSameWolfAttack,
    availableGuardSameTarget: values.availableGuardSameTarget,
    reincarnationSkillAll: values.reincarnationSkillAll,
    availableSuddonlyDeath: values.availableSuddonlyDeath,
    availableCommit: values.availableCommit,
    availableSpectate: values.availableSpectate,
    openSkillInGrave: values.openSkillInGrave,
    visibleGraveSpectateMessage: values.visibleGraveSpectateMessage,
    availableAction: values.availableAction,
    randomOrganization: values.randomOrganization,
    organization: stripPersonCountPrefix(values.organization),
    campAllocationList: values.campAllocationList.map((camp) => ({
      campCode: camp.campCode,
      campName: camp.campName,
      minNum: camp.minNum,
      maxNum: camp.maxNum,
      allocation: camp.allocation,
      reincarnationAllocation: camp.reincarnationAllocation,
      skillAllocation: camp.skillAllocation.map((skill) => ({
        skillCode: skill.skillCode,
        skillName: skill.skillName,
        minNum: skill.minNum,
        maxNum: skill.maxNum,
        allocation: skill.allocation,
        reincarnationAllocation: skill.reincarnationAllocation,
      })),
    })),
    wolfAllocation: { minNum: values.wolfAllocation.minNum, maxNum: values.wolfAllocation.maxNum },
    allowedSecretSayCode: values.allowedSecretSayCode,
    joinPassword: values.joinPassword === "" ? null : values.joinPassword,
    ageLimit: values.ageLimit === "" ? null : values.ageLimit,
    sayRestrictList: values.sayRestrictList.map((r) => ({
      skillCode: r.skillCode,
      skillName: r.skillName,
      restrict: r.restrict,
      length: r.length,
      count: r.count,
    })),
    skillSayRestrictList: values.skillSayRestrictList.map((r) => ({
      messageTypeCode: r.messageTypeCode,
      messageTypeName: r.messageTypeName,
      restrict: r.restrict,
      length: r.length,
      count: r.count,
    })),
    rpSayRestrictList: values.rpSayRestrictList.map((r) => ({
      messageTypeCode: r.messageTypeCode,
      messageTypeName: r.messageTypeName,
      restrict: r.restrict,
      length: r.length,
      count: r.count,
    })),
  };
}
