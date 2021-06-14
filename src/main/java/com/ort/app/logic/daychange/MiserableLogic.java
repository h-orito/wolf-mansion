package com.ort.app.logic.daychange;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class MiserableLogic {

    @Autowired
    private MessageLogic messageLogic;

    // 無惨メッセージ
    public void insertAttackedMessage(DayChangeVillage dayChangeVillage) {
        List<VillagePlayer> victimPlayerList = dayChangeVillage.deadPlayers.filterMiserable().getList();
        if (CollectionUtils.isEmpty(victimPlayerList)) {
            messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .content("今日は犠牲者がいないようだ。人狼は襲撃に失敗したのだろうか。")
                    .build());
            return;
        }

        Collections.shuffle(victimPlayerList);
        StringJoiner joiner = new StringJoiner("と", "次の日の朝、", "が無惨な姿で発見された。");
        victimPlayerList.stream().forEach(player -> {
            joiner.add(player.name());
        });
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .content(joiner.toString())
                .build());
    }
}
