package com.ort.app.logic.daychange;

import java.time.LocalDateTime;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ort.app.logic.MessageLogic;
import com.ort.app.web.dto.VillageInfo;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Camp;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.CommitBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Vote;

/**
 * DayChangeLogicはロジックに集中させたいので、本質的でないDB操作等はこちらへ
 * @author h-orito
 */
@Component
public class DayChangeLogicHelper {

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
    private VillageDayBhv villageDayBhv;
    @Autowired
    private CommitBhv commitBhv;
    @Autowired
    private AbilityBhv abilityBhv;
    @Autowired
    private VoteBhv voteBhv;
    @Autowired
    private MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public VillageInfo convertToVillageInfo(Village village) {
        VillageInfo vInfo = new VillageInfo();
        vInfo.villageId = village.getVillageId();
        vInfo.village = village;
        vInfo.vPlayers = village.getVillagePlayers();
        vInfo.settings = village.getVillageSettingsAsOne().get();
        vInfo.day = village.getVillageDays().latestDay().getDay();

        return vInfo;
    }

    public int selectCommitNum(VillageInfo vInfo) {
        return commitBhv.selectCount(cb -> {
            cb.query().setVillageId_Equal(vInfo.village.getVillageId());
            cb.query().setDay_Equal(vInfo.day);
        });
    }

    public Village selectVillage(Integer villageId) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
        villageBhv.load(village, loader -> {
            loader.loadVillagePlayer(vpCB -> {
                vpCB.setupSelect_Chara();
                vpCB.setupSelect_Player();
                vpCB.setupSelect_SkillBySkillCode();
                vpCB.query().setIsGone_Equal_False();
                vpCB.query().setIsSpectator_Equal_False();
            }).withNestedReferrer(vpLoader -> {
                vpLoader.loadVillagePlayerStatusByVillagePlayerId(vpStCB -> {
                    vpStCB.setupSelect_VillagePlayerByToVillagePlayerId().withChara();
                });
            });
        });
        return village;
    }

    public ListResultBean<VillagePlayer> selectVillagePlayerList(Integer villageId) {
        return villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Player();
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    public List<Vote> selectVoteList(Integer villageId, int day) {
        return voteBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
        });
    }

    public ListResultBean<Ability> selectAbilityList(Integer villageId, int day) {
        return abilityBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
        });
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    public void updateVillageDay(VillageInfo vInfo, LocalDateTime daychangeDatetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(vInfo.villageId);
        villageDay.setDay(vInfo.day);
        villageDay.setDaychangeDatetime(daychangeDatetime.plusDays(1L)); // 1日延長
        villageDayBhv.update(villageDay);
    }

    public void updateVillageStatus(Integer villageId, CDef.VillageStatus status) {
        Village village = new Village();
        village.setVillageStatusCodeAsVillageStatus(status);
        villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
    }

    public void updateVillageEpilogue(Integer villageId, int day, Camp winCamp) {
        Village village = new Village();
        village.setVillageStatusCode_エピローグ();
        village.setEpilogueDay(day);
        village.setWinCampCodeAsCamp(winCamp);
        villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
    }

    // 村日付のみ即コミットされるようにする。publicでないと効かないので注意。
    @Transactional(rollbackFor = Exception.class) // 念のため検査例外でもロールバックされるようにしておく
    public void insertVillageDayTransactional(Integer villageId, int day, LocalDateTime nextDayChangeDatetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(villageId);
        villageDay.setDay(day);
        villageDay.setDaychangeDatetime(nextDayChangeDatetime);
        villageDayBhv.insert(villageDay);
    }

    public void updateVillageSettingsSameWolfAttackTrue(Integer villageId) {
        VillageSettings vs = new VillageSettings();
        vs.setVillageId(villageId);
        vs.setIsAvailableSameWolfAttack_True();
        villageSettingsBhv.update(vs);
    }

    public void updateVillagePlayerDead(int day, VillagePlayer targetPlayer, CDef.DeadReason deadReason) {
        VillagePlayer vPlayer = new VillagePlayer();
        vPlayer.setDeadReasonCodeAsDeadReason(deadReason);
        vPlayer.setIsDead_True();
        vPlayer.setDeadDay(day);
        villagePlayerBhv.queryUpdate(vPlayer, cb -> cb.query().setVillagePlayerId_Equal(targetPlayer.getVillagePlayerId()));
    }

    public void deadBomberIfNeeded(Integer villageId, int day, ListResultBean<VillagePlayer> villagePlayerList) {
        villagePlayerList.stream().filter(vp -> {
            return vp.isSkillCode爆弾魔() && vp.isIsDeadFalse();
        }).forEach(bomber -> {
            OptionalEntity<Ability> optAbility = abilityBhv.selectEntity(cb -> {
                cb.query().setVillageId_Equal(villageId);
                cb.query().setAbilityTypeCode_Equal_爆弾設置();
                cb.query().setCharaId_Equal(bomber.getCharaId());
                cb.fetchFirst(1);
            });
            if (optAbility.isPresent()) {
                return;
            }
            // 爆弾を設置していない
            String message = String.format("%sは、物足りないので自分の部屋を爆破した。", bomber.name());
            messageLogic.insertMessageIgnoreError(villageId, day, CDef.MessageType.公開システムメッセージ, message);
            updateVillagePlayerDead(day, bomber, CDef.DeadReason.爆死); // 死亡処理
        });
    }

    public void updateIsWin(ListResultBean<VillagePlayer> villagePlayerList, CDef.Camp winCamp) {
        villagePlayerList.forEach(vp -> {
            if (CDef.Camp.codeOf(vp.getCampCode()) == CDef.Camp.愉快犯陣営) {
                // 愉快犯陣営は生存していれば追加勝利
                vp.setIsWin(vp.isIsDeadFalse());
            } else {
                // 他は勝利陣営だったら勝利
                vp.setIsWin(winCamp.code().equals(vp.getCampCode()));
            }
            villagePlayerBhv.update(vp);
        });
    }

    public void insertMessage(DayChangeVillage dayChangeVillage, CDef.MessageType type, String message) {
        messageLogic.insertMessageIgnoreError(dayChangeVillage.villageId, dayChangeVillage.day, type, message);
    }

    public void insertMessage(DayChangeVillage dayChangeVillage, CDef.MessageType type, String message, Integer villagePlayerId) {
        messageLogic.insertMessageIgnoreError(dayChangeVillage.villageId, dayChangeVillage.day, type, message, villagePlayerId, true, null);
    }

    public void insertMessage(DayChangeVillage dayChangeVillage, CDef.MessageType type, String message, Integer villagePlayerId,
            CDef.FaceType faceType) {
        messageLogic.insertMessageIgnoreError(dayChangeVillage.villageId, dayChangeVillage.day, type, message, villagePlayerId, true,
                faceType);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============

}
