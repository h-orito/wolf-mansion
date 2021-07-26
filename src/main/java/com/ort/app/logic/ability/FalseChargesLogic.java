/**
 * 
 */
package com.ort.app.logic.ability;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class FalseChargesLogic {

    @Autowired
    private FootstepLogic footstepLogic;

    public void falseCharges(DayChangeVillage dayChangeVillage) {
        VillagePlayers victims = new VillagePlayers(dayChangeVillage.deadPlayers.filterMiserable().getList());
        if (victims.list.isEmpty()) {
            return;
        }
        dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.冤罪者).list.forEach(vp -> {
            Integer charaId = vp.getCharaId();
            Integer targetCharaId = victims.getRandom().getCharaId();
            List<String> footstepList =
                    footstepLogic.getFootstepCandidateList(dayChangeVillage.villageId, vp, dayChangeVillage.day, charaId, targetCharaId);
            Collections.shuffle(footstepList);
            footstepLogic.insertFootStep(dayChangeVillage.villageId, dayChangeVillage.day - 1, charaId, footstepList.get(0));
        });
    }
}
