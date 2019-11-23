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
        map.put("呪", CDef.Skill.呪狼);
        map.put("智", CDef.Skill.智狼);
        map.put("狂", CDef.Skill.狂人);
        map.put("C", CDef.Skill.C国狂人);
        map.put("魔", CDef.Skill.魔神官);
        map.put("信", CDef.Skill.狂信者);
        map.put("占", CDef.Skill.占い師);
        map.put("賢", CDef.Skill.賢者);
        map.put("星", CDef.Skill.占星術師);
        map.put("霊", CDef.Skill.霊能者);
        map.put("導", CDef.Skill.導師);
        map.put("狐", CDef.Skill.妖狐);
        map.put("共", CDef.Skill.共鳴者);
        map.put("狩", CDef.Skill.狩人);
        map.put("パ", CDef.Skill.パン屋);
        map.put("探", CDef.Skill.探偵);
        map.put("罠", CDef.Skill.罠師);
        map.put("爆", CDef.Skill.爆弾魔);

        SKILL_SHORTNAME_MAP = MapUtils.unmodifiableMap(map);
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
