package com.ort.app.web.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;

public class SkillUtil {

    public static Map<String, CDef.Skill> SKILL_SHORTNAME_MAP;
    static {
        Map<String, CDef.Skill> map = new HashMap<>();
        map.put("村", CDef.Skill.村人);
        map.put("狼", CDef.Skill.人狼);
        map.put("狂", CDef.Skill.狂人);
        map.put("C", CDef.Skill.C国狂人);
        map.put("魔", CDef.Skill.魔神官);
        map.put("占", CDef.Skill.占い師);
        map.put("賢", CDef.Skill.賢者);
        map.put("霊", CDef.Skill.霊能者);
        map.put("導", CDef.Skill.導師);
        map.put("狐", CDef.Skill.妖狐);
        map.put("共", CDef.Skill.共鳴者);
        map.put("狩", CDef.Skill.狩人);
        SKILL_SHORTNAME_MAP = MapUtils.unmodifiableMap(map);
    }

    private SkillUtil() {
    }

    public static boolean hasDivineAbility(CDef.Skill skill) {
        return skill == CDef.Skill.占い師 || skill == CDef.Skill.賢者;
    }

    public static boolean hasSkillPsychicAbility(CDef.Skill skill) {
        return skill == CDef.Skill.導師 || skill == CDef.Skill.魔神官;
    }

    public static boolean hasMadmanAbility(CDef.Skill skill) {
        return skill == CDef.Skill.C国狂人 || skill == CDef.Skill.狂人 || skill == CDef.Skill.魔神官;
    }

    public static Map<Skill, Integer> createSkillPersonNum(String org) {
        Map<CDef.Skill, Integer> skillPersonNumMap = new HashMap<>();
        for (CDef.Skill skill : CDef.Skill.values()) {
            skillPersonNumMap.put(skill, 0);
        }
        for (String character : org.split("")) {
            CDef.Skill skill = SKILL_SHORTNAME_MAP.get(character);
            Integer skillPersonNum = skillPersonNumMap.get(skill);
            skillPersonNum++;
            skillPersonNumMap.put(skill, skillPersonNum);
        }
        return skillPersonNumMap;
    }
}
