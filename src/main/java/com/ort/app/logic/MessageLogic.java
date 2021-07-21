package com.ort.app.logic;

import com.ort.app.logic.message.MessageEntity;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.MessageSendtoBhv;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.*;
import com.ort.fw.util.WerewolfMansionDateUtil;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private static final Logger logger = LoggerFactory.getLogger(MessageLogic.class);

    private static final Pattern normalAnchorPattern = Pattern.compile("^(?![\\+=\\?@\\-\\*a])(\\d{1,5})");
    private static final Pattern graveAnchorPattern = Pattern.compile("\\+(\\d{1,5})");
    private static final Pattern masonAnchorPattern = Pattern.compile("=(\\d{1,5})");
    private static final Pattern loverAnchorPattern = Pattern.compile("\\?(\\d{1,5})");
    private static final Pattern spectatePattern = Pattern.compile("@(\\d{1,5})");
    private static final Pattern monologuePattern = Pattern.compile("\\-(\\d{1,5})");
    private static final Pattern whisperPattern = Pattern.compile("\\*(\\d{1,5})");
    private static final Pattern actionPattern = Pattern.compile("a(\\d{1,5})");
    private static final Map<Pattern, CDef.MessageType> patternMessageTypeMap;

    static {
        Map<Pattern, CDef.MessageType> map = new HashMap<Pattern, CDef.MessageType>();
        map.put(normalAnchorPattern, CDef.MessageType.通常発言);
        map.put(graveAnchorPattern, CDef.MessageType.死者の呻き);
        map.put(masonAnchorPattern, CDef.MessageType.共鳴発言);
        map.put(loverAnchorPattern, CDef.MessageType.恋人発言);
        map.put(spectatePattern, CDef.MessageType.見学発言);
        map.put(monologuePattern, CDef.MessageType.独り言);
        map.put(whisperPattern, CDef.MessageType.人狼の囁き);
        map.put(actionPattern, CDef.MessageType.アクション);
        patternMessageTypeMap = map;
    }

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageBhv messageBhv;
    @Autowired
    private MessageSendtoBhv messageSendtoBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private RandomKeywordBhv randomKeywordBhv;

    @Value("${slack.webhook-url:}")
    private String slackWebhookUrl;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 秘話など、相手がいる可能性があるのはこれを使う
    public void save(MessageEntity entity) throws WerewolfMansionBusinessException {
        // ランダム機能などメッセージ関数を置換して登録
        String message = entity.content;
        if (!entity.isConvertDisable) {
            ListResultBean<VillagePlayer> vPlayerList = selectVPlayerList(entity.villageId);
            message = replaceMessage(entity.content, vPlayerList);
        }
        insertMessage(entity.replaceContent(message));

        // 特定の文字列が含まれていたらslack投稿
        postToSlackIfNeeded(entity.villageId, entity.day, message);
    }

    // 日付更新など、被らないことがわかっている場合に使う
    public void saveIgnoreError(MessageEntity entity) {
        try {
            save(entity);
        } catch (WerewolfMansionBusinessException e) {}
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
    public void insertAbilityMessage(Integer villageId, int day, String message) {
        MessageEntity entity = MessageEntity.privateSystemBuilder(villageId, day) //
                .content(message) //
                .build();
        saveIgnoreError(entity);
    }

    // 能力セットメッセージ登録
    public void insertPublicAbilityMessage(Integer villageId, int day, String message) {
        MessageEntity entity = MessageEntity.publicSystemBuilder(villageId, day) //
                .content(message) //
                .build();
        saveIgnoreError(entity);
    }

    // 当日の自分の発言取得（発言制限用）
    public List<Message> selectDayPersonMessage(Integer villageId, int day, Integer vPlayerId) {
        ListResultBean<Message> messageList = messageBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setVillagePlayerId_Equal(vPlayerId);
            cb.query().setDay_Equal(day);
            cb.query()
                    .setMessageTypeCode_InScope_AsMessageType(Arrays.asList( //
                            CDef.MessageType.通常発言, //
                            CDef.MessageType.人狼の囁き, //
                            CDef.MessageType.共鳴発言, //
                            CDef.MessageType.恋人発言, //
                            CDef.MessageType.アクション));
        });
        // 3日目以降は襲撃メッセージがあるので、それを除く
        if (day < 3) {
            return messageList;
        }
        OptionalEntity<Message> optFirstAttackMessage = messageBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setMessageTypeCode_Equal_人狼の囁き();
            cb.query().addOrderBy_RegisterDatetime_Asc();
            cb.fetchFirst(1);
        });
        if (!optFirstAttackMessage.isPresent()) {
            return messageList;
        }
        return messageList.stream()
                .filter(m -> m.getMessageId().intValue() != optFirstAttackMessage.get().getMessageId().intValue())
                .collect(Collectors.toList());
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
    private void insertMessage(MessageEntity entity) throws WerewolfMansionBusinessException {
        Message message = new Message();
        message.setVillageId(entity.villageId);
        message.setDay(entity.day);
        Optional.ofNullable(entity.villagePlayer).ifPresent(villagePlayer -> {
            message.setVillagePlayerId(villagePlayer.getVillagePlayerId());
            message.setCharaName(villagePlayer.getCharaName());
            message.setCharaShortName(villagePlayer.getCharaShortName());
        });
        Optional.ofNullable(entity.targetVillagePlayer).ifPresent(targetVillagePlayer -> {
            message.setToVillagePlayerId(targetVillagePlayer.getVillagePlayerId());
            message.setToCharaName(targetVillagePlayer.getCharaName());
            message.setToCharaShortName(targetVillagePlayer.getCharaShortName());
        });
        message.setPlayerId(null);
        message.setMessageTypeCodeAsMessageType(entity.messageType);
        message.setMessageContent(entity.content);
        message.setMessageDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        message.setIsConvertDisable(entity.isConvertDisable);
        Optional.ofNullable(entity.faceType).ifPresent(faceType -> {
            message.setFaceTypeCodeAsFaceType(faceType);
        });
        for (int i = 0; i < 3; i++) {
            try {
                // 採番で被ることがあるため、insert失敗しても合計3回までやりなおす
                message.setMessageNumber(selectNextMessageNumber(entity.villageId, entity.messageType));
                messageBhv.insert(message);
                // アンカー先を保存しておく
                saveMessageSendto(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                // 何もせずやり直す
            }
        }
        // ここにきたら発言失敗している
        throw new WerewolfMansionBusinessException("混み合っているため発言に失敗しました。再度発言してください。");
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
                restTemplate.exchange(slackWebhookUrl, HttpMethod.POST, formEntity, String.class);
            } catch (Exception e) {
                logger.error("slack投稿に失敗しました", e);
            }
        }
    }

    // アンカー先を保存
    private void saveMessageSendto(Message message) {
        Stream.of(message.getMessageContent().split("\\>\\>")).forEach(str -> {
            patternMessageTypeMap.forEach((pattern, messageType) -> {
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    OptionalEntity<Message> optMessage = messageBhv.selectEntity(cb -> {
                        cb.query().setVillageId_Equal(message.getVillageId());
                        cb.query().setMessageTypeCode_Equal_AsMessageType(messageType);
                        cb.query().setMessageNumber_Equal(number);
                    });
                    if (!optMessage.isPresent() || optMessage.get().getVillagePlayerId() == null) {
                        return;
                    }
                    MessageSendto sendTo = new MessageSendto();
                    sendTo.setMessageId(message.getMessageId());
                    sendTo.setVillagePlayerId(optMessage.get().getVillagePlayerId());
                    OptionalEntity<MessageSendto> optEntity = messageSendtoBhv.selectEntity(cb -> {
                        cb.query().setMessageId_Equal(sendTo.getMessageId());
                        cb.query().setVillagePlayerId_Equal(sendTo.getVillagePlayerId());
                    });
                    if (!optEntity.isPresent()) {
                        messageSendtoBhv.insert(sendTo);
                    }
                }
            });
        });
    }
}
