package com.ort.app.logic.ability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;

@Component
public class BakeryLogic {

    @Autowired
    private MessageLogic messageLogic;

    // パン屋メッセージ
    public void insertBakeryMessageIfNeeded(DayChangeVillage dayChangeVillage) {
        // パン屋がいない場合は何もしない
        if (dayChangeVillage.vPlayers.filterBySkill(CDef.Skill.パン屋).list.isEmpty()) {
            return;
        }
        // 生存している人がいる場合
        if (!dayChangeVillage.alivePlayers().filterBySkill(CDef.Skill.パン屋).list.isEmpty()) {
            messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .content("パン屋がおいしいパンを焼いてくれたそうです。")
                    .build());
            return;
        }
        // 今日で全員死亡した場合
        if (dayChangeVillage.deadPlayers.list.stream().anyMatch(dp -> {
            return dp.villagePlayer.getSkillCodeAsSkill() == CDef.Skill.パン屋;
        })) {
            messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .content("今日からはもうおいしいパンが食べられません。")
                    .build());
        }
    }
}
