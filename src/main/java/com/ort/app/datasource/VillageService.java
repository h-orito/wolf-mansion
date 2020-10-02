package com.ort.app.datasource;

import java.util.Arrays;
import java.util.Optional;

import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillagePlayerDeadHistoryBhv;
import com.ort.dbflute.exbhv.VillagePlayerRoomHistoryBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayerDeadHistory;
import com.ort.dbflute.exentity.VillagePlayerRoomHistory;
import com.ort.fw.security.UserInfo;

@Repository
public class VillageService {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private VillageDayBhv villageDayBhv;
    @Autowired
    private VillagePlayerRoomHistoryBhv villagePlayerRoomHistoryBhv;
    @Autowired
    private VillagePlayerDeadHistoryBhv villagePlayerDeadHistoryBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Village selectVillage( //
            Integer villageId, //
            boolean includeGone, //
            boolean includeSpectator //
    ) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne().withCharaGroup();
            cb.query().setVillageId_Equal(villageId);
        });
        villageBhv.load(village, loader -> {
            loader.loadVillagePlayer(vpCB -> {
                vpCB.setupSelect_Chara();
                vpCB.setupSelect_Player();
                vpCB.setupSelect_SkillBySkillCode();
                vpCB.setupSelect_DeadReason();
                if (!includeGone) {
                    vpCB.query().setIsGone_Equal_False();
                }
                if (!includeSpectator) {
                    vpCB.query().setIsSpectator_Equal_False();
                }
                vpCB.query().addOrderBy_DeadDay_Asc();
            }).withNestedReferrer(vpLoader -> {
                vpLoader.loadVillagePlayerStatusByVillagePlayerId(vpStCB -> {
                    vpStCB.setupSelect_VillagePlayerByToVillagePlayerId().withChara();
                });
                vpLoader.pulloutChara().loadCharaImage(ciCB -> {});
                vpLoader.loadVillagePlayerDeadHistory(history -> {});
                vpLoader.loadVillagePlayerRoomHistory(history -> {});
            });
            loader.loadVillageDay(vdCB -> {
                vdCB.query().addOrderBy_Day_Asc();
            });
            loader.loadNormalSayRestriction(nsrCB -> {});
            loader.loadSkillSayRestriction(ssrCB -> {});
        });
        return village;
    }

    public int selectLatestDay(Integer villageId) {
        return villageDayBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnDay();
            cb.query().setVillageId_Equal(villageId);
        }).get();
    }

    public Optional<VillagePlayer> selectVillagePlayer(Integer villageId, UserInfo userInfo, boolean isContainSpectator) {
        if (userInfo == null) {
            return Optional.empty();
        }
        OptionalEntity<VillagePlayer> optVillagePlayer = villagePlayerBhv.selectEntity(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_Player();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().queryPlayer().setPlayerName_Equal(userInfo.getUsername());
            cb.query().queryVillage().setVillageStatusCode_NotInScope_AsVillageStatus(
                    Arrays.asList(CDef.VillageStatus.廃村, CDef.VillageStatus.終了));
        });
        if (!optVillagePlayer.isPresent()) {
            return Optional.empty();
        }
        villagePlayerBhv.load(optVillagePlayer.get(), loader -> {
            loader.loadVillagePlayerStatusByVillagePlayerId(vpStCB -> {
                vpStCB.setupSelect_VillagePlayerByToVillagePlayerId().withChara();
            });
            loader.pulloutChara().loadCharaImage(charaImageCB -> {
                charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc();
            });
            loader.loadVillagePlayerDeadHistory(history -> {});
            loader.loadVillagePlayerRoomHistory(history -> {});
        });
        return Optional.of(optVillagePlayer.get());
    }

    public void assignRoom(VillagePlayer villagePlayer, int roomNumber, int day) {
        // 村参加者をupdate
        VillagePlayer entity = new VillagePlayer();
        entity.setRoomNumber(roomNumber);
        villagePlayerBhv.queryUpdate(entity, cb -> cb.query().setVillagePlayerId_Equal(villagePlayer.getVillagePlayerId()));
        // 履歴をinsert
        VillagePlayerRoomHistory history = new VillagePlayerRoomHistory();
        history.setVillagePlayerId(villagePlayer.getVillagePlayerId());
        history.setRoomNumber(roomNumber);
        history.setDay(day);
        villagePlayerRoomHistoryBhv.insert(history);
    }

    public void dead(VillagePlayer targetPlayer, int day, CDef.DeadReason deadReason) {
        VillagePlayer vPlayer = new VillagePlayer();
        vPlayer.setDeadReasonCodeAsDeadReason(deadReason);
        vPlayer.setIsDead_True();
        vPlayer.setDeadDay(day);
        villagePlayerBhv.queryUpdate(vPlayer, cb -> cb.query().setVillagePlayerId_Equal(targetPlayer.getVillagePlayerId()));
        VillagePlayerDeadHistory history = new VillagePlayerDeadHistory();
        history.setVillagePlayerId(targetPlayer.getVillagePlayerId());
        history.setDay(day);
        history.setDeadReasonCodeAsDeadReason(deadReason);
        history.setIsDead_True();
        villagePlayerDeadHistoryBhv.insert(history);
    }

    public void revive(VillagePlayer targetPlayer, int day) {
        VillagePlayer vPlayer = new VillagePlayer();
        vPlayer.setDeadReasonCodeAsDeadReason(null);
        vPlayer.setIsDead_False();
        vPlayer.setDeadDay(null);
        villagePlayerBhv.queryUpdate(vPlayer, cb -> cb.query().setVillagePlayerId_Equal(targetPlayer.getVillagePlayerId()));
        VillagePlayerDeadHistory history = new VillagePlayerDeadHistory();
        history.setVillagePlayerId(targetPlayer.getVillagePlayerId());
        history.setDay(day);
        history.setIsDead_False();
        villagePlayerDeadHistoryBhv.insert(history);
    }
}
