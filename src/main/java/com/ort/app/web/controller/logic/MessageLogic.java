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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.util.CharaUtil;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.RandomContent;
import com.ort.dbflute.exentity.RandomKeyword;
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
    private static final String SLACK_URL = "https://hooks.slack.com/services/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";
    private static final Logger logger = LoggerFactory.getLogger(MessageLogic.class);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageBhv messageBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RandomKeywordBhv randomKeywordBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content, Integer villagePlayerId,
            Integer targetVillagePlayerId, Integer playerId, boolean isConvertDisable) throws WerewolfMansionBusinessException {
        Message message = new Message();
        message.setVillageId(villageId);
        message.setDay(day);
        message.setVillagePlayerId(villagePlayerId);
        message.setToVillagePlayerId(targetVillagePlayerId);
        message.setPlayerId(playerId);
        message.setMessageTypeCodeAsMessageType(messageType);
        message.setMessageContent(content);
        message.setMessageDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        message.setIsConvertDisable(isConvertDisable);
        for (int i = 0; i < 3; i++) {
            try {
                // 採番で被ることがあるため、insert失敗しても合計3回までやりなおす
                message.setMessageNumber(selectNextMessageNumber(villageId, messageType));
                messageBhv.insert(message);
                return;
            } catch (Exception e) {
                // 何もせずやり直す
            }
        }
        // ここにきたら発言失敗している
        throw new WerewolfMansionBusinessException("混み合っているため発言に失敗しました。再度発言してください。");
    }

    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content, Integer villagePlayerId,
            Integer targetVillagePlayerId, boolean isConvertDisable) throws WerewolfMansionBusinessException {
        // ランダム機能などメッセージ関数を置換して登録
        String message = content;
        if (!isConvertDisable) {
            ListResultBean<VillagePlayer> vPlayerList = selectVPlayerList(villageId);
            message = replaceMessage(content, vPlayerList);
        }
        insertMessage(villageId, day, messageType, message, villagePlayerId, targetVillagePlayerId, null, isConvertDisable);
        // 特定の文字列が含まれていたらslack投稿
        postToSlackIfNeeded(villageId, day, message);
    }

    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content, Integer villagePlayerId,
            boolean isConvertDisable) throws WerewolfMansionBusinessException {
        insertMessage(villageId, day, messageType, content, villagePlayerId, null, isConvertDisable);
    }

    public void insertMessage(Integer villageId, int day, CDef.MessageType messageType, String content, boolean isConvertDisable)
            throws WerewolfMansionBusinessException {
        insertMessage(villageId, day, messageType, content, null, isConvertDisable);
    }

    // 次の発言番号を返す
    private int selectNextMessageNumber(Integer villageId, CDef.MessageType messageType) {
        Integer maxNessageNumber = messageBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnMessageNumber();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setMessageTypeCode_Equal_AsMessageType(messageType);
        }).orElse(0);
        return maxNessageNumber + 1;
    }

    // 能力セットメッセージ登録
    public void insertAbilityMessage(Integer villageId, int day, Integer charaId, Integer targetCharaId,
            List<VillagePlayer> villagePlayerList, String footstep, boolean isDefault) {
        String message = makeAbilitySetMessage(charaId, targetCharaId, villagePlayerList, footstep, isDefault);
        try {
            insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ, message, true);
        } catch (WerewolfMansionBusinessException e) {
            // ここでは被らないはずなので何もしない
        }
    }

    // 足音セットメッセージ登録
    public void insertFootstepMessage(Integer villageId, int day, Integer charaId, List<VillagePlayer> villagePlayerList, String footstep,
            boolean isDefault) {
        String message = makeFootstepSetMessage(charaId, villagePlayerList, footstep, isDefault);
        try {
            insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ, message, true);
        } catch (WerewolfMansionBusinessException e) {
            // ここでは被らないはずなので何もしない
        }
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
        Skill skill = myself.getSkillCodeAsSkill();
        String myChara = CharaUtil.makeCharaName(myself);
        // 対象
        VillagePlayer target = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(targetCharaId)).findFirst().get();
        String targetChara = CharaUtil.makeCharaName(target);
        if (skill == CDef.Skill.人狼) {
            return messageSource.getMessage("ability.werewolf.message",
                    new String[] { myChara, targetChara, footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        } else if (SkillUtil.hasDivineAbility(skill)) {
            return messageSource.getMessage("ability.seer.message",
                    new String[] { myChara, targetChara, footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        } else if (skill == CDef.Skill.狩人) {
            return messageSource.getMessage("ability.hunter.message",
                    new String[] { myChara, targetChara, footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        }
        return null;
    }

    private String makeFootstepSetMessage(Integer charaId, List<VillagePlayer> villagePlayerList, String footstep, boolean isDefault) {
        // キャラ名
        VillagePlayer myself = villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().get();
        String myChara = CharaUtil.makeCharaName(myself);
        return messageSource.getMessage("ability.foxmadman.message", new String[] { myChara, footstep, isDefault ? "（自動設定）" : "" },
                Locale.JAPAN);
    }

    private String replaceMessage(String message, List<VillagePlayer> vPlayerList) {
        List<VillagePlayer> livingPlayerList =
                vPlayerList.stream().filter(vp -> vp.isIsSpectatorFalse() && vp.isIsDeadFalse()).collect(Collectors.toList());
        ListResultBean<RandomKeyword> randomKeywordList = randomKeywordBhv.selectList(cb -> {});
        randomKeywordBhv.loadRandomContent(randomKeywordList, contentCB -> {});

        return String.join("\n", Stream.of(message.replace("\r\n", "\n").split("\n")).map(mes -> {
            String replacedMessage = String.join("]]", Stream.of(mes.split("\\]\\]")).map(m -> {
                String rm = replaceDiceMessage(m);
                rm = replaceFortuneMessage(rm);
                rm = replaceOrMessage(rm);
                rm = replaceWhoMessage(rm, livingPlayerList);
                rm = replaceAllwhoMessage(rm, vPlayerList);
                for (int i = 0; i < randomKeywordList.size(); i++) {
                    RandomKeyword keyword = randomKeywordList.get(i);
                    rm = replaceUserDefinedRandomMessage(rm, keyword);
                }
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

    // ユーザ定義キーワードの変換
    private String replaceUserDefinedRandomMessage(String mes, RandomKeyword keyword) {
        String replacedMessage = mes;
        String regex = "\\[\\[" + keyword.getKeyword() + "$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mes);
        if (matcher.find()) {
            List<RandomContent> contentList = keyword.getRandomContentList();
            Collections.shuffle(contentList);
            replacedMessage = mes.replaceAll(regex, contentList.get(0).getRandomMessage() + matcher.group(0));
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

    // 特定の文字列が含まれていたらslack投稿
    private void postToSlackIfNeeded(Integer villageId, int day, String message) {
        if (message.contains("@国主") || message.contains("＠国主")) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                // TODO json変換ちゃんとやる
                String request = "{\"text\": \"" + "<@U8Z40QDUM> " + String.format("vid: %d, message: \n%s", villageId, message) + "\"}";
                HttpHeaders formHeaders = new HttpHeaders();
                formHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity<String> formEntity = new HttpEntity<String>(request, formHeaders);
                restTemplate.exchange(SLACK_URL, HttpMethod.POST, formEntity, String.class);
            } catch (Exception e) {
                logger.error("slack投稿に失敗しました", e);
            }
        }
    }
}
