package com.ort.app.web.controller.logic;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.util.CharaUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionDateUtil;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageParticipateLogic {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private MessageLogic messageLogic;

    @Autowired
    private TwitterLogic twitterLogic;

    @Autowired
    private CharaBhv charaBhv;

    @Autowired
    private PlayerBhv playerBhv;

    @Autowired
    private MessageSource messageSource;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Integer participate(Integer villageId, Integer playerId, Integer charaId, CDef.Skill requestSkill, CDef.Skill secondRequestSkill,
            String joinMessage, boolean isSpectator, boolean isConvertDisable) {
        // 村参加
        Integer villagePlayerId =
                insertVillagePlayer(villageId, playerId, charaId, requestSkill, secondRequestSkill, joinMessage, isSpectator);
        if (!isSpectator) {
            // 参加メッセージ
            int participateNum = villagePlayerBhv.selectCount(cb -> {
                cb.query().setVillageId_Equal(villageId);
                cb.query().setIsGone_Equal_False();
                cb.query().setIsSpectator_Equal_False();
            });
            String charaName = charaBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaId_Equal(charaId)).getCharaName();
            try {
                messageLogic.insertMessage(villageId, 0, CDef.MessageType.公開システムメッセージ, String.format("%d人目、%s。", participateNum, charaName),
                        true);
                // 参加発言
                messageLogic.insertMessage(villageId, 0, CDef.MessageType.通常発言, joinMessage, villagePlayerId, isConvertDisable,
                        CDef.FaceType.通常);
                // 希望役職メッセージ
                String message = messageSource.getMessage("requestskill.message",
                        new String[] { charaName, requestSkill.alias(), secondRequestSkill.alias() }, Locale.JAPAN);
                messageLogic.insertMessage(villageId, 0, CDef.MessageType.非公開システムメッセージ, message, true);
            } catch (WerewolfMansionBusinessException e) {
                // ここでは何回も被らないので何もしない
            }
            // ちょうど開始人数だったらTweet
            tweetIfNeeded(participateNum, villageId);
        } else {
            // 見学者
            // 参加メッセージ
            int participateNum = villagePlayerBhv.selectCount(cb -> {
                cb.query().setVillageId_Equal(villageId);
                cb.query().setIsGone_Equal_False();
                cb.query().setIsSpectator_Equal_True();
            });
            String charaName = charaBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaId_Equal(charaId)).getCharaName();
            try {
                messageLogic.insertMessage(villageId, 0, CDef.MessageType.公開システムメッセージ,
                        String.format("(見学) %d人目、%s。", participateNum, charaName), true);
                // 参加発言
                messageLogic.insertMessage(villageId, 0, CDef.MessageType.見学発言, joinMessage, villagePlayerId, isConvertDisable,
                        CDef.FaceType.通常);
            } catch (WerewolfMansionBusinessException e) {
                // ここでは何回も被らないので何もしない
            }
        }
        return villagePlayerId;
    }

    public void leave(VillagePlayer villagePlayer) {
        // プレイヤー削除
        VillagePlayer entity = new VillagePlayer();
        entity.setIsGone_True();
        villagePlayerBhv.queryUpdate(entity, cb -> {
            cb.query().setVillagePlayerId_Equal(villagePlayer.getVillagePlayerId());
        });
        // 退村発言
        String charaName =
                charaBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaId_Equal(villagePlayer.getCharaId())).getCharaName();
        try {
            messageLogic.insertMessage(villagePlayer.getVillageId(), 0, CDef.MessageType.公開システムメッセージ, String.format("%sは村を去った。", charaName),
                    true);
        } catch (WerewolfMansionBusinessException e) {
            // ここでは何回も被らないので何もしない
        }
    }

    public void changeRequestSkill(VillagePlayer villagePlayer, String skillCode, String secondSkillCode) {
        CDef.Skill requestSkill = CDef.Skill.codeOf(skillCode);
        CDef.Skill secondRequestSkill = CDef.Skill.codeOf(secondSkillCode);
        if (requestSkill == null || secondRequestSkill == null) {
            throw new IllegalArgumentException("不正な役職コード");
        }
        VillagePlayer entity = new VillagePlayer();
        entity.setRequestSkillCodeAsSkill(requestSkill);
        entity.setSecondRequestSkillCodeAsSkill(secondRequestSkill);
        // 希望役職変更
        villagePlayerBhv.queryUpdate(entity, cb -> {
            cb.query().setVillagePlayerId_Equal(villagePlayer.getVillagePlayerId());
        });
        // 変更メッセージ
        String charaName = CharaUtil.makeCharaName(villagePlayer);
        String message = messageSource.getMessage("requestskill.message",
                new String[] { charaName, requestSkill.alias(), secondRequestSkill.alias() }, Locale.JAPAN);
        try {
            messageLogic.insertMessage(villagePlayer.getVillageId(), 0, CDef.MessageType.非公開システムメッセージ, message, true);
        } catch (WerewolfMansionBusinessException e) {
            // ここでは何回も被らないので何もしない
        }
    }

    public boolean isParticipatingOrCreatingVillage() {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            return false;
        }
        if ("master".equals(userInfo.getUsername())) {
            return false; // masterは参加してない扱い
        }
        // 終了していない村に参加しているか
        String username = userInfo.getUsername();
        int participateCount = playerBhv.selectCount(cb -> {
            cb.query().setPlayerName_Equal(username);
            // 募集中、開始待ち、進行中の村に参戦している
            cb.query().existsVillagePlayer(villagePlayerCB -> {
                villagePlayerCB.query().queryVillage().setVillageStatusCode_InScope_AsVillageStatus(
                        Arrays.asList(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
                villagePlayerCB.query().setIsGone_Equal_False();
            });
        });
        if (participateCount > 0) {
            return true;
        }
        // 自分が建てた村が終了していない
        int progressVillageCount = villageBhv.selectCount(cb -> {
            cb.query().setCreatePlayerName_Equal(username);
            cb.query().setVillageStatusCode_InScope_AsVillageStatus(
                    Arrays.asList(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
        });

        return progressVillageCount > 0;
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private Integer insertVillagePlayer(Integer villageId, Integer playerId, Integer charaId, CDef.Skill requestSkill,
            CDef.Skill secondRequestSkill, String joinMessage, boolean isSpectator) {
        VillagePlayer villagePlayer = new VillagePlayer();
        villagePlayer.setVillageId(villageId);
        villagePlayer.setPlayerId(playerId);
        villagePlayer.setCharaId(charaId);
        villagePlayer.setIsDead_False();
        villagePlayer.setIsSpectator(isSpectator);
        villagePlayer.setIsGone_False();
        villagePlayer.setRequestSkillCodeAsSkill(requestSkill);
        villagePlayer.setSecondRequestSkillCodeAsSkill(secondRequestSkill);
        villagePlayer.setLastAccessDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        villagePlayerBhv.insert(villagePlayer);
        return villagePlayer.getVillagePlayerId();
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void tweetIfNeeded(int participateNum, Integer villageId) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.setupSelect_VillageSettingsAsOne();
        });
        VillageSettings settings = village.getVillageSettingsAsOne().get();
        String villageName = village.getVillageDisplayName();
        String startDatetime = settings.getStartDatetime().format(TIME_FORMAT);

        if (settings.getStartPersonMinNum().intValue() == participateNum) {
            twitterLogic.tweet(String.format("人数が揃いました。次回更新時に村が開始されます。\r\n村名：%s\r\n開始予定：%s", villageName, startDatetime), villageId);
        }
    }
}
