package com.ort.app.logic.daychange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.ability.AttackLogic;
import com.ort.app.logic.ability.CohabitLogic;
import com.ort.app.logic.ability.CourtLogic;
import com.ort.app.logic.ability.DisturbLogic;
import com.ort.app.logic.ability.DivineLogic;
import com.ort.app.logic.ability.FantasistLogic;
import com.ort.app.logic.ability.GuardLogic;
import com.ort.app.logic.ability.InvestigateLogic;
import com.ort.app.logic.ability.SleepwalkLogic;
import com.ort.app.logic.ability.StalkingLogic;
import com.ort.app.logic.ability.WallPunchLogic;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Village;
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
    private WallPunchLogic wallPunchLogic;
    @Autowired
    private InvestigateLogic investigateLogic;
    @Autowired
    private CourtLogic courtLogic;
    @Autowired
    private StalkingLogic stalkingLogic;
    @Autowired
    private FantasistLogic fantasistLogic;
    @Autowired
    private SleepwalkLogic sleepwarkLogic;
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

        // 噛み
        attackLogic.insertDefaultAttack(village, newDay);
        // 占い
        divineLogic.insertDefaultDivine(village, newDay);
        // 狂人と妖狐の足音
        disturbLogic.insertDefaultFootstep(village, newDay);
        // 同棲
        cohabitLogic.insertDefaultCohabit(village, newDay);
        // 妄想癖
        fantasistLogic.insertDefaultFootstep(village, newDay);
        // 夢遊病
        sleepwarkLogic.insertDefaultFootstep(village, newDay);

        if (newDay == 1) {
            courtLogic.insertDefaultCourt(village, newDay);
            stalkingLogic.insertDefaultStalking(village, newDay);
            return; // 1日目は護衛と投票なし
        }

        // 護衛
        guardLogic.insertDefaultGuard(village, newDay);
        // 壁殴り代行
        wallPunchLogic.insertDefaultWallPunch(village, newDay);
        // 探偵による調査
        investigateLogic.insertDefaultInvestigate(village, newDay);

        // 投票
        if (village.getVillageSettingsAsOne().get().isIsAvailableSuddonlyDeathFalse()) { // 突然死ありの場合はデフォ投票はなし
            village.getVillagePlayers().filterAlive().list.forEach(vp -> {
                insertVote(villageId, newDay, vp.getCharaId(), vp.getCharaId());
            });
        }
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
}
