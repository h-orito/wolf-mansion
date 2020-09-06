package com.ort.app.logic.daychange;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class MiserableLogic {

    @Autowired
    private DayChangeLogicHelper helper;

    // 無惨メッセージ
    public void insertAttackedMessage(DayChangeVillage dayChangeVillage) {
        List<VillagePlayer> victimPlayerList = dayChangeVillage.deadPlayers.filterMiserable().getList();
        if (CollectionUtils.isEmpty(victimPlayerList)) {
            helper.insertMessage(dayChangeVillage, CDef.MessageType.公開システムメッセージ, "今日は犠牲者がいないようだ。人狼は襲撃に失敗したのだろうか。");
            return;
        }

        Collections.shuffle(victimPlayerList);
        StringJoiner joiner = new StringJoiner("と", "次の日の朝、", "が無惨な姿で発見された。");
        victimPlayerList.stream().forEach(player -> {
            joiner.add(player.name());
        });
        helper.insertMessage(dayChangeVillage, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }
}
