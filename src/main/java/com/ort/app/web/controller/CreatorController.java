package com.ort.app.web.controller;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.logic.message.MessageEntity;
import com.ort.app.web.controller.assist.VillageAssist;
import com.ort.app.web.controller.assist.impl.VillageForms;
import com.ort.app.web.form.VillageKickForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.form.validator.CreatorSayFormValidator;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.RandomKeyword;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
public class CreatorController {

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
    private RandomKeywordBhv randomKeywordBhv;
    @Autowired
    private VillageParticipateLogic villageParticipateLogic;
    @Autowired
    private CreatorSayFormValidator villageSayFormValidator;
    @Autowired
    private VillageAssist villageAssist;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;

    @InitBinder("creatorSayForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(villageSayFormValidator);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 村建て機能：強制退村
    @PostMapping("/village/{villageId}/kick")
    private String kick(
            @PathVariable Integer villageId,
            VillageKickForm kickForm,
            UriComponentsBuilder builder
    ) {
        // 村建てでなければNG
        String redirectUrl = "redirect:" + builder.path("/village/" + villageId).build().toUri().toString() + "#bottom";
        if (!isCreator(villageId)) {
            return redirectUrl;
        }

        Integer charaId = kickForm.getCharaId();
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
        OptionalEntity<VillagePlayer> optVPlayer = villagePlayerBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setCharaId_Equal(charaId);
            cb.query().setIsGone_Equal_False();
            cb.query().setCharaId_NotEqual(village.getVillageSettingsAsOne().get().getDummyCharaId());
        });
        // いなかった
        if (!optVPlayer.isPresent()) {
            return redirectUrl;
        }
        // 退村させる
        villageParticipateLogic.leave(optVPlayer.get());
        return redirectUrl;
    }

    // 村建て機能：村建て発言確認画面へ
    @PostMapping("/village/{villageId}/creator-say-confirm")
    private String confirm(
            @PathVariable Integer villageId,
            @Validated @ModelAttribute("creatorSayForm") VillageSayForm creatorSayForm,
            BindingResult result,
            Model model,
            UriComponentsBuilder builder
    ) {
        if (result.hasErrors()) {
            String returnView = villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
            model.addAttribute("creatorSayForm", creatorSayForm);
            return returnView;
        }
        // 村建てでなければNG
        if (!isCreator(villageId)) {
            return "redirect:" + builder.path("/village/" + villageId).build().toUri().toString() + "#bottom";
        }
        model.addAttribute("villageId", villageId);
        Village village = selectVillage(villageId);
        model.addAttribute("villageName", village.getVillageDisplayName());
        model.addAttribute("randomKeywords", String.join(",",
                randomKeywordBhv.selectList(cb -> {
                }).stream().map(RandomKeyword::getKeyword).collect(Collectors.toList())));

        return "creator-say-confirm";
    }

    // 村建て機能：村建て発言
    @PostMapping("/village/{villageId}/creator-say")
    private String creatorSay(
            @PathVariable Integer villageId,
            @Validated @ModelAttribute("creatorSayForm") VillageSayForm creatorSayForm,
            BindingResult result,
            Model model,
            UriComponentsBuilder builder
    ) {
        if (result.hasErrors()) {
            String returnView = villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
            model.addAttribute("creatorSayForm", creatorSayForm);
            return returnView;
        }
        // 村建てでなければNG
        String redirectUrl = "redirect:" + builder.path("/village/" + villageId).build().toUri().toString() + "#bottom";
        if (!isCreator(villageId)) {
            return redirectUrl;
        }
        int day = villageService.selectLatestDay(villageId);
        messageLogic.saveIgnoreError(new MessageEntity.Builder(villageId, day) //
                .messageType(CDef.MessageType.村建て発言)
                .content(creatorSayForm.getMessage())
                .isConvertDisable(BooleanUtils.isTrue(creatorSayForm.getIsConvertDisable()))
                .build());

        return redirectUrl;
    }

    // 村建て機能：廃村
    @PostMapping("/village/{villageId}/cancel")
    private String cancel(
            @PathVariable Integer villageId,
            Model model,
            UriComponentsBuilder builder
    ) {
        // 村建てでなければNG
        String redirectUrl = "redirect:" + builder.path("/village/" + villageId).build().toUri().toString() + "#bottom";
        if (!isCreator(villageId)) {
            return redirectUrl;
        }
        // プロローグでなければNG
        Village village = selectVillage(villageId);
        if (!village.isVillageStatusCode募集中() && !village.isVillageStatusCode開始待ち()) {
            return redirectUrl;
        }
        // 廃村にする
        updateVillageCancel(villageId);
        return redirectUrl;
    }

    // 村建て機能：エピローグ延長
    @PostMapping("/village/{villageId}/extend-epilogue")
    private String extend(
            @PathVariable Integer villageId,
            Model model,
            UriComponentsBuilder builder
    ) {
        // 村建てでなければNG
        String redirectUrl = "redirect:" + builder.path("/village/" + villageId).build().toUri().toString() + "#bottom";
        if (!isCreator(villageId)) {
            return redirectUrl;
        }
        // エピローグでなければNG
        Village village = selectVillage(villageId);
        if (!village.isVillageStatusCodeエピローグ()) {
            return redirectUrl;
        }
        // エピローグを1日延長する
        extendEpilogue(villageId);
        return redirectUrl;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private Village selectVillage(Integer villageId) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne().withCharaGroup();
            cb.query().setVillageId_Equal(villageId);
        });
        return village;
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void updateVillageCancel(Integer villageId) {
        Village entity = new Village();
        entity.setVillageId(villageId);
        entity.setVillageStatusCode_廃村();
        villageBhv.update(entity);
    }

    private void updateVillageDayExtend(Integer villageId, int day, LocalDateTime dayChangeDatetime) {
        VillageDay entity = new VillageDay();
        entity.setVillageId(villageId);
        entity.setDay(day);
        entity.setDaychangeDatetime(dayChangeDatetime);
        villageDayBhv.update(entity);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isCreator(Integer villageId) {
        // 村建てでなければNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            return false;
        }
        OptionalEntity<Village> optVillage = villageBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setCreatePlayerName_Equal(userInfo.getUsername());
        });
        if (!optVillage.isPresent()) {
            return false;
        }
        return true;
    }

    private void extendEpilogue(Integer villageId) {
        // 最新の村日付を取得
        VillageDay villageDay = villageDayBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get(0);
        // 最新＝エピローグの日付更新時間を1日延長
        updateVillageDayExtend(villageId, villageDay.getDay(), villageDay.getDaychangeDatetime().plusDays(1L));
    }
}
