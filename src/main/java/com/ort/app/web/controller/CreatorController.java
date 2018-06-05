package com.ort.app.web.controller;

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

import com.ort.app.web.controller.assist.VillageAssist;
import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.controller.logic.VillageParticipateLogic;
import com.ort.app.web.form.VillageKickForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.form.validator.CreatorSayFormValidator;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
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
    private VillageParticipateLogic villageParticipateLogic;
    @Autowired
    private CreatorSayFormValidator villageSayFormValidator;
    @Autowired
    private VillageAssist villageAssist;
    @Autowired
    private MessageLogic messageLogic;

    @InitBinder("creatorSayForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(villageSayFormValidator);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 村建て機能：強制退村
    @PostMapping("/village/{villageId}/kick")
    private String kick(@PathVariable Integer villageId, VillageKickForm kickForm) {
        // 村建てでなければNG
        if (!isCreator(villageId)) {
            return "redirect:/village/" + villageId;
        }

        Integer charaId = kickForm.getCharaId();

        OptionalEntity<VillagePlayer> optVPlayer = villagePlayerBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setCharaId_Equal(charaId);
            cb.query().setIsGone_Equal_False();
            cb.query().queryChara().setIsDummy_Equal_False();
        });
        // いなかった
        if (!optVPlayer.isPresent()) {
            return "redirect:/village/" + villageId;
        }
        // 退村させる
        villageParticipateLogic.leave(optVPlayer.get());

        return "redirect:/village/" + villageId;
    }

    // 村建て機能：村建て発言確認画面へ
    @PostMapping("/village/{villageId}/creator-say-confirm")
    private String confirm(@PathVariable Integer villageId, @Validated @ModelAttribute("creatorSayForm") VillageSayForm creatorSayForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            String returnView = villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
            model.addAttribute("creatorSayForm", creatorSayForm);
            return returnView;
        }
        // 村建てでなければNG
        if (!isCreator(villageId)) {
            return "redirect:/village/" + villageId;
        }
        model.addAttribute("villageId", villageId);
        Village village = selectVillage(villageId);
        model.addAttribute("villageName", village.getVillageDisplayName());

        return "creator-say-confirm";
    }

    // 村建て機能：村建て発言
    @PostMapping("/village/{villageId}/creator-say")
    private String creatorSay(@PathVariable Integer villageId, @Validated @ModelAttribute("creatorSayForm") VillageSayForm creatorSayForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            String returnView = villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
            model.addAttribute("creatorSayForm", creatorSayForm);
            return returnView;
        }
        // 村建てでなければNG
        if (!isCreator(villageId)) {
            return "redirect:/village/" + villageId;
        }
        int day = villageAssist.selectLatestDay(villageId);
        messageLogic.insertMessage(villageId, day, CDef.MessageType.村建て発言, creatorSayForm.getMessage());

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
}
