package com.ort.app.web.controller.assist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.form.VillageSayForm;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageSayAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageAssist villageAssist;

    @Autowired
    private MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public String setConfirmModel(Integer villageId, VillageSayForm sayForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        // 発言権利がなかったらNG
        if (!isAvailableSay(villageId, userInfo, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        model.addAttribute("characterImgUrl", villagePlayer.getChara().get().getCharaImgUrl());

        model.addAttribute("villageId", villageId);
        Village village = selectVillage(villageId);
        model.addAttribute("villageName", village.getVillageDisplayName());
        // 発言確認画面へ
        return "say-confirm";
    }

    public String say(Integer villageId, VillageSayForm sayForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }
        // 発言権利がなかったらNG
        if (!isAvailableSay(villageId, userInfo, sayForm)) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, sayForm, null, null, model);
        }

        int day = villageAssist.selectLatestDay(villageId);
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("発言種別改ざん");
        }

        // ランダム機能などメッセージ関数を置換
        String message = replaceMessage(sayForm.getMessage());

        messageLogic.insertMessage(villageId, day, type, message, villagePlayer.getVillagePlayerId());
        // 最新の日付を表示
        return "redirect:/village/" + villageId + "#bottom";
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private Village selectVillage(Integer villageId) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne().withCharaGroup();
            cb.query().setVillageId_Equal(villageId);
        });
        villageBhv.loadVillagePlayer(village, villagePlayerCB -> { // 参加者一覧用
            villagePlayerCB.setupSelect_Chara();
            villagePlayerCB.setupSelect_SkillBySkillCode();
            villagePlayerCB.setupSelect_DeadReason();
            villagePlayerCB.query().setIsGone_Equal_False();
            villagePlayerCB.query().addOrderBy_DeadDay_Asc();
        });
        return village;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 発言可能かチェック
    private boolean isAvailableSay(Integer villageId, UserInfo userInfo, VillageSayForm sayForm) {
        CDef.MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("発言種別が改ざんされている");
        }
        Village village = selectVillage(villageId);
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        // 管理者は発言可能
        if (userInfo.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            return true;
        }
        switch (type) {
        case 人狼の囁き:
            return isAvailableWerewolfSay(village, villagePlayer);
        case 通常発言:
            return isAvailableNormalSay(village, villagePlayer);
        case 共鳴発言:
            return isAvailableMasonSay(village, villagePlayer);
        case 死者の呻き:
            return isAvailableGraveSay(village, villagePlayer);
        case 見学発言:
            return isAvailableSpectateSay(village, villagePlayer);
        case 独り言:
            return isAvailableMonologueSay(village);
        default:
            throw new IllegalArgumentException("発言種別が改ざんされている type: " + type);
        }
    }

    private boolean isAvailableNormalSay(Village village, VillagePlayer villagePlayer) {
        // エピローグ以外で死亡している場合は不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead()) && !village.isVillageStatusCodeエピローグ()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableWerewolfSay(Village village, VillagePlayer villagePlayer) {
        // 狼とC狂以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && skill != CDef.Skill.C国狂人) {
            return false;
        }
        // 死亡していたら不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableMasonSay(Village village, VillagePlayer villagePlayer) {
        // 共有以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.共鳴者) {
            return false;
        }
        // 死亡していたら不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableGraveSay(Village village, VillagePlayer villagePlayer) {
        // 死亡していなかったら不可
        if (!BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableSpectateSay(Village village, VillagePlayer villagePlayer) {
        // 見学していなかったら不可
        if (!BooleanUtils.isTrue(villagePlayer.getIsSpectator())) {
            return false;
        }
        return true;
    }

    private boolean isAvailableMonologueSay(Village village) {
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private String replaceMessage(String message) {
        return String.join("\n", Stream.of(message.replace("\r\n", "\n").split("\n")).map(mes -> {
            String replacedMessage = mes;
            replacedMessage = replaceDiceMessage(mes);
            replacedMessage = replaceFortuneMessage(replacedMessage);
            replacedMessage = replaceOrMessage(replacedMessage);
            return replacedMessage;
        }).collect(Collectors.toList()));
    }

    // [[2d6]]の変換
    private String replaceDiceMessage(String mes) {
        String replacedMessage = mes;
        // while (true) {
        Matcher diceMatcher = dicePattern.matcher(replacedMessage);
        if (diceMatcher.find()) {
            //Randomクラスのインスタンス化
            Random rnd = new Random();
            int hoge = diceMatcher.groupCount();
            int diceNum = Integer.parseInt(diceMatcher.group(1));
            int diceSize = Integer.parseInt(diceMatcher.group(2));
            String diceStr = "";
            for (int i = 0; i < diceNum; i++) {
                int num = diceSize <= 0 ? 0 : rnd.nextInt(diceSize) + 1;
                diceStr += "(" + num + ")";
            }
            replacedMessage = mes.replaceFirst(diceRegex, diceStr + String.format("[[%dd%d]]", diceNum, diceSize));
        } else {
            //          break;
        }
        //  }
        return replacedMessage;
    }

    private static final String diceRegex = "\\[\\[(\\d{1})d(\\d{1,5})\\]\\]";
    private static final Pattern dicePattern = Pattern.compile(diceRegex);
    private static final String fortuneRegex = "\\[\\[fortune\\]\\]";
    private static final Pattern fortunePattern = Pattern.compile(fortuneRegex);
    private static final String orRegex = "(?!\\[\\[fortune\\]\\])\\[\\[([^\\]]*or.*?)\\]\\]";
    private static final Pattern orPattern = Pattern.compile(orRegex);

    // [[fortune]]の変換
    private String replaceFortuneMessage(String mes) {
        String replacedMessage = mes;
        Matcher fortuneMatcher = fortunePattern.matcher(mes);
        if (fortuneMatcher.find()) {
            //Randomクラスのインスタンス化
            Random rnd = new Random();
            replacedMessage = mes.replaceAll(fortuneRegex, rnd.nextInt(101) + "[[fortune]]");
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
}
