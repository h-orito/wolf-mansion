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
import com.ort.app.logic.ability.CourtLogic;
import com.ort.app.logic.ability.DisturbLogic;
import com.ort.app.logic.ability.DivineLogic;
import com.ort.app.logic.ability.GuardLogic;
import com.ort.app.logic.ability.InvestigateLogic;
import com.ort.app.logic.ability.StalkingLogic;
import com.ort.app.logic.message.MessageEntity;
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
    private CourtLogic courtLogic;
    @Autowired
    private StalkingLogic stalkingLogic;
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
    // ??????????????????????????????????????????????????????????????????????????????
    public void setDefaultVoteAndAbility(Integer villageId, int newDay) {
        // ????????????????????????????????????????????????
        Village village = villageService.selectVillage(villageId, false, false);
        List<VillagePlayer> vPlayerList = village.getVillagePlayerList();

        // ??????
        attackLogic.insertDefaultAttack(village, newDay);
        // ??????
        divineLogic.insertDefaultDivine(village, newDay);
        // ????????????????????????
        disturbLogic.insertDefaultFootstep(village, newDay);
        // ??????
        cohabitLogic.insertDefaultCohabit(village, newDay);

        if (newDay == 1) {
            courtLogic.insertDefaultCourt(village, newDay);
            stalkingLogic.insertDefaultStalking(village, newDay);
            return; // 1??????????????????????????????
        }

        // ??????
        guardLogic.insertDefaultGuard(village, newDay);
        // ?????????????????????
        investigateLogic.insertDefaultInvestigate(village, newDay);

        // ??????
        if (village.getVillageSettingsAsOne().get().isIsAvailableSuddonlyDeathFalse()) { // ???????????????????????????????????????????????????
            village.getVillagePlayers().filterAlive().list.forEach(vp -> {
                insertVote(villageId, newDay, vp.getCharaId(), vp.getCharaId());
            });
        }
        // ??????????????????????????????
        insertAlivePlayerMessage(villageId, newDay, vPlayerList);
        // ???????????????????????????
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

    // ??????????????????????????????
    private void insertAlivePlayerMessage(Integer villageId, int day, List<VillagePlayer> vPlayerList) {
        long livePersonNum = vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).count();
        StringJoiner joiner = new StringJoiner("???", "?????????????????????????????????" + livePersonNum + "??????\n", "");
        vPlayerList.stream()
                .filter(vp -> vp.isIsDeadFalse())
                .sorted((vp1, vp2) -> vp1.getRoomNumber() - vp2.getRoomNumber())
                .forEach(player -> joiner.add(player.name()));
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, day) //
                .content(joiner.toString())
                .build());
    }

    private void insertFootstepMessage(Integer villageId, int newDay, List<VillagePlayer> vPlayerList) {
        if (newDay == 1) {
            return;
        }
        List<Integer> livingPlayerRoomNumList =
                vPlayerList.stream().filter(vp -> vp.isIsDeadFalse()).map(VillagePlayer::getRoomNumber).collect(Collectors.toList());
        String message = footstepLogic.getFootstepMessage(villageId, newDay - 1, livingPlayerRoomNumList);
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, newDay) //
                .content(message)
                .build());
    }
}
