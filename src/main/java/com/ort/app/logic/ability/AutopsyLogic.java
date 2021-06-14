package com.ort.app.logic.ability;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.daychange.DayChangeVillage.DeadPlayers;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;

@Component
public class AutopsyLogic {

    @Autowired
    private MessageLogic messageLogic;

    // 検死
    public void autopsy(DayChangeVillage dayChangeVillage) {
        DeadPlayers miserablePlayers = dayChangeVillage.deadPlayers.filterMiserable();
        if (miserablePlayers.getList().isEmpty()) {
            return;
        }
        // 検死官が生存している場合のみ
        if (dayChangeVillage.alivePlayers().filterBySkill(CDef.Skill.検死官).list.isEmpty()) {
            return;
        }

        String message = String.join("\n",
                miserablePlayers.list.stream()
                        .sorted((dp1, dp2) -> dp1.villagePlayer.getRoomNumber() - dp2.villagePlayer.getRoomNumber())
                        .map(dp -> String.format("%sの死因は、%sのようだ。", dp.villagePlayer.name(), dp.reason.alias()))
                        .collect(Collectors.toList()));
        messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .messageType(CDef.MessageType.検死結果)
                .content(message)
                .build());
    }
}
