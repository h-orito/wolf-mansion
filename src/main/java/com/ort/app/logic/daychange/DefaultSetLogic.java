package com.ort.app.logic.daychange;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.ability.AttackLogic;
import com.ort.app.logic.ability.CohabitLogic;
import com.ort.app.logic.ability.DisturbLogic;
import com.ort.app.logic.ability.DivineLogic;
import com.ort.app.logic.ability.GuardLogic;
import com.ort.app.logic.ability.InvestigateLogic;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.Vote;

@Component
public class DefaultSetLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private AttackLogic attackLogic;
    @Autowired
    private DivineLogic divineLogic;
    @Autowired
    private DisturbLogic disturbLogic;
    @Autowired
    private CohabitLogic cohabitLogic;
    @Autowired
    private GuardLogic guardLogic;
    @Autowired
    private InvestigateLogic investigateLogic;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VoteBhv voteBhv;
    @Autowired
    private VillageService villageService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 投票、能力行使のデフォルト設定、生存者メッセージ登録
    public void setDefaultVoteAndAbility(Integer villageId, int newDay) {
        // 最新の状況が必要なので取得し直す
        Village village = villageService.selectVillage(villageId, false, false);
        List<VillagePlayer> vPlayerList = village.getVillagePlayerList();

        // 噛み
        attackLogic.insertDefaultAttack(village, newDay);
        // 占い
        divineLogic.insertDefaultDivine(village, newDay);
        // 狂人と妖狐の足音
        disturbLogic.insertDefaultFootstep(village, newDay);
        // 同棲
        cohabitLogic.insertDefaultCohabit(village, newDay);

        if (newDay == 1) {
            return; // 1日目は護衛と投票なし
        }

        // 護衛
        guardLogic.insertDefaultGuard(village, newDay);
        // 探偵による調査
        investigateLogic.insertDefaultInvestigate(village, newDay);

        // 投票
        if (village.getVillageSettingsAsOne().get().isIsAvailableSuddonlyDeathFalse()) { // 突然死ありの場合はデフォ投票はなし
            village.getVillagePlayers().filterAlive().list.forEach(vp -> {
                insertVote(villageId, newDay, vp.getCharaId(), vp.getCharaId());
            });
        }
        // 生存者メッセージ登録
        insertAlivePlayerMessage(villageId, newDay, vPlayerList);
        // 足音メッセージ登録
        insertFootstepMessage(villageId, newDay, vPlayerList);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void insertVote(Integer villageId, int newDay, Integer charaId, Integer targetCharaId) {
        Vote vote = new Vote();
        vote.setVillageId(villageId);
        vote.setDay(newDay);
        vote.setCharaId(charaId);
        vote.setVoteCharaId(targetCharaId);
        voteBhv.insert(vote);
    }

    // 生存者メッセージ登録
    private void insertAlivePlayerMessage(Integer villageId, int day, List<VillagePlayer> vPlayerList) {
        long livePersonNum = vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).count();
        StringJoiner joiner = new StringJoiner("、", "現在の生存者は、以下の" + livePersonNum + "名。\n", "");
        vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber()).forEach(
                player -> joiner.add(player.name()));
        messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, joiner.toString());
    }

    private void insertFootstepMessage(Integer villageId, int newDay, List<VillagePlayer> vPlayerList) {
        if (newDay == 1) {
            return;
        }
        List<Integer> livingPlayerRoomNumList =
                vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).map(VillagePlayer::getRoomNumber).collect(Collectors.toList());
        String message = footstepLogic.getFootstepMessage(villageId, newDay - 1, livingPlayerRoomNumList);
        messageLogic.insertMessageIgnoreError(villageId, newDay, CDef.MessageType.公開システムメッセージ, message);
    }
}
