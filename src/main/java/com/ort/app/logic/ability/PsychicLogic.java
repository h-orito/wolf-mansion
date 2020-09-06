package com.ort.app.logic.ability;

import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class PsychicLogic {

    @Autowired
    private DayChangeLogicHelper helper;

    // 霊視
    public void psychic(DayChangeVillage dayChangeVillage) {
        // 霊視可能な死者
        List<VillagePlayer> psychicableDeadPlayerList = dayChangeVillage.deadPlayers.filterPsychicable().getList();
        if (CollectionUtils.isEmpty(psychicableDeadPlayerList)) {
            return;
        }
        // 霊能者
        if (!dayChangeVillage.alivePlayers().filterBySkill(CDef.Skill.霊能者).list.isEmpty()) {
            StringJoiner joiner = new StringJoiner("\n");
            psychicableDeadPlayerList.forEach(deadPlayer -> {
                boolean isTargetWerewolf = deadPlayer.getSkillCodeAsSkill().isHasAttackAbility();
                joiner.add(String.format("%sは%sのようだ。", deadPlayer.name(), isTargetWerewolf ? "人狼" : "人間"));
            });
            helper.insertMessage(dayChangeVillage, CDef.MessageType.白黒霊視結果, joiner.toString());
        }

        // 導師と魔神官
        if (!dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasSkillPsychicAbility()).list.isEmpty()) {
            StringJoiner joiner = new StringJoiner("\n");
            psychicableDeadPlayerList.forEach(deadPlayer -> {
                joiner.add(String.format("%sは%sのようだ。", deadPlayer.name(), deadPlayer.getSkillCodeAsSkill().alias()));
            });
            helper.insertMessage(dayChangeVillage, CDef.MessageType.役職霊視結果, joiner.toString());
        }
    }
}
