package com.ort.app.web.controller.logic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Component
public class MessageLogic {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final String diceRegex = "\\[\\[(\\d{1})d(\\d{1,5})$";
    private static final Pattern dicePattern = Pattern.compile(diceRegex);
    private static final String fortuneRegex = "\\[\\[fortune$";
    private static final Pattern fortunePattern = Pattern.compile(fortuneRegex);
    private static final String orRegex = "(?!\\[\\[fortune)\\[\\[([^\\]]*or.*?)$";
    private static final Pattern orPattern = Pattern.compile(orRegex);
    private static final String whoRegex = "(?!\\[\\[allwho)(\\[\\[who)$";
    private static final Pattern whoPattern = Pattern.compile(whoRegex);
    private static final String allwhoRegex = "\\[\\[allwho$";
    private static final Pattern allwhoPattern = Pattern.compile(allwhoRegex);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageBhv messageBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private MessageSource messageSource;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content, Integer villagePlayerId,
            Integer playerId) {
        Message message = new Message();
        message.setVillageId(villageId);
        message.setDay(day);
        message.setVillagePlayerId(villagePlayerId);
        message.setPlayerId(playerId);
        message.setMessageTypeCodeAsMessageType(messageType);
        message.setMessageNumber(selectNextMessageNumber(villageId, messageType));
        message.setMessageContent(content);
        message.setMessageDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        messageBhv.insert(message);
    }

    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content, Integer villagePlayerId) {
        // ランダム機能などメッセージ関数を置換して登録
        ListResultBean<VillagePlayer> vPlayerList = selectVPlayerList(villageId);
        String message = replaceMessage(content, vPlayerList);
        insertMessage(villageId, day, messageType, message, villagePlayerId, null);
    }

    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content) {
        insertMessage(villageId, day, messageType, content, null, null);
    }

    /**
     * 表ロックを取りつつ次の番号を返す
     * @param villageId
     * @param messageType
     * @return
     */
    public int selectNextMessageNumber(Integer villageId, CDef.MessageType messageType) {
        Integer maxNessageNumber = messageBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnMessageNumber();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setMessageTypeCode_Equal_AsMessageType(messageType);
            cb.lockForUpdate();
        }).orElse(0);
        return maxNessageNumber + 1;
    }

    // 能力セットメッセージ登録
    public void insertAbilityMessage(Integer villageId, int day, Integer charaId, Integer targetCharaId,
            List<VillagePlayer> villagePlayerList, String footstep, boolean isDefault) {
        String message = makeAbilitySetMessage(charaId, targetCharaId, villagePlayerList, footstep, isDefault);
        insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ, message);
    }

    // 足音セットメッセージ登録
    public void insertFootstepMessage(Integer villageId, int day, Integer charaId, List<VillagePlayer> villagePlayerList, String footstep,
            boolean isDefault) {
        String message = makeFootstepSetMessage(charaId, villagePlayerList, footstep, isDefault);
        insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ, message);
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<VillagePlayer> selectVPlayerList(Integer villageId) {
        ListResultBean<VillagePlayer> vPlayerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.setupSelect_DeadReason();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().addOrderBy_DeadDay_Asc();
        });
        return vPlayerList;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String makeAbilitySetMessage(Integer charaId, Integer targetCharaId, List<VillagePlayer> villagePlayerList, String footstep,
            boolean isDefault) {
        // 自分
        VillagePlayer myself = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get();
        // 自分の役職
        Skill skill = myself.getSkillCodeAsSkill();
        // キャラ名
        String charaName = myself.getChara().get().getCharaName();
        // 対象キャラ名
        String targetCharaName = villagePlayerList.stream()
                .filter(vp -> vp.getCharaId().equals(targetCharaId))
                .findFirst()
                .get()
                .getChara()
                .get()
                .getCharaName();
        if (skill == CDef.Skill.人狼) {
            return messageSource.getMessage("ability.werewolf.message",
                    new String[] { charaName, targetCharaName, footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        } else if (SkillUtil.hasDivineAbility(skill)) {
            return messageSource.getMessage("ability.seer.message",
                    new String[] { charaName, targetCharaName, footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        } else if (skill == CDef.Skill.狩人) {
            return messageSource.getMessage("ability.hunter.message",
                    new String[] { charaName, targetCharaName, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        }
        return null;
    }

    private String makeFootstepSetMessage(Integer charaId, List<VillagePlayer> villagePlayerList, String footstep, boolean isDefault) {
        // キャラ名
        String charaName =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get().getChara().get().getCharaName();
        return messageSource.getMessage("ability.foxmadman.message", new String[] { charaName, footstep, isDefault ? "（自動設定）" : "" },
                Locale.JAPAN);
    }

    private String replaceMessage(String message, List<VillagePlayer> vPlayerList) {
        List<VillagePlayer> livingPlayerList =
                vPlayerList.stream().filter(vp -> vp.isIsSpectatorFalse() && vp.isIsDeadFalse()).collect(Collectors.toList());
        return String.join("\n", Stream.of(message.replace("\r\n", "\n").split("\n")).map(mes -> {
            String replacedMessage = String.join("]]", Stream.of(mes.split("\\]\\]")).map(m -> {
                String rm = replaceDiceMessage(m);
                rm = replaceFortuneMessage(rm);
                rm = replaceOrMessage(rm);
                rm = replaceWhoMessage(rm, livingPlayerList);
                rm = replaceAllwhoMessage(rm, vPlayerList);
                return rm;
            }).collect(Collectors.toList()));
            if (mes.endsWith("]]")) {
                return replacedMessage + "]]";
            } else {
                return replacedMessage;
            }
        }).collect(Collectors.toList()));
    }

    // [[2d6]]の変換
    private String replaceDiceMessage(String mes) {
        String replacedMessage = mes;
        Matcher diceMatcher = dicePattern.matcher(replacedMessage);
        if (diceMatcher.find()) {
            //Randomクラスのインスタンス化
            Random rnd = new Random();
            int diceNum = Integer.parseInt(diceMatcher.group(1));
            int diceSize = Integer.parseInt(diceMatcher.group(2));
            String diceStr = "";
            for (int i = 0; i < diceNum; i++) {
                int num = diceSize <= 0 ? 0 : rnd.nextInt(diceSize) + 1;
                diceStr += "(" + num + ")";
            }
            replacedMessage = mes.replaceFirst(diceRegex, diceStr + diceMatcher.group(0));
        }
        return replacedMessage;
    }

    // [[fortune]]の変換
    private String replaceFortuneMessage(String mes) {
        String replacedMessage = mes;
        Matcher fortuneMatcher = fortunePattern.matcher(mes);
        if (fortuneMatcher.find()) {
            //Randomクラスのインスタンス化
            Random rnd = new Random();
            replacedMessage = mes.replaceAll(fortuneRegex, rnd.nextInt(101) + fortuneMatcher.group(0));
        }
        return replacedMessage;
    }

    // [[AorBorC]]の変換
    private String replaceOrMessage(String mes) {
        String replacedMessage = mes;
        Matcher orMatcher = orPattern.matcher(mes);
        if (orMatcher.find()) {
            String matchString = orMatcher.group(1);
            List<String> choiceList = Arrays.asList(matchString.split("or"));
            Collections.shuffle(choiceList);
            replacedMessage = mes.replaceAll(orRegex, choiceList.get(0) + orMatcher.group(0));
        }
        return replacedMessage;
    }

    // [[who]]の変換
    private String replaceWhoMessage(String mes, List<VillagePlayer> vPlayerList) {
        String replacedMessage = mes;
        Matcher whoMatcher = whoPattern.matcher(mes);
        if (whoMatcher.find()) {
            Collections.shuffle(vPlayerList);
            replacedMessage = mes.replaceAll(whoRegex, vPlayerList.get(0).getChara().get().getCharaName() + whoMatcher.group(0));
        }
        return replacedMessage;
    }

    // [[allwho]]の変換
    private String replaceAllwhoMessage(String mes, List<VillagePlayer> vPlayerList) {
        String replacedMessage = mes;
        Matcher allwhoMatcher = allwhoPattern.matcher(mes);
        if (allwhoMatcher.find()) {
            Collections.shuffle(vPlayerList);
            replacedMessage = mes.replaceAll(allwhoRegex, vPlayerList.get(0).getChara().get().getCharaName() + allwhoMatcher.group(0));
        }
        return replacedMessage;
    }
}
