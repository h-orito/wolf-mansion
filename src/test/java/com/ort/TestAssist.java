package com.ort;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.DayChangeLogic;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;

@Component
public class TestAssist {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final String DEAFULT_ORG = "狼狼狼狂狐賢導狩共共霊霊霊霊霊霊";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private VillageSettingsBhv villageSettingsBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private MessageBhv messageBhv;
    @Autowired
    private VillageDayBhv villageDayBhv;
    @Autowired
    private DayChangeLogic dayChangeLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Integer createVillage() {
        return this.createVillage(DEAFULT_ORG);
    }

    public Integer createVillage(String organize) {
        Integer villageId = insertVillage();
        insertVillageSetting(villageId, organize);
        insertVillageDay(villageId, 0, LocalDateTime.now().plusHours(1L));
        return villageId;
    }

    // フルメン＋見学1人＋退村1人
    public Integer createPrologueFullMemberVillage() {
        return this.createPrologueFullMemberVillage(DEAFULT_ORG);
    }

    // フルメン＋見学1人＋退村1人
    public Integer createPrologueFullMemberVillage(String organize) {
        Integer villageId = this.createVillage(organize);
        int personNum = organize.length();
        for (int i = 1; i <= personNum; i++) {
            this.participate(villageId, i, i, this.getRandomSkill(), this.getRandomSkill());
        }
        this.spectate(villageId, personNum + 1, personNum + 1);
        Integer vpId = this.participate(villageId, personNum + 2, personNum + 2, getRandomSkill(), getRandomSkill());
        this.leave(vpId);
        return villageId;
    }

    public Integer participate(//
            Integer villageId, //
            Integer playerId, //
            Integer charaId //
    ) {
        return this.participate(villageId, playerId, charaId, CDef.Skill.おまかせ, CDef.Skill.おまかせ);
    }

    public Integer participate(//
            Integer villageId, //
            Integer playerId, //
            Integer charaId, //
            CDef.Skill requestSkill, //
            CDef.Skill secondRequestSkill //
    ) {
        VillagePlayer vp = new VillagePlayer();
        vp.setVillageId(villageId);
        vp.setPlayerId(playerId);
        vp.setCharaId(charaId);
        vp.setRequestSkillCodeAsSkill(requestSkill);
        vp.setSecondRequestSkillCodeAsSkill(secondRequestSkill);
        vp.setLastAccessDatetime(LocalDateTime.now());
        vp.setIsDead_False();
        vp.setIsSpectator_False();
        vp.setIsGone_False();
        villagePlayerBhv.insert(vp);
        return vp.getVillagePlayerId();
    }

    public void leave(Integer villagePlayerId) {
        VillagePlayer vp = new VillagePlayer();
        vp.setVillagePlayerId(villagePlayerId);
        vp.setIsGone_True();
        villagePlayerBhv.update(vp);
    }

    public Integer spectate( //
            Integer villageId, //
            Integer playerId, //
            Integer charaId) {
        VillagePlayer vp = new VillagePlayer();
        vp.setVillageId(villageId);
        vp.setPlayerId(playerId);
        vp.setCharaId(charaId);
        vp.setLastAccessDatetime(LocalDateTime.now());
        vp.setIsDead_False();
        vp.setIsSpectator_True();
        vp.setIsGone_False();
        villagePlayerBhv.insert(vp);
        return vp.getVillagePlayerId();
    }

    public void kill(VillagePlayer villagePlayer, int day, CDef.DeadReason reason) {
        villagePlayer.setDeadDay(day);
        villagePlayer.setIsDead_True();
        villagePlayer.setDeadReasonCodeAsDeadReason(reason);
        villagePlayerBhv.update(villagePlayer);
    }

    public void insertVillageDay(Integer villageId, int day, LocalDateTime datetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(villageId);
        villageDay.setDay(day);
        villageDay.setDaychangeDatetime(datetime);
        villageDayBhv.insert(villageDay);
    }

    // 次の日へ
    public void toNextDay(Integer villageId) {
        toLatestDayChangeDatetimePast(villageId);
        dayChangeLogic.dayChangeIfNeeded(villageId);
    }

    // 新しい日付だけ追加
    public void addNextDay(Integer villageId) {
        VillageDay latestDay = villageDayBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get();
        latestDay.setDaychangeDatetime(LocalDateTime.now().plusSeconds(1L));
        latestDay.setDay(latestDay.getDay() + 1);
        villageDayBhv.insert(latestDay);
    }

    // 最新日の更新時間を過去に
    public void toLatestDayChangeDatetimePast(Integer villageId) {
        VillageDay latestDay = villageDayBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get();
        latestDay.setDaychangeDatetime(LocalDateTime.now().minusSeconds(1L));
        villageDayBhv.update(latestDay);
    }

    public List<Message> selectLatestMessage(Integer villageId, int day) {
        return messageBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
        });
    }

    private Integer insertVillage() {
        Village village = new Village();
        village.setVillageDisplayName("village_name");
        village.setCreatePlayerName("creator");
        village.setVillageStatusCode_募集中();
        villageBhv.insert(village);
        return village.getVillageId();
    }

    private void insertVillageSetting(Integer villageId, String organize) {
        VillageSettings setting = new VillageSettings();
        setting.setVillageId(villageId);
        setting.setDummyCharaId(1);
        setting.setStartPersonMinNum(organize.length());
        setting.setPersonMaxNum(organize.length());
        setting.setStartDatetime(LocalDateTime.now().plusHours(1L));
        setting.setDayChangeIntervalSeconds(60 * 60 * 24);
        setting.setIsOpenVote(true);
        setting.setIsPossibleSkillRequest_True();
        setting.setIsAvailableSpectate_True();
        setting.setIsAvailableSameWolfAttack_False();
        setting.setIsOpenSkillInGrave_False();
        setting.setIsVisibleGraveSpectateMessage_False();
        setting.setIsAvailableSuddonlyDeath_True();
        setting.setIsAvailableCommit_True();
        setting.setIsAvailableGuardSameTarget_False();
        setting.setCharacterGroupId(1);
        setting.setOrganize(organize);
        setting.setAllowedSecretSayCode_全員();
        villageSettingsBhv.insert(setting);
    }

    private CDef.Skill getRandomSkill() {
        List<Skill> list = CDef.Skill.listAll();
        Collections.shuffle(list);
        return list.get(0);
    }
}
