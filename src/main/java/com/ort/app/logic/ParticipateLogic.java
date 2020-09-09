package com.ort.app.logic;

import java.util.Arrays;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.web.dto.VillageInfo;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exentity.Chara;

@Component
public class ParticipateLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private PlayerBhv playerBhv;
    @Autowired
    private CharaBhv charaBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 入村可能か
    public boolean isAvailableParticipate(VillageInfo villageInfo) {
        // ログインしていない場合は表示しない
        if (villageInfo.user == null) {
            return false;
        }
        // 現在プロローグでない場合表示しない
        if (!villageInfo.village.isVillageStatusCode募集中() && !villageInfo.village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // 決着のついていない村に参戦している場合表示しない
        int participateCount = playerBhv.selectCount(cb -> {
            cb.query().setPlayerName_Equal(villageInfo.user.getUsername());
            // 募集中、開始待ち、進行中の村に参戦している
            cb.query().existsVillagePlayer(villagePlayerCB -> {
                villagePlayerCB.query().queryVillage().setVillageStatusCode_InScope_AsVillageStatus(
                        Arrays.asList(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
                villagePlayerCB.query().setIsGone_Equal_False();
            });
        });
        if (participateCount > 0) {
            return false;
        }
        // 既に最大人数まで参加していたら表示しない
        int maxCharaNum = charaBhv.selectCount(cb -> cb.query().setCharaGroupId_Equal(villageInfo.settings.getCharacterGroupId()));
        int participateNum = villageInfo.vPlayers.filterNotGone().list.size();
        if (participateNum >= maxCharaNum) {
            return false;
        }
        // 突然死により入村制限がかかっている人は表示しない
        if (selectIsRestrictedParticipation(villageInfo.user.getUsername())) {
            return false;
        }
        return true;
    }

    // 退村可能か
    public boolean isAvailableLeave(VillageInfo villageInfo) {
        // 現在プロローグでない場合NG
        if (!villageInfo.village.isVillageStatusCode募集中() && !villageInfo.village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // この村に参戦していない場合NG
        if (!villageInfo.isParticipate()) {
            return false;
        }
        return true;
    }

    // 参戦キャラとして選択可能なキャラを取得
    public List<Chara> selectSelectableCharaList(VillageInfo villageInfo) {
        List<Integer> alreadyParticipateCharaIdList = villageInfo.vPlayers.filterNotGone().map(vp -> vp.getCharaId());
        ListResultBean<Chara> charaList = charaBhv.selectList(cb -> {
            cb.query().setCharaGroupId_Equal(villageInfo.settings.getCharacterGroupId());
            cb.query().setCharaId_NotInScope(alreadyParticipateCharaIdList);
        });
        charaBhv.loadCharaImage(charaList, ciCB -> {
            ciCB.query().setFaceTypeCode_Equal_通常();
        });
        return charaList;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private boolean selectIsRestrictedParticipation(String playerName) {
        return playerBhv.selectEntity(cb -> {
            cb.query().setPlayerName_Equal(playerName);
            cb.query().setIsRestrictedParticipation_Equal_True();
        }).isPresent();
    }
}
