package com.ort.app.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.MapUtils;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;

public class SkillUtil {

    // 表示順役職一覧（おまかせ除く）
    public static final List<CDef.Skill> SORTED_SKILL_LIST = CDef.Skill.listAll().stream().filter(skill -> {
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
        skillAbilityTypeMap.put(CDef.Skill.壁殴り代行, CDef.AbilityType.壁殴り);
        skillAbilityTypeMap.put(CDef.Skill.探偵, CDef.AbilityType.捜査);
        skillAbilityTypeMap.put(CDef.Skill.罠師, CDef.AbilityType.罠設置);
        skillAbilityTypeMap.put(CDef.Skill.爆弾魔, CDef.AbilityType.爆弾設置);
        skillAbilityTypeMap.put(CDef.Skill.同棲者, CDef.AbilityType.同棲);
        skillAbilityTypeMap.put(CDef.Skill.指揮官, CDef.AbilityType.指揮);
        skillAbilityTypeMap.put(CDef.Skill.果実籠, CDef.AbilityType.フルーツバスケット);
        skillAbilityTypeMap.put(CDef.Skill.求愛者, CDef.AbilityType.求愛);
        skillAbilityTypeMap.put(CDef.Skill.ストーカー, CDef.AbilityType.ストーキング);
        skillAbilityTypeMap.put(CDef.Skill.絡新婦, CDef.AbilityType.誘惑);
        skillAbilityTypeMap.put(CDef.Skill.美人局, CDef.AbilityType.美人局);
        skillAbilityTypeMap.put(CDef.Skill.誑狐, CDef.AbilityType.誑かす);
        skillAbilityTypeMap.put(CDef.Skill.一匹狼, CDef.AbilityType.単独襲撃);
        SKILL_ABILITY_TYPE_MAP = Collections.unmodifiableMap(skillAbilityTypeMap);

        Map<String, CDef.Skill> skillShortNameMap = new HashMap<>();
        SORTED_SKILL_LIST.forEach(skill -> skillShortNameMap.put(skill.skill_short_name(), skill));
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

    public static String getSkillListStr() {
        StringJoiner joiner = new StringJoiner("／");
        SORTED_SKILL_LIST.stream().forEach(skill -> {
            SKILL_SHORTNAME_MAP.forEach((shortName, s) -> {
                if (s == skill) {
                    joiner.add(String.format("%s:%s", shortName, skill.alias()));
                    return;
                }
            });
        });
        return joiner.toString();
    }

    public static List<CDef.Skill> getSelectableSkillList(String organize) {
        Set<CDef.Skill> skillSet = new HashSet<>();
        Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).forEach(org -> {
            Stream.of(org.split("")).forEach(skillStr -> {
                skillSet.add(SKILL_SHORTNAME_MAP.get(skillStr));
            });
        });
        skillSet.addAll(CDef.Skill.listOfSomeoneSkill());
        return CDef.Skill.listAll()
                .stream() //
                .sorted((s1, s2) -> Integer.parseInt(s1.order()) - Integer.parseInt(s2.order())) //
                .filter(cdef -> skillSet.contains(cdef))
                .collect(Collectors.toList());
    }
}
