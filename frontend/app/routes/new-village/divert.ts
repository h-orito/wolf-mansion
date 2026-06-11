import type { VillageSettingView } from "~/features/villages/api";
import type { SimpleSkillView } from "~/features/skills/api";
import { addPersonCountPrefix } from "./organization";
import { createDefaultValues, type NewVillageFormInput } from "./schema";

/**
 * 流用元の村設定をフォーム値へ変換する (正本は backend `NewVillageForm.override`)。
 *
 * 既定値の上に流用元の設定を上書きする形のため、`override` が流用しない項目
 * (村名・開始日時・入村パスワード・役職希望・ダミーキャラ名/略称/発言) は既定値に戻る。
 * 流用元の村に存在しない役職・陣営・発言種別の行 (流用元の作成後に実装されたもの) は
 * 既定値のまま = 発言制限は無制限扱いになる。
 */
export function toDivertValues(
  setting: VillageSettingView,
  skills: SimpleSkillView[],
  now: Date,
): NewVillageFormInput {
  const defaults = createDefaultValues(skills, now);
  const random = setting.organize.randomOrganization;
  const charaValues = setting.chara.isOriginalCharachip
    ? {}
    : {
        characterSetId: setting.chara.charachipIds,
        dummyCharaId: setting.chara.dummyCharaId,
      };
  const welcomeTag = setting.tags.list.find(
    (t) => t.code === "ANYONE_WELCOME" || t.code === "RELATIVES_ONLY",
  );
  const ageTag = setting.tags.list.find((t) => t.code === "R15" || t.code === "R18");
  return {
    ...defaults,
    startPersonMinNum: setting.personMin,
    personMaxNum: setting.personMax,
    dayChangeIntervalHours: Math.floor(setting.dayChangeIntervalSeconds / 3600),
    dayChangeIntervalMinutes: Math.floor((setting.dayChangeIntervalSeconds % 3600) / 60),
    dayChangeIntervalSeconds: setting.dayChangeIntervalSeconds % 60,
    shouldOriginalImage: setting.chara.isOriginalCharachip,
    ...charaValues,
    openVote: setting.rule.isOpenVote,
    availableSameWolfAttack: setting.rule.isAvailableSameWolfAttack,
    openSkillInGrave: setting.rule.isOpenSkillInGrave,
    visibleGraveSpectateMessage: setting.rule.isVisibleGraveSpectateMessage,
    allowedSecretSayCode: setting.rule.secretSayRange
      .code as NewVillageFormInput["allowedSecretSayCode"],
    availableSpectate: setting.rule.isAvailableSpectate,
    creatorIsProducer: setting.rule.isCreatorIsProducer,
    availableSuddonlyDeath: setting.rule.isAvailableSuddenlyDeath,
    availableCommit: setting.rule.isAvailableCommit,
    availableGuardSameTarget: setting.rule.isAvailableGuardSameTarget,
    availableAction: setting.rule.isAvailableAction,
    organization: addPersonCountPrefix(setting.organize.fixedOrganization),
    randomOrganization: setting.rule.isRandomOrganization,
    reincarnationSkillAll: setting.rule.isReincarnationSkillAll,
    campAllocationList: defaults.campAllocationList.map((camp) => {
      const dbCamp = random.campAllocation.find((c) => c.camp.code === camp.campCode);
      return {
        ...camp,
        minNum: dbCamp?.min ?? 0,
        maxNum: dbCamp?.max ?? null,
        allocation: dbCamp?.initAllocation ?? 0,
        reincarnationAllocation: dbCamp?.reincarnationAllocation ?? 50,
        skillAllocation: camp.skillAllocation.map((skill) => {
          const dbSkill = random.skillAllocation.find((s) => s.skill.code === skill.skillCode);
          return {
            ...skill,
            minNum: dbSkill?.min ?? 0,
            maxNum: dbSkill?.max ?? null,
            allocation: dbSkill?.initAllocation ?? 0,
            reincarnationAllocation: dbSkill?.reincarnationAllocation ?? 50,
          };
        }),
      };
    }),
    // 流用元に人狼数配分が無い場合は既定値のまま (固定編成の村など)
    wolfAllocation: random.wolfAllocation
      ? { minNum: random.wolfAllocation.min, maxNum: random.wolfAllocation.max ?? null }
      : defaults.wolfAllocation,
    sayRestrictList: defaults.sayRestrictList.map((row) => {
      const restrict = setting.sayRestriction.normalSayRestriction.find(
        (r) => r.skill.code === row.skillCode,
      );
      return {
        ...row,
        restrict: restrict != null,
        count: restrict?.count ?? 20,
        length: restrict?.length ?? 400,
      };
    }),
    skillSayRestrictList: defaults.skillSayRestrictList.map((row) =>
      overrideMessageTypeRestrict(row, setting),
    ),
    rpSayRestrictList: defaults.rpSayRestrictList.map((row) =>
      overrideMessageTypeRestrict(row, setting),
    ),
    welcomeRange: (welcomeTag?.code ?? "") as NewVillageFormInput["welcomeRange"],
    ageLimit: (ageTag?.code ?? "") as NewVillageFormInput["ageLimit"],
  };
}

function overrideMessageTypeRestrict(
  row: NewVillageFormInput["skillSayRestrictList"][number],
  setting: VillageSettingView,
): NewVillageFormInput["skillSayRestrictList"][number] {
  const restrict = setting.sayRestriction.skillSayRestriction.find(
    (r) => r.messageType.code === row.messageTypeCode,
  );
  return {
    ...row,
    restrict: restrict != null,
    count: restrict?.count ?? 20,
    length: restrict?.length ?? 400,
  };
}
