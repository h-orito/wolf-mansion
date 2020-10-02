package com.ort.app.logic.daychange.ability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;

@Component
public class RevivalLogic {

    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private VillageService villageService;

    public void revivalAbsoluteWolf(DayChangeVillage dayChangeVillage) {
        // 絶対人狼以外の人狼系役職が生存しているか
        boolean isAliveWolfWithoutAbsoluteWolf = dayChangeVillage.vPlayers.filterBy(vp -> {
            return vp.getSkillCodeAsSkill().isHasAttackAbility() && vp.getSkillCodeAsSkill() != CDef.Skill.絶対人狼;
        }).list.stream().anyMatch(vp -> dayChangeVillage.isAlive(vp));
        if (!isAliveWolfWithoutAbsoluteWolf) {
            return;
        }
        dayChangeVillage.deadPlayers.getList().stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.絶対人狼).forEach(vp -> {
            villageService.revive(vp, dayChangeVillage.day);
            dayChangeVillage.deadPlayers.remove(vp);
            String message = String.format("不思議なことに、%sが生き返った。", vp.name());
            helper.insertMessage(dayChangeVillage, CDef.MessageType.公開システムメッセージ, message);
        });
    }
}
