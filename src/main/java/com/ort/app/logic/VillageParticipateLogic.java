package com.ort.app.logic;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Chara;
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
        Chara chara = charaBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaId_Equal(charaId));
        String charaName = chara.getCharaName();
        VillagePlayer villagePlayer =
                insertVillagePlayer(villageId, playerId, chara, requestSkill, secondRequestSkill, joinMessage, isSpectator);
        int participateNum = villagePlayerBhv.selectCount(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal(isSpectator);
        });

        // 参加メッセージ
        String systemMessage = String.format("%s%d人目、%s。", isSpectator ? "(見学) " : "", participateNum, charaName);
        insertParticipateSystemMessage(villageId, charaName, participateNum, systemMessage);
        // 参加発言
        insertJoinMessage(villageId, joinMessage, isConvertDisable, villagePlayer,
                isSpectator ? CDef.MessageType.見学発言 : CDef.MessageType.通常発言);
        if (!isSpectator) {
            // 希望役職メッセージ
            insertSkillRequestMessage(villageId, requestSkill, secondRequestSkill, charaName);
            // ちょうど開始人数だったらTweet
            tweetIfNeeded(participateNum, villageId);
        }
        return villagePlayer.getVillagePlayerId();
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
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villagePlayer.getVillageId(), 0) //
                .content(String.format("%sは村を去った。", charaName))
                .build());
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
        String charaName = villagePlayer.name();
        String message = messageSource.getMessage("requestskill.message",
                new String[] { charaName, requestSkill.alias(), secondRequestSkill.alias() }, Locale.JAPAN);
        messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(villagePlayer.getVillageId(), 0) //
                .content(message)
                .build());
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
                villagePlayerCB.query()
                        .queryVillage()
                        .setVillageStatusCode_InScope_AsVillageStatus(
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
            cb.query()
                    .setVillageStatusCode_InScope_AsVillageStatus(
                            Arrays.asList(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
        });

        return progressVillageCount > 0;
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private VillagePlayer insertVillagePlayer( //
            Integer villageId, //
            Integer playerId, //
            Chara chara, // 
            CDef.Skill requestSkill, //
            CDef.Skill secondRequestSkill, //
            String joinMessage, //
            boolean isSpectator //
    ) {
        VillagePlayer villagePlayer = new VillagePlayer();
        villagePlayer.setVillageId(villageId);
        villagePlayer.setPlayerId(playerId);
        villagePlayer.setCharaId(chara.getCharaId());
        villagePlayer.setIsDead_False();
        villagePlayer.setIsSpectator(isSpectator);
        villagePlayer.setIsGone_False();
        villagePlayer.setRequestSkillCodeAsSkill(requestSkill);
        villagePlayer.setSecondRequestSkillCodeAsSkill(secondRequestSkill);
        villagePlayer.setLastAccessDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        villagePlayer.setCharaName(chara.getCharaName());
        villagePlayer.setCharaShortName(chara.getCharaShortName());
        villagePlayerBhv.insert(villagePlayer);
        return villagePlayer;
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
        if (StringUtils.isNotEmpty(settings.getJoinPassword())) {
            return; // 身内村は通知しない
        }
        String villageName = village.getVillageDisplayName();
        String startDatetime = settings.getStartDatetime().format(TIME_FORMAT);

        if (settings.getStartPersonMinNum().intValue() == participateNum) {
            twitterLogic.tweet(String.format("人数が揃いました。次回更新時に村が開始されます。\r\n村名：%s\r\n開始予定：%s", villageName, startDatetime), villageId);
        }
    }

    // 参加システムメッセージ
    private void insertParticipateSystemMessage(Integer villageId, String charaName, int participateNum, String systemMessage) {
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, 0) //
                .content(systemMessage)
                .build());
    }

    // 参加発言
    private void insertJoinMessage(Integer villageId, String joinMessage, boolean isConvertDisable, VillagePlayer villagePlayer,
            CDef.MessageType messageType) {
        messageLogic.saveIgnoreError(new MessageEntity.Builder(villageId, 0) //
                .messageType(messageType)
                .content(joinMessage)
                .villagePlayer(villagePlayer)
                .isConvertDisable(isConvertDisable)
                .faceType(CDef.FaceType.通常)
                .build());
    }

    // 希望役職メッセージ
    private void insertSkillRequestMessage(Integer villageId, CDef.Skill requestSkill, CDef.Skill secondRequestSkill, String charaName) {
        String message = messageSource.getMessage("requestskill.message",
                new String[] { charaName, requestSkill.alias(), secondRequestSkill.alias() }, Locale.JAPAN);
        messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(villageId, 0) //
                .content(message)
                .build());
    }
}
