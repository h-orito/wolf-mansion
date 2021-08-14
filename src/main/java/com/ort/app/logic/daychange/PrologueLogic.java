package com.ort.app.logic.daychange;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.AssignLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.TwitterLogic;
import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.logic.message.MessageEntity;
import com.ort.app.util.SkillUtil;
import com.ort.app.web.dto.VillageInfo;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.util.WolfMansionDateUtil;

@Component
public class PrologueLogic {

    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private AssignLogic assignLogic;
    @Autowired
    private VillageParticipateLogic villageParticipateLogic;
    @Autowired
    private TwitterLogic twitterLogic;
    @Autowired
    private DefaultSetLogic defaultSetLogic;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private VillageService villageService;

    // プロローグで長時間接続していない人がいたら退村させる
    public void leaveVillageIfNeeded(VillageInfo vInfo) {
        if (vInfo.day != 0) {
            return;
        }

        // 24時間アクセスしていなかったら村を出る
        LocalDateTime yesterday = WolfMansionDateUtil.currentLocalDateTime().minusDays(1L);

        villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(vInfo.villageId);
            cb.orScopeQuery(orCB -> {
                orCB.query().setLastAccessDatetime_IsNull();
                orCB.query().setLastAccessDatetime_LessThan(yesterday);
            });
            cb.query().setCharaId_NotEqual(vInfo.settings.getDummyCharaId());
            cb.query().setIsGone_Equal_False();
        }).stream().forEach(vp -> {
            villageParticipateLogic.leave(vp);
        });
    }

    // 延長or廃村
    public void extendOrCancelVillage(VillageInfo vInfo, LocalDateTime daychangeDatetime) {
        if (vInfo.vPlayers.list.size() > 1) {
            // 一人でも参加していたら延長
            helper.updateVillageDay(vInfo, daychangeDatetime); // 村日付を1日延長
            // 延長メッセージ登録
            messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(vInfo.villageId, vInfo.day) //
                    .content("まだ村人たちは揃っていないようだ。")
                    .build());
        } else {
            // 一人も参加していなかったら廃村
            helper.updateVillageStatus(vInfo.villageId, CDef.VillageStatus.廃村);
        }
    }

    // 村を開始させる
    public void startVillage(VillageInfo vInfo, int newDay) {
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(vInfo.villageId, 1)
                .content(messageSource.getMessage("village.start.message.day1", null, Locale.JAPAN))
                .build());
        assignLogic.assignSkill(vInfo.villageId, vInfo.vPlayers, vInfo.settings); // 役職割り当て
        assignLogic.assignRoom(vInfo.villageId, vInfo.vPlayers); // 部屋割り当て
        helper.updateVillageStatus(vInfo.villageId, CDef.VillageStatus.進行中); // 村ステータス更新
        updateVillageSettingsIfNeeded(vInfo.villageId, vInfo.vPlayers, vInfo.settings); // 特殊ルール変更
        insertAbsoluteSkillMessageIfNeeded(vInfo); // 絶対人狼メッセージ
        defaultSetLogic.setDefaultVoteAndAbility(vInfo.villageId, newDay); // 投票、能力行使のデフォルト設定
        insertDummyCharaMessage(vInfo.villageId, vInfo.vPlayers, vInfo.settings); // ダミーキャラ発言
        tweetIfNeeded(vInfo.villageId, vInfo.village, vInfo.settings);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 特殊ルール変更
    private void updateVillageSettingsIfNeeded(Integer villageId, VillagePlayers vPlayers, VillageSettings settings) {
        // 狼人数が3より少ない場合、同一人狼による連続襲撃可能にする
        if (settings.isIsAvailableSameWolfAttackTrue()) {
            return;
        }
        String organize = settings.getOrganize();
        String personNumOrg = Stream.of(organize.replaceAll("\r\n", "\n").split("\n"))
                .filter(org -> org.length() == vPlayers.list.size())
                .findFirst()
                .get();
        Map<Skill, Integer> skillPersonNum = SkillUtil.createSkillPersonNum(personNumOrg);
        Integer wolfNum = CDef.Skill.listOfHasAttackAbility().stream().mapToInt(skill -> skillPersonNum.get(skill)).sum();
        if (wolfNum < 3) {
            helper.updateVillageSettingsSameWolfAttackTrue(villageId);
            messageLogic.saveIgnoreError(
                    MessageEntity.publicSystemBuilder(villageId, 1).content("人狼の人数が3名より少ないため、同一人狼による連続襲撃を「可能」に変更します。").build());
        }
    }

    private void insertDummyCharaMessage(Integer villageId, VillagePlayers vPlayers, VillageSettings settings) {
        VillagePlayer dummyChara = vPlayers.findByCharaId(settings.getDummyCharaId());
        String message = dummyChara.getChara().get().getDefaultFirstdayMessage();
        if (StringUtils.isEmpty(message)) {
            return;
        }
        messageLogic.saveIgnoreError(new MessageEntity.Builder(villageId, 1) //
                .messageType(CDef.MessageType.通常発言)
                .content(message)
                .villagePlayer(dummyChara)
                .isConvertDisable(true)
                .faceType(CDef.FaceType.通常)
                .build());
    }

    private void insertAbsoluteSkillMessageIfNeeded(VillageInfo vInfo) {
        // 役職が反映されていないので取得しなおす
        Village village = villageService.selectVillage(vInfo.villageId, false, false);
        VillageInfo villageInfo = helper.convertToVillageInfo(village);

        // 絶対人狼
        VillagePlayers abusoluteWolfs = villageInfo.vPlayers.filterBySkill(CDef.Skill.絶対人狼);
        if (!abusoluteWolfs.list.isEmpty()) {
            String message = String.join("、", abusoluteWolfs.map(wolf -> wolf.name())) + "は絶対人狼のようだ。";
            messageLogic.insertPublicAbilityMessage(villageInfo.villageId, 1, message);
        }

        // 梟
        VillagePlayers owls = villageInfo.vPlayers.filterBySkill(CDef.Skill.梟);
        if (!owls.list.isEmpty()) {
            String message = "この村には強力な聴力を持つ者がいるようだ。";
            messageLogic.insertPublicAbilityMessage(villageInfo.villageId, 1, message);
        }
    }

    private void tweetIfNeeded(Integer villageId, Village village, VillageSettings settings) {
        if (StringUtils.isNotEmpty(settings.getJoinPassword())) {
            return; // 身内村は通知しない
        }
        twitterLogic.tweet(String.format("%sが開始されました。", village.getVillageDisplayName()), villageId);
    }
}
