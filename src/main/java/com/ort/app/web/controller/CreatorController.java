package com.ort.app.web.controller;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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
    // ??????????????????????????????
    @PostMapping("/village/{villageId}/kick")
    private String kick(@PathVariable Integer villageId, VillageKickForm kickForm) {
        // ????????????????????????NG
        if (!isCreator(villageId)) {
            return "redirect:/village/" + villageId;
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
        // ???????????????
        if (!optVPlayer.isPresent()) {
            return "redirect:/village/" + villageId;
        }
        // ???????????????
        villageParticipateLogic.leave(optVPlayer.get());

        return "redirect:/village/" + villageId;
    }

    // ????????????????????????????????????????????????
    @PostMapping("/village/{villageId}/creator-say-confirm")
    private String confirm(@PathVariable Integer villageId, @Validated @ModelAttribute("creatorSayForm") VillageSayForm creatorSayForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            String returnView = villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
            model.addAttribute("creatorSayForm", creatorSayForm);
            return returnView;
        }
        // ????????????????????????NG
        if (!isCreator(villageId)) {
            return "redirect:/village/" + villageId;
        }
        model.addAttribute("villageId", villageId);
        Village village = selectVillage(villageId);
        model.addAttribute("villageName", village.getVillageDisplayName());
        model.addAttribute("randomKeywords", String.join(",",
                randomKeywordBhv.selectList(cb -> {}).stream().map(RandomKeyword::getKeyword).collect(Collectors.toList())));

        return "creator-say-confirm";
    }

    // ?????????????????????????????????
    @PostMapping("/village/{villageId}/creator-say")
    private String creatorSay(@PathVariable Integer villageId, @Validated @ModelAttribute("creatorSayForm") VillageSayForm creatorSayForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            String returnView = villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
            model.addAttribute("creatorSayForm", creatorSayForm);
            return returnView;
        }
        // ????????????????????????NG
        if (!isCreator(villageId)) {
            return "redirect:/village/" + villageId;
        }
        int day = villageService.selectLatestDay(villageId);
        messageLogic.saveIgnoreError(new MessageEntity.Builder(villageId, day) //
                .messageType(CDef.MessageType.???????????????)
                .content(creatorSayForm.getMessage())
                .isConvertDisable(BooleanUtils.isTrue(creatorSayForm.getIsConvertDisable()))
                .build());

        return "redirect:/village/" + villageId + "#bottom";
    }

    // ????????????????????????
    @PostMapping("/village/{villageId}/cancel")
    private String cancel(@PathVariable Integer villageId, Model model) {
        // ????????????????????????NG
        if (!isCreator(villageId)) {
            return "redirect:/village/" + villageId;
        }
        // ??????????????????????????????NG
        Village village = selectVillage(villageId);
        if (!village.isVillageStatusCode?????????() && !village.isVillageStatusCode????????????()) {
            return "redirect:/village/" + villageId;
        }
        // ???????????????
        updateVillageCancel(villageId);

        return "redirect:/village/" + villageId + "#bottom";
    }

    // ???????????????????????????????????????
    @PostMapping("/village/{villageId}/extend-epilogue")
    private String extend(@PathVariable Integer villageId, Model model) {
        // ????????????????????????NG
        if (!isCreator(villageId)) {
            return "redirect:/village/" + villageId;
        }
        // ??????????????????????????????NG
        Village village = selectVillage(villageId);
        if (!village.isVillageStatusCode???????????????()) {
            return "redirect:/village/" + villageId;
        }
        // ??????????????????1???????????????
        extendEpilogue(villageId);

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
        return village;
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void updateVillageCancel(Integer villageId) {
        Village entity = new Village();
        entity.setVillageId(villageId);
        entity.setVillageStatusCode_??????();
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
        // ????????????????????????NG
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
        // ???????????????????????????
        VillageDay villageDay = villageDayBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get(0);
        // ????????????????????????????????????????????????1?????????
        updateVillageDayExtend(villageId, villageDay.getDay(), villageDay.getDaychangeDatetime().plusDays(1L));
    }
}
