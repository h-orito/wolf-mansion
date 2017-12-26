package com.ort.app.web.controller;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ort.app.web.controller.assist.VillageAssist;
import com.ort.app.web.controller.logic.DayChangeLogic;
import com.ort.app.web.controller.logic.VillageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class VillageController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageAssist assist;

    @Autowired
    private CharaBhv charaBhv;

    @Autowired
    private VillageLogic villageLogic;

    @Autowired
    DayChangeLogic dayChangeLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 村最新日付初期表示
    @GetMapping("/village/{villageId}")
    private String villageIndex(@PathVariable Integer villageId, Model model) {
        // 日付更新
        dayChangeLogic.dayChangeIfNeeded(villageId);

        // 最新の日付取得
        int day = assist.selectLatestDay(villageId);
        assist.setIndexModel(villageId, day, model);
        return "village";
    }

    // 発言取得
    @GetMapping("/village/getMessageList")
    @ResponseBody
    private VillageMessageListResultContent getDayMessageList(VillageGetMessageListForm form) {
        return assist.getMessageList(form);
    }

    // 参戦
    @PostMapping("/village/{villageId}/participate")
    private String participate(@PathVariable Integer villageId,
            @Validated @ModelAttribute("participateForm") VillageParticipateForm participateForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付取得
            int day = assist.selectLatestDay(villageId);
            assist.setIndexModel(villageId, day, model);
            return "village";
        }
        // 既にそのキャラが参戦していたらNG
        try {
            assist.assertAlreadyParticipateChara(villageId, participateForm);
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("participateErrorMessage", e.getMessage());
            // 最新の日付取得
            int day = assist.selectLatestDay(villageId);
            assist.setIndexModel(villageId, day, model);
            return "village";
        }

        // 入村
        assist.participate(villageId, participateForm, userInfo);

        return "redirect:/village/" + villageId;
    }

    // 管理者機能：参戦
    @PostMapping("/admin/village/{villageId}/allparticipate")
    private String allparticipate(@PathVariable Integer villageId, VillageParticipateForm participateForm, Model model) {
        // 参戦していないキャラを人数分探す
        ListResultBean<Chara> charaList = charaBhv.selectList(cb -> {
            cb.query().queryCharaGroup().existsVillageSettings(
                    villageSettingsCB -> villageSettingsCB.query().setVillageId_Equal(villageId));
            cb.query().notExistsVillagePlayer(villagePlayerCB -> villagePlayerCB.query().setVillageId_Equal(villageId));
            cb.fetchFirst(participateForm.getPersonNumber());
        });
        for (int i = 0; i < charaList.size(); i++) {
            int playerId = i + 2; // テストアカウントは2~
            // 希望役職をランダムに取得
            CDef.Skill randomSkill = CDef.Skill.values()[(int) (Math.random() * CDef.Skill.values().length - 1)];
            // 入村
            villageLogic.participate(villageId, playerId, charaList.get(i).getCharaId(), randomSkill, "テストアカウント入村です。playerId：" + playerId);
        }

        return "redirect:/village/" + villageId;
    }
}
