package com.ort.app.web.util;

import com.ort.dbflute.allcommon.CDef;

public class SkillUtil {

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
}
