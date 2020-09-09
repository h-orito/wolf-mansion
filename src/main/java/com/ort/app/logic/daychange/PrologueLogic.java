package com.ort.app.logic.daychange;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.logic.AssignLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.TwitterLogic;
import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.util.SkillUtil;
import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.util.WerewolfMansionDateUtil;

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

    // プロローグで長時間接続していない人がいたら退村させる
    public void leaveVillageIfNeeded(VillageInfo vInfo) {
        if (vInfo.day != 0) {
            return;
        }

        // 24時間アクセスしていなかったら村を出る
        LocalDateTime yesterday = WerewolfMansionDateUtil.currentLocalDateTime().minusDays(1L);

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
            messageLogic.insertMessageIgnoreError(vInfo.villageId, vInfo.day, CDef.MessageType.公開システムメッセージ, "まだ村人たちは揃っていないようだ。"); // 延長メッセージ登録
        } else {
            // 一人も参加していなかったら廃村
            helper.updateVillageStatus(vInfo.villageId, CDef.VillageStatus.廃村);
        }
    }

    // 村を開始させる
    public void startVillage(VillageInfo vInfo, int newDay) {
        messageLogic.insertMessageIgnoreError(vInfo.villageId, 1, CDef.MessageType.公開システムメッセージ,
                messageSource.getMessage("village.start.message.day1", null, Locale.JAPAN));
        assignLogic.assignSkill(vInfo.villageId, vInfo.vPlayers, vInfo.settings); // 役職割り当て
        assignLogic.assignRoom(vInfo.villageId, vInfo.vPlayers); // 部屋割り当て
        helper.updateVillageStatus(vInfo.villageId, CDef.VillageStatus.進行中); // 村ステータス更新
        updateVillageSettingsIfNeeded(vInfo.villageId, vInfo.vPlayers, vInfo.settings); // 特殊ルール変更
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
            messageLogic.insertMessageIgnoreError(villageId, 1, CDef.MessageType.公開システムメッセージ, "人狼の人数が3名より少ないため、同一人狼による連続襲撃を「可能」に変更します。");
        }
    }

    private void insertDummyCharaMessage(Integer villageId, VillagePlayers vPlayers, VillageSettings settings) {
        VillagePlayer dummyChara = vPlayers.findByCharaId(settings.getDummyCharaId());
        String message = dummyChara.getChara().get().getDefaultFirstdayMessage();
        if (StringUtils.isEmpty(message)) {
            return;
        }
        try {
            messageLogic.insertMessage(villageId, 1, CDef.MessageType.通常発言, message, dummyChara.getVillagePlayerId(), true,
                    CDef.FaceType.通常);
        } catch (WerewolfMansionBusinessException e) {}
    }

    private void tweetIfNeeded(Integer villageId, Village village, VillageSettings settings) {
        if (StringUtils.isNotEmpty(settings.getJoinPassword())) {
            return; // 身内村は通知しない
        }
        twitterLogic.tweet(String.format("%sが開始されました。", village.getVillageDisplayName()), villageId);
    }
}
