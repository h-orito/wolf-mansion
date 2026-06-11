import type { VillageCreateRequest } from "~/features/villages/api";
import { stripPersonCountPrefix } from "./organization";
import type { NewVillageFormInput } from "./schema";

/**
 * フォーム値を村作成リクエスト (JSON part) へ変換する。
 * フォーム独自の表現 (空文字 = 未指定、編成の「N人：」プレフィックス) を API の形へ戻す。
 */
export function toCreateRequest(values: NewVillageFormInput): VillageCreateRequest {
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
    shouldOriginalImage: values.shouldOriginalImage,
    characterSetId: values.characterSetId,
    dummyCharaId: values.dummyCharaId,
    dummyCharaName: values.dummyCharaName,
    dummyCharaShortName: values.dummyCharaShortName,
    dummyJoinMessage: values.dummyJoinMessage,
    dummyDay1Message: values.dummyDay1Message === "" ? null : values.dummyDay1Message,
    joinPassword: values.joinPassword === "" ? null : values.joinPassword,
    openVote: values.openVote,
    possibleSkillRequest: values.possibleSkillRequest,
    availableSameWolfAttack: values.availableSameWolfAttack,
    availableGuardSameTarget: values.availableGuardSameTarget,
    reincarnationSkillAll: values.reincarnationSkillAll,
    availableSuddonlyDeath: values.availableSuddonlyDeath,
    availableCommit: values.availableCommit,
    availableSpectate: values.availableSpectate,
    creatorIsProducer: values.creatorIsProducer,
    openSkillInGrave: values.openSkillInGrave,
    visibleGraveSpectateMessage: values.visibleGraveSpectateMessage,
    availableAction: values.availableAction,
    randomOrganization: values.randomOrganization,
    // 編成は固定/闇鍋どちらの値も送る (選ばれなかった側も既定値で保存される)
    organization: stripPersonCountPrefix(values.organization),
    campAllocationList: values.campAllocationList.map((camp) => ({
      campCode: camp.campCode,
      minNum: camp.minNum,
      maxNum: camp.maxNum,
      allocation: camp.allocation,
      reincarnationAllocation: camp.reincarnationAllocation,
      skillAllocation: camp.skillAllocation.map((skill) => ({
        skillCode: skill.skillCode,
        minNum: skill.minNum,
        maxNum: skill.maxNum,
        allocation: skill.allocation,
        reincarnationAllocation: skill.reincarnationAllocation,
      })),
    })),
    wolfAllocation: { minNum: values.wolfAllocation.minNum, maxNum: values.wolfAllocation.maxNum },
    allowedSecretSayCode: values.allowedSecretSayCode,
    sayRestrictList: values.sayRestrictList.map((r) => ({
      skillCode: r.skillCode,
      restrict: r.restrict,
      length: r.length,
      count: r.count,
    })),
    skillSayRestrictList: values.skillSayRestrictList.map((r) => ({
      messageTypeCode: r.messageTypeCode,
      restrict: r.restrict,
      length: r.length,
      count: r.count,
    })),
    rpSayRestrictList: values.rpSayRestrictList.map((r) => ({
      messageTypeCode: r.messageTypeCode,
      restrict: r.restrict,
      length: r.length,
      count: r.count,
    })),
    ageLimit: values.ageLimit === "" ? null : values.ageLimit,
  };
}
