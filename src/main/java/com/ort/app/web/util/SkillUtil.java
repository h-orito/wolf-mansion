package com.ort.app.web.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;

public class SkillUtil {

    // 能力セットできる役職
    public static List<CDef.Skill> SET_AVAILABLE_SKILL_LIST = Arrays.asList(CDef.Skill.人狼, CDef.Skill.呪狼, CDef.Skill.智狼, CDef.Skill.占い師,
            CDef.Skill.狩人, CDef.Skill.狂人, CDef.Skill.妖狐, CDef.Skill.C国狂人, CDef.Skill.狂信者, CDef.Skill.賢者, CDef.Skill.占星術師, CDef.Skill.魔神官,
            CDef.Skill.探偵, CDef.Skill.罠師, CDef.Skill.爆弾魔);
    // 発言制限対象役職
    public static final List<CDef.Skill> RESTRICT_SKILLS = CDef.Skill.listAll().stream().filter(skill -> {
        return !skill.alias().contains("おまかせ");
    }).sorted((s1, s2) -> Integer.parseInt(s1.order()) - Integer.parseInt(s2.order())).collect(Collectors.toList());
    // 役職略称とのマッピング
    public static Map<String, CDef.Skill> SKILL_SHORTNAME_MAP;
    // 役職と能力行使種別のマッピング
    public static Map<CDef.Skill, CDef.AbilityType> SKILL_ABILITY_TYPE_MAP = null;
    static {
        Map<CDef.Skill, CDef.AbilityType> skillAbilityTypeMap = new HashMap<>();
        CDef.Skill.listOfHasAttackAbility().forEach(skill -> {
            skillAbilityTypeMap.put(skill, CDef.AbilityType.襲撃);
        });
        CDef.Skill.listOfHasDivineAbility().forEach(skill -> {
            skillAbilityTypeMap.put(skill, CDef.AbilityType.占い);
        });
        skillAbilityTypeMap.put(CDef.Skill.狩人, CDef.AbilityType.護衛);
        skillAbilityTypeMap.put(CDef.Skill.探偵, CDef.AbilityType.捜査);
        skillAbilityTypeMap.put(CDef.Skill.罠師, CDef.AbilityType.罠設置);
        skillAbilityTypeMap.put(CDef.Skill.爆弾魔, CDef.AbilityType.爆弾設置);
        SKILL_ABILITY_TYPE_MAP = Collections.unmodifiableMap(skillAbilityTypeMap);

        Map<String, CDef.Skill> skillShortNameMap = new HashMap<>();
        skillShortNameMap.put("村", CDef.Skill.村人);
        skillShortNameMap.put("狼", CDef.Skill.人狼);
        skillShortNameMap.put("呪", CDef.Skill.呪狼);
        skillShortNameMap.put("智", CDef.Skill.智狼);
        skillShortNameMap.put("狂", CDef.Skill.狂人);
        skillShortNameMap.put("C", CDef.Skill.C国狂人);
        skillShortNameMap.put("魔", CDef.Skill.魔神官);
        skillShortNameMap.put("信", CDef.Skill.狂信者);
        skillShortNameMap.put("占", CDef.Skill.占い師);
        skillShortNameMap.put("賢", CDef.Skill.賢者);
        skillShortNameMap.put("星", CDef.Skill.占星術師);
        skillShortNameMap.put("霊", CDef.Skill.霊能者);
        skillShortNameMap.put("導", CDef.Skill.導師);
        skillShortNameMap.put("狐", CDef.Skill.妖狐);
        skillShortNameMap.put("共", CDef.Skill.共鳴者);
        skillShortNameMap.put("狩", CDef.Skill.狩人);
        skillShortNameMap.put("パ", CDef.Skill.パン屋);
        skillShortNameMap.put("探", CDef.Skill.探偵);
        skillShortNameMap.put("罠", CDef.Skill.罠師);
        skillShortNameMap.put("爆", CDef.Skill.爆弾魔);

        SKILL_SHORTNAME_MAP = MapUtils.unmodifiableMap(skillShortNameMap);
    }

    private SkillUtil() {
    }

    public static Map<Skill, Integer> createSkillPersonNum(String org) {
        Map<CDef.Skill, Integer> skillPersonNumMap = new HashMap<>();
        for (CDef.Skill skill : CDef.Skill.values()) {
            skillPersonNumMap.put(skill, 0);
        }
        for (String character : org.split("")) {
            if (SKILL_SHORTNAME_MAP.containsKey(character)) {
                CDef.Skill skill = SKILL_SHORTNAME_MAP.get(character);
                Integer skillPersonNum = skillPersonNumMap.get(skill);
                skillPersonNum++;
                skillPersonNumMap.put(skill, skillPersonNum);
            }
        }
        return skillPersonNumMap;
    }
}
