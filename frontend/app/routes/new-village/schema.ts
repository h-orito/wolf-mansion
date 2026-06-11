import { z } from "zod";

import type { SimpleSkillView } from "~/features/skills/api";
import { addPersonCountPrefix, DEFAULT_FIXED_ORGANIZATION } from "./organization";

/**
 * 村作成フォームの入力スキーマ。
 *
 * 検証の正本は backend (`NewVillageForm` の Bean Validation + `NewVillageFormValidator`)。
 * ここはその項目単位 + 軽量な相関チェックを UX 向上のため再現する。編成内容 (固定編成の
 * 行構成・闇鍋配分の整合) のような重い相関は backend の検証に委ねる。
 * 文字数等の定数は村作成 API 接続時に OpenAPI 生成定数 (`~/api/constants`) へ置き換える。
 */

export const VILLAGE_NAME_MIN_LENGTH = 5;
export const VILLAGE_NAME_MAX_LENGTH = 40;
export const START_PERSON_MIN_NUM = 8;
export const PERSON_MAX_NUM_LIMIT = 999;
export const JOIN_PASSWORD_MIN_LENGTH = 3;
export const JOIN_PASSWORD_MAX_LENGTH = 12;
/** 更新間隔の下限/上限 (秒)。 */
export const INTERVAL_MIN_SECONDS = 60;
export const INTERVAL_MAX_SECONDS = 72 * 60 * 60;
/** 開始日時は現在から 14 日以内。 */
export const START_DATETIME_MAX_DAYS = 14;

const VILLAGE_NAME_MESSAGE = `村名は${VILLAGE_NAME_MIN_LENGTH}文字以上${VILLAGE_NAME_MAX_LENGTH}文字以下で入力してください`;
const START_PERSON_MIN_MESSAGE = `最低開始人数は${START_PERSON_MIN_NUM}以上で入力してください`;
const PERSON_MAX_MESSAGE = `定員は${PERSON_MAX_NUM_LIMIT}以下で入力してください`;
const PERSON_MAX_CORRELATION_MESSAGE = "定員は最少開始人数以上で設定してください";
const INTERVAL_MESSAGE = "更新間隔は1分以上72時間以内で設定してください";
const START_DATETIME_MESSAGE = "開始日時は現在から14日以内の存在する日付にしてください";
const JOIN_PASSWORD_MESSAGE = `入村パスワードは${JOIN_PASSWORD_MIN_LENGTH}文字以上${JOIN_PASSWORD_MAX_LENGTH}文字以内にしてください`;
const ALLOCATION_MESSAGE = "0~100で入力してください";
const WOLF_ALLOCATION_MESSAGE = "1~100で入力してください";
const SAY_RESTRICT_MESSAGE = "発言制限は0~400 * 0~100 で設定してください";

/** 発言制限の上限 (1回あたりの文字数 / 1日あたりの回数)。 */
export const SAY_RESTRICT_LENGTH_MAX = 400;
export const SAY_RESTRICT_COUNT_MAX = 100;

const allocationNum = z
  .number(ALLOCATION_MESSAGE)
  .int(ALLOCATION_MESSAGE)
  .min(0, ALLOCATION_MESSAGE)
  .max(100, ALLOCATION_MESSAGE);

const wolfAllocationNum = z
  .number(WOLF_ALLOCATION_MESSAGE)
  .int(WOLF_ALLOCATION_MESSAGE)
  .min(1, WOLF_ALLOCATION_MESSAGE)
  .max(100, WOLF_ALLOCATION_MESSAGE);

const skillAllocationSchema = z.object({
  skillCode: z.string(),
  skillName: z.string(),
  minNum: allocationNum,
  maxNum: allocationNum.nullable(),
  allocation: allocationNum,
  reincarnationAllocation: allocationNum,
});

/** 制限ありの行のみ length/count を検証する (制限なしの行は無制限扱いで値を使わない)。 */
function refineSayRestrict(
  values: { restrict: boolean; length: number | null; count: number | null },
  ctx: z.RefinementCtx,
) {
  if (!values.restrict) return;
  if (
    values.length == null ||
    !Number.isInteger(values.length) ||
    values.length < 0 ||
    values.length > SAY_RESTRICT_LENGTH_MAX
  ) {
    ctx.addIssue({ code: "custom", path: ["length"], message: SAY_RESTRICT_MESSAGE });
  }
  if (
    values.count == null ||
    !Number.isInteger(values.count) ||
    values.count < 0 ||
    values.count > SAY_RESTRICT_COUNT_MAX
  ) {
    ctx.addIssue({ code: "custom", path: ["count"], message: SAY_RESTRICT_MESSAGE });
  }
}

const skillSayRestrictSchema = z
  .object({
    skillCode: z.string(),
    skillName: z.string(),
    restrict: z.boolean(),
    length: z.number().nullable(),
    count: z.number().nullable(),
  })
  .superRefine(refineSayRestrict);

const messageTypeSayRestrictSchema = z
  .object({
    messageTypeCode: z.string(),
    messageTypeName: z.string(),
    restrict: z.boolean(),
    length: z.number().nullable(),
    count: z.number().nullable(),
  })
  .superRefine(refineSayRestrict);

const campAllocationSchema = z.object({
  campCode: z.string(),
  campName: z.string(),
  minNum: allocationNum,
  maxNum: allocationNum.nullable(),
  allocation: allocationNum,
  reincarnationAllocation: allocationNum,
  skillAllocation: z.array(skillAllocationSchema),
});

export const newVillageSchema = z
  .object({
    villageName: z
      .string()
      .min(VILLAGE_NAME_MIN_LENGTH, VILLAGE_NAME_MESSAGE)
      .max(VILLAGE_NAME_MAX_LENGTH, VILLAGE_NAME_MESSAGE),
    welcomeRange: z.enum(["", "ANYONE_WELCOME", "RELATIVES_ONLY"]),
    startPersonMinNum: z
      .number(START_PERSON_MIN_MESSAGE)
      .int(START_PERSON_MIN_MESSAGE)
      .min(START_PERSON_MIN_NUM, START_PERSON_MIN_MESSAGE),
    personMaxNum: z
      .number(PERSON_MAX_MESSAGE)
      .int(PERSON_MAX_MESSAGE)
      .max(PERSON_MAX_NUM_LIMIT, PERSON_MAX_MESSAGE),
    dayChangeIntervalHours: z.number().int().min(0).max(72),
    dayChangeIntervalMinutes: z.number().int().min(0).max(59),
    dayChangeIntervalSeconds: z.number().int().min(0).max(59),
    startYear: z.number().int(),
    startMonth: z.number().int().min(1).max(12),
    startDay: z.number().int().min(1).max(31),
    startHour: z.number().int().min(0).max(23),
    startMinute: z.number().int().min(0).max(59),
    openVote: z.boolean(),
    possibleSkillRequest: z.boolean(),
    availableSameWolfAttack: z.boolean(),
    availableGuardSameTarget: z.boolean(),
    reincarnationSkillAll: z.boolean(),
    availableSuddonlyDeath: z.boolean(),
    availableCommit: z.boolean(),
    availableSpectate: z.boolean(),
    creatorIsProducer: z.boolean(),
    openSkillInGrave: z.boolean(),
    visibleGraveSpectateMessage: z.boolean(),
    availableAction: z.boolean(),
    randomOrganization: z.boolean(),
    /** 表示用「N人：」プレフィックス込みのテキスト (送信時に取り除く)。 */
    organization: z.string(),
    campAllocationList: z.array(campAllocationSchema),
    wolfAllocation: z.object({
      minNum: wolfAllocationNum,
      maxNum: wolfAllocationNum.nullable(),
    }),
    sayRestrictList: z.array(skillSayRestrictSchema),
    skillSayRestrictList: z.array(messageTypeSayRestrictSchema),
    rpSayRestrictList: z.array(messageTypeSayRestrictSchema),
    allowedSecretSayCode: z.enum(["NOTHING", "ONLY_CREATOR", "EVERYTHING"]),
    joinPassword: z
      .string()
      .refine(
        (v) =>
          v === "" ||
          (v.length >= JOIN_PASSWORD_MIN_LENGTH && v.length <= JOIN_PASSWORD_MAX_LENGTH),
        JOIN_PASSWORD_MESSAGE,
      ),
    ageLimit: z.enum(["", "R15", "R18"]),
  })
  .superRefine((values, ctx) => {
    if (values.personMaxNum < values.startPersonMinNum) {
      ctx.addIssue({
        code: "custom",
        path: ["personMaxNum"],
        message: PERSON_MAX_CORRELATION_MESSAGE,
      });
    }

    const intervalSeconds =
      values.dayChangeIntervalHours * 60 * 60 +
      values.dayChangeIntervalMinutes * 60 +
      values.dayChangeIntervalSeconds;
    if (intervalSeconds < INTERVAL_MIN_SECONDS || intervalSeconds > INTERVAL_MAX_SECONDS) {
      ctx.addIssue({
        code: "custom",
        path: ["dayChangeIntervalHours"],
        message: INTERVAL_MESSAGE,
      });
    }

    const start = new Date(
      values.startYear,
      values.startMonth - 1,
      values.startDay,
      values.startHour,
      values.startMinute,
    );
    const exists =
      start.getFullYear() === values.startYear &&
      start.getMonth() === values.startMonth - 1 &&
      start.getDate() === values.startDay;
    const now = new Date();
    const limit = new Date(now.getTime() + START_DATETIME_MAX_DAYS * 24 * 60 * 60 * 1000);
    if (!exists || start < now || start > limit) {
      ctx.addIssue({ code: "custom", path: ["startYear"], message: START_DATETIME_MESSAGE });
    }
  });

export type NewVillageFormInput = z.infer<typeof newVillageSchema>;
export type CampAllocationInput = NewVillageFormInput["campAllocationList"][number];

/** 発言制限の対象 (発言種別)。正本は backend `NewVillageForm.initialize` の対象種別。 */
export const SKILL_SAY_MESSAGE_TYPES = [
  { messageTypeCode: "WEREWOLF_SAY", messageTypeName: "人狼の囁き" },
  { messageTypeCode: "MASON_SAY", messageTypeName: "共鳴発言" },
  { messageTypeCode: "LOVERS_SAY", messageTypeName: "恋人発言" },
  { messageTypeCode: "TELEPATHY", messageTypeName: "念話" },
] as const;

export const RP_SAY_MESSAGE_TYPES = [
  { messageTypeCode: "ACTION", messageTypeName: "アクション" },
] as const;

type MessageTypeRef = { messageTypeCode: string; messageTypeName: string };

/** 役職別 (通常発言) の発言制限の初期行。行順は役職一覧 API の並びに合わせる。 */
export function createDefaultSayRestricts(
  skills: SimpleSkillView[],
): NewVillageFormInput["sayRestrictList"] {
  return skills.map((s) => ({
    skillCode: s.code,
    skillName: s.name,
    restrict: false,
    length: 400,
    count: 20,
  }));
}

/** 発言種別 (役職発言 / RP発言) の発言制限の初期行。 */
export function createDefaultMessageTypeSayRestricts(
  messageTypes: readonly MessageTypeRef[],
): NewVillageFormInput["skillSayRestrictList"] {
  return messageTypes.map((t) => ({ ...t, restrict: false, length: 400, count: 20 }));
}

/** 闇鍋配分テーブルの陣営の並び順 (正本は backend `NewVillageForm` の陣営順)。 */
const CAMP_CODE_ORDER = ["VILLAGER", "WEREWOLF", "FOX", "LOVERS", "CRIMINAL"];

/** 村人役職のコード (闇鍋の最少人数既定値はダミー分の 1 を確保する)。 */
const VILLAGER_SKILL_CODE = "VILLAGER";

/** 役職一覧から闇鍋配分の初期行を組み立てる (既定値は backend `NewVillageForm.initialize` と同一)。 */
export function createDefaultCampAllocations(skills: SimpleSkillView[]): CampAllocationInput[] {
  return CAMP_CODE_ORDER.map((campCode) => {
    const campSkills = skills.filter((s) => s.campCode === campCode);
    return {
      campCode,
      campName: campSkills[0]?.campName ?? campCode,
      minNum: 0,
      maxNum: null,
      allocation: 50,
      reincarnationAllocation: 50,
      skillAllocation: campSkills.map((s) => ({
        skillCode: s.code,
        skillName: s.name,
        minNum: s.code === VILLAGER_SKILL_CODE ? 1 : 0,
        maxNum: s.requestable ? null : 0,
        allocation: 50,
        reincarnationAllocation: s.revivable ? 50 : 0,
      })),
    };
  });
}

/** フォーム初期値 (backend `NewVillageForm.initialize` と同じ既定値)。 */
export function createDefaultValues(skills: SimpleSkillView[], now: Date): NewVillageFormInput {
  const start = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 7);
  return {
    villageName: "",
    welcomeRange: "",
    startPersonMinNum: 8,
    personMaxNum: 20,
    dayChangeIntervalHours: 24,
    dayChangeIntervalMinutes: 0,
    dayChangeIntervalSeconds: 0,
    startYear: start.getFullYear(),
    startMonth: start.getMonth() + 1,
    startDay: start.getDate(),
    startHour: 0,
    startMinute: 0,
    openVote: true,
    possibleSkillRequest: true,
    availableSameWolfAttack: true,
    availableGuardSameTarget: true,
    reincarnationSkillAll: false,
    availableSuddonlyDeath: false,
    availableCommit: false,
    availableSpectate: false,
    creatorIsProducer: false,
    openSkillInGrave: false,
    visibleGraveSpectateMessage: false,
    availableAction: false,
    randomOrganization: false,
    organization: addPersonCountPrefix(DEFAULT_FIXED_ORGANIZATION),
    campAllocationList: createDefaultCampAllocations(skills),
    wolfAllocation: { minNum: 1, maxNum: null },
    sayRestrictList: createDefaultSayRestricts(skills),
    skillSayRestrictList: createDefaultMessageTypeSayRestricts(SKILL_SAY_MESSAGE_TYPES),
    rpSayRestrictList: createDefaultMessageTypeSayRestricts(RP_SAY_MESSAGE_TYPES),
    allowedSecretSayCode: "NOTHING",
    joinPassword: "",
    ageLimit: "",
  };
}
